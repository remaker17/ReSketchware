package app.resketchware.ui.activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import app.resketchware.R;
import app.resketchware.ui.adapters.DesignPagerAdapter;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.SketchwareUtil;

import java.util.HashMap;

public class DesignActivity extends AppCompatActivity {

    private HashMap<String, Object> project;

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        if (!ContextUtil.hasStoragePermissions(this)) {
            showNoPermissionDialog();
            return;
        }

        tabLayout = findViewById(R.id.tab_layout);
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.pager);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args = getIntent().getBundleExtra("project");
            project = (HashMap<String, Object>) args.getSerializable("project");
        } else {
            project = (HashMap<String, Object>) savedInstanceState.getSerializable("project");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(SketchwareUtil.valueOrEmpty(project.get("my_app_name")));
        toolbar.setSubtitle(SketchwareUtil.valueOrEmpty(project.get("sc_id")));
        toolbar.setNavigationOnClickListener(ignored -> onBackPressed());

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new DesignPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("project", project);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_design, menu);
        return true;
    }

    private void showNoPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_no_title)
                .setMessage(R.string.permission_no_message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                })
                .show();
    }
}