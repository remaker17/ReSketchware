package app.resketchware.ui.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;

import app.resketchware.R;
import app.resketchware.ui.fragments.ProjectsFragment;
import app.resketchware.ui.fragments.SettingsFragment;
import app.resketchware.ui.interfaces.ScrollableToTop;
import app.resketchware.utils.ContextUtil;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1000;
    @IdRes private int currentNavId = R.id.menu_projects;

    private BottomNavigationView mBottomNav;

    private ProjectsFragment mProjectsFragment;
    private SettingsFragment mSettingsFragment;

    private NavigationBarView.OnItemSelectedListener mOnItemSelectedListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.menu_projects || id == R.id.menu_settings) {
                        onNavigationSelected(id);
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

        mBottomNav = findViewById(R.id.bottom_nav);

        if (savedInstanceState == null) {
            mProjectsFragment = new ProjectsFragment();
            mSettingsFragment = new SettingsFragment();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mProjectsFragment)
                    .add(R.id.container, mSettingsFragment).hide(mSettingsFragment)
                    .commit();
        }

        mBottomNav.setOnItemSelectedListener(mOnItemSelectedListener);

        if (!ContextUtil.hasStoragePermissions(this)) {
            showPermissionDialog();
        }
        if (ContextUtil.hasStoragePermissions(this)) {
            allFilesAccessCheck();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected_nav_id", currentNavId);
        getSupportFragmentManager().putFragment(outState, "projectsFragment", mProjectsFragment);
        getSupportFragmentManager().putFragment(outState, "settingsFragment", mSettingsFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null || mProjectsFragment != null) {
            return;
        }

        mProjectsFragment = (ProjectsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "projectsFragment");
        mSettingsFragment = (SettingsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "settingsFragment");

        currentNavId = savedInstanceState.getInt("selected_nav_id");
        mBottomNav.setSelectedItemId(currentNavId);

        Fragment currentFragment = getFragment(currentNavId);
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .hide(mProjectsFragment)
                .hide(mSettingsFragment)
                .show(currentFragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOnItemSelectedListener = null;
        mProjectsFragment = null;
        mSettingsFragment = null;
    }

    private Fragment getFragment(@IdRes int navId) {
        if (navId == R.id.menu_projects) {
            return mProjectsFragment;
        } else if (navId == R.id.menu_settings) {
            return mSettingsFragment;
        }
        throw new IllegalArgumentException();
    }

    private void onNavigationSelected(@IdRes int navId) {
        Fragment newFragment = getFragment(navId);
        if (navId == currentNavId) {
            if (newFragment instanceof ScrollableToTop newScrollableFragment) {
                newScrollableFragment.scrollToTop();
            }
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .hide(getFragment(currentNavId))
                .show(newFragment)
                .commit();
        currentNavId = navId;
    }

    private void requestStoragePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }

    private void requestAllFilesPermissions() {
        if (Environment.isExternalStorageManager()) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ignored) {
            // ignored
        }
    }

    public void showPermissionDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.permission_ask_title)
                .setMessage(R.string.permission_ask_message)
                .setPositiveButton(android.R.string.ok, (d, which) -> {
                    requestStoragePermissions();
                    d.dismiss();
                })
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void allFilesAccessCheck() {
        if (Build.VERSION.SDK_INT >= 30) {
            if (Environment.isExternalStorageManager()) {
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.permission_ask_title)
                    .setMessage(R.string.permission_all_files_ask_message)
                    .setPositiveButton(android.R.string.ok, (d, which) -> {
                        requestAllFilesPermissions();
                        d.dismiss();
                    })
                    .setCancelable(false)
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            allFilesAccessCheck();
        }
    }
}