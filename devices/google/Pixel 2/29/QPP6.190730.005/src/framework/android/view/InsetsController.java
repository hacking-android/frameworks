/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.-$
 *  android.view.-$$Lambda
 *  android.view.-$$Lambda$9vBfnQOmNnsc9WU80IIatZHQGKc
 *  android.view.-$$Lambda$InsetsController
 *  android.view.-$$Lambda$InsetsController$Cj7UJrCkdHvJAZ_cYKrXuTMsjz8
 */
package android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.-$;
import android.view.Choreographer;
import android.view.DisplayCutout;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.ImeInsetsSourceConsumer;
import android.view.InsetsAnimationControlImpl;
import android.view.InsetsSource;
import android.view.InsetsSourceConsumer;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsAnimationListener;
import android.view.WindowInsetsController;
import android.view._$$Lambda$9vBfnQOmNnsc9WU80IIatZHQGKc;
import android.view._$$Lambda$InsetsController$Cj7UJrCkdHvJAZ_cYKrXuTMsjz8;
import android.view._$$Lambda$InsetsController$HI9QZ2HvGm6iykc_WONz2KPG61Q;
import android.view._$$Lambda$InsetsController$n9dGLDW5oKSxT73i9ZlnIPWSzms;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.function.Supplier;

public class InsetsController
implements WindowInsetsController {
    private static final int ANIMATION_DURATION_HIDE_MS = 340;
    private static final int ANIMATION_DURATION_SHOW_MS = 275;
    private static final int DIRECTION_HIDE = 2;
    private static final int DIRECTION_NONE = 0;
    private static final int DIRECTION_SHOW = 1;
    private static final Interpolator INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static TypeEvaluator<Insets> sEvaluator = _$$Lambda$InsetsController$Cj7UJrCkdHvJAZ_cYKrXuTMsjz8.INSTANCE;
    private final String TAG;
    private final Runnable mAnimCallback;
    private boolean mAnimCallbackScheduled;
    private final ArrayList<InsetsAnimationControlImpl> mAnimationControls = new ArrayList();
    @AnimationDirection
    private int mAnimationDirection;
    private final Rect mFrame = new Rect();
    private WindowInsets mLastInsets;
    private final Rect mLastLegacyContentInsets = new Rect();
    private int mLastLegacySoftInputMode;
    private final Rect mLastLegacyStableInsets = new Rect();
    private int mPendingTypesToShow;
    private final SparseArray<InsetsSourceConsumer> mSourceConsumers = new SparseArray();
    private final InsetsState mState = new InsetsState();
    private final SparseArray<InsetsSourceControl> mTmpControlArray = new SparseArray();
    private final ArrayList<InsetsAnimationControlImpl> mTmpFinishedControls = new ArrayList();
    private final InsetsState mTmpState = new InsetsState();
    private final ViewRootImpl mViewRoot;

    public InsetsController(ViewRootImpl viewRootImpl) {
        this.TAG = "InsetsControllerImpl";
        this.mViewRoot = viewRootImpl;
        this.mAnimCallback = new _$$Lambda$InsetsController$HI9QZ2HvGm6iykc_WONz2KPG61Q(this);
    }

    private void applyAnimation(final int n, final boolean bl, boolean bl2) {
        if (n == 0) {
            return;
        }
        this.controlAnimationUnchecked(n, new WindowInsetsAnimationControlListener(){
            private ObjectAnimator mAnimator;
            private WindowInsetsAnimationController mController;

            private void onAnimationFinish() {
                Object object = InsetsController.this;
                int n2 = 0;
                ((InsetsController)object).mAnimationDirection = 0;
                object = this.mController;
                if (bl) {
                    n2 = n;
                }
                object.finish(n2);
            }

            @Override
            public void onCancelled() {
                this.mAnimator.cancel();
            }

            @Override
            public void onReady(WindowInsetsAnimationController object, int n2) {
                this.mController = object;
                if (bl) {
                    InsetsController.this.showDirectly(n2);
                } else {
                    InsetsController.this.hideDirectly(n2);
                }
                InsetsProperty insetsProperty = new InsetsProperty();
                TypeEvaluator typeEvaluator = sEvaluator;
                Insets insets = bl ? object.getHiddenStateInsets() : object.getShownStateInsets();
                Insets insets2 = bl ? object.getShownStateInsets() : object.getHiddenStateInsets();
                this.mAnimator = ObjectAnimator.ofObject(object, insetsProperty, typeEvaluator, insets, insets2);
                object = this.mAnimator;
                long l = bl ? 275L : 340L;
                ((ObjectAnimator)object).setDuration(l);
                this.mAnimator.setInterpolator(INTERPOLATOR);
                this.mAnimator.addListener(new AnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(Animator animator2) {
                        this.onAnimationFinish();
                    }
                });
                this.mAnimator.start();
            }

        }, this.mState.getDisplayFrame(), bl2);
    }

    private void applyLocalVisibilityOverride() {
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; --i) {
            this.mSourceConsumers.valueAt(i).applyLocalVisibilityOverride();
        }
    }

    private void cancelAnimation(InsetsAnimationControlImpl insetsAnimationControlImpl) {
        insetsAnimationControlImpl.onCancelled();
        this.mAnimationControls.remove(insetsAnimationControlImpl);
    }

    private void cancelExistingControllers(int n) {
        for (int i = this.mAnimationControls.size() - 1; i >= 0; --i) {
            InsetsAnimationControlImpl insetsAnimationControlImpl = this.mAnimationControls.get(i);
            if ((insetsAnimationControlImpl.getTypes() & n) == 0) continue;
            this.cancelAnimation(insetsAnimationControlImpl);
        }
    }

    private Pair<Integer, Boolean> collectConsumers(boolean bl, ArraySet<Integer> arraySet, SparseArray<InsetsSourceConsumer> sparseArray) {
        int n = 0;
        boolean bl2 = true;
        for (int i = arraySet.size() - 1; i >= 0; --i) {
            InsetsSourceConsumer insetsSourceConsumer = this.getSourceConsumer(arraySet.valueAt(i));
            int n2 = n;
            boolean bl3 = bl2;
            if (insetsSourceConsumer.getControl() != null) {
                if (!insetsSourceConsumer.isVisible()) {
                    n2 = insetsSourceConsumer.requestShow(bl);
                    if (n2 != 0) {
                        if (n2 != 1) {
                            if (n2 != 2) {
                                n2 = n;
                                bl3 = bl2;
                            } else {
                                int n3 = this.mPendingTypesToShow;
                                n2 = n;
                                bl3 = bl2;
                                if (n3 != 0) {
                                    this.mPendingTypesToShow = n3 & InsetsState.toPublicType(10);
                                    n2 = n;
                                    bl3 = bl2;
                                }
                            }
                        } else {
                            bl3 = false;
                            n2 = n;
                        }
                    } else {
                        n2 = n | InsetsState.toPublicType(insetsSourceConsumer.getType());
                        bl3 = bl2;
                    }
                    bl2 = bl3;
                } else {
                    insetsSourceConsumer.notifyHidden();
                    n2 = n | InsetsState.toPublicType(insetsSourceConsumer.getType());
                }
                sparseArray.put(insetsSourceConsumer.getType(), insetsSourceConsumer);
                bl3 = bl2;
            }
            n = n2;
            bl2 = bl3;
        }
        return new Pair<Integer, Boolean>(n, bl2);
    }

    private int collectPendingConsumers(int n, SparseArray<InsetsSourceConsumer> sparseArray) {
        int n2 = this.mPendingTypesToShow;
        int n3 = n;
        if (n2 != 0) {
            n3 = n | n2;
            Object object = this.mState;
            ArraySet<Integer> arraySet = InsetsState.toInternalType(n2);
            for (n = arraySet.size() - 1; n >= 0; --n) {
                object = this.getSourceConsumer(arraySet.valueAt(n));
                sparseArray.put(((InsetsSourceConsumer)object).getType(), (InsetsSourceConsumer)object);
            }
            this.mPendingTypesToShow = 0;
        }
        return n3;
    }

    private void controlAnimationUnchecked(int n, WindowInsetsAnimationControlListener object, Rect rect, boolean bl) {
        if (n == 0) {
            return;
        }
        this.cancelExistingControllers(n);
        Object object2 = this.mState;
        Object object3 = InsetsState.toInternalType(n);
        object2 = new SparseArray();
        object3 = this.collectConsumers(bl, (ArraySet<Integer>)object3, (SparseArray<InsetsSourceConsumer>)object2);
        n = (Integer)((Pair)object3).first;
        if (!((Boolean)((Pair)object3).second).booleanValue()) {
            this.mPendingTypesToShow = n;
            return;
        }
        if ((n = this.collectPendingConsumers(n, (SparseArray<InsetsSourceConsumer>)object2)) == 0) {
            object.onCancelled();
            return;
        }
        object = new InsetsAnimationControlImpl((SparseArray<InsetsSourceConsumer>)object2, rect, this.mState, (WindowInsetsAnimationControlListener)object, n, new _$$Lambda$InsetsController$n9dGLDW5oKSxT73i9ZlnIPWSzms(this), this);
        this.mAnimationControls.add((InsetsAnimationControlImpl)object);
    }

    private void controlWindowInsetsAnimation(int n, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, boolean bl) {
        if (!this.mState.getDisplayFrame().equals(this.mFrame)) {
            windowInsetsAnimationControlListener.onCancelled();
            return;
        }
        this.controlAnimationUnchecked(n, windowInsetsAnimationControlListener, this.mFrame, bl);
    }

    private InsetsSourceConsumer createConsumerOfType(int n) {
        if (n == 10) {
            return new ImeInsetsSourceConsumer(this.mState, (Supplier<SurfaceControl.Transaction>)_$$Lambda$9vBfnQOmNnsc9WU80IIatZHQGKc.INSTANCE, this);
        }
        return new InsetsSourceConsumer(n, this.mState, (Supplier<SurfaceControl.Transaction>)_$$Lambda$9vBfnQOmNnsc9WU80IIatZHQGKc.INSTANCE, this);
    }

    private void hideDirectly(int n) {
        ArraySet<Integer> arraySet = InsetsState.toInternalType(n);
        for (n = arraySet.size() - 1; n >= 0; --n) {
            this.getSourceConsumer(arraySet.valueAt(n)).hide();
        }
    }

    static /* synthetic */ Insets lambda$static$0(float f, Insets insets, Insets insets2) {
        return Insets.of(0, (int)((float)insets.top + (float)(insets2.top - insets.top) * f), 0, (int)((float)insets.bottom + (float)(insets2.bottom - insets.bottom) * f));
    }

    private void sendStateToWindowManager() {
        InsetsState insetsState = new InsetsState();
        for (int i = this.mSourceConsumers.size() - 1; i >= 0; --i) {
            InsetsSourceConsumer insetsSourceConsumer = this.mSourceConsumers.valueAt(i);
            if (insetsSourceConsumer.getControl() == null) continue;
            insetsState.addSource(this.mState.getSource(insetsSourceConsumer.getType()));
        }
        try {
            this.mViewRoot.mWindowSession.insetsModified(this.mViewRoot.mWindow, insetsState);
        }
        catch (RemoteException remoteException) {
            Log.e("InsetsControllerImpl", "Failed to call insetsModified", remoteException);
        }
    }

    /*
     * Exception decompiling
     */
    private void show(int var1_1, boolean var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:414)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:226)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:646)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:52)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:580)
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

    private void showDirectly(int n) {
        ArraySet<Integer> arraySet = InsetsState.toInternalType(n);
        for (n = arraySet.size() - 1; n >= 0; --n) {
            this.getSourceConsumer(arraySet.valueAt(n)).show();
        }
    }

    @VisibleForTesting
    public void applyImeVisibility(boolean bl) {
        if (bl) {
            this.show(2, true);
        } else {
            this.hide(2);
        }
    }

    @VisibleForTesting
    public WindowInsets calculateInsets(boolean bl, boolean bl2, DisplayCutout displayCutout, Rect rect, Rect rect2, int n) {
        this.mLastLegacyContentInsets.set(rect);
        this.mLastLegacyStableInsets.set(rect2);
        this.mLastLegacySoftInputMode = n;
        this.mLastInsets = this.mState.calculateInsets(this.mFrame, bl, bl2, displayCutout, rect, rect2, n, null);
        return this.mLastInsets;
    }

    @VisibleForTesting
    public void cancelExistingAnimation() {
        this.cancelExistingControllers(WindowInsets.Type.all());
    }

    @Override
    public void controlWindowInsetsAnimation(int n, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        this.controlWindowInsetsAnimation(n, windowInsetsAnimationControlListener, false);
    }

    @VisibleForTesting
    public void dispatchAnimationFinished(WindowInsetsAnimationListener.InsetsAnimation insetsAnimation) {
        this.mViewRoot.mView.dispatchWindowInsetsAnimationFinished(insetsAnimation);
    }

    @VisibleForTesting
    public void dispatchAnimationStarted(WindowInsetsAnimationListener.InsetsAnimation insetsAnimation) {
        this.mViewRoot.mView.dispatchWindowInsetsAnimationStarted(insetsAnimation);
    }

    void dump(String string2, PrintWriter printWriter) {
        printWriter.println(string2);
        printWriter.println("InsetsController:");
        InsetsState insetsState = this.mState;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        insetsState.dump(stringBuilder.toString(), printWriter);
    }

    @VisibleForTesting
    public InsetsSourceConsumer getSourceConsumer(int n) {
        InsetsSourceConsumer insetsSourceConsumer = this.mSourceConsumers.get(n);
        if (insetsSourceConsumer != null) {
            return insetsSourceConsumer;
        }
        insetsSourceConsumer = this.createConsumerOfType(n);
        this.mSourceConsumers.put(n, insetsSourceConsumer);
        return insetsSourceConsumer;
    }

    public InsetsState getState() {
        return this.mState;
    }

    ViewRootImpl getViewRoot() {
        return this.mViewRoot;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void hide(int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:414)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:226)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:646)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:52)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:580)
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

    public /* synthetic */ SyncRtSurfaceTransactionApplier lambda$controlAnimationUnchecked$2$InsetsController() {
        return new SyncRtSurfaceTransactionApplier(this.mViewRoot.mView);
    }

    public /* synthetic */ void lambda$new$1$InsetsController() {
        int n;
        this.mAnimCallbackScheduled = false;
        if (this.mAnimationControls.isEmpty()) {
            return;
        }
        this.mTmpFinishedControls.clear();
        Object object = new InsetsState(this.mState, true);
        for (n = this.mAnimationControls.size() - 1; n >= 0; --n) {
            InsetsAnimationControlImpl insetsAnimationControlImpl = this.mAnimationControls.get(n);
            if (!this.mAnimationControls.get(n).applyChangeInsets((InsetsState)object)) continue;
            this.mTmpFinishedControls.add(insetsAnimationControlImpl);
        }
        object = ((InsetsState)object).calculateInsets(this.mFrame, this.mLastInsets.isRound(), this.mLastInsets.shouldAlwaysConsumeSystemBars(), this.mLastInsets.getDisplayCutout(), this.mLastLegacyContentInsets, this.mLastLegacyStableInsets, this.mLastLegacySoftInputMode, null);
        this.mViewRoot.mView.dispatchWindowInsetsAnimationProgress((WindowInsets)object);
        for (n = this.mTmpFinishedControls.size() - 1; n >= 0; --n) {
            this.dispatchAnimationFinished(this.mTmpFinishedControls.get(n).getAnimation());
        }
    }

    void notifyControlRevoked(InsetsSourceConsumer insetsSourceConsumer) {
        for (int i = this.mAnimationControls.size() - 1; i >= 0; --i) {
            InsetsAnimationControlImpl insetsAnimationControlImpl = this.mAnimationControls.get(i);
            if ((insetsAnimationControlImpl.getTypes() & InsetsState.toPublicType(insetsSourceConsumer.getType())) == 0) continue;
            this.cancelAnimation(insetsAnimationControlImpl);
        }
    }

    @VisibleForTesting
    public void notifyFinished(InsetsAnimationControlImpl insetsAnimationControlImpl, int n) {
        this.mAnimationControls.remove(insetsAnimationControlImpl);
        this.hideDirectly(insetsAnimationControlImpl.getTypes() & n);
        this.showDirectly(insetsAnimationControlImpl.getTypes() & n);
    }

    @VisibleForTesting
    public void notifyVisibilityChanged() {
        this.mViewRoot.notifyInsetsChanged();
        this.sendStateToWindowManager();
    }

    public void onControlsChanged(InsetsSourceControl[] object) {
        int n;
        if (object != null) {
            int n2 = ((InsetsSourceControl[])object).length;
            for (n = 0; n < n2; ++n) {
                InsetsSourceControl insetsSourceControl = object[n];
                if (insetsSourceControl == null) continue;
                this.mTmpControlArray.put(insetsSourceControl.getType(), insetsSourceControl);
            }
        }
        for (n = this.mSourceConsumers.size() - 1; n >= 0; --n) {
            object = this.mSourceConsumers.valueAt(n);
            ((InsetsSourceConsumer)object).setControl(this.mTmpControlArray.get(((InsetsSourceConsumer)object).getType()));
        }
        for (n = this.mTmpControlArray.size() - 1; n >= 0; --n) {
            object = this.mTmpControlArray.valueAt(n);
            this.getSourceConsumer(((InsetsSourceControl)object).getType()).setControl((InsetsSourceControl)object);
        }
        this.mTmpControlArray.clear();
    }

    @VisibleForTesting
    public void onFrameChanged(Rect rect) {
        if (this.mFrame.equals(rect)) {
            return;
        }
        this.mViewRoot.notifyInsetsChanged();
        this.mFrame.set(rect);
    }

    boolean onStateChanged(InsetsState insetsState) {
        if (this.mState.equals(insetsState)) {
            return false;
        }
        this.mState.set(insetsState);
        this.mTmpState.set(insetsState, true);
        this.applyLocalVisibilityOverride();
        this.mViewRoot.notifyInsetsChanged();
        if (!this.mState.equals(this.mTmpState)) {
            this.sendStateToWindowManager();
        }
        return true;
    }

    public void onWindowFocusGained() {
        this.getSourceConsumer(10).onWindowFocusGained();
    }

    public void onWindowFocusLost() {
        this.getSourceConsumer(10).onWindowFocusLost();
    }

    @VisibleForTesting
    public void scheduleApplyChangeInsets() {
        if (!this.mAnimCallbackScheduled) {
            this.mViewRoot.mChoreographer.postCallback(2, this.mAnimCallback, null);
            this.mAnimCallbackScheduled = true;
        }
    }

    @Override
    public void show(int n) {
        this.show(n, false);
    }

    private static @interface AnimationDirection {
    }

    private static class InsetsProperty
    extends Property<WindowInsetsAnimationController, Insets> {
        InsetsProperty() {
            super(Insets.class, "Insets");
        }

        @Override
        public Insets get(WindowInsetsAnimationController windowInsetsAnimationController) {
            return windowInsetsAnimationController.getCurrentInsets();
        }

        @Override
        public void set(WindowInsetsAnimationController windowInsetsAnimationController, Insets insets) {
            windowInsetsAnimationController.changeInsets(insets);
        }
    }

}

