package app.resketchware.ui.viewholders

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.card.MaterialCardView

import app.resketchware.R
import app.resketchware.ui.extensions.toSurfaceColor

class TonesViewHolder(private val view: View) : ViewHolder(view) {

    private val cardView: MaterialCardView
        get() = view.findViewById(R.id.tone)
    private val check: ImageView
        get() = view.findViewById(R.id.check)
    private var selectedColor = 0

    fun bind(colorInt: Int, selectedColor: Int) {
        this.selectedColor = selectedColor
        cardView.setCardBackgroundColor(colorInt)

        if (absoluteAdapterPosition != selectedColor) {
            check.visibility = View.GONE
        } else {
            check.visibility = View.VISIBLE
            check.drawable.mutate().setTint(
                ColorUtils.setAlphaComponent(colorInt.toSurfaceColor(), 125)
            )
        }
    }
}