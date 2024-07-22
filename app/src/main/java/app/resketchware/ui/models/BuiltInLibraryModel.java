package app.resketchware.ui.models;

import app.resketchware.utils.BuiltInLibraries;
import app.resketchware.utils.BuiltInLibraryUtil;

public final class BuiltInLibraryModel {

  /**
   * e.g: material-1.0.0
   */
  private String name;

  /**
   * e.g: com.google.android.material
   */
  private String packageName = "";

  private boolean hasAssets;
  private boolean hasResources;

  public BuiltInLibraryModel(String name) {
    this.name = name;
    this.hasAssets = name.equals(BuiltInLibraries.CODE_VIEW);
    this.hasResources = BuiltInLibraryUtil.hasResources(name);

    if (hasResources || hasAssets) {
      this.packageName = BuiltInLibraryUtil.getPackageName(name);
    }
  }

  public String getName() {
    return name;
  }

  public String getPackageName() {
    return packageName;
  }

  public boolean hasAssets() {
    return hasAssets;
  }

  public boolean hasResources() {
    return hasResources;
  }
}
