package com.example.mealplanner.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.Meal;
import com.example.mealplanner.R;
import com.example.mealplanner.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.Map;

// Adapter for views displaying the meal plans
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private ArrayList<Meal> meals;
    private Map<Long, String> recipeIdToNameMap;

    // Constructor takes an ArrayList of Meal objects and a Map of recipe IDs to recipe names
    public MealAdapter(ArrayList<Meal> meals, Map<Long, String> recipeIdToNameMap) {
        this.meals = meals;
        this.recipeIdToNameMap = recipeIdToNameMap;
    }

    // Creating the view with the meal items (Meal Plans)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new ViewHolder(view);
    }

    // Populating a view with the meal and corresponding recipes
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        String breakfast = recipeIdToNameMap.get(meal.getBreakfastId());
        String lunch = recipeIdToNameMap.get(meal.getLunchId());
        String dinner = recipeIdToNameMap.get(meal.getDinnerId());

        holder.breakfastTextView.setText(breakfast);
        holder.lunchTextView.setText(lunch);
        holder.dinnerTextView.setText(dinner);

        // Method to open recipe details for breakfast
        holder.breakfastTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecipeDetailActivity(v, meal.getBreakfastId());
            }
        });

        // Method to open recipe details for lunch
        holder.lunchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecipeDetailActivity(v, meal.getLunchId());
            }
        });

        // Method to open recipe details for dinner
        holder.dinnerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecipeDetailActivity(v, meal.getDinnerId());
            }
        });
    }

    // Actual method opening the recipe details activity by ID of the recipe
    private void startRecipeDetailActivity(View v, long recipeId) {
        Intent intent = new Intent(v.getContext(), RecipeDetailActivity.class);
        intent.putExtra("RECIPE_ID", recipeId);
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView breakfastTextView;
        public TextView lunchTextView;
        public TextView dinnerTextView;

        public ViewHolder(View view) {
            super(view);
            breakfastTextView = view.findViewById(R.id.breakfast_meal);
            lunchTextView = view.findViewById(R.id.lunch_meal);
            dinnerTextView = view.findViewById(R.id.dinner_meal);
        }
    }
}
