/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$xi7ZBZfKmkbt5CSsaL8qlNeHupc
 */
package java.util.stream;

import java.util.function.DoubleBinaryOperator;
import java.util.stream.-$;

public final class _$$Lambda$xi7ZBZfKmkbt5CSsaL8qlNeHupc
implements DoubleBinaryOperator {
    public static final /* synthetic */ -$.Lambda.xi7ZBZfKmkbt5CSsaL8qlNeHupc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$xi7ZBZfKmkbt5CSsaL8qlNeHupc();
    }

    private /* synthetic */ _$$Lambda$xi7ZBZfKmkbt5CSsaL8qlNeHupc() {
    }

    @Override
    public final double applyAsDouble(double d, double d2) {
        return Math.max(d, d2);
    }
}

