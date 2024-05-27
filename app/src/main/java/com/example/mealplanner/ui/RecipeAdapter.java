package com.example.mealplanner.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.Recipe;
import com.example.mealplanner.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;

// Adapter for views displaying the recipes
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<Recipe> recipes;

    // Constructor takes an ArrayList of Recipes objects
    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    // Creating the view with the recipe card
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view);
    }

    // Populating the views with the names of recipes
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.nameTextView.setText(recipe.getName());

        // Listener to open the recipe details activity by ID
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipeDetailActivity.class);

                intent.putExtra("RECIPE_ID", recipe.getId());

                v.getContext().startActivity(intent);
            }
        });
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

