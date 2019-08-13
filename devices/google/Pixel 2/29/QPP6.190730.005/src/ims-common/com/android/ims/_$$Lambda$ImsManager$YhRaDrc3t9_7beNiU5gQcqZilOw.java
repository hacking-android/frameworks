/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.feature.CapabilityChangeRequest
 *  android.telephony.ims.feature.CapabilityChangeRequest$CapabilityPair
 *  com.android.ims.-$
 *  com.android.ims.-$$Lambda
 *  com.android.ims.-$$Lambda$ImsManager
 *  com.android.ims.-$$Lambda$ImsManager$YhRaDrc3t9_7beNiU5gQcqZilOw
 */
package com.android.ims;

import android.telephony.ims.feature.CapabilityChangeRequest;
import com.android.ims.-$;
import com.android.ims.ImsManager;
import java.util.function.Predicate;

public final class _$$Lambda$ImsManager$YhRaDrc3t9_7beNiU5gQcqZilOw
implements Predicate {
    public static final /* synthetic */ -$.Lambda.ImsManager.YhRaDrc3t9_7beNiU5gQcqZilOw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ImsManager$YhRaDrc3t9_7beNiU5gQcqZilOw();
    }

    private /* synthetic */ _$$Lambda$ImsManager$YhRaDrc3t9_7beNiU5gQcqZilOw() {
    }

    public final boolean test(Object object) {
        return ImsManager.lambda$isImsNeeded$3((CapabilityChangeRequest.CapabilityPair)object);
    }
}

