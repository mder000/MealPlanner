package com.example.mealplanner.ui.recipes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealplanner.Recipe;
import com.example.mealplanner.backend.DbHelper;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;

    public RecipesViewModel(Application application){
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is recipes fragment");
    }

    public LiveData<String> getText() { return mText; }
}
