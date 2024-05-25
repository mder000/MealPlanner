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

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private ArrayList<Meal> meals;
    private Map<Long, String> recipeIdToNameMap;

    public MealAdapter(ArrayList<Meal> meals, Map<Long, String> recipeIdToNameMap) {
        this.meals = meals;
        this.recipeIdToNameMap = recipeIdToNameMap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        String breakfast = recipeIdToNameMap.get(meal.getBreakfastId());
        String lunch = recipeIdToNameMap.get(meal.getLunchId());
        String dinner = recipeIdToNameMap.get(meal.getDinnerId());

        holder.breakfastTextView.setText("Breakfast: " + breakfast);
        holder.lunchTextView.setText("Lunch: " + lunch);
        holder.dinnerTextView.setText("Dinner: " + dinner);

        // Set an OnClickListener for the breakfast TextView
        holder.breakfastTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecipeDetailActivity(v, meal.getBreakfastId());
            }
        });

        // Set an OnClickListener for the lunch TextView
        holder.lunchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecipeDetailActivity(v, meal.getLunchId());
            }
        });

        // Set an OnClickListener for the dinner TextView
        holder.dinnerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecipeDetailActivity(v, meal.getDinnerId());
            }
        });
    }

    // Method to start RecipeDetailActivity
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
