/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.app.ActivityThread;
import android.app.Application;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.PathParser;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AdaptiveIconDrawable
extends Drawable
implements Drawable.Callback {
    private static final int BACKGROUND_ID = 0;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float EXTRA_INSET_PERCENTAGE = 0.25f;
    private static final int FOREGROUND_ID = 1;
    public static final float MASK_SIZE = 100.0f;
    private static final float SAFEZONE_SCALE = 0.9166667f;
    private static Path sMask;
    private final Canvas mCanvas;
    private boolean mChildRequestedInvalidation;
    private Rect mHotspotBounds;
    LayerState mLayerState;
    private Bitmap mLayersBitmap;
    private Shader mLayersShader;
    private final Path mMask;
    private final Matrix mMaskMatrix;
    private final Path mMaskScaleOnly;
    private boolean mMutated;
    private Paint mPaint = new Paint(7);
    private boolean mSuspendChildInvalidation;
    private final Rect mTmpOutRect = new Rect();
    private final Region mTransparentRegion;

    AdaptiveIconDrawable() {
        this((LayerState)null, null);
    }

    AdaptiveIconDrawable(LayerState object, Resources resources) {
        this.mLayerState = this.createConstantState((LayerState)object, resources);
        object = ActivityThread.currentActivityThread() == null ? Resources.getSystem() : ActivityThread.currentActivityThread().getApplication().getResources();
        sMask = PathParser.createPathFromPathData(((Resources)object).getString(17039741));
        this.mMask = new Path(sMask);
        this.mMaskScaleOnly = new Path(this.mMask);
        this.mMaskMatrix = new Matrix();
        this.mCanvas = new Canvas();
        this.mTransparentRegion = new Region();
    }

    public AdaptiveIconDrawable(Drawable drawable2, Drawable drawable3) {
        this((LayerState)null, null);
        if (drawable2 != null) {
            this.addLayer(0, this.createChildDrawable(drawable2));
        }
        if (drawable3 != null) {
            this.addLayer(1, this.createChildDrawable(drawable3));
        }
    }

    private void addLayer(int n, ChildDrawable childDrawable) {
        this.mLayerState.mChildren[n] = childDrawable;
        this.mLayerState.invalidateCache();
    }

    private ChildDrawable createChildDrawable(Drawable object) {
        ChildDrawable childDrawable = new ChildDrawable(this.mLayerState.mDensity);
        childDrawable.mDrawable = object;
        childDrawable.mDrawable.setCallback(this);
        object = this.mLayerState;
        ((LayerState)object).mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
        return childDrawable;
    }

    public static float getExtraInsetFraction() {
        return 0.25f;
    }

    public static float getExtraInsetPercentage() {
        return 0.25f;
    }

    private int getMaxIntrinsicHeight() {
        int n = -1;
        int n2 = 0;
        do {
            int n3;
            Object object = this.mLayerState;
            if (n2 >= 2) break;
            object = ((LayerState)object).mChildren[n2];
            if (((ChildDrawable)object).mDrawable == null) {
                n3 = n;
            } else {
                int n4 = ((ChildDrawable)object).mDrawable.getIntrinsicHeight();
                n3 = n;
                if (n4 > n) {
                    n3 = n4;
                }
            }
            ++n2;
            n = n3;
        } while (true);
        return n;
    }

    private int getMaxIntrinsicWidth() {
        int n = -1;
        int n2 = 0;
        do {
            int n3;
            Object object = this.mLayerState;
            if (n2 >= 2) break;
            object = ((LayerState)object).mChildren[n2];
            if (((ChildDrawable)object).mDrawable == null) {
                n3 = n;
            } else {
                int n4 = ((ChildDrawable)object).mDrawable.getIntrinsicWidth();
                n3 = n;
                if (n4 > n) {
                    n3 = n4;
                }
            }
            ++n2;
            n = n3;
        } while (true);
        return n;
    }

    private void inflateLayers(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        LayerState layerState = this.mLayerState;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3) continue;
            Object object2 = xmlPullParser.getName();
            if (((String)object2).equals("background")) {
                n = 0;
            } else {
                if (!((String)object2).equals("foreground")) continue;
                n = 1;
            }
            ChildDrawable childDrawable = new ChildDrawable(layerState.mDensity);
            object2 = AdaptiveIconDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.AdaptiveIconDrawableLayer);
            this.updateLayerFromTypedArray(childDrawable, (TypedArray)object2);
            ((TypedArray)object2).recycle();
            if (childDrawable.mDrawable == null && childDrawable.mThemeAttrs == null) {
                while ((n2 = xmlPullParser.next()) == 4) {
                }
                if (n2 == 2) {
                    childDrawable.mDrawable = Drawable.createFromXmlInnerForDensity((Resources)object, xmlPullParser, attributeSet, this.mLayerState.mSrcDensityOverride, theme);
                    childDrawable.mDrawable.setCallback(this);
                    layerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                    ((StringBuilder)object).append(": <foreground> or <background> tag requires a 'drawable'attribute or child tag defining a drawable");
                    throw new XmlPullParserException(((StringBuilder)object).toString());
                }
            }
            this.addLayer(n, childDrawable);
        }
    }

    private void resumeChildInvalidation() {
        this.mSuspendChildInvalidation = false;
        if (this.mChildRequestedInvalidation) {
            this.mChildRequestedInvalidation = false;
            this.invalidateSelf();
        }
    }

    private void suspendChildInvalidation() {
        this.mSuspendChildInvalidation = true;
    }

    private void updateLayerBounds(Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        try {
            this.suspendChildInvalidation();
            this.updateLayerBoundsInternal(rect);
            this.updateMaskBoundsInternal(rect);
            return;
        }
        finally {
            this.resumeChildInvalidation();
        }
    }

    private void updateLayerBoundsInternal(Rect rect) {
        int n = rect.width() / 2;
        int n2 = rect.height() / 2;
        Object object = this.mLayerState;
        for (int i = 0; i < 2; ++i) {
            object = this.mLayerState.mChildren[i];
            if (object == null || (object = ((ChildDrawable)object).mDrawable) == null) continue;
            int n3 = (int)((float)rect.width() / 1.3333334f);
            int n4 = (int)((float)rect.height() / 1.3333334f);
            Rect rect2 = this.mTmpOutRect;
            rect2.set(n - n3, n2 - n4, n + n3, n2 + n4);
            ((Drawable)object).setBounds(rect2);
        }
    }

    private void updateLayerFromTypedArray(ChildDrawable childDrawable, TypedArray object) {
        LayerState layerState = this.mLayerState;
        layerState.mChildrenChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        childDrawable.mThemeAttrs = ((TypedArray)object).extractThemeAttrs();
        if ((object = ((TypedArray)object).getDrawableForDensity(0, layerState.mSrcDensityOverride)) != null) {
            if (childDrawable.mDrawable != null) {
                childDrawable.mDrawable.setCallback(null);
            }
            childDrawable.mDrawable = object;
            childDrawable.mDrawable.setCallback(this);
            layerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
        }
    }

    private void updateMaskBoundsInternal(Rect rect) {
        this.mMaskMatrix.setScale((float)rect.width() / 100.0f, (float)rect.height() / 100.0f);
        sMask.transform(this.mMaskMatrix, this.mMaskScaleOnly);
        this.mMaskMatrix.postTranslate(rect.left, rect.top);
        sMask.transform(this.mMaskMatrix, this.mMask);
        Bitmap bitmap = this.mLayersBitmap;
        if (bitmap == null || bitmap.getWidth() != rect.width() || this.mLayersBitmap.getHeight() != rect.height()) {
            this.mLayersBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        }
        this.mPaint.setShader(null);
        this.mTransparentRegion.setEmpty();
        this.mLayersShader = null;
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return;
        }
        int n = Drawable.resolveDensity(theme.getResources(), 0);
        layerState.setDensity(n);
        ChildDrawable[] arrchildDrawable = layerState.mChildren;
        for (int i = 0; i < 2; ++i) {
            Object object = arrchildDrawable[i];
            ((ChildDrawable)object).setDensity(n);
            if (((ChildDrawable)object).mThemeAttrs != null) {
                TypedArray typedArray = theme.resolveAttributes(((ChildDrawable)object).mThemeAttrs, R.styleable.AdaptiveIconDrawableLayer);
                this.updateLayerFromTypedArray((ChildDrawable)object, typedArray);
                typedArray.recycle();
            }
            if ((object = ((ChildDrawable)object).mDrawable) == null || !((Drawable)object).canApplyTheme()) continue;
            ((Drawable)object).applyTheme(theme);
            layerState.mChildrenChangingConfigurations |= ((Drawable)object).getChangingConfigurations();
        }
    }

    @Override
    public boolean canApplyTheme() {
        LayerState layerState = this.mLayerState;
        boolean bl = layerState != null && layerState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            if (object != null) {
                ((Drawable)object).clearMutated();
            }
            ++n;
        } while (true);
        this.mMutated = false;
    }

    LayerState createConstantState(LayerState layerState, Resources resources) {
        return new LayerState(layerState, this, resources);
    }

    @Override
    public void draw(Canvas canvas) {
        Object object = this.mLayersBitmap;
        if (object == null) {
            return;
        }
        if (this.mLayersShader == null) {
            this.mCanvas.setBitmap((Bitmap)object);
            this.mCanvas.drawColor(-16777216);
            int n = 0;
            do {
                object = this.mLayerState;
                if (n >= 2) break;
                if (((LayerState)object).mChildren[n] != null && (object = this.mLayerState.mChildren[n].mDrawable) != null) {
                    ((Drawable)object).draw(this.mCanvas);
                }
                ++n;
            } while (true);
            this.mLayersShader = new BitmapShader(this.mLayersBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mPaint.setShader(this.mLayersShader);
        }
        if (this.mMaskScaleOnly != null) {
            object = this.getBounds();
            canvas.translate(((Rect)object).left, ((Rect)object).top);
            canvas.drawPath(this.mMaskScaleOnly, this.mPaint);
            canvas.translate(-((Rect)object).left, -((Rect)object).top);
        }
    }

    @Override
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public Drawable getBackground() {
        return this.mLayerState.mChildren[0].mDrawable;
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

    public Drawable getForeground() {
        return this.mLayerState.mChildren[1].mDrawable;
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

    public Path getIconMask() {
        return this.mMask;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int)((float)this.getMaxIntrinsicHeight() * 0.6666667f);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int)((float)this.getMaxIntrinsicWidth() * 0.6666667f);
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    @Override
    public void getOutline(Outline outline) {
        outline.setConvexPath(this.mMask);
    }

    public Region getSafeZone() {
        this.mMaskMatrix.reset();
        this.mMaskMatrix.setScale(0.9166667f, 0.9166667f, this.getBounds().centerX(), this.getBounds().centerY());
        Path path = new Path();
        this.mMask.transform(this.mMaskMatrix, path);
        Region region = new Region(this.getBounds());
        region.setPath(path, region);
        return region;
    }

    @Override
    public Region getTransparentRegion() {
        if (this.mTransparentRegion.isEmpty()) {
            this.mMask.toggleInverseFillType();
            this.mTransparentRegion.set(this.getBounds());
            Region region = this.mTransparentRegion;
            region.setPath(this.mMask, region);
            this.mMask.toggleInverseFillType();
        }
        return this.mTransparentRegion;
    }

    @Override
    public boolean hasFocusStateSpecified() {
        return this.mLayerState.hasFocusStateSpecified();
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return;
        }
        int n = Drawable.resolveDensity(resources, 0);
        layerState.setDensity(n);
        layerState.mSrcDensityOverride = this.mSrcDensityOverride;
        ChildDrawable[] arrchildDrawable = layerState.mChildren;
        for (int i = 0; i < layerState.mChildren.length; ++i) {
            arrchildDrawable[i].setDensity(n);
        }
        this.inflateLayers(resources, xmlPullParser, attributeSet, theme);
    }

    @Override
    public void invalidateDrawable(Drawable drawable2) {
        if (this.mSuspendChildInvalidation) {
            this.mChildRequestedInvalidation = true;
        } else {
            this.invalidateSelf();
        }
    }

    @Override
    public void invalidateSelf() {
        this.mLayersShader = null;
        super.invalidateSelf();
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
        int n = 0;
        do {
            LayerState layerState = this.mLayerState;
            if (n >= 2) break;
            if (arrchildDrawable[n].mDrawable != null && arrchildDrawable[n].mDrawable.isProjected()) {
                return true;
            }
            ++n;
        } while (true);
        return false;
    }

    @Override
    public boolean isStateful() {
        return this.mLayerState.isStateful();
    }

    @Override
    public void jumpToCurrentState() {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            if (object != null) {
                ((Drawable)object).jumpToCurrentState();
            }
            ++n;
        } while (true);
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mLayerState = this.createConstantState(this.mLayerState, null);
            int n = 0;
            do {
                Object object = this.mLayerState;
                if (n >= 2) break;
                object = object.mChildren[n].mDrawable;
                if (object != null) {
                    ((Drawable)object).mutate();
                }
                ++n;
            } while (true);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        this.updateLayerBounds(rect);
    }

    @Override
    protected boolean onLevelChange(int n) {
        boolean bl = false;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n2 = 0;
        do {
            Object object = this.mLayerState;
            if (n2 >= 2) break;
            object = arrchildDrawable[n2].mDrawable;
            boolean bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (((Drawable)object).setLevel(n)) {
                    bl2 = true;
                }
            }
            ++n2;
            bl = bl2;
        } while (true);
        if (bl) {
            this.updateLayerBounds(this.getBounds());
        }
        return bl;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        boolean bl = false;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            boolean bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (((Drawable)object).isStateful()) {
                    bl2 = bl;
                    if (((Drawable)object).setState(arrn)) {
                        bl2 = true;
                    }
                }
            }
            ++n;
            bl = bl2;
        } while (true);
        if (bl) {
            this.updateLayerBounds(this.getBounds());
        }
        return bl;
    }

    @Override
    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        this.scheduleSelf(runnable, l);
    }

    @Override
    public void setAlpha(int n) {
        this.mPaint.setAlpha(n);
    }

    @Override
    public void setAutoMirrored(boolean bl) {
        this.mLayerState.mAutoMirrored = bl;
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            if (object != null) {
                ((Drawable)object).setAutoMirrored(bl);
            }
            ++n;
        } while (true);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            if (object != null) {
                ((Drawable)object).setColorFilter(colorFilter);
            }
            ++n;
        } while (true);
    }

    @Override
    public void setDither(boolean bl) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            if (object != null) {
                ((Drawable)object).setDither(bl);
            }
            ++n;
        } while (true);
    }

    @Override
    public void setHotspot(float f, float f2) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            if (object != null) {
                ((Drawable)object).setHotspot(f, f2);
            }
            ++n;
        } while (true);
    }

    @Override
    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        Object object = this.mLayerState.mChildren;
        int n5 = 0;
        do {
            Object object2 = this.mLayerState;
            if (n5 >= 2) break;
            object2 = object[n5].mDrawable;
            if (object2 != null) {
                ((Drawable)object2).setHotspotBounds(n, n2, n3, n4);
            }
            ++n5;
        } while (true);
        object = this.mHotspotBounds;
        if (object == null) {
            this.mHotspotBounds = new Rect(n, n2, n3, n4);
        } else {
            ((Rect)object).set(n, n2, n3, n4);
        }
    }

    public void setOpacity(int n) {
        this.mLayerState.mOpacityOverride = n;
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        Object object = this.mLayerState;
        for (int i = 0; i < 2; ++i) {
            object = arrchildDrawable[i].mDrawable;
            if (object == null) continue;
            ((Drawable)object).setTintBlendMode(blendMode);
        }
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        Object object = this.mLayerState;
        for (int i = 0; i < 2; ++i) {
            object = arrchildDrawable[i].mDrawable;
            if (object == null) continue;
            ((Drawable)object).setTintList(colorStateList);
        }
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        ChildDrawable[] arrchildDrawable = this.mLayerState.mChildren;
        int n = 0;
        do {
            Object object = this.mLayerState;
            if (n >= 2) break;
            object = arrchildDrawable[n].mDrawable;
            if (object != null) {
                ((Drawable)object).setVisible(bl, bl2);
            }
            ++n;
        } while (true);
        return bl3;
    }

    @Override
    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }

    static class ChildDrawable {
        public int mDensity = 160;
        public Drawable mDrawable;
        public int[] mThemeAttrs;

        ChildDrawable(int n) {
            this.mDensity = n;
        }

        ChildDrawable(ChildDrawable childDrawable, AdaptiveIconDrawable adaptiveIconDrawable, Resources resources) {
            Object object;
            Drawable drawable2 = childDrawable.mDrawable;
            if (drawable2 != null) {
                object = drawable2.getConstantState();
                object = object == null ? drawable2 : (resources != null ? ((Drawable.ConstantState)object).newDrawable(resources) : ((Drawable.ConstantState)object).newDrawable());
                ((Drawable)object).setCallback(adaptiveIconDrawable);
                ((Drawable)object).setBounds(drawable2.getBounds());
                ((Drawable)object).setLevel(drawable2.getLevel());
            } else {
                object = null;
            }
            this.mDrawable = object;
            this.mThemeAttrs = childDrawable.mThemeAttrs;
            this.mDensity = Drawable.resolveDensity(resources, childDrawable.mDensity);
        }

        public boolean canApplyTheme() {
            Drawable drawable2;
            boolean bl = this.mThemeAttrs != null || (drawable2 = this.mDrawable) != null && drawable2.canApplyTheme();
            return bl;
        }

        public final void setDensity(int n) {
            if (this.mDensity != n) {
                this.mDensity = n;
            }
        }
    }

    static class LayerState
    extends Drawable.ConstantState {
        static final int N_CHILDREN = 2;
        private boolean mAutoMirrored;
        int mChangingConfigurations;
        private boolean mCheckedOpacity;
        private boolean mCheckedStateful;
        ChildDrawable[] mChildren;
        int mChildrenChangingConfigurations;
        int mDensity;
        private boolean mIsStateful;
        private int mOpacity;
        int mOpacityOverride;
        int mSrcDensityOverride;
        private int[] mThemeAttrs;

        LayerState(LayerState layerState, AdaptiveIconDrawable adaptiveIconDrawable, Resources resources) {
            int n = 0;
            this.mSrcDensityOverride = 0;
            this.mOpacityOverride = 0;
            this.mAutoMirrored = false;
            if (layerState != null) {
                n = layerState.mDensity;
            }
            this.mDensity = Drawable.resolveDensity(resources, n);
            this.mChildren = new ChildDrawable[2];
            if (layerState != null) {
                ChildDrawable[] arrchildDrawable = layerState.mChildren;
                this.mChangingConfigurations = layerState.mChangingConfigurations;
                this.mChildrenChangingConfigurations = layerState.mChildrenChangingConfigurations;
                for (n = 0; n < 2; ++n) {
                    ChildDrawable childDrawable = arrchildDrawable[n];
                    this.mChildren[n] = new ChildDrawable(childDrawable, adaptiveIconDrawable, resources);
                }
                this.mCheckedOpacity = layerState.mCheckedOpacity;
                this.mOpacity = layerState.mOpacity;
                this.mCheckedStateful = layerState.mCheckedStateful;
                this.mIsStateful = layerState.mIsStateful;
                this.mAutoMirrored = layerState.mAutoMirrored;
                this.mThemeAttrs = layerState.mThemeAttrs;
                this.mOpacityOverride = layerState.mOpacityOverride;
                this.mSrcDensityOverride = layerState.mSrcDensityOverride;
            } else {
                for (n = 0; n < 2; ++n) {
                    this.mChildren[n] = new ChildDrawable(this.mDensity);
                }
            }
        }

        @Override
        public boolean canApplyTheme() {
            if (this.mThemeAttrs == null && !super.canApplyTheme()) {
                ChildDrawable[] arrchildDrawable = this.mChildren;
                for (int i = 0; i < 2; ++i) {
                    if (!arrchildDrawable[i].canApplyTheme()) continue;
                    return true;
                }
                return false;
            }
            return true;
        }

        public final boolean canConstantState() {
            ChildDrawable[] arrchildDrawable = this.mChildren;
            for (int i = 0; i < 2; ++i) {
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
            ChildDrawable[] arrchildDrawable = this.mChildren;
            int n2 = -1;
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= 2) break;
                if (arrchildDrawable[n3].mDrawable != null) {
                    n = n3;
                    break;
                }
                ++n3;
            } while (true);
            n3 = n >= 0 ? arrchildDrawable[n].mDrawable.getOpacity() : -2;
            ++n;
            while (n < 2) {
                Drawable drawable2 = arrchildDrawable[n].mDrawable;
                n2 = n3;
                if (drawable2 != null) {
                    n2 = Drawable.resolveOpacity(n3, drawable2.getOpacity());
                }
                ++n;
                n3 = n2;
            }
            this.mOpacity = n3;
            this.mCheckedOpacity = true;
            return n3;
        }

        public final boolean hasFocusStateSpecified() {
            ChildDrawable[] arrchildDrawable = this.mChildren;
            for (int i = 0; i < 2; ++i) {
                Drawable drawable2 = arrchildDrawable[i].mDrawable;
                if (drawable2 == null || !drawable2.hasFocusStateSpecified()) continue;
                return true;
            }
            return false;
        }

        public void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }

        public final boolean isStateful() {
            boolean bl;
            if (this.mCheckedStateful) {
                return this.mIsStateful;
            }
            ChildDrawable[] arrchildDrawable = this.mChildren;
            boolean bl2 = false;
            int n = 0;
            do {
                bl = bl2;
                if (n >= 2) break;
                Drawable drawable2 = arrchildDrawable[n].mDrawable;
                if (drawable2 != null && drawable2.isStateful()) {
                    bl = true;
                    break;
                }
                ++n;
            } while (true);
            this.mIsStateful = bl;
            this.mCheckedStateful = true;
            return bl;
        }

        @Override
        public Drawable newDrawable() {
            return new AdaptiveIconDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new AdaptiveIconDrawable(this, resources);
        }

        public final void setDensity(int n) {
            if (this.mDensity != n) {
                this.mDensity = n;
            }
        }
    }

}

