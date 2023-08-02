package app.resketchware.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import app.resketchware.ui.fragments.BlankFragment;
import app.resketchware.ui.fragments.EventFragment;

public class DesignPagerAdapter extends FragmentStateAdapter {

    public DesignPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new EventFragment();
        } else {
            return new BlankFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
