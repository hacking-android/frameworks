/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.infra.-$
 *  com.android.internal.infra.-$$Lambda
 *  com.android.internal.infra.-$$Lambda$EbzSql2RHkXox5Myj8A-7kLC4_A
 */
package com.android.internal.infra;

import com.android.internal.infra.-$;
import com.android.internal.infra.AbstractRemoteService;
import java.util.function.BiConsumer;

public final class _$$Lambda$EbzSql2RHkXox5Myj8A_7kLC4_A
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.EbzSql2RHkXox5Myj8A-7kLC4_A INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EbzSql2RHkXox5Myj8A_7kLC4_A();
    }

    private /* synthetic */ _$$Lambda$EbzSql2RHkXox5Myj8A_7kLC4_A() {
    }

    public final void accept(Object object, Object object2) {
        ((AbstractRemoteService)object).handlePendingRequest((AbstractRemoteService.MyAsyncPendingRequest)object2);
    }
}

