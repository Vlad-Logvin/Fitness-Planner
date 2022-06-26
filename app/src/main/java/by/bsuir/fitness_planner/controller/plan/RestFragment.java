package by.bsuir.fitness_planner.controller.plan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.HistoryPopupBinding;
import by.bsuir.fitness_planner.databinding.RestFragmentBinding;
import by.bsuir.fitness_planner.databinding.RestPopupBinding;
import by.bsuir.fitness_planner.model.Rest;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.util.InputFilterMinMax;
import by.bsuir.fitness_planner.util.Response;

public class RestFragment extends Fragment {
    private RestFragmentBinding binding;
    private User user;
    private AlertDialog historyAlertDialog;

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
        binding.history.setOnClickListener(v -> showHistory());
        binding.textToEdit.setText(setTextToEdit());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String setTextToEdit() {
        double sum = ServiceFactory.getInstance().getRestService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Rest::getAmount).sum();
        double percentage = sum / user.getAimRest() * 100.0;
        if (percentage < 60.0) {
            return Response.KEEP_GOING.getMessage();
        } else if (percentage >= 60.0 && percentage < 95.0) {
            return Response.ALMOST_DONE.getMessage();
        } else if (percentage >= 95 && percentage < 105) {
            return Response.DONE.getMessage();
        } else {
            return Response.PLEASE_STOP.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void showHistory() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View historyPopup = getLayoutInflater().inflate(R.layout.history_popup, null);
            builder.setView(historyPopup)
                    .setNeutralButton(R.string.close, (dialog, id) -> dialog.cancel());
            TableLayout layout = HistoryPopupBinding.bind(historyPopup).tableHistory;
            layout.addView(getHeadRow());
            List<Rest> rest = ServiceFactory.getInstance().getRestService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < rest.size(); i++) {
                layout.addView(getTableRow(rest.get(i)));
            }
            historyAlertDialog = builder.create();
            historyAlertDialog.show();
        } catch (Exception ignored) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    private TableRow getTableRow(Rest rest) {
        TableRow tr = new TableRow(getContext());
        fillTableRow(tr, String.valueOf(rest.getAmount()));
        fillTableRow(tr, rest.getCreated().toString());
        ImageButton button = new ImageButton(getContext());
        button.setImageIcon(Icon.createWithResource(getContext(), R.drawable.trash_24x24));
        button.setOnClickListener(v -> processRemoval(rest));
        tr.addView(button);
        return tr;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processRemoval(Rest rest) {
        ServiceFactory.getInstance().getRestService().removeRested(getContext(), rest);
        setRest();
        historyAlertDialog.cancel();
        showHistory();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    private TableRow getHeadRow() {
        TableRow tr = new TableRow(getContext());
        fillTableRow(tr, "Amount");
        fillTableRow(tr, "Date");
        fillTableRow(tr, "Delete");
        return tr;
    }

    private void fillTableRow(TableRow tableRow, String name) {
        TextView tv = new TextView(getContext());
        tv.setText(name);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(20);
        tv.setMaxWidth(300);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableRow.addView(tv);
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
