package app.resketchware.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class AnimationUtil {

    public static void animateViewHeight(ViewGroup viewGroup, boolean isExpanding, int duration, Animator.AnimatorListener listener) {
        int startHeight = isExpanding ? 0 : viewGroup.getMeasuredHeight();
        int endHeight = isExpanding ? viewGroup.getMeasuredHeight() : 0;
        ValueAnimator heightAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        heightAnimator.addUpdateListener(new HeightAnimatorUpdateListener(viewGroup));
        if (listener != null) {
            heightAnimator.addListener(listener);
        }
        heightAnimator.setDuration(duration);
        heightAnimator.start();
    }

    private static class HeightAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private final ViewGroup viewGroup;

        public HeightAnimatorUpdateListener(ViewGroup viewGroup) {
            this.viewGroup = viewGroup;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int height = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            layoutParams.height = height;
            viewGroup.setLayoutParams(layoutParams);
        }
    }
}