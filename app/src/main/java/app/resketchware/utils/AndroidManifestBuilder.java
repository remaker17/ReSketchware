package app.resketchware.utils;

import app.resketchware.beans.ProjectFileBean;
import app.resketchware.beans.SrcCodeBean;
import app.resketchware.ui.models.Project;

import java.io.File;
import java.util.ArrayList;

public class AndroidManifestBuilder {
  private final Project project;
  private final ProjectSettings projectSettings;
  private final ArrayList<ProjectFileBean> projectFileBeans;
  private final XmlBuilder xmlBuilder = new XmlBuilder("manifest");
  private final String packageName;
  private final boolean targetsSdkVersion31OrHigher = false;

  public AndroidManifestBuilder(Project project, ArrayList<ProjectFileBean> projectFileBeans) {
    this.project = project;
    this.projectFileBeans = projectFileBeans;

    projectSettings = new ProjectSettings(project.getId());
    targetsSdkVersion31OrHigher = projectSettings.getTargetSdkVersion() >= 31;
    packageName = project.getPackageName();
  }

  public String build() {
    xmlBuilder.addAttribute("xmlns", "android", "http://schemas.android.com/apk/res/android");
    xmlBuilder.addAttribute("", "package", project.getPackageName());

    XmlBuilder applicationTag = new XmlBuilder("application");
    applicationTag.addAttribute("android", "allowBackup", "true");
    applicationTag.addAttribute("android", "icon", "@drawable/app_icon");
    applicationTag.addAttribute("android", "label", "@string/app_name");

    String applicationClassName = projectSettings.getApplicationClass();
    if (!applicationClassName.equals(".SketchApplication") ||
        new File(project.getDataJavaDirectory().getAbsolutePath(), "SketchApplication.java").exists()) {
      applicationTag.addAttribute("android", "name", applicationClassName);
    }

    {
      XmlBuilder activityTag = new XmlBuilder("activity");
      activityTag.addAttribute("android", "name", ".DebugActivity");
      activityTag.addAttribute("android", "screenOrientation", "portrait");
      applicationTag.addChildElement(activityTag);
    }

    xmlBuilder.addChildElement(applicationTag);
    return AndroidManifestInjector.mHolder(xmlBuilder.toCode()).replaceAll("\\$\\{applicationId\\}", packageName);
  }
}
