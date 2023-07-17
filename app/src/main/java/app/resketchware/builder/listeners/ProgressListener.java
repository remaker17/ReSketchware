package app.resketchware.builder.listeners;

import app.resketchware.ui.viewmodels.CompilerViewModel;

public interface ProgressListener {
    void post(String message);

    static ProgressListener wrap(CompilerViewModel viewModel) {
        return new ProgressListener() {
            @Override
            public void post(String message) {
                viewModel.setMessage(message);
            }
        };
    }
}