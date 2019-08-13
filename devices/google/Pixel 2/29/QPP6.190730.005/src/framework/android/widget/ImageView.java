/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.widget.-$
 *  android.widget.-$$Lambda
 *  android.widget.-$$Lambda$ImageView
 *  android.widget.-$$Lambda$ImageView$GWf2-Z-LHjSbTbrF-I3WzfR0LeM
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.widget.-$;
import android.widget.RemoteViews;
import android.widget._$$Lambda$ImageView$GWf2_Z_LHjSbTbrF_I3WzfR0LeM;
import com.android.internal.R;
import java.io.IOException;
import java.util.List;

@RemoteViews.RemoteView
public class ImageView
extends View {
    private static final String LOG_TAG = "ImageView";
    private static boolean sCompatAdjustViewBounds;
    private static boolean sCompatDone;
    private static boolean sCompatDrawableVisibilityDispatch;
    private static boolean sCompatUseCorrectStreamDensity;
    private static final Matrix.ScaleToFit[] sS2FArray;
    private static final ScaleType[] sScaleTypeArray;
    @UnsupportedAppUsage
    private boolean mAdjustViewBounds = false;
    @UnsupportedAppUsage
    private int mAlpha = 255;
    private int mBaseline = -1;
    private boolean mBaselineAlignBottom = false;
    private ColorFilter mColorFilter = null;
    @UnsupportedAppUsage
    private boolean mCropToPadding;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124051687L)
    private Matrix mDrawMatrix = null;
    @UnsupportedAppUsage
    private Drawable mDrawable = null;
    private BlendMode mDrawableBlendMode = null;
    @UnsupportedAppUsage
    private int mDrawableHeight;
    private ColorStateList mDrawableTintList = null;
    @UnsupportedAppUsage
    private int mDrawableWidth;
    private boolean mHasAlpha = false;
    private boolean mHasColorFilter = false;
    private boolean mHasDrawableBlendMode = false;
    private boolean mHasDrawableTint = false;
    private boolean mHasXfermode = false;
    private boolean mHaveFrame = false;
    private int mLevel = 0;
    private Matrix mMatrix;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mMaxHeight = Integer.MAX_VALUE;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mMaxWidth = Integer.MAX_VALUE;
    private boolean mMergeState = false;
    @UnsupportedAppUsage
    private BitmapDrawable mRecycleableBitmapDrawable = null;
    @UnsupportedAppUsage
    private int mResource = 0;
    private ScaleType mScaleType;
    private int[] mState = null;
    private final RectF mTempDst = new RectF();
    private final RectF mTempSrc = new RectF();
    @UnsupportedAppUsage
    private Uri mUri;
    private final int mViewAlphaScale;
    private Xfermode mXfermode;

    static {
        sScaleTypeArray = new ScaleType[]{ScaleType.MATRIX, ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER, ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE};
        sS2FArray = new Matrix.ScaleToFit[]{Matrix.ScaleToFit.FILL, Matrix.ScaleToFit.START, Matrix.ScaleToFit.CENTER, Matrix.ScaleToFit.END};
    }

    public ImageView(Context context) {
        super(context);
        this.mViewAlphaScale = 256;
        this.initImageView();
    }

    public ImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ImageView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        this.mViewAlphaScale = 256;
        this.initImageView();
        if (this.getImportantForAutofill() == 0) {
            this.setImportantForAutofill(2);
        }
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ImageView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.ImageView, attributeSet, typedArray, n, n2);
        object = typedArray.getDrawable(0);
        if (object != null) {
            this.setImageDrawable((Drawable)object);
        }
        this.mBaselineAlignBottom = typedArray.getBoolean(6, false);
        this.mBaseline = typedArray.getDimensionPixelSize(8, -1);
        this.setAdjustViewBounds(typedArray.getBoolean(2, false));
        this.setMaxWidth(typedArray.getDimensionPixelSize(3, Integer.MAX_VALUE));
        this.setMaxHeight(typedArray.getDimensionPixelSize(4, Integer.MAX_VALUE));
        n = typedArray.getInt(1, -1);
        if (n >= 0) {
            this.setScaleType(sScaleTypeArray[n]);
        }
        if (typedArray.hasValue(5)) {
            this.mDrawableTintList = typedArray.getColorStateList(5);
            this.mHasDrawableTint = true;
            this.mDrawableBlendMode = BlendMode.SRC_ATOP;
            this.mHasDrawableBlendMode = true;
        }
        if (typedArray.hasValue(9)) {
            this.mDrawableBlendMode = Drawable.parseBlendMode(typedArray.getInt(9, -1), this.mDrawableBlendMode);
            this.mHasDrawableBlendMode = true;
        }
        this.applyImageTint();
        n = typedArray.getInt(10, 255);
        if (n != 255) {
            this.setImageAlpha(n);
        }
        this.mCropToPadding = typedArray.getBoolean(7, false);
        typedArray.recycle();
    }

    private void applyAlpha() {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && this.mHasAlpha) {
            this.mDrawable = drawable2.mutate();
            this.mDrawable.setAlpha(this.mAlpha * 256 >> 8);
        }
    }

    private void applyColorFilter() {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && this.mHasColorFilter) {
            this.mDrawable = drawable2.mutate();
            this.mDrawable.setColorFilter(this.mColorFilter);
        }
    }

    private void applyImageTint() {
        if (this.mDrawable != null && (this.mHasDrawableTint || this.mHasDrawableBlendMode)) {
            this.mDrawable = this.mDrawable.mutate();
            if (this.mHasDrawableTint) {
                this.mDrawable.setTintList(this.mDrawableTintList);
            }
            if (this.mHasDrawableBlendMode) {
                this.mDrawable.setTintBlendMode(this.mDrawableBlendMode);
            }
            if (this.mDrawable.isStateful()) {
                this.mDrawable.setState(this.getDrawableState());
            }
        }
    }

    private void applyXfermode() {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && this.mHasXfermode) {
            this.mDrawable = drawable2.mutate();
            this.mDrawable.setXfermode(this.mXfermode);
        }
    }

    private void configureBounds() {
        if (this.mDrawable != null && this.mHaveFrame) {
            int n = this.mDrawableWidth;
            int n2 = this.mDrawableHeight;
            int n3 = this.getWidth() - this.mPaddingLeft - this.mPaddingRight;
            int n4 = this.getHeight() - this.mPaddingTop - this.mPaddingBottom;
            boolean bl = !(n >= 0 && n3 != n || n2 >= 0 && n4 != n2);
            if (n > 0 && n2 > 0 && ScaleType.FIT_XY != this.mScaleType) {
                this.mDrawable.setBounds(0, 0, n, n2);
                if (ScaleType.MATRIX == this.mScaleType) {
                    this.mDrawMatrix = this.mMatrix.isIdentity() ? null : this.mMatrix;
                } else if (bl) {
                    this.mDrawMatrix = null;
                } else if (ScaleType.CENTER == this.mScaleType) {
                    this.mDrawMatrix = this.mMatrix;
                    this.mDrawMatrix.setTranslate(Math.round((float)(n3 - n) * 0.5f), Math.round((float)(n4 - n2) * 0.5f));
                } else if (ScaleType.CENTER_CROP == this.mScaleType) {
                    float f;
                    this.mDrawMatrix = this.mMatrix;
                    float f2 = 0.0f;
                    float f3 = 0.0f;
                    if (n * n4 > n3 * n2) {
                        f = (float)n4 / (float)n2;
                        f2 = ((float)n3 - (float)n * f) * 0.5f;
                    } else {
                        f = (float)n3 / (float)n;
                        f3 = ((float)n4 - (float)n2 * f) * 0.5f;
                    }
                    this.mDrawMatrix.setScale(f, f);
                    this.mDrawMatrix.postTranslate(Math.round(f2), Math.round(f3));
                } else if (ScaleType.CENTER_INSIDE == this.mScaleType) {
                    this.mDrawMatrix = this.mMatrix;
                    float f = n <= n3 && n2 <= n4 ? 1.0f : Math.min((float)n3 / (float)n, (float)n4 / (float)n2);
                    float f4 = Math.round(((float)n3 - (float)n * f) * 0.5f);
                    float f5 = Math.round(((float)n4 - (float)n2 * f) * 0.5f);
                    this.mDrawMatrix.setScale(f, f);
                    this.mDrawMatrix.postTranslate(f4, f5);
                } else {
                    this.mTempSrc.set(0.0f, 0.0f, n, n2);
                    this.mTempDst.set(0.0f, 0.0f, n3, n4);
                    this.mDrawMatrix = this.mMatrix;
                    this.mDrawMatrix.setRectToRect(this.mTempSrc, this.mTempDst, ImageView.scaleTypeToScaleToFit(this.mScaleType));
                }
            } else {
                this.mDrawable.setBounds(0, 0, n3, n4);
                this.mDrawMatrix = null;
            }
            return;
        }
    }

    private Drawable getDrawableFromUri(Uri uri) {
        Object object = uri.getScheme();
        if ("android.resource".equals(object)) {
            try {
                object = this.mContext.getContentResolver().getResourceId(uri);
                object = ((ContentResolver.OpenResourceIdResult)object).r.getDrawable(((ContentResolver.OpenResourceIdResult)object).id, this.mContext.getTheme());
                return object;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to open content: ");
                ((StringBuilder)object).append(uri);
                Log.w(LOG_TAG, ((StringBuilder)object).toString(), exception);
            }
        } else {
            if (!"content".equals(object) && !"file".equals(object)) {
                return Drawable.createFromPath(uri.toString());
            }
            object = sCompatUseCorrectStreamDensity ? this.getResources() : null;
            try {
                object = ImageDecoder.decodeDrawable(ImageDecoder.createSource(this.mContext.getContentResolver(), uri, (Resources)object), (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$ImageView$GWf2_Z_LHjSbTbrF_I3WzfR0LeM.INSTANCE);
                return object;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to open content: ");
                stringBuilder.append(uri);
                Log.w(LOG_TAG, stringBuilder.toString(), iOException);
            }
        }
        return null;
    }

    private void initImageView() {
        this.mMatrix = new Matrix();
        this.mScaleType = ScaleType.FIT_CENTER;
        if (!sCompatDone) {
            int n = this.mContext.getApplicationInfo().targetSdkVersion;
            boolean bl = false;
            boolean bl2 = n <= 17;
            sCompatAdjustViewBounds = bl2;
            bl2 = n > 23;
            sCompatUseCorrectStreamDensity = bl2;
            bl2 = bl;
            if (n < 24) {
                bl2 = true;
            }
            sCompatDrawableVisibilityDispatch = bl2;
            sCompatDone = true;
        }
    }

    private boolean isFilledByImage() {
        Object object = this.mDrawable;
        boolean bl = false;
        boolean bl2 = false;
        if (object == null) {
            return false;
        }
        Rect rect = ((Drawable)object).getBounds();
        object = this.mDrawMatrix;
        if (object == null) {
            if (rect.left <= 0 && rect.top <= 0 && rect.right >= this.getWidth() && rect.bottom >= this.getHeight()) {
                bl2 = true;
            }
            return bl2;
        }
        if (((Matrix)object).rectStaysRect()) {
            RectF rectF = this.mTempSrc;
            RectF rectF2 = this.mTempDst;
            rectF.set(rect);
            ((Matrix)object).mapRect(rectF2, rectF);
            bl2 = rectF2.left <= 0.0f && rectF2.top <= 0.0f && rectF2.right >= (float)this.getWidth() && rectF2.bottom >= (float)this.getHeight() ? true : bl;
            return bl2;
        }
        return false;
    }

    static /* synthetic */ void lambda$getDrawableFromUri$0(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
    }

    @UnsupportedAppUsage
    private void resizeFromDrawable() {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            int n;
            int n2;
            int n3 = n = drawable2.getIntrinsicWidth();
            if (n < 0) {
                n3 = this.mDrawableWidth;
            }
            n = n2 = drawable2.getIntrinsicHeight();
            if (n2 < 0) {
                n = this.mDrawableHeight;
            }
            if (n3 != this.mDrawableWidth || n != this.mDrawableHeight) {
                this.mDrawableWidth = n3;
                this.mDrawableHeight = n;
                this.requestLayout();
            }
        }
    }

    private int resolveAdjustedSize(int n, int n2, int n3) {
        int n4 = n;
        int n5 = View.MeasureSpec.getMode(n3);
        n3 = View.MeasureSpec.getSize(n3);
        n = n5 != Integer.MIN_VALUE ? (n5 != 0 ? (n5 != 1073741824 ? n4 : n3) : Math.min(n, n2)) : Math.min(Math.min(n, n3), n2);
        return n;
    }

    @UnsupportedAppUsage
    private void resolveUri() {
        block10 : {
            Object object;
            block9 : {
                block8 : {
                    if (this.mDrawable != null) {
                        return;
                    }
                    if (this.getResources() == null) {
                        return;
                    }
                    object = null;
                    if (this.mResource == 0) break block8;
                    try {
                        Drawable drawable2 = this.mContext.getDrawable(this.mResource);
                        object = drawable2;
                    }
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unable to find resource: ");
                        stringBuilder.append(this.mResource);
                        Log.w(LOG_TAG, stringBuilder.toString(), exception);
                        this.mResource = 0;
                    }
                    break block9;
                }
                object = this.mUri;
                if (object == null) break block10;
                Drawable drawable3 = this.getDrawableFromUri((Uri)object);
                object = drawable3;
                if (drawable3 == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("resolveUri failed on bad bitmap uri: ");
                    ((StringBuilder)object).append(this.mUri);
                    Log.w(LOG_TAG, ((StringBuilder)object).toString());
                    this.mUri = null;
                    object = drawable3;
                }
            }
            this.updateDrawable((Drawable)object);
            return;
        }
    }

    @UnsupportedAppUsage
    private static Matrix.ScaleToFit scaleTypeToScaleToFit(ScaleType scaleType) {
        return sS2FArray[scaleType.nativeInt - 1];
    }

    @UnsupportedAppUsage
    private void updateDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mRecycleableBitmapDrawable;
        if (drawable2 != drawable3 && drawable3 != null) {
            drawable3.setBitmap(null);
        }
        boolean bl = false;
        drawable3 = this.mDrawable;
        boolean bl2 = false;
        if (drawable3 != null) {
            bl = drawable3 == drawable2;
            boolean bl3 = bl;
            this.mDrawable.setCallback(null);
            this.unscheduleDrawable(this.mDrawable);
            bl = bl3;
            if (!sCompatDrawableVisibilityDispatch) {
                bl = bl3;
                if (!bl3) {
                    bl = bl3;
                    if (this.isAttachedToWindow()) {
                        this.mDrawable.setVisible(false, false);
                        bl = bl3;
                    }
                }
            }
        }
        this.mDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            drawable2.setLayoutDirection(this.getLayoutDirection());
            if (drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
            if (!bl || sCompatDrawableVisibilityDispatch) {
                boolean bl4;
                if (sCompatDrawableVisibilityDispatch) {
                    bl4 = bl2;
                    if (this.getVisibility() == 0) {
                        bl4 = true;
                    }
                } else {
                    bl4 = bl2;
                    if (this.isAttachedToWindow()) {
                        bl4 = bl2;
                        if (this.getWindowVisibility() == 0) {
                            bl4 = bl2;
                            if (this.isShown()) {
                                bl4 = true;
                            }
                        }
                    }
                }
                drawable2.setVisible(bl4, true);
            }
            drawable2.setLevel(this.mLevel);
            this.mDrawableWidth = drawable2.getIntrinsicWidth();
            this.mDrawableHeight = drawable2.getIntrinsicHeight();
            this.applyImageTint();
            this.applyColorFilter();
            this.applyAlpha();
            this.applyXfermode();
            this.configureBounds();
        } else {
            this.mDrawableHeight = -1;
            this.mDrawableWidth = -1;
        }
    }

    public void animateTransform(Matrix matrix) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 == null) {
            return;
        }
        if (matrix == null) {
            int n = this.getWidth();
            int n2 = this.mPaddingLeft;
            int n3 = this.mPaddingRight;
            int n4 = this.getHeight();
            int n5 = this.mPaddingTop;
            int n6 = this.mPaddingBottom;
            this.mDrawable.setBounds(0, 0, n - n2 - n3, n4 - n5 - n6);
            this.mDrawMatrix = null;
        } else {
            drawable2.setBounds(0, 0, this.mDrawableWidth, this.mDrawableHeight);
            if (this.mDrawMatrix == null) {
                this.mDrawMatrix = new Matrix();
            }
            this.mDrawMatrix.set(matrix);
        }
        this.invalidate();
    }

    public final void clearColorFilter() {
        this.setColorFilter(null);
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:baseline", this.getBaseline());
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ImageView.class.getName();
    }

    public boolean getAdjustViewBounds() {
        return this.mAdjustViewBounds;
    }

    @ViewDebug.ExportedProperty(category="layout")
    @Override
    public int getBaseline() {
        if (this.mBaselineAlignBottom) {
            return this.getMeasuredHeight();
        }
        return this.mBaseline;
    }

    public boolean getBaselineAlignBottom() {
        return this.mBaselineAlignBottom;
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public boolean getCropToPadding() {
        return this.mCropToPadding;
    }

    public Drawable getDrawable() {
        if (this.mDrawable == this.mRecycleableBitmapDrawable) {
            this.mRecycleableBitmapDrawable = null;
        }
        return this.mDrawable;
    }

    public int getImageAlpha() {
        return this.mAlpha;
    }

    public Matrix getImageMatrix() {
        Matrix matrix = this.mDrawMatrix;
        if (matrix == null) {
            return new Matrix(Matrix.IDENTITY_MATRIX);
        }
        return matrix;
    }

    public BlendMode getImageTintBlendMode() {
        return this.mDrawableBlendMode;
    }

    public ColorStateList getImageTintList() {
        return this.mDrawableTintList;
    }

    public PorterDuff.Mode getImageTintMode() {
        Enum enum_ = this.mDrawableBlendMode;
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    @Override
    public boolean hasOverlappingRendering() {
        boolean bl = this.getBackground() != null && this.getBackground().getCurrent() != null;
        return bl;
    }

    @Override
    public void invalidateDrawable(Drawable drawable2) {
        if (drawable2 == this.mDrawable) {
            if (drawable2 != null) {
                int n = drawable2.getIntrinsicWidth();
                int n2 = drawable2.getIntrinsicHeight();
                if (n != this.mDrawableWidth || n2 != this.mDrawableHeight) {
                    this.mDrawableWidth = n;
                    this.mDrawableHeight = n2;
                    this.configureBounds();
                }
            }
            this.invalidate();
        } else {
            super.invalidateDrawable(drawable2);
        }
    }

    @Override
    public boolean isDefaultFocusHighlightNeeded(Drawable drawable2, Drawable drawable3) {
        Drawable drawable4 = this.mDrawable;
        boolean bl = false;
        boolean bl2 = drawable4 == null || !drawable4.isStateful() || !this.mDrawable.hasFocusStateSpecified();
        boolean bl3 = bl;
        if (super.isDefaultFocusHighlightNeeded(drawable2, drawable3)) {
            bl3 = bl;
            if (bl2) {
                bl3 = true;
            }
        }
        return bl3;
    }

    @Override
    public boolean isOpaque() {
        Drawable drawable2;
        boolean bl = super.isOpaque() || (drawable2 = this.mDrawable) != null && this.mXfermode == null && drawable2.getOpacity() == -1 && this.mAlpha * 256 >> 8 == 255 && this.isFilledByImage();
        return bl;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && sCompatDrawableVisibilityDispatch) {
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
        }
    }

    @Override
    public int[] onCreateDrawableState(int n) {
        int[] arrn = this.mState;
        if (arrn == null) {
            return super.onCreateDrawableState(n);
        }
        if (!this.mMergeState) {
            return arrn;
        }
        return ImageView.mergeDrawableStates(super.onCreateDrawableState(arrn.length + n), this.mState);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && sCompatDrawableVisibilityDispatch) {
            drawable2.setVisible(false, false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawable == null) {
            return;
        }
        if (this.mDrawableWidth != 0 && this.mDrawableHeight != 0) {
            if (this.mDrawMatrix == null && this.mPaddingTop == 0 && this.mPaddingLeft == 0) {
                this.mDrawable.draw(canvas);
            } else {
                int n = canvas.getSaveCount();
                canvas.save();
                if (this.mCropToPadding) {
                    int n2 = this.mScrollX;
                    int n3 = this.mScrollY;
                    canvas.clipRect(this.mPaddingLeft + n2, this.mPaddingTop + n3, this.mRight + n2 - this.mLeft - this.mPaddingRight, this.mBottom + n3 - this.mTop - this.mPaddingBottom);
                }
                canvas.translate(this.mPaddingLeft, this.mPaddingTop);
                Matrix matrix = this.mDrawMatrix;
                if (matrix != null) {
                    canvas.concat(matrix);
                }
                this.mDrawable.draw(canvas);
                canvas.restoreToCount(n);
            }
            return;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        this.resolveUri();
        float f = 0.0f;
        int n7 = 0;
        int n8 = 0;
        int n9 = View.MeasureSpec.getMode(n);
        int n10 = View.MeasureSpec.getMode(n2);
        if (this.mDrawable == null) {
            this.mDrawableWidth = -1;
            this.mDrawableHeight = -1;
            n4 = 0;
            n3 = 0;
        } else {
            n6 = this.mDrawableWidth;
            n4 = this.mDrawableHeight;
            n5 = n6;
            if (n6 <= 0) {
                n5 = 1;
            }
            n6 = n4;
            if (n4 <= 0) {
                n6 = 1;
            }
            n3 = n5;
            n4 = n6;
            if (this.mAdjustViewBounds) {
                n3 = 1;
                n4 = n9 != 1073741824 ? 1 : 0;
                n7 = n4;
                n4 = n10 != 1073741824 ? n3 : 0;
                n8 = n4;
                f = (float)n5 / (float)n6;
                n4 = n6;
                n3 = n5;
            }
        }
        int n11 = this.mPaddingLeft;
        int n12 = this.mPaddingRight;
        n9 = this.mPaddingTop;
        int n13 = this.mPaddingBottom;
        if (n7 == 0 && n8 == 0) {
            n5 = Math.max(n3 + (n11 + n12), this.getSuggestedMinimumWidth());
            n6 = Math.max(n4 + (n9 + n13), this.getSuggestedMinimumHeight());
            n5 = ImageView.resolveSizeAndState(n5, n, 0);
            n6 = ImageView.resolveSizeAndState(n6, n2, 0);
        } else {
            n5 = this.resolveAdjustedSize(n3 + n11 + n12, this.mMaxWidth, n);
            n4 = this.resolveAdjustedSize(n4 + n9 + n13, this.mMaxHeight, n2);
            if (f != 0.0f) {
                n6 = n4;
                n3 = n5;
                if ((double)Math.abs((float)(n5 - n11 - n12) / (float)(n4 - n9 - n13) - f) > 1.0E-7) {
                    if (n7 != 0) {
                        n6 = (int)((float)(n4 - n9 - n13) * f) + n11 + n12;
                        if (n8 == 0 && !sCompatAdjustViewBounds) {
                            n5 = this.resolveAdjustedSize(n6, this.mMaxWidth, n);
                        }
                        n10 = 0;
                        n = n5;
                        if (n6 <= n5) {
                            n = n6;
                            n10 = 1;
                        }
                    } else {
                        n10 = 0;
                        n = n5;
                    }
                    n6 = n4;
                    n3 = n;
                    if (n10 == 0) {
                        n6 = n4;
                        n3 = n;
                        if (n8 != 0) {
                            n10 = (int)((float)(n - n11 - n12) / f) + n9 + n13;
                            n5 = n4;
                            if (n7 == 0) {
                                n5 = n4;
                                if (!sCompatAdjustViewBounds) {
                                    n5 = this.resolveAdjustedSize(n10, this.mMaxHeight, n2);
                                }
                            }
                            n6 = n5;
                            n3 = n;
                            if (n10 <= n5) {
                                n6 = n10;
                                n3 = n;
                            }
                        }
                    }
                }
                n5 = n3;
            } else {
                n6 = n4;
            }
        }
        this.setMeasuredDimension(n5, n6);
    }

    @Override
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        CharSequence charSequence = this.getContentDescription();
        if (!TextUtils.isEmpty(charSequence)) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(n);
        }
    }

    @Override
    public void onVisibilityAggregated(boolean bl) {
        super.onVisibilityAggregated(bl);
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && !sCompatDrawableVisibilityDispatch) {
            drawable2.setVisible(bl, false);
        }
    }

    @RemotableViewMethod
    public void setAdjustViewBounds(boolean bl) {
        this.mAdjustViewBounds = bl;
        if (bl) {
            this.setScaleType(ScaleType.FIT_CENTER);
        }
    }

    @RemotableViewMethod
    @Deprecated
    public void setAlpha(int n) {
        if (this.mAlpha != (n &= 255)) {
            this.mAlpha = n;
            this.mHasAlpha = true;
            this.applyAlpha();
            this.invalidate();
        }
    }

    public void setBaseline(int n) {
        if (this.mBaseline != n) {
            this.mBaseline = n;
            this.requestLayout();
        }
    }

    public void setBaselineAlignBottom(boolean bl) {
        if (this.mBaselineAlignBottom != bl) {
            this.mBaselineAlignBottom = bl;
            this.requestLayout();
        }
    }

    @RemotableViewMethod
    public final void setColorFilter(int n) {
        this.setColorFilter(n, PorterDuff.Mode.SRC_ATOP);
    }

    public final void setColorFilter(int n, PorterDuff.Mode mode) {
        this.setColorFilter(new PorterDuffColorFilter(n, mode));
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mColorFilter = colorFilter;
            this.mHasColorFilter = true;
            this.applyColorFilter();
            this.invalidate();
        }
    }

    public void setCropToPadding(boolean bl) {
        if (this.mCropToPadding != bl) {
            this.mCropToPadding = bl;
            this.requestLayout();
            this.invalidate();
        }
    }

    @Override
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        this.mHaveFrame = true;
        this.configureBounds();
        return bl;
    }

    @RemotableViewMethod
    public void setImageAlpha(int n) {
        this.setAlpha(n);
    }

    @RemotableViewMethod
    public void setImageBitmap(Bitmap bitmap) {
        this.mDrawable = null;
        BitmapDrawable bitmapDrawable = this.mRecycleableBitmapDrawable;
        if (bitmapDrawable == null) {
            this.mRecycleableBitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
        } else {
            bitmapDrawable.setBitmap(bitmap);
        }
        this.setImageDrawable(this.mRecycleableBitmapDrawable);
    }

    public void setImageDrawable(Drawable drawable2) {
        if (this.mDrawable != drawable2) {
            this.mResource = 0;
            this.mUri = null;
            int n = this.mDrawableWidth;
            int n2 = this.mDrawableHeight;
            this.updateDrawable(drawable2);
            if (n != this.mDrawableWidth || n2 != this.mDrawableHeight) {
                this.requestLayout();
            }
            this.invalidate();
        }
    }

    @RemotableViewMethod(asyncImpl="setImageIconAsync")
    public void setImageIcon(Icon object) {
        object = object == null ? null : ((Icon)object).loadDrawable(this.mContext);
        this.setImageDrawable((Drawable)object);
    }

    public Runnable setImageIconAsync(Icon object) {
        object = object == null ? null : ((Icon)object).loadDrawable(this.mContext);
        return new ImageDrawableCallback((Drawable)object, null, 0);
    }

    @RemotableViewMethod
    public void setImageLevel(int n) {
        this.mLevel = n;
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setLevel(n);
            this.resizeFromDrawable();
        }
    }

    public void setImageMatrix(Matrix matrix) {
        Matrix matrix2 = matrix;
        if (matrix != null) {
            matrix2 = matrix;
            if (matrix.isIdentity()) {
                matrix2 = null;
            }
        }
        if (matrix2 == null && !this.mMatrix.isIdentity() || matrix2 != null && !this.mMatrix.equals(matrix2)) {
            this.mMatrix.set(matrix2);
            this.configureBounds();
            this.invalidate();
        }
    }

    @RemotableViewMethod(asyncImpl="setImageResourceAsync")
    public void setImageResource(int n) {
        int n2 = this.mDrawableWidth;
        int n3 = this.mDrawableHeight;
        this.updateDrawable(null);
        this.mResource = n;
        this.mUri = null;
        this.resolveUri();
        if (n2 != this.mDrawableWidth || n3 != this.mDrawableHeight) {
            this.requestLayout();
        }
        this.invalidate();
    }

    @UnsupportedAppUsage
    public Runnable setImageResourceAsync(int n) {
        Drawable drawable2;
        Drawable drawable3 = drawable2 = null;
        int n2 = n;
        if (n != 0) {
            try {
                drawable3 = this.getContext().getDrawable(n);
                n2 = n;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to find resource: ");
                stringBuilder.append(n);
                Log.w(LOG_TAG, stringBuilder.toString(), exception);
                n2 = 0;
                drawable3 = drawable2;
            }
        }
        return new ImageDrawableCallback(drawable3, null, n2);
    }

    public void setImageState(int[] arrn, boolean bl) {
        this.mState = arrn;
        this.mMergeState = bl;
        if (this.mDrawable != null) {
            this.refreshDrawableState();
            this.resizeFromDrawable();
        }
    }

    public void setImageTintBlendMode(BlendMode blendMode) {
        this.mDrawableBlendMode = blendMode;
        this.mHasDrawableBlendMode = true;
        this.applyImageTint();
    }

    public void setImageTintList(ColorStateList colorStateList) {
        this.mDrawableTintList = colorStateList;
        this.mHasDrawableTint = true;
        this.applyImageTint();
    }

    public void setImageTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setImageTintBlendMode((BlendMode)enum_);
    }

    @RemotableViewMethod(asyncImpl="setImageURIAsync")
    public void setImageURI(Uri uri) {
        Uri uri2;
        if (this.mResource != 0 || (uri2 = this.mUri) != uri && (uri == null || uri2 == null || !uri.equals(uri2))) {
            this.updateDrawable(null);
            this.mResource = 0;
            this.mUri = uri;
            int n = this.mDrawableWidth;
            int n2 = this.mDrawableHeight;
            this.resolveUri();
            if (n != this.mDrawableWidth || n2 != this.mDrawableHeight) {
                this.requestLayout();
            }
            this.invalidate();
        }
    }

    @UnsupportedAppUsage
    public Runnable setImageURIAsync(Uri uri) {
        Uri uri2;
        int n = this.mResource;
        Drawable drawable2 = null;
        if (n == 0 && ((uri2 = this.mUri) == uri || uri != null && uri2 != null && uri.equals(uri2))) {
            return null;
        }
        if (uri != null) {
            drawable2 = this.getDrawableFromUri(uri);
        }
        if (drawable2 == null) {
            uri = null;
        }
        return new ImageDrawableCallback(drawable2, uri, 0);
    }

    @RemotableViewMethod
    public void setMaxHeight(int n) {
        this.mMaxHeight = n;
    }

    @RemotableViewMethod
    public void setMaxWidth(int n) {
        this.mMaxWidth = n;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != null) {
            if (this.mScaleType != scaleType) {
                this.mScaleType = scaleType;
                this.requestLayout();
                this.invalidate();
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public void setSelected(boolean bl) {
        super.setSelected(bl);
        this.resizeFromDrawable();
    }

    @RemotableViewMethod
    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null && sCompatDrawableVisibilityDispatch) {
            boolean bl = n == 0;
            drawable2.setVisible(bl, false);
        }
    }

    public final void setXfermode(Xfermode xfermode) {
        if (this.mXfermode != xfermode) {
            this.mXfermode = xfermode;
            this.mHasXfermode = true;
            this.applyXfermode();
            this.invalidate();
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = this.mDrawable == drawable2 || super.verifyDrawable(drawable2);
        return bl;
    }

    private class ImageDrawableCallback
    implements Runnable {
        private final Drawable drawable;
        private final int resource;
        private final Uri uri;

        ImageDrawableCallback(Drawable drawable2, Uri uri, int n) {
            this.drawable = drawable2;
            this.uri = uri;
            this.resource = n;
        }

        @Override
        public void run() {
            ImageView.this.setImageDrawable(this.drawable);
            ImageView.this.mUri = this.uri;
            ImageView.this.mResource = this.resource;
        }
    }

    public static enum ScaleType {
        MATRIX(0),
        FIT_XY(1),
        FIT_START(2),
        FIT_CENTER(3),
        FIT_END(4),
        CENTER(5),
        CENTER_CROP(6),
        CENTER_INSIDE(7);
        
        final int nativeInt;

        private ScaleType(int n2) {
            this.nativeInt = n2;
        }
    }

}

