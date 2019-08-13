/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Insets;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class GradientDrawable
extends Drawable {
    private static final float DEFAULT_INNER_RADIUS_RATIO = 3.0f;
    private static final float DEFAULT_THICKNESS_RATIO = 9.0f;
    public static final int LINE = 2;
    public static final int LINEAR_GRADIENT = 0;
    public static final int OVAL = 1;
    public static final int RADIAL_GRADIENT = 1;
    private static final int RADIUS_TYPE_FRACTION = 1;
    private static final int RADIUS_TYPE_FRACTION_PARENT = 2;
    private static final int RADIUS_TYPE_PIXELS = 0;
    public static final int RECTANGLE = 0;
    public static final int RING = 3;
    public static final int SWEEP_GRADIENT = 2;
    private int mAlpha = 255;
    private BlendModeColorFilter mBlendModeColorFilter;
    private ColorFilter mColorFilter;
    @UnsupportedAppUsage
    private final Paint mFillPaint = new Paint(1);
    private boolean mGradientIsDirty;
    private float mGradientRadius;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
    private GradientState mGradientState;
    private Paint mLayerPaint;
    private boolean mMutated;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124051827L)
    private Rect mPadding;
    private final Path mPath = new Path();
    private boolean mPathIsDirty = true;
    private final RectF mRect = new RectF();
    private Path mRingPath;
    @UnsupportedAppUsage
    private Paint mStrokePaint;

    public GradientDrawable() {
        this(new GradientState(Orientation.TOP_BOTTOM, null), null);
    }

    private GradientDrawable(GradientState gradientState, Resources resources) {
        this.mGradientState = gradientState;
        this.updateLocalState(resources);
    }

    public GradientDrawable(Orientation orientation, int[] arrn) {
        this(new GradientState(orientation, arrn), null);
    }

    private void applyThemeChildElements(Resources.Theme object) {
        TypedArray typedArray;
        GradientState gradientState = this.mGradientState;
        if (gradientState.mAttrSize != null) {
            typedArray = ((Resources.Theme)object).resolveAttributes(gradientState.mAttrSize, R.styleable.GradientDrawableSize);
            this.updateGradientDrawableSize(typedArray);
            typedArray.recycle();
        }
        if (gradientState.mAttrGradient != null) {
            typedArray = ((Resources.Theme)object).resolveAttributes(gradientState.mAttrGradient, R.styleable.GradientDrawableGradient);
            try {
                this.updateGradientDrawableGradient(((Resources.Theme)object).getResources(), typedArray);
            }
            finally {
                typedArray.recycle();
            }
        }
        if (gradientState.mAttrSolid != null) {
            typedArray = ((Resources.Theme)object).resolveAttributes(gradientState.mAttrSolid, R.styleable.GradientDrawableSolid);
            this.updateGradientDrawableSolid(typedArray);
            typedArray.recycle();
        }
        if (gradientState.mAttrStroke != null) {
            typedArray = ((Resources.Theme)object).resolveAttributes(gradientState.mAttrStroke, R.styleable.GradientDrawableStroke);
            this.updateGradientDrawableStroke(typedArray);
            typedArray.recycle();
        }
        if (gradientState.mAttrCorners != null) {
            typedArray = ((Resources.Theme)object).resolveAttributes(gradientState.mAttrCorners, R.styleable.DrawableCorners);
            this.updateDrawableCorners(typedArray);
            typedArray.recycle();
        }
        if (gradientState.mAttrPadding != null) {
            object = ((Resources.Theme)object).resolveAttributes(gradientState.mAttrPadding, R.styleable.GradientDrawablePadding);
            this.updateGradientDrawablePadding((TypedArray)object);
            ((TypedArray)object).recycle();
        }
    }

    private void buildPathIfDirty() {
        GradientState gradientState = this.mGradientState;
        if (this.mPathIsDirty) {
            this.ensureValidRect();
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, gradientState.mRadiusArray, Path.Direction.CW);
            this.mPathIsDirty = false;
        }
    }

    private Path buildRing(GradientState object) {
        if (!(this.mRingPath == null || ((GradientState)object).mUseLevelForShape && this.mPathIsDirty)) {
            return this.mRingPath;
        }
        this.mPathIsDirty = false;
        float f = ((GradientState)object).mUseLevelForShape ? (float)this.getLevel() * 360.0f / 10000.0f : 360.0f;
        RectF rectF = new RectF(this.mRect);
        float f2 = rectF.width() / 2.0f;
        float f3 = rectF.height() / 2.0f;
        float f4 = ((GradientState)object).mThickness != -1 ? (float)((GradientState)object).mThickness : rectF.width() / ((GradientState)object).mThicknessRatio;
        float f5 = ((GradientState)object).mInnerRadius != -1 ? (float)((GradientState)object).mInnerRadius : rectF.width() / ((GradientState)object).mInnerRadiusRatio;
        object = new RectF(rectF);
        ((RectF)object).inset(f2 - f5, f3 - f5);
        rectF = new RectF((RectF)object);
        rectF.inset(-f4, -f4);
        Path path = this.mRingPath;
        if (path == null) {
            this.mRingPath = new Path();
        } else {
            path.reset();
        }
        path = this.mRingPath;
        if (f < 360.0f && f > -360.0f) {
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(f2 + f5, f3);
            path.lineTo(f2 + f5 + f4, f3);
            path.arcTo(rectF, 0.0f, f, false);
            path.arcTo((RectF)object, f, -f, false);
            path.close();
        } else {
            path.addOval(rectF, Path.Direction.CW);
            path.addOval((RectF)object, Path.Direction.CCW);
        }
        return path;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean ensureValidRect() {
        block23 : {
            block31 : {
                block26 : {
                    block30 : {
                        block29 : {
                            block28 : {
                                block27 : {
                                    block24 : {
                                        block25 : {
                                            block22 : {
                                                if (this.mGradientIsDirty == false) return this.mRect.isEmpty() ^ true;
                                                this.mGradientIsDirty = false;
                                                var1_1 = this.getBounds();
                                                var2_2 = 0.0f;
                                                var3_3 = this.mStrokePaint;
                                                if (var3_3 != null) {
                                                    var2_2 = var3_3.getStrokeWidth() * 0.5f;
                                                }
                                                var4_4 = this.mGradientState;
                                                this.mRect.set((float)var1_1.left + var2_2, (float)var1_1.top + var2_2, (float)var1_1.right - var2_2, (float)var1_1.bottom - var2_2);
                                                var3_3 = var4_4.mGradientColors;
                                                if (var3_3 == null) return this.mRect.isEmpty() ^ true;
                                                var1_1 = this.mRect;
                                                var5_5 = var4_4.mGradient;
                                                var2_2 = 1.0f;
                                                if (var5_5 != 0) break block22;
                                                if (var4_4.mUseLevel) {
                                                    var2_2 = (float)this.getLevel() / 10000.0f;
                                                }
                                                switch (1.$SwitchMap$android$graphics$drawable$GradientDrawable$Orientation[var4_4.getOrientation().ordinal()]) {
                                                    default: {
                                                        var6_6 = var1_1.left;
                                                        var7_16 = var1_1.top;
                                                        var8_19 = var1_1.right * var2_2;
                                                        var9_22 = var1_1.bottom * var2_2;
                                                        var2_2 = var6_6;
                                                        ** break;
                                                    }
                                                    case 7: {
                                                        var6_7 = var1_1.left;
                                                        var7_16 = var1_1.top;
                                                        var8_19 = var1_1.right * var2_2;
                                                        var9_22 = var7_16;
                                                        var2_2 = var6_7;
                                                        ** break;
                                                    }
                                                    case 6: {
                                                        var6_8 = var1_1.left;
                                                        var7_16 = var1_1.bottom;
                                                        var8_19 = var1_1.right * var2_2;
                                                        var9_22 = var1_1.top * var2_2;
                                                        var2_2 = var6_8;
                                                        ** break;
                                                    }
                                                    case 5: {
                                                        var7_16 = var1_1.left;
                                                        var6_9 = var1_1.bottom;
                                                        var8_19 = var7_16;
                                                        var9_22 = var1_1.top * var2_2;
                                                        var2_2 = var7_16;
                                                        var7_16 = var6_9;
                                                        ** break;
                                                    }
                                                    case 4: {
                                                        var6_10 = var1_1.right;
                                                        var7_16 = var1_1.bottom;
                                                        var8_19 = var1_1.left * var2_2;
                                                        var9_22 = var1_1.top * var2_2;
                                                        var2_2 = var6_10;
                                                        ** break;
                                                    }
                                                    case 3: {
                                                        var6_11 = var1_1.right;
                                                        var7_16 = var1_1.top;
                                                        var8_19 = var1_1.left * var2_2;
                                                        var9_22 = var7_16;
                                                        var2_2 = var6_11;
                                                        ** break;
                                                    }
                                                    case 2: {
                                                        var6_12 = var1_1.right;
                                                        var7_16 = var1_1.top;
                                                        var8_19 = var1_1.left * var2_2;
                                                        var9_22 = var1_1.bottom * var2_2;
                                                        var2_2 = var6_12;
                                                        ** break;
                                                    }
                                                    case 1: 
                                                }
                                                var6_13 = var1_1.left;
                                                var7_16 = var1_1.top;
                                                var8_19 = var6_13;
                                                var9_22 = var1_1.bottom * var2_2;
                                                var2_2 = var6_13;
lbl75: // 8 sources:
                                                this.mFillPaint.setShader(new LinearGradient(var2_2, var7_16, var8_19, var9_22, var3_3, var4_4.mPositions, Shader.TileMode.CLAMP));
                                                break block23;
                                            }
                                            if (var4_4.mGradient != 1) break block24;
                                            var10_25 = var1_1.left;
                                            var11_27 = var1_1.right;
                                            var6_14 = var1_1.left;
                                            var12_29 = var4_4.mCenterX;
                                            var13_31 = var1_1.top;
                                            var9_23 = var1_1.bottom;
                                            var14_32 = var1_1.top;
                                            var15_34 = var4_4.mCenterY;
                                            var8_20 = var4_4.mGradientRadius;
                                            if (var4_4.mGradientRadiusType != 1) break block25;
                                            var2_2 = var4_4.mWidth >= 0 ? (float)var4_4.mWidth : var1_1.width();
                                            var7_17 = var4_4.mHeight >= 0 ? (float)var4_4.mHeight : var1_1.height();
                                            var2_2 = var8_20 * Math.min(var2_2, var7_17);
                                            ** GOTO lbl-1000
                                        }
                                        var2_2 = var8_20;
                                        if (var4_4.mGradientRadiusType == 2) {
                                            var7_17 = var8_20 * Math.min(var1_1.width(), var1_1.height());
                                        } else lbl-1000: // 2 sources:
                                        {
                                            var7_17 = var2_2;
                                        }
                                        var2_2 = var7_17;
                                        if (var4_4.mUseLevel) {
                                            var2_2 = var7_17 * ((float)this.getLevel() / 10000.0f);
                                        }
                                        this.mGradientRadius = var2_2;
                                        if (var2_2 <= 0.0f) {
                                            var2_2 = 0.001f;
                                        }
                                        this.mFillPaint.setShader(new RadialGradient(var10_25 + (var11_27 - var6_14) * var12_29, var13_31 + (var9_23 - var14_32) * var15_34, var2_2, var3_3, null, Shader.TileMode.CLAMP));
                                        break block23;
                                    }
                                    if (var4_4.mGradient != 2) break block23;
                                    var2_2 = var1_1.left;
                                    var9_24 = var1_1.right;
                                    var6_15 = var1_1.left;
                                    var10_26 = var4_4.mCenterX;
                                    var12_30 = var1_1.top;
                                    var11_28 = var1_1.bottom;
                                    var8_21 = var1_1.top;
                                    var7_18 = var4_4.mCenterY;
                                    var1_1 = var3_3;
                                    if (!var4_4.mUseLevel) break block26;
                                    var16_36 = var4_4.mTempColors;
                                    var17_37 = var3_3.length;
                                    if (var16_36 == null) break block27;
                                    var1_1 = var16_36;
                                    if (var16_36.length == var17_37 + 1) break block28;
                                }
                                var1_1 = new int[var17_37 + 1];
                                var4_4.mTempColors = var1_1;
                            }
                            System.arraycopy(var3_3, 0, var1_1, 0, var17_37);
                            var1_1[var17_37] = var3_3[var17_37 - 1];
                            var16_36 = var4_4.mTempPositions;
                            var14_33 = 1.0f / (float)(var17_37 - 1);
                            if (var16_36 == null) break block29;
                            var3_3 = var16_36;
                            if (var16_36.length == var17_37 + 1) break block30;
                        }
                        var3_3 = new float[var17_37 + 1];
                        var4_4.mTempPositions = var3_3;
                    }
                    var15_35 = (float)this.getLevel() / 10000.0f;
                    for (var5_5 = 0; var5_5 < var17_37; ++var5_5) {
                        var3_3[var5_5] = (int)((float)var5_5 * var14_33 * var15_35);
                    }
                    var3_3[var17_37] = (int)1.0f;
                    break block31;
                }
                var3_3 = null;
            }
            this.mFillPaint.setShader(new SweepGradient(var2_2 + (var9_24 - var6_15) * var10_26, var12_30 + (var11_28 - var8_21) * var7_18, var1_1, (float[])var3_3));
        }
        if (var4_4.mSolidColors != null) return this.mRect.isEmpty() ^ true;
        this.mFillPaint.setColor(-16777216);
        return this.mRect.isEmpty() ^ true;
    }

    private static float getFloatOrFraction(TypedArray object, int n, float f) {
        if ((object = ((TypedArray)object).peekValue(n)) != null) {
            n = ((TypedValue)object).type == 6 ? 1 : 0;
            f = n != 0 ? ((TypedValue)object).getFraction(1.0f, 1.0f) : ((TypedValue)object).getFloat();
        }
        return f;
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            Object object;
            if (n2 != 2 || n > n3) continue;
            String string2 = xmlPullParser.getName();
            if (string2.equals("size")) {
                object = GradientDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableSize);
                this.updateGradientDrawableSize((TypedArray)object);
                ((TypedArray)object).recycle();
                continue;
            }
            if (string2.equals("gradient")) {
                object = GradientDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableGradient);
                this.updateGradientDrawableGradient(resources, (TypedArray)object);
                ((TypedArray)object).recycle();
                continue;
            }
            if (string2.equals("solid")) {
                object = GradientDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableSolid);
                this.updateGradientDrawableSolid((TypedArray)object);
                ((TypedArray)object).recycle();
                continue;
            }
            if (string2.equals("stroke")) {
                object = GradientDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawableStroke);
                this.updateGradientDrawableStroke((TypedArray)object);
                ((TypedArray)object).recycle();
                continue;
            }
            if (string2.equals("corners")) {
                object = GradientDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.DrawableCorners);
                this.updateDrawableCorners((TypedArray)object);
                ((TypedArray)object).recycle();
                continue;
            }
            if (string2.equals("padding")) {
                object = GradientDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawablePadding);
                this.updateGradientDrawablePadding((TypedArray)object);
                ((TypedArray)object).recycle();
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad element under <shape>: ");
            ((StringBuilder)object).append(string2);
            Log.w("drawable", ((StringBuilder)object).toString());
        }
    }

    static boolean isOpaque(int n) {
        boolean bl = (n >> 24 & 255) == 255;
        return bl;
    }

    private boolean isOpaqueForState() {
        Paint paint;
        if (this.mGradientState.mStrokeWidth >= 0 && (paint = this.mStrokePaint) != null && !GradientDrawable.isOpaque(paint.getColor())) {
            return false;
        }
        return this.mGradientState.mGradientColors != null || GradientDrawable.isOpaque(this.mFillPaint.getColor());
    }

    private int modulateAlpha(int n) {
        int n2 = this.mAlpha;
        return n * (n2 + (n2 >> 7)) >> 8;
    }

    private void setStrokeInternal(int n, int n2, float f, float f2) {
        if (this.mStrokePaint == null) {
            this.mStrokePaint = new Paint(1);
            this.mStrokePaint.setStyle(Paint.Style.STROKE);
        }
        this.mStrokePaint.setStrokeWidth(n);
        this.mStrokePaint.setColor(n2);
        DashPathEffect dashPathEffect = null;
        if (f > 0.0f) {
            dashPathEffect = new DashPathEffect(new float[]{f, f2}, 0.0f);
        }
        this.mStrokePaint.setPathEffect(dashPathEffect);
        this.mGradientIsDirty = true;
        this.invalidateSelf();
    }

    private void updateDrawableCorners(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrCorners = typedArray.extractThemeAttrs();
        int n = typedArray.getDimensionPixelSize(0, (int)gradientState.mRadius);
        this.setCornerRadius(n);
        int n2 = typedArray.getDimensionPixelSize(1, n);
        int n3 = typedArray.getDimensionPixelSize(2, n);
        int n4 = typedArray.getDimensionPixelSize(3, n);
        int n5 = typedArray.getDimensionPixelSize(4, n);
        if (n2 != n || n3 != n || n4 != n || n5 != n) {
            this.setCornerRadii(new float[]{n2, n2, n3, n3, n5, n5, n4, n4});
        }
    }

    private void updateGradientDrawableGradient(Resources resources, TypedArray object) {
        float f;
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        gradientState.mAttrGradient = ((TypedArray)object).extractThemeAttrs();
        gradientState.mCenterX = GradientDrawable.getFloatOrFraction((TypedArray)object, 5, gradientState.mCenterX);
        gradientState.mCenterY = GradientDrawable.getFloatOrFraction((TypedArray)object, 6, gradientState.mCenterY);
        gradientState.mUseLevel = ((TypedArray)object).getBoolean(2, gradientState.mUseLevel);
        gradientState.mGradient = ((TypedArray)object).getInt(4, gradientState.mGradient);
        int n = gradientState.mGradientColors != null ? 1 : 0;
        boolean bl = gradientState.hasCenterColor();
        int n2 = n != 0 ? gradientState.mGradientColors[0] : 0;
        int n3 = bl ? gradientState.mGradientColors[1] : 0;
        n = gradientState.hasCenterColor() ? gradientState.mGradientColors[2] : (n != 0 ? gradientState.mGradientColors[1] : 0);
        int n4 = ((TypedArray)object).getColor(0, n2);
        n2 = !((TypedArray)object).hasValue(8) && !bl ? 0 : 1;
        n3 = ((TypedArray)object).getColor(8, n3);
        n = ((TypedArray)object).getColor(1, n);
        if (n2 != 0) {
            gradientState.mGradientColors = new int[3];
            gradientState.mGradientColors[0] = n4;
            gradientState.mGradientColors[1] = n3;
            gradientState.mGradientColors[2] = n;
            gradientState.mPositions = new float[3];
            gradientState.mPositions[0] = 0.0f;
            float[] arrf = gradientState.mPositions;
            f = gradientState.mCenterX != 0.5f ? gradientState.mCenterX : gradientState.mCenterY;
            arrf[1] = f;
            gradientState.mPositions[2] = 1.0f;
        } else {
            gradientState.mGradientColors = new int[2];
            gradientState.mGradientColors[0] = n4;
            gradientState.mGradientColors[1] = n;
        }
        gradientState.mAngle = ((int)((TypedArray)object).getFloat(3, gradientState.mAngle) % 360 + 360) % 360;
        object = ((TypedArray)object).peekValue(7);
        if (object != null) {
            if (((TypedValue)object).type == 6) {
                f = ((TypedValue)object).getFraction(1.0f, 1.0f);
                n = (((TypedValue)object).data >> 0 & 15) == 1 ? 2 : 1;
            } else if (((TypedValue)object).type == 5) {
                f = ((TypedValue)object).getDimension(resources.getDisplayMetrics());
                n = 0;
            } else {
                f = ((TypedValue)object).getFloat();
                n = 0;
            }
            gradientState.mGradientRadius = f;
            gradientState.mGradientRadiusType = n;
        }
    }

    private void updateGradientDrawablePadding(TypedArray typedArray) {
        Object object = this.mGradientState;
        ((GradientState)object).mChangingConfigurations |= typedArray.getChangingConfigurations();
        ((GradientState)object).mAttrPadding = typedArray.extractThemeAttrs();
        if (((GradientState)object).mPadding == null) {
            ((GradientState)object).mPadding = new Rect();
        }
        object = ((GradientState)object).mPadding;
        ((Rect)object).set(typedArray.getDimensionPixelOffset(0, ((Rect)object).left), typedArray.getDimensionPixelOffset(1, ((Rect)object).top), typedArray.getDimensionPixelOffset(2, ((Rect)object).right), typedArray.getDimensionPixelOffset(3, ((Rect)object).bottom));
        this.mPadding = object;
    }

    private void updateGradientDrawableSize(TypedArray typedArray) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrSize = typedArray.extractThemeAttrs();
        gradientState.mWidth = typedArray.getDimensionPixelSize(1, gradientState.mWidth);
        gradientState.mHeight = typedArray.getDimensionPixelSize(0, gradientState.mHeight);
    }

    private void updateGradientDrawableSolid(TypedArray object) {
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        gradientState.mAttrSolid = ((TypedArray)object).extractThemeAttrs();
        if ((object = ((TypedArray)object).getColorStateList(0)) != null) {
            this.setColor((ColorStateList)object);
        }
    }

    private void updateGradientDrawableStroke(TypedArray typedArray) {
        ColorStateList colorStateList;
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrStroke = typedArray.extractThemeAttrs();
        int n = typedArray.getDimensionPixelSize(0, Math.max(0, gradientState.mStrokeWidth));
        float f = typedArray.getDimension(2, gradientState.mStrokeDashWidth);
        ColorStateList colorStateList2 = colorStateList = typedArray.getColorStateList(1);
        if (colorStateList == null) {
            colorStateList2 = gradientState.mStrokeColors;
        }
        if (f != 0.0f) {
            this.setStroke(n, colorStateList2, f, typedArray.getDimension(3, gradientState.mStrokeDashGap));
        } else {
            this.setStroke(n, colorStateList2);
        }
    }

    private void updateLocalState(Resources object) {
        Object object2;
        int n;
        object = this.mGradientState;
        if (((GradientState)object).mSolidColors != null) {
            object2 = this.getState();
            n = ((GradientState)object).mSolidColors.getColorForState((int[])object2, 0);
            this.mFillPaint.setColor(n);
        } else if (((GradientState)object).mGradientColors == null) {
            this.mFillPaint.setColor(0);
        } else {
            this.mFillPaint.setColor(-16777216);
        }
        this.mPadding = ((GradientState)object).mPadding;
        if (((GradientState)object).mStrokeWidth >= 0) {
            this.mStrokePaint = new Paint(1);
            this.mStrokePaint.setStyle(Paint.Style.STROKE);
            this.mStrokePaint.setStrokeWidth(((GradientState)object).mStrokeWidth);
            if (((GradientState)object).mStrokeColors != null) {
                object2 = this.getState();
                n = ((GradientState)object).mStrokeColors.getColorForState((int[])object2, 0);
                this.mStrokePaint.setColor(n);
            }
            if (((GradientState)object).mStrokeDashWidth != 0.0f) {
                object2 = new DashPathEffect(new float[]{((GradientState)object).mStrokeDashWidth, ((GradientState)object).mStrokeDashGap}, 0.0f);
                this.mStrokePaint.setPathEffect((PathEffect)object2);
            }
        }
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, ((GradientState)object).mTint, ((GradientState)object).mBlendMode);
        this.mGradientIsDirty = true;
        ((GradientState)object).computeOpacity();
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ColorStateList colorStateList;
        int n;
        GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mThemeAttrs = typedArray.extractThemeAttrs();
        gradientState.mShape = typedArray.getInt(3, gradientState.mShape);
        gradientState.mDither = typedArray.getBoolean(0, gradientState.mDither);
        if (gradientState.mShape == 3) {
            gradientState.mInnerRadius = typedArray.getDimensionPixelSize(7, gradientState.mInnerRadius);
            if (gradientState.mInnerRadius == -1) {
                gradientState.mInnerRadiusRatio = typedArray.getFloat(4, gradientState.mInnerRadiusRatio);
            }
            gradientState.mThickness = typedArray.getDimensionPixelSize(8, gradientState.mThickness);
            if (gradientState.mThickness == -1) {
                gradientState.mThicknessRatio = typedArray.getFloat(5, gradientState.mThicknessRatio);
            }
            gradientState.mUseLevelForShape = typedArray.getBoolean(6, gradientState.mUseLevelForShape);
        }
        if ((n = typedArray.getInt(9, -1)) != -1) {
            gradientState.mBlendMode = Drawable.parseBlendMode(n, BlendMode.SRC_IN);
        }
        if ((colorStateList = typedArray.getColorStateList(1)) != null) {
            gradientState.mTint = colorStateList;
        }
        gradientState.mOpticalInsets = Insets.of(typedArray.getDimensionPixelSize(10, gradientState.mOpticalInsets.left), typedArray.getDimensionPixelSize(11, gradientState.mOpticalInsets.top), typedArray.getDimensionPixelSize(12, gradientState.mOpticalInsets.right), typedArray.getDimensionPixelSize(13, gradientState.mOpticalInsets.bottom));
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        GradientState gradientState = this.mGradientState;
        if (gradientState == null) {
            return;
        }
        gradientState.setDensity(Drawable.resolveDensity(theme.getResources(), 0));
        if (gradientState.mThemeAttrs != null) {
            TypedArray typedArray = theme.resolveAttributes(gradientState.mThemeAttrs, R.styleable.GradientDrawable);
            this.updateStateFromTypedArray(typedArray);
            typedArray.recycle();
        }
        if (gradientState.mTint != null && gradientState.mTint.canApplyTheme()) {
            gradientState.mTint = gradientState.mTint.obtainForTheme(theme);
        }
        if (gradientState.mSolidColors != null && gradientState.mSolidColors.canApplyTheme()) {
            gradientState.mSolidColors = gradientState.mSolidColors.obtainForTheme(theme);
        }
        if (gradientState.mStrokeColors != null && gradientState.mStrokeColors.canApplyTheme()) {
            gradientState.mStrokeColors = gradientState.mStrokeColors.obtainForTheme(theme);
        }
        this.applyThemeChildElements(theme);
        this.updateLocalState(theme.getResources());
    }

    @Override
    public boolean canApplyTheme() {
        GradientState gradientState = this.mGradientState;
        boolean bl = gradientState != null && gradientState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        int n;
        int n2;
        boolean bl;
        int n3;
        Object object;
        int n4;
        float f;
        int n5;
        GradientState gradientState;
        boolean bl2;
        block32 : {
            block33 : {
                if (!this.ensureValidRect()) {
                    return;
                }
                n = this.mFillPaint.getAlpha();
                object = this.mStrokePaint;
                boolean bl3 = false;
                n2 = object != null ? ((Paint)object).getAlpha() : 0;
                n3 = this.modulateAlpha(n);
                n5 = this.modulateAlpha(n2);
                bl2 = n5 > 0 && (object = this.mStrokePaint) != null && ((Paint)object).getStrokeWidth() > 0.0f;
                n4 = n3 > 0 ? 1 : 0;
                gradientState = this.mGradientState;
                object = this.mColorFilter;
                if (object == null) {
                    object = this.mBlendModeColorFilter;
                }
                bl = bl3;
                if (!bl2) break block32;
                bl = bl3;
                if (n4 == 0) break block32;
                bl = bl3;
                if (gradientState.mShape == 2) break block32;
                bl = bl3;
                if (n5 >= 255) break block32;
                if (this.mAlpha < 255) break block33;
                bl = bl3;
                if (object == null) break block32;
            }
            bl = true;
        }
        if (bl) {
            if (this.mLayerPaint == null) {
                this.mLayerPaint = new Paint();
            }
            this.mLayerPaint.setDither(gradientState.mDither);
            this.mLayerPaint.setAlpha(this.mAlpha);
            this.mLayerPaint.setColorFilter((ColorFilter)object);
            f = this.mStrokePaint.getStrokeWidth();
            canvas.saveLayer(this.mRect.left - f, this.mRect.top - f, this.mRect.right + f, this.mRect.bottom + f, this.mLayerPaint);
            this.mFillPaint.setColorFilter(null);
            this.mStrokePaint.setColorFilter(null);
        } else {
            Object object2 = object;
            GradientState gradientState2 = gradientState;
            this.mFillPaint.setAlpha(n3);
            this.mFillPaint.setDither(gradientState2.mDither);
            this.mFillPaint.setColorFilter((ColorFilter)object2);
            if (object2 != null && gradientState2.mSolidColors == null) {
                this.mFillPaint.setColor(this.mAlpha << 24);
            }
            if (bl2) {
                this.mStrokePaint.setAlpha(n5);
                this.mStrokePaint.setDither(gradientState2.mDither);
                this.mStrokePaint.setColorFilter((ColorFilter)object2);
            }
        }
        n4 = gradientState.mShape;
        if (n4 != 0) {
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 == 3) {
                        object = this.buildRing(gradientState);
                        canvas.drawPath((Path)object, this.mFillPaint);
                        if (bl2) {
                            canvas.drawPath((Path)object, this.mStrokePaint);
                        }
                    }
                } else {
                    object = this.mRect;
                    f = ((RectF)object).centerY();
                    if (bl2) {
                        canvas.drawLine(((RectF)object).left, f, ((RectF)object).right, f, this.mStrokePaint);
                    }
                }
            } else {
                canvas.drawOval(this.mRect, this.mFillPaint);
                if (bl2) {
                    canvas.drawOval(this.mRect, this.mStrokePaint);
                }
            }
        } else if (gradientState.mRadiusArray != null) {
            this.buildPathIfDirty();
            canvas.drawPath(this.mPath, this.mFillPaint);
            if (bl2) {
                canvas.drawPath(this.mPath, this.mStrokePaint);
            }
        } else if (gradientState.mRadius > 0.0f) {
            f = Math.min(gradientState.mRadius, Math.min(this.mRect.width(), this.mRect.height()) * 0.5f);
            canvas.drawRoundRect(this.mRect, f, f, this.mFillPaint);
            if (bl2) {
                canvas.drawRoundRect(this.mRect, f, f, this.mStrokePaint);
            }
        } else {
            if (this.mFillPaint.getColor() != 0 || object != null || this.mFillPaint.getShader() != null) {
                canvas.drawRect(this.mRect, this.mFillPaint);
            }
            if (bl2) {
                canvas.drawRect(this.mRect, this.mStrokePaint);
            }
        }
        if (bl) {
            canvas.restore();
        } else {
            this.mFillPaint.setAlpha(n);
            if (bl2) {
                this.mStrokePaint.setAlpha(n2);
            }
        }
    }

    @Override
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mGradientState.getChangingConfigurations();
    }

    public ColorStateList getColor() {
        return this.mGradientState.mSolidColors;
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public int[] getColors() {
        int[] arrn = this.mGradientState.mGradientColors == null ? null : (int[])this.mGradientState.mGradientColors.clone();
        return arrn;
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        this.mGradientState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mGradientState;
    }

    public float[] getCornerRadii() {
        return (float[])this.mGradientState.mRadiusArray.clone();
    }

    public float getCornerRadius() {
        return this.mGradientState.mRadius;
    }

    public float getGradientCenterX() {
        return this.mGradientState.mCenterX;
    }

    public float getGradientCenterY() {
        return this.mGradientState.mCenterY;
    }

    public float getGradientRadius() {
        if (this.mGradientState.mGradient != 1) {
            return 0.0f;
        }
        this.ensureValidRect();
        return this.mGradientRadius;
    }

    public int getGradientType() {
        return this.mGradientState.mGradient;
    }

    public int getInnerRadius() {
        return this.mGradientState.mInnerRadius;
    }

    public float getInnerRadiusRatio() {
        return this.mGradientState.mInnerRadiusRatio;
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mGradientState.mHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return this.mGradientState.mWidth;
    }

    @Override
    public int getOpacity() {
        int n = this.mAlpha == 255 && this.mGradientState.mOpaqueOverBounds && this.isOpaqueForState() ? -1 : -3;
        return n;
    }

    @Override
    public Insets getOpticalInsets() {
        return this.mGradientState.mOpticalInsets;
    }

    public Orientation getOrientation() {
        return this.mGradientState.getOrientation();
    }

    @Override
    public void getOutline(Outline outline) {
        Paint paint;
        GradientState gradientState = this.mGradientState;
        Rect rect = this.getBounds();
        int n = gradientState.mOpaqueOverShape && (this.mGradientState.mStrokeWidth <= 0 || (paint = this.mStrokePaint) == null || paint.getAlpha() == this.mFillPaint.getAlpha()) ? 1 : 0;
        float f = n != 0 ? (float)this.modulateAlpha(this.mFillPaint.getAlpha()) / 255.0f : 0.0f;
        outline.setAlpha(f);
        n = gradientState.mShape;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return;
                }
                paint = this.mStrokePaint;
                f = paint == null ? 1.0E-4f : paint.getStrokeWidth() * 0.5f;
                float f2 = rect.centerY();
                n = (int)Math.floor(f2 - f);
                int n2 = (int)Math.ceil(f2 + f);
                outline.setRect(rect.left, n, rect.right, n2);
                return;
            }
            outline.setOval(rect);
            return;
        }
        if (gradientState.mRadiusArray != null) {
            this.buildPathIfDirty();
            outline.setConvexPath(this.mPath);
            return;
        }
        f = 0.0f;
        if (gradientState.mRadius > 0.0f) {
            f = Math.min(gradientState.mRadius, (float)Math.min(rect.width(), rect.height()) * 0.5f);
        }
        outline.setRoundRect(rect, f);
    }

    @Override
    public boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2 != null) {
            rect.set(rect2);
            return true;
        }
        return super.getPadding(rect);
    }

    public int getShape() {
        return this.mGradientState.mShape;
    }

    public int getThickness() {
        return this.mGradientState.mThickness;
    }

    public float getThicknessRatio() {
        return this.mGradientState.mThicknessRatio;
    }

    public boolean getUseLevel() {
        return this.mGradientState.mUseLevel;
    }

    @Override
    public boolean hasFocusStateSpecified() {
        GradientState gradientState = this.mGradientState;
        boolean bl = gradientState.mSolidColors != null && gradientState.mSolidColors.hasFocusStateSpecified() || gradientState.mStrokeColors != null && gradientState.mStrokeColors.hasFocusStateSpecified() || gradientState.mTint != null && gradientState.mTint.hasFocusStateSpecified();
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mGradientState.setDensity(Drawable.resolveDensity(resources, 0));
        TypedArray typedArray = GradientDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientDrawable);
        this.updateStateFromTypedArray(typedArray);
        typedArray.recycle();
        this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        this.updateLocalState(resources);
    }

    @Override
    public boolean isStateful() {
        GradientState gradientState = this.mGradientState;
        boolean bl = super.isStateful() || gradientState.mSolidColors != null && gradientState.mSolidColors.isStateful() || gradientState.mStrokeColors != null && gradientState.mStrokeColors.isStateful() || gradientState.mTint != null && gradientState.mTint.isStateful();
        return bl;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mGradientState = new GradientState(this.mGradientState, null);
            this.updateLocalState(null);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mRingPath = null;
        this.mPathIsDirty = true;
        this.mGradientIsDirty = true;
    }

    @Override
    protected boolean onLevelChange(int n) {
        super.onLevelChange(n);
        this.mGradientIsDirty = true;
        this.mPathIsDirty = true;
        this.invalidateSelf();
        return true;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        int n;
        boolean bl = false;
        GradientState gradientState = this.mGradientState;
        ColorStateList colorStateList = gradientState.mSolidColors;
        boolean bl2 = bl;
        if (colorStateList != null) {
            n = colorStateList.getColorForState(arrn, 0);
            bl2 = bl;
            if (this.mFillPaint.getColor() != n) {
                this.mFillPaint.setColor(n);
                bl2 = true;
            }
        }
        Paint paint = this.mStrokePaint;
        bl = bl2;
        if (paint != null) {
            colorStateList = gradientState.mStrokeColors;
            bl = bl2;
            if (colorStateList != null) {
                n = colorStateList.getColorForState(arrn, 0);
                bl = bl2;
                if (paint.getColor() != n) {
                    paint.setColor(n);
                    bl = true;
                }
            }
        }
        bl2 = bl;
        if (gradientState.mTint != null) {
            bl2 = bl;
            if (gradientState.mBlendMode != null) {
                this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, gradientState.mTint, gradientState.mBlendMode);
                bl2 = true;
            }
        }
        if (bl2) {
            this.invalidateSelf();
            return true;
        }
        return false;
    }

    @Override
    public void setAlpha(int n) {
        if (n != this.mAlpha) {
            this.mAlpha = n;
            this.invalidateSelf();
        }
    }

    public void setAntiAlias(boolean bl) {
        this.mFillPaint.setAntiAlias(bl);
    }

    public void setColor(int n) {
        this.mGradientState.setSolidColors(ColorStateList.valueOf(n));
        this.mFillPaint.setColor(n);
        this.invalidateSelf();
    }

    public void setColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            this.setColor(0);
        } else {
            int n = colorStateList.getColorForState(this.getState(), 0);
            this.mGradientState.setSolidColors(colorStateList);
            this.mFillPaint.setColor(n);
            this.invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter != this.mColorFilter) {
            this.mColorFilter = colorFilter;
            this.invalidateSelf();
        }
    }

    public void setColors(int[] arrn) {
        this.setColors(arrn, null);
    }

    public void setColors(int[] arrn, float[] arrf) {
        this.mGradientState.setGradientColors(arrn);
        this.mGradientState.mPositions = arrf;
        this.mGradientIsDirty = true;
        this.invalidateSelf();
    }

    public void setCornerRadii(float[] arrf) {
        this.mGradientState.setCornerRadii(arrf);
        this.mPathIsDirty = true;
        this.invalidateSelf();
    }

    public void setCornerRadius(float f) {
        this.mGradientState.setCornerRadius(f);
        this.mPathIsDirty = true;
        this.invalidateSelf();
    }

    @Override
    public void setDither(boolean bl) {
        if (bl != this.mGradientState.mDither) {
            this.mGradientState.mDither = bl;
            this.invalidateSelf();
        }
    }

    public void setGradientCenter(float f, float f2) {
        this.mGradientState.setGradientCenter(f, f2);
        this.mGradientIsDirty = true;
        this.invalidateSelf();
    }

    public void setGradientRadius(float f) {
        this.mGradientState.setGradientRadius(f, 0);
        this.mGradientIsDirty = true;
        this.invalidateSelf();
    }

    public void setGradientType(int n) {
        this.mGradientState.setGradientType(n);
        this.mGradientIsDirty = true;
        this.invalidateSelf();
    }

    public void setInnerRadius(int n) {
        this.mGradientState.mInnerRadius = n;
        this.mPathIsDirty = true;
        this.invalidateSelf();
    }

    public void setInnerRadiusRatio(float f) {
        if (!(f <= 0.0f)) {
            this.mGradientState.mInnerRadiusRatio = f;
            this.mPathIsDirty = true;
            this.invalidateSelf();
            return;
        }
        throw new IllegalArgumentException("Ratio must be greater than zero");
    }

    public void setOrientation(Orientation orientation) {
        this.mGradientState.setOrientation(orientation);
        this.mGradientIsDirty = true;
        this.invalidateSelf();
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        if (this.mGradientState.mPadding == null) {
            this.mGradientState.mPadding = new Rect();
        }
        this.mGradientState.mPadding.set(n, n2, n3, n4);
        this.mPadding = this.mGradientState.mPadding;
        this.invalidateSelf();
    }

    public void setShape(int n) {
        this.mRingPath = null;
        this.mPathIsDirty = true;
        this.mGradientState.setShape(n);
        this.invalidateSelf();
    }

    public void setSize(int n, int n2) {
        this.mGradientState.setSize(n, n2);
        this.mPathIsDirty = true;
        this.invalidateSelf();
    }

    public void setStroke(int n, int n2) {
        this.setStroke(n, n2, 0.0f, 0.0f);
    }

    public void setStroke(int n, int n2, float f, float f2) {
        this.mGradientState.setStroke(n, ColorStateList.valueOf(n2), f, f2);
        this.setStrokeInternal(n, n2, f, f2);
    }

    public void setStroke(int n, ColorStateList colorStateList) {
        this.setStroke(n, colorStateList, 0.0f, 0.0f);
    }

    public void setStroke(int n, ColorStateList colorStateList, float f, float f2) {
        this.mGradientState.setStroke(n, colorStateList, f, f2);
        int n2 = colorStateList == null ? 0 : colorStateList.getColorForState(this.getState(), 0);
        this.setStrokeInternal(n, n2, f, f2);
    }

    public void setThickness(int n) {
        this.mGradientState.mThickness = n;
        this.mPathIsDirty = true;
        this.invalidateSelf();
    }

    public void setThicknessRatio(float f) {
        if (!(f <= 0.0f)) {
            this.mGradientState.mThicknessRatio = f;
            this.mPathIsDirty = true;
            this.invalidateSelf();
            return;
        }
        throw new IllegalArgumentException("Ratio must be greater than zero");
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        GradientState gradientState = this.mGradientState;
        gradientState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, gradientState.mTint, blendMode);
        this.invalidateSelf();
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        GradientState gradientState = this.mGradientState;
        gradientState.mTint = colorStateList;
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, gradientState.mBlendMode);
        this.invalidateSelf();
    }

    public void setUseLevel(boolean bl) {
        this.mGradientState.mUseLevel = bl;
        this.mGradientIsDirty = true;
        this.invalidateSelf();
    }

    @Override
    public void setXfermode(Xfermode xfermode) {
        super.setXfermode(xfermode);
        this.mFillPaint.setXfermode(xfermode);
    }

    static final class GradientState
    extends Drawable.ConstantState {
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public int mAngle = 0;
        int[] mAttrCorners;
        int[] mAttrGradient;
        int[] mAttrPadding;
        int[] mAttrSize;
        int[] mAttrSolid;
        int[] mAttrStroke;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        float mCenterX = 0.5f;
        float mCenterY = 0.5f;
        public int mChangingConfigurations;
        int mDensity = 160;
        public boolean mDither = false;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public int mGradient = 0;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public int[] mGradientColors;
        float mGradientRadius = 0.5f;
        int mGradientRadiusType = 0;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public int mHeight = -1;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public int mInnerRadius = -1;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public float mInnerRadiusRatio = 3.0f;
        boolean mOpaqueOverBounds;
        boolean mOpaqueOverShape;
        public Insets mOpticalInsets = Insets.NONE;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public Orientation mOrientation;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public Rect mPadding = null;
        @UnsupportedAppUsage
        public float[] mPositions;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public float mRadius = 0.0f;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public float[] mRadiusArray = null;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public int mShape = 0;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public ColorStateList mSolidColors;
        public ColorStateList mStrokeColors;
        @UnsupportedAppUsage(trackingBug=124050917L)
        public float mStrokeDashGap = 0.0f;
        @UnsupportedAppUsage(trackingBug=124050917L)
        public float mStrokeDashWidth = 0.0f;
        @UnsupportedAppUsage(trackingBug=124050917L)
        public int mStrokeWidth = -1;
        public int[] mTempColors;
        public float[] mTempPositions;
        int[] mThemeAttrs;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050218L)
        public int mThickness = -1;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050218L)
        public float mThicknessRatio = 9.0f;
        ColorStateList mTint = null;
        boolean mUseLevel = false;
        boolean mUseLevelForShape = true;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050917L)
        public int mWidth = -1;

        public GradientState(GradientState gradientState, Resources resources) {
            this.mChangingConfigurations = gradientState.mChangingConfigurations;
            this.mShape = gradientState.mShape;
            this.mGradient = gradientState.mGradient;
            this.mAngle = gradientState.mAngle;
            this.mOrientation = gradientState.mOrientation;
            this.mSolidColors = gradientState.mSolidColors;
            Object object = gradientState.mGradientColors;
            if (object != null) {
                this.mGradientColors = (int[])object.clone();
            }
            if ((object = gradientState.mPositions) != null) {
                this.mPositions = (float[])object.clone();
            }
            this.mStrokeColors = gradientState.mStrokeColors;
            this.mStrokeWidth = gradientState.mStrokeWidth;
            this.mStrokeDashWidth = gradientState.mStrokeDashWidth;
            this.mStrokeDashGap = gradientState.mStrokeDashGap;
            this.mRadius = gradientState.mRadius;
            object = gradientState.mRadiusArray;
            if (object != null) {
                this.mRadiusArray = (float[])object.clone();
            }
            if ((object = gradientState.mPadding) != null) {
                this.mPadding = new Rect((Rect)object);
            }
            this.mWidth = gradientState.mWidth;
            this.mHeight = gradientState.mHeight;
            this.mInnerRadiusRatio = gradientState.mInnerRadiusRatio;
            this.mThicknessRatio = gradientState.mThicknessRatio;
            this.mInnerRadius = gradientState.mInnerRadius;
            this.mThickness = gradientState.mThickness;
            this.mDither = gradientState.mDither;
            this.mOpticalInsets = gradientState.mOpticalInsets;
            this.mCenterX = gradientState.mCenterX;
            this.mCenterY = gradientState.mCenterY;
            this.mGradientRadius = gradientState.mGradientRadius;
            this.mGradientRadiusType = gradientState.mGradientRadiusType;
            this.mUseLevel = gradientState.mUseLevel;
            this.mUseLevelForShape = gradientState.mUseLevelForShape;
            this.mOpaqueOverBounds = gradientState.mOpaqueOverBounds;
            this.mOpaqueOverShape = gradientState.mOpaqueOverShape;
            this.mTint = gradientState.mTint;
            this.mBlendMode = gradientState.mBlendMode;
            this.mThemeAttrs = gradientState.mThemeAttrs;
            this.mAttrSize = gradientState.mAttrSize;
            this.mAttrGradient = gradientState.mAttrGradient;
            this.mAttrSolid = gradientState.mAttrSolid;
            this.mAttrStroke = gradientState.mAttrStroke;
            this.mAttrCorners = gradientState.mAttrCorners;
            this.mAttrPadding = gradientState.mAttrPadding;
            this.mDensity = Drawable.resolveDensity(resources, gradientState.mDensity);
            int n = gradientState.mDensity;
            int n2 = this.mDensity;
            if (n != n2) {
                this.applyDensityScaling(n, n2);
            }
        }

        public GradientState(Orientation orientation, int[] arrn) {
            this.setOrientation(orientation);
            this.setGradientColors(arrn);
        }

        private void applyDensityScaling(int n, int n2) {
            Object object;
            float f;
            int n3 = this.mInnerRadius;
            if (n3 > 0) {
                this.mInnerRadius = Drawable.scaleFromDensity(n3, n, n2, true);
            }
            if ((n3 = this.mThickness) > 0) {
                this.mThickness = Drawable.scaleFromDensity(n3, n, n2, true);
            }
            if (this.mOpticalInsets != Insets.NONE) {
                this.mOpticalInsets = Insets.of(Drawable.scaleFromDensity(this.mOpticalInsets.left, n, n2, true), Drawable.scaleFromDensity(this.mOpticalInsets.top, n, n2, true), Drawable.scaleFromDensity(this.mOpticalInsets.right, n, n2, true), Drawable.scaleFromDensity(this.mOpticalInsets.bottom, n, n2, true));
            }
            if ((object = this.mPadding) != null) {
                object.left = Drawable.scaleFromDensity(object.left, n, n2, false);
                object = this.mPadding;
                object.top = Drawable.scaleFromDensity(object.top, n, n2, false);
                object = this.mPadding;
                object.right = Drawable.scaleFromDensity(object.right, n, n2, false);
                object = this.mPadding;
                object.bottom = Drawable.scaleFromDensity(object.bottom, n, n2, false);
            }
            if ((f = this.mRadius) > 0.0f) {
                this.mRadius = Drawable.scaleFromDensity(f, n, n2);
            }
            if ((object = this.mRadiusArray) != null) {
                object[0] = Drawable.scaleFromDensity((int)object[0], n, n2, true);
                object = this.mRadiusArray;
                object[1] = Drawable.scaleFromDensity((int)object[1], n, n2, true);
                object = this.mRadiusArray;
                object[2] = Drawable.scaleFromDensity((int)object[2], n, n2, true);
                object = this.mRadiusArray;
                object[3] = Drawable.scaleFromDensity((int)object[3], n, n2, true);
            }
            if ((n3 = this.mStrokeWidth) > 0) {
                this.mStrokeWidth = Drawable.scaleFromDensity(n3, n, n2, true);
            }
            if (this.mStrokeDashWidth > 0.0f) {
                this.mStrokeDashWidth = Drawable.scaleFromDensity(this.mStrokeDashGap, n, n2);
            }
            if ((f = this.mStrokeDashGap) > 0.0f) {
                this.mStrokeDashGap = Drawable.scaleFromDensity(f, n, n2);
            }
            if (this.mGradientRadiusType == 0) {
                this.mGradientRadius = Drawable.scaleFromDensity(this.mGradientRadius, n, n2);
            }
            if ((n3 = this.mWidth) > 0) {
                this.mWidth = Drawable.scaleFromDensity(n3, n, n2, true);
            }
            if ((n3 = this.mHeight) > 0) {
                this.mHeight = Drawable.scaleFromDensity(n3, n, n2, true);
            }
        }

        private void computeOpacity() {
            boolean bl = false;
            this.mOpaqueOverBounds = false;
            this.mOpaqueOverShape = false;
            if (this.mGradientColors != null) {
                int[] arrn;
                for (int i = 0; i < (arrn = this.mGradientColors).length; ++i) {
                    if (GradientDrawable.isOpaque(arrn[i])) continue;
                    return;
                }
            }
            if (this.mGradientColors == null && this.mSolidColors == null) {
                return;
            }
            this.mOpaqueOverShape = true;
            boolean bl2 = bl;
            if (this.mShape == 0) {
                bl2 = bl;
                if (this.mRadius <= 0.0f) {
                    bl2 = bl;
                    if (this.mRadiusArray == null) {
                        bl2 = true;
                    }
                }
            }
            this.mOpaqueOverBounds = bl2;
        }

        private int getAngleFromOrientation(Orientation orientation) {
            if (orientation != null) {
                switch (orientation) {
                    default: {
                        return 0;
                    }
                    case TL_BR: {
                        return 315;
                    }
                    case BL_TR: {
                        return 45;
                    }
                    case BOTTOM_TOP: {
                        return 90;
                    }
                    case BR_TL: {
                        return 135;
                    }
                    case RIGHT_LEFT: {
                        return 180;
                    }
                    case TR_BL: {
                        return 225;
                    }
                    case TOP_BOTTOM: 
                }
                return 270;
            }
            return 0;
        }

        private void updateGradientStateOrientation() {
            if (this.mGradient == 0) {
                int n = this.mAngle;
                if (n % 45 == 0) {
                    Orientation orientation = n != 0 ? (n != 45 ? (n != 90 ? (n != 135 ? (n != 180 ? (n != 225 ? (n != 270 ? (n != 315 ? Orientation.LEFT_RIGHT : Orientation.TL_BR) : Orientation.TOP_BOTTOM) : Orientation.TR_BL) : Orientation.RIGHT_LEFT) : Orientation.BR_TL) : Orientation.BOTTOM_TOP) : Orientation.BL_TR) : Orientation.LEFT_RIGHT;
                    this.mOrientation = orientation;
                } else {
                    throw new IllegalArgumentException("Linear gradient requires 'angle' attribute to be a multiple of 45");
                }
            }
        }

        @Override
        public boolean canApplyTheme() {
            ColorStateList colorStateList;
            boolean bl = this.mThemeAttrs != null || this.mAttrSize != null || this.mAttrGradient != null || this.mAttrSolid != null || this.mAttrStroke != null || this.mAttrCorners != null || this.mAttrPadding != null || (colorStateList = this.mTint) != null && colorStateList.canApplyTheme() || (colorStateList = this.mStrokeColors) != null && colorStateList.canApplyTheme() || (colorStateList = this.mSolidColors) != null && colorStateList.canApplyTheme() || super.canApplyTheme();
            return bl;
        }

        @Override
        public int getChangingConfigurations() {
            int n = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mStrokeColors;
            int n2 = 0;
            int n3 = colorStateList != null ? colorStateList.getChangingConfigurations() : 0;
            colorStateList = this.mSolidColors;
            int n4 = colorStateList != null ? colorStateList.getChangingConfigurations() : 0;
            colorStateList = this.mTint;
            if (colorStateList != null) {
                n2 = colorStateList.getChangingConfigurations();
            }
            return n | n3 | n4 | n2;
        }

        public Orientation getOrientation() {
            this.updateGradientStateOrientation();
            return this.mOrientation;
        }

        public boolean hasCenterColor() {
            int[] arrn = this.mGradientColors;
            boolean bl = arrn != null && arrn.length == 3;
            return bl;
        }

        @Override
        public Drawable newDrawable() {
            return new GradientDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            GradientState gradientState = Drawable.resolveDensity(resources, this.mDensity) != this.mDensity ? new GradientState(this, resources) : this;
            return new GradientDrawable(gradientState, resources);
        }

        public void setCornerRadii(float[] arrf) {
            this.mRadiusArray = arrf;
            if (arrf == null) {
                this.mRadius = 0.0f;
            }
            this.computeOpacity();
        }

        public void setCornerRadius(float f) {
            float f2 = f;
            if (f < 0.0f) {
                f2 = 0.0f;
            }
            this.mRadius = f2;
            this.mRadiusArray = null;
            this.computeOpacity();
        }

        public final void setDensity(int n) {
            if (this.mDensity != n) {
                int n2 = this.mDensity;
                this.mDensity = n;
                this.applyDensityScaling(n2, n);
            }
        }

        public void setGradientCenter(float f, float f2) {
            this.mCenterX = f;
            this.mCenterY = f2;
        }

        public void setGradientColors(int[] arrn) {
            this.mGradientColors = arrn;
            this.mSolidColors = null;
            this.computeOpacity();
        }

        public void setGradientRadius(float f, int n) {
            this.mGradientRadius = f;
            this.mGradientRadiusType = n;
        }

        public void setGradientType(int n) {
            this.mGradient = n;
        }

        public void setOrientation(Orientation orientation) {
            this.mAngle = this.getAngleFromOrientation(orientation);
            this.mOrientation = orientation;
        }

        public void setShape(int n) {
            this.mShape = n;
            this.computeOpacity();
        }

        public void setSize(int n, int n2) {
            this.mWidth = n;
            this.mHeight = n2;
        }

        public void setSolidColors(ColorStateList colorStateList) {
            this.mGradientColors = null;
            this.mSolidColors = colorStateList;
            this.computeOpacity();
        }

        public void setStroke(int n, ColorStateList colorStateList, float f, float f2) {
            this.mStrokeWidth = n;
            this.mStrokeColors = colorStateList;
            this.mStrokeDashWidth = f;
            this.mStrokeDashGap = f2;
            this.computeOpacity();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GradientType {
    }

    public static enum Orientation {
        TOP_BOTTOM,
        TR_BL,
        RIGHT_LEFT,
        BR_TL,
        BOTTOM_TOP,
        BL_TR,
        LEFT_RIGHT,
        TL_BR;
        
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RadiusType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Shape {
    }

}

