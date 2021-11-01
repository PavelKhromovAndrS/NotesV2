package com.example.notesv2.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Toast;

import com.example.notesv2.R;
import com.example.notesv2.domain.NotesAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_FRAGMENT = "MAIN_FRAGMENT";

    public ListNotesFragment listNotesFragment = new ListNotesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, listNotesFragment, MAIN_FRAGMENT)
                .commit();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> {
            if (listNotesFragment != null && listNotesFragment.isVisible()) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Выход")
                        .setMessage("Вы действительно хотите выйти?")
                        .setPositiveButton("Да", (dialog, which) -> finish())
                        .setNegativeButton("Нет", (dialog, which) -> dialog.cancel())
                        .setCancelable(false)
                        .show();
            } else {
                onBackPressed();
            }

        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_all_notes);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_all_notes) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, listNotesFragment)
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
        });
    }

}