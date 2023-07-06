package app.resketchware.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import app.resketchware.R;

public class NewProjectDialog extends BottomSheetDialogFragment {

    private View advancedOptions;
    private View cancelButton;
    private View createButton;
    private View hiddenAdvancedOptions;
    private TextView advancedOptionsTextView;

    private boolean isAdvancedOptionsOpened = false;

    public static NewProjectDialog newInstance() {
        return new NewProjectDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        advancedOptions = view.findViewById(R.id.advanced_options);
        hiddenAdvancedOptions = view.findViewById(R.id.hidden_advanced_options);
        advancedOptionsTextView = view.findViewById(R.id.advanced_options_text);
        cancelButton = view.findViewById(R.id.cancel);
        createButton = view.findViewById(R.id.create);

        advancedOptions.setOnClickListener(v -> {
            hiddenAdvancedOptions.setVisibility(isAdvancedOptionsOpened ? View.GONE : View.VISIBLE);
            int dropDrawableResId = isAdvancedOptionsOpened ? R.drawable.rsw_drop_down : R.drawable.rsw_drop_up;
            advancedOptionsTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, dropDrawableResId, 0);
            isAdvancedOptionsOpened = !isAdvancedOptionsOpened;
        });

        cancelButton.setOnClickListener(v -> dismiss());
        createButton.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
        });
    }
}