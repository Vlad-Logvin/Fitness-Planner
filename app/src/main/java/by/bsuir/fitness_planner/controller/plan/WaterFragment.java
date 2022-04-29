package by.bsuir.fitness_planner.controller.plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import by.bsuir.fitness_planner.databinding.WaterFragmentBinding;

public class WaterFragment extends Fragment {
    private WaterFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WaterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
