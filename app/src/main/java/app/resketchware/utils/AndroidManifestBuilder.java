package app.resketchware.utils;

import android.content.Intent;

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

    private String packageName;
    private boolean targetsSdkVersion31OrHigher = false;

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

        for (ProjectFileBean bean : projectFileBeans) {
            if (!bean.fileName.contains("_fragment")) {
                XmlBuilder activityTag = new XmlBuilder("activity");

                String javaName = bean.getJavaName();
                activityTag.addAttribute("android", "name", "." + javaName.substring(0, javaName.indexOf(".java")));

                if (!AndroidManifestInjector.getActivityAttrs(activityTag, project.getId(), bean.getJavaName())) {
                    activityTag.addAttribute("android", "configChanges", "orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout");
                    activityTag.addAttribute("android", "hardwareAccelerated", "true");
                    activityTag.addAttribute("android", "supportsPictureInPicture", "true");
                }
                /* if (!AndroidManifestInjector.isActivityThemeUsed(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    if (c.g) {
                        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN)) {
                            activityTag.addAttribute("android", "theme", "@style/AppTheme.FullScreen");
                        }
                    } else if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN)) {
                        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                            activityTag.addAttribute("android", "theme", "@style/NoStatusBar");
                        } else {
                            activityTag.addAttribute("android", "theme", "@style/FullScreen");
                        }
                    } else if (!projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                        activityTag.addAttribute("android", "theme", "@style/NoActionBar");
                    }
                } */
                if (!AndroidManifestInjector.isActivityOrientationUsed(activityTag, project.getId(), bean.getJavaName())) {
                    int orientation = bean.orientation;
                    if (orientation == ProjectFileBean.ORIENTATION_PORTRAIT) {
                        activityTag.addAttribute("android", "screenOrientation", "portrait");
                    } else if (orientation == ProjectFileBean.ORIENTATION_LANDSCAPE) {
                        activityTag.addAttribute("android", "screenOrientation", "landscape");
                    }
                }
                /* if (!AndroidManifestInjector.isActivityKeyboardUsed(activityTag, project.getId(), bean.getJavaName())) {
                    String keyboardSetting = vq.a(projectFileBean.keyboardSetting);
                    if (keyboardSetting.length() > 0) {
                        activityTag.addAttribute("android", "windowSoftInputMode", keyboardSetting);
                    }
                } */
                if (bean.fileName.equals(AndroidManifestInjector.getLauncherActivity(project.getId()))) {
                    XmlBuilder intentFilterTag = new XmlBuilder("intent-filter");

                    XmlBuilder actionTag = new XmlBuilder("action");
                    actionTag.addAttribute("android", "name", Intent.ACTION_MAIN);
                    intentFilterTag.addChildElement(actionTag);

                    XmlBuilder categoryTag = new XmlBuilder("category");
                    categoryTag.addAttribute("android", "name", Intent.CATEGORY_LAUNCHER);
                    intentFilterTag.addChildElement(categoryTag);

                    if (targetsSdkVersion31OrHigher && !AndroidManifestInjector.isActivityExportedUsed(project.getId(), javaName)) {
                        activityTag.addAttribute("android", "exported", "true");
                    }
                    activityTag.addChildElement(intentFilterTag);
                } /* else if (c.isDynamicLinkUsed) {
                    if (targetsSdkVersion31OrHigher && !AndroidManifestInjector.isActivityExportedUsed(c.sc_id, javaName)) {
                        activityTag.addAttribute("android", "exported", "false");
                    }
                    writeDLIntentFilter(activityTag);
                } */
                applicationTag.addChildElement(activityTag);
            }
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