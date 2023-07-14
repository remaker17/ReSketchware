package app.resketchware.ui.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.android.material.card.MaterialCardView;

import app.resketchware.R;

public class TonesViewHolder extends ViewHolder {

    private final MaterialCardView cardView;
    private final ImageView check;
    private int selectedColor;

    public TonesViewHolder(View view) {
        super(view);

        cardView = view.findViewById(R.id.tone);
        check = view.findViewById(R.id.check);
    }

    public void bind(int colorInt, int selectedColor) {
        this.selectedColor = selectedColor;
        cardView.setCardBackgroundColor(colorInt);

        if (getAbsoluteAdapterPosition() != selectedColor) {
            check.setVisibility(View.GONE);
        } else {
            check.setVisibility(View.VISIBLE);
            int surfaceColor = ColorUtils.calculateLuminance(colorInt) < 0.35 ? Color.WHITE : Color.BLACK;
            check.getDrawable().mutate().setTint(
                ColorUtils.setAlphaComponent(surfaceColor, 125)
            );
        }
    }
}