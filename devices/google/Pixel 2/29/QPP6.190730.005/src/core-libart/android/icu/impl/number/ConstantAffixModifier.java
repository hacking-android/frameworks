/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.Modifier;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.text.NumberFormat;

public class ConstantAffixModifier
implements Modifier {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final ConstantAffixModifier EMPTY = new ConstantAffixModifier();
    private final NumberFormat.Field field;
    private final String prefix;
    private final boolean strong;
    private final String suffix;

    public ConstantAffixModifier() {
        this.prefix = "";
        this.suffix = "";
        this.field = null;
        this.strong = false;
    }

    public ConstantAffixModifier(String string, String string2, NumberFormat.Field field, boolean bl) {
        String string3 = "";
        if (string == null) {
            string = "";
        }
        this.prefix = string;
        string = string2 == null ? string3 : string2;
        this.suffix = string;
        this.field = field;
        this.strong = bl;
    }

    @Override
    public int apply(NumberStringBuilder numberStringBuilder, int n, int n2) {
        return numberStringBuilder.insert(n2, this.suffix, this.field) + numberStringBuilder.insert(n, this.prefix, this.field);
    }

    @Override
    public boolean containsField(NumberFormat.Field field) {
        return false;
    }

    @Override
    public int getCodePointCount() {
        String string = this.prefix;
        int n = string.codePointCount(0, string.length());
        string = this.suffix;
        return n + string.codePointCount(0, string.length());
    }

    @Override
    public Modifier.Parameters getParameters() {
        return null;
    }

    @Override
    public int getPrefixLength() {
        return this.prefix.length();
    }

    @Override
    public boolean isStrong() {
        return this.strong;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        boolean bl = modifier instanceof ConstantAffixModifier;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        modifier = (ConstantAffixModifier)modifier;
        bl = bl2;
        if (this.prefix.equals(((ConstantAffixModifier)modifier).prefix)) {
            bl = bl2;
            if (this.suffix.equals(((ConstantAffixModifier)modifier).suffix)) {
                bl = bl2;
                if (this.field == ((ConstantAffixModifier)modifier).field) {
                    bl = bl2;
                    if (this.strong == ((ConstantAffixModifier)modifier).strong) {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    public String toString() {
        return String.format("<ConstantAffixModifier prefix:'%s' suffix:'%s'>", this.prefix, this.suffix);
    }
}

