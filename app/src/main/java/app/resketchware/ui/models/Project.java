package app.resketchware.ui.models;

import com.google.gson.annotations.SerializedName;

import app.resketchware.beans.SrcCodeBean;
import app.resketchware.utils.AndroidManifestBuilder;
import app.resketchware.utils.Decompress;
import app.resketchware.utils.FileUtil;
import app.resketchware.utils.ProjectSettings;
import app.resketchware.utils.SketchwarePath;
import app.resketchware.utils.XmlBuilderHelper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public final class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("sc_id") private String id;
    @SerializedName("my_app_name") private String applicationName;
    @SerializedName("my_sc_pkg_name") private String packageName;
    @SerializedName("my_ws_name") private String projectName;
    @SerializedName("sc_ver_name") private String versionName;
    @SerializedName("sc_ver_code") private String versionCode;
    @SerializedName("custom_icon") private boolean hasCustomIcon;

    public Project(String id, String applicationName, String packageName, String projectName, String versionName, String versionCode) {
        this(id, applicationName, packageName, projectName, versionName, versionCode, false);
    }

    public Project(String id, String applicationName, String packageName, String projectName, String versionName, String versionCode, boolean hasCustomIcon) {
        this.id = id != null ? id : "";
        this.applicationName = applicationName != null ? applicationName : "";
        this.packageName = packageName != null ? packageName : "";
        this.projectName = projectName != null ? projectName : "";
        this.versionName = versionName != null ? versionName : "";
        this.versionCode = versionCode != null ? versionCode : "";
        this.hasCustomIcon = hasCustomIcon;
    }

    public String getId() {
        return id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getCombinedVersion() {
        return versionName + " (" + versionCode + ")";
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public boolean hasCustomIcon() {
        return hasCustomIcon;
    }

    public void generateProjectFiles() {
        ArrayList<SrcCodeBean> srcCodeBeans = getSourceFiles();

        for (SrcCodeBean bean : srcCodeBeans) {
            saveFile(bean.getSrcFilename(), bean.getSource());
        }
    }

    public ArrayList<SrcCodeBean> getSourceFiles() {
        AndroidManifestBuilder manifestBuilder = new AndroidManifestBuilder(this, null);
        ProjectSettings projectSettings = new ProjectSettings(id);

        ArrayList<SrcCodeBean> srcCodeBeans = new ArrayList<>();
        srcCodeBeans.add(new SrcCodeBean("AndroidManifest.xml", manifestBuilder.build()));

        XmlBuilderHelper colorsFileBuilder = new XmlBuilderHelper();
        colorsFileBuilder.addColor("colorPrimary", "#2196f3");
        colorsFileBuilder.addColor("colorPrimaryDark", "#1976d2");
        colorsFileBuilder.addColor("colorAccent", "#f44336");
        colorsFileBuilder.addColor("colorControlHighlight", "#33ffffff");
        colorsFileBuilder.addColor("colorControlNormal", "#9e9e9e");
        srcCodeBeans.add(new SrcCodeBean("colors.xml", colorsFileBuilder.toCode()));

        XmlBuilderHelper stylesFileBuilder = new XmlBuilderHelper();
        stylesFileBuilder.addStyle("AppTheme", "Theme.MaterialComponents.Light.NoActionBar" + (projectSettings.isEnableBridgelessThemes() ? "" : ".Bridge"));
        stylesFileBuilder.addItemToStyle("AppTheme", "colorPrimary", "@color/colorPrimary");
        stylesFileBuilder.addItemToStyle("AppTheme", "colorPrimaryDark", "@color/colorPrimaryDark");
        stylesFileBuilder.addItemToStyle("AppTheme", "colorAccent", "@color/colorAccent");
        stylesFileBuilder.addItemToStyle("AppTheme", "colorControlHighlight", "@color/colorControlHighlight");
        stylesFileBuilder.addItemToStyle("AppTheme", "colorControlNormal", "@color/colorControlNormal");
        stylesFileBuilder.addStyle("AppTheme.FullScreen", "AppTheme");
        stylesFileBuilder.addItemToStyle("AppTheme.FullScreen", "android:windowFullscreen", "true");
        stylesFileBuilder.addItemToStyle("AppTheme.FullScreen", "android:windowContentOverlay", "@null");
        stylesFileBuilder.addStyle("AppTheme.AppBarOverlay", "ThemeOverlay.MaterialComponents.Dark.ActionBar");
        stylesFileBuilder.addStyle("AppTheme.PopupOverlay", "ThemeOverlay.MaterialComponents.Light");
        srcCodeBeans.add(new SrcCodeBean("styles.xml", stylesFileBuilder.toCode()));

        XmlBuilderHelper stringsFileBuilder = new XmlBuilderHelper();
        stringsFileBuilder.addNonTranslatableString("app_name", applicationName);
        srcCodeBeans.add(new SrcCodeBean("strings.xml", stringsFileBuilder.toCode()));

        return srcCodeBeans;
    }

    public void saveFile(String fileName, String fileContent) {
        String filePath = getFilePath(fileName);
        FileUtil.saveFile(filePath, fileContent);
    }

    private String getFilePath(String fileName) {
        if (fileName.equals("AndroidManifest.xml")) {
            return getAndroidManifest().getAbsolutePath();
        } else {
            return getResDirectory().getAbsolutePath() + File.separator + "values" + File.separator + fileName;
        }
    }

    public void deleteTemporaryFiles() {
        File file = new File(getResDirectory().getAbsolutePath() + File.separator + "values-v21");
        if (FileUtil.isExists(file)) {
            FileUtil.deleteFile(file);
        }
        FileUtil.deleteFile(getBinDirectory());
        FileUtil.deleteFile(getMyscDirectory());
        FileUtil.deleteFile(getRJavaDirectory());
    }

    public void createNeededDirectories() {
        FileUtil.createDirectory(getBinDirectory());
        FileUtil.createDirectory(getCompiledClassesDirectory());
        FileUtil.createDirectory(getRJavaDirectory());
        FileUtil.createDirectory(getJavaDirectory());
        FileUtil.createDirectory(getResDirectory());
        FileUtil.createDirectory(getAssetsDirectory());
    }

    public void extractResFromAssets() {
        Decompress.unzipFromAssets("resource" + File.separator + "res.zip", getResDirectory().getAbsolutePath());
    }

    public File getDataJavaDirectory() {
        return new File(SketchwarePath.DATA, id + File.separator + "files" + File.separator + "java");
    }

    public File getMyscDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id);
    }

    public File getAssetsDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "app" + File.separator + "src" + File.separator + "main" + File.separator + "assets");
    }

    public File getJavaDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "app" + File.separator + "src" + File.separator + "main" + File.separator + "java");
    }

    public File getRJavaDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "gen");
    }

    public File getResourceDirectory() {
        return new File(SketchwarePath.DATA + File.separator + id + "files" + File.separator + "resource");
    }

    public File getResourcesApkDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "bin" + File.separator + projectName + ".apk.res");
    }

    public File getAndroidManifest() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "app" + File.separator + "src" + File.separator + "main" + File.separator + "AndroidManifest.xml");
    }

    public File getBinDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "bin");
    }

    public File getResDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "app" + File.separator + "src" + File.separator + "main" + File.separator + "res");
    }

    public File getCompiledClassesDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "bin" + File.separator + "classes");
    }

    public File getUnsignedUnalignedApkDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "bin" + File.separator + projectName + ".apk.unsigned");
    }

    public File getClassesDexDirectory() {
        return new File(SketchwarePath.MYSC + File.separator + id + File.separator + "bin" + File.separator + "classes.dex");
    }
}