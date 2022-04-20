package com.training.project.ui.dialog;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Static library support version of the framework's {@link android.app.DialogFragment}.
 * Used to write apps that run on platforms prior to Android 3.0.  When running
 * on Android 3.0 or above, this implementation is still used; it does not try
 * to switch to the framework's implementation.  See the framework SDK
 * documentation for a class overview.
 */
public class BaseDialogFragment extends DialogFragment {


    public BaseDialogFragment() {
        super();
    }

    public BaseDialogFragment(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnCancelListener(null);
        dialog.setOnDismissListener(null);
        return dialog;
    }
}