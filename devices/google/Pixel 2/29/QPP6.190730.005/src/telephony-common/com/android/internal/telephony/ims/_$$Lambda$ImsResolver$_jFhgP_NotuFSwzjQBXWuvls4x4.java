/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.stub.ImsFeatureConfiguration
 *  android.telephony.ims.stub.ImsFeatureConfiguration$FeatureSlotPair
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.stub.ImsFeatureConfiguration;
import com.android.internal.telephony.ims.ImsResolver;
import java.util.function.Predicate;

public final class _$$Lambda$ImsResolver$_jFhgP_NotuFSwzjQBXWuvls4x4
implements Predicate {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$ImsResolver$_jFhgP_NotuFSwzjQBXWuvls4x4(int n) {
        this.f$0 = n;
    }

    public final boolean test(Object object) {
        return ImsResolver.lambda$calculateFeaturesToCreate$3(this.f$0, (ImsFeatureConfiguration.FeatureSlotPair)object);
    }
}

