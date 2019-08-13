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

public final class NfcF
extends BasicTagTechnology {
    public static final String EXTRA_PMM = "pmm";
    public static final String EXTRA_SC = "systemcode";
    private static final String TAG = "NFC";
    private byte[] mManufacturer = null;
    private byte[] mSystemCode = null;

    public NfcF(Tag parcelable) throws RemoteException {
        super((Tag)parcelable, 4);
        parcelable = ((Tag)parcelable).getTechExtras(4);
        if (parcelable != null) {
            this.mSystemCode = ((Bundle)parcelable).getByteArray(EXTRA_SC);
            this.mManufacturer = ((Bundle)parcelable).getByteArray(EXTRA_PMM);
        }
    }

    public static NfcF get(Tag object) {
        if (!((Tag)object).hasTech(4)) {
            return null;
        }
        try {
            object = new NfcF((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public byte[] getManufacturer() {
        return this.mManufacturer;
    }

    public int getMaxTransceiveLength() {
        return this.getMaxTransceiveLengthInternal();
    }

    public byte[] getSystemCode() {
        return this.mSystemCode;
    }

    public int getTimeout() {
        try {
            int n = this.mTag.getTagService().getTimeout(4);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return 0;
        }
    }

    public void setTimeout(int n) {
        try {
            if (this.mTag.getTagService().setTimeout(4, n) != 0) {
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

