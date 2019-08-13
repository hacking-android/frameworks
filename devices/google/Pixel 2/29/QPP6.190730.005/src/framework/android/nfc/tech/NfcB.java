/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.Tag;
import android.nfc.tech.BasicTagTechnology;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.IOException;

public final class NfcB
extends BasicTagTechnology {
    public static final String EXTRA_APPDATA = "appdata";
    public static final String EXTRA_PROTINFO = "protinfo";
    private byte[] mAppData;
    private byte[] mProtInfo;

    public NfcB(Tag parcelable) throws RemoteException {
        super((Tag)parcelable, 2);
        parcelable = ((Tag)parcelable).getTechExtras(2);
        this.mAppData = ((Bundle)parcelable).getByteArray(EXTRA_APPDATA);
        this.mProtInfo = ((Bundle)parcelable).getByteArray(EXTRA_PROTINFO);
    }

    public static NfcB get(Tag object) {
        if (!((Tag)object).hasTech(2)) {
            return null;
        }
        try {
            object = new NfcB((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public byte[] getApplicationData() {
        return this.mAppData;
    }

    public int getMaxTransceiveLength() {
        return this.getMaxTransceiveLengthInternal();
    }

    public byte[] getProtocolInfo() {
        return this.mProtInfo;
    }

    public byte[] transceive(byte[] arrby) throws IOException {
        return this.transceive(arrby, true);
    }
}

