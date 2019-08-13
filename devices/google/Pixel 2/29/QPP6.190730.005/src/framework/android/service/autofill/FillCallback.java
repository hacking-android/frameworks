/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.RemoteException;
import android.service.autofill.FillResponse;
import android.service.autofill.IFillCallback;
import android.util.Log;

public final class FillCallback {
    private static final String TAG = "FillCallback";
    private final IFillCallback mCallback;
    private boolean mCalled;
    private final int mRequestId;

    public FillCallback(IFillCallback iFillCallback, int n) {
        this.mCallback = iFillCallback;
        this.mRequestId = n;
    }

    private void assertNotCalled() {
        if (!this.mCalled) {
            return;
        }
        throw new IllegalStateException("Already called");
    }

    public void onFailure(CharSequence charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onFailure(): ");
        stringBuilder.append((Object)charSequence);
        Log.w(TAG, stringBuilder.toString());
        this.assertNotCalled();
        this.mCalled = true;
        try {
            this.mCallback.onFailure(this.mRequestId, charSequence);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    public void onSuccess(FillResponse fillResponse) {
        this.assertNotCalled();
        this.mCalled = true;
        if (fillResponse != null) {
            fillResponse.setRequestId(this.mRequestId);
        }
        try {
            this.mCallback.onSuccess(fillResponse);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }
}

