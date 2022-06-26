package by.bsuir.fitness_planner.controller.plan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.R;
import by.bsuir.fitness_planner.databinding.GeneralPlanFragmentBinding;
import by.bsuir.fitness_planner.model.Food;
import by.bsuir.fitness_planner.model.Rest;
import by.bsuir.fitness_planner.model.Sleep;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.model.Water;
import by.bsuir.fitness_planner.service.ServiceFactory;
import by.bsuir.fitness_planner.service.UserService;

public class GeneralPlanFragment extends Fragment {
    private GeneralPlanFragmentBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        binding = GeneralPlanFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        buildFoodGraph();
        buildWaterGraph();
        buildRestGraph();
        buildSleepGraph();
        binding.water
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToWaterFragment(user)));
        binding.rest
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToRestFragment(user)));
        binding.sleep
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToSleepFragment(user)));
        binding.food
                .setOnClickListener(v -> navigateTo(GeneralPlanFragmentDirections
                        .actionGeneralPlanFragmentToFoodFragment(user)));
        binding.aim.setOnClickListener(v -> setUpAim());
        binding.back.setOnClickListener(v -> processExit());
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpAim() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View aimPopup = getLayoutInflater().inflate(R.layout.aim_popup, null);
        ((EditText) aimPopup.findViewById(R.id.kilocalories_input))
                .setText(Double.toString(user.getAimCalories()));
        ((EditText) aimPopup.findViewById(R.id.rest_input))
                .setText(Double.toString(user.getAimRest()));
        ((EditText) aimPopup.findViewById(R.id.sleep_input))
                .setText(Double.toString(user.getAimSleep()));
        ((EditText) aimPopup.findViewById(R.id.water_input))
                .setText(Double.toString(user.getAimWater()));
        builder.setView(aimPopup)
                .setNegativeButton(R.string.close, (dialog, id) -> dialog.cancel())
                .setPositiveButton(R.string.save, this::processSetUpAim);
        builder.create().show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processSetUpAim(DialogInterface dialog, int id) {
        String kilocalories = ((EditText) ((AlertDialog) dialog)
                .findViewById(R.id.kilocalories_input))
                .getText()
                .toString();
        String sleep = ((EditText) ((AlertDialog) dialog).findViewById(R.id.sleep_input))
                .getText()
                .toString();
        String water = ((EditText) ((AlertDialog) dialog).findViewById(R.id.water_input))
                .getText()
                .toString();
        String rest = ((EditText) ((AlertDialog) dialog).findViewById(R.id.rest_input))
                .getText()
                .toString();
        if (isEmpty(kilocalories) || isEmpty(sleep) || isEmpty(water) || isEmpty(rest)) {
            showNotification("Please, feel all the fields!");
        } else {
            UserService userService = ServiceFactory.getInstance().getUserService();
            user.setAimWater(Double.parseDouble(water));
            user.setAimSleep(Double.parseDouble(sleep));
            user.setAimRest(Double.parseDouble(rest));
            user.setAimCalories(Double.parseDouble(kilocalories));
            userService.save(getContext(), user);
        }
    }


    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildSleepGraph() {
        try {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            List<Sleep> sleep = ServiceFactory.getInstance().getSleepService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < sleep.size(); i++) {
                series.appendData(new DataPoint(i, sleep.get(i).getAmount()), true, sleep.size());
                series.setThickness(8);
                series.setColor(R.color.sleep_color);
            }
            binding.sleepGraph.addSeries(series);
            applyStylesToGraphView(binding.sleepGraph, "Sleep Graph");
        } catch (Exception ignored) {
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildRestGraph() {
        try {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            List<Rest> rest = ServiceFactory.getInstance().getRestService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < rest.size(); i++) {
                series.appendData(new DataPoint(i, rest.get(i).getAmount()), true, rest.size());
                series.setThickness(8);
                series.setColor(R.color.rest_color);
            }
            binding.restGraph.addSeries(series);
            applyStylesToGraphView(binding.restGraph, "Rest Graph");
        } catch (Exception ignored) {
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildWaterGraph() {
        try {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            List<Water> water = ServiceFactory.getInstance().getWaterService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < water.size(); i++) {
                series.appendData(new DataPoint(i, water.get(i).getAmount()), true, water.size());
                series.setThickness(8);
                series.setColor(R.color.water_color);
            }
            binding.waterGraph.addSeries(series);
            applyStylesToGraphView(binding.waterGraph, "Water Graph");
        } catch (Exception ignored) {
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildFoodGraph() {
        try {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            List<Food> food = ServiceFactory.getInstance().getFoodService()
                    .findByUserId(getContext(), user.getId());
            for (int i = 0; i < food.size(); i++) {
                series.appendData(new DataPoint(i, food.get(i).getCalories()), true, food.size());
                series.setThickness(8);
                series.setColor(R.color.food_color);
            }
            GraphView caloriesGraph = binding.caloriesGraph;
            caloriesGraph.addSeries(series);
            applyStylesToGraphView(caloriesGraph, "Calories Graph");
        } catch (Exception ignored) {
        }
    }

    private void applyStylesToGraphView(GraphView caloriesGraph, String title) {
        caloriesGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        caloriesGraph.getViewport().setDrawBorder(true);
        caloriesGraph.setTitle(title);
        caloriesGraph.setTitleTextSize(66);
    }

    private void navigateTo(NavDirections direction) {
        NavHostFragment.findNavController(GeneralPlanFragment.this).navigate(direction);
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

    private void showNotification(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    private void init() {
        user = GeneralPlanFragmentArgs.fromBundle(getArguments()).getUser();
        if (user == null) {
            showNotification("ERROR!!! NO USER FOUND!");
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        try {
            binding.amountCalories.setText(ServiceFactory.getInstance().getFoodService()
                    .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                    .mapToDouble(Food::getCalories).sum() + " kcal");
        } catch (Exception ignored) {
        }
        try {
            binding.amountLiters.setText(ServiceFactory.getInstance().getWaterService()
                    .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                    .mapToDouble(Water::getAmount).sum() + " lit");
        } catch (Exception ignored) {
        }
        try {
            binding.amountRest.setText(ServiceFactory.getInstance().getRestService()
                    .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                    .mapToDouble(Rest::getAmount).sum() + " hr");
        } catch (Exception ignored) {
        }
        try {
            binding.amountSleep.setText(ServiceFactory.getInstance().getSleepService()
                    .findByUserIdAndDate(getContext(), user.getId(), LocalDate.now()).stream()
                    .mapToDouble(Sleep::getAmount).sum() + " hr");
        } catch (Exception ignored) {
        }
    }

    /*
    ServiceFactory.getInstance().getFoodService().eat(getContext(), new Food(0, user.getId(), LocalDate.now().minusDays(5), "Apple", 2000, 3000));
ServiceFactory.getInstance().getFoodService().eat(getContext(), new Food(0, user.getId(), LocalDate.now().minusDays(4), "Apple", 2000, 2500));
ServiceFactory.getInstance().getFoodService().eat(getContext(), new Food(0, user.getId(), LocalDate.now().minusDays(3), "Apple", 2000, 2750));
ServiceFactory.getInstance().getFoodService().eat(getContext(), new Food(0, user.getId(), LocalDate.now().minusDays(2), "Apple", 2000, 3000));
ServiceFactory.getInstance().getFoodService().eat(getContext(), new Food(0, user.getId(), LocalDate.now().minusDays(1), "Apple", 2000, 4000));
ServiceFactory.getInstance().getFoodService().eat(getContext(), new Food(0, user.getId(), LocalDate.now(), "Apple", 2000, 3000));

ServiceFactory.getInstance().getSleepService().sleep(getContext(), new Sleep(0, user.getId(), LocalDate.now().minusDays(5), 8));
ServiceFactory.getInstance().getSleepService().sleep(getContext(), new Sleep(0, user.getId(), LocalDate.now().minusDays(4), 7));
ServiceFactory.getInstance().getSleepService().sleep(getContext(), new Sleep(0, user.getId(), LocalDate.now().minusDays(3), 9));
ServiceFactory.getInstance().getSleepService().sleep(getContext(), new Sleep(0, user.getId(), LocalDate.now().minusDays(2), 10));
ServiceFactory.getInstance().getSleepService().sleep(getContext(), new Sleep(0, user.getId(), LocalDate.now().minusDays(1), 4));

ServiceFactory.getInstance().getRestService().rest(getContext(), new Rest(0, user.getId(), LocalDate.now().minusDays(5), 5));
ServiceFactory.getInstance().getRestService().rest(getContext(), new Rest(0, user.getId(), LocalDate.now().minusDays(4), 4));
ServiceFactory.getInstance().getRestService().rest(getContext(), new Rest(0, user.getId(), LocalDate.now().minusDays(3), 5));
ServiceFactory.getInstance().getRestService().rest(getContext(), new Rest(0, user.getId(), LocalDate.now().minusDays(2), 4));
ServiceFactory.getInstance().getRestService().rest(getContext(), new Rest(0, user.getId(), LocalDate.now().minusDays(1), 5));

ServiceFactory.getInstance().getWaterService().drink(getContext(), new Water(0, user.getId(), LocalDate.now().minusDays(5), 3));
ServiceFactory.getInstance().getWaterService().drink(getContext(), new Water(0, user.getId(), LocalDate.now().minusDays(4), 4));
ServiceFactory.getInstance().getWaterService().drink(getContext(), new Water(0, user.getId(), LocalDate.now().minusDays(3), 2));
ServiceFactory.getInstance().getWaterService().drink(getContext(), new Water(0, user.getId(), LocalDate.now().minusDays(2), 3));
ServiceFactory.getInstance().getWaterService().drink(getContext(), new Water(0, user.getId(), LocalDate.now().minusDays(1), 1));
     */
}
