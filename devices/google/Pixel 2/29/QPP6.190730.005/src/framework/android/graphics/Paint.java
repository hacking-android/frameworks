/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorSpace;
import android.graphics.MaskFilter;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rasterizer;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.TemporaryBuffer;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.fonts.FontVariationAxis;
import android.os.LocaleList;
import android.text.GraphicsOperations;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import com.android.internal.annotations.GuardedBy;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import libcore.util.NativeAllocationRegistry;

public class Paint {
    public static final int ANTI_ALIAS_FLAG = 1;
    public static final int AUTO_HINTING_TEXT_FLAG = 2048;
    public static final int BIDI_DEFAULT_LTR = 2;
    public static final int BIDI_DEFAULT_RTL = 3;
    private static final int BIDI_FLAG_MASK = 7;
    public static final int BIDI_FORCE_LTR = 4;
    public static final int BIDI_FORCE_RTL = 5;
    public static final int BIDI_LTR = 0;
    private static final int BIDI_MAX_FLAG_VALUE = 5;
    public static final int BIDI_RTL = 1;
    public static final int CURSOR_AFTER = 0;
    public static final int CURSOR_AT = 4;
    public static final int CURSOR_AT_OR_AFTER = 1;
    public static final int CURSOR_AT_OR_BEFORE = 3;
    public static final int CURSOR_BEFORE = 2;
    private static final int CURSOR_OPT_MAX_VALUE = 4;
    public static final int DEV_KERN_TEXT_FLAG = 256;
    public static final int DIRECTION_LTR = 0;
    public static final int DIRECTION_RTL = 1;
    public static final int DITHER_FLAG = 4;
    public static final int EMBEDDED_BITMAP_TEXT_FLAG = 1024;
    public static final int END_HYPHEN_EDIT_INSERT_ARMENIAN_HYPHEN = 3;
    public static final int END_HYPHEN_EDIT_INSERT_HYPHEN = 2;
    public static final int END_HYPHEN_EDIT_INSERT_MAQAF = 4;
    public static final int END_HYPHEN_EDIT_INSERT_UCAS_HYPHEN = 5;
    public static final int END_HYPHEN_EDIT_INSERT_ZWJ_AND_HYPHEN = 6;
    public static final int END_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int END_HYPHEN_EDIT_REPLACE_WITH_HYPHEN = 1;
    public static final int FAKE_BOLD_TEXT_FLAG = 32;
    public static final int FILTER_BITMAP_FLAG = 2;
    static final int HIDDEN_DEFAULT_PAINT_FLAGS = 1282;
    public static final int HINTING_OFF = 0;
    public static final int HINTING_ON = 1;
    public static final int LCD_RENDER_TEXT_FLAG = 512;
    public static final int LINEAR_TEXT_FLAG = 64;
    public static final int START_HYPHEN_EDIT_INSERT_HYPHEN = 1;
    public static final int START_HYPHEN_EDIT_INSERT_ZWJ = 2;
    public static final int START_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int STRIKE_THRU_TEXT_FLAG = 16;
    public static final int SUBPIXEL_TEXT_FLAG = 128;
    public static final int UNDERLINE_TEXT_FLAG = 8;
    public static final int VERTICAL_TEXT_FLAG = 4096;
    static final Align[] sAlignArray;
    private static final Object sCacheLock;
    static final Cap[] sCapArray;
    static final Join[] sJoinArray;
    @GuardedBy(value={"sCacheLock"})
    private static final HashMap<String, Integer> sMinikinLocaleListIdCache;
    static final Style[] sStyleArray;
    public int mBidiFlags = 2;
    private long mColor;
    private ColorFilter mColorFilter;
    private float mCompatScaling;
    private String mFontFeatureSettings;
    private String mFontVariationSettings;
    private boolean mHasCompatScaling;
    private float mInvCompatScaling;
    private LocaleList mLocales;
    private MaskFilter mMaskFilter;
    private long mNativeColorFilter;
    @UnsupportedAppUsage
    private long mNativePaint;
    private long mNativeShader;
    private PathEffect mPathEffect;
    private Shader mShader;
    private long mShadowLayerColor;
    private float mShadowLayerDx;
    private float mShadowLayerDy;
    private float mShadowLayerRadius;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Typeface mTypeface;
    private Xfermode mXfermode;

    static {
        sCacheLock = new Object();
        sMinikinLocaleListIdCache = new HashMap();
        sStyleArray = new Style[]{Style.FILL, Style.STROKE, Style.FILL_AND_STROKE};
        sCapArray = new Cap[]{Cap.BUTT, Cap.ROUND, Cap.SQUARE};
        sJoinArray = new Join[]{Join.MITER, Join.ROUND, Join.BEVEL};
        sAlignArray = new Align[]{Align.LEFT, Align.CENTER, Align.RIGHT};
    }

    public Paint() {
        this(0);
    }

