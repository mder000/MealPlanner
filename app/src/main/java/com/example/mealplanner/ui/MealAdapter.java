package com.example.mealplanner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.Meal;
import com.example.mealplanner.R;

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
        String text = "Breakfast: " + breakfast + "\nLunch: " + lunch + "\nDinner: " + dinner;
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_meal);
        }
    }
}
