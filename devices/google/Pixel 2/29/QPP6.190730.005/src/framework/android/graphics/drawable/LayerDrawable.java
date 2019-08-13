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
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class LayerDrawable
extends Drawable
implements Drawable.Callback {
    public static final int INSET_UNDEFINED = Integer.MIN_VALUE;
    private static final String LOG_TAG = "LayerDrawable";
    public static final int PADDING_MODE_NEST = 0;
    public static final int PADDING_MODE_STACK = 1;
    private boolean mChildRequestedInvalidation;
    private Rect mHotspotBounds;
    @UnsupportedAppUsage
    LayerState mLayerState;
    private boolean mMutated;
    private int[] mPaddingB;
    private int[] mPaddingL;
    private int[] mPaddingR;
    private int[] mPaddingT;
    private boolean mSuspendChildInvalidation;
    private final Rect mTmpContainer = new Rect();
    private final Rect mTmpOutRect = new Rect();
    private final Rect mTmpRect = new Rect();

    LayerDrawable() {
        this((LayerState)null, null);
    }

    LayerDrawable(LayerState layerState, Resources resources) {
        this.mLayerState = this.createConstantState(layerState, resources);
        if (this.mLayerState.mNumChildren > 0) {
            this.ensurePadding();
            this.refreshPadding();
        }
    }

    public LayerDrawable(Drawable[] arrdrawable) {
        this(arrdrawable, null);
    }

    LayerDrawable(Drawable[] object, LayerState arrchildDrawable) {
        this((LayerState)arrchildDrawable, null);
        if (object != null) {
            int n = ((Drawable[])object).length;
            arrchildDrawable = new ChildDrawable[n];
            for (int i = 0; i < n; ++i) {
                Drawable drawable2;
                arrchildDrawable[i] = new ChildDrawable(this.mLayerState.mDensity);
                arrchildDrawable[i].mDrawable = drawable2 = object[i];
                if (drawable2 == null) continue;
                drawable2.setCallback(this);
                LayerState layerState = this.mLayerState;
                layerState.mChildrenChangingConfigurations |= drawable2.getChangingConfigurations();
            }
            object = this.mLayerState;
            object.mNumChildren = n;
            object.mChildren = arrchildDrawable;
            this.ensurePadding();
            this.refreshPadding();
            return;
        }
        throw new IllegalArgumentException("layers must be non-null");
    }

    private void computeNestedPadding(Rect rect) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            this.refreshChildPadding(i, arrchildDrawable[i]);
            rect.left += this.mPaddingL[i];
            rect.top += this.mPaddingT[i];
            rect.right += this.mPaddingR[i];
            rect.bottom += this.mPaddingB[i];
        }
    }

    private void computeStackedPadding(Rect rect) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            this.refreshChildPadding(i, arrchildDrawable[i]);
            rect.left = Math.max(rect.left, this.mPaddingL[i]);
            rect.top = Math.max(rect.top, this.mPaddingT[i]);
            rect.right = Math.max(rect.right, this.mPaddingR[i]);
            rect.bottom = Math.max(rect.bottom, this.mPaddingB[i]);
        }
    }

    private ChildDrawable createLayer(Drawable drawable2) {
        ChildDrawable childDrawable = new ChildDrawable(this.mLayerState.mDensity);
        childDrawable.mDrawable = drawable2;
        return childDrawable;
    }

    private Drawable getFirstNonNullDrawable() {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            return drawable2;
        }
        return null;
    }

    private void inflateLayers(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        LayerState layerState = this.mLayerState;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3 || !xmlPullParser.getName().equals("item")) continue;
            ChildDrawable childDrawable = new ChildDrawable(layerState.mDensity);
            TypedArray typedArray = LayerDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.LayerDrawableItem);
            this.updateLayerFromTypedArray(childDrawable, typedArray);
            typedArray.recycle();
            if (childDrawable.mDrawable == null && (childDrawable.mThemeAttrs == null || childDrawable.mThemeAttrs[4] == 0)) {
                while ((n = xmlPullParser.next()) == 4) {
                }
                if (n == 2) {
                    childDrawable.mDrawable = Drawable.createFromXmlInner((Resources)object, xmlPullParser, attributeSet, theme);
                    childDrawable.mDrawable.setCallback(this);
                    layerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                    ((StringBuilder)object).append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    throw new XmlPullParserException(((StringBuilder)object).toString());
                }
            }
            this.addLayer(childDrawable);
        }
    }

    private boolean refreshChildPadding(int n, ChildDrawable childDrawable) {
        if (childDrawable.mDrawable != null) {
            Rect rect = this.mTmpRect;
            childDrawable.mDrawable.getPadding(rect);
            if (rect.left != this.mPaddingL[n] || rect.top != this.mPaddingT[n] || rect.right != this.mPaddingR[n] || rect.bottom != this.mPaddingB[n]) {
                this.mPaddingL[n] = rect.left;
                this.mPaddingT[n] = rect.top;
                this.mPaddingR[n] = rect.right;
                this.mPaddingB[n] = rect.bottom;
                return true;
            }
        }
        return false;
    }

    private static int resolveGravity(int n, int n2, int n3, int n4, int n5) {
        int n6 = n;
        if (!Gravity.isHorizontal(n)) {
            n6 = n2 < 0 ? n | 7 : n | 8388611;
        }
        n = n6;
        if (!Gravity.isVertical(n6)) {
            n = n3 < 0 ? n6 | 112 : n6 | 48;
        }
        n6 = n;
        if (n2 < 0) {
            n6 = n;
            if (n4 < 0) {
                n6 = n | 7;
            }
        }
        n = n6;
        if (n3 < 0) {
            n = n6;
            if (n5 < 0) {
                n = n6 | 112;
            }
        }
        return n;
    }

    private void resumeChildInvalidation() {
        this.mSuspendChildInvalidation = false;
        if (this.mChildRequestedInvalidation) {
            this.mChildRequestedInvalidation = false;
            this.invalidateSelf();
        }
    }

    private void setLayerInsetInternal(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        ChildDrawable childDrawable = this.mLayerState.mChildren[n];
        childDrawable.mInsetL = n2;
        childDrawable.mInsetT = n3;
        childDrawable.mInsetR = n4;
        childDrawable.mInsetB = n5;
        childDrawable.mInsetS = n6;
        childDrawable.mInsetE = n7;
    }

    private void suspendChildInvalidation() {
        this.mSuspendChildInvalidation = true;
    }

    private void updateLayerBounds(Rect rect) {
        try {
            this.suspendChildInvalidation();
            this.updateLayerBoundsInternal(rect);
            return;
        }
        finally {
            this.resumeChildInvalidation();
        }
    }

    private void updateLayerBoundsInternal(Rect rect) {
        Rect rect2 = this.mTmpOutRect;
        int n = this.getLayoutDirection();
        boolean bl = false;
        int n2 = n == 1 ? 1 : 0;
        if (this.mLayerState.mPaddingMode == 0) {
            bl = true;
        }
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n3 = this.mLayerState.mNumChildren;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = n2;
        n2 = n6;
        do {
            Rect rect3 = rect;
            if (n8 >= n3) break;
            ChildDrawable childDrawable = arrchildDrawable[n8];
            Drawable drawable2 = childDrawable.mDrawable;
            if (drawable2 != null) {
                int n10 = childDrawable.mInsetT;
                int n11 = childDrawable.mInsetB;
                int n12 = n9 != 0 ? childDrawable.mInsetE : childDrawable.mInsetS;
                n6 = n9 != 0 ? childDrawable.mInsetS : childDrawable.mInsetE;
                if (n12 == Integer.MIN_VALUE) {
                    n12 = childDrawable.mInsetL;
                }
                if (n6 == Integer.MIN_VALUE) {
                    n6 = childDrawable.mInsetR;
                }
                Rect rect4 = this.mTmpContainer;
                rect4.set(rect3.left + n12 + n7, rect3.top + n10 + n2, rect3.right - n6 - n5, rect3.bottom - n11 - n4);
                n10 = drawable2.getIntrinsicWidth();
                n12 = drawable2.getIntrinsicHeight();
                n6 = childDrawable.mWidth;
                n11 = childDrawable.mHeight;
                int n13 = LayerDrawable.resolveGravity(childDrawable.mGravity, n6, n11, n10, n12);
                if (n6 < 0) {
                    n6 = n10;
                }
                if (n11 >= 0) {
                    n12 = n11;
                }
                Gravity.apply(n13, n6, n12, rect4, rect2, n);
                drawable2.setBounds(rect2);
                if (bl) {
                    n7 += this.mPaddingL[n8];
                    n5 += this.mPaddingR[n8];
                    n6 = this.mPaddingT[n8];
                    n4 += this.mPaddingB[n8];
                    n2 += n6;
                }
            }
            ++n8;
        } while (true);
    }

    private void updateLayerFromTypedArray(ChildDrawable childDrawable, TypedArray object) {
        LayerState layerState = this.mLayerState;
        layerState.mChildrenChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        childDrawable.mThemeAttrs = ((TypedArray)object).extractThemeAttrs();
        int n = ((TypedArray)object).getIndexCount();
        block12 : for (int i = 0; i < n; ++i) {
            int n2 = ((TypedArray)object).getIndex(i);
            switch (n2) {
                default: {
                    continue block12;
                }
                case 10: {
                    childDrawable.mInsetE = ((TypedArray)object).getDimensionPixelOffset(n2, childDrawable.mInsetE);
                    continue block12;
                }
                case 9: {
                    childDrawable.mInsetS = ((TypedArray)object).getDimensionPixelOffset(n2, childDrawable.mInsetS);
                    continue block12;
                }
                case 8: {
                    childDrawable.mInsetB = ((TypedArray)object).getDimensionPixelOffset(n2, childDrawable.mInsetB);
                    continue block12;
                }
                case 7: {
                    childDrawable.mInsetR = ((TypedArray)object).getDimensionPixelOffset(n2, childDrawable.mInsetR);
                    continue block12;
                }
                case 6: {
                    childDrawable.mInsetT = ((TypedArray)object).getDimensionPixelOffset(n2, childDrawable.mInsetT);
                    continue block12;
                }
                case 5: {
                    childDrawable.mInsetL = ((TypedArray)object).getDimensionPixelOffset(n2, childDrawable.mInsetL);
                    continue block12;
                }
                case 3: {
                    childDrawable.mWidth = ((TypedArray)object).getDimensionPixelSize(n2, childDrawable.mWidth);
                    continue block12;
                }
                case 2: {
                    childDrawable.mHeight = ((TypedArray)object).getDimensionPixelSize(n2, childDrawable.mHeight);
                    continue block12;
                }
                case 1: {
                    childDrawable.mId = ((TypedArray)object).getResourceId(n2, childDrawable.mId);
                    continue block12;
                }
                case 0: {
                    childDrawable.mGravity = ((TypedArray)object).getInteger(n2, childDrawable.mGravity);
                }
            }
        }
        if ((object = ((TypedArray)object).getDrawable(4)) != null) {
            if (childDrawable.mDrawable != null) {
                childDrawable.mDrawable.setCallback(null);
            }
            childDrawable.mDrawable = object;
            childDrawable.mDrawable.setCallback(this);
            layerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        LayerState layerState = this.mLayerState;
        layerState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        layerState.mThemeAttrs = typedArray.extractThemeAttrs();
        int n = typedArray.getIndexCount();
        block11 : for (int i = 0; i < n; ++i) {
            int n2 = typedArray.getIndex(i);
            switch (n2) {
                default: {
                    continue block11;
                }
                case 8: {
                    layerState.mPaddingMode = typedArray.getInteger(n2, layerState.mPaddingMode);
                    continue block11;
                }
                case 7: {
                    layerState.mAutoMirrored = typedArray.getBoolean(n2, layerState.mAutoMirrored);
                    continue block11;
                }
                case 6: {
                    layerState.mPaddingEnd = typedArray.getDimensionPixelOffset(n2, layerState.mPaddingEnd);
                    continue block11;
                }
                case 5: {
                    layerState.mPaddingStart = typedArray.getDimensionPixelOffset(n2, layerState.mPaddingStart);
                    continue block11;
                }
                case 4: {
                    layerState.mOpacityOverride = typedArray.getInt(n2, layerState.mOpacityOverride);
                    continue block11;
                }
                case 3: {
                    layerState.mPaddingBottom = typedArray.getDimensionPixelOffset(n2, layerState.mPaddingBottom);
                    continue block11;
                }
                case 2: {
                    layerState.mPaddingRight = typedArray.getDimensionPixelOffset(n2, layerState.mPaddingRight);
                    continue block11;
                }
                case 1: {
                    layerState.mPaddingTop = typedArray.getDimensionPixelOffset(n2, layerState.mPaddingTop);
                    continue block11;
                }
                case 0: {
                    layerState.mPaddingLeft = typedArray.getDimensionPixelOffset(n2, layerState.mPaddingLeft);
                }
            }
        }
    }

    public int addLayer(Drawable object) {
        object = this.createLayer((Drawable)object);
        int n = this.addLayer((ChildDrawable)object);
        this.ensurePadding();
        this.refreshChildPadding(n, (ChildDrawable)object);
        return n;
    }

    @UnsupportedAppUsage
    int addLayer(ChildDrawable childDrawable) {
        LayerState layerState = this.mLayerState;
        int n = layerState.mNumChildren;
        int n2 = layerState.mChildren != null ? layerState.mChildren.length : 0;
        if (n >= n2) {
            ChildDrawable[] arrchildDrawable = new ChildDrawable[n2 + 10];
            if (n > 0) {
                System.arraycopy(layerState.mChildren, 0, arrchildDrawable, 0, n);
            }
            layerState.mChildren = arrchildDrawable;
        }
        layerState.mChildren[n] = childDrawable;
        ++layerState.mNumChildren;
        layerState.invalidateCache();
        return n;
    }

    ChildDrawable addLayer(Drawable drawable2, int[] object, int n, int n2, int n3, int n4, int n5) {
        ChildDrawable childDrawable = this.createLayer(drawable2);
        childDrawable.mId = n;
        childDrawable.mThemeAttrs = object;
        childDrawable.mDrawable.setAutoMirrored(this.isAutoMirrored());
        childDrawable.mInsetL = n2;
        childDrawable.mInsetT = n3;
        childDrawable.mInsetR = n4;
        childDrawable.mInsetB = n5;
        this.addLayer(childDrawable);
        object = this.mLayerState;
        object.mChildrenChangingConfigurations |= drawable2.getChangingConfigurations();
        drawable2.setCallback(this);
        return childDrawable;
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        Object object;
        super.applyTheme(theme);
        LayerState layerState = this.mLayerState;
        int n = Drawable.resolveDensity(theme.getResources(), 0);
        layerState.setDensity(n);
        if (layerState.mThemeAttrs != null) {
            object = theme.resolveAttributes(layerState.mThemeAttrs, R.styleable.LayerDrawable);
            this.updateStateFromTypedArray((TypedArray)object);
            ((TypedArray)object).recycle();
        }
        object = layerState.mChildren;
        int n2 = layerState.mNumChildren;
        for (int i = 0; i < n2; ++i) {
            Object object2;
            Object object3 = object[i];
            ((ChildDrawable)object3).setDensity(n);
            if (((ChildDrawable)object3).mThemeAttrs != null) {
                object2 = theme.resolveAttributes(((ChildDrawable)object3).mThemeAttrs, R.styleable.LayerDrawableItem);
                this.updateLayerFromTypedArray((ChildDrawable)object3, (TypedArray)object2);
                ((TypedArray)object2).recycle();
            }
            if ((object2 = ((ChildDrawable)object3).mDrawable) == null || !((Drawable)object2).canApplyTheme()) continue;
            ((Drawable)object2).applyTheme(theme);
            layerState.mChildrenChangingConfigurations |= ((Drawable)object2).getChangingConfigurations();
        }
    }

    @Override
    public boolean canApplyTheme() {
        boolean bl = this.mLayerState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.clearMutated();
        }
        this.mMutated = false;
    }

    LayerState createConstantState(LayerState layerState, Resources resources) {
        return new LayerState(layerState, this, resources);
    }

    @Override
    public void draw(Canvas canvas) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.draw(canvas);
        }
    }

    @UnsupportedAppUsage
    void ensurePadding() {
        int n = this.mLayerState.mNumChildren;
        int[] arrn = this.mPaddingL;
        if (arrn != null && arrn.length >= n) {
            return;
        }
        this.mPaddingL = new int[n];
        this.mPaddingT = new int[n];
        this.mPaddingR = new int[n];
        this.mPaddingB = new int[n];
    }

    public Drawable findDrawableByLayerId(int n) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        for (int i = this.mLayerState.mNumChildren - 1; i >= 0; --i) {
            if (arrchildDrawable[i].mId != n) continue;
            return arrchildDrawable[i].mDrawable;
        }
        return null;
    }

    public int findIndexByLayerId(int n) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n2 = this.mLayerState.mNumChildren;
        for (int i = 0; i < n2; ++i) {
            if (arrchildDrawable[i].mId != n) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int getAlpha() {
        Drawable drawable2 = this.getFirstNonNullDrawable();
        if (drawable2 != null) {
            return drawable2.getAlpha();
        }
        return super.getAlpha();
    }

    public int getBottomPadding() {
        return this.mLayerState.mPaddingBottom;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mLayerState.getChangingConfigurations();
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        if (this.mLayerState.canConstantState()) {
            this.mLayerState.mChangingConfigurations = this.getChangingConfigurations();
            return this.mLayerState;
        }
        return null;
    }

    public Drawable getDrawable(int n) {
        if (n < this.mLayerState.mNumChildren) {
            return this.mLayerState.mChildren[n].mDrawable;
        }
        throw new IndexOutOfBoundsException();
    }

    public int getEndPadding() {
        return this.mLayerState.mPaddingEnd;
    }

    @Override
    public void getHotspotBounds(Rect rect) {
        Rect rect2 = this.mHotspotBounds;
        if (rect2 != null) {
            rect.set(rect2);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    public int getId(int n) {
        if (n < this.mLayerState.mNumChildren) {
            return this.mLayerState.mChildren[n].mId;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getIntrinsicHeight() {
        int n = -1;
        int n2 = 0;
        int n3 = 0;
        boolean bl = this.mLayerState.mPaddingMode == 0;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n4 = this.mLayerState.mNumChildren;
        for (int i = 0; i < n4; ++i) {
            int n5;
            int n6;
            ChildDrawable childDrawable = arrchildDrawable[i];
            if (childDrawable.mDrawable == null) {
                n5 = n2;
                n6 = n3;
            } else {
                int n7 = childDrawable.mHeight < 0 ? childDrawable.mDrawable.getIntrinsicHeight() : childDrawable.mHeight;
                n6 = n7 < 0 ? -1 : childDrawable.mInsetT + n7 + childDrawable.mInsetB + n2 + n3;
                n7 = n;
                if (n6 > n) {
                    n7 = n6;
                }
                n = n7;
                n5 = n2;
                n6 = n3;
                if (bl) {
                    n5 = n2 + this.mPaddingT[i];
                    n6 = n3 + this.mPaddingB[i];
                    n = n7;
                }
            }
            n2 = n5;
            n3 = n6;
        }
        return n;
    }

    @Override
    public int getIntrinsicWidth() {
        int n = -1;
        int n2 = 0;
        int n3 = 0;
        int n4 = this.mLayerState.mPaddingMode;
        boolean bl = false;
        boolean bl2 = n4 == 0;
        if (this.getLayoutDirection() == 1) {
            bl = true;
        }
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n5 = this.mLayerState.mNumChildren;
        for (int i = 0; i < n5; ++i) {
            ChildDrawable childDrawable = arrchildDrawable[i];
            if (childDrawable.mDrawable == null) {
                n4 = n;
            } else {
                int n6 = bl ? childDrawable.mInsetE : childDrawable.mInsetS;
                n4 = bl ? childDrawable.mInsetS : childDrawable.mInsetE;
                if (n6 == Integer.MIN_VALUE) {
                    n6 = childDrawable.mInsetL;
                }
                if (n4 == Integer.MIN_VALUE) {
                    n4 = childDrawable.mInsetR;
                }
                int n7 = childDrawable.mWidth < 0 ? childDrawable.mDrawable.getIntrinsicWidth() : childDrawable.mWidth;
                n6 = n7 < 0 ? -1 : n7 + n6 + n4 + n2 + n3;
                n4 = n;
                if (n6 > n) {
                    n4 = n6;
                }
                if (bl2) {
                    n2 += this.mPaddingL[i];
                    n3 += this.mPaddingR[i];
                }
            }
            n = n4;
        }
        return n;
    }

    public int getLayerGravity(int n) {
        return this.mLayerState.mChildren[n].mGravity;
    }

    public int getLayerHeight(int n) {
        return this.mLayerState.mChildren[n].mHeight;
    }

    public int getLayerInsetBottom(int n) {
        return this.mLayerState.mChildren[n].mInsetB;
    }

    public int getLayerInsetEnd(int n) {
        return this.mLayerState.mChildren[n].mInsetE;
    }

    public int getLayerInsetLeft(int n) {
        return this.mLayerState.mChildren[n].mInsetL;
    }

    public int getLayerInsetRight(int n) {
        return this.mLayerState.mChildren[n].mInsetR;
    }

    public int getLayerInsetStart(int n) {
        return this.mLayerState.mChildren[n].mInsetS;
    }

    public int getLayerInsetTop(int n) {
        return this.mLayerState.mChildren[n].mInsetT;
    }

    public int getLayerWidth(int n) {
        return this.mLayerState.mChildren[n].mWidth;
    }

    public int getLeftPadding() {
        return this.mLayerState.mPaddingLeft;
    }

    public int getNumberOfLayers() {
        return this.mLayerState.mNumChildren;
    }

    @Override
    public int getOpacity() {
        if (this.mLayerState.mOpacityOverride != 0) {
            return this.mLayerState.mOpacityOverride;
        }
        return this.mLayerState.getOpacity();
    }

    @Override
    public void getOutline(Outline outline) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.getOutline(outline);
            if (outline.isEmpty()) continue;
            return;
        }
    }

    @Override
    public boolean getPadding(Rect rect) {
        LayerState layerState = this.mLayerState;
        if (layerState.mPaddingMode == 0) {
            this.computeNestedPadding(rect);
        } else {
            this.computeStackedPadding(rect);
        }
        int n = layerState.mPaddingTop;
        int n2 = layerState.mPaddingBottom;
        int n3 = this.getLayoutDirection();
        boolean bl = false;
        n3 = n3 == 1 ? 1 : 0;
        int n4 = n3 != 0 ? layerState.mPaddingEnd : layerState.mPaddingStart;
        n3 = n3 != 0 ? layerState.mPaddingStart : layerState.mPaddingEnd;
        if (n4 < 0) {
            n4 = layerState.mPaddingLeft;
        }
        if (n3 < 0) {
            n3 = layerState.mPaddingRight;
        }
        if (n4 >= 0) {
            rect.left = n4;
        }
        if (n >= 0) {
            rect.top = n;
        }
        if (n3 >= 0) {
            rect.right = n3;
        }
        if (n2 >= 0) {
            rect.bottom = n2;
        }
        if (rect.left != 0 || rect.top != 0 || rect.right != 0 || rect.bottom != 0) {
            bl = true;
        }
        return bl;
    }

    public int getPaddingMode() {
        return this.mLayerState.mPaddingMode;
    }

    public int getRightPadding() {
        return this.mLayerState.mPaddingRight;
    }

    public int getStartPadding() {
        return this.mLayerState.mPaddingStart;
    }

    public int getTopPadding() {
        return this.mLayerState.mPaddingTop;
    }

    @Override
    public boolean hasFocusStateSpecified() {
        return this.mLayerState.hasFocusStateSpecified();
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        LayerState layerState = this.mLayerState;
        int n = Drawable.resolveDensity(resources, 0);
        layerState.setDensity(n);
        ChildDrawable[] arrchildDrawable = LayerDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.LayerDrawable);
        this.updateStateFromTypedArray((TypedArray)arrchildDrawable);
        arrchildDrawable.recycle();
        arrchildDrawable = layerState.mChildren;
        int n2 = layerState.mNumChildren;
        for (int i = 0; i < n2; ++i) {
            arrchildDrawable[i].setDensity(n);
        }
        this.inflateLayers(resources, xmlPullParser, attributeSet, theme);
        this.ensurePadding();
        this.refreshPadding();
    }

    @Override
    public void invalidateDrawable(Drawable drawable2) {
        if (this.mSuspendChildInvalidation) {
            this.mChildRequestedInvalidation = true;
        } else {
            this.mLayerState.invalidateCache();
            this.invalidateSelf();
        }
    }

    @Override
    public boolean isAutoMirrored() {
        return this.mLayerState.mAutoMirrored;
    }

    @Override
    public boolean isProjected() {
        if (super.isProjected()) {
            return true;
        }
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null || !drawable2.isProjected()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isStateful() {
        return this.mLayerState.isStateful();
    }

    @Override
    public void jumpToCurrentState() {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mLayerState = this.createConstantState(this.mLayerState, null);
            ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
            int n = this.mLayerState.mNumChildren;
            for (int i = 0; i < n; ++i) {
                Drawable drawable2 = arrchildDrawable[i].mDrawable;
                if (drawable2 == null) continue;
                drawable2.mutate();
            }
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        this.updateLayerBounds(rect);
    }

    @Override
    public boolean onLayoutDirectionChanged(int n) {
        boolean bl = false;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n2 = this.mLayerState.mNumChildren;
        for (int i = 0; i < n2; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            boolean bl2 = bl;
            if (drawable2 != null) {
                bl2 = bl | drawable2.setLayoutDirection(n);
            }
            bl = bl2;
        }
        this.updateLayerBounds(this.getBounds());
        return bl;
    }

    @Override
    protected boolean onLevelChange(int n) {
        boolean bl = false;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n2 = this.mLayerState.mNumChildren;
        for (int i = 0; i < n2; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            boolean bl2 = bl;
            if (drawable2 != null) {
                bl2 = bl;
                if (drawable2.setLevel(n)) {
                    this.refreshChildPadding(i, arrchildDrawable[i]);
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        if (bl) {
            this.updateLayerBounds(this.getBounds());
        }
        return bl;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        boolean bl = false;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            boolean bl2 = bl;
            if (drawable2 != null) {
                bl2 = bl;
                if (drawable2.isStateful()) {
                    bl2 = bl;
                    if (drawable2.setState(arrn)) {
                        this.refreshChildPadding(i, arrchildDrawable[i]);
                        bl2 = true;
                    }
                }
            }
            bl = bl2;
        }
        if (bl) {
            this.updateLayerBounds(this.getBounds());
        }
        return bl;
    }

    void refreshPadding() {
        int n = this.mLayerState.mNumChildren;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        for (int i = 0; i < n; ++i) {
            this.refreshChildPadding(i, arrchildDrawable[i]);
        }
    }

    @Override
    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        this.scheduleSelf(runnable, l);
    }

    @Override
    public void setAlpha(int n) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n2 = this.mLayerState.mNumChildren;
        for (int i = 0; i < n2; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setAlpha(n);
        }
    }

    @Override
    public void setAutoMirrored(boolean bl) {
        this.mLayerState.mAutoMirrored = bl;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setAutoMirrored(bl);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setColorFilter(colorFilter);
        }
    }

    @Override
    public void setDither(boolean bl) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setDither(bl);
        }
    }

    public void setDrawable(int n, Drawable drawable2) {
        if (n < this.mLayerState.mNumChildren) {
            ChildDrawable childDrawable = this.mLayerState.mChildren[n];
            if (childDrawable.mDrawable != null) {
                if (drawable2 != null) {
                    drawable2.setBounds(childDrawable.mDrawable.getBounds());
                }
                childDrawable.mDrawable.setCallback(null);
            }
            if (drawable2 != null) {
                drawable2.setCallback(this);
            }
            childDrawable.mDrawable = drawable2;
            this.mLayerState.invalidateCache();
            this.refreshChildPadding(n, childDrawable);
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean setDrawableByLayerId(int n, Drawable drawable2) {
        if ((n = this.findIndexByLayerId(n)) < 0) {
            return false;
        }
        this.setDrawable(n, drawable2);
        return true;
    }

    @Override
    public void setHotspot(float f, float f2) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        Object object;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n5 = this.mLayerState.mNumChildren;
        for (int i = 0; i < n5; ++i) {
            object = arrchildDrawable[i].mDrawable;
            if (object == null) continue;
            ((Drawable)object).setHotspotBounds(n, n2, n3, n4);
        }
        object = this.mHotspotBounds;
        if (object == null) {
            this.mHotspotBounds = new Rect(n, n2, n3, n4);
        } else {
            ((Rect)object).set(n, n2, n3, n4);
        }
    }

    public void setId(int n, int n2) {
        this.mLayerState.mChildren[n].mId = n2;
    }

    public void setLayerGravity(int n, int n2) {
        this.mLayerState.mChildren[n].mGravity = n2;
    }

    public void setLayerHeight(int n, int n2) {
        this.mLayerState.mChildren[n].mHeight = n2;
    }

    public void setLayerInset(int n, int n2, int n3, int n4, int n5) {
        this.setLayerInsetInternal(n, n2, n3, n4, n5, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public void setLayerInsetBottom(int n, int n2) {
        this.mLayerState.mChildren[n].mInsetB = n2;
    }

    public void setLayerInsetEnd(int n, int n2) {
        this.mLayerState.mChildren[n].mInsetE = n2;
    }

    public void setLayerInsetLeft(int n, int n2) {
        this.mLayerState.mChildren[n].mInsetL = n2;
    }

    public void setLayerInsetRelative(int n, int n2, int n3, int n4, int n5) {
        this.setLayerInsetInternal(n, 0, n3, 0, n5, n2, n4);
    }

    public void setLayerInsetRight(int n, int n2) {
        this.mLayerState.mChildren[n].mInsetR = n2;
    }

    public void setLayerInsetStart(int n, int n2) {
        this.mLayerState.mChildren[n].mInsetS = n2;
    }

    public void setLayerInsetTop(int n, int n2) {
        this.mLayerState.mChildren[n].mInsetT = n2;
    }

    public void setLayerSize(int n, int n2, int n3) {
        ChildDrawable childDrawable = this.mLayerState.mChildren[n];
        childDrawable.mWidth = n2;
        childDrawable.mHeight = n3;
    }

    public void setLayerWidth(int n, int n2) {
        this.mLayerState.mChildren[n].mWidth = n2;
    }

    public void setOpacity(int n) {
        this.mLayerState.mOpacityOverride = n;
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        LayerState layerState = this.mLayerState;
        layerState.mPaddingLeft = n;
        layerState.mPaddingTop = n2;
        layerState.mPaddingRight = n3;
        layerState.mPaddingBottom = n4;
        layerState.mPaddingStart = -1;
        layerState.mPaddingEnd = -1;
    }

    public void setPaddingMode(int n) {
        if (this.mLayerState.mPaddingMode != n) {
            this.mLayerState.mPaddingMode = n;
        }
    }

    public void setPaddingRelative(int n, int n2, int n3, int n4) {
        LayerState layerState = this.mLayerState;
        layerState.mPaddingStart = n;
        layerState.mPaddingTop = n2;
        layerState.mPaddingEnd = n3;
        layerState.mPaddingBottom = n4;
        layerState.mPaddingLeft = -1;
        layerState.mPaddingRight = -1;
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setTintBlendMode(blendMode);
        }
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setTintList(colorStateList);
        }
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            Drawable drawable2 = arrchildDrawable[i].mDrawable;
            if (drawable2 == null) continue;
            drawable2.setVisible(bl, bl2);
        }
        return bl3;
    }

    @Override
    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }

    static class ChildDrawable {
        public int mDensity = 160;
        @UnsupportedAppUsage
        public Drawable mDrawable;
        public int mGravity = 0;
        public int mHeight = -1;
        public int mId = -1;
        public int mInsetB;
        public int mInsetE = Integer.MIN_VALUE;
        public int mInsetL;
        public int mInsetR;
        public int mInsetS = Integer.MIN_VALUE;
        public int mInsetT;
        public int[] mThemeAttrs;
        public int mWidth = -1;

        ChildDrawable(int n) {
            this.mDensity = n;
        }

        ChildDrawable(ChildDrawable childDrawable, LayerDrawable layerDrawable, Resources resources) {
            Object object;
            Drawable drawable2 = childDrawable.mDrawable;
            if (drawable2 != null) {
                object = drawable2.getConstantState();
                if (object == null) {
                    Drawable drawable3 = drawable2;
                    object = drawable3;
                    if (drawable2.getCallback() != null) {
                        Log.w(LayerDrawable.LOG_TAG, "Invalid drawable added to LayerDrawable! Drawable already belongs to another owner but does not expose a constant state.", new RuntimeException());
                        object = drawable3;
                    }
                } else {
                    object = resources != null ? ((Drawable.ConstantState)object).newDrawable(resources) : ((Drawable.ConstantState)object).newDrawable();
                }
                ((Drawable)object).setLayoutDirection(drawable2.getLayoutDirection());
                ((Drawable)object).setBounds(drawable2.getBounds());
                ((Drawable)object).setLevel(drawable2.getLevel());
                ((Drawable)object).setCallback(layerDrawable);
            } else {
                object = null;
            }
            this.mDrawable = object;
            this.mThemeAttrs = childDrawable.mThemeAttrs;
            this.mInsetL = childDrawable.mInsetL;
            this.mInsetT = childDrawable.mInsetT;
            this.mInsetR = childDrawable.mInsetR;
            this.mInsetB = childDrawable.mInsetB;
            this.mInsetS = childDrawable.mInsetS;
            this.mInsetE = childDrawable.mInsetE;
            this.mWidth = childDrawable.mWidth;
            this.mHeight = childDrawable.mHeight;
            this.mGravity = childDrawable.mGravity;
            this.mId = childDrawable.mId;
            this.mDensity = Drawable.resolveDensity(resources, childDrawable.mDensity);
            int n = childDrawable.mDensity;
            int n2 = this.mDensity;
            if (n != n2) {
                this.applyDensityScaling(n, n2);
            }
        }

        private void applyDensityScaling(int n, int n2) {
            this.mInsetL = Drawable.scaleFromDensity(this.mInsetL, n, n2, false);
            this.mInsetT = Drawable.scaleFromDensity(this.mInsetT, n, n2, false);
            this.mInsetR = Drawable.scaleFromDensity(this.mInsetR, n, n2, false);
            this.mInsetB = Drawable.scaleFromDensity(this.mInsetB, n, n2, false);
            int n3 = this.mInsetS;
            if (n3 != Integer.MIN_VALUE) {
                this.mInsetS = Drawable.scaleFromDensity(n3, n, n2, false);
            }
            if ((n3 = this.mInsetE) != Integer.MIN_VALUE) {
                this.mInsetE = Drawable.scaleFromDensity(n3, n, n2, false);
            }
            if ((n3 = this.mWidth) > 0) {
                this.mWidth = Drawable.scaleFromDensity(n3, n, n2, true);
            }
            if ((n3 = this.mHeight) > 0) {
                this.mHeight = Drawable.scaleFromDensity(n3, n, n2, true);
            }
        }

        public boolean canApplyTheme() {
            Drawable drawable2;
            boolean bl = this.mThemeAttrs != null || (drawable2 = this.mDrawable) != null && drawable2.canApplyTheme();
            return bl;
        }

        public final void setDensity(int n) {
            if (this.mDensity != n) {
                int n2 = this.mDensity;
                this.mDensity = n;
                this.applyDensityScaling(n2, n);
            }
        }
    }

    static class LayerState
    extends Drawable.ConstantState {
        private boolean mAutoMirrored = false;
        int mChangingConfigurations;
        private boolean mCheckedOpacity;
        private boolean mCheckedStateful;
        @UnsupportedAppUsage
        ChildDrawable[] mChildren;
        int mChildrenChangingConfigurations;
        int mDensity;
        private boolean mIsStateful;
        int mNumChildren;
        private int mOpacity;
        int mOpacityOverride = 0;
        int mPaddingBottom = -1;
        int mPaddingEnd = -1;
        int mPaddingLeft = -1;
        private int mPaddingMode = 0;
        int mPaddingRight = -1;
        int mPaddingStart = -1;
        int mPaddingTop = -1;
        private int[] mThemeAttrs;

        LayerState(LayerState layerState, LayerDrawable layerDrawable, Resources resources) {
            int n = layerState != null ? layerState.mDensity : 0;
            this.mDensity = Drawable.resolveDensity(resources, n);
            if (layerState != null) {
                int n2;
                ChildDrawable[] arrchildDrawable = layerState.mChildren;
                this.mNumChildren = n2 = layerState.mNumChildren;
                this.mChildren = new ChildDrawable[n2];
                this.mChangingConfigurations = layerState.mChangingConfigurations;
                this.mChildrenChangingConfigurations = layerState.mChildrenChangingConfigurations;
                for (n = 0; n < n2; ++n) {
                    ChildDrawable childDrawable = arrchildDrawable[n];
                    this.mChildren[n] = new ChildDrawable(childDrawable, layerDrawable, resources);
                }
                this.mCheckedOpacity = layerState.mCheckedOpacity;
                this.mOpacity = layerState.mOpacity;
                this.mCheckedStateful = layerState.mCheckedStateful;
                this.mIsStateful = layerState.mIsStateful;
                this.mAutoMirrored = layerState.mAutoMirrored;
                this.mPaddingMode = layerState.mPaddingMode;
                this.mThemeAttrs = layerState.mThemeAttrs;
                this.mPaddingTop = layerState.mPaddingTop;
                this.mPaddingBottom = layerState.mPaddingBottom;
                this.mPaddingLeft = layerState.mPaddingLeft;
                this.mPaddingRight = layerState.mPaddingRight;
                this.mPaddingStart = layerState.mPaddingStart;
                this.mPaddingEnd = layerState.mPaddingEnd;
                this.mOpacityOverride = layerState.mOpacityOverride;
                n = layerState.mDensity;
                n2 = this.mDensity;
                if (n != n2) {
                    this.applyDensityScaling(n, n2);
                }
            } else {
                this.mNumChildren = 0;
                this.mChildren = null;
            }
        }

        private void applyDensityScaling(int n, int n2) {
            int n3 = this.mPaddingLeft;
            if (n3 > 0) {
                this.mPaddingLeft = Drawable.scaleFromDensity(n3, n, n2, false);
            }
            if ((n3 = this.mPaddingTop) > 0) {
                this.mPaddingTop = Drawable.scaleFromDensity(n3, n, n2, false);
            }
            if ((n3 = this.mPaddingRight) > 0) {
                this.mPaddingRight = Drawable.scaleFromDensity(n3, n, n2, false);
            }
            if ((n3 = this.mPaddingBottom) > 0) {
                this.mPaddingBottom = Drawable.scaleFromDensity(n3, n, n2, false);
            }
            if ((n3 = this.mPaddingStart) > 0) {
                this.mPaddingStart = Drawable.scaleFromDensity(n3, n, n2, false);
            }
            if ((n3 = this.mPaddingEnd) > 0) {
                this.mPaddingEnd = Drawable.scaleFromDensity(n3, n, n2, false);
            }
        }

        @Override
        public boolean canApplyTheme() {
            if (this.mThemeAttrs == null && !super.canApplyTheme()) {
                ChildDrawable[] arrchildDrawable = this.mChildren;
                int n = this.mNumChildren;
                for (int i = 0; i < n; ++i) {
                    if (!arrchildDrawable[i].canApplyTheme()) continue;
                    return true;
                }
                return false;
            }
            return true;
        }

        public final boolean canConstantState() {
            ChildDrawable[] arrchildDrawable = this.mChildren;
            int n = this.mNumChildren;
            for (int i = 0; i < n; ++i) {
                Drawable drawable2 = arrchildDrawable[i].mDrawable;
                if (drawable2 == null || drawable2.getConstantState() != null) continue;
                return false;
            }
            return true;
        }

        @Override
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
        }

        public final int getOpacity() {
            int n;
            if (this.mCheckedOpacity) {
                return this.mOpacity;
            }
            int n2 = this.mNumChildren;
            ChildDrawable[] arrchildDrawable = this.mChildren;
            int n3 = -1;
            int n4 = 0;
            do {
                n = n3;
                if (n4 >= n2) break;
                if (arrchildDrawable[n4].mDrawable != null) {
                    n = n4;
                    break;
                }
                ++n4;
            } while (true);
            n4 = n >= 0 ? arrchildDrawable[n].mDrawable.getOpacity() : -2;
            ++n;
            n3 = n4;
            while (n < n2) {
                Drawable drawable2 = arrchildDrawable[n].mDrawable;
                n4 = n3;
                if (drawable2 != null) {
                    n4 = Drawable.resolveOpacity(n3, drawable2.getOpacity());
                }
                ++n;
                n3 = n4;
            }
            this.mOpacity = n3;
            this.mCheckedOpacity = true;
            return n3;
        }

        public final boolean hasFocusStateSpecified() {
            int n = this.mNumChildren;
            ChildDrawable[] arrchildDrawable = this.mChildren;
            for (int i = 0; i < n; ++i) {
                Drawable drawable2 = arrchildDrawable[i].mDrawable;
                if (drawable2 == null || !drawable2.hasFocusStateSpecified()) continue;
                return true;
            }
            return false;
        }

        void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }

        public final boolean isStateful() {
            boolean bl;
            if (this.mCheckedStateful) {
                return this.mIsStateful;
            }
            int n = this.mNumChildren;
            ChildDrawable[] arrchildDrawable = this.mChildren;
            boolean bl2 = false;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                Drawable drawable2 = arrchildDrawable[n2].mDrawable;
                if (drawable2 != null && drawable2.isStateful()) {
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            this.mIsStateful = bl;
            this.mCheckedStateful = true;
            return bl;
        }

        @Override
        public Drawable newDrawable() {
            return new LayerDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new LayerDrawable(this, resources);
        }

        protected void onDensityChanged(int n, int n2) {
            this.applyDensityScaling(n, n2);
        }

        public final void setDensity(int n) {
            if (this.mDensity != n) {
                int n2 = this.mDensity;
                this.mDensity = n;
                this.onDensityChanged(n2, n);
            }
        }
    }

}

