package by.bsuir.fitness_planner.controller.plan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.databinding.GeneralPlanFragmentBinding;
import by.bsuir.fitness_planner.model.User;

public class GeneralPlanFragment extends Fragment {
    private GeneralPlanFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = GeneralPlanFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.water
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToWaterFragment(user)));
        binding.rest
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToRestFragment(user)));
        binding.sleep
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToSleepFragment(user)));
        binding.food
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToFoodFragment(user)));

        binding.back.setOnClickListener(v -> processExit());
    }

    private void navigateTo(NavDirections direction) {
        NavHostFragment.findNavController(GeneralPlanFragment.this).navigate(direction);
    }

    private void processExit() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                requireActivity().finish();
                System.exit(0);
            }
        };
        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to exit Fitness Planner?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void showNotification(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    private void init() {
        user = GeneralPlanFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            showNotification("ERROR!!! NO USER FOUND!");
        }
    }
}
