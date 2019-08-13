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
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
import android.graphics.NinePatch;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable._$$Lambda$NinePatchDrawable$yQvfm7FAkslD5wdGFysjgwt8cLE;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class NinePatchDrawable
extends Drawable {
    private static final boolean DEFAULT_DITHER = false;
    private int mBitmapHeight = -1;
    private int mBitmapWidth = -1;
    private BlendModeColorFilter mBlendModeFilter;
    private boolean mMutated;
    @UnsupportedAppUsage
    private NinePatchState mNinePatchState;
    private Insets mOpticalInsets = Insets.NONE;
    private Rect mOutlineInsets;
    private float mOutlineRadius;
    private Rect mPadding;
    private Paint mPaint;
    private int mTargetDensity = 160;
    private Rect mTempRect;

    NinePatchDrawable() {
        this.mNinePatchState = new NinePatchState();
    }

    public NinePatchDrawable(Resources resources, Bitmap bitmap, byte[] arrby, Rect rect, Rect rect2, String string2) {
        this(new NinePatchState(new NinePatch(bitmap, arrby, string2), rect, rect2), resources);
    }

    public NinePatchDrawable(Resources resources, Bitmap bitmap, byte[] arrby, Rect rect, String string2) {
        this(new NinePatchState(new NinePatch(bitmap, arrby, string2), rect), resources);
    }

    public NinePatchDrawable(Resources resources, NinePatch ninePatch) {
        this(new NinePatchState(ninePatch, new Rect()), resources);
    }

    @Deprecated
    public NinePatchDrawable(Bitmap bitmap, byte[] arrby, Rect rect, String string2) {
        this(new NinePatchState(new NinePatch(bitmap, arrby, string2), rect), null);
    }

    @Deprecated
    public NinePatchDrawable(NinePatch ninePatch) {
        this(new NinePatchState(ninePatch, new Rect()), null);
    }

    private NinePatchDrawable(NinePatchState ninePatchState, Resources resources) {
        this.mNinePatchState = ninePatchState;
        this.updateLocalState(resources);
    }

    private void computeBitmapSize() {
        Object object = this.mNinePatchState.mNinePatch;
        if (object == null) {
            return;
        }
        int n = this.mTargetDensity;
        int n2 = ((NinePatch)object).getDensity() == 0 ? n : ((NinePatch)object).getDensity();
        Parcelable parcelable = this.mNinePatchState.mOpticalInsets;
        this.mOpticalInsets = parcelable != Insets.NONE ? Insets.of(Drawable.scaleFromDensity(parcelable.left, n2, n, true), Drawable.scaleFromDensity(parcelable.top, n2, n, true), Drawable.scaleFromDensity(parcelable.right, n2, n, true), Drawable.scaleFromDensity(parcelable.bottom, n2, n, true)) : Insets.NONE;
        parcelable = this.mNinePatchState.mPadding;
        if (parcelable != null) {
            if (this.mPadding == null) {
                this.mPadding = new Rect();
            }
            this.mPadding.left = Drawable.scaleFromDensity(((Rect)parcelable).left, n2, n, true);
            this.mPadding.top = Drawable.scaleFromDensity(((Rect)parcelable).top, n2, n, true);
            this.mPadding.right = Drawable.scaleFromDensity(((Rect)parcelable).right, n2, n, true);
            this.mPadding.bottom = Drawable.scaleFromDensity(((Rect)parcelable).bottom, n2, n, true);
        } else {
            this.mPadding = null;
        }
        this.mBitmapHeight = Drawable.scaleFromDensity(((NinePatch)object).getHeight(), n2, n, true);
        this.mBitmapWidth = Drawable.scaleFromDensity(((NinePatch)object).getWidth(), n2, n, true);
        object = ((NinePatch)object).getBitmap().getNinePatchInsets();
        if (object != null) {
            parcelable = ((NinePatch.InsetStruct)object).outlineRect;
            this.mOutlineInsets = NinePatch.InsetStruct.scaleInsets(((Rect)parcelable).left, ((Rect)parcelable).top, ((Rect)parcelable).right, ((Rect)parcelable).bottom, (float)n / (float)n2);
            this.mOutlineRadius = Drawable.scaleFromDensity(((NinePatch.InsetStruct)object).outlineRadius, n2, n);
        } else {
            this.mOutlineInsets = null;
        }
    }

    static /* synthetic */ void lambda$updateStateFromTypedArray$0(Rect rect, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setOutPaddingRect(rect);
        imageDecoder.setAllocator(1);
    }

    private boolean needsMirroring() {
        boolean bl = this.isAutoMirrored();
        boolean bl2 = true;
        if (!bl || this.getLayoutDirection() != 1) {
            bl2 = false;
        }
        return bl2;
    }

    private void updateLocalState(Resources resources) {
        NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState.mDither) {
            this.setDither(ninePatchState.mDither);
        }
        this.mTargetDensity = resources == null && ninePatchState.mNinePatch != null ? ninePatchState.mNinePatch.getDensity() : Drawable.resolveDensity(resources, this.mTargetDensity);
        this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, ninePatchState.mTint, ninePatchState.mBlendMode);
        this.computeBitmapSize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateStateFromTypedArray(TypedArray object) throws XmlPullParserException {
        Object object2 = ((TypedArray)object).getResources();
        NinePatchState ninePatchState = this.mNinePatchState;
        ninePatchState.mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        ninePatchState.mThemeAttrs = ((TypedArray)object).extractThemeAttrs();
        ninePatchState.mDither = ((TypedArray)object).getBoolean(1, ninePatchState.mDither);
        int n = ((TypedArray)object).getResourceId(0, 0);
        if (n != 0) {
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            Bitmap bitmap = null;
            Object object3 = bitmap;
            try {
                object3 = bitmap;
                Object object4 = new TypedValue();
                object3 = bitmap;
                InputStream inputStream = ((Resources)object2).openRawResource(n, (TypedValue)object4);
                n = 0;
                object3 = bitmap;
                if (((TypedValue)object4).density == 0) {
                    n = 160;
                } else {
                    object3 = bitmap;
                    if (((TypedValue)object4).density != 65535) {
                        object3 = bitmap;
                        n = ((TypedValue)object4).density;
                    }
                }
                object3 = bitmap;
                object2 = ImageDecoder.createSource((Resources)object2, inputStream, n);
                object3 = bitmap;
                object3 = bitmap;
                object4 = new _$$Lambda$NinePatchDrawable$yQvfm7FAkslD5wdGFysjgwt8cLE(rect);
                object3 = bitmap;
                bitmap = ImageDecoder.decodeBitmap((ImageDecoder.Source)object2, (ImageDecoder.OnHeaderDecodedListener)object4);
                object3 = bitmap;
                inputStream.close();
                object3 = bitmap;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (object3 == null) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(((TypedArray)object).getPositionDescription());
                ((StringBuilder)object3).append(": <nine-patch> requires a valid src attribute");
                throw new XmlPullParserException(((StringBuilder)object3).toString());
            }
            if (((Bitmap)object3).getNinePatchChunk() == null) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(((TypedArray)object).getPositionDescription());
                ((StringBuilder)object3).append(": <nine-patch> requires a valid 9-patch source image");
                throw new XmlPullParserException(((StringBuilder)object3).toString());
            }
            ((Bitmap)object3).getOpticalInsets(rect2);
            ninePatchState.mNinePatch = new NinePatch((Bitmap)object3, ((Bitmap)object3).getNinePatchChunk());
            ninePatchState.mPadding = rect;
            ninePatchState.mOpticalInsets = Insets.of(rect2);
        }
        ninePatchState.mAutoMirrored = ((TypedArray)object).getBoolean(4, ninePatchState.mAutoMirrored);
        ninePatchState.mBaseAlpha = ((TypedArray)object).getFloat(3, ninePatchState.mBaseAlpha);
        n = ((TypedArray)object).getInt(5, -1);
        if (n != -1) {
            ninePatchState.mBlendMode = Drawable.parseBlendMode(n, BlendMode.SRC_IN);
        }
        if ((object = ((TypedArray)object).getColorStateList(2)) != null) {
            ninePatchState.mTint = object;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void applyTheme(Resources.Theme var1_1) {
        block8 : {
            super.applyTheme(var1_1);
            var2_3 = this.mNinePatchState;
            if (var2_3 == null) {
                return;
            }
            if (var2_3.mThemeAttrs != null) {
                var3_4 = var1_1.resolveAttributes(var2_3.mThemeAttrs, R.styleable.NinePatchDrawable);
                this.updateStateFromTypedArray(var3_4);
lbl9: // 2 sources:
                do {
                    var3_4.recycle();
                    break block8;
                    break;
                } while (true);
                {
                    catch (Throwable var1_2) {
                    }
                    catch (XmlPullParserException var4_5) {}
                    {
                        NinePatchDrawable.rethrowAsRuntimeException((Exception)var4_5);
                        ** continue;
                    }
                }
                var3_4.recycle();
                throw var1_2;
            }
        }
        if (var2_3.mTint != null && var2_3.mTint.canApplyTheme()) {
            var2_3.mTint = var2_3.mTint.obtainForTheme(var1_1);
        }
        this.updateLocalState(var1_1.getResources());
    }

    @Override
    public boolean canApplyTheme() {
        NinePatchState ninePatchState = this.mNinePatchState;
        boolean bl = ninePatchState != null && ninePatchState.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        boolean bl;
        int n;
        NinePatchState ninePatchState = this.mNinePatchState;
        Rect rect = this.getBounds();
        int n2 = -1;
        if (this.mBlendModeFilter != null && this.getPaint().getColorFilter() == null) {
            this.mPaint.setColorFilter(this.mBlendModeFilter);
            bl = true;
        } else {
            bl = false;
        }
        if (ninePatchState.mBaseAlpha != 1.0f) {
            n = this.getPaint().getAlpha();
            this.mPaint.setAlpha((int)((float)n * ninePatchState.mBaseAlpha + 0.5f));
        } else {
            n = -1;
        }
        int n3 = canvas.getDensity() == 0 && ninePatchState.mNinePatch.getDensity() != 0 ? 1 : 0;
        Rect rect2 = rect;
        if (n3 != 0) {
            n2 = -1 >= 0 ? -1 : canvas.save();
            float f = (float)this.mTargetDensity / (float)ninePatchState.mNinePatch.getDensity();
            canvas.scale(f, f, rect.left, rect.top);
            if (this.mTempRect == null) {
                this.mTempRect = new Rect();
            }
            rect2 = this.mTempRect;
            rect2.left = rect.left;
            rect2.top = rect.top;
            rect2.right = rect.left + Math.round((float)rect.width() / f);
            rect2.bottom = rect.top + Math.round((float)rect.height() / f);
        }
        n3 = n2;
        if (this.needsMirroring()) {
            if (n2 < 0) {
                n2 = canvas.save();
            }
            canvas.scale(-1.0f, 1.0f, (float)(rect2.left + rect2.right) / 2.0f, (float)(rect2.top + rect2.bottom) / 2.0f);
            n3 = n2;
        }
        ninePatchState.mNinePatch.draw(canvas, rect2, this.mPaint);
        if (n3 >= 0) {
            canvas.restoreToCount(n3);
        }
        if (bl) {
            this.mPaint.setColorFilter(null);
        }
        if (n >= 0) {
            this.mPaint.setAlpha(n);
        }
    }

    @Override
    public int getAlpha() {
        if (this.mPaint == null) {
            return 255;
        }
        return this.getPaint().getAlpha();
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mNinePatchState.getChangingConfigurations();
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        this.mNinePatchState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mNinePatchState;
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override
    public int getOpacity() {
        Paint paint;
        int n = !(this.mNinePatchState.mNinePatch.hasAlpha() || (paint = this.mPaint) != null && paint.getAlpha() < 255) ? -1 : -3;
        return n;
    }

    @Override
    public Insets getOpticalInsets() {
        Insets insets = this.mOpticalInsets;
        if (this.needsMirroring()) {
            return Insets.of(insets.right, insets.top, insets.left, insets.bottom);
        }
        return insets;
    }

    @Override
    public void getOutline(Outline outline) {
        Rect rect = this.getBounds();
        if (rect.isEmpty()) {
            return;
        }
        Object object = this.mNinePatchState;
        if (object != null && this.mOutlineInsets != null && (object = ((NinePatchState)object).mNinePatch.getBitmap().getNinePatchInsets()) != null) {
            outline.setRoundRect(rect.left + this.mOutlineInsets.left, rect.top + this.mOutlineInsets.top, rect.right - this.mOutlineInsets.right, rect.bottom - this.mOutlineInsets.bottom, this.mOutlineRadius);
            outline.setAlpha(((NinePatch.InsetStruct)object).outlineAlpha * ((float)this.getAlpha() / 255.0f));
            return;
        }
        super.getOutline(outline);
    }

    @Override
    public boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2 != null) {
            rect.set(rect2);
            boolean bl = (rect.left | rect.top | rect.right | rect.bottom) != 0;
            return bl;
        }
        return super.getPadding(rect);
    }

    public Paint getPaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
            this.mPaint.setDither(false);
        }
        return this.mPaint;
    }

    @Override
    public Region getTransparentRegion() {
        return this.mNinePatchState.mNinePatch.getTransparentRegion(this.getBounds());
    }

    @Override
    public boolean hasFocusStateSpecified() {
        boolean bl = this.mNinePatchState.mTint != null && this.mNinePatchState.mTint.hasFocusStateSpecified();
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser object, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, (XmlPullParser)object, attributeSet, theme);
        object = NinePatchDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.NinePatchDrawable);
        this.updateStateFromTypedArray((TypedArray)object);
        ((TypedArray)object).recycle();
        this.updateLocalState(resources);
    }

    @Override
    public boolean isAutoMirrored() {
        return this.mNinePatchState.mAutoMirrored;
    }

    @Override
    public boolean isFilterBitmap() {
        boolean bl = this.mPaint != null && this.getPaint().isFilterBitmap();
        return bl;
    }

    @Override
    public boolean isStateful() {
        NinePatchState ninePatchState = this.mNinePatchState;
        boolean bl = super.isStateful() || ninePatchState.mTint != null && ninePatchState.mTint.isStateful();
        return bl;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mNinePatchState = new NinePatchState(this.mNinePatchState);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected boolean onStateChange(int[] object) {
        object = this.mNinePatchState;
        if (object.mTint != null && object.mBlendMode != null) {
            this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, object.mTint, object.mBlendMode);
            return true;
        }
        return false;
    }

    @Override
    public void setAlpha(int n) {
        if (this.mPaint == null && n == 255) {
            return;
        }
        this.getPaint().setAlpha(n);
        this.invalidateSelf();
    }

    @Override
    public void setAutoMirrored(boolean bl) {
        this.mNinePatchState.mAutoMirrored = bl;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mPaint == null && colorFilter == null) {
            return;
        }
        this.getPaint().setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    @Override
    public void setDither(boolean bl) {
        if (this.mPaint == null && !bl) {
            return;
        }
        this.getPaint().setDither(bl);
        this.invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean bl) {
        this.getPaint().setFilterBitmap(bl);
        this.invalidateSelf();
    }

    public void setTargetDensity(int n) {
        int n2 = n;
        if (n == 0) {
            n2 = 160;
        }
        if (this.mTargetDensity != n2) {
            this.mTargetDensity = n2;
            this.computeBitmapSize();
            this.invalidateSelf();
        }
    }

    public void setTargetDensity(Canvas canvas) {
        this.setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        this.setTargetDensity(displayMetrics.densityDpi);
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        NinePatchState ninePatchState = this.mNinePatchState;
        ninePatchState.mBlendMode = blendMode;
        this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, ninePatchState.mTint, blendMode);
        this.invalidateSelf();
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        NinePatchState ninePatchState = this.mNinePatchState;
        ninePatchState.mTint = colorStateList;
        this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, colorStateList, ninePatchState.mBlendMode);
        this.invalidateSelf();
    }

    static final class NinePatchState
    extends Drawable.ConstantState {
        boolean mAutoMirrored = false;
        float mBaseAlpha = 1.0f;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        int mChangingConfigurations;
        boolean mDither = false;
        @UnsupportedAppUsage
        NinePatch mNinePatch = null;
        Insets mOpticalInsets = Insets.NONE;
        Rect mPadding = null;
        int[] mThemeAttrs;
        ColorStateList mTint = null;

        NinePatchState() {
        }

        NinePatchState(NinePatch ninePatch, Rect rect) {
            this(ninePatch, rect, null, false, false);
        }

        NinePatchState(NinePatch ninePatch, Rect rect, Rect rect2) {
            this(ninePatch, rect, rect2, false, false);
        }

        NinePatchState(NinePatch ninePatch, Rect rect, Rect rect2, boolean bl, boolean bl2) {
            this.mNinePatch = ninePatch;
            this.mPadding = rect;
            this.mOpticalInsets = Insets.of(rect2);
            this.mDither = bl;
            this.mAutoMirrored = bl2;
        }

        NinePatchState(NinePatchState ninePatchState) {
            this.mChangingConfigurations = ninePatchState.mChangingConfigurations;
            this.mNinePatch = ninePatchState.mNinePatch;
            this.mTint = ninePatchState.mTint;
            this.mBlendMode = ninePatchState.mBlendMode;
            this.mPadding = ninePatchState.mPadding;
            this.mOpticalInsets = ninePatchState.mOpticalInsets;
            this.mBaseAlpha = ninePatchState.mBaseAlpha;
            this.mDither = ninePatchState.mDither;
            this.mAutoMirrored = ninePatchState.mAutoMirrored;
            this.mThemeAttrs = ninePatchState.mThemeAttrs;
        }

        @Override
        public boolean canApplyTheme() {
            ColorStateList colorStateList;
            boolean bl = this.mThemeAttrs != null || (colorStateList = this.mTint) != null && colorStateList.canApplyTheme() || super.canApplyTheme();
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
            return new NinePatchDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new NinePatchDrawable(this, resources);
        }
    }

}

