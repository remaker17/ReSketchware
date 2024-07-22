package app.resketchware.ui.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.ColorInt;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import app.resketchware.R;
import app.resketchware.utils.ContextUtil;

public class SearchBar extends Toolbar {

  private static final int DEF_STYLE_RES = R.style.Widget_RSW_SearchBar;

  private final TextView textView;
  private final boolean layoutInflated;
  private final Drawable defaultNavigationIcon;
  private final boolean tintNavigationIcon;
  @Nullable private Integer navigationIconTint;
  @Nullable private Drawable originalNavigationIconBackground;
  private int menuResId = -1;
  private MaterialShapeDrawable backgroundShape;

  public SearchBar(@NonNull Context context) {
    this(context, null);
  }

  public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, R.attr.searchBarStyle);
  }

  public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    defaultNavigationIcon =
      AppCompatResources.getDrawable(context, getDefaultNavigationIconResource());

    TypedArray a =
      context.obtainStyledAttributes(attrs, R.styleable.SearchBar, defStyleAttr, DEF_STYLE_RES);

    ShapeAppearanceModel shapeAppearanceModel =
      ShapeAppearanceModel.builder(context, attrs, defStyleAttr, DEF_STYLE_RES).build();
    int backgroundColor = a.getColor(R.styleable.SearchBar_backgroundTint, 0);
    boolean hideNavigationIcon = a.getBoolean(R.styleable.SearchBar_hideNavigationIcon, false);
    tintNavigationIcon = a.getBoolean(R.styleable.SearchBar_tintNavigationIcon, true);
    if (a.hasValue(R.styleable.SearchBar_navigationIconTint)) {
      navigationIconTint = a.getColor(R.styleable.SearchBar_navigationIconTint, -1);
    }
    int textAppearanceResId = a.getResourceId(R.styleable.SearchBar_android_textAppearance, -1);
    String text = a.getString(R.styleable.SearchBar_android_text);
    String hint = a.getString(R.styleable.SearchBar_android_hint);

    a.recycle();

    if (!hideNavigationIcon) {
      initNavigationIcon();
    }
    setClickable(true);
    setFocusable(true);

    LayoutInflater.from(context).inflate(R.layout.layout_search_bar, this);
    layoutInflated = true;

    textView = findViewById(R.id.search_bar_text_view);

    initTextView(textAppearanceResId, text, hint);
    initBackground(shapeAppearanceModel, backgroundColor);
  }

  @Override
  public void setElevation(float elevation) {}

  @Override
  public void setNavigationIcon(@Nullable Drawable navigationIcon) {
    super.setNavigationIcon(maybeTintNavigationIcon(navigationIcon));
  }

  @Nullable
  private Drawable maybeTintNavigationIcon(@Nullable Drawable navigationIcon) {
    if (!tintNavigationIcon || navigationIcon == null) {
      return navigationIcon;
    }

    int navigationIconColor;
    if (navigationIconTint != null) {
      navigationIconColor = navigationIconTint;
    } else {
      int navigationIconColorAttr =
        navigationIcon == defaultNavigationIcon
          ? com.google.android.material.R.attr.colorControlNormal
          : com.google.android.material.R.attr.colorOnSurface;
      navigationIconColor = ContextUtil.getThemeColor(getContext(), navigationIconColorAttr);
    }

    Drawable wrappedNavigationIcon = DrawableCompat.wrap(navigationIcon.mutate());
    DrawableCompat.setTint(wrappedNavigationIcon, navigationIconColor);
    return wrappedNavigationIcon;
  }

  private void setNavigationIconDecorative(boolean decorative) {
    ImageButton navigationIconButton = getNavigationIconButton();
    if (navigationIconButton == null) {
      return;
    }

    navigationIconButton.setClickable(!decorative);
    navigationIconButton.setFocusable(!decorative);

    Drawable navigationIconBackground = navigationIconButton.getBackground();
    if (navigationIconBackground != null) {
      originalNavigationIconBackground = navigationIconBackground;
    }

    navigationIconButton.setBackgroundDrawable(
        decorative ? null : originalNavigationIconBackground);
  }

  @Nullable
  private ImageButton getNavigationIconButton() {
    Drawable navigationIcon = getNavigationIcon();
    if (navigationIcon == null) {
      return null;
    }
    for (int i = 0; i < getChildCount(); i++) {
      View child = getChildAt(i);
      if (child instanceof ImageButton ib) {
        if (ib.getDrawable()  == navigationIcon) {
          return ib;
        }
      }
    }
    return null;
  }

  @Override
  public void inflateMenu(@MenuRes int resId) {
    Menu menu = getMenu();
    if (menu instanceof MenuBuilder menuBuilder) {
      menuBuilder.stopDispatchingItemsChanged();
    }
    super.inflateMenu(resId);
    this.menuResId = resId;
    if (menu instanceof MenuBuilder menuBuilder) {
      menuBuilder.startDispatchingItemsChanged();
    }
  }

  @Override
  public void setTitle(CharSequence title) {}

  @Override
  public void setSubtitle(CharSequence subtitle) {}

  private void initNavigationIcon() {
    setNavigationIcon(getNavigationIcon() == null ? defaultNavigationIcon : getNavigationIcon());
    setNavigationIconDecorative(true);
  }

  private void initTextView(@StyleRes int textAppearanceResId, String text, String hint) {
    if (textAppearanceResId != -1) {
      TextViewCompat.setTextAppearance(textView, textAppearanceResId);
    }
    setText(text);
    setHint(hint);
    if (getNavigationIcon() == null) {
      MarginLayoutParamsCompat.setMarginStart(
        (MarginLayoutParams) textView.getLayoutParams(),
        getResources().getDimensionPixelSize(R.dimen.rsw_searchbar_text_margin_start_no_navigation_icon));
    }
  }

  private void initBackground(ShapeAppearanceModel shapeAppearance, @ColorInt int backgroundColor) {
    backgroundShape = new MaterialShapeDrawable(shapeAppearance);
    backgroundShape.initializeElevationOverlay(getContext());
    backgroundShape.setFillColor(ColorStateList.valueOf(backgroundColor));

    int rippleColor = ContextUtil.getThemeColor(getContext(), com.google.android.material.R.attr.colorControlHighlight);
    RippleDrawable background = new RippleDrawable(ColorStateList.valueOf(rippleColor), backgroundShape, backgroundShape);

    ViewCompat.setBackground(this, background);
  }

  @DrawableRes
  protected int getDefaultNavigationIconResource() {
    return R.drawable.rsw_search;
  }

  @NonNull
  public TextView getTextView() {
    return textView;
  }

  @NonNull
  public CharSequence getText() {
    return textView.getText();
  }

  public void setText(@Nullable CharSequence text) {
    textView.setText(text);
  }

  public void setText(@Nullable int textResId) {
    textView.setText(textResId);
  }

  public void clearText() {
    textView.setText("");
  }

  @Nullable
  public CharSequence getHint() {
    return textView.getHint();
  }

  public void setHint(@Nullable CharSequence hint) {
    textView.setHint(hint);
  }

  public void setHint(@Nullable int hintResId) {
    textView.setHint(hintResId);
  }

  @Override
  @NonNull
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    CharSequence text = getText();
    savedState.text = text == null ? null : text.toString();
    return savedState;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    if (!(state instanceof SavedState)) {
      super.onRestoreInstanceState(state);
      return;
    }
    SavedState savedState = (SavedState) state;
    super.onRestoreInstanceState(savedState.getSuperState());
    setText(savedState.text);
  }

  static class SavedState extends AbsSavedState {

    String text;

    public SavedState(Parcel source) {
      this(source, null);
    }

    public SavedState(Parcel source, @Nullable ClassLoader classLoader) {
      super(source, classLoader);
      text = source.readString();
    }

    public SavedState(Parcelable superState) {
      super(superState);
    }

    public static final Parcelable.Creator<SavedState> CREATOR =
        new ClassLoaderCreator<SavedState>() {

      @Override
      public SavedState createFromParcel(Parcel source, ClassLoader loader) {
        return new SavedState(source, loader);
      }

      @Override
      public SavedState createFromParcel(Parcel source) {
        return new SavedState(source);
      }

      @Override
      public SavedState[] newArray(int size) {
        return new SavedState[size];
      }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      super.writeToParcel(dest, flags);
      dest.writeString(text);
    }
  }
}
