package com.example.mealplanner.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealplanner.R;

import com.example.mealplanner.Recipe;
import com.example.mealplanner.backend.DbHelper;
import com.example.mealplanner.databinding.FragmentRecipesBinding;
import com.example.mealplanner.ui.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment {

    private FragmentRecipesBinding binding;
    private RecipeAdapter recipeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecipesViewModel recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRecipes;
        recipesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recipes");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        // Initialize the database helper
        DbHelper dbHelper = new DbHelper(getContext());

        // Get the recipes from the database
        ArrayList<Recipe> recipes = dbHelper.getRecipes();

        // Create a new RecyclerView
        RecyclerView recyclerView = new RecyclerView(getContext());

        // Create new layout parameters
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
        );

        // Convert 56dp to pixels
        float density = getResources().getDisplayMetrics().density;
        int bottomPadding = Math.round(56 * density); // 56dp

        // Set the RecyclerView's layout parameters
        recyclerView.setLayoutParams(layoutParams);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeAdapter = new RecipeAdapter(recipes);
        recyclerView.setAdapter(recipeAdapter);

        // Add padding to the bottom of the RecyclerView
        recyclerView.setPadding(0, 100, 0, bottomPadding);
        recyclerView.setClipToPadding(false);

        // Add the RecyclerView to the root view at the first position
        ((ViewGroup) root).addView(recyclerView, 0);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


