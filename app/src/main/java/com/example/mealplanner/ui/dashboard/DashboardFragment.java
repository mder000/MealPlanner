package com.example.mealplanner.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.Meal;
import com.example.mealplanner.R;
import com.example.mealplanner.Recipe;
import com.example.mealplanner.backend.DbHelper;
import com.example.mealplanner.databinding.FragmentDashboardBinding;
import com.example.mealplanner.ui.MealAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    // Fragment to display all meal plans
    // Created from a template, navigation menu template hence the dashboard name
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        DbHelper dbHelper = new DbHelper(getContext());

        // Fetching all of the meals and recipes from the database
        ArrayList<Meal> meals = dbHelper.getMeals();
        ArrayList<Recipe> recipes = dbHelper.getRecipes();

        // Mapping recipe names with IDs to display them, for each meal
        Map<Long, String> recipeIdToNameMap = new HashMap<>();
        for (Recipe recipe : recipes) {
            recipeIdToNameMap.put(recipe.getId(), recipe.getName());
        }

        RecyclerView recyclerView = root.findViewById(R.id.meals_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MealAdapter mealAdapter = new MealAdapter(meals, recipeIdToNameMap);
        recyclerView.setAdapter(mealAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
