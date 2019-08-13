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
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleBackground;
import android.graphics.drawable.RippleComponent;
import android.graphics.drawable.RippleForeground;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.android.internal.R;
import java.io.IOException;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class RippleDrawable
extends LayerDrawable {
    private static final int MASK_CONTENT = 1;
    private static final int MASK_EXPLICIT = 2;
    private static final int MASK_NONE = 0;
    private static final int MASK_UNKNOWN = -1;
    private static final int MAX_RIPPLES = 10;
    public static final int RADIUS_AUTO = -1;
    private RippleBackground mBackground;
    @UnsupportedAppUsage
    private int mDensity;
    private final Rect mDirtyBounds = new Rect();
    private final Rect mDrawingBounds = new Rect();
    private RippleForeground[] mExitingRipples;
    private int mExitingRipplesCount = 0;
    private boolean mForceSoftware;
    private boolean mHasPending;
    private boolean mHasValidMask;
    private final Rect mHotspotBounds = new Rect();
    private Drawable mMask;
    private Bitmap mMaskBuffer;
    private Canvas mMaskCanvas;
    private PorterDuffColorFilter mMaskColorFilter;
    private Matrix mMaskMatrix;
    private BitmapShader mMaskShader;
    private boolean mOverrideBounds;
    private float mPendingX;
    private float mPendingY;
    private RippleForeground mRipple;
    private boolean mRippleActive;
    private Paint mRipplePaint;
    @UnsupportedAppUsage
    private RippleState mState;
    private final Rect mTempRect = new Rect();

    RippleDrawable() {
        this(new RippleState(null, null, null), null);
    }

    public RippleDrawable(ColorStateList colorStateList, Drawable drawable2, Drawable drawable3) {
        this(new RippleState(null, null, null), null);
        if (colorStateList != null) {
            if (drawable2 != null) {
                this.addLayer(drawable2, null, 0, 0, 0, 0, 0);
            }
            if (drawable3 != null) {
                this.addLayer(drawable3, null, 16908334, 0, 0, 0, 0);
            }
            this.setColor(colorStateList);
            this.ensurePadding();
            this.refreshPadding();
            this.updateLocalState();
            return;
        }
        throw new IllegalArgumentException("RippleDrawable requires a non-null color");
    }

    private RippleDrawable(RippleState rippleState, Resources resources) {
        rippleState = this.mState = new RippleState(rippleState, this, resources);
        this.mLayerState = rippleState;
        this.mDensity = Drawable.resolveDensity(resources, rippleState.mDensity);
        if (this.mState.mNumChildren > 0) {
            this.ensurePadding();
            this.refreshPadding();
        }
        this.updateLocalState();
    }

    private void cancelExitingRipples() {
        int n = this.mExitingRipplesCount;
        Object[] arrobject = this.mExitingRipples;
        for (int i = 0; i < n; ++i) {
            arrobject[i].end();
        }
        if (arrobject != null) {
            Arrays.fill(arrobject, 0, n, null);
        }
        this.mExitingRipplesCount = 0;
        this.invalidateSelf(false);
    }

    private void clearHotspots() {
        RippleComponent rippleComponent = this.mRipple;
        if (rippleComponent != null) {
            ((RippleForeground)rippleComponent).end();
            this.mRipple = null;
            this.mRippleActive = false;
        }
        if ((rippleComponent = this.mBackground) != null) {
            ((RippleBackground)rippleComponent).setState(false, false, false);
        }
        this.cancelExitingRipples();
    }

    private void drawBackgroundAndRipples(Canvas canvas) {
        RippleForeground rippleForeground = this.mRipple;
        RippleForeground[] arrrippleForeground = this.mBackground;
        int n = this.mExitingRipplesCount;
        if (!(rippleForeground != null || n > 0 || arrrippleForeground != null && arrrippleForeground.isVisible())) {
            return;
        }
        float f = this.mHotspotBounds.exactCenterX();
        float f2 = this.mHotspotBounds.exactCenterY();
        canvas.translate(f, f2);
        Paint paint = this.getRipplePaint();
        if (arrrippleForeground != null && arrrippleForeground.isVisible()) {
            arrrippleForeground.draw(canvas, paint);
        }
        if (n > 0) {
            arrrippleForeground = this.mExitingRipples;
            for (int i = 0; i < n; ++i) {
                arrrippleForeground[i].draw(canvas, paint);
            }
        }
        if (rippleForeground != null) {
            rippleForeground.draw(canvas, paint);
        }
        canvas.translate(-f, -f2);
    }

    private void drawContent(Canvas canvas) {
        LayerDrawable.ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            if (arrchildDrawable[i].mId == 16908334) continue;
            arrchildDrawable[i].mDrawable.draw(canvas);
        }
    }

    private void drawMask(Canvas canvas) {
        this.mMask.draw(canvas);
    }

    private int getMaskType() {
        Object object;
        if (!(this.mRipple != null || this.mExitingRipplesCount > 0 || (object = this.mBackground) != null && object.isVisible())) {
            return -1;
        }
        object = this.mMask;
        if (object != null) {
            if (object.getOpacity() == -1) {
                return 0;
            }
            return 2;
        }
        object = this.mLayerState.mChildren;
        int n = this.mLayerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            if (object[i].mDrawable.getOpacity() == -1) continue;
            return 1;
        }
        return 0;
    }

    private boolean isBounded() {
        boolean bl = this.getNumberOfLayers() > 0;
        return bl;
    }

    private void onHotspotBoundsChanged() {
        int n = this.mExitingRipplesCount;
        Object object = this.mExitingRipples;
        for (int i = 0; i < n; ++i) {
            object[i].onHotspotBoundsChanged();
        }
        object = this.mRipple;
        if (object != null) {
            ((RippleComponent)object).onHotspotBoundsChanged();
        }
        if ((object = this.mBackground) != null) {
            ((RippleComponent)object).onHotspotBoundsChanged();
        }
    }

    private void pruneRipples() {
        int n;
        int n2 = 0;
        RippleForeground[] arrrippleForeground = this.mExitingRipples;
        int n3 = this.mExitingRipplesCount;
        for (n = 0; n < n3; ++n) {
            int n4 = n2;
            if (!arrrippleForeground[n].hasFinishedExit()) {
                arrrippleForeground[n2] = arrrippleForeground[n];
                n4 = n2 + 1;
            }
            n2 = n4;
        }
        for (n = n2; n < n3; ++n) {
            arrrippleForeground[n] = null;
        }
        this.mExitingRipplesCount = n2;
    }

    private void setBackgroundActive(boolean bl, boolean bl2, boolean bl3) {
        RippleBackground rippleBackground;
        if (this.mBackground == null && (bl || bl2)) {
            this.mBackground = new RippleBackground(this, this.mHotspotBounds, this.isBounded());
            this.mBackground.setup(this.mState.mMaxRadius, this.mDensity);
        }
        if ((rippleBackground = this.mBackground) != null) {
            rippleBackground.setState(bl2, bl, bl3);
        }
    }

    private void setRippleActive(boolean bl) {
        if (this.mRippleActive != bl) {
            this.mRippleActive = bl;
            if (bl) {
                this.tryRippleEnter();
            } else {
                this.tryRippleExit();
            }
        }
    }

    private void tryRippleEnter() {
        if (this.mExitingRipplesCount >= 10) {
            return;
        }
        if (this.mRipple == null) {
            float f;
            float f2;
            if (this.mHasPending) {
                this.mHasPending = false;
                f2 = this.mPendingX;
                f = this.mPendingY;
            } else {
                f2 = this.mHotspotBounds.exactCenterX();
                f = this.mHotspotBounds.exactCenterY();
            }
            this.mRipple = new RippleForeground(this, this.mHotspotBounds, f2, f, this.mForceSoftware);
        }
        this.mRipple.setup(this.mState.mMaxRadius, this.mDensity);
        this.mRipple.enter();
    }

    private void tryRippleExit() {
        if (this.mRipple != null) {
            RippleForeground rippleForeground;
            if (this.mExitingRipples == null) {
                this.mExitingRipples = new RippleForeground[10];
            }
            RippleForeground[] arrrippleForeground = this.mExitingRipples;
            int n = this.mExitingRipplesCount;
            this.mExitingRipplesCount = n + 1;
            arrrippleForeground[n] = rippleForeground = this.mRipple;
            rippleForeground.exit();
            this.mRipple = null;
        }
    }

    private void updateLocalState() {
        this.mMask = this.findDrawableByLayerId(16908334);
    }

    private void updateMaskShaderIfNeeded() {
        if (this.mHasValidMask) {
            return;
        }
        int n = this.getMaskType();
        if (n == -1) {
            return;
        }
        this.mHasValidMask = true;
        Parcelable parcelable = this.getBounds();
        if (n != 0 && !((Rect)parcelable).isEmpty()) {
            Object object = this.mMaskBuffer;
            if (object != null && ((Bitmap)object).getWidth() == ((Rect)parcelable).width() && this.mMaskBuffer.getHeight() == ((Rect)parcelable).height()) {
                this.mMaskBuffer.eraseColor(0);
            } else {
                object = this.mMaskBuffer;
                if (object != null) {
                    ((Bitmap)object).recycle();
                }
                this.mMaskBuffer = Bitmap.createBitmap(((Rect)parcelable).width(), ((Rect)parcelable).height(), Bitmap.Config.ALPHA_8);
                this.mMaskShader = new BitmapShader(this.mMaskBuffer, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                this.mMaskCanvas = new Canvas(this.mMaskBuffer);
            }
            object = this.mMaskMatrix;
            if (object == null) {
                this.mMaskMatrix = new Matrix();
            } else {
                ((Matrix)object).reset();
            }
            if (this.mMaskColorFilter == null) {
                this.mMaskColorFilter = new PorterDuffColorFilter(0, PorterDuff.Mode.SRC_IN);
            }
            int n2 = ((Rect)parcelable).left;
            int n3 = ((Rect)parcelable).top;
            this.mMaskCanvas.translate(-n2, -n3);
            if (n == 2) {
                this.drawMask(this.mMaskCanvas);
            } else if (n == 1) {
                this.drawContent(this.mMaskCanvas);
            }
            this.mMaskCanvas.translate(n2, n3);
            return;
        }
        parcelable = this.mMaskBuffer;
        if (parcelable != null) {
            ((Bitmap)parcelable).recycle();
            this.mMaskBuffer = null;
            this.mMaskShader = null;
            this.mMaskCanvas = null;
        }
        this.mMaskMatrix = null;
        this.mMaskColorFilter = null;
    }

    private void updateStateFromTypedArray(TypedArray typedArray) throws XmlPullParserException {
        Object object = this.mState;
        ((RippleState)object).mChangingConfigurations |= typedArray.getChangingConfigurations();
        ((RippleState)object).mTouchThemeAttrs = typedArray.extractThemeAttrs();
        object = typedArray.getColorStateList(0);
        if (object != null) {
            this.mState.mColor = object;
        }
        object = this.mState;
        ((RippleState)object).mMaxRadius = typedArray.getDimensionPixelSize(1, ((RippleState)object).mMaxRadius);
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (this.mState.mColor == null && (this.mState.mTouchThemeAttrs == null || this.mState.mTouchThemeAttrs[0] == 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(typedArray.getPositionDescription());
            stringBuilder.append(": <ripple> requires a valid color attribute");
            throw new XmlPullParserException(stringBuilder.toString());
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
            var2_3 = this.mState;
            if (var2_3 == null) {
                return;
            }
            if (var2_3.mTouchThemeAttrs != null) {
                var3_4 = var1_1.resolveAttributes(var2_3.mTouchThemeAttrs, R.styleable.RippleDrawable);
                this.updateStateFromTypedArray(var3_4);
                this.verifyRequiredAttributes(var3_4);
lbl10: // 2 sources:
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
                        RippleDrawable.rethrowAsRuntimeException((Exception)var4_5);
                        ** continue;
                    }
                }
                var3_4.recycle();
                throw var1_2;
            }
        }
        if (var2_3.mColor != null && var2_3.mColor.canApplyTheme()) {
            var2_3.mColor = var2_3.mColor.obtainForTheme(var1_1);
        }
        this.updateLocalState();
    }

    @Override
    public boolean canApplyTheme() {
        RippleState rippleState = this.mState;
        boolean bl = rippleState != null && rippleState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    @Override
    RippleState createConstantState(LayerDrawable.LayerState layerState, Resources resources) {
        return new RippleState(layerState, this, resources);
    }

    @Override
    public void draw(Canvas canvas) {
        this.pruneRipples();
        Rect rect = this.getDirtyBounds();
        int n = canvas.save(2);
        if (this.isBounded()) {
            canvas.clipRect(rect);
        }
        this.drawContent(canvas);
        this.drawBackgroundAndRipples(canvas);
        canvas.restoreToCount(n);
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override
    public Rect getDirtyBounds() {
        if (!this.isBounded()) {
            Rect rect = this.mDrawingBounds;
            Rect rect2 = this.mDirtyBounds;
            rect2.set(rect);
            rect.setEmpty();
            int n = (int)this.mHotspotBounds.exactCenterX();
            int n2 = (int)this.mHotspotBounds.exactCenterY();
            Rect rect3 = this.mTempRect;
            Object object = this.mExitingRipples;
            int n3 = this.mExitingRipplesCount;
            for (int i = 0; i < n3; ++i) {
                object[i].getBounds(rect3);
                rect3.offset(n, n2);
                rect.union(rect3);
            }
            object = this.mBackground;
            if (object != null) {
                ((RippleComponent)object).getBounds(rect3);
                rect3.offset(n, n2);
                rect.union(rect3);
            }
            rect2.union(rect);
            rect2.union(super.getDirtyBounds());
            return rect2;
        }
        return this.getBounds();
    }

    @Override
    public void getHotspotBounds(Rect rect) {
        rect.set(this.mHotspotBounds);
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    @Override
    public void getOutline(Outline outline) {
        LayerDrawable.LayerState layerState = this.mLayerState;
        LayerDrawable.ChildDrawable[] arrchildDrawable = layerState.mChildren;
        int n = layerState.mNumChildren;
        for (int i = 0; i < n; ++i) {
            if (arrchildDrawable[i].mId == 16908334) continue;
            arrchildDrawable[i].mDrawable.getOutline(outline);
            if (outline.isEmpty()) continue;
            return;
        }
    }

    public int getRadius() {
        return this.mState.mMaxRadius;
    }

    @UnsupportedAppUsage
    Paint getRipplePaint() {
        Object object;
        int n;
        if (this.mRipplePaint == null) {
            this.mRipplePaint = new Paint();
            this.mRipplePaint.setAntiAlias(true);
            this.mRipplePaint.setStyle(Paint.Style.FILL);
        }
        float f = this.mHotspotBounds.exactCenterX();
        float f2 = this.mHotspotBounds.exactCenterY();
        this.updateMaskShaderIfNeeded();
        if (this.mMaskShader != null) {
            object = this.getBounds();
            this.mMaskMatrix.setTranslate((float)((Rect)object).left - f, (float)((Rect)object).top - f2);
            this.mMaskShader.setLocalMatrix(this.mMaskMatrix);
        }
        int n2 = n = this.mState.mColor.getColorForState(this.getState(), -16777216);
        if (Color.alpha(n) > 128) {
            n2 = 16777215 & n | Integer.MIN_VALUE;
        }
        Paint paint = this.mRipplePaint;
        object = this.mMaskColorFilter;
        if (object != null) {
            n = n2 | -16777216;
            if (((PorterDuffColorFilter)object).getColor() != n) {
                this.mMaskColorFilter = new PorterDuffColorFilter(n, this.mMaskColorFilter.getMode());
            }
            paint.setColor(-16777216 & n2);
            paint.setColorFilter(this.mMaskColorFilter);
            paint.setShader(this.mMaskShader);
        } else {
            paint.setColor(n2);
            paint.setColorFilter(null);
            paint.setShader(null);
        }
        return paint;
    }

    @Override
    public boolean hasFocusStateSpecified() {
        return true;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = RippleDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.RippleDrawable);
        this.setPaddingMode(1);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateStateFromTypedArray(typedArray);
        this.verifyRequiredAttributes(typedArray);
        typedArray.recycle();
        this.updateLocalState();
    }

    @Override
    public void invalidateSelf() {
        this.invalidateSelf(true);
    }

    void invalidateSelf(boolean bl) {
        super.invalidateSelf();
        if (bl) {
            this.mHasValidMask = false;
        }
    }

    @Override
    public boolean isProjected() {
        if (this.isBounded()) {
            return false;
        }
        int n = this.mState.mMaxRadius;
        Rect rect = this.getBounds();
        Rect rect2 = this.mHotspotBounds;
        return n == -1 || n > rect2.width() / 2 || n > rect2.height() / 2 || !rect.equals(rect2) && !rect.contains(rect2);
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        RippleComponent rippleComponent = this.mRipple;
        if (rippleComponent != null) {
            ((RippleForeground)rippleComponent).end();
        }
        if ((rippleComponent = this.mBackground) != null) {
            ((RippleBackground)rippleComponent).jumpToFinal();
        }
        this.cancelExitingRipples();
    }

    @Override
    public Drawable mutate() {
        super.mutate();
        this.mState = (RippleState)this.mLayerState;
        this.mMask = this.findDrawableByLayerId(16908334);
        return this;
    }

    @Override
    protected void onBoundsChange(Rect object) {
        super.onBoundsChange((Rect)object);
        if (!this.mOverrideBounds) {
            this.mHotspotBounds.set((Rect)object);
            this.onHotspotBoundsChanged();
        }
        int n = this.mExitingRipplesCount;
        object = this.mExitingRipples;
        for (int i = 0; i < n; ++i) {
            object[i].onBoundsChange();
        }
        object = this.mBackground;
        if (object != null) {
            ((RippleComponent)object).onBoundsChange();
        }
        if ((object = this.mRipple) != null) {
            ((RippleComponent)object).onBoundsChange();
        }
        this.invalidateSelf();
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        boolean bl;
        boolean bl2 = super.onStateChange(arrn);
        int n = arrn.length;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = false;
        for (int i = 0; i < n; ++i) {
            boolean bl8;
            boolean bl9;
            int n2 = arrn[i];
            if (n2 == 16842910) {
                bl9 = true;
                bl = bl6;
                bl8 = bl5;
            } else if (n2 == 16842908) {
                bl8 = true;
                bl9 = bl7;
                bl = bl6;
            } else if (n2 == 16842919) {
                bl = true;
                bl9 = bl7;
                bl8 = bl5;
            } else {
                bl9 = bl7;
                bl = bl6;
                bl8 = bl5;
                if (n2 == 16843623) {
                    bl4 = true;
                    bl8 = bl5;
                    bl = bl6;
                    bl9 = bl7;
                }
            }
            bl7 = bl9;
            bl6 = bl;
            bl5 = bl8;
        }
        bl = bl3;
        if (bl7) {
            bl = bl3;
            if (bl6) {
                bl = true;
            }
        }
        this.setRippleActive(bl);
        this.setBackgroundActive(bl4, bl5, bl6);
        return bl2;
    }

    public void setColor(ColorStateList colorStateList) {
        this.mState.mColor = colorStateList;
        this.invalidateSelf(false);
    }

    @Override
    public boolean setDrawableByLayerId(int n, Drawable drawable2) {
        if (super.setDrawableByLayerId(n, drawable2)) {
            if (n == 16908334) {
                this.mMask = drawable2;
                this.mHasValidMask = false;
            }
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public void setForceSoftware(boolean bl) {
        this.mForceSoftware = bl;
    }

    @Override
    public void setHotspot(float f, float f2) {
        RippleForeground rippleForeground;
        if (this.mRipple == null || this.mBackground == null) {
            this.mPendingX = f;
            this.mPendingY = f2;
            this.mHasPending = true;
        }
        if ((rippleForeground = this.mRipple) != null) {
            rippleForeground.move(f, f2);
        }
    }

    @Override
    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        this.mOverrideBounds = true;
        this.mHotspotBounds.set(n, n2, n3, n4);
        this.onHotspotBoundsChanged();
    }

    @Override
    public void setPaddingMode(int n) {
        super.setPaddingMode(n);
    }

    public void setRadius(int n) {
        this.mState.mMaxRadius = n;
        this.invalidateSelf(false);
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        bl2 = super.setVisible(bl, bl2);
        if (!bl) {
            this.clearHotspots();
        } else if (bl2) {
            if (this.mRippleActive) {
                this.tryRippleEnter();
            }
            this.jumpToCurrentState();
        }
        return bl2;
    }

    static class RippleState
    extends LayerDrawable.LayerState {
        @UnsupportedAppUsage
        ColorStateList mColor = ColorStateList.valueOf(-65281);
        int mMaxRadius = -1;
        int[] mTouchThemeAttrs;

        public RippleState(LayerDrawable.LayerState layerState, RippleDrawable object, Resources resources) {
            super(layerState, (LayerDrawable)object, resources);
            if (layerState != null && layerState instanceof RippleState) {
                object = (RippleState)layerState;
                this.mTouchThemeAttrs = ((RippleState)object).mTouchThemeAttrs;
                this.mColor = ((RippleState)object).mColor;
                this.mMaxRadius = ((RippleState)object).mMaxRadius;
                if (((RippleState)object).mDensity != this.mDensity) {
                    this.applyDensityScaling(layerState.mDensity, this.mDensity);
                }
            }
        }

        private void applyDensityScaling(int n, int n2) {
            int n3 = this.mMaxRadius;
            if (n3 != -1) {
                this.mMaxRadius = Drawable.scaleFromDensity(n3, n, n2, true);
            }
        }

        @Override
        public boolean canApplyTheme() {
            ColorStateList colorStateList;
            boolean bl = this.mTouchThemeAttrs != null || (colorStateList = this.mColor) != null && colorStateList.canApplyTheme() || super.canApplyTheme();
            return bl;
        }

        @Override
        public int getChangingConfigurations() {
            int n = super.getChangingConfigurations();
            ColorStateList colorStateList = this.mColor;
            int n2 = colorStateList != null ? colorStateList.getChangingConfigurations() : 0;
            return n | n2;
        }

        @Override
        public Drawable newDrawable() {
            return new RippleDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new RippleDrawable(this, resources);
        }

        @Override
        protected void onDensityChanged(int n, int n2) {
            super.onDensityChanged(n, n2);
            this.applyDensityScaling(n, n2);
        }
    }

}

