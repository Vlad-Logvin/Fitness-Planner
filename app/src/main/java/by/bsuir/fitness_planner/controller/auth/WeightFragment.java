package by.bsuir.fitness_planner.controller.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.databinding.WeightFragmentBinding;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.service.ServiceFactory;
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(WeightFragment.this)
                .navigate(processArgumentsSaving(WeightFragmentDirections
                        .actionWeightFragmentToHeightFragment())));
        binding.next.setOnClickListener(v -> processClickOnNext());
        binding.weight.setFilters(new InputFilter[]{new InputFilterMinMax(0, 300, 2)});
    }

    private void processClickOnNext() {
        if (binding.weight.getText().toString().isEmpty()) {
            showNotification();
        } else {
            user.setWeight(Double.parseDouble(binding.weight.getText().toString()));
            showApprovalMenu();
        }
    }

    private void showApprovalMenu() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                user.setLast(true);
                user.setAimSleep(8d);
                user.setAimCalories(2000d);
                user.setAimRest(4);
                user.setAimWater(3);
                ServiceFactory.getInstance().getUserService().save(getContext(), user);
                NavHostFragment.findNavController(WeightFragment.this)
                        .navigate(WeightFragmentDirections.actionWeightFragmentToStartFragment());
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Checked the entered data:\n" +
                "Email: " + user.getEmail() + "\n" +
                "Name: " + user.getName() + "\n" +
                "Age: " + user.getAge() + "\n" +
                "Gender: " + user.getGender() + "\n" +
                "Weight: " + user.getWeight() + "\n" +
                "Height: " + user.getHeight())
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

    private void showNotification() {
        Toast toast = Toast.makeText(getContext(), "Please, enter weight", Toast.LENGTH_LONG);
        toast.show();
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
