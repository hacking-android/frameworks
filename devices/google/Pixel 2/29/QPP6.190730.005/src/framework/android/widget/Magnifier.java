/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BaseRecordingCanvas;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceSession;
import android.view.SurfaceView;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget._$$Lambda$Magnifier$InternalPopupWindow$qfjMrDJVvOQUv9_kKVdpLzbaJ_A;
import android.widget._$$Lambda$Magnifier$InternalPopupWindow$t9Cn2sIi2LBUhAVikvRPKKoAwIU;
import android.widget._$$Lambda$Magnifier$K0um0QSTAb4wXwua60CgJIIwGaI;
import android.widget._$$Lambda$Magnifier$esRj9C7NyDvOX8eqqqLKuB6jpTw;
import android.widget._$$Lambda$Magnifier$sEUKNU2_gseoDMBt_HOs_JGAfZ8;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Magnifier {
    private static final int NONEXISTENT_PREVIOUS_CONFIG_VALUE = -1;
    public static final int SOURCE_BOUND_MAX_IN_SURFACE = 0;
    public static final int SOURCE_BOUND_MAX_VISIBLE = 1;
    private static final String TAG = "Magnifier";
    private static final HandlerThread sPixelCopyHandlerThread = new HandlerThread("magnifier pixel copy result handler");
    private int mBottomContentBound;
    private Callback mCallback;
    private final Point mClampedCenterZoomCoords = new Point();
    private final boolean mClippingEnabled;
    private SurfaceInfo mContentCopySurface;
    private final int mDefaultHorizontalSourceToMagnifierOffset;
    private final int mDefaultVerticalSourceToMagnifierOffset;
    private final Object mDestroyLock = new Object();
    private boolean mDirtyState;
    private int mLeftContentBound;
    private final Object mLock = new Object();
    private final Drawable mOverlay;
    private SurfaceInfo mParentSurface;
    private final Rect mPixelCopyRequestRect = new Rect();
    private final PointF mPrevShowSourceCoords = new PointF(-1.0f, -1.0f);
    private final PointF mPrevShowWindowCoords = new PointF(-1.0f, -1.0f);
    private final Point mPrevStartCoordsInSurface = new Point(-1, -1);
    private int mRightContentBound;
    private int mSourceHeight;
    private int mSourceWidth;
    private int mTopContentBound;
    private final View mView;
    private final int[] mViewCoordinatesInSurface;
    private InternalPopupWindow mWindow;
    private final Point mWindowCoords = new Point();
    private final float mWindowCornerRadius;
    private final float mWindowElevation;
    private final int mWindowHeight;
    private final int mWindowWidth;
    private float mZoom;

    static {
        sPixelCopyHandlerThread.start();
    }

    @Deprecated
    public Magnifier(View view) {
        this(Magnifier.createBuilderWithOldMagnifierDefaults(view));
    }

    private Magnifier(Builder builder) {
        this.mView = builder.mView;
        this.mWindowWidth = builder.mWidth;
        this.mWindowHeight = builder.mHeight;
        this.mZoom = builder.mZoom;
        this.mSourceWidth = Math.round((float)this.mWindowWidth / this.mZoom);
        this.mSourceHeight = Math.round((float)this.mWindowHeight / this.mZoom);
        this.mWindowElevation = builder.mElevation;
        this.mWindowCornerRadius = builder.mCornerRadius;
        this.mOverlay = builder.mOverlay;
        this.mDefaultHorizontalSourceToMagnifierOffset = builder.mHorizontalDefaultSourceToMagnifierOffset;
        this.mDefaultVerticalSourceToMagnifierOffset = builder.mVerticalDefaultSourceToMagnifierOffset;
        this.mClippingEnabled = builder.mClippingEnabled;
        this.mLeftContentBound = builder.mLeftContentBound;
        this.mTopContentBound = builder.mTopContentBound;
        this.mRightContentBound = builder.mRightContentBound;
        this.mBottomContentBound = builder.mBottomContentBound;
        this.mViewCoordinatesInSurface = new int[2];
    }

    static Builder createBuilderWithOldMagnifierDefaults(View object) {
        Builder builder = new Builder((View)object);
        Context context = ((View)object).getContext();
        object = context.obtainStyledAttributes(null, R.styleable.Magnifier, 17956980, 0);
        builder.mWidth = ((TypedArray)object).getDimensionPixelSize(5, 0);
        builder.mHeight = ((TypedArray)object).getDimensionPixelSize(2, 0);
        builder.mElevation = ((TypedArray)object).getDimension(1, 0.0f);
        builder.mCornerRadius = Magnifier.getDeviceDefaultDialogCornerRadius(context);
        builder.mZoom = ((TypedArray)object).getFloat(6, 0.0f);
        builder.mHorizontalDefaultSourceToMagnifierOffset = ((TypedArray)object).getDimensionPixelSize(3, 0);
        builder.mVerticalDefaultSourceToMagnifierOffset = ((TypedArray)object).getDimensionPixelSize(4, 0);
        builder.mOverlay = new ColorDrawable(((TypedArray)object).getColor(0, 0));
        ((TypedArray)object).recycle();
        builder.mClippingEnabled = true;
        builder.mLeftContentBound = 1;
        builder.mTopContentBound = 0;
        builder.mRightContentBound = 1;
        builder.mBottomContentBound = 0;
        return builder;
    }

    private Point getCurrentClampedWindowCoordinates() {
        Parcelable parcelable;
        if (!this.mClippingEnabled) {
            return new Point(this.mWindowCoords);
        }
        if (this.mParentSurface.mIsMainWindowSurface) {
            parcelable = this.mView.getRootWindowInsets().getSystemWindowInsets();
            parcelable = new Rect(parcelable.left + SurfaceInfo.access$1500((SurfaceInfo)this.mParentSurface).left, parcelable.top + SurfaceInfo.access$1500((SurfaceInfo)this.mParentSurface).top, this.mParentSurface.mWidth - parcelable.right - SurfaceInfo.access$1500((SurfaceInfo)this.mParentSurface).right, this.mParentSurface.mHeight - parcelable.bottom - SurfaceInfo.access$1500((SurfaceInfo)this.mParentSurface).bottom);
        } else {
            parcelable = new Rect(0, 0, this.mParentSurface.mWidth, this.mParentSurface.mHeight);
        }
        return new Point(Math.max(((Rect)parcelable).left, Math.min(((Rect)parcelable).right - this.mWindowWidth, this.mWindowCoords.x)), Math.max(((Rect)parcelable).top, Math.min(((Rect)parcelable).bottom - this.mWindowHeight, this.mWindowCoords.y)));
    }

    private static float getDeviceDefaultDialogCornerRadius(Context object) {
        object = new ContextThemeWrapper((Context)object, 16974120).obtainStyledAttributes(new int[]{16844145});
        float f = ((TypedArray)object).getDimension(0, 0.0f);
        ((TypedArray)object).recycle();
        return f;
    }

    public static PointF getMagnifierDefaultSize() {
        Resources resources = Resources.getSystem();
        float f = resources.getDisplayMetrics().density;
        PointF pointF = new PointF();
        pointF.x = resources.getDimension(17105120) / f;
        pointF.y = resources.getDimension(17105117) / f;
        return pointF;
    }

    private void obtainContentCoordinates(float f, float f2) {
        int n;
        int n2;
        int[] arrn;
        int n3;
        int n4;
        Object object = this.mViewCoordinatesInSurface;
        int n5 = object[0];
        int n6 = object[1];
        this.mView.getLocationInSurface((int[])object);
        object = this.mViewCoordinatesInSurface;
        if (object[0] != n5 || object[1] != n6) {
            this.mDirtyState = true;
        }
        if (this.mView instanceof SurfaceView) {
            n6 = Math.round(f);
            n5 = Math.round(f2);
        } else {
            n6 = Math.round(f + (float)this.mViewCoordinatesInSurface[0]);
            n5 = Math.round(f2 + (float)this.mViewCoordinatesInSurface[1]);
        }
        object = new Rect[2];
        object[0] = (int)new Rect(0, 0, this.mContentCopySurface.mWidth, this.mContentCopySurface.mHeight);
        Rect rect = new Rect();
        this.mView.getGlobalVisibleRect(rect);
        if (this.mView.getViewRootImpl() != null) {
            arrn = this.mView.getViewRootImpl().mWindowAttributes.surfaceInsets;
            rect.offset(arrn.left, arrn.top);
        }
        if (this.mView instanceof SurfaceView) {
            arrn = this.mViewCoordinatesInSurface;
            rect.offset(-arrn[0], -arrn[1]);
        }
        object[1] = (int)rect;
        int n7 = Integer.MIN_VALUE;
        for (n = this.mLeftContentBound; n >= 0; --n) {
            n7 = Math.max(n7, object[n].left);
        }
        n = Integer.MIN_VALUE;
        for (n4 = this.mTopContentBound; n4 >= 0; --n4) {
            n = Math.max(n, object[n4].top);
        }
        n4 = Integer.MAX_VALUE;
        for (n2 = this.mRightContentBound; n2 >= 0; --n2) {
            n4 = Math.min(n4, object[n2].right);
        }
        n2 = Integer.MAX_VALUE;
        for (n3 = this.mBottomContentBound; n3 >= 0; --n3) {
            n2 = Math.min(n2, object[n3].bottom);
        }
        n3 = Math.min(n7, this.mContentCopySurface.mWidth - this.mSourceWidth);
        n7 = Math.min(n, this.mContentCopySurface.mHeight - this.mSourceHeight);
        if (n3 < 0 || n7 < 0) {
            Log.e(TAG, "Magnifier's content is copied from a surface smaller thanthe content requested size. The magnifier will be dismissed.");
        }
        n = Math.max(n4, this.mSourceWidth + n3);
        n4 = Math.max(n2, this.mSourceHeight + n7);
        object = this.mClampedCenterZoomCoords;
        n2 = this.mSourceWidth;
        object.x = Math.max(n2 / 2 + n3, Math.min(n6, n - n2 / 2));
        object = this.mClampedCenterZoomCoords;
        n6 = this.mSourceHeight;
        object.y = Math.max(n6 / 2 + n7, Math.min(n5, n4 - n6 / 2));
    }

    private void obtainSurfaces() {
        Object object;
        Object object2;
        Object object3;
        Object object4 = object2 = SurfaceInfo.NULL;
        if (this.mView.getViewRootImpl() != null) {
            object3 = this.mView.getViewRootImpl();
            object = ((ViewRootImpl)object3).mSurface;
            object4 = object2;
            if (object != null) {
                object4 = object2;
                if (((Surface)object).isValid()) {
                    object4 = object3.mWindowAttributes.surfaceInsets;
                    int n = ((ViewRootImpl)object3).getWidth();
                    int n2 = ((Rect)object4).left;
                    int n3 = ((Rect)object4).right;
                    int n4 = ((ViewRootImpl)object3).getHeight();
                    int n5 = ((Rect)object4).top;
                    int n6 = ((Rect)object4).bottom;
                    object4 = new SurfaceInfo(((ViewRootImpl)object3).getSurfaceControl(), (Surface)object, n + n2 + n3, n4 + n5 + n6, (Rect)object4, true);
                }
            }
        }
        object3 = SurfaceInfo.NULL;
        object = this.mView;
        object2 = object3;
        if (object instanceof SurfaceView) {
            SurfaceControl surfaceControl = ((SurfaceView)object).getSurfaceControl();
            SurfaceHolder surfaceHolder = ((SurfaceView)this.mView).getHolder();
            object = surfaceHolder.getSurface();
            object2 = object3;
            if (surfaceControl != null) {
                object2 = object3;
                if (surfaceControl.isValid()) {
                    object2 = surfaceHolder.getSurfaceFrame();
                    object2 = new SurfaceInfo(surfaceControl, (Surface)object, ((Rect)object2).right, ((Rect)object2).bottom, new Rect(), false);
                }
            }
        }
        object3 = object4 != SurfaceInfo.NULL ? object4 : object2;
        this.mParentSurface = object3;
        if (this.mView instanceof SurfaceView) {
            object4 = object2;
        }
        this.mContentCopySurface = object4;
    }

    private void obtainWindowCoordinates(float f, float f2) {
        int n;
        int n2;
        if (this.mView instanceof SurfaceView) {
            n = Math.round(f);
            n2 = Math.round(f2);
        } else {
            n = Math.round((float)this.mViewCoordinatesInSurface[0] + f);
            n2 = Math.round((float)this.mViewCoordinatesInSurface[1] + f2);
        }
        Point point = this.mWindowCoords;
        point.x = n - this.mWindowWidth / 2;
        point.y = n2 - this.mWindowHeight / 2;
        if (this.mParentSurface != this.mContentCopySurface) {
            point.x += this.mViewCoordinatesInSurface[0];
            point = this.mWindowCoords;
            point.y += this.mViewCoordinatesInSurface[1];
        }
    }

    private void onPixelCopyFailed() {
        Log.e(TAG, "Magnifier failed to copy content from the view Surface. It will be dismissed.");
        Handler.getMain().postAtFrontOfQueue(new _$$Lambda$Magnifier$esRj9C7NyDvOX8eqqqLKuB6jpTw(this));
    }

    private void performPixelCopy(int n, int n2, boolean bl) {
        if (this.mContentCopySurface.mSurface != null && this.mContentCopySurface.mSurface.isValid()) {
            Point point = this.getCurrentClampedWindowCoordinates();
            this.mPixelCopyRequestRect.set(n, n2, this.mSourceWidth + n, this.mSourceHeight + n2);
            InternalPopupWindow internalPopupWindow = this.mWindow;
            Parcelable parcelable = Bitmap.createBitmap(this.mSourceWidth, this.mSourceHeight, Bitmap.Config.ARGB_8888);
            PixelCopy.request(this.mContentCopySurface.mSurface, this.mPixelCopyRequestRect, parcelable, (PixelCopy.OnPixelCopyFinishedListener)new _$$Lambda$Magnifier$K0um0QSTAb4wXwua60CgJIIwGaI(this, internalPopupWindow, bl, point, (Bitmap)parcelable), sPixelCopyHandlerThread.getThreadHandler());
            parcelable = this.mPrevStartCoordsInSurface;
            ((Point)parcelable).x = n;
            ((Point)parcelable).y = n2;
            this.mDirtyState = false;
            return;
        }
        this.onPixelCopyFailed();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dismiss() {
        if (this.mWindow == null) return;
        Object object = this.mLock;
        synchronized (object) {
            this.mWindow.destroy();
            this.mWindow = null;
        }
        Parcelable parcelable = this.mPrevShowSourceCoords;
        parcelable.x = -1.0f;
        parcelable.y = -1.0f;
        parcelable = this.mPrevShowWindowCoords;
        parcelable.x = -1.0f;
        parcelable.y = -1.0f;
        parcelable = this.mPrevStartCoordsInSurface;
        ((Point)parcelable).x = -1;
        ((Point)parcelable).y = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bitmap getContent() {
        Object object = this.mWindow;
        if (object == null) {
            return null;
        }
        object = ((InternalPopupWindow)object).mLock;
        synchronized (object) {
            return this.mWindow.mCurrentContent;
        }
    }

    public float getCornerRadius() {
        return this.mWindowCornerRadius;
    }

    public int getDefaultHorizontalSourceToMagnifierOffset() {
        return this.mDefaultHorizontalSourceToMagnifierOffset;
    }

    public int getDefaultVerticalSourceToMagnifierOffset() {
        return this.mDefaultVerticalSourceToMagnifierOffset;
    }

    public float getElevation() {
        return this.mWindowElevation;
    }

    public int getHeight() {
        return this.mWindowHeight;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bitmap getOriginalContent() {
        Object object = this.mWindow;
        if (object == null) {
            return null;
        }
        object = ((InternalPopupWindow)object).mLock;
        synchronized (object) {
            return Bitmap.createBitmap(this.mWindow.mBitmap);
        }
    }

    public Drawable getOverlay() {
        return this.mOverlay;
    }

    public Point getPosition() {
        if (this.mWindow == null) {
            return null;
        }
        Point point = this.getCurrentClampedWindowCoordinates();
        point.offset(-SurfaceInfo.access$1500((SurfaceInfo)this.mParentSurface).left, -SurfaceInfo.access$1500((SurfaceInfo)this.mParentSurface).top);
        return new Point(point);
    }

    public int getSourceHeight() {
        return this.mSourceHeight;
    }

    public Point getSourcePosition() {
        if (this.mWindow == null) {
            return null;
        }
        Point point = new Point(this.mPixelCopyRequestRect.left, this.mPixelCopyRequestRect.top);
        point.offset(-SurfaceInfo.access$1500((SurfaceInfo)this.mContentCopySurface).left, -SurfaceInfo.access$1500((SurfaceInfo)this.mContentCopySurface).top);
        return new Point(point);
    }

    public int getSourceWidth() {
        return this.mSourceWidth;
    }

    public int getWidth() {
        return this.mWindowWidth;
    }

    public float getZoom() {
        return this.mZoom;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public /* synthetic */ void lambda$onPixelCopyFailed$2$Magnifier() {
        this.dismiss();
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onOperationComplete();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$performPixelCopy$1$Magnifier(InternalPopupWindow internalPopupWindow, boolean bl, Point point, Bitmap bitmap, int n) {
        if (n != 0) {
            this.onPixelCopyFailed();
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mWindow != internalPopupWindow) {
                return;
            }
            if (bl) {
                this.mWindow.setContentPositionForNextDraw(point.x, point.y);
            }
            this.mWindow.updateContent(bitmap);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$show$0$Magnifier(InternalPopupWindow internalPopupWindow, Point point) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mWindow != internalPopupWindow) {
                return;
            }
            this.mWindow.setContentPositionForNextDraw(point.x, point.y);
            return;
        }
    }

    public void setOnOperationCompleteCallback(Callback callback) {
        this.mCallback = callback;
        InternalPopupWindow internalPopupWindow = this.mWindow;
        if (internalPopupWindow != null) {
            internalPopupWindow.mCallback = callback;
        }
    }

    public void setZoom(float f) {
        Preconditions.checkArgumentPositive(f, "Zoom should be positive");
        this.mZoom = f;
        this.mSourceWidth = Math.round((float)this.mWindowWidth / this.mZoom);
        this.mSourceHeight = Math.round((float)this.mWindowHeight / this.mZoom);
        this.mDirtyState = true;
    }

    public void show(float f, float f2) {
        this.show(f, f2, (float)this.mDefaultHorizontalSourceToMagnifierOffset + f, (float)this.mDefaultVerticalSourceToMagnifierOffset + f2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void show(float f, float f2, float f3, float f4) {
        Object object;
        this.obtainSurfaces();
        this.obtainContentCoordinates(f, f2);
        this.obtainWindowCoordinates(f3, f4);
        int n = this.mClampedCenterZoomCoords.x;
        int n2 = this.mSourceWidth / 2;
        int n3 = this.mClampedCenterZoomCoords.y;
        int n4 = this.mSourceHeight / 2;
        if (f == this.mPrevShowSourceCoords.x && f2 == this.mPrevShowSourceCoords.y && !this.mDirtyState) {
            if (f3 != this.mPrevShowWindowCoords.x || f4 != this.mPrevShowWindowCoords.y) {
                object = this.getCurrentClampedWindowCoordinates();
                InternalPopupWindow internalPopupWindow = this.mWindow;
                sPixelCopyHandlerThread.getThreadHandler().post(new _$$Lambda$Magnifier$sEUKNU2_gseoDMBt_HOs_JGAfZ8(this, internalPopupWindow, (Point)object));
            }
        } else {
            if (this.mWindow == null) {
                Object object2 = this.mLock;
                synchronized (object2) {
                    InternalPopupWindow internalPopupWindow;
                    Context context = this.mView.getContext();
                    Display display = this.mView.getDisplay();
                    SurfaceControl surfaceControl = this.mParentSurface.mSurfaceControl;
                    int n5 = this.mWindowWidth;
                    int n6 = this.mWindowHeight;
                    float f5 = this.mWindowElevation;
                    float f6 = this.mWindowCornerRadius;
                    object = this.mOverlay != null ? this.mOverlay : new ColorDrawable(0);
                    this.mWindow = internalPopupWindow = new InternalPopupWindow(context, display, surfaceControl, n5, n6, f5, f6, (Drawable)object, Handler.getMain(), this.mLock, this.mCallback);
                }
            }
            this.performPixelCopy(n - n2, n3 - n4, true);
        }
        object = this.mPrevShowSourceCoords;
        ((PointF)object).x = f;
        ((PointF)object).y = f2;
        object = this.mPrevShowWindowCoords;
        ((PointF)object).x = f3;
        ((PointF)object).y = f4;
    }

    public void update() {
        if (this.mWindow != null) {
            this.obtainSurfaces();
            if (!this.mDirtyState) {
                this.performPixelCopy(this.mPrevStartCoordsInSurface.x, this.mPrevStartCoordsInSurface.y, false);
            } else {
                this.show(this.mPrevShowSourceCoords.x, this.mPrevShowSourceCoords.y, this.mPrevShowWindowCoords.x, this.mPrevShowWindowCoords.y);
            }
        }
    }

    public static final class Builder {
        private int mBottomContentBound;
        private boolean mClippingEnabled;
        private float mCornerRadius;
        private float mElevation;
        private int mHeight;
        private int mHorizontalDefaultSourceToMagnifierOffset;
        private int mLeftContentBound;
        private Drawable mOverlay;
        private int mRightContentBound;
        private int mTopContentBound;
        private int mVerticalDefaultSourceToMagnifierOffset;
        private View mView;
        private int mWidth;
        private float mZoom;

        public Builder(View view) {
            this.mView = Preconditions.checkNotNull(view);
            this.applyDefaults();
        }

        private void applyDefaults() {
            Resources resources = this.mView.getContext().getResources();
            this.mWidth = resources.getDimensionPixelSize(17105120);
            this.mHeight = resources.getDimensionPixelSize(17105117);
            this.mElevation = resources.getDimension(17105116);
            this.mCornerRadius = resources.getDimension(17105115);
            this.mZoom = resources.getFloat(17105121);
            this.mHorizontalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(17105118);
            this.mVerticalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(17105119);
            this.mOverlay = new ColorDrawable(resources.getColor(17170750, null));
            this.mClippingEnabled = true;
            this.mLeftContentBound = 1;
            this.mTopContentBound = 1;
            this.mRightContentBound = 1;
            this.mBottomContentBound = 1;
        }

        public Magnifier build() {
            return new Magnifier(this);
        }

        public Builder setClippingEnabled(boolean bl) {
            this.mClippingEnabled = bl;
            return this;
        }

        public Builder setCornerRadius(float f) {
            Preconditions.checkArgumentNonNegative(f, "Corner radius should be non-negative");
            this.mCornerRadius = f;
            return this;
        }

        public Builder setDefaultSourceToMagnifierOffset(int n, int n2) {
            this.mHorizontalDefaultSourceToMagnifierOffset = n;
            this.mVerticalDefaultSourceToMagnifierOffset = n2;
            return this;
        }

        public Builder setElevation(float f) {
            Preconditions.checkArgumentNonNegative(f, "Elevation should be non-negative");
            this.mElevation = f;
            return this;
        }

        public Builder setInitialZoom(float f) {
            Preconditions.checkArgumentPositive(f, "Zoom should be positive");
            this.mZoom = f;
            return this;
        }

        public Builder setOverlay(Drawable drawable2) {
            this.mOverlay = drawable2;
            return this;
        }

        public Builder setSize(int n, int n2) {
            Preconditions.checkArgumentPositive(n, "Width should be positive");
            Preconditions.checkArgumentPositive(n2, "Height should be positive");
            this.mWidth = n;
            this.mHeight = n2;
            return this;
        }

        public Builder setSourceBounds(int n, int n2, int n3, int n4) {
            this.mLeftContentBound = n;
            this.mTopContentBound = n2;
            this.mRightContentBound = n3;
            this.mBottomContentBound = n4;
            return this;
        }
    }

    public static interface Callback {
        public void onOperationComplete();
    }

    private static class InternalPopupWindow {
        private static final int SURFACE_Z = 5;
        private Bitmap mBitmap;
        private final RenderNode mBitmapRenderNode;
        private Callback mCallback;
        private final int mContentHeight;
        private final int mContentWidth;
        private Bitmap mCurrentContent;
        private final Display mDisplay;
        private boolean mFirstDraw;
        private boolean mFrameDrawScheduled;
        private final Handler mHandler;
        private int mLastDrawContentPositionX;
        private int mLastDrawContentPositionY;
        private final Object mLock;
        private final Runnable mMagnifierUpdater;
        private final int mOffsetX;
        private final int mOffsetY;
        private final Drawable mOverlay;
        private final RenderNode mOverlayRenderNode;
        private boolean mPendingWindowPositionUpdate;
        private final ThreadedRenderer.SimpleRenderer mRenderer;
        private final Surface mSurface;
        private final SurfaceControl mSurfaceControl;
        private final int mSurfaceHeight;
        private final SurfaceSession mSurfaceSession;
        private final int mSurfaceWidth;
        private int mWindowPositionX;
        private int mWindowPositionY;

        InternalPopupWindow(Context object, Display display, SurfaceControl surfaceControl, int n, int n2, float f, float f2, Drawable drawable2, Handler handler, Object object2, Callback callback) {
            block3 : {
                this.mFirstDraw = true;
                this.mDisplay = display;
                this.mOverlay = drawable2;
                this.mLock = object2;
                this.mCallback = callback;
                this.mContentWidth = n;
                this.mContentHeight = n2;
                this.mOffsetX = (int)(f * 1.05f);
                this.mOffsetY = (int)(1.05f * f);
                this.mSurfaceWidth = this.mContentWidth + this.mOffsetX * 2;
                this.mSurfaceHeight = this.mContentHeight + this.mOffsetY * 2;
                this.mSurfaceSession = new SurfaceSession();
                this.mSurfaceControl = new SurfaceControl.Builder(this.mSurfaceSession).setFormat(-3).setBufferSize(this.mSurfaceWidth, this.mSurfaceHeight).setName("magnifier surface").setFlags(4).setParent(surfaceControl).build();
                this.mSurface = new Surface();
                this.mSurface.copyFrom(this.mSurfaceControl);
                this.mRenderer = new ThreadedRenderer.SimpleRenderer((Context)object, "magnifier renderer", this.mSurface);
                this.mBitmapRenderNode = this.createRenderNodeForBitmap("magnifier content", f, f2);
                this.mOverlayRenderNode = this.createRenderNodeForOverlay("magnifier overlay", f2);
                this.setupOverlay();
                object = this.mRenderer.getRootNode().beginRecording(n, n2);
                ((Canvas)object).insertReorderBarrier();
                ((RecordingCanvas)object).drawRenderNode(this.mBitmapRenderNode);
                ((Canvas)object).insertInorderBarrier();
                ((RecordingCanvas)object).drawRenderNode(this.mOverlayRenderNode);
                ((Canvas)object).insertInorderBarrier();
                if (this.mCallback == null) break block3;
                this.mCurrentContent = Bitmap.createBitmap(this.mContentWidth, this.mContentHeight, Bitmap.Config.ARGB_8888);
                this.updateCurrentContentForTesting();
            }
            this.mHandler = handler;
            this.mMagnifierUpdater = new _$$Lambda$Magnifier$InternalPopupWindow$t9Cn2sIi2LBUhAVikvRPKKoAwIU(this);
            this.mFrameDrawScheduled = false;
            return;
            finally {
                this.mRenderer.getRootNode().endRecording();
            }
        }

        private RenderNode createRenderNodeForBitmap(String object, float f, float f2) {
            object = RenderNode.create((String)object, null);
            int n = this.mOffsetX;
            int n2 = this.mOffsetY;
            ((RenderNode)object).setLeftTopRightBottom(n, n2, this.mContentWidth + n, this.mContentHeight + n2);
            ((RenderNode)object).setElevation(f);
            Object object2 = new Outline();
            ((Outline)object2).setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, f2);
            ((Outline)object2).setAlpha(1.0f);
            ((RenderNode)object).setOutline((Outline)object2);
            ((RenderNode)object).setClipToOutline(true);
            object2 = ((RenderNode)object).beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                ((BaseRecordingCanvas)object2).drawColor(-16711936);
                return object;
            }
            finally {
                ((RenderNode)object).endRecording();
            }
        }

        private RenderNode createRenderNodeForOverlay(String object, float f) {
            RenderNode renderNode = RenderNode.create((String)object, null);
            int n = this.mOffsetX;
            int n2 = this.mOffsetY;
            renderNode.setLeftTopRightBottom(n, n2, this.mContentWidth + n, this.mContentHeight + n2);
            object = new Outline();
            ((Outline)object).setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, f);
            ((Outline)object).setAlpha(1.0f);
            renderNode.setOutline((Outline)object);
            renderNode.setClipToOutline(true);
            return renderNode;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void doDraw() {
            Object object;
            Object object2 = this.mLock;
            synchronized (object2) {
                if (!this.mSurface.isValid()) {
                    return;
                }
                RecordingCanvas recordingCanvas = this.mBitmapRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
                Rect rect = new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                object = new Rect(0, 0, this.mContentWidth, this.mContentHeight);
                Paint paint = new Paint();
                paint.setFilterBitmap(true);
                recordingCanvas.drawBitmap(this.mBitmap, rect, (Rect)object, paint);
                if (!this.mPendingWindowPositionUpdate && !this.mFirstDraw) {
                    object = null;
                } else {
                    boolean bl = this.mFirstDraw;
                    this.mFirstDraw = false;
                    boolean bl2 = this.mPendingWindowPositionUpdate;
                    this.mPendingWindowPositionUpdate = false;
                    int n = this.mWindowPositionX;
                    int n2 = this.mWindowPositionY;
                    object = new _$$Lambda$Magnifier$InternalPopupWindow$qfjMrDJVvOQUv9_kKVdpLzbaJ_A(this, bl2, n, n2, bl);
                    this.mRenderer.setLightCenter(this.mDisplay, n, n2);
                }
                this.mLastDrawContentPositionX = this.mWindowPositionX + this.mOffsetX;
                this.mLastDrawContentPositionY = this.mWindowPositionY + this.mOffsetY;
                this.mFrameDrawScheduled = false;
            }
            this.mRenderer.draw((HardwareRenderer.FrameDrawingCallback)object);
            if (this.mCallback != null) {
                this.updateCurrentContentForTesting();
                this.mCallback.onOperationComplete();
            }
            return;
            {
                finally {
                    this.mBitmapRenderNode.endRecording();
                }
            }
        }

        private void drawOverlay() {
            RecordingCanvas recordingCanvas = this.mOverlayRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                this.mOverlay.setBounds(0, 0, this.mContentWidth, this.mContentHeight);
                this.mOverlay.draw(recordingCanvas);
                return;
            }
            finally {
                this.mOverlayRenderNode.endRecording();
            }
        }

        public static /* synthetic */ void lambda$t9Cn2sIi2LBUhAVikvRPKKoAwIU(InternalPopupWindow internalPopupWindow) {
            internalPopupWindow.doDraw();
        }

        private void requestUpdate() {
            if (this.mFrameDrawScheduled) {
                return;
            }
            Message message = Message.obtain(this.mHandler, this.mMagnifierUpdater);
            message.setAsynchronous(true);
            message.sendToTarget();
            this.mFrameDrawScheduled = true;
        }

        private void setupOverlay() {
            this.drawOverlay();
            this.mOverlay.setCallback(new Drawable.Callback(){

                @Override
                public void invalidateDrawable(Drawable drawable2) {
                    InternalPopupWindow.this.drawOverlay();
                    if (InternalPopupWindow.this.mCallback != null) {
                        InternalPopupWindow.this.updateCurrentContentForTesting();
                    }
                }

                @Override
                public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
                    Handler.getMain().postAtTime(runnable, drawable2, l);
                }

                @Override
                public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
                    Handler.getMain().removeCallbacks(runnable, drawable2);
                }
            });
        }

        private void updateCurrentContentForTesting() {
            Canvas canvas = new Canvas(this.mCurrentContent);
            Rect rect = new Rect(0, 0, this.mContentWidth, this.mContentHeight);
            Parcelable parcelable = this.mBitmap;
            if (parcelable != null && !parcelable.isRecycled()) {
                parcelable = new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                canvas.drawBitmap(this.mBitmap, (Rect)parcelable, rect, null);
            }
            this.mOverlay.setBounds(rect);
            this.mOverlay.draw(canvas);
        }

        public void destroy() {
            this.mRenderer.destroy();
            this.mSurface.destroy();
            this.mSurfaceControl.remove();
            this.mSurfaceSession.kill();
            this.mHandler.removeCallbacks(this.mMagnifierUpdater);
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                bitmap.recycle();
            }
        }

        public /* synthetic */ void lambda$doDraw$0$Magnifier$InternalPopupWindow(boolean bl, int n, int n2, boolean bl2, long l) {
            if (!this.mSurface.isValid()) {
                return;
            }
            SurfaceControl.openTransaction();
            this.mSurfaceControl.deferTransactionUntil(this.mSurface, l);
            if (bl) {
                this.mSurfaceControl.setPosition(n, n2);
            }
            if (bl2) {
                this.mSurfaceControl.setLayer(5);
                this.mSurfaceControl.show();
            }
            SurfaceControl.closeTransaction();
        }

        public void setContentPositionForNextDraw(int n, int n2) {
            this.mWindowPositionX = n - this.mOffsetX;
            this.mWindowPositionY = n2 - this.mOffsetY;
            this.mPendingWindowPositionUpdate = true;
            this.requestUpdate();
        }

        public void updateContent(Bitmap bitmap) {
            Bitmap bitmap2 = this.mBitmap;
            if (bitmap2 != null) {
                bitmap2.recycle();
            }
            this.mBitmap = bitmap;
            this.requestUpdate();
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SourceBound {
    }

    private static class SurfaceInfo {
        public static final SurfaceInfo NULL = new SurfaceInfo(null, null, 0, 0, null, false);
        private int mHeight;
        private Rect mInsets;
        private boolean mIsMainWindowSurface;
        private Surface mSurface;
        private SurfaceControl mSurfaceControl;
        private int mWidth;

        SurfaceInfo(SurfaceControl surfaceControl, Surface surface, int n, int n2, Rect rect, boolean bl) {
            this.mSurfaceControl = surfaceControl;
            this.mSurface = surface;
            this.mWidth = n;
            this.mHeight = n2;
            this.mInsets = rect;
            this.mIsMainWindowSurface = bl;
        }

        static /* synthetic */ Rect access$1500(SurfaceInfo surfaceInfo) {
            return surfaceInfo.mInsets;
        }
    }

}

