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

public class CreateGroceryListActivty extends AppCompatActivity {
    private LinearLayout ingredientsLayout;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_grocery_list_activty);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Grocery List");
        }

        // Initialize the LinearLayout that will contain the EditText fields
        ingredientsLayout = findViewById(R.id.ingredients_layout);

        // Initialize the database helper
        dbHelper = new DbHelper(this);

        // Set up the add ingredient button
        Button addIngredientButton = findViewById(R.id.add_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a new EditText field to the LinearLayout
                EditText newIngredientEditText = new EditText(CreateGroceryListActivty.this);
                newIngredientEditText.setHint("Enter an ingredient");
                ingredientsLayout.addView(newIngredientEditText);
            }
        });

        // Set up the save button
        Button saveButton = findViewById(R.id.save_grocery_list);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Create a new GroceryList object
                GroceryList groceryList = new GroceryList(0, ingredients.toString());

                // Insert the GroceryList object into the database
                dbHelper.insertGroceryList(groceryList);

                // Show a toast message
                Toast.makeText(CreateGroceryListActivty.this, "Grocery list added!", Toast.LENGTH_SHORT).show();

                // Remove all the EditText fields
                ingredientsLayout.removeAllViews();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

