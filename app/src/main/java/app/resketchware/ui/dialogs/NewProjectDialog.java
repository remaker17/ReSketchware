package app.resketchware.ui.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import app.resketchware.R;
import app.resketchware.ui.widgets.ThemeColorView;

public class NewProjectDialog extends BottomSheetDialogFragment {

    private LinearLayout mThemeColorsContainer;
    private View mAdvancedOptions;
    private View mCancelButton;
    private View mCreateButton;
    private ViewGroup mHiddenAdvancedOptions;
    private TextView mAdvancedOptionsTextView;

    private final String[] themeColorKeys = {
            "color_accent",
            "color_primary",
            "color_primary_dark",
            "color_control_highlight",
            "color_control_normal"
    };
    private final String[] themeColorLabels = {
            "colorAccent",
            "colorPrimary",
            "colorPrimaryDark",
            "colorControlHighlight",
            "colorControlNormal"
    };
    private final int[] projectThemeColors = new int[themeColorKeys.length];

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

        projectThemeColors[0] = getResources().getColor(R.color.rsw_color_orange_5);
        projectThemeColors[1] = getResources().getColor(R.color.rsw_color_blue_5);
        projectThemeColors[2] = getResources().getColor(R.color.rsw_color_blue_6);
        projectThemeColors[3] = getResources().getColor(R.color.rsw_color_gray_1);
        projectThemeColors[4] = getResources().getColor(R.color.rsw_color_gray_5);

        for (int i = 0; i < themeColorKeys.length; i++) {
            final int index = i;
            ThemeColorView colorView = new ThemeColorView(view.getContext(), i);
            colorView.nameTextView.setText(themeColorLabels[i]);
            mThemeColorsContainer.addView(colorView);
            colorView.setOnClickListener(v -> pickColor(v, index));
        }

        syncThemeColors();
    }

    private void syncThemeColors() {
        for (int i = 0; i < projectThemeColors.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                BlendModeColorFilter colorFilter = new BlendModeColorFilter(projectThemeColors[i], BlendMode.SRC_ATOP);
                ((ThemeColorView) mThemeColorsContainer.getChildAt(i)).colorView.getBackground().setColorFilter(colorFilter);
            } else {
                PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(projectThemeColors[i], PorterDuff.Mode.SRC_ATOP);
                ((ThemeColorView) mThemeColorsContainer.getChildAt(i)).colorView.getBackground().setColorFilter(colorFilter);
            }
        }
    }

    private void initViews(View view) {
        mThemeColorsContainer = view.findViewById(R.id.colors_container);
        mAdvancedOptions = view.findViewById(R.id.advanced_options);
        mHiddenAdvancedOptions = view.findViewById(R.id.hidden_advanced_options);
        mAdvancedOptionsTextView = view.findViewById(R.id.advanced_options_text);
        mCancelButton = view.findViewById(R.id.cancel);
        mCreateButton = view.findViewById(R.id.create);
    }

    private void setListeners() {
        mAdvancedOptions.setOnClickListener(v -> toggleAdvancedOptions());
        mCancelButton.setOnClickListener(v -> dismiss());
        mCreateButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
        });
    }

    private void toggleAdvancedOptions() {
        ViewGroup dialogView = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        int dropDrawableResId = mHiddenAdvancedOptions.isShown() ? R.drawable.rsw_drop_down : R.drawable.rsw_drop_up;
        mAdvancedOptionsTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, dropDrawableResId, 0);

        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(300);
        TransitionManager.beginDelayedTransition(dialogView, autoTransition);

        if (!mHiddenAdvancedOptions.isShown()) {
            mHiddenAdvancedOptions.setVisibility(View.VISIBLE);
        } else {
            mHiddenAdvancedOptions.setVisibility(View.GONE);
        }
    }

    private void pickColor(View v, int colorIndex) {
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(projectThemeColors[(int) v.getTag()]);
        dialog.setOnPositiveClick(color -> {
            projectThemeColors[colorIndex] = color;
            syncThemeColors();
            return kotlin.Unit.INSTANCE;
        });
        dialog.show(getChildFragmentManager(), null);
    }
}