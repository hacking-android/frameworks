/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

@SystemApi
public final class RemoteCallback
implements Parcelable {
    public static final Parcelable.Creator<RemoteCallback> CREATOR = new Parcelable.Creator<RemoteCallback>(){

        @Override
        public RemoteCallback createFromParcel(Parcel parcel) {
            return new RemoteCallback(parcel);
        }

        public RemoteCallback[] newArray(int n) {
            return new RemoteCallback[n];
        }
    };
    private final IRemoteCallback mCallback;
    private final Handler mHandler;
    private final OnResultListener mListener;

    RemoteCallback(Parcel parcel) {
        this.mListener = null;
        this.mHandler = null;
        this.mCallback = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
    }

    public RemoteCallback(OnResultListener onResultListener) {
        this(onResultListener, null);
    }

    public RemoteCallback(OnResultListener onResultListener, Handler handler) {
        if (onResultListener != null) {
            this.mListener = onResultListener;
            this.mHandler = handler;
            this.mCallback = new IRemoteCallback.Stub(){

                @Override
                public void sendResult(Bundle bundle) {
                    RemoteCallback.this.sendResult(bundle);
                }
            };
            return;
        }
        throw new NullPointerException("listener cannot be null");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void sendResult(final Bundle bundle) {
        OnResultListener onResultListener = this.mListener;
        if (onResultListener != null) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        RemoteCallback.this.mListener.onResult(bundle);
                    }
                });
            } else {
                onResultListener.onResult(bundle);
            }
        } else {
            try {
                this.mCallback.sendResult(bundle);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mCallback.asBinder());
    }

    public static interface OnResultListener {
        public void onResult(Bundle var1);
    }

}

