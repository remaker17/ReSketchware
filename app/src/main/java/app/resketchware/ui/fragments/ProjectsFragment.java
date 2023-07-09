package app.resketchware.ui.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import app.resketchware.R;
import app.resketchware.ui.adapters.ProjectsAdapter;
import app.resketchware.ui.activities.MainActivity;
import app.resketchware.ui.dialogs.NewProjectDialog;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.SketchwareUtil;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.ProjectSelectionCallback {

    private ProjectsAdapter adapter;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        adapter = new ProjectsAdapter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int horizontalMargin = ContextUtil.getDimenFromResources(requireContext(), R.dimen.rsw_margin_medium);
        int verticalMargin = ContextUtil.getDimenFromResources(requireContext(), R.dimen.rsw_margin_xxsmall);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = horizontalMargin;
                outRect.right = horizontalMargin;
                outRect.top = verticalMargin;
                outRect.bottom = verticalMargin;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (ContextUtil.hasStoragePermissions(requireContext())) {
                refreshProjects(false);
            } else {
                swipeRefreshLayout.setRefreshing(false);
                ((MainActivity) requireActivity()).showPermissionDialog();
            }
        });

        refreshProjects(true);

        fab.setOnClickListener(v -> {
            NewProjectDialog.newInstance().show(getParentFragmentManager(), null);
        });
    }

    @Override
    public void projectClicked(HashMap<String, Object> project) {}

    private void refreshProjects(boolean checkPermission) {
        if (checkPermission) {
            if (!ContextUtil.hasStoragePermissions(requireContext())) {
                return;
            }
        }

        adapter.changeProjectsDataset(SketchwareUtil.getSketchwareProjects());
        swipeRefreshLayout.setRefreshing(false);
    }
}