package app.resketchware.ui.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
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
import app.resketchware.utils.AnimationUtil;

public class NewProjectDialog extends BottomSheetDialogFragment {

    private LinearLayout themeColorsContainer;
    private View advancedOptions;
    private View cancelButton;
    private View createButton;
    private ViewGroup scrollContainer;
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
        projectThemeColors[3] = getResources().getColor(R.color.rsw_color_black_alpha12);
        projectThemeColors[4] = getResources().getColor(R.color.rsw_color_gray_5);

        for (int i = 0; i < themeColorKeys.length; i++) {
            ThemeColorView colorView = new ThemeColorView(requireContext(), i);
            colorView.getNameTextView().setText(themeColorLabels[i]);
            colorView.getColorView().getBackground().setColorFilter(new BlendModeColorFilter(projectThemeColors[i], BlendMode.SRC_ATOP));
            themeColorsContainer.addView(colorView);
        }
    }

    private void initViews(View view) {
        themeColorsContainer = view.findViewById(R.id.colors_container);
        advancedOptions = view.findViewById(R.id.advanced_options);
        hiddenAdvancedOptions = view.findViewById(R.id.hidden_advanced_options);
        advancedOptionsTextView = view.findViewById(R.id.advanced_options_text);
        scrollContainer = view.findViewById(R.id.scroll_container);
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
        int dropDrawableResId = hiddenAdvancedOptions.isShown() ? R.drawable.rsw_drop_down : R.drawable.rsw_drop_up;
        advancedOptionsTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, dropDrawableResId, 0);

        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(scrollContainer, autoTransition);

        if (!hiddenAdvancedOptions.isShown()) {
            hiddenAdvancedOptions.setVisibility(View.VISIBLE);
            // AnimationUtil.animateViewHeight(hiddenAdvancedOptions, true, 300, null);
        } else {
            hiddenAdvancedOptions.setVisibility(View.GONE);
            /*AnimationUtil.animateViewHeight(hiddenAdvancedOptions, false, 300, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    hiddenAdvancedOptions.setVisibility(View.GONE);
                }
            });*/
        }
    }
}