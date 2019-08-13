/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.IWindow;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRequestPreparer;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityManager;
import android.view.accessibility.IAccessibilityManagerClient;
import android.view.accessibility._$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA;
import android.view.accessibility._$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM;
import android.view.accessibility._$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI;
import android.view.accessibility._$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IntPair;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccessibilityManager {
    public static final String ACTION_CHOOSE_ACCESSIBILITY_BUTTON = "com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON";
    public static final int AUTOCLICK_DELAY_DEFAULT = 600;
    public static final int DALTONIZER_CORRECT_DEUTERANOMALY = 12;
    public static final int DALTONIZER_DISABLED = -1;
    @UnsupportedAppUsage
    public static final int DALTONIZER_SIMULATE_MONOCHROMACY = 0;
    private static final boolean DEBUG = false;
    public static final int FLAG_CONTENT_CONTROLS = 4;
    public static final int FLAG_CONTENT_ICONS = 1;
    public static final int FLAG_CONTENT_TEXT = 2;
    private static final String LOG_TAG = "AccessibilityManager";
    public static final int STATE_FLAG_ACCESSIBILITY_ENABLED = 1;
    public static final int STATE_FLAG_HIGH_TEXT_CONTRAST_ENABLED = 4;
    public static final int STATE_FLAG_TOUCH_EXPLORATION_ENABLED = 2;
    @UnsupportedAppUsage
    private static AccessibilityManager sInstance;
    @UnsupportedAppUsage
    static final Object sInstanceSync;
    AccessibilityPolicy mAccessibilityPolicy;
    @UnsupportedAppUsage
    private final ArrayMap<AccessibilityStateChangeListener, Handler> mAccessibilityStateChangeListeners = new ArrayMap();
    final Handler.Callback mCallback = new MyCallback();
    private final IAccessibilityManagerClient.Stub mClient = new IAccessibilityManagerClient.Stub(){

        public /* synthetic */ void lambda$notifyServicesStateChanged$0$AccessibilityManager$1(AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
            accessibilityServicesStateChangeListener.onAccessibilityServicesStateChanged(AccessibilityManager.this);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void notifyServicesStateChanged(long l) {
            ArrayMap arrayMap;
            AccessibilityManager.this.updateUiTimeout(l);
            Object object = AccessibilityManager.this.mLock;
            synchronized (object) {
                if (AccessibilityManager.this.mServicesStateChangeListeners.isEmpty()) {
                    return;
                }
                arrayMap = new ArrayMap(AccessibilityManager.this.mServicesStateChangeListeners);
            }
            int n = arrayMap.size();
            int n2 = 0;
            while (n2 < n) {
                object = (AccessibilityServicesStateChangeListener)AccessibilityManager.this.mServicesStateChangeListeners.keyAt(n2);
                ((Handler)AccessibilityManager.this.mServicesStateChangeListeners.valueAt(n2)).post(new _$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA(this, (AccessibilityServicesStateChangeListener)object));
                ++n2;
            }
            return;
        }

        @Override
        public void setRelevantEventTypes(int n) {
            AccessibilityManager.this.mRelevantEventTypes = n;
        }

        @Override
        public void setState(int n) {
            AccessibilityManager.this.mHandler.obtainMessage(1, n, 0).sendToTarget();
        }
    };
    @UnsupportedAppUsage
    final Handler mHandler;
    private final ArrayMap<HighTextContrastChangeListener, Handler> mHighTextContrastStateChangeListeners = new ArrayMap();
    int mInteractiveUiTimeout;
    @UnsupportedAppUsage(maxTargetSdk=28)
    boolean mIsEnabled;
    @UnsupportedAppUsage(trackingBug=123768939L)
    boolean mIsHighTextContrastEnabled;
    boolean mIsTouchExplorationEnabled;
    @UnsupportedAppUsage
    private final Object mLock = new Object();
    int mNonInteractiveUiTimeout;
    int mRelevantEventTypes = -1;
    private SparseArray<List<AccessibilityRequestPreparer>> mRequestPreparerLists;
    @UnsupportedAppUsage
    private IAccessibilityManager mService;
    private final ArrayMap<AccessibilityServicesStateChangeListener, Handler> mServicesStateChangeListeners = new ArrayMap();
    private final ArrayMap<TouchExplorationStateChangeListener, Handler> mTouchExplorationStateChangeListeners = new ArrayMap();
    @UnsupportedAppUsage
    final int mUserId;

    static {
        sInstanceSync = new Object();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AccessibilityManager(Context object, IAccessibilityManager iAccessibilityManager, int n) {
        this.mHandler = new Handler(((Context)object).getMainLooper(), this.mCallback);
        this.mUserId = n;
        object = this.mLock;
        synchronized (object) {
            this.tryConnectToServiceLocked(iAccessibilityManager);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AccessibilityManager(Handler object, IAccessibilityManager iAccessibilityManager, int n) {
        this.mHandler = object;
        this.mUserId = n;
        object = this.mLock;
        synchronized (object) {
            this.tryConnectToServiceLocked(iAccessibilityManager);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static AccessibilityManager getInstance(Context context) {
        Object object = sInstanceSync;
        synchronized (object) {
            if (sInstance == null) {
                AccessibilityManager accessibilityManager;
                int n = Binder.getCallingUid() != 1000 && context.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0 && context.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0 ? context.getUserId() : -2;
                sInstance = accessibilityManager = new AccessibilityManager(context, null, n);
            }
            return sInstance;
        }
    }

    private IAccessibilityManager getServiceLocked() {
        if (this.mService == null) {
            this.tryConnectToServiceLocked(null);
        }
        return this.mService;
    }

    public static boolean isAccessibilityButtonSupported() {
        return Resources.getSystem().getBoolean(17891513);
    }

    static /* synthetic */ void lambda$notifyAccessibilityStateChanged$0(AccessibilityStateChangeListener accessibilityStateChangeListener, boolean bl) {
        accessibilityStateChangeListener.onAccessibilityStateChanged(bl);
    }

    static /* synthetic */ void lambda$notifyHighTextContrastStateChanged$2(HighTextContrastChangeListener highTextContrastChangeListener, boolean bl) {
        highTextContrastChangeListener.onHighTextContrastStateChanged(bl);
    }

    static /* synthetic */ void lambda$notifyTouchExplorationStateChanged$1(TouchExplorationStateChangeListener touchExplorationStateChangeListener, boolean bl) {
        touchExplorationStateChangeListener.onTouchExplorationStateChanged(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyAccessibilityStateChanged() {
        ArrayMap<AccessibilityStateChangeListener, Handler> arrayMap;
        boolean bl;
        Object object = this.mLock;
        synchronized (object) {
            if (this.mAccessibilityStateChangeListeners.isEmpty()) {
                return;
            }
            bl = this.isEnabled();
            arrayMap = new ArrayMap<AccessibilityStateChangeListener, Handler>(this.mAccessibilityStateChangeListeners);
        }
        int n = arrayMap.size();
        int n2 = 0;
        while (n2 < n) {
            object = arrayMap.keyAt(n2);
            arrayMap.valueAt(n2).post(new _$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo((AccessibilityStateChangeListener)object, bl));
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyHighTextContrastStateChanged() {
        ArrayMap<HighTextContrastChangeListener, Handler> arrayMap;
        boolean bl;
        Object object = this.mLock;
        synchronized (object) {
            if (this.mHighTextContrastStateChangeListeners.isEmpty()) {
                return;
            }
            bl = this.mIsHighTextContrastEnabled;
            arrayMap = new ArrayMap<HighTextContrastChangeListener, Handler>(this.mHighTextContrastStateChangeListeners);
        }
        int n = arrayMap.size();
        int n2 = 0;
        while (n2 < n) {
            object = arrayMap.keyAt(n2);
            arrayMap.valueAt(n2).post(new _$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM((HighTextContrastChangeListener)object, bl));
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyTouchExplorationStateChanged() {
        ArrayMap<TouchExplorationStateChangeListener, Handler> arrayMap;
        boolean bl;
        Object object = this.mLock;
        synchronized (object) {
            if (this.mTouchExplorationStateChangeListeners.isEmpty()) {
                return;
            }
            bl = this.mIsTouchExplorationEnabled;
            arrayMap = new ArrayMap<TouchExplorationStateChangeListener, Handler>(this.mTouchExplorationStateChangeListeners);
        }
        int n = arrayMap.size();
        int n2 = 0;
        while (n2 < n) {
            object = arrayMap.keyAt(n2);
            arrayMap.valueAt(n2).post(new _$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI((TouchExplorationStateChangeListener)object, bl));
            ++n2;
        }
        return;
    }

    @UnsupportedAppUsage
    private void setStateLocked(int n) {
        boolean bl = false;
        boolean bl2 = (n & 1) != 0;
        boolean bl3 = (n & 2) != 0;
        if ((n & 4) != 0) {
            bl = true;
        }
        boolean bl4 = this.isEnabled();
        boolean bl5 = this.mIsTouchExplorationEnabled;
        boolean bl6 = this.mIsHighTextContrastEnabled;
        this.mIsEnabled = bl2;
        this.mIsTouchExplorationEnabled = bl3;
        this.mIsHighTextContrastEnabled = bl;
        if (bl4 != this.isEnabled()) {
            this.notifyAccessibilityStateChanged();
        }
        if (bl5 != bl3) {
            this.notifyTouchExplorationStateChanged();
        }
        if (bl6 != bl) {
            this.notifyHighTextContrastStateChanged();
        }
    }

    private void tryConnectToServiceLocked(IAccessibilityManager object) {
        IAccessibilityManager iAccessibilityManager = object;
        if (object == null) {
            object = ServiceManager.getService("accessibility");
            if (object == null) {
                return;
            }
            iAccessibilityManager = IAccessibilityManager.Stub.asInterface((IBinder)object);
        }
        try {
            long l = iAccessibilityManager.addClient(this.mClient, this.mUserId);
            this.setStateLocked(IntPair.first(l));
            this.mRelevantEventTypes = IntPair.second(l);
            this.updateUiTimeout(iAccessibilityManager.getRecommendedTimeoutMillis());
            this.mService = iAccessibilityManager;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "AccessibilityManagerService is dead", remoteException);
        }
    }

    private void updateUiTimeout(long l) {
        this.mInteractiveUiTimeout = IntPair.first(l);
        this.mNonInteractiveUiTimeout = IntPair.second(l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addAccessibilityInteractionConnection(IWindow iWindow, String string2, IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
        IAccessibilityManager iAccessibilityManager;
        int n;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return -1;
            }
            n = this.mUserId;
        }
        try {
            return iAccessibilityManager.addAccessibilityInteractionConnection(iWindow, iAccessibilityInteractionConnection, string2, n);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while adding an accessibility interaction connection. ", remoteException);
            return -1;
        }
    }

    public void addAccessibilityRequestPreparer(AccessibilityRequestPreparer accessibilityRequestPreparer) {
        List<AccessibilityRequestPreparer> list;
        if (this.mRequestPreparerLists == null) {
            this.mRequestPreparerLists = new SparseArray(1);
        }
        int n = accessibilityRequestPreparer.getAccessibilityViewId();
        List<AccessibilityRequestPreparer> list2 = list = this.mRequestPreparerLists.get(n);
        if (list == null) {
            list2 = new ArrayList<AccessibilityRequestPreparer>(1);
            this.mRequestPreparerLists.put(n, list2);
        }
        list2.add(accessibilityRequestPreparer);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener, Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            ArrayMap<AccessibilityServicesStateChangeListener, Handler> arrayMap = this.mServicesStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(accessibilityServicesStateChangeListener, handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener, Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            ArrayMap<AccessibilityStateChangeListener, Handler> arrayMap = this.mAccessibilityStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(accessibilityStateChangeListener, handler);
            return;
        }
    }

    public boolean addAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener) {
        this.addAccessibilityStateChangeListener(accessibilityStateChangeListener, null);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addHighTextContrastStateChangeListener(HighTextContrastChangeListener highTextContrastChangeListener, Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            ArrayMap<HighTextContrastChangeListener, Handler> arrayMap = this.mHighTextContrastStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(highTextContrastChangeListener, handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener, Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            ArrayMap<TouchExplorationStateChangeListener, Handler> arrayMap = this.mTouchExplorationStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(touchExplorationStateChangeListener, handler);
            return;
        }
    }

    public boolean addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        this.addTouchExplorationStateChangeListener(touchExplorationStateChangeListener, null);
        return true;
    }

    @Deprecated
    public List<ServiceInfo> getAccessibilityServiceList() {
        List<AccessibilityServiceInfo> list = this.getInstalledAccessibilityServiceList();
        ArrayList<ServiceInfo> arrayList = new ArrayList<ServiceInfo>();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            arrayList.add(list.get((int)i).getResolveInfo().serviceInfo);
        }
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getAccessibilityShortcutService() {
        IAccessibilityManager iAccessibilityManager;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
        }
        if (iAccessibilityManager == null) return null;
        try {
            return iAccessibilityManager.getAccessibilityShortcutService();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public int getAccessibilityWindowId(IBinder iBinder) {
        IAccessibilityManager iAccessibilityManager;
        if (iBinder == null) {
            return -1;
        }
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return -1;
            }
        }
        try {
            return iAccessibilityManager.getAccessibilityWindowId(iBinder);
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    @VisibleForTesting
    public Handler.Callback getCallback() {
        return this.mCallback;
    }

    public IAccessibilityManagerClient getClient() {
        return this.mClient;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int n) {
        List<AccessibilityServiceInfo> list;
        int n2;
        List<AccessibilityServiceInfo> list2 = this.mLock;
        synchronized (list2) {
            list = this.getServiceLocked();
            if (list == null) {
                return Collections.emptyList();
            }
            n2 = this.mUserId;
        }
        list2 = null;
        try {
            list2 = list = list.getEnabledAccessibilityServiceList(n, n2);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", remoteException);
        }
        AccessibilityPolicy accessibilityPolicy = this.mAccessibilityPolicy;
        list = list2;
        if (accessibilityPolicy != null) {
            list = accessibilityPolicy.getEnabledAccessibilityServiceList(n, list2);
        }
        if (list == null) return Collections.emptyList();
        return Collections.unmodifiableList(list);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList() {
        List<AccessibilityServiceInfo> list;
        int n;
        List<AccessibilityServiceInfo> list2 = this.mLock;
        synchronized (list2) {
            list = this.getServiceLocked();
            if (list == null) {
                return Collections.emptyList();
            }
            n = this.mUserId;
        }
        list2 = null;
        try {
            list2 = list = list.getInstalledAccessibilityServiceList(n);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", remoteException);
        }
        AccessibilityPolicy accessibilityPolicy = this.mAccessibilityPolicy;
        list = list2;
        if (accessibilityPolicy != null) {
            list = accessibilityPolicy.getInstalledAccessibilityServiceList(list2);
        }
        if (list == null) return Collections.emptyList();
        return Collections.unmodifiableList(list);
    }

    public AccessibilityServiceInfo getInstalledServiceInfoWithComponentName(ComponentName componentName) {
        List<AccessibilityServiceInfo> list = this.getInstalledAccessibilityServiceList();
        if (list != null && componentName != null) {
            for (int i = 0; i < list.size(); ++i) {
                if (!componentName.equals(list.get(i).getComponentName())) continue;
                return list.get(i);
            }
            return null;
        }
        return null;
    }

    public int getRecommendedTimeoutMillis(int n, int n2) {
        boolean bl = false;
        boolean bl2 = (n2 & 4) != 0;
        if ((n2 & 1) != 0 || (n2 & 2) != 0) {
            bl = true;
        }
        n = n2 = n;
        if (bl2) {
            n = Math.max(n2, this.mInteractiveUiTimeout);
        }
        n2 = n;
        if (bl) {
            n2 = Math.max(n, this.mNonInteractiveUiTimeout);
        }
        return n2;
    }

    public List<AccessibilityRequestPreparer> getRequestPreparersForAccessibilityId(int n) {
        SparseArray<List<AccessibilityRequestPreparer>> sparseArray = this.mRequestPreparerLists;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void interrupt() {
        Object object;
        int n;
        Object object2 = this.mLock;
        synchronized (object2) {
            object = this.getServiceLocked();
            if (object == null) {
                return;
            }
            if (!this.isEnabled()) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    Log.e(LOG_TAG, "Interrupt called with accessibility disabled");
                    return;
                }
                object = new IllegalStateException("Accessibility off. Did you forget to check that?");
                throw object;
            }
            n = this.mUserId;
        }
        try {
            object.interrupt(n);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while requesting interrupt from all services. ", remoteException);
        }
    }

    public boolean isAccessibilityVolumeStreamActive() {
        List<AccessibilityServiceInfo> list = this.getEnabledAccessibilityServiceList(-1);
        for (int i = 0; i < list.size(); ++i) {
            if ((list.get((int)i).flags & 128) == 0) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIsEnabled) return true;
            if (this.mAccessibilityPolicy == null) return false;
            if (!this.mAccessibilityPolicy.isEnabled(this.mIsEnabled)) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean isHighTextContrastEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.getServiceLocked() != null) return this.mIsHighTextContrastEnabled;
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isTouchExplorationEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.getServiceLocked() != null) return this.mIsTouchExplorationEnabled;
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyAccessibilityButtonClicked(int n) {
        IAccessibilityManager iAccessibilityManager;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return;
            }
        }
        try {
            iAccessibilityManager.notifyAccessibilityButtonClicked(n);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while dispatching accessibility button click", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyAccessibilityButtonVisibilityChanged(boolean bl) {
        IAccessibilityManager iAccessibilityManager;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return;
            }
        }
        try {
            iAccessibilityManager.notifyAccessibilityButtonVisibilityChanged(bl);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while dispatching accessibility button visibility change", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void performAccessibilityShortcut() {
        IAccessibilityManager iAccessibilityManager;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return;
            }
        }
        try {
            iAccessibilityManager.performAccessibilityShortcut();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error performing accessibility shortcut. ", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeAccessibilityInteractionConnection(IWindow iWindow) {
        IAccessibilityManager iAccessibilityManager;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return;
            }
        }
        try {
            iAccessibilityManager.removeAccessibilityInteractionConnection(iWindow);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while removing an accessibility interaction connection. ", remoteException);
        }
    }

    public void removeAccessibilityRequestPreparer(AccessibilityRequestPreparer accessibilityRequestPreparer) {
        if (this.mRequestPreparerLists == null) {
            return;
        }
        int n = accessibilityRequestPreparer.getAccessibilityViewId();
        List<AccessibilityRequestPreparer> list = this.mRequestPreparerLists.get(n);
        if (list != null) {
            list.remove(accessibilityRequestPreparer);
            if (list.isEmpty()) {
                this.mRequestPreparerLists.remove(n);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mServicesStateChangeListeners.remove(accessibilityServicesStateChangeListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean removeAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener) {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mAccessibilityStateChangeListeners.indexOfKey(accessibilityStateChangeListener);
            this.mAccessibilityStateChangeListeners.remove(accessibilityStateChangeListener);
            if (n < 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeHighTextContrastStateChangeListener(HighTextContrastChangeListener highTextContrastChangeListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mHighTextContrastStateChangeListeners.remove(highTextContrastChangeListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean removeTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mTouchExplorationStateChangeListeners.indexOfKey(touchExplorationStateChangeListener);
            this.mTouchExplorationStateChangeListeners.remove(touchExplorationStateChangeListener);
            if (n < 0) return false;
            return true;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void sendAccessibilityEvent(AccessibilityEvent object) {
        Throwable throwable322;
        Object accessibilityEvent;
        block20 : {
            block19 : {
                Object object2;
                Object object3 = this.mLock;
                // MONITORENTER : object3
                IAccessibilityManager iAccessibilityManager = this.getServiceLocked();
                if (iAccessibilityManager == null) {
                    // MONITOREXIT : object3
                    return;
                }
                ((AccessibilityEvent)object).setEventTime(SystemClock.uptimeMillis());
                if (this.mAccessibilityPolicy != null) {
                    accessibilityEvent = object2 = this.mAccessibilityPolicy.onAccessibilityEvent((AccessibilityEvent)object, this.mIsEnabled, this.mRelevantEventTypes);
                    if (object2 == null) {
                        // MONITOREXIT : object3
                        return;
                    }
                } else {
                    accessibilityEvent = object;
                }
                if (!this.isEnabled()) {
                    if (Looper.myLooper() != Looper.getMainLooper()) {
                        Log.e(LOG_TAG, "AccessibilityEvent sent with accessibility disabled");
                        // MONITOREXIT : object3
                        return;
                    }
                    object = new IllegalStateException("Accessibility off. Did you forget to check that?");
                    throw object;
                }
                if ((((AccessibilityEvent)accessibilityEvent).getEventType() & this.mRelevantEventTypes) == 0) {
                    // MONITOREXIT : object3
                    return;
                }
                int n = this.mUserId;
                // MONITOREXIT : object3
                long l = Binder.clearCallingIdentity();
                iAccessibilityManager.sendAccessibilityEvent((AccessibilityEvent)accessibilityEvent, n);
                Binder.restoreCallingIdentity(l);
                if (object == accessibilityEvent) break block19;
                catch (Throwable throwable2) {
                    try {
                        Binder.restoreCallingIdentity(l);
                        throw throwable2;
                    }
                    catch (Throwable throwable322) {
                        break block20;
                    }
                    catch (RemoteException remoteException) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Error during sending ");
                        ((StringBuilder)object2).append(accessibilityEvent);
                        ((StringBuilder)object2).append(" ");
                        Log.e(LOG_TAG, ((StringBuilder)object2).toString(), remoteException);
                        if (object == accessibilityEvent) break block19;
                    }
                }
                ((AccessibilityEvent)object).recycle();
            }
            ((AccessibilityEvent)accessibilityEvent).recycle();
            return;
        }
        if (object != accessibilityEvent) {
            ((AccessibilityEvent)object).recycle();
        }
        ((AccessibilityEvent)accessibilityEvent).recycle();
        throw throwable322;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean sendFingerprintGesture(int n) {
        IAccessibilityManager iAccessibilityManager;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return false;
            }
        }
        try {
            return iAccessibilityManager.sendFingerprintGesture(n);
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAccessibilityPolicy(AccessibilityPolicy accessibilityPolicy) {
        Object object = this.mLock;
        synchronized (object) {
            this.mAccessibilityPolicy = accessibilityPolicy;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
        IAccessibilityManager iAccessibilityManager;
        Object object = this.mLock;
        synchronized (object) {
            iAccessibilityManager = this.getServiceLocked();
            if (iAccessibilityManager == null) {
                return;
            }
        }
        try {
            iAccessibilityManager.setPictureInPictureActionReplacingConnection(iAccessibilityInteractionConnection);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error setting picture in picture action replacement", remoteException);
        }
    }

    public static interface AccessibilityPolicy {
        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int var1, List<AccessibilityServiceInfo> var2);

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(List<AccessibilityServiceInfo> var1);

        public int getRelevantEventTypes(int var1);

        public boolean isEnabled(boolean var1);

        public AccessibilityEvent onAccessibilityEvent(AccessibilityEvent var1, boolean var2, int var3);
    }

    public static interface AccessibilityServicesStateChangeListener {
        public void onAccessibilityServicesStateChanged(AccessibilityManager var1);
    }

    public static interface AccessibilityStateChangeListener {
        public void onAccessibilityStateChanged(boolean var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ContentFlag {
    }

    public static interface HighTextContrastChangeListener {
        public void onHighTextContrastStateChanged(boolean var1);
    }

    private final class MyCallback
    implements Handler.Callback {
        public static final int MSG_SET_STATE = 1;

        private MyCallback() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean handleMessage(Message object) {
            if (((Message)object).what != 1) {
                return true;
            }
            int n = ((Message)object).arg1;
            object = AccessibilityManager.this.mLock;
            synchronized (object) {
                AccessibilityManager.this.setStateLocked(n);
                return true;
            }
        }
    }

    public static interface TouchExplorationStateChangeListener {
        public void onTouchExplorationStateChanged(boolean var1);
    }

}

