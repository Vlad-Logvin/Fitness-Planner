package by.bsuir.fitness_planner.controller.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.GenderFragmentBinding;
import by.bsuir.fitness_planner.model.User;

public class GenderFragment extends Fragment {
    private GenderFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = GenderFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(GenderFragment.this)
                .navigate(processArgumentsSaving(GenderFragmentDirections
                        .actionGenderFragmentToAuthFragment())));
        binding.female.setOnClickListener(v -> checkFemale());
        binding.male.setOnClickListener(v -> checkMale());
        binding.next.setOnClickListener(v -> processClickOnNext());
    }

    private void checkFemale() {
        binding.female.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources()
                .getDrawable(R.drawable.female_gender_checked_64x64), null, null);
        binding.male.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources()
                .getDrawable(R.drawable.male_gender_unchecked_64x64), null, null);
    }

    private void checkMale() {
        binding.male.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources()
                .getDrawable(R.drawable.male_gender_checked_64x64), null, null);
        binding.female.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources()
                .getDrawable(R.drawable.female_gender_unchecked_64x64), null, null);
    }

    private void processClickOnNext() {
        if (binding.gender.getCheckedRadioButtonId() != -1) {
            NavHostFragment.findNavController(GenderFragment.this)
                    .navigate(processArgumentsSaving(GenderFragmentDirections
                            .actionGenderFragmentToAgeFragment()));
        } else {
            showNotification();
        }
    }

    private void initView() {
        String gender = user.getGender();
        if ("Male".equals(gender)) {
            binding.male.setChecked(true);
            checkMale();
        } else if ("Female".equals(gender)) {
            binding.female.setChecked(true);
            checkFemale();
        }
    }

    private void showNotification() {
        Toast toast = Toast.makeText(getContext(), "Please, enter gender", Toast.LENGTH_LONG);
        toast.show();
    }


    @NonNull
    private NavDirections processArgumentsSaving(NavDirections action) {
        user.setGender(binding.male.isChecked() ? "Male" : "Female");
        if (action instanceof GenderFragmentDirections.ActionGenderFragmentToAuthFragment) {
            GenderFragmentDirections.ActionGenderFragmentToAuthFragment a = (GenderFragmentDirections.ActionGenderFragmentToAuthFragment) action;
            a.setUser(user);
        } else if (action instanceof GenderFragmentDirections.ActionGenderFragmentToAgeFragment) {
            GenderFragmentDirections.ActionGenderFragmentToAgeFragment a = (GenderFragmentDirections.ActionGenderFragmentToAgeFragment) action;
            a.setUser(user);
        }
        return action;
    }

    private void init() {
        user = GenderFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            user = new User();
        }
    }
}
