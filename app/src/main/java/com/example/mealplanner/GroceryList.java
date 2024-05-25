package com.example.mealplanner;

public class GroceryList {
    private long id;
    private String ingredient;

    public GroceryList(long id, String ingredient) {
        this.id = id;
        this.ingredient = ingredient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
