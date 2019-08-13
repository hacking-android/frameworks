/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.dataconnection.-$
 *  com.android.internal.telephony.dataconnection.-$$Lambda
 *  com.android.internal.telephony.dataconnection.-$$Lambda$XZAGhHrbkIDyusER4MAM6luKcT0
 */
package com.android.internal.telephony.dataconnection;

import com.android.internal.telephony.dataconnection.-$;
import java.net.InetAddress;
import java.util.function.Function;

public final class _$$Lambda$XZAGhHrbkIDyusER4MAM6luKcT0
implements Function {
    public static final /* synthetic */ -$.Lambda.XZAGhHrbkIDyusER4MAM6luKcT0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$XZAGhHrbkIDyusER4MAM6luKcT0();
    }

    private /* synthetic */ _$$Lambda$XZAGhHrbkIDyusER4MAM6luKcT0() {
    }

    public final Object apply(Object object) {
        return ((InetAddress)object).getHostAddress();
    }
}

