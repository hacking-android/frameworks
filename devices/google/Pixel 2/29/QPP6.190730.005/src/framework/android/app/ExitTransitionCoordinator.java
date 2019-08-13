/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityTransitionCoordinator;
import android.app.ActivityTransitionState;
import android.app.SharedElementCallback;
import android.app._$$Lambda$ExitTransitionCoordinator$QSAvMs76ZWnO0eiLyXWkcGxkRIY;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ExitTransitionCoordinator
extends ActivityTransitionCoordinator {
    private static final long MAX_WAIT_MS = 1000L;
    private static final String TAG = "ExitTransitionCoordinator";
    private Activity mActivity;
    private ObjectAnimator mBackgroundAnimator;
    private boolean mExitNotified;
    private Bundle mExitSharedElementBundle;
    private Handler mHandler;
    private HideSharedElementsCallback mHideSharedElementsCallback;
    private boolean mIsBackgroundReady;
    private boolean mIsCanceled;
    private boolean mIsExitStarted;
    private boolean mIsHidden;
    private Bundle mSharedElementBundle;
    private boolean mSharedElementNotified;
    private boolean mSharedElementsHidden;

    public ExitTransitionCoordinator(Activity activity, Window window, SharedElementCallback sharedElementCallback, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<View> arrayList3, boolean bl) {
        super(window, arrayList, sharedElementCallback, bl);
        this.viewsReady(this.mapSharedElements(arrayList2, arrayList3));
        this.stripOffscreenViews();
        this.mIsBackgroundReady = bl ^ true;
        this.mActivity = activity;
    }

    private void beginTransitions() {
        Object object = this.getSharedElementExitTransition();
        Transition transition2 = this.getExitTransition();
        Transition transition3 = ExitTransitionCoordinator.mergeTransitions((Transition)object, transition2);
        object = this.getDecor();
        if (transition3 != null && object != null) {
            this.setGhostVisibility(4);
            this.scheduleGhostVisibilityChange(4);
            if (transition2 != null) {
                this.setTransitioningViewsVisiblity(0, false);
            }
            TransitionManager.beginDelayedTransition((ViewGroup)object, transition3);
            this.scheduleGhostVisibilityChange(0);
            this.setGhostVisibility(0);
            if (transition2 != null) {
                this.setTransitioningViewsVisiblity(4, false);
            }
            ((View)object).invalidate();
        } else {
            this.transitionStarted();
        }
    }

    private Bundle captureExitSharedElementsState() {
        Bundle bundle = new Bundle();
        RectF rectF = new RectF();
        Matrix matrix = new Matrix();
        for (int i = 0; i < this.mSharedElements.size(); ++i) {
            String string2 = (String)this.mSharedElementNames.get(i);
            Bundle bundle2 = this.mExitSharedElementBundle.getBundle(string2);
            if (bundle2 != null) {
                bundle.putBundle(string2, bundle2);
                continue;
            }
            this.captureSharedElementState((View)this.mSharedElements.get(i), string2, bundle, matrix, rectF);
        }
        return bundle;
    }

    private void delayCancel() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(106, 1000L);
        }
    }

    private void fadeOutBackground() {
        if (this.mBackgroundAnimator == null) {
            Object object = this.getDecor();
            if (object != null && (object = ((View)object).getBackground()) != null) {
                object = ((Drawable)object).mutate();
                this.getWindow().setBackgroundDrawable((Drawable)object);
                this.mBackgroundAnimator = ObjectAnimator.ofInt(object, "alpha", 0);
                this.mBackgroundAnimator.addListener(new AnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(Animator animator2) {
                        ExitTransitionCoordinator.this.mBackgroundAnimator = null;
                        if (!ExitTransitionCoordinator.this.mIsCanceled) {
                            ExitTransitionCoordinator.this.mIsBackgroundReady = true;
                            ExitTransitionCoordinator.this.notifyComplete();
                        }
                        ExitTransitionCoordinator.this.backgroundAnimatorComplete();
                    }
                });
                this.mBackgroundAnimator.setDuration(this.getFadeDuration());
                this.mBackgroundAnimator.start();
            } else {
                this.backgroundAnimatorComplete();
                this.mIsBackgroundReady = true;
            }
        }
    }

    private void finish() {
        this.stopCancel();
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.mActivityTransitionState.clear();
            this.mActivity.finish();
            this.mActivity.overridePendingTransition(0, 0);
            this.mActivity = null;
        }
        this.clearState();
    }

    private void finishIfNecessary() {
        if (this.mIsReturning && this.mExitNotified && this.mActivity != null && (this.mSharedElements.isEmpty() || this.mSharedElementsHidden)) {
            this.finish();
        }
        if (!this.mIsReturning && this.mExitNotified) {
            this.mActivity = null;
        }
    }

    private Transition getExitTransition() {
        Transition transition2;
        Transition transition3 = transition2 = null;
        if (this.mTransitioningViews != null) {
            transition3 = transition2;
            if (!this.mTransitioningViews.isEmpty()) {
                transition3 = this.configureTransition(this.getViewsTransition(), true);
                ExitTransitionCoordinator.removeExcludedViews(transition3, this.mTransitioningViews);
                if (this.mTransitioningViews.isEmpty()) {
                    transition3 = null;
                }
            }
        }
        if (transition3 == null) {
            this.viewsTransitionComplete();
        } else {
            transition3.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener(){

                @Override
                public void onTransitionEnd(Transition transition2) {
                    ArrayList arrayList;
                    ExitTransitionCoordinator.this.viewsTransitionComplete();
                    if (ExitTransitionCoordinator.this.mIsHidden && (arrayList = mTransitioningViews) != null) {
                        ExitTransitionCoordinator.this.showViews(arrayList, true);
                        ExitTransitionCoordinator.this.setTransitioningViewsVisiblity(0, true);
                    }
                    if (ExitTransitionCoordinator.this.mSharedElementBundle != null) {
                        ExitTransitionCoordinator.this.delayCancel();
                    }
                    super.onTransitionEnd(transition2);
                }
            });
        }
        return transition3;
    }

    private Transition getSharedElementExitTransition() {
        Transition transition2 = null;
        if (!this.mSharedElements.isEmpty()) {
            transition2 = this.configureTransition(this.getSharedElementTransition(), false);
        }
        if (transition2 == null) {
            this.sharedElementTransitionComplete();
        } else {
            transition2.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener(){

                @Override
                public void onTransitionEnd(Transition transition2) {
                    ExitTransitionCoordinator.this.sharedElementTransitionComplete();
                    if (ExitTransitionCoordinator.this.mIsHidden) {
                        ExitTransitionCoordinator exitTransitionCoordinator = ExitTransitionCoordinator.this;
                        exitTransitionCoordinator.showViews(exitTransitionCoordinator.mSharedElements, true);
                    }
                    super.onTransitionEnd(transition2);
                }
            });
            ((View)this.mSharedElements.get(0)).invalidate();
        }
        return transition2;
    }

    private void hideSharedElements() {
        this.moveSharedElementsFromOverlay();
        HideSharedElementsCallback hideSharedElementsCallback = this.mHideSharedElementsCallback;
        if (hideSharedElementsCallback != null) {
            hideSharedElementsCallback.hideSharedElements();
        }
        if (!this.mIsHidden) {
            this.hideViews(this.mSharedElements);
        }
        this.mSharedElementsHidden = true;
        this.finishIfNecessary();
    }

    private void notifyExitComplete() {
        if (!this.mExitNotified && this.isViewsTransitionComplete()) {
            this.mExitNotified = true;
            this.mResultReceiver.send(104, null);
            this.mResultReceiver = null;
            ViewGroup viewGroup = this.getDecor();
            if (!this.mIsReturning && viewGroup != null) {
                viewGroup.suppressLayout(false);
            }
            this.finishIfNecessary();
        }
    }

    private void sharedElementExitBack() {
        Bundle bundle;
        final ViewGroup viewGroup = this.getDecor();
        if (viewGroup != null) {
            viewGroup.suppressLayout(true);
        }
        if (viewGroup != null && (bundle = this.mExitSharedElementBundle) != null && !bundle.isEmpty() && !this.mSharedElements.isEmpty() && this.getSharedElementTransition() != null) {
            this.startTransition(new Runnable(){

                @Override
                public void run() {
                    ExitTransitionCoordinator.this.startSharedElementExit(viewGroup);
                }
            });
        } else {
            this.sharedElementTransitionComplete();
        }
    }

    private void startExitTransition() {
        Transition transition2 = this.getExitTransition();
        ViewGroup viewGroup = this.getDecor();
        if (transition2 != null && viewGroup != null && this.mTransitioningViews != null) {
            this.setTransitioningViewsVisiblity(0, false);
            TransitionManager.beginDelayedTransition(viewGroup, transition2);
            this.setTransitioningViewsVisiblity(4, false);
            viewGroup.invalidate();
        } else {
            this.transitionStarted();
        }
    }

    private void startSharedElementExit(ViewGroup viewGroup) {
        Transition transition2 = this.getSharedElementExitTransition();
        transition2.addListener(new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(Transition transition2) {
                transition2.removeListener(this);
                if (ExitTransitionCoordinator.this.isViewsTransitionComplete()) {
                    ExitTransitionCoordinator.this.delayCancel();
                }
            }
        });
        ArrayList<View> arrayList = this.createSnapshots(this.mExitSharedElementBundle, this.mSharedElementNames);
        OneShotPreDrawListener.add(viewGroup, new _$$Lambda$ExitTransitionCoordinator$QSAvMs76ZWnO0eiLyXWkcGxkRIY(this, arrayList));
        this.setGhostVisibility(4);
        this.scheduleGhostVisibilityChange(4);
        if (this.mListener != null) {
            this.mListener.onSharedElementEnd(this.mSharedElementNames, this.mSharedElements, arrayList);
        }
        TransitionManager.beginDelayedTransition(viewGroup, transition2);
        this.scheduleGhostVisibilityChange(0);
        this.setGhostVisibility(0);
        viewGroup.invalidate();
    }

    private void stopCancel() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(106);
        }
    }

    @Override
    protected void clearState() {
        this.mHandler = null;
        this.mSharedElementBundle = null;
        ObjectAnimator objectAnimator = this.mBackgroundAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mBackgroundAnimator = null;
        }
        this.mExitSharedElementBundle = null;
        super.clearState();
    }

    protected Transition getSharedElementTransition() {
        if (this.mIsReturning) {
            return this.getWindow().getSharedElementReturnTransition();
        }
        return this.getWindow().getSharedElementExitTransition();
    }

    @Override
    protected Transition getViewsTransition() {
        if (this.mIsReturning) {
            return this.getWindow().getReturnTransition();
        }
        return this.getWindow().getExitTransition();
    }

    protected boolean isReadyToNotify() {
        boolean bl = this.mSharedElementBundle != null && this.mResultReceiver != null && this.mIsBackgroundReady;
        return bl;
    }

    public /* synthetic */ void lambda$startSharedElementExit$0$ExitTransitionCoordinator(ArrayList arrayList) {
        this.setSharedElementState(this.mExitSharedElementBundle, arrayList);
    }

    @Override
    protected boolean moveSharedElementWithParent() {
        return this.mIsReturning ^ true;
    }

    protected void notifyComplete() {
        if (this.isReadyToNotify()) {
            if (!this.mSharedElementNotified) {
                this.mSharedElementNotified = true;
                this.delayCancel();
                if (!this.mActivity.isTopOfTask()) {
                    this.mResultReceiver.send(108, null);
                }
                if (this.mListener == null) {
                    this.mResultReceiver.send(103, this.mSharedElementBundle);
                    this.notifyExitComplete();
                } else {
                    final ResultReceiver resultReceiver = this.mResultReceiver;
                    final Bundle bundle = this.mSharedElementBundle;
                    this.mListener.onSharedElementsArrived(this.mSharedElementNames, this.mSharedElements, new SharedElementCallback.OnSharedElementsReadyListener(){

                        @Override
                        public void onSharedElementsReady() {
                            resultReceiver.send(103, bundle);
                            ExitTransitionCoordinator.this.notifyExitComplete();
                        }
                    });
                }
            } else {
                this.notifyExitComplete();
            }
        }
    }

    @Override
    protected void onReceiveResult(int n, Bundle bundle) {
        if (n != 100) {
            if (n != 101) {
                switch (n) {
                    default: {
                        break;
                    }
                    case 107: {
                        this.mExitSharedElementBundle = bundle;
                        this.sharedElementExitBack();
                        break;
                    }
                    case 106: {
                        this.mIsCanceled = true;
                        this.finish();
                        break;
                    }
                    case 105: {
                        this.mHandler.removeMessages(106);
                        this.startExit();
                        break;
                    }
                }
            } else {
                this.stopCancel();
                if (!this.mIsCanceled) {
                    this.hideSharedElements();
                }
            }
        } else {
            this.stopCancel();
            this.mResultReceiver = (ResultReceiver)bundle.getParcelable("android:remoteReceiver");
            if (this.mIsCanceled) {
                this.mResultReceiver.send(106, null);
                this.mResultReceiver = null;
            } else {
                this.notifyComplete();
            }
        }
    }

    @Override
    protected void onTransitionsComplete() {
        this.notifyComplete();
    }

    public void resetViews() {
        ViewGroup viewGroup = this.getDecor();
        if (viewGroup != null) {
            TransitionManager.endTransitions(viewGroup);
        }
        if (this.mTransitioningViews != null) {
            this.showViews(this.mTransitioningViews, true);
            this.setTransitioningViewsVisiblity(0, true);
        }
        this.showViews(this.mSharedElements, true);
        this.mIsHidden = true;
        if (!this.mIsReturning && viewGroup != null) {
            viewGroup.suppressLayout(false);
        }
        this.moveSharedElementsFromOverlay();
        this.clearState();
    }

    void setHideSharedElementsCallback(HideSharedElementsCallback hideSharedElementsCallback) {
        this.mHideSharedElementsCallback = hideSharedElementsCallback;
    }

    @Override
    protected void sharedElementTransitionComplete() {
        Bundle bundle = this.mExitSharedElementBundle == null ? this.captureSharedElementState() : this.captureExitSharedElementsState();
        this.mSharedElementBundle = bundle;
        super.sharedElementTransitionComplete();
    }

    public void startExit() {
        if (!this.mIsExitStarted) {
            this.backgroundAnimatorComplete();
            this.mIsExitStarted = true;
            this.pauseInput();
            ViewGroup viewGroup = this.getDecor();
            if (viewGroup != null) {
                viewGroup.suppressLayout(true);
            }
            this.moveSharedElementsToOverlay();
            this.startTransition(new Runnable(){

                @Override
                public void run() {
                    if (ExitTransitionCoordinator.this.mActivity != null) {
                        ExitTransitionCoordinator.this.beginTransitions();
                    } else {
                        ExitTransitionCoordinator.this.startExitTransition();
                    }
                }
            });
        }
    }

    public void startExit(int n, Intent object) {
        if (!this.mIsExitStarted) {
            boolean bl = true;
            this.mIsExitStarted = true;
            this.pauseInput();
            Object object2 = this.getDecor();
            if (object2 != null) {
                ((ViewGroup)object2).suppressLayout(true);
            }
            this.mHandler = new Handler(){

                @Override
                public void handleMessage(Message message) {
                    ExitTransitionCoordinator.this.mIsCanceled = true;
                    ExitTransitionCoordinator.this.finish();
                }
            };
            this.delayCancel();
            this.moveSharedElementsToOverlay();
            if (object2 != null && ((View)object2).getBackground() == null) {
                this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            if (object2 != null && object2.getContext().getApplicationInfo().targetSdkVersion < 23) {
                bl = false;
            }
            object2 = bl ? this.mSharedElementNames : this.mAllSharedElementNames;
            object = ActivityOptions.makeSceneTransitionAnimation(this.mActivity, this, (ArrayList<String>)object2, n, (Intent)object);
            this.mActivity.convertToTranslucent(new Activity.TranslucentConversionListener(){

                @Override
                public void onTranslucentConversionComplete(boolean bl) {
                    if (!ExitTransitionCoordinator.this.mIsCanceled) {
                        ExitTransitionCoordinator.this.fadeOutBackground();
                    }
                }
            }, (ActivityOptions)object);
            this.startTransition(new Runnable(){

                @Override
                public void run() {
                    ExitTransitionCoordinator.this.startExitTransition();
                }
            });
        }
    }

    public void stop() {
        Activity activity;
        if (this.mIsReturning && (activity = this.mActivity) != null) {
            activity.convertToTranslucent(null, null);
            this.finish();
        }
    }

    static interface HideSharedElementsCallback {
        public void hideSharedElements();
    }

}

