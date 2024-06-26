package com.example.mealplanner.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mealplanner.CreateMealActivity;
import com.example.mealplanner.CreateGroceryListActivty;
import com.example.mealplanner.CreateRecipeActivity;
import com.example.mealplanner.Recipe;
import com.example.mealplanner.RecipeDetailActivity;
import com.example.mealplanner.backend.DbHelper;
import com.example.mealplanner.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DbHelper dbHelper;

    // Fragment for the home page
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        CardView recipeCard = binding.recipeCard;
        TextView recipeText = binding.recipeText;

        // Fetching a random recipe from the db
        DbHelper dbHelper = new DbHelper(getContext());
        Recipe randomRecipe = dbHelper.getRandomRecipe();

        // Message in the clickable card with a random recipe
        recipeText.setText("Random recipe for you!\n\n" + "-> " + randomRecipe.getName() + " <-" + "\n\nCheck the recipe here");

        // OnClick method to open the recipe details
        recipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                intent.putExtra("RECIPE_ID", randomRecipe.getId());
                startActivity(intent);
            }
        });

        // Method to open the create new meal plan page
        final Button newMealButton = binding.newMeal;
        newMealButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateMealActivity.class);
                startActivity(intent);
            }
        });

        // Method to open the create new grocery list page
        final Button newGroceryListButton = binding.newGrocery;
        newGroceryListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateGroceryListActivty.class);
                startActivity(intent);
            }
        });

        // Method to open the create new recipe page
        final Button newRecipeButton = binding.newRecipe;
        newRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateRecipeActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}