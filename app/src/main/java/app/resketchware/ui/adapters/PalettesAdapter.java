package app.resketchware.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import app.resketchware.R;
import app.resketchware.ui.viewholders.PalettesViewHolder;

import java.util.List;

public class PalettesAdapter extends RecyclerView.Adapter<PalettesViewHolder> {

    private final List<Integer> colors;
    private int selectedAccent = Color.WHITE;
    private PaletteClickListener paletteClickListener;

    public interface PaletteClickListener {
        void invoke(int color);
    }

    public PalettesAdapter(List<Integer> colors) {
        this.colors = colors;
    }

    @Override
    public PalettesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_palette_item, parent, false);
        return new PalettesViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    @Override
    public void onBindViewHolder(PalettesViewHolder holder, int position) {
        holder.bind(colors.get(holder.getAbsoluteAdapterPosition()), selectedAccent);
        holder.itemView.setOnClickListener(v -> {
            if (holder.getAbsoluteAdapterPosition() != selectedAccent) {
                notifyItemChanged(selectedAccent);
                selectedAccent = holder.getAbsoluteAdapterPosition();
                notifyItemChanged(selectedAccent);
                if (paletteClickListener != null) {
                    paletteClickListener.invoke(colors.get(selectedAccent));
                }
            }
        });
    }

    public void setOnPaletteClickListener(PaletteClickListener listener) {
        paletteClickListener = listener;
    }
}