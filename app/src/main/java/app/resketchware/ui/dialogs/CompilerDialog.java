package app.resketchware.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.resketchware.R;
import app.resketchware.ui.dialogs.base.BaseBottomSheetDialogFragment;
import app.resketchware.ui.models.Project;

public class CompilerDialog extends BaseBottomSheetDialogFragment {

    private Project project;

    private TextView titleTextView;
    private TextView messageTextView;

    private String message;

    public static CompilerDialog newInstance() {
        return new CompilerDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String msg = getArguments().getString("message");
            if (msg != null) {
               message = msg;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_compiler, container, false);

        titleTextView = view.findViewById(R.id.title);
        messageTextView = view.findViewById(R.id.message);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (message != null) {
            messageTextView.setText(message);
        }
    }

    @NonNull
    public TextView getMessage() {
        return messageTextView;
    }
}