package app.resketchware.utils;

import android.os.Environment;

public class SketchwarePath {
    public static final String ROOT = Environment.getExternalStorageDirectory() + "/.sketchware";
    public static final String DATA = ROOT + "/data";
    public static final String MYSC = ROOT + "/mysc";
    public static final String MYSC_LIST = MYSC + "/list";
    public static final String RESOURCES = ROOT + "/resources";
    public static final String RESOURCES_ICONS = RESOURCES + "/icons";
}