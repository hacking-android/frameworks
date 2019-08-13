/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityTransitionCoordinator;
import android.app.EnterTransitionCoordinator;
import android.app.ExitTransitionCoordinator;
import android.app.SharedElementCallback;
import android.app._$$Lambda$ActivityTransitionState$yioLR6wQWjZ9DcWK5bibElIbsXc;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.transition.Transition;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.internal.view.OneShotPreDrawListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;

class ActivityTransitionState {
    private static final String EXITING_MAPPED_FROM = "android:exitingMappedFrom";
    private static final String EXITING_MAPPED_TO = "android:exitingMappedTo";
    private static final String PENDING_EXIT_SHARED_ELEMENTS = "android:pendingExitSharedElements";
    private ExitTransitionCoordinator mCalledExitCoordinator;
    private ActivityOptions mEnterActivityOptions;
    private EnterTransitionCoordinator mEnterTransitionCoordinator;
    private SparseArray<WeakReference<ExitTransitionCoordinator>> mExitTransitionCoordinators;
    private int mExitTransitionCoordinatorsKey = 1;
    private ArrayList<String> mExitingFrom;
    private ArrayList<String> mExitingTo;
    private ArrayList<View> mExitingToView;
    private boolean mHasExited;
    private boolean mIsEnterPostponed;
    private boolean mIsEnterTriggered;
    private ArrayList<String> mPendingExitNames;
    private ExitTransitionCoordinator mReturnExitCoordinator;

    private ArrayList<String> getPendingExitNames() {
        EnterTransitionCoordinator enterTransitionCoordinator;
        if (this.mPendingExitNames == null && (enterTransitionCoordinator = this.mEnterTransitionCoordinator) != null) {
            this.mPendingExitNames = enterTransitionCoordinator.getPendingExitSharedElementNames();
        }
        return this.mPendingExitNames;
    }

    private void restoreExitedViews() {
        ExitTransitionCoordinator exitTransitionCoordinator = this.mCalledExitCoordinator;
        if (exitTransitionCoordinator != null) {
            exitTransitionCoordinator.resetViews();
            this.mCalledExitCoordinator = null;
        }
    }

    private void restoreReenteringViews() {
        EnterTransitionCoordinator enterTransitionCoordinator = this.mEnterTransitionCoordinator;
        if (enterTransitionCoordinator != null && enterTransitionCoordinator.isReturning() && !this.mEnterTransitionCoordinator.isCrossTask()) {
            this.mEnterTransitionCoordinator.forceViewsToAppear();
            this.mExitingFrom = null;
            this.mExitingTo = null;
            this.mExitingToView = null;
        }
    }

    private void startEnter() {
        if (this.mEnterTransitionCoordinator.isReturning()) {
            ArrayList<View> arrayList = this.mExitingToView;
            if (arrayList != null) {
                this.mEnterTransitionCoordinator.viewInstancesReady(this.mExitingFrom, this.mExitingTo, arrayList);
            } else {
                this.mEnterTransitionCoordinator.namedViewsReady(this.mExitingFrom, this.mExitingTo);
            }
        } else {
            this.mEnterTransitionCoordinator.namedViewsReady(null, null);
            this.mPendingExitNames = null;
        }
        this.mExitingFrom = null;
        this.mExitingTo = null;
        this.mExitingToView = null;
        this.mEnterActivityOptions = null;
    }

    public int addExitTransitionCoordinator(ExitTransitionCoordinator object) {
        int n;
        if (this.mExitTransitionCoordinators == null) {
            this.mExitTransitionCoordinators = new SparseArray();
        }
        object = new WeakReference<ExitTransitionCoordinator>((ExitTransitionCoordinator)object);
        for (n = this.mExitTransitionCoordinators.size() - 1; n >= 0; --n) {
            if (this.mExitTransitionCoordinators.valueAt(n).get() != null) continue;
            this.mExitTransitionCoordinators.removeAt(n);
        }
        n = this.mExitTransitionCoordinatorsKey;
        this.mExitTransitionCoordinatorsKey = n + 1;
        this.mExitTransitionCoordinators.append(n, (WeakReference<ExitTransitionCoordinator>)object);
        return n;
    }

    public void clear() {
        this.mPendingExitNames = null;
        this.mExitingFrom = null;
        this.mExitingTo = null;
        this.mExitingToView = null;
        this.mCalledExitCoordinator = null;
        this.mEnterTransitionCoordinator = null;
        this.mEnterActivityOptions = null;
        this.mExitTransitionCoordinators = null;
    }

    public void enterReady(Activity activity) {
        Object object = this.mEnterActivityOptions;
        if (object != null && !this.mIsEnterTriggered) {
            this.mIsEnterTriggered = true;
            this.mHasExited = false;
            object = ((ActivityOptions)object).getSharedElementNames();
            ResultReceiver resultReceiver = this.mEnterActivityOptions.getResultReceiver();
            if (this.mEnterActivityOptions.isReturning()) {
                this.restoreExitedViews();
                activity.getWindow().getDecorView().setVisibility(0);
            }
            this.mEnterTransitionCoordinator = new EnterTransitionCoordinator(activity, resultReceiver, (ArrayList<String>)object, this.mEnterActivityOptions.isReturning(), this.mEnterActivityOptions.isCrossTask());
            if (this.mEnterActivityOptions.isCrossTask()) {
                this.mExitingFrom = new ArrayList<String>(this.mEnterActivityOptions.getSharedElementNames());
                this.mExitingTo = new ArrayList<String>(this.mEnterActivityOptions.getSharedElementNames());
            }
            if (!this.mIsEnterPostponed) {
                this.startEnter();
            }
            return;
        }
    }

    public boolean isTransitionRunning() {
        ActivityTransitionCoordinator activityTransitionCoordinator = this.mEnterTransitionCoordinator;
        if (activityTransitionCoordinator != null && activityTransitionCoordinator.isTransitionRunning()) {
            return true;
        }
        activityTransitionCoordinator = this.mCalledExitCoordinator;
        if (activityTransitionCoordinator != null && activityTransitionCoordinator.isTransitionRunning()) {
            return true;
        }
        activityTransitionCoordinator = this.mReturnExitCoordinator;
        return activityTransitionCoordinator != null && activityTransitionCoordinator.isTransitionRunning();
    }

    public /* synthetic */ void lambda$startExitBackTransition$0$ActivityTransitionState(Activity activity) {
        ExitTransitionCoordinator exitTransitionCoordinator = this.mReturnExitCoordinator;
        if (exitTransitionCoordinator != null) {
            exitTransitionCoordinator.startExit(activity.mResultCode, activity.mResultData);
        }
    }

    public void onResume(Activity activity) {
        if (this.mEnterTransitionCoordinator != null && !activity.isTopOfTask()) {
            activity.mHandler.postDelayed(new Runnable(){

                @Override
                public void run() {
                    if (ActivityTransitionState.this.mEnterTransitionCoordinator == null || ActivityTransitionState.this.mEnterTransitionCoordinator.isWaitingForRemoteExit()) {
                        ActivityTransitionState.this.restoreExitedViews();
                        ActivityTransitionState.this.restoreReenteringViews();
                    }
                }
            }, 1000L);
        } else {
            this.restoreExitedViews();
            this.restoreReenteringViews();
        }
    }

    public void onStop() {
        this.restoreExitedViews();
        ActivityTransitionCoordinator activityTransitionCoordinator = this.mEnterTransitionCoordinator;
        if (activityTransitionCoordinator != null) {
            ((EnterTransitionCoordinator)activityTransitionCoordinator).stop();
            this.mEnterTransitionCoordinator = null;
        }
        if ((activityTransitionCoordinator = this.mReturnExitCoordinator) != null) {
            ((ExitTransitionCoordinator)activityTransitionCoordinator).stop();
            this.mReturnExitCoordinator = null;
        }
    }

    public void postponeEnterTransition() {
        this.mIsEnterPostponed = true;
    }

    public void readState(Bundle bundle) {
        if (bundle != null) {
            EnterTransitionCoordinator enterTransitionCoordinator = this.mEnterTransitionCoordinator;
            if (enterTransitionCoordinator == null || enterTransitionCoordinator.isReturning()) {
                this.mPendingExitNames = bundle.getStringArrayList(PENDING_EXIT_SHARED_ELEMENTS);
            }
            if (this.mEnterTransitionCoordinator == null) {
                this.mExitingFrom = bundle.getStringArrayList(EXITING_MAPPED_FROM);
                this.mExitingTo = bundle.getStringArrayList(EXITING_MAPPED_TO);
            }
        }
    }

    public void saveState(Bundle bundle) {
        ArrayList<String> arrayList = this.getPendingExitNames();
        if (arrayList != null) {
            bundle.putStringArrayList(PENDING_EXIT_SHARED_ELEMENTS, arrayList);
        }
        if ((arrayList = this.mExitingFrom) != null) {
            bundle.putStringArrayList(EXITING_MAPPED_FROM, arrayList);
            bundle.putStringArrayList(EXITING_MAPPED_TO, this.mExitingTo);
        }
    }

    public void setEnterActivityOptions(Activity activity, ActivityOptions object) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView();
        if (window.hasFeature(13) && object != null && this.mEnterActivityOptions == null && this.mEnterTransitionCoordinator == null && ((ActivityOptions)object).getAnimationType() == 5) {
            this.mEnterActivityOptions = object;
            this.mIsEnterTriggered = false;
            if (this.mEnterActivityOptions.isReturning()) {
                this.restoreExitedViews();
                int n = this.mEnterActivityOptions.getResultCode();
                if (n != 0) {
                    object = this.mEnterActivityOptions.getResultData();
                    if (object != null) {
                        ((Intent)object).setExtrasClassLoader(activity.getClassLoader());
                    }
                    activity.onActivityReenter(n, (Intent)object);
                }
            }
        }
    }

    public boolean startExitBackTransition(Activity activity) {
        ArrayList<String> arrayList = this.getPendingExitNames();
        if (arrayList != null && this.mCalledExitCoordinator == null) {
            if (!this.mHasExited) {
                boolean bl;
                ViewGroup viewGroup;
                this.mHasExited = true;
                Object object = this.mEnterTransitionCoordinator;
                if (object != null) {
                    object = ((EnterTransitionCoordinator)object).getEnterViewsTransition();
                    viewGroup = this.mEnterTransitionCoordinator.getDecor();
                    bl = this.mEnterTransitionCoordinator.cancelEnter();
                    this.mEnterTransitionCoordinator = null;
                    if (object != null && viewGroup != null) {
                        ((Transition)object).pause(viewGroup);
                    }
                } else {
                    object = null;
                    viewGroup = null;
                    bl = false;
                }
                this.mReturnExitCoordinator = new ExitTransitionCoordinator(activity, activity.getWindow(), activity.mEnterTransitionListener, arrayList, null, null, true);
                if (object != null && viewGroup != null) {
                    ((Transition)object).resume(viewGroup);
                }
                if (bl && viewGroup != null) {
                    OneShotPreDrawListener.add(viewGroup, new _$$Lambda$ActivityTransitionState$yioLR6wQWjZ9DcWK5bibElIbsXc(this, activity));
                } else {
                    this.mReturnExitCoordinator.startExit(activity.mResultCode, activity.mResultData);
                }
            }
            return true;
        }
        return false;
    }

    public void startExitOutTransition(Activity object, Bundle bundle) {
        this.mEnterTransitionCoordinator = null;
        if (((Activity)object).getWindow().hasFeature(13) && this.mExitTransitionCoordinators != null) {
            object = new ActivityOptions(bundle);
            if (((ActivityOptions)object).getAnimationType() == 5) {
                int n = ((ActivityOptions)object).getExitCoordinatorKey();
                if ((n = this.mExitTransitionCoordinators.indexOfKey(n)) >= 0) {
                    this.mCalledExitCoordinator = (ExitTransitionCoordinator)this.mExitTransitionCoordinators.valueAt(n).get();
                    this.mExitTransitionCoordinators.removeAt(n);
                    object = this.mCalledExitCoordinator;
                    if (object != null) {
                        this.mExitingFrom = ((ActivityTransitionCoordinator)object).getAcceptedNames();
                        this.mExitingTo = this.mCalledExitCoordinator.getMappedNames();
                        this.mExitingToView = this.mCalledExitCoordinator.copyMappedViews();
                        this.mCalledExitCoordinator.startExit();
                    }
                }
            }
            return;
        }
    }

    public void startPostponedEnterTransition() {
        if (this.mIsEnterPostponed) {
            this.mIsEnterPostponed = false;
            if (this.mEnterTransitionCoordinator != null) {
                this.startEnter();
            }
        }
    }

}

