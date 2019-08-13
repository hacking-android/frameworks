/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$UowTf7vzuMsu4sv1-eMs5iEeNh0
 */
package java.util.stream;

import java.util.IntSummaryStatistics;
import java.util.function.ObjIntConsumer;
import java.util.stream.-$;

public final class _$$Lambda$UowTf7vzuMsu4sv1_eMs5iEeNh0
implements ObjIntConsumer {
    public static final /* synthetic */ -$.Lambda.UowTf7vzuMsu4sv1-eMs5iEeNh0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$UowTf7vzuMsu4sv1_eMs5iEeNh0();
    }

    private /* synthetic */ _$$Lambda$UowTf7vzuMsu4sv1_eMs5iEeNh0() {
    }

    public final void accept(Object object, int n) {
        ((IntSummaryStatistics)object).accept(n);
    }
}

