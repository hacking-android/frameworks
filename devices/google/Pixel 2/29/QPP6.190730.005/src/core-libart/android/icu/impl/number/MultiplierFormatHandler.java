/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.MicroProps;
import android.icu.impl.number.MicroPropsGenerator;
import android.icu.number.Scale;

public class MultiplierFormatHandler
implements MicroPropsGenerator {
    final Scale multiplier;
    final MicroPropsGenerator parent;

    public MultiplierFormatHandler(Scale scale, MicroPropsGenerator microPropsGenerator) {
        this.multiplier = scale;
        this.parent = microPropsGenerator;
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microProps = this.parent.processQuantity(decimalQuantity);
        this.multiplier.applyTo(decimalQuantity);
        return microProps;
    }
}

