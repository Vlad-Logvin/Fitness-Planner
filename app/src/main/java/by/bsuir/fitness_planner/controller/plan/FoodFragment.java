package by.bsuir.fitness_planner.controller.plan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.FoodFragmentBinding;
import by.bsuir.fitness_planner.databinding.FoodPopupBinding;
import by.bsuir.fitness_planner.databinding.HistoryPopupBinding;
import by.bsuir.fitness_planner.model.Food;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.util.InputFilterMinMax;
import by.bsuir.fitness_planner.util.Response;

public class FoodFragment extends Fragment {
    private FoodFragmentBinding binding;
    private User user;
    private AlertDialog historyAlertDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = FoodFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFood();
        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(FoodFragment.this)
                .navigate(FoodFragmentDirections.actionFoodFragmentToGeneralPlanFragment(user)));
        binding.add.setOnClickListener(v -> addFood());
        binding.history.setOnClickListener(v -> showHistory());
        binding.textToEdit.setText(setTextToEdit());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String setTextToEdit() {
        double sum = ServiceFactory.getInstance().getFoodService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Food::getCalories).sum();
        double percentage = user.getAimCalories() / sum * 100.0;
        if (percentage > 60.0) {
            return Response.KEEP_GOING.getMessage();
        } else if (percentage >= 60.0 && percentage < 95.0) {
            return Response.ALMOST_DONE.getMessage();
        } else if (percentage >= 95 && percentage < 105) {
            return Response.DONE.getMessage();
        } else {
            return Response.PLEASE_STOP.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void showHistory() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View historyPopup = getLayoutInflater().inflate(R.layout.history_popup, null);
            builder.setView(historyPopup)
                    .setNeutralButton(R.string.close, (dialog, id) -> dialog.cancel());
            TableLayout layout = HistoryPopupBinding.bind(historyPopup).tableHistory;
            layout.addView(getHeadRow());
            List<Food> food = ServiceFactory.getInstance().getFoodService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < food.size(); i++) {
                layout.addView(getTableRow(food.get(i)));
            }
            historyAlertDialog = builder.create();
            historyAlertDialog.show();
        } catch (Exception ignored) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    private TableRow getTableRow(Food food) {
        TableRow tr = new TableRow(getContext());
        fillTableRow(tr, String.valueOf(food.getId()));
        fillTableRow(tr, food.getName());
        fillTableRow(tr, String.valueOf(food.getGrams()));
        fillTableRow(tr, String.valueOf(food.getCalories()));
        ImageButton button = new ImageButton(getContext());
        button.setImageIcon(Icon.createWithResource(getContext(), R.drawable.trash_24x24));
        button.setOnClickListener(v -> processRemoval(food));
        tr.addView(button);
        return tr;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processRemoval(Food food) {
        ServiceFactory.getInstance().getFoodService()
                .removeEaten(getContext(), food);
        setFood();
        historyAlertDialog.cancel();
        showHistory();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    private TableRow getHeadRow() {
        TableRow tr = new TableRow(getContext());
        fillTableRow(tr, "Name");
        fillTableRow(tr, "Grams");
        fillTableRow(tr, "Kcal");
        fillTableRow(tr, "Delete");
        return tr;
    }

    private void fillTableRow(TableRow tableRow, String name) {
        TextView tv = new TextView(getContext());
        tv.setText(name);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(20);
        tv.setMaxWidth(300);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableRow.addView(tv);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setFood() {
        binding.amountCalories.setText(ServiceFactory.getInstance().getFoodService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Food::getCalories).sum() + " kcal");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addFood() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View foodPopup = getLayoutInflater().inflate(R.layout.food_popup, null);
        builder.setView(foodPopup)
                .setPositiveButton(R.string.add, this::processAddingFood)
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
        FoodPopupBinding.bind(foodPopup).grams
                .setFilters(new InputFilter[]{new InputFilterMinMax(1, 15000, 2)});
        FoodPopupBinding.bind(foodPopup).calories
                .setFilters(new InputFilter[]{new InputFilterMinMax(1, 10000, 1)});
        builder.create().show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processAddingFood(DialogInterface dialog, int id) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.name)).getText()
                .toString();
        String grams = ((EditText) ((AlertDialog) dialog).findViewById(R.id.grams)).getText()
                .toString();
        String kilocalories = ((EditText) ((AlertDialog) dialog).findViewById(R.id.calories))
                .getText()
                .toString();
        if (isEmpty(name) || isEmpty(grams) || isEmpty(kilocalories)) {
            showNotification("Please, feel all the fields!");
        } else {
            ServiceFactory.getInstance().getFoodService()
                    .eat(getContext(), new Food(0, user.getId(), LocalDate.now(), name, Double
                            .parseDouble(grams), Double.parseDouble(kilocalories)));
            setFood();
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    private void showNotification(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    private void init() {
        user = FoodFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            showNotification("ERROR!!! NO USER FOUND!");
        }
    }
}
