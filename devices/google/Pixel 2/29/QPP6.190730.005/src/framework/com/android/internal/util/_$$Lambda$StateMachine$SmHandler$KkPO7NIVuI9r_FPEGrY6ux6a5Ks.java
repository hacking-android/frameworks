/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import com.android.internal.util.StateMachine;
import java.util.function.Predicate;

public final class _$$Lambda$StateMachine$SmHandler$KkPO7NIVuI9r_FPEGrY6ux6a5Ks
implements Predicate {
    private final /* synthetic */ StateMachine.SmHandler.StateInfo f$0;

    public /* synthetic */ _$$Lambda$StateMachine$SmHandler$KkPO7NIVuI9r_FPEGrY6ux6a5Ks(StateMachine.SmHandler.StateInfo stateInfo) {
        this.f$0 = stateInfo;
    }

    public final boolean test(Object object) {
        return StateMachine.SmHandler.lambda$removeState$0(this.f$0, (StateMachine.SmHandler.StateInfo)object);
    }
}

