/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.ColorSpace;
import java.util.function.DoubleUnaryOperator;

public final class _$$Lambda$ColorSpace$Rgb$V_0lmM2WEpxGBDV_1G1wvvidn7Y
implements DoubleUnaryOperator {
    private final /* synthetic */ ColorSpace.Rgb.TransferParameters f$0;

    public /* synthetic */ _$$Lambda$ColorSpace$Rgb$V_0lmM2WEpxGBDV_1G1wvvidn7Y(ColorSpace.Rgb.TransferParameters transferParameters) {
        this.f$0 = transferParameters;
    }

    @Override
    public final double applyAsDouble(double d) {
        return ColorSpace.Rgb.lambda$new$1(this.f$0, d);
    }
}

