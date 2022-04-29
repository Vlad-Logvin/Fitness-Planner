package by.bsuir.fitness_planner.controller.plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import by.bsuir.fitness_planner.databinding.SleepFragmentBinding;

public class SleepFragment extends Fragment {
    private SleepFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SleepFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
