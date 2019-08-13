/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.nfc.NfcAdapter;

public final class NfcEvent {
    public final NfcAdapter nfcAdapter;
    public final int peerLlcpMajorVersion;
    public final int peerLlcpMinorVersion;

    NfcEvent(NfcAdapter nfcAdapter, byte by) {
        this.nfcAdapter = nfcAdapter;
        this.peerLlcpMajorVersion = (by & 240) >> 4;
        this.peerLlcpMinorVersion = by & 15;
    }
}

