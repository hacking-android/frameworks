/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.FormatException;
import android.nfc.INfcTag;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.BasicTagTechnology;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;

public final class Ndef
extends BasicTagTechnology {
    public static final String EXTRA_NDEF_CARDSTATE = "ndefcardstate";
    public static final String EXTRA_NDEF_MAXLENGTH = "ndefmaxlength";
    public static final String EXTRA_NDEF_MSG = "ndefmsg";
    public static final String EXTRA_NDEF_TYPE = "ndeftype";
    public static final String ICODE_SLI = "com.nxp.ndef.icodesli";
    public static final String MIFARE_CLASSIC = "com.nxp.ndef.mifareclassic";
    public static final int NDEF_MODE_READ_ONLY = 1;
    public static final int NDEF_MODE_READ_WRITE = 2;
    public static final int NDEF_MODE_UNKNOWN = 3;
    public static final String NFC_FORUM_TYPE_1 = "org.nfcforum.ndef.type1";
    public static final String NFC_FORUM_TYPE_2 = "org.nfcforum.ndef.type2";
    public static final String NFC_FORUM_TYPE_3 = "org.nfcforum.ndef.type3";
    public static final String NFC_FORUM_TYPE_4 = "org.nfcforum.ndef.type4";
    private static final String TAG = "NFC";
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;
    public static final int TYPE_ICODE_SLI = 102;
    public static final int TYPE_MIFARE_CLASSIC = 101;
    public static final int TYPE_OTHER = -1;
    public static final String UNKNOWN = "android.ndef.unknown";
    private final int mCardState;
    private final int mMaxNdefSize;
    private final NdefMessage mNdefMsg;
    private final int mNdefType;

    public Ndef(Tag parcelable) throws RemoteException {
        super((Tag)parcelable, 6);
        parcelable = ((Tag)parcelable).getTechExtras(6);
        if (parcelable != null) {
            this.mMaxNdefSize = ((BaseBundle)((Object)parcelable)).getInt(EXTRA_NDEF_MAXLENGTH);
            this.mCardState = ((BaseBundle)((Object)parcelable)).getInt(EXTRA_NDEF_CARDSTATE);
            this.mNdefMsg = (NdefMessage)((Bundle)parcelable).getParcelable(EXTRA_NDEF_MSG);
            this.mNdefType = ((BaseBundle)((Object)parcelable)).getInt(EXTRA_NDEF_TYPE);
            return;
        }
        throw new NullPointerException("NDEF tech extras are null.");
    }

    public static Ndef get(Tag object) {
        if (!((Tag)object).hasTech(6)) {
            return null;
        }
        try {
            object = new Ndef((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public boolean canMakeReadOnly() {
        INfcTag iNfcTag = this.mTag.getTagService();
        if (iNfcTag == null) {
            return false;
        }
        try {
            boolean bl = iNfcTag.canMakeReadOnly(this.mNdefType);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return false;
        }
    }

    public NdefMessage getCachedNdefMessage() {
        return this.mNdefMsg;
    }

    public int getMaxSize() {
        return this.mMaxNdefSize;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public NdefMessage getNdefMessage() throws IOException, FormatException {
        this.checkConnected();
        try {
            Object object = this.mTag.getTagService();
            if (object == null) {
                object = new IOException("Mock tags don't support this operation.");
                throw object;
            }
            int n = this.mTag.getServiceHandle();
            if (object.isNdef(n)) {
                NdefMessage ndefMessage = object.ndefRead(n);
                if (ndefMessage != null) return ndefMessage;
                if (object.isPresent(n)) {
                    return ndefMessage;
                }
                object = new TagLostException();
                throw object;
            }
            if (object.isPresent(n)) {
                return null;
            }
            object = new TagLostException();
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return null;
        }
    }

    public String getType() {
        int n = this.mNdefType;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 101) {
                            if (n != 102) {
                                return UNKNOWN;
                            }
                            return ICODE_SLI;
                        }
                        return MIFARE_CLASSIC;
                    }
                    return NFC_FORUM_TYPE_4;
                }
                return NFC_FORUM_TYPE_3;
            }
            return NFC_FORUM_TYPE_2;
        }
        return NFC_FORUM_TYPE_1;
    }

    public boolean isWritable() {
        boolean bl = this.mCardState == 2;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean makeReadOnly() throws IOException {
        this.checkConnected();
        try {
            Object object = this.mTag.getTagService();
            if (object == null) {
                return false;
            }
            if (!object.isNdef(this.mTag.getServiceHandle())) {
                object = new IOException("Tag is not ndef");
                throw object;
            }
            int n = object.ndefMakeReadOnly(this.mTag.getServiceHandle());
            if (n == -8) {
                return false;
            }
            if (n == -1) {
                object = new IOException();
                throw object;
            }
            if (n == 0) {
                return true;
            }
            object = new IOException();
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeNdefMessage(NdefMessage object) throws IOException, FormatException {
        this.checkConnected();
        try {
            INfcTag iNfcTag = this.mTag.getTagService();
            if (iNfcTag == null) {
                object = new IOException("Mock tags don't support this operation.");
                throw object;
            }
            int n = this.mTag.getServiceHandle();
            if (!iNfcTag.isNdef(n)) {
                object = new IOException("Tag is not ndef");
                throw object;
            }
            if ((n = iNfcTag.ndefWrite(n, (NdefMessage)object)) == -8) {
                object = new FormatException();
                throw object;
            }
            if (n == -1) {
                object = new IOException();
                throw object;
            }
            if (n == 0) {
                return;
            }
            object = new IOException();
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
        }
    }
}

