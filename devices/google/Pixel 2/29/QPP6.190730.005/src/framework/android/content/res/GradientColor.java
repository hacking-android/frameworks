/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content.res;

import android.content.res.ComplexColor;
import android.content.res.ConstantState;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class GradientColor
extends ComplexColor {
    private static final boolean DBG_GRADIENT = false;
    private static final String TAG = "GradientColor";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private int mCenterColor = 0;
    private float mCenterX = 0.0f;
    private float mCenterY = 0.0f;
    private int mChangingConfigurations;
    private int mDefaultColor;
    private int mEndColor = 0;
    private float mEndX = 0.0f;
    private float mEndY = 0.0f;
    private GradientColorFactory mFactory;
    private float mGradientRadius = 0.0f;
    private int mGradientType = 0;
    private boolean mHasCenterColor = false;
    private int[] mItemColors;
    private float[] mItemOffsets;
    private int[][] mItemsThemeAttrs;
    private Shader mShader = null;
    private int mStartColor = 0;
    private float mStartX = 0.0f;
    private float mStartY = 0.0f;
    private int[] mThemeAttrs;
    private int mTileMode = 0;

    private GradientColor() {
    }

    private GradientColor(GradientColor arrn) {
        if (arrn != null) {
            this.mChangingConfigurations = arrn.mChangingConfigurations;
            this.mDefaultColor = arrn.mDefaultColor;
            this.mShader = arrn.mShader;
            this.mGradientType = arrn.mGradientType;
            this.mCenterX = arrn.mCenterX;
            this.mCenterY = arrn.mCenterY;
            this.mStartX = arrn.mStartX;
            this.mStartY = arrn.mStartY;
            this.mEndX = arrn.mEndX;
            this.mEndY = arrn.mEndY;
            this.mStartColor = arrn.mStartColor;
            this.mCenterColor = arrn.mCenterColor;
            this.mEndColor = arrn.mEndColor;
            this.mHasCenterColor = arrn.mHasCenterColor;
            this.mGradientRadius = arrn.mGradientRadius;
            this.mTileMode = arrn.mTileMode;
            int[] arrn2 = arrn.mItemColors;
            if (arrn2 != null) {
                this.mItemColors = (int[])arrn2.clone();
            }
            if ((arrn2 = arrn.mItemOffsets) != null) {
                this.mItemOffsets = (float[])arrn2.clone();
            }
            if ((arrn2 = arrn.mThemeAttrs) != null) {
                this.mThemeAttrs = (int[])arrn2.clone();
            }
            if ((arrn = arrn.mItemsThemeAttrs) != null) {
                this.mItemsThemeAttrs = (int[][])arrn.clone();
            }
        }
    }

    private void applyItemsAttrsTheme(Resources.Theme theme) {
        if (this.mItemsThemeAttrs == null) {
            return;
        }
        boolean bl = false;
        int[][] arrn = this.mItemsThemeAttrs;
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            boolean bl2 = bl;
            if (arrn[i] != null) {
                TypedArray typedArray = theme.resolveAttributes(arrn[i], R.styleable.GradientColorItem);
                arrn[i] = typedArray.extractThemeAttrs(arrn[i]);
                if (arrn[i] != null) {
                    bl = true;
                }
                int[] arrn2 = this.mItemColors;
                arrn2[i] = typedArray.getColor(0, arrn2[i]);
                arrn2 = this.mItemOffsets;
                arrn2[i] = (int)typedArray.getFloat(1, arrn2[i]);
                this.mChangingConfigurations |= typedArray.getChangingConfigurations();
                typedArray.recycle();
                bl2 = bl;
            }
            bl = bl2;
        }
        if (!bl) {
            this.mItemsThemeAttrs = null;
        }
    }

    private void applyRootAttrsTheme(Resources.Theme object) {
        object = ((Resources.Theme)object).resolveAttributes(this.mThemeAttrs, R.styleable.GradientColor);
        this.mThemeAttrs = ((TypedArray)object).extractThemeAttrs(this.mThemeAttrs);
        this.updateRootElementState((TypedArray)object);
        this.mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        ((TypedArray)object).recycle();
    }

    private void applyTheme(Resources.Theme theme) {
        if (this.mThemeAttrs != null) {
            this.applyRootAttrsTheme(theme);
        }
        if (this.mItemsThemeAttrs != null) {
            this.applyItemsAttrsTheme(theme);
        }
        this.onColorsChange();
    }

    public static GradientColor createFromXml(Resources resources, XmlResourceParser xmlResourceParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        AttributeSet attributeSet = Xml.asAttributeSet(xmlResourceParser);
        while ((n = xmlResourceParser.next()) != 2 && n != 1) {
        }
        if (n == 2) {
            return GradientColor.createFromXmlInner(resources, xmlResourceParser, attributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    static GradientColor createFromXmlInner(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Object object2 = xmlPullParser.getName();
        if (((String)object2).equals("gradient")) {
            object2 = new GradientColor();
            GradientColor.super.inflate((Resources)object, xmlPullParser, attributeSet, theme);
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(": invalid gradient color tag ");
        ((StringBuilder)object).append((String)object2);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    private void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = Resources.obtainAttributes(resources, theme, attributeSet, R.styleable.GradientColor);
        this.updateRootElementState(typedArray);
        this.mChangingConfigurations |= typedArray.getChangingConfigurations();
        typedArray.recycle();
        this.validateXmlContent();
        this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        this.onColorsChange();
    }

    private void inflateChildElements(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = xmlPullParser.getDepth() + 1;
        float[] arrf = new float[20];
        int[] arrn = new int[arrf.length];
        int[][] arrarrn = new int[arrf.length][];
        int n4 = 0;
        boolean bl = false;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3 || !xmlPullParser.getName().equals("item")) continue;
            TypedArray typedArray = Resources.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.GradientColorItem);
            boolean bl2 = typedArray.hasValue(0);
            boolean bl3 = typedArray.hasValue(1);
            if (bl2 && bl3) {
                int[] arrn2 = typedArray.extractThemeAttrs();
                n2 = typedArray.getColor(0, 0);
                float f = typedArray.getFloat(1, 0.0f);
                this.mChangingConfigurations |= typedArray.getChangingConfigurations();
                typedArray.recycle();
                if (arrn2 != null) {
                    bl = true;
                }
                arrn = GrowingArrayUtils.append(arrn, n4, n2);
                arrf = GrowingArrayUtils.append(arrf, n4, f);
                arrarrn = GrowingArrayUtils.append(arrarrn, n4, arrn2);
                ++n4;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
            ((StringBuilder)object).append(": <item> tag requires a 'color' attribute and a 'offset' attribute!");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        if (n4 > 0) {
            if (bl) {
                this.mItemsThemeAttrs = new int[n4][];
                System.arraycopy(arrarrn, 0, this.mItemsThemeAttrs, 0, n4);
            } else {
                this.mItemsThemeAttrs = null;
            }
            this.mItemColors = new int[n4];
            this.mItemOffsets = new float[n4];
            System.arraycopy(arrn, 0, this.mItemColors, 0, n4);
            System.arraycopy(arrf, 0, this.mItemOffsets, 0, n4);
        }
    }

    private void onColorsChange() {
        int n;
        float[] arrf = null;
        int[] arrn = this.mItemColors;
        if (arrn != null) {
            int n2 = arrn.length;
            arrn = new int[n2];
            arrf = new float[n2];
            for (n = 0; n < n2; ++n) {
                arrn[n] = this.mItemColors[n];
                arrf[n] = this.mItemOffsets[n];
            }
        } else if (this.mHasCenterColor) {
            arrn = new int[]{this.mStartColor, this.mCenterColor, this.mEndColor};
            arrf = new float[]{0.0f, 0.5f, 1.0f};
        } else {
            arrn = new int[]{this.mStartColor, this.mEndColor};
        }
        if (arrn.length < 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<gradient> tag requires 2 color values specified!");
            stringBuilder.append(arrn.length);
            stringBuilder.append(" ");
            stringBuilder.append(arrn);
            Log.w(TAG, stringBuilder.toString());
        }
        this.mShader = (n = this.mGradientType) == 0 ? new LinearGradient(this.mStartX, this.mStartY, this.mEndX, this.mEndY, arrn, arrf, GradientColor.parseTileMode(this.mTileMode)) : (n == 1 ? new RadialGradient(this.mCenterX, this.mCenterY, this.mGradientRadius, arrn, arrf, GradientColor.parseTileMode(this.mTileMode)) : new SweepGradient(this.mCenterX, this.mCenterY, arrn, arrf));
        this.mDefaultColor = arrn[0];
    }

    private static Shader.TileMode parseTileMode(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return Shader.TileMode.CLAMP;
                }
                return Shader.TileMode.MIRROR;
            }
            return Shader.TileMode.REPEAT;
        }
        return Shader.TileMode.CLAMP;
    }

    private void updateRootElementState(TypedArray typedArray) {
        this.mThemeAttrs = typedArray.extractThemeAttrs();
        this.mStartX = typedArray.getFloat(8, this.mStartX);
        this.mStartY = typedArray.getFloat(9, this.mStartY);
        this.mEndX = typedArray.getFloat(10, this.mEndX);
        this.mEndY = typedArray.getFloat(11, this.mEndY);
        this.mCenterX = typedArray.getFloat(3, this.mCenterX);
        this.mCenterY = typedArray.getFloat(4, this.mCenterY);
        this.mGradientType = typedArray.getInt(2, this.mGradientType);
        this.mStartColor = typedArray.getColor(0, this.mStartColor);
        this.mHasCenterColor |= typedArray.hasValue(7);
        this.mCenterColor = typedArray.getColor(7, this.mCenterColor);
        this.mEndColor = typedArray.getColor(1, this.mEndColor);
        this.mTileMode = typedArray.getInt(6, this.mTileMode);
        this.mGradientRadius = typedArray.getFloat(5, this.mGradientRadius);
    }

    private void validateXmlContent() throws XmlPullParserException {
        if (this.mGradientRadius <= 0.0f && this.mGradientType == 1) {
            throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
        }
    }

    @Override
    public boolean canApplyTheme() {
        boolean bl = this.mThemeAttrs != null || this.mItemsThemeAttrs != null;
        return bl;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mChangingConfigurations;
    }

    @Override
    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new GradientColorFactory(this);
        }
        return this.mFactory;
    }

    @Override
    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    public Shader getShader() {
        return this.mShader;
    }

    @Override
    public GradientColor obtainForTheme(Resources.Theme theme) {
        if (theme != null && this.canApplyTheme()) {
            GradientColor gradientColor = new GradientColor(this);
            gradientColor.applyTheme(theme);
            return gradientColor;
        }
        return this;
    }

    private static class GradientColorFactory
    extends ConstantState<ComplexColor> {
        private final GradientColor mSrc;

        public GradientColorFactory(GradientColor gradientColor) {
            this.mSrc = gradientColor;
        }

        @Override
        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        @Override
        public GradientColor newInstance() {
            return this.mSrc;
        }

        @Override
        public GradientColor newInstance(Resources resources, Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface GradientTileMode {
    }

}

