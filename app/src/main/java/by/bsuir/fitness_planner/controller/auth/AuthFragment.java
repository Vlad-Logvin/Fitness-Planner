package by.bsuir.fitness_planner.controller.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.databinding.AuthFragmentBinding;
import by.bsuir.fitness_planner.model.User;

public class AuthFragment extends Fragment {
    private AuthFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = AuthFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        binding.signIn.setOnClickListener(v -> processClickOnSignIn());
        binding.back.setOnClickListener(v -> processExit());
    }

    private void initView() {
        try {
            binding.nameInput.setText(user.getName());
            binding.emailInput.setText(user.getEmail());
        } catch (Exception ignored) {
        }
    }

    private void showNotification(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    private void processExit() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                requireActivity().finish();
                System.exit(0);
            }
        };
        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to exit Fitness Planner?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @NonNull
    private AuthFragmentDirections.ActionAuthFragmentToGenderFragment processArgumentSaving() {
        user.setName(binding.nameInput.getText().toString());
        user.setEmail(binding.emailInput.getText().toString());
        AuthFragmentDirections.ActionAuthFragmentToGenderFragment action = AuthFragmentDirections
                .actionAuthFragmentToGenderFragment();
        action.setUser(user);
        return action;
    }

    private void processClickOnSignIn() {
        if (validateInput()) {
            NavHostFragment.findNavController(AuthFragment.this).navigate(processArgumentSaving());
        }
    }

    private boolean validateInput() {
        boolean isValid = true;
        String name = binding.nameInput.getText().toString();
        String email = binding.emailInput.getText().toString();
        if (!name.trim().matches("[a-zA-Z0-9а-яА-Я_\\-\\s]{3,40}")) {
            showNotification("Not valid name, name must contain a-z, A-Z, 0-9, а-я, А-Я, _, -, space from 3 to 40 symbols");
            isValid = false;
        }
        if (!email.trim()
                .matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            showNotification("Not valid email");
            isValid = false;
        }
        return isValid;
    }

    private void init() {
        user = AuthFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            user = new User();
        }
    }
}
