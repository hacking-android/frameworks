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

public final class _$$Lambda$ImsResolver$VfY5To_kbbTJevLzywTg__S1JhA
implements Predicate {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$ImsResolver$VfY5To_kbbTJevLzywTg__S1JhA(int n) {
        this.f$0 = n;
    }

    public final boolean test(Object object) {
        return ImsResolver.lambda$calculateFeaturesToCreate$4(this.f$0, (ImsFeatureConfiguration.FeatureSlotPair)object);
    }
}

