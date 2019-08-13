/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.Tag;
import java.io.Closeable;
import java.io.IOException;

public interface TagTechnology
extends Closeable {
    public static final int ISO_DEP = 3;
    public static final int MIFARE_CLASSIC = 8;
    public static final int MIFARE_ULTRALIGHT = 9;
    public static final int NDEF = 6;
    public static final int NDEF_FORMATABLE = 7;
    public static final int NFC_A = 1;
    public static final int NFC_B = 2;
    public static final int NFC_BARCODE = 10;
    public static final int NFC_F = 4;
    public static final int NFC_V = 5;

    @Override
    public void close() throws IOException;

    public void connect() throws IOException;

    public Tag getTag();

    public boolean isConnected();

    public void reconnect() throws IOException;
}

