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
import by.bsuir.fitness_planner.databinding.HistoryPopupBinding;
import by.bsuir.fitness_planner.databinding.WaterFragmentBinding;
import by.bsuir.fitness_planner.databinding.WaterPopupBinding;
import by.bsuir.fitness_planner.model.Food;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.model.Water;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.util.InputFilterMinMax;
import by.bsuir.fitness_planner.util.Response;

public class WaterFragment extends Fragment {
    private WaterFragmentBinding binding;
    private User user;
    private AlertDialog historyAlertDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = WaterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWater();
        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(WaterFragment.this)
                .navigate(WaterFragmentDirections.actionWaterFragmentToGeneralPlanFragment(user)));
        binding.add.setOnClickListener(v -> addWater());
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
            List<Water> water = ServiceFactory.getInstance().getWaterService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < water.size(); i++) {
                layout.addView(getTableRow(water.get(i)));
            }
            historyAlertDialog = builder.create();
            historyAlertDialog.show();
        } catch (Exception ignored) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    private TableRow getTableRow(Water water) {
        TableRow tr = new TableRow(getContext());
        fillTableRow(tr, String.valueOf(water.getAmount()));
        fillTableRow(tr, water.getCreated().toString());
        ImageButton button = new ImageButton(getContext());
        button.setImageIcon(Icon.createWithResource(getContext(), R.drawable.trash_24x24));
        button.setOnClickListener(v -> processRemoval(water));
        tr.addView(button);
        return tr;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processRemoval(Water water) {
        ServiceFactory.getInstance().getWaterService().removeDrunk(getContext(), water);
        setWater();
        historyAlertDialog.cancel();
        showHistory();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    private TableRow getHeadRow() {
        TableRow tr = new TableRow(getContext());
        fillTableRow(tr, "Amount");
        fillTableRow(tr, "Date");
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
    private void setWater() {
        binding.amountLiters.setText(ServiceFactory.getInstance().getWaterService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Water::getAmount).sum() + " lit");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addWater() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View waterPopup = getLayoutInflater().inflate(R.layout.water_popup, null);
        builder.setView(waterPopup)
                .setPositiveButton(R.string.water, this::processAddingWater)
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
        WaterPopupBinding.bind(waterPopup).amountLiters
                .setFilters(new InputFilter[]{new InputFilterMinMax(0, 20, 1)});
        builder.create().show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processAddingWater(DialogInterface dialog, int id) {
        String water = ((EditText) ((AlertDialog) dialog).findViewById(R.id.amount_liters))
                .getText()
                .toString();
        if (isEmpty(water)) {
            showNotification("Please, feel all the fields!");
        } else {
            ServiceFactory.getInstance().getWaterService()
                    .drink(getContext(), new Water(0, user.getId(), LocalDate.now(), Double
                            .parseDouble(water)));
            setWater();
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
        user = WaterFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            showNotification("ERROR!!! NO USER FOUND!");
        }
    }
}
