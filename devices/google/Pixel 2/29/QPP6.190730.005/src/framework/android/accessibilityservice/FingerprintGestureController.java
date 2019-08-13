/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.IAccessibilityServiceConnection;
import android.accessibilityservice._$$Lambda$FingerprintGestureController$BQjrQQom4K3C98FNiI0fi7SvHfY;
import android.accessibilityservice._$$Lambda$FingerprintGestureController$M_ZApqp96G6ZF2WdWrGDJ8Qsfck;
import android.os.Handler;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;

public final class FingerprintGestureController {
    public static final int FINGERPRINT_GESTURE_SWIPE_DOWN = 8;
    public static final int FINGERPRINT_GESTURE_SWIPE_LEFT = 2;
    public static final int FINGERPRINT_GESTURE_SWIPE_RIGHT = 1;
    public static final int FINGERPRINT_GESTURE_SWIPE_UP = 4;
    private static final String LOG_TAG = "FingerprintGestureController";
    private final IAccessibilityServiceConnection mAccessibilityServiceConnection;
    private final ArrayMap<FingerprintGestureCallback, Handler> mCallbackHandlerMap = new ArrayMap(1);
    private final Object mLock = new Object();

    @VisibleForTesting
    public FingerprintGestureController(IAccessibilityServiceConnection iAccessibilityServiceConnection) {
        this.mAccessibilityServiceConnection = iAccessibilityServiceConnection;
    }

    static /* synthetic */ void lambda$onGesture$1(FingerprintGestureCallback fingerprintGestureCallback, int n) {
        fingerprintGestureCallback.onGestureDetected(n);
    }

    static /* synthetic */ void lambda$onGestureDetectionActiveChanged$0(FingerprintGestureCallback fingerprintGestureCallback, boolean bl) {
        fingerprintGestureCallback.onGestureDetectionAvailabilityChanged(bl);
    }

    public boolean isGestureDetectionAvailable() {
        try {
            boolean bl = this.mAccessibilityServiceConnection.isFingerprintGestureDetectionAvailable();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "Failed to check if fingerprint gestures are active", remoteException);
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onGesture(int n) {
        ArrayMap<FingerprintGestureCallback, Handler> arrayMap;
        Object object = this.mLock;
        synchronized (object) {
            arrayMap = new ArrayMap<FingerprintGestureCallback, Handler>(this.mCallbackHandlerMap);
        }
        int n2 = arrayMap.size();
        int n3 = 0;
        while (n3 < n2) {
            object = arrayMap.keyAt(n3);
            Handler handler = arrayMap.valueAt(n3);
            if (handler != null) {
                handler.post(new _$$Lambda$FingerprintGestureController$BQjrQQom4K3C98FNiI0fi7SvHfY((FingerprintGestureCallback)object, n));
            } else {
                ((FingerprintGestureCallback)object).onGestureDetected(n);
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onGestureDetectionActiveChanged(boolean bl) {
        ArrayMap<FingerprintGestureCallback, Handler> arrayMap;
        Object object = this.mLock;
        synchronized (object) {
            arrayMap = new ArrayMap<FingerprintGestureCallback, Handler>(this.mCallbackHandlerMap);
        }
        int n = arrayMap.size();
        int n2 = 0;
        while (n2 < n) {
            object = arrayMap.keyAt(n2);
            Handler handler = arrayMap.valueAt(n2);
            if (handler != null) {
                handler.post(new _$$Lambda$FingerprintGestureController$M_ZApqp96G6ZF2WdWrGDJ8Qsfck((FingerprintGestureCallback)object, bl));
            } else {
                ((FingerprintGestureCallback)object).onGestureDetectionAvailabilityChanged(bl);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerFingerprintGestureCallback(FingerprintGestureCallback fingerprintGestureCallback, Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCallbackHandlerMap.put(fingerprintGestureCallback, handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterFingerprintGestureCallback(FingerprintGestureCallback fingerprintGestureCallback) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCallbackHandlerMap.remove(fingerprintGestureCallback);
            return;
        }
    }

    public static abstract class FingerprintGestureCallback {
        public void onGestureDetected(int n) {
        }

        public void onGestureDetectionAvailabilityChanged(boolean bl) {
        }
    }

}

