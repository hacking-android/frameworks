/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  sun.nio.ch.-$
 *  sun.nio.ch.-$$Lambda
 *  sun.nio.ch.-$$Lambda$ThreadPool
 *  sun.nio.ch.-$$Lambda$ThreadPool$N88rfRTSpCtnK5fgJO-WA6OwVQM
 */
package sun.nio.ch;

import java.util.concurrent.ThreadFactory;
import sun.nio.ch.-$;
import sun.nio.ch.ThreadPool;

public final class _$$Lambda$ThreadPool$N88rfRTSpCtnK5fgJO_WA6OwVQM
implements ThreadFactory {
    public static final /* synthetic */ -$.Lambda.ThreadPool.N88rfRTSpCtnK5fgJO-WA6OwVQM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ThreadPool$N88rfRTSpCtnK5fgJO_WA6OwVQM();
    }

    private /* synthetic */ _$$Lambda$ThreadPool$N88rfRTSpCtnK5fgJO_WA6OwVQM() {
    }

    @Override
    public final Thread newThread(Runnable runnable) {
        return ThreadPool.lambda$defaultThreadFactory$0(runnable);
    }
}

