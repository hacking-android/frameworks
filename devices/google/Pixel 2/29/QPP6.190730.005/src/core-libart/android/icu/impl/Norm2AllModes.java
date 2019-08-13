/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CacheBase;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.SoftCache;
import android.icu.text.Normalizer;
import android.icu.text.Normalizer2;
import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class Norm2AllModes {
    public static final NoopNormalizer2 NOOP_NORMALIZER2;
    private static CacheBase<String, Norm2AllModes, ByteBuffer> cache;
    public final ComposeNormalizer2 comp;
    public final DecomposeNormalizer2 decomp;
    public final ComposeNormalizer2 fcc;
    public final FCDNormalizer2 fcd;
    public final Normalizer2Impl impl;

    static {
        cache = new SoftCache<String, Norm2AllModes, ByteBuffer>(){

            @Override
            protected Norm2AllModes createInstance(String object, ByteBuffer object2) {
                if (object2 == null) {
                    Normalizer2Impl normalizer2Impl = new Normalizer2Impl();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(".nrm");
                    object = normalizer2Impl.load(((StringBuilder)object2).toString());
                } else {
                    object = new Normalizer2Impl().load((ByteBuffer)object2);
                }
                return new Norm2AllModes((Normalizer2Impl)object);
            }
        };
        NOOP_NORMALIZER2 = new NoopNormalizer2();
    }

    private Norm2AllModes(Normalizer2Impl normalizer2Impl) {
        this.impl = normalizer2Impl;
        this.comp = new ComposeNormalizer2(normalizer2Impl, false);
        this.decomp = new DecomposeNormalizer2(normalizer2Impl);
        this.fcd = new FCDNormalizer2(normalizer2Impl);
        this.fcc = new ComposeNormalizer2(normalizer2Impl, true);
    }

    public static Normalizer2 getFCDNormalizer2() {
        return Norm2AllModes.getNFCInstance().fcd;
    }

    public static Norm2AllModes getInstance(ByteBuffer byteBuffer, String string) {
        Norm2AllModesSingleton norm2AllModesSingleton;
        if (byteBuffer == null && (norm2AllModesSingleton = string.equals("nfc") ? NFCSingleton.INSTANCE : (string.equals("nfkc") ? NFKCSingleton.INSTANCE : (string.equals("nfkc_cf") ? NFKC_CFSingleton.INSTANCE : null))) != null) {
            if (norm2AllModesSingleton.exception == null) {
                return norm2AllModesSingleton.allModes;
            }
            throw norm2AllModesSingleton.exception;
        }
        return cache.getInstance(string, byteBuffer);
    }

    private static Norm2AllModes getInstanceFromSingleton(Norm2AllModesSingleton norm2AllModesSingleton) {
        if (norm2AllModesSingleton.exception == null) {
            return norm2AllModesSingleton.allModes;
        }
        throw norm2AllModesSingleton.exception;
    }

    public static Normalizer2WithImpl getN2WithImpl(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return Norm2AllModes.getNFKCInstance().comp;
                }
                return Norm2AllModes.getNFCInstance().comp;
            }
            return Norm2AllModes.getNFKCInstance().decomp;
        }
        return Norm2AllModes.getNFCInstance().decomp;
    }

    public static Norm2AllModes getNFCInstance() {
        return Norm2AllModes.getInstanceFromSingleton(NFCSingleton.INSTANCE);
    }

    public static Norm2AllModes getNFKCInstance() {
        return Norm2AllModes.getInstanceFromSingleton(NFKCSingleton.INSTANCE);
    }

    public static Norm2AllModes getNFKC_CFInstance() {
        return Norm2AllModes.getInstanceFromSingleton(NFKC_CFSingleton.INSTANCE);
    }

    public static final class ComposeNormalizer2
    extends Normalizer2WithImpl {
        private final boolean onlyContiguous;

        public ComposeNormalizer2(Normalizer2Impl normalizer2Impl, boolean bl) {
            super(normalizer2Impl);
            this.onlyContiguous = bl;
        }

        @Override
        public int getQuickCheck(int n) {
            return this.impl.getCompQuickCheck(this.impl.getNorm16(n));
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return this.impl.hasCompBoundaryAfter(n, this.onlyContiguous);
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return this.impl.hasCompBoundaryBefore(n);
        }

        @Override
        public boolean isInert(int n) {
            return this.impl.isCompInert(n, this.onlyContiguous);
        }

        @Override
        public boolean isNormalized(CharSequence charSequence) {
            return this.impl.compose(charSequence, 0, charSequence.length(), this.onlyContiguous, false, new Normalizer2Impl.ReorderingBuffer(this.impl, new StringBuilder(), 5));
        }

        @Override
        protected void normalize(CharSequence charSequence, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.compose(charSequence, 0, charSequence.length(), this.onlyContiguous, true, reorderingBuffer);
        }

        @Override
        protected void normalizeAndAppend(CharSequence charSequence, boolean bl, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.composeAndAppend(charSequence, bl, this.onlyContiguous, reorderingBuffer);
        }

        @Override
        public Normalizer.QuickCheckResult quickCheck(CharSequence charSequence) {
            int n = this.impl.composeQuickCheck(charSequence, 0, charSequence.length(), this.onlyContiguous, false);
            if ((n & 1) != 0) {
                return Normalizer.MAYBE;
            }
            if (n >>> 1 == charSequence.length()) {
                return Normalizer.YES;
            }
            return Normalizer.NO;
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return this.impl.composeQuickCheck(charSequence, 0, charSequence.length(), this.onlyContiguous, true) >>> 1;
        }
    }

    public static final class DecomposeNormalizer2
    extends Normalizer2WithImpl {
        public DecomposeNormalizer2(Normalizer2Impl normalizer2Impl) {
            super(normalizer2Impl);
        }

        @Override
        public int getQuickCheck(int n) {
            return (int)this.impl.isDecompYes(this.impl.getNorm16(n));
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return this.impl.hasDecompBoundaryAfter(n);
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return this.impl.hasDecompBoundaryBefore(n);
        }

        @Override
        public boolean isInert(int n) {
            return this.impl.isDecompInert(n);
        }

        @Override
        protected void normalize(CharSequence charSequence, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.decompose(charSequence, 0, charSequence.length(), reorderingBuffer);
        }

        @Override
        protected void normalizeAndAppend(CharSequence charSequence, boolean bl, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.decomposeAndAppend(charSequence, bl, reorderingBuffer);
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return this.impl.decompose(charSequence, 0, charSequence.length(), null);
        }
    }

    public static final class FCDNormalizer2
    extends Normalizer2WithImpl {
        public FCDNormalizer2(Normalizer2Impl normalizer2Impl) {
            super(normalizer2Impl);
        }

        @Override
        public int getQuickCheck(int n) {
            return (int)this.impl.isDecompYes(this.impl.getNorm16(n));
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return this.impl.hasFCDBoundaryAfter(n);
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return this.impl.hasFCDBoundaryBefore(n);
        }

        @Override
        public boolean isInert(int n) {
            return this.impl.isFCDInert(n);
        }

        @Override
        protected void normalize(CharSequence charSequence, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.makeFCD(charSequence, 0, charSequence.length(), reorderingBuffer);
        }

        @Override
        protected void normalizeAndAppend(CharSequence charSequence, boolean bl, Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.makeFCDAndAppend(charSequence, bl, reorderingBuffer);
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return this.impl.makeFCD(charSequence, 0, charSequence.length(), null);
        }
    }

    private static final class NFCSingleton {
        private static final Norm2AllModesSingleton INSTANCE = new Norm2AllModesSingleton("nfc");

        private NFCSingleton() {
        }
    }

    private static final class NFKCSingleton {
        private static final Norm2AllModesSingleton INSTANCE = new Norm2AllModesSingleton("nfkc");

        private NFKCSingleton() {
        }
    }

    private static final class NFKC_CFSingleton {
        private static final Norm2AllModesSingleton INSTANCE = new Norm2AllModesSingleton("nfkc_cf");

        private NFKC_CFSingleton() {
        }
    }

    public static final class NoopNormalizer2
    extends Normalizer2 {
        @Override
        public StringBuilder append(StringBuilder stringBuilder, CharSequence charSequence) {
            if (stringBuilder != charSequence) {
                stringBuilder.append(charSequence);
                return stringBuilder;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public String getDecomposition(int n) {
            return null;
        }

        @Override
        public boolean hasBoundaryAfter(int n) {
            return true;
        }

        @Override
        public boolean hasBoundaryBefore(int n) {
            return true;
        }

        @Override
        public boolean isInert(int n) {
            return true;
        }

        @Override
        public boolean isNormalized(CharSequence charSequence) {
            return true;
        }

        @Override
        public Appendable normalize(CharSequence object, Appendable appendable) {
            if (appendable != object) {
                try {
                    object = appendable.append((CharSequence)object);
                    return object;
                }
                catch (IOException iOException) {
                    throw new ICUUncheckedIOException(iOException);
                }
            }
            throw new IllegalArgumentException();
        }

        @Override
        public StringBuilder normalize(CharSequence charSequence, StringBuilder stringBuilder) {
            if (stringBuilder != charSequence) {
                stringBuilder.setLength(0);
                stringBuilder.append(charSequence);
                return stringBuilder;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence) {
            if (stringBuilder != charSequence) {
                stringBuilder.append(charSequence);
                return stringBuilder;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public Normalizer.QuickCheckResult quickCheck(CharSequence charSequence) {
            return Normalizer.YES;
        }

        @Override
        public int spanQuickCheckYes(CharSequence charSequence) {
            return charSequence.length();
        }
    }

    private static final class Norm2AllModesSingleton {
        private Norm2AllModes allModes;
        private RuntimeException exception;

        private Norm2AllModesSingleton(String object) {
            try {
                Normalizer2Impl normalizer2Impl = new Normalizer2Impl();
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(".nrm");
                object2 = normalizer2Impl.load(((StringBuilder)object2).toString());
                this.allModes = object = new Norm2AllModes((Normalizer2Impl)object2);
            }
            catch (RuntimeException runtimeException) {
                this.exception = runtimeException;
            }
        }
    }

    public static abstract class Normalizer2WithImpl
    extends Normalizer2 {
        public final Normalizer2Impl impl;

        public Normalizer2WithImpl(Normalizer2Impl normalizer2Impl) {
            this.impl = normalizer2Impl;
        }

        @Override
        public StringBuilder append(StringBuilder stringBuilder, CharSequence charSequence) {
            return this.normalizeSecondAndAppend(stringBuilder, charSequence, false);
        }

        @Override
        public int composePair(int n, int n2) {
            return this.impl.composePair(n, n2);
        }

        @Override
        public int getCombiningClass(int n) {
            Normalizer2Impl normalizer2Impl = this.impl;
            return normalizer2Impl.getCC(normalizer2Impl.getNorm16(n));
        }

        @Override
        public String getDecomposition(int n) {
            return this.impl.getDecomposition(n);
        }

        public abstract int getQuickCheck(int var1);

        @Override
        public String getRawDecomposition(int n) {
            return this.impl.getRawDecomposition(n);
        }

        @Override
        public boolean isNormalized(CharSequence charSequence) {
            boolean bl = charSequence.length() == this.spanQuickCheckYes(charSequence);
            return bl;
        }

        @Override
        public Appendable normalize(CharSequence charSequence, Appendable appendable) {
            if (appendable != charSequence) {
                Normalizer2Impl.ReorderingBuffer reorderingBuffer = new Normalizer2Impl.ReorderingBuffer(this.impl, appendable, charSequence.length());
                this.normalize(charSequence, reorderingBuffer);
                reorderingBuffer.flush();
                return appendable;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public StringBuilder normalize(CharSequence charSequence, StringBuilder stringBuilder) {
            if (stringBuilder != charSequence) {
                stringBuilder.setLength(0);
                this.normalize(charSequence, new Normalizer2Impl.ReorderingBuffer(this.impl, stringBuilder, charSequence.length()));
                return stringBuilder;
            }
            throw new IllegalArgumentException();
        }

        protected abstract void normalize(CharSequence var1, Normalizer2Impl.ReorderingBuffer var2);

        protected abstract void normalizeAndAppend(CharSequence var1, boolean var2, Normalizer2Impl.ReorderingBuffer var3);

        @Override
        public StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence) {
            return this.normalizeSecondAndAppend(stringBuilder, charSequence, true);
        }

        public StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence, boolean bl) {
            if (stringBuilder != charSequence) {
                this.normalizeAndAppend(charSequence, bl, new Normalizer2Impl.ReorderingBuffer(this.impl, stringBuilder, stringBuilder.length() + charSequence.length()));
                return stringBuilder;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public Normalizer.QuickCheckResult quickCheck(CharSequence object) {
            object = this.isNormalized((CharSequence)object) ? Normalizer.YES : Normalizer.NO;
            return object;
        }
    }

}

