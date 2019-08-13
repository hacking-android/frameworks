/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.ColorSpace;
import java.util.function.DoubleUnaryOperator;

public final class _$$Lambda$ColorSpace$Rgb$iMkODTKa3_8kPZUnZZerD2Lv_yo
implements DoubleUnaryOperator {
    private final /* synthetic */ ColorSpace.Rgb.TransferParameters f$0;

    public /* synthetic */ _$$Lambda$ColorSpace$Rgb$iMkODTKa3_8kPZUnZZerD2Lv_yo(ColorSpace.Rgb.TransferParameters transferParameters) {
        this.f$0 = transferParameters;
    }

    @Override
    public final double applyAsDouble(double d) {
        return ColorSpace.Rgb.lambda$new$3(this.f$0, d);
    }
}

