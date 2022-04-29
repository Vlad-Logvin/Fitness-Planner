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

import by.bsuir.fitness_planner.databinding.AgeFragmentBinding;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.util.InputFilterMinMax;

public class AgeFragment extends Fragment {
    private AgeFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = AgeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(AgeFragment.this)
                .navigate(processArgumentsSaving(AgeFragmentDirections
                        .actionAgeFragmentToGenderFragment())));
        binding.next.setOnClickListener(v -> processClickOnNext());
        binding.age.setFilters(new InputFilter[]{new InputFilterMinMax(0, 118, 0)});
    }

    private void processClickOnNext() {
        if (!binding.age.getText().toString().isEmpty()) {
            NavHostFragment.findNavController(AgeFragment.this)
                    .navigate(processArgumentsSaving(AgeFragmentDirections
                            .actionAgeFragmentToHeightFragment()));
        } else {
            showNotification();
        }
    }

    private void showNotification() {
        Toast toast = Toast.makeText(getContext(), "Please, enter age", Toast.LENGTH_LONG);
        toast.show();
    }

    @NonNull
    private NavDirections processArgumentsSaving(NavDirections action) {
        try {
            user.setAge(Integer.parseInt(binding.age.getText().toString()));
        } catch (Exception ignored) {
        }
        if (action instanceof AgeFragmentDirections.ActionAgeFragmentToGenderFragment) {
            AgeFragmentDirections.ActionAgeFragmentToGenderFragment a = (AgeFragmentDirections.ActionAgeFragmentToGenderFragment) action;
            a.setUser(user);
        } else if (action instanceof AgeFragmentDirections.ActionAgeFragmentToHeightFragment) {
            AgeFragmentDirections.ActionAgeFragmentToHeightFragment a = (AgeFragmentDirections.ActionAgeFragmentToHeightFragment) action;
            a.setUser(user);
        }
        return action;
    }

    private void initView() {
        try {
            String age = String.valueOf(user.getAge());
            binding.age.setText("0".equals(age) ? "" : age);
        } catch (Exception ignored) {
        }
    }

    private void init() {
        user = AgeFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            user = new User();
        }
    }
}
