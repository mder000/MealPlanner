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

// Activity to crate a new recipe
public class CreateRecipeActivity extends AppCompatActivity {
    private EditText nameEditText;
    private LinearLayout ingredientsLayout;
    private EditText instructionsEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        // Changing the text in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Recipe");
        }

        nameEditText = findViewById(R.id.edittext_name);
        ingredientsLayout = findViewById(R.id.ingredients_layout);
        instructionsEditText = findViewById(R.id.edittext_instructions);

        dbHelper = new DbHelper(this);

        // Button to add a new ingredient to the list
        Button addIngredientButton = findViewById(R.id.add_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newIngredientEditText = new EditText(CreateRecipeActivity.this);
                newIngredientEditText.setHint("Enter an ingredient");
                ingredientsLayout.addView(newIngredientEditText);
            }
        });

        // Button to save the recipe
        Button saveButton = findViewById(R.id.save_recipe);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();

                // Saving all the ingredients to a list
                StringBuilder ingredients = new StringBuilder();
                for (int i = 0; i < ingredientsLayout.getChildCount(); i++) {
                    EditText ingredientEditText = (EditText) ingredientsLayout.getChildAt(i);
                    ingredients.append(ingredientEditText.getText().toString());
                    if (i < ingredientsLayout.getChildCount() - 1) {
                        ingredients.append("\n");
                    }
                }

                // Parsing the ingredient list ot string
                String instructions = instructionsEditText.getText().toString();

                Recipe recipe = new Recipe(0, name, ingredients.toString(), instructions);

                dbHelper.insertRecipe(recipe);

                // Notification on successful creation of the recipe
                Toast.makeText(CreateRecipeActivity.this, "Recipe added!", Toast.LENGTH_SHORT).show();

                // Resetting all the fields to initial values
                nameEditText.setText("");
                instructionsEditText.setText("");

                ingredientsLayout.removeAllViews();
            }
        });
    }
}

