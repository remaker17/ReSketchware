package app.resketchware.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import app.resketchware.App;
import app.resketchware.managers.BuiltInLibraryManager;
import app.resketchware.ui.models.BuiltInLibraryModel;
import app.resketchware.ui.models.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SketchwareUtil {
  private static final String TAG = "SketchwareUtil";
  private static File androidJar;

  private SketchwareUtil() {
  }

  public static File getBootstrapFile() {
    if (androidJar == null) {
      Context context = App.getContext();
      androidJar = new File(context.getCacheDir(), "rt.jar");

      if (!androidJar.exists()) {
        Decompress.unzipFromAssets(context, "rt.zip", androidJar.getParentFile().getAbsolutePath());
      }
    }
    return androidJar;
  }

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

  public static String getClasspath() {
    StringBuilder classpath = new StringBuilder();
    classpath.append(getBootstrapFile().getAbsolutePath());
    classpath.append(":").append(new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "core-lambda-stubs.jar").getAbsolutePath());

    for (BuiltInLibraryModel library : BuiltInLibraryManager.getLibraries()) {
      classpath.append(":").append(BuiltInLibraries.getLibraryClassesJarPathString(library.getName()));
    }
    return classpath.toString();
  }
}
