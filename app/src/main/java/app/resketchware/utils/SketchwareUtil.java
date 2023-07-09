package app.resketchware.utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SketchwareUtil {
    private static final String SKETCHWARE_PROJECTS_LIST_PATH = ".sketchware/mysc/list";
    private static final String TAG = "SketchwareUtil";

    public static List<HashMap<String, Object>> getSketchwareProjects() {
        List<HashMap<String, Object>> projects = new ArrayList<>();
        File projectsDirectory = new File(Environment.getExternalStorageDirectory(), SKETCHWARE_PROJECTS_LIST_PATH);
        File[] files = projectsDirectory.listFiles();
        if (files == null) {
            return projects;
        }

        for (File file : files) {
            try {
                HashMap<String, Object> project = getProjectData(file);

                if (project != null && valueOrEmpty(project.get("sc_id")).equals(file.getName())) {
                    projects.add(project);
                }
            } catch (Exception ignored) {
                // ignored
            }
        }
        return projects;
    }

    private static HashMap<String, Object> getProjectData(File file) throws Exception {
        File projectFile = new File(file, "project");
        if (!projectFile.exists()) {
            return null;
        }

        byte[] projectFileByteArray = FileUtil.readBytes(projectFile);
        String projectDataString = new String(SketchwareEncryptor.decrypt(projectFileByteArray));
        Log.i(TAG, "Decrypted data: " + projectDataString);

        return convertToSketchwareProject(projectDataString);
    }

    private static HashMap<String, Object> convertToSketchwareProject(String hash) {
        Gson gson = new Gson();
        TypeToken<HashMap<String, Object>> typeToken = new TypeToken<HashMap<String, Object>>() {};
        return gson.fromJson(hash, typeToken.getType());
    }

    public static String valueOrEmpty(Object value) {
        return value == null ? "" : value.toString();
    }
}