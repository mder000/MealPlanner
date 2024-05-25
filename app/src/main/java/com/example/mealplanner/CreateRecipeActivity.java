package com.example.mealplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealplanner.backend.DbHelper;

public class CreateRecipeActivity extends AppCompatActivity {
    private EditText nameEditText;
    private LinearLayout ingredientsLayout;
    private EditText instructionsEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Recipe");
        }

        // Initialize the EditText fields
        nameEditText = findViewById(R.id.edittext_name);
        ingredientsLayout = findViewById(R.id.ingredients_layout);
        instructionsEditText = findViewById(R.id.edittext_instructions);

        // Initialize the database helper
        dbHelper = new DbHelper(this);

        // Set up the add ingredient button
        Button addIngredientButton = findViewById(R.id.add_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a new EditText field to the ingredients LinearLayout
                EditText newIngredientEditText = new EditText(CreateRecipeActivity.this);
                newIngredientEditText.setHint("Enter an ingredient");
                ingredientsLayout.addView(newIngredientEditText);
            }
        });

        // Set up the save button
        Button saveButton = findViewById(R.id.save_recipe);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text entered by the user
                String name = nameEditText.getText().toString();

                // Concatenate all the ingredients into a single string
                StringBuilder ingredients = new StringBuilder();
                for (int i = 0; i < ingredientsLayout.getChildCount(); i++) {
                    EditText ingredientEditText = (EditText) ingredientsLayout.getChildAt(i);
                    ingredients.append(ingredientEditText.getText().toString());
                    if (i < ingredientsLayout.getChildCount() - 1) {
                        // Add a line break between each ingredient
                        ingredients.append("\n");
                    }
                }

                // Get the instructions entered by the user
                String instructions = instructionsEditText.getText().toString();

                // Create a new Recipe object
                Recipe recipe = new Recipe(0, name, ingredients.toString(), instructions);

                // Insert the Recipe object into the database
                dbHelper.insertRecipe(recipe);

                // Show a toast message
                Toast.makeText(CreateRecipeActivity.this, "Recipe added!", Toast.LENGTH_SHORT).show();

                // Clear the EditText fields
                nameEditText.setText("");
                instructionsEditText.setText("");

                // Remove all the EditText fields for ingredients
                ingredientsLayout.removeAllViews();
            }
        });
    }
}

