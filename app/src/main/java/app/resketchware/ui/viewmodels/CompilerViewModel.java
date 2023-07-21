package app.resketchware.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.resketchware.ui.models.Project;

public class CompilerViewModel extends ViewModel {

    private MutableLiveData<String> message;
    private MutableLiveData<Project> project;
    private MutableLiveData<Boolean> isCompiling;

    public void setMessage(@Nullable String value) {
        message.setValue(value);
    }

    public void setProject(Project value) {
        project.setValue(value);
    }

    public void setCompiling(boolean value) {
        isCompiling.setValue(value);
    }

    public LiveData<String> getMessage() {
        if (message == null) {
            message = new MutableLiveData<>(null);
        }
        return message;
    }

    public LiveData<Project> getProject() {
        if (project == null) {
            project = new MutableLiveData<>();
        }
        return project;
    }

    public MutableLiveData<Boolean> isCompiling() {
        if (isCompiling == null) {
            isCompiling = new MutableLiveData<>();
        }
        return isCompiling;
    }
}