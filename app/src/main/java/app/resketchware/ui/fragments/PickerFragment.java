package app.resketchware.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;

import java.util.HashMap;
import java.util.Map;

import app.resketchware.R;

public class PickerFragment extends Fragment {

    private final Map<Slider, TextView> sliders = new HashMap<>();
    private int selectedColor = Color.WHITE;

    private Slider sliderR;
    private Slider sliderG;
    private Slider sliderB;

    private TextView valueR;
    private TextView valueG;
    private TextView valueB;

    private PickerUpdateListener pickerUpdateListener;

    public interface PickerUpdateListener {
        void invoke(float r, float g, float b);
    }

    public static PickerFragment newInstance(int selectedColor) {
        PickerFragment fragment = new PickerFragment();
        Bundle args = new Bundle();
        args.putInt("selected_color", selectedColor);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnPickerUpdateListener(PickerUpdateListener listener) {
        pickerUpdateListener = listener;
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
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        sliderR = view.findViewById(R.id.slider_r);
        sliderG = view.findViewById(R.id.slider_g);
        sliderB = view.findViewById(R.id.slider_b);

        valueR = view.findViewById(R.id.value_r);
        valueG = view.findViewById(R.id.value_g);
        valueB = view.findViewById(R.id.value_b);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sliders.put(sliderR, valueR);
        sliders.put(sliderG, valueG);
        sliders.put(sliderB, valueB);

        updateSliders();

        for (Map.Entry<Slider, TextView> entry : sliders.entrySet()) {
            final Slider slider = entry.getKey();
            final TextView value = entry.getValue();

            slider.addOnChangeListener((sliderView, valueFloat, fromUser) -> {
                value.setText(String.valueOf(valueFloat));

                if (fromUser) {
                    pickerUpdateListener.invoke(sliderR.getValue(), sliderG.getValue(), sliderB.getValue());
                }
            });
        }
    }

    public void updateColor(int color) {
        if (selectedColor != color) {
            selectedColor = color;
            updateSliders();
        }
    }

    private void updateSliders() {
        sliderR.setValue(Color.red(selectedColor));
        sliderG.setValue(Color.green(selectedColor));
        sliderB.setValue(Color.blue(selectedColor));
    }
}