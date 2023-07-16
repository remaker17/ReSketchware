package app.resketchware.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

import com.google.android.material.slider.Slider

import app.resketchware.R
import app.resketchware.ui.extensions.toSliderValue

class PickerFragment : Fragment() {

    private val sliders = mutableMapOf<Slider, TextView>()
    private var selectedColor = Color.WHITE

    private lateinit var sliderR: Slider
    private lateinit var sliderG: Slider
    private lateinit var sliderB: Slider

    private lateinit var valueR: TextView
    private lateinit var valueG: TextView
    private lateinit var valueB: TextView

    var onPickerUpdate: ((Float, Float, Float) -> Unit)? = null
    var onResetPalette: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("selected_color")?.let { color ->
            selectedColor = color
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_picker, container, false)

        sliderR = view.findViewById(R.id.slider_r)
        sliderG = view.findViewById(R.id.slider_g)
        sliderB = view.findViewById(R.id.slider_b)

        valueR = view.findViewById(R.id.value_r)
        valueG = view.findViewById(R.id.value_g)
        valueB = view.findViewById(R.id.value_b)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sliders[sliderR] = valueR
        sliders[sliderG] = valueG
        sliders[sliderB] = valueB

        updateSliders()

        val iterator = sliders.iterator().withIndex()

        while (iterator.hasNext()) {
            val item = iterator.next()
            item.value.key?.let { slider ->
                val textView = sliders[slider]
                textView?.text = slider.value.toSliderValue()

                slider.addOnChangeListener { _, value, fromUser ->
                    textView?.text = value.toSliderValue()

                    if (fromUser) {
                        onPickerUpdate?.invoke(sliderR.value, sliderG.value, sliderB.value)
                    }
                }

                slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                    override fun onStartTrackingTouch(slider: Slider) {}
                    override fun onStopTrackingTouch(slider: Slider) {
                        onResetPalette?.invoke()
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onPickerUpdate = null
        onResetPalette = null
    }

    fun updateColor(color: Int) {
        if (selectedColor != color) {
            selectedColor = color
            updateSliders()
        }
    }

    private fun updateSliders() {
        sliderR.value = Color.red(selectedColor).toFloat()
        sliderG.value = Color.green(selectedColor).toFloat()
        sliderB.value = Color.blue(selectedColor).toFloat()
    }

    companion object {
        @JvmStatic
        fun newInstance(selectedColor: Int) = PickerFragment().apply {
            arguments = bundleOf("selected_color" to selectedColor)
        }
    }
}