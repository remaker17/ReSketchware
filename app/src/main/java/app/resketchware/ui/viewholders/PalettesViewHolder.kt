package app.resketchware.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.google.android.material.card.MaterialCardView

import app.resketchware.R
import app.resketchware.utils.ContextUtil

class PalettesViewHolder(private val view: View) : ViewHolder(view) {

    private val cardView: MaterialCardView
        get() = view.findViewById(R.id.palette)
    private val colorName: TextView
        get() = view.findViewById(R.id.color_name)

    private var selectedAccent = 0

    fun bind(position: Int, selectedAccent: Int) {
        this.selectedAccent = selectedAccent

        val context = itemView.context
        val resources = itemView.resources

        val name = resources.getStringArray(R.array.rsw_accent_names)[absoluteAdapterPosition]
        colorName.text = name

        if (absoluteAdapterPosition != selectedAccent) {
            val colorDisabled = ContextUtil.getThemeColor(context, android.R.attr.colorButtonNormal)
            cardView.strokeColor = colorDisabled

            val drawable = ContextCompat.getDrawable(context, R.drawable.rsw_checked)
            drawable?.mutate()?.setTint(colorDisabled)

            colorName.setTextColor(colorDisabled)
            colorName.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        } else {
            val palette = resources.getIntArray(R.array.rsw_accent_colors)[position]
            cardView.strokeColor = palette

            val drawable = ContextCompat.getDrawable(context, R.drawable.rsw_checked)
            drawable?.mutate()?.setTint(palette)

            colorName.setTextColor(palette)
            colorName.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        }
    }
}