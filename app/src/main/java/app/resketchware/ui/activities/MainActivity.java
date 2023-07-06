package app.resketchware.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;

import app.resketchware.R;
import app.resketchware.ui.fragments.ProjectsFragment;
import app.resketchware.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;

    private BottomNavigationView bottomNav;
    private Toolbar toolbar;

    private final Fragment projectsFragment = new ProjectsFragment();
    private final Fragment settingsFragment = new SettingsFragment();

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.menu_projects) {
                        switchToProjectsScreen();
                        return true;
                    } else if (id == R.id.menu_settings) {
                        switchToSettingsScreen();
                        return true;
                    } else {
                        return false;
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switchToProjectsScreen();

        bottomNav.setOnItemSelectedListener(onItemSelectedListener);

        if (!hasStoragePermissions()) {
            showPermissionDialog();
        }
    }

    private void switchToProjectsScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, projectsFragment)
                .commit();
    }

    private void switchToSettingsScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, settingsFragment)
                .commit();
    }

    private boolean hasStoragePermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }

    private void showPermissionDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.permission_ask_title)
                .setMessage(R.string.permission_ask_message)
                .setPositiveButton(android.R.string.ok, (d, which) -> {
                    d.dismiss();
                    requestStoragePermissions();
                })
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}