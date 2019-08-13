/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.os.-$
 *  com.android.internal.os.-$$Lambda
 *  com.android.internal.os.-$$Lambda$ZygoteServer
 *  com.android.internal.os.-$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE
 */
package com.android.internal.os;

import com.android.internal.os.-$;
import com.android.internal.os.ZygoteServer;
import java.io.FileDescriptor;
import java.util.function.ToIntFunction;

public final class _$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.ZygoteServer.NJVbduCrCzDq0RHpPga7lyCk4eE INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE();
    }

    private /* synthetic */ _$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE() {
    }

    public final int applyAsInt(Object object) {
        return ZygoteServer.lambda$runSelectLoop$0((FileDescriptor)object);
    }
}

