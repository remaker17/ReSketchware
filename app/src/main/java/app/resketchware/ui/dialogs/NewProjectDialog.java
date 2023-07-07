package app.resketchware.ui.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import app.resketchware.utils.AnimationUtil;

public class NewProjectDialog extends BottomSheetDialogFragment {

    private View advancedOptions;
    private View cancelButton;
    private View createButton;
    private ViewGroup hiddenAdvancedOptions;
    private TextView advancedOptionsTextView;

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

        initViews(view);
        setListeners();
    }

    private void initViews(View view) {
        advancedOptions = view.findViewById(R.id.advanced_options);
        hiddenAdvancedOptions = view.findViewById(R.id.hidden_advanced_options);
        advancedOptionsTextView = view.findViewById(R.id.advanced_options_text);
        cancelButton = view.findViewById(R.id.cancel);
        createButton = view.findViewById(R.id.create);
    }

    private void setListeners() {
        advancedOptions.setOnClickListener(v -> toggleAdvancedOptions());
        cancelButton.setOnClickListener(v -> dismiss());
        createButton.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
        });
    }

    private void toggleAdvancedOptions() {
        int dropDrawableResId = hiddenAdvancedOptions.isShown() ? R.drawable.rsw_drop_down : R.drawable.rsw_drop_up;
        advancedOptionsTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, dropDrawableResId, 0);

        if (!hiddenAdvancedOptions.isShown()) {
            hiddenAdvancedOptions.setVisibility(View.VISIBLE);
            AnimationUtil.animateViewHeight(hiddenAdvancedOptions, true, 300, null);
            return;
        }

        AnimationUtil.animateViewHeight(hiddenAdvancedOptions, false, 300, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hiddenAdvancedOptions.setVisibility(View.GONE);
            }
        });
    }
}