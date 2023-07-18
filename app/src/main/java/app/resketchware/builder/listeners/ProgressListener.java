package app.resketchware.builder.listeners;

import app.resketchware.ui.viewmodels.CompilerViewModel;

public interface ProgressListener {
    void post(String message);
}