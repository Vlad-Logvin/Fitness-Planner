package by.bsuir.fitness_planner.controller.plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import by.bsuir.fitness_planner.databinding.FoodFragmentBinding;

public class FoodFragment extends Fragment {
    private FoodFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FoodFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


}
