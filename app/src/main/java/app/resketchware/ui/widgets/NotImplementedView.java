package app.resketchware.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.resketchware.R;

public class NotImplementedView extends LinearLayout {
    private final int titleTextAppearance;
    private final int messageTextAppearance;

    private final TextView titleTextView;
    private final TextView messageTextView;

    public NotImplementedView(@NonNull Context context) {
        this(context, null);
    }

    public NotImplementedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.notImplementedViewStyle);
    }

    public NotImplementedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.not_implemented_view, this, true);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);

        titleTextView = findViewById(R.id.title);
        messageTextView = findViewById(R.id.message);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NotImplementedView, defStyleAttr, R.style.Widget_RSW_NotImplementedView);
        titleTextAppearance = a.getResourceId(R.styleable.NotImplementedView_titleTextAppearance, -1);
        messageTextAppearance = a.getResourceId(R.styleable.NotImplementedView_messageTextAppearance, -1);
        a.recycle();

        if (titleTextAppearance != -1) {
            titleTextView.setTextAppearance(titleTextAppearance);
        }
        if (messageTextAppearance != -1) {
            messageTextView.setTextAppearance(messageTextAppearance);
        }

        setDefaultText();
    }

    private void setDefaultText() {
        String message = getContext().getString(R.string.not_implemented_message);
        SpannableString spannableString = new SpannableString(message);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/TheRemakerMan/ReSketchware");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        };

        int startIndex = message.indexOf("GitHub");
        int endIndex = startIndex + "GitHub".length();
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        messageTextView.setText(spannableString);
        messageTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}