/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.android.internal.util.FunctionalUtils
 *  com.android.internal.util.FunctionalUtils$ThrowingSupplier
 */
package com.android.internal.telephony;

import android.content.Context;
import com.android.internal.telephony.NetworkScanRequestTracker;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$NetworkScanRequestTracker$kZrc_pK3C_d6BRM_xQpUxJvEcU4
implements FunctionalUtils.ThrowingSupplier {
    private final /* synthetic */ Context f$0;

    public /* synthetic */ _$$Lambda$NetworkScanRequestTracker$kZrc_pK3C_d6BRM_xQpUxJvEcU4(Context context) {
        this.f$0 = context;
    }

    public final Object getOrThrow() {
        return NetworkScanRequestTracker.lambda$getAllowedMccMncsForLocationRestrictedScan$0(this.f$0);
    }
}

