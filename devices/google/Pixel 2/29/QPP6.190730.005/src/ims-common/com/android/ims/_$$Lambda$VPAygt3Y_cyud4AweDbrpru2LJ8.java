/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.ims.-$
 *  com.android.ims.-$$Lambda
 *  com.android.ims.-$$Lambda$VPAygt3Y-cyud4AweDbrpru2LJ8
 */
package com.android.ims;

import com.android.ims.-$;
import com.android.ims.MmTelFeatureConnection;
import java.util.function.Consumer;

public final class _$$Lambda$VPAygt3Y_cyud4AweDbrpru2LJ8
implements Consumer {
    public static final /* synthetic */ -$.Lambda.VPAygt3Y-cyud4AweDbrpru2LJ8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$VPAygt3Y_cyud4AweDbrpru2LJ8();
    }

    private /* synthetic */ _$$Lambda$VPAygt3Y_cyud4AweDbrpru2LJ8() {
    }

    public final void accept(Object object) {
        ((MmTelFeatureConnection.IFeatureUpdate)object).notifyUnavailable();
    }
}

