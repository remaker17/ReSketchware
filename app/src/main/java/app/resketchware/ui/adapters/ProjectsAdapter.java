package app.resketchware.ui.adapters;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import app.resketchware.R;
import app.resketchware.ui.viewholders.ProjectsViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * TODO: Change project model [HashMap<...>] to Project.java
 */
public class ProjectsAdapter extends Adapter<ProjectsViewHolder> {

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

        // animateView(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return projects == null ? 0 : projects.size();
    }

    public void changeProjectsDataset(List<HashMap<String, Object>> newProjects) {
        this.projects = newProjects;
        notifyDataSetChanged();
    }

    private void animateView(View itemView) {
        float startScale = 1.15f;
        float endScale = 1f;
        int animationDuration = 300;

        ValueAnimator animator = ValueAnimator.ofFloat(startScale, endScale);
        animator.setDuration(animationDuration);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            itemView.setScaleY(value);
            itemView.setScaleX(value);
        });
        animator.start();
    }
}