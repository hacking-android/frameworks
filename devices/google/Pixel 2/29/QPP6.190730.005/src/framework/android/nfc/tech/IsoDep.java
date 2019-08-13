/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.INfcTag;
import android.nfc.Tag;
import android.nfc.tech.BasicTagTechnology;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;

public final class IsoDep
extends BasicTagTechnology {
    public static final String EXTRA_HIST_BYTES = "histbytes";
    public static final String EXTRA_HI_LAYER_RESP = "hiresp";
    private static final String TAG = "NFC";
    private byte[] mHiLayerResponse = null;
    private byte[] mHistBytes = null;

    public IsoDep(Tag parcelable) throws RemoteException {
        super((Tag)parcelable, 3);
        parcelable = ((Tag)parcelable).getTechExtras(3);
        if (parcelable != null) {
            this.mHiLayerResponse = ((Bundle)parcelable).getByteArray(EXTRA_HI_LAYER_RESP);
            this.mHistBytes = ((Bundle)parcelable).getByteArray(EXTRA_HIST_BYTES);
        }
    }

    public static IsoDep get(Tag object) {
        if (!((Tag)object).hasTech(3)) {
            return null;
        }
        try {
            object = new IsoDep((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public byte[] getHiLayerResponse() {
        return this.mHiLayerResponse;
    }

    public byte[] getHistoricalBytes() {
        return this.mHistBytes;
    }

    public int getMaxTransceiveLength() {
        return this.getMaxTransceiveLengthInternal();
    }

    public int getTimeout() {
        try {
            int n = this.mTag.getTagService().getTimeout(3);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return 0;
        }
    }

    public boolean isExtendedLengthApduSupported() {
        try {
            boolean bl = this.mTag.getTagService().getExtendedLengthApdusSupported();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return false;
        }
    }

    public void setTimeout(int n) {
        try {
            if (this.mTag.getTagService().setTimeout(3, n) != 0) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("The supplied timeout is not valid");
                throw illegalArgumentException;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
        }
    }

    public byte[] transceive(byte[] arrby) throws IOException {
        return this.transceive(arrby, true);
    }
}

