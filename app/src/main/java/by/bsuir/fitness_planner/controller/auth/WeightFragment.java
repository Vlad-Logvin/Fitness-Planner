package by.bsuir.fitness_planner.controller.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.databinding.WeightFragmentBinding;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.util.InputFilterMinMax;

public class WeightFragment extends Fragment {
    private WeightFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = WeightFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(WeightFragment.this)
                .navigate(processArgumentsSaving(WeightFragmentDirections
                        .actionWeightFragmentToHeightFragment())));
        binding.next.setOnClickListener(v -> showApprovalMenu());
        binding.weight.setFilters(new InputFilter[]{new InputFilterMinMax(0, 300, 2)});
    }

    private void showApprovalMenu() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                //mark as approved
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Checked the entered data:\n")
                .setPositiveButton("Approve", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    @NonNull
    private NavDirections processArgumentsSaving(NavDirections action) {
        try {
            user.setWeight(Double.parseDouble(binding.weight.getText().toString()));
        } catch (Exception ignored) {
        }
        if (action instanceof WeightFragmentDirections.ActionWeightFragmentToHeightFragment) {
            WeightFragmentDirections.ActionWeightFragmentToHeightFragment a = (WeightFragmentDirections.ActionWeightFragmentToHeightFragment) action;
            a.setUser(user);
        }
        return action;
    }


    private void initView() {
        try {
            String weight = String.valueOf(user.getWeight());
            binding.weight.setText("0.0".equals(weight) ? "" : weight);
        } catch (Exception ignored) {
        }
    }

    private void init() {
        user = WeightFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            user = new User();
        }
    }
}
