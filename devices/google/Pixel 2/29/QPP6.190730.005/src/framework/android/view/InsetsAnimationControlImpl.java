/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseSetArray;
import android.view.DisplayCutout;
import android.view.InsetsController;
import android.view.InsetsSource;
import android.view.InsetsSourceConsumer;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsAnimationListener;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.function.Supplier;

@VisibleForTesting
public class InsetsAnimationControlImpl
implements WindowInsetsAnimationController {
    private final WindowInsetsAnimationListener.InsetsAnimation mAnimation;
    private boolean mCancelled;
    private final SparseArray<InsetsSourceConsumer> mConsumers;
    private final InsetsController mController;
    private Insets mCurrentInsets;
    private boolean mFinished;
    private int mFinishedShownTypes;
    private final Rect mFrame;
    private final Insets mHiddenInsets;
    private final InsetsState mInitialInsetsState;
    private final WindowInsetsAnimationControlListener mListener;
    private Insets mPendingInsets;
    private final Insets mShownInsets;
    private final SparseSetArray<InsetsSourceConsumer> mSideSourceMap = new SparseSetArray();
    private final Rect mTmpFrame = new Rect();
    private final Matrix mTmpMatrix = new Matrix();
    private final Supplier<SyncRtSurfaceTransactionApplier> mTransactionApplierSupplier;
    private final SparseIntArray mTypeSideMap = new SparseIntArray();
    private final int mTypes;

    @VisibleForTesting
    public InsetsAnimationControlImpl(SparseArray<InsetsSourceConsumer> sparseArray, Rect rect, InsetsState insetsState, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, int n, Supplier<SyncRtSurfaceTransactionApplier> supplier, InsetsController insetsController) {
        this.mConsumers = sparseArray;
        this.mListener = windowInsetsAnimationControlListener;
        this.mTypes = n;
        this.mTransactionApplierSupplier = supplier;
        this.mController = insetsController;
        this.mInitialInsetsState = new InsetsState(insetsState, true);
        this.mCurrentInsets = this.getInsetsFromState(this.mInitialInsetsState, rect, null);
        this.mHiddenInsets = this.calculateInsets(this.mInitialInsetsState, rect, sparseArray, false, null);
        this.mShownInsets = this.calculateInsets(this.mInitialInsetsState, rect, sparseArray, true, this.mTypeSideMap);
        this.mFrame = new Rect(rect);
        InsetsAnimationControlImpl.buildTypeSourcesMap(this.mTypeSideMap, this.mSideSourceMap, this.mConsumers);
        windowInsetsAnimationControlListener.onReady(this, n);
        this.mAnimation = new WindowInsetsAnimationListener.InsetsAnimation(this.mTypes, this.mHiddenInsets, this.mShownInsets);
        this.mController.dispatchAnimationStarted(this.mAnimation);
    }

    private void addTranslationToMatrix(int n, int n2, Matrix matrix, Rect rect) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        matrix.postTranslate(0.0f, n2);
                        rect.offset(0, n2);
                    }
                } else {
                    matrix.postTranslate(n2, 0.0f);
                    rect.offset(n2, 0);
                }
            } else {
                matrix.postTranslate(0.0f, -n2);
                rect.offset(0, -n2);
            }
        } else {
            matrix.postTranslate(-n2, 0.0f);
            rect.offset(-n2, 0);
        }
    }

    private static void buildTypeSourcesMap(SparseIntArray sparseIntArray, SparseSetArray<InsetsSourceConsumer> sparseSetArray, SparseArray<InsetsSourceConsumer> sparseArray) {
        for (int i = sparseIntArray.size() - 1; i >= 0; --i) {
            int n = sparseIntArray.keyAt(i);
            sparseSetArray.add(sparseIntArray.valueAt(i), sparseArray.get(n));
        }
    }

    private Insets calculateInsets(InsetsState insetsState, Rect rect, SparseArray<InsetsSourceConsumer> sparseArray, boolean bl, SparseIntArray sparseIntArray) {
        for (int i = sparseArray.size() - 1; i >= 0; --i) {
            insetsState.getSource(sparseArray.valueAt(i).getType()).setVisible(bl);
        }
        return this.getInsetsFromState(insetsState, rect, sparseIntArray);
    }

    private Insets getInsetsFromState(InsetsState insetsState, Rect rect, SparseIntArray sparseIntArray) {
        return insetsState.calculateInsets(rect, false, false, null, null, null, 16, sparseIntArray).getInsets(this.mTypes);
    }

    private Insets sanitize(Insets insets) {
        return Insets.max(Insets.min(insets, this.mShownInsets), this.mHiddenInsets);
    }

    private void updateLeashesForSide(int n, int n2, int n3, ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> arrayList, InsetsState insetsState) {
        ArraySet<InsetsSourceConsumer> arraySet = this.mSideSourceMap.get(n);
        for (int i = arraySet.size() - 1; i >= 0; --i) {
            Object object = arraySet.valueAt(i);
            Object object2 = this.mInitialInsetsState.getSource(((InsetsSourceConsumer)object).getType());
            InsetsSourceControl insetsSourceControl = ((InsetsSourceConsumer)object).getControl();
            object = ((InsetsSourceConsumer)object).getControl().getLeash();
            this.mTmpMatrix.setTranslate(insetsSourceControl.getSurfacePosition().x, insetsSourceControl.getSurfacePosition().y);
            this.mTmpFrame.set(((InsetsSource)object2).getFrame());
            this.addTranslationToMatrix(n, n2, this.mTmpMatrix, this.mTmpFrame);
            insetsState.getSource(((InsetsSource)object2).getType()).setFrame(this.mTmpFrame);
            object2 = this.mTmpMatrix;
            boolean bl = n3 != 0;
            arrayList.add(new SyncRtSurfaceTransactionApplier.SurfaceParams((SurfaceControl)object, 1.0f, (Matrix)object2, null, 0, 0.0f, bl));
        }
    }

    @VisibleForTesting
    public boolean applyChangeInsets(InsetsState insetsState) {
        if (this.mCancelled) {
            return false;
        }
        Insets insets = Insets.subtract(this.mShownInsets, this.mPendingInsets);
        ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> arrayList = new ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams>();
        if (insets.left != 0) {
            this.updateLeashesForSide(0, insets.left, this.mPendingInsets.left, arrayList, insetsState);
        }
        if (insets.top != 0) {
            this.updateLeashesForSide(1, insets.top, this.mPendingInsets.top, arrayList, insetsState);
        }
        if (insets.right != 0) {
            this.updateLeashesForSide(2, insets.right, this.mPendingInsets.right, arrayList, insetsState);
        }
        if (insets.bottom != 0) {
            this.updateLeashesForSide(3, insets.bottom, this.mPendingInsets.bottom, arrayList, insetsState);
        }
        this.mTransactionApplierSupplier.get().scheduleApply(arrayList.toArray(new SyncRtSurfaceTransactionApplier.SurfaceParams[arrayList.size()]));
        this.mCurrentInsets = this.mPendingInsets;
        if (this.mFinished) {
            this.mController.notifyFinished(this, this.mFinishedShownTypes);
        }
        return this.mFinished;
    }

    @Override
    public void changeInsets(Insets insets) {
        if (!this.mFinished) {
            if (!this.mCancelled) {
                this.mPendingInsets = this.sanitize(insets);
                this.mController.scheduleApplyChangeInsets();
                return;
            }
            throw new IllegalStateException("Can't change insets on an animation that is cancelled.");
        }
        throw new IllegalStateException("Can't change insets on an animation that is finished.");
    }

    @Override
    public void finish(int n) {
        if (this.mCancelled) {
            return;
        }
        InsetsState insetsState = new InsetsState(this.mController.getState());
        for (int i = this.mConsumers.size() - 1; i >= 0; --i) {
            InsetsSourceConsumer insetsSourceConsumer = this.mConsumers.valueAt(i);
            boolean bl = (InsetsState.toPublicType(insetsSourceConsumer.getType()) & n) != 0;
            insetsState.getSource(insetsSourceConsumer.getType()).setVisible(bl);
        }
        this.changeInsets(this.getInsetsFromState(insetsState, this.mFrame, null));
        this.mFinished = true;
        this.mFinishedShownTypes = n;
    }

    WindowInsetsAnimationListener.InsetsAnimation getAnimation() {
        return this.mAnimation;
    }

    @Override
    public Insets getCurrentInsets() {
        return this.mCurrentInsets;
    }

    @Override
    public Insets getHiddenStateInsets() {
        return this.mHiddenInsets;
    }

    @Override
    public Insets getShownStateInsets() {
        return this.mShownInsets;
    }

    @Override
    public int getTypes() {
        return this.mTypes;
    }

    @VisibleForTesting
    public void onCancelled() {
        if (this.mFinished) {
            return;
        }
        this.mCancelled = true;
        this.mListener.onCancelled();
    }
}

