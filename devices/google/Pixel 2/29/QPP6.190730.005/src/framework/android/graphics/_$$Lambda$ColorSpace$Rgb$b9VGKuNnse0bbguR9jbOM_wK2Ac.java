/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.ColorSpace;
import java.util.function.DoubleUnaryOperator;

public final class _$$Lambda$ColorSpace$Rgb$b9VGKuNnse0bbguR9jbOM_wK2Ac
implements DoubleUnaryOperator {
    private final /* synthetic */ ColorSpace.Rgb.TransferParameters f$0;

    public /* synthetic */ _$$Lambda$ColorSpace$Rgb$b9VGKuNnse0bbguR9jbOM_wK2Ac(ColorSpace.Rgb.TransferParameters transferParameters) {
        this.f$0 = transferParameters;
    }

    @Override
    public final double applyAsDouble(double d) {
        return ColorSpace.Rgb.lambda$new$2(this.f$0, d);
    }
}

