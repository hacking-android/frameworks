/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$BZcmU4lh1MU8ke57orLk6ELdvT4
 */
package java.util.stream;

import java.util.DoubleSummaryStatistics;
import java.util.function.BiConsumer;
import java.util.stream.-$;

public final class _$$Lambda$BZcmU4lh1MU8ke57orLk6ELdvT4
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.BZcmU4lh1MU8ke57orLk6ELdvT4 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BZcmU4lh1MU8ke57orLk6ELdvT4();
    }

    private /* synthetic */ _$$Lambda$BZcmU4lh1MU8ke57orLk6ELdvT4() {
    }

    public final void accept(Object object, Object object2) {
        ((DoubleSummaryStatistics)object).combine((DoubleSummaryStatistics)object2);
    }
}

