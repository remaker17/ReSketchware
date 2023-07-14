package app.resketchware.ui.viewholders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.android.material.card.MaterialCardView;

import app.resketchware.R;
import app.resketchware.utils.ContextUtil;

public class PalettesViewHolder extends ViewHolder {

    private final MaterialCardView cardView;
    private final TextView colorName;
    private int selectedAccent;

    public PalettesViewHolder(View view) {
        super(view);

        cardView = view.findViewById(R.id.palette);
        colorName = view.findViewById(R.id.color_name);
    }

    public void bind(int position, int selectedAccent) {
        this.selectedAccent = selectedAccent;

        Context context = itemView.getContext();
        Resources resources = itemView.getResources();

        String name = resources.getStringArray(R.array.rsw_accent_names)[position];
        colorName.setText(name);

        if (getAbsoluteAdapterPosition() != selectedAccent) {
            int colorDisabled = ContextUtil.getThemeColor(context, R.attr.rsw_background2);
            cardView.setStrokeColor(colorDisabled);

            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.rsw_checked);
            drawable.mutate().setTint(colorDisabled);

            colorName.setTextColor(colorDisabled);
            colorName.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
        } else {
            int palette = resources.getIntArray(R.array.rsw_accent_colors)[position];
            cardView.setStrokeColor(palette);

            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.rsw_checked);
            drawable.mutate().setTint(palette);

            colorName.setTextColor(palette);
            colorName.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
        }
    }
}