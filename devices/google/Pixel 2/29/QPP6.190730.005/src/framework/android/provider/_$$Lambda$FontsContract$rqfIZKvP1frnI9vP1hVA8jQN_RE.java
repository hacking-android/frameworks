/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.provider.FontRequest;
import android.provider.FontsContract;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class _$$Lambda$FontsContract$rqfIZKvP1frnI9vP1hVA8jQN_RE
implements Runnable {
    private final /* synthetic */ FontRequest f$0;
    private final /* synthetic */ String f$1;
    private final /* synthetic */ AtomicReference f$2;
    private final /* synthetic */ Lock f$3;
    private final /* synthetic */ AtomicBoolean f$4;
    private final /* synthetic */ AtomicBoolean f$5;
    private final /* synthetic */ Condition f$6;

    public /* synthetic */ _$$Lambda$FontsContract$rqfIZKvP1frnI9vP1hVA8jQN_RE(FontRequest fontRequest, String string2, AtomicReference atomicReference, Lock lock, AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2, Condition condition) {
        this.f$0 = fontRequest;
        this.f$1 = string2;
        this.f$2 = atomicReference;
        this.f$3 = lock;
        this.f$4 = atomicBoolean;
        this.f$5 = atomicBoolean2;
        this.f$6 = condition;
    }

    @Override
    public final void run() {
        FontsContract.lambda$getFontSync$0(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
    }
}

