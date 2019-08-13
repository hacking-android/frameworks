/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.INfcTag;
import android.nfc.Tag;
import android.nfc.tech.BasicTagTechnology;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;

public final class MifareUltralight
extends BasicTagTechnology {
    public static final String EXTRA_IS_UL_C = "isulc";
    private static final int MAX_PAGE_COUNT = 256;
    private static final int NXP_MANUFACTURER_ID = 4;
    public static final int PAGE_SIZE = 4;
    private static final String TAG = "NFC";
    public static final int TYPE_ULTRALIGHT = 1;
    public static final int TYPE_ULTRALIGHT_C = 2;
    public static final int TYPE_UNKNOWN = -1;
    private int mType;

    public MifareUltralight(Tag tag) throws RemoteException {
        super(tag, 9);
        NfcA nfcA = NfcA.get(tag);
        this.mType = -1;
        if (nfcA.getSak() == 0 && tag.getId()[0] == 4) {
            this.mType = tag.getTechExtras(9).getBoolean(EXTRA_IS_UL_C) ? 2 : 1;
        }
    }

    public static MifareUltralight get(Tag object) {
        if (!((Tag)object).hasTech(9)) {
            return null;
        }
        try {
            object = new MifareUltralight((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    private static void validatePageIndex(int n) {
        if (n >= 0 && n < 256) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("page out of bounds: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getMaxTransceiveLength() {
        return this.getMaxTransceiveLengthInternal();
    }

    public int getTimeout() {
        try {
            int n = this.mTag.getTagService().getTimeout(9);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return 0;
        }
    }

    public int getType() {
        return this.mType;
    }

    public byte[] readPages(int n) throws IOException {
        MifareUltralight.validatePageIndex(n);
        this.checkConnected();
        return this.transceive(new byte[]{48, (byte)n}, false);
    }

    public void setTimeout(int n) {
        try {
            if (this.mTag.getTagService().setTimeout(9, n) != 0) {
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

    public void writePage(int n, byte[] arrby) throws IOException {
        MifareUltralight.validatePageIndex(n);
        this.checkConnected();
        byte[] arrby2 = new byte[arrby.length + 2];
        arrby2[0] = (byte)-94;
        arrby2[1] = (byte)n;
        System.arraycopy(arrby, 0, arrby2, 2, arrby.length);
        this.transceive(arrby2, false);
    }
}

