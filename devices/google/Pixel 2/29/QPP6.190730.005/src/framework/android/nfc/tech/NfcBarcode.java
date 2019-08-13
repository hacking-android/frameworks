/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.Tag;
import android.nfc.tech.BasicTagTechnology;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.IOException;

public final class NfcBarcode
extends BasicTagTechnology {
    public static final String EXTRA_BARCODE_TYPE = "barcodetype";
    public static final int TYPE_KOVIO = 1;
    public static final int TYPE_UNKNOWN = -1;
    private int mType;

    public NfcBarcode(Tag parcelable) throws RemoteException {
        super((Tag)parcelable, 10);
        parcelable = ((Tag)parcelable).getTechExtras(10);
        if (parcelable != null) {
            this.mType = ((BaseBundle)((Object)parcelable)).getInt(EXTRA_BARCODE_TYPE);
            return;
        }
        throw new NullPointerException("NfcBarcode tech extras are null.");
    }

    public static NfcBarcode get(Tag object) {
        if (!((Tag)object).hasTech(10)) {
            return null;
        }
        try {
            object = new NfcBarcode((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public byte[] getBarcode() {
        if (this.mType != 1) {
            return null;
        }
        return this.mTag.getId();
    }

    public int getType() {
        return this.mType;
    }
}

