package app.resketchware.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.resketchware.ui.fragments.BlankFragment;

public class DesignPagerAdapter extends FragmentStatePagerAdapter {

    public DesignPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return new BlankFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position + 1);
    }
}