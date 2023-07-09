package app.resketchware.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import app.resketchware.R;

import java.util.HashMap;

public class ProjectsViewHolder extends ViewHolder {

    private final TextView projectId;
    private final TextView projectTitle;
    private final TextView projectPackageName;

    public ProjectsViewHolder(View view) {
        super(view);

        projectId = view.findViewById(R.id.project_id);
        projectTitle = view.findViewById(R.id.project_title);
        projectPackageName = view.findViewById(R.id.project_package_name);
    }

    public void bind(HashMap<String, Object> project) {
        projectId.setText(valueOrEmpty(project.get("sc_id")));
        projectTitle.setText(valueOrEmpty(project.get("my_ws_name")));
        projectPackageName.setText(valueOrEmpty(project.get("my_app_name")));
    }

    /**
     * TODO: Move the function to a separate class.
     */
    private String valueOrEmpty(Object value) {
        return value == null ? "" : value.toString();
    }
}