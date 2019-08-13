/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceSession;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view._$$Lambda$SurfaceView$4$wAwzCgpoBmqWbw6GlT0xJXSxjm4;
import android.view._$$Lambda$SurfaceView$Cs7TGTdA1lXf9qW8VOJAfEsMjdk;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class SurfaceView
extends View
implements ViewRootImpl.WindowStoppedCallback {
    private static final boolean DEBUG = false;
    private static final String TAG = "SurfaceView";
    private boolean mAttachedToWindow;
    SurfaceControl mBackgroundControl;
    @UnsupportedAppUsage
    final ArrayList<SurfaceHolder.Callback> mCallbacks = new ArrayList();
    final Configuration mConfiguration = new Configuration();
    float mCornerRadius;
    SurfaceControl mDeferredDestroySurfaceControl;
    boolean mDrawFinished = false;
    @UnsupportedAppUsage
    private final ViewTreeObserver.OnPreDrawListener mDrawListener = new ViewTreeObserver.OnPreDrawListener(){

        @Override
        public boolean onPreDraw() {
            SurfaceView surfaceView = SurfaceView.this;
            boolean bl = surfaceView.getWidth() > 0 && SurfaceView.this.getHeight() > 0;
            surfaceView.mHaveFrame = bl;
            SurfaceView.this.updateSurface();
            return true;
        }
    };
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    boolean mDrawingStopped = true;
    @UnsupportedAppUsage
    int mFormat = -1;
    private boolean mGlobalListenersAdded;
    @UnsupportedAppUsage
    boolean mHaveFrame = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    boolean mIsCreating = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    long mLastLockTime = 0L;
    int mLastSurfaceHeight = -1;
    int mLastSurfaceWidth = -1;
    boolean mLastWindowVisibility = false;
    final int[] mLocation = new int[2];
    private int mPendingReportDraws;
    private RenderNode.PositionUpdateListener mPositionListener = new RenderNode.PositionUpdateListener(){

        @Override
        public void positionChanged(long l, int n, int n2, int n3, int n4) {
            if (SurfaceView.this.mSurfaceControl == null) {
                return;
            }
            SurfaceView.this.mRtHandlingPositionUpdates = true;
            if (SurfaceView.access$100((SurfaceView)SurfaceView.this).left == n && SurfaceView.access$100((SurfaceView)SurfaceView.this).top == n2 && SurfaceView.access$100((SurfaceView)SurfaceView.this).right == n3 && SurfaceView.access$100((SurfaceView)SurfaceView.this).bottom == n4) {
                return;
            }
            try {
                SurfaceView.this.mRTLastReportedPosition.set(n, n2, n3, n4);
                SurfaceView.this.setParentSpaceRectangle(SurfaceView.this.mRTLastReportedPosition, l);
            }
            catch (Exception exception) {
                Log.e(SurfaceView.TAG, "Exception from repositionChild", exception);
            }
        }

        @Override
        public void positionLost(long l) {
            SurfaceView.this.mRTLastReportedPosition.setEmpty();
            if (SurfaceView.this.mSurfaceControl == null) {
                return;
            }
            if (l > 0L) {
                ViewRootImpl viewRootImpl = SurfaceView.this.getViewRootImpl();
                SurfaceView.this.mRtTransaction.deferTransactionUntilSurface(SurfaceView.this.mSurfaceControl, viewRootImpl.mSurface, l);
            }
            SurfaceView.this.mRtTransaction.hide(SurfaceView.this.mSurfaceControl);
            SurfaceView.this.mRtTransaction.apply();
        }
    };
    private Rect mRTLastReportedPosition = new Rect();
    @UnsupportedAppUsage
    int mRequestedFormat = 4;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mRequestedHeight = -1;
    boolean mRequestedVisible = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mRequestedWidth = -1;
    Paint mRoundedViewportPaint;
    private volatile boolean mRtHandlingPositionUpdates = false;
    private SurfaceControl.Transaction mRtTransaction = new SurfaceControl.Transaction();
    final Rect mScreenRect = new Rect();
    private final ViewTreeObserver.OnScrollChangedListener mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener(){

        @Override
        public void onScrollChanged() {
            SurfaceView.this.updateSurface();
        }
    };
    int mSubLayer = -2;
    @UnsupportedAppUsage
    final Surface mSurface = new Surface();
    SurfaceControl mSurfaceControl;
    boolean mSurfaceCreated = false;
    private int mSurfaceFlags = 4;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final Rect mSurfaceFrame = new Rect();
    int mSurfaceHeight = -1;
    @UnsupportedAppUsage
    private final SurfaceHolder mSurfaceHolder = new SurfaceHolder(){
        private static final String LOG_TAG = "SurfaceHolder";

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private Canvas internalLockCanvas(Rect var1_1, boolean var2_4) {
            block6 : {
                SurfaceView.this.mSurfaceLock.lock();
                var4_6 = var3_5 = null;
                if (SurfaceView.this.mDrawingStopped) break block6;
                var4_6 = var3_5;
                if (SurfaceView.this.mSurfaceControl == null) break block6;
                if (!var2_4) ** GOTO lbl10
                try {
                    block7 : {
                        var1_1 = SurfaceView.this.mSurface.lockHardwareCanvas();
                        break block7;
lbl10: // 1 sources:
                        var1_1 = SurfaceView.this.mSurface.lockCanvas((Rect)var1_1);
                    }
                    var4_6 = var1_1;
                }
                catch (Exception var1_2) {
                    Log.e("SurfaceHolder", "Exception locking surface", var1_2);
                    var4_6 = var3_5;
                }
            }
            if (var4_6 != null) {
                SurfaceView.this.mLastLockTime = SystemClock.uptimeMillis();
                return var4_6;
            }
            var5_7 = SystemClock.uptimeMillis();
            var7_8 = SurfaceView.this.mLastLockTime + 100L;
            var9_9 = var5_7;
            if (var7_8 > var5_7) {
                try {
                    Thread.sleep(var7_8 - var5_7);
                }
                catch (InterruptedException var1_3) {
                    // empty catch block
                }
                var9_9 = SystemClock.uptimeMillis();
            }
            var1_1 = SurfaceView.this;
            var1_1.mLastLockTime = var9_9;
            var1_1.mSurfaceLock.unlock();
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void addCallback(SurfaceHolder.Callback callback) {
            ArrayList<SurfaceHolder.Callback> arrayList = SurfaceView.this.mCallbacks;
            synchronized (arrayList) {
                if (!SurfaceView.this.mCallbacks.contains(callback)) {
                    SurfaceView.this.mCallbacks.add(callback);
                }
                return;
            }
        }

        @Override
        public Surface getSurface() {
            return SurfaceView.this.mSurface;
        }

        @Override
        public Rect getSurfaceFrame() {
            return SurfaceView.this.mSurfaceFrame;
        }

        @Override
        public boolean isCreating() {
            return SurfaceView.this.mIsCreating;
        }

        public /* synthetic */ void lambda$setKeepScreenOn$0$SurfaceView$4(boolean bl) {
            SurfaceView.this.setKeepScreenOn(bl);
        }

        @Override
        public Canvas lockCanvas() {
            return this.internalLockCanvas(null, false);
        }

        @Override
        public Canvas lockCanvas(Rect rect) {
            return this.internalLockCanvas(rect, false);
        }

        @Override
        public Canvas lockHardwareCanvas() {
            return this.internalLockCanvas(null, true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void removeCallback(SurfaceHolder.Callback callback) {
            ArrayList<SurfaceHolder.Callback> arrayList = SurfaceView.this.mCallbacks;
            synchronized (arrayList) {
                SurfaceView.this.mCallbacks.remove(callback);
                return;
            }
        }

        @Override
        public void setFixedSize(int n, int n2) {
            if (SurfaceView.this.mRequestedWidth != n || SurfaceView.this.mRequestedHeight != n2) {
                SurfaceView surfaceView = SurfaceView.this;
                surfaceView.mRequestedWidth = n;
                surfaceView.mRequestedHeight = n2;
                surfaceView.requestLayout();
            }
        }

        @Override
        public void setFormat(int n) {
            int n2 = n;
            if (n == -1) {
                n2 = 4;
            }
            SurfaceView surfaceView = SurfaceView.this;
            surfaceView.mRequestedFormat = n2;
            if (surfaceView.mSurfaceControl != null) {
                SurfaceView.this.updateSurface();
            }
        }

        @Override
        public void setKeepScreenOn(boolean bl) {
            SurfaceView.this.runOnUiThread(new _$$Lambda$SurfaceView$4$wAwzCgpoBmqWbw6GlT0xJXSxjm4(this, bl));
        }

        @Override
        public void setSizeFromLayout() {
            if (SurfaceView.this.mRequestedWidth != -1 || SurfaceView.this.mRequestedHeight != -1) {
                SurfaceView surfaceView = SurfaceView.this;
                surfaceView.mRequestedHeight = -1;
                surfaceView.mRequestedWidth = -1;
                surfaceView.requestLayout();
            }
        }

        @Deprecated
        @Override
        public void setType(int n) {
        }

        @Override
        public void unlockCanvasAndPost(Canvas canvas) {
            SurfaceView.this.mSurface.unlockCanvasAndPost(canvas);
            SurfaceView.this.mSurfaceLock.unlock();
        }
    };
    @UnsupportedAppUsage
    final ReentrantLock mSurfaceLock = new ReentrantLock();
    SurfaceSession mSurfaceSession;
    int mSurfaceWidth = -1;
    final Rect mTmpRect = new Rect();
    private CompatibilityInfo.Translator mTranslator;
    boolean mViewVisibility = false;
    boolean mVisible = false;
    int mWindowSpaceLeft = -1;
    int mWindowSpaceTop = -1;
    boolean mWindowStopped = false;
    boolean mWindowVisibility = false;

    public SurfaceView(Context context) {
        this(context, null);
    }

    public SurfaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SurfaceView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SurfaceView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mRenderNode.addPositionUpdateListener(this.mPositionListener);
        this.setWillNotDraw(true);
    }

    private void applySurfaceTransforms(SurfaceControl surfaceControl, Rect rect, long l) {
        if (l > 0L) {
            ViewRootImpl viewRootImpl = this.getViewRootImpl();
            this.mRtTransaction.deferTransactionUntilSurface(surfaceControl, viewRootImpl.mSurface, l);
        }
        this.mRtTransaction.setPosition(surfaceControl, rect.left, rect.top);
        this.mRtTransaction.setMatrix(surfaceControl, (float)rect.width() / (float)this.mSurfaceWidth, 0.0f, 0.0f, (float)rect.height() / (float)this.mSurfaceHeight);
        if (this.mViewVisibility) {
            this.mRtTransaction.show(surfaceControl);
        }
    }

    private void clearSurfaceViewPort(Canvas canvas) {
        if (this.mCornerRadius > 0.0f) {
            canvas.getClipBounds(this.mTmpRect);
            float f = this.mTmpRect.left;
            float f2 = this.mTmpRect.top;
            float f3 = this.mTmpRect.right;
            float f4 = this.mTmpRect.bottom;
            float f5 = this.mCornerRadius;
            canvas.drawRoundRect(f, f2, f3, f4, f5, f5, this.mRoundedViewportPaint);
        } else {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
    }

    private Rect getParentSurfaceInsets() {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.mWindowAttributes.surfaceInsets;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SurfaceHolder.Callback[] getSurfaceCallbacks() {
        ArrayList<SurfaceHolder.Callback> arrayList = this.mCallbacks;
        synchronized (arrayList) {
            SurfaceHolder.Callback[] arrcallback = new SurfaceHolder.Callback[this.mCallbacks.size()];
            this.mCallbacks.toArray(arrcallback);
            return arrcallback;
        }
    }

    private boolean isAboveParent() {
        boolean bl = this.mSubLayer >= 0;
        return bl;
    }

    public static /* synthetic */ void lambda$SyyzxOgxKwZMRgiiTGcRYbOU5JY(SurfaceView surfaceView) {
        surfaceView.onDrawFinished();
    }

    private void onDrawFinished() {
        SurfaceControl surfaceControl = this.mDeferredDestroySurfaceControl;
        if (surfaceControl != null) {
            surfaceControl.remove();
            this.mDeferredDestroySurfaceControl = null;
        }
        this.runOnUiThread(new _$$Lambda$SurfaceView$Cs7TGTdA1lXf9qW8VOJAfEsMjdk(this));
    }

    private void performDrawFinished() {
        if (this.mPendingReportDraws > 0) {
            this.mDrawFinished = true;
            if (this.mAttachedToWindow) {
                this.notifyDrawFinished();
                this.invalidate();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(System.identityHashCode(this));
            stringBuilder.append("finished drawing but no pending report draw (extra call to draw completion runnable?)");
            Log.e(TAG, stringBuilder.toString());
        }
    }

    private void releaseSurfaces() {
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl != null) {
            surfaceControl.remove();
            this.mSurfaceControl = null;
        }
        if ((surfaceControl = this.mBackgroundControl) != null) {
            surfaceControl.remove();
            this.mBackgroundControl = null;
        }
    }

    private void runOnUiThread(Runnable runnable) {
        Handler handler = this.getHandler();
        if (handler != null && handler.getLooper() != Looper.myLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    private void setParentSpaceRectangle(Rect rect, long l) {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        this.applySurfaceTransforms(this.mSurfaceControl, rect, l);
        this.applyChildSurfaceTransaction_renderWorker(this.mRtTransaction, viewRootImpl.mSurface, l);
        this.mRtTransaction.apply();
    }

    private void updateBackgroundVisibilityInTransaction(SurfaceControl surfaceControl) {
        SurfaceControl surfaceControl2 = this.mBackgroundControl;
        if (surfaceControl2 == null) {
            return;
        }
        if (this.mSubLayer < 0 && (this.mSurfaceFlags & 1024) != 0) {
            surfaceControl2.show();
            this.mBackgroundControl.setRelativeLayer(surfaceControl, Integer.MIN_VALUE);
        } else {
            this.mBackgroundControl.hide();
        }
    }

    private void updateOpaqueFlag() {
        this.mSurfaceFlags = !PixelFormat.formatHasAlpha(this.mRequestedFormat) ? (this.mSurfaceFlags |= 1024) : (this.mSurfaceFlags &= -1025);
    }

    private void updateRequestedVisibility() {
        boolean bl = this.mViewVisibility && this.mWindowVisibility && !this.mWindowStopped;
        this.mRequestedVisible = bl;
    }

    protected void applyChildSurfaceTransaction_renderWorker(SurfaceControl.Transaction transaction, Surface surface, long l) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.mDrawFinished && !this.isAboveParent() && (this.mPrivateFlags & 128) == 128) {
            this.clearSurfaceViewPort(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.mDrawFinished && !this.isAboveParent() && (this.mPrivateFlags & 128) == 0) {
            this.clearSurfaceViewPort(canvas);
        }
        super.draw(canvas);
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        if (!this.isAboveParent() && this.mDrawFinished) {
            boolean bl;
            boolean bl2 = true;
            if ((this.mPrivateFlags & 128) == 0) {
                bl = super.gatherTransparentRegion(region);
            } else {
                bl = bl2;
                if (region != null) {
                    int n = this.getWidth();
                    int n2 = this.getHeight();
                    bl = bl2;
                    if (n > 0) {
                        bl = bl2;
                        if (n2 > 0) {
                            this.getLocationInWindow(this.mLocation);
                            int[] arrn = this.mLocation;
                            int n3 = arrn[0];
                            int n4 = arrn[1];
                            region.op(n3, n4, n3 + n, n4 + n2, Region.Op.UNION);
                            bl = bl2;
                        }
                    }
                }
            }
            if (PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
                bl = false;
            }
            return bl;
        }
        return super.gatherTransparentRegion(region);
    }

    public SurfaceHolder getHolder() {
        return this.mSurfaceHolder;
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @UnsupportedAppUsage
    public boolean isFixedSize() {
        boolean bl = this.mRequestedWidth != -1 || this.mRequestedHeight != -1;
        return bl;
    }

    public /* synthetic */ void lambda$onDrawFinished$0$SurfaceView() {
        this.performDrawFinished();
    }

    void notifyDrawFinished() {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.pendingDrawFinished();
        }
        --this.mPendingReportDraws;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getViewRootImpl().addWindowStoppedCallback(this);
        boolean bl = false;
        this.mWindowStopped = false;
        if (this.getVisibility() == 0) {
            bl = true;
        }
        this.mViewVisibility = bl;
        this.updateRequestedVisibility();
        this.mAttachedToWindow = true;
        this.mParent.requestTransparentRegion(this);
        if (!this.mGlobalListenersAdded) {
            ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
            viewTreeObserver.addOnScrollChangedListener(this.mScrollChangedListener);
            viewTreeObserver.addOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = true;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Object object = this.getViewRootImpl();
        if (object != null) {
            ((ViewRootImpl)object).removeWindowStoppedCallback(this);
        }
        this.mAttachedToWindow = false;
        if (this.mGlobalListenersAdded) {
            object = this.getViewTreeObserver();
            ((ViewTreeObserver)object).removeOnScrollChangedListener(this.mScrollChangedListener);
            ((ViewTreeObserver)object).removeOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = false;
        }
        while (this.mPendingReportDraws > 0) {
            this.notifyDrawFinished();
        }
        this.mRequestedVisible = false;
        this.updateSurface();
        object = this.mSurfaceControl;
        if (object != null) {
            ((SurfaceControl)object).remove();
        }
        this.mSurfaceControl = null;
        this.mHaveFrame = false;
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = this.mRequestedWidth;
        n = n3 >= 0 ? SurfaceView.resolveSizeAndState(n3, n, 0) : SurfaceView.getDefaultSize(0, n);
        n3 = this.mRequestedHeight;
        n2 = n3 >= 0 ? SurfaceView.resolveSizeAndState(n3, n2, 0) : SurfaceView.getDefaultSize(0, n2);
        this.setMeasuredDimension(n, n2);
    }

    @Override
    protected void onWindowVisibilityChanged(int n) {
        super.onWindowVisibilityChanged(n);
        boolean bl = n == 0;
        this.mWindowVisibility = bl;
        this.updateRequestedVisibility();
        this.updateSurface();
    }

    public void setCornerRadius(float f) {
        this.mCornerRadius = f;
        if (this.mCornerRadius > 0.0f && this.mRoundedViewportPaint == null) {
            this.mRoundedViewportPaint = new Paint(1);
            this.mRoundedViewportPaint.setBlendMode(BlendMode.CLEAR);
            this.mRoundedViewportPaint.setColor(0);
        }
        this.invalidate();
    }

    @UnsupportedAppUsage
    @Override
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        this.updateSurface();
        return bl;
    }

    public void setResizeBackgroundColor(int n) {
        if (this.mBackgroundControl == null) {
            return;
        }
        float f = (float)Color.red(n) / 255.0f;
        float f2 = (float)Color.green(n) / 255.0f;
        float f3 = (float)Color.blue(n) / 255.0f;
        SurfaceControl.openTransaction();
        try {
            this.mBackgroundControl.setColor(new float[]{f, f2, f3});
            return;
        }
        finally {
            SurfaceControl.closeTransaction();
        }
    }

    public void setSecure(boolean bl) {
        this.mSurfaceFlags = bl ? (this.mSurfaceFlags |= 128) : (this.mSurfaceFlags &= -129);
    }

    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = true;
        boolean bl2 = n == 0;
        this.mViewVisibility = bl2;
        bl2 = this.mWindowVisibility && this.mViewVisibility && !this.mWindowStopped ? bl : false;
        if (bl2 != this.mRequestedVisible) {
            this.requestLayout();
        }
        this.mRequestedVisible = bl2;
        this.updateSurface();
    }

    public void setZOrderMediaOverlay(boolean bl) {
        int n = bl ? -1 : -2;
        this.mSubLayer = n;
    }

    public void setZOrderOnTop(boolean bl) {
        this.mSubLayer = bl ? 1 : -2;
    }

    /*
     * Exception decompiling
     */
    protected void updateSurface() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void windowStopped(boolean bl) {
        this.mWindowStopped = bl;
        this.updateRequestedVisibility();
        this.updateSurface();
    }

}

