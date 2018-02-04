package org.androidluckyguys.architecture.data.util;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

/**
 * Created by Lakeba
 */

public class SnackbarMessage extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, final SnackbarObserver observer) {
        super.observe(owner, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String t) {
                if (t == null) {
                    return;
                }
                observer.onNewMessage(t);
            }
        });
    }

    public interface SnackbarObserver {
        /**
         * Called when there is a new message to be shown.
         * @param snackbarMessage The new message, non-null.
         */
        //void onNewMessage(@StringRes int snackbarMessageResourceId);
        void onNewMessage(String snackbarMessage);
    }

}