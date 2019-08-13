/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUBinary;
import android.icu.impl.Norm2AllModes;
import android.icu.text.Normalizer;
import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public abstract class Normalizer2 {
    @Deprecated
    protected Normalizer2() {
    }

    public static Normalizer2 getInstance(InputStream object, String string, Mode mode) {
        ByteBuffer byteBuffer = null;
        if (object != null) {
            try {
                byteBuffer = ICUBinary.getByteBufferFromInputStreamAndCloseStream((InputStream)object);
            }
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }
        object = Norm2AllModes.getInstance(byteBuffer, string);
        int n = 1.$SwitchMap$android$icu$text$Normalizer2$Mode[mode.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return null;
                    }
                    return ((Norm2AllModes)object).fcc;
                }
                return ((Norm2AllModes)object).fcd;
            }
            return ((Norm2AllModes)object).decomp;
        }
        return ((Norm2AllModes)object).comp;
    }

    public static Normalizer2 getNFCInstance() {
        return Norm2AllModes.getNFCInstance().comp;
    }

    public static Normalizer2 getNFDInstance() {
        return Norm2AllModes.getNFCInstance().decomp;
    }

    public static Normalizer2 getNFKCCasefoldInstance() {
        return Norm2AllModes.getNFKC_CFInstance().comp;
    }

    public static Normalizer2 getNFKCInstance() {
        return Norm2AllModes.getNFKCInstance().comp;
    }

    public static Normalizer2 getNFKDInstance() {
        return Norm2AllModes.getNFKCInstance().decomp;
    }

    public abstract StringBuilder append(StringBuilder var1, CharSequence var2);

    public int composePair(int n, int n2) {
        return -1;
    }

    public int getCombiningClass(int n) {
        return 0;
    }

    public abstract String getDecomposition(int var1);

    public String getRawDecomposition(int n) {
        return null;
    }

    public abstract boolean hasBoundaryAfter(int var1);

    public abstract boolean hasBoundaryBefore(int var1);

    public abstract boolean isInert(int var1);

    public abstract boolean isNormalized(CharSequence var1);

    public abstract Appendable normalize(CharSequence var1, Appendable var2);

    public String normalize(CharSequence charSequence) {
        if (charSequence instanceof String) {
            int n = this.spanQuickCheckYes(charSequence);
            if (n == charSequence.length()) {
                return (String)charSequence;
            }
            if (n != 0) {
                return this.normalizeSecondAndAppend(new StringBuilder(charSequence.length()).append(charSequence, 0, n), charSequence.subSequence(n, charSequence.length())).toString();
            }
        }
        return this.normalize(charSequence, new StringBuilder(charSequence.length())).toString();
    }

    public abstract StringBuilder normalize(CharSequence var1, StringBuilder var2);

    public abstract StringBuilder normalizeSecondAndAppend(StringBuilder var1, CharSequence var2);

    public abstract Normalizer.QuickCheckResult quickCheck(CharSequence var1);

    public abstract int spanQuickCheckYes(CharSequence var1);

    public static enum Mode {
        COMPOSE,
        DECOMPOSE,
        FCD,
        COMPOSE_CONTIGUOUS;
        
    }

}

