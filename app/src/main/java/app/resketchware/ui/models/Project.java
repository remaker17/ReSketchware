package app.resketchware.ui.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("sc_id") private final String id;
    @SerializedName("my_app_name") private final String applicationName;
    @SerializedName("my_sc_pkg_name") private final String packageName;
    @SerializedName("sc_ver_name") private final String versionName;
    @SerializedName("sc_ver_code") private final String versionCode;
    @SerializedName("custom_icon") private final boolean hasCustomIcon;

    public Project(String id, String applicationName, String packageName, String versionName, String versionCode) {
        this(id, applicationName, packageName, versionName, versionCode, false);
    }

    public Project(String id, String applicationName, String packageName, String versionName, String versionCode, boolean hasCustomIcon) {
        this.id = id != null ? id : "";
        this.applicationName = applicationName != null ? applicationName : "";
        this.packageName = packageName != null ? packageName : "";
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
}