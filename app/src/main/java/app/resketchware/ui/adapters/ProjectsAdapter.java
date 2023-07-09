package app.resketchware.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.resketchware.R;
import app.resketchware.ui.animators.ProjectItemAnimator;
import app.resketchware.ui.viewholders.ProjectsViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * TODO: Change project model [HashMap<...>] to Project.java
 */
public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsViewHolder> {

    private List<HashMap<String, Object>> projects;
    private ProjectSelectionCallback listener;

    public interface ProjectSelectionCallback {
        void projectClicked(HashMap<String, Object> project);
    }

    public ProjectsAdapter(ProjectSelectionCallback listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_project_item, parent, false);
        return new ProjectsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsViewHolder holder, int position) {
        HashMap<String, Object> project = projects.get(position);
        holder.bind(project);
        holder.itemView.setOnClickListener(v -> listener.projectClicked(project));
    }

    @Override
    public int getItemCount() {
        return projects == null ? 0 : projects.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new ProjectItemAnimator());
    }

    public void changeProjectsDataset(List<HashMap<String, Object>> newProjects) {
        this.projects = newProjects;
        notifyDataSetChanged();
    }
}