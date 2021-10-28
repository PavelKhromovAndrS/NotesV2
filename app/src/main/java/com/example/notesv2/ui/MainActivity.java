package com.example.notesv2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notesv2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_all_notes) {

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_view, new ListNotesFragment())
                            .commit();

                    return true;
                }

                if (item.getItemId() == R.id.action_search) {
                    Toast.makeText(MainActivity.this, R.string.toast_search, Toast.LENGTH_SHORT).show();

                    return true;
                }

                if (item.getItemId() == R.id.action_settings) {
                    Toast.makeText(MainActivity.this, R.string.toast_settings, Toast.LENGTH_SHORT).show();

                    return true;
                }

                return false;
            }
        });
    }

}