/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Collectors
 *  java.util.stream.-$$Lambda$Collectors$SMVdf7W0ks2OOmS3zJw7DHc-Nhc
 */
package java.util.stream;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$SMVdf7W0ks2OOmS3zJw7DHc_Nhc
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.Collectors.SMVdf7W0ks2OOmS3zJw7DHc-Nhc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Collectors$SMVdf7W0ks2OOmS3zJw7DHc_Nhc();
    }

    private /* synthetic */ _$$Lambda$Collectors$SMVdf7W0ks2OOmS3zJw7DHc_Nhc() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$toSet$4((Set)object, (Set)object2);
    }
}

