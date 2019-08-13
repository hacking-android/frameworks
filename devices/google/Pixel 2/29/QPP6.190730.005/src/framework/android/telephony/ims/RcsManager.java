/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.content.Context;
import android.telephony.ims.RcsMessageStore;

public class RcsManager {
    private final RcsMessageStore mRcsMessageStore;

    public RcsManager(Context context) {
        this.mRcsMessageStore = new RcsMessageStore(context);
    }

    public RcsMessageStore getRcsMessageStore() {
        return this.mRcsMessageStore;
    }
}

