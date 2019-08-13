/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.android.internal.telephony.ims.-$
 *  com.android.internal.telephony.ims.-$$Lambda
 *  com.android.internal.telephony.ims.-$$Lambda$WamP7BPq0j01TgYE3GvUqU3b-rs
 */
package com.android.internal.telephony.ims;

import android.content.Context;
import com.android.internal.telephony.ims.-$;
import com.android.internal.telephony.ims.ImsResolver;
import com.android.internal.telephony.ims.ImsServiceFeatureQueryManager;

public final class _$$Lambda$WamP7BPq0j01TgYE3GvUqU3b_rs
implements ImsResolver.ImsDynamicQueryManagerFactory {
    public static final /* synthetic */ -$.Lambda.WamP7BPq0j01TgYE3GvUqU3b-rs INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$WamP7BPq0j01TgYE3GvUqU3b_rs();
    }

    private /* synthetic */ _$$Lambda$WamP7BPq0j01TgYE3GvUqU3b_rs() {
    }

    @Override
    public final ImsServiceFeatureQueryManager create(Context context, ImsServiceFeatureQueryManager.Listener listener) {
        return new ImsServiceFeatureQueryManager(context, listener);
    }
}

