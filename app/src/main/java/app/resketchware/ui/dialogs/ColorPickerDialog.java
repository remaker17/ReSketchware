package app.resketchware.ui.dialogs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import app.resketchware.R;
import app.resketchware.ui.fragments.PalettesFragment;
import app.resketchware.ui.fragments.PickerFragment;

public class ColorPickerDialog extends BottomSheetDialogFragment {

    private PalettesFragment palettesFragment;
    private PickerFragment pickerFragment;

    private TabLayout tabLayout;
    private TextView hexValue;
    private TextView title;
    private MaterialCardView colorPreview;
    private ViewPager2 viewPager;

    private int selectedColor = Color.WHITE;
    private PositiveClickListener positiveClickListener;

    public interface PositiveClickListener {
        void invoke(int color);
    }

    public static ColorPickerDialog newInstance(int selectedColor) {
        ColorPickerDialog fragment = new ColorPickerDialog();
        Bundle args = new Bundle();
        args.putInt("selected_color", selectedColor);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnPositiveClickListener(PositiveClickListener listener) {
        positiveClickListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedColor = getArguments().getInt("selected_color", Color.WHITE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_color_picker, container, false);

        colorPreview = view.findViewById(R.id.color_preview);
        hexValue = view.findViewById(R.id.hex_value);
        tabLayout = view.findViewById(R.id.tab_layout);
        title = view.findViewById(R.id.title);
        viewPager = view.findViewById(R.id.pager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View cancelButton = view.findViewById(R.id.cancel);
        View positiveButton = view.findViewById(R.id.ok);

        cancelButton.setOnClickListener(v -> dismissAllowingStateLoss());
        positiveButton.setOnClickListener(v -> {
            if (positiveClickListener != null) {
                positiveClickListener.invoke(selectedColor);
            }
            dismissAllowingStateLoss();
        });

        ColorPickerPagerAdapter adapter = new ColorPickerPagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            int[] tabIcons = {R.drawable.rsw_colorize, R.drawable.rsw_palette};
            tab.setIcon(tabIcons[position]);
        }).attach();

        updateColors(selectedColor);
    }

    private Fragment handleOnNavigationItemSelected(int itemId) {
        switch (itemId) {
            case 0:
                return pickerFragment != null ? pickerFragment : initFragmentAt(itemId);
            default:
                return palettesFragment != null ? palettesFragment : initFragmentAt(itemId);
        }
    }

    private Fragment initFragmentAt(int itemId) {
        switch (itemId) {
            case 0:
                pickerFragment = PickerFragment.newInstance(selectedColor);
                pickerFragment.setOnPickerUpdateListener((r, g, b) -> {
                    int color = Color.rgb(r, g, b);
                    if (selectedColor != color) {
                        selectedColor = color;
                        updateColors(color);
                    }
                });
                return pickerFragment;
            default:
                palettesFragment = PalettesFragment.newInstance();
                palettesFragment.setOnColorSelectListener(color -> {
                    if (selectedColor != color) {
                        selectedColor = color;
                        updateColors(color);
                    }
                });
                return palettesFragment;
        }
    }

    private void updateColors(int color) {
        int contrastColor = ColorUtils.calculateLuminance(color) < 0.35 ? Color.WHITE : Color.BLACK;

        tabLayout.setSelectedTabIndicatorColor(contrastColor);
        tabLayout.setTabIconTint(ColorStateList.valueOf(contrastColor));
        tabLayout.setTabRippleColor(ColorStateList.valueOf(ColorUtils.setAlphaComponent(contrastColor, Math.round(0.25f * 255))));

        hexValue.setText(String.format("#FF%06X", 0xFFFFFF & color));
        hexValue.setTextColor(contrastColor);

        title.setTextColor(contrastColor);

        colorPreview.setCardBackgroundColor(color);

        if (pickerFragment != null) {
            pickerFragment.updateColor(color);
        }
    }

    private class ColorPickerPagerAdapter extends FragmentStateAdapter {

        public ColorPickerPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return handleOnNavigationItemSelected(position);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}