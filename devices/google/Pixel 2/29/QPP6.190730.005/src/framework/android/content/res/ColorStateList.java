/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ComplexColor;
import android.content.res.ConstantState;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import android.util.StateSet;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ColorStateList
extends ComplexColor
implements Parcelable {
    public static final Parcelable.Creator<ColorStateList> CREATOR;
    private static final int DEFAULT_COLOR = -65536;
    private static final int[][] EMPTY;
    private static final String TAG = "ColorStateList";
    private static final SparseArray<WeakReference<ColorStateList>> sCache;
    private int mChangingConfigurations;
    @UnsupportedAppUsage
    private int[] mColors;
    @UnsupportedAppUsage
    private int mDefaultColor;
    @UnsupportedAppUsage
    private ColorStateListFactory mFactory;
    private boolean mIsOpaque;
    @UnsupportedAppUsage
    private int[][] mStateSpecs;
    private int[][] mThemeAttrs;

    static {
        EMPTY = new int[][]{new int[0]};
        sCache = new SparseArray();
        CREATOR = new Parcelable.Creator<ColorStateList>(){

            @Override
            public ColorStateList createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                int[][] arrarrn = new int[n][];
                for (int i = 0; i < n; ++i) {
                    arrarrn[i] = parcel.createIntArray();
                }
                return new ColorStateList(arrarrn, parcel.createIntArray());
            }

            public ColorStateList[] newArray(int n) {
                return new ColorStateList[n];
            }
        };
    }

    @UnsupportedAppUsage
    private ColorStateList() {
    }

    private ColorStateList(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.mChangingConfigurations = colorStateList.mChangingConfigurations;
            this.mStateSpecs = colorStateList.mStateSpecs;
            this.mDefaultColor = colorStateList.mDefaultColor;
            this.mIsOpaque = colorStateList.mIsOpaque;
            this.mThemeAttrs = (int[][])colorStateList.mThemeAttrs.clone();
            this.mColors = (int[])colorStateList.mColors.clone();
        }
    }

    public ColorStateList(int[][] arrn, int[] arrn2) {
        this.mStateSpecs = arrn;
        this.mColors = arrn2;
        this.onColorsChanged();
    }

    private void applyTheme(Resources.Theme theme) {
        if (this.mThemeAttrs == null) {
            return;
        }
        int n = 0;
        int[][] arrn = this.mThemeAttrs;
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = n;
            if (arrn[i] != null) {
                TypedArray typedArray = theme.resolveAttributes(arrn[i], R.styleable.ColorStateListItem);
                float f = arrn[i][0] != 0 ? (float)Color.alpha(this.mColors[i]) / 255.0f : 1.0f;
                arrn[i] = typedArray.extractThemeAttrs(arrn[i]);
                if (arrn[i] != null) {
                    n = 1;
                }
                n3 = typedArray.getColor(0, this.mColors[i]);
                f = typedArray.getFloat(1, f);
                this.mColors[i] = this.modulateColorAlpha(n3, f);
                this.mChangingConfigurations |= typedArray.getChangingConfigurations();
                typedArray.recycle();
                n3 = n;
            }
            n = n3;
        }
        if (n == 0) {
            this.mThemeAttrs = null;
        }
        this.onColorsChanged();
    }

    @Deprecated
    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return ColorStateList.createFromXml(resources, xmlPullParser, null);
    }

    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n == 2) {
            return ColorStateList.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    static ColorStateList createFromXmlInner(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Object object2 = xmlPullParser.getName();
        if (((String)object2).equals("selector")) {
            object2 = new ColorStateList();
            ColorStateList.super.inflate((Resources)object, xmlPullParser, attributeSet, theme);
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(": invalid color state list tag ");
        ((StringBuilder)object).append((String)object2);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    private void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = xmlPullParser.getDepth() + 1;
        int n4 = 0;
        int n5 = -65536;
        boolean bl = false;
        int[][] arrn = ArrayUtils.newUnpaddedArray(int[].class, 20);
        int[][] arrarrn = new int[arrn.length][];
        int[] arrn2 = new int[arrn.length];
        int n6 = 0;
        while ((n = xmlPullParser.next()) != 1 && ((n2 = xmlPullParser.getDepth()) >= n3 || n != 3)) {
            if (n != 2 || n2 > n3 || !xmlPullParser.getName().equals("item")) continue;
            int[] arrn3 = Resources.obtainAttributes(resources, theme, attributeSet, R.styleable.ColorStateListItem);
            int[] arrn4 = arrn3.extractThemeAttrs();
            int n7 = arrn3.getColor(0, -65281);
            float f = arrn3.getFloat(1, 1.0f);
            int n8 = arrn3.getChangingConfigurations();
            arrn3.recycle();
            n = attributeSet.getAttributeCount();
            arrn3 = new int[n];
            int n9 = 0;
            for (n2 = 0; n2 < n; ++n2) {
                int n10 = attributeSet.getAttributeNameResource(n2);
                if (n10 == 16843173 || n10 == 16843551) continue;
                if (!attributeSet.getAttributeBooleanValue(n2, false)) {
                    n10 = -n10;
                }
                arrn3[n9] = n10;
                ++n9;
            }
            arrn3 = StateSet.trimStateSet(arrn3, n9);
            n = this.modulateColorAlpha(n7, f);
            if (n6 == 0 || arrn3.length == 0) {
                n5 = n;
            }
            if (arrn4 != null) {
                bl = true;
            }
            arrn2 = GrowingArrayUtils.append(arrn2, n6, n);
            arrarrn = GrowingArrayUtils.append(arrarrn, n6, arrn4);
            arrn = GrowingArrayUtils.append(arrn, n6, arrn3);
            ++n6;
            n4 |= n8;
        }
        this.mChangingConfigurations = n4;
        this.mDefaultColor = n5;
        if (bl) {
            this.mThemeAttrs = new int[n6][];
            System.arraycopy(arrarrn, 0, this.mThemeAttrs, 0, n6);
        } else {
            this.mThemeAttrs = null;
        }
        this.mColors = new int[n6];
        this.mStateSpecs = new int[n6][];
        System.arraycopy(arrn2, 0, this.mColors, 0, n6);
        System.arraycopy(arrn, 0, this.mStateSpecs, 0, n6);
        this.onColorsChanged();
    }

    private int modulateColorAlpha(int n, float f) {
        if (f == 1.0f) {
            return n;
        }
        return 16777215 & n | MathUtils.constrain((int)((float)Color.alpha(n) * f + 0.5f), 0, 255) << 24;
    }

    @UnsupportedAppUsage
    private void onColorsChanged() {
        int n = -65536;
        boolean bl = true;
        int[][] arrn = this.mStateSpecs;
        int[] arrn2 = this.mColors;
        int n2 = arrn.length;
        boolean bl2 = bl;
        if (n2 > 0) {
            int n3;
            n = arrn2[0];
            int n4 = n2 - 1;
            do {
                n3 = n;
                if (n4 <= 0) break;
                if (arrn[n4].length == 0) {
                    n3 = arrn2[n4];
                    break;
                }
                --n4;
            } while (true);
            n4 = 0;
            do {
                n = n3;
                bl2 = bl;
                if (n4 >= n2) break;
                if (Color.alpha(arrn2[n4]) != 255) {
                    bl2 = false;
                    n = n3;
                    break;
                }
                ++n4;
            } while (true);
        }
        this.mDefaultColor = n;
        this.mIsOpaque = bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ColorStateList valueOf(int n) {
        SparseArray<WeakReference<ColorStateList>> sparseArray = sCache;
        synchronized (sparseArray) {
            ColorStateList colorStateList;
            int n2 = sCache.indexOfKey(n);
            if (n2 >= 0) {
                colorStateList = (ColorStateList)sCache.valueAt(n2).get();
                if (colorStateList != null) {
                    return colorStateList;
                }
                sCache.removeAt(n2);
            }
            n2 = sCache.size() - 1;
            do {
                if (n2 < 0) {
                    colorStateList = new ColorStateList(EMPTY, new int[]{n});
                    SparseArray<WeakReference<ColorStateList>> sparseArray2 = sCache;
                    WeakReference<ColorStateList> weakReference = new WeakReference<ColorStateList>(colorStateList);
                    sparseArray2.put(n, weakReference);
                    return colorStateList;
                }
                if (sCache.valueAt(n2).get() == null) {
                    sCache.removeAt(n2);
                }
                --n2;
            } while (true);
        }
    }

    @UnsupportedAppUsage
    @Override
    public boolean canApplyTheme() {
        boolean bl = this.mThemeAttrs != null;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mChangingConfigurations;
    }

    public int getColorForState(int[] arrn, int n) {
        int n2 = this.mStateSpecs.length;
        for (int i = 0; i < n2; ++i) {
            if (!StateSet.stateSetMatches(this.mStateSpecs[i], arrn)) continue;
            return this.mColors[i];
        }
        return n;
    }

    @UnsupportedAppUsage
    public int[] getColors() {
        return this.mColors;
    }

    @Override
    public ConstantState<ComplexColor> getConstantState() {
        if (this.mFactory == null) {
            this.mFactory = new ColorStateListFactory(this);
        }
        return this.mFactory;
    }

    @Override
    public int getDefaultColor() {
        return this.mDefaultColor;
    }

    @UnsupportedAppUsage
    public int[][] getStates() {
        return this.mStateSpecs;
    }

    public boolean hasFocusStateSpecified() {
        return StateSet.containsAttribute(this.mStateSpecs, 16842908);
    }

    public boolean hasState(int n) {
        for (int[] arrn : this.mStateSpecs) {
            int n2 = arrn.length;
            for (int i = 0; i < n2; ++i) {
                if (arrn[i] != n && arrn[i] != n) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    public boolean isOpaque() {
        return this.mIsOpaque;
    }

    @Override
    public boolean isStateful() {
        boolean bl;
        int[][] arrn = this.mStateSpecs;
        int n = arrn.length;
        boolean bl2 = bl = false;
        if (n >= 1) {
            bl2 = bl;
            if (arrn[0].length > 0) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @UnsupportedAppUsage
    @Override
    public ColorStateList obtainForTheme(Resources.Theme theme) {
        if (theme != null && this.canApplyTheme()) {
            ColorStateList colorStateList = new ColorStateList(this);
            colorStateList.applyTheme(theme);
            return colorStateList;
        }
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ColorStateList{mThemeAttrs=");
        stringBuilder.append(Arrays.deepToString((Object[])this.mThemeAttrs));
        stringBuilder.append("mChangingConfigurations=");
        stringBuilder.append(this.mChangingConfigurations);
        stringBuilder.append("mStateSpecs=");
        stringBuilder.append(Arrays.deepToString((Object[])this.mStateSpecs));
        stringBuilder.append("mColors=");
        stringBuilder.append(Arrays.toString(this.mColors));
        stringBuilder.append("mDefaultColor=");
        stringBuilder.append(this.mDefaultColor);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public ColorStateList withAlpha(int n) {
        int[] arrn = new int[this.mColors.length];
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            arrn[i] = this.mColors[i] & 16777215 | n << 24;
        }
        return new ColorStateList(this.mStateSpecs, arrn);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.canApplyTheme()) {
            Log.w(TAG, "Wrote partially-resolved ColorStateList to parcel!");
        }
        int n2 = this.mStateSpecs.length;
        parcel.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            parcel.writeIntArray(this.mStateSpecs[n]);
        }
        parcel.writeIntArray(this.mColors);
    }

    private static class ColorStateListFactory
    extends ConstantState<ComplexColor> {
        private final ColorStateList mSrc;

        @UnsupportedAppUsage
        public ColorStateListFactory(ColorStateList colorStateList) {
            this.mSrc = colorStateList;
        }

        @Override
        public int getChangingConfigurations() {
            return this.mSrc.mChangingConfigurations;
        }

        @Override
        public ColorStateList newInstance() {
            return this.mSrc;
        }

        @Override
        public ColorStateList newInstance(Resources resources, Resources.Theme theme) {
            return this.mSrc.obtainForTheme(theme);
        }
    }

}

