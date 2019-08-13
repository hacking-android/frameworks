/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.os.IResultReceiver;

public class ResultReceiver
implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator<ResultReceiver>(){

        @Override
        public ResultReceiver createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        public ResultReceiver[] newArray(int n) {
            return new ResultReceiver[n];
        }
    };
    final Handler mHandler;
    final boolean mLocal;
    IResultReceiver mReceiver;

    public ResultReceiver(Handler handler) {
        this.mLocal = true;
        this.mHandler = handler;
    }

    ResultReceiver(Parcel parcel) {
        this.mLocal = false;
        this.mHandler = null;
        this.mReceiver = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void onReceiveResult(int n, Bundle bundle) {
    }

    public void send(int n, Bundle bundle) {
        if (this.mLocal) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new MyRunnable(n, bundle));
            } else {
                this.onReceiveResult(n, bundle);
            }
            return;
        }
        IResultReceiver iResultReceiver = this.mReceiver;
        if (iResultReceiver != null) {
            try {
                iResultReceiver.send(n, bundle);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        synchronized (this) {
            if (this.mReceiver == null) {
                MyResultReceiver myResultReceiver = new MyResultReceiver();
                this.mReceiver = myResultReceiver;
            }
            parcel.writeStrongBinder(this.mReceiver.asBinder());
            return;
        }
    }

    class MyResultReceiver
    extends IResultReceiver.Stub {
        MyResultReceiver() {
        }

        @Override
        public void send(int n, Bundle bundle) {
            if (ResultReceiver.this.mHandler != null) {
                ResultReceiver.this.mHandler.post(new MyRunnable(n, bundle));
            } else {
                ResultReceiver.this.onReceiveResult(n, bundle);
            }
        }
    }

    class MyRunnable
    implements Runnable {
        final int mResultCode;
        final Bundle mResultData;

        MyRunnable(int n, Bundle bundle) {
            this.mResultCode = n;
            this.mResultData = bundle;
        }

        @Override
        public void run() {
            ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

}

