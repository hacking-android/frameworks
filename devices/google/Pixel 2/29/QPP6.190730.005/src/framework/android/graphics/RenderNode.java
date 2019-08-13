/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.view.NativeVectorDrawableAnimator;
import android.view.RenderNodeAnimator;
import com.android.internal.util.ArrayUtils;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.util.NativeAllocationRegistry;

public final class RenderNode {
    public static final int USAGE_BACKGROUND = 1;
    public static final int USAGE_UNKNOWN = 0;
    private final AnimationHost mAnimationHost;
    private CompositePositionUpdateListener mCompositePositionUpdateListener;
    private RecordingCanvas mCurrentRecordingCanvas;
    public final long mNativeRenderNode;

    private RenderNode(long l) {
        this.mNativeRenderNode = l;
        NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativeRenderNode);
        this.mAnimationHost = null;
    }

    public RenderNode(String string2) {
        this(string2, null);
    }

    private RenderNode(String string2, AnimationHost animationHost) {
        this.mNativeRenderNode = RenderNode.nCreate(string2);
        NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativeRenderNode);
        this.mAnimationHost = animationHost;
    }

    static /* synthetic */ long access$000() {
        return RenderNode.nGetNativeFinalizer();
    }

    public static RenderNode adopt(long l) {
        return new RenderNode(l);
    }

    public static RenderNode create(String string2, AnimationHost animationHost) {
        return new RenderNode(string2, animationHost);
    }

    private static native void nAddAnimator(long var0, long var2);

    private static native long nCreate(String var0);

    private static native void nEndAllAnimators(long var0);

    @CriticalNative
    private static native boolean nGetAllowForceDark(long var0);

    @CriticalNative
    private static native float nGetAlpha(long var0);

    @CriticalNative
    private static native int nGetAmbientShadowColor(long var0);

    @CriticalNative
    private static native boolean nGetAnimationMatrix(long var0, long var2);

    @CriticalNative
    private static native int nGetBottom(long var0);

    @CriticalNative
    private static native float nGetCameraDistance(long var0);

    @CriticalNative
    private static native boolean nGetClipToBounds(long var0);

    @CriticalNative
    private static native boolean nGetClipToOutline(long var0);

    private static native int nGetDebugSize(long var0);

    @CriticalNative
    private static native float nGetElevation(long var0);

    @CriticalNative
    private static native int nGetHeight(long var0);

    @CriticalNative
    private static native void nGetInverseTransformMatrix(long var0, long var2);

    @CriticalNative
    private static native int nGetLayerType(long var0);

    @CriticalNative
    private static native int nGetLeft(long var0);

    private static native long nGetNativeFinalizer();

    @CriticalNative
    private static native float nGetPivotX(long var0);

    @CriticalNative
    private static native float nGetPivotY(long var0);

    @CriticalNative
    private static native int nGetRight(long var0);

    @CriticalNative
    private static native float nGetRotation(long var0);

    @CriticalNative
    private static native float nGetRotationX(long var0);

    @CriticalNative
    private static native float nGetRotationY(long var0);

    @CriticalNative
    private static native float nGetScaleX(long var0);

    @CriticalNative
    private static native float nGetScaleY(long var0);

    @CriticalNative
    private static native int nGetSpotShadowColor(long var0);

    @CriticalNative
    private static native int nGetTop(long var0);

    @CriticalNative
    private static native void nGetTransformMatrix(long var0, long var2);

    @CriticalNative
    private static native float nGetTranslationX(long var0);

    @CriticalNative
    private static native float nGetTranslationY(long var0);

    @CriticalNative
    private static native float nGetTranslationZ(long var0);

    @CriticalNative
    private static native long nGetUniqueId(long var0);

    @CriticalNative
    private static native int nGetWidth(long var0);

    @CriticalNative
    private static native boolean nHasIdentityMatrix(long var0);

    @CriticalNative
    private static native boolean nHasOverlappingRendering(long var0);

    @CriticalNative
    private static native boolean nHasShadow(long var0);

    @CriticalNative
    private static native boolean nIsPivotExplicitlySet(long var0);

    @CriticalNative
    private static native boolean nIsValid(long var0);

    @CriticalNative
    private static native boolean nOffsetLeftAndRight(long var0, int var2);

    @CriticalNative
    private static native boolean nOffsetTopAndBottom(long var0, int var2);

    private static native void nOutput(long var0);

    private static native void nRequestPositionUpdates(long var0, PositionUpdateListener var2);

    @CriticalNative
    private static native boolean nResetPivot(long var0);

    @CriticalNative
    private static native boolean nSetAllowForceDark(long var0, boolean var2);

    @CriticalNative
    private static native boolean nSetAlpha(long var0, float var2);

    @CriticalNative
    private static native boolean nSetAmbientShadowColor(long var0, int var2);

    @CriticalNative
    private static native boolean nSetAnimationMatrix(long var0, long var2);

    @CriticalNative
    private static native boolean nSetBottom(long var0, int var2);

    @CriticalNative
    private static native boolean nSetCameraDistance(long var0, float var2);

    @CriticalNative
    private static native boolean nSetClipBounds(long var0, int var2, int var3, int var4, int var5);

    @CriticalNative
    private static native boolean nSetClipBoundsEmpty(long var0);

    @CriticalNative
    private static native boolean nSetClipToBounds(long var0, boolean var2);

    @CriticalNative
    private static native boolean nSetClipToOutline(long var0, boolean var2);

    @FastNative
    private static native void nSetDisplayList(long var0, long var2);

    @CriticalNative
    private static native boolean nSetElevation(long var0, float var2);

    @CriticalNative
    private static native boolean nSetHasOverlappingRendering(long var0, boolean var2);

    @CriticalNative
    private static native boolean nSetLayerPaint(long var0, long var2);

    @CriticalNative
    private static native boolean nSetLayerType(long var0, int var2);

    @CriticalNative
    private static native boolean nSetLeft(long var0, int var2);

    @CriticalNative
    private static native boolean nSetLeftTopRightBottom(long var0, int var2, int var3, int var4, int var5);

    @CriticalNative
    private static native boolean nSetOutlineConvexPath(long var0, long var2, float var4);

    @CriticalNative
    private static native boolean nSetOutlineEmpty(long var0);

    @CriticalNative
    private static native boolean nSetOutlineNone(long var0);

    @CriticalNative
    private static native boolean nSetOutlineRoundRect(long var0, int var2, int var3, int var4, int var5, float var6, float var7);

    @CriticalNative
    private static native boolean nSetPivotX(long var0, float var2);

    @CriticalNative
    private static native boolean nSetPivotY(long var0, float var2);

    @CriticalNative
    private static native boolean nSetProjectBackwards(long var0, boolean var2);

    @CriticalNative
    private static native boolean nSetProjectionReceiver(long var0, boolean var2);

    @CriticalNative
    private static native boolean nSetRevealClip(long var0, boolean var2, float var3, float var4, float var5);

    @CriticalNative
    private static native boolean nSetRight(long var0, int var2);

    @CriticalNative
    private static native boolean nSetRotation(long var0, float var2);

    @CriticalNative
    private static native boolean nSetRotationX(long var0, float var2);

    @CriticalNative
    private static native boolean nSetRotationY(long var0, float var2);

    @CriticalNative
    private static native boolean nSetScaleX(long var0, float var2);

    @CriticalNative
    private static native boolean nSetScaleY(long var0, float var2);

    @CriticalNative
    private static native boolean nSetSpotShadowColor(long var0, int var2);

    @CriticalNative
    private static native boolean nSetStaticMatrix(long var0, long var2);

    @CriticalNative
    private static native boolean nSetTop(long var0, int var2);

    @CriticalNative
    private static native boolean nSetTranslationX(long var0, float var2);

    @CriticalNative
    private static native boolean nSetTranslationY(long var0, float var2);

    @CriticalNative
    private static native boolean nSetTranslationZ(long var0, float var2);

    @CriticalNative
    private static native void nSetUsageHint(long var0, int var2);

    public void addAnimator(RenderNodeAnimator renderNodeAnimator) {
        if (this.isAttached()) {
            RenderNode.nAddAnimator(this.mNativeRenderNode, renderNodeAnimator.getNativeAnimator());
            this.mAnimationHost.registerAnimatingRenderNode(this);
            return;
        }
        throw new IllegalStateException("Cannot start this animator on a detached view!");
    }

    public void addPositionUpdateListener(PositionUpdateListener positionUpdateListener) {
        CompositePositionUpdateListener compositePositionUpdateListener = this.mCompositePositionUpdateListener;
        positionUpdateListener = compositePositionUpdateListener == null ? new CompositePositionUpdateListener(positionUpdateListener) : compositePositionUpdateListener.with(positionUpdateListener);
        this.mCompositePositionUpdateListener = positionUpdateListener;
        RenderNode.nRequestPositionUpdates(this.mNativeRenderNode, positionUpdateListener);
    }

    public RecordingCanvas beginRecording() {
        return this.beginRecording(RenderNode.nGetWidth(this.mNativeRenderNode), RenderNode.nGetHeight(this.mNativeRenderNode));
    }

    public RecordingCanvas beginRecording(int n, int n2) {
        if (this.mCurrentRecordingCanvas == null) {
            this.mCurrentRecordingCanvas = RecordingCanvas.obtain(this, n, n2);
            return this.mCurrentRecordingCanvas;
        }
        throw new IllegalStateException("Recording currently in progress - missing #endRecording() call?");
    }

    public long computeApproximateMemoryUsage() {
        return RenderNode.nGetDebugSize(this.mNativeRenderNode);
    }

    public void discardDisplayList() {
        RenderNode.nSetDisplayList(this.mNativeRenderNode, 0L);
    }

    @Deprecated
    public void end(RecordingCanvas recordingCanvas) {
        if (recordingCanvas == this.mCurrentRecordingCanvas) {
            this.endRecording();
            return;
        }
        throw new IllegalArgumentException("Wrong canvas");
    }

    public void endAllAnimators() {
        RenderNode.nEndAllAnimators(this.mNativeRenderNode);
    }

    public void endRecording() {
        if (this.mCurrentRecordingCanvas != null) {
            RecordingCanvas recordingCanvas = this.mCurrentRecordingCanvas;
            this.mCurrentRecordingCanvas = null;
            long l = recordingCanvas.finishRecording();
            RenderNode.nSetDisplayList(this.mNativeRenderNode, l);
            recordingCanvas.recycle();
            return;
        }
        throw new IllegalStateException("No recording in progress, forgot to call #beginRecording()?");
    }

    public float getAlpha() {
        return RenderNode.nGetAlpha(this.mNativeRenderNode);
    }

    public int getAmbientShadowColor() {
        return RenderNode.nGetAmbientShadowColor(this.mNativeRenderNode);
    }

    public Matrix getAnimationMatrix() {
        Matrix matrix = new Matrix();
        if (RenderNode.nGetAnimationMatrix(this.mNativeRenderNode, matrix.native_instance)) {
            return matrix;
        }
        return null;
    }

    public int getBottom() {
        return RenderNode.nGetBottom(this.mNativeRenderNode);
    }

    public float getCameraDistance() {
        return -RenderNode.nGetCameraDistance(this.mNativeRenderNode);
    }

    public boolean getClipToBounds() {
        return RenderNode.nGetClipToBounds(this.mNativeRenderNode);
    }

    public boolean getClipToOutline() {
        return RenderNode.nGetClipToOutline(this.mNativeRenderNode);
    }

    public float getElevation() {
        return RenderNode.nGetElevation(this.mNativeRenderNode);
    }

    public int getHeight() {
        return RenderNode.nGetHeight(this.mNativeRenderNode);
    }

    public void getInverseMatrix(Matrix matrix) {
        RenderNode.nGetInverseTransformMatrix(this.mNativeRenderNode, matrix.native_instance);
    }

    public int getLeft() {
        return RenderNode.nGetLeft(this.mNativeRenderNode);
    }

    public void getMatrix(Matrix matrix) {
        RenderNode.nGetTransformMatrix(this.mNativeRenderNode, matrix.native_instance);
    }

    public float getPivotX() {
        return RenderNode.nGetPivotX(this.mNativeRenderNode);
    }

    public float getPivotY() {
        return RenderNode.nGetPivotY(this.mNativeRenderNode);
    }

    public int getRight() {
        return RenderNode.nGetRight(this.mNativeRenderNode);
    }

    public float getRotationX() {
        return RenderNode.nGetRotationX(this.mNativeRenderNode);
    }

    public float getRotationY() {
        return RenderNode.nGetRotationY(this.mNativeRenderNode);
    }

    public float getRotationZ() {
        return RenderNode.nGetRotation(this.mNativeRenderNode);
    }

    public float getScaleX() {
        return RenderNode.nGetScaleX(this.mNativeRenderNode);
    }

    public float getScaleY() {
        return RenderNode.nGetScaleY(this.mNativeRenderNode);
    }

    public int getSpotShadowColor() {
        return RenderNode.nGetSpotShadowColor(this.mNativeRenderNode);
    }

    public int getTop() {
        return RenderNode.nGetTop(this.mNativeRenderNode);
    }

    public float getTranslationX() {
        return RenderNode.nGetTranslationX(this.mNativeRenderNode);
    }

    public float getTranslationY() {
        return RenderNode.nGetTranslationY(this.mNativeRenderNode);
    }

    public float getTranslationZ() {
        return RenderNode.nGetTranslationZ(this.mNativeRenderNode);
    }

    public long getUniqueId() {
        return RenderNode.nGetUniqueId(this.mNativeRenderNode);
    }

    public boolean getUseCompositingLayer() {
        boolean bl = RenderNode.nGetLayerType(this.mNativeRenderNode) != 0;
        return bl;
    }

    public int getWidth() {
        return RenderNode.nGetWidth(this.mNativeRenderNode);
    }

    public boolean hasDisplayList() {
        return RenderNode.nIsValid(this.mNativeRenderNode);
    }

    public boolean hasIdentityMatrix() {
        return RenderNode.nHasIdentityMatrix(this.mNativeRenderNode);
    }

    public boolean hasOverlappingRendering() {
        return RenderNode.nHasOverlappingRendering(this.mNativeRenderNode);
    }

    public boolean hasShadow() {
        return RenderNode.nHasShadow(this.mNativeRenderNode);
    }

    public boolean isAttached() {
        AnimationHost animationHost = this.mAnimationHost;
        boolean bl = animationHost != null && animationHost.isAttached();
        return bl;
    }

    public boolean isForceDarkAllowed() {
        return RenderNode.nGetAllowForceDark(this.mNativeRenderNode);
    }

    public boolean isPivotExplicitlySet() {
        return RenderNode.nIsPivotExplicitlySet(this.mNativeRenderNode);
    }

    public boolean offsetLeftAndRight(int n) {
        return RenderNode.nOffsetLeftAndRight(this.mNativeRenderNode, n);
    }

    public boolean offsetTopAndBottom(int n) {
        return RenderNode.nOffsetTopAndBottom(this.mNativeRenderNode, n);
    }

    public void output() {
        RenderNode.nOutput(this.mNativeRenderNode);
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        if (this.isAttached()) {
            this.mAnimationHost.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
            return;
        }
        throw new IllegalStateException("Cannot start this animator on a detached view!");
    }

    public void removePositionUpdateListener(PositionUpdateListener positionUpdateListener) {
        CompositePositionUpdateListener compositePositionUpdateListener = this.mCompositePositionUpdateListener;
        if (compositePositionUpdateListener != null) {
            positionUpdateListener = compositePositionUpdateListener.without(positionUpdateListener);
            this.mCompositePositionUpdateListener = positionUpdateListener;
            RenderNode.nRequestPositionUpdates(this.mNativeRenderNode, positionUpdateListener);
        }
    }

    public boolean resetPivot() {
        return RenderNode.nResetPivot(this.mNativeRenderNode);
    }

    public boolean setAlpha(float f) {
        return RenderNode.nSetAlpha(this.mNativeRenderNode, f);
    }

    public boolean setAmbientShadowColor(int n) {
        return RenderNode.nSetAmbientShadowColor(this.mNativeRenderNode, n);
    }

    public boolean setAnimationMatrix(Matrix matrix) {
        long l = this.mNativeRenderNode;
        long l2 = matrix != null ? matrix.native_instance : 0L;
        return RenderNode.nSetAnimationMatrix(l, l2);
    }

    public boolean setBottom(int n) {
        return RenderNode.nSetBottom(this.mNativeRenderNode, n);
    }

    public boolean setCameraDistance(float f) {
        if (Float.isFinite(f) && !(f < 0.0f)) {
            return RenderNode.nSetCameraDistance(this.mNativeRenderNode, -f);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("distance must be finite & positive, given=");
        stringBuilder.append(f);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean setClipRect(Rect rect) {
        if (rect == null) {
            return RenderNode.nSetClipBoundsEmpty(this.mNativeRenderNode);
        }
        return RenderNode.nSetClipBounds(this.mNativeRenderNode, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean setClipToBounds(boolean bl) {
        return RenderNode.nSetClipToBounds(this.mNativeRenderNode, bl);
    }

    public boolean setClipToOutline(boolean bl) {
        return RenderNode.nSetClipToOutline(this.mNativeRenderNode, bl);
    }

    public boolean setElevation(float f) {
        return RenderNode.nSetElevation(this.mNativeRenderNode, f);
    }

    public boolean setForceDarkAllowed(boolean bl) {
        return RenderNode.nSetAllowForceDark(this.mNativeRenderNode, bl);
    }

    public boolean setHasOverlappingRendering(boolean bl) {
        return RenderNode.nSetHasOverlappingRendering(this.mNativeRenderNode, bl);
    }

    @Deprecated
    public boolean setLayerPaint(Paint paint) {
        long l = this.mNativeRenderNode;
        long l2 = paint != null ? paint.getNativeInstance() : 0L;
        return RenderNode.nSetLayerPaint(l, l2);
    }

    @Deprecated
    public boolean setLayerType(int n) {
        return RenderNode.nSetLayerType(this.mNativeRenderNode, n);
    }

    public boolean setLeft(int n) {
        return RenderNode.nSetLeft(this.mNativeRenderNode, n);
    }

    public boolean setLeftTopRightBottom(int n, int n2, int n3, int n4) {
        return RenderNode.nSetLeftTopRightBottom(this.mNativeRenderNode, n, n2, n3, n4);
    }

    public boolean setOutline(Outline outline) {
        if (outline == null) {
            return RenderNode.nSetOutlineNone(this.mNativeRenderNode);
        }
        int n = outline.mMode;
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    return RenderNode.nSetOutlineConvexPath(this.mNativeRenderNode, outline.mPath.mNativePath, outline.mAlpha);
                }
                throw new IllegalArgumentException("Unrecognized outline?");
            }
            return RenderNode.nSetOutlineRoundRect(this.mNativeRenderNode, outline.mRect.left, outline.mRect.top, outline.mRect.right, outline.mRect.bottom, outline.mRadius, outline.mAlpha);
        }
        return RenderNode.nSetOutlineEmpty(this.mNativeRenderNode);
    }

    public boolean setPivotX(float f) {
        return RenderNode.nSetPivotX(this.mNativeRenderNode, f);
    }

    public boolean setPivotY(float f) {
        return RenderNode.nSetPivotY(this.mNativeRenderNode, f);
    }

    public boolean setPosition(int n, int n2, int n3, int n4) {
        return RenderNode.nSetLeftTopRightBottom(this.mNativeRenderNode, n, n2, n3, n4);
    }

    public boolean setPosition(Rect rect) {
        return RenderNode.nSetLeftTopRightBottom(this.mNativeRenderNode, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean setProjectBackwards(boolean bl) {
        return RenderNode.nSetProjectBackwards(this.mNativeRenderNode, bl);
    }

    public boolean setProjectionReceiver(boolean bl) {
        return RenderNode.nSetProjectionReceiver(this.mNativeRenderNode, bl);
    }

    public boolean setRevealClip(boolean bl, float f, float f2, float f3) {
        return RenderNode.nSetRevealClip(this.mNativeRenderNode, bl, f, f2, f3);
    }

    public boolean setRight(int n) {
        return RenderNode.nSetRight(this.mNativeRenderNode, n);
    }

    public boolean setRotationX(float f) {
        return RenderNode.nSetRotationX(this.mNativeRenderNode, f);
    }

    public boolean setRotationY(float f) {
        return RenderNode.nSetRotationY(this.mNativeRenderNode, f);
    }

    public boolean setRotationZ(float f) {
        return RenderNode.nSetRotation(this.mNativeRenderNode, f);
    }

    public boolean setScaleX(float f) {
        return RenderNode.nSetScaleX(this.mNativeRenderNode, f);
    }

    public boolean setScaleY(float f) {
        return RenderNode.nSetScaleY(this.mNativeRenderNode, f);
    }

    public boolean setSpotShadowColor(int n) {
        return RenderNode.nSetSpotShadowColor(this.mNativeRenderNode, n);
    }

    public boolean setStaticMatrix(Matrix matrix) {
        return RenderNode.nSetStaticMatrix(this.mNativeRenderNode, matrix.native_instance);
    }

    public boolean setTop(int n) {
        return RenderNode.nSetTop(this.mNativeRenderNode, n);
    }

    public boolean setTranslationX(float f) {
        return RenderNode.nSetTranslationX(this.mNativeRenderNode, f);
    }

    public boolean setTranslationY(float f) {
        return RenderNode.nSetTranslationY(this.mNativeRenderNode, f);
    }

    public boolean setTranslationZ(float f) {
        return RenderNode.nSetTranslationZ(this.mNativeRenderNode, f);
    }

    public void setUsageHint(int n) {
        RenderNode.nSetUsageHint(this.mNativeRenderNode, n);
    }

    public boolean setUseCompositingLayer(boolean bl, Paint paint) {
        long l = this.mNativeRenderNode;
        int n = bl ? 2 : 0;
        bl = RenderNode.nSetLayerType(l, n);
        long l2 = this.mNativeRenderNode;
        l = paint != null ? paint.getNativeInstance() : 0L;
        return bl | RenderNode.nSetLayerPaint(l2, l);
    }

    @Deprecated
    public RecordingCanvas start(int n, int n2) {
        return this.beginRecording(n, n2);
    }

    public static interface AnimationHost {
        public boolean isAttached();

        public void registerAnimatingRenderNode(RenderNode var1);

        public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator var1);
    }

    private static final class CompositePositionUpdateListener
    implements PositionUpdateListener {
        private static final PositionUpdateListener[] sEmpty = new PositionUpdateListener[0];
        private final PositionUpdateListener[] mListeners;

        CompositePositionUpdateListener(PositionUpdateListener ... arrpositionUpdateListener) {
            if (arrpositionUpdateListener == null) {
                arrpositionUpdateListener = sEmpty;
            }
            this.mListeners = arrpositionUpdateListener;
        }

        @Override
        public void positionChanged(long l, int n, int n2, int n3, int n4) {
            PositionUpdateListener[] arrpositionUpdateListener = this.mListeners;
            int n5 = arrpositionUpdateListener.length;
            for (int i = 0; i < n5; ++i) {
                arrpositionUpdateListener[i].positionChanged(l, n, n2, n3, n4);
            }
        }

        @Override
        public void positionLost(long l) {
            PositionUpdateListener[] arrpositionUpdateListener = this.mListeners;
            int n = arrpositionUpdateListener.length;
            for (int i = 0; i < n; ++i) {
                arrpositionUpdateListener[i].positionLost(l);
            }
        }

        public CompositePositionUpdateListener with(PositionUpdateListener positionUpdateListener) {
            return new CompositePositionUpdateListener(ArrayUtils.appendElement(PositionUpdateListener.class, this.mListeners, positionUpdateListener));
        }

        public CompositePositionUpdateListener without(PositionUpdateListener positionUpdateListener) {
            return new CompositePositionUpdateListener(ArrayUtils.removeElement(PositionUpdateListener.class, this.mListeners, positionUpdateListener));
        }
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)RenderNode.class.getClassLoader(), (long)RenderNode.access$000());

        private NoImagePreloadHolder() {
        }
    }

    public static interface PositionUpdateListener {
        public void positionChanged(long var1, int var3, int var4, int var5, int var6);

        public void positionLost(long var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UsageHint {
    }

}

