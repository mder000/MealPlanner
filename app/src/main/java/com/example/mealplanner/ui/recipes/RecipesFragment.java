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

    // Fragment to display all of the recipes
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecipesViewModel recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRecipes;
        recipesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Changing the text in the action bar and deleting the go back button
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recipes");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        DbHelper dbHelper = new DbHelper(getContext());

        // Fetching all the recipes
        ArrayList<Recipe> recipes = dbHelper.getRecipes();

        // Setting up the recycler view
        RecyclerView recyclerView = new RecyclerView(getContext());

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
        );

        // Calculating the bottom padding for the recycler view to stay above the navigation menu
        float density = getResources().getDisplayMetrics().density;
        int bottomPadding = Math.round(56 * density);

        recyclerView.setLayoutParams(layoutParams);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeAdapter = new RecipeAdapter(recipes);
        recyclerView.setAdapter(recipeAdapter);

        // Setting up the padding
        recyclerView.setPadding(0, 100, 0, bottomPadding);
        recyclerView.setClipToPadding(false);

        // Recycle view added dynamically to the root as the fragment is not a child of the main activity
        ((ViewGroup) root).addView(recyclerView, 0);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


