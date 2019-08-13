/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.hardware.display.AmbientBrightnessDayStats;
import android.hardware.display.BrightnessChangeEvent;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.Curve;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.hardware.display.IDisplayManagerCallback;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.WifiDisplayStatus;
import android.media.projection.IMediaProjection;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.DisplayInfo;
import android.view.Surface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DisplayManagerGlobal {
    private static final boolean DEBUG = false;
    public static final int EVENT_DISPLAY_ADDED = 1;
    public static final int EVENT_DISPLAY_CHANGED = 2;
    public static final int EVENT_DISPLAY_REMOVED = 3;
    private static final String TAG = "DisplayManager";
    private static final boolean USE_CACHE = false;
    @UnsupportedAppUsage
    private static DisplayManagerGlobal sInstance;
    private DisplayManagerCallback mCallback;
    private int[] mDisplayIdCache;
    private final SparseArray<DisplayInfo> mDisplayInfoCache = new SparseArray();
    private final ArrayList<DisplayListenerDelegate> mDisplayListeners = new ArrayList();
    @UnsupportedAppUsage
    private final IDisplayManager mDm;
    private final Object mLock = new Object();
    private final ColorSpace mWideColorSpace;
    private int mWifiDisplayScanNestCount;

    private DisplayManagerGlobal(IDisplayManager iDisplayManager) {
        this.mDm = iDisplayManager;
        try {
            this.mWideColorSpace = ColorSpace.get(ColorSpace.Named.values()[this.mDm.getPreferredWideGamutColorSpaceId()]);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private int findDisplayListenerLocked(DisplayManager.DisplayListener displayListener) {
        int n = this.mDisplayListeners.size();
        for (int i = 0; i < n; ++i) {
            if (this.mDisplayListeners.get((int)i).mListener != displayListener) continue;
            return i;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static DisplayManagerGlobal getInstance() {
        synchronized (DisplayManagerGlobal.class) {
            DisplayManagerGlobal displayManagerGlobal;
            if (sInstance != null) return sInstance;
            IBinder iBinder = ServiceManager.getService("display");
            if (iBinder == null) return sInstance;
            sInstance = displayManagerGlobal = new DisplayManagerGlobal(IDisplayManager.Stub.asInterface(iBinder));
            return sInstance;
        }
    }

    private static Looper getLooperForHandler(Handler object) {
        object = object != null ? ((Handler)object).getLooper() : Looper.myLooper();
        Object object2 = object;
        if (object == null) {
            object2 = Looper.getMainLooper();
        }
        if (object2 != null) {
            return object2;
        }
        throw new RuntimeException("Could not get Looper for the UI thread.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleDisplayEvent(int n, int n2) {
        Object object = this.mLock;
        synchronized (object) {
            int n3 = this.mDisplayListeners.size();
            int n4 = 0;
            while (n4 < n3) {
                this.mDisplayListeners.get(n4).sendDisplayEvent(n, n2);
                ++n4;
            }
            return;
        }
    }

    private void registerCallbackIfNeededLocked() {
        if (this.mCallback == null) {
            this.mCallback = new DisplayManagerCallback();
            try {
                this.mDm.registerCallback(this.mCallback);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void connectWifiDisplay(String string2) {
        if (string2 != null) {
            try {
                this.mDm.connectWifiDisplay(string2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("deviceAddress must not be null");
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public VirtualDisplay createVirtualDisplay(Context object, MediaProjection object2, String string2, int n, int n2, int n3, Surface surface, int n4, VirtualDisplay.Callback object3, Handler object4, String string3) {
        void var1_5;
        block6 : {
            if (TextUtils.isEmpty(string2)) throw new IllegalArgumentException("name must be non-null and non-empty");
            if (n <= 0) throw new IllegalArgumentException("width, height, and densityDpi must be greater than 0");
            if (n2 <= 0) throw new IllegalArgumentException("width, height, and densityDpi must be greater than 0");
            if (n3 <= 0) throw new IllegalArgumentException("width, height, and densityDpi must be greater than 0");
            object3 = new VirtualDisplayCallback((VirtualDisplay.Callback)object3, (Handler)object4);
            object2 = object2 != null ? ((MediaProjection)object2).getProjection() : null;
            object4 = this.mDm;
            object = ((Context)object).getPackageName();
            try {
                n = object4.createVirtualDisplay((IVirtualDisplayCallback)object3, (IMediaProjection)object2, (String)object, string2, n, n2, n3, surface, n4, string3);
                if (n >= 0) break block6;
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not create virtual display: ");
            }
            catch (RemoteException remoteException) {
                throw var1_5.rethrowFromSystemServer();
            }
            ((StringBuilder)object).append(string2);
            Log.e(TAG, ((StringBuilder)object).toString());
            return null;
        }
        object = this.getRealDisplay(n);
        if (object != null) return new VirtualDisplay(this, (Display)object, (IVirtualDisplayCallback)object3, surface);
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not obtain display info for newly created virtual display: ");
        ((StringBuilder)object).append(string2);
        Log.wtf(TAG, ((StringBuilder)object).toString());
        this.mDm.releaseVirtualDisplay((IVirtualDisplayCallback)object3);
        return null;
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var1_5.rethrowFromSystemServer();
    }

    @UnsupportedAppUsage
    public void disconnectWifiDisplay() {
        try {
            this.mDm.disconnectWifiDisplay();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void forgetWifiDisplay(String string2) {
        if (string2 != null) {
            try {
                this.mDm.forgetWifiDisplay(string2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("deviceAddress must not be null");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<AmbientBrightnessDayStats> getAmbientBrightnessStats() {
        try {
            ParceledListSlice parceledListSlice = this.mDm.getAmbientBrightnessStats();
            if (parceledListSlice != null) return parceledListSlice.getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    public BrightnessConfiguration getBrightnessConfigurationForUser(int n) {
        try {
            BrightnessConfiguration brightnessConfiguration = this.mDm.getBrightnessConfigurationForUser(n);
            return brightnessConfiguration;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<BrightnessChangeEvent> getBrightnessEvents(String object) {
        try {
            object = this.mDm.getBrightnessEvents((String)object);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    public Display getCompatibleDisplay(int n, Resources resources) {
        DisplayInfo displayInfo = this.getDisplayInfo(n);
        if (displayInfo == null) {
            return null;
        }
        return new Display(this, n, displayInfo, resources);
    }

    public Display getCompatibleDisplay(int n, DisplayAdjustments displayAdjustments) {
        DisplayInfo displayInfo = this.getDisplayInfo(n);
        if (displayInfo == null) {
            return null;
        }
        return new Display(this, n, displayInfo, displayAdjustments);
    }

    public BrightnessConfiguration getDefaultBrightnessConfiguration() {
        try {
            BrightnessConfiguration brightnessConfiguration = this.mDm.getDefaultBrightnessConfiguration();
            return brightnessConfiguration;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public int[] getDisplayIds() {
        try {
            Object object = this.mLock;
            // MONITORENTER : object
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        int[] arrn = this.mDm.getDisplayIds();
        this.registerCallbackIfNeededLocked();
        // MONITOREXIT : object
        return arrn;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public DisplayInfo getDisplayInfo(int n) {
        try {
            Object object = this.mLock;
            // MONITORENTER : object
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        DisplayInfo displayInfo = this.mDm.getDisplayInfo(n);
        if (displayInfo == null) {
            // MONITOREXIT : object
            return null;
        }
        this.registerCallbackIfNeededLocked();
        // MONITOREXIT : object
        return displayInfo;
    }

    public Pair<float[], float[]> getMinimumBrightnessCurve() {
        try {
            Object object = this.mDm.getMinimumBrightnessCurve();
            object = Pair.create(((Curve)object).getX(), ((Curve)object).getY());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ColorSpace getPreferredWideGamutColorSpace() {
        return this.mWideColorSpace;
    }

    @UnsupportedAppUsage
    public Display getRealDisplay(int n) {
        return this.getCompatibleDisplay(n, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    public Point getStableDisplaySize() {
        try {
            Point point = this.mDm.getStableDisplaySize();
            return point;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public WifiDisplayStatus getWifiDisplayStatus() {
        try {
            WifiDisplayStatus wifiDisplayStatus = this.mDm.getWifiDisplayStatus();
            return wifiDisplayStatus;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isUidPresentOnDisplay(int n, int n2) {
        try {
            boolean bl = this.mDm.isUidPresentOnDisplay(n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void pauseWifiDisplay() {
        try {
            this.mDm.pauseWifiDisplay();
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
    public void registerDisplayListener(DisplayManager.DisplayListener displayListener, Handler object) {
        if (displayListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.findDisplayListenerLocked(displayListener) < 0) {
                Looper looper = DisplayManagerGlobal.getLooperForHandler((Handler)object);
                object = this.mDisplayListeners;
                DisplayListenerDelegate displayListenerDelegate = new DisplayListenerDelegate(displayListener, looper);
                ((ArrayList)object).add(displayListenerDelegate);
                this.registerCallbackIfNeededLocked();
            }
            return;
        }
    }

    public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) {
        try {
            this.mDm.releaseVirtualDisplay(iVirtualDisplayCallback);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void renameWifiDisplay(String string2, String string3) {
        if (string2 != null) {
            try {
                this.mDm.renameWifiDisplay(string2, string3);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("deviceAddress must not be null");
    }

    public void requestColorMode(int n, int n2) {
        try {
            this.mDm.requestColorMode(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int n, int n2, int n3) {
        try {
            this.mDm.resizeVirtualDisplay(iVirtualDisplayCallback, n, n2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resumeWifiDisplay() {
        try {
            this.mDm.resumeWifiDisplay();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int n, String string2) {
        try {
            this.mDm.setBrightnessConfigurationForUser(brightnessConfiguration, n, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        try {
            this.mDm.setTemporaryAutoBrightnessAdjustment(f);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTemporaryBrightness(int n) {
        try {
            this.mDm.setTemporaryBrightness(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    void setVirtualDisplayState(IVirtualDisplayCallback iVirtualDisplayCallback, boolean bl) {
        try {
            this.mDm.setVirtualDisplayState(iVirtualDisplayCallback, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) {
        boolean bl;
        try {
            this.mDm.setVirtualDisplaySurface(iVirtualDisplayCallback, surface);
            bl = surface != null;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        this.setVirtualDisplayState(iVirtualDisplayCallback, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startWifiDisplayScan() {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mWifiDisplayScanNestCount;
            this.mWifiDisplayScanNestCount = n + 1;
            if (n == 0) {
                this.registerCallbackIfNeededLocked();
                try {
                    this.mDm.startWifiDisplayScan();
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
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
    public void stopWifiDisplayScan() {
        Object object = this.mLock;
        synchronized (object) {
            int n;
            this.mWifiDisplayScanNestCount = n = this.mWifiDisplayScanNestCount - 1;
            if (n == 0) {
                try {
                    this.mDm.stopWifiDisplayScan();
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            } else if (this.mWifiDisplayScanNestCount < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Wifi display scan nest count became negative: ");
                stringBuilder.append(this.mWifiDisplayScanNestCount);
                Log.wtf(TAG, stringBuilder.toString());
                this.mWifiDisplayScanNestCount = 0;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterDisplayListener(DisplayManager.DisplayListener displayListener) {
        if (displayListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        Object object = this.mLock;
        synchronized (object) {
            int n = this.findDisplayListenerLocked(displayListener);
            if (n >= 0) {
                this.mDisplayListeners.get(n).clearEvents();
                this.mDisplayListeners.remove(n);
            }
            return;
        }
    }

    private static final class DisplayListenerDelegate
    extends Handler {
        public final DisplayManager.DisplayListener mListener;

        DisplayListenerDelegate(DisplayManager.DisplayListener displayListener, Looper looper) {
            super(looper, null, true);
            this.mListener = displayListener;
        }

        public void clearEvents() {
            this.removeCallbacksAndMessages(null);
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.mListener.onDisplayRemoved(message.arg1);
                    }
                } else {
                    this.mListener.onDisplayChanged(message.arg1);
                }
            } else {
                this.mListener.onDisplayAdded(message.arg1);
            }
        }

        public void sendDisplayEvent(int n, int n2) {
            this.sendMessage(this.obtainMessage(n2, n, 0));
        }
    }

    private final class DisplayManagerCallback
    extends IDisplayManagerCallback.Stub {
        private DisplayManagerCallback() {
        }

        @Override
        public void onDisplayEvent(int n, int n2) {
            DisplayManagerGlobal.this.handleDisplayEvent(n, n2);
        }
    }

    private static final class VirtualDisplayCallback
    extends IVirtualDisplayCallback.Stub {
        private VirtualDisplayCallbackDelegate mDelegate;

        public VirtualDisplayCallback(VirtualDisplay.Callback callback, Handler handler) {
            if (callback != null) {
                this.mDelegate = new VirtualDisplayCallbackDelegate(callback, handler);
            }
        }

        @Override
        public void onPaused() {
            VirtualDisplayCallbackDelegate virtualDisplayCallbackDelegate = this.mDelegate;
            if (virtualDisplayCallbackDelegate != null) {
                virtualDisplayCallbackDelegate.sendEmptyMessage(0);
            }
        }

        @Override
        public void onResumed() {
            VirtualDisplayCallbackDelegate virtualDisplayCallbackDelegate = this.mDelegate;
            if (virtualDisplayCallbackDelegate != null) {
                virtualDisplayCallbackDelegate.sendEmptyMessage(1);
            }
        }

        @Override
        public void onStopped() {
            VirtualDisplayCallbackDelegate virtualDisplayCallbackDelegate = this.mDelegate;
            if (virtualDisplayCallbackDelegate != null) {
                virtualDisplayCallbackDelegate.sendEmptyMessage(2);
            }
        }
    }

    private static final class VirtualDisplayCallbackDelegate
    extends Handler {
        public static final int MSG_DISPLAY_PAUSED = 0;
        public static final int MSG_DISPLAY_RESUMED = 1;
        public static final int MSG_DISPLAY_STOPPED = 2;
        private final VirtualDisplay.Callback mCallback;

        public VirtualDisplayCallbackDelegate(VirtualDisplay.Callback callback, Handler object) {
            object = object != null ? ((Handler)object).getLooper() : Looper.myLooper();
            super((Looper)object, null, true);
            this.mCallback = callback;
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        this.mCallback.onStopped();
                    }
                } else {
                    this.mCallback.onResumed();
                }
            } else {
                this.mCallback.onPaused();
            }
        }
    }

}