    public Paint(int n) {
        this.mNativePaint = Paint.nInit();
        NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativePaint);
        this.setFlags(n | 1282);
        this.mInvCompatScaling = 1.0f;
        this.mCompatScaling = 1.0f;
        this.setTextLocales(LocaleList.getAdjustedDefault());
        this.mColor = Color.pack(-16777216);
    }

    public Paint(Paint paint) {
        this.mNativePaint = Paint.nInitWithPaint(paint.getNativeInstance());
        NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativePaint);
        this.setClassVariablesFrom(paint);
    }

    static /* synthetic */ long access$000() {
        return Paint.nGetNativeFinalizer();
    }

    private Xfermode installXfermode(Xfermode xfermode) {
        Xfermode xfermode2;
        int n;
        int n2 = xfermode != null ? xfermode.porterDuffMode : Xfermode.DEFAULT;
        if (n2 != (n = (xfermode2 = this.mXfermode) != null ? xfermode2.porterDuffMode : Xfermode.DEFAULT)) {
            Paint.nSetXfermode(this.mNativePaint, n2);
        }
        this.mXfermode = xfermode;
        return xfermode;
    }

    @CriticalNative
    private static native float nAscent(long var0);

    private static native int nBreakText(long var0, String var2, boolean var3, float var4, int var5, float[] var6);

    private static native int nBreakText(long var0, char[] var2, int var3, int var4, float var5, int var6, float[] var7);

    @CriticalNative
    private static native float nDescent(long var0);

    @CriticalNative
    private static native boolean nEqualsForTextMeasurement(long var0, long var2);

    private static native void nGetCharArrayBounds(long var0, char[] var2, int var3, int var4, int var5, Rect var6);

    @CriticalNative
    private static native int nGetEndHyphenEdit(long var0);

    @CriticalNative
    private static native boolean nGetFillPath(long var0, long var2, long var4);

    @CriticalNative
    private static native int nGetFlags(long var0);

    @FastNative
    private static native float nGetFontMetrics(long var0, FontMetrics var2);

    @FastNative
    private static native int nGetFontMetricsInt(long var0, FontMetricsInt var2);

    @CriticalNative
    private static native int nGetHinting(long var0);

    @CriticalNative
    private static native float nGetLetterSpacing(long var0);

    private static native long nGetNativeFinalizer();

    private static native int nGetOffsetForAdvance(long var0, char[] var2, int var3, int var4, int var5, int var6, boolean var7, float var8);

    private static native float nGetRunAdvance(long var0, char[] var2, int var3, int var4, int var5, int var6, boolean var7, int var8);

    @CriticalNative
    private static native int nGetStartHyphenEdit(long var0);

    @CriticalNative
    private static native float nGetStrikeThruPosition(long var0);

    @CriticalNative
    private static native float nGetStrikeThruThickness(long var0);

    private static native void nGetStringBounds(long var0, String var2, int var3, int var4, int var5, Rect var6);

    @CriticalNative
    private static native int nGetStrokeCap(long var0);

    @CriticalNative
    private static native int nGetStrokeJoin(long var0);

    @CriticalNative
    private static native float nGetStrokeMiter(long var0);

    @CriticalNative
    private static native float nGetStrokeWidth(long var0);

    @CriticalNative
    private static native int nGetStyle(long var0);

    private static native float nGetTextAdvances(long var0, String var2, int var3, int var4, int var5, int var6, int var7, float[] var8, int var9);

    private static native float nGetTextAdvances(long var0, char[] var2, int var3, int var4, int var5, int var6, int var7, float[] var8, int var9);

    @CriticalNative
    private static native int nGetTextAlign(long var0);

    private static native void nGetTextPath(long var0, int var2, String var3, int var4, int var5, float var6, float var7, long var8);

    private static native void nGetTextPath(long var0, int var2, char[] var3, int var4, int var5, float var6, float var7, long var8);

    private native int nGetTextRunCursor(long var1, String var3, int var4, int var5, int var6, int var7, int var8);

    private native int nGetTextRunCursor(long var1, char[] var3, int var4, int var5, int var6, int var7, int var8);

    @CriticalNative
    private static native float nGetTextScaleX(long var0);

    @CriticalNative
    private static native float nGetTextSize(long var0);

    @CriticalNative
    private static native float nGetTextSkewX(long var0);

    @CriticalNative
    private static native float nGetUnderlinePosition(long var0);

    @CriticalNative
    private static native float nGetUnderlineThickness(long var0);

    @CriticalNative
    private static native float nGetWordSpacing(long var0);

    private static native boolean nHasGlyph(long var0, int var2, String var3);

    @CriticalNative
    private static native boolean nHasShadowLayer(long var0);

    private static native long nInit();

    private static native long nInitWithPaint(long var0);

    @CriticalNative
    private static native boolean nIsElegantTextHeight(long var0);

    @CriticalNative
    private static native void nReset(long var0);

    @CriticalNative
    private static native void nSet(long var0, long var2);

    @CriticalNative
    private static native void nSetAlpha(long var0, int var2);

    @CriticalNative
    private static native void nSetAntiAlias(long var0, boolean var2);

    @CriticalNative
    private static native void nSetColor(long var0, int var2);

    @CriticalNative
    private static native void nSetColor(long var0, long var2, long var4);

    @CriticalNative
    private static native long nSetColorFilter(long var0, long var2);

    @CriticalNative
    private static native void nSetDither(long var0, boolean var2);

    @CriticalNative
    private static native void nSetElegantTextHeight(long var0, boolean var2);

    @CriticalNative
    private static native void nSetEndHyphenEdit(long var0, int var2);

    @CriticalNative
    private static native void nSetFakeBoldText(long var0, boolean var2);

    @CriticalNative
    private static native void nSetFilterBitmap(long var0, boolean var2);

    @CriticalNative
    private static native void nSetFlags(long var0, int var2);

    @FastNative
    private static native void nSetFontFeatureSettings(long var0, String var2);

    @CriticalNative
    private static native void nSetHinting(long var0, int var2);

    @CriticalNative
    private static native void nSetLetterSpacing(long var0, float var2);

    @CriticalNative
    private static native void nSetLinearText(long var0, boolean var2);

    @CriticalNative
    private static native long nSetMaskFilter(long var0, long var2);

    @CriticalNative
    private static native long nSetPathEffect(long var0, long var2);

    @CriticalNative
    private static native long nSetShader(long var0, long var2);

    @CriticalNative
    private static native void nSetShadowLayer(long var0, float var2, float var3, float var4, long var5, long var7);

    @CriticalNative
    private static native void nSetStartHyphenEdit(long var0, int var2);

    @CriticalNative
    private static native void nSetStrikeThruText(long var0, boolean var2);

    @CriticalNative
    private static native void nSetStrokeCap(long var0, int var2);

    @CriticalNative
    private static native void nSetStrokeJoin(long var0, int var2);

    @CriticalNative
    private static native void nSetStrokeMiter(long var0, float var2);

    @CriticalNative
    private static native void nSetStrokeWidth(long var0, float var2);

    @CriticalNative
    private static native void nSetStyle(long var0, int var2);

    @CriticalNative
    private static native void nSetSubpixelText(long var0, boolean var2);

    @CriticalNative
    private static native void nSetTextAlign(long var0, int var2);

    @FastNative
    private static native int nSetTextLocales(long var0, String var2);

    @CriticalNative
    private static native void nSetTextLocalesByMinikinLocaleListId(long var0, int var2);

    @CriticalNative
    private static native void nSetTextScaleX(long var0, float var2);

    @CriticalNative
    private static native void nSetTextSize(long var0, float var2);

    @CriticalNative
    private static native void nSetTextSkewX(long var0, float var2);

    @CriticalNative
    private static native void nSetTypeface(long var0, long var2);

    @CriticalNative
    private static native void nSetUnderlineText(long var0, boolean var2);

    @CriticalNative
    private static native void nSetWordSpacing(long var0, float var2);

    @CriticalNative
    private static native void nSetXfermode(long var0, int var2);

    private void setClassVariablesFrom(Paint paint) {
        this.mColor = paint.mColor;
        this.mColorFilter = paint.mColorFilter;
        this.mMaskFilter = paint.mMaskFilter;
        this.mPathEffect = paint.mPathEffect;
        this.mShader = paint.mShader;
        this.mNativeShader = paint.mNativeShader;
        this.mTypeface = paint.mTypeface;
        this.mXfermode = paint.mXfermode;
        this.mHasCompatScaling = paint.mHasCompatScaling;
        this.mCompatScaling = paint.mCompatScaling;
        this.mInvCompatScaling = paint.mInvCompatScaling;
        this.mBidiFlags = paint.mBidiFlags;
        this.mLocales = paint.mLocales;
        this.mFontFeatureSettings = paint.mFontFeatureSettings;
        this.mFontVariationSettings = paint.mFontVariationSettings;
        this.mShadowLayerRadius = paint.mShadowLayerRadius;
        this.mShadowLayerDx = paint.mShadowLayerDx;
        this.mShadowLayerDy = paint.mShadowLayerDy;
        this.mShadowLayerColor = paint.mShadowLayerColor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void syncTextLocalesWithMinikin() {
        Integer n;
        String string2 = this.mLocales.toLanguageTags();
        Object object = sCacheLock;
        synchronized (object) {
            n = sMinikinLocaleListIdCache.get(string2);
            if (n == null) {
                int n2 = Paint.nSetTextLocales(this.mNativePaint, string2);
                sMinikinLocaleListIdCache.put(string2, n2);
                return;
            }
        }
        Paint.nSetTextLocalesByMinikinLocaleListId(this.mNativePaint, n);
    }

    public float ascent() {
        return Paint.nAscent(this.mNativePaint);
    }

    public int breakText(CharSequence charSequence, int n, int n2, boolean bl, float f, float[] arrf) {
        if (charSequence != null) {
            if ((n | n2 | n2 - n | charSequence.length() - n2) >= 0) {
                if (charSequence.length() != 0 && n != n2) {
                    if (n == 0 && charSequence instanceof String && n2 == charSequence.length()) {
                        return this.breakText((String)charSequence, bl, f, arrf);
                    }
                    char[] arrc = TemporaryBuffer.obtain(n2 - n);
                    TextUtils.getChars(charSequence, n, n2, arrc, 0);
                    n = bl ? this.breakText(arrc, 0, n2 - n, f, arrf) : this.breakText(arrc, 0, -(n2 - n), f, arrf);
                    TemporaryBuffer.recycle(arrc);
                    return n;
                }
                return 0;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public int breakText(String string2, boolean bl, float f, float[] arrf) {
        if (string2 != null) {
            if (string2.length() == 0) {
                return 0;
            }
            if (!this.mHasCompatScaling) {
                return Paint.nBreakText(this.mNativePaint, string2, bl, f, this.mBidiFlags, arrf);
            }
            float f2 = this.getTextSize();
            this.setTextSize(this.mCompatScaling * f2);
            int n = Paint.nBreakText(this.mNativePaint, string2, bl, f * this.mCompatScaling, this.mBidiFlags, arrf);
            this.setTextSize(f2);
            if (arrf != null) {
                arrf[0] = arrf[0] * this.mInvCompatScaling;
            }
            return n;
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public int breakText(char[] arrc, int n, int n2, float f, float[] arrf) {
        if (arrc != null) {
            if (n >= 0 && arrc.length - n >= Math.abs(n2)) {
                if (arrc.length != 0 && n2 != 0) {
                    if (!this.mHasCompatScaling) {
                        return Paint.nBreakText(this.mNativePaint, arrc, n, n2, f, this.mBidiFlags, arrf);
                    }
                    float f2 = this.getTextSize();
                    this.setTextSize(this.mCompatScaling * f2);
                    n = Paint.nBreakText(this.mNativePaint, arrc, n, n2, f * this.mCompatScaling, this.mBidiFlags, arrf);
                    this.setTextSize(f2);
                    if (arrf != null) {
                        arrf[0] = arrf[0] * this.mInvCompatScaling;
                    }
                    return n;
                }
                return 0;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public void clearShadowLayer() {
        this.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
    }

    public float descent() {
        return Paint.nDescent(this.mNativePaint);
    }

    public boolean equalsForTextMeasurement(Paint paint) {
        return Paint.nEqualsForTextMeasurement(this.mNativePaint, paint.mNativePaint);
    }

    public int getAlpha() {
        return Math.round(Color.alpha(this.mColor) * 255.0f);
    }

    public int getBidiFlags() {
        return this.mBidiFlags;
    }

    public BlendMode getBlendMode() {
        Xfermode xfermode = this.mXfermode;
        if (xfermode == null) {
            return null;
        }
        return BlendMode.fromValue(xfermode.porterDuffMode);
    }

    public int getColor() {
        return Color.toArgb(this.mColor);
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public long getColorLong() {
        return this.mColor;
    }

    public int getEndHyphenEdit() {
        return Paint.nGetEndHyphenEdit(this.mNativePaint);
    }

    public boolean getFillPath(Path path, Path path2) {
        return Paint.nGetFillPath(this.mNativePaint, path.readOnlyNI(), path2.mutateNI());
    }

    public int getFlags() {
        return Paint.nGetFlags(this.mNativePaint);
    }

    public String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public float getFontMetrics(FontMetrics fontMetrics) {
        return Paint.nGetFontMetrics(this.mNativePaint, fontMetrics);
    }

    public FontMetrics getFontMetrics() {
        FontMetrics fontMetrics = new FontMetrics();
        this.getFontMetrics(fontMetrics);
        return fontMetrics;
    }

    public int getFontMetricsInt(FontMetricsInt fontMetricsInt) {
        return Paint.nGetFontMetricsInt(this.mNativePaint, fontMetricsInt);
    }

    public FontMetricsInt getFontMetricsInt() {
        FontMetricsInt fontMetricsInt = new FontMetricsInt();
        this.getFontMetricsInt(fontMetricsInt);
        return fontMetricsInt;
    }

    public float getFontSpacing() {
        return this.getFontMetrics(null);
    }

    public String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public int getHinting() {
        return Paint.nGetHinting(this.mNativePaint);
    }

    public float getLetterSpacing() {
        return Paint.nGetLetterSpacing(this.mNativePaint);
    }

    public MaskFilter getMaskFilter() {
        return this.mMaskFilter;
    }

    @UnsupportedAppUsage
    public long getNativeInstance() {
        Object object = this.mShader;
        long l = 0L;
        long l2 = object == null ? 0L : ((Shader)object).getNativeInstance();
        if (l2 != this.mNativeShader) {
            this.mNativeShader = l2;
            Paint.nSetShader(this.mNativePaint, this.mNativeShader);
        }
        if ((l2 = (object = this.mColorFilter) == null ? l : ((ColorFilter)object).getNativeInstance()) != this.mNativeColorFilter) {
            this.mNativeColorFilter = l2;
            Paint.nSetColorFilter(this.mNativePaint, this.mNativeColorFilter);
        }
        return this.mNativePaint;
    }

    public int getOffsetForAdvance(CharSequence charSequence, int n, int n2, int n3, int n4, boolean bl, float f) {
        if (charSequence != null) {
            if ((n3 | n | n2 | n4 | n - n3 | n2 - n | n4 - n2 | charSequence.length() - n4) >= 0) {
                char[] arrc = TemporaryBuffer.obtain(n4 - n3);
                TextUtils.getChars(charSequence, n3, n4, arrc, 0);
                n = this.getOffsetForAdvance(arrc, n - n3, n2 - n3, 0, n4 - n3, bl, f);
                TemporaryBuffer.recycle(arrc);
                return n + n3;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public int getOffsetForAdvance(char[] arrc, int n, int n2, int n3, int n4, boolean bl, float f) {
        if (arrc != null) {
            if ((n3 | n | n2 | n4 | n - n3 | n2 - n | n4 - n2 | arrc.length - n4) >= 0) {
                return Paint.nGetOffsetForAdvance(this.mNativePaint, arrc, n, n2, n3, n4, bl, f);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public PathEffect getPathEffect() {
        return this.mPathEffect;
    }

    @Deprecated
    public Rasterizer getRasterizer() {
        return null;
    }

    public float getRunAdvance(CharSequence charSequence, int n, int n2, int n3, int n4, boolean bl, int n5) {
        if (charSequence != null) {
            if ((n3 | n | n5 | n2 | n4 | n - n3 | n5 - n | n2 - n5 | n4 - n2 | charSequence.length() - n4) >= 0) {
                if (n2 == n) {
                    return 0.0f;
                }
                char[] arrc = TemporaryBuffer.obtain(n4 - n3);
                TextUtils.getChars(charSequence, n3, n4, arrc, 0);
                float f = this.getRunAdvance(arrc, n - n3, n2 - n3, 0, n4 - n3, bl, n5 - n3);
                TemporaryBuffer.recycle(arrc);
                return f;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public float getRunAdvance(char[] arrc, int n, int n2, int n3, int n4, boolean bl, int n5) {
        if (arrc != null) {
            if ((n3 | n | n5 | n2 | n4 | n - n3 | n5 - n | n2 - n5 | n4 - n2 | arrc.length - n4) >= 0) {
                if (n2 == n) {
                    return 0.0f;
                }
                return Paint.nGetRunAdvance(this.mNativePaint, arrc, n, n2, n3, n4, bl, n5);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public Shader getShader() {
        return this.mShader;
    }

    public int getShadowLayerColor() {
        return Color.toArgb(this.mShadowLayerColor);
    }

    public long getShadowLayerColorLong() {
        return this.mShadowLayerColor;
    }

    public float getShadowLayerDx() {
        return this.mShadowLayerDx;
    }

    public float getShadowLayerDy() {
        return this.mShadowLayerDy;
    }

    public float getShadowLayerRadius() {
        return this.mShadowLayerRadius;
    }

    public int getStartHyphenEdit() {
        return Paint.nGetStartHyphenEdit(this.mNativePaint);
    }

    public float getStrikeThruPosition() {
        return Paint.nGetStrikeThruPosition(this.mNativePaint);
    }

    public float getStrikeThruThickness() {
        return Paint.nGetStrikeThruThickness(this.mNativePaint);
    }

    public Cap getStrokeCap() {
        return sCapArray[Paint.nGetStrokeCap(this.mNativePaint)];
    }

    public Join getStrokeJoin() {
        return sJoinArray[Paint.nGetStrokeJoin(this.mNativePaint)];
    }

    public float getStrokeMiter() {
        return Paint.nGetStrokeMiter(this.mNativePaint);
    }

    public float getStrokeWidth() {
        return Paint.nGetStrokeWidth(this.mNativePaint);
    }

    public Style getStyle() {
        return sStyleArray[Paint.nGetStyle(this.mNativePaint)];
    }

    public Align getTextAlign() {
        return sAlignArray[Paint.nGetTextAlign(this.mNativePaint)];
    }

    public void getTextBounds(CharSequence charSequence, int n, int n2, Rect rect) {
        if ((n | n2 | n2 - n | charSequence.length() - n2) >= 0) {
            if (rect != null) {
                char[] arrc = TemporaryBuffer.obtain(n2 - n);
                TextUtils.getChars(charSequence, n, n2, arrc, 0);
                this.getTextBounds(arrc, 0, n2 - n, rect);
                TemporaryBuffer.recycle(arrc);
                return;
            }
            throw new NullPointerException("need bounds Rect");
        }
        throw new IndexOutOfBoundsException();
    }

    public void getTextBounds(String string2, int n, int n2, Rect rect) {
        if ((n | n2 | n2 - n | string2.length() - n2) >= 0) {
            if (rect != null) {
                Paint.nGetStringBounds(this.mNativePaint, string2, n, n2, this.mBidiFlags, rect);
                return;
            }
            throw new NullPointerException("need bounds Rect");
        }
        throw new IndexOutOfBoundsException();
    }

    public void getTextBounds(char[] arrc, int n, int n2, Rect rect) {
        if ((n | n2) >= 0 && n + n2 <= arrc.length) {
            if (rect != null) {
                Paint.nGetCharArrayBounds(this.mNativePaint, arrc, n, n2, this.mBidiFlags, rect);
                return;
            }
            throw new NullPointerException("need bounds Rect");
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public Locale getTextLocale() {
        return this.mLocales.get(0);
    }

    public LocaleList getTextLocales() {
        return this.mLocales;
    }

    public void getTextPath(String string2, int n, int n2, float f, float f2, Path path) {
        if ((n | n2 | n2 - n | string2.length() - n2) >= 0) {
            Paint.nGetTextPath(this.mNativePaint, this.mBidiFlags, string2, n, n2, f, f2, path.mutateNI());
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public void getTextPath(char[] arrc, int n, int n2, float f, float f2, Path path) {
        if ((n | n2) >= 0 && n + n2 <= arrc.length) {
            Paint.nGetTextPath(this.mNativePaint, this.mBidiFlags, arrc, n, n2, f, f2, path.mutateNI());
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public float getTextRunAdvances(char[] arrc, int n, int n2, int n3, int n4, boolean bl, float[] arrf, int n5) {
        if (arrc != null) {
            int n6 = arrc.length;
            int n7 = arrf == null ? 0 : arrf.length - (n5 + n2);
            if ((n | n2 | n3 | n4 | n5 | n - n3 | n4 - n2 | n3 + n4 - (n + n2) | n6 - (n3 + n4) | n7) >= 0) {
                if (arrc.length != 0 && n2 != 0) {
                    if (!this.mHasCompatScaling) {
                        long l = this.mNativePaint;
                        n7 = bl ? 5 : 4;
                        return Paint.nGetTextAdvances(l, arrc, n, n2, n3, n4, n7, arrf, n5);
                    }
                    float f = this.getTextSize();
                    this.setTextSize(this.mCompatScaling * f);
                    long l = this.mNativePaint;
                    n7 = bl ? 5 : 4;
                    float f2 = Paint.nGetTextAdvances(l, arrc, n, n2, n3, n4, n7, arrf, n5);
                    this.setTextSize(f);
                    if (arrf != null) {
                        for (n = n5; n < n5 + n2; ++n) {
                            arrf[n] = arrf[n] * this.mInvCompatScaling;
                        }
                    }
                    return this.mInvCompatScaling * f2;
                }
                return 0.0f;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public int getTextRunCursor(CharSequence charSequence, int n, int n2, boolean bl, int n3, int n4) {
        if (!(charSequence instanceof String || charSequence instanceof SpannedString || charSequence instanceof SpannableString)) {
            if (charSequence instanceof GraphicsOperations) {
                return ((GraphicsOperations)charSequence).getTextRunCursor(n, n2, bl, n3, n4, this);
            }
            int n5 = n2 - n;
            char[] arrc = TemporaryBuffer.obtain(n5);
            TextUtils.getChars(charSequence, n, n2, arrc, 0);
            n3 = this.getTextRunCursor(arrc, 0, n5, bl, n3 - n, n4);
            TemporaryBuffer.recycle(arrc);
            n2 = -1;
            n = n3 == -1 ? n2 : n3 + n;
            return n;
        }
        return this.getTextRunCursor(charSequence.toString(), n, n2, bl, n3, n4);
    }

    public int getTextRunCursor(String string2, int n, int n2, boolean bl, int n3, int n4) {
        if ((n | n2 | n3 | n2 - n | n3 - n | n2 - n3 | string2.length() - n2 | n4) >= 0 && n4 <= 4) {
            long l = this.mNativePaint;
            return this.nGetTextRunCursor(l, string2, n, n2, (int)bl, n3, n4);
        }
        throw new IndexOutOfBoundsException();
    }

    public int getTextRunCursor(char[] arrc, int n, int n2, boolean bl, int n3, int n4) {
        int n5 = n + n2;
        if ((n | n5 | n3 | n5 - n | n3 - n | n5 - n3 | arrc.length - n5 | n4) >= 0 && n4 <= 4) {
            long l = this.mNativePaint;
            return this.nGetTextRunCursor(l, arrc, n, n2, (int)bl, n3, n4);
        }
        throw new IndexOutOfBoundsException();
    }

    public float getTextScaleX() {
        return Paint.nGetTextScaleX(this.mNativePaint);
    }

    public float getTextSize() {
        return Paint.nGetTextSize(this.mNativePaint);
    }

    public float getTextSkewX() {
        return Paint.nGetTextSkewX(this.mNativePaint);
    }

    public int getTextWidths(CharSequence charSequence, int n, int n2, float[] arrf) {
        if (charSequence != null) {
            if ((n | n2 | n2 - n | charSequence.length() - n2) >= 0) {
                if (n2 - n <= arrf.length) {
                    if (charSequence.length() != 0 && n != n2) {
                        if (charSequence instanceof String) {
                            return this.getTextWidths((String)charSequence, n, n2, arrf);
                        }
                        if (!(charSequence instanceof SpannedString) && !(charSequence instanceof SpannableString)) {
                            if (charSequence instanceof GraphicsOperations) {
                                return ((GraphicsOperations)charSequence).getTextWidths(n, n2, arrf, this);
                            }
                            char[] arrc = TemporaryBuffer.obtain(n2 - n);
                            TextUtils.getChars(charSequence, n, n2, arrc, 0);
                            n = this.getTextWidths(arrc, 0, n2 - n, arrf);
                            TemporaryBuffer.recycle(arrc);
                            return n;
                        }
                        return this.getTextWidths(charSequence.toString(), n, n2, arrf);
                    }
                    return 0;
                }
                throw new ArrayIndexOutOfBoundsException();
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public int getTextWidths(String string2, int n, int n2, float[] arrf) {
        if (string2 != null) {
            if ((n | n2 | n2 - n | string2.length() - n2) >= 0) {
                if (n2 - n <= arrf.length) {
                    if (string2.length() != 0 && n != n2) {
                        if (!this.mHasCompatScaling) {
                            Paint.nGetTextAdvances(this.mNativePaint, string2, n, n2, n, n2, this.mBidiFlags, arrf, 0);
                            return n2 - n;
                        }
                        float f = this.getTextSize();
                        this.setTextSize(this.mCompatScaling * f);
                        Paint.nGetTextAdvances(this.mNativePaint, string2, n, n2, n, n2, this.mBidiFlags, arrf, 0);
                        this.setTextSize(f);
                        for (int i = 0; i < n2 - n; ++i) {
                            arrf[i] = arrf[i] * this.mInvCompatScaling;
                        }
                        return n2 - n;
                    }
                    return 0;
                }
                throw new ArrayIndexOutOfBoundsException();
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public int getTextWidths(String string2, float[] arrf) {
        return this.getTextWidths(string2, 0, string2.length(), arrf);
    }

    public int getTextWidths(char[] arrc, int n, int n2, float[] arrf) {
        if (arrc != null) {
            if ((n | n2) >= 0 && n + n2 <= arrc.length && n2 <= arrf.length) {
                if (arrc.length != 0 && n2 != 0) {
                    if (!this.mHasCompatScaling) {
                        Paint.nGetTextAdvances(this.mNativePaint, arrc, n, n2, n, n2, this.mBidiFlags, arrf, 0);
                        return n2;
                    }
                    float f = this.getTextSize();
                    this.setTextSize(this.mCompatScaling * f);
                    Paint.nGetTextAdvances(this.mNativePaint, arrc, n, n2, n, n2, this.mBidiFlags, arrf, 0);
                    this.setTextSize(f);
                    for (n = 0; n < n2; ++n) {
                        arrf[n] = arrf[n] * this.mInvCompatScaling;
                    }
                    return n2;
                }
                return 0;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public float getUnderlinePosition() {
        return Paint.nGetUnderlinePosition(this.mNativePaint);
    }

    public float getUnderlineThickness() {
        return Paint.nGetUnderlineThickness(this.mNativePaint);
    }

    public float getWordSpacing() {
        return Paint.nGetWordSpacing(this.mNativePaint);
    }

    public Xfermode getXfermode() {
        return this.mXfermode;
    }

    public boolean hasGlyph(String string2) {
        return Paint.nHasGlyph(this.mNativePaint, this.mBidiFlags, string2);
    }

    public boolean hasShadowLayer() {
        return Paint.nHasShadowLayer(this.mNativePaint);
    }

    public final boolean isAntiAlias() {
        int n = this.getFlags();
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public final boolean isDither() {
        boolean bl = (this.getFlags() & 4) != 0;
        return bl;
    }

    public boolean isElegantTextHeight() {
        return Paint.nIsElegantTextHeight(this.mNativePaint);
    }

    public final boolean isFakeBoldText() {
        boolean bl = (this.getFlags() & 32) != 0;
        return bl;
    }

    public final boolean isFilterBitmap() {
        boolean bl = (this.getFlags() & 2) != 0;
        return bl;
    }

    public final boolean isLinearText() {
        boolean bl = (this.getFlags() & 64) != 0;
        return bl;
    }

    public final boolean isStrikeThruText() {
        boolean bl = (this.getFlags() & 16) != 0;
        return bl;
    }

    public final boolean isSubpixelText() {
        boolean bl = (this.getFlags() & 128) != 0;
        return bl;
    }

    public final boolean isUnderlineText() {
        boolean bl = (this.getFlags() & 8) != 0;
        return bl;
    }

    public float measureText(CharSequence charSequence, int n, int n2) {
        if (charSequence != null) {
            if ((n | n2 | n2 - n | charSequence.length() - n2) >= 0) {
                if (charSequence.length() != 0 && n != n2) {
                    if (charSequence instanceof String) {
                        return this.measureText((String)charSequence, n, n2);
                    }
                    if (!(charSequence instanceof SpannedString) && !(charSequence instanceof SpannableString)) {
                        if (charSequence instanceof GraphicsOperations) {
                            return ((GraphicsOperations)charSequence).measureText(n, n2, this);
                        }
                        char[] arrc = TemporaryBuffer.obtain(n2 - n);
                        TextUtils.getChars(charSequence, n, n2, arrc, 0);
                        float f = this.measureText(arrc, 0, n2 - n);
                        TemporaryBuffer.recycle(arrc);
                        return f;
                    }
                    return this.measureText(charSequence.toString(), n, n2);
                }
                return 0.0f;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public float measureText(String string2) {
        if (string2 != null) {
            return this.measureText(string2, 0, string2.length());
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public float measureText(String string2, int n, int n2) {
        if (string2 != null) {
            if ((n | n2 | n2 - n | string2.length() - n2) >= 0) {
                if (string2.length() != 0 && n != n2) {
                    if (!this.mHasCompatScaling) {
                        return (float)Math.ceil(Paint.nGetTextAdvances(this.mNativePaint, string2, n, n2, n, n2, this.mBidiFlags, null, 0));
                    }
                    float f = this.getTextSize();
                    this.setTextSize(this.mCompatScaling * f);
                    float f2 = Paint.nGetTextAdvances(this.mNativePaint, string2, n, n2, n, n2, this.mBidiFlags, null, 0);
                    this.setTextSize(f);
                    return (float)Math.ceil(this.mInvCompatScaling * f2);
                }
                return 0.0f;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public float measureText(char[] arrc, int n, int n2) {
        if (arrc != null) {
            if ((n | n2) >= 0 && n + n2 <= arrc.length) {
                if (arrc.length != 0 && n2 != 0) {
                    if (!this.mHasCompatScaling) {
                        return (float)Math.ceil(Paint.nGetTextAdvances(this.mNativePaint, arrc, n, n2, n, n2, this.mBidiFlags, null, 0));
                    }
                    float f = this.getTextSize();
                    this.setTextSize(this.mCompatScaling * f);
                    float f2 = Paint.nGetTextAdvances(this.mNativePaint, arrc, n, n2, n, n2, this.mBidiFlags, null, 0);
                    this.setTextSize(f);
                    return (float)Math.ceil(this.mInvCompatScaling * f2);
                }
                return 0.0f;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("text cannot be null");
    }

    public void reset() {
        Paint.nReset(this.mNativePaint);
        this.setFlags(1282);
        this.mColor = Color.pack(-16777216);
        this.mColorFilter = null;
        this.mMaskFilter = null;
        this.mPathEffect = null;
        this.mShader = null;
        this.mNativeShader = 0L;
        this.mTypeface = null;
        this.mXfermode = null;
        this.mHasCompatScaling = false;
        this.mCompatScaling = 1.0f;
        this.mInvCompatScaling = 1.0f;
        this.mBidiFlags = 2;
        this.setTextLocales(LocaleList.getAdjustedDefault());
        this.setElegantTextHeight(false);
        this.mFontFeatureSettings = null;
        this.mFontVariationSettings = null;
        this.mShadowLayerRadius = 0.0f;
        this.mShadowLayerDx = 0.0f;
        this.mShadowLayerDy = 0.0f;
        this.mShadowLayerColor = Color.pack(0);
    }

    public void set(Paint paint) {
        if (this != paint) {
            Paint.nSet(this.mNativePaint, paint.mNativePaint);
            this.setClassVariablesFrom(paint);
        }
    }

    public void setARGB(int n, int n2, int n3, int n4) {
        this.setColor(n << 24 | n2 << 16 | n3 << 8 | n4);
    }

    public void setAlpha(int n) {
        ColorSpace colorSpace = Color.colorSpace(this.mColor);
        this.mColor = Color.pack(Color.red(this.mColor), Color.green(this.mColor), Color.blue(this.mColor), (float)n * 0.003921569f, colorSpace);
        Paint.nSetAlpha(this.mNativePaint, n);
    }

    public void setAntiAlias(boolean bl) {
        Paint.nSetAntiAlias(this.mNativePaint, bl);
    }

    public void setBidiFlags(int n) {
        if ((n &= 7) <= 5) {
            this.mBidiFlags = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown bidi flag: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setBlendMode(BlendMode object) {
        object = object != null ? object.getXfermode() : null;
        this.installXfermode((Xfermode)object);
    }

    public void setColor(int n) {
        Paint.nSetColor(this.mNativePaint, n);
        this.mColor = Color.pack(n);
    }

    public void setColor(long l) {
        ColorSpace colorSpace = Color.colorSpace(l);
        Paint.nSetColor(this.mNativePaint, colorSpace.getNativeInstance(), l);
        this.mColor = l;
    }

    public ColorFilter setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mNativeColorFilter = -1L;
        }
        this.mColorFilter = colorFilter;
        return colorFilter;
    }

    @UnsupportedAppUsage
    public void setCompatibilityScaling(float f) {
        if ((double)f == 1.0) {
            this.mHasCompatScaling = false;
            this.mInvCompatScaling = 1.0f;
            this.mCompatScaling = 1.0f;
        } else {
            this.mHasCompatScaling = true;
            this.mCompatScaling = f;
            this.mInvCompatScaling = 1.0f / f;
        }
    }

    public void setDither(boolean bl) {
        Paint.nSetDither(this.mNativePaint, bl);
    }

    public void setElegantTextHeight(boolean bl) {
        Paint.nSetElegantTextHeight(this.mNativePaint, bl);
    }

    public void setEndHyphenEdit(int n) {
        Paint.nSetEndHyphenEdit(this.mNativePaint, n);
    }

    public void setFakeBoldText(boolean bl) {
        Paint.nSetFakeBoldText(this.mNativePaint, bl);
    }

    public void setFilterBitmap(boolean bl) {
        Paint.nSetFilterBitmap(this.mNativePaint, bl);
    }

    public void setFlags(int n) {
        Paint.nSetFlags(this.mNativePaint, n);
    }

    public void setFontFeatureSettings(String string2) {
        String string3 = string2;
        if (string2 != null) {
            string3 = string2;
            if (string2.equals("")) {
                string3 = null;
            }
        }
        if (string3 == null && this.mFontFeatureSettings == null || string3 != null && string3.equals(this.mFontFeatureSettings)) {
            return;
        }
        this.mFontFeatureSettings = string3;
        Paint.nSetFontFeatureSettings(this.mNativePaint, string3);
    }

    public boolean setFontVariationSettings(String object) {
        String string2 = TextUtils.nullIfEmpty((String)object);
        object = this.mFontVariationSettings;
        if (!(string2 == object || string2 != null && string2.equals(object))) {
            if (string2 != null && string2.length() != 0) {
                Object object2;
                object = object2 = this.mTypeface;
                if (object2 == null) {
                    object = Typeface.DEFAULT;
                }
                FontVariationAxis[] arrfontVariationAxis = FontVariationAxis.fromFontVariationSettings(string2);
                object2 = new ArrayList<E>();
                for (FontVariationAxis fontVariationAxis : arrfontVariationAxis) {
                    if (!((Typeface)object).isSupportedAxes(fontVariationAxis.getOpenTypeTagValue())) continue;
                    ((ArrayList)object2).add(fontVariationAxis);
                }
                if (((ArrayList)object2).isEmpty()) {
                    return false;
                }
                this.mFontVariationSettings = string2;
                this.setTypeface(Typeface.createFromTypefaceWithVariation((Typeface)object, (List<FontVariationAxis>)object2));
                return true;
            }
            this.mFontVariationSettings = null;
            this.setTypeface(Typeface.createFromTypefaceWithVariation(this.mTypeface, Collections.<FontVariationAxis>emptyList()));
            return true;
        }
        return true;
    }

    public void setHinting(int n) {
        Paint.nSetHinting(this.mNativePaint, n);
    }

    public void setLetterSpacing(float f) {
        Paint.nSetLetterSpacing(this.mNativePaint, f);
    }

    public void setLinearText(boolean bl) {
        Paint.nSetLinearText(this.mNativePaint, bl);
    }

    public MaskFilter setMaskFilter(MaskFilter maskFilter) {
        long l = 0L;
        if (maskFilter != null) {
            l = maskFilter.native_instance;
        }
        Paint.nSetMaskFilter(this.mNativePaint, l);
        this.mMaskFilter = maskFilter;
        return maskFilter;
    }

    public PathEffect setPathEffect(PathEffect pathEffect) {
        long l = 0L;
        if (pathEffect != null) {
            l = pathEffect.native_instance;
        }
        Paint.nSetPathEffect(this.mNativePaint, l);
        this.mPathEffect = pathEffect;
        return pathEffect;
    }

    @Deprecated
    public Rasterizer setRasterizer(Rasterizer rasterizer) {
        return rasterizer;
    }

    public Shader setShader(Shader shader) {
        if (this.mShader != shader) {
            this.mNativeShader = -1L;
            Paint.nSetShader(this.mNativePaint, 0L);
        }
        this.mShader = shader;
        return shader;
    }

    public void setShadowLayer(float f, float f2, float f3, int n) {
        this.setShadowLayer(f, f2, f3, Color.pack(n));
    }

    public void setShadowLayer(float f, float f2, float f3, long l) {
        ColorSpace colorSpace = Color.colorSpace(l);
        Paint.nSetShadowLayer(this.mNativePaint, f, f2, f3, colorSpace.getNativeInstance(), l);
        this.mShadowLayerRadius = f;
        this.mShadowLayerDx = f2;
        this.mShadowLayerDy = f3;
        this.mShadowLayerColor = l;
    }

    public void setStartHyphenEdit(int n) {
        Paint.nSetStartHyphenEdit(this.mNativePaint, n);
    }

    public void setStrikeThruText(boolean bl) {
        Paint.nSetStrikeThruText(this.mNativePaint, bl);
    }

    public void setStrokeCap(Cap cap) {
        Paint.nSetStrokeCap(this.mNativePaint, cap.nativeInt);
    }

    public void setStrokeJoin(Join join) {
        Paint.nSetStrokeJoin(this.mNativePaint, join.nativeInt);
    }

    public void setStrokeMiter(float f) {
        Paint.nSetStrokeMiter(this.mNativePaint, f);
    }

    public void setStrokeWidth(float f) {
        Paint.nSetStrokeWidth(this.mNativePaint, f);
    }

    public void setStyle(Style style2) {
        Paint.nSetStyle(this.mNativePaint, style2.nativeInt);
    }

    public void setSubpixelText(boolean bl) {
        Paint.nSetSubpixelText(this.mNativePaint, bl);
    }

    public void setTextAlign(Align align) {
        Paint.nSetTextAlign(this.mNativePaint, align.nativeInt);
    }

    public void setTextLocale(Locale locale) {
        if (locale != null) {
            LocaleList localeList = this.mLocales;
            if (localeList != null && localeList.size() == 1 && locale.equals(this.mLocales.get(0))) {
                return;
            }
            this.mLocales = new LocaleList(locale);
            this.syncTextLocalesWithMinikin();
            return;
        }
        throw new IllegalArgumentException("locale cannot be null");
    }

    public void setTextLocales(LocaleList localeList) {
        if (localeList != null && !localeList.isEmpty()) {
            if (localeList.equals(this.mLocales)) {
                return;
            }
            this.mLocales = localeList;
            this.syncTextLocalesWithMinikin();
            return;
        }
        throw new IllegalArgumentException("locales cannot be null or empty");
    }

    public void setTextScaleX(float f) {
        Paint.nSetTextScaleX(this.mNativePaint, f);
    }

    public void setTextSize(float f) {
        Paint.nSetTextSize(this.mNativePaint, f);
    }

    public void setTextSkewX(float f) {
        Paint.nSetTextSkewX(this.mNativePaint, f);
    }

    public Typeface setTypeface(Typeface typeface) {
        long l = typeface == null ? 0L : typeface.native_instance;
        Paint.nSetTypeface(this.mNativePaint, l);
        this.mTypeface = typeface;
        return typeface;
    }

    public void setUnderlineText(boolean bl) {
        Paint.nSetUnderlineText(this.mNativePaint, bl);
    }

    public void setWordSpacing(float f) {
        Paint.nSetWordSpacing(this.mNativePaint, f);
    }

    public Xfermode setXfermode(Xfermode xfermode) {
        return this.installXfermode(xfermode);
    }

    public static enum Align {
        LEFT(0),
        CENTER(1),
        RIGHT(2);
        
        final int nativeInt;

        private Align(int n2) {
            this.nativeInt = n2;
        }
    }

    public static enum Cap {
        BUTT(0),
        ROUND(1),
        SQUARE(2);
        
        final int nativeInt;

        private Cap(int n2) {
            this.nativeInt = n2;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CursorOption {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EndHyphenEdit {
    }

    public static class FontMetrics {
        public float ascent;
        public float bottom;
        public float descent;
        public float leading;
        public float top;
    }

    public static class FontMetricsInt {
        public int ascent;
        public int bottom;
        public int descent;
        public int leading;
        public int top;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FontMetricsInt: top=");
            stringBuilder.append(this.top);
            stringBuilder.append(" ascent=");
            stringBuilder.append(this.ascent);
            stringBuilder.append(" descent=");
            stringBuilder.append(this.descent);
            stringBuilder.append(" bottom=");
            stringBuilder.append(this.bottom);
            stringBuilder.append(" leading=");
            stringBuilder.append(this.leading);
            return stringBuilder.toString();
        }
    }

    public static enum Join {
        MITER(0),
        ROUND(1),
        BEVEL(2);
        
        final int nativeInt;

        private Join(int n2) {
            this.nativeInt = n2;
        }
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Paint.class.getClassLoader(), (long)Paint.access$000());

        private NoImagePreloadHolder() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StartHyphenEdit {
    }

    public static enum Style {
        FILL(0),
        STROKE(1),
        FILL_AND_STROKE(2);
        
        final int nativeInt;

        private Style(int n2) {
            this.nativeInt = n2;
        }
    }

}

