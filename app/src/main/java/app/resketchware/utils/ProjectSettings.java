package app.resketchware.utils;

import com.google.gson.reflect.TypeToken;

import app.resketchware.ui.models.ProjectSettingsModel;

import java.io.File;

public class ProjectSettings {

    private final String projectId;
    private final ProjectSettingsModel settings;

    public ProjectSettings(String projectId) {
        this.projectId = projectId;

        if (FileUtil.isExists(getPath())) {
            TypeToken<ProjectSettingsModel> typeToken = new TypeToken<ProjectSettingsModel>() {};
            settings = GsonHelper.fromJson(FileUtil.readFile(getPath()), typeToken.getType());
        } else {
            settings = new ProjectSettingsModel();
        }
    }

    public int getMinSdkVersion() {
        return settings.getMinSdk("21");
    }

    public int getTargetSdkVersion() {
        return settings.getTargetSdk("28");
    }

    public String getApplicationClass() {
        return settings.getApplicationClass(".SketchApplication");
    }

    public boolean isEnableBridgelessThemes() {
        return settings.isEnableBridgelessThemes();
    }

    private String getPath() {
        return SketchwarePath.DATA + File.separator + projectId + File.separator + "project_config";
    }
}