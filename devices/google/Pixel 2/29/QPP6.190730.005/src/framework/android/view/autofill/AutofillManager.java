/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.view.autofill;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SystemApi;
import android.content.AutofillOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.metrics.LogMaker;
import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.autofill.FillEventHistory;
import android.service.autofill.UserData;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.view.autofill.IAugmentedAutofillManagerClient;
import android.view.autofill.IAutoFillManager;
import android.view.autofill.IAutoFillManagerClient;
import android.view.autofill.IAutofillWindowPresenter;
import android.view.autofill.ParcelableMap;
import android.view.autofill._$$Lambda$AutofillManager$AugmentedAutofillManagerClient$OrAY5q15e0VwuCSYnsGgs6GcY1U;
import android.view.autofill._$$Lambda$AutofillManager$AugmentedAutofillManagerClient$k_qssZkEBwVEPdSmrHGsi2QT_3Y;
import android.view.autofill._$$Lambda$AutofillManager$AugmentedAutofillManagerClient$tbNtqpHgXnRdc3JO5HaBlxclFg0;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$1jAzMluMSJksx55SMUQn4BKB2Ng;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$BPlC2x7GLNHFS92rPUSzbcpFhUc;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$K79QnIPRaZuikYDQdsLcIUBhqiI;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$QIW_100CKwHzdHffwaus9KOEHCA;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$_IhPS_W7AwZ4M9TKIIFigmQd5SE;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$dCTetwfU0gT1ZrSzZGZiGStXlOY;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$eeFWMGoPtaXdpslR3NLvhgXvMMs;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$kRL9XILLc2XNr90gxVDACLzcyqc;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$pM5e3ez5KTBdZt4d8qLEERBUSiU;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$qH36EJk2Hkdja9ZZmTxqYPyr0YA;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$qyxZ4PACUgHFGSvMBHzgwjJ3yns;
import android.view.autofill._$$Lambda$AutofillManager$AutofillManagerClient$xqXjXW0fvc8JdYR5fgGKw9lJc3I;
import android.view.autofill._$$Lambda$AutofillManager$V76JiQu509LCUz3_ckpb_nB3JhA;
import android.view.autofill._$$Lambda$AutofillManager$YfpJNFodEuj5lbXfPlc77fsEvC8;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.SyncResultReceiver;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;
import sun.misc.Cleaner;

public final class AutofillManager {
    public static final int ACTION_START_SESSION = 1;
    public static final int ACTION_VALUE_CHANGED = 4;
    public static final int ACTION_VIEW_ENTERED = 2;
    public static final int ACTION_VIEW_EXITED = 3;
    private static final int AUTHENTICATION_ID_DATASET_ID_MASK = 65535;
    private static final int AUTHENTICATION_ID_DATASET_ID_SHIFT = 16;
    public static final int AUTHENTICATION_ID_DATASET_ID_UNDEFINED = 65535;
    public static final int DEFAULT_LOGGING_LEVEL;
    public static final int DEFAULT_MAX_PARTITIONS_SIZE = 10;
    public static final String DEVICE_CONFIG_AUGMENTED_SERVICE_IDLE_UNBIND_TIMEOUT = "augmented_service_idle_unbind_timeout";
    public static final String DEVICE_CONFIG_AUGMENTED_SERVICE_REQUEST_TIMEOUT = "augmented_service_request_timeout";
    public static final String DEVICE_CONFIG_AUTOFILL_SMART_SUGGESTION_SUPPORTED_MODES = "smart_suggestion_supported_modes";
    public static final String EXTRA_ASSIST_STRUCTURE = "android.view.autofill.extra.ASSIST_STRUCTURE";
    public static final String EXTRA_AUGMENTED_AUTOFILL_CLIENT = "android.view.autofill.extra.AUGMENTED_AUTOFILL_CLIENT";
    public static final String EXTRA_AUTHENTICATION_RESULT = "android.view.autofill.extra.AUTHENTICATION_RESULT";
    public static final String EXTRA_CLIENT_STATE = "android.view.autofill.extra.CLIENT_STATE";
    public static final String EXTRA_RESTORE_SESSION_TOKEN = "android.view.autofill.extra.RESTORE_SESSION_TOKEN";
    public static final int FC_SERVICE_TIMEOUT = 5000;
    public static final int FLAG_ADD_CLIENT_DEBUG = 2;
    public static final int FLAG_ADD_CLIENT_ENABLED = 1;
    public static final int FLAG_ADD_CLIENT_ENABLED_FOR_AUGMENTED_AUTOFILL_ONLY = 8;
    public static final int FLAG_ADD_CLIENT_VERBOSE = 4;
    public static final int FLAG_SMART_SUGGESTION_OFF = 0;
    public static final int FLAG_SMART_SUGGESTION_SYSTEM = 1;
    private static final String LAST_AUTOFILLED_DATA_TAG = "android:lastAutoFilledData";
    public static final int MAX_TEMP_AUGMENTED_SERVICE_DURATION_MS = 120000;
    public static final int NO_LOGGING = 0;
    public static final int NO_SESSION = Integer.MAX_VALUE;
    public static final int PENDING_UI_OPERATION_CANCEL = 1;
    public static final int PENDING_UI_OPERATION_RESTORE = 2;
    public static final int RECEIVER_FLAG_SESSION_FOR_AUGMENTED_AUTOFILL_ONLY = 1;
    public static final int RESULT_CODE_NOT_SERVICE = -1;
    public static final int RESULT_OK = 0;
    private static final String SESSION_ID_TAG = "android:sessionId";
    public static final int SET_STATE_FLAG_DEBUG = 8;
    public static final int SET_STATE_FLAG_ENABLED = 1;
    public static final int SET_STATE_FLAG_FOR_AUTOFILL_ONLY = 32;
    public static final int SET_STATE_FLAG_RESET_CLIENT = 4;
    public static final int SET_STATE_FLAG_RESET_SESSION = 2;
    public static final int SET_STATE_FLAG_VERBOSE = 16;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DISABLED_BY_SERVICE = 4;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_SHOWING_SAVE_UI = 3;
    private static final String STATE_TAG = "android:state";
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_UNKNOWN_COMPAT_MODE = 5;
    public static final int STATE_UNKNOWN_FAILED = 6;
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final String TAG = "AutofillManager";
    @GuardedBy(value={"mLock"})
    private IAugmentedAutofillManagerClient mAugmentedAutofillServiceClient;
    @GuardedBy(value={"mLock"})
    private AutofillCallback mCallback;
    @GuardedBy(value={"mLock"})
    private CompatibilityBridge mCompatibilityBridge;
    private final Context mContext;
    @GuardedBy(value={"mLock"})
    private boolean mEnabled;
    @GuardedBy(value={"mLock"})
    private boolean mEnabledForAugmentedAutofillOnly;
    @GuardedBy(value={"mLock"})
    private Set<AutofillId> mEnteredForAugmentedAutofillIds;
    @GuardedBy(value={"mLock"})
    private ArraySet<AutofillId> mEnteredIds;
    @GuardedBy(value={"mLock"})
    private ArraySet<AutofillId> mFillableIds;
    @GuardedBy(value={"mLock"})
    private boolean mForAugmentedAutofillOnly;
    private AutofillId mIdShownFillUi;
    @GuardedBy(value={"mLock"})
    private ParcelableMap mLastAutofilledData;
    private final Object mLock = new Object();
    private final MetricsLogger mMetricsLogger = new MetricsLogger();
    @GuardedBy(value={"mLock"})
    private boolean mOnInvisibleCalled;
    private final AutofillOptions mOptions;
    @GuardedBy(value={"mLock"})
    private boolean mSaveOnFinish;
    @GuardedBy(value={"mLock"})
    private AutofillId mSaveTriggerId;
    private final IAutoFillManager mService;
    @GuardedBy(value={"mLock"})
    private IAutoFillManagerClient mServiceClient;
    @GuardedBy(value={"mLock"})
    private Cleaner mServiceClientCleaner;
    @GuardedBy(value={"mLock"})
    private int mSessionId = Integer.MAX_VALUE;
    @GuardedBy(value={"mLock"})
    private int mState;
    @GuardedBy(value={"mLock"})
    private TrackedViews mTrackedViews;

    static {
        int n = Build.IS_DEBUGGABLE ? 2 : 0;
        DEFAULT_LOGGING_LEVEL = n;
    }

    public AutofillManager(Context object, IAutoFillManager iAutoFillManager) {
        boolean bl = false;
        this.mState = 0;
        this.mContext = Preconditions.checkNotNull(object, "context cannot be null");
        this.mService = iAutoFillManager;
        this.mOptions = ((Context)object).getAutofillOptions();
        object = this.mOptions;
        if (object != null) {
            boolean bl2 = (((AutofillOptions)object).loggingLevel & 2) != 0;
            Helper.sDebug = bl2;
            bl2 = bl;
            if ((this.mOptions.loggingLevel & 4) != 0) {
                bl2 = true;
            }
            Helper.sVerbose = bl2;
        }
    }

    @GuardedBy(value={"mLock"})
    private void addEnteredIdLocked(AutofillId autofillId) {
        if (this.mEnteredIds == null) {
            this.mEnteredIds = new ArraySet(1);
        }
        autofillId.resetSessionId();
        this.mEnteredIds.add(autofillId);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void authenticate(int n, int n2, IntentSender intentSender, Intent intent) {
        Object object = this.mLock;
        synchronized (object) {
            AutofillClient autofillClient;
            if (n == this.mSessionId && (autofillClient = this.getClient()) != null) {
                this.mOnInvisibleCalled = false;
                autofillClient.autofillClientAuthenticate(n2, intentSender, intent);
            }
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void autofill(int var1_1, List<AutofillId> var2_2, List<AutofillValue> var3_7) {
        block21 : {
            var4_8 = this.mLock;
            // MONITORENTER : var4_8
            if (var1_1 != this.mSessionId) {
                // MONITOREXIT : var4_8
                return;
            }
            var5_9 = this.getClient();
            if (var5_9 == null) {
                // MONITOREXIT : var4_8
                return;
            }
            var6_10 = var2_2.size();
            var7_11 = null;
            var8_12 = var5_9.autofillClientFindViewsByAutofillIdTraversal(Helper.toArray((Collection<AutofillId>)var2_2));
            var9_13 = null;
            var1_1 = 0;
            for (var10_14 = 0; var10_14 < var6_10; ++var10_14) {
                try {
                    var11_15 = (AutofillId)var2_2.get(var10_14);
                }
                catch (Throwable var2_3) {
                    throw var2_5;
                }
                var12_16 = (AutofillValue)var3_7.get(var10_14);
                var13_17 = var8_12[var10_14];
                if (var13_17 == null) {
                    var14_18 = new ArrayList<E>();
                    var14_18.append("autofill(): no View with id ");
                    var14_18.append((Object)var11_15);
                    Log.d("AutofillManager", var14_18.toString());
                    var14_18 = var9_13;
                    if (var9_13 == null) {
                        var14_18 = new ArrayList<E>();
                    }
                    var14_18.add(var11_15);
                    var9_13 = var14_18;
                    continue;
                }
                if (var11_15.isVirtualInt()) {
                    var14_18 = var7_11;
                    if (var7_11 == null) {
                        var14_18 = new ArrayList<E>(1);
                    }
                    var15_19 = (SparseArray)var14_18.get(var13_17);
                    var7_11 = var15_19;
                    if (var15_19 == null) {
                        var7_11 = new SparseArray(5);
                        var14_18.put(var13_17, var7_11);
                    }
                    var7_11.put(var11_15.getVirtualChildIntId(), var12_16);
                    var7_11 = var14_18;
                    continue;
                }
                if (this.mLastAutofilledData == null) {
                    var14_18 = new ArrayList<E>(var6_10 - var10_14);
                    this.mLastAutofilledData = var14_18;
                }
                this.mLastAutofilledData.put(var11_15, var12_16);
                var13_17.autofill(var12_16);
                this.setAutofilledIfValuesIs(var13_17, var12_16);
                ++var1_1;
                continue;
            }
            if (var9_13 == null) ** GOTO lbl76
            try {
                if (Helper.sVerbose) {
                    var2_2 = new StringBuilder();
                    var2_2.append("autofill(): total failed views: ");
                    var2_2.append(var9_13);
                    Log.v("AutofillManager", var2_2.toString());
                }
                try {
                    this.mService.setAutofillFailure(this.mSessionId, var9_13, this.mContext.getUserId());
                }
                catch (RemoteException var2_4) {
                    var2_4.rethrowFromSystemServer();
                }
lbl76: // 3 sources:
                var10_14 = var1_1;
                if (var7_11 == null) break block21;
                var16_20 = 0;
                do {
                    var10_14 = var1_1;
                    if (var16_20 >= var7_11.size()) ** break;
                    var2_2 = (View)var7_11.keyAt(var16_20);
                    var3_7 = (SparseArray)var7_11.valueAt(var16_20);
                    var2_2.autofill((SparseArray<AutofillValue>)var3_7);
                    var1_1 += var3_7.size();
                    ++var16_20;
                } while (true);
            }
            catch (Throwable var2_6) {
                throw var2_5;
            }
        }
        this.mMetricsLogger.write(this.newLog(913).addTaggedData(914, var6_10).addTaggedData(915, var10_14));
        // MONITOREXIT : var4_8
    }

    @GuardedBy(value={"mLock"})
    private void cancelLocked() {
        if (!this.mEnabled && !this.isActiveLocked()) {
            return;
        }
        this.cancelSessionLocked();
    }

    @GuardedBy(value={"mLock"})
    private void cancelSessionLocked() {
        if (Helper.sVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("cancelSessionLocked(): ");
            stringBuilder.append(this.getStateAsStringLocked());
            Log.v(TAG, stringBuilder.toString());
        }
        if (!this.isActiveLocked()) {
            return;
        }
        try {
            this.mService.cancelSession(this.mSessionId, this.mContext.getUserId());
            this.resetSessionLocked(true);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @GuardedBy(value={"mLock"})
    private void commitLocked() {
        if (!this.mEnabled && !this.isActiveLocked()) {
            return;
        }
        this.finishSessionLocked();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dispatchUnhandledKey(int n, AutofillId object, KeyEvent keyEvent) {
        View view = this.findView((AutofillId)object);
        if (view == null) {
            return;
        }
        object = this.mLock;
        synchronized (object) {
            AutofillClient autofillClient;
            if (this.mSessionId == n && (autofillClient = this.getClient()) != null) {
                autofillClient.autofillClientDispatchUnhandledKey(view, keyEvent);
            }
            return;
        }
    }

    @GuardedBy(value={"mLock"})
    private void ensureServiceClientAddedIfNeededLocked() {
        Object object = this.getClient();
        if (object == null) {
            return;
        }
        if (this.mServiceClient == null) {
            int n;
            int n2;
            boolean bl;
            Object object2;
            block9 : {
                boolean bl2;
                this.mServiceClient = new AutofillManagerClient(this);
                try {
                    n = this.mContext.getUserId();
                    object2 = new SyncResultReceiver(5000);
                    this.mService.addClient(this.mServiceClient, object.autofillClientGetComponentName(), n, (IResultReceiver)object2);
                    n2 = ((SyncResultReceiver)object2).getIntResult();
                    bl2 = false;
                    bl = (n2 & 1) != 0;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                this.mEnabled = bl;
                bl = (n2 & 2) != 0;
                Helper.sDebug = bl;
                bl = (n2 & 4) != 0;
                Helper.sVerbose = bl;
                bl = bl2;
                if ((n2 & 8) == 0) break block9;
                bl = true;
            }
            this.mEnabledForAugmentedAutofillOnly = bl;
            if (Helper.sVerbose) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("receiver results: flags=");
                ((StringBuilder)object2).append(n2);
                ((StringBuilder)object2).append(" enabled=");
                ((StringBuilder)object2).append(this.mEnabled);
                ((StringBuilder)object2).append(", enabledForAugmentedOnly: ");
                ((StringBuilder)object2).append(this.mEnabledForAugmentedAutofillOnly);
                Log.v(TAG, ((StringBuilder)object2).toString());
            }
            IAutoFillManager iAutoFillManager = this.mService;
            object = this.mServiceClient;
            object2 = new _$$Lambda$AutofillManager$V76JiQu509LCUz3_ckpb_nB3JhA(iAutoFillManager, (IAutoFillManagerClient)object, n);
            this.mServiceClientCleaner = Cleaner.create(this, (Runnable)object2);
        }
    }

    private View findView(AutofillId autofillId) {
        AutofillClient autofillClient = this.getClient();
        if (autofillClient != null) {
            return autofillClient.autofillClientFindViewByAutofillIdTraversal(autofillId);
        }
        return null;
    }

    @GuardedBy(value={"mLock"})
    private void finishSessionLocked() {
        if (Helper.sVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("finishSessionLocked(): ");
            stringBuilder.append(this.getStateAsStringLocked());
            Log.v(TAG, stringBuilder.toString());
        }
        if (!this.isActiveLocked()) {
            return;
        }
        try {
            this.mService.finishSession(this.mSessionId, this.mContext.getUserId());
            this.resetSessionLocked(true);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void getAugmentedAutofillClient(IResultReceiver object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Object object3;
            if (this.mAugmentedAutofillServiceClient == null) {
                this.mAugmentedAutofillServiceClient = object3 = new AugmentedAutofillManagerClient(this);
            }
            object3 = new Bundle();
            ((Bundle)object3).putBinder(EXTRA_AUGMENTED_AUTOFILL_CLIENT, this.mAugmentedAutofillServiceClient.asBinder());
            try {
                object.send(0, (Bundle)object3);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not send AugmentedAutofillClient back: ");
                ((StringBuilder)object).append(remoteException);
                Log.w(TAG, ((StringBuilder)object).toString());
            }
            return;
        }
    }

    private static AutofillId getAutofillId(View view, int n) {
        return new AutofillId(view.getAutofillViewId(), n);
    }

    private AutofillClient getClient() {
        AutofillClient autofillClient = this.mContext.getAutofillClient();
        if (autofillClient == null && Helper.sVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No AutofillClient for ");
            stringBuilder.append(this.mContext.getPackageName());
            stringBuilder.append(" on context ");
            stringBuilder.append(this.mContext);
            Log.v(TAG, stringBuilder.toString());
        }
        return autofillClient;
    }

    public static int getDatasetIdFromAuthenticationId(int n) {
        return 65535 & n;
    }

    public static int getRequestIdFromAuthenticationId(int n) {
        return n >> 16;
    }

    public static String getSmartSuggestionModeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INVALID:");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            return "SYSTEM";
        }
        return "OFF";
    }

    private static String getStateAsString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INVALID:");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            case 6: {
                return "UNKNOWN_FAILED";
            }
            case 5: {
                return "UNKNOWN_COMPAT_MODE";
            }
            case 4: {
                return "DISABLED_BY_SERVICE";
            }
            case 3: {
                return "SHOWING_SAVE_UI";
            }
            case 2: {
                return "FINISHED";
            }
            case 1: {
                return "ACTIVE";
            }
            case 0: 
        }
        return "UNKNOWN";
    }

    @GuardedBy(value={"mLock"})
    private String getStateAsStringLocked() {
        return AutofillManager.getStateAsString(this.mState);
    }

    @GuardedBy(value={"mLock"})
    private boolean isActiveLocked() {
        int n = this.mState;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    private boolean isClientDisablingEnterExitEvent() {
        AutofillClient autofillClient = this.getClient();
        boolean bl = autofillClient != null && autofillClient.isDisablingEnterExitEventForAutofill();
        return bl;
    }

    private boolean isClientVisibleForAutofillLocked() {
        AutofillClient autofillClient = this.getClient();
        boolean bl = autofillClient != null && autofillClient.autofillClientIsVisibleForAutofill();
        return bl;
    }

    @GuardedBy(value={"mLock"})
    private boolean isDisabledByServiceLocked() {
        boolean bl = this.mState == 4;
        return bl;
    }

    @GuardedBy(value={"mLock"})
    private boolean isFinishedLocked() {
        boolean bl = this.mState == 2;
        return bl;
    }

    static /* synthetic */ void lambda$ensureServiceClientAddedIfNeededLocked$1(IAutoFillManager iAutoFillManager, IAutoFillManagerClient iAutoFillManagerClient, int n) {
        try {
            iAutoFillManager.removeClient(iAutoFillManagerClient, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public static int makeAuthenticationId(int n, int n2) {
        return n << 16 | 65535 & n2;
    }

    private LogMaker newLog(int n) {
        AutofillClient autofillClient;
        LogMaker logMaker = new LogMaker(n).addTaggedData(1456, this.mSessionId);
        if (this.isCompatibilityModeEnabledLocked()) {
            logMaker.addTaggedData(1414, 1);
        }
        if ((autofillClient = this.getClient()) == null) {
            logMaker.setPackageName(this.mContext.getPackageName());
        } else {
            logMaker.setComponentName(autofillClient.autofillClientGetComponentName());
        }
        return logMaker;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void notifyNoFillUi(int n, AutofillId autofillId, int n2) {
        Object object;
        View view;
        if (Helper.sVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("notifyNoFillUi(): sessionId=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", autofillId=");
            ((StringBuilder)object).append(autofillId);
            ((StringBuilder)object).append(", sessionFinishedState=");
            ((StringBuilder)object).append(n2);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        if ((view = this.findView(autofillId)) == null) {
            return;
        }
        Object var6_6 = null;
        Object object2 = this.mLock;
        // MONITORENTER : object2
        object = var6_6;
        if (this.mSessionId == n) {
            object = var6_6;
            if (this.getClient() != null) {
                object = this.mCallback;
            }
        }
        // MONITOREXIT : object2
        if (object != null) {
            if (autofillId.isVirtualInt()) {
                ((AutofillCallback)object).onAutofillEvent(view, autofillId.getVirtualChildIntId(), 3);
            } else {
                ((AutofillCallback)object).onAutofillEvent(view, 3);
            }
        }
        if (n2 == 0) return;
        this.setSessionFinished(n2, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyViewClicked(AutofillId autofillId) {
        Object object;
        if (!this.hasAutofillFeature()) {
            return;
        }
        if (Helper.sVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("notifyViewClicked(): id=");
            ((StringBuilder)object).append(autofillId);
            ((StringBuilder)object).append(", trigger=");
            ((StringBuilder)object).append(this.mSaveTriggerId);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        object = this.mLock;
        synchronized (object) {
            if (this.mEnabled && this.isActiveLocked()) {
                if (this.mSaveTriggerId != null && this.mSaveTriggerId.equals(autofillId)) {
                    if (Helper.sDebug) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("triggering commit by click of ");
                        stringBuilder.append(autofillId);
                        Log.d(TAG, stringBuilder.toString());
                    }
                    this.commitLocked();
                    this.mMetricsLogger.write(this.newLog(1229));
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void notifyViewEntered(View view, int n) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object = this.mLock;
        // MONITORENTER : object
        AutofillCallback autofillCallback = this.notifyViewEnteredLocked(view, n);
        // MONITOREXIT : object
        if (autofillCallback == null) return;
        this.mCallback.onAutofillEvent(view, 3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void notifyViewEntered(View view, int n, Rect object, int n2) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object2 = this.mLock;
        // MONITORENTER : object2
        object = this.notifyViewEnteredLocked(view, n, (Rect)object, n2);
        // MONITOREXIT : object2
        if (object == null) return;
        ((AutofillCallback)object).onAutofillEvent(view, n, 3);
    }

    @GuardedBy(value={"mLock"})
    private AutofillCallback notifyViewEnteredLocked(View object, int n) {
        Object object2;
        AutofillId autofillId = ((View)object).getAutofillId();
        if (this.shouldIgnoreViewEnteredLocked(autofillId, n)) {
            return null;
        }
        AutofillValue autofillValue = null;
        this.ensureServiceClientAddedIfNeededLocked();
        if (!this.mEnabled && !this.mEnabledForAugmentedAutofillOnly) {
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ignoring notifyViewEntered(");
                ((StringBuilder)object).append(autofillId);
                ((StringBuilder)object).append("): disabled");
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            object2 = autofillValue;
            if (this.mCallback != null) {
                object2 = this.mCallback;
            }
        } else {
            object2 = autofillValue;
            if (!this.isClientDisablingEnterExitEvent()) {
                object2 = ((View)object).getAutofillValue();
                if (!this.isActiveLocked()) {
                    this.startSessionLocked(autofillId, null, (AutofillValue)object2, n);
                } else {
                    if (this.mForAugmentedAutofillOnly && (n & 1) != 0) {
                        if (Helper.sDebug) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("notifyViewEntered(");
                            ((StringBuilder)object).append(autofillId);
                            ((StringBuilder)object).append("): resetting mForAugmentedAutofillOnly on manual request");
                            Log.d(TAG, ((StringBuilder)object).toString());
                        }
                        this.mForAugmentedAutofillOnly = false;
                    }
                    this.updateSessionLocked(autofillId, null, (AutofillValue)object2, 2, n);
                }
                this.addEnteredIdLocked(autofillId);
                object2 = autofillValue;
            }
        }
        return object2;
    }

    @GuardedBy(value={"mLock"})
    private AutofillCallback notifyViewEnteredLocked(View object, int n, Rect rect, int n2) {
        AutofillId autofillId = AutofillManager.getAutofillId((View)object, n);
        Object var6_6 = null;
        if (this.shouldIgnoreViewEnteredLocked(autofillId, n2)) {
            return null;
        }
        this.ensureServiceClientAddedIfNeededLocked();
        if (!this.mEnabled && !this.mEnabledForAugmentedAutofillOnly) {
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ignoring notifyViewEntered(");
                ((StringBuilder)object).append(autofillId);
                ((StringBuilder)object).append("): disabled");
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            object = var6_6;
            if (this.mCallback != null) {
                object = this.mCallback;
            }
        } else {
            object = var6_6;
            if (!this.isClientDisablingEnterExitEvent()) {
                if (!this.isActiveLocked()) {
                    this.startSessionLocked(autofillId, rect, null, n2);
                } else {
                    if (this.mForAugmentedAutofillOnly && (n2 & 1) != 0) {
                        if (Helper.sDebug) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("notifyViewEntered(");
                            ((StringBuilder)object).append(autofillId);
                            ((StringBuilder)object).append("): resetting mForAugmentedAutofillOnly on manual request");
                            Log.d(TAG, ((StringBuilder)object).toString());
                        }
                        this.mForAugmentedAutofillOnly = false;
                    }
                    this.updateSessionLocked(autofillId, rect, null, 2, n2);
                }
                this.addEnteredIdLocked(autofillId);
                object = var6_6;
            }
        }
        return object;
    }

    @GuardedBy(value={"mLock"})
    private void notifyViewExitedLocked(View view, int n) {
        this.ensureServiceClientAddedIfNeededLocked();
        if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && this.isActiveLocked() && !this.isClientDisablingEnterExitEvent()) {
            this.updateSessionLocked(AutofillManager.getAutofillId(view, n), null, null, 3, 0);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyViewVisibilityChangedInternal(View object, int n, boolean bl, boolean bl2) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mForAugmentedAutofillOnly) {
                if (Helper.sVerbose) {
                    Log.v(TAG, "notifyViewVisibilityChanged(): ignoring on augmented only mode");
                }
                return;
            }
            if (this.mEnabled && this.isActiveLocked()) {
                StringBuilder stringBuilder;
                AutofillId autofillId = bl2 ? AutofillManager.getAutofillId((View)object, n) : ((View)object).getAutofillId();
                if (Helper.sVerbose) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("visibility changed for ");
                    stringBuilder.append(autofillId);
                    stringBuilder.append(": ");
                    stringBuilder.append(bl);
                    Log.v(TAG, stringBuilder.toString());
                }
                if (!bl && this.mFillableIds != null && this.mFillableIds.contains(autofillId)) {
                    if (Helper.sDebug) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Hidding UI when view ");
                        stringBuilder.append(autofillId);
                        stringBuilder.append(" became invisible");
                        Log.d(TAG, stringBuilder.toString());
                    }
                    this.requestHideFillUi(autofillId, (View)object);
                }
                if (this.mTrackedViews != null) {
                    this.mTrackedViews.notifyViewVisibilityChangedLocked(autofillId, bl);
                } else if (Helper.sVerbose) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Ignoring visibility change on ");
                    ((StringBuilder)object).append(autofillId);
                    ((StringBuilder)object).append(": no tracked views");
                    Log.v(TAG, ((StringBuilder)object).toString());
                }
            }
            return;
        }
    }

    private void post(Runnable runnable) {
        AutofillClient autofillClient = this.getClient();
        if (autofillClient == null) {
            if (Helper.sVerbose) {
                Log.v(TAG, "ignoring post() because client is null");
            }
            return;
        }
        autofillClient.autofillClientRunOnUiThread(runnable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void requestHideFillUi(AutofillId autofillId, View view) {
        AutofillCallback autofillCallback;
        AutofillCallback autofillCallback2 = null;
        Object object = this.mLock;
        synchronized (object) {
            AutofillClient autofillClient = this.getClient();
            autofillCallback = autofillCallback2;
            if (autofillClient != null) {
                autofillCallback = autofillCallback2;
                if (autofillClient.autofillClientRequestHideFillUi()) {
                    this.mIdShownFillUi = null;
                    autofillCallback = this.mCallback;
                }
            }
        }
        if (autofillCallback == null) return;
        if (autofillId.isVirtualInt()) {
            autofillCallback.onAutofillEvent(view, autofillId.getVirtualChildIntId(), 2);
            return;
        }
        autofillCallback.onAutofillEvent(view, 2);
    }

    private void requestHideFillUi(AutofillId object, boolean bl) {
        View view = object == null ? null : this.findView((AutofillId)object);
        if (Helper.sVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("requestHideFillUi(");
            stringBuilder.append(object);
            stringBuilder.append("): anchor = ");
            stringBuilder.append(view);
            Log.v(TAG, stringBuilder.toString());
        }
        if (view == null) {
            if (bl && (object = this.getClient()) != null) {
                object.autofillClientRequestHideFillUi();
            }
            return;
        }
        this.requestHideFillUi((AutofillId)object, view);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void requestShowFillUi(int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
        View view = this.findView(autofillId);
        if (view == null) {
            return;
        }
        AutofillCallback autofillCallback = null;
        Object object = this.mLock;
        // MONITORENTER : object
        int n4 = this.mSessionId;
        AutofillCallback autofillCallback2 = autofillCallback;
        if (n4 == n) {
            AutofillClient autofillClient = this.getClient();
            autofillCallback2 = autofillCallback;
            if (autofillClient != null) {
                void var6_7;
                void var4_5;
                void var3_4;
                void var5_6;
                autofillCallback2 = autofillCallback;
                if (autofillClient.autofillClientRequestShowFillUi(view, (int)var3_4, (int)var4_5, (Rect)var5_6, (IAutofillWindowPresenter)var6_7)) {
                    autofillCallback2 = this.mCallback;
                    this.mIdShownFillUi = autofillId;
                }
            }
            // MONITOREXIT : object
        }
        if (autofillCallback2 == null) return;
        if (autofillId.isVirtualInt()) {
            autofillCallback2.onAutofillEvent(view, autofillId.getVirtualChildIntId(), 1);
            return;
        }
        autofillCallback2.onAutofillEvent(view, 1);
        return;
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    @GuardedBy(value={"mLock"})
    private void resetSessionLocked(boolean bl) {
        this.mSessionId = Integer.MAX_VALUE;
        this.mState = 0;
        this.mTrackedViews = null;
        this.mFillableIds = null;
        this.mSaveTriggerId = null;
        this.mIdShownFillUi = null;
        if (bl) {
            this.mEnteredIds = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setAutofilledIfValuesIs(View view, AutofillValue autofillValue) {
        if (!Objects.equals(view.getAutofillValue(), autofillValue)) return;
        Object object = this.mLock;
        synchronized (object) {
            if (this.mLastAutofilledData == null) {
                ParcelableMap parcelableMap;
                this.mLastAutofilledData = parcelableMap = new ParcelableMap(1);
            }
            this.mLastAutofilledData.put(view.getAutofillId(), autofillValue);
        }
        view.setAutofilled(true);
    }

    private void setNotifyOnClickLocked(AutofillId autofillId, boolean bl) {
        Object object = this.findView(autofillId);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("setNotifyOnClick(): invalid id: ");
            ((StringBuilder)object).append(autofillId);
            Log.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        ((View)object).setNotifyAutofillManagerOnClick(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setSaveUiState(int n, boolean bl) {
        Object object;
        if (Helper.sDebug) {
            object = new StringBuilder();
            ((StringBuilder)object).append("setSaveUiState(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("): ");
            ((StringBuilder)object).append(bl);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        object = this.mLock;
        synchronized (object) {
            if (this.mSessionId != Integer.MAX_VALUE) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setSaveUiState(");
                stringBuilder.append(n);
                stringBuilder.append(", ");
                stringBuilder.append(bl);
                stringBuilder.append(") called on existing session ");
                stringBuilder.append(this.mSessionId);
                stringBuilder.append("; cancelling it");
                Log.w(TAG, stringBuilder.toString());
                this.cancelSessionLocked();
            }
            if (bl) {
                this.mSessionId = n;
                this.mState = 3;
            } else {
                this.mSessionId = Integer.MAX_VALUE;
                this.mState = 0;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setSessionFinished(int n, List<AutofillId> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).resetSessionId();
            }
        }
        Object object = this.mLock;
        synchronized (object) {
            ArraySet<AutofillId> arraySet;
            if (Helper.sVerbose) {
                arraySet = new ArraySet<AutofillId>();
                ((StringBuilder)((Object)arraySet)).append("setSessionFinished(): from ");
                ((StringBuilder)((Object)arraySet)).append(this.getStateAsStringLocked());
                ((StringBuilder)((Object)arraySet)).append(" to ");
                ((StringBuilder)((Object)arraySet)).append(AutofillManager.getStateAsString(n));
                ((StringBuilder)((Object)arraySet)).append("; autofillableIds=");
                ((StringBuilder)((Object)arraySet)).append(list);
                Log.v(TAG, ((StringBuilder)((Object)arraySet)).toString());
            }
            if (list != null) {
                arraySet = new ArraySet<AutofillId>(list);
                this.mEnteredIds = arraySet;
            }
            if (n != 5 && n != 6) {
                this.resetSessionLocked(false);
                this.mState = n;
            } else {
                this.resetSessionLocked(true);
                this.mState = 0;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void setState(int n) {
        if (Helper.sVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setState(");
            stringBuilder.append(n);
            stringBuilder.append(": ");
            stringBuilder.append(DebugUtils.flagsToString(AutofillManager.class, "SET_STATE_FLAG_", n));
            stringBuilder.append(")");
            Log.v(TAG, stringBuilder.toString());
        }
        Object object = this.mLock;
        // MONITORENTER : object
        boolean bl = true;
        if ((n & 32) != 0) {
            this.mForAugmentedAutofillOnly = true;
            // MONITOREXIT : object
            return;
        }
        boolean bl2 = (n & 1) != 0;
        this.mEnabled = bl2;
        if (!this.mEnabled || (n & 2) != 0) {
            this.resetSessionLocked(true);
        }
        if ((n & 4) != 0) {
            this.mServiceClient = null;
            this.mAugmentedAutofillServiceClient = null;
            if (this.mServiceClientCleaner != null) {
                this.mServiceClientCleaner.clean();
                this.mServiceClientCleaner = null;
            }
        }
        // MONITOREXIT : object
        bl2 = (n & 8) != 0;
        Helper.sDebug = bl2;
        bl2 = (n & 16) != 0 ? bl : false;
        Helper.sVerbose = bl2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setTrackedViews(int n, AutofillId[] object4, boolean bl, boolean bl2, AutofillId[] arrautofillId, AutofillId autofillId) {
        if (autofillId != null) {
            autofillId.resetSessionId();
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            Object object3;
            if (Helper.sVerbose) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("setTrackedViews(): sessionId=");
                ((StringBuilder)object3).append(n);
                ((StringBuilder)object3).append(", trackedIds=");
                ((StringBuilder)object3).append(Arrays.toString((Object[])object4));
                ((StringBuilder)object3).append(", saveOnAllViewsInvisible=");
                ((StringBuilder)object3).append(bl);
                ((StringBuilder)object3).append(", saveOnFinish=");
                ((StringBuilder)object3).append(bl2);
                ((StringBuilder)object3).append(", fillableIds=");
                ((StringBuilder)object3).append(Arrays.toString(arrautofillId));
                ((StringBuilder)object3).append(", saveTrigerId=");
                ((StringBuilder)object3).append(autofillId);
                ((StringBuilder)object3).append(", mFillableIds=");
                ((StringBuilder)object3).append(this.mFillableIds);
                ((StringBuilder)object3).append(", mEnabled=");
                ((StringBuilder)object3).append(this.mEnabled);
                ((StringBuilder)object3).append(", mSessionId=");
                ((StringBuilder)object3).append(this.mSessionId);
                Log.v(TAG, ((StringBuilder)object3).toString());
            }
            if (this.mEnabled && this.mSessionId == n) {
                this.mTrackedViews = bl ? (object3 = new TrackedViews((AutofillId[])object4)) : null;
                this.mSaveOnFinish = bl2;
                if (arrautofillId != null) {
                    if (this.mFillableIds == null) {
                        object4 = new ArraySet(arrautofillId.length);
                        this.mFillableIds = object4;
                    }
                    for (Object object4 : arrautofillId) {
                        ((AutofillId)object4).resetSessionId();
                        this.mFillableIds.add((AutofillId)object4);
                    }
                }
                if (this.mSaveTriggerId != null && !this.mSaveTriggerId.equals(autofillId)) {
                    this.setNotifyOnClickLocked(this.mSaveTriggerId, false);
                }
                if (autofillId != null && !autofillId.equals(this.mSaveTriggerId)) {
                    this.mSaveTriggerId = autofillId;
                    this.setNotifyOnClickLocked(this.mSaveTriggerId, true);
                }
            }
            return;
        }
    }

    @GuardedBy(value={"mLock"})
    private boolean shouldIgnoreViewEnteredLocked(AutofillId autofillId, int n) {
        Object object;
        if (this.isDisabledByServiceLocked()) {
            if (Helper.sVerbose) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ignoring notifyViewEntered(flags=");
                stringBuilder.append(n);
                stringBuilder.append(", view=");
                stringBuilder.append(autofillId);
                stringBuilder.append(") on state ");
                stringBuilder.append(this.getStateAsStringLocked());
                stringBuilder.append(" because disabled by svc");
                Log.v(TAG, stringBuilder.toString());
            }
            return true;
        }
        if (this.isFinishedLocked() && (n & 1) == 0 && (object = this.mEnteredIds) != null && ((ArraySet)object).contains(autofillId)) {
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ignoring notifyViewEntered(flags=");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(", view=");
                ((StringBuilder)object).append(autofillId);
                ((StringBuilder)object).append(") on state ");
                ((StringBuilder)object).append(this.getStateAsStringLocked());
                ((StringBuilder)object).append(" because view was already entered: ");
                ((StringBuilder)object).append(this.mEnteredIds);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return true;
        }
        return false;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @GuardedBy(value={"mLock"})
    private void startSessionLocked(AutofillId object, Rect object2, AutofillValue autofillValue, int n) {
        void var1_4;
        Set<AutofillId> set = this.mEnteredForAugmentedAutofillIds;
        if (set != null && set.contains(object) || this.mEnabledForAugmentedAutofillOnly) {
            if (Helper.sVerbose) {
                set = new StringBuilder();
                ((StringBuilder)((Object)set)).append("Starting session for augmented autofill on ");
                ((StringBuilder)((Object)set)).append(object);
                Log.v(TAG, ((StringBuilder)((Object)set)).toString());
            }
            n |= 8;
        }
        if (Helper.sVerbose) {
            set = new StringBuilder();
            ((StringBuilder)((Object)set)).append("startSessionLocked(): id=");
            ((StringBuilder)((Object)set)).append(object);
            ((StringBuilder)((Object)set)).append(", bounds=");
            ((StringBuilder)((Object)set)).append(object2);
            ((StringBuilder)((Object)set)).append(", value=");
            ((StringBuilder)((Object)set)).append(autofillValue);
            ((StringBuilder)((Object)set)).append(", flags=");
            ((StringBuilder)((Object)set)).append(n);
            ((StringBuilder)((Object)set)).append(", state=");
            ((StringBuilder)((Object)set)).append(this.getStateAsStringLocked());
            ((StringBuilder)((Object)set)).append(", compatMode=");
            ((StringBuilder)((Object)set)).append(this.isCompatibilityModeEnabledLocked());
            ((StringBuilder)((Object)set)).append(", augmentedOnly=");
            ((StringBuilder)((Object)set)).append(this.mForAugmentedAutofillOnly);
            ((StringBuilder)((Object)set)).append(", enabledAugmentedOnly=");
            ((StringBuilder)((Object)set)).append(this.mEnabledForAugmentedAutofillOnly);
            ((StringBuilder)((Object)set)).append(", enteredIds=");
            ((StringBuilder)((Object)set)).append(this.mEnteredIds);
            Log.v(TAG, ((StringBuilder)((Object)set)).toString());
        }
        if (this.mForAugmentedAutofillOnly && !this.mEnabledForAugmentedAutofillOnly && (n & 1) != 0) {
            if (Helper.sVerbose) {
                Log.v(TAG, "resetting mForAugmentedAutofillOnly on manual autofill request");
            }
            this.mForAugmentedAutofillOnly = false;
        }
        if (this.mState != 0 && !this.isFinishedLocked() && (n & 1) == 0) {
            if (!Helper.sVerbose) return;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("not automatically starting session for ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" on state ");
            ((StringBuilder)object2).append(this.getStateAsStringLocked());
            ((StringBuilder)object2).append(" and flags ");
            ((StringBuilder)object2).append(n);
            Log.v(TAG, ((StringBuilder)object2).toString());
            return;
        }
        AutofillClient autofillClient = this.getClient();
        if (autofillClient == null) {
            return;
        }
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        set = autofillClient.autofillClientGetComponentName();
        IAutoFillManager iAutoFillManager = this.mService;
        IBinder iBinder = autofillClient.autofillClientGetActivityToken();
        IBinder iBinder2 = this.mServiceClient.asBinder();
        int n2 = this.mContext.getUserId();
        boolean bl = this.mCallback != null;
        boolean bl2 = this.isCompatibilityModeEnabledLocked();
        try {
            iAutoFillManager.startSession(iBinder, iBinder2, (AutofillId)object, (Rect)object2, autofillValue, n2, bl, n, (ComponentName)((Object)set), bl2, syncResultReceiver);
            this.mSessionId = syncResultReceiver.getIntResult();
            if (this.mSessionId != Integer.MAX_VALUE) {
                this.mState = 1;
            }
            if ((syncResultReceiver.getOptionalExtraIntResult(0) & 1) != 0) {
                if (Helper.sDebug) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("startSession(");
                    ((StringBuilder)object).append(set);
                    ((StringBuilder)object).append("): for augmented only");
                    Log.d(TAG, ((StringBuilder)object).toString());
                }
                this.mForAugmentedAutofillOnly = true;
            }
            autofillClient.autofillClientResetableStateAvailable();
            return;
        }
        catch (RemoteException remoteException) {
            throw var1_4.rethrowFromSystemServer();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var1_4.rethrowFromSystemServer();
    }

    @GuardedBy(value={"mLock"})
    private void updateSessionLocked(AutofillId autofillId, Rect rect, AutofillValue autofillValue, int n, int n2) {
        if (Helper.sVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateSessionLocked(): id=");
            stringBuilder.append(autofillId);
            stringBuilder.append(", bounds=");
            stringBuilder.append(rect);
            stringBuilder.append(", value=");
            stringBuilder.append(autofillValue);
            stringBuilder.append(", action=");
            stringBuilder.append(n);
            stringBuilder.append(", flags=");
            stringBuilder.append(n2);
            Log.v(TAG, stringBuilder.toString());
        }
        try {
            this.mService.updateSession(this.mSessionId, autofillId, rect, autofillValue, n, n2, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        if (Helper.sVerbose) {
            Log.v(TAG, "cancel() called by app");
        }
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            this.cancelLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void commit() {
        if (!this.hasAutofillFeature()) {
            return;
        }
        if (Helper.sVerbose) {
            Log.v(TAG, "commit() called by app");
        }
        Object object = this.mLock;
        synchronized (object) {
            this.commitLocked();
            return;
        }
    }

    public void disableAutofillServices() {
        if (!this.hasAutofillFeature()) {
            return;
        }
        try {
            this.mService.disableOwnedAutofillServices(this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disableOwnedAutofillServices() {
        this.disableAutofillServices();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(String string2, PrintWriter printWriter) {
        printWriter.print(string2);
        printWriter.println("AutofillManager:");
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("  ");
        string2 = ((StringBuilder)object).toString();
        printWriter.print(string2);
        printWriter.print("sessionId: ");
        printWriter.println(this.mSessionId);
        printWriter.print(string2);
        printWriter.print("state: ");
        printWriter.println(this.getStateAsStringLocked());
        printWriter.print(string2);
        printWriter.print("context: ");
        printWriter.println(this.mContext);
        object = this.getClient();
        if (object != null) {
            printWriter.print(string2);
            printWriter.print("client: ");
            printWriter.print(object);
            printWriter.print(" (");
            printWriter.print(object.autofillClientGetActivityToken());
            printWriter.println(')');
        }
        printWriter.print(string2);
        printWriter.print("enabled: ");
        printWriter.println(this.mEnabled);
        printWriter.print(string2);
        printWriter.print("enabledAugmentedOnly: ");
        printWriter.println(this.mForAugmentedAutofillOnly);
        printWriter.print(string2);
        printWriter.print("hasService: ");
        object = this.mService;
        boolean bl = true;
        boolean bl2 = object != null;
        printWriter.println(bl2);
        printWriter.print(string2);
        printWriter.print("hasCallback: ");
        bl2 = this.mCallback != null ? bl : false;
        printWriter.println(bl2);
        printWriter.print(string2);
        printWriter.print("onInvisibleCalled ");
        printWriter.println(this.mOnInvisibleCalled);
        printWriter.print(string2);
        printWriter.print("last autofilled data: ");
        printWriter.println(this.mLastAutofilledData);
        printWriter.print(string2);
        printWriter.print("id of last fill UI shown: ");
        printWriter.println(this.mIdShownFillUi);
        printWriter.print(string2);
        printWriter.print("tracked views: ");
        if (this.mTrackedViews == null) {
            printWriter.println("null");
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  ");
            object = ((StringBuilder)object).toString();
            printWriter.println();
            printWriter.print((String)object);
            printWriter.print("visible:");
            printWriter.println(this.mTrackedViews.mVisibleTrackedIds);
            printWriter.print((String)object);
            printWriter.print("invisible:");
            printWriter.println(this.mTrackedViews.mInvisibleTrackedIds);
        }
        printWriter.print(string2);
        printWriter.print("fillable ids: ");
        printWriter.println(this.mFillableIds);
        printWriter.print(string2);
        printWriter.print("entered ids: ");
        printWriter.println(this.mEnteredIds);
        if (this.mEnteredForAugmentedAutofillIds != null) {
            printWriter.print(string2);
            printWriter.print("entered ids for augmented autofill: ");
            printWriter.println(this.mEnteredForAugmentedAutofillIds);
        }
        if (this.mForAugmentedAutofillOnly) {
            printWriter.print(string2);
            printWriter.println("For Augmented Autofill Only");
        }
        printWriter.print(string2);
        printWriter.print("save trigger id: ");
        printWriter.println(this.mSaveTriggerId);
        printWriter.print(string2);
        printWriter.print("save on finish(): ");
        printWriter.println(this.mSaveOnFinish);
        if (this.mOptions != null) {
            printWriter.print(string2);
            printWriter.print("options: ");
            this.mOptions.dumpShort(printWriter);
            printWriter.println();
        }
        printWriter.print(string2);
        printWriter.print("compat mode enabled: ");
        object = this.mLock;
        synchronized (object) {
            if (this.mCompatibilityBridge != null) {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(string2);
                charSequence.append("  ");
                charSequence = charSequence.toString();
                printWriter.println("true");
                printWriter.print((String)charSequence);
                printWriter.print("windowId: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedWindowId);
                printWriter.print((String)charSequence);
                printWriter.print("nodeId: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedNodeId);
                printWriter.print((String)charSequence);
                printWriter.print("virtualId: ");
                printWriter.println(AccessibilityNodeInfo.getVirtualDescendantId(this.mCompatibilityBridge.mFocusedNodeId));
                printWriter.print((String)charSequence);
                printWriter.print("focusedBounds: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedBounds);
            } else {
                printWriter.println("false");
            }
        }
        printWriter.print(string2);
        printWriter.print("debug: ");
        printWriter.print(Helper.sDebug);
        printWriter.print(" verbose: ");
        printWriter.println(Helper.sVerbose);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enableCompatibilityMode() {
        Object object = this.mLock;
        synchronized (object) {
            Object object2;
            if (Helper.sDebug) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("creating CompatibilityBridge for ");
                ((StringBuilder)object2).append(this.mContext);
                Slog.d(TAG, ((StringBuilder)object2).toString());
            }
            this.mCompatibilityBridge = object2 = new CompatibilityBridge();
            return;
        }
    }

    public ComponentName getAutofillServiceComponentName() {
        if (this.mService == null) {
            return null;
        }
        Object object = new SyncResultReceiver(5000);
        try {
            this.mService.getAutofillServiceComponentName((IResultReceiver)object);
            object = (ComponentName)((SyncResultReceiver)object).getParcelableResult();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<String> getAvailableFieldClassificationAlgorithms() {
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            void var1_5;
            this.mService.getAvailableFieldClassificationAlgorithms(syncResultReceiver);
            String[] arrstring = syncResultReceiver.getStringArrayResult();
            if (arrstring != null) {
                List<String> list = Arrays.asList(arrstring);
                return var1_5;
            }
            List list = Collections.emptyList();
            return var1_5;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public String getDefaultFieldClassificationAlgorithm() {
        Object object = new SyncResultReceiver(5000);
        try {
            this.mService.getDefaultFieldClassificationAlgorithm((IResultReceiver)object);
            object = ((SyncResultReceiver)object).getStringResult();
            return object;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public FillEventHistory getFillEventHistory() {
        try {
            Object object = new SyncResultReceiver(5000);
            this.mService.getFillEventHistory((IResultReceiver)object);
            object = (FillEventHistory)((SyncResultReceiver)object).getParcelableResult();
            return object;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public AutofillId getNextAutofillId() {
        AutofillClient autofillClient = this.getClient();
        if (autofillClient == null) {
            return null;
        }
        AutofillId autofillId = autofillClient.autofillClientGetNextAutofillId();
        if (autofillId == null && Helper.sDebug) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getNextAutofillId(): client ");
            stringBuilder.append(autofillClient);
            stringBuilder.append(" returned null");
            Log.d(TAG, stringBuilder.toString());
        }
        return autofillId;
    }

    public UserData getUserData() {
        try {
            Object object = new SyncResultReceiver(5000);
            this.mService.getUserData((IResultReceiver)object);
            object = (UserData)((SyncResultReceiver)object).getParcelableResult();
            return object;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public String getUserDataId() {
        try {
            Object object = new SyncResultReceiver(5000);
            this.mService.getUserDataId((IResultReceiver)object);
            object = ((SyncResultReceiver)object).getStringResult();
            return object;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public boolean hasAutofillFeature() {
        boolean bl = this.mService != null;
        return bl;
    }

    public boolean hasEnabledAutofillServices() {
        IInterface iInterface = this.mService;
        boolean bl = false;
        if (iInterface == null) {
            return false;
        }
        iInterface = new SyncResultReceiver(5000);
        try {
            this.mService.isServiceEnabled(this.mContext.getUserId(), this.mContext.getPackageName(), (IResultReceiver)iInterface);
            int n = ((SyncResultReceiver)iInterface).getIntResult();
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAutofillSupported() {
        IInterface iInterface = this.mService;
        boolean bl = false;
        if (iInterface == null) {
            return false;
        }
        iInterface = new SyncResultReceiver(5000);
        try {
            this.mService.isServiceSupported(this.mContext.getUserId(), (IResultReceiver)iInterface);
            int n = ((SyncResultReceiver)iInterface).getIntResult();
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAutofillUiShowing() {
        AutofillClient autofillClient = this.mContext.getAutofillClient();
        boolean bl = autofillClient != null && autofillClient.autofillClientIsFillUiShowing();
        return bl;
    }

    @GuardedBy(value={"mLock"})
    public boolean isCompatibilityModeEnabledLocked() {
        boolean bl = this.mCompatibilityBridge != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isEnabled() {
        if (!this.hasAutofillFeature()) {
            return false;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.isDisabledByServiceLocked()) {
                return false;
            }
            this.ensureServiceClientAddedIfNeededLocked();
            return this.mEnabled;
        }
    }

    public boolean isFieldClassificationEnabled() {
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        boolean bl = false;
        try {
            this.mService.isFieldClassificationEnabled(syncResultReceiver);
            int n = syncResultReceiver.getIntResult();
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$onVisibleForAutofill$0$AutofillManager() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mEnabled && this.isActiveLocked() && this.mTrackedViews != null) {
                this.mTrackedViews.onVisibleForAutofillChangedLocked();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyValueChanged(View view) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        AutofillId autofillId = null;
        boolean bl = false;
        Object object = null;
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mLastAutofilledData == null) {
                view.setAutofilled(false);
            } else {
                autofillId = view.getAutofillId();
                if (this.mLastAutofilledData.containsKey(autofillId)) {
                    object = view.getAutofillValue();
                    bl = true;
                    if (Objects.equals(this.mLastAutofilledData.get(autofillId), object)) {
                        view.setAutofilled(true);
                    } else {
                        view.setAutofilled(false);
                        this.mLastAutofilledData.remove(autofillId);
                    }
                } else {
                    view.setAutofilled(false);
                }
            }
            if (this.mForAugmentedAutofillOnly) {
                if (Helper.sVerbose) {
                    Log.v(TAG, "notifyValueChanged(): not notifying system server on augmented-only mode");
                }
                return;
            }
            if (this.mEnabled && this.isActiveLocked()) {
                AutofillId autofillId2 = autofillId;
                if (autofillId == null) {
                    autofillId2 = view.getAutofillId();
                }
                if (!bl) {
                    object = view.getAutofillValue();
                }
                this.updateSessionLocked(autofillId2, null, (AutofillValue)object, 4, 0);
                return;
            }
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("notifyValueChanged(");
                ((StringBuilder)object).append(view.getAutofillId());
                ((StringBuilder)object).append("): ignoring on state ");
                ((StringBuilder)object).append(this.getStateAsStringLocked());
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyValueChanged(View view, int n, AutofillValue object) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mForAugmentedAutofillOnly) {
                if (Helper.sVerbose) {
                    Log.v(TAG, "notifyValueChanged(): ignoring on augmented only mode");
                }
                return;
            }
            if (this.mEnabled && this.isActiveLocked()) {
                this.updateSessionLocked(AutofillManager.getAutofillId(view, n), null, (AutofillValue)object, 4, 0);
                return;
            }
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("notifyValueChanged(");
                ((StringBuilder)object).append(view.getAutofillId());
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("): ignoring on state ");
                ((StringBuilder)object).append(this.getStateAsStringLocked());
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return;
        }
    }

    public void notifyViewClicked(View view) {
        this.notifyViewClicked(view.getAutofillId());
    }

    public void notifyViewClicked(View view, int n) {
        this.notifyViewClicked(AutofillManager.getAutofillId(view, n));
    }

    public void notifyViewEntered(View view) {
        this.notifyViewEntered(view, 0);
    }

    public void notifyViewEntered(View view, int n, Rect rect) {
        this.notifyViewEntered(view, n, rect, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyViewEnteredForAugmentedAutofill(View object) {
        AutofillId autofillId = ((View)object).getAutofillId();
        object = this.mLock;
        synchronized (object) {
            if (this.mEnteredForAugmentedAutofillIds == null) {
                ArraySet<AutofillId> arraySet = new ArraySet<AutofillId>(1);
                this.mEnteredForAugmentedAutofillIds = arraySet;
            }
            this.mEnteredForAugmentedAutofillIds.add(autofillId);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyViewExited(View view) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            this.notifyViewExitedLocked(view);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyViewExited(View view, int n) {
        Object object;
        if (Helper.sVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("notifyViewExited(");
            ((StringBuilder)object).append(view.getAutofillId());
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        if (!this.hasAutofillFeature()) {
            return;
        }
        object = this.mLock;
        synchronized (object) {
            this.notifyViewExitedLocked(view, n);
            return;
        }
    }

    @GuardedBy(value={"mLock"})
    void notifyViewExitedLocked(View view) {
        this.ensureServiceClientAddedIfNeededLocked();
        if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && this.isActiveLocked() && !this.isClientDisablingEnterExitEvent()) {
            this.updateSessionLocked(view.getAutofillId(), null, null, 3, 0);
        }
    }

    public void notifyViewVisibilityChanged(View view, int n, boolean bl) {
        this.notifyViewVisibilityChangedInternal(view, n, bl, true);
    }

    public void notifyViewVisibilityChanged(View view, boolean bl) {
        this.notifyViewVisibilityChangedInternal(view, 0, bl, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onActivityFinishing() {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mSaveOnFinish) {
                if (Helper.sDebug) {
                    Log.d(TAG, "onActivityFinishing(): calling commitLocked()");
                }
                this.commitLocked();
            } else {
                if (Helper.sDebug) {
                    Log.d(TAG, "onActivityFinishing(): calling cancelLocked()");
                }
                this.cancelLocked();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onAuthenticationResult(int n, Intent parcelable, View object) {
        Object object2;
        if (!this.hasAutofillFeature()) {
            return;
        }
        if (Helper.sDebug) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("onAuthenticationResult(): id= ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(", data=");
            ((StringBuilder)object2).append(parcelable);
            Log.d(TAG, ((StringBuilder)object2).toString());
        }
        object2 = this.mLock;
        synchronized (object2) {
            if (!this.isActiveLocked()) {
                return;
            }
            if (!this.mOnInvisibleCalled && object != null && ((View)object).canNotifyAutofillEnterExitEvent()) {
                this.notifyViewExitedLocked((View)object);
                this.notifyViewEnteredLocked((View)object, 0);
            }
            if (parcelable == null) {
                return;
            }
            Object t = parcelable.getParcelableExtra(EXTRA_AUTHENTICATION_RESULT);
            object = new Bundle();
            ((Bundle)object).putParcelable(EXTRA_AUTHENTICATION_RESULT, (Parcelable)t);
            parcelable = parcelable.getBundleExtra(EXTRA_CLIENT_STATE);
            if (parcelable != null) {
                ((Bundle)object).putBundle(EXTRA_CLIENT_STATE, (Bundle)parcelable);
            }
            try {
                this.mService.setAuthenticationResult((Bundle)object, this.mSessionId, n, this.mContext.getUserId());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error delivering authentication result", remoteException);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate(Bundle object) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            this.mLastAutofilledData = (ParcelableMap)((Bundle)object).getParcelable(LAST_AUTOFILLED_DATA_TAG);
            if (this.isActiveLocked()) {
                Log.w(TAG, "New session was started before onCreate()");
                return;
            }
            this.mSessionId = ((BaseBundle)object).getInt(SESSION_ID_TAG, Integer.MAX_VALUE);
            this.mState = ((BaseBundle)object).getInt(STATE_TAG, 0);
            if (this.mSessionId != Integer.MAX_VALUE) {
                this.ensureServiceClientAddedIfNeededLocked();
                object = this.getClient();
                if (object != null) {
                    Object object3 = new SyncResultReceiver(5000);
                    try {
                        this.mService.restoreSession(this.mSessionId, object.autofillClientGetActivityToken(), this.mServiceClient.asBinder(), (IResultReceiver)object3);
                        int n = ((SyncResultReceiver)object3).getIntResult();
                        boolean bl = true;
                        if (n != 1) {
                            bl = false;
                        }
                        if (!bl) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Session ");
                            ((StringBuilder)object).append(this.mSessionId);
                            ((StringBuilder)object).append(" could not be restored");
                            Log.w(TAG, ((StringBuilder)object).toString());
                            this.mSessionId = Integer.MAX_VALUE;
                            this.mState = 0;
                        } else {
                            if (Helper.sDebug) {
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append("session ");
                                ((StringBuilder)object3).append(this.mSessionId);
                                ((StringBuilder)object3).append(" was restored");
                                Log.d(TAG, ((StringBuilder)object3).toString());
                            }
                            object.autofillClientResetableStateAvailable();
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "Could not figure out if there was an autofill session", remoteException);
                    }
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onInvisibleForAutofill() {
        Object object = this.mLock;
        synchronized (object) {
            this.mOnInvisibleCalled = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onPendingSaveUi(int n, IBinder iBinder) {
        Object object;
        if (Helper.sVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onPendingSaveUi(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("): ");
            ((StringBuilder)object).append(iBinder);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        object = this.mLock;
        synchronized (object) {
            try {
                try {
                    this.mService.onPendingSaveUi(n, iBinder);
                }
                catch (RemoteException remoteException) {
                    remoteException.rethrowFromSystemServer();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onSaveInstanceState(Bundle bundle) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mSessionId != Integer.MAX_VALUE) {
                bundle.putInt(SESSION_ID_TAG, this.mSessionId);
            }
            if (this.mState != 0) {
                bundle.putInt(STATE_TAG, this.mState);
            }
            if (this.mLastAutofilledData != null) {
                bundle.putParcelable(LAST_AUTOFILLED_DATA_TAG, this.mLastAutofilledData);
            }
            return;
        }
    }

    public void onVisibleForAutofill() {
        Choreographer.getInstance().postCallback(4, new _$$Lambda$AutofillManager$YfpJNFodEuj5lbXfPlc77fsEvC8(this), null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerCallback(AutofillCallback autofillCallback) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (autofillCallback == null) {
                return;
            }
            boolean bl = this.mCallback != null;
            this.mCallback = autofillCallback;
            if (!bl) {
                try {
                    this.mService.setHasCallback(this.mSessionId, this.mContext.getUserId(), true);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    public void requestAutofill(View view) {
        this.notifyViewEntered(view, 1);
    }

    public void requestAutofill(View view, int n, Rect rect) {
        this.notifyViewEntered(view, n, rect, 1);
    }

    public void requestHideFillUi() {
        this.requestHideFillUi(this.mIdShownFillUi, true);
    }

    @SystemApi
    public void setAugmentedAutofillWhitelist(Set<String> object, Set<ComponentName> set) {
        block3 : {
            block4 : {
                int n;
                if (!this.hasAutofillFeature()) {
                    return;
                }
                SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
                try {
                    this.mService.setAugmentedAutofillWhitelist(Helper.toList(object), Helper.toList(set), syncResultReceiver);
                    n = syncResultReceiver.getIntResult();
                    if (n == -1) break block3;
                    if (n == 0) break block4;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("setAugmentedAutofillWhitelist(): received invalid result: ");
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                ((StringBuilder)object).append(n);
                Log.wtf(TAG, ((StringBuilder)object).toString());
                return;
            }
            return;
        }
        throw new SecurityException("caller is not user's Augmented Autofill Service");
    }

    public void setUserData(UserData userData) {
        try {
            this.mService.setUserData(userData);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterCallback(AutofillCallback autofillCallback) {
        if (!this.hasAutofillFeature()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (autofillCallback != null && this.mCallback != null && autofillCallback == this.mCallback) {
                this.mCallback = null;
                try {
                    this.mService.setHasCallback(this.mSessionId, this.mContext.getUserId(), false);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    private static final class AugmentedAutofillManagerClient
    extends IAugmentedAutofillManagerClient.Stub {
        private final WeakReference<AutofillManager> mAfm;

        private AugmentedAutofillManagerClient(AutofillManager autofillManager) {
            this.mAfm = new WeakReference<AutofillManager>(autofillManager);
        }

        static /* synthetic */ void lambda$autofill$0(AutofillManager autofillManager, int n, List list, List list2) {
            autofillManager.autofill(n, list, list2);
        }

        static /* synthetic */ void lambda$requestHideFillUi$2(AutofillManager autofillManager, AutofillId autofillId) {
            autofillManager.requestHideFillUi(autofillId, false);
        }

        static /* synthetic */ void lambda$requestShowFillUi$1(AutofillManager autofillManager, int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
            autofillManager.requestShowFillUi(n, autofillId, n2, n3, rect, iAutofillWindowPresenter);
        }

        @Override
        public void autofill(int n, List<AutofillId> list, List<AutofillValue> list2) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AugmentedAutofillManagerClient$k_qssZkEBwVEPdSmrHGsi2QT_3Y(autofillManager, n, list, list2));
            }
        }

        @Override
        public Rect getViewCoordinates(AutofillId autofillId) {
            Object object = (AutofillManager)this.mAfm.get();
            if (object == null) {
                return null;
            }
            if ((object = ((AutofillManager)object).getClient()) == null) {
                object = new StringBuilder();
                object.append("getViewCoordinates(");
                object.append(autofillId);
                object.append("): no autofill client");
                Log.w(AutofillManager.TAG, object.toString());
                return null;
            }
            Object object2 = object.autofillClientFindViewByAutofillIdTraversal(autofillId);
            if (object2 == null) {
                object = new StringBuilder();
                object.append("getViewCoordinates(");
                object.append(autofillId);
                object.append("): could not find view");
                Log.w(AutofillManager.TAG, object.toString());
                return null;
            }
            Rect rect = new Rect();
            ((View)object2).getWindowVisibleDisplayFrame(rect);
            object = new int[2];
            ((View)object2).getLocationOnScreen((int[])object);
            object = new Rect(object[0], object[1] - rect.top, object[0] + ((View)object2).getWidth(), object[1] - rect.top + ((View)object2).getHeight());
            if (Helper.sVerbose) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Coordinates for ");
                ((StringBuilder)object2).append(autofillId);
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(object);
                Log.v(AutofillManager.TAG, ((StringBuilder)object2).toString());
            }
            return object;
        }

        @Override
        public void requestHideFillUi(int n, AutofillId autofillId) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AugmentedAutofillManagerClient$tbNtqpHgXnRdc3JO5HaBlxclFg0(autofillManager, autofillId));
            }
        }

        @Override
        public void requestShowFillUi(int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AugmentedAutofillManagerClient$OrAY5q15e0VwuCSYnsGgs6GcY1U(autofillManager, n, autofillId, n2, n3, rect, iAutofillWindowPresenter));
            }
        }
    }

    public static abstract class AutofillCallback {
        public static final int EVENT_INPUT_HIDDEN = 2;
        public static final int EVENT_INPUT_SHOWN = 1;
        public static final int EVENT_INPUT_UNAVAILABLE = 3;

        public void onAutofillEvent(View view, int n) {
        }

        public void onAutofillEvent(View view, int n, int n2) {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface AutofillEventType {
        }

    }

    public static interface AutofillClient {
        public void autofillClientAuthenticate(int var1, IntentSender var2, Intent var3);

        public void autofillClientDispatchUnhandledKey(View var1, KeyEvent var2);

        public View autofillClientFindViewByAccessibilityIdTraversal(int var1, int var2);

        public View autofillClientFindViewByAutofillIdTraversal(AutofillId var1);

        public View[] autofillClientFindViewsByAutofillIdTraversal(AutofillId[] var1);

        public IBinder autofillClientGetActivityToken();

        public ComponentName autofillClientGetComponentName();

        public AutofillId autofillClientGetNextAutofillId();

        public boolean[] autofillClientGetViewVisibility(AutofillId[] var1);

        public boolean autofillClientIsCompatibilityModeEnabled();

        public boolean autofillClientIsFillUiShowing();

        public boolean autofillClientIsVisibleForAutofill();

        public boolean autofillClientRequestHideFillUi();

        public boolean autofillClientRequestShowFillUi(View var1, int var2, int var3, Rect var4, IAutofillWindowPresenter var5);

        public void autofillClientResetableStateAvailable();

        public void autofillClientRunOnUiThread(Runnable var1);

        public boolean isDisablingEnterExitEventForAutofill();
    }

    private static final class AutofillManagerClient
    extends IAutoFillManagerClient.Stub {
        private final WeakReference<AutofillManager> mAfm;

        private AutofillManagerClient(AutofillManager autofillManager) {
            this.mAfm = new WeakReference<AutofillManager>(autofillManager);
        }

        static /* synthetic */ void lambda$authenticate$2(AutofillManager autofillManager, int n, int n2, IntentSender intentSender, Intent intent) {
            autofillManager.authenticate(n, n2, intentSender, intent);
        }

        static /* synthetic */ void lambda$autofill$1(AutofillManager autofillManager, int n, List list, List list2) {
            autofillManager.autofill(n, list, list2);
        }

        static /* synthetic */ void lambda$dispatchUnhandledKey$6(AutofillManager autofillManager, int n, AutofillId autofillId, KeyEvent keyEvent) {
            autofillManager.dispatchUnhandledKey(n, autofillId, keyEvent);
        }

        static /* synthetic */ void lambda$getAugmentedAutofillClient$11(AutofillManager autofillManager, IResultReceiver iResultReceiver) {
            autofillManager.getAugmentedAutofillClient(iResultReceiver);
        }

        static /* synthetic */ void lambda$notifyNoFillUi$5(AutofillManager autofillManager, int n, AutofillId autofillId, int n2) {
            autofillManager.notifyNoFillUi(n, autofillId, n2);
        }

        static /* synthetic */ void lambda$requestHideFillUi$4(AutofillManager autofillManager, AutofillId autofillId) {
            autofillManager.requestHideFillUi(autofillId, false);
        }

        static /* synthetic */ void lambda$requestShowFillUi$3(AutofillManager autofillManager, int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
            autofillManager.requestShowFillUi(n, autofillId, n2, n3, rect, iAutofillWindowPresenter);
        }

        static /* synthetic */ void lambda$setSaveUiState$9(AutofillManager autofillManager, int n, boolean bl) {
            autofillManager.setSaveUiState(n, bl);
        }

        static /* synthetic */ void lambda$setSessionFinished$10(AutofillManager autofillManager, int n, List list) {
            autofillManager.setSessionFinished(n, list);
        }

        static /* synthetic */ void lambda$setState$0(AutofillManager autofillManager, int n) {
            autofillManager.setState(n);
        }

        static /* synthetic */ void lambda$setTrackedViews$8(AutofillManager autofillManager, int n, AutofillId[] arrautofillId, boolean bl, boolean bl2, AutofillId[] arrautofillId2, AutofillId autofillId) {
            autofillManager.setTrackedViews(n, arrautofillId, bl, bl2, arrautofillId2, autofillId);
        }

        static /* synthetic */ void lambda$startIntentSender$7(AutofillManager object, IntentSender intentSender, Intent intent) {
            try {
                ((AutofillManager)object).mContext.startIntentSender(intentSender, intent, 0, 0, 0);
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("startIntentSender() failed for intent:");
                ((StringBuilder)object).append(intentSender);
                Log.e(AutofillManager.TAG, ((StringBuilder)object).toString(), sendIntentException);
            }
        }

        @Override
        public void authenticate(int n, int n2, IntentSender intentSender, Intent intent) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$qyxZ4PACUgHFGSvMBHzgwjJ3yns(autofillManager, n, n2, intentSender, intent));
            }
        }

        @Override
        public void autofill(int n, List<AutofillId> list, List<AutofillValue> list2) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$1jAzMluMSJksx55SMUQn4BKB2Ng(autofillManager, n, list, list2));
            }
        }

        @Override
        public void dispatchUnhandledKey(int n, AutofillId autofillId, KeyEvent keyEvent) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$xqXjXW0fvc8JdYR5fgGKw9lJc3I(autofillManager, n, autofillId, keyEvent));
            }
        }

        @Override
        public void getAugmentedAutofillClient(IResultReceiver iResultReceiver) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$eeFWMGoPtaXdpslR3NLvhgXvMMs(autofillManager, iResultReceiver));
            }
        }

        @Override
        public void notifyNoFillUi(int n, AutofillId autofillId, int n2) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$K79QnIPRaZuikYDQdsLcIUBhqiI(autofillManager, n, autofillId, n2));
            }
        }

        @Override
        public void requestHideFillUi(int n, AutofillId autofillId) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$dCTetwfU0gT1ZrSzZGZiGStXlOY(autofillManager, autofillId));
            }
        }

        @Override
        public void requestShowFillUi(int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$kRL9XILLc2XNr90gxVDACLzcyqc(autofillManager, n, autofillId, n2, n3, rect, iAutofillWindowPresenter));
            }
        }

        @Override
        public void setSaveUiState(int n, boolean bl) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$QIW_100CKwHzdHffwaus9KOEHCA(autofillManager, n, bl));
            }
        }

        @Override
        public void setSessionFinished(int n, List<AutofillId> list) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$_IhPS_W7AwZ4M9TKIIFigmQd5SE(autofillManager, n, list));
            }
        }

        @Override
        public void setState(int n) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$qH36EJk2Hkdja9ZZmTxqYPyr0YA(autofillManager, n));
            }
        }

        @Override
        public void setTrackedViews(int n, AutofillId[] arrautofillId, boolean bl, boolean bl2, AutofillId[] arrautofillId2, AutofillId autofillId) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$BPlC2x7GLNHFS92rPUSzbcpFhUc(autofillManager, n, arrautofillId, bl, bl2, arrautofillId2, autofillId));
            }
        }

        @Override
        public void startIntentSender(IntentSender intentSender, Intent intent) {
            AutofillManager autofillManager = (AutofillManager)this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new _$$Lambda$AutofillManager$AutofillManagerClient$pM5e3ez5KTBdZt4d8qLEERBUSiU(autofillManager, intentSender, intent));
            }
        }
    }

    private final class CompatibilityBridge
    implements AccessibilityManager.AccessibilityPolicy {
        @GuardedBy(value={"mLock"})
        AccessibilityServiceInfo mCompatServiceInfo;
        @GuardedBy(value={"mLock"})
        private final Rect mFocusedBounds = new Rect();
        @GuardedBy(value={"mLock"})
        private long mFocusedNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        @GuardedBy(value={"mLock"})
        private int mFocusedWindowId = -1;
        @GuardedBy(value={"mLock"})
        private final Rect mTempBounds = new Rect();

        CompatibilityBridge() {
            AccessibilityManager.getInstance(AutofillManager.this.mContext).setAccessibilityPolicy(this);
        }

        private View findViewByAccessibilityId(int n, long l) {
            AutofillClient autofillClient = AutofillManager.this.getClient();
            if (autofillClient == null) {
                return null;
            }
            return autofillClient.autofillClientFindViewByAccessibilityIdTraversal(AccessibilityNodeInfo.getAccessibilityViewId(l), n);
        }

        private AccessibilityNodeInfo findVirtualNodeByAccessibilityId(View object, int n) {
            if ((object = ((View)object).getAccessibilityNodeProvider()) == null) {
                return null;
            }
            return ((AccessibilityNodeProvider)object).createAccessibilityNodeInfo(n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private AccessibilityServiceInfo getCompatServiceInfo() {
            Object object = AutofillManager.this.mLock;
            synchronized (object) {
                if (this.mCompatServiceInfo != null) {
                    return this.mCompatServiceInfo;
                }
                Object object2 = new Intent();
                Parcelable parcelable = new ComponentName("android", "com.android.server.autofill.AutofillCompatAccessibilityService");
                ((Intent)object2).setComponent((ComponentName)parcelable);
                parcelable = AutofillManager.this.mContext.getPackageManager().resolveService((Intent)object2, 1048704);
                try {
                    AccessibilityServiceInfo accessibilityServiceInfo;
                    this.mCompatServiceInfo = accessibilityServiceInfo = new AccessibilityServiceInfo((ResolveInfo)parcelable, AutofillManager.this.mContext);
                    return this.mCompatServiceInfo;
                }
                catch (IOException | XmlPullParserException throwable) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot find compat autofill service:");
                    stringBuilder.append(object2);
                    Log.e(AutofillManager.TAG, stringBuilder.toString());
                    object2 = new IllegalStateException("Cannot find compat autofill service");
                    throw object2;
                }
            }
        }

        private boolean isVirtualNode(int n) {
            boolean bl = n != -1 && n != Integer.MAX_VALUE;
            return bl;
        }

        private void notifyValueChanged(int n, long l) {
            int n2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
            if (!this.isVirtualNode(n2)) {
                return;
            }
            View view = this.findViewByAccessibilityId(n, l);
            if (view == null) {
                return;
            }
            AccessibilityNodeInfo accessibilityNodeInfo = this.findVirtualNodeByAccessibilityId(view, n2);
            if (accessibilityNodeInfo == null) {
                return;
            }
            AutofillManager.this.notifyValueChanged(view, n2, AutofillValue.forText(accessibilityNodeInfo.getText()));
        }

        private void notifyViewClicked(int n, long l) {
            int n2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
            if (!this.isVirtualNode(n2)) {
                return;
            }
            View view = this.findViewByAccessibilityId(n, l);
            if (view == null) {
                return;
            }
            if (this.findVirtualNodeByAccessibilityId(view, n2) == null) {
                return;
            }
            AutofillManager.this.notifyViewClicked(view, n2);
        }

        private boolean notifyViewEntered(int n, long l, Rect rect) {
            int n2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
            if (!this.isVirtualNode(n2)) {
                return false;
            }
            View view = this.findViewByAccessibilityId(n, l);
            if (view == null) {
                return false;
            }
            AccessibilityNodeInfo accessibilityNodeInfo = this.findVirtualNodeByAccessibilityId(view, n2);
            if (accessibilityNodeInfo == null) {
                return false;
            }
            if (!accessibilityNodeInfo.isEditable()) {
                return false;
            }
            Rect rect2 = this.mTempBounds;
            accessibilityNodeInfo.getBoundsInScreen(rect2);
            if (rect2.equals(rect)) {
                return false;
            }
            rect.set(rect2);
            AutofillManager.this.notifyViewEntered(view, n2, rect2);
            return true;
        }

        private void notifyViewExited(int n, long l) {
            int n2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
            if (!this.isVirtualNode(n2)) {
                return;
            }
            View view = this.findViewByAccessibilityId(n, l);
            if (view == null) {
                return;
            }
            AutofillManager.this.notifyViewExited(view, n2);
        }

        @GuardedBy(value={"mLock"})
        private void updateTrackedViewsLocked() {
            if (AutofillManager.this.mTrackedViews != null) {
                AutofillManager.this.mTrackedViews.onVisibleForAutofillChangedLocked();
            }
        }

        @Override
        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int n, List<AccessibilityServiceInfo> list) {
            List<AccessibilityServiceInfo> list2 = list;
            if (list == null) {
                list2 = new ArrayList<AccessibilityServiceInfo>();
            }
            list2.add(this.getCompatServiceInfo());
            return list2;
        }

        @Override
        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(List<AccessibilityServiceInfo> list) {
            List<AccessibilityServiceInfo> list2 = list;
            if (list == null) {
                list2 = new ArrayList<AccessibilityServiceInfo>();
            }
            list2.add(this.getCompatServiceInfo());
            return list2;
        }

        @Override
        public int getRelevantEventTypes(int n) {
            return n | 8 | 16 | 1 | 2048;
        }

        @Override
        public boolean isEnabled(boolean bl) {
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public AccessibilityEvent onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean bl, int n) {
            Object object;
            n = accessibilityEvent.getEventType();
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onAccessibilityEvent(");
                ((StringBuilder)object).append(AccessibilityEvent.eventTypeToString(n));
                ((StringBuilder)object).append("): virtualId=");
                ((StringBuilder)object).append(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityEvent.getSourceNodeId()));
                ((StringBuilder)object).append(", client=");
                ((StringBuilder)object).append(AutofillManager.this.getClient());
                Log.v(AutofillManager.TAG, ((StringBuilder)object).toString());
            }
            if (n != 1) {
                if (n != 8) {
                    if (n != 16) {
                        AutofillClient autofillClient;
                        if (n == 2048 && (autofillClient = AutofillManager.this.getClient()) != null) {
                            object = AutofillManager.this.mLock;
                            // MONITORENTER : object
                            if (autofillClient.autofillClientIsFillUiShowing()) {
                                this.notifyViewEntered(this.mFocusedWindowId, this.mFocusedNodeId, this.mFocusedBounds);
                            }
                            this.updateTrackedViewsLocked();
                            // MONITOREXIT : object
                        }
                    } else {
                        object = AutofillManager.this.mLock;
                        // MONITORENTER : object
                        if (this.mFocusedWindowId == accessibilityEvent.getWindowId() && this.mFocusedNodeId == accessibilityEvent.getSourceNodeId()) {
                            this.notifyValueChanged(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                        }
                        // MONITOREXIT : object
                    }
                } else {
                    long l;
                    object = AutofillManager.this.mLock;
                    // MONITORENTER : object
                    if (this.mFocusedWindowId == accessibilityEvent.getWindowId() && this.mFocusedNodeId == accessibilityEvent.getSourceNodeId()) {
                        // MONITOREXIT : object
                        return accessibilityEvent;
                    }
                    if (this.mFocusedWindowId != -1 && this.mFocusedNodeId != AccessibilityNodeInfo.UNDEFINED_NODE_ID) {
                        this.notifyViewExited(this.mFocusedWindowId, this.mFocusedNodeId);
                        this.mFocusedWindowId = -1;
                        this.mFocusedNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
                        this.mFocusedBounds.set(0, 0, 0, 0);
                    }
                    if (this.notifyViewEntered(n = accessibilityEvent.getWindowId(), l = accessibilityEvent.getSourceNodeId(), this.mFocusedBounds)) {
                        this.mFocusedWindowId = n;
                        this.mFocusedNodeId = l;
                    }
                    // MONITOREXIT : object
                }
            } else {
                object = AutofillManager.this.mLock;
                // MONITORENTER : object
                this.notifyViewClicked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                // MONITOREXIT : object
            }
            if (!bl) return null;
            return accessibilityEvent;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SmartSuggestionMode {
    }

    private class TrackedViews {
        private ArraySet<AutofillId> mInvisibleTrackedIds;
        private ArraySet<AutofillId> mVisibleTrackedIds;

        TrackedViews(AutofillId[] arrautofillId) {
            boolean[] arrbl = AutofillManager.this.getClient();
            if (!ArrayUtils.isEmpty(arrautofillId) && arrbl != null) {
                if (arrbl.autofillClientIsVisibleForAutofill()) {
                    if (Helper.sVerbose) {
                        Log.v(AutofillManager.TAG, "client is visible, check tracked ids");
                    }
                    arrbl = arrbl.autofillClientGetViewVisibility(arrautofillId);
                } else {
                    arrbl = new boolean[arrautofillId.length];
                }
                int n = arrautofillId.length;
                for (int i = 0; i < n; ++i) {
                    AutofillId autofillId = arrautofillId[i];
                    autofillId.resetSessionId();
                    if (arrbl[i]) {
                        this.mVisibleTrackedIds = this.addToSet(this.mVisibleTrackedIds, autofillId);
                        continue;
                    }
                    this.mInvisibleTrackedIds = this.addToSet(this.mInvisibleTrackedIds, autofillId);
                }
            }
            if (Helper.sVerbose) {
                arrbl = new StringBuilder();
                arrbl.append("TrackedViews(trackedIds=");
                arrbl.append(Arrays.toString(arrautofillId));
                arrbl.append("):  mVisibleTrackedIds=");
                arrbl.append(this.mVisibleTrackedIds);
                arrbl.append(" mInvisibleTrackedIds=");
                arrbl.append(this.mInvisibleTrackedIds);
                Log.v(AutofillManager.TAG, arrbl.toString());
            }
            if (this.mVisibleTrackedIds == null) {
                AutofillManager.this.finishSessionLocked();
            }
        }

        private <T> ArraySet<T> addToSet(ArraySet<T> arraySet, T t) {
            ArraySet<Object> arraySet2 = arraySet;
            if (arraySet == null) {
                arraySet2 = new ArraySet(1);
            }
            arraySet2.add(t);
            return arraySet2;
        }

        private <T> boolean isInSet(ArraySet<T> arraySet, T t) {
            boolean bl = arraySet != null && arraySet.contains(t);
            return bl;
        }

        private <T> ArraySet<T> removeFromSet(ArraySet<T> arraySet, T t) {
            if (arraySet == null) {
                return null;
            }
            arraySet.remove(t);
            if (arraySet.isEmpty()) {
                return null;
            }
            return arraySet;
        }

        @GuardedBy(value={"mLock"})
        void notifyViewVisibilityChangedLocked(AutofillId object, boolean bl) {
            if (Helper.sDebug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("notifyViewVisibilityChangedLocked(): id=");
                stringBuilder.append(object);
                stringBuilder.append(" isVisible=");
                stringBuilder.append(bl);
                Log.d(AutofillManager.TAG, stringBuilder.toString());
            }
            if (AutofillManager.this.isClientVisibleForAutofillLocked()) {
                if (bl) {
                    if (this.isInSet(this.mInvisibleTrackedIds, object)) {
                        this.mInvisibleTrackedIds = this.removeFromSet(this.mInvisibleTrackedIds, object);
                        this.mVisibleTrackedIds = this.addToSet(this.mVisibleTrackedIds, object);
                    }
                } else if (this.isInSet(this.mVisibleTrackedIds, object)) {
                    this.mVisibleTrackedIds = this.removeFromSet(this.mVisibleTrackedIds, object);
                    this.mInvisibleTrackedIds = this.addToSet(this.mInvisibleTrackedIds, object);
                }
            }
            if (this.mVisibleTrackedIds == null) {
                if (Helper.sVerbose) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("No more visible ids. Invisibile = ");
                    ((StringBuilder)object).append(this.mInvisibleTrackedIds);
                    Log.v(AutofillManager.TAG, ((StringBuilder)object).toString());
                }
                AutofillManager.this.finishSessionLocked();
            }
        }

        @GuardedBy(value={"mLock"})
        void onVisibleForAutofillChangedLocked() {
            boolean[] arrbl = AutofillManager.this.getClient();
            Object object = null;
            Object object2 = null;
            Object object3 = null;
            Object object4 = null;
            if (arrbl != null) {
                int n;
                int n2;
                Object object5;
                Object object6;
                if (Helper.sVerbose) {
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append("onVisibleForAutofillChangedLocked(): inv= ");
                    ((StringBuilder)object6).append(this.mInvisibleTrackedIds);
                    ((StringBuilder)object6).append(" vis=");
                    ((StringBuilder)object6).append(this.mVisibleTrackedIds);
                    Log.v(AutofillManager.TAG, ((StringBuilder)object6).toString());
                }
                if ((object6 = this.mInvisibleTrackedIds) != null) {
                    object5 = new ArrayList<AutofillId>((Collection<AutofillId>)object6);
                    object6 = arrbl.autofillClientGetViewVisibility(Helper.toArray(object5));
                    n2 = ((ArrayList)object5).size();
                    n = 0;
                    do {
                        object = object2;
                        object3 = object4;
                        if (n >= n2) break;
                        AutofillId autofillId = ((ArrayList)object5).get(n);
                        if (object6[n] != false) {
                            object2 = object3 = this.addToSet((ArraySet<T>)object2, (T)autofillId);
                            object = object4;
                            if (Helper.sDebug) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("onVisibleForAutofill() ");
                                ((StringBuilder)object).append(autofillId);
                                ((StringBuilder)object).append(" became visible");
                                Log.d(AutofillManager.TAG, ((StringBuilder)object).toString());
                                object2 = object3;
                                object = object4;
                            }
                        } else {
                            object = this.addToSet((ArraySet<T>)object4, (T)autofillId);
                        }
                        ++n;
                        object4 = object;
                    } while (true);
                }
                object6 = this.mVisibleTrackedIds;
                object4 = object;
                object2 = object3;
                if (object6 != null) {
                    object6 = new ArrayList(object6);
                    arrbl = arrbl.autofillClientGetViewVisibility(Helper.toArray((Collection<AutofillId>)object6));
                    n2 = ((ArrayList)object6).size();
                    n = 0;
                    do {
                        object4 = object;
                        object2 = object3;
                        if (n >= n2) break;
                        object5 = (AutofillId)((ArrayList)object6).get(n);
                        if (arrbl[n]) {
                            object4 = this.addToSet((ArraySet<T>)object, (T)object5);
                        } else {
                            object2 = this.addToSet((ArraySet<T>)object3, (T)object5);
                            object4 = object;
                            object3 = object2;
                            if (Helper.sDebug) {
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append("onVisibleForAutofill() ");
                                ((StringBuilder)object3).append(object5);
                                ((StringBuilder)object3).append(" became invisible");
                                Log.d(AutofillManager.TAG, ((StringBuilder)object3).toString());
                                object3 = object2;
                                object4 = object;
                            }
                        }
                        ++n;
                        object = object4;
                    } while (true);
                }
                this.mInvisibleTrackedIds = object2;
                this.mVisibleTrackedIds = object4;
            }
            if (this.mVisibleTrackedIds == null) {
                if (Helper.sVerbose) {
                    Log.v(AutofillManager.TAG, "onVisibleForAutofillChangedLocked(): no more visible ids");
                }
                AutofillManager.this.finishSessionLocked();
            }
        }
    }

}

