/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics.text;

import android.graphics.Paint;
import android.graphics.Rect;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import libcore.util.NativeAllocationRegistry;

public class MeasuredText {
    private char[] mChars;
    private boolean mComputeHyphenation;
    private boolean mComputeLayout;
    private long mNativePtr;

    private MeasuredText(long l, char[] arrc, boolean bl, boolean bl2) {
        this.mNativePtr = l;
        this.mChars = arrc;
        this.mComputeHyphenation = bl;
        this.mComputeLayout = bl2;
    }

    static /* synthetic */ long access$000() {
        return MeasuredText.nGetReleaseFunc();
    }

    private static native void nGetBounds(long var0, char[] var2, int var3, int var4, Rect var5);

    @CriticalNative
    private static native float nGetCharWidthAt(long var0, int var2);

    @CriticalNative
    private static native int nGetMemoryUsage(long var0);

    @CriticalNative
    private static native long nGetReleaseFunc();

    @CriticalNative
    private static native float nGetWidth(long var0, int var2, int var3);

    public void getBounds(int n, int n2, Rect rect) {
        boolean bl = true;
        boolean bl2 = n >= 0 && n <= this.mChars.length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("start(");
        stringBuilder.append(n);
        stringBuilder.append(") must be 0 <= start <= ");
        stringBuilder.append(this.mChars.length);
        Preconditions.checkArgument(bl2, stringBuilder.toString());
        bl2 = n2 >= 0 && n2 <= this.mChars.length;
        stringBuilder = new StringBuilder();
        stringBuilder.append("end(");
        stringBuilder.append(n2);
        stringBuilder.append(") must be 0 <= end <= ");
        stringBuilder.append(this.mChars.length);
        Preconditions.checkArgument(bl2, stringBuilder.toString());
        bl2 = n <= n2 ? bl : false;
        stringBuilder = new StringBuilder();
        stringBuilder.append("start(");
        stringBuilder.append(n);
        stringBuilder.append(") is larger than end(");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        Preconditions.checkArgument(bl2, stringBuilder.toString());
        Preconditions.checkNotNull(rect);
        MeasuredText.nGetBounds(this.mNativePtr, this.mChars, n, n2, rect);
    }

    public float getCharWidthAt(int n) {
        boolean bl = n >= 0 && n < this.mChars.length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("offset(");
        stringBuilder.append(n);
        stringBuilder.append(") is larger than text length: ");
        stringBuilder.append(this.mChars.length);
        Preconditions.checkArgument(bl, stringBuilder.toString());
        return MeasuredText.nGetCharWidthAt(this.mNativePtr, n);
    }

    public char[] getChars() {
        return this.mChars;
    }

    public int getMemoryUsage() {
        return MeasuredText.nGetMemoryUsage(this.mNativePtr);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }

    public float getWidth(int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0 && n <= this.mChars.length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("start(");
        stringBuilder.append(n);
        stringBuilder.append(") must be 0 <= start <= ");
        stringBuilder.append(this.mChars.length);
        Preconditions.checkArgument(bl2, stringBuilder.toString());
        bl2 = n2 >= 0 && n2 <= this.mChars.length;
        stringBuilder = new StringBuilder();
        stringBuilder.append("end(");
        stringBuilder.append(n2);
        stringBuilder.append(") must be 0 <= end <= ");
        stringBuilder.append(this.mChars.length);
        Preconditions.checkArgument(bl2, stringBuilder.toString());
        bl2 = n <= n2 ? bl : false;
        stringBuilder = new StringBuilder();
        stringBuilder.append("start(");
        stringBuilder.append(n);
        stringBuilder.append(") is larger than end(");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        Preconditions.checkArgument(bl2, stringBuilder.toString());
        return MeasuredText.nGetWidth(this.mNativePtr, n, n2);
    }

