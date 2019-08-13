/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.Modifier;
import android.icu.impl.number.ModifierStore;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.text.NumberFormat;
import java.util.Arrays;

public class ConstantMultiFieldModifier
implements Modifier {
    private final boolean overwrite;
    private final Modifier.Parameters parameters;
    protected final char[] prefixChars;
    protected final NumberFormat.Field[] prefixFields;
    private final boolean strong;
    protected final char[] suffixChars;
    protected final NumberFormat.Field[] suffixFields;

    public ConstantMultiFieldModifier(NumberStringBuilder numberStringBuilder, NumberStringBuilder numberStringBuilder2, boolean bl, boolean bl2) {
        this(numberStringBuilder, numberStringBuilder2, bl, bl2, null);
    }

    public ConstantMultiFieldModifier(NumberStringBuilder numberStringBuilder, NumberStringBuilder numberStringBuilder2, boolean bl, boolean bl2, Modifier.Parameters parameters) {
        this.prefixChars = numberStringBuilder.toCharArray();
        this.suffixChars = numberStringBuilder2.toCharArray();
        this.prefixFields = numberStringBuilder.toFieldArray();
        this.suffixFields = numberStringBuilder2.toFieldArray();
        this.overwrite = bl;
        this.strong = bl2;
        this.parameters = parameters;
    }

    @Override
    public int apply(NumberStringBuilder numberStringBuilder, int n, int n2) {
        int n3;
        int n4 = n3 = numberStringBuilder.insert(n, this.prefixChars, this.prefixFields);
        if (this.overwrite) {
            n4 = n3 + numberStringBuilder.splice(n + n3, n2 + n3, "", 0, 0, null);
        }
        return n4 + numberStringBuilder.insert(n2 + n4, this.suffixChars, this.suffixFields);
    }

    @Override
    public boolean containsField(NumberFormat.Field field) {
        NumberFormat.Field[] arrfield;
        int n;
        for (n = 0; n < (arrfield = this.prefixFields).length; ++n) {
            if (arrfield[n] != field) continue;
            return true;
        }
        for (n = 0; n < (arrfield = this.suffixFields).length; ++n) {
            if (arrfield[n] != field) continue;
            return true;
        }
        return false;
    }

    @Override
    public int getCodePointCount() {
        char[] arrc = this.prefixChars;
        int n = Character.codePointCount(arrc, 0, arrc.length);
        arrc = this.suffixChars;
        return n + Character.codePointCount(arrc, 0, arrc.length);
    }

    @Override
    public Modifier.Parameters getParameters() {
        return this.parameters;
    }

    @Override
    public int getPrefixLength() {
        return this.prefixChars.length;
    }

    @Override
    public boolean isStrong() {
        return this.strong;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        boolean bl;
        block2 : {
            boolean bl2 = modifier instanceof ConstantMultiFieldModifier;
            bl = false;
            if (!bl2) {
                return false;
            }
            modifier = (ConstantMultiFieldModifier)modifier;
            Modifier.Parameters parameters = this.parameters;
            if (parameters != null && ((ConstantMultiFieldModifier)modifier).parameters != null && parameters.obj == modifier.parameters.obj) {
                return true;
            }
            if (!Arrays.equals(this.prefixChars, ((ConstantMultiFieldModifier)modifier).prefixChars) || !Arrays.equals(this.prefixFields, ((ConstantMultiFieldModifier)modifier).prefixFields) || !Arrays.equals(this.suffixChars, ((ConstantMultiFieldModifier)modifier).suffixChars) || !Arrays.equals(this.suffixFields, ((ConstantMultiFieldModifier)modifier).suffixFields) || this.overwrite != ((ConstantMultiFieldModifier)modifier).overwrite || this.strong != ((ConstantMultiFieldModifier)modifier).strong) break block2;
            bl = true;
        }
        return bl;
    }

    public String toString() {
        NumberStringBuilder numberStringBuilder = new NumberStringBuilder();
        this.apply(numberStringBuilder, 0, 0);
        int n = this.getPrefixLength();
        return String.format("<ConstantMultiFieldModifier prefix:'%s' suffix:'%s'>", numberStringBuilder.subSequence(0, n), numberStringBuilder.subSequence(n, numberStringBuilder.length()));
    }
}

