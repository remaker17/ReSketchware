package app.resketchware.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.resketchware.ui.models.Project;

public class CompilerViewModel extends ViewModel {

    private final MutableLiveData<String> mMessage = new MutableLiveData<>();
    private final MutableLiveData<Project> mProject = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCompiling = new MutableLiveData<>();

    public void setMessage(@Nullable String message) {
        mMessage.setValue(message);
    }

    public void setProject(Project project) {
        mProject.setValue(project);
    }

    public void setCompiling(boolean isCompiling) {
        mCompiling.setValue(isCompiling);
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }

    public LiveData<Project> getProject() {
        return mProject;
    }

    public MutableLiveData<Boolean> isCompiling() {
        return mCompiling;
    }
}