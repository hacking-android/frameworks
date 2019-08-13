/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics.text;

import android.graphics.text.MeasuredText;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.util.NativeAllocationRegistry;

public class LineBreaker {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)LineBreaker.class.getClassLoader(), (long)LineBreaker.nGetReleaseFunc());
    private final long mNativePtr;

    private LineBreaker(int n, int n2, int n3, int[] arrn) {
        boolean bl = true;
        if (n3 != 1) {
            bl = false;
        }
        this.mNativePtr = LineBreaker.nInit(n, n2, bl, arrn);
        sRegistry.registerNativeAllocation((Object)this, this.mNativePtr);
    }

    static /* synthetic */ long access$100() {
        return LineBreaker.nGetReleaseResultFunc();
    }

    private static native long nComputeLineBreaks(long var0, char[] var2, long var3, int var5, float var6, int var7, float var8, float[] var9, float var10, int var11);

    @CriticalNative
    private static native float nGetLineAscent(long var0, int var2);

    @CriticalNative
    private static native int nGetLineBreakOffset(long var0, int var2);

    @CriticalNative
    private static native int nGetLineCount(long var0);

    @CriticalNative
    private static native float nGetLineDescent(long var0, int var2);

    @CriticalNative
    private static native int nGetLineFlag(long var0, int var2);

    @CriticalNative
    private static native float nGetLineWidth(long var0, int var2);

    @CriticalNative
    private static native long nGetReleaseFunc();

    @CriticalNative
    private static native long nGetReleaseResultFunc();

    @FastNative
    private static native long nInit(int var0, int var1, boolean var2, int[] var3);

    public Result computeLineBreaks(MeasuredText measuredText, ParagraphConstraints paragraphConstraints, int n) {
        return new Result(LineBreaker.nComputeLineBreaks(this.mNativePtr, measuredText.getChars(), measuredText.getNativePtr(), measuredText.getChars().length, paragraphConstraints.mFirstWidth, paragraphConstraints.mFirstWidthLineCount, paragraphConstraints.mWidth, paragraphConstraints.mVariableTabStops, paragraphConstraints.mDefaultTabStop, n));
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BreakStrategy {
    }

    public static final class Builder {
        private int mBreakStrategy = 0;
        private int mHyphenationFrequency = 0;
        private int[] mIndents = null;
        private int mJustificationMode = 0;

        public LineBreaker build() {
            return new LineBreaker(this.mBreakStrategy, this.mHyphenationFrequency, this.mJustificationMode, this.mIndents);
        }

        public Builder setBreakStrategy(int n) {
            this.mBreakStrategy = n;
            return this;
        }

        public Builder setHyphenationFrequency(int n) {
            this.mHyphenationFrequency = n;
            return this;
        }

        public Builder setIndents(int[] arrn) {
            this.mIndents = arrn;
            return this;
        }

        public Builder setJustificationMode(int n) {
            this.mJustificationMode = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HyphenationFrequency {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface JustificationMode {
    }

    public static class ParagraphConstraints {
        private float mDefaultTabStop = 0.0f;
        private float mFirstWidth = 0.0f;
        private int mFirstWidthLineCount = 0;
        private float[] mVariableTabStops = null;
        private float mWidth = 0.0f;

        public float getDefaultTabStop() {
            return this.mDefaultTabStop;
        }

        public float getFirstWidth() {
            return this.mFirstWidth;
        }

        public int getFirstWidthLineCount() {
            return this.mFirstWidthLineCount;
        }

        public float[] getTabStops() {
            return this.mVariableTabStops;
        }

        public float getWidth() {
            return this.mWidth;
        }

        public void setIndent(float f, int n) {
            this.mFirstWidth = f;
            this.mFirstWidthLineCount = n;
        }

        public void setTabStops(float[] arrf, float f) {
            this.mVariableTabStops = arrf;
            this.mDefaultTabStop = f;
        }

        public void setWidth(float f) {
            this.mWidth = f;
        }
    }

    public static class Result {
        private static final int END_HYPHEN_MASK = 7;
        private static final int HYPHEN_MASK = 255;
        private static final int START_HYPHEN_BITS_SHIFT = 3;
        private static final int START_HYPHEN_MASK = 24;
        private static final int TAB_MASK = 536870912;
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Result.class.getClassLoader(), (long)LineBreaker.access$100());
        private final long mPtr;

        private Result(long l) {
            this.mPtr = l;
            sRegistry.registerNativeAllocation((Object)this, this.mPtr);
        }

        public int getEndLineHyphenEdit(int n) {
            return LineBreaker.nGetLineFlag(this.mPtr, n) & 7;
        }

        public float getLineAscent(int n) {
            return LineBreaker.nGetLineAscent(this.mPtr, n);
        }

        public int getLineBreakOffset(int n) {
            return LineBreaker.nGetLineBreakOffset(this.mPtr, n);
        }

        public int getLineCount() {
            return LineBreaker.nGetLineCount(this.mPtr);
        }

        public float getLineDescent(int n) {
            return LineBreaker.nGetLineDescent(this.mPtr, n);
        }

        public float getLineWidth(int n) {
            return LineBreaker.nGetLineWidth(this.mPtr, n);
        }

        public int getStartLineHyphenEdit(int n) {
            return (LineBreaker.nGetLineFlag(this.mPtr, n) & 24) >> 3;
        }

        public boolean hasLineTab(int n) {
            boolean bl = (LineBreaker.nGetLineFlag(this.mPtr, n) & 536870912) != 0;
            return bl;
        }
    }

}

