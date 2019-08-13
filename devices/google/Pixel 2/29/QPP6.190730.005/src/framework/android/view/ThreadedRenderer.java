/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.FrameInfo;
import android.graphics.HardwareRenderer;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.animation.AnimationUtils;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public final class ThreadedRenderer
extends HardwareRenderer {
    public static final String DEBUG_DIRTY_REGIONS_PROPERTY = "debug.hwui.show_dirty_regions";
    public static final String DEBUG_FORCE_DARK = "debug.hwui.force_dark";
    public static final String DEBUG_FPS_DIVISOR = "debug.hwui.fps_divisor";
    public static final String DEBUG_OVERDRAW_PROPERTY = "debug.hwui.overdraw";
    public static final String DEBUG_SHOW_LAYERS_UPDATES_PROPERTY = "debug.hwui.show_layers_updates";
    public static final String DEBUG_SHOW_NON_RECTANGULAR_CLIP_PROPERTY = "debug.hwui.show_non_rect_clip";
    public static int EGL_CONTEXT_PRIORITY_HIGH_IMG = 0;
    public static int EGL_CONTEXT_PRIORITY_LOW_IMG = 0;
    public static int EGL_CONTEXT_PRIORITY_MEDIUM_IMG = 0;
    public static final String OVERDRAW_PROPERTY_SHOW = "show";
    static final String PRINT_CONFIG_PROPERTY = "debug.hwui.print_config";
    static final String PROFILE_MAXFRAMES_PROPERTY = "debug.hwui.profile.maxframes";
    public static final String PROFILE_PROPERTY = "debug.hwui.profile";
    public static final String PROFILE_PROPERTY_VISUALIZE_BARS = "visual_bars";
    private static final String[] VISUALIZERS;
    public static boolean sRendererDisabled;
    private static Boolean sSupportsOpenGL;
    public static boolean sSystemRendererDisabled;
    public static boolean sTrimForeground;
    private boolean mEnabled;
    private boolean mHasInsets;
    private int mHeight;
    private boolean mInitialized = false;
    private int mInsetLeft;
    private int mInsetTop;
    private final float mLightRadius;
    private final float mLightY;
    private final float mLightZ;
    private HardwareRenderer.FrameDrawingCallback mNextRtFrameCallback;
    private boolean mRequested = true;
    private boolean mRootNodeNeedsUpdate;
    private int mSurfaceHeight;
    private int mSurfaceWidth;
    private int mWidth;

    static {
        EGL_CONTEXT_PRIORITY_HIGH_IMG = 12545;
        EGL_CONTEXT_PRIORITY_MEDIUM_IMG = 12546;
        EGL_CONTEXT_PRIORITY_LOW_IMG = 12547;
        ThreadedRenderer.isAvailable();
        sRendererDisabled = false;
        sSystemRendererDisabled = false;
        sTrimForeground = false;
        VISUALIZERS = new String[]{PROFILE_PROPERTY_VISUALIZE_BARS};
    }

    ThreadedRenderer(Context object, boolean bl, String string2) {
        this.setName(string2);
        this.setOpaque(bl ^ true);
        object = ((Context)object).obtainStyledAttributes(null, R.styleable.Lighting, 0, 0);
        this.mLightY = ((TypedArray)object).getDimension(3, 0.0f);
        this.mLightZ = ((TypedArray)object).getDimension(4, 0.0f);
        this.mLightRadius = ((TypedArray)object).getDimension(2, 0.0f);
        float f = ((TypedArray)object).getFloat(0, 0.0f);
        float f2 = ((TypedArray)object).getFloat(1, 0.0f);
        ((TypedArray)object).recycle();
        this.setLightSourceAlpha(f, f2);
    }

    public static ThreadedRenderer create(Context context, boolean bl, String string2) {
        ThreadedRenderer threadedRenderer = null;
        if (ThreadedRenderer.isAvailable()) {
            threadedRenderer = new ThreadedRenderer(context, bl, string2);
        }
        return threadedRenderer;
    }

    private static void destroyResources(View view) {
        view.destroyHardwareResources();
    }

    public static void disable(boolean bl) {
        sRendererDisabled = true;
        if (bl) {
            sSystemRendererDisabled = true;
        }
    }

    public static void enableForegroundTrimming() {
        sTrimForeground = true;
    }

    public static boolean isAvailable() {
        Boolean bl = sSupportsOpenGL;
        if (bl != null) {
            return bl;
        }
        boolean bl2 = false;
        if (SystemProperties.getInt("ro.kernel.qemu", 0) == 0) {
            sSupportsOpenGL = true;
            return true;
        }
        int n = SystemProperties.getInt("qemu.gles", -1);
        if (n == -1) {
            return false;
        }
        if (n > 0) {
            bl2 = true;
        }
        sSupportsOpenGL = bl2;
        return sSupportsOpenGL;
    }

    private void updateEnabledState(Surface surface) {
        if (surface != null && surface.isValid()) {
            this.setEnabled(this.mInitialized);
        } else {
            this.setEnabled(false);
        }
    }

    private void updateRootDisplayList(View view, DrawCallbacks drawCallbacks) {
        Trace.traceBegin(8L, "Record View#draw()");
        this.updateViewTreeDisplayList(view);
        Object object = this.mNextRtFrameCallback;
        this.mNextRtFrameCallback = null;
        if (object != null) {
            this.setFrameCallback((HardwareRenderer.FrameDrawingCallback)object);
        }
        if (this.mRootNodeNeedsUpdate || !this.mRootNode.hasDisplayList()) {
            object = this.mRootNode.beginRecording(this.mSurfaceWidth, this.mSurfaceHeight);
            int n = ((Canvas)object).save();
            ((Canvas)object).translate(this.mInsetLeft, this.mInsetTop);
            drawCallbacks.onPreDraw((RecordingCanvas)object);
            ((RecordingCanvas)object).enableZ();
            ((RecordingCanvas)object).drawRenderNode(view.updateDisplayListIfDirty());
            ((RecordingCanvas)object).disableZ();
            drawCallbacks.onPostDraw((RecordingCanvas)object);
            ((Canvas)object).restoreToCount(n);
            this.mRootNodeNeedsUpdate = false;
        }
        Trace.traceEnd(8L);
        return;
        finally {
            this.mRootNode.endRecording();
        }
    }

    private void updateViewTreeDisplayList(View view) {
        view.mPrivateFlags |= 32;
        boolean bl = (view.mPrivateFlags & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        view.mRecreateDisplayList = bl;
        view.mPrivateFlags &= Integer.MAX_VALUE;
        view.updateDisplayListIfDirty();
        view.mRecreateDisplayList = false;
    }

    Picture captureRenderingCommands() {
        return null;
    }

    @Override
    public void destroy() {
        this.mInitialized = false;
        this.updateEnabledState(null);
        super.destroy();
    }

    void destroyHardwareResources(View view) {
        ThreadedRenderer.destroyResources(view);
        this.clearContent();
    }

    void draw(View view, View.AttachInfo attachInfo, DrawCallbacks drawCallbacks) {
        int n;
        Choreographer choreographer = attachInfo.mViewRootImpl.mChoreographer;
        choreographer.mFrameInfo.markDrawStart();
        this.updateRootDisplayList(view, drawCallbacks);
        if (attachInfo.mPendingAnimatingRenderNodes != null) {
            int n2 = attachInfo.mPendingAnimatingRenderNodes.size();
            for (n = 0; n < n2; ++n) {
                this.registerAnimatingRenderNode(attachInfo.mPendingAnimatingRenderNodes.get(n));
            }
            attachInfo.mPendingAnimatingRenderNodes.clear();
            attachInfo.mPendingAnimatingRenderNodes = null;
        }
        if (((n = this.syncAndDrawFrame(choreographer.mFrameInfo)) & 2) != 0) {
            this.setEnabled(false);
            attachInfo.mViewRootImpl.mSurface.release();
            attachInfo.mViewRootImpl.invalidate();
        }
        if ((n & 1) != 0) {
            attachInfo.mViewRootImpl.invalidate();
        }
    }

    void dumpGfxInfo(PrintWriter object, FileDescriptor fileDescriptor, String[] arrstring) {
        ((PrintWriter)object).flush();
        int n = arrstring != null && arrstring.length != 0 ? 0 : 1;
        int n2 = n;
        for (int i = 0; i < arrstring.length; ++i) {
            object = arrstring[i];
            n = -1;
            int n3 = ((String)object).hashCode();
            if (n3 != -252053678) {
                if (n3 != 1492) {
                    if (n3 == 108404047 && ((String)object).equals("reset")) {
                        n = 1;
                    }
                } else if (((String)object).equals("-a")) {
                    n = 2;
                }
            } else if (((String)object).equals("framestats")) {
                n = 0;
            }
            n = n != 0 ? (n != 1 ? (n != 2 ? n2 : 1) : n2 | 2) : n2 | 1;
            n2 = n;
        }
        this.dumpProfileInfo(fileDescriptor, n2);
    }

    int getHeight() {
        return this.mHeight;
    }

    public RenderNode getRootNode() {
        return this.mRootNode;
    }

    int getWidth() {
        return this.mWidth;
    }

    boolean initialize(Surface surface) throws Surface.OutOfResourcesException {
        boolean bl = this.mInitialized;
        this.mInitialized = true;
        this.updateEnabledState(surface);
        this.setSurface(surface);
        return bl ^ true;
    }

    boolean initializeIfNeeded(int n, int n2, View.AttachInfo attachInfo, Surface surface, Rect rect) throws Surface.OutOfResourcesException {
        if (this.isRequested() && !this.isEnabled() && this.initialize(surface)) {
            this.setup(n, n2, attachInfo, rect);
            return true;
        }
        return false;
    }

    void invalidateRoot() {
        this.mRootNodeNeedsUpdate = true;
    }

    boolean isEnabled() {
        return this.mEnabled;
    }

    boolean isRequested() {
        return this.mRequested;
    }

    @Override
    public boolean loadSystemProperties() {
        boolean bl = super.loadSystemProperties();
        if (bl) {
            this.invalidateRoot();
        }
        return bl;
    }

    void registerRtFrameCallback(HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        this.mNextRtFrameCallback = frameDrawingCallback;
    }

    void setEnabled(boolean bl) {
        this.mEnabled = bl;
    }

    void setLightCenter(View.AttachInfo attachInfo) {
        Point point = attachInfo.mPoint;
        attachInfo.mDisplay.getRealSize(point);
        this.setLightSourceGeometry((float)point.x / 2.0f - (float)attachInfo.mWindowLeft, this.mLightY - (float)attachInfo.mWindowTop, this.mLightZ, this.mLightRadius);
    }

    void setRequested(boolean bl) {
        this.mRequested = bl;
    }

    @Override
    public void setSurface(Surface surface) {
        if (surface != null && surface.isValid()) {
            super.setSurface(surface);
        } else {
            super.setSurface(null);
        }
    }

    void setup(int n, int n2, View.AttachInfo attachInfo, Rect rect) {
        this.mWidth = n;
        this.mHeight = n2;
        if (rect != null && (rect.left != 0 || rect.right != 0 || rect.top != 0 || rect.bottom != 0)) {
            this.mHasInsets = true;
            this.mInsetLeft = rect.left;
            this.mInsetTop = rect.top;
            this.mSurfaceWidth = this.mInsetLeft + n + rect.right;
            this.mSurfaceHeight = this.mInsetTop + n2 + rect.bottom;
            this.setOpaque(false);
        } else {
            this.mHasInsets = false;
            this.mInsetLeft = 0;
            this.mInsetTop = 0;
            this.mSurfaceWidth = n;
            this.mSurfaceHeight = n2;
        }
        this.mRootNode.setLeftTopRightBottom(-this.mInsetLeft, -this.mInsetTop, this.mSurfaceWidth, this.mSurfaceHeight);
        this.setLightCenter(attachInfo);
    }

    void updateSurface(Surface surface) throws Surface.OutOfResourcesException {
        this.updateEnabledState(surface);
        this.setSurface(surface);
    }

    static interface DrawCallbacks {
        public void onPostDraw(RecordingCanvas var1);

        public void onPreDraw(RecordingCanvas var1);
    }

    public static class SimpleRenderer
    extends HardwareRenderer {
        private final float mLightRadius;
        private final float mLightY;
        private final float mLightZ;

        public SimpleRenderer(Context object, String string2, Surface surface) {
            this.setName(string2);
            this.setOpaque(false);
            this.setSurface(surface);
            object = ((Context)object).obtainStyledAttributes(null, R.styleable.Lighting, 0, 0);
            this.mLightY = ((TypedArray)object).getDimension(3, 0.0f);
            this.mLightZ = ((TypedArray)object).getDimension(4, 0.0f);
            this.mLightRadius = ((TypedArray)object).getDimension(2, 0.0f);
            float f = ((TypedArray)object).getFloat(0, 0.0f);
            float f2 = ((TypedArray)object).getFloat(1, 0.0f);
            ((TypedArray)object).recycle();
            this.setLightSourceAlpha(f, f2);
        }

        public void draw(HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
            long l = AnimationUtils.currentAnimationTimeMillis();
            if (frameDrawingCallback != null) {
                this.setFrameCallback(frameDrawingCallback);
            }
            this.createRenderRequest().setVsyncTime(l * 1000000L).syncAndDraw();
        }

        public RenderNode getRootNode() {
            return this.mRootNode;
        }

        public void setLightCenter(Display display, int n, int n2) {
            Point point = new Point();
            display.getRealSize(point);
            this.setLightSourceGeometry((float)point.x / 2.0f - (float)n, this.mLightY - (float)n2, this.mLightZ, this.mLightRadius);
        }
    }

}

