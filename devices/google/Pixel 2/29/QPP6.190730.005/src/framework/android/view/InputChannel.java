/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public final class InputChannel
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<InputChannel> CREATOR = new Parcelable.Creator<InputChannel>(){

        @Override
        public InputChannel createFromParcel(Parcel parcel) {
            InputChannel inputChannel = new InputChannel();
            inputChannel.readFromParcel(parcel);
            return inputChannel;
        }

        public InputChannel[] newArray(int n) {
            return new InputChannel[n];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "InputChannel";
    @UnsupportedAppUsage
    private long mPtr;

    private native void nativeDispose(boolean var1);

    private native void nativeDup(InputChannel var1);

    private native String nativeGetName();

    private native IBinder nativeGetToken();

    private static native InputChannel[] nativeOpenInputChannelPair(String var0);

    private native void nativeReadFromParcel(Parcel var1);

    private native void nativeSetToken(IBinder var1);

    private native void nativeTransferTo(InputChannel var1);

    private native void nativeWriteToParcel(Parcel var1);

    public static InputChannel[] openInputChannelPair(String string2) {
        if (string2 != null) {
            return InputChannel.nativeOpenInputChannelPair(string2);
        }
        throw new IllegalArgumentException("name must not be null");
    }

    @Override
    public int describeContents() {
        return 1;
    }

    public void dispose() {
        this.nativeDispose(false);
    }

    public InputChannel dup() {
        InputChannel inputChannel = new InputChannel();
        this.nativeDup(inputChannel);
        return inputChannel;
    }

    protected void finalize() throws Throwable {
        try {
            this.nativeDispose(true);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public String getName() {
        String string2 = this.nativeGetName();
        if (string2 == null) {
            string2 = "uninitialized";
        }
        return string2;
    }

    public IBinder getToken() {
        return this.nativeGetToken();
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel != null) {
            this.nativeReadFromParcel(parcel);
            return;
        }
        throw new IllegalArgumentException("in must not be null");
    }

    public void setToken(IBinder iBinder) {
        this.nativeSetToken(iBinder);
    }

    public String toString() {
        return this.getName();
    }

    public void transferTo(InputChannel inputChannel) {
        if (inputChannel != null) {
            this.nativeTransferTo(inputChannel);
            return;
        }
        throw new IllegalArgumentException("outParameter must not be null");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (parcel != null) {
            this.nativeWriteToParcel(parcel);
            if ((n & 1) != 0) {
                this.dispose();
            }
            return;
        }
        throw new IllegalArgumentException("out must not be null");
    }

}