    public static final class Builder {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)MeasuredText.class.getClassLoader(), (long)MeasuredText.access$000());
        private boolean mComputeHyphenation = false;
        private boolean mComputeLayout = true;
        private int mCurrentOffset = 0;
        private MeasuredText mHintMt = null;
        private long mNativePtr;
        private final char[] mText;

        public Builder(MeasuredText measuredText) {
            Preconditions.checkNotNull(measuredText);
            this.mText = measuredText.mChars;
            this.mNativePtr = Builder.nInitBuilder();
            if (measuredText.mComputeLayout) {
                this.mComputeHyphenation = measuredText.mComputeHyphenation;
                this.mComputeLayout = measuredText.mComputeLayout;
                this.mHintMt = measuredText;
                return;
            }
            throw new IllegalArgumentException("The input MeasuredText must not be created with setComputeLayout(false).");
        }

        public Builder(char[] arrc) {
            Preconditions.checkNotNull(arrc);
            this.mText = arrc;
            this.mNativePtr = Builder.nInitBuilder();
        }

        private void ensureNativePtrNoReuse() {
            if (this.mNativePtr != 0L) {
                return;
            }
            throw new IllegalStateException("Builder can not be reused.");
        }

        private static native void nAddReplacementRun(long var0, long var2, int var4, int var5, float var6);

        private static native void nAddStyleRun(long var0, long var2, int var4, int var5, boolean var6);

        private static native long nBuildMeasuredText(long var0, long var2, char[] var4, boolean var5, boolean var6);

        private static native void nFreeBuilder(long var0);

        private static native long nInitBuilder();

        public Builder appendReplacementRun(Paint paint, int n, float f) {
            boolean bl = true;
            boolean bl2 = n > 0;
            Preconditions.checkArgument(bl2, "length can not be negative");
            n = this.mCurrentOffset + n;
            bl2 = n <= this.mText.length ? bl : false;
            Preconditions.checkArgument(bl2, "Replacement exceeds the text length");
            Builder.nAddReplacementRun(this.mNativePtr, paint.getNativeInstance(), this.mCurrentOffset, n, f);
            this.mCurrentOffset = n;
            return this;
        }

        public Builder appendStyleRun(Paint paint, int n, boolean bl) {
            Preconditions.checkNotNull(paint);
            boolean bl2 = true;
            boolean bl3 = n > 0;
            Preconditions.checkArgument(bl3, "length can not be negative");
            n = this.mCurrentOffset + n;
            bl3 = n <= this.mText.length ? bl2 : false;
            Preconditions.checkArgument(bl3, "Style exceeds the text length");
            Builder.nAddStyleRun(this.mNativePtr, paint.getNativeInstance(), this.mCurrentOffset, n, bl);
            this.mCurrentOffset = n;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public MeasuredText build() {
            this.ensureNativePtrNoReuse();
            if (this.mCurrentOffset != this.mText.length) throw new IllegalStateException("Style info has not been provided for all text.");
            MeasuredText measuredText = this.mHintMt;
            if (measuredText != null) {
                if (measuredText.mComputeHyphenation != this.mComputeHyphenation) throw new IllegalArgumentException("The hyphenation configuration is different from given hint MeasuredText");
            }
            try {
                long l = this.mHintMt == null ? 0L : this.mHintMt.getNativePtr();
                l = Builder.nBuildMeasuredText(this.mNativePtr, l, this.mText, this.mComputeHyphenation, this.mComputeLayout);
                measuredText = new MeasuredText(l, this.mText, this.mComputeHyphenation, this.mComputeLayout);
                sRegistry.registerNativeAllocation((Object)measuredText, l);
                return measuredText;
            }
            finally {
                Builder.nFreeBuilder(this.mNativePtr);
                this.mNativePtr = 0L;
            }
        }

        public Builder setComputeHyphenation(boolean bl) {
            this.mComputeHyphenation = bl;
            return this;
        }

        public Builder setComputeLayout(boolean bl) {
            this.mComputeLayout = bl;
            return this;
        }
    }

}

