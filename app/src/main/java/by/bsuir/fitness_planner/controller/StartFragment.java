package by.bsuir.fitness_planner.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.databinding.StartFragmentBinding;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.service.ServiceFactory;

public class StartFragment extends Fragment {
    private StartFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StartFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        User user = ServiceFactory.getInstance().getUserService().findLast(getContext(), true);
        if (user != null) {
            StartFragmentDirections.ActionStartFragmentToGeneralPlanFragment action = StartFragmentDirections
                    .actionStartFragmentToGeneralPlanFragment();
            action.setUser(user);
            NavHostFragment.findNavController(StartFragment.this).navigate(action);
        } else {
            NavHostFragment.findNavController(StartFragment.this)
                    .navigate(StartFragmentDirections.actionStartFragmentToAuthFragment());
        }
    }
}
