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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Initialize the database helper
        DbHelper dbHelper = new DbHelper(getContext());

        // Get the meals and recipes from the database
        ArrayList<Meal> meals = dbHelper.getMeals();
        ArrayList<Recipe> recipes = dbHelper.getRecipes();

        // Create a map of recipe names to their IDs
        Map<Long, String> recipeIdToNameMap = new HashMap<>();
        for (Recipe recipe : recipes) {
            recipeIdToNameMap.put(recipe.getId(), recipe.getName());
        }

        // Get the RecyclerView from the layout
        RecyclerView recyclerView = root.findViewById(R.id.meals_recyclerview);

        // Set up the RecyclerView
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
