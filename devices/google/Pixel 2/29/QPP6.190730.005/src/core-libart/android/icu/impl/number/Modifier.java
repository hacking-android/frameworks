/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.number.ModifierStore;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.text.NumberFormat;

public interface Modifier {
    public int apply(NumberStringBuilder var1, int var2, int var3);

    public boolean containsField(NumberFormat.Field var1);

    public int getCodePointCount();

    public Parameters getParameters();

    public int getPrefixLength();

    public boolean isStrong();

    public boolean semanticallyEquivalent(Modifier var1);

    public static class Parameters {
        public ModifierStore obj;
        public StandardPlural plural;
        public int signum;
    }

}

