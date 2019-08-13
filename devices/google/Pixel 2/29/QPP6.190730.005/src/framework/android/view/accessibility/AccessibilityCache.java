/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArray;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityCache {
    public static final int CACHE_CRITICAL_EVENTS_MASK = 4307005;
    private static final boolean CHECK_INTEGRITY = Build.IS_ENG;
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "AccessibilityCache";
    private long mAccessibilityFocus = Integer.MAX_VALUE;
    private final AccessibilityNodeRefresher mAccessibilityNodeRefresher;
    private long mInputFocus = Integer.MAX_VALUE;
    private boolean mIsAllWindowsCached;
    private final Object mLock = new Object();
    private final SparseArray<LongSparseArray<AccessibilityNodeInfo>> mNodeCache = new SparseArray();
    private final SparseArray<AccessibilityWindowInfo> mTempWindowArray = new SparseArray();
    private final SparseArray<AccessibilityWindowInfo> mWindowCache = new SparseArray();

    public AccessibilityCache(AccessibilityNodeRefresher accessibilityNodeRefresher) {
        this.mAccessibilityNodeRefresher = accessibilityNodeRefresher;
    }

    private void clearNodesForWindowLocked(int n) {
        if (this.mNodeCache.get(n) == null) {
            return;
        }
        this.mNodeCache.remove(n);
    }

    private void clearSubTreeLocked(int n, long l) {
        LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(n);
        if (longSparseArray != null) {
            this.clearSubTreeRecursiveLocked(longSparseArray, l);
        }
    }

    private boolean clearSubTreeRecursiveLocked(LongSparseArray<AccessibilityNodeInfo> longSparseArray, long l) {
        AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(l);
        if (accessibilityNodeInfo == null) {
            this.clear();
            return true;
        }
        longSparseArray.remove(l);
        int n = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < n; ++i) {
            if (!this.clearSubTreeRecursiveLocked(longSparseArray, accessibilityNodeInfo.getChildId(i))) continue;
            return true;
        }
        return false;
    }

    private void clearWindowCache() {
        this.mWindowCache.clear();
        this.mIsAllWindowsCached = false;
    }

    private void refreshCachedNodeLocked(int n, long l) {
        LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(n);
        if (longSparseArray == null) {
            return;
        }
        if ((longSparseArray = longSparseArray.get(l)) == null) {
            return;
        }
        if (this.mAccessibilityNodeRefresher.refreshNode((AccessibilityNodeInfo)((Object)longSparseArray), true)) {
            return;
        }
        this.clearSubTreeLocked(n, l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void add(AccessibilityNodeInfo accessibilityNodeInfo) {
        Object object = this.mLock;
        synchronized (object) {
            AccessibilityNodeInfo accessibilityNodeInfo2;
            long l;
            int n = accessibilityNodeInfo.getWindowId();
            Object object2 = this.mNodeCache.get(n);
            LongSparseArray<Object> longSparseArray = object2;
            if (object2 == null) {
                longSparseArray = new LongSparseArray<Object>();
                this.mNodeCache.put(n, longSparseArray);
            }
            if ((accessibilityNodeInfo2 = (AccessibilityNodeInfo)longSparseArray.get(l = accessibilityNodeInfo.getSourceNodeId())) != null) {
                long l2;
                object2 = accessibilityNodeInfo.getChildNodeIds();
                int n2 = accessibilityNodeInfo2.getChildCount();
                for (int i = 0; i < n2; ++i) {
                    l2 = accessibilityNodeInfo2.getChildId(i);
                    if (object2 == null || ((LongArray)object2).indexOf(l2) < 0) {
                        this.clearSubTreeLocked(n, l2);
                    }
                    if (longSparseArray.get(l) != null) continue;
                    this.clearNodesForWindowLocked(n);
                    return;
                }
                l2 = accessibilityNodeInfo2.getParentNodeId();
                if (accessibilityNodeInfo.getParentNodeId() != l2) {
                    this.clearSubTreeLocked(n, l2);
                }
            }
            object2 = new AccessibilityNodeInfo(accessibilityNodeInfo);
            longSparseArray.put(l, object2);
            if (((AccessibilityNodeInfo)object2).isAccessibilityFocused()) {
                if (this.mAccessibilityFocus != Integer.MAX_VALUE && this.mAccessibilityFocus != l) {
                    this.refreshCachedNodeLocked(n, this.mAccessibilityFocus);
                }
                this.mAccessibilityFocus = l;
            } else if (this.mAccessibilityFocus == l) {
                this.mAccessibilityFocus = Integer.MAX_VALUE;
            }
            if (((AccessibilityNodeInfo)object2).isFocused()) {
                this.mInputFocus = l;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addWindow(AccessibilityWindowInfo accessibilityWindowInfo) {
        Object object = this.mLock;
        synchronized (object) {
            int n = accessibilityWindowInfo.getId();
            SparseArray<AccessibilityWindowInfo> sparseArray = this.mWindowCache;
            AccessibilityWindowInfo accessibilityWindowInfo2 = new AccessibilityWindowInfo(accessibilityWindowInfo);
            sparseArray.put(n, accessibilityWindowInfo2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void checkIntegrity() {
        Object object = this;
        Object object2 = ((AccessibilityCache)object).mLock;
        synchronized (object2) {
            int n;
            Object object3;
            Object object4;
            if (((AccessibilityCache)object).mWindowCache.size() <= 0 && ((AccessibilityCache)object).mNodeCache.size() == 0) {
                return;
            }
            StringBuilder stringBuilder = null;
            Object object5 = null;
            int n2 = ((AccessibilityCache)object).mWindowCache.size();
            for (n = 0; n < n2; ++n) {
                object3 = ((AccessibilityCache)object).mWindowCache.valueAt(n);
                object4 = object5;
                if (((AccessibilityWindowInfo)object3).isActive()) {
                    if (object5 != null) {
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("Duplicate active window:");
                        ((StringBuilder)object4).append(object3);
                        Log.e(LOG_TAG, ((StringBuilder)object4).toString());
                        object4 = object5;
                    } else {
                        object4 = object3;
                    }
                }
                object5 = stringBuilder;
                if (((AccessibilityWindowInfo)object3).isFocused()) {
                    if (stringBuilder != null) {
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append("Duplicate focused window:");
                        ((StringBuilder)object5).append(object3);
                        Log.e(LOG_TAG, ((StringBuilder)object5).toString());
                        object5 = stringBuilder;
                    } else {
                        object5 = object3;
                    }
                }
                stringBuilder = object5;
                object5 = object4;
            }
            object4 = null;
            object3 = null;
            int n3 = ((AccessibilityCache)object).mNodeCache.size();
            int n4 = 0;
            n = n2;
            do {
                object = this;
                if (n4 >= n3) {
                    return;
                }
                LongSparseArray<AccessibilityNodeInfo> longSparseArray = ((AccessibilityCache)object).mNodeCache.valueAt(n4);
                if (longSparseArray.size() > 0) {
                    ArraySet<AccessibilityNodeInfo> arraySet = new ArraySet<AccessibilityNodeInfo>();
                    int n5 = ((AccessibilityCache)object).mNodeCache.keyAt(n4);
                    int n6 = longSparseArray.size();
                    for (n2 = 0; n2 < n6; ++n2) {
                        int n7;
                        AccessibilityNodeInfo accessibilityNodeInfo;
                        int n8;
                        AccessibilityNodeInfo accessibilityNodeInfo2 = longSparseArray.valueAt(n2);
                        if (!arraySet.add(accessibilityNodeInfo2)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Duplicate node: ");
                            ((StringBuilder)object).append(accessibilityNodeInfo2);
                            ((StringBuilder)object).append(" in window:");
                            ((StringBuilder)object).append(n5);
                            Log.e(LOG_TAG, ((StringBuilder)object).toString());
                            continue;
                        }
                        object = object4;
                        if (accessibilityNodeInfo2.isAccessibilityFocused()) {
                            if (object4 != null) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Duplicate accessibility focus:");
                                ((StringBuilder)object).append(accessibilityNodeInfo2);
                                ((StringBuilder)object).append(" in window:");
                                ((StringBuilder)object).append(n5);
                                Log.e(LOG_TAG, ((StringBuilder)object).toString());
                                object = object4;
                            } else {
                                object = accessibilityNodeInfo2;
                            }
                        }
                        object4 = object3;
                        if (accessibilityNodeInfo2.isFocused()) {
                            if (object3 != null) {
                                object4 = new StringBuilder();
                                ((StringBuilder)object4).append("Duplicate input focus: ");
                                ((StringBuilder)object4).append(accessibilityNodeInfo2);
                                ((StringBuilder)object4).append(" in window:");
                                ((StringBuilder)object4).append(n5);
                                Log.e(LOG_TAG, ((StringBuilder)object4).toString());
                                object4 = object3;
                            } else {
                                object4 = accessibilityNodeInfo2;
                            }
                        }
                        if ((accessibilityNodeInfo = longSparseArray.get(accessibilityNodeInfo2.getParentNodeId())) != null) {
                            block28 : {
                                int n9 = accessibilityNodeInfo.getChildCount();
                                n7 = 0;
                                for (n8 = 0; n8 < n9; ++n8) {
                                    if (longSparseArray.get(accessibilityNodeInfo.getChildId(n8)) != accessibilityNodeInfo2) continue;
                                    n8 = 1;
                                    break block28;
                                }
                                n8 = n7;
                            }
                            object3 = object5;
                            n = n7 = n;
                            object5 = object3;
                            if (n8 == 0) {
                                object5 = new StringBuilder();
                                ((StringBuilder)object5).append("Invalid parent-child relation between parent: ");
                                ((StringBuilder)object5).append(accessibilityNodeInfo);
                                ((StringBuilder)object5).append(" and child: ");
                                ((StringBuilder)object5).append(accessibilityNodeInfo2);
                                Log.e(LOG_TAG, ((StringBuilder)object5).toString());
                                n = n7;
                                object5 = object3;
                            }
                        }
                        n7 = accessibilityNodeInfo2.getChildCount();
                        for (n8 = 0; n8 < n7; ++n8) {
                            object3 = longSparseArray.get(accessibilityNodeInfo2.getChildId(n8));
                            if (object3 == null || longSparseArray.get(((AccessibilityNodeInfo)object3).getParentNodeId()) == accessibilityNodeInfo2) continue;
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("Invalid child-parent relation between child: ");
                            ((StringBuilder)object3).append(accessibilityNodeInfo2);
                            ((StringBuilder)object3).append(" and parent: ");
                            ((StringBuilder)object3).append(accessibilityNodeInfo);
                            Log.e(LOG_TAG, ((StringBuilder)object3).toString());
                        }
                        object3 = object4;
                        object4 = object;
                    }
                }
                ++n4;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clear() {
        Object object = this.mLock;
        synchronized (object) {
            this.clearWindowCache();
            int n = this.mNodeCache.size() - 1;
            do {
                if (n < 0) {
                    this.mAccessibilityFocus = Integer.MAX_VALUE;
                    this.mInputFocus = Integer.MAX_VALUE;
                    return;
                }
                this.clearNodesForWindowLocked(this.mNodeCache.keyAt(n));
                --n;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AccessibilityNodeInfo getNode(int n, long l) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mNodeCache.get(n);
            if (object2 == null) {
                return null;
            }
            AccessibilityNodeInfo accessibilityNodeInfo = ((LongSparseArray)object2).get(l);
            object2 = accessibilityNodeInfo;
            if (accessibilityNodeInfo == null) return object2;
            return new AccessibilityNodeInfo(accessibilityNodeInfo);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AccessibilityWindowInfo getWindow(int n) {
        Object object = this.mLock;
        synchronized (object) {
            AccessibilityWindowInfo accessibilityWindowInfo = this.mWindowCache.get(n);
            if (accessibilityWindowInfo == null) return null;
            return new AccessibilityWindowInfo(accessibilityWindowInfo);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<AccessibilityWindowInfo> getWindows() {
        Object object = this.mLock;
        synchronized (object) {
            int n;
            AccessibilityWindowInfo accessibilityWindowInfo;
            if (!this.mIsAllWindowsCached) {
                return null;
            }
            int n2 = this.mWindowCache.size();
            if (n2 <= 0) {
                return null;
            }
            SparseArray<AccessibilityWindowInfo> sparseArray = this.mTempWindowArray;
            sparseArray.clear();
            for (n = 0; n < n2; ++n) {
                accessibilityWindowInfo = this.mWindowCache.valueAt(n);
                sparseArray.put(accessibilityWindowInfo.getLayer(), accessibilityWindowInfo);
            }
            n = sparseArray.size();
            ArrayList<AccessibilityWindowInfo> arrayList = new ArrayList<AccessibilityWindowInfo>(n);
            --n;
            while (n >= 0) {
                AccessibilityWindowInfo accessibilityWindowInfo2 = sparseArray.valueAt(n);
                accessibilityWindowInfo = new AccessibilityWindowInfo(accessibilityWindowInfo2);
                arrayList.add(accessibilityWindowInfo);
                sparseArray.removeAt(n);
                --n;
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Object object = this.mLock;
        // MONITORENTER : object
        switch (accessibilityEvent.getEventType()) {
            default: {
                break;
            }
            case 65536: {
                if (this.mAccessibilityFocus != accessibilityEvent.getSourceNodeId()) break;
                this.refreshCachedNodeLocked(accessibilityEvent.getWindowId(), this.mAccessibilityFocus);
                this.mAccessibilityFocus = Integer.MAX_VALUE;
                break;
            }
            case 32768: {
                if (this.mAccessibilityFocus != Integer.MAX_VALUE) {
                    this.refreshCachedNodeLocked(accessibilityEvent.getWindowId(), this.mAccessibilityFocus);
                }
                this.mAccessibilityFocus = accessibilityEvent.getSourceNodeId();
                this.refreshCachedNodeLocked(accessibilityEvent.getWindowId(), this.mAccessibilityFocus);
                break;
            }
            case 4096: {
                this.clearSubTreeLocked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                break;
            }
            case 2048: {
                Object object2 = this.mLock;
                // MONITORENTER : object2
                int n = accessibilityEvent.getWindowId();
                long l = accessibilityEvent.getSourceNodeId();
                if ((accessibilityEvent.getContentChangeTypes() & 1) != 0) {
                    this.clearSubTreeLocked(n, l);
                } else {
                    this.refreshCachedNodeLocked(n, l);
                }
                // MONITOREXIT : object2
                break;
            }
            case 32: 
            case 4194304: {
                this.clear();
                break;
            }
            case 8: {
                if (this.mInputFocus != Integer.MAX_VALUE) {
                    this.refreshCachedNodeLocked(accessibilityEvent.getWindowId(), this.mInputFocus);
                }
                this.mInputFocus = accessibilityEvent.getSourceNodeId();
                this.refreshCachedNodeLocked(accessibilityEvent.getWindowId(), this.mInputFocus);
                break;
            }
            case 1: 
            case 4: 
            case 16: 
            case 8192: {
                this.refreshCachedNodeLocked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
            }
        }
        // MONITOREXIT : object
        if (!CHECK_INTEGRITY) return;
        this.checkIntegrity();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setWindows(List<AccessibilityWindowInfo> list) {
        Object object = this.mLock;
        synchronized (object) {
            this.clearWindowCache();
            if (list == null) {
                return;
            }
            int n = list.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mIsAllWindowsCached = true;
                    return;
                }
                this.addWindow(list.get(n2));
                ++n2;
            } while (true);
        }
    }

    public static class AccessibilityNodeRefresher {
        public boolean refreshNode(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            return accessibilityNodeInfo.refresh(null, bl);
        }
    }

}

