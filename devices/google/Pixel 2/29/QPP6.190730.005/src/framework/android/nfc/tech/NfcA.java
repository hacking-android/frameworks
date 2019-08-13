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

public final class NfcA
extends BasicTagTechnology {
    public static final String EXTRA_ATQA = "atqa";
    public static final String EXTRA_SAK = "sak";
    private static final String TAG = "NFC";
    private byte[] mAtqa;
    private short mSak;

    public NfcA(Tag parcelable) throws RemoteException {
        super((Tag)parcelable, 1);
        parcelable = ((Tag)parcelable).getTechExtras(1);
        this.mSak = ((Bundle)parcelable).getShort(EXTRA_SAK);
        this.mAtqa = ((Bundle)parcelable).getByteArray(EXTRA_ATQA);
    }

    public static NfcA get(Tag object) {
        if (!((Tag)object).hasTech(1)) {
            return null;
        }
        try {
            object = new NfcA((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public byte[] getAtqa() {
        return this.mAtqa;
    }

    public int getMaxTransceiveLength() {
        return this.getMaxTransceiveLengthInternal();
    }

    public short getSak() {
        return this.mSak;
    }

    public int getTimeout() {
        try {
            int n = this.mTag.getTagService().getTimeout(1);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return 0;
        }
    }

    public void setTimeout(int n) {
        try {
            if (this.mTag.getTagService().setTimeout(1, n) != 0) {
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

