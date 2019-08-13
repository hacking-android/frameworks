/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.view.Choreographer;
import android.view.ThreadedRenderer;
import android.view.ViewRootImpl;
import com.android.internal.policy.DecorView;

public class BackdropFrameRenderer
extends Thread
implements Choreographer.FrameCallback {
    private Drawable mCaptionBackgroundDrawable;
    private Choreographer mChoreographer;
    private DecorView mDecorView;
    private RenderNode mFrameAndBackdropNode;
    private boolean mFullscreen;
    private int mLastCaptionHeight;
    private int mLastContentHeight;
    private int mLastContentWidth;
    private int mLastXOffset;
    private int mLastYOffset;
    private ColorDrawable mNavigationBarColor;
    private final Rect mNewTargetRect = new Rect();
    private boolean mOldFullscreen;
    private final Rect mOldStableInsets = new Rect();
    private final Rect mOldSystemInsets = new Rect();
    private final Rect mOldTargetRect = new Rect();
    private ThreadedRenderer mRenderer;
    private boolean mReportNextDraw;
    private Drawable mResizingBackgroundDrawable;
    private final Rect mStableInsets = new Rect();
    private ColorDrawable mStatusBarColor;
    private RenderNode mSystemBarBackgroundNode;
    private final Rect mSystemInsets = new Rect();
    private final Rect mTargetRect = new Rect();
    private final Rect mTmpRect = new Rect();
    private Drawable mUserCaptionBackgroundDrawable;

    public BackdropFrameRenderer(DecorView decorView, ThreadedRenderer threadedRenderer, Rect rect, Drawable drawable2, Drawable drawable3, Drawable drawable4, int n, int n2, boolean bl, Rect rect2, Rect rect3) {
        this.setName("ResizeFrame");
        this.mRenderer = threadedRenderer;
        this.onResourcesLoaded(decorView, drawable2, drawable3, drawable4, n, n2);
        this.mFrameAndBackdropNode = RenderNode.create("FrameAndBackdropNode", null);
        this.mRenderer.addRenderNode(this.mFrameAndBackdropNode, true);
        this.mTargetRect.set(rect);
        this.mFullscreen = bl;
        this.mOldFullscreen = bl;
        this.mSystemInsets.set(rect2);
        this.mStableInsets.set(rect3);
        this.mOldSystemInsets.set(rect2);
        this.mOldStableInsets.set(rect3);
        this.start();
    }

    private void addSystemBarNodeIfNeeded() {
        if (this.mSystemBarBackgroundNode != null) {
            return;
        }
        this.mSystemBarBackgroundNode = RenderNode.create("SystemBarBackgroundNode", null);
        this.mRenderer.addRenderNode(this.mSystemBarBackgroundNode, false);
    }

    private void doFrameUncheckedLocked() {
        this.mNewTargetRect.set(this.mTargetRect);
        if (!this.mNewTargetRect.equals(this.mOldTargetRect) || this.mOldFullscreen != this.mFullscreen || !this.mStableInsets.equals(this.mOldStableInsets) || !this.mSystemInsets.equals(this.mOldSystemInsets) || this.mReportNextDraw) {
            this.mOldFullscreen = this.mFullscreen;
            this.mOldTargetRect.set(this.mNewTargetRect);
            this.mOldSystemInsets.set(this.mSystemInsets);
            this.mOldStableInsets.set(this.mStableInsets);
            this.redrawLocked(this.mNewTargetRect, this.mFullscreen, this.mSystemInsets, this.mStableInsets);
        }
    }

    private void drawColorViews(int n, int n2, int n3, int n4, boolean bl, Rect rect, Rect rect2) {
        Object object = this.mSystemBarBackgroundNode;
        if (object == null) {
            return;
        }
        RecordingCanvas recordingCanvas = ((RenderNode)object).beginRecording(n3, n4);
        this.mSystemBarBackgroundNode.setLeftTopRightBottom(n, n2, n + n3, n2 + n4);
        n2 = DecorView.getColorViewTopInset(this.mStableInsets.top, this.mSystemInsets.top);
        object = this.mStatusBarColor;
        if (object != null) {
            ((Drawable)object).setBounds(0, 0, n + n3, n2);
            this.mStatusBarColor.draw(recordingCanvas);
        }
        if (this.mNavigationBarColor != null && bl) {
            DecorView.getNavigationBarRect(n3, n4, rect2, rect, this.mTmpRect, 1.0f);
            this.mNavigationBarColor.setBounds(this.mTmpRect);
            this.mNavigationBarColor.draw(recordingCanvas);
        }
        this.mSystemBarBackgroundNode.endRecording();
        this.mRenderer.drawRenderNode(this.mSystemBarBackgroundNode);
    }

    private void pingRenderLocked(boolean bl) {
        Choreographer choreographer = this.mChoreographer;
        if (choreographer != null && !bl) {
            choreographer.postFrameCallback(this);
        } else {
            this.doFrameUncheckedLocked();
        }
    }

    private void redrawLocked(Rect object, boolean bl, Rect rect, Rect rect2) {
        int n = this.mDecorView.getCaptionHeight();
        if (n != 0) {
            this.mLastCaptionHeight = n;
        }
        if (!(this.mLastCaptionHeight == 0 && this.mDecorView.isShowingCaption() || this.mLastContentWidth == 0 || this.mLastContentHeight == 0)) {
            int n2 = this.mLastXOffset + ((Rect)object).left;
            n = this.mLastYOffset + ((Rect)object).top;
            int n3 = ((Rect)object).width();
            int n4 = ((Rect)object).height();
            this.mFrameAndBackdropNode.setLeftTopRightBottom(n2, n, n2 + n3, n + n4);
            RecordingCanvas recordingCanvas = this.mFrameAndBackdropNode.beginRecording(n3, n4);
            object = this.mUserCaptionBackgroundDrawable;
            if (object == null) {
                object = this.mCaptionBackgroundDrawable;
            }
            if (object != null) {
                ((Drawable)object).setBounds(0, 0, n2 + n3, this.mLastCaptionHeight + n);
                ((Drawable)object).draw(recordingCanvas);
            }
            if ((object = this.mResizingBackgroundDrawable) != null) {
                ((Drawable)object).setBounds(0, this.mLastCaptionHeight, n2 + n3, n + n4);
                this.mResizingBackgroundDrawable.draw(recordingCanvas);
            }
            this.mFrameAndBackdropNode.endRecording();
            this.drawColorViews(n2, n, n3, n4, bl, rect, rect2);
            this.mRenderer.drawRenderNode(this.mFrameAndBackdropNode);
            this.reportDrawIfNeeded();
            return;
        }
    }

    private void reportDrawIfNeeded() {
        if (this.mReportNextDraw) {
            if (this.mDecorView.isAttachedToWindow()) {
                this.mDecorView.getViewRootImpl().reportDrawFinish();
            }
            this.mReportNextDraw = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void doFrame(long l) {
        synchronized (this) {
            if (this.mRenderer == null) {
                this.reportDrawIfNeeded();
                Looper.myLooper().quit();
                return;
            }
            this.doFrameUncheckedLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onConfigurationChange() {
        synchronized (this) {
            if (this.mRenderer != null) {
                this.mOldTargetRect.set(0, 0, 0, 0);
                this.pingRenderLocked(false);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean onContentDrawn(int n, int n2, int n3, int n4) {
        synchronized (this) {
            int n5 = this.mLastContentWidth;
            boolean bl = true;
            n5 = n5 == 0 ? 1 : 0;
            this.mLastContentWidth = n3;
            this.mLastContentHeight = n4 - this.mLastCaptionHeight;
            this.mLastXOffset = n;
            this.mLastYOffset = n2;
            this.mRenderer.setContentDrawBounds(this.mLastXOffset, this.mLastYOffset, this.mLastXOffset + this.mLastContentWidth, this.mLastYOffset + this.mLastCaptionHeight + this.mLastContentHeight);
            if (n5 == 0) return false;
            if (this.mLastCaptionHeight != 0) return bl;
            if (this.mDecorView.isShowingCaption()) return false;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onRequestDraw(boolean bl) {
        synchronized (this) {
            this.mReportNextDraw = bl;
            this.mOldTargetRect.set(0, 0, 0, 0);
            this.pingRenderLocked(true);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onResourcesLoaded(DecorView object, Drawable drawable2, Drawable drawable3, Drawable drawable4, int n, int n2) {
        synchronized (this) {
            this.mDecorView = object;
            object = drawable2 != null && drawable2.getConstantState() != null ? drawable2.getConstantState().newDrawable() : null;
            this.mResizingBackgroundDrawable = object;
            object = drawable3 != null && drawable3.getConstantState() != null ? drawable3.getConstantState().newDrawable() : null;
            this.mCaptionBackgroundDrawable = object;
            object = drawable4 != null && drawable4.getConstantState() != null ? drawable4.getConstantState().newDrawable() : null;
            this.mUserCaptionBackgroundDrawable = object;
            if (this.mCaptionBackgroundDrawable == null) {
                this.mCaptionBackgroundDrawable = this.mResizingBackgroundDrawable;
            }
            if (n != 0) {
                this.mStatusBarColor = object = new ColorDrawable(n);
                this.addSystemBarNodeIfNeeded();
            } else {
                this.mStatusBarColor = null;
            }
            if (n2 != 0) {
                this.mNavigationBarColor = object = new ColorDrawable(n2);
                this.addSystemBarNodeIfNeeded();
            } else {
                this.mNavigationBarColor = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void releaseRenderer() {
        synchronized (this) {
            if (this.mRenderer != null) {
                this.mRenderer.setContentDrawBounds(0, 0, 0, 0);
                this.mRenderer.removeRenderNode(this.mFrameAndBackdropNode);
                if (this.mSystemBarBackgroundNode != null) {
                    this.mRenderer.removeRenderNode(this.mSystemBarBackgroundNode);
                }
                this.mRenderer = null;
                this.pingRenderLocked(false);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        Looper.prepare();
        // MONITORENTER : this
        this.mChoreographer = Choreographer.getInstance();
        // MONITOREXIT : this
        Looper.loop();
        // MONITORENTER : this
        this.mChoreographer = null;
        Choreographer.releaseInstance();
        // MONITOREXIT : this
        return;
        finally {
            this.releaseRenderer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTargetRect(Rect rect, boolean bl, Rect rect2, Rect rect3) {
        synchronized (this) {
            this.mFullscreen = bl;
            this.mTargetRect.set(rect);
            this.mSystemInsets.set(rect2);
            this.mStableInsets.set(rect3);
            this.pingRenderLocked(false);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setUserCaptionBackgroundDrawable(Drawable drawable2) {
        synchronized (this) {
            this.mUserCaptionBackgroundDrawable = drawable2;
            return;
        }
    }
}

