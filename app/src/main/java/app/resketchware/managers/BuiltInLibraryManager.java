package app.resketchware.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import app.resketchware.ui.models.BuiltInLibraryModel;
import app.resketchware.utils.BuiltInLibraries;
import app.resketchware.utils.BuiltInLibraryUtil;

/**
 * A class to keep track of a project's built-in libraries.
 */
public class BuiltInLibraryManager {

    private static final ArrayList<String> libraryNames = new ArrayList<>();
    private static final ArrayList<BuiltInLibraryModel> libraries = new ArrayList<>();

    /**
     * Add a built-in library to the project libraries list.
     * Won't add a library if it's in the list already,
     *
     * @param libraryName The built-in library's name, e.g. material-1.0.0
     */
    public static void addLibrary(String libraryName) {
        if (libraryNames.contains(libraryName)) {
            Log.v("BuiltInLibraryManager", "Didn't add built-in library: " + libraryName + " to project's dependencies again");
        } else {
            Log.d("BuiltInLibraryManager", "Added built-in library: " + libraryName + " to project's dependencies");
            libraryNames.add(libraryName);
            libraries.add(new BuiltInLibraryModel(libraryName));
            addDependencies(libraryName);
        }
    }

    private static void addDependencies(String libraryName) {
        for (String libraryDependency : BuiltInLibraryUtil.getKnownDependencies(libraryName)) {
            addLibrary(libraryDependency);
        }
    }

    public static ArrayList<BuiltInLibraryModel> getLibraries() {
        Log.v("BuiltInLibraryManager", Arrays.toString(libraries.toArray()));
        return libraries;
    }
}