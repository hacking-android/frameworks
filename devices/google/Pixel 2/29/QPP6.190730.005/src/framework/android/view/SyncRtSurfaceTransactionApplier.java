/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view._$$Lambda$SyncRtSurfaceTransactionApplier$ttntIVYYZl7t890CcQHVoB3U1nQ;
import com.android.internal.annotations.VisibleForTesting;
import java.util.function.Consumer;

public class SyncRtSurfaceTransactionApplier {
    private final Surface mTargetSurface;
    private final ViewRootImpl mTargetViewRootImpl;
    private final float[] mTmpFloat9 = new float[9];

    public SyncRtSurfaceTransactionApplier(View object) {
        Object var2_2 = null;
        object = object != null ? ((View)object).getViewRootImpl() : null;
        this.mTargetViewRootImpl = object;
        ViewRootImpl viewRootImpl = this.mTargetViewRootImpl;
        object = var2_2;
        if (viewRootImpl != null) {
            object = viewRootImpl.mSurface;
        }
        this.mTargetSurface = object;
    }

    public static void applyParams(SurfaceControl.Transaction transaction, SurfaceParams surfaceParams, float[] arrf) {
        transaction.setMatrix(surfaceParams.surface, surfaceParams.matrix, arrf);
        transaction.setWindowCrop(surfaceParams.surface, surfaceParams.windowCrop);
        transaction.setAlpha(surfaceParams.surface, surfaceParams.alpha);
        transaction.setLayer(surfaceParams.surface, surfaceParams.layer);
        transaction.setCornerRadius(surfaceParams.surface, surfaceParams.cornerRadius);
        if (surfaceParams.visible) {
            transaction.show(surfaceParams.surface);
        } else {
            transaction.hide(surfaceParams.surface);
        }
    }

    public static void create(View view, final Consumer<SyncRtSurfaceTransactionApplier> consumer) {
        if (view == null) {
            consumer.accept(null);
        } else if (view.getViewRootImpl() != null) {
            consumer.accept(new SyncRtSurfaceTransactionApplier(view));
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(){

                @Override
                public void onViewAttachedToWindow(View view) {
                    View.this.removeOnAttachStateChangeListener(this);
                    consumer.accept(new SyncRtSurfaceTransactionApplier(View.this));
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                }
            });
        }
    }

    public /* synthetic */ void lambda$scheduleApply$0$SyncRtSurfaceTransactionApplier(SurfaceParams[] arrsurfaceParams, long l) {
        Object object = this.mTargetSurface;
        if (object != null && ((Surface)object).isValid()) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            for (int i = arrsurfaceParams.length - 1; i >= 0; --i) {
                object = arrsurfaceParams[i];
                transaction.deferTransactionUntilSurface(((SurfaceParams)object).surface, this.mTargetSurface, l);
                SyncRtSurfaceTransactionApplier.applyParams(transaction, (SurfaceParams)object, this.mTmpFloat9);
            }
            transaction.setEarlyWakeup();
            transaction.apply();
            return;
        }
    }

    public void scheduleApply(SurfaceParams ... arrsurfaceParams) {
        ViewRootImpl viewRootImpl = this.mTargetViewRootImpl;
        if (viewRootImpl == null) {
            return;
        }
        viewRootImpl.registerRtFrameCallback(new _$$Lambda$SyncRtSurfaceTransactionApplier$ttntIVYYZl7t890CcQHVoB3U1nQ(this, arrsurfaceParams));
        this.mTargetViewRootImpl.getView().invalidate();
    }

    public static class SurfaceParams {
        @VisibleForTesting
        public final float alpha;
        @VisibleForTesting
        final float cornerRadius;
        @VisibleForTesting
        public final int layer;
        @VisibleForTesting
        public final Matrix matrix;
        @VisibleForTesting
        public final SurfaceControl surface;
        public final boolean visible;
        @VisibleForTesting
        public final Rect windowCrop;

        public SurfaceParams(SurfaceControl surfaceControl, float f, Matrix matrix, Rect rect, int n, float f2, boolean bl) {
            this.surface = surfaceControl;
            this.alpha = f;
            this.matrix = new Matrix(matrix);
            this.windowCrop = new Rect(rect);
            this.layer = n;
            this.cornerRadius = f2;
            this.visible = bl;
        }
    }

}

