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
import java.util.Random;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.HistoryPopupBinding;
import by.bsuir.fitness_planner.databinding.SleepFragmentBinding;
import by.bsuir.fitness_planner.databinding.SleepPopupBinding;
import by.bsuir.fitness_planner.model.Sleep;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.util.InputFilterMinMax;
import by.bsuir.fitness_planner.util.Response;

public class SleepFragment extends Fragment {
    private SleepFragmentBinding binding;
    private User user;
    private Random random = new Random();
    private AlertDialog historyAlertDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = SleepFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSleep();
        binding.back.setOnClickListener(v -> NavHostFragment.findNavController(SleepFragment.this)
                .navigate(SleepFragmentDirections.actionSleepFragmentToGeneralPlanFragment(user)));
        binding.add.setOnClickListener(v -> addSleep());
        binding.history.setOnClickListener(v -> showHistory());
        binding.textToEdit.setText(setTextToEdit());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String setTextToEdit() {
        double sum = ServiceFactory.getInstance().getSleepService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Sleep::getAmount).sum();
        double percentage = sum / user.getAimSleep() * 100.0;
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
            List<Sleep> sleep = ServiceFactory.getInstance().getSleepService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < sleep.size(); i++) {
                layout.addView(getTableRow(sleep.get(i)));
            }
            historyAlertDialog = builder.create();
            historyAlertDialog.show();
        } catch (Exception ignored) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    private TableRow getTableRow(Sleep sleep) {
        TableRow tr = new TableRow(getContext());
        fillTableRow(tr, String.valueOf(sleep.getAmount()));
        fillTableRow(tr, sleep.getCreated().toString());
        ImageButton button = new ImageButton(getContext());
        button.setImageIcon(Icon.createWithResource(getContext(), R.drawable.trash_24x24));
        button.setOnClickListener(v -> processRemoval(sleep));
        tr.addView(button);
        return tr;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processRemoval(Sleep sleep) {
        ServiceFactory.getInstance().getSleepService().removeSlept(getContext(), sleep);
        setSleep();
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
    private void setSleep() {
        binding.amountSleep.setText(ServiceFactory.getInstance().getSleepService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Sleep::getAmount).sum() + " hr");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addSleep() {
        double sum = ServiceFactory
                .getInstance().getSleepService()
                .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                .mapToDouble(Sleep::getAmount).sum();
        if (sum < 24) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View sleepPopup = getLayoutInflater().inflate(R.layout.sleep_popup, null);
            builder.setView(sleepPopup)
                    .setPositiveButton(R.string.sleep, this::processAddingSleep)
                    .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
            SleepPopupBinding.bind(sleepPopup).amountSleep
                    .setFilters(new InputFilter[]{new InputFilterMinMax(0, (int) (25.0 - sum), 1)});
            builder.create().show();
        } else {
            binding.add.setX(random.nextFloat() * 800 + 50);
            binding.add.setY(random.nextFloat() * 1500 + 50);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processAddingSleep(DialogInterface dialog, int id) {
        String sleep = ((EditText) ((AlertDialog) dialog).findViewById(R.id.amount_sleep))
                .getText()
                .toString();
        if (isEmpty(sleep)) {
            showNotification("Please, feel all the fields!");
        } else {
            ServiceFactory.getInstance().getSleepService()
                    .sleep(getContext(), new Sleep(0, user.getId(), LocalDate.now(), Double
                            .parseDouble(sleep)));
            setSleep();
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
        user = SleepFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            showNotification("ERROR!!! NO USER FOUND!");
        }
    }
}
