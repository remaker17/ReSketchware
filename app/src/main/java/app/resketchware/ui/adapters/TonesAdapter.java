package app.resketchware.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import app.resketchware.R;
import app.resketchware.ui.viewholders.TonesViewHolder;
import app.resketchware.utils.ColorPickerUtil;

import java.util.List;

public class TonesAdapter extends RecyclerView.Adapter<TonesViewHolder> {

    private List<Integer> colors = ColorPickerUtil.colorsMap.get(0);
    private int selectedColor = RecyclerView.NO_POSITION;
    private int selectedPalette = 0;
    private ToneClickListener toneClickListener;

    public interface ToneClickListener {
        void invoke(int color);
    }

    public void swapColors(int selection) {
        resetColor();
        selectedPalette = selection;
        colors = ColorPickerUtil.colorsMap.get(selection);
        notifyDataSetChanged();
    }

    @Override
    public TonesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_tone_item, parent, false);
        return new TonesViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    @Override
    public void onBindViewHolder(TonesViewHolder holder, int position) {
        holder.bind(colors.get(holder.getAbsoluteAdapterPosition()), selectedColor);
        holder.itemView.setOnClickListener(v -> {
            if (holder.getAbsoluteAdapterPosition() != selectedColor) {
                notifyItemChanged(selectedColor);
                selectedColor = holder.getAbsoluteAdapterPosition();
                notifyItemChanged(selectedColor);
                if (toneClickListener != null) {
                    toneClickListener.invoke(colors.get(selectedColor));
                }
            }
        });
    }

    public void setOnToneClickListener(ToneClickListener listener) {
        toneClickListener = listener;
    }

    public void resetColor() {
        selectedColor = RecyclerView.NO_POSITION;
    }
}