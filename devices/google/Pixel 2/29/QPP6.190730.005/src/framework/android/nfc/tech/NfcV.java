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

public final class NfcV
extends BasicTagTechnology {
    public static final String EXTRA_DSFID = "dsfid";
    public static final String EXTRA_RESP_FLAGS = "respflags";
    private byte mDsfId;
    private byte mRespFlags;

    public NfcV(Tag parcelable) throws RemoteException {
        super((Tag)parcelable, 5);
        parcelable = ((Tag)parcelable).getTechExtras(5);
        this.mRespFlags = ((Bundle)parcelable).getByte(EXTRA_RESP_FLAGS);
        this.mDsfId = ((Bundle)parcelable).getByte(EXTRA_DSFID);
    }

    public static NfcV get(Tag object) {
        if (!((Tag)object).hasTech(5)) {
            return null;
        }
        try {
            object = new NfcV((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public byte getDsfId() {
        return this.mDsfId;
    }

    public int getMaxTransceiveLength() {
        return this.getMaxTransceiveLengthInternal();
    }

    public byte getResponseFlags() {
        return this.mRespFlags;
    }

    public byte[] transceive(byte[] arrby) throws IOException {
        return this.transceive(arrby, true);
    }
}

