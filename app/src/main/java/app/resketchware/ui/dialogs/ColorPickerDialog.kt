package app.resketchware.ui.dialogs

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import app.resketchware.R
import app.resketchware.ui.fragments.PalettesFragment
import app.resketchware.ui.fragments.PickerFragment
import app.resketchware.ui.extensions.toHex
import app.resketchware.ui.extensions.toSurfaceColor

class ColorPickerDialog : BottomSheetDialogFragment() {
  private var palettesFragment: PalettesFragment? = null
  private var pickerFragment: PickerFragment? = null

  private lateinit var tabLayout: TabLayout
  private lateinit var hexValue: TextView
  private lateinit var title: TextView
  private lateinit var colorPreview: MaterialCardView
  private lateinit var viewPager: ViewPager2
  private lateinit var resetButton: View

  private var backupColor = -1
  private var selectedColor = -1
  var onPositiveClick: ((Int) -> Unit)? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.getInt("selected_color")?.let { color ->
      backupColor = color
      selectedColor = color
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.dialog_color_picker, container, false)

    colorPreview = view.findViewById(R.id.color_preview)
    hexValue = view.findViewById(R.id.hex_value)
    tabLayout = view.findViewById(R.id.tab_layout)
    title = view.findViewById(R.id.title)
    viewPager = view.findViewById(R.id.pager)

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val cancelButton = view.findViewById<View>(R.id.cancel)
    val positiveButton = view.findViewById<View>(R.id.ok)
    resetButton = view.findViewById(R.id.reset)

    cancelButton.setOnClickListener { dismissAllowingStateLoss() }
    positiveButton.setOnClickListener {
      onPositiveClick?.invoke(selectedColor)
      dismissAllowingStateLoss()
    }
    resetButton.setOnClickListener {
      if (backupColor != selectedColor) {
        it.visibility = View.GONE
        selectedColor = backupColor
        updateColors(selectedColor)
        palettesFragment?.resetPalette()
      }
    }

    val adapter = ColorPickerPagerAdapter(requireActivity())
    viewPager.adapter = adapter
    viewPager.offscreenPageLimit = 2

    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
      val tabIcons = arrayOf(R.drawable.rsw_colorize, R.drawable.rsw_palette)
      tab.setIcon(tabIcons[position])
    }.attach()

    updateColors(selectedColor)
    disableOverScroll()
  }

  override fun onDestroy() {
    super.onDestroy()
    pickerFragment?.let {
      it.onPickerUpdate = null
      it.onResetPalette = null
    }
    palettesFragment?.let {
      it.onColorSelect = null
    }
  }

  private fun handleOnNavigationItemSelected(itemId: Int) = when (itemId) {
    0 -> pickerFragment ?: initFragmentAt(itemId)
    else -> palettesFragment ?: initFragmentAt(itemId)
  }

  private fun initFragmentAt(itemId: Int): Fragment {
    when (itemId) {
      0 -> pickerFragment = PickerFragment.newInstance(selectedColor).apply {
        onPickerUpdate = { r, g, b ->
          val color = Color.rgb(r.toInt(), g.toInt(), b.toInt())
          if (selectedColor != color) {
            updateColors(color)
            selectedColor = color
          }
        }
        onResetPalette = {
          palettesFragment?.resetPalette()
        }
      }
      else -> palettesFragment = PalettesFragment.newInstance().apply {
        onColorSelect = { color ->
          if (selectedColor != color) {
            updateColors(color)
            selectedColor = color
          }
        }
      }
    }
    return handleOnNavigationItemSelected(itemId)
  }

  private fun updateColors(color: Int) {
    val contrastColor = color.toSurfaceColor()

    colorPreview.setCardBackgroundColor(color)

    tabLayout.setSelectedTabIndicatorColor(contrastColor)
    tabLayout.tabIconTint = ColorStateList.valueOf(contrastColor)
    tabLayout.tabRippleColor = ColorStateList.valueOf(
      ColorUtils.setAlphaComponent(contrastColor, 25)
    )

    hexValue.text = color.toHex()
    hexValue.setTextColor(contrastColor)

    title.setTextColor(contrastColor)

    if (resetButton.isGone) {
      resetButton.visibility = View.VISIBLE
    }

    pickerFragment?.updateColor(color)
  }

  private fun disableOverScroll() {
    val view = viewPager.getChildAt(0)
    if (view is RecyclerView) {
      view.overScrollMode = View.OVER_SCROLL_NEVER
    }
  }

  private inner class ColorPickerPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment = handleOnNavigationItemSelected(position)
    override fun getItemCount(): Int = 2
  }

  companion object {
    @JvmStatic
    fun newInstance(selectedColor: Int) = ColorPickerDialog().apply {
      arguments = bundleOf("selected_color" to selectedColor)
    }
  }
}
