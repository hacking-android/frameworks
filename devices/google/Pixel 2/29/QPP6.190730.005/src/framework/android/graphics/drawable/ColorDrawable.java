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
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewDebug;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ColorDrawable
extends Drawable {
    private BlendModeColorFilter mBlendModeColorFilter;
    @ViewDebug.ExportedProperty(deepExport=true, prefix="state_")
    private ColorState mColorState;
    private boolean mMutated;
    @UnsupportedAppUsage
    private final Paint mPaint = new Paint(1);

    public ColorDrawable() {
        this.mColorState = new ColorState();
    }

    public ColorDrawable(int n) {
        this.mColorState = new ColorState();
        this.setColor(n);
    }

    private ColorDrawable(ColorState colorState, Resources resources) {
        this.mColorState = colorState;
        this.updateLocalState(resources);
    }

    private void updateLocalState(Resources resources) {
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, this.mColorState.mTint, this.mColorState.mBlendMode);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ColorState colorState = this.mColorState;
        colorState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        colorState.mThemeAttrs = typedArray.extractThemeAttrs();
        colorState.mUseColor = colorState.mBaseColor = typedArray.getColor(0, colorState.mBaseColor);
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        ColorState colorState = this.mColorState;
        if (colorState == null) {
            return;
        }
        if (colorState.mThemeAttrs != null) {
            TypedArray typedArray = theme.resolveAttributes(colorState.mThemeAttrs, R.styleable.ColorDrawable);
            this.updateStateFromTypedArray(typedArray);
            typedArray.recycle();
        }
        if (colorState.mTint != null && colorState.mTint.canApplyTheme()) {
            colorState.mTint = colorState.mTint.obtainForTheme(theme);
        }
        this.updateLocalState(theme.getResources());
    }

    @Override
    public boolean canApplyTheme() {
        boolean bl = this.mColorState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        ColorFilter colorFilter = this.mPaint.getColorFilter();
        if (this.mColorState.mUseColor >>> 24 != 0 || colorFilter != null || this.mBlendModeColorFilter != null) {
            if (colorFilter == null) {
                this.mPaint.setColorFilter(this.mBlendModeColorFilter);
            }
            this.mPaint.setColor(this.mColorState.mUseColor);
            canvas.drawRect(this.getBounds(), this.mPaint);
            this.mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getAlpha() {
        return this.mColorState.mUseColor >>> 24;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mColorState.getChangingConfigurations();
    }

    public int getColor() {
        return this.mColorState.mUseColor;
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        return this.mColorState;
    }

    @Override
    public int getOpacity() {
        if (this.mBlendModeColorFilter == null && this.mPaint.getColorFilter() == null) {
            int n = this.mColorState.mUseColor >>> 24;
            if (n != 0) {
                if (n != 255) {
                    return -3;
                }
                return -1;
            }
            return -2;
        }
        return -3;
    }

    @Override
    public void getOutline(Outline outline) {
        outline.setRect(this.getBounds());
        outline.setAlpha((float)this.getAlpha() / 255.0f);
    }

    public Xfermode getXfermode() {
        return this.mPaint.getXfermode();
    }

    @Override
    public boolean hasFocusStateSpecified() {
        boolean bl = this.mColorState.mTint != null && this.mColorState.mTint.hasFocusStateSpecified();
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser object, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, (XmlPullParser)object, attributeSet, theme);
        object = ColorDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.ColorDrawable);
        this.updateStateFromTypedArray((TypedArray)object);
        ((TypedArray)object).recycle();
        this.updateLocalState(resources);
    }

    @Override
    public boolean isStateful() {
        boolean bl = this.mColorState.mTint != null && this.mColorState.mTint.isStateful();
        return bl;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mColorState = new ColorState(this.mColorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected boolean onStateChange(int[] object) {
        object = this.mColorState;
        if (object.mTint != null && object.mBlendMode != null) {
            this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, object.mTint, object.mBlendMode);
            return true;
        }
        return false;
    }

    @Override
    public void setAlpha(int n) {
        int n2 = this.mColorState.mBaseColor;
        if (this.mColorState.mUseColor != (n = this.mColorState.mBaseColor << 8 >>> 8 | (n2 >>> 24) * (n + (n >> 7)) >> 8 << 24)) {
            this.mColorState.mUseColor = n;
            this.invalidateSelf();
        }
    }

    public void setColor(int n) {
        if (this.mColorState.mBaseColor != n || this.mColorState.mUseColor != n) {
            ColorState colorState = this.mColorState;
            colorState.mUseColor = n;
            colorState.mBaseColor = n;
            this.invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        ColorState colorState = this.mColorState;
        colorState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, colorState.mTint, blendMode);
        this.invalidateSelf();
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        ColorState colorState = this.mColorState;
        colorState.mTint = colorStateList;
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, colorState.mBlendMode);
        this.invalidateSelf();
    }

    @Override
    public void setXfermode(Xfermode xfermode) {
        this.mPaint.setXfermode(xfermode);
        this.invalidateSelf();
    }

    static final class ColorState
    extends Drawable.ConstantState {
        int mBaseColor;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        int mChangingConfigurations;
        int[] mThemeAttrs;
        ColorStateList mTint = null;
        @ViewDebug.ExportedProperty
        @UnsupportedAppUsage
        int mUseColor;

        ColorState() {
        }

        ColorState(ColorState colorState) {
            this.mThemeAttrs = colorState.mThemeAttrs;
            this.mBaseColor = colorState.mBaseColor;
            this.mUseColor = colorState.mUseColor;
            this.mChangingConfigurations = colorState.mChangingConfigurations;
            this.mTint = colorState.mTint;
            this.mBlendMode = colorState.mBlendMode;
        }

        @Override
        public boolean canApplyTheme() {
            ColorStateList colorStateList;
            boolean bl = this.mThemeAttrs != null || (colorStateList = this.mTint) != null && colorStateList.canApplyTheme();
            return bl;
        }

        @Override
        public int getChangingConfigurations() {
            int n = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            int n2 = colorStateList != null ? colorStateList.getChangingConfigurations() : 0;
            return n | n2;
        }

        @Override
        public Drawable newDrawable() {
            return new ColorDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new ColorDrawable(this, resources);
        }
    }

}

