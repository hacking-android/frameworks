/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.stub.ImsFeatureConfiguration
 *  android.telephony.ims.stub.ImsFeatureConfiguration$FeatureSlotPair
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.stub.ImsFeatureConfiguration;
import com.android.internal.telephony.ims.ImsServiceController;
import java.util.function.Predicate;

public final class _$$Lambda$ImsServiceController$8NvoVXkZRS5LCradATGpNMBXAqg
implements Predicate {
    private final /* synthetic */ ImsFeatureConfiguration.FeatureSlotPair f$0;

    public /* synthetic */ _$$Lambda$ImsServiceController$8NvoVXkZRS5LCradATGpNMBXAqg(ImsFeatureConfiguration.FeatureSlotPair featureSlotPair) {
        this.f$0 = featureSlotPair;
    }

    public final boolean test(Object object) {
        return ImsServiceController.lambda$removeImsServiceFeature$0(this.f$0, (ImsServiceController.ImsFeatureStatusCallback)object);
    }
}

