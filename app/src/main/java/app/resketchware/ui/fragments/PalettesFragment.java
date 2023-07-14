package app.resketchware.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.resketchware.R;
import app.resketchware.ui.adapters.PalettesAdapter;
import app.resketchware.ui.adapters.TonesAdapter;
import app.resketchware.utils.ColorPickerUtil;

import java.util.ArrayList;
import java.util.List;

public class PalettesFragment extends Fragment {

    private RecyclerView palettesRecyclerView;
    private RecyclerView selectedPaletteRecyclerView;
    private PalettesAdapter palettesAdapter;
    private TonesAdapter tonesAdapter;
    private ColorSelectListener colorSelectListener;

    public interface ColorSelectListener {
        void invoke(int color);
    }

    public static PalettesFragment newInstance() {
        return new PalettesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palettes, container, false);

        palettesRecyclerView = view.findViewById(R.id.palettes);
        selectedPaletteRecyclerView = view.findViewById(R.id.selected_palette);

        List<Integer> list = new ArrayList<>(ColorPickerUtil.colorsMap.keySet());

        tonesAdapter = new TonesAdapter();
        palettesAdapter = new PalettesAdapter(list);

        palettesRecyclerView.setAdapter(palettesAdapter);
        palettesRecyclerView.setHasFixedSize(true);
        selectedPaletteRecyclerView.setAdapter(tonesAdapter);
        selectedPaletteRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tonesAdapter.setOnToneClickListener((color) -> {
            if (colorSelectListener != null) {
                colorSelectListener.invoke(color);
            }
        });

        palettesAdapter.setOnPaletteClickListener((position) -> {
            if (tonesAdapter != null) {
                tonesAdapter.swapColors(position);
            }
        });
    }

    public void setOnColorSelectListener(ColorSelectListener listener) {
        colorSelectListener = listener;
    }

    public void resetPalette() {
        palettesAdapter.resetSelectedPalette();
        tonesAdapter.resetColor();
    }
}