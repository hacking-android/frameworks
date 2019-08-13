/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.ValidationMatcher;
import android.icu.number.Scale;

public class MultiplierParseHandler
extends ValidationMatcher {
    private final Scale multiplier;

    public MultiplierParseHandler(Scale scale) {
        this.multiplier = scale;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.quantity != null) {
            this.multiplier.applyReciprocalTo(parsedNumber.quantity);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<MultiplierHandler ");
        stringBuilder.append(this.multiplier);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
}

