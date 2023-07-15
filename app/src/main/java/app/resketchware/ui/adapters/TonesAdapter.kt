package app.resketchware.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.resketchware.R
import app.resketchware.ui.viewholders.TonesViewHolder
import app.resketchware.utils.ColorPickerUtil

class TonesAdapter : RecyclerView.Adapter<TonesViewHolder>() {

    private var colors = ColorPickerUtil.colorsMap.getValue(0)
    private var selectedColor = RecyclerView.NO_POSITION
    private var selectedPalette = 0
    var onToneClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TonesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_tone_item, parent, false)
        return TonesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onBindViewHolder(holder: TonesViewHolder, position: Int) {
        holder.bind(colors[holder.absoluteAdapterPosition].toInt(), selectedColor)
        holder.itemView.setOnClickListener {
            if (holder.absoluteAdapterPosition != selectedColor) {
                notifyItemChanged(selectedColor)
                selectedColor = holder.absoluteAdapterPosition
                notifyItemChanged(selectedColor)
                onToneClick?.invoke(colors[holder.absoluteAdapterPosition].toInt())
            }
        }
    }

    fun swapColors(selection: Int) {
        resetColor()
        selectedPalette = selection
        colors = ColorPickerUtil.colorsMap.getValue(selection)
        notifyDataSetChanged()
    }

    fun resetColor() {
        selectedColor = RecyclerView.NO_POSITION
    }
}