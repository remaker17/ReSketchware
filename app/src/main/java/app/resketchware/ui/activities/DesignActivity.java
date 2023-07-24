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
import app.resketchware.ui.viewmodels.DesignViewModel;
import app.resketchware.services.CompilerService;
import app.resketchware.services.CompilerServiceConnection;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.SketchwareUtil;

public class DesignActivity extends AppCompatActivity {

    private Project mProject;

    private CompilerDialog mCompilerDialog;
    private CompilerViewModel mCompilerViewModel;
    private CompilerServiceConnection mServiceConnection;
    private DesignViewModel mDesignViewModel;

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager2 mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!ContextUtil.hasStoragePermissions(this)) {
            showNoPermissionDialog();
            return;
        }

        setContentView(R.layout.activity_design);

        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.pager);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args = getIntent().getBundleExtra("project");
            mProject = (Project) args.getSerializable("project");
        } else {
            mProject = (Project) savedInstanceState.getSerializable("project");
        }

        mCompilerViewModel = new ViewModelProvider(this).get(CompilerViewModel.class);
        mDesignViewModel = new ViewModelProvider(this).get(DesignViewModel.class);

        mCompilerViewModel.setProject(mProject);
        mCompilerDialog = CompilerDialog.newInstance();
        mServiceConnection = new CompilerServiceConnection(mCompilerViewModel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mProject.getApplicationName());
        mToolbar.setSubtitle(mProject.getId());
        mToolbar.setNavigationOnClickListener(ignored -> onBackPressed());

        mViewPager.setAdapter(new DesignPagerAdapter(this));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mDesignViewModel.setTabPosition(position);
            }
        });

        int[] tabTitles = {R.string.view, R.string.event, R.string.component};
        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            tab.setText(tabTitles[position]);
        }).attach();

        mCompilerViewModel.isCompiling().observe(this, isCompiling -> {
            if (isCompiling) {
                if (!mCompilerDialog.isShowing()) {
                    mCompilerDialog.show(getSupportFragmentManager(), null);
                }
            } else {
                if (mCompilerDialog.isShowing()) {
                    mCompilerDialog.dismiss();
                }
            }
        });

        mCompilerViewModel.getMessage().observe(this, message -> {
            if (message != null) {
                mCompilerDialog.getMessage().append(message + "\n");
            }
        });

        mDesignViewModel.getTabPosition().observe(this, position -> {
            if (mViewPager.getCurrentItem() != position) {
                mViewPager.setCurrentItem(position);
            }
        });

        disableOverScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProject = null;
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
        if (mDesignViewModel.getTabPosition().getValue() > 0) {
            mDesignViewModel.setTabPosition(mDesignViewModel.getTabPosition().getValue() - 1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("project", mProject);
    }

    private void disableOverScroll() {
        View view = mViewPager.getChildAt(0);
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
        if (mServiceConnection.isCompiling() || Boolean.TRUE.equals(mCompilerViewModel.isCompiling().getValue())) {
            return;
        }

        mCompilerViewModel.setCompiling(true);

        startService(new Intent(this, CompilerService.class));
        bindService(new Intent(this, CompilerService.class), mServiceConnection, Context.BIND_IMPORTANT);
    }
}