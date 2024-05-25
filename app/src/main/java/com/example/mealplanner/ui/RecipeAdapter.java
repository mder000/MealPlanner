package com.example.mealplanner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.nameTextView.setText("Name: " + recipe.getName());
        holder.ingredientsTextView.setText("Ingredients:\n" + recipe.getIngredients());
        holder.instructionsTextView.setText("Instructions:\n" + recipe.getInstructions());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView ingredientsTextView;
        public TextView instructionsTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.recipe_name);
            ingredientsTextView = view.findViewById(R.id.recipe_ingredients);
            instructionsTextView = view.findViewById(R.id.recipe_instructions);
        }
    }
}
