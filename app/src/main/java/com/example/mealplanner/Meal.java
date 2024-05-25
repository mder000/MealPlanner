package com.example.mealplanner;

public class Meal {
    private long id;
    private long breakfastId;
    private long lunchId;
    private long dinnerId;

    public Meal(long id, long breakfastId, long lunchId, long dinnerId) {
        this.id = id;
        this.breakfastId = breakfastId;
        this.lunchId = lunchId;
        this.dinnerId = dinnerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBreakfastId() {
        return breakfastId;
    }

    public void setBreakfastId(long breakfastId) {
        this.breakfastId = breakfastId;
    }

    public long getLunchId() {
        return lunchId;
    }

    public void setLunchId(long lunchId) {
        this.lunchId = lunchId;
    }

    public long getDinnerId() {
        return dinnerId;
    }

    public void setDinnerId(long dinnerId) {
        this.dinnerId = dinnerId;
    }
}