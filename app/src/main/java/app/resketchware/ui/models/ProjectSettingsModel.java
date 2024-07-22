package app.resketchware.ui.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class ProjectSettingsModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("min_sdk")
  private final String minSdk;
  @SerializedName("enable_bridgeless_themes")
  private final String enableBridgelessThemes;
  @SerializedName("app_class")
  private final String applicationClass;
  @SerializedName("target_sdk")
  private final String targetSdk;
  @SerializedName("disable_old_methods")
  private final String disableOldMethods;
  @SerializedName("disable_large_heap")
  private final String disableLargeHeap;

  public ProjectSettingsModel() {
    this("21", "true", ".SketchApplication", "28", "false", "true");
  }

  public ProjectSettingsModel(String minSdk, String enableBridgelessThemes, String applicationClass, String targetSdk, String disableOldMethods, String disableLargeHeap) {
    this.minSdk = minSdk;
    this.enableBridgelessThemes = enableBridgelessThemes;
    this.applicationClass = applicationClass;
    this.targetSdk = targetSdk;
    this.disableOldMethods = disableOldMethods;
    this.disableLargeHeap = disableLargeHeap;
  }

  public int getMinSdk(String defaultValue) {
    if (minSdk == null || minSdk.isEmpty()) {
      minSdk = defaultValue;
    }
    return Integer.parseInt(minSdk);
  }

  public boolean isEnableBridgelessThemes() {
    return Boolean.parseBoolean(enableBridgelessThemes);
  }

  public String getApplicationClass(String defaultValue) {
    if (applicationClass == null || applicationClass.isEmpty()) {
      applicationClass = defaultValue;
    }
    return applicationClass;
  }

  public int getTargetSdk(String defaultValue) {
    if (targetSdk == null || targetSdk.isEmpty()) {
      targetSdk = defaultValue;
    }
    return Integer.parseInt(targetSdk);
  }

  public boolean isDisableOldMethods() {
    return Boolean.parseBoolean(disableOldMethods);
  }

  public boolean isDisableLargeHeap() {
    return Boolean.parseBoolean(disableLargeHeap);
  }
}
