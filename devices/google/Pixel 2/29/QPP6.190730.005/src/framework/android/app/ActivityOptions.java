/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityTransitionCoordinator;
import android.app.ActivityTransitionState;
import android.app.ExitTransitionCoordinator;
import android.app.PendingIntent;
import android.app.SharedElementCallback;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.GraphicBuffer;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.Pair;
import android.util.Slog;
import android.view.AppTransitionAnimationSpec;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import java.util.ArrayList;
import java.util.Collection;

public class ActivityOptions {
    public static final int ANIM_CLIP_REVEAL = 11;
    public static final int ANIM_CUSTOM = 1;
    public static final int ANIM_CUSTOM_IN_PLACE = 10;
    public static final int ANIM_DEFAULT = 6;
    public static final int ANIM_LAUNCH_TASK_BEHIND = 7;
    public static final int ANIM_NONE = 0;
    public static final int ANIM_OPEN_CROSS_PROFILE_APPS = 12;
    public static final int ANIM_REMOTE_ANIMATION = 13;
    public static final int ANIM_SCALE_UP = 2;
    public static final int ANIM_SCENE_TRANSITION = 5;
    public static final int ANIM_THUMBNAIL_ASPECT_SCALE_DOWN = 9;
    public static final int ANIM_THUMBNAIL_ASPECT_SCALE_UP = 8;
    public static final int ANIM_THUMBNAIL_SCALE_DOWN = 4;
    public static final int ANIM_THUMBNAIL_SCALE_UP = 3;
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";
    private static final String KEY_ANIMATION_FINISHED_LISTENER = "android:activity.animationFinishedListener";
    public static final String KEY_ANIM_ENTER_RES_ID = "android:activity.animEnterRes";
    public static final String KEY_ANIM_EXIT_RES_ID = "android:activity.animExitRes";
    public static final String KEY_ANIM_HEIGHT = "android:activity.animHeight";
    public static final String KEY_ANIM_IN_PLACE_RES_ID = "android:activity.animInPlaceRes";
    private static final String KEY_ANIM_SPECS = "android:activity.animSpecs";
    public static final String KEY_ANIM_START_LISTENER = "android:activity.animStartListener";
    public static final String KEY_ANIM_START_X = "android:activity.animStartX";
    public static final String KEY_ANIM_START_Y = "android:activity.animStartY";
    public static final String KEY_ANIM_THUMBNAIL = "android:activity.animThumbnail";
    public static final String KEY_ANIM_TYPE = "android:activity.animType";
    public static final String KEY_ANIM_WIDTH = "android:activity.animWidth";
    private static final String KEY_AVOID_MOVE_TO_FRONT = "android.activity.avoidMoveToFront";
    private static final String KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING = "android:activity.disallowEnterPictureInPictureWhileLaunching";
    private static final String KEY_EXIT_COORDINATOR_INDEX = "android:activity.exitCoordinatorIndex";
    private static final String KEY_FREEZE_RECENT_TASKS_REORDERING = "android.activity.freezeRecentTasksReordering";
    private static final String KEY_INSTANT_APP_VERIFICATION_BUNDLE = "android:instantapps.installerbundle";
    private static final String KEY_LAUNCH_ACTIVITY_TYPE = "android.activity.activityType";
    public static final String KEY_LAUNCH_BOUNDS = "android:activity.launchBounds";
    private static final String KEY_LAUNCH_DISPLAY_ID = "android.activity.launchDisplayId";
    private static final String KEY_LAUNCH_TASK_ID = "android.activity.launchTaskId";
    private static final String KEY_LAUNCH_WINDOWING_MODE = "android.activity.windowingMode";
    private static final String KEY_LOCK_TASK_MODE = "android:activity.lockTaskMode";
    public static final String KEY_PACKAGE_NAME = "android:activity.packageName";
    private static final String KEY_PENDING_INTENT_LAUNCH_FLAGS = "android.activity.pendingIntentLaunchFlags";
    private static final String KEY_REMOTE_ANIMATION_ADAPTER = "android:activity.remoteAnimationAdapter";
    private static final String KEY_RESULT_CODE = "android:activity.resultCode";
    private static final String KEY_RESULT_DATA = "android:activity.resultData";
    private static final String KEY_ROTATION_ANIMATION_HINT = "android:activity.rotationAnimationHint";
    private static final String KEY_SPECS_FUTURE = "android:activity.specsFuture";
    private static final String KEY_SPLIT_SCREEN_CREATE_MODE = "android:activity.splitScreenCreateMode";
    private static final String KEY_TASK_OVERLAY = "android.activity.taskOverlay";
    private static final String KEY_TASK_OVERLAY_CAN_RESUME = "android.activity.taskOverlayCanResume";
    private static final String KEY_TRANSITION_COMPLETE_LISTENER = "android:activity.transitionCompleteListener";
    private static final String KEY_TRANSITION_IS_RETURNING = "android:activity.transitionIsReturning";
    private static final String KEY_TRANSITION_SHARED_ELEMENTS = "android:activity.sharedElementNames";
    private static final String KEY_USAGE_TIME_REPORT = "android:activity.usageTimeReport";
    private static final String TAG = "ActivityOptions";
    private AppTransitionAnimationSpec[] mAnimSpecs;
    private IRemoteCallback mAnimationFinishedListener;
    private IRemoteCallback mAnimationStartedListener;
    private int mAnimationType = 0;
    private Bundle mAppVerificationBundle;
    private boolean mAvoidMoveToFront;
    private int mCustomEnterResId;
    private int mCustomExitResId;
    private int mCustomInPlaceResId;
    private boolean mDisallowEnterPictureInPictureWhileLaunching;
    private int mExitCoordinatorIndex;
    private boolean mFreezeRecentTasksReordering;
    private int mHeight;
    private boolean mIsReturning;
    @WindowConfiguration.ActivityType
    private int mLaunchActivityType = 0;
    private Rect mLaunchBounds;
    private int mLaunchDisplayId = -1;
    private int mLaunchTaskId = -1;
    @WindowConfiguration.WindowingMode
    private int mLaunchWindowingMode = 0;
    private boolean mLockTaskMode = false;
    private String mPackageName;
    private int mPendingIntentLaunchFlags;
    private RemoteAnimationAdapter mRemoteAnimationAdapter;
    private int mResultCode;
    private Intent mResultData;
    private int mRotationAnimationHint = -1;
    private ArrayList<String> mSharedElementNames;
    private IAppTransitionAnimationSpecsFuture mSpecsFuture;
    private int mSplitScreenCreateMode = 0;
    private int mStartX;
    private int mStartY;
    private boolean mTaskOverlay;
    private boolean mTaskOverlayCanResume;
    private Bitmap mThumbnail;
    private ResultReceiver mTransitionReceiver;
    private PendingIntent mUsageTimeReport;
    private int mWidth;

    private ActivityOptions() {
    }

    public ActivityOptions(Bundle bundle) {
        Parcelable[] arrparcelable;
        bundle.setDefusable(true);
        this.mPackageName = bundle.getString(KEY_PACKAGE_NAME);
        try {
            this.mUsageTimeReport = (PendingIntent)bundle.getParcelable(KEY_USAGE_TIME_REPORT);
        }
        catch (RuntimeException runtimeException) {
            Slog.w(TAG, runtimeException);
        }
        this.mLaunchBounds = (Rect)bundle.getParcelable(KEY_LAUNCH_BOUNDS);
        this.mAnimationType = bundle.getInt(KEY_ANIM_TYPE);
        switch (this.mAnimationType) {
            default: {
                break;
            }
            case 10: {
                this.mCustomInPlaceResId = bundle.getInt(KEY_ANIM_IN_PLACE_RES_ID, 0);
                break;
            }
            case 5: {
                this.mTransitionReceiver = (ResultReceiver)bundle.getParcelable(KEY_TRANSITION_COMPLETE_LISTENER);
                this.mIsReturning = bundle.getBoolean(KEY_TRANSITION_IS_RETURNING, false);
                this.mSharedElementNames = bundle.getStringArrayList(KEY_TRANSITION_SHARED_ELEMENTS);
                this.mResultData = (Intent)bundle.getParcelable(KEY_RESULT_DATA);
                this.mResultCode = bundle.getInt(KEY_RESULT_CODE);
                this.mExitCoordinatorIndex = bundle.getInt(KEY_EXIT_COORDINATOR_INDEX);
                break;
            }
            case 3: 
            case 4: 
            case 8: 
            case 9: {
                arrparcelable = (Parcelable[])bundle.getParcelable(KEY_ANIM_THUMBNAIL);
                if (arrparcelable != null) {
                    this.mThumbnail = Bitmap.wrapHardwareBuffer((GraphicBuffer)arrparcelable, null);
                }
                this.mStartX = bundle.getInt(KEY_ANIM_START_X, 0);
                this.mStartY = bundle.getInt(KEY_ANIM_START_Y, 0);
                this.mWidth = bundle.getInt(KEY_ANIM_WIDTH, 0);
                this.mHeight = bundle.getInt(KEY_ANIM_HEIGHT, 0);
                this.mAnimationStartedListener = IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_START_LISTENER));
                break;
            }
            case 2: 
            case 11: {
                this.mStartX = bundle.getInt(KEY_ANIM_START_X, 0);
                this.mStartY = bundle.getInt(KEY_ANIM_START_Y, 0);
                this.mWidth = bundle.getInt(KEY_ANIM_WIDTH, 0);
                this.mHeight = bundle.getInt(KEY_ANIM_HEIGHT, 0);
                break;
            }
            case 1: {
                this.mCustomEnterResId = bundle.getInt(KEY_ANIM_ENTER_RES_ID, 0);
                this.mCustomExitResId = bundle.getInt(KEY_ANIM_EXIT_RES_ID, 0);
                this.mAnimationStartedListener = IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_START_LISTENER));
            }
        }
        this.mLockTaskMode = bundle.getBoolean(KEY_LOCK_TASK_MODE, false);
        this.mLaunchDisplayId = bundle.getInt(KEY_LAUNCH_DISPLAY_ID, -1);
        this.mLaunchWindowingMode = bundle.getInt(KEY_LAUNCH_WINDOWING_MODE, 0);
        this.mLaunchActivityType = bundle.getInt(KEY_LAUNCH_ACTIVITY_TYPE, 0);
        this.mLaunchTaskId = bundle.getInt(KEY_LAUNCH_TASK_ID, -1);
        this.mPendingIntentLaunchFlags = bundle.getInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, 0);
        this.mTaskOverlay = bundle.getBoolean(KEY_TASK_OVERLAY, false);
        this.mTaskOverlayCanResume = bundle.getBoolean(KEY_TASK_OVERLAY_CAN_RESUME, false);
        this.mAvoidMoveToFront = bundle.getBoolean(KEY_AVOID_MOVE_TO_FRONT, false);
        this.mFreezeRecentTasksReordering = bundle.getBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, false);
        this.mSplitScreenCreateMode = bundle.getInt(KEY_SPLIT_SCREEN_CREATE_MODE, 0);
        this.mDisallowEnterPictureInPictureWhileLaunching = bundle.getBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, false);
        if (bundle.containsKey(KEY_ANIM_SPECS)) {
            arrparcelable = bundle.getParcelableArray(KEY_ANIM_SPECS);
            this.mAnimSpecs = new AppTransitionAnimationSpec[arrparcelable.length];
            for (int i = arrparcelable.length - 1; i >= 0; --i) {
                this.mAnimSpecs[i] = (AppTransitionAnimationSpec)arrparcelable[i];
            }
        }
        if (bundle.containsKey(KEY_ANIMATION_FINISHED_LISTENER)) {
            this.mAnimationFinishedListener = IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIMATION_FINISHED_LISTENER));
        }
        this.mRotationAnimationHint = bundle.getInt(KEY_ROTATION_ANIMATION_HINT, -1);
        this.mAppVerificationBundle = bundle.getBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE);
        if (bundle.containsKey(KEY_SPECS_FUTURE)) {
            this.mSpecsFuture = IAppTransitionAnimationSpecsFuture.Stub.asInterface(bundle.getBinder(KEY_SPECS_FUTURE));
        }
        this.mRemoteAnimationAdapter = (RemoteAnimationAdapter)bundle.getParcelable(KEY_REMOTE_ANIMATION_ADAPTER);
    }

    public static void abort(ActivityOptions activityOptions) {
        if (activityOptions != null) {
            activityOptions.abort();
        }
    }

    public static ActivityOptions fromBundle(Bundle object) {
        object = object != null ? new ActivityOptions((Bundle)object) : null;
        return object;
    }

    private static ActivityOptions makeAspectScaledThumbnailAnimation(View view, Bitmap arrn, int n, int n2, int n3, int n4, Handler handler, OnAnimationStartedListener onAnimationStartedListener, boolean bl) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        int n5 = bl ? 8 : 9;
        activityOptions.mAnimationType = n5;
        activityOptions.mThumbnail = arrn;
        arrn = new int[2];
        view.getLocationOnScreen(arrn);
        activityOptions.mStartX = arrn[0] + n;
        activityOptions.mStartY = arrn[1] + n2;
        activityOptions.mWidth = n3;
        activityOptions.mHeight = n4;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeBasic() {
        return new ActivityOptions();
    }

    public static ActivityOptions makeClipRevealAnimation(View view, int n, int n2, int n3, int n4) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 11;
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        activityOptions.mStartX = arrn[0] + n;
        activityOptions.mStartY = arrn[1] + n2;
        activityOptions.mWidth = n3;
        activityOptions.mHeight = n4;
        return activityOptions;
    }

    public static ActivityOptions makeCustomAnimation(Context context, int n, int n2) {
        return ActivityOptions.makeCustomAnimation(context, n, n2, null, null);
    }

    @UnsupportedAppUsage
    public static ActivityOptions makeCustomAnimation(Context context, int n, int n2, Handler handler, OnAnimationStartedListener onAnimationStartedListener) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        activityOptions.mAnimationType = 1;
        activityOptions.mCustomEnterResId = n;
        activityOptions.mCustomExitResId = n2;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeCustomInPlaceAnimation(Context context, int n) {
        if (n != 0) {
            ActivityOptions activityOptions = new ActivityOptions();
            activityOptions.mPackageName = context.getPackageName();
            activityOptions.mAnimationType = 10;
            activityOptions.mCustomInPlaceResId = n;
            return activityOptions;
        }
        throw new RuntimeException("You must specify a valid animation.");
    }

    @UnsupportedAppUsage
    public static ActivityOptions makeMultiThumbFutureAspectScaleAnimation(Context context, Handler handler, IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, OnAnimationStartedListener onAnimationStartedListener, boolean bl) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        int n = bl ? 8 : 9;
        activityOptions.mAnimationType = n;
        activityOptions.mSpecsFuture = iAppTransitionAnimationSpecsFuture;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeOpenCrossProfileAppsAnimation() {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 12;
        return activityOptions;
    }

    @UnsupportedAppUsage
    public static ActivityOptions makeRemoteAnimation(RemoteAnimationAdapter remoteAnimationAdapter) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mRemoteAnimationAdapter = remoteAnimationAdapter;
        activityOptions.mAnimationType = 13;
        return activityOptions;
    }

    public static ActivityOptions makeScaleUpAnimation(View view, int n, int n2, int n3, int n4) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = 2;
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        activityOptions.mStartX = arrn[0] + n;
        activityOptions.mStartY = arrn[1] + n2;
        activityOptions.mWidth = n3;
        activityOptions.mHeight = n4;
        return activityOptions;
    }

    static ActivityOptions makeSceneTransitionAnimation(Activity activity, ExitTransitionCoordinator exitTransitionCoordinator, ArrayList<String> arrayList, int n, Intent intent) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 5;
        activityOptions.mSharedElementNames = arrayList;
        activityOptions.mTransitionReceiver = exitTransitionCoordinator;
        activityOptions.mIsReturning = true;
        activityOptions.mResultCode = n;
        activityOptions.mResultData = intent;
        activityOptions.mExitCoordinatorIndex = activity.mActivityTransitionState.addExitTransitionCoordinator(exitTransitionCoordinator);
        return activityOptions;
    }

    public static ActivityOptions makeSceneTransitionAnimation(Activity activity, View view, String string2) {
        return ActivityOptions.makeSceneTransitionAnimation(activity, Pair.create(view, string2));
    }

    @SafeVarargs
    public static ActivityOptions makeSceneTransitionAnimation(Activity activity, Pair<View, String> ... arrpair) {
        ActivityOptions activityOptions = new ActivityOptions();
        ActivityOptions.makeSceneTransitionAnimation(activity, activity.getWindow(), activityOptions, activity.mExitTransitionListener, arrpair);
        return activityOptions;
    }

    static ExitTransitionCoordinator makeSceneTransitionAnimation(Activity activity, Window object, ActivityOptions activityOptions, SharedElementCallback sharedElementCallback, Pair<View, String>[] arrpair) {
        if (!((Window)object).hasFeature(13)) {
            activityOptions.mAnimationType = 6;
            return null;
        }
        activityOptions.mAnimationType = 5;
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<View> arrayList2 = new ArrayList<View>();
        if (arrpair != null) {
            for (int i = 0; i < arrpair.length; ++i) {
                Pair<View, String> pair = arrpair[i];
                String string2 = (String)pair.second;
                if (string2 != null) {
                    arrayList.add(string2);
                    if ((View)pair.first != null) {
                        arrayList2.add((View)pair.first);
                        continue;
                    }
                    throw new IllegalArgumentException("Shared element must not be null");
                }
                throw new IllegalArgumentException("Shared element name must not be null");
            }
        }
        activityOptions.mTransitionReceiver = object = new ExitTransitionCoordinator(activity, (Window)object, sharedElementCallback, arrayList, arrayList, arrayList2, false);
        activityOptions.mSharedElementNames = arrayList;
        boolean bl = activity == null;
        activityOptions.mIsReturning = bl;
        activityOptions.mExitCoordinatorIndex = activity == null ? -1 : activity.mActivityTransitionState.addExitTransitionCoordinator((ExitTransitionCoordinator)object);
        return object;
    }

    public static ActivityOptions makeTaskLaunchBehind() {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 7;
        return activityOptions;
    }

    private static ActivityOptions makeThumbnailAnimation(View view, Bitmap arrn, int n, int n2, OnAnimationStartedListener onAnimationStartedListener, boolean bl) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        int n3 = bl ? 3 : 4;
        activityOptions.mAnimationType = n3;
        activityOptions.mThumbnail = arrn;
        arrn = new int[2];
        view.getLocationOnScreen(arrn);
        activityOptions.mStartX = arrn[0] + n;
        activityOptions.mStartY = arrn[1] + n2;
        activityOptions.setOnAnimationStartedListener(view.getHandler(), onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeThumbnailAspectScaleDownAnimation(View view, Bitmap bitmap, int n, int n2, int n3, int n4, Handler handler, OnAnimationStartedListener onAnimationStartedListener) {
        return ActivityOptions.makeAspectScaledThumbnailAnimation(view, bitmap, n, n2, n3, n4, handler, onAnimationStartedListener, false);
    }

    public static ActivityOptions makeThumbnailAspectScaleDownAnimation(View view, AppTransitionAnimationSpec[] arrappTransitionAnimationSpec, Handler handler, OnAnimationStartedListener onAnimationStartedListener, OnAnimationFinishedListener onAnimationFinishedListener) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = 9;
        activityOptions.mAnimSpecs = arrappTransitionAnimationSpec;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        activityOptions.setOnAnimationFinishedListener(handler, onAnimationFinishedListener);
        return activityOptions;
    }

    public static ActivityOptions makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int n, int n2) {
        return ActivityOptions.makeThumbnailScaleUpAnimation(view, bitmap, n, n2, null);
    }

    private static ActivityOptions makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int n, int n2, OnAnimationStartedListener onAnimationStartedListener) {
        return ActivityOptions.makeThumbnailAnimation(view, bitmap, n, n2, onAnimationStartedListener, true);
    }

    private void setOnAnimationFinishedListener(final Handler handler, final OnAnimationFinishedListener onAnimationFinishedListener) {
        if (onAnimationFinishedListener != null) {
            this.mAnimationFinishedListener = new IRemoteCallback.Stub(){

                @Override
                public void sendResult(Bundle bundle) throws RemoteException {
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                            onAnimationFinishedListener.onAnimationFinished();
                        }
                    });
                }

            };
        }
    }

    private void setOnAnimationStartedListener(final Handler handler, final OnAnimationStartedListener onAnimationStartedListener) {
        if (onAnimationStartedListener != null) {
            this.mAnimationStartedListener = new IRemoteCallback.Stub(){

                @Override
                public void sendResult(Bundle bundle) throws RemoteException {
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                            onAnimationStartedListener.onAnimationStarted();
                        }
                    });
                }

            };
        }
    }

    @SafeVarargs
    public static ActivityOptions startSharedElementAnimation(Window window, Pair<View, String> ... object) {
        ActivityOptions activityOptions = new ActivityOptions();
        if (window.getDecorView() == null) {
            return activityOptions;
        }
        if ((object = ActivityOptions.makeSceneTransitionAnimation(null, window, activityOptions, null, object)) != null) {
            ((ExitTransitionCoordinator)object).setHideSharedElementsCallback(new HideWindowListener(window, (ExitTransitionCoordinator)object));
            ((ExitTransitionCoordinator)object).startExit();
        }
        return activityOptions;
    }

    public static void stopSharedElementAnimation(Window object) {
        View view = ((Window)object).getDecorView();
        if (view == null) {
            return;
        }
        object = (ExitTransitionCoordinator)view.getTag(16908849);
        if (object != null) {
            ((ActivityTransitionCoordinator)object).cancelPendingTransitions();
            view.setTagInternal(16908849, null);
            TransitionManager.endTransitions((ViewGroup)view);
            ((ExitTransitionCoordinator)object).resetViews();
            ((ExitTransitionCoordinator)object).clearState();
            view.setVisibility(0);
        }
    }

    public void abort() {
        IRemoteCallback iRemoteCallback = this.mAnimationStartedListener;
        if (iRemoteCallback != null) {
            try {
                iRemoteCallback.sendResult(null);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public boolean canTaskOverlayResume() {
        return this.mTaskOverlayCanResume;
    }

    public boolean disallowEnterPictureInPictureWhileLaunching() {
        return this.mDisallowEnterPictureInPictureWhileLaunching;
    }

    public ActivityOptions forTargetActivity() {
        if (this.mAnimationType == 5) {
            ActivityOptions activityOptions = new ActivityOptions();
            activityOptions.update(this);
            return activityOptions;
        }
        return null;
    }

    public boolean freezeRecentTasksReordering() {
        return this.mFreezeRecentTasksReordering;
    }

    public AppTransitionAnimationSpec[] getAnimSpecs() {
        return this.mAnimSpecs;
    }

    public IRemoteCallback getAnimationFinishedListener() {
        return this.mAnimationFinishedListener;
    }

    public int getAnimationType() {
        return this.mAnimationType;
    }

    public boolean getAvoidMoveToFront() {
        return this.mAvoidMoveToFront;
    }

    public int getCustomEnterResId() {
        return this.mCustomEnterResId;
    }

    public int getCustomExitResId() {
        return this.mCustomExitResId;
    }

    public int getCustomInPlaceResId() {
        return this.mCustomInPlaceResId;
    }

    public int getExitCoordinatorKey() {
        return this.mExitCoordinatorIndex;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getLaunchActivityType() {
        return this.mLaunchActivityType;
    }

    public Rect getLaunchBounds() {
        return this.mLaunchBounds;
    }

    public int getLaunchDisplayId() {
        return this.mLaunchDisplayId;
    }

    public boolean getLaunchTaskBehind() {
        boolean bl = this.mAnimationType == 7;
        return bl;
    }

    public int getLaunchTaskId() {
        return this.mLaunchTaskId;
    }

    public int getLaunchWindowingMode() {
        return this.mLaunchWindowingMode;
    }

    public boolean getLockTaskMode() {
        return this.mLockTaskMode;
    }

    public IRemoteCallback getOnAnimationStartListener() {
        return this.mAnimationStartedListener;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getPendingIntentLaunchFlags() {
        return this.mPendingIntentLaunchFlags;
    }

    public RemoteAnimationAdapter getRemoteAnimationAdapter() {
        return this.mRemoteAnimationAdapter;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public Intent getResultData() {
        return this.mResultData;
    }

    public ResultReceiver getResultReceiver() {
        return this.mTransitionReceiver;
    }

    public int getRotationAnimationHint() {
        return this.mRotationAnimationHint;
    }

    public ArrayList<String> getSharedElementNames() {
        return this.mSharedElementNames;
    }

    public IAppTransitionAnimationSpecsFuture getSpecsFuture() {
        return this.mSpecsFuture;
    }

    public int getSplitScreenCreateMode() {
        return this.mSplitScreenCreateMode;
    }

    public int getStartX() {
        return this.mStartX;
    }

    public int getStartY() {
        return this.mStartY;
    }

    public boolean getTaskOverlay() {
        return this.mTaskOverlay;
    }

    public GraphicBuffer getThumbnail() {
        Parcelable parcelable = this.mThumbnail;
        parcelable = parcelable != null ? parcelable.createGraphicBufferHandle() : null;
        return parcelable;
    }

    public PendingIntent getUsageTimeReport() {
        return this.mUsageTimeReport;
    }

    public int getWidth() {
        return this.mWidth;
    }

    boolean isCrossTask() {
        boolean bl = this.mExitCoordinatorIndex < 0;
        return bl;
    }

    public boolean isReturning() {
        return this.mIsReturning;
    }

    public Bundle popAppVerificationBundle() {
        Bundle bundle = this.mAppVerificationBundle;
        this.mAppVerificationBundle = null;
        return bundle;
    }

    public void requestUsageTimeReport(PendingIntent pendingIntent) {
        this.mUsageTimeReport = pendingIntent;
    }

    public ActivityOptions setAppVerificationBundle(Bundle bundle) {
        this.mAppVerificationBundle = bundle;
        return this;
    }

    public void setAvoidMoveToFront() {
        this.mAvoidMoveToFront = true;
    }

    public void setDisallowEnterPictureInPictureWhileLaunching(boolean bl) {
        this.mDisallowEnterPictureInPictureWhileLaunching = bl;
    }

    public void setFreezeRecentTasksReordering() {
        this.mFreezeRecentTasksReordering = true;
    }

    public void setLaunchActivityType(int n) {
        this.mLaunchActivityType = n;
    }

    public ActivityOptions setLaunchBounds(Rect rect) {
        rect = rect != null ? new Rect(rect) : null;
        this.mLaunchBounds = rect;
        return this;
    }

    public ActivityOptions setLaunchDisplayId(int n) {
        this.mLaunchDisplayId = n;
        return this;
    }

    public void setLaunchTaskId(int n) {
        this.mLaunchTaskId = n;
    }

    public void setLaunchWindowingMode(int n) {
        this.mLaunchWindowingMode = n;
    }

    public ActivityOptions setLockTaskEnabled(boolean bl) {
        this.mLockTaskMode = bl;
        return this;
    }

    public void setPendingIntentLaunchFlags(int n) {
        this.mPendingIntentLaunchFlags = n;
    }

    public void setRemoteAnimationAdapter(RemoteAnimationAdapter remoteAnimationAdapter) {
        this.mRemoteAnimationAdapter = remoteAnimationAdapter;
    }

    public void setRotationAnimationHint(int n) {
        this.mRotationAnimationHint = n;
    }

    @UnsupportedAppUsage
    public void setSplitScreenCreateMode(int n) {
        this.mSplitScreenCreateMode = n;
    }

    public void setTaskOverlay(boolean bl, boolean bl2) {
        this.mTaskOverlay = bl;
        this.mTaskOverlayCanResume = bl2;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        Object object = this.mPackageName;
        if (object != null) {
            bundle.putString(KEY_PACKAGE_NAME, (String)object);
        }
        if ((object = this.mLaunchBounds) != null) {
            bundle.putParcelable(KEY_LAUNCH_BOUNDS, (Parcelable)object);
        }
        bundle.putInt(KEY_ANIM_TYPE, this.mAnimationType);
        object = this.mUsageTimeReport;
        if (object != null) {
            bundle.putParcelable(KEY_USAGE_TIME_REPORT, (Parcelable)object);
        }
        int n = this.mAnimationType;
        Object object2 = null;
        object = null;
        switch (n) {
            default: {
                break;
            }
            case 10: {
                bundle.putInt(KEY_ANIM_IN_PLACE_RES_ID, this.mCustomInPlaceResId);
                break;
            }
            case 5: {
                object = this.mTransitionReceiver;
                if (object != null) {
                    bundle.putParcelable(KEY_TRANSITION_COMPLETE_LISTENER, (Parcelable)object);
                }
                bundle.putBoolean(KEY_TRANSITION_IS_RETURNING, this.mIsReturning);
                bundle.putStringArrayList(KEY_TRANSITION_SHARED_ELEMENTS, this.mSharedElementNames);
                bundle.putParcelable(KEY_RESULT_DATA, this.mResultData);
                bundle.putInt(KEY_RESULT_CODE, this.mResultCode);
                bundle.putInt(KEY_EXIT_COORDINATOR_INDEX, this.mExitCoordinatorIndex);
                break;
            }
            case 3: 
            case 4: 
            case 8: 
            case 9: {
                object2 = this.mThumbnail;
                if (object2 != null) {
                    if ((object2 = ((Bitmap)object2).copy(Bitmap.Config.HARDWARE, false)) != null) {
                        bundle.putParcelable(KEY_ANIM_THUMBNAIL, ((Bitmap)object2).createGraphicBufferHandle());
                    } else {
                        Slog.w(TAG, "Failed to copy thumbnail");
                    }
                }
                bundle.putInt(KEY_ANIM_START_X, this.mStartX);
                bundle.putInt(KEY_ANIM_START_Y, this.mStartY);
                bundle.putInt(KEY_ANIM_WIDTH, this.mWidth);
                bundle.putInt(KEY_ANIM_HEIGHT, this.mHeight);
                object2 = this.mAnimationStartedListener;
                if (object2 != null) {
                    object = object2.asBinder();
                }
                bundle.putBinder(KEY_ANIM_START_LISTENER, (IBinder)object);
                break;
            }
            case 2: 
            case 11: {
                bundle.putInt(KEY_ANIM_START_X, this.mStartX);
                bundle.putInt(KEY_ANIM_START_Y, this.mStartY);
                bundle.putInt(KEY_ANIM_WIDTH, this.mWidth);
                bundle.putInt(KEY_ANIM_HEIGHT, this.mHeight);
                break;
            }
            case 1: {
                bundle.putInt(KEY_ANIM_ENTER_RES_ID, this.mCustomEnterResId);
                bundle.putInt(KEY_ANIM_EXIT_RES_ID, this.mCustomExitResId);
                IRemoteCallback iRemoteCallback = this.mAnimationStartedListener;
                object = object2;
                if (iRemoteCallback != null) {
                    object = iRemoteCallback.asBinder();
                }
                bundle.putBinder(KEY_ANIM_START_LISTENER, (IBinder)object);
            }
        }
        boolean bl = this.mLockTaskMode;
        if (bl) {
            bundle.putBoolean(KEY_LOCK_TASK_MODE, bl);
        }
        if ((n = this.mLaunchDisplayId) != -1) {
            bundle.putInt(KEY_LAUNCH_DISPLAY_ID, n);
        }
        if ((n = this.mLaunchWindowingMode) != 0) {
            bundle.putInt(KEY_LAUNCH_WINDOWING_MODE, n);
        }
        if ((n = this.mLaunchActivityType) != 0) {
            bundle.putInt(KEY_LAUNCH_ACTIVITY_TYPE, n);
        }
        if ((n = this.mLaunchTaskId) != -1) {
            bundle.putInt(KEY_LAUNCH_TASK_ID, n);
        }
        if ((n = this.mPendingIntentLaunchFlags) != 0) {
            bundle.putInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, n);
        }
        if (bl = this.mTaskOverlay) {
            bundle.putBoolean(KEY_TASK_OVERLAY, bl);
        }
        if (bl = this.mTaskOverlayCanResume) {
            bundle.putBoolean(KEY_TASK_OVERLAY_CAN_RESUME, bl);
        }
        if (bl = this.mAvoidMoveToFront) {
            bundle.putBoolean(KEY_AVOID_MOVE_TO_FRONT, bl);
        }
        if (bl = this.mFreezeRecentTasksReordering) {
            bundle.putBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, bl);
        }
        if ((n = this.mSplitScreenCreateMode) != 0) {
            bundle.putInt(KEY_SPLIT_SCREEN_CREATE_MODE, n);
        }
        if (bl = this.mDisallowEnterPictureInPictureWhileLaunching) {
            bundle.putBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, bl);
        }
        if ((object = this.mAnimSpecs) != null) {
            bundle.putParcelableArray(KEY_ANIM_SPECS, (Parcelable[])object);
        }
        if ((object = this.mAnimationFinishedListener) != null) {
            bundle.putBinder(KEY_ANIMATION_FINISHED_LISTENER, object.asBinder());
        }
        if ((object = this.mSpecsFuture) != null) {
            bundle.putBinder(KEY_SPECS_FUTURE, object.asBinder());
        }
        if ((n = this.mRotationAnimationHint) != -1) {
            bundle.putInt(KEY_ROTATION_ANIMATION_HINT, n);
        }
        if ((object = this.mAppVerificationBundle) != null) {
            bundle.putBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE, (Bundle)object);
        }
        if ((object = this.mRemoteAnimationAdapter) != null) {
            bundle.putParcelable(KEY_REMOTE_ANIMATION_ADAPTER, (Parcelable)object);
        }
        return bundle;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActivityOptions(");
        stringBuilder.append(this.hashCode());
        stringBuilder.append("), mPackageName=");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append(", mAnimationType=");
        stringBuilder.append(this.mAnimationType);
        stringBuilder.append(", mStartX=");
        stringBuilder.append(this.mStartX);
        stringBuilder.append(", mStartY=");
        stringBuilder.append(this.mStartY);
        stringBuilder.append(", mWidth=");
        stringBuilder.append(this.mWidth);
        stringBuilder.append(", mHeight=");
        stringBuilder.append(this.mHeight);
        return stringBuilder.toString();
    }

    public void update(ActivityOptions activityOptions) {
        Object object = activityOptions.mPackageName;
        if (object != null) {
            this.mPackageName = object;
        }
        this.mUsageTimeReport = activityOptions.mUsageTimeReport;
        this.mTransitionReceiver = null;
        this.mSharedElementNames = null;
        this.mIsReturning = false;
        this.mResultData = null;
        this.mResultCode = 0;
        this.mExitCoordinatorIndex = 0;
        this.mAnimationType = activityOptions.mAnimationType;
        switch (activityOptions.mAnimationType) {
            default: {
                break;
            }
            case 10: {
                this.mCustomInPlaceResId = activityOptions.mCustomInPlaceResId;
                break;
            }
            case 5: {
                this.mTransitionReceiver = activityOptions.mTransitionReceiver;
                this.mSharedElementNames = activityOptions.mSharedElementNames;
                this.mIsReturning = activityOptions.mIsReturning;
                this.mThumbnail = null;
                this.mAnimationStartedListener = null;
                this.mResultData = activityOptions.mResultData;
                this.mResultCode = activityOptions.mResultCode;
                this.mExitCoordinatorIndex = activityOptions.mExitCoordinatorIndex;
                break;
            }
            case 3: 
            case 4: 
            case 8: 
            case 9: {
                this.mThumbnail = activityOptions.mThumbnail;
                this.mStartX = activityOptions.mStartX;
                this.mStartY = activityOptions.mStartY;
                this.mWidth = activityOptions.mWidth;
                this.mHeight = activityOptions.mHeight;
                object = this.mAnimationStartedListener;
                if (object != null) {
                    try {
                        object.sendResult(null);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
                this.mAnimationStartedListener = activityOptions.mAnimationStartedListener;
                break;
            }
            case 2: {
                this.mStartX = activityOptions.mStartX;
                this.mStartY = activityOptions.mStartY;
                this.mWidth = activityOptions.mWidth;
                this.mHeight = activityOptions.mHeight;
                object = this.mAnimationStartedListener;
                if (object != null) {
                    try {
                        object.sendResult(null);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
                this.mAnimationStartedListener = null;
                break;
            }
            case 1: {
                this.mCustomEnterResId = activityOptions.mCustomEnterResId;
                this.mCustomExitResId = activityOptions.mCustomExitResId;
                this.mThumbnail = null;
                object = this.mAnimationStartedListener;
                if (object != null) {
                    try {
                        object.sendResult(null);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
                this.mAnimationStartedListener = activityOptions.mAnimationStartedListener;
            }
        }
        this.mLockTaskMode = activityOptions.mLockTaskMode;
        this.mAnimSpecs = activityOptions.mAnimSpecs;
        this.mAnimationFinishedListener = activityOptions.mAnimationFinishedListener;
        this.mSpecsFuture = activityOptions.mSpecsFuture;
        this.mRemoteAnimationAdapter = activityOptions.mRemoteAnimationAdapter;
    }

    private static class HideWindowListener
    extends TransitionListenerAdapter
    implements ExitTransitionCoordinator.HideSharedElementsCallback {
        private final ExitTransitionCoordinator mExit;
        private boolean mSharedElementHidden;
        private ArrayList<View> mSharedElements;
        private boolean mTransitionEnded;
        private final boolean mWaitingForTransition;
        private final Window mWindow;

        public HideWindowListener(Window object, ExitTransitionCoordinator exitTransitionCoordinator) {
            this.mWindow = object;
            this.mExit = exitTransitionCoordinator;
            this.mSharedElements = new ArrayList(exitTransitionCoordinator.mSharedElements);
            object = this.mWindow.getExitTransition();
            if (object != null) {
                ((Transition)object).addListener(this);
                this.mWaitingForTransition = true;
            } else {
                this.mWaitingForTransition = false;
            }
            object = this.mWindow.getDecorView();
            if (object != null) {
                if (((View)object).getTag(16908849) == null) {
                    ((View)object).setTagInternal(16908849, exitTransitionCoordinator);
                } else {
                    throw new IllegalStateException("Cannot start a transition while one is running");
                }
            }
        }

        private void hideWhenDone() {
            if (this.mSharedElementHidden && (!this.mWaitingForTransition || this.mTransitionEnded)) {
                this.mExit.resetViews();
                int n = this.mSharedElements.size();
                for (int i = 0; i < n; ++i) {
                    this.mSharedElements.get(i).requestLayout();
                }
                View view = this.mWindow.getDecorView();
                if (view != null) {
                    view.setTagInternal(16908849, null);
                    view.setVisibility(8);
                }
            }
        }

        @Override
        public void hideSharedElements() {
            this.mSharedElementHidden = true;
            this.hideWhenDone();
        }

        @Override
        public void onTransitionEnd(Transition transition2) {
            this.mTransitionEnded = true;
            this.hideWhenDone();
            transition2.removeListener(this);
        }
    }

    public static interface OnAnimationFinishedListener {
        public void onAnimationFinished();
    }

    public static interface OnAnimationStartedListener {
        public void onAnimationStarted();
    }

}

