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

public class CompilerDialog extends BaseBottomSheetDialogFragment {

    private TextView mTitleText;
    private TextView mMessageText;

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

        mTitleText = view.findViewById(R.id.title);
        mMessageText = view.findViewById(R.id.message);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (message != null) {
            mMessageText.setText(message);
        }
    }

    public TextView getTitle() {
        return mTitleText;
    }

    public TextView getMessage() {
        return mMessageText;
    }
}