/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Collectors
 *  java.util.stream.-$$Lambda$Collectors$oMCfAR-_eVSty8GsYzK5sec1Kag
 */
package java.util.stream;

import java.util.DoubleSummaryStatistics;
import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$oMCfAR__eVSty8GsYzK5sec1Kag
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.Collectors.oMCfAR-_eVSty8GsYzK5sec1Kag INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Collectors$oMCfAR__eVSty8GsYzK5sec1Kag();
    }

    private /* synthetic */ _$$Lambda$Collectors$oMCfAR__eVSty8GsYzK5sec1Kag() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$summarizingDouble$65((DoubleSummaryStatistics)object, (DoubleSummaryStatistics)object2);
    }
}

