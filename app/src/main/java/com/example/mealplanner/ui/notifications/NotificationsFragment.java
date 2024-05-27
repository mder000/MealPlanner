package com.example.mealplanner.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.GroceryList;
import com.example.mealplanner.backend.DbHelper;
import com.example.mealplanner.databinding.FragmentNotificationsBinding;
import com.example.mealplanner.ui.GroceryListAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    // Fragment to display all the grocery list
    // Created from a template, navigation menu template hence the notifications name
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Fetching all the grocery lists in the db
        DbHelper dbHelper = new DbHelper(getContext());
        ArrayList<GroceryList> groceryLists = dbHelper.getGroceryLists();

        RecyclerView recyclerView = binding.recyclerviewGroceryLists;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new GroceryListAdapter(groceryLists));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}