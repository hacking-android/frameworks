/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.ColorSpace;
import java.util.function.DoubleUnaryOperator;

public final class _$$Lambda$ColorSpace$Rgb$8EkhO2jIf14tuA3BvrmYJMa7YXM
implements DoubleUnaryOperator {
    private final /* synthetic */ ColorSpace.Rgb f$0;

    public /* synthetic */ _$$Lambda$ColorSpace$Rgb$8EkhO2jIf14tuA3BvrmYJMa7YXM(ColorSpace.Rgb rgb) {
        this.f$0 = rgb;
    }

    @Override
    public final double applyAsDouble(double d) {
        return ColorSpace.Rgb.lambda$8EkhO2jIf14tuA3BvrmYJMa7YXM(this.f$0, d);
    }
}

