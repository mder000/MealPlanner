package com.example.mealplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealplanner.backend.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateMealActivity extends AppCompatActivity {

    private Spinner breakfastSpinner;
    private Spinner lunchSpinner;
    private Spinner dinnerSpinner;
    private DbHelper dbHelper;
    private Map<String, Long> recipeNameToIdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_meal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Meal Plan");
        }

        // Initialize the database helper
        dbHelper = new DbHelper(this);

        // Get the recipes from the database
        ArrayList<Recipe> recipes = dbHelper.getRecipes();

        // Create a map of recipe names to their IDs
        recipeNameToIdMap = new HashMap<>();
        ArrayList<String> recipeNames = new ArrayList<>();
        recipeNames.add("Select a recipe"); // Add the initial dummy item
        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getName());
            recipeNameToIdMap.put(recipe.getName(), recipe.getId());
        }

        // Create an ArrayAdapter for the recipe names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Initialize the Spinners and set their adapters
        breakfastSpinner = findViewById(R.id.breakfast_spinner);
        lunchSpinner = findViewById(R.id.lunch_spinner);
        dinnerSpinner = findViewById(R.id.dinner_spinner);
        breakfastSpinner.setAdapter(adapter);
        lunchSpinner.setAdapter(adapter);
        dinnerSpinner.setAdapter(adapter);

        // Set up the save button
        Button saveButton = findViewById(R.id.save_meal_plan_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected recipes
                String breakfast = (String) breakfastSpinner.getSelectedItem();
                String lunch = (String) lunchSpinner.getSelectedItem();
                String dinner = (String) dinnerSpinner.getSelectedItem();

                // Check if a valid recipe is selected
                if (breakfast.equals("Select a recipe") || lunch.equals("Select a recipe") || dinner.equals("Select a recipe")) {
                    Toast.makeText(CreateMealActivity.this, "Please select a recipe for each meal.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the IDs of the selected recipes
                long breakfastId = recipeNameToIdMap.get(breakfast);
                long lunchId = recipeNameToIdMap.get(lunch);
                long dinnerId = recipeNameToIdMap.get(dinner);

                // Create a new Meal object
                Meal meal = new Meal(0, breakfastId, lunchId, dinnerId);

                // Insert the Meal object into the database
                dbHelper.insertMeal(meal);

                // Show a toast message
                Toast.makeText(CreateMealActivity.this, "Meal plan added!", Toast.LENGTH_SHORT).show();

                // Reset the Spinner selection
                breakfastSpinner.setSelection(0);
                lunchSpinner.setSelection(0);
                dinnerSpinner.setSelection(0);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

