package app.resketchware.ui.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.io.File;
import java.util.HashMap;

import app.resketchware.R;
import app.resketchware.utils.SketchwareUtil;

public class ProjectsViewHolder extends ViewHolder {

    private static final String ICONS_PATH = ".sketchware" + File.separator + "resources" + File.separator + "icons";

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

    public void bind(HashMap<String, Object> project) {
        projectId.setText(SketchwareUtil.valueOrEmpty(project.get("sc_id")));
        projectTitle.setText(SketchwareUtil.valueOrEmpty(project.get("my_app_name")));
        projectPackageName.setText(SketchwareUtil.valueOrEmpty(project.get("my_sc_pkg_name")));
        projectVersion.setText(SketchwareUtil.valueOrEmpty(project.get("sc_ver_name")) + " (" + SketchwareUtil.valueOrEmpty(project.get("sc_ver_code")) + ")");

        boolean haveCustomIcon = (boolean) project.get("custom_icon");
        if (haveCustomIcon) {
            String iconFolder = Environment.getExternalStorageDirectory() + ICONS_PATH + File.separator + SketchwareUtil.valueOrEmpty(project.get("sc_id")) + File.separator + "icon.png";
            Uri uri = getUriForFile(iconFolder);
            projectIcon.setImageURI(uri);
            projectIcon.setImageTintList(null);
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