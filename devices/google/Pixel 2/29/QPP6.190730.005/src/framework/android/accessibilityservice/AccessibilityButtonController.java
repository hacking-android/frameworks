/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.IAccessibilityServiceConnection;
import android.accessibilityservice._$$Lambda$AccessibilityButtonController$RskKrfcSyUz7I9Sqaziy1P990ZM;
import android.accessibilityservice._$$Lambda$AccessibilityButtonController$b_UAM9QJWcH4KQOC_odiN0t_boU;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.Preconditions;

public final class AccessibilityButtonController {
    private static final String LOG_TAG = "A11yButtonController";
    private ArrayMap<AccessibilityButtonCallback, Handler> mCallbacks;
    private final Object mLock;
    private final IAccessibilityServiceConnection mServiceConnection;

    AccessibilityButtonController(IAccessibilityServiceConnection iAccessibilityServiceConnection) {
        this.mServiceConnection = iAccessibilityServiceConnection;
        this.mLock = new Object();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dispatchAccessibilityButtonAvailabilityChanged(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCallbacks != null && !this.mCallbacks.isEmpty()) {
                ArrayMap<AccessibilityButtonCallback, Handler> arrayMap = new ArrayMap<AccessibilityButtonCallback, Handler>(this.mCallbacks);
                // MONITOREXIT [2, 5] lbl5 : MonitorExitStatement: MONITOREXIT : var2_2
                int n = 0;
                int n2 = arrayMap.size();
                do {
                    if (n >= n2) {
                        return;
                    }
                    object = arrayMap.keyAt(n);
                    arrayMap.valueAt(n).post(new _$$Lambda$AccessibilityButtonController$RskKrfcSyUz7I9Sqaziy1P990ZM(this, (AccessibilityButtonCallback)object, bl));
                    ++n;
                } while (true);
            }
            Slog.w(LOG_TAG, "Received accessibility button availability change with no callbacks!");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dispatchAccessibilityButtonClicked() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCallbacks != null && !this.mCallbacks.isEmpty()) {
                ArrayMap<AccessibilityButtonCallback, Handler> arrayMap = new ArrayMap<AccessibilityButtonCallback, Handler>(this.mCallbacks);
                // MONITOREXIT [2, 5] lbl5 : MonitorExitStatement: MONITOREXIT : var1_1
                int n = 0;
                int n2 = arrayMap.size();
                do {
                    if (n >= n2) {
                        return;
                    }
                    object = arrayMap.keyAt(n);
                    arrayMap.valueAt(n).post(new _$$Lambda$AccessibilityButtonController$b_UAM9QJWcH4KQOC_odiN0t_boU(this, (AccessibilityButtonCallback)object));
                    ++n;
                } while (true);
            }
            Slog.w(LOG_TAG, "Received accessibility button click with no callbacks!");
            return;
        }
    }

    public boolean isAccessibilityButtonAvailable() {
        IAccessibilityServiceConnection iAccessibilityServiceConnection = this.mServiceConnection;
        if (iAccessibilityServiceConnection != null) {
            try {
                boolean bl = iAccessibilityServiceConnection.isAccessibilityButtonAvailable();
                return bl;
            }
            catch (RemoteException remoteException) {
                Slog.w(LOG_TAG, "Failed to get accessibility button availability.", remoteException);
                remoteException.rethrowFromSystemServer();
                return false;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$dispatchAccessibilityButtonAvailabilityChanged$1$AccessibilityButtonController(AccessibilityButtonCallback accessibilityButtonCallback, boolean bl) {
        accessibilityButtonCallback.onAvailabilityChanged(this, bl);
    }

    public /* synthetic */ void lambda$dispatchAccessibilityButtonClicked$0$AccessibilityButtonController(AccessibilityButtonCallback accessibilityButtonCallback) {
        accessibilityButtonCallback.onClicked(this);
    }

    public void registerAccessibilityButtonCallback(AccessibilityButtonCallback accessibilityButtonCallback) {
        this.registerAccessibilityButtonCallback(accessibilityButtonCallback, new Handler(Looper.getMainLooper()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerAccessibilityButtonCallback(AccessibilityButtonCallback accessibilityButtonCallback, Handler handler) {
        Preconditions.checkNotNull(accessibilityButtonCallback);
        Preconditions.checkNotNull(handler);
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCallbacks == null) {
                ArrayMap arrayMap = new ArrayMap();
                this.mCallbacks = arrayMap;
            }
            this.mCallbacks.put(accessibilityButtonCallback, handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterAccessibilityButtonCallback(AccessibilityButtonCallback accessibilityButtonCallback) {
        Preconditions.checkNotNull(accessibilityButtonCallback);
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCallbacks == null) {
                return;
            }
            int n = this.mCallbacks.indexOfKey(accessibilityButtonCallback);
            if (n < 0) return;
            boolean bl = true;
            if (!bl) return;
            this.mCallbacks.removeAt(n);
            return;
        }
    }

    public static abstract class AccessibilityButtonCallback {
        public void onAvailabilityChanged(AccessibilityButtonController accessibilityButtonController, boolean bl) {
        }

        public void onClicked(AccessibilityButtonController accessibilityButtonController) {
        }
    }

}

