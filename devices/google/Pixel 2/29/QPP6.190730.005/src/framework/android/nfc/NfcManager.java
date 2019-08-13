/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.nfc.NfcAdapter;

public final class NfcManager {
    private final NfcAdapter mAdapter;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public NfcManager(Context object) {
        object = ((Context)object).getApplicationContext();
        if (object != null) {
            try {
                object = NfcAdapter.getNfcAdapter((Context)object);
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                object = null;
            }
            this.mAdapter = object;
            return;
        }
        throw new IllegalArgumentException("context not associated with any application (using a mock context?)");
    }

    public NfcAdapter getDefaultAdapter() {
        return this.mAdapter;
    }
}

