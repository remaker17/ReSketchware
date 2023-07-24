package app.resketchware.ui.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import app.resketchware.R;
import app.resketchware.ui.adapters.ProjectsAdapter;
import app.resketchware.ui.activities.DesignActivity;
import app.resketchware.ui.activities.MainActivity;
import app.resketchware.ui.dialogs.NewProjectDialog;
import app.resketchware.ui.interfaces.ScrollableToTop;
import app.resketchware.ui.models.Project;
import app.resketchware.ui.widgets.SearchBar;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.SketchwareUtil;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.ProjectSelectionCallback, ScrollableToTop {

    private ProjectsAdapter mAdapter;

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SearchBar mSearchBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        mFab = view.findViewById(R.id.fab);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSearchBar = view.findViewById(R.id.search_bar);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(mSearchBar);

        mAdapter = new ProjectsAdapter(this);

        int horizontalMargin = ContextUtil.getDimenFromResources(requireContext(), R.dimen.rsw_margin_medium);
        int verticalMargin = ContextUtil.getDimenFromResources(requireContext(), R.dimen.rsw_margin_xxsmall);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = horizontalMargin;
                outRect.right = horizontalMargin;
                outRect.top = verticalMargin;
                outRect.bottom = verticalMargin;
            }
        });

        refreshProjects(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (ContextUtil.hasStoragePermissions(requireContext())) {
                refreshProjects(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                ((MainActivity) requireActivity()).showPermissionDialog();
            }
        });

        mFab.setOnClickListener(v -> {
            NewProjectDialog.newInstance().show(getParentFragmentManager(), null);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
    }

    @Override
    public void projectClicked(Project project) {
        Bundle args = new Bundle();
        args.putSerializable("project", project);

        Intent intent = new Intent(requireContext(), DesignActivity.class);
        intent.putExtra("project", args);
        requireContext().startActivity(intent);
    }

    @Override
    public void scrollToTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void refreshProjects(boolean checkPermission) {
        if (checkPermission) {
            if (!ContextUtil.hasStoragePermissions(requireContext())) {
                return;
            }
        }

        new Thread(() -> {
            List<Project> projects = SketchwareUtil.getSketchwareProjects();
            Collections.sort(projects, (o1, o2) -> Integer.compare(
                    Integer.parseInt(o2.getId()),
                    Integer.parseInt(o1.getId())
            ));

            getActivity().runOnUiThread(() -> {
                mAdapter.changeProjectsDataset(projects);
                mSwipeRefreshLayout.setRefreshing(false);
            });
        }).start();
    }
}