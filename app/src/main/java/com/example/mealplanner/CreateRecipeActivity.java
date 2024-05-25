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

public class CreateRecipeActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText ingredientsEditText;
    private EditText instructionsEditText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_recipe);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Recipe");
        }

        // Initialize the EditText fields
        nameEditText = findViewById(R.id.edittext_name);
        ingredientsEditText = findViewById(R.id.edittext_ingredients);
        instructionsEditText = findViewById(R.id.edittext_instructions);

        // Initialize the database helper
        dbHelper = new DbHelper(this);

        // Set up the save button
        Button saveButton = findViewById(R.id.save_recipe);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text entered by the user
                String name = nameEditText.getText().toString();
                String ingredients = ingredientsEditText.getText().toString();
                String instructions = instructionsEditText.getText().toString();

                // Create a new Recipe object
                Recipe recipe = new Recipe(0, name, ingredients, instructions);

                // Insert the Recipe object into the database
                dbHelper.insertRecipe(recipe);

                // Show a toast message
                Toast.makeText(CreateRecipeActivity.this, "Recipe added!", Toast.LENGTH_SHORT).show();

                // Clear the EditText fields
                nameEditText.setText("");
                ingredientsEditText.setText("");
                instructionsEditText.setText("");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

