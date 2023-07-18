package app.resketchware.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import app.resketchware.R;
import app.resketchware.ui.adapters.DesignPagerAdapter;
import app.resketchware.ui.dialogs.CompilerDialog;
import app.resketchware.ui.models.Project;
import app.resketchware.ui.viewmodels.CompilerViewModel;
import app.resketchware.services.CompilerService;
import app.resketchware.services.CompilerServiceConnection;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.SketchwareUtil;

public class DesignActivity extends AppCompatActivity {

    private Project project;
    private MutableLiveData<Integer> currentTab = new MutableLiveData<>(0);

    private CompilerDialog compilerDialog;
    private CompilerViewModel compilerViewModel;
    private CompilerServiceConnection serviceConnection;

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!ContextUtil.hasStoragePermissions(this)) {
            showNoPermissionDialog();
            return;
        }

        setContentView(R.layout.activity_design);

        tabLayout = findViewById(R.id.tab_layout);
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.pager);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args = getIntent().getBundleExtra("project");
            project = (Project) args.getSerializable("project");
        } else {
            project = (Project) savedInstanceState.getSerializable("project");
        }

        compilerViewModel = new ViewModelProvider(this).get(CompilerViewModel.class);
        compilerViewModel.setProject(project);
        compilerDialog = CompilerDialog.newInstance(null);
        serviceConnection = new CompilerServiceConnection(compilerViewModel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(project.getApplicationName());
        toolbar.setSubtitle(project.getId());
        toolbar.setNavigationOnClickListener(ignored -> onBackPressed());

        viewPager.setAdapter(new DesignPagerAdapter(this));
        viewPager.setOffscreenPageLimit(3);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentTab.setValue(position);
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            int[] tabTitles = {R.string.view, R.string.event, R.string.component};
            tab.setText(tabTitles[position]);
        }).attach();

        compilerViewModel.isCompiling().observe(this, isCompiling -> {
            if (isCompiling) {
                if (compilerDialog.isShowing()) {
                    return;
                }
                compilerDialog.show(getSupportFragmentManager(), null);
            } else {
                if (compilerDialog.isShowing()) {
                    compilerDialog.dismiss();
                }
            }
        });

        compilerViewModel.getMessage().observe(this, message -> {
            compilerDialog.getMessage().append(message + "\n");
        });

        currentTab.observe(this, currentItem -> {
            viewPager.setCurrentItem(currentItem);
        });

        disableOverScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compilerDialog = null;
        compilerViewModel = null;
        project = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_design, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.run_project) {
             compile();
             return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentTab.getValue() > 0) {
            currentTab.setValue(currentTab.getValue() - 1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("project", project);
    }

    private void disableOverScroll() {
        View view = viewPager.getChildAt(0);
        if (view instanceof RecyclerView rv) {
            rv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
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

    private void compile() {
        if (serviceConnection.isCompiling()) {
            new AlertDialog.Builder(this)
                .setTitle("Compiler")
                .setMessage("The project is already compiling")
                .setPositiveButton(android.R.string.ok, null)
                .show();
            return;
        }

        compilerViewModel.setCompiling(true);

        startService(new Intent(this, CompilerService.class));
        bindService(new Intent(this, CompilerService.class), serviceConnection, Context.BIND_IMPORTANT);
    }
}