package com.example.mealplanner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealplanner.backend.DbHelper;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Recipe");
        }

        // Get the ID of the recipe from the Intent
        long recipeId = getIntent().getLongExtra("RECIPE_ID", -1);

        // Initialize the database helper
        DbHelper dbHelper = new DbHelper(this);

        // Get the recipe from the database
        Recipe recipe = dbHelper.getRecipeById(recipeId);

        // Get the TextViews from the layout
        TextView nameTextView = findViewById(R.id.recipe_name);
        TextView ingredientsTextView = findViewById(R.id.recipe_ingredients);
        TextView instructionsTextView = findViewById(R.id.recipe_instructions);

        // Set the recipe details
        nameTextView.setText(recipe.getName());
        ingredientsTextView.setText(recipe.getIngredients());
        instructionsTextView.setText(recipe.getInstructions());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
