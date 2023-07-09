package app.resketchware.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class ContextUtil {

    /**
     * Private constructor to prevent instantiation of class.
     */
    private ContextUtil() {}

    /**
     * Retrieves the color value of the specified attribute from the current theme of the supplied context.
     * @param context The context used to retrieve the theme.
     * @param attr The attribute whose color value is to be retrieved.
     * @return The integer representation of the color value.
     */
    @ColorInt
    public static int getThemeColor(Context context, @AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    /**
     * Retrieves the dimension value associated with the specified dimension resource ID from the resources of the supplied context.
     * @param context The context used to retrieve the dimension value.
     * @param dimen The dimension resource ID to retrieve.
     * @return The dimension value in pixels.
     */
    public static int getDimenFromResources(Context context, @DimenRes int dimen) {
        return context.getResources().getDimensionPixelSize(dimen);
    }

    public static boolean hasStoragePermissions(@NonNull Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }
}