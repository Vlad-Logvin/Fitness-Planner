package by.bsuir.fitness_planner.controller.auth;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.AgeFragmentBinding;
import by.bsuir.fitness_planner.util.InputFilterMinMax;

public class AgeFragment extends Fragment {
    private AgeFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = AgeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(AgeFragment.this)
                .navigate(R.id.action_AgeFragment_to_GenderFragment));
        binding.next.setOnClickListener(v -> NavHostFragment.findNavController(AgeFragment.this)
                .navigate(R.id.action_AgeFragment_to_HeightFragment));
        binding.age.setFilters(new InputFilter[]{new InputFilterMinMax(1, 118, 0)});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
