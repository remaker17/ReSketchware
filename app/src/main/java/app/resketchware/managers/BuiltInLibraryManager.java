package app.resketchware.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.resketchware.ui.models.BuiltInLibraryModel;
import app.resketchware.utils.BuiltInLibraries;
import app.resketchware.utils.BuiltInLibraryUtil;

/**
 * A class to keep track of a project's built-in libraries.
 */
public class BuiltInLibraryManager {

    private final ArrayList<String> libraryNames = new ArrayList<>();
    private final ArrayList<BuiltInLibraryModel> libraries = new ArrayList<>();

    /**
     * Add a built-in library to the project libraries list.
     * Won't add a library if it's in the list already,
     *
     * @param libraryName The built-in library's name, e.g. material-1.0.0
     */
    public void addLibrary(String libraryName) {
        Optional<BuiltInLibraries.BuiltInLibrary> library = BuiltInLibraries.BuiltInLibrary.ofName(libraryName);
        //noinspection SimplifyOptionalCallChains because #isEmpty() isn't available on Android.
        if (!library.isPresent()) {
            if (!libraryNames.contains(libraryName)) {
                Log.d("BuiltInLibraryManager", "Added built-in library: " + libraryName + " to project's dependencies");
                libraryNames.add(libraryName);
                libraries.add(new BuiltInLibraryModel(libraryName));
                addDependencies(libraryName);
            } else {
                Log.v("BuiltInLibraryManager", "Didn't add built-in library \"" + libraryName + "\" to project's dependencies again");
            }
        } else {
            Log.v("BuiltInLibraryManager", "Didn't add built-in library \"" + libraryName + "\" to project's dependencies as it's excluded");
        }
    }

    private void addDependencies(String libraryName) {
        for (String libraryDependency : BuiltInLibraryUtil.getKnownDependencies(libraryName)) {
            addLibrary(libraryDependency);
        }
    }

    public ArrayList<BuiltInLibraryModel> getLibraries() {
        return libraries;
    }
}