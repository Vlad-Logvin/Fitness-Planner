package by.bsuir.fitness_planner.controller.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.AuthFragmentBinding;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.util.JSONUtil;

public class AuthFragment extends Fragment {
    private AuthFragmentBinding binding;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            user = (User) JSONUtil.getInstance().fromJson(savedInstanceState.getString("user"), User.class);
        }
        if (user == null) {
            this.user = new User();
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = AuthFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.nameInput.setText(user.getName());
        binding.emailInput.setText(user.getEmail());
        binding.signIn.setOnClickListener(v -> {
            NavHostFragment.findNavController(AuthFragment.this).navigate(R.id.action_AuthFragment_to_GenderFragment);
            user.setName(binding.nameInput.getText().toString());
            user.setEmail(binding.emailInput.getText().toString());
            //savedInstanceState.putString("user", JSONUtil.getInstance().toJson(user));
        });
        binding.back.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        requireActivity().finish();
                        System.exit(0);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you want to exit Fitness Planner?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("user", JSONUtil.getInstance().toJson(user));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
