/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.content.IntentSender;
import android.os.RemoteException;
import android.service.autofill.ISaveCallback;
import android.util.Log;
import com.android.internal.util.Preconditions;

public final class SaveCallback {
    private static final String TAG = "SaveCallback";
    private final ISaveCallback mCallback;
    private boolean mCalled;

    SaveCallback(ISaveCallback iSaveCallback) {
        this.mCallback = iSaveCallback;
    }

    private void assertNotCalled() {
        if (!this.mCalled) {
            return;
        }
        throw new IllegalStateException("Already called");
    }

    private void onSuccessInternal(IntentSender intentSender) {
        this.assertNotCalled();
        this.mCalled = true;
        try {
            this.mCallback.onSuccess(intentSender);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    public void onFailure(CharSequence charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onFailure(): ");
        stringBuilder.append((Object)charSequence);
        Log.w(TAG, stringBuilder.toString());
        this.assertNotCalled();
        this.mCalled = true;
        try {
            this.mCallback.onFailure(charSequence);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    public void onSuccess() {
        this.onSuccessInternal(null);
    }

    public void onSuccess(IntentSender intentSender) {
        this.onSuccessInternal(Preconditions.checkNotNull(intentSender));
    }
}

