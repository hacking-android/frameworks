/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.accessibilityservice.IAccessibilityServiceConnection;
import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityCache;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class AccessibilityInteractionClient
extends IAccessibilityInteractionConnectionCallback.Stub {
    private static final boolean CHECK_INTEGRITY = true;
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "AccessibilityInteractionClient";
    public static final int NO_ID = -1;
    private static final long TIMEOUT_INTERACTION_MILLIS = 5000L;
    private static AccessibilityCache sAccessibilityCache;
    private static final LongSparseArray<AccessibilityInteractionClient> sClients;
    private static final SparseArray<IAccessibilityServiceConnection> sConnectionCache;
    private static final Object sStaticLock;
    private AccessibilityNodeInfo mFindAccessibilityNodeInfoResult;
    private List<AccessibilityNodeInfo> mFindAccessibilityNodeInfosResult;
    private final Object mInstanceLock = new Object();
    private volatile int mInteractionId = -1;
    private final AtomicInteger mInteractionIdCounter = new AtomicInteger();
    private boolean mPerformAccessibilityActionResult;
    private Message mSameThreadMessage;

    static {
        sStaticLock = new Object();
        sClients = new LongSparseArray();
        sConnectionCache = new SparseArray();
        sAccessibilityCache = new AccessibilityCache(new AccessibilityCache.AccessibilityNodeRefresher());
    }

    private AccessibilityInteractionClient() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addConnection(int n, IAccessibilityServiceConnection iAccessibilityServiceConnection) {
        SparseArray<IAccessibilityServiceConnection> sparseArray = sConnectionCache;
        synchronized (sparseArray) {
            sConnectionCache.put(n, iAccessibilityServiceConnection);
            return;
        }
    }

    private void checkFindAccessibilityNodeInfoResultIntegrity(List<AccessibilityNodeInfo> object) {
        int n;
        Object object2;
        int n2;
        if (object.size() == 0) {
            return;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = object.get(0);
        int n3 = object.size();
        for (n2 = 1; n2 < n3; ++n2) {
            n = n2;
            do {
                object2 = accessibilityNodeInfo;
                if (n >= n3) break;
                object2 = object.get(n);
                if (accessibilityNodeInfo.getParentNodeId() == ((AccessibilityNodeInfo)object2).getSourceNodeId()) break;
                ++n;
            } while (true);
            accessibilityNodeInfo = object2;
        }
        if (accessibilityNodeInfo == null) {
            Log.e(LOG_TAG, "No root.");
        }
        HashSet<AccessibilityNodeInfo> hashSet = new HashSet<AccessibilityNodeInfo>();
        object2 = new LinkedList();
        object2.add(accessibilityNodeInfo);
        while (!object2.isEmpty()) {
            accessibilityNodeInfo = (AccessibilityNodeInfo)object2.poll();
            if (!hashSet.add(accessibilityNodeInfo)) {
                Log.e(LOG_TAG, "Duplicate node.");
                return;
            }
            int n4 = accessibilityNodeInfo.getChildCount();
            for (n2 = 0; n2 < n4; ++n2) {
                long l = accessibilityNodeInfo.getChildId(n2);
                for (n = 0; n < n3; ++n) {
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo)object.get(n);
                    if (accessibilityNodeInfo2.getSourceNodeId() != l) continue;
                    object2.add(accessibilityNodeInfo2);
                }
            }
        }
        n2 = object.size() - hashSet.size();
        if (n2 > 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" Disconnected nodes.");
            Log.e(LOG_TAG, ((StringBuilder)object).toString());
        }
    }

    private void clearResultLocked() {
        this.mInteractionId = -1;
        this.mFindAccessibilityNodeInfoResult = null;
        this.mFindAccessibilityNodeInfosResult = null;
        this.mPerformAccessibilityActionResult = false;
    }

    private void finalizeAndCacheAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, int n, boolean bl, String[] arrstring) {
        if (accessibilityNodeInfo != null) {
            CharSequence charSequence;
            accessibilityNodeInfo.setConnectionId(n);
            if (!(ArrayUtils.isEmpty(arrstring) || (charSequence = accessibilityNodeInfo.getPackageName()) != null && ArrayUtils.contains(arrstring, charSequence.toString()))) {
                accessibilityNodeInfo.setPackageName(arrstring[0]);
            }
            accessibilityNodeInfo.setSealed(true);
            if (!bl) {
                sAccessibilityCache.add(accessibilityNodeInfo);
            }
        }
    }

    private void finalizeAndCacheAccessibilityNodeInfos(List<AccessibilityNodeInfo> list, int n, boolean bl, String[] arrstring) {
        if (list != null) {
            int n2 = list.size();
            for (int i = 0; i < n2; ++i) {
                this.finalizeAndCacheAccessibilityNodeInfo(list.get(i), n, bl, arrstring);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static IAccessibilityServiceConnection getConnection(int n) {
        SparseArray<IAccessibilityServiceConnection> sparseArray = sConnectionCache;
        synchronized (sparseArray) {
            return sConnectionCache.get(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AccessibilityNodeInfo getFindAccessibilityNodeInfoResultAndClear(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            AccessibilityNodeInfo accessibilityNodeInfo = this.waitForResultTimedLocked(n) ? this.mFindAccessibilityNodeInfoResult : null;
            this.clearResultLocked();
            return accessibilityNodeInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<AccessibilityNodeInfo> getFindAccessibilityNodeInfosResultAndClear(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            List<AccessibilityNodeInfo> list = this.waitForResultTimedLocked(n) ? this.mFindAccessibilityNodeInfosResult : Collections.emptyList();
            this.clearResultLocked();
            if (Build.IS_DEBUGGABLE) {
                this.checkFindAccessibilityNodeInfoResultIntegrity(list);
            }
            return list;
        }
    }

    @UnsupportedAppUsage
    public static AccessibilityInteractionClient getInstance() {
        return AccessibilityInteractionClient.getInstanceForThread(Thread.currentThread().getId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AccessibilityInteractionClient getInstanceForThread(long l) {
        Object object = sStaticLock;
        synchronized (object) {
            AccessibilityInteractionClient accessibilityInteractionClient;
            AccessibilityInteractionClient accessibilityInteractionClient2 = accessibilityInteractionClient = sClients.get(l);
            if (accessibilityInteractionClient == null) {
                accessibilityInteractionClient2 = new AccessibilityInteractionClient();
                sClients.put(l, accessibilityInteractionClient2);
            }
            return accessibilityInteractionClient2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean getPerformAccessibilityActionResultAndClear(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            boolean bl = this.waitForResultTimedLocked(n) ? this.mPerformAccessibilityActionResult : false;
            this.clearResultLocked();
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Message getSameProcessMessageAndClear() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            Message message = this.mSameThreadMessage;
            this.mSameThreadMessage = null;
            return message;
        }
    }

    private static String idToString(int n, long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("/");
        stringBuilder.append(AccessibilityNodeInfo.idToString(l));
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void removeConnection(int n) {
        SparseArray<IAccessibilityServiceConnection> sparseArray = sConnectionCache;
        synchronized (sparseArray) {
            sConnectionCache.remove(n);
            return;
        }
    }

    @VisibleForTesting
    public static void setCache(AccessibilityCache accessibilityCache) {
        sAccessibilityCache = accessibilityCache;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean waitForResultTimedLocked(int n) {
        long l = SystemClock.uptimeMillis();
        do {
            try {
                Message message = this.getSameProcessMessageAndClear();
                if (message != null) {
                    message.getTarget().handleMessage(message);
                }
                if (this.mInteractionId == n) {
                    return true;
                }
                if (this.mInteractionId > n) {
                    return false;
                }
                long l2 = 5000L - (SystemClock.uptimeMillis() - l);
                if (l2 <= 0L) {
                    return false;
                }
                this.mInstanceLock.wait(l2);
            }
            catch (InterruptedException interruptedException) {
            }
        } while (true);
    }

    @UnsupportedAppUsage
    public void clearCache() {
        sAccessibilityCache.clear();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AccessibilityNodeInfo findAccessibilityNodeInfoByAccessibilityId(int n, int n2, long l, boolean bl, int n3, Bundle object) {
        block15 : {
            if ((n3 & 2) != 0) {
                if ((n3 & 1) == 0) throw new IllegalArgumentException("FLAG_PREFETCH_SIBLINGS requires FLAG_PREFETCH_PREDECESSORS");
            }
            arrstring = AccessibilityInteractionClient.getConnection(n);
            if (arrstring == null) return null;
            if (bl) ** GOTO lbl16
            object2 = AccessibilityInteractionClient.sAccessibilityCache;
            {
                catch (RemoteException remoteException) {
                }
            }
            object2 = object2.getNode(n2, l);
            if (object2 != null) {
                return object2;
            }
lbl16: // 3 sources:
            n4 = this.mInteractionIdCounter.getAndIncrement();
            l2 = Binder.clearCallingIdentity();
            arrstring = arrstring.findAccessibilityNodeInfoByAccessibilityId(n2, l, n4, this, n3, Thread.currentThread().getId(), (Bundle)object);
            Binder.restoreCallingIdentity(l2);
            if (arrstring == null) return null;
            object = this.getFindAccessibilityNodeInfosResultAndClear(n4);
            try {
                this.finalizeAndCacheAccessibilityNodeInfos(object, n, bl, arrstring);
                if (object == null) return null;
                if (object.isEmpty() != false) return null;
                n = 1;
                while (n < object.size()) {
                    object.get(n).recycle();
                    ++n;
                }
                return object.get(0);
                catch (Throwable throwable) {
                    Binder.restoreCallingIdentity(l2);
                    throw throwable;
                }
            }
            catch (RemoteException remoteException) {}
            break block15;
            catch (RemoteException remoteException) {}
            break block15;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.e("AccessibilityInteractionClient", "Error while calling remote findAccessibilityNodeInfoByAccessibilityId", (Throwable)var7_11);
        return null;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(int n, int n2, long l, String object) {
        void var5_8;
        block7 : {
            String[] arrstring = AccessibilityInteractionClient.getConnection(n);
            if (arrstring == null) return Collections.emptyList();
            int n3 = this.mInteractionIdCounter.getAndIncrement();
            long l2 = Binder.clearCallingIdentity();
            arrstring = arrstring.findAccessibilityNodeInfosByText(n2, l, (String)((Object)object), n3, this, Thread.currentThread().getId());
            {
                catch (Throwable throwable) {
                    Binder.restoreCallingIdentity(l2);
                    throw throwable;
                }
            }
            Binder.restoreCallingIdentity(l2);
            if (arrstring == null) return Collections.emptyList();
            object = this.getFindAccessibilityNodeInfosResultAndClear(n3);
            if (object == null) return Collections.emptyList();
            try {
                this.finalizeAndCacheAccessibilityNodeInfos(object, n, false, arrstring);
                return object;
            }
            catch (RemoteException remoteException) {}
            break block7;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.w(LOG_TAG, "Error while calling remote findAccessibilityNodeInfosByViewText", (Throwable)var5_8);
        return Collections.emptyList();
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(int n, int n2, long l, String arrstring) {
        void var5_8;
        block7 : {
            List<AccessibilityNodeInfo> object = AccessibilityInteractionClient.getConnection(n);
            if (object == null) return Collections.emptyList();
            int n3 = this.mInteractionIdCounter.getAndIncrement();
            long l2 = Binder.clearCallingIdentity();
            arrstring = object.findAccessibilityNodeInfosByViewId(n2, l, (String)arrstring, n3, (IAccessibilityInteractionConnectionCallback)this, Thread.currentThread().getId());
            {
                catch (Throwable throwable) {
                    Binder.restoreCallingIdentity(l2);
                    throw throwable;
                }
            }
            Binder.restoreCallingIdentity(l2);
            if (arrstring == null) return Collections.emptyList();
            object = this.getFindAccessibilityNodeInfosResultAndClear(n3);
            if (object == null) return Collections.emptyList();
            try {
                this.finalizeAndCacheAccessibilityNodeInfos(object, n, false, arrstring);
                return object;
            }
            catch (RemoteException remoteException) {}
            break block7;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.w(LOG_TAG, "Error while calling remote findAccessibilityNodeInfoByViewIdInActiveWindow", (Throwable)var5_8);
        return Collections.emptyList();
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AccessibilityNodeInfo findFocus(int n, int n2, long l, int n3) {
        void var6_9;
        block7 : {
            Object object = AccessibilityInteractionClient.getConnection(n);
            if (object == null) return null;
            int n4 = this.mInteractionIdCounter.getAndIncrement();
            long l2 = Binder.clearCallingIdentity();
            String[] arrstring = object.findFocus(n2, l, n3, n4, this, Thread.currentThread().getId());
            {
                catch (Throwable throwable) {
                    Binder.restoreCallingIdentity(l2);
                    throw throwable;
                }
            }
            Binder.restoreCallingIdentity(l2);
            if (arrstring == null) return null;
            object = this.getFindAccessibilityNodeInfoResultAndClear(n4);
            try {
                this.finalizeAndCacheAccessibilityNodeInfo((AccessibilityNodeInfo)object, n, false, arrstring);
                return object;
            }
            catch (RemoteException remoteException) {}
            break block7;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.w(LOG_TAG, "Error while calling remote findFocus", (Throwable)var6_9);
        return null;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AccessibilityNodeInfo focusSearch(int n, int n2, long l, int n3) {
        void var6_9;
        block7 : {
            Object object = AccessibilityInteractionClient.getConnection(n);
            if (object == null) return null;
            int n4 = this.mInteractionIdCounter.getAndIncrement();
            long l2 = Binder.clearCallingIdentity();
            String[] arrstring = object.focusSearch(n2, l, n3, n4, this, Thread.currentThread().getId());
            {
                catch (Throwable throwable) {
                    Binder.restoreCallingIdentity(l2);
                    throw throwable;
                }
            }
            Binder.restoreCallingIdentity(l2);
            if (arrstring == null) return null;
            object = this.getFindAccessibilityNodeInfoResultAndClear(n4);
            try {
                this.finalizeAndCacheAccessibilityNodeInfo((AccessibilityNodeInfo)object, n, false, arrstring);
                return object;
            }
            catch (RemoteException remoteException) {}
            break block7;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.w(LOG_TAG, "Error while calling remote accessibilityFocusSearch", (Throwable)var6_9);
        return null;
    }

    public AccessibilityNodeInfo getRootInActiveWindow(int n) {
        return this.findAccessibilityNodeInfoByAccessibilityId(n, Integer.MAX_VALUE, AccessibilityNodeInfo.ROOT_NODE_ID, false, 4, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AccessibilityWindowInfo getWindow(int n, int n2) {
        Object object = AccessibilityInteractionClient.getConnection(n);
        if (object == null) return null;
        AccessibilityWindowInfo accessibilityWindowInfo = sAccessibilityCache.getWindow(n2);
        if (accessibilityWindowInfo != null) {
            return accessibilityWindowInfo;
        }
        long l = Binder.clearCallingIdentity();
        object = object.getWindow(n2);
        {
            catch (Throwable throwable) {
                Binder.restoreCallingIdentity(l);
                throw throwable;
            }
        }
        try {
            Binder.restoreCallingIdentity(l);
            if (object == null) return null;
            sAccessibilityCache.addWindow((AccessibilityWindowInfo)object);
            return object;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while calling remote getWindow", remoteException);
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<AccessibilityWindowInfo> getWindows(int n) {
        IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(n);
        if (iAccessibilityServiceConnection == null) return Collections.emptyList();
        List<AccessibilityWindowInfo> list = sAccessibilityCache.getWindows();
        if (list != null) {
            return list;
        }
        long l = Binder.clearCallingIdentity();
        list = iAccessibilityServiceConnection.getWindows();
        {
            catch (Throwable throwable) {
                Binder.restoreCallingIdentity(l);
                throw throwable;
            }
        }
        try {
            Binder.restoreCallingIdentity(l);
            if (list == null) return Collections.emptyList();
            sAccessibilityCache.setWindows(list);
            return list;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while calling remote getWindows", remoteException);
        }
        return Collections.emptyList();
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        sAccessibilityCache.onAccessibilityEvent(accessibilityEvent);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean performAccessibilityAction(int n, int n2, long l, int n3, Bundle bundle) {
        IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(n);
        if (iAccessibilityServiceConnection == null) return false;
        n = this.mInteractionIdCounter.getAndIncrement();
        long l2 = Binder.clearCallingIdentity();
        boolean bl = iAccessibilityServiceConnection.performAccessibilityAction(n2, l, n3, bundle, n, this, Thread.currentThread().getId());
        {
            catch (Throwable throwable) {
                Binder.restoreCallingIdentity(l2);
                throw throwable;
            }
        }
        try {
            Binder.restoreCallingIdentity(l2);
            if (!bl) return false;
            return this.getPerformAccessibilityActionResultAndClear(n);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "Error while calling remote performAccessibilityAction", remoteException);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (n > this.mInteractionId) {
                this.mFindAccessibilityNodeInfoResult = accessibilityNodeInfo;
                this.mInteractionId = n;
            }
            this.mInstanceLock.notifyAll();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (n > this.mInteractionId) {
                if (list != null) {
                    boolean bl = Binder.getCallingPid() != Process.myPid();
                    if (!bl) {
                        ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<AccessibilityNodeInfo>(list);
                        this.mFindAccessibilityNodeInfosResult = arrayList;
                    } else {
                        this.mFindAccessibilityNodeInfosResult = list;
                    }
                } else {
                    this.mFindAccessibilityNodeInfosResult = Collections.emptyList();
                }
                this.mInteractionId = n;
            }
            this.mInstanceLock.notifyAll();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setPerformAccessibilityActionResult(boolean bl, int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (n > this.mInteractionId) {
                this.mPerformAccessibilityActionResult = bl;
                this.mInteractionId = n;
            }
            this.mInstanceLock.notifyAll();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setSameThreadMessage(Message message) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.mSameThreadMessage = message;
            this.mInstanceLock.notifyAll();
            return;
        }
    }
}

