/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.ims.-$
 *  com.android.ims.-$$Lambda
 *  com.android.ims.-$$Lambda$a4IO_gY853vtN_bjQR9bZYk4Js0
 */
package com.android.ims;

import com.android.ims.-$;
import com.android.ims.MmTelFeatureConnection;
import java.util.function.Consumer;

public final class _$$Lambda$a4IO_gY853vtN_bjQR9bZYk4Js0
implements Consumer {
    public static final /* synthetic */ -$.Lambda.a4IO_gY853vtN_bjQR9bZYk4Js0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$a4IO_gY853vtN_bjQR9bZYk4Js0();
    }

    private /* synthetic */ _$$Lambda$a4IO_gY853vtN_bjQR9bZYk4Js0() {
    }

    public final void accept(Object object) {
        ((MmTelFeatureConnection.IFeatureUpdate)object).notifyStateChanged();
    }
}

