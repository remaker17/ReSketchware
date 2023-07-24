package app.resketchware.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DesignViewModel extends ViewModel {

    private final MutableLiveData<Integer> mTabPosition = new MutableLiveData<>(0);

    public void setTabPosition(int position) {
        mTabPosition.setValue(position);
    }

    public MutableLiveData<Integer> getTabPosition() {
        return mTabPosition;
    }
}