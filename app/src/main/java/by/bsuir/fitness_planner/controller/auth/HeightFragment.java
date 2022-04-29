package by.bsuir.fitness_planner.controller.auth;

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

import by.bsuir.fitness_planner.databinding.HeightFragmentBinding;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.util.InputFilterMinMax;

public class HeightFragment extends Fragment {
    private HeightFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = HeightFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(HeightFragment.this)
                .navigate(processArgumentsSaving(HeightFragmentDirections
                        .actionHeightFragmentToAgeFragment())));
        binding.next.setOnClickListener(v -> processClickOnNext());
        binding.height.setFilters(new InputFilter[]{new InputFilterMinMax(0, 300, 2)});
    }

    private void processClickOnNext() {
        if (!binding.height.getText().toString().isEmpty()) {
            NavHostFragment.findNavController(HeightFragment.this)
                    .navigate(processArgumentsSaving(HeightFragmentDirections
                            .actionHeightFragmentToWeightFragment()));
        } else {
            showNotification();
        }
    }

    private void showNotification() {
        Toast toast = Toast.makeText(getContext(), "Please, enter height", Toast.LENGTH_LONG);
        toast.show();
    }

    @NonNull
    private NavDirections processArgumentsSaving(NavDirections action) {
        try {
            user.setHeight(Double.parseDouble(binding.height.getText().toString()));
        } catch (Exception ignored) {
        }
        if (action instanceof HeightFragmentDirections.ActionHeightFragmentToAgeFragment) {
            HeightFragmentDirections.ActionHeightFragmentToAgeFragment a = (HeightFragmentDirections.ActionHeightFragmentToAgeFragment) action;
            a.setUser(user);
        } else if (action instanceof HeightFragmentDirections.ActionHeightFragmentToWeightFragment) {
            HeightFragmentDirections.ActionHeightFragmentToWeightFragment a = (HeightFragmentDirections.ActionHeightFragmentToWeightFragment) action;
            a.setUser(user);
        }
        return action;
    }

    private void initView() {
        try {
            String height = String.valueOf(user.getHeight());
            binding.height.setText("0.0".equals(height) ? "" : height);
        } catch (Exception ignored) {
        }
    }

    private void init() {
        user = HeightFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            user = new User();
        }
    }
}
