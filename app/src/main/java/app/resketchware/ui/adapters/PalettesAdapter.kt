package app.resketchware.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import app.resketchware.R
import app.resketchware.ui.viewholders.PalettesViewHolder

class PalettesAdapter(private val colors: List<Int>) : RecyclerView.Adapter<PalettesViewHolder>() {

    var selectedAccent = 0
    var onPaletteClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalettesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_palette_item, parent, false)
        return PalettesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onBindViewHolder(holder: PalettesViewHolder, position: Int) {
        holder.bind(colors[holder.absoluteAdapterPosition], selectedAccent)
        holder.itemView.setOnClickListener {
            if (holder.absoluteAdapterPosition != selectedAccent) {
                notifyItemChanged(selectedAccent)
                selectedAccent = holder.absoluteAdapterPosition
                notifyItemChanged(selectedAccent)
                onPaletteClick?.invoke(colors[selectedAccent])
            }
        }
    }

    fun resetSelectedPalette() {
        onPaletteClick?.invoke(colors[selectedAccent])
    }
}