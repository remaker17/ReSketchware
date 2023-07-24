package app.resketchware.ui.dialogs.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    protected Dialog mDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = super.onCreateDialog(savedInstanceState);
        return mDialog;
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }
}