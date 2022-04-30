package by.bsuir.fitness_planner.controller.plan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.FoodFragmentBinding;
import by.bsuir.fitness_planner.databinding.FoodPopupBinding;
import by.bsuir.fitness_planner.model.Food;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.util.InputFilterMinMax;

public class FoodFragment extends Fragment {
    private FoodFragmentBinding binding;
    private User user;

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
                .setPositiveButton(R.string.add_food, this::processAddingFood)
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
        FoodPopupBinding.bind(foodPopup).grams
                .setFilters(new InputFilter[]{new InputFilterMinMax(1, 15000, 2)});
        FoodPopupBinding.bind(foodPopup).calories
                .setFilters(new InputFilter[]{new InputFilterMinMax(1, 10000, 2)});
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
