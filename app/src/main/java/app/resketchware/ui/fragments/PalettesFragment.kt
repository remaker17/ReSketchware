package app.resketchware.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.view.doOnPreDraw
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import app.resketchware.R
import app.resketchware.ui.adapters.PalettesAdapter
import app.resketchware.ui.adapters.TonesAdapter
import app.resketchware.utils.ColorPickerUtil

class PalettesFragment : Fragment() {

    private lateinit var palettesRecyclerView: RecyclerView
    private lateinit var selectedPaletteRecyclerView: RecyclerView

    private var palettesAdapter: PalettesAdapter? = null
    private var tonesAdapter: TonesAdapter? = null
    var onColorSelect: ((Int) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_palettes, container, false)

        palettesRecyclerView = view.findViewById(R.id.palettes)
        selectedPaletteRecyclerView = view.findViewById(R.id.selected_palette)

        tonesAdapter = TonesAdapter()
        palettesAdapter = PalettesAdapter(ColorPickerUtil.colorsMap.keys.toList())

        palettesRecyclerView.adapter = palettesAdapter?.apply {
            onPaletteClick = { position ->
                tonesAdapter?.swapColors(position)
            }
        }
        palettesRecyclerView.setHasFixedSize(true)

        selectedPaletteRecyclerView.adapter = tonesAdapter?.apply {
            onToneClick = { color ->
                onColorSelect?.invoke(color)
            }
        }
        selectedPaletteRecyclerView.setHasFixedSize(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedPaletteRecyclerView.doOnPreDraw {
            palettesRecyclerView.updatePadding(left = it.left, right = it.right)
            (palettesRecyclerView.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(0, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        palettesAdapter = null
        tonesAdapter = null
    }

    fun resetPalette() {
        palettesAdapter?.resetSelectedPalette()
        tonesAdapter?.resetColor()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PalettesFragment()
    }
}