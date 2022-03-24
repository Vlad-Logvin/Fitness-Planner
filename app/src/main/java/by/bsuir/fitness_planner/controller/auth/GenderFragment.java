package by.bsuir.fitness_planner.controller.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.GenderFragmentBinding;

public class GenderFragment extends Fragment {
    private GenderFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = GenderFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(GenderFragment.this)
                .navigate(R.id.action_GenderFragment_to_AuthFragment));
        binding.next.setOnClickListener(v -> NavHostFragment.findNavController(GenderFragment.this)
                .navigate(R.id.action_GenderFragment_to_AgeFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
