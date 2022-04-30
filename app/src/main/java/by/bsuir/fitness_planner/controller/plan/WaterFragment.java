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
import by.bsuir.fitness_planner.databinding.WaterFragmentBinding;
import by.bsuir.fitness_planner.databinding.WaterPopupBinding;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.model.Water;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.util.InputFilterMinMax;

public class WaterFragment extends Fragment {
    private WaterFragmentBinding binding;
    private User user;

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
