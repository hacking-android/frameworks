/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.ModifierStore;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.impl.number.range.PrefixInfixSuffixLengthHelper;
import android.icu.text.NumberFormat;
import android.icu.util.ICUException;

public class SimpleModifier
implements Modifier {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ARG_NUM_LIMIT = 256;
    private final String compiledPattern;
    private final NumberFormat.Field field;
    private final Modifier.Parameters parameters;
    private final int prefixLength;
    private final boolean strong;
    private final int suffixLength;
    private final int suffixOffset;

    public SimpleModifier(String string, NumberFormat.Field field, boolean bl) {
        this(string, field, bl, null);
    }

    public SimpleModifier(String string, NumberFormat.Field field, boolean bl, Modifier.Parameters parameters) {
        this.compiledPattern = string;
        this.field = field;
        this.strong = bl;
        this.parameters = parameters;
        if (SimpleFormatterImpl.getArgumentLimit(string) == 0) {
            this.prefixLength = string.charAt(1) - 256;
            this.suffixOffset = -1;
            this.suffixLength = 0;
        } else {
            if (string.charAt(1) != '\u0000') {
                this.prefixLength = string.charAt(1) - 256;
                this.suffixOffset = this.prefixLength + 3;
            } else {
                this.prefixLength = 0;
                this.suffixOffset = 2;
            }
            this.suffixLength = this.prefixLength + 3 < string.length() ? string.charAt(this.suffixOffset) - 256 : 0;
        }
    }

    public static void formatTwoArgPattern(String string, NumberStringBuilder numberStringBuilder, int n, PrefixInfixSuffixLengthHelper prefixInfixSuffixLengthHelper, NumberFormat.Field field) {
        if (SimpleFormatterImpl.getArgumentLimit(string) == 2) {
            int n2 = 0;
            int n3 = string.charAt(1);
            int n4 = 1 + 1;
            if (n3 < 256) {
                n3 = 0;
            } else {
                numberStringBuilder.insert(n + 0, string, n4, n4 + (n3 -= 256), field);
                n2 = 0 + n3;
                n4 = n4 + n3 + 1;
            }
            int n5 = string.charAt(n4);
            int n6 = n4 + 1;
            if (n5 < 256) {
                n5 = 0;
                n4 = n2;
                n2 = n5;
            } else {
                numberStringBuilder.insert(n + n2, string, n6, n6 + (n5 -= 256), field);
                n4 = n2 + n5;
                n6 = n6 + n5 + 1;
                n2 = n5;
            }
            if (n6 == string.length()) {
                n = 0;
            } else {
                n5 = string.charAt(n6) - 256;
                numberStringBuilder.insert(n + n4, string, ++n6, n6 + n5, field);
                n = n5;
            }
            prefixInfixSuffixLengthHelper.lengthPrefix = n3;
            prefixInfixSuffixLengthHelper.lengthInfix = n2;
            prefixInfixSuffixLengthHelper.lengthSuffix = n;
            return;
        }
        throw new ICUException();
    }

    @Override
    public int apply(NumberStringBuilder numberStringBuilder, int n, int n2) {
        return this.formatAsPrefixSuffix(numberStringBuilder, n, n2, this.field);
    }

    @Override
    public boolean containsField(NumberFormat.Field field) {
        return false;
    }

    public int formatAsPrefixSuffix(NumberStringBuilder numberStringBuilder, int n, int n2, NumberFormat.Field field) {
        int n3;
        if (this.suffixOffset == -1) {
            return numberStringBuilder.splice(n, n2, this.compiledPattern, 2, this.prefixLength + 2, field);
        }
        int n4 = this.prefixLength;
        if (n4 > 0) {
            numberStringBuilder.insert(n, this.compiledPattern, 2, n4 + 2, field);
        }
        if ((n3 = this.suffixLength) > 0) {
            n = this.prefixLength;
            String string = this.compiledPattern;
            n4 = this.suffixOffset;
            numberStringBuilder.insert(n2 + n, string, n4 + 1, n4 + 1 + n3, field);
        }
        return this.prefixLength + this.suffixLength;
    }

    @Override
    public int getCodePointCount() {
        int n = 0;
        int n2 = this.prefixLength;
        if (n2 > 0) {
            n = 0 + Character.codePointCount(this.compiledPattern, 2, n2 + 2);
        }
        int n3 = this.suffixLength;
        n2 = n;
        if (n3 > 0) {
            String string = this.compiledPattern;
            n2 = this.suffixOffset;
            n2 = n + Character.codePointCount(string, n2 + 1, n2 + 1 + n3);
        }
        return n2;
    }

    @Override
    public Modifier.Parameters getParameters() {
        return this.parameters;
    }

    @Override
    public int getPrefixLength() {
        return this.prefixLength;
    }

    @Override
    public boolean isStrong() {
        return this.strong;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        boolean bl = modifier instanceof SimpleModifier;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        modifier = (SimpleModifier)modifier;
        Modifier.Parameters parameters = this.parameters;
        if (parameters != null && ((SimpleModifier)modifier).parameters != null && parameters.obj == modifier.parameters.obj) {
            return true;
        }
        bl = bl2;
        if (this.compiledPattern.equals(((SimpleModifier)modifier).compiledPattern)) {
            bl = bl2;
            if (this.field == ((SimpleModifier)modifier).field) {
                bl = bl2;
                if (this.strong == ((SimpleModifier)modifier).strong) {
                    bl = true;
                }
            }
        }
        return bl;
    }
}

