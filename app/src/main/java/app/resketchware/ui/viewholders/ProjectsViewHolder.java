package app.resketchware.ui.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.io.File;

import app.resketchware.R;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.SketchwarePath;
import app.resketchware.utils.SketchwareUtil;

public class ProjectsViewHolder extends ViewHolder {

    private final ImageView projectIcon;
    private final TextView projectId;
    private final TextView projectTitle;
    private final TextView projectPackageName;
    private final TextView projectVersion;

    private final Context context;

    public ProjectsViewHolder(View view) {
        super(view);

        context = view.getContext();

        projectIcon = view.findViewById(R.id.project_icon);
        projectId = view.findViewById(R.id.project_id);
        projectTitle = view.findViewById(R.id.project_title);
        projectPackageName = view.findViewById(R.id.project_package_name);
        projectVersion = view.findViewById(R.id.project_version);
    }

    public void bind(Project project) {
        projectId.setText(project.getId());
        projectTitle.setText(project.getApplicationName());
        projectPackageName.setText(project.getPackageName());
        projectVersion.setText(project.getCombinedVersion());

        if (project.hasCustomIcon()) {
            String iconFolder = SketchwarePath.RESOURCES_ICONS + File.separator + project.getId() + File.separator + "icon.png";
            Uri uri = getUriForFile(iconFolder);
            projectIcon.setImageURI(uri);
        } else {
            projectIcon.setImageResource(R.drawable.android_icon);
        }
    }

    private Uri getUriForFile(String filePath) {
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = context.getPackageName() + ".fileprovider";
            return FileProvider.getUriForFile(context, authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
