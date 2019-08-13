/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.stub.ImsFeatureConfiguration
 *  android.telephony.ims.stub.ImsFeatureConfiguration$FeatureSlotPair
 *  com.android.internal.telephony.ims.-$
 *  com.android.internal.telephony.ims.-$$Lambda
 *  com.android.internal.telephony.ims.-$$Lambda$ImsResolver
 *  com.android.internal.telephony.ims.-$$Lambda$ImsResolver$SIkPixr-qGLIK-usUJIKu6S5BBs
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.stub.ImsFeatureConfiguration;
import com.android.internal.telephony.ims.-$;
import com.android.internal.telephony.ims.ImsResolver;
import java.util.function.Predicate;

public final class _$$Lambda$ImsResolver$SIkPixr_qGLIK_usUJIKu6S5BBs
implements Predicate {
    public static final /* synthetic */ -$.Lambda.ImsResolver.SIkPixr-qGLIK-usUJIKu6S5BBs INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ImsResolver$SIkPixr_qGLIK_usUJIKu6S5BBs();
    }

    private /* synthetic */ _$$Lambda$ImsResolver$SIkPixr_qGLIK_usUJIKu6S5BBs() {
    }

    public final boolean test(Object object) {
        return ImsResolver.lambda$shouldFeaturesCauseBind$6((ImsFeatureConfiguration.FeatureSlotPair)object);
    }
}

