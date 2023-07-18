package app.resketchware.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.resketchware.ui.models.Project;

public class CompilerViewModel extends ViewModel {

    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Project> project = new MutableLiveData<>();

    private MutableLiveData<Boolean> isCompiling;

    public void setMessage(String value) {
        message.setValue(value);
    }

    public void setProject(Project value) {
        project.setValue(value);
    }

    public void setCompiling(boolean value) {
        isCompiling.setValue(value);
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<Project> getProject() {
        return project;
    }

    public MutableLiveData<Boolean> isCompiling() {
        if (isCompiling == null) {
            isCompiling = new MutableLiveData<>();
        }
        return isCompiling;
    }
}