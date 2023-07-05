package app.resketchware.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import app.resketchware.R;
import app.resketchware.ui.fragments.ProjectsFragment;
import app.resketchware.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private Toolbar toolbar;

    private final Fragment projectsFragment = ProjectsFragment.newInstance();
    private final Fragment settingsFragment = SettingsFragment.newInstance();
    private Fragment activeFragment = projectsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
            .add(R.id.container, settingsFragment)
            .hide(settingsFragment)
            .commit();

        getSupportFragmentManager().beginTransaction()
            .add(R.id.container, projectsFragment)
            .commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_projects) {
                    getSupportFragmentManager().beginTransaction()
                        .hide(activeFragment)
                        .show(projectsFragment)
                        .commit();
                    activeFragment = projectsFragment;
                    return true;
                } else if (id == R.id.menu_settings) {
                    getSupportFragmentManager().beginTransaction()
                        .hide(activeFragment)
                        .show(settingsFragment)
                        .commit();
                    activeFragment = settingsFragment;
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}