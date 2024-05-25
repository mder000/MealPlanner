package com.example.mealplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealplanner.backend.DbHelper;

public class CreateGroceryListActivty extends AppCompatActivity {
    private EditText ingredientsEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_grocery_list_activty);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Grocery List");
        }

        // Initialize the EditText field
        ingredientsEditText = findViewById(R.id.edittext_ingredients);

        // Initialize the database helper
        dbHelper = new DbHelper(this);

        // Set up the save button
        Button saveButton = findViewById(R.id.save_grocery_list);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text entered by the user
                String ingredients = ingredientsEditText.getText().toString();

                // Create a new GroceryList object
                GroceryList groceryList = new GroceryList(0, ingredients);

                // Insert the GroceryList object into the database
                dbHelper.insertGroceryList(groceryList);

                // Show a toast message
                Toast.makeText(CreateGroceryListActivty.this, "Grocery list added!", Toast.LENGTH_SHORT).show();

                // Clear the EditText field
                ingredientsEditText.setText("");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
