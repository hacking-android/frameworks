/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

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
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ShapeDrawable
extends Drawable {
    private BlendModeColorFilter mBlendModeColorFilter;
    private boolean mMutated;
    private ShapeState mShapeState;

    public ShapeDrawable() {
        this(new ShapeState(), null);
    }

    private ShapeDrawable(ShapeState shapeState, Resources resources) {
        this.mShapeState = shapeState;
        this.updateLocalState();
    }

    public ShapeDrawable(Shape shape) {
        this(new ShapeState(), null);
        this.mShapeState.mShape = shape;
    }

    private static int modulateAlpha(int n, int n2) {
        return n * ((n2 >>> 7) + n2) >>> 8;
    }

    private void updateLocalState() {
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, this.mShapeState.mTint, this.mShapeState.mBlendMode);
    }

    private void updateShape() {
        if (this.mShapeState.mShape != null) {
            Rect rect = this.getBounds();
            int n = rect.width();
            int n2 = rect.height();
            this.mShapeState.mShape.resize(n, n2);
            if (this.mShapeState.mShaderFactory != null) {
                this.mShapeState.mPaint.setShader(this.mShapeState.mShaderFactory.resize(n, n2));
            }
        }
        this.invalidateSelf();
    }

    private void updateStateFromTypedArray(TypedArray object) {
        ShapeState shapeState = this.mShapeState;
        Paint paint = shapeState.mPaint;
        shapeState.mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        shapeState.mThemeAttrs = ((TypedArray)object).extractThemeAttrs();
        paint.setColor(((TypedArray)object).getColor(4, paint.getColor()));
        paint.setDither(((TypedArray)object).getBoolean(0, paint.isDither()));
        shapeState.mIntrinsicWidth = (int)((TypedArray)object).getDimension(3, shapeState.mIntrinsicWidth);
        shapeState.mIntrinsicHeight = (int)((TypedArray)object).getDimension(2, shapeState.mIntrinsicHeight);
        int n = ((TypedArray)object).getInt(5, -1);
        if (n != -1) {
            shapeState.mBlendMode = Drawable.parseBlendMode(n, BlendMode.SRC_IN);
        }
        if ((object = ((TypedArray)object).getColorStateList(1)) != null) {
            shapeState.mTint = object;
        }
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        ShapeState shapeState = this.mShapeState;
        if (shapeState == null) {
            return;
        }
        if (shapeState.mThemeAttrs != null) {
            TypedArray typedArray = theme.resolveAttributes(shapeState.mThemeAttrs, R.styleable.ShapeDrawable);
            this.updateStateFromTypedArray(typedArray);
            typedArray.recycle();
        }
        if (shapeState.mTint != null && shapeState.mTint.canApplyTheme()) {
            shapeState.mTint = shapeState.mTint.obtainForTheme(theme);
        }
        this.updateLocalState();
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = this.getBounds();
        ShapeState shapeState = this.mShapeState;
        Paint paint = shapeState.mPaint;
        int n = paint.getAlpha();
        paint.setAlpha(ShapeDrawable.modulateAlpha(n, shapeState.mAlpha));
        if (paint.getAlpha() != 0 || paint.getXfermode() != null || paint.hasShadowLayer()) {
            boolean bl;
            if (this.mBlendModeColorFilter != null && paint.getColorFilter() == null) {
                paint.setColorFilter(this.mBlendModeColorFilter);
                bl = true;
            } else {
                bl = false;
            }
            if (shapeState.mShape != null) {
                int n2 = canvas.save();
                canvas.translate(rect.left, rect.top);
                this.onDraw(shapeState.mShape, canvas, paint);
                canvas.restoreToCount(n2);
            } else {
                canvas.drawRect(rect, paint);
            }
            if (bl) {
                paint.setColorFilter(null);
            }
        }
        paint.setAlpha(n);
    }

    @Override
    public int getAlpha() {
        return this.mShapeState.mAlpha;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mShapeState.getChangingConfigurations();
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        this.mShapeState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mShapeState;
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mShapeState.mIntrinsicHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return this.mShapeState.mIntrinsicWidth;
    }

    @Override
    public int getOpacity() {
        Paint paint;
        if (this.mShapeState.mShape == null && (paint = this.mShapeState.mPaint).getXfermode() == null) {
            int n = paint.getAlpha();
            if (n == 0) {
                return -2;
            }
            if (n == 255) {
                return -1;
            }
        }
        return -3;
    }

    @Override
    public void getOutline(Outline outline) {
        if (this.mShapeState.mShape != null) {
            this.mShapeState.mShape.getOutline(outline);
            outline.setAlpha((float)this.getAlpha() / 255.0f);
        }
    }

    @Override
    public boolean getPadding(Rect rect) {
        if (this.mShapeState.mPadding != null) {
            rect.set(this.mShapeState.mPadding);
            return true;
        }
        return super.getPadding(rect);
    }

    public Paint getPaint() {
        return this.mShapeState.mPaint;
    }

    public ShaderFactory getShaderFactory() {
        return this.mShapeState.mShaderFactory;
    }

    public Shape getShape() {
        return this.mShapeState.mShape;
    }

    @Override
    public boolean hasFocusStateSpecified() {
        boolean bl = this.mShapeState.mTint != null && this.mShapeState.mTint.hasFocusStateSpecified();
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme object) throws XmlPullParserException, IOException {
        int n;
        super.inflate(resources, xmlPullParser, attributeSet, (Resources.Theme)object);
        object = ShapeDrawable.obtainAttributes(resources, (Resources.Theme)object, attributeSet, R.styleable.ShapeDrawable);
        this.updateStateFromTypedArray((TypedArray)object);
        ((TypedArray)object).recycle();
        int n2 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
            if (n != 2 || this.inflateTag((String)(object = xmlPullParser.getName()), resources, xmlPullParser, attributeSet)) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown element: ");
            stringBuilder.append((String)object);
            stringBuilder.append(" for ShapeDrawable ");
            stringBuilder.append(this);
            Log.w("drawable", stringBuilder.toString());
        }
        this.updateLocalState();
    }

    protected boolean inflateTag(String object, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        if ("padding".equals(object)) {
            object = resources.obtainAttributes(attributeSet, R.styleable.ShapeDrawablePadding);
            this.setPadding(((TypedArray)object).getDimensionPixelOffset(0, 0), ((TypedArray)object).getDimensionPixelOffset(1, 0), ((TypedArray)object).getDimensionPixelOffset(2, 0), ((TypedArray)object).getDimensionPixelOffset(3, 0));
            ((TypedArray)object).recycle();
            return true;
        }
        return false;
    }

    @Override
    public boolean isStateful() {
        ShapeState shapeState = this.mShapeState;
        boolean bl = super.isStateful() || shapeState.mTint != null && shapeState.mTint.isStateful();
        return bl;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mShapeState = new ShapeState(this.mShapeState);
            this.updateLocalState();
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.updateShape();
    }

    protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
        shape.draw(canvas, paint);
    }

    @Override
    protected boolean onStateChange(int[] object) {
        object = this.mShapeState;
        if (object.mTint != null && object.mBlendMode != null) {
            this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, object.mTint, object.mBlendMode);
            return true;
        }
        return false;
    }

    @Override
    public void setAlpha(int n) {
        this.mShapeState.mAlpha = n;
        this.invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mShapeState.mPaint.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    @Override
    public void setDither(boolean bl) {
        this.mShapeState.mPaint.setDither(bl);
        this.invalidateSelf();
    }

    public void setIntrinsicHeight(int n) {
        this.mShapeState.mIntrinsicHeight = n;
        this.invalidateSelf();
    }

    public void setIntrinsicWidth(int n) {
        this.mShapeState.mIntrinsicWidth = n;
        this.invalidateSelf();
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        if ((n | n2 | n3 | n4) == 0) {
            this.mShapeState.mPadding = null;
        } else {
            if (this.mShapeState.mPadding == null) {
                this.mShapeState.mPadding = new Rect();
            }
            this.mShapeState.mPadding.set(n, n2, n3, n4);
        }
        this.invalidateSelf();
    }

    public void setPadding(Rect rect) {
        if (rect == null) {
            this.mShapeState.mPadding = null;
        } else {
            if (this.mShapeState.mPadding == null) {
                this.mShapeState.mPadding = new Rect();
            }
            this.mShapeState.mPadding.set(rect);
        }
        this.invalidateSelf();
    }

    public void setShaderFactory(ShaderFactory shaderFactory) {
        this.mShapeState.mShaderFactory = shaderFactory;
    }

    public void setShape(Shape shape) {
        this.mShapeState.mShape = shape;
        this.updateShape();
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        ShapeState shapeState = this.mShapeState;
        shapeState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, shapeState.mTint, blendMode);
        this.invalidateSelf();
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        ShapeState shapeState = this.mShapeState;
        shapeState.mTint = colorStateList;
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, shapeState.mBlendMode);
        this.invalidateSelf();
    }

    @Override
    public void setXfermode(Xfermode xfermode) {
        this.mShapeState.mPaint.setXfermode(xfermode);
        this.invalidateSelf();
    }

    public static abstract class ShaderFactory {
        public abstract Shader resize(int var1, int var2);
    }

    static final class ShapeState
    extends Drawable.ConstantState {
        int mAlpha = 255;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        int mChangingConfigurations;
        int mIntrinsicHeight;
        int mIntrinsicWidth;
        Rect mPadding;
        final Paint mPaint;
        ShaderFactory mShaderFactory;
        Shape mShape;
        int[] mThemeAttrs;
        ColorStateList mTint;

        ShapeState() {
            this.mPaint = new Paint(1);
        }

        ShapeState(ShapeState shapeState) {
            this.mChangingConfigurations = shapeState.mChangingConfigurations;
            this.mPaint = new Paint(shapeState.mPaint);
            this.mThemeAttrs = shapeState.mThemeAttrs;
            Object object = shapeState.mShape;
            if (object != null) {
                try {
                    this.mShape = ((Shape)object).clone();
                }
                catch (CloneNotSupportedException cloneNotSupportedException) {
                    this.mShape = shapeState.mShape;
                }
            }
            this.mTint = shapeState.mTint;
            this.mBlendMode = shapeState.mBlendMode;
            object = shapeState.mPadding;
            if (object != null) {
                this.mPadding = new Rect((Rect)object);
            }
            this.mIntrinsicWidth = shapeState.mIntrinsicWidth;
            this.mIntrinsicHeight = shapeState.mIntrinsicHeight;
            this.mAlpha = shapeState.mAlpha;
            this.mShaderFactory = shapeState.mShaderFactory;
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
            return new ShapeDrawable(new ShapeState(this), null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new ShapeDrawable(new ShapeState(this), resources);
        }
    }

}

