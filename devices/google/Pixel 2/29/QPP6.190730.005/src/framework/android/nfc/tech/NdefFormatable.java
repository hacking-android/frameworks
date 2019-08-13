/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.FormatException;
import android.nfc.INfcTag;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.nfc.tech.BasicTagTechnology;
import android.nfc.tech.MifareClassic;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;

public final class NdefFormatable
extends BasicTagTechnology {
    private static final String TAG = "NFC";

    public NdefFormatable(Tag tag) throws RemoteException {
        super(tag, 7);
    }

    public static NdefFormatable get(Tag object) {
        if (!((Tag)object).hasTech(7)) {
            return null;
        }
        try {
            object = new NdefFormatable((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public void format(NdefMessage ndefMessage) throws IOException, FormatException {
        this.format(ndefMessage, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void format(NdefMessage object, boolean bl) throws IOException, FormatException {
        this.checkConnected();
        try {
            int n = this.mTag.getServiceHandle();
            INfcTag iNfcTag = this.mTag.getTagService();
            int n2 = iNfcTag.formatNdef(n, MifareClassic.KEY_DEFAULT);
            if (n2 == -8) {
                object = new FormatException();
                throw object;
            }
            if (n2 == -1) {
                object = new IOException();
                throw object;
            }
            if (n2 != 0) {
                object = new IOException();
                throw object;
            }
            if (!iNfcTag.isNdef(n)) {
                object = new IOException();
                throw object;
            }
            if (object != null) {
                n2 = iNfcTag.ndefWrite(n, (NdefMessage)object);
                if (n2 == -8) {
                    object = new FormatException();
                    throw object;
                }
                if (n2 == -1) {
                    object = new IOException();
                    throw object;
                }
                if (n2 != 0) {
                    object = new IOException();
                    throw object;
                }
            }
            if (!bl) return;
            if ((n = iNfcTag.ndefMakeReadOnly(n)) == -8) {
                object = new IOException();
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

    public void formatReadOnly(NdefMessage ndefMessage) throws IOException, FormatException {
        this.format(ndefMessage, true);
    }
}

