package com.example.foody_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foody_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Retrieve user_id from Intent
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("userId");
            Log.d("MainActivity", "Received userId: " + userId);
        }
        fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set listener for item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.menu_chat:
                    selectedFragment = new ChatFragment();
                    Bundle args = new Bundle();
                    args.putString("user_id", userId);
                    selectedFragment.setArguments(args);
                    break;
                case R.id.menu_cart:

                    break;
                case R.id.menu_profile:
                    selectedFragment = new UserInformation();
                    break;
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
                return true;
            }

            return false;
        });
        // Set default fragment on first launch
        if (savedInstanceState == null) {
            HomeFragment defaultFragment = new HomeFragment();
            replaceFragment(defaultFragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}