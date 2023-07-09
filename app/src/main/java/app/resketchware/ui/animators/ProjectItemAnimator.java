package app.resketchware.ui.animators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectItemAnimator extends DefaultItemAnimator {

    private static final int ANIMATION_DURATION_MS = 320;
    private static final float START_SCALE = 1.15f;
    private static final float END_SCALE = 1f;

    private boolean isAnimated = false;

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        if (isAnimated) {
            return super.animateAdd(holder);
        }

        View itemView = holder.itemView;
        ValueAnimator animator = ValueAnimator.ofFloat(START_SCALE, END_SCALE);
        animator.setDuration(ANIMATION_DURATION_MS);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            itemView.setScaleY(value);
            itemView.setScaleX(value);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                dispatchAddStarting(holder);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchAddFinished(holder);
            }
        });
        animator.start();
        return true;
    }
}