package app.resketchware.ui.widgets;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.resketchware.R;

public class ThemeColorView extends LinearLayout {
  public final View colorView;
  public final TextView nameTextView;

  public ThemeColorView(Context context, int tag) {
    super(context);

    LayoutInflater inflater = LayoutInflater.from(context);
    inflater.inflate(R.layout.view_theme_color, this, true);

    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

    setBackgroundResource(outValue.resourceId);
    setGravity(Gravity.CENTER);
    setOrientation(LinearLayout.VERTICAL);
    setTag(tag);

    colorView = findViewById(R.id.color_view);
    nameTextView = findViewById(R.id.name_text);
  }
}
