/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityTransitionCoordinator;
import android.app.SharedElementCallback;
import android.app._$$Lambda$EnterTransitionCoordinator$3$I_t9rJUkrW7bwRLQtTrE8DgvPZs;
import android.app._$$Lambda$EnterTransitionCoordinator$3$bzpzcEqxdHzyaWu6Gq6AOD9dFMo;
import android.app._$$Lambda$EnterTransitionCoordinator$dV8bqDBqB_WsCnMyvajWuP4ArwA;
import android.app._$$Lambda$EnterTransitionCoordinator$wYWFlx9zS3bxJYkN44Bpwx_EKis;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class EnterTransitionCoordinator
extends ActivityTransitionCoordinator {
    private static final int MIN_ANIMATION_FRAMES = 2;
    private static final String TAG = "EnterTransitionCoordinator";
    private Activity mActivity;
    private boolean mAreViewsReady;
    private ObjectAnimator mBackgroundAnimator;
    private Transition mEnterViewsTransition;
    private boolean mHasStopped;
    private boolean mIsCanceled;
    private final boolean mIsCrossTask;
    private boolean mIsExitTransitionComplete;
    private boolean mIsReadyForTransition;
    private boolean mIsViewsTransitionStarted;
    private ArrayList<String> mPendingExitNames;
    private Drawable mReplacedBackground;
    private boolean mSharedElementTransitionStarted;
    private Bundle mSharedElementsBundle;
    private OneShotPreDrawListener mViewsReadyListener;
    private boolean mWasOpaque;

    public EnterTransitionCoordinator(Activity object, ResultReceiver object2, ArrayList<String> arrayList, boolean bl, boolean bl2) {
        Window window = ((Activity)object).getWindow();
        boolean bl3 = bl && !bl2;
        super(window, arrayList, EnterTransitionCoordinator.getListener((Activity)object, bl3), bl);
        this.mActivity = object;
        this.mIsCrossTask = bl2;
        this.setResultReceiver((ResultReceiver)object2);
        this.prepareEnter();
        object = new Bundle();
        ((Bundle)object).putParcelable("android:remoteReceiver", this);
        this.mResultReceiver.send(100, (Bundle)object);
        object2 = this.getDecor();
        if (object2 != null) {
            object = ((View)object2).getViewTreeObserver();
            ((ViewTreeObserver)object).addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener((ViewTreeObserver)object, (View)object2){
                final /* synthetic */ View val$decorView;
                final /* synthetic */ ViewTreeObserver val$viewTreeObserver;
                {
                    this.val$viewTreeObserver = viewTreeObserver;
                    this.val$decorView = view;
                }

                @Override
                public boolean onPreDraw() {
                    if (EnterTransitionCoordinator.this.mIsReadyForTransition) {
                        if (this.val$viewTreeObserver.isAlive()) {
                            this.val$viewTreeObserver.removeOnPreDrawListener(this);
                        } else {
                            this.val$decorView.getViewTreeObserver().removeOnPreDrawListener(this);
                        }
                    }
                    return false;
                }
            });
        }
    }

    private boolean allowOverlappingTransitions() {
        boolean bl = this.mIsReturning ? this.getWindow().getAllowReturnTransitionOverlap() : this.getWindow().getAllowEnterTransitionOverlap();
        return bl;
    }

    private Transition beginTransition(ViewGroup viewGroup, boolean bl, boolean bl2) {
        Transition transition2 = null;
        Transition transition3 = null;
        if (bl2) {
            if (!this.mSharedElementNames.isEmpty()) {
                transition3 = this.configureTransition(this.getSharedElementTransition(), false);
            }
            if (transition3 == null) {
                this.sharedElementTransitionStarted();
                this.sharedElementTransitionComplete();
                transition2 = transition3;
            } else {
                transition3.addListener(new TransitionListenerAdapter(){

                    @Override
                    public void onTransitionEnd(Transition transition2) {
                        transition2.removeListener(this);
                        EnterTransitionCoordinator.this.sharedElementTransitionComplete();
                    }

                    @Override
                    public void onTransitionStart(Transition transition2) {
                        EnterTransitionCoordinator.this.sharedElementTransitionStarted();
                    }
                });
                transition2 = transition3;
            }
        }
        transition3 = null;
        Object var6_6 = null;
        if (bl) {
            this.mIsViewsTransitionStarted = true;
            transition3 = var6_6;
            if (this.mTransitioningViews != null) {
                transition3 = var6_6;
                if (!this.mTransitioningViews.isEmpty()) {
                    transition3 = this.configureTransition(this.getViewsTransition(), true);
                }
            }
            if (transition3 == null) {
                this.viewsTransitionComplete();
            } else {
                transition3.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener(){

                    @Override
                    public void onTransitionEnd(Transition transition2) {
                        EnterTransitionCoordinator.this.mEnterViewsTransition = null;
                        transition2.removeListener(this);
                        EnterTransitionCoordinator.this.viewsTransitionComplete();
                        super.onTransitionEnd(transition2);
                    }

                    @Override
                    public void onTransitionStart(Transition transition2) {
                        EnterTransitionCoordinator.this.mEnterViewsTransition = transition2;
                        ArrayList arrayList = mTransitioningViews;
                        if (arrayList != null) {
                            EnterTransitionCoordinator.this.showViews(arrayList, false);
                        }
                        super.onTransitionStart(transition2);
                    }
                });
            }
        }
        if ((transition3 = EnterTransitionCoordinator.mergeTransitions(transition2, transition3)) != null) {
            transition3.addListener(new ActivityTransitionCoordinator.ContinueTransitionListener());
            if (bl) {
                this.setTransitioningViewsVisiblity(4, false);
            }
            TransitionManager.beginDelayedTransition(viewGroup, transition3);
            if (bl) {
                this.setTransitioningViewsVisiblity(0, false);
            }
            viewGroup.invalidate();
        } else {
            this.transitionStarted();
        }
        return transition3;
    }

    private void cancel() {
        if (!this.mIsCanceled) {
            this.mIsCanceled = true;
            if (this.getViewsTransition() != null && !this.mIsViewsTransitionStarted) {
                if (this.mTransitioningViews != null) {
                    this.mTransitioningViews.addAll(this.mSharedElements);
                }
            } else {
                this.showViews(this.mSharedElements, true);
            }
            this.moveSharedElementsFromOverlay();
            this.mSharedElementNames.clear();
            this.mSharedElements.clear();
            this.mAllSharedElementNames.clear();
            this.startSharedElementTransition(null);
            this.onRemoteExitTransitionComplete();
        }
    }

    private static SharedElementCallback getListener(Activity object, boolean bl) {
        object = bl ? ((Activity)object).mExitTransitionListener : ((Activity)object).mEnterTransitionListener;
        return object;
    }

    private void makeOpaque() {
        Activity activity;
        if (!this.mHasStopped && (activity = this.mActivity) != null) {
            if (this.mWasOpaque) {
                activity.convertFromTranslucent();
            }
            this.mActivity = null;
        }
    }

    private ArrayMap<String, View> mapNamedElements(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        ArrayMap<String, View> arrayMap = new ArrayMap<String, View>();
        Object object = this.getDecor();
        if (object != null) {
            ((ViewGroup)object).findNamedViews(arrayMap);
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList2.size(); ++i) {
                Object object2 = arrayList2.get(i);
                object = arrayList.get(i);
                if (object2 == null || ((String)object2).equals(object) || (object2 = arrayMap.get(object2)) == null) continue;
                arrayMap.put((String)object, (View)object2);
            }
        }
        return arrayMap;
    }

    private void onTakeSharedElements() {
        if (this.mIsReadyForTransition && this.mSharedElementsBundle != null) {
            Object object = this.mSharedElementsBundle;
            this.mSharedElementsBundle = null;
            object = new SharedElementCallback.OnSharedElementsReadyListener((Bundle)object){
                final /* synthetic */ Bundle val$sharedElementState;
                {
                    this.val$sharedElementState = bundle;
                }

                public /* synthetic */ void lambda$onSharedElementsReady$0$EnterTransitionCoordinator$3(Bundle bundle) {
                    EnterTransitionCoordinator.this.startSharedElementTransition(bundle);
                }

                public /* synthetic */ void lambda$onSharedElementsReady$1$EnterTransitionCoordinator$3(Bundle bundle) {
                    EnterTransitionCoordinator.this.startTransition(new _$$Lambda$EnterTransitionCoordinator$3$bzpzcEqxdHzyaWu6Gq6AOD9dFMo(this, bundle));
                }

                @Override
                public void onSharedElementsReady() {
                    ViewGroup viewGroup = EnterTransitionCoordinator.this.getDecor();
                    if (viewGroup != null) {
                        OneShotPreDrawListener.add(viewGroup, false, new _$$Lambda$EnterTransitionCoordinator$3$I_t9rJUkrW7bwRLQtTrE8DgvPZs(this, this.val$sharedElementState));
                        viewGroup.invalidate();
                    }
                }
            };
            if (this.mListener == null) {
                object.onSharedElementsReady();
            } else {
                this.mListener.onSharedElementsArrived(this.mSharedElementNames, this.mSharedElements, (SharedElementCallback.OnSharedElementsReadyListener)object);
            }
            return;
        }
    }

    private static void removeNullViews(ArrayList<View> arrayList) {
        if (arrayList != null) {
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                if (arrayList.get(i) != null) continue;
                arrayList.remove(i);
            }
        }
    }

    private void requestLayoutForSharedElements() {
        int n = this.mSharedElements.size();
        for (int i = 0; i < n; ++i) {
            ((View)this.mSharedElements.get(i)).requestLayout();
        }
    }

    private void sendSharedElementDestination() {
        boolean bl;
        Object object = this.getDecor();
        if (this.allowOverlappingTransitions() && this.getEnterViewsTransition() != null) {
            bl = false;
        } else if (object == null) {
            bl = true;
        } else {
            boolean bl2;
            bl = bl2 = ((View)object).isLayoutRequested() ^ true;
            if (bl2) {
                int n = 0;
                do {
                    bl = bl2;
                    if (n >= this.mSharedElements.size()) break;
                    if (((View)this.mSharedElements.get(n)).isLayoutRequested()) {
                        bl = false;
                        break;
                    }
                    ++n;
                } while (true);
            }
        }
        if (bl) {
            object = this.captureSharedElementState();
            this.moveSharedElementsToOverlay();
            this.mResultReceiver.send(107, (Bundle)object);
        } else if (object != null) {
            OneShotPreDrawListener.add((View)object, new _$$Lambda$EnterTransitionCoordinator$dV8bqDBqB_WsCnMyvajWuP4ArwA(this));
        }
        if (this.allowOverlappingTransitions()) {
            this.startEnterTransitionOnly();
        }
    }

    private void sharedElementTransitionStarted() {
        this.mSharedElementTransitionStarted = true;
        if (this.mIsExitTransitionComplete) {
            this.send(104, null);
        }
    }

    private void startEnterTransition(Transition object) {
        Object object2 = this.getDecor();
        if (!this.mIsReturning && object2 != null) {
            if ((object2 = ((View)object2).getBackground()) != null) {
                object = ((Drawable)object2).mutate();
                this.getWindow().setBackgroundDrawable((Drawable)object);
                this.mBackgroundAnimator = ObjectAnimator.ofInt(object, "alpha", 255);
                this.mBackgroundAnimator.setDuration(this.getFadeDuration());
                this.mBackgroundAnimator.addListener(new AnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(Animator animator2) {
                        EnterTransitionCoordinator.this.makeOpaque();
                        EnterTransitionCoordinator.this.backgroundAnimatorComplete();
                    }
                });
                this.mBackgroundAnimator.start();
            } else if (object != null) {
                ((Transition)object).addListener(new TransitionListenerAdapter(){

                    @Override
                    public void onTransitionEnd(Transition transition2) {
                        transition2.removeListener(this);
                        EnterTransitionCoordinator.this.makeOpaque();
                    }
                });
                this.backgroundAnimatorComplete();
            } else {
                this.makeOpaque();
                this.backgroundAnimatorComplete();
            }
        } else {
            this.backgroundAnimatorComplete();
        }
    }

    private void startEnterTransitionOnly() {
        this.startTransition(new Runnable(){

            @Override
            public void run() {
                Object object = EnterTransitionCoordinator.this.getDecor();
                if (object != null) {
                    object = EnterTransitionCoordinator.this.beginTransition((ViewGroup)object, true, false);
                    EnterTransitionCoordinator.this.startEnterTransition((Transition)object);
                }
            }
        });
    }

    private void startRejectedAnimations(final ArrayList<View> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            final ViewGroup viewGroup = this.getDecor();
            if (viewGroup != null) {
                ViewGroupOverlay viewGroupOverlay = viewGroup.getOverlay();
                Object object = null;
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    object = arrayList.get(i);
                    viewGroupOverlay.add((View)object);
                    object = ObjectAnimator.ofFloat(object, View.ALPHA, 1.0f, 0.0f);
                    ((ObjectAnimator)object).start();
                }
                ((Animator)object).addListener(new AnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(Animator object) {
                        object = viewGroup.getOverlay();
                        int n = arrayList.size();
                        for (int i = 0; i < n; ++i) {
                            ((ViewGroupOverlay)object).remove((View)arrayList.get(i));
                        }
                    }
                });
            }
            return;
        }
    }

    private void startSharedElementTransition(Bundle cloneable) {
        ViewGroup viewGroup = this.getDecor();
        if (viewGroup == null) {
            return;
        }
        Cloneable cloneable2 = new ArrayList<String>(this.mAllSharedElementNames);
        cloneable2.removeAll(this.mSharedElementNames);
        cloneable2 = this.createSnapshots((Bundle)cloneable, (Collection<String>)((Object)cloneable2));
        if (this.mListener != null) {
            this.mListener.onRejectSharedElements((List<View>)((Object)cloneable2));
        }
        EnterTransitionCoordinator.removeNullViews(cloneable2);
        this.startRejectedAnimations((ArrayList<View>)cloneable2);
        cloneable2 = this.createSnapshots((Bundle)cloneable, this.mSharedElementNames);
        ArrayList arrayList = this.mSharedElements;
        boolean bl = true;
        this.showViews(arrayList, true);
        this.scheduleSetSharedElementEnd((ArrayList<View>)cloneable2);
        cloneable = this.setSharedElementState((Bundle)cloneable, (ArrayList<View>)cloneable2);
        this.requestLayoutForSharedElements();
        if (!this.allowOverlappingTransitions() || this.mIsReturning) {
            bl = false;
        }
        this.setGhostVisibility(4);
        this.scheduleGhostVisibilityChange(4);
        this.pauseInput();
        cloneable2 = this.beginTransition(viewGroup, bl, true);
        this.scheduleGhostVisibilityChange(0);
        this.setGhostVisibility(0);
        if (bl) {
            this.startEnterTransition((Transition)cloneable2);
        }
        EnterTransitionCoordinator.setOriginalSharedElementState(this.mSharedElements, (ArrayList<ActivityTransitionCoordinator.SharedElementOriginalState>)cloneable);
        if (this.mResultReceiver != null) {
            viewGroup.postOnAnimation(new Runnable(){
                int mAnimations;

                @Override
                public void run() {
                    block1 : {
                        block0 : {
                            int n = this.mAnimations;
                            this.mAnimations = n + 1;
                            if (n >= 2) break block0;
                            ViewGroup viewGroup = EnterTransitionCoordinator.this.getDecor();
                            if (viewGroup == null) break block1;
                            viewGroup.postOnAnimation(this);
                            break block1;
                        }
                        if (EnterTransitionCoordinator.this.mResultReceiver == null) break block1;
                        EnterTransitionCoordinator.this.mResultReceiver.send(101, null);
                        EnterTransitionCoordinator.this.mResultReceiver = null;
                    }
                }
            });
        }
    }

    private void triggerViewsReady(ArrayMap<String, View> arrayMap) {
        if (this.mAreViewsReady) {
            return;
        }
        this.mAreViewsReady = true;
        ViewGroup viewGroup = this.getDecor();
        if (viewGroup != null && (!viewGroup.isAttachedToWindow() || !arrayMap.isEmpty() && arrayMap.valueAt(0).isLayoutRequested())) {
            this.mViewsReadyListener = OneShotPreDrawListener.add(viewGroup, new _$$Lambda$EnterTransitionCoordinator$wYWFlx9zS3bxJYkN44Bpwx_EKis(this, arrayMap));
            viewGroup.invalidate();
        } else {
            this.viewsReady(arrayMap);
        }
    }

    public boolean cancelEnter() {
        this.setGhostVisibility(4);
        this.mHasStopped = true;
        this.mIsCanceled = true;
        this.clearState();
        return super.cancelPendingTransitions();
    }

    @Override
    protected void clearState() {
        this.mSharedElementsBundle = null;
        this.mEnterViewsTransition = null;
        this.mResultReceiver = null;
        ObjectAnimator objectAnimator = this.mBackgroundAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mBackgroundAnimator = null;
        }
        super.clearState();
    }

    public void forceViewsToAppear() {
        if (!this.mIsReturning) {
            return;
        }
        if (!this.mIsReadyForTransition) {
            OneShotPreDrawListener oneShotPreDrawListener;
            this.mIsReadyForTransition = true;
            if (this.getDecor() != null && (oneShotPreDrawListener = this.mViewsReadyListener) != null) {
                oneShotPreDrawListener.removeListener();
                this.mViewsReadyListener = null;
            }
            this.showViews(this.mTransitioningViews, true);
            this.setTransitioningViewsVisiblity(0, true);
            this.mSharedElements.clear();
            this.mAllSharedElementNames.clear();
            this.mTransitioningViews.clear();
            this.mIsReadyForTransition = true;
            this.viewsTransitionComplete();
            this.sharedElementTransitionComplete();
        } else {
            if (!this.mSharedElementTransitionStarted) {
                this.moveSharedElementsFromOverlay();
                this.mSharedElementTransitionStarted = true;
                this.showViews(this.mSharedElements, true);
                this.mSharedElements.clear();
                this.sharedElementTransitionComplete();
            }
            if (!this.mIsViewsTransitionStarted) {
                this.mIsViewsTransitionStarted = true;
                this.showViews(this.mTransitioningViews, true);
                this.setTransitioningViewsVisiblity(0, true);
                this.mTransitioningViews.clear();
                this.viewsTransitionComplete();
            }
            this.cancelPendingTransitions();
        }
        this.mAreViewsReady = true;
        if (this.mResultReceiver != null) {
            this.mResultReceiver.send(106, null);
            this.mResultReceiver = null;
        }
    }

    public Transition getEnterViewsTransition() {
        return this.mEnterViewsTransition;
    }

    public ArrayList<String> getPendingExitSharedElementNames() {
        return this.mPendingExitNames;
    }

    protected Transition getSharedElementTransition() {
        Window window = this.getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getSharedElementReenterTransition();
        }
        return window.getSharedElementEnterTransition();
    }

    @Override
    protected Transition getViewsTransition() {
        Window window = this.getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getReenterTransition();
        }
        return window.getEnterTransition();
    }

    boolean isCrossTask() {
        return this.mIsCrossTask;
    }

    public boolean isReturning() {
        return this.mIsReturning;
    }

    public boolean isWaitingForRemoteExit() {
        boolean bl = this.mIsReturning && this.mResultReceiver != null;
        return bl;
    }

    public /* synthetic */ void lambda$sendSharedElementDestination$1$EnterTransitionCoordinator() {
        if (this.mResultReceiver != null) {
            Bundle bundle = this.captureSharedElementState();
            this.moveSharedElementsToOverlay();
            this.mResultReceiver.send(107, bundle);
        }
    }

    public /* synthetic */ void lambda$triggerViewsReady$0$EnterTransitionCoordinator(ArrayMap arrayMap) {
        this.mViewsReadyListener = null;
        this.viewsReady(arrayMap);
    }

    public void namedViewsReady(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        this.triggerViewsReady(this.mapNamedElements(arrayList, arrayList2));
    }

    @Override
    protected void onReceiveResult(int n, Bundle bundle) {
        if (n != 103) {
            if (n != 104) {
                if (n != 106) {
                    if (n == 108 && !this.mIsCanceled) {
                        this.mPendingExitNames = this.mAllSharedElementNames;
                    }
                } else {
                    this.cancel();
                }
            } else if (!this.mIsCanceled) {
                this.mIsExitTransitionComplete = true;
                if (this.mSharedElementTransitionStarted) {
                    this.onRemoteExitTransitionComplete();
                }
            }
        } else if (!this.mIsCanceled) {
            this.mSharedElementsBundle = bundle;
            this.onTakeSharedElements();
        }
    }

    protected void onRemoteExitTransitionComplete() {
        if (!this.allowOverlappingTransitions()) {
            this.startEnterTransitionOnly();
        }
    }

    @Override
    protected void onTransitionsComplete() {
        this.moveSharedElementsFromOverlay();
        ViewGroup viewGroup = this.getDecor();
        if (viewGroup != null) {
            viewGroup.sendAccessibilityEvent(2048);
            Window window = this.getWindow();
            if (window != null && this.mReplacedBackground == viewGroup.getBackground()) {
                window.setBackgroundDrawable(null);
            }
        }
    }

    protected void prepareEnter() {
        Object object = this.getDecor();
        if (this.mActivity != null && object != null) {
            if (!this.isCrossTask()) {
                this.mActivity.overridePendingTransition(0, 0);
            }
            if (!this.mIsReturning) {
                this.mWasOpaque = this.mActivity.convertToTranslucent(null, null);
                if ((object = ((View)object).getBackground()) == null) {
                    this.mReplacedBackground = object = new ColorDrawable(0);
                } else {
                    this.getWindow().setBackgroundDrawable(null);
                    object = ((Drawable)object).mutate();
                    ((Drawable)object).setAlpha(0);
                }
                this.getWindow().setBackgroundDrawable((Drawable)object);
            } else {
                this.mActivity = null;
            }
            return;
        }
    }

    public void stop() {
        Object object = this.mBackgroundAnimator;
        if (object != null) {
            ((ValueAnimator)object).end();
            this.mBackgroundAnimator = null;
        } else if (this.mWasOpaque && (object = this.getDecor()) != null && (object = ((View)object).getBackground()) != null) {
            ((Drawable)object).setAlpha(1);
        }
        this.makeOpaque();
        this.mIsCanceled = true;
        this.mResultReceiver = null;
        this.mActivity = null;
        this.moveSharedElementsFromOverlay();
        if (this.mTransitioningViews != null) {
            this.showViews(this.mTransitioningViews, true);
            this.setTransitioningViewsVisiblity(0, true);
        }
        this.showViews(this.mSharedElements, true);
        this.clearState();
    }

    public void viewInstancesReady(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<View> arrayList3) {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            int n = 0;
            do {
                bl = bl2;
                if (n >= arrayList3.size()) break block3;
                View view = arrayList3.get(n);
                if (!TextUtils.equals(view.getTransitionName(), arrayList2.get(n)) || !view.isAttachedToWindow()) break;
                ++n;
            } while (true);
            bl = true;
        }
        if (bl) {
            this.triggerViewsReady(this.mapNamedElements(arrayList, arrayList2));
        } else {
            this.triggerViewsReady(this.mapSharedElements(arrayList, arrayList3));
        }
    }

    @Override
    protected void viewsReady(ArrayMap<String, View> object) {
        super.viewsReady((ArrayMap<String, View>)object);
        this.mIsReadyForTransition = true;
        this.hideViews(this.mSharedElements);
        object = this.getViewsTransition();
        if (object != null && this.mTransitioningViews != null) {
            EnterTransitionCoordinator.removeExcludedViews((Transition)object, this.mTransitioningViews);
            this.stripOffscreenViews();
            this.hideViews(this.mTransitioningViews);
        }
        if (this.mIsReturning) {
            this.sendSharedElementDestination();
        } else {
            this.moveSharedElementsToOverlay();
        }
        if (this.mSharedElementsBundle != null) {
            this.onTakeSharedElements();
        }
    }

}

