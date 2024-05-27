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

// Activity to create a new grocery list
public class CreateGroceryListActivty extends AppCompatActivity {
    private LinearLayout ingredientsLayout;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_grocery_list_activty);

        // Changing the text in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Grocery List");
        }

        ingredientsLayout = findViewById(R.id.ingredients_layout);

        dbHelper = new DbHelper(this);

        // Button to add a new ingredient to the list
        Button addIngredientButton = findViewById(R.id.add_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newIngredientEditText = new EditText(CreateGroceryListActivty.this);
                newIngredientEditText.setHint("Enter an ingredient");
                ingredientsLayout.addView(newIngredientEditText);
            }
        });

        // Button to save the grocery lit
        Button saveButton = findViewById(R.id.save_grocery_list);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saving all the ingredients to a list
                StringBuilder ingredients = new StringBuilder();
                for (int i = 0; i < ingredientsLayout.getChildCount(); i++) {
                    EditText ingredientEditText = (EditText) ingredientsLayout.getChildAt(i);
                    ingredients.append(ingredientEditText.getText().toString());
                    if (i < ingredientsLayout.getChildCount() - 1) {
                        ingredients.append("\n");
                    }
                }

                // Parsing the ingredients list to a string
                GroceryList groceryList = new GroceryList(0, ingredients.toString());

                dbHelper.insertGroceryList(groceryList);

                // Notification the the grocery list was added
                Toast.makeText(CreateGroceryListActivty.this, "Grocery list added!", Toast.LENGTH_SHORT).show();

                // Deleting the input fields for ingredients
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

