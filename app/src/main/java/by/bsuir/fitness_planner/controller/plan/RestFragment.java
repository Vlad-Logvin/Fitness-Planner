package by.bsuir.fitness_planner.controller.plan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.RestFragmentBinding;
import by.bsuir.fitness_planner.databinding.RestPopupBinding;
import by.bsuir.fitness_planner.model.Rest;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.util.InputFilterMinMax;

public class RestFragment extends Fragment {
    private RestFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = RestFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRest();
        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(RestFragment.this)
                .navigate(RestFragmentDirections.actionRestFragmentToGeneralPlanFragment(user)));
        binding.add.setOnClickListener(v -> addRest());
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setRest() {
        binding.amountRest.setText(ServiceFactory.getInstance().getRestService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Rest::getAmount).sum() + " hr");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addRest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View restPopup = getLayoutInflater().inflate(R.layout.rest_popup, null);
        builder.setView(restPopup)
                .setPositiveButton(R.string.rest, this::processAddingRest)
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
        RestPopupBinding.bind(restPopup).amountRest
                .setFilters(new InputFilter[]{new InputFilterMinMax(0, 993, 1)});
        builder.create().show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processAddingRest(DialogInterface dialog, int id) {
        String rest = ((EditText) ((AlertDialog) dialog).findViewById(R.id.amount_rest)).getText()
                .toString();
        if (isEmpty(rest)) {
            showNotification("Please, feel all the fields!");
        } else {
            ServiceFactory.getInstance().getRestService()
                    .rest(getContext(), new Rest(0, user.getId(), LocalDate.now(), Double
                            .parseDouble(rest)));
            setRest();
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    private void showNotification(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    private void init() {
        user = RestFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            showNotification("ERROR!!! NO USER FOUND!");
        }
    }
}
