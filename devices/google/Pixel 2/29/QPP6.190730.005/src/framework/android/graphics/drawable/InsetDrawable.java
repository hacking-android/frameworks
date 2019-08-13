/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class InsetDrawable
extends DrawableWrapper {
    @UnsupportedAppUsage
    private InsetState mState;
    private final Rect mTmpInsetRect = new Rect();
    private final Rect mTmpRect = new Rect();

    InsetDrawable() {
        this(new InsetState(null, null), null);
    }

    public InsetDrawable(Drawable drawable2, float f) {
        this(drawable2, f, f, f, f);
    }

    public InsetDrawable(Drawable drawable2, float f, float f2, float f3, float f4) {
        this(new InsetState(null, null), null);
        this.mState.mInsetLeft = new InsetValue(f, 0);
        this.mState.mInsetTop = new InsetValue(f2, 0);
        this.mState.mInsetRight = new InsetValue(f3, 0);
        this.mState.mInsetBottom = new InsetValue(f4, 0);
        this.setDrawable(drawable2);
    }

    public InsetDrawable(Drawable drawable2, int n) {
        this(drawable2, n, n, n, n);
    }

    public InsetDrawable(Drawable drawable2, int n, int n2, int n3, int n4) {
        this(new InsetState(null, null), null);
        this.mState.mInsetLeft = new InsetValue(0.0f, n);
        this.mState.mInsetTop = new InsetValue(0.0f, n2);
        this.mState.mInsetRight = new InsetValue(0.0f, n3);
        this.mState.mInsetBottom = new InsetValue(0.0f, n4);
        this.setDrawable(drawable2);
    }

    private InsetDrawable(InsetState insetState, Resources resources) {
        super(insetState, resources);
        this.mState = insetState;
    }

    private InsetValue getInset(TypedArray typedArray, int n, InsetValue insetValue) {
        if (typedArray.hasValue(n)) {
            TypedValue typedValue = typedArray.peekValue(n);
            if (typedValue.type == 6) {
                float f = typedValue.getFraction(1.0f, 1.0f);
                if (!(f >= 1.0f)) {
                    return new InsetValue(f, 0);
                }
                throw new IllegalStateException("Fraction cannot be larger than 1");
            }
            if ((n = typedArray.getDimensionPixelOffset(n, 0)) != 0) {
                return new InsetValue(0.0f, n);
            }
        }
        return insetValue;
    }

    private void getInsets(Rect rect) {
        Rect rect2 = this.getBounds();
        rect.left = this.mState.mInsetLeft.getDimension(rect2.width());
        rect.right = this.mState.mInsetRight.getDimension(rect2.width());
        rect.top = this.mState.mInsetTop.getDimension(rect2.height());
        rect.bottom = this.mState.mInsetBottom.getDimension(rect2.height());
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        InsetState insetState = this.mState;
        if (insetState == null) {
            return;
        }
        insetState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        insetState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValue(6)) {
            InsetValue insetValue;
            insetState.mInsetLeft = insetValue = this.getInset(typedArray, 6, new InsetValue());
            insetState.mInsetTop = insetValue;
            insetState.mInsetRight = insetValue;
            insetState.mInsetBottom = insetValue;
        }
        insetState.mInsetLeft = this.getInset(typedArray, 2, insetState.mInsetLeft);
        insetState.mInsetTop = this.getInset(typedArray, 4, insetState.mInsetTop);
        insetState.mInsetRight = this.getInset(typedArray, 3, insetState.mInsetRight);
        insetState.mInsetBottom = this.getInset(typedArray, 5, insetState.mInsetBottom);
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (this.getDrawable() == null && (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(typedArray.getPositionDescription());
            stringBuilder.append(": <inset> tag requires a 'drawable' attribute or child tag defining a drawable");
            throw new XmlPullParserException(stringBuilder.toString());
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void applyTheme(Resources.Theme var1_1) {
        super.applyTheme((Resources.Theme)var1_1);
        var2_2 = this.mState;
        if (var2_2 == null) {
            return;
        }
        if (InsetState.access$000(var2_2) == null) return;
        var1_1 = var1_1.resolveAttributes(InsetState.access$000(var2_2), R.styleable.InsetDrawable);
        this.updateStateFromTypedArray((TypedArray)var1_1);
        this.verifyRequiredAttributes((TypedArray)var1_1);
        var1_1.recycle();
        return;
        {
            catch (XmlPullParserException var2_4) {}
            {
                InsetDrawable.rethrowAsRuntimeException((Exception)var2_4);
            }
        }
        ** finally { 
lbl15: // 1 sources:
        var1_1.recycle();
        throw var2_3;
    }

    @Override
    public int getIntrinsicHeight() {
        int n = this.getDrawable().getIntrinsicHeight();
        float f = this.mState.mInsetTop.mFraction + this.mState.mInsetBottom.mFraction;
        if (n >= 0 && !(f >= 1.0f)) {
            return (int)((float)n / (1.0f - f)) + this.mState.mInsetTop.mDimension + this.mState.mInsetBottom.mDimension;
        }
        return -1;
    }

    @Override
    public int getIntrinsicWidth() {
        int n = this.getDrawable().getIntrinsicWidth();
        float f = this.mState.mInsetLeft.mFraction + this.mState.mInsetRight.mFraction;
        if (n >= 0 && !(f >= 1.0f)) {
            return (int)((float)n / (1.0f - f)) + this.mState.mInsetLeft.mDimension + this.mState.mInsetRight.mDimension;
        }
        return -1;
    }

    @Override
    public int getOpacity() {
        InsetState insetState = this.mState;
        int n = this.getDrawable().getOpacity();
        this.getInsets(this.mTmpInsetRect);
        if (n == -1 && (this.mTmpInsetRect.left > 0 || this.mTmpInsetRect.top > 0 || this.mTmpInsetRect.right > 0 || this.mTmpInsetRect.bottom > 0)) {
            return -3;
        }
        return n;
    }

    @Override
    public Insets getOpticalInsets() {
        Insets insets = super.getOpticalInsets();
        this.getInsets(this.mTmpInsetRect);
        return Insets.of(insets.left + this.mTmpInsetRect.left, insets.top + this.mTmpInsetRect.top, insets.right + this.mTmpInsetRect.right, insets.bottom + this.mTmpInsetRect.bottom);
    }

    @Override
    public void getOutline(Outline outline) {
        this.getDrawable().getOutline(outline);
    }

    @Override
    public boolean getPadding(Rect rect) {
        boolean bl = super.getPadding(rect);
        this.getInsets(this.mTmpInsetRect);
        rect.left += this.mTmpInsetRect.left;
        rect.right += this.mTmpInsetRect.right;
        rect.top += this.mTmpInsetRect.top;
        rect.bottom += this.mTmpInsetRect.bottom;
        bl = bl || (this.mTmpInsetRect.left | this.mTmpInsetRect.right | this.mTmpInsetRect.top | this.mTmpInsetRect.bottom) != 0;
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = InsetDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.InsetDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateStateFromTypedArray(typedArray);
        this.verifyRequiredAttributes(typedArray);
        typedArray.recycle();
    }

    @Override
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new InsetState(this.mState, null);
        return this.mState;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        Rect rect2 = this.mTmpRect;
        rect2.set(rect);
        rect2.left += this.mState.mInsetLeft.getDimension(rect.width());
        rect2.top += this.mState.mInsetTop.getDimension(rect.height());
        rect2.right -= this.mState.mInsetRight.getDimension(rect.width());
        rect2.bottom -= this.mState.mInsetBottom.getDimension(rect.height());
        super.onBoundsChange(rect2);
    }

    static final class InsetState
    extends DrawableWrapper.DrawableWrapperState {
        InsetValue mInsetBottom;
        InsetValue mInsetLeft;
        InsetValue mInsetRight;
        InsetValue mInsetTop;
        private int[] mThemeAttrs;

        InsetState(InsetState insetState, Resources resources) {
            super(insetState, resources);
            if (insetState != null) {
                this.mInsetLeft = insetState.mInsetLeft.clone();
                this.mInsetTop = insetState.mInsetTop.clone();
                this.mInsetRight = insetState.mInsetRight.clone();
                this.mInsetBottom = insetState.mInsetBottom.clone();
                if (insetState.mDensity != this.mDensity) {
                    this.applyDensityScaling(insetState.mDensity, this.mDensity);
                }
            } else {
                this.mInsetLeft = new InsetValue();
                this.mInsetTop = new InsetValue();
                this.mInsetRight = new InsetValue();
                this.mInsetBottom = new InsetValue();
            }
        }

        private void applyDensityScaling(int n, int n2) {
            this.mInsetLeft.scaleFromDensity(n, n2);
            this.mInsetTop.scaleFromDensity(n, n2);
            this.mInsetRight.scaleFromDensity(n, n2);
            this.mInsetBottom.scaleFromDensity(n, n2);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            InsetState insetState;
            if (resources != null) {
                int n = resources.getDisplayMetrics().densityDpi;
                if (n == 0) {
                    n = 160;
                }
                insetState = n != this.mDensity ? new InsetState(this, resources) : this;
            } else {
                insetState = this;
            }
            return new InsetDrawable(insetState, resources);
        }

        @Override
        void onDensityChanged(int n, int n2) {
            super.onDensityChanged(n, n2);
            this.applyDensityScaling(n, n2);
        }
    }

    static final class InsetValue
    implements Cloneable {
        int mDimension;
        final float mFraction;

        public InsetValue() {
            this(0.0f, 0);
        }

        public InsetValue(float f, int n) {
            this.mFraction = f;
            this.mDimension = n;
        }

        public InsetValue clone() {
            return new InsetValue(this.mFraction, this.mDimension);
        }

        int getDimension(int n) {
            return (int)((float)n * this.mFraction) + this.mDimension;
        }

        void scaleFromDensity(int n, int n2) {
            int n3 = this.mDimension;
            if (n3 != 0) {
                this.mDimension = Bitmap.scaleFromDensity(n3, n, n2);
            }
        }
    }

}

