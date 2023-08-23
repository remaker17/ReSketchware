package app.resketchware.utils;

import android.os.Log;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class AndroidManifestInjector {
    private AndroidManifestInjector() {}

    public static File getPathAndroidManifestAttributeInjection(String projectId) {
        return new File(SketchwarePath.DATA + File.separator + projectId + File.separator + "Injection" + File.separator + "androidmanifest" + File.separator + "attributes.json");
    }

    public static File getPathAndroidManifestLauncherActivity(String projectId) {
        return new File(SketchwarePath.DATA + File.separator + projectId + File.separator + "Injection" + File.separator + "androidmanifest" + File.separator + "activity_launcher.txt");
    }

    private static ArrayList<HashMap<String, Object>> readAndroidManifestAttributeInjections(String projectId) {
        ArrayList<HashMap<String, Object>> attributes;
        TypeToken<ArrayList<HashMap<String, Object>>> typeToken = new TypeToken<ArrayList<HashMap<String, Object>>>() {};

        parseAttributes:
        {
            String errorMessage;

            try {
                File injections = getPathAndroidManifestAttributeInjection(projectId);

                if (injections.exists()) {
                    attributes = GsonHelper.fromJson(FileUtil.readFile(injections), typeToken.getType());

                    if (attributes == null) {
                        errorMessage = "result == null";
                    } else {
                        break parseAttributes;
                    }
                } else {
                    return new ArrayList<>();
                }
            } catch (Exception e) {
                errorMessage = e.toString();
            }

            attributes = new ArrayList<>();
            Log.e("AndroidManifestInjector", "Failed to parse AndroidManifest attribute injections.", errorMessage);
        }

        return attributes;
    }

    public static boolean isActivityThemeUsed(String projectId, String actName) {
        return isActivityAttributeUsed("android:theme", projectId, actName);
    }

    public static boolean isActivityOrientationUsed(String projectId, String actName) {
        return isActivityAttributeUsed("android:screenOrientation", projectId, actName);
    }

    public static boolean isActivityKeyboardUsed(String projectId, String actName) {
        return isActivityAttributeUsed("android:windowSoftInputMode", projectId, actName);
    }

    public static boolean isActivityExportedUsed(String projectId, String activityName) {
        return isActivityAttributeUsed("android:exported", projectId, activityName);
    }

    public static boolean isActivityAttributeUsed(String attribute, String projectId, String activityName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = activityName.substring(0, activityName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attributeMap = attributes.get(i);
            Object name = attributeMap.get("name");

            if (name instanceof String) {
                if (className.equals(name) || name.equals("_apply_for_all_activities") {
                    Object value = attributeMap.get("value");

                    if (value instanceof String) {
                        if (((String) value).contains(attribute)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean getActivityAttrs(XmlBuilder builder, String projectId, String activityName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = activityName.substring(0, activityName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name)) {
                    addToActivity(builder, projectId, activityName);
                    return true;
                }
            }
        }

        return false;
    }

    public static void addToActivity(XmlBuilder builder, String projectId, String activityName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = activityName.substring(0, activityName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name) || name.equals("_apply_for_all_activities")) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        builder.addAttributeValue((String) value);
                    }
                }
            }
        }
    }

    public static String mHolder(String manifest) {
        List<String> manifestLines = new ArrayList<>(Arrays.asList(manifest.split("\n")));
        StringBuilder result = new StringBuilder();

        for (String line : manifestLines) {
            if (result.length() == 0) {
                result.append(line);
                continue;
            }
            result.append('\n');
            result.append(line);
        }

        return result.toString();
    }

    public static String getLauncherActivity(String projectId) {
        File launcherActivityFile = getPathAndroidManifestLauncherActivity(projectId);

        if (launcherActivityFile.exists()) {
            String launcherActivity = FileUtil.readFile(launcherActivityFile);

            if (!launcherActivity.contains(" ") && !launcherActivity.contains(".")) {
                return launcherActivity;
            }
        }

        return "main";
    }
}