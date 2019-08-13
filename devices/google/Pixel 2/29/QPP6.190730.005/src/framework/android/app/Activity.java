/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.ActivityTransitionState;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.DirectAction;
import android.app.Fragment;
import android.app.FragmentController;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.IRequestFinishCallback;
import android.app.Instrumentation;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.PictureInPictureArgs;
import android.app.PictureInPictureParams;
import android.app.ProfilerInfo;
import android.app.ResourcesManager;
import android.app.SearchManager;
import android.app.SharedElementCallback;
import android.app.TaskStackBuilder;
import android.app.VoiceInteractor;
import android.app._$$Lambda$Activity$1$pR5b3qDyhldlD2RtkXoHHxgyGPU;
import android.app.assist.AssistContent;
import android.content.AutofillOptions;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.GraphicsEnvironment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.RemoteAnimationDefinition;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityEvent;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillPopupWindow;
import android.view.autofill.Helper;
import android.view.autofill.IAutofillWindowPresenter;
import android.view.contentcapture.ContentCaptureManager;
import android.widget.Toast;
import android.widget.Toolbar;
import com.android.internal.R;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.ToolbarActionBar;
import com.android.internal.app.WindowDecorActionBar;
import com.android.internal.policy.PhoneWindow;
import dalvik.system.VMRuntime;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Activity
extends ContextThemeWrapper
implements LayoutInflater.Factory2,
Window.Callback,
KeyEvent.Callback,
View.OnCreateContextMenuListener,
ComponentCallbacks2,
Window.OnWindowDismissedCallback,
Window.WindowControllerCallback,
AutofillManager.AutofillClient,
ContentCaptureManager.ContentCaptureClient {
    private static final String AUTOFILL_RESET_NEEDED = "@android:autofillResetNeeded";
    private static final String AUTO_FILL_AUTH_WHO_PREFIX = "@android:autoFillAuth:";
    private static final int CONTENT_CAPTURE_PAUSE = 3;
    private static final int CONTENT_CAPTURE_RESUME = 2;
    private static final int CONTENT_CAPTURE_START = 1;
    private static final int CONTENT_CAPTURE_STOP = 4;
    private static final boolean DEBUG_LIFECYCLE = false;
    public static final int DEFAULT_KEYS_DIALER = 1;
    public static final int DEFAULT_KEYS_DISABLE = 0;
    public static final int DEFAULT_KEYS_SEARCH_GLOBAL = 4;
    public static final int DEFAULT_KEYS_SEARCH_LOCAL = 3;
    public static final int DEFAULT_KEYS_SHORTCUT = 2;
    public static final int DONT_FINISH_TASK_WITH_ACTIVITY = 0;
    public static final int FINISH_TASK_WITH_ACTIVITY = 2;
    public static final int FINISH_TASK_WITH_ROOT_ACTIVITY = 1;
    protected static final int[] FOCUSED_STATE_SET = new int[]{16842908};
    @UnsupportedAppUsage
    static final String FRAGMENTS_TAG = "android:fragments";
    private static final String HAS_CURENT_PERMISSIONS_REQUEST_KEY = "android:hasCurrentPermissionsRequest";
    private static final String KEYBOARD_SHORTCUTS_RECEIVER_PKG_NAME = "com.android.systemui";
    private static final String LAST_AUTOFILL_ID = "android:lastAutofillId";
    private static final int LOG_AM_ON_ACTIVITY_RESULT_CALLED = 30062;
    private static final int LOG_AM_ON_CREATE_CALLED = 30057;
    private static final int LOG_AM_ON_DESTROY_CALLED = 30060;
    private static final int LOG_AM_ON_PAUSE_CALLED = 30021;
    private static final int LOG_AM_ON_RESTART_CALLED = 30058;
    private static final int LOG_AM_ON_RESUME_CALLED = 30022;
    private static final int LOG_AM_ON_START_CALLED = 30059;
    private static final int LOG_AM_ON_STOP_CALLED = 30049;
    private static final int LOG_AM_ON_TOP_RESUMED_GAINED_CALLED = 30064;
    private static final int LOG_AM_ON_TOP_RESUMED_LOST_CALLED = 30065;
    private static final String REQUEST_PERMISSIONS_WHO_PREFIX = "@android:requestPermissions:";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_FIRST_USER = 1;
    public static final int RESULT_OK = -1;
    private static final String SAVED_DIALOGS_TAG = "android:savedDialogs";
    private static final String SAVED_DIALOG_ARGS_KEY_PREFIX = "android:dialog_args_";
    private static final String SAVED_DIALOG_IDS_KEY = "android:savedDialogIds";
    private static final String SAVED_DIALOG_KEY_PREFIX = "android:dialog_";
    private static final String TAG = "Activity";
    private static final String WINDOW_HIERARCHY_TAG = "android:viewHierarchyState";
    ActionBar mActionBar = null;
    private int mActionModeTypeStarting = 0;
    @UnsupportedAppUsage
    ActivityInfo mActivityInfo;
    private final ArrayList<Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new ArrayList();
    @UnsupportedAppUsage
    ActivityTransitionState mActivityTransitionState = new ActivityTransitionState();
    @UnsupportedAppUsage
    private Application mApplication;
    private IBinder mAssistToken;
    private boolean mAutoFillIgnoreFirstResumePause;
    private boolean mAutoFillResetNeeded;
    private AutofillManager mAutofillManager;
    private AutofillPopupWindow mAutofillPopupWindow;
    @UnsupportedAppUsage
    boolean mCalled;
    private boolean mCanEnterPictureInPicture = false;
    private boolean mChangeCanvasToTranslucent;
    boolean mChangingConfigurations = false;
    @UnsupportedAppUsage
    private ComponentName mComponent;
    @UnsupportedAppUsage
    int mConfigChangeFlags;
    private ContentCaptureManager mContentCaptureManager;
    @UnsupportedAppUsage
    Configuration mCurrentConfig;
    View mDecor = null;
    private int mDefaultKeyMode = 0;
    private SpannableStringBuilder mDefaultKeySsb = null;
    @UnsupportedAppUsage
    private boolean mDestroyed;
    private boolean mDoReportFullyDrawn = true;
    @UnsupportedAppUsage
    String mEmbeddedID;
    private boolean mEnableDefaultActionBarUp;
    boolean mEnterAnimationComplete;
    SharedElementCallback mEnterTransitionListener = SharedElementCallback.NULL_CALLBACK;
    SharedElementCallback mExitTransitionListener = SharedElementCallback.NULL_CALLBACK;
    @UnsupportedAppUsage
    boolean mFinished;
    @UnsupportedAppUsage
    final FragmentController mFragments = FragmentController.createController(new HostCallbacks());
    @UnsupportedAppUsage
    final Handler mHandler = new Handler();
    private boolean mHasCurrentPermissionsRequest;
    @UnsupportedAppUsage
    private int mIdent;
    private final Object mInstanceTracker = StrictMode.trackActivity(this);
    @UnsupportedAppUsage
    private Instrumentation mInstrumentation;
    @UnsupportedAppUsage
    Intent mIntent;
    private int mLastAutofillId = 1073741823;
    @UnsupportedAppUsage
    NonConfigurationInstances mLastNonConfigurationInstances;
    @UnsupportedAppUsage
    ActivityThread mMainThread;
    @GuardedBy(value={"mManagedCursors"})
    private final ArrayList<ManagedCursor> mManagedCursors = new ArrayList();
    private SparseArray<ManagedDialog> mManagedDialogs;
    private MenuInflater mMenuInflater;
    @UnsupportedAppUsage
    Activity mParent;
    @UnsupportedAppUsage
    String mReferrer;
    private boolean mRestoredFromBundle;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    int mResultCode = 0;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    Intent mResultData = null;
    @UnsupportedAppUsage
    boolean mResumed;
    private SearchEvent mSearchEvent;
    private SearchManager mSearchManager;
    boolean mStartedActivity;
    @UnsupportedAppUsage
    boolean mStopped;
    private ActivityManager.TaskDescription mTaskDescription = new ActivityManager.TaskDescription();
    @UnsupportedAppUsage
    private CharSequence mTitle;
    private int mTitleColor = 0;
    private boolean mTitleReady = false;
    @UnsupportedAppUsage
    private IBinder mToken;
    private TranslucentConversionListener mTranslucentCallback;
    private Thread mUiThread;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    boolean mVisibleFromClient = true;
    boolean mVisibleFromServer = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    VoiceInteractor mVoiceInteractor;
    @UnsupportedAppUsage
    private Window mWindow;
    @UnsupportedAppUsage
    boolean mWindowAdded = false;
    @UnsupportedAppUsage
    private WindowManager mWindowManager;

    private void cancelInputsAndStartExitTransition(Bundle bundle) {
        Object object = this.mWindow;
        object = object != null ? ((Window)object).peekDecorView() : null;
        if (object != null) {
            ((View)object).cancelPendingInputEvents();
        }
        if (bundle != null) {
            this.mActivityTransitionState.startExitOutTransition(this, bundle);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object[] collectActivityLifecycleCallbacks() {
        Object[] arrobject = null;
        ArrayList<Application.ActivityLifecycleCallbacks> arrayList = this.mActivityLifecycleCallbacks;
        synchronized (arrayList) {
            if (this.mActivityLifecycleCallbacks.size() <= 0) return arrobject;
            return this.mActivityLifecycleCallbacks.toArray();
        }
    }

    private Dialog createDialog(Integer object, Bundle bundle, Bundle bundle2) {
        if ((object = this.onCreateDialog((Integer)object, bundle2)) == null) {
            return null;
        }
        ((Dialog)object).dispatchOnCreate(bundle);
        return object;
    }

    private boolean deviceSupportsPictureInPictureMode() {
        return this.getPackageManager().hasSystemFeature("android.software.picture_in_picture");
    }

    private void dispatchActivityCreated(Bundle bundle) {
        this.getApplication().dispatchActivityCreated(this, bundle);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityCreated(this, bundle);
            }
        }
    }

    private void dispatchActivityDestroyed() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityDestroyed(this);
            }
        }
        this.getApplication().dispatchActivityDestroyed(this);
    }

    private void dispatchActivityPaused() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPaused(this);
            }
        }
        this.getApplication().dispatchActivityPaused(this);
    }

    private void dispatchActivityPostCreated(Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPostCreated(this, bundle);
            }
        }
        this.getApplication().dispatchActivityPostCreated(this, bundle);
    }

    private void dispatchActivityPostDestroyed() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPostDestroyed(this);
            }
        }
        this.getApplication().dispatchActivityPostDestroyed(this);
    }

    private void dispatchActivityPostPaused() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPostPaused(this);
            }
        }
        this.getApplication().dispatchActivityPostPaused(this);
    }

    private void dispatchActivityPostResumed() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPostResumed(this);
            }
        }
        this.getApplication().dispatchActivityPostResumed(this);
    }

    private void dispatchActivityPostSaveInstanceState(Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPostSaveInstanceState(this, bundle);
            }
        }
        this.getApplication().dispatchActivityPostSaveInstanceState(this, bundle);
    }

    private void dispatchActivityPostStarted() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPostStarted(this);
            }
        }
        this.getApplication().dispatchActivityPostStarted(this);
    }

    private void dispatchActivityPostStopped() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPostStopped(this);
            }
        }
        this.getApplication().dispatchActivityPostStopped(this);
    }

    private void dispatchActivityPreCreated(Bundle bundle) {
        this.getApplication().dispatchActivityPreCreated(this, bundle);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPreCreated(this, bundle);
            }
        }
    }

    private void dispatchActivityPreDestroyed() {
        this.getApplication().dispatchActivityPreDestroyed(this);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPreDestroyed(this);
            }
        }
    }

    private void dispatchActivityPrePaused() {
        this.getApplication().dispatchActivityPrePaused(this);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPrePaused(this);
            }
        }
    }

    private void dispatchActivityPreResumed() {
        this.getApplication().dispatchActivityPreResumed(this);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPreResumed(this);
            }
        }
    }

    private void dispatchActivityPreSaveInstanceState(Bundle bundle) {
        this.getApplication().dispatchActivityPreSaveInstanceState(this, bundle);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPreSaveInstanceState(this, bundle);
            }
        }
    }

    private void dispatchActivityPreStarted() {
        this.getApplication().dispatchActivityPreStarted(this);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPreStarted(this);
            }
        }
    }

    private void dispatchActivityPreStopped() {
        this.getApplication().dispatchActivityPreStopped(this);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityPreStopped(this);
            }
        }
    }

    private void dispatchActivityResumed() {
        this.getApplication().dispatchActivityResumed(this);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityResumed(this);
            }
        }
    }

    private void dispatchActivitySaveInstanceState(Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivitySaveInstanceState(this, bundle);
            }
        }
        this.getApplication().dispatchActivitySaveInstanceState(this, bundle);
    }

    private void dispatchActivityStarted() {
        this.getApplication().dispatchActivityStarted(this);
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityStarted(this);
            }
        }
    }

    private void dispatchActivityStopped() {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                ((Application.ActivityLifecycleCallbacks)arrobject[i]).onActivityStopped(this);
            }
        }
        this.getApplication().dispatchActivityStopped(this);
    }

    private void dispatchRequestPermissionsResult(int n, Intent arrn) {
        this.mHasCurrentPermissionsRequest = false;
        String[] arrstring = arrn != null ? arrn.getStringArrayExtra("android.content.pm.extra.REQUEST_PERMISSIONS_NAMES") : new String[0];
        arrn = arrn != null ? arrn.getIntArrayExtra("android.content.pm.extra.REQUEST_PERMISSIONS_RESULTS") : new int[0];
        this.onRequestPermissionsResult(n, arrstring, arrn);
    }

    private void dispatchRequestPermissionsResultToFragment(int n, Intent arrn, Fragment fragment) {
        String[] arrstring = arrn != null ? arrn.getStringArrayExtra("android.content.pm.extra.REQUEST_PERMISSIONS_NAMES") : new String[0];
        arrn = arrn != null ? arrn.getIntArrayExtra("android.content.pm.extra.REQUEST_PERMISSIONS_RESULTS") : new int[0];
        fragment.onRequestPermissionsResult(n, arrstring, arrn);
    }

    private void enableAutofillCompatibilityIfNeeded() {
        AutofillManager autofillManager;
        if (this.isAutofillCompatibilityEnabled() && (autofillManager = this.getSystemService(AutofillManager.class)) != null) {
            autofillManager.enableCompatibilityMode();
        }
    }

    private void ensureSearchManager() {
        if (this.mSearchManager != null) {
            return;
        }
        try {
            SearchManager searchManager;
            this.mSearchManager = searchManager = new SearchManager(this, null);
            return;
        }
        catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
            throw new IllegalStateException(serviceNotFoundException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private void finish(int var1_1) {
        block6 : {
            block7 : {
                var2_2 = this.mParent;
                if (var2_2 != null) break block7;
                // MONITORENTER : this
                var3_4 = this.mResultCode;
                var2_2 = this.mResultData;
                // MONITOREXIT : this
                if (var2_2 == null) ** GOTO lbl10
                try {
                    var2_2.prepareToLeaveProcess(this);
lbl10: // 2 sources:
                    if (ActivityTaskManager.getService().finishActivity(this.mToken, var3_4, (Intent)var2_2, var1_1)) {
                        this.mFinished = true;
                    }
                    break block6;
                }
                catch (RemoteException var2_3) {}
                break block6;
            }
            var2_2.finishFromChild(this);
        }
        var2_2 = this.mIntent;
        if (var2_2 == null) return;
        if (var2_2.hasExtra("android.view.autofill.extra.RESTORE_SESSION_TOKEN") == false) return;
        this.getAutofillManager().onPendingSaveUi(2, this.mIntent.getIBinderExtra("android.view.autofill.extra.RESTORE_SESSION_TOKEN"));
    }

    private AutofillManager getAutofillManager() {
        if (this.mAutofillManager == null) {
            this.mAutofillManager = this.getSystemService(AutofillManager.class);
        }
        return this.mAutofillManager;
    }

    private ContentCaptureManager getContentCaptureManager() {
        if (!UserHandle.isApp(Process.myUid())) {
            return null;
        }
        if (this.mContentCaptureManager == null) {
            this.mContentCaptureManager = this.getSystemService(ContentCaptureManager.class);
        }
        return this.mContentCaptureManager;
    }

    private String getContentCaptureTypeAsString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("UNKNOW-");
                        stringBuilder.append(n);
                        return stringBuilder.toString();
                    }
                    return "STOP";
                }
                return "PAUSE";
            }
            return "RESUME";
        }
        return "START";
    }

    private static native String getDlWarning();

    private void initWindowDecorActionBar() {
        Window window = this.getWindow();
        window.getDecorView();
        if (!this.isChild() && window.hasFeature(8) && this.mActionBar == null) {
            this.mActionBar = new WindowDecorActionBar(this);
            this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            this.mWindow.setDefaultIcon(this.mActivityInfo.getIconResource());
            this.mWindow.setDefaultLogo(this.mActivityInfo.getLogoResource());
            return;
        }
    }

    private IllegalArgumentException missingDialog(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("no dialog with id ");
        stringBuilder.append(n);
        stringBuilder.append(" was ever shown via Activity#showDialog");
        return new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void notifyContentCaptureManagerIfNeeded(int var1_1) {
        block6 : {
            if (Trace.isTagEnabled(64L)) {
                var2_2 = new StringBuilder();
                var2_2.append("notifyContentCapture(");
                var2_2.append(this.getContentCaptureTypeAsString(var1_1));
                var2_2.append(") for ");
                var2_2.append(this.mComponent.toShortString());
                Trace.traceBegin(64L, var2_2.toString());
            }
            var2_2 = this.getContentCaptureManager();
            if (var2_2 != null) break block6;
            Trace.traceEnd(64L);
            return;
        }
        if (var1_1 == 1) ** GOTO lbl39
        if (var1_1 == 2) ** GOTO lbl37
        if (var1_1 == 3) ** GOTO lbl35
        if (var1_1 == 4) ** GOTO lbl33
        try {
            var2_2 = new StringBuilder();
            var2_2.append("Invalid @ContentCaptureNotificationType: ");
            var2_2.append(var1_1);
            Log.wtf("Activity", var2_2.toString());
            return;
lbl33: // 1 sources:
            var2_2.onActivityDestroyed();
            return;
lbl35: // 1 sources:
            var2_2.onActivityPaused();
            return;
lbl37: // 1 sources:
            var2_2.onActivityResumed();
            return;
lbl39: // 1 sources:
            var3_4 = this.getWindow();
            if (var3_4 != null) {
                var2_2.updateWindowAttributes(var3_4.getAttributes());
            }
            var2_2.onActivityCreated(this.mToken, this.getComponentName());
            return;
        }
        finally {
            Trace.traceEnd(64L);
        }
    }

    private void restoreHasCurrentPermissionRequest(Bundle bundle) {
        if (bundle != null) {
            this.mHasCurrentPermissionsRequest = bundle.getBoolean("android:hasCurrentPermissionsRequest", false);
        }
    }

    private void restoreManagedDialogs(Bundle bundle) {
        if ((bundle = bundle.getBundle("android:savedDialogs")) == null) {
            return;
        }
        int[] arrn = bundle.getIntArray("android:savedDialogIds");
        int n = arrn.length;
        this.mManagedDialogs = new SparseArray<E>(n);
        for (int i = 0; i < n; ++i) {
            Integer n2 = arrn[i];
            Bundle bundle2 = bundle.getBundle(Activity.savedDialogKeyFor(n2));
            if (bundle2 == null) continue;
            ManagedDialog managedDialog = new ManagedDialog();
            managedDialog.mArgs = bundle.getBundle(Activity.savedDialogArgsKeyFor(n2));
            managedDialog.mDialog = this.createDialog(n2, bundle2, managedDialog.mArgs);
            if (managedDialog.mDialog == null) continue;
            this.mManagedDialogs.put(n2, managedDialog);
            this.onPrepareDialog(n2, managedDialog.mDialog, managedDialog.mArgs);
            managedDialog.mDialog.onRestoreInstanceState(bundle2);
        }
    }

    @UnsupportedAppUsage
    private void saveManagedDialogs(Bundle bundle) {
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray == null) {
            return;
        }
        int n = sparseArray.size();
        if (n == 0) {
            return;
        }
        Bundle bundle2 = new Bundle();
        int[] arrn = new int[this.mManagedDialogs.size()];
        for (int i = 0; i < n; ++i) {
            int n2;
            arrn[i] = n2 = this.mManagedDialogs.keyAt(i);
            sparseArray = this.mManagedDialogs.valueAt(i);
            bundle2.putBundle(Activity.savedDialogKeyFor(n2), ((ManagedDialog)sparseArray).mDialog.onSaveInstanceState());
            if (((ManagedDialog)sparseArray).mArgs == null) continue;
            bundle2.putBundle(Activity.savedDialogArgsKeyFor(n2), ((ManagedDialog)sparseArray).mArgs);
        }
        bundle2.putIntArray("android:savedDialogIds", arrn);
        bundle.putBundle("android:savedDialogs", bundle2);
    }

    private static String savedDialogArgsKeyFor(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android:dialog_args_");
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    private static String savedDialogKeyFor(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android:dialog_");
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void startIntentSenderForResultInner(IntentSender var1_1, String var2_4, int var3_5, Intent var4_6, int var5_7, int var6_8, Bundle var7_9) throws IntentSender.SendIntentException {
        block6 : {
            var8_10 = this.transferSpringboardActivityOptions((Bundle)var7_9);
            var7_9 = null;
            if (var4_6 == null) ** GOTO lbl10
            try {
                var4_6.migrateExtraStreamToClipData();
                var4_6.prepareToLeaveProcess(this);
                var7_9 = var4_6.resolveTypeIfNeeded(this.getContentResolver());
lbl10: // 2 sources:
                var9_11 = ActivityTaskManager.getService();
                var10_12 = this.mMainThread.getApplicationThread();
                var11_13 = var1_1 != null ? var1_1.getTarget() : null;
                var5_7 = var9_11.startActivityIntentSender(var10_12, var11_13, (IBinder)(var1_1 = var1_1 != null ? var1_1.getWhitelistToken() : null), var4_6, (String)var7_9, this.mToken, var2_4, var3_5, var5_7, var6_8, var8_10);
                if (var5_7 == -96) {
                    var1_1 = new IntentSender.SendIntentException();
                    throw var1_1;
                }
                Instrumentation.checkStartActivityResult(var5_7, null);
                if (var8_10 != null) {
                    this.cancelInputsAndStartExitTransition(var8_10);
                }
                break block6;
            }
            catch (RemoteException var1_2) {}
            break block6;
            catch (RemoteException var1_3) {
                // empty catch block
            }
        }
        if (var3_5 < 0) return;
        this.mStartedActivity = true;
    }

    private void storeHasCurrentPermissionRequest(Bundle bundle) {
        if (bundle != null && this.mHasCurrentPermissionsRequest) {
            bundle.putBoolean("android:hasCurrentPermissionsRequest", true);
        }
    }

    private Bundle transferSpringboardActivityOptions(Bundle bundle) {
        Object object;
        if (bundle == null && (object = this.mWindow) != null && !((Window)object).isActive() && (object = this.getActivityOptions()) != null && ((ActivityOptions)object).getAnimationType() == 5) {
            return ((ActivityOptions)object).toBundle();
        }
        return bundle;
    }

    private void writeEventLog(int n, String string2) {
        EventLog.writeEvent(n, UserHandle.myUserId(), this.getComponentName().getClassName(), string2);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getWindow().addContentView(view, layoutParams);
        this.initWindowDecorActionBar();
    }

    @UnsupportedAppUsage
    final void attach(Context object, ActivityThread object2, Instrumentation object3, IBinder iBinder, int n, Application application, Intent intent, ActivityInfo activityInfo, CharSequence charSequence, Activity activity, String string2, NonConfigurationInstances nonConfigurationInstances, Configuration configuration, String string3, IVoiceInteractor iVoiceInteractor, Window window, ViewRootImpl.ActivityConfigCallback activityConfigCallback, IBinder iBinder2) {
        this.attachBaseContext((Context)object);
        this.mFragments.attachHost(null);
        this.mWindow = new PhoneWindow(this, window, activityConfigCallback);
        this.mWindow.setWindowControllerCallback(this);
        this.mWindow.setCallback(this);
        this.mWindow.setOnWindowDismissedCallback(this);
        this.mWindow.getLayoutInflater().setPrivateFactory(this);
        if (activityInfo.softInputMode != 0) {
            this.mWindow.setSoftInputMode(activityInfo.softInputMode);
        }
        if (activityInfo.uiOptions != 0) {
            this.mWindow.setUiOptions(activityInfo.uiOptions);
        }
        this.mUiThread = Thread.currentThread();
        this.mMainThread = object2;
        this.mInstrumentation = object3;
        this.mToken = iBinder;
        this.mAssistToken = iBinder2;
        this.mIdent = n;
        this.mApplication = application;
        this.mIntent = intent;
        this.mReferrer = string3;
        this.mComponent = intent.getComponent();
        this.mActivityInfo = activityInfo;
        this.mTitle = charSequence;
        this.mParent = activity;
        this.mEmbeddedID = string2;
        this.mLastNonConfigurationInstances = nonConfigurationInstances;
        if (iVoiceInteractor != null) {
            this.mVoiceInteractor = nonConfigurationInstances != null ? nonConfigurationInstances.voiceInteractor : new VoiceInteractor(iVoiceInteractor, this, this, Looper.myLooper());
        }
        object2 = this.mWindow;
        object = (WindowManager)((Context)object).getSystemService("window");
        iBinder = this.mToken;
        object3 = this.mComponent.flattenToString();
        boolean bl = (activityInfo.flags & 512) != 0;
        ((Window)object2).setWindowManager((WindowManager)object, iBinder, (String)object3, bl);
        object = this.mParent;
        if (object != null) {
            this.mWindow.setContainer(((Activity)object).getWindow());
        }
        this.mWindowManager = this.mWindow.getWindowManager();
        this.mCurrentConfig = configuration;
        this.mWindow.setColorMode(activityInfo.colorMode);
        this.setAutofillOptions(application.getAutofillOptions());
        this.setContentCaptureOptions(application.getContentCaptureOptions());
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        if (context != null) {
            context.setAutofillClient(this);
            context.setContentCaptureOptions(this.getContentCaptureOptions());
        }
    }

    @Override
    public final void autofillClientAuthenticate(int n, IntentSender intentSender, Intent intent) {
        try {
            this.startIntentSenderForResultInner(intentSender, "@android:autoFillAuth:", n, intent, 0, 0, null);
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("authenticate() failed for intent:");
            stringBuilder.append(intentSender);
            Log.e("Activity", stringBuilder.toString(), sendIntentException);
        }
    }

    @Override
    public final void autofillClientDispatchUnhandledKey(View object, KeyEvent keyEvent) {
        if ((object = ((View)object).getViewRootImpl()) != null) {
            ((ViewRootImpl)object).dispatchKeyFromAutofill(keyEvent);
        }
    }

    @Override
    public final View autofillClientFindViewByAccessibilityIdTraversal(int n, int n2) {
        ArrayList<ViewRootImpl> arrayList = WindowManagerGlobal.getInstance().getRootViews(this.getActivityToken());
        for (int i = 0; i < arrayList.size(); ++i) {
            View view = arrayList.get(i).getView();
            if (view == null || view.getAccessibilityWindowId() != n2 || (view = view.findViewByAccessibilityIdTraversal(n)) == null) continue;
            return view;
        }
        return null;
    }

    @Override
    public final View autofillClientFindViewByAutofillIdTraversal(AutofillId autofillId) {
        ArrayList<ViewRootImpl> arrayList = WindowManagerGlobal.getInstance().getRootViews(this.getActivityToken());
        for (int i = 0; i < arrayList.size(); ++i) {
            View view = arrayList.get(i).getView();
            if (view == null || (view = view.findViewByAutofillIdTraversal(autofillId.getViewId())) == null) continue;
            return view;
        }
        return null;
    }

    @Override
    public final View[] autofillClientFindViewsByAutofillIdTraversal(AutofillId[] arrautofillId) {
        View[] arrview = new View[arrautofillId.length];
        ArrayList<ViewRootImpl> arrayList = WindowManagerGlobal.getInstance().getRootViews(this.getActivityToken());
        for (int i = 0; i < arrayList.size(); ++i) {
            View view = arrayList.get(i).getView();
            if (view == null) continue;
            int n = arrautofillId.length;
            for (int j = 0; j < n; ++j) {
                if (arrview[j] != null) continue;
                arrview[j] = view.findViewByAutofillIdTraversal(arrautofillId[j].getViewId());
            }
        }
        return arrview;
    }

    @Override
    public final IBinder autofillClientGetActivityToken() {
        return this.getActivityToken();
    }

    @Override
    public final ComponentName autofillClientGetComponentName() {
        return this.getComponentName();
    }

    @Override
    public AutofillId autofillClientGetNextAutofillId() {
        return new AutofillId(this.getNextAutofillId());
    }

    @Override
    public final boolean[] autofillClientGetViewVisibility(AutofillId[] object) {
        int n = ((AutofillId[])object).length;
        boolean[] arrbl = new boolean[n];
        for (int i = 0; i < n; ++i) {
            AutofillId autofillId = object[i];
            View view = this.autofillClientFindViewByAutofillIdTraversal(autofillId);
            if (view == null) continue;
            arrbl[i] = !autofillId.isVirtualInt() ? view.isVisibleToUser() : view.isVisibleToUserForAutofill(autofillId.getVirtualChildIntId());
        }
        if (Helper.sVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("autofillClientGetViewVisibility(): ");
            ((StringBuilder)object).append(Arrays.toString(arrbl));
            Log.v("Activity", ((StringBuilder)object).toString());
        }
        return arrbl;
    }

    @Override
    public final boolean autofillClientIsCompatibilityModeEnabled() {
        return this.isAutofillCompatibilityEnabled();
    }

    @Override
    public final boolean autofillClientIsFillUiShowing() {
        AutofillPopupWindow autofillPopupWindow = this.mAutofillPopupWindow;
        boolean bl = autofillPopupWindow != null && autofillPopupWindow.isShowing();
        return bl;
    }

    @Override
    public final boolean autofillClientIsVisibleForAutofill() {
        return this.mStopped ^ true;
    }

    @Override
    public final boolean autofillClientRequestHideFillUi() {
        AutofillPopupWindow autofillPopupWindow = this.mAutofillPopupWindow;
        if (autofillPopupWindow == null) {
            return false;
        }
        autofillPopupWindow.dismiss();
        this.mAutofillPopupWindow = null;
        return true;
    }

    @Override
    public final boolean autofillClientRequestShowFillUi(View view, int n, int n2, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
        boolean bl;
        AutofillPopupWindow autofillPopupWindow = this.mAutofillPopupWindow;
        if (autofillPopupWindow == null) {
            bl = false;
            this.mAutofillPopupWindow = new AutofillPopupWindow(iAutofillWindowPresenter);
        } else {
            bl = autofillPopupWindow.isShowing();
        }
        this.mAutofillPopupWindow.update(view, 0, 0, n, n2, rect);
        bl = !bl && this.mAutofillPopupWindow.isShowing();
        return bl;
    }

    @Override
    public final void autofillClientResetableStateAvailable() {
        this.mAutoFillResetNeeded = true;
    }

    @Override
    public final void autofillClientRunOnUiThread(Runnable runnable) {
        this.runOnUiThread(runnable);
    }

    @Override
    public boolean canStartActivityForResult() {
        return true;
    }

    public void closeContextMenu() {
        if (this.mWindow.hasFeature(6)) {
            this.mWindow.closePanel(6);
        }
    }

    public void closeOptionsMenu() {
        ActionBar actionBar;
        if (this.mWindow.hasFeature(0) && ((actionBar = this.mActionBar) == null || !actionBar.closeOptionsMenu())) {
            this.mWindow.closePanel(0);
        }
    }

    @Override
    public final ComponentName contentCaptureClientGetComponentName() {
        return this.getComponentName();
    }

    @SystemApi
    public void convertFromTranslucent() {
        try {
            this.mTranslucentCallback = null;
            if (ActivityTaskManager.getService().convertFromTranslucent(this.mToken)) {
                WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, true);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public boolean convertToTranslucent(TranslucentConversionListener object, ActivityOptions activityOptions) {
        boolean bl;
        try {
            this.mTranslucentCallback = object;
            IActivityTaskManager iActivityTaskManager = ActivityTaskManager.getService();
            IBinder iBinder = this.mToken;
            object = activityOptions == null ? null : activityOptions.toBundle();
            this.mChangeCanvasToTranslucent = iActivityTaskManager.convertToTranslucent(iBinder, (Bundle)object);
            WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
            bl = true;
        }
        catch (RemoteException remoteException) {
            this.mChangeCanvasToTranslucent = false;
            bl = false;
        }
        if (!this.mChangeCanvasToTranslucent && (object = this.mTranslucentCallback) != null) {
            object.onTranslucentConversionComplete(bl);
        }
        return this.mChangeCanvasToTranslucent;
    }

    public PendingIntent createPendingResult(int n, Intent parcelable, int n2) {
        block3 : {
            Object object;
            String string2 = this.getPackageName();
            Object var5_6 = null;
            try {
                parcelable.prepareToLeaveProcess(this);
                IActivityManager iActivityManager = ActivityManager.getService();
                object = this.mParent == null ? this.mToken : this.mParent.mToken;
                String string3 = this.mEmbeddedID;
                int n3 = this.getUserId();
                object = iActivityManager.getIntentSender(3, string2, (IBinder)object, string3, n, new Intent[]{parcelable}, null, n2, null, n3);
                parcelable = var5_6;
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            parcelable = new PendingIntent((IIntentSender)object);
        }
        return parcelable;
    }

    @Deprecated
    public final void dismissDialog(int n) {
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray != null) {
            if ((sparseArray = sparseArray.get(n)) != null) {
                ((ManagedDialog)sparseArray).mDialog.dismiss();
                return;
            }
            throw this.missingDialog(n);
        }
        throw this.missingDialog(n);
    }

    public final void dismissKeyboardShortcutsHelper() {
        Intent intent = new Intent("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS");
        intent.setPackage("com.android.systemui");
        this.sendBroadcastAsUser(intent, Process.myUserHandle());
    }

    @UnsupportedAppUsage
    void dispatchActivityResult(String object, int n, int n2, Intent intent, String string2) {
        this.mFragments.noteStateNotSaved();
        if (object == null) {
            this.onActivityResult(n, n2, intent);
        } else if (((String)object).startsWith("@android:requestPermissions:")) {
            if (TextUtils.isEmpty((CharSequence)(object = ((String)object).substring("@android:requestPermissions:".length())))) {
                this.dispatchRequestPermissionsResult(n, intent);
            } else if ((object = this.mFragments.findFragmentByWho((String)object)) != null) {
                this.dispatchRequestPermissionsResultToFragment(n, intent, (Fragment)object);
            }
        } else if (((String)object).startsWith("@android:view:")) {
            for (ViewRootImpl viewRootImpl : WindowManagerGlobal.getInstance().getRootViews(this.getActivityToken())) {
                if (viewRootImpl.getView() == null || !viewRootImpl.getView().dispatchActivityResult((String)object, n, n2, intent)) continue;
                return;
            }
        } else if (((String)object).startsWith("@android:autoFillAuth:")) {
            object = n2 == -1 ? intent : null;
            this.getAutofillManager().onAuthenticationResult(n, (Intent)object, this.getCurrentFocus());
        } else if ((object = this.mFragments.findFragmentByWho((String)object)) != null) {
            ((Fragment)object).onActivityResult(n, n2, intent);
        }
        this.writeEventLog(30062, string2);
    }

    public void dispatchEnterAnimationComplete() {
        this.mEnterAnimationComplete = true;
        this.mInstrumentation.onEnterAnimationComplete();
        this.onEnterAnimationComplete();
        if (this.getWindow() != null && this.getWindow().getDecorView() != null) {
            this.getWindow().getDecorView().getViewTreeObserver().dispatchOnEnterAnimationComplete();
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        this.onUserInteraction();
        if (this.getWindow().superDispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        return this.onGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Object object;
        this.onUserInteraction();
        if (keyEvent.getKeyCode() == 82 && (object = this.mActionBar) != null && ((ActionBar)object).onMenuKeyEvent(keyEvent)) {
            return true;
        }
        Window window = this.getWindow();
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View view = this.mDecor;
        object = view;
        if (view == null) {
            object = window.getDecorView();
        }
        object = object != null ? ((View)object).getKeyDispatcherState() : null;
        return keyEvent.dispatch(this, (KeyEvent.DispatcherState)object, this);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        this.onUserInteraction();
        if (this.getWindow().superDispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        return this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    void dispatchMovedToDisplay(int n, Configuration configuration) {
        this.updateDisplay(n);
        this.onMovedToDisplay(n, configuration);
    }

    final void dispatchMultiWindowModeChanged(boolean bl, Configuration configuration) {
        this.mFragments.dispatchMultiWindowModeChanged(bl, configuration);
        Window window = this.mWindow;
        if (window != null) {
            window.onMultiWindowModeChanged();
        }
        this.onMultiWindowModeChanged(bl, configuration);
    }

    final void dispatchPictureInPictureModeChanged(boolean bl, Configuration configuration) {
        this.mFragments.dispatchPictureInPictureModeChanged(bl, configuration);
        Window window = this.mWindow;
        if (window != null) {
            window.onPictureInPictureModeChanged(bl);
        }
        this.onPictureInPictureModeChanged(bl, configuration);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(this.getClass().getName());
        accessibilityEvent.setPackageName(this.getPackageName());
        Object object = this.getWindow().getAttributes();
        boolean bl = ((ViewGroup.LayoutParams)object).width == -1 && ((ViewGroup.LayoutParams)object).height == -1;
        accessibilityEvent.setFullScreen(bl);
        object = this.getTitle();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            accessibilityEvent.getText().add((CharSequence)object);
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.onUserInteraction();
        }
        if (this.getWindow().superDispatchTouchEvent(motionEvent)) {
            return true;
        }
        return this.onTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        this.onUserInteraction();
        if (this.getWindow().superDispatchTrackballEvent(motionEvent)) {
            return true;
        }
        return this.onTrackballEvent(motionEvent);
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.dumpInner(string2, fileDescriptor, printWriter, arrstring);
    }

    void dumpAutofillManager(String string2, PrintWriter printWriter) {
        AutofillManager autofillManager = this.getAutofillManager();
        if (autofillManager != null) {
            autofillManager.dump(string2, printWriter);
            printWriter.print(string2);
            printWriter.print("Autofill Compat Mode: ");
            printWriter.println(this.isAutofillCompatibilityEnabled());
        } else {
            printWriter.print(string2);
            printWriter.println("No AutofillManager");
        }
    }

    void dumpContentCaptureManager(String string2, PrintWriter printWriter) {
        ContentCaptureManager contentCaptureManager = this.getContentCaptureManager();
        if (contentCaptureManager != null) {
            contentCaptureManager.dump(string2, printWriter);
        } else {
            printWriter.print(string2);
            printWriter.println("No ContentCaptureManager");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void dumpInner(String var1_1, FileDescriptor var2_2, PrintWriter var3_3, String[] var4_4) {
        block6 : {
            block8 : {
                block7 : {
                    if (var4_4 == null || var4_4.length <= 0) break block6;
                    var5_5 = 0;
                    var6_6 = var4_4[0];
                    var7_7 = var6_6.hashCode();
                    if (var7_7 == 1159329357) break block7;
                    if (var7_7 != 1455016274 || !var6_6.equals("--autofill")) ** GOTO lbl-1000
                    break block8;
                }
                if (var6_6.equals("--contentcapture")) {
                    var5_5 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var5_5 = -1;
                }
            }
            if (var5_5 == 0) {
                this.dumpAutofillManager(var1_1, var3_3);
                return;
            }
            if (var5_5 == 1) {
                this.dumpContentCaptureManager(var1_1, var3_3);
                return;
            }
        }
        var3_3.print(var1_1);
        var3_3.print("Local Activity ");
        var3_3.print(Integer.toHexString(System.identityHashCode(this)));
        var3_3.println(" State:");
        var6_6 = new StringBuilder();
        var6_6.append(var1_1);
        var6_6.append("  ");
        var8_8 = var6_6.toString();
        var3_3.print(var8_8);
        var3_3.print("mResumed=");
        var3_3.print(this.mResumed);
        var3_3.print(" mStopped=");
        var3_3.print(this.mStopped);
        var3_3.print(" mFinished=");
        var3_3.println(this.mFinished);
        var3_3.print(var8_8);
        var3_3.print("mChangingConfigurations=");
        var3_3.println(this.mChangingConfigurations);
        var3_3.print(var8_8);
        var3_3.print("mCurrentConfig=");
        var3_3.println(this.mCurrentConfig);
        this.mFragments.dumpLoaders(var8_8, var2_2, var3_3, var4_4);
        this.mFragments.getFragmentManager().dump(var8_8, var2_2, var3_3, var4_4);
        var6_6 = this.mVoiceInteractor;
        if (var6_6 != null) {
            var6_6.dump(var8_8, var2_2, var3_3, var4_4);
        }
        if (this.getWindow() != null && this.getWindow().peekDecorView() != null && this.getWindow().peekDecorView().getViewRootImpl() != null) {
            this.getWindow().peekDecorView().getViewRootImpl().dump(var1_1, var2_2, var3_3, var4_4);
        }
        this.mHandler.getLooper().dump(new PrintWriterPrinter(var3_3), var1_1);
        this.dumpAutofillManager(var1_1, var3_3);
        this.dumpContentCaptureManager(var1_1, var3_3);
        ResourcesManager.getInstance().dump(var1_1, var3_3);
    }

    @Deprecated
    public void enterPictureInPictureMode() {
        this.enterPictureInPictureMode(new PictureInPictureParams.Builder().build());
    }

    @Deprecated
    public boolean enterPictureInPictureMode(PictureInPictureArgs pictureInPictureArgs) {
        return this.enterPictureInPictureMode(PictureInPictureArgs.convert(pictureInPictureArgs));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean enterPictureInPictureMode(PictureInPictureParams object) {
        try {
            if (!this.deviceSupportsPictureInPictureMode()) {
                return false;
            }
            if (object == null) {
                object = new IllegalArgumentException("Expected non-null picture-in-picture params");
                throw object;
            }
            if (this.mCanEnterPictureInPicture) {
                return ActivityTaskManager.getService().enterPictureInPictureMode(this.mToken, (PictureInPictureParams)object);
            }
            object = new IllegalStateException("Activity must be resumed to enter picture-in-picture");
            throw object;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public void enterPictureInPictureModeIfPossible() {
        if (this.mActivityInfo.supportsPictureInPicture()) {
            this.enterPictureInPictureMode();
        }
    }

    public <T extends View> T findViewById(int n) {
        return this.getWindow().findViewById(n);
    }

    public void finish() {
        this.finish(0);
    }

    public void finishActivity(int n) {
        Activity activity = this.mParent;
        if (activity == null) {
            try {
                ActivityTaskManager.getService().finishSubActivity(this.mToken, this.mEmbeddedID, n);
            }
            catch (RemoteException remoteException) {}
        } else {
            activity.finishActivityFromChild(this, n);
        }
    }

    public void finishActivityFromChild(Activity activity, int n) {
        try {
            ActivityTaskManager.getService().finishSubActivity(this.mToken, activity.mEmbeddedID, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void finishAffinity() {
        if (this.mParent == null) {
            if (this.mResultCode == 0 && this.mResultData == null) {
                try {
                    if (ActivityTaskManager.getService().finishActivityAffinity(this.mToken)) {
                        this.mFinished = true;
                    }
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                return;
            }
            throw new IllegalStateException("Can not be called to deliver a result");
        }
        throw new IllegalStateException("Can not be called from an embedded activity");
    }

    public void finishAfterTransition() {
        if (!this.mActivityTransitionState.startExitBackTransition(this)) {
            this.finish();
        }
    }

    public void finishAndRemoveTask() {
        this.finish(1);
    }

    public void finishFromChild(Activity activity) {
        this.finish();
    }

    public ActionBar getActionBar() {
        this.initWindowDecorActionBar();
        return this.mActionBar;
    }

    @UnsupportedAppUsage
    ActivityOptions getActivityOptions() {
        try {
            ActivityOptions activityOptions = ActivityOptions.fromBundle(ActivityTaskManager.getService().getActivityOptions(this.mToken));
            return activityOptions;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @VisibleForTesting
    public final ActivityThread getActivityThread() {
        return this.mMainThread;
    }

    @UnsupportedAppUsage
    @Override
    public final IBinder getActivityToken() {
        Object object = this.mParent;
        object = object != null ? ((Activity)object).getActivityToken() : this.mToken;
        return object;
    }

    public final Application getApplication() {
        return this.mApplication;
    }

    public final IBinder getAssistToken() {
        Object object = this.mParent;
        object = object != null ? ((Activity)object).getAssistToken() : this.mAssistToken;
        return object;
    }

    @Override
    public final AutofillManager.AutofillClient getAutofillClient() {
        return this;
    }

    public ComponentName getCallingActivity() {
        try {
            ComponentName componentName = ActivityTaskManager.getService().getCallingActivity(this.mToken);
            return componentName;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getCallingPackage() {
        try {
            String string2 = ActivityTaskManager.getService().getCallingPackage(this.mToken);
            return string2;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public int getChangingConfigurations() {
        return this.mConfigChangeFlags;
    }

    public ComponentName getComponentName() {
        return this.mComponent;
    }

    @Override
    public final ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return this;
    }

    public Scene getContentScene() {
        return this.getWindow().getContentScene();
    }

    public TransitionManager getContentTransitionManager() {
        return this.getWindow().getTransitionManager();
    }

    public View getCurrentFocus() {
        Object object = this.mWindow;
        object = object != null ? ((Window)object).getCurrentFocus() : null;
        return object;
    }

    @Deprecated
    public FragmentManager getFragmentManager() {
        return this.mFragments.getFragmentManager();
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    HashMap<String, Object> getLastNonConfigurationChildInstances() {
        Object object = this.mLastNonConfigurationInstances;
        object = object != null ? ((NonConfigurationInstances)object).children : null;
        return object;
    }

    public Object getLastNonConfigurationInstance() {
        Object object = this.mLastNonConfigurationInstances;
        object = object != null ? ((NonConfigurationInstances)object).activity : null;
        return object;
    }

    public LayoutInflater getLayoutInflater() {
        return this.getWindow().getLayoutInflater();
    }

    @Deprecated
    public LoaderManager getLoaderManager() {
        return this.mFragments.getLoaderManager();
    }

    public String getLocalClassName() {
        String string2 = this.getPackageName();
        String string3 = this.mComponent.getClassName();
        int n = string2.length();
        if (string3.startsWith(string2) && string3.length() > n && string3.charAt(n) == '.') {
            return string3.substring(n + 1);
        }
        return string3;
    }

    public int getMaxNumPictureInPictureActions() {
        try {
            int n = ActivityTaskManager.getService().getMaxNumPictureInPictureActions(this.mToken);
            return n;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    public final MediaController getMediaController() {
        return this.getWindow().getMediaController();
    }

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            this.mMenuInflater = actionBar != null ? new MenuInflater(actionBar.getThemedContext(), this) : new MenuInflater(this);
        }
        return this.mMenuInflater;
    }

    @Override
    public int getNextAutofillId() {
        if (this.mLastAutofillId == 2147483646) {
            this.mLastAutofillId = 1073741823;
        }
        ++this.mLastAutofillId;
        return this.mLastAutofillId;
    }

    public final Activity getParent() {
        return this.mParent;
    }

    public Intent getParentActivityIntent() {
        String string2 = this.mActivityInfo.parentActivityName;
        if (TextUtils.isEmpty(string2)) {
            return null;
        }
        ComponentName componentName = new ComponentName((Context)this, string2);
        try {
            Intent intent;
            if (this.getPackageManager().getActivityInfo((ComponentName)componentName, (int)0).parentActivityName == null) {
                intent = Intent.makeMainActivity(componentName);
            } else {
                intent = new Intent();
                intent = intent.setComponent(componentName);
            }
            return intent;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getParentActivityIntent: bad parentActivityName '");
            stringBuilder.append(string2);
            stringBuilder.append("' in manifest");
            Log.e("Activity", stringBuilder.toString());
            return null;
        }
    }

    public SharedPreferences getPreferences(int n) {
        return this.getSharedPreferences(this.getLocalClassName(), n);
    }

    public Uri getReferrer() {
        block6 : {
            Object object;
            block5 : {
                object = this.getIntent();
                Uri uri = (Uri)((Intent)object).getParcelableExtra("android.intent.extra.REFERRER");
                if (uri == null) break block5;
                return uri;
            }
            object = ((Intent)object).getStringExtra("android.intent.extra.REFERRER_NAME");
            if (object == null) break block6;
            try {
                object = Uri.parse((String)object);
                return object;
            }
            catch (BadParcelableException badParcelableException) {
                Log.w("Activity", "Cannot read referrer from intent; intent extras contain unknown custom Parcelable objects");
            }
        }
        if (this.mReferrer != null) {
            return new Uri.Builder().scheme("android-app").authority(this.mReferrer).build();
        }
        return null;
    }

    public int getRequestedOrientation() {
        Activity activity = this.mParent;
        if (activity == null) {
            try {
                int n = ActivityTaskManager.getService().getRequestedOrientation(this.mToken);
                return n;
            }
            catch (RemoteException remoteException) {
                return -1;
            }
        }
        return activity.getRequestedOrientation();
    }

    public final SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    @Override
    public Object getSystemService(String string2) {
        if (this.getBaseContext() != null) {
            if ("window".equals(string2)) {
                return this.mWindowManager;
            }
            if ("search".equals(string2)) {
                this.ensureSearchManager();
                return this.mSearchManager;
            }
            return super.getSystemService(string2);
        }
        throw new IllegalStateException("System services not available to Activities before onCreate()");
    }

    public int getTaskId() {
        try {
            int n = ActivityTaskManager.getService().getTaskForActivity(this.mToken, false);
            return n;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    public final CharSequence getTitle() {
        return this.mTitle;
    }

    public final int getTitleColor() {
        return this.mTitleColor;
    }

    public VoiceInteractor getVoiceInteractor() {
        return this.mVoiceInteractor;
    }

    public final int getVolumeControlStream() {
        return this.getWindow().getVolumeControlStream();
    }

    public Window getWindow() {
        return this.mWindow;
    }

    public WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    public boolean hasWindowFocus() {
        Object object = this.getWindow();
        if (object != null && (object = ((Window)object).getDecorView()) != null) {
            return ((View)object).hasWindowFocus();
        }
        return false;
    }

    public void invalidateOptionsMenu() {
        ActionBar actionBar;
        if (this.mWindow.hasFeature(0) && ((actionBar = this.mActionBar) == null || !actionBar.invalidateOptionsMenu())) {
            this.mWindow.invalidatePanelMenu(0);
        }
    }

    public boolean isActivityTransitionRunning() {
        return this.mActivityTransitionState.isTransitionRunning();
    }

    @SystemApi
    @Deprecated
    public boolean isBackgroundVisibleBehind() {
        return false;
    }

    public boolean isChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public final boolean isChild() {
        boolean bl = this.mParent != null;
        return bl;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    @Override
    public final boolean isDisablingEnterExitEventForAutofill() {
        boolean bl = this.mAutoFillIgnoreFirstResumePause || !this.mResumed;
        return bl;
    }

    public boolean isFinishing() {
        return this.mFinished;
    }

    public boolean isImmersive() {
        try {
            boolean bl = ActivityTaskManager.getService().isImmersive(this.mToken);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isInMultiWindowMode() {
        try {
            boolean bl = ActivityTaskManager.getService().isInMultiWindowMode(this.mToken);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isInPictureInPictureMode() {
        try {
            boolean bl = ActivityTaskManager.getService().isInPictureInPictureMode(this.mToken);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isLocalVoiceInteractionSupported() {
        try {
            boolean bl = ActivityTaskManager.getService().supportsLocalVoiceInteraction();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isOverlayWithDecorCaptionEnabled() {
        return this.mWindow.isOverlayWithDecorCaptionEnabled();
    }

    @UnsupportedAppUsage
    public final boolean isResumed() {
        return this.mResumed;
    }

    @Override
    public boolean isTaskRoot() {
        boolean bl = false;
        try {
            int n = ActivityTaskManager.getService().getTaskForActivity(this.mToken, true);
            if (n >= 0) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    final boolean isTopOfTask() {
        if (this.mToken != null && this.mWindow != null) {
            try {
                boolean bl = ActivityTaskManager.getService().isTopOfTask(this.getActivityToken());
                return bl;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        return false;
    }

    public boolean isVoiceInteraction() {
        boolean bl = this.mVoiceInteractor != null;
        return bl;
    }

    public boolean isVoiceInteractionRoot() {
        boolean bl = false;
        try {
            boolean bl2;
            if (this.mVoiceInteractor != null && (bl2 = ActivityTaskManager.getService().isRootVoiceInteraction(this.mToken))) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    void makeVisible() {
        if (!this.mWindowAdded) {
            this.getWindowManager().addView(this.mDecor, this.getWindow().getAttributes());
            this.mWindowAdded = true;
        }
        this.mDecor.setVisibility(0);
    }

    @Deprecated
    @UnsupportedAppUsage
    public final Cursor managedQuery(Uri object, String[] arrstring, String string2, String string3) {
        object = this.getContentResolver().query((Uri)object, arrstring, string2, null, string3);
        if (object != null) {
            this.startManagingCursor((Cursor)object);
        }
        return object;
    }

    @Deprecated
    public final Cursor managedQuery(Uri object, String[] arrstring, String string2, String[] arrstring2, String string3) {
        object = this.getContentResolver().query((Uri)object, arrstring, string2, arrstring2, string3);
        if (object != null) {
            this.startManagingCursor((Cursor)object);
        }
        return object;
    }

    public boolean moveTaskToBack(boolean bl) {
        try {
            bl = ActivityTaskManager.getService().moveActivityTaskToBack(this.mToken, bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean navigateUpTo(Intent intent) {
        Object object = this.mParent;
        if (object != null) return ((Activity)object).navigateUpToFromChild(this, intent);
        if (intent.getComponent() == null) {
            object = intent.resolveActivity(this.getPackageManager());
            if (object == null) {
                return false;
            }
            intent = new Intent(intent);
            intent.setComponent((ComponentName)object);
        }
        // MONITORENTER : this
        int n = this.mResultCode;
        object = this.mResultData;
        // MONITOREXIT : this
        if (object != null) {
            ((Intent)object).prepareToLeaveProcess(this);
        }
        try {
            intent.prepareToLeaveProcess(this);
            return ActivityTaskManager.getService().navigateUpTo(this.mToken, intent, n, (Intent)object);
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean navigateUpToFromChild(Activity activity, Intent intent) {
        return this.navigateUpTo(intent);
    }

    @Override
    public void onActionModeFinished(ActionMode actionMode) {
    }

    @Override
    public void onActionModeStarted(ActionMode actionMode) {
    }

    public void onActivityReenter(int n, Intent intent) {
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme object, int n, boolean bl) {
        Activity activity = this.mParent;
        if (activity == null) {
            super.onApplyThemeResource((Resources.Theme)object, n, bl);
        } else {
            try {
                ((Resources.Theme)object).setTo(activity.getTheme());
            }
            catch (Exception exception) {
                // empty catch block
            }
            ((Resources.Theme)object).applyStyle(n, false);
        }
        object = ((Resources.Theme)object).obtainStyledAttributes(R.styleable.ActivityTaskDescription);
        if (this.mTaskDescription.getPrimaryColor() == 0 && (n = ((TypedArray)object).getColor(1, 0)) != 0 && Color.alpha(n) == 255) {
            this.mTaskDescription.setPrimaryColor(n);
        }
        if ((n = ((TypedArray)object).getColor(0, 0)) != 0 && Color.alpha(n) == 255) {
            this.mTaskDescription.setBackgroundColor(n);
        }
        if ((n = ((TypedArray)object).getColor(2, 0)) != 0) {
            this.mTaskDescription.setStatusBarColor(n);
        }
        if ((n = ((TypedArray)object).getColor(3, 0)) != 0) {
            this.mTaskDescription.setNavigationBarColor(n);
        }
        if ((n = this.getApplicationInfo().targetSdkVersion < 29 ? 1 : 0) == 0) {
            this.mTaskDescription.setEnsureStatusBarContrastWhenTransparent(((TypedArray)object).getBoolean(4, false));
            this.mTaskDescription.setEnsureNavigationBarContrastWhenTransparent(((TypedArray)object).getBoolean(5, true));
        }
        ((TypedArray)object).recycle();
        this.setTaskDescription(this.mTaskDescription);
    }

    @Deprecated
    public void onAttachFragment(Fragment fragment) {
    }

    @Override
    public void onAttachedToWindow() {
    }

    public void onBackPressed() {
        Object object = this.mActionBar;
        if (object != null && ((ActionBar)object).collapseActionView()) {
            return;
        }
        object = this.mFragments.getFragmentManager();
        if (!((FragmentManager)object).isStateSaved() && ((FragmentManager)object).popBackStackImmediate()) {
            return;
        }
        if (!this.isTaskRoot()) {
            this.finishAfterTransition();
            return;
        }
        try {
            IActivityTaskManager iActivityTaskManager = ActivityTaskManager.getService();
            object = this.mToken;
            IRequestFinishCallback.Stub stub = new IRequestFinishCallback.Stub(){

                public /* synthetic */ void lambda$requestFinish$0$Activity$1() {
                    Activity.this.finishAfterTransition();
                }

                @Override
                public void requestFinish() {
                    Activity.this.mHandler.post(new _$$Lambda$Activity$1$pR5b3qDyhldlD2RtkXoHHxgyGPU(this));
                }
            };
            iActivityTaskManager.onBackPressedOnTaskRoot((IBinder)object, stub);
        }
        catch (RemoteException remoteException) {
            this.finishAfterTransition();
        }
    }

    @SystemApi
    @Deprecated
    public void onBackgroundVisibleBehindChanged(boolean bl) {
    }

    protected void onChildTitleChanged(Activity activity, CharSequence charSequence) {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
        this.mFragments.dispatchConfigurationChanged(configuration);
        Object object = this.mWindow;
        if (object != null) {
            ((Window)object).onConfigurationChanged(configuration);
        }
        if ((object = this.mActionBar) != null) {
            ((ActionBar)object).onConfigurationChanged(configuration);
        }
    }

    @Override
    public void onContentChanged() {
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onContextItemSelected(menuItem);
        }
        return false;
    }

    public void onContextMenuClosed(Menu menu2) {
        Activity activity = this.mParent;
        if (activity != null) {
            activity.onContextMenuClosed(menu2);
        }
    }

    protected void onCreate(Bundle bundle) {
        Object object = this.mLastNonConfigurationInstances;
        if (object != null) {
            this.mFragments.restoreLoaderNonConfig(((NonConfigurationInstances)object).loaders);
        }
        if (this.mActivityInfo.parentActivityName != null) {
            object = this.mActionBar;
            if (object == null) {
                this.mEnableDefaultActionBarUp = true;
            } else {
                ((ActionBar)object).setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
        boolean bl = false;
        if (bundle != null) {
            this.mAutoFillResetNeeded = bundle.getBoolean("@android:autofillResetNeeded", false);
            this.mLastAutofillId = bundle.getInt("android:lastAutofillId", 1073741823);
            if (this.mAutoFillResetNeeded) {
                this.getAutofillManager().onCreate(bundle);
            }
            T t = bundle.getParcelable("android:fragments");
            FragmentController fragmentController = this.mFragments;
            object = this.mLastNonConfigurationInstances;
            object = object != null ? ((NonConfigurationInstances)object).fragments : null;
            fragmentController.restoreAllState((Parcelable)t, (FragmentManagerNonConfig)object);
        }
        this.mFragments.dispatchCreate();
        this.dispatchActivityCreated(bundle);
        object = this.mVoiceInteractor;
        if (object != null) {
            ((VoiceInteractor)object).attachActivity(this);
        }
        if (bundle != null) {
            bl = true;
        }
        this.mRestoredFromBundle = bl;
        this.mCalled = true;
    }

    public void onCreate(Bundle bundle, PersistableBundle persistableBundle) {
        this.onCreate(bundle);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    public CharSequence onCreateDescription() {
        return null;
    }

    @Deprecated
    protected Dialog onCreateDialog(int n) {
        return null;
    }

    @Deprecated
    protected Dialog onCreateDialog(int n, Bundle bundle) {
        return this.onCreateDialog(n);
    }

    public void onCreateNavigateUpTaskStack(TaskStackBuilder taskStackBuilder) {
        taskStackBuilder.addParentStack(this);
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onCreateOptionsMenu(menu2);
        }
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int n, Menu menu2) {
        if (n == 0) {
            return this.onCreateOptionsMenu(menu2) | this.mFragments.dispatchCreateOptionsMenu(menu2, this.getMenuInflater());
        }
        return false;
    }

    @Override
    public View onCreatePanelView(int n) {
        return null;
    }

    @Deprecated
    public boolean onCreateThumbnail(Bitmap bitmap, Canvas canvas) {
        return false;
    }

    @Override
    public View onCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        if (!"fragment".equals(string2)) {
            return this.onCreateView(string2, context, attributeSet);
        }
        return this.mFragments.onCreateView(view, string2, context, attributeSet);
    }

    @Override
    public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onDestroy() {
        int n;
        int n2;
        this.mCalled = true;
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray != null) {
            n = sparseArray.size();
            for (n2 = 0; n2 < n; ++n2) {
                sparseArray = this.mManagedDialogs.valueAt(n2);
                if (!((ManagedDialog)sparseArray).mDialog.isShowing()) continue;
                ((ManagedDialog)sparseArray).mDialog.dismiss();
            }
            this.mManagedDialogs = null;
        }
        sparseArray = this.mManagedCursors;
        synchronized (sparseArray) {
            n = this.mManagedCursors.size();
            for (n2 = 0; n2 < n; ++n2) {
                ManagedCursor managedCursor = this.mManagedCursors.get(n2);
                if (managedCursor == null) continue;
                managedCursor.mCursor.close();
            }
            this.mManagedCursors.clear();
        }
        sparseArray = this.mSearchManager;
        if (sparseArray != null) {
            ((SearchManager)((Object)sparseArray)).stopSearch();
        }
        if ((sparseArray = this.mActionBar) != null) {
            ((ActionBar)((Object)sparseArray)).onDestroy();
        }
        this.dispatchActivityDestroyed();
        this.notifyContentCaptureManagerIfNeeded(4);
    }

    @Override
    public void onDetachedFromWindow() {
    }

    public void onEnterAnimationComplete() {
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onGetDirectActions(CancellationSignal cancellationSignal, Consumer<List<DirectAction>> consumer) {
        consumer.accept(Collections.<T>emptyList());
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent object) {
        boolean bl;
        if (n == 4) {
            if (this.getApplicationInfo().targetSdkVersion >= 5) {
                ((KeyEvent)object).startTracking();
            } else {
                this.onBackPressed();
            }
            return true;
        }
        int n2 = this.mDefaultKeyMode;
        if (n2 == 0) {
            return false;
        }
        if (n2 == 2) {
            Window window = this.getWindow();
            return window.hasFeature(0) && window.performPanelShortcut(0, n, (KeyEvent)object, 2);
        }
        if (n == 61) {
            return false;
        }
        n2 = 0;
        if (((KeyEvent)object).getRepeatCount() == 0 && !((KeyEvent)object).isSystem()) {
            boolean bl2 = TextKeyListener.getInstance().onKeyDown(null, this.mDefaultKeySsb, n, (KeyEvent)object);
            n = n2;
            bl = bl2;
            if (bl2) {
                n = n2;
                bl = bl2;
                if (this.mDefaultKeySsb.length() > 0) {
                    String string2 = this.mDefaultKeySsb.toString();
                    n = 1;
                    n2 = this.mDefaultKeyMode;
                    if (n2 != 1) {
                        if (n2 != 3) {
                            if (n2 != 4) {
                                bl = bl2;
                            } else {
                                this.startSearch(string2, false, null, true);
                                bl = bl2;
                            }
                        } else {
                            this.startSearch(string2, false, null, false);
                            bl = bl2;
                        }
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("tel:");
                        ((StringBuilder)object).append(string2);
                        object = new Intent("android.intent.action.DIAL", Uri.parse(((StringBuilder)object).toString()));
                        ((Intent)object).addFlags(268435456);
                        this.startActivity((Intent)object);
                        bl = bl2;
                    }
                }
            }
        } else {
            n = 1;
            bl = false;
        }
        if (n != 0) {
            this.mDefaultKeySsb.clear();
            this.mDefaultKeySsb.clearSpans();
            Selection.setSelection(this.mDefaultKeySsb, 0);
        }
        return bl;
    }

    @Override
    public boolean onKeyLongPress(int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        ActionBar actionBar = this.getActionBar();
        boolean bl = actionBar != null && actionBar.onKeyShortcut(n, keyEvent);
        return bl;
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (this.getApplicationInfo().targetSdkVersion >= 5 && n == 4 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
            this.onBackPressed();
            return true;
        }
        return false;
    }

    public void onLocalVoiceInteractionStarted() {
    }

    public void onLocalVoiceInteractionStopped() {
    }

    @Override
    public void onLowMemory() {
        this.mCalled = true;
        this.mFragments.dispatchLowMemory();
    }

    @Override
    public boolean onMenuItemSelected(int n, MenuItem object) {
        CharSequence charSequence = object.getTitleCondensed();
        if (n != 0) {
            if (n != 6) {
                return false;
            }
            if (charSequence != null) {
                EventLog.writeEvent(50000, 1, charSequence.toString());
            }
            if (this.onContextItemSelected((MenuItem)object)) {
                return true;
            }
            return this.mFragments.dispatchContextItemSelected((MenuItem)object);
        }
        if (charSequence != null) {
            EventLog.writeEvent(50000, 0, charSequence.toString());
        }
        if (this.onOptionsItemSelected((MenuItem)object)) {
            return true;
        }
        if (this.mFragments.dispatchOptionsItemSelected((MenuItem)object)) {
            return true;
        }
        if (object.getItemId() == 16908332 && (object = this.mActionBar) != null && (((ActionBar)object).getDisplayOptions() & 4) != 0) {
            object = this.mParent;
            if (object == null) {
                return this.onNavigateUp();
            }
            return ((Activity)object).onNavigateUpFromChild(this);
        }
        return false;
    }

    @Override
    public boolean onMenuOpened(int n, Menu object) {
        if (n == 8) {
            this.initWindowDecorActionBar();
            object = this.mActionBar;
            if (object != null) {
                ((ActionBar)object).dispatchMenuVisibilityChanged(true);
            } else {
                Log.e("Activity", "Tried to open action bar menu with no action bar");
            }
        }
        return true;
    }

    public void onMovedToDisplay(int n, Configuration configuration) {
    }

    @Deprecated
    public void onMultiWindowModeChanged(boolean bl) {
    }

    public void onMultiWindowModeChanged(boolean bl, Configuration configuration) {
        this.onMultiWindowModeChanged(bl);
    }

    public boolean onNavigateUp() {
        Object object = this.getParentActivityIntent();
        if (object != null) {
            if (this.mActivityInfo.taskAffinity == null) {
                this.finish();
            } else if (this.shouldUpRecreateTask((Intent)object)) {
                object = TaskStackBuilder.create(this);
                this.onCreateNavigateUpTaskStack((TaskStackBuilder)object);
                this.onPrepareNavigateUpTaskStack((TaskStackBuilder)object);
                ((TaskStackBuilder)object).startActivities();
                if (this.mResultCode == 0 && this.mResultData == null) {
                    this.finishAffinity();
                } else {
                    Log.i("Activity", "onNavigateUp only finishing topmost activity to return a result");
                    this.finish();
                }
            } else {
                this.navigateUpTo((Intent)object);
            }
            return true;
        }
        return false;
    }

    public boolean onNavigateUpFromChild(Activity activity) {
        return this.onNavigateUp();
    }

    public void onNewActivityOptions(ActivityOptions activityOptions) {
        this.mActivityTransitionState.setEnterActivityOptions(this, activityOptions);
        if (!this.mStopped) {
            this.mActivityTransitionState.enterReady(this);
        }
    }

    protected void onNewIntent(Intent intent) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onOptionsItemSelected(menuItem);
        }
        return false;
    }

    public void onOptionsMenuClosed(Menu menu2) {
        Activity activity = this.mParent;
        if (activity != null) {
            activity.onOptionsMenuClosed(menu2);
        }
    }

    @Override
    public void onPanelClosed(int n, Menu menu2) {
        if (n != 0) {
            if (n != 6) {
                if (n == 8) {
                    this.initWindowDecorActionBar();
                    this.mActionBar.dispatchMenuVisibilityChanged(false);
                }
            } else {
                this.onContextMenuClosed(menu2);
            }
        } else {
            this.mFragments.dispatchOptionsMenuClosed(menu2);
            this.onOptionsMenuClosed(menu2);
        }
    }

    protected void onPause() {
        this.dispatchActivityPaused();
        if (this.mAutoFillResetNeeded) {
            if (!this.mAutoFillIgnoreFirstResumePause) {
                View view = this.getCurrentFocus();
                if (view != null && view.canNotifyAutofillEnterExitEvent()) {
                    this.getAutofillManager().notifyViewExited(view);
                }
            } else {
                this.mAutoFillIgnoreFirstResumePause = false;
            }
        }
        this.notifyContentCaptureManagerIfNeeded(3);
        this.mCalled = true;
    }

    public void onPerformDirectAction(String string2, Bundle bundle, CancellationSignal cancellationSignal, Consumer<Bundle> consumer) {
    }

    @Deprecated
    public void onPictureInPictureModeChanged(boolean bl) {
    }

    public void onPictureInPictureModeChanged(boolean bl, Configuration configuration) {
        this.onPictureInPictureModeChanged(bl);
    }

    protected void onPostCreate(Bundle bundle) {
        if (!this.isChild()) {
            this.mTitleReady = true;
            this.onTitleChanged(this.getTitle(), this.getTitleColor());
        }
        this.mCalled = true;
        this.notifyContentCaptureManagerIfNeeded(1);
    }

    public void onPostCreate(Bundle bundle, PersistableBundle persistableBundle) {
        this.onPostCreate(bundle);
    }

    protected void onPostResume() {
        Object object = this.getWindow();
        if (object != null) {
            ((Window)object).makeActive();
        }
        if ((object = this.mActionBar) != null) {
            ((ActionBar)object).setShowHideAnimationEnabled(true);
        }
        this.mCalled = true;
    }

    @Deprecated
    protected void onPrepareDialog(int n, Dialog dialog) {
        dialog.setOwnerActivity(this);
    }

    @Deprecated
    protected void onPrepareDialog(int n, Dialog dialog, Bundle bundle) {
        this.onPrepareDialog(n, dialog);
    }

    public void onPrepareNavigateUpTaskStack(TaskStackBuilder taskStackBuilder) {
    }

    public boolean onPrepareOptionsMenu(Menu menu2) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onPrepareOptionsMenu(menu2);
        }
        return true;
    }

    @Override
    public boolean onPreparePanel(int n, View view, Menu menu2) {
        if (n == 0) {
            return this.onPrepareOptionsMenu(menu2) | this.mFragments.dispatchPrepareOptionsMenu(menu2);
        }
        return true;
    }

    public void onProvideAssistContent(AssistContent assistContent) {
    }

    public void onProvideAssistData(Bundle bundle) {
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu2, int n) {
        if (menu2 == null) {
            return;
        }
        Object object = null;
        int n2 = menu2.size();
        for (n = 0; n < n2; ++n) {
            Object object2 = menu2.getItem(n);
            CharSequence charSequence = object2.getTitle();
            char c = object2.getAlphabeticShortcut();
            int n3 = object2.getAlphabeticModifiers();
            object2 = object;
            if (charSequence != null) {
                object2 = object;
                if (c != '\u0000') {
                    object2 = object;
                    if (object == null) {
                        int n4 = this.mApplication.getApplicationInfo().labelRes;
                        object = n4 != 0 ? this.getString(n4) : null;
                        object2 = new KeyboardShortcutGroup((CharSequence)object);
                    }
                    ((KeyboardShortcutGroup)object2).addItem(new KeyboardShortcutInfo(charSequence, c, n3));
                }
            }
            object = object2;
        }
        if (object != null) {
            list.add((KeyboardShortcutGroup)object);
        }
    }

    public Uri onProvideReferrer() {
        return null;
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
    }

    protected void onRestart() {
        this.mCalled = true;
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        if (this.mWindow != null && (bundle = bundle.getBundle("android:viewHierarchyState")) != null) {
            this.mWindow.restoreHierarchyState(bundle);
        }
    }

    public void onRestoreInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        if (bundle != null) {
            this.onRestoreInstanceState(bundle);
        }
    }

    protected void onResume() {
        View view;
        this.dispatchActivityResumed();
        this.mActivityTransitionState.onResume(this);
        this.enableAutofillCompatibilityIfNeeded();
        if (this.mAutoFillResetNeeded && !this.mAutoFillIgnoreFirstResumePause && (view = this.getCurrentFocus()) != null && view.canNotifyAutofillEnterExitEvent()) {
            this.getAutofillManager().notifyViewEntered(view);
        }
        this.notifyContentCaptureManagerIfNeeded(2);
        this.mCalled = true;
    }

    HashMap<String, Object> onRetainNonConfigurationChildInstances() {
        return null;
    }

    public Object onRetainNonConfigurationInstance() {
        return null;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putBundle("android:viewHierarchyState", this.mWindow.saveHierarchyState());
        bundle.putInt("android:lastAutofillId", this.mLastAutofillId);
        Parcelable parcelable = this.mFragments.saveAllState();
        if (parcelable != null) {
            bundle.putParcelable("android:fragments", parcelable);
        }
        if (this.mAutoFillResetNeeded) {
            bundle.putBoolean("@android:autofillResetNeeded", true);
            this.getAutofillManager().onSaveInstanceState(bundle);
        }
        this.dispatchActivitySaveInstanceState(bundle);
    }

    public void onSaveInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        this.onSaveInstanceState(bundle);
    }

    @Override
    public boolean onSearchRequested() {
        int n = this.getResources().getConfiguration().uiMode & 15;
        if (n != 4 && n != 6) {
            this.startSearch(null, false, null, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        boolean bl = this.onSearchRequested();
        this.mSearchEvent = null;
        return bl;
    }

    protected void onStart() {
        this.mCalled = true;
        this.mFragments.doLoaderStart();
        this.dispatchActivityStarted();
        if (this.mAutoFillResetNeeded) {
            this.getAutofillManager().onVisibleForAutofill();
        }
    }

    @Deprecated
    public void onStateNotSaved() {
    }

    protected void onStop() {
        Object object = this.mActionBar;
        if (object != null) {
            ((ActionBar)object).setShowHideAnimationEnabled(false);
        }
        this.mActivityTransitionState.onStop();
        this.dispatchActivityStopped();
        this.mTranslucentCallback = null;
        this.mCalled = true;
        if (this.mAutoFillResetNeeded) {
            this.getAutofillManager().onInvisibleForAutofill();
        }
        if (this.isFinishing()) {
            if (this.mAutoFillResetNeeded) {
                this.getAutofillManager().onActivityFinishing();
            } else {
                object = this.mIntent;
                if (object != null && ((Intent)object).hasExtra("android.view.autofill.extra.RESTORE_SESSION_TOKEN")) {
                    this.getAutofillManager().onPendingSaveUi(1, this.mIntent.getIBinderExtra("android.view.autofill.extra.RESTORE_SESSION_TOKEN"));
                }
            }
        }
        this.mEnterAnimationComplete = false;
    }

    protected void onTitleChanged(CharSequence charSequence, int n) {
        if (this.mTitleReady) {
            Object object = this.getWindow();
            if (object != null) {
                ((Window)object).setTitle(charSequence);
                if (n != 0) {
                    ((Window)object).setTitleColor(n);
                }
            }
            if ((object = this.mActionBar) != null) {
                ((ActionBar)object).setWindowTitle(charSequence);
            }
        }
    }

    public void onTopResumedActivityChanged(boolean bl) {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mWindow.shouldCloseOnTouch(this, motionEvent)) {
            this.finish();
            return true;
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    void onTranslucentConversionComplete(boolean bl) {
        TranslucentConversionListener translucentConversionListener = this.mTranslucentCallback;
        if (translucentConversionListener != null) {
            translucentConversionListener.onTranslucentConversionComplete(bl);
            this.mTranslucentCallback = null;
        }
        if (this.mChangeCanvasToTranslucent) {
            WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
        }
    }

    @Override
    public void onTrimMemory(int n) {
        this.mCalled = true;
        this.mFragments.dispatchTrimMemory(n);
    }

    public void onUserInteraction() {
    }

    protected void onUserLeaveHint() {
    }

    @Deprecated
    public void onVisibleBehindCanceled() {
        this.mCalled = true;
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        Object object;
        if (this.mParent == null && (object = this.mDecor) != null && ((View)object).getParent() != null) {
            this.getWindowManager().updateViewLayout((View)object, layoutParams);
            object = this.mContentCaptureManager;
            if (object != null) {
                ((ContentCaptureManager)object).updateWindowAttributes(layoutParams);
            }
        }
    }

    @Override
    public void onWindowDismissed(boolean bl, boolean bl2) {
        int n = bl ? 2 : 0;
        this.finish(n);
        if (bl2) {
            this.overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        if (this.mActionModeTypeStarting == 0) {
            this.initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                return actionBar.startActionMode(callback);
            }
        }
        return null;
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback object, int n) {
        try {
            this.mActionModeTypeStarting = n;
            object = this.onWindowStartingActionMode((ActionMode.Callback)object);
            return object;
        }
        finally {
            this.mActionModeTypeStarting = 0;
        }
    }

    public void openContextMenu(View view) {
        view.showContextMenu();
    }

    public void openOptionsMenu() {
        ActionBar actionBar;
        if (this.mWindow.hasFeature(0) && ((actionBar = this.mActionBar) == null || !actionBar.openOptionsMenu())) {
            this.mWindow.openPanel(0, null);
        }
    }

    public void overridePendingTransition(int n, int n2) {
        try {
            ActivityTaskManager.getService().overridePendingTransition(this.mToken, this.getPackageName(), n, n2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    final void performCreate(Bundle bundle) {
        this.performCreate(bundle, null);
    }

    @UnsupportedAppUsage
    final void performCreate(Bundle bundle, PersistableBundle persistableBundle) {
        this.dispatchActivityPreCreated(bundle);
        this.mCanEnterPictureInPicture = true;
        this.restoreHasCurrentPermissionRequest(bundle);
        if (persistableBundle != null) {
            this.onCreate(bundle, persistableBundle);
        } else {
            this.onCreate(bundle);
        }
        this.writeEventLog(30057, "performCreate");
        this.mActivityTransitionState.readState(bundle);
        this.mVisibleFromClient = true ^ this.mWindow.getWindowStyle().getBoolean(10, false);
        this.mFragments.dispatchActivityCreated();
        this.mActivityTransitionState.setEnterActivityOptions(this, this.getActivityOptions());
        this.dispatchActivityPostCreated(bundle);
    }

    final void performDestroy() {
        this.dispatchActivityPreDestroyed();
        this.mDestroyed = true;
        this.mWindow.destroy();
        this.mFragments.dispatchDestroy();
        this.onDestroy();
        this.writeEventLog(30060, "performDestroy");
        this.mFragments.doLoaderDestroy();
        VoiceInteractor voiceInteractor = this.mVoiceInteractor;
        if (voiceInteractor != null) {
            voiceInteractor.detachActivity();
        }
        this.dispatchActivityPostDestroyed();
    }

    final void performNewIntent(Intent intent) {
        this.mCanEnterPictureInPicture = true;
        this.onNewIntent(intent);
    }

    final void performPause() {
        this.dispatchActivityPrePaused();
        this.mDoReportFullyDrawn = false;
        this.mFragments.dispatchPause();
        this.mCalled = false;
        this.onPause();
        this.writeEventLog(30021, "performPause");
        this.mResumed = false;
        if (!this.mCalled && this.getApplicationInfo().targetSdkVersion >= 9) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Activity ");
            stringBuilder.append(this.mComponent.toShortString());
            stringBuilder.append(" did not call through to super.onPause()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        this.dispatchActivityPostPaused();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void performRestart(boolean bl, String charSequence) {
        this.mCanEnterPictureInPicture = true;
        this.mFragments.noteStateNotSaved();
        if (this.mToken != null && this.mParent == null) {
            WindowManagerGlobal.getInstance().setStoppedState(this.mToken, false);
        }
        if (!this.mStopped) return;
        this.mStopped = false;
        ArrayList<ManagedCursor> arrayList = this.mManagedCursors;
        synchronized (arrayList) {
            int n = this.mManagedCursors.size();
            for (int i = 0; i < n; ++i) {
                ManagedCursor managedCursor = this.mManagedCursors.get(i);
                if (!managedCursor.mReleased && !managedCursor.mUpdated) continue;
                if (!managedCursor.mCursor.requery() && this.getApplicationInfo().targetSdkVersion >= 14) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("trying to requery an already closed cursor  ");
                    ((StringBuilder)charSequence).append(managedCursor.mCursor);
                    IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)charSequence).toString());
                    throw illegalStateException;
                }
                managedCursor.mReleased = false;
                managedCursor.mUpdated = false;
            }
        }
        this.mCalled = false;
        this.mInstrumentation.callActivityOnRestart(this);
        this.writeEventLog(30058, (String)charSequence);
        if (this.mCalled) {
            if (!bl) return;
            this.performStart((String)charSequence);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Activity ");
        ((StringBuilder)charSequence).append(this.mComponent.toShortString());
        ((StringBuilder)charSequence).append(" did not call through to super.onRestart()");
        throw new SuperNotCalledException(((StringBuilder)charSequence).toString());
    }

    final void performRestoreInstanceState(Bundle bundle) {
        this.onRestoreInstanceState(bundle);
        this.restoreManagedDialogs(bundle);
    }

    final void performRestoreInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        this.onRestoreInstanceState(bundle, persistableBundle);
        if (bundle != null) {
            this.restoreManagedDialogs(bundle);
        }
    }

    final void performResume(boolean bl, String charSequence) {
        this.dispatchActivityPreResumed();
        this.performRestart(true, (String)charSequence);
        this.mFragments.execPendingActions();
        this.mLastNonConfigurationInstances = null;
        if (this.mAutoFillResetNeeded) {
            bl = this.mAutoFillIgnoreFirstResumePause = bl;
        }
        this.mCalled = false;
        this.mInstrumentation.callActivityOnResume(this);
        this.writeEventLog(30022, (String)charSequence);
        if (this.mCalled) {
            if (!this.mVisibleFromClient && !this.mFinished) {
                Log.w("Activity", "An activity without a UI must call finish() before onResume() completes");
                if (this.getApplicationInfo().targetSdkVersion > 22) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Activity ");
                    ((StringBuilder)charSequence).append(this.mComponent.toShortString());
                    ((StringBuilder)charSequence).append(" did not call finish() prior to onResume() completing");
                    throw new IllegalStateException(((StringBuilder)charSequence).toString());
                }
            }
            this.mCalled = false;
            this.mFragments.dispatchResume();
            this.mFragments.execPendingActions();
            this.onPostResume();
            if (this.mCalled) {
                this.dispatchActivityPostResumed();
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Activity ");
            ((StringBuilder)charSequence).append(this.mComponent.toShortString());
            ((StringBuilder)charSequence).append(" did not call through to super.onPostResume()");
            throw new SuperNotCalledException(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Activity ");
        ((StringBuilder)charSequence).append(this.mComponent.toShortString());
        ((StringBuilder)charSequence).append(" did not call through to super.onResume()");
        throw new SuperNotCalledException(((StringBuilder)charSequence).toString());
    }

    final void performSaveInstanceState(Bundle bundle) {
        this.dispatchActivityPreSaveInstanceState(bundle);
        this.onSaveInstanceState(bundle);
        this.saveManagedDialogs(bundle);
        this.mActivityTransitionState.saveState(bundle);
        this.storeHasCurrentPermissionRequest(bundle);
        this.dispatchActivityPostSaveInstanceState(bundle);
    }

    final void performSaveInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        this.dispatchActivityPreSaveInstanceState(bundle);
        this.onSaveInstanceState(bundle, persistableBundle);
        this.saveManagedDialogs(bundle);
        this.storeHasCurrentPermissionRequest(bundle);
        this.dispatchActivityPostSaveInstanceState(bundle);
    }

    final void performStart(String charSequence) {
        this.dispatchActivityPreStarted();
        this.mActivityTransitionState.setEnterActivityOptions(this, this.getActivityOptions());
        this.mFragments.noteStateNotSaved();
        this.mCalled = false;
        this.mFragments.execPendingActions();
        this.mInstrumentation.callActivityOnStart(this);
        this.writeEventLog(30059, (String)charSequence);
        if (this.mCalled) {
            String string2;
            this.mFragments.dispatchStart();
            this.mFragments.reportLoaderStart();
            boolean bl = (this.mApplication.getApplicationInfo().flags & 2) != 0;
            boolean bl2 = SystemProperties.getInt("ro.bionic.ld.warning", 0) == 1;
            if ((bl || bl2) && (string2 = Activity.getDlWarning()) != null) {
                charSequence = this.getApplicationInfo().loadLabel(this.getPackageManager()).toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Detected problems with app native libraries\n(please consult log for detail):\n");
                stringBuilder.append(string2);
                string2 = stringBuilder.toString();
                if (bl) {
                    new AlertDialog.Builder(this).setTitle(charSequence).setMessage(string2).setPositiveButton(17039370, null).setCancelable(false).show();
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append("\n");
                    stringBuilder.append(string2);
                    Toast.makeText((Context)this, stringBuilder.toString(), 1).show();
                }
            }
            GraphicsEnvironment.getInstance().showAngleInUseDialogBox(this);
            this.mActivityTransitionState.enterReady(this);
            this.dispatchActivityPostStarted();
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Activity ");
        ((StringBuilder)charSequence).append(this.mComponent.toShortString());
        ((StringBuilder)charSequence).append(" did not call through to super.onStart()");
        throw new SuperNotCalledException(((StringBuilder)charSequence).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void performStop(boolean bl, String object) {
        this.mDoReportFullyDrawn = false;
        this.mFragments.doLoaderStop(this.mChangingConfigurations);
        this.mCanEnterPictureInPicture = false;
        if (!this.mStopped) {
            this.dispatchActivityPreStopped();
            Object object2 = this.mWindow;
            if (object2 != null) {
                ((Window)object2).closeAllPanels();
            }
            if (!bl && this.mToken != null && this.mParent == null) {
                WindowManagerGlobal.getInstance().setStoppedState(this.mToken, true);
            }
            this.mFragments.dispatchStop();
            this.mCalled = false;
            this.mInstrumentation.callActivityOnStop(this);
            this.writeEventLog(30049, (String)object);
            if (!this.mCalled) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Activity ");
                ((StringBuilder)object).append(this.mComponent.toShortString());
                ((StringBuilder)object).append(" did not call through to super.onStop()");
                throw new SuperNotCalledException(((StringBuilder)object).toString());
            }
            object = this.mManagedCursors;
            synchronized (object) {
                int n = this.mManagedCursors.size();
                for (int i = 0; i < n; ++i) {
                    object2 = this.mManagedCursors.get(i);
                    if (((ManagedCursor)object2).mReleased) continue;
                    ((ManagedCursor)object2).mCursor.deactivate();
                    ((ManagedCursor)object2).mReleased = true;
                }
            }
            this.mStopped = true;
            this.dispatchActivityPostStopped();
        }
        this.mResumed = false;
    }

    final void performTopResumedActivityChanged(boolean bl, String string2) {
        this.onTopResumedActivityChanged(bl);
        int n = bl ? 30064 : 30065;
        this.writeEventLog(n, string2);
    }

    final void performUserLeaving() {
        this.onUserInteraction();
        this.onUserLeaveHint();
    }

    public void postponeEnterTransition() {
        this.mActivityTransitionState.postponeEnterTransition();
    }

    public void recreate() {
        if (this.mParent == null) {
            if (Looper.myLooper() == this.mMainThread.getLooper()) {
                this.mMainThread.scheduleRelaunchActivity(this.mToken);
                return;
            }
            throw new IllegalStateException("Must be called from main thread");
        }
        throw new IllegalStateException("Can only be called on top-level activity");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        ArrayList<Application.ActivityLifecycleCallbacks> arrayList = this.mActivityLifecycleCallbacks;
        synchronized (arrayList) {
            this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
            return;
        }
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    @UnsupportedAppUsage
    public void registerRemoteAnimations(RemoteAnimationDefinition remoteAnimationDefinition) {
        try {
            ActivityTaskManager.getService().registerRemoteAnimations(this.mToken, remoteAnimationDefinition);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean releaseInstance() {
        try {
            boolean bl = ActivityTaskManager.getService().releaseActivityInstance(this.mToken);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Deprecated
    public final void removeDialog(int n) {
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray != null && (sparseArray = sparseArray.get(n)) != null) {
            ((ManagedDialog)sparseArray).mDialog.dismiss();
            this.mManagedDialogs.remove(n);
        }
    }

    public void reportFullyDrawn() {
        if (this.mDoReportFullyDrawn) {
            this.mDoReportFullyDrawn = false;
            try {
                ActivityTaskManager.getService().reportActivityFullyDrawn(this.mToken, this.mRestoredFromBundle);
                VMRuntime.getRuntime().notifyStartupCompleted();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public DragAndDropPermissions requestDragAndDropPermissions(DragEvent parcelable) {
        if ((parcelable = DragAndDropPermissions.obtain((DragEvent)parcelable)) != null && ((DragAndDropPermissions)parcelable).take(this.getActivityToken())) {
            return parcelable;
        }
        return null;
    }

    public final void requestPermissions(String[] arrstring, int n) {
        if (n >= 0) {
            if (this.mHasCurrentPermissionsRequest) {
                Log.w("Activity", "Can request only one set of permissions at a time");
                this.onRequestPermissionsResult(n, new String[0], new int[0]);
                return;
            }
            this.startActivityForResult("@android:requestPermissions:", this.getPackageManager().buildRequestPermissionsIntent(arrstring), n, null);
            this.mHasCurrentPermissionsRequest = true;
            return;
        }
        throw new IllegalArgumentException("requestCode should be >= 0");
    }

    public final void requestShowKeyboardShortcuts() {
        Intent intent = new Intent("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS");
        intent.setPackage("com.android.systemui");
        this.sendBroadcastAsUser(intent, Process.myUserHandle());
    }

    @Deprecated
    public boolean requestVisibleBehind(boolean bl) {
        return false;
    }

    public final boolean requestWindowFeature(int n) {
        return this.getWindow().requestFeature(n);
    }

    public final <T extends View> T requireViewById(int n) {
        T t = this.findViewById(n);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Activity");
    }

    NonConfigurationInstances retainNonConfigurationInstances() {
        Object object = this.onRetainNonConfigurationInstance();
        Object object2 = this.onRetainNonConfigurationChildInstances();
        FragmentManagerNonConfig fragmentManagerNonConfig = this.mFragments.retainNestedNonConfig();
        this.mFragments.doLoaderStart();
        this.mFragments.doLoaderStop(true);
        ArrayMap<String, LoaderManager> arrayMap = this.mFragments.retainLoaderNonConfig();
        if (object == null && object2 == null && fragmentManagerNonConfig == null && arrayMap == null && this.mVoiceInteractor == null) {
            return null;
        }
        NonConfigurationInstances nonConfigurationInstances = new NonConfigurationInstances();
        nonConfigurationInstances.activity = object;
        nonConfigurationInstances.children = object2;
        nonConfigurationInstances.fragments = fragmentManagerNonConfig;
        nonConfigurationInstances.loaders = arrayMap;
        object2 = this.mVoiceInteractor;
        if (object2 != null) {
            ((VoiceInteractor)object2).retainInstance();
            nonConfigurationInstances.voiceInteractor = this.mVoiceInteractor;
        }
        return nonConfigurationInstances;
    }

    public final void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public void setActionBar(Toolbar object) {
        ActionBar actionBar = this.getActionBar();
        if (!(actionBar instanceof WindowDecorActionBar)) {
            this.mMenuInflater = null;
            if (actionBar != null) {
                actionBar.onDestroy();
            }
            if (object != null) {
                this.mActionBar = object = new ToolbarActionBar((Toolbar)object, this.getTitle(), this);
                this.mWindow.setCallback(((ToolbarActionBar)object).getWrappedWindowCallback());
            } else {
                this.mActionBar = null;
                this.mWindow.setCallback(this);
            }
            this.invalidateOptionsMenu();
            return;
        }
        throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_ACTION_BAR and set android:windowActionBar to false in your theme to use a Toolbar instead.");
    }

    public void setContentTransitionManager(TransitionManager transitionManager) {
        this.getWindow().setTransitionManager(transitionManager);
    }

    public void setContentView(int n) {
        this.getWindow().setContentView(n);
        this.initWindowDecorActionBar();
    }

    public void setContentView(View view) {
        this.getWindow().setContentView(view);
        this.initWindowDecorActionBar();
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getWindow().setContentView(view, layoutParams);
        this.initWindowDecorActionBar();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setDefaultKeyMode(int n) {
        block4 : {
            block5 : {
                this.mDefaultKeyMode = n;
                if (n == 0) break block4;
                if (n == 1) break block5;
                if (n == 2) break block4;
                if (n != 3) {
                    if (n != 4) throw new IllegalArgumentException();
                }
            }
            this.mDefaultKeySsb = new SpannableStringBuilder();
            Selection.setSelection(this.mDefaultKeySsb, 0);
            return;
        }
        this.mDefaultKeySsb = null;
    }

    @UnsupportedAppUsage
    public void setDisablePreviewScreenshots(boolean bl) {
        try {
            ActivityTaskManager.getService().setDisablePreviewScreenshots(this.mToken, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback sharedElementCallback2 = sharedElementCallback;
        if (sharedElementCallback == null) {
            sharedElementCallback2 = SharedElementCallback.NULL_CALLBACK;
        }
        this.mEnterTransitionListener = sharedElementCallback2;
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback sharedElementCallback2 = sharedElementCallback;
        if (sharedElementCallback == null) {
            sharedElementCallback2 = SharedElementCallback.NULL_CALLBACK;
        }
        this.mExitTransitionListener = sharedElementCallback2;
    }

    public final void setFeatureDrawable(int n, Drawable drawable2) {
        this.getWindow().setFeatureDrawable(n, drawable2);
    }

    public final void setFeatureDrawableAlpha(int n, int n2) {
        this.getWindow().setFeatureDrawableAlpha(n, n2);
    }

    public final void setFeatureDrawableResource(int n, int n2) {
        this.getWindow().setFeatureDrawableResource(n, n2);
    }

    public final void setFeatureDrawableUri(int n, Uri uri) {
        this.getWindow().setFeatureDrawableUri(n, uri);
    }

    public void setFinishOnTouchOutside(boolean bl) {
        this.mWindow.setCloseOnTouchOutside(bl);
    }

    public void setImmersive(boolean bl) {
        try {
            ActivityTaskManager.getService().setImmersive(this.mToken, bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void setInheritShowWhenLocked(boolean bl) {
        try {
            ActivityTaskManager.getService().setInheritShowWhenLocked(this.mToken, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public final void setMediaController(MediaController mediaController) {
        this.getWindow().setMediaController(mediaController);
    }

    public void setOverlayWithDecorCaptionEnabled(boolean bl) {
        this.mWindow.setOverlayWithDecorCaptionEnabled(bl);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final void setParent(Activity activity) {
        this.mParent = activity;
    }

    @Deprecated
    @UnsupportedAppUsage
    public void setPersistent(boolean bl) {
    }

    @Deprecated
    public void setPictureInPictureArgs(PictureInPictureArgs pictureInPictureArgs) {
        this.setPictureInPictureParams(PictureInPictureArgs.convert(pictureInPictureArgs));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPictureInPictureParams(PictureInPictureParams object) {
        try {
            if (!this.deviceSupportsPictureInPictureMode()) {
                return;
            }
            if (object != null) {
                ActivityTaskManager.getService().setPictureInPictureParams(this.mToken, (PictureInPictureParams)object);
                return;
            }
            object = new IllegalArgumentException("Expected non-null picture-in-picture params");
            throw object;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Deprecated
    public final void setProgress(int n) {
        this.getWindow().setFeatureInt(2, n + 0);
    }

    @Deprecated
    public final void setProgressBarIndeterminate(boolean bl) {
        Window window = this.getWindow();
        int n = bl ? -3 : -4;
        window.setFeatureInt(2, n);
    }

    @Deprecated
    public final void setProgressBarIndeterminateVisibility(boolean bl) {
        Window window = this.getWindow();
        int n = bl ? -1 : -2;
        window.setFeatureInt(5, n);
    }

    @Deprecated
    public final void setProgressBarVisibility(boolean bl) {
        Window window = this.getWindow();
        int n = bl ? -1 : -2;
        window.setFeatureInt(2, n);
    }

    public void setRequestedOrientation(int n) {
        Activity activity = this.mParent;
        if (activity == null) {
            try {
                ActivityTaskManager.getService().setRequestedOrientation(this.mToken, n);
            }
            catch (RemoteException remoteException) {}
        } else {
            activity.setRequestedOrientation(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setResult(int n) {
        synchronized (this) {
            this.mResultCode = n;
            this.mResultData = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setResult(int n, Intent intent) {
        synchronized (this) {
            this.mResultCode = n;
            this.mResultData = intent;
            return;
        }
    }

    @Deprecated
    public final void setSecondaryProgress(int n) {
        this.getWindow().setFeatureInt(2, n + 20000);
    }

    public void setShowWhenLocked(boolean bl) {
        try {
            ActivityTaskManager.getService().setShowWhenLocked(this.mToken, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTaskDescription(ActivityManager.TaskDescription parcelable) {
        ActivityManager.TaskDescription taskDescription = this.mTaskDescription;
        if (taskDescription != parcelable) {
            taskDescription.copyFromPreserveHiddenFields((ActivityManager.TaskDescription)parcelable);
            if (parcelable.getIconFilename() == null && parcelable.getIcon() != null) {
                int n = ActivityManager.getLauncherLargeIconSizeInner(this);
                parcelable = Bitmap.createScaledBitmap(parcelable.getIcon(), n, n, true);
                this.mTaskDescription.setIcon((Bitmap)parcelable);
            }
        }
        try {
            ActivityTaskManager.getService().setTaskDescription(this.mToken, this.mTaskDescription);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setTheme(int n) {
        super.setTheme(n);
        this.mWindow.setTheme(n);
    }

    public void setTitle(int n) {
        this.setTitle(this.getText(n));
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.onTitleChanged(charSequence, this.mTitleColor);
        Activity activity = this.mParent;
        if (activity != null) {
            activity.onChildTitleChanged(this, charSequence);
        }
    }

    @Deprecated
    public void setTitleColor(int n) {
        this.mTitleColor = n;
        this.onTitleChanged(this.mTitle, n);
    }

    public void setTurnScreenOn(boolean bl) {
        try {
            ActivityTaskManager.getService().setTurnScreenOn(this.mToken, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setVisible(boolean bl) {
        if (this.mVisibleFromClient != bl) {
            this.mVisibleFromClient = bl;
            if (this.mVisibleFromServer) {
                if (bl) {
                    this.makeVisible();
                } else {
                    this.mDecor.setVisibility(4);
                }
            }
        }
    }

    void setVoiceInteractor(IVoiceInteractor iVoiceInteractor) {
        VoiceInteractor object2 = this.mVoiceInteractor;
        if (object2 != null && object2.getActiveRequests() != null) {
            for (VoiceInteractor.Request request : this.mVoiceInteractor.getActiveRequests()) {
                request.cancel();
                request.clear();
            }
        }
        this.mVoiceInteractor = iVoiceInteractor == null ? null : new VoiceInteractor(iVoiceInteractor, this, this, Looper.myLooper());
    }

    public final void setVolumeControlStream(int n) {
        this.getWindow().setVolumeControlStream(n);
    }

    public void setVrModeEnabled(boolean bl, ComponentName componentName) throws PackageManager.NameNotFoundException {
        try {
            if (ActivityTaskManager.getService().setVrMode(this.mToken, bl, componentName) != 0) {
                PackageManager.NameNotFoundException nameNotFoundException = new PackageManager.NameNotFoundException(componentName.flattenToString());
                throw nameNotFoundException;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public boolean shouldShowRequestPermissionRationale(String string2) {
        return this.getPackageManager().shouldShowRequestPermissionRationale(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean shouldUpRecreateTask(Intent parcelable) {
        try {
            ComponentName componentName;
            PackageManager packageManager = this.getPackageManager();
            ComponentName componentName2 = componentName = ((Intent)parcelable).getComponent();
            if (componentName == null) {
                componentName2 = ((Intent)parcelable).resolveActivity(packageManager);
            }
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName2, 0);
            if (activityInfo.taskAffinity != null) return ActivityTaskManager.getService().shouldUpRecreateTask(this.mToken, activityInfo.taskAffinity);
            return false;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean showAssist(Bundle bundle) {
        try {
            boolean bl = ActivityTaskManager.getService().showAssistFromActivity(this.mToken, bundle);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Deprecated
    public final void showDialog(int n) {
        this.showDialog(n, null);
    }

    @Deprecated
    public final boolean showDialog(int n, Bundle bundle) {
        ManagedDialog managedDialog;
        if (this.mManagedDialogs == null) {
            this.mManagedDialogs = new SparseArray<E>();
        }
        ManagedDialog managedDialog2 = managedDialog = this.mManagedDialogs.get(n);
        if (managedDialog == null) {
            managedDialog2 = new ManagedDialog();
            managedDialog2.mDialog = this.createDialog(n, null, bundle);
            if (managedDialog2.mDialog == null) {
                return false;
            }
            this.mManagedDialogs.put(n, managedDialog2);
        }
        managedDialog2.mArgs = bundle;
        this.onPrepareDialog(n, managedDialog2.mDialog, bundle);
        managedDialog2.mDialog.show();
        return true;
    }

    public void showLockTaskEscapeMessage() {
        try {
            ActivityTaskManager.getService().showLockTaskEscapeMessage(this.mToken);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.mWindow.getDecorView().startActionMode(callback);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int n) {
        return this.mWindow.getDecorView().startActionMode(callback, n);
    }

    @Override
    public void startActivities(Intent[] arrintent) {
        this.startActivities(arrintent, null);
    }

    @Override
    public void startActivities(Intent[] arrintent, Bundle bundle) {
        this.mInstrumentation.execStartActivities(this, this.mMainThread.getApplicationThread(), this.mToken, this, arrintent, bundle);
    }

    @Override
    public void startActivity(Intent intent) {
        this.startActivity(intent, null);
    }

    @Override
    public void startActivity(Intent intent, Bundle bundle) {
        if (bundle != null) {
            this.startActivityForResult(intent, -1, bundle);
        } else {
            this.startActivityForResult(intent, -1);
        }
    }

    public void startActivityAsCaller(Intent object, Bundle bundle, IBinder iBinder, boolean bl, int n) {
        if (this.mParent == null) {
            bundle = this.transferSpringboardActivityOptions(bundle);
            object = this.mInstrumentation.execStartActivityAsCaller(this, this.mMainThread.getApplicationThread(), this.mToken, this, (Intent)object, -1, bundle, iBinder, bl, n);
            if (object != null) {
                this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, -1, ((Instrumentation.ActivityResult)object).getResultCode(), ((Instrumentation.ActivityResult)object).getResultData());
            }
            this.cancelInputsAndStartExitTransition(bundle);
            return;
        }
        throw new RuntimeException("Can't be called from a child");
    }

    @Override
    public void startActivityAsUser(Intent object, Bundle bundle, UserHandle userHandle) {
        if (this.mParent == null) {
            bundle = this.transferSpringboardActivityOptions(bundle);
            object = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, this.mEmbeddedID, (Intent)object, -1, bundle, userHandle);
            if (object != null) {
                this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, -1, ((Instrumentation.ActivityResult)object).getResultCode(), ((Instrumentation.ActivityResult)object).getResultData());
            }
            this.cancelInputsAndStartExitTransition(bundle);
            return;
        }
        throw new RuntimeException("Can't be called from a child");
    }

    @Override
    public void startActivityAsUser(Intent intent, UserHandle userHandle) {
        this.startActivityAsUser(intent, null, userHandle);
    }

    public void startActivityAsUserFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle, UserHandle userHandle) {
        this.startActivityForResultAsUser(intent, fragment.mWho, n, bundle, userHandle);
    }

    public void startActivityForResult(Intent intent, int n) {
        this.startActivityForResult(intent, n, null);
    }

    public void startActivityForResult(Intent object, int n, Bundle bundle) {
        Activity activity = this.mParent;
        if (activity == null) {
            bundle = this.transferSpringboardActivityOptions(bundle);
            object = this.mInstrumentation.execStartActivity((Context)this, (IBinder)this.mMainThread.getApplicationThread(), this.mToken, this, (Intent)object, n, bundle);
            if (object != null) {
                this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, n, ((Instrumentation.ActivityResult)object).getResultCode(), ((Instrumentation.ActivityResult)object).getResultData());
            }
            if (n >= 0) {
                this.mStartedActivity = true;
            }
            this.cancelInputsAndStartExitTransition(bundle);
        } else if (bundle != null) {
            activity.startActivityFromChild(this, (Intent)object, n, bundle);
        } else {
            activity.startActivityFromChild(this, (Intent)object, n);
        }
    }

    @UnsupportedAppUsage
    @Override
    public void startActivityForResult(String string2, Intent object, int n, Bundle bundle) {
        Uri uri = this.onProvideReferrer();
        if (uri != null) {
            ((Intent)object).putExtra("android.intent.extra.REFERRER", uri);
        }
        bundle = this.transferSpringboardActivityOptions(bundle);
        object = this.mInstrumentation.execStartActivity((Context)this, (IBinder)this.mMainThread.getApplicationThread(), this.mToken, string2, (Intent)object, n, bundle);
        if (object != null) {
            this.mMainThread.sendActivityResult(this.mToken, string2, n, ((Instrumentation.ActivityResult)object).getResultCode(), ((Instrumentation.ActivityResult)object).getResultData());
        }
        this.cancelInputsAndStartExitTransition(bundle);
    }

    public void startActivityForResultAsUser(Intent intent, int n, Bundle bundle, UserHandle userHandle) {
        this.startActivityForResultAsUser(intent, this.mEmbeddedID, n, bundle, userHandle);
    }

    @UnsupportedAppUsage
    public void startActivityForResultAsUser(Intent intent, int n, UserHandle userHandle) {
        this.startActivityForResultAsUser(intent, n, null, userHandle);
    }

    public void startActivityForResultAsUser(Intent object, String string2, int n, Bundle bundle, UserHandle userHandle) {
        if (this.mParent == null) {
            bundle = this.transferSpringboardActivityOptions(bundle);
            object = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, string2, (Intent)object, n, bundle, userHandle);
            if (object != null) {
                this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, n, ((Instrumentation.ActivityResult)object).getResultCode(), ((Instrumentation.ActivityResult)object).getResultData());
            }
            if (n >= 0) {
                this.mStartedActivity = true;
            }
            this.cancelInputsAndStartExitTransition(bundle);
            return;
        }
        throw new RuntimeException("Can't be called from a child");
    }

    public void startActivityFromChild(Activity activity, Intent intent, int n) {
        this.startActivityFromChild(activity, intent, n, null);
    }

    public void startActivityFromChild(Activity activity, Intent object, int n, Bundle bundle) {
        bundle = this.transferSpringboardActivityOptions(bundle);
        object = this.mInstrumentation.execStartActivity((Context)this, (IBinder)this.mMainThread.getApplicationThread(), this.mToken, activity, (Intent)object, n, bundle);
        if (object != null) {
            this.mMainThread.sendActivityResult(this.mToken, activity.mEmbeddedID, n, ((Instrumentation.ActivityResult)object).getResultCode(), ((Instrumentation.ActivityResult)object).getResultData());
        }
        this.cancelInputsAndStartExitTransition(bundle);
    }

    @Deprecated
    public void startActivityFromFragment(Fragment fragment, Intent intent, int n) {
        this.startActivityFromFragment(fragment, intent, n, null);
    }

    @Deprecated
    public void startActivityFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle) {
        this.startActivityForResult(fragment.mWho, intent, n, bundle);
    }

    public boolean startActivityIfNeeded(Intent intent, int n) {
        return this.startActivityIfNeeded(intent, n, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean startActivityIfNeeded(Intent intent, int n, Bundle bundle) {
        if (this.mParent != null) throw new UnsupportedOperationException("startActivityIfNeeded can only be called from a top-level activity");
        int n2 = 1;
        try {
            int n3;
            Uri uri = this.onProvideReferrer();
            if (uri != null) {
                intent.putExtra("android.intent.extra.REFERRER", uri);
            }
            intent.migrateExtraStreamToClipData();
            intent.prepareToLeaveProcess(this);
            n2 = n3 = ActivityTaskManager.getService().startActivity(this.mMainThread.getApplicationThread(), this.getBasePackageName(), intent, intent.resolveTypeIfNeeded(this.getContentResolver()), this.mToken, this.mEmbeddedID, n, 1, null, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        Instrumentation.checkStartActivityResult(n2, intent);
        boolean bl = true;
        if (n >= 0) {
            this.mStartedActivity = true;
        }
        if (n2 == 1) return false;
        return bl;
    }

    @Override
    public void startIntentSender(IntentSender intentSender, Intent intent, int n, int n2, int n3) throws IntentSender.SendIntentException {
        this.startIntentSender(intentSender, intent, n, n2, n3, null);
    }

    @Override
    public void startIntentSender(IntentSender intentSender, Intent intent, int n, int n2, int n3, Bundle bundle) throws IntentSender.SendIntentException {
        if (bundle != null) {
            this.startIntentSenderForResult(intentSender, -1, intent, n, n2, n3, bundle);
        } else {
            this.startIntentSenderForResult(intentSender, -1, intent, n, n2, n3);
        }
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4) throws IntentSender.SendIntentException {
        this.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4, null);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        Activity activity = this.mParent;
        if (activity == null) {
            this.startIntentSenderForResultInner(intentSender, this.mEmbeddedID, n, intent, n2, n3, bundle);
        } else if (bundle != null) {
            activity.startIntentSenderFromChild(this, intentSender, n, intent, n2, n3, n4, bundle);
        } else {
            activity.startIntentSenderFromChild(this, intentSender, n, intent, n2, n3, n4);
        }
    }

    public void startIntentSenderFromChild(Activity activity, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4) throws IntentSender.SendIntentException {
        this.startIntentSenderFromChild(activity, intentSender, n, intent, n2, n3, n4, null);
    }

    public void startIntentSenderFromChild(Activity activity, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        this.startIntentSenderForResultInner(intentSender, activity.mEmbeddedID, n, intent, n2, n3, bundle);
    }

    public void startIntentSenderFromChildFragment(Fragment fragment, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        this.startIntentSenderForResultInner(intentSender, fragment.mWho, n, intent, n2, n3, bundle);
    }

    public void startLocalVoiceInteraction(Bundle bundle) {
        try {
            ActivityTaskManager.getService().startLocalVoiceInteraction(this.mToken, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void startLockTask() {
        try {
            ActivityTaskManager.getService().startLockTaskModeByToken(this.mToken);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void startManagingCursor(Cursor cursor) {
        ArrayList<ManagedCursor> arrayList = this.mManagedCursors;
        synchronized (arrayList) {
            ArrayList<ManagedCursor> arrayList2 = this.mManagedCursors;
            ManagedCursor managedCursor = new ManagedCursor(cursor);
            arrayList2.add(managedCursor);
            return;
        }
    }

    public boolean startNextMatchingActivity(Intent intent) {
        return this.startNextMatchingActivity(intent, null);
    }

    public boolean startNextMatchingActivity(Intent intent, Bundle bundle) {
        if (this.mParent == null) {
            try {
                intent.migrateExtraStreamToClipData();
                intent.prepareToLeaveProcess(this);
                boolean bl = ActivityTaskManager.getService().startNextMatchingActivity(this.mToken, intent, bundle);
                return bl;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        throw new UnsupportedOperationException("startNextMatchingActivity can only be called from a top-level activity");
    }

    public void startPostponedEnterTransition() {
        this.mActivityTransitionState.startPostponedEnterTransition();
    }

    public void startSearch(String string2, boolean bl, Bundle bundle, boolean bl2) {
        this.ensureSearchManager();
        this.mSearchManager.startSearch(string2, bl, this.getComponentName(), bundle, bl2);
    }

    public void stopLocalVoiceInteraction() {
        try {
            ActivityTaskManager.getService().stopLocalVoiceInteraction(this.mToken);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void stopLockTask() {
        try {
            ActivityTaskManager.getService().stopLockTaskModeByToken(this.mToken);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void stopManagingCursor(Cursor cursor) {
        ArrayList<ManagedCursor> arrayList = this.mManagedCursors;
        synchronized (arrayList) {
            int n = this.mManagedCursors.size();
            for (int i = 0; i < n; ++i) {
                if (this.mManagedCursors.get(i).mCursor != cursor) continue;
                this.mManagedCursors.remove(i);
                break;
            }
            return;
        }
    }

    public void takeKeyEvents(boolean bl) {
        this.getWindow().takeKeyEvents(bl);
    }

    @Override
    public void toggleFreeformWindowingMode() throws RemoteException {
        ActivityTaskManager.getService().toggleFreeformWindowingMode(this.mToken);
    }

    public void triggerSearch(String string2, Bundle bundle) {
        this.ensureSearchManager();
        this.mSearchManager.triggerSearch(string2, this.getComponentName(), bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        ArrayList<Application.ActivityLifecycleCallbacks> arrayList = this.mActivityLifecycleCallbacks;
        synchronized (arrayList) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacks);
            return;
        }
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface ContentCaptureNotificationType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface DefaultKeyMode {
    }

    class HostCallbacks
    extends FragmentHostCallback<Activity> {
        public HostCallbacks() {
            super(Activity.this);
        }

        @Override
        public void onAttachFragment(Fragment fragment) {
            Activity.this.onAttachFragment(fragment);
        }

        @Override
        public void onDump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            Activity.this.dump(string2, fileDescriptor, printWriter, arrstring);
        }

        @Override
        public <T extends View> T onFindViewById(int n) {
            return Activity.this.findViewById(n);
        }

        @Override
        public Activity onGetHost() {
            return Activity.this;
        }

        @Override
        public LayoutInflater onGetLayoutInflater() {
            LayoutInflater layoutInflater = Activity.this.getLayoutInflater();
            if (this.onUseFragmentManagerInflaterFactory()) {
                return layoutInflater.cloneInContext(Activity.this);
            }
            return layoutInflater;
        }

        @Override
        public int onGetWindowAnimations() {
            Window window = Activity.this.getWindow();
            int n = window == null ? 0 : window.getAttributes().windowAnimations;
            return n;
        }

        @Override
        public boolean onHasView() {
            Window window = Activity.this.getWindow();
            boolean bl = window != null && window.peekDecorView() != null;
            return bl;
        }

        @Override
        public boolean onHasWindowAnimations() {
            boolean bl = Activity.this.getWindow() != null;
            return bl;
        }

        @Override
        public void onInvalidateOptionsMenu() {
            Activity.this.invalidateOptionsMenu();
        }

        @Override
        public void onRequestPermissionsFromFragment(Fragment object, String[] object2, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Activity.REQUEST_PERMISSIONS_WHO_PREFIX);
            stringBuilder.append(((Fragment)object).mWho);
            object = stringBuilder.toString();
            object2 = Activity.this.getPackageManager().buildRequestPermissionsIntent((String[])object2);
            Activity.this.startActivityForResult((String)object, (Intent)object2, n, null);
        }

        @Override
        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return Activity.this.isFinishing() ^ true;
        }

        @Override
        public void onStartActivityAsUserFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle, UserHandle userHandle) {
            Activity.this.startActivityAsUserFromFragment(fragment, intent, n, bundle, userHandle);
        }

        @Override
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle) {
            Activity.this.startActivityFromFragment(fragment, intent, n, bundle);
        }

        @Override
        public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
            if (Activity.this.mParent == null) {
                Activity.this.startIntentSenderForResultInner(intentSender, fragment.mWho, n, intent, n2, n3, bundle);
            } else if (bundle != null) {
                Activity.this.mParent.startIntentSenderFromChildFragment(fragment, intentSender, n, intent, n2, n3, n4, bundle);
            }
        }

        @Override
        public boolean onUseFragmentManagerInflaterFactory() {
            boolean bl = Activity.this.getApplicationInfo().targetSdkVersion >= 21;
            return bl;
        }
    }

    private static final class ManagedCursor {
        private final Cursor mCursor;
        private boolean mReleased;
        private boolean mUpdated;

        ManagedCursor(Cursor cursor) {
            this.mCursor = cursor;
            this.mReleased = false;
            this.mUpdated = false;
        }
    }

    private static class ManagedDialog {
        Bundle mArgs;
        Dialog mDialog;

        private ManagedDialog() {
        }
    }

    static final class NonConfigurationInstances {
        Object activity;
        HashMap<String, Object> children;
        FragmentManagerNonConfig fragments;
        ArrayMap<String, LoaderManager> loaders;
        VoiceInteractor voiceInteractor;

        NonConfigurationInstances() {
        }
    }

    @SystemApi
    public static interface TranslucentConversionListener {
        public void onTranslucentConversionComplete(boolean var1);
    }

}

