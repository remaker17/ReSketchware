package app.resketchware.utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import app.resketchware.ui.models.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SketchwareUtil {
    private static final String TAG = "SketchwareUtil";

    public static List<Project> getSketchwareProjects() {
        List<Project> projects = new ArrayList<>();
        File projectsDirectory = new File(SketchwarePath.MYSC_LIST);
        File[] files = projectsDirectory.listFiles();
        if (files == null) {
            return projects;
        }

        for (File file : files) {
            try {
                Project project = getProjectData(file);

                if (project != null && project.getId().equals(file.getName())) {
                    projects.add(project);
                }
            } catch (Exception ignored) {
                // ignored
            }
        }
        return projects;
    }

    private static Project getProjectData(File file) throws Exception {
        File projectFile = new File(file, "project");
        if (!projectFile.exists()) {
            return null;
        }

        byte[] projectFileByteArray = FileUtil.readBytes(projectFile);
        String projectDataString = new String(SketchwareEncryptor.decrypt(projectFileByteArray));
        Log.i(TAG, "Decrypted data: " + projectDataString);

        return convertToSketchwareProject(projectDataString);
    }

    private static Project convertToSketchwareProject(String hash) {
        TypeToken<Project> typeToken = new TypeToken<Project>() {};
        return GsonHelper.fromJson(hash, typeToken.getType());
    }
}