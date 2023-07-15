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

    private LinearLayout themeColorsContainer;
    private View advancedOptions;
    private View cancelButton;
    private View createButton;
    private ViewGroup hiddenAdvancedOptions;
    private TextView advancedOptionsTextView;

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
            themeColorsContainer.addView(colorView);
            colorView.setOnClickListener(v -> pickColor(v, index));
        }

        syncThemeColors();
    }

    private void syncThemeColors() {
        for (int i = 0; i < projectThemeColors.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                BlendModeColorFilter colorFilter = new BlendModeColorFilter(projectThemeColors[i], BlendMode.SRC_ATOP);
                ((ThemeColorView) themeColorsContainer.getChildAt(i)).colorView.getBackground().setColorFilter(colorFilter);
            } else {
                PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(projectThemeColors[i], PorterDuff.Mode.SRC_ATOP);
                ((ThemeColorView) themeColorsContainer.getChildAt(i)).colorView.getBackground().setColorFilter(colorFilter);
            }
        }
    }

    private void initViews(View view) {
        themeColorsContainer = view.findViewById(R.id.colors_container);
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
            Toast.makeText(requireContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
        });
    }

    private void toggleAdvancedOptions() {
        ViewGroup dialogView = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        int dropDrawableResId = hiddenAdvancedOptions.isShown() ? R.drawable.rsw_drop_down : R.drawable.rsw_drop_up;
        advancedOptionsTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, dropDrawableResId, 0);

        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(300);
        TransitionManager.beginDelayedTransition(dialogView, autoTransition);

        if (!hiddenAdvancedOptions.isShown()) {
            hiddenAdvancedOptions.setVisibility(View.VISIBLE);
        } else {
            hiddenAdvancedOptions.setVisibility(View.GONE);
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