/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplayStatus;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioRoutesInfo;
import android.media.IAudioRoutesObserver;
import android.media.IAudioService;
import android.media.IMediaRouterClient;
import android.media.IMediaRouterService;
import android.media.IRemoteVolumeObserver;
import android.media.MediaRouterClientState;
import android.media.RemoteControlClient;
import android.media.VolumeProvider;
import android.media._$$Lambda$MediaRouter$Static$Client$xd63sG7fVJYYu49J1xqN8JYQvo4;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.DisplayAddress;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class MediaRouter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int AVAILABILITY_FLAG_IGNORE_DEFAULT_ROUTE = 1;
    public static final int CALLBACK_FLAG_PASSIVE_DISCOVERY = 8;
    public static final int CALLBACK_FLAG_PERFORM_ACTIVE_SCAN = 1;
    public static final int CALLBACK_FLAG_REQUEST_DISCOVERY = 4;
    public static final int CALLBACK_FLAG_UNFILTERED_EVENTS = 2;
    private static final boolean DEBUG = Log.isLoggable("MediaRouter", 3);
    public static final String MIRRORING_GROUP_ID = "android.media.mirroring_group";
    static final int ROUTE_TYPE_ANY = 8388615;
    public static final int ROUTE_TYPE_LIVE_AUDIO = 1;
    public static final int ROUTE_TYPE_LIVE_VIDEO = 2;
    public static final int ROUTE_TYPE_REMOTE_DISPLAY = 4;
    public static final int ROUTE_TYPE_USER = 8388608;
    private static final String TAG = "MediaRouter";
    static final HashMap<Context, MediaRouter> sRouters = new HashMap();
    static Static sStatic;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MediaRouter(Context object) {
        synchronized (Static.class) {
            if (sStatic == null) {
                Context context = ((Context)object).getApplicationContext();
                sStatic = object = new Static(context);
                sStatic.startMonitoringRoutes(context);
            }
            return;
        }
    }

    static void addRouteStatic(RouteInfo routeInfo) {
        Object object;
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Adding route: ");
            ((StringBuilder)object).append(routeInfo);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if (!MediaRouter.sStatic.mCategories.contains(object = routeInfo.getCategory())) {
            MediaRouter.sStatic.mCategories.add((RouteCategory)object);
        }
        if (((RouteCategory)object).isGroupable() && !(routeInfo instanceof RouteGroup)) {
            object = new RouteGroup(routeInfo.getCategory());
            ((RouteGroup)object).mSupportedTypes = routeInfo.mSupportedTypes;
            MediaRouter.sStatic.mRoutes.add((RouteInfo)object);
            MediaRouter.dispatchRouteAdded((RouteInfo)object);
            ((RouteGroup)object).addRoute(routeInfo);
        } else {
            MediaRouter.sStatic.mRoutes.add(routeInfo);
            MediaRouter.dispatchRouteAdded(routeInfo);
        }
    }

    static void dispatchRouteAdded(RouteInfo routeInfo) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeInfo)) continue;
            callbackInfo.cb.onRouteAdded(callbackInfo.router, routeInfo);
        }
    }

    static void dispatchRouteChanged(RouteInfo routeInfo) {
        MediaRouter.dispatchRouteChanged(routeInfo, routeInfo.mSupportedTypes);
    }

    static void dispatchRouteChanged(RouteInfo routeInfo, int n) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Dispatching route change: ");
            stringBuilder.append(routeInfo);
            Log.d(TAG, stringBuilder.toString());
        }
        int n2 = routeInfo.mSupportedTypes;
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            boolean bl = callbackInfo.filterRouteEvent(n);
            boolean bl2 = callbackInfo.filterRouteEvent(n2);
            if (!bl && bl2) {
                callbackInfo.cb.onRouteAdded(callbackInfo.router, routeInfo);
                if (routeInfo.isSelected()) {
                    callbackInfo.cb.onRouteSelected(callbackInfo.router, n2, routeInfo);
                }
            }
            if (bl || bl2) {
                callbackInfo.cb.onRouteChanged(callbackInfo.router, routeInfo);
            }
            if (!bl || bl2) continue;
            if (routeInfo.isSelected()) {
                callbackInfo.cb.onRouteUnselected(callbackInfo.router, n, routeInfo);
            }
            callbackInfo.cb.onRouteRemoved(callbackInfo.router, routeInfo);
        }
    }

    static void dispatchRouteGrouped(RouteInfo routeInfo, RouteGroup routeGroup, int n) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeGroup)) continue;
            callbackInfo.cb.onRouteGrouped(callbackInfo.router, routeInfo, routeGroup, n);
        }
    }

    static void dispatchRoutePresentationDisplayChanged(RouteInfo routeInfo) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeInfo)) continue;
            callbackInfo.cb.onRoutePresentationDisplayChanged(callbackInfo.router, routeInfo);
        }
    }

    static void dispatchRouteRemoved(RouteInfo routeInfo) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeInfo)) continue;
            callbackInfo.cb.onRouteRemoved(callbackInfo.router, routeInfo);
        }
    }

    static void dispatchRouteSelected(int n, RouteInfo routeInfo) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeInfo)) continue;
            callbackInfo.cb.onRouteSelected(callbackInfo.router, n, routeInfo);
        }
    }

    static void dispatchRouteUngrouped(RouteInfo routeInfo, RouteGroup routeGroup) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeGroup)) continue;
            callbackInfo.cb.onRouteUngrouped(callbackInfo.router, routeInfo, routeGroup);
        }
    }

    static void dispatchRouteUnselected(int n, RouteInfo routeInfo) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeInfo)) continue;
            callbackInfo.cb.onRouteUnselected(callbackInfo.router, n, routeInfo);
        }
    }

    static void dispatchRouteVolumeChanged(RouteInfo routeInfo) {
        for (CallbackInfo callbackInfo : MediaRouter.sStatic.mCallbacks) {
            if (!callbackInfo.filterRouteEvent(routeInfo)) continue;
            callbackInfo.cb.onRouteVolumeChanged(callbackInfo.router, routeInfo);
        }
    }

    private int findCallbackInfo(Callback callback) {
        int n = MediaRouter.sStatic.mCallbacks.size();
        for (int i = 0; i < n; ++i) {
            if (MediaRouter.sStatic.mCallbacks.get((int)i).cb != callback) continue;
            return i;
        }
        return -1;
    }

    private static WifiDisplay findWifiDisplay(WifiDisplay[] arrwifiDisplay, String string2) {
        for (int i = 0; i < arrwifiDisplay.length; ++i) {
            WifiDisplay wifiDisplay = arrwifiDisplay[i];
            if (!wifiDisplay.getDeviceAddress().equals(string2)) continue;
            return wifiDisplay;
        }
        return null;
    }

    private static RouteInfo findWifiDisplayRoute(WifiDisplay wifiDisplay) {
        int n = MediaRouter.sStatic.mRoutes.size();
        for (int i = 0; i < n; ++i) {
            RouteInfo routeInfo = MediaRouter.sStatic.mRoutes.get(i);
            if (!wifiDisplay.getDeviceAddress().equals(routeInfo.mDeviceAddress)) continue;
            return routeInfo;
        }
        return null;
    }

    static RouteInfo getRouteAtStatic(int n) {
        return MediaRouter.sStatic.mRoutes.get(n);
    }

    static int getRouteCountStatic() {
        return MediaRouter.sStatic.mRoutes.size();
    }

    static int getWifiDisplayStatusCode(WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus) {
        int n = wifiDisplayStatus.getScanState() == 1 ? 1 : (wifiDisplay.isAvailable() ? (wifiDisplay.canConnect() ? 3 : 5) : 4);
        int n2 = n;
        if (wifiDisplay.equals(wifiDisplayStatus.getActiveDisplay())) {
            n2 = wifiDisplayStatus.getActiveDisplayState();
            if (n2 != 0) {
                n2 = n2 != 1 ? (n2 != 2 ? n : 6) : 2;
            } else {
                Log.e(TAG, "Active display is not connected!");
                n2 = n;
            }
        }
        return n2;
    }

    static boolean isWifiDisplayEnabled(WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus) {
        boolean bl = wifiDisplay.isAvailable() && (wifiDisplay.canConnect() || wifiDisplay.equals(wifiDisplayStatus.getActiveDisplay()));
        return bl;
    }

    static RouteInfo makeWifiDisplayRoute(WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus) {
        RouteInfo routeInfo = new RouteInfo(MediaRouter.sStatic.mSystemCategory);
        routeInfo.mDeviceAddress = wifiDisplay.getDeviceAddress();
        routeInfo.mSupportedTypes = 7;
        routeInfo.mVolumeHandling = 0;
        routeInfo.mPlaybackType = 1;
        routeInfo.setRealStatusCode(MediaRouter.getWifiDisplayStatusCode(wifiDisplay, wifiDisplayStatus));
        routeInfo.mEnabled = MediaRouter.isWifiDisplayEnabled(wifiDisplay, wifiDisplayStatus);
        routeInfo.mName = wifiDisplay.getFriendlyDisplayName();
        routeInfo.mDescription = MediaRouter.sStatic.mResources.getText(17041311);
        routeInfo.updatePresentationDisplay();
        routeInfo.mDeviceType = 1;
        return routeInfo;
    }

    static boolean matchesDeviceAddress(WifiDisplay wifiDisplay, RouteInfo routeInfo) {
        boolean bl = routeInfo != null && routeInfo.mDeviceAddress != null;
        if (wifiDisplay == null && !bl) {
            return true;
        }
        if (wifiDisplay != null && bl) {
            return wifiDisplay.getDeviceAddress().equals(routeInfo.mDeviceAddress);
        }
        return false;
    }

    static void removeRouteStatic(RouteInfo routeInfo) {
        Object object;
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Removing route: ");
            ((StringBuilder)object).append(routeInfo);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if (MediaRouter.sStatic.mRoutes.remove(routeInfo)) {
            boolean bl;
            object = routeInfo.getCategory();
            int n = MediaRouter.sStatic.mRoutes.size();
            boolean bl2 = false;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                if (object == MediaRouter.sStatic.mRoutes.get(n2).getCategory()) {
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            if (routeInfo.isSelected()) {
                MediaRouter.selectDefaultRouteStatic();
            }
            if (!bl) {
                MediaRouter.sStatic.mCategories.remove(object);
            }
            MediaRouter.dispatchRouteRemoved(routeInfo);
        }
    }

    static void selectDefaultRouteStatic() {
        if (MediaRouter.sStatic.mSelectedRoute != MediaRouter.sStatic.mBluetoothA2dpRoute && sStatic.isBluetoothA2dpOn()) {
            MediaRouter.selectRouteStatic(8388615, MediaRouter.sStatic.mBluetoothA2dpRoute, false);
        } else {
            MediaRouter.selectRouteStatic(8388615, MediaRouter.sStatic.mDefaultAudioVideo, false);
        }
    }

    static void selectRouteStatic(int n, RouteInfo routeInfo, boolean bl) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Selecting route: ");
        ((StringBuilder)object).append(routeInfo);
        Log.v(TAG, ((StringBuilder)object).toString());
        RouteInfo routeInfo2 = MediaRouter.sStatic.mSelectedRoute;
        object = sStatic.isBluetoothA2dpOn() ? MediaRouter.sStatic.mBluetoothA2dpRoute : MediaRouter.sStatic.mDefaultAudioVideo;
        RouteInfo routeInfo3 = MediaRouter.sStatic.mDefaultAudioVideo;
        boolean bl2 = false;
        boolean bl3 = routeInfo2 == routeInfo3 || routeInfo2 == MediaRouter.sStatic.mBluetoothA2dpRoute;
        if (!(routeInfo2 != routeInfo || bl3 && routeInfo != object)) {
            return;
        }
        if (!routeInfo.matchesTypes(n)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("selectRoute ignored; cannot select route with supported types ");
            ((StringBuilder)object).append(MediaRouter.typesToString(routeInfo.getSupportedTypes()));
            ((StringBuilder)object).append(" into route types ");
            ((StringBuilder)object).append(MediaRouter.typesToString(n));
            Log.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        routeInfo3 = MediaRouter.sStatic.mBluetoothA2dpRoute;
        if (sStatic.isPlaybackActive() && routeInfo3 != null && (n & 1) != 0 && (routeInfo == routeInfo3 || routeInfo == MediaRouter.sStatic.mDefaultAudioVideo)) {
            object = MediaRouter.sStatic.mAudioService;
            boolean bl4 = routeInfo == routeInfo3;
            try {
                object.setBluetoothA2dpOn(bl4);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error changing Bluetooth A2DP state", remoteException);
            }
        }
        object = MediaRouter.sStatic.mDisplayService.getWifiDisplayStatus().getActiveDisplay();
        bl3 = routeInfo2 != null && routeInfo2.mDeviceAddress != null;
        if (routeInfo.mDeviceAddress != null) {
            bl2 = true;
        }
        if (object != null || bl3 || bl2) {
            if (bl2 && !MediaRouter.matchesDeviceAddress((WifiDisplay)object, routeInfo)) {
                if (MediaRouter.sStatic.mCanConfigureWifiDisplays) {
                    MediaRouter.sStatic.mDisplayService.connectWifiDisplay(routeInfo.mDeviceAddress);
                } else {
                    Log.e(TAG, "Cannot connect to wifi displays because this process is not allowed to do so.");
                }
            } else if (object != null && !bl2) {
                MediaRouter.sStatic.mDisplayService.disconnectWifiDisplay();
            }
        }
        sStatic.setSelectedRoute(routeInfo, bl);
        if (routeInfo2 != null) {
            MediaRouter.dispatchRouteUnselected(routeInfo2.getSupportedTypes() & n, routeInfo2);
            if (routeInfo2.resolveStatusCode()) {
                MediaRouter.dispatchRouteChanged(routeInfo2);
            }
        }
        if (routeInfo.resolveStatusCode()) {
            MediaRouter.dispatchRouteChanged(routeInfo);
        }
        MediaRouter.dispatchRouteSelected(routeInfo.getSupportedTypes() & n, routeInfo);
        sStatic.updateDiscoveryRequest();
    }

    private static boolean shouldShowWifiDisplay(WifiDisplay wifiDisplay, WifiDisplay wifiDisplay2) {
        boolean bl = wifiDisplay.isRemembered() || wifiDisplay.equals(wifiDisplay2);
        return bl;
    }

    static void systemVolumeChanged(int n) {
        RouteInfo routeInfo = MediaRouter.sStatic.mSelectedRoute;
        if (routeInfo == null) {
            return;
        }
        if (routeInfo != MediaRouter.sStatic.mBluetoothA2dpRoute && routeInfo != MediaRouter.sStatic.mDefaultAudioVideo) {
            if (MediaRouter.sStatic.mBluetoothA2dpRoute != null) {
                try {
                    routeInfo = MediaRouter.sStatic.mAudioService.isBluetoothA2dpOn() ? MediaRouter.sStatic.mBluetoothA2dpRoute : MediaRouter.sStatic.mDefaultAudioVideo;
                    MediaRouter.dispatchRouteVolumeChanged(routeInfo);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Error checking Bluetooth A2DP state to report volume change", remoteException);
                }
            } else {
                MediaRouter.dispatchRouteVolumeChanged(MediaRouter.sStatic.mDefaultAudioVideo);
            }
        } else {
            MediaRouter.dispatchRouteVolumeChanged(routeInfo);
        }
    }

    static String typesToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((n & 1) != 0) {
            stringBuilder.append("ROUTE_TYPE_LIVE_AUDIO ");
        }
        if ((n & 2) != 0) {
            stringBuilder.append("ROUTE_TYPE_LIVE_VIDEO ");
        }
        if ((n & 4) != 0) {
            stringBuilder.append("ROUTE_TYPE_REMOTE_DISPLAY ");
        }
        if ((8388608 & n) != 0) {
            stringBuilder.append("ROUTE_TYPE_USER ");
        }
        return stringBuilder.toString();
    }

    static void updateRoute(RouteInfo routeInfo) {
        MediaRouter.dispatchRouteChanged(routeInfo);
    }

    private static void updateWifiDisplayRoute(RouteInfo routeInfo, WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus, boolean bl) {
        boolean bl2;
        boolean bl3 = false;
        String string2 = wifiDisplay.getFriendlyDisplayName();
        if (!routeInfo.getName().equals(string2)) {
            routeInfo.mName = string2;
            bl3 = true;
        }
        boolean bl4 = routeInfo.mEnabled != (bl2 = MediaRouter.isWifiDisplayEnabled(wifiDisplay, wifiDisplayStatus));
        routeInfo.mEnabled = bl2;
        if (bl3 | bl4 | routeInfo.setRealStatusCode(MediaRouter.getWifiDisplayStatusCode(wifiDisplay, wifiDisplayStatus))) {
            MediaRouter.dispatchRouteChanged(routeInfo);
        }
        if ((!bl2 || bl) && routeInfo.isSelected()) {
            MediaRouter.selectDefaultRouteStatic();
        }
    }

    static void updateWifiDisplayStatus(WifiDisplayStatus object) {
        WifiDisplay[] arrwifiDisplay;
        Object object2;
        Object object3;
        Object object4;
        int n;
        if (((WifiDisplayStatus)object).getFeatureState() == 3) {
            arrwifiDisplay = ((WifiDisplayStatus)object).getDisplays();
            object4 = object2 = ((WifiDisplayStatus)object).getActiveDisplay();
            if (!MediaRouter.sStatic.mCanConfigureWifiDisplays) {
                if (object2 != null) {
                    arrwifiDisplay = new WifiDisplay[]{object2};
                    object4 = object2;
                } else {
                    arrwifiDisplay = WifiDisplay.EMPTY_ARRAY;
                    object4 = object2;
                }
            }
        } else {
            arrwifiDisplay = WifiDisplay.EMPTY_ARRAY;
            object4 = null;
        }
        object2 = object4 != null ? ((WifiDisplay)object4).getDeviceAddress() : null;
        for (n = 0; n < arrwifiDisplay.length; ++n) {
            WifiDisplay wifiDisplay = arrwifiDisplay[n];
            if (!MediaRouter.shouldShowWifiDisplay(wifiDisplay, (WifiDisplay)object4)) continue;
            object3 = MediaRouter.findWifiDisplayRoute(wifiDisplay);
            if (object3 == null) {
                object3 = MediaRouter.makeWifiDisplayRoute(wifiDisplay, (WifiDisplayStatus)object);
                MediaRouter.addRouteStatic((RouteInfo)object3);
            } else {
                String string2 = wifiDisplay.getDeviceAddress();
                boolean bl = !string2.equals(object2) && string2.equals(MediaRouter.sStatic.mPreviousActiveWifiDisplayAddress);
                MediaRouter.updateWifiDisplayRoute((RouteInfo)object3, wifiDisplay, (WifiDisplayStatus)object, bl);
            }
            if (!wifiDisplay.equals((WifiDisplay)object4)) continue;
            MediaRouter.selectRouteStatic(((RouteInfo)object3).getSupportedTypes(), (RouteInfo)object3, false);
        }
        n = MediaRouter.sStatic.mRoutes.size();
        do {
            int n2 = n - 1;
            if (n <= 0) break;
            object = MediaRouter.sStatic.mRoutes.get(n2);
            if (!(((RouteInfo)object).mDeviceAddress == null || (object3 = MediaRouter.findWifiDisplay(arrwifiDisplay, ((RouteInfo)object).mDeviceAddress)) != null && MediaRouter.shouldShowWifiDisplay((WifiDisplay)object3, (WifiDisplay)object4))) {
                MediaRouter.removeRouteStatic((RouteInfo)object);
            }
            n = n2;
        } while (true);
        MediaRouter.sStatic.mPreviousActiveWifiDisplayAddress = object2;
    }

    public void addCallback(int n, Callback callback) {
        this.addCallback(n, callback, 0);
    }

    public void addCallback(int n, Callback object, int n2) {
        int n3 = this.findCallbackInfo((Callback)object);
        if (n3 >= 0) {
            object = MediaRouter.sStatic.mCallbacks.get(n3);
            ((CallbackInfo)object).type |= n;
            ((CallbackInfo)object).flags |= n2;
        } else {
            object = new CallbackInfo((Callback)object, n, n2, this);
            MediaRouter.sStatic.mCallbacks.add((CallbackInfo)object);
        }
        sStatic.updateDiscoveryRequest();
    }

    public void addRouteInt(RouteInfo routeInfo) {
        MediaRouter.addRouteStatic(routeInfo);
    }

    public void addUserRoute(UserRouteInfo userRouteInfo) {
        MediaRouter.addRouteStatic(userRouteInfo);
    }

    public void clearUserRoutes() {
        int n = 0;
        while (n < MediaRouter.sStatic.mRoutes.size()) {
            int n2;
            block4 : {
                RouteInfo routeInfo;
                block3 : {
                    routeInfo = MediaRouter.sStatic.mRoutes.get(n);
                    if (routeInfo instanceof UserRouteInfo) break block3;
                    n2 = n;
                    if (!(routeInfo instanceof RouteGroup)) break block4;
                }
                MediaRouter.removeRouteStatic(routeInfo);
                n2 = n - 1;
            }
            n = n2 + 1;
        }
    }

    public RouteCategory createRouteCategory(int n, boolean bl) {
        return new RouteCategory(n, 8388608, bl);
    }

    public RouteCategory createRouteCategory(CharSequence charSequence, boolean bl) {
        return new RouteCategory(charSequence, 8388608, bl);
    }

    public UserRouteInfo createUserRoute(RouteCategory routeCategory) {
        return new UserRouteInfo(routeCategory);
    }

    public RouteCategory getCategoryAt(int n) {
        return MediaRouter.sStatic.mCategories.get(n);
    }

    public int getCategoryCount() {
        return MediaRouter.sStatic.mCategories.size();
    }

    public RouteInfo getDefaultRoute() {
        return MediaRouter.sStatic.mDefaultAudioVideo;
    }

    public RouteInfo getFallbackRoute() {
        RouteInfo routeInfo = MediaRouter.sStatic.mBluetoothA2dpRoute != null ? MediaRouter.sStatic.mBluetoothA2dpRoute : MediaRouter.sStatic.mDefaultAudioVideo;
        return routeInfo;
    }

    public RouteInfo getRouteAt(int n) {
        return MediaRouter.sStatic.mRoutes.get(n);
    }

    public int getRouteCount() {
        return MediaRouter.sStatic.mRoutes.size();
    }

    @UnsupportedAppUsage
    public RouteInfo getSelectedRoute() {
        return this.getSelectedRoute(8388615);
    }

    public RouteInfo getSelectedRoute(int n) {
        if (MediaRouter.sStatic.mSelectedRoute != null && (MediaRouter.sStatic.mSelectedRoute.mSupportedTypes & n) != 0) {
            return MediaRouter.sStatic.mSelectedRoute;
        }
        if (n == 8388608) {
            return null;
        }
        return MediaRouter.sStatic.mDefaultAudioVideo;
    }

    public RouteCategory getSystemCategory() {
        return MediaRouter.sStatic.mSystemCategory;
    }

    public boolean isRouteAvailable(int n, int n2) {
        int n3 = MediaRouter.sStatic.mRoutes.size();
        for (int i = 0; i < n3; ++i) {
            RouteInfo routeInfo = MediaRouter.sStatic.mRoutes.get(i);
            if (!routeInfo.matchesTypes(n) || (n2 & 1) != 0 && routeInfo == MediaRouter.sStatic.mDefaultAudioVideo) continue;
            return true;
        }
        return false;
    }

    public void rebindAsUser(int n) {
        sStatic.rebindAsUser(n);
    }

    public void removeCallback(Callback callback) {
        int n = this.findCallbackInfo(callback);
        if (n >= 0) {
            MediaRouter.sStatic.mCallbacks.remove(n);
            sStatic.updateDiscoveryRequest();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeCallback(");
            stringBuilder.append(callback);
            stringBuilder.append("): callback not registered");
            Log.w(TAG, stringBuilder.toString());
        }
    }

    public void removeRouteInt(RouteInfo routeInfo) {
        MediaRouter.removeRouteStatic(routeInfo);
    }

    public void removeUserRoute(UserRouteInfo userRouteInfo) {
        MediaRouter.removeRouteStatic(userRouteInfo);
    }

    public void selectRoute(int n, RouteInfo routeInfo) {
        if (routeInfo != null) {
            MediaRouter.selectRouteStatic(n, routeInfo, true);
            return;
        }
        throw new IllegalArgumentException("Route cannot be null.");
    }

    @UnsupportedAppUsage
    public void selectRouteInt(int n, RouteInfo routeInfo, boolean bl) {
        MediaRouter.selectRouteStatic(n, routeInfo, bl);
    }

    public void setRouterGroupId(String string2) {
        sStatic.setRouterGroupId(string2);
    }

    public static abstract class Callback {
        public abstract void onRouteAdded(MediaRouter var1, RouteInfo var2);

        public abstract void onRouteChanged(MediaRouter var1, RouteInfo var2);

        public abstract void onRouteGrouped(MediaRouter var1, RouteInfo var2, RouteGroup var3, int var4);

        public void onRoutePresentationDisplayChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public abstract void onRouteRemoved(MediaRouter var1, RouteInfo var2);

        public abstract void onRouteSelected(MediaRouter var1, int var2, RouteInfo var3);

        public abstract void onRouteUngrouped(MediaRouter var1, RouteInfo var2, RouteGroup var3);

        public abstract void onRouteUnselected(MediaRouter var1, int var2, RouteInfo var3);

        public abstract void onRouteVolumeChanged(MediaRouter var1, RouteInfo var2);
    }

    static class CallbackInfo {
        public final Callback cb;
        public int flags;
        public final MediaRouter router;
        public int type;

        public CallbackInfo(Callback callback, int n, int n2, MediaRouter mediaRouter) {
            this.cb = callback;
            this.type = n;
            this.flags = n2;
            this.router = mediaRouter;
        }

        public boolean filterRouteEvent(int n) {
            boolean bl = (this.flags & 2) != 0 || (this.type & n) != 0;
            return bl;
        }

        public boolean filterRouteEvent(RouteInfo routeInfo) {
            return this.filterRouteEvent(routeInfo.mSupportedTypes);
        }
    }

    public static class RouteCategory {
        final boolean mGroupable;
        boolean mIsSystem;
        CharSequence mName;
        int mNameResId;
        int mTypes;

        RouteCategory(int n, int n2, boolean bl) {
            this.mNameResId = n;
            this.mTypes = n2;
            this.mGroupable = bl;
        }

        RouteCategory(CharSequence charSequence, int n, boolean bl) {
            this.mName = charSequence;
            this.mTypes = n;
            this.mGroupable = bl;
        }

        public CharSequence getName() {
            return this.getName(MediaRouter.sStatic.mResources);
        }

        public CharSequence getName(Context context) {
            return this.getName(context.getResources());
        }

        CharSequence getName(Resources resources) {
            int n = this.mNameResId;
            if (n != 0) {
                return resources.getText(n);
            }
            return this.mName;
        }

        public List<RouteInfo> getRoutes(List<RouteInfo> list) {
            if (list == null) {
                list = new ArrayList<RouteInfo>();
            } else {
                list.clear();
            }
            int n = MediaRouter.getRouteCountStatic();
            for (int i = 0; i < n; ++i) {
                RouteInfo routeInfo = MediaRouter.getRouteAtStatic(i);
                if (routeInfo.mCategory != this) continue;
                list.add(routeInfo);
            }
            return list;
        }

        public int getSupportedTypes() {
            return this.mTypes;
        }

        public boolean isGroupable() {
            return this.mGroupable;
        }

        public boolean isSystem() {
            return this.mIsSystem;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RouteCategory{ name=");
            stringBuilder.append((Object)this.getName());
            stringBuilder.append(" types=");
            stringBuilder.append(MediaRouter.typesToString(this.mTypes));
            stringBuilder.append(" groupable=");
            stringBuilder.append(this.mGroupable);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class RouteGroup
    extends RouteInfo {
        final ArrayList<RouteInfo> mRoutes = new ArrayList();
        private boolean mUpdateName;

        RouteGroup(RouteCategory routeCategory) {
            super(routeCategory);
            this.mGroup = this;
            this.mVolumeHandling = 0;
        }

        public void addRoute(RouteInfo routeInfo) {
            if (routeInfo.getGroup() == null) {
                if (routeInfo.getCategory() == this.mCategory) {
                    int n = this.mRoutes.size();
                    this.mRoutes.add(routeInfo);
                    routeInfo.mGroup = this;
                    this.mUpdateName = true;
                    this.updateVolume();
                    this.routeUpdated();
                    MediaRouter.dispatchRouteGrouped(routeInfo, this, n);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Route cannot be added to a group with a different category. (Route category=");
                stringBuilder.append(routeInfo.getCategory());
                stringBuilder.append(" group category=");
                stringBuilder.append(this.mCategory);
                stringBuilder.append(")");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Route ");
            stringBuilder.append(routeInfo);
            stringBuilder.append(" is already part of a group.");
            throw new IllegalStateException(stringBuilder.toString());
        }

        public void addRoute(RouteInfo routeInfo, int n) {
            if (routeInfo.getGroup() == null) {
                if (routeInfo.getCategory() == this.mCategory) {
                    this.mRoutes.add(n, routeInfo);
                    routeInfo.mGroup = this;
                    this.mUpdateName = true;
                    this.updateVolume();
                    this.routeUpdated();
                    MediaRouter.dispatchRouteGrouped(routeInfo, this, n);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Route cannot be added to a group with a different category. (Route category=");
                stringBuilder.append(routeInfo.getCategory());
                stringBuilder.append(" group category=");
                stringBuilder.append(this.mCategory);
                stringBuilder.append(")");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Route ");
            stringBuilder.append(routeInfo);
            stringBuilder.append(" is already part of a group.");
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        CharSequence getName(Resources resources) {
            if (this.mUpdateName) {
                this.updateName();
            }
            return super.getName(resources);
        }

        public RouteInfo getRouteAt(int n) {
            return this.mRoutes.get(n);
        }

        public int getRouteCount() {
            return this.mRoutes.size();
        }

        void memberNameChanged(RouteInfo routeInfo, CharSequence charSequence) {
            this.mUpdateName = true;
            this.routeUpdated();
        }

        void memberStatusChanged(RouteInfo routeInfo, CharSequence charSequence) {
            this.setStatusInt(charSequence);
        }

        void memberVolumeChanged(RouteInfo routeInfo) {
            this.updateVolume();
        }

        public void removeRoute(int n) {
            RouteInfo routeInfo = this.mRoutes.remove(n);
            routeInfo.mGroup = null;
            this.mUpdateName = true;
            this.updateVolume();
            MediaRouter.dispatchRouteUngrouped(routeInfo, this);
            this.routeUpdated();
        }

        public void removeRoute(RouteInfo routeInfo) {
            if (routeInfo.getGroup() == this) {
                this.mRoutes.remove(routeInfo);
                routeInfo.mGroup = null;
                this.mUpdateName = true;
                this.updateVolume();
                MediaRouter.dispatchRouteUngrouped(routeInfo, this);
                this.routeUpdated();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Route ");
            stringBuilder.append(routeInfo);
            stringBuilder.append(" is not a member of this group.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Override
        public void requestSetVolume(int n) {
            int n2 = this.getVolumeMax();
            if (n2 == 0) {
                return;
            }
            float f = (float)n / (float)n2;
            int n3 = this.getRouteCount();
            for (n2 = 0; n2 < n3; ++n2) {
                RouteInfo routeInfo = this.getRouteAt(n2);
                routeInfo.requestSetVolume((int)((float)routeInfo.getVolumeMax() * f));
            }
            if (n != this.mVolume) {
                this.mVolume = n;
                MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        @Override
        public void requestUpdateVolume(int n) {
            if (this.getVolumeMax() == 0) {
                return;
            }
            int n2 = this.getRouteCount();
            int n3 = 0;
            for (int i = 0; i < n2; ++i) {
                RouteInfo routeInfo = this.getRouteAt(i);
                routeInfo.requestUpdateVolume(n);
                int n4 = routeInfo.getVolume();
                int n5 = n3;
                if (n4 > n3) {
                    n5 = n4;
                }
                n3 = n5;
            }
            if (n3 != this.mVolume) {
                this.mVolume = n3;
                MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        @Override
        void routeUpdated() {
            Object object;
            int n = 0;
            int n2 = this.mRoutes.size();
            if (n2 == 0) {
                MediaRouter.removeRouteStatic(this);
                return;
            }
            int n3 = 0;
            int n4 = 1;
            int n5 = 1;
            int n6 = 0;
            do {
                int n7 = 0;
                if (n6 >= n2) break;
                object = this.mRoutes.get(n6);
                n |= ((RouteInfo)object).mSupportedTypes;
                int n8 = ((RouteInfo)object).getVolumeMax();
                int n9 = n3;
                if (n8 > n3) {
                    n9 = n8;
                }
                n3 = ((RouteInfo)object).getPlaybackType() == 0 ? 1 : 0;
                n4 &= n3;
                n3 = n7;
                if (((RouteInfo)object).getVolumeHandling() == 0) {
                    n3 = 1;
                }
                n5 &= n3;
                ++n6;
                n3 = n9;
            } while (true);
            n6 = n4 != 0 ? 0 : 1;
            this.mPlaybackType = n6;
            n5 = n5 != 0 ? 0 : 1;
            this.mVolumeHandling = n5;
            this.mSupportedTypes = n;
            this.mVolumeMax = n3;
            object = n2 == 1 ? this.mRoutes.get(0).getIconDrawable() : null;
            this.mIcon = object;
            super.routeUpdated();
        }

        public void setIconDrawable(Drawable drawable2) {
            this.mIcon = drawable2;
        }

        public void setIconResource(int n) {
            this.setIconDrawable(MediaRouter.sStatic.mResources.getDrawable(n));
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(super.toString());
            stringBuilder.append('[');
            int n = this.mRoutes.size();
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(this.mRoutes.get(i));
            }
            stringBuilder.append(']');
            return stringBuilder.toString();
        }

        void updateName() {
            StringBuilder stringBuilder = new StringBuilder();
            int n = this.mRoutes.size();
            for (int i = 0; i < n; ++i) {
                RouteInfo routeInfo = this.mRoutes.get(i);
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(routeInfo.getName());
            }
            this.mName = stringBuilder.toString();
            this.mUpdateName = false;
        }

        void updateVolume() {
            int n = this.getRouteCount();
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                int n3 = this.getRouteAt(i).getVolume();
                int n4 = n2;
                if (n3 > n2) {
                    n4 = n3;
                }
                n2 = n4;
            }
            if (n2 != this.mVolume) {
                this.mVolume = n2;
                MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }
    }

    public static class RouteInfo {
        public static final int DEVICE_TYPE_BLUETOOTH = 3;
        public static final int DEVICE_TYPE_SPEAKER = 2;
        public static final int DEVICE_TYPE_TV = 1;
        public static final int DEVICE_TYPE_UNKNOWN = 0;
        public static final int PLAYBACK_TYPE_LOCAL = 0;
        public static final int PLAYBACK_TYPE_REMOTE = 1;
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        public static final int STATUS_AVAILABLE = 3;
        public static final int STATUS_CONNECTED = 6;
        @UnsupportedAppUsage
        public static final int STATUS_CONNECTING = 2;
        public static final int STATUS_IN_USE = 5;
        public static final int STATUS_NONE = 0;
        public static final int STATUS_NOT_AVAILABLE = 4;
        public static final int STATUS_SCANNING = 1;
        final RouteCategory mCategory;
        CharSequence mDescription;
        String mDeviceAddress;
        int mDeviceType;
        boolean mEnabled = true;
        String mGlobalRouteId;
        RouteGroup mGroup;
        Drawable mIcon;
        CharSequence mName;
        @UnsupportedAppUsage
        int mNameResId;
        int mPlaybackStream = 3;
        int mPlaybackType = 0;
        Display mPresentationDisplay;
        int mPresentationDisplayId = -1;
        private int mRealStatusCode;
        final IRemoteVolumeObserver.Stub mRemoteVolObserver = new IRemoteVolumeObserver.Stub(){

            @Override
            public void dispatchRemoteVolumeUpdate(final int n, final int n2) {
                MediaRouter.sStatic.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        if (RouteInfo.this.mVcb != null) {
                            if (n != 0) {
                                RouteInfo.this.mVcb.vcb.onVolumeUpdateRequest(RouteInfo.this.mVcb.route, n);
                            } else {
                                RouteInfo.this.mVcb.vcb.onVolumeSetRequest(RouteInfo.this.mVcb.route, n2);
                            }
                        }
                    }
                });
            }

        };
        private int mResolvedStatusCode;
        private CharSequence mStatus;
        int mSupportedTypes;
        private Object mTag;
        VolumeCallbackInfo mVcb;
        int mVolume = 15;
        int mVolumeHandling = 1;
        int mVolumeMax = 15;

        RouteInfo(RouteCategory routeCategory) {
            this.mCategory = routeCategory;
            this.mDeviceType = 0;
        }

        private Display choosePresentationDisplay() {
            if ((this.mSupportedTypes & 2) != 0) {
                Display[] arrdisplay = sStatic.getAllPresentationDisplays();
                int n = this.mPresentationDisplayId;
                int n2 = 0;
                if (n >= 0) {
                    for (Display display : arrdisplay) {
                        if (display.getDisplayId() != this.mPresentationDisplayId) continue;
                        return display;
                    }
                    return null;
                }
                if (this.mDeviceAddress != null) {
                    n = arrdisplay.length;
                    for (int i = n2; i < n; ++i) {
                        Display display = arrdisplay[i];
                        if (display.getType() != 3 || !this.mDeviceAddress.equals(display.getAddress())) continue;
                        return display;
                    }
                    return null;
                }
                if (this == MediaRouter.sStatic.mDefaultAudioVideo && arrdisplay.length > 0) {
                    return arrdisplay[0];
                }
            }
            return null;
        }

        public RouteCategory getCategory() {
            return this.mCategory;
        }

        public CharSequence getDescription() {
            return this.mDescription;
        }

        @UnsupportedAppUsage
        public String getDeviceAddress() {
            return this.mDeviceAddress;
        }

        public int getDeviceType() {
            return this.mDeviceType;
        }

        public RouteGroup getGroup() {
            return this.mGroup;
        }

        public Drawable getIconDrawable() {
            return this.mIcon;
        }

        public CharSequence getName() {
            return this.getName(MediaRouter.sStatic.mResources);
        }

        public CharSequence getName(Context context) {
            return this.getName(context.getResources());
        }

        @UnsupportedAppUsage
        CharSequence getName(Resources resources) {
            int n = this.mNameResId;
            if (n != 0) {
                return resources.getText(n);
            }
            return this.mName;
        }

        public int getPlaybackStream() {
            return this.mPlaybackStream;
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public Display getPresentationDisplay() {
            return this.mPresentationDisplay;
        }

        public CharSequence getStatus() {
            return this.mStatus;
        }

        @UnsupportedAppUsage
        public int getStatusCode() {
            return this.mResolvedStatusCode;
        }

        public int getSupportedTypes() {
            return this.mSupportedTypes;
        }

        public Object getTag() {
            return this.mTag;
        }

        public int getVolume() {
            if (this.mPlaybackType == 0) {
                int n;
                int n2 = 0;
                try {
                    n = MediaRouter.sStatic.mAudioService.getStreamVolume(this.mPlaybackStream);
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Error getting local stream volume", remoteException);
                    n = n2;
                }
                return n;
            }
            return this.mVolume;
        }

        public int getVolumeHandling() {
            return this.mVolumeHandling;
        }

        public int getVolumeMax() {
            if (this.mPlaybackType == 0) {
                int n;
                int n2 = 0;
                try {
                    n = MediaRouter.sStatic.mAudioService.getStreamMaxVolume(this.mPlaybackStream);
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Error getting local stream volume", remoteException);
                    n = n2;
                }
                return n;
            }
            return this.mVolumeMax;
        }

        public boolean isBluetooth() {
            boolean bl = this == MediaRouter.sStatic.mBluetoothA2dpRoute;
            return bl;
        }

        public boolean isConnecting() {
            boolean bl = this.mResolvedStatusCode == 2;
            return bl;
        }

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public boolean isDefault() {
            boolean bl = this == MediaRouter.sStatic.mDefaultAudioVideo;
            return bl;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        @UnsupportedAppUsage
        public boolean isSelected() {
            boolean bl = this == MediaRouter.sStatic.mSelectedRoute;
            return bl;
        }

        @UnsupportedAppUsage
        public boolean matchesTypes(int n) {
            boolean bl = (this.mSupportedTypes & n) != 0;
            return bl;
        }

        public void requestSetVolume(int n) {
            if (this.mPlaybackType == 0) {
                try {
                    MediaRouter.sStatic.mAudioService.setStreamVolume(this.mPlaybackStream, n, 0, ActivityThread.currentPackageName());
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Error setting local stream volume", remoteException);
                }
            } else {
                sStatic.requestSetVolume(this, n);
            }
        }

        public void requestUpdateVolume(int n) {
            if (this.mPlaybackType == 0) {
                try {
                    n = Math.max(0, Math.min(this.getVolume() + n, this.getVolumeMax()));
                    MediaRouter.sStatic.mAudioService.setStreamVolume(this.mPlaybackStream, n, 0, ActivityThread.currentPackageName());
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Error setting local stream volume", remoteException);
                }
            } else {
                sStatic.requestUpdateVolume(this, n);
            }
        }

        boolean resolveStatusCode() {
            int n;
            int n2 = n = this.mRealStatusCode;
            if (this.isSelected()) {
                n2 = n != 1 && n != 3 ? n : 2;
            }
            if (this.mResolvedStatusCode == n2) {
                return false;
            }
            this.mResolvedStatusCode = n2;
            n2 = n2 != 1 ? (n2 != 2 ? (n2 != 3 ? (n2 != 4 ? (n2 != 5 ? 0 : 17040315) : 17040316) : 17040313) : 17040314) : 17040317;
            CharSequence charSequence = n2 != 0 ? MediaRouter.sStatic.mResources.getText(n2) : null;
            this.mStatus = charSequence;
            return true;
        }

        void routeUpdated() {
            MediaRouter.updateRoute(this);
        }

        @UnsupportedAppUsage
        public void select() {
            MediaRouter.selectRouteStatic(this.mSupportedTypes, this, true);
        }

        boolean setRealStatusCode(int n) {
            if (this.mRealStatusCode != n) {
                this.mRealStatusCode = n;
                return this.resolveStatusCode();
            }
            return false;
        }

        void setStatusInt(CharSequence charSequence) {
            if (!charSequence.equals(this.mStatus)) {
                this.mStatus = charSequence;
                RouteGroup routeGroup = this.mGroup;
                if (routeGroup != null) {
                    routeGroup.memberStatusChanged(this, charSequence);
                }
                this.routeUpdated();
            }
        }

        public void setTag(Object object) {
            this.mTag = object;
            this.routeUpdated();
        }

        public String toString() {
            String string2 = MediaRouter.typesToString(this.getSupportedTypes());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getSimpleName());
            stringBuilder.append("{ name=");
            stringBuilder.append((Object)this.getName());
            stringBuilder.append(", description=");
            stringBuilder.append((Object)this.getDescription());
            stringBuilder.append(", status=");
            stringBuilder.append((Object)this.getStatus());
            stringBuilder.append(", category=");
            stringBuilder.append(this.getCategory());
            stringBuilder.append(", supportedTypes=");
            stringBuilder.append(string2);
            stringBuilder.append(", presentationDisplay=");
            stringBuilder.append(this.mPresentationDisplay);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        boolean updatePresentationDisplay() {
            Display display = this.choosePresentationDisplay();
            if (this.mPresentationDisplay != display) {
                this.mPresentationDisplay = display;
                return true;
            }
            return false;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface DeviceType {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface PlaybackType {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        private static @interface PlaybackVolume {
        }

    }

    public static class SimpleCallback
    extends Callback {
        @Override
        public void onRouteAdded(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        @Override
        public void onRouteChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        @Override
        public void onRouteGrouped(MediaRouter mediaRouter, RouteInfo routeInfo, RouteGroup routeGroup, int n) {
        }

        @Override
        public void onRouteRemoved(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        @Override
        public void onRouteSelected(MediaRouter mediaRouter, int n, RouteInfo routeInfo) {
        }

        @Override
        public void onRouteUngrouped(MediaRouter mediaRouter, RouteInfo routeInfo, RouteGroup routeGroup) {
        }

        @Override
        public void onRouteUnselected(MediaRouter mediaRouter, int n, RouteInfo routeInfo) {
        }

        @Override
        public void onRouteVolumeChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }
    }

    static class Static
    implements DisplayManager.DisplayListener {
        boolean mActivelyScanningWifiDisplays;
        final IAudioRoutesObserver.Stub mAudioRoutesObserver = new IAudioRoutesObserver.Stub(){

            @Override
            public void dispatchAudioRoutesChanged(final AudioRoutesInfo audioRoutesInfo) {
                Static.this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        Static.this.updateAudioRoutes(audioRoutesInfo);
                    }
                });
            }

        };
        final IAudioService mAudioService;
        RouteInfo mBluetoothA2dpRoute;
        final CopyOnWriteArrayList<CallbackInfo> mCallbacks = new CopyOnWriteArrayList();
        final boolean mCanConfigureWifiDisplays;
        final ArrayList<RouteCategory> mCategories = new ArrayList();
        IMediaRouterClient mClient;
        MediaRouterClientState mClientState;
        final AudioRoutesInfo mCurAudioRoutesInfo = new AudioRoutesInfo();
        int mCurrentUserId = -1;
        RouteInfo mDefaultAudioVideo;
        boolean mDiscoverRequestActiveScan;
        int mDiscoveryRequestRouteTypes;
        final DisplayManager mDisplayService;
        final Handler mHandler;
        final IMediaRouterService mMediaRouterService;
        final String mPackageName;
        String mPreviousActiveWifiDisplayAddress;
        final Resources mResources;
        final ArrayList<RouteInfo> mRoutes = new ArrayList();
        RouteInfo mSelectedRoute;
        final RouteCategory mSystemCategory;

        Static(Context context) {
            this.mPackageName = context.getPackageName();
            this.mResources = context.getResources();
            this.mHandler = new Handler(context.getMainLooper());
            this.mAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
            this.mDisplayService = (DisplayManager)context.getSystemService("display");
            this.mMediaRouterService = IMediaRouterService.Stub.asInterface(ServiceManager.getService("media_router"));
            boolean bl = false;
            this.mSystemCategory = new RouteCategory(17039839, 3, false);
            this.mSystemCategory.mIsSystem = true;
            if (context.checkPermission("android.permission.CONFIGURE_WIFI_DISPLAY", Process.myPid(), Process.myUid()) == 0) {
                bl = true;
            }
            this.mCanConfigureWifiDisplays = bl;
        }

        private void updatePresentationDisplays(int n) {
            int n2 = this.mRoutes.size();
            for (int i = 0; i < n2; ++i) {
                RouteInfo routeInfo = this.mRoutes.get(i);
                if (!routeInfo.updatePresentationDisplay() && (routeInfo.mPresentationDisplay == null || routeInfo.mPresentationDisplay.getDisplayId() != n)) continue;
                MediaRouter.dispatchRoutePresentationDisplayChanged(routeInfo);
            }
        }

        RouteInfo findGlobalRoute(String string2) {
            int n = this.mRoutes.size();
            for (int i = 0; i < n; ++i) {
                RouteInfo routeInfo = this.mRoutes.get(i);
                if (!string2.equals(routeInfo.mGlobalRouteId)) continue;
                return routeInfo;
            }
            return null;
        }

        public Display[] getAllPresentationDisplays() {
            return this.mDisplayService.getDisplays("android.hardware.display.category.PRESENTATION");
        }

        boolean isBluetoothA2dpOn() {
            boolean bl;
            boolean bl2 = bl = false;
            try {
                if (this.mBluetoothA2dpRoute != null) {
                    boolean bl3 = this.mAudioService.isBluetoothA2dpOn();
                    bl2 = bl;
                    if (bl3) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            catch (RemoteException remoteException) {
                Log.e(MediaRouter.TAG, "Error querying Bluetooth A2DP state", remoteException);
                return false;
            }
        }

        boolean isPlaybackActive() {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                try {
                    boolean bl = this.mMediaRouterService.isPlaybackActive(iMediaRouterClient);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Unable to retrieve playback active state.", remoteException);
                }
            }
            return false;
        }

        RouteInfo makeGlobalRoute(MediaRouterClientState.RouteInfo routeInfo) {
            RouteInfo routeInfo2 = new RouteInfo(this.mSystemCategory);
            routeInfo2.mGlobalRouteId = routeInfo.id;
            routeInfo2.mName = routeInfo.name;
            routeInfo2.mDescription = routeInfo.description;
            routeInfo2.mSupportedTypes = routeInfo.supportedTypes;
            routeInfo2.mDeviceType = routeInfo.deviceType;
            routeInfo2.mEnabled = routeInfo.enabled;
            routeInfo2.setRealStatusCode(routeInfo.statusCode);
            routeInfo2.mPlaybackType = routeInfo.playbackType;
            routeInfo2.mPlaybackStream = routeInfo.playbackStream;
            routeInfo2.mVolume = routeInfo.volume;
            routeInfo2.mVolumeMax = routeInfo.volumeMax;
            routeInfo2.mVolumeHandling = routeInfo.volumeHandling;
            routeInfo2.mPresentationDisplayId = routeInfo.presentationDisplayId;
            routeInfo2.updatePresentationDisplay();
            return routeInfo2;
        }

        @Override
        public void onDisplayAdded(int n) {
            this.updatePresentationDisplays(n);
        }

        @Override
        public void onDisplayChanged(int n) {
            this.updatePresentationDisplays(n);
        }

        @Override
        public void onDisplayRemoved(int n) {
            this.updatePresentationDisplays(n);
        }

        void publishClientDiscoveryRequest() {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                try {
                    this.mMediaRouterService.setDiscoveryRequest(iMediaRouterClient, this.mDiscoveryRequestRouteTypes, this.mDiscoverRequestActiveScan);
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Unable to publish media router client discovery request.", remoteException);
                }
            }
        }

        void publishClientSelectedRoute(boolean bl) {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                IMediaRouterService iMediaRouterService = this.mMediaRouterService;
                String string2 = this.mSelectedRoute != null ? this.mSelectedRoute.mGlobalRouteId : null;
                try {
                    iMediaRouterService.setSelectedRoute(iMediaRouterClient, string2, bl);
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Unable to publish media router client selected route.", remoteException);
                }
            }
        }

        void rebindAsUser(int n) {
            if (this.mCurrentUserId != n || n < 0 || this.mClient == null) {
                IMediaRouterClient iMediaRouterClient = this.mClient;
                if (iMediaRouterClient != null) {
                    try {
                        this.mMediaRouterService.unregisterClient(iMediaRouterClient);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(MediaRouter.TAG, "Unable to unregister media router client.", remoteException);
                    }
                    this.mClient = null;
                }
                this.mCurrentUserId = n;
                try {
                    iMediaRouterClient = new Client();
                    this.mMediaRouterService.registerClientAsUser(iMediaRouterClient, this.mPackageName, n);
                    this.mClient = iMediaRouterClient;
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Unable to register media router client.", remoteException);
                }
                this.publishClientDiscoveryRequest();
                this.publishClientSelectedRoute(false);
                this.updateClientState();
            }
        }

        void requestSetVolume(RouteInfo routeInfo, int n) {
            IMediaRouterClient iMediaRouterClient;
            if (routeInfo.mGlobalRouteId != null && (iMediaRouterClient = this.mClient) != null) {
                try {
                    this.mMediaRouterService.requestSetVolume(iMediaRouterClient, routeInfo.mGlobalRouteId, n);
                }
                catch (RemoteException remoteException) {
                    Log.w(MediaRouter.TAG, "Unable to request volume change.", remoteException);
                }
            }
        }

        void requestUpdateVolume(RouteInfo routeInfo, int n) {
            IMediaRouterClient iMediaRouterClient;
            if (routeInfo.mGlobalRouteId != null && (iMediaRouterClient = this.mClient) != null) {
                try {
                    this.mMediaRouterService.requestUpdateVolume(iMediaRouterClient, routeInfo.mGlobalRouteId, n);
                }
                catch (RemoteException remoteException) {
                    Log.w(MediaRouter.TAG, "Unable to request volume change.", remoteException);
                }
            }
        }

        public void setRouterGroupId(String string2) {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                try {
                    this.mMediaRouterService.registerClientGroupId(iMediaRouterClient, string2);
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Unable to register group ID of the client.", remoteException);
                }
            }
        }

        void setSelectedRoute(RouteInfo routeInfo, boolean bl) {
            this.mSelectedRoute = routeInfo;
            this.publishClientSelectedRoute(bl);
        }

        void startMonitoringRoutes(Context object) {
            this.mDefaultAudioVideo = new RouteInfo(this.mSystemCategory);
            Object object2 = this.mDefaultAudioVideo;
            ((RouteInfo)object2).mNameResId = 17039840;
            ((RouteInfo)object2).mSupportedTypes = 3;
            ((RouteInfo)object2).updatePresentationDisplay();
            if (((AudioManager)((Context)object).getSystemService("audio")).isVolumeFixed()) {
                this.mDefaultAudioVideo.mVolumeHandling = 0;
            }
            MediaRouter.addRouteStatic(this.mDefaultAudioVideo);
            MediaRouter.updateWifiDisplayStatus(this.mDisplayService.getWifiDisplayStatus());
            ((Context)object).registerReceiver(new WifiDisplayStatusChangedReceiver(), new IntentFilter("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED"));
            ((Context)object).registerReceiver(new VolumeChangeReceiver(), new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
            this.mDisplayService.registerDisplayListener(this, this.mHandler);
            object = null;
            try {
                object = object2 = this.mAudioService.startWatchingRoutes(this.mAudioRoutesObserver);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            if (object != null) {
                this.updateAudioRoutes((AudioRoutesInfo)object);
            }
            this.rebindAsUser(UserHandle.myUserId());
            if (this.mSelectedRoute == null) {
                MediaRouter.selectDefaultRouteStatic();
            }
        }

        void updateAudioRoutes(AudioRoutesInfo object) {
            Object object2;
            boolean bl = false;
            int n = 0;
            int n2 = 0;
            if (((AudioRoutesInfo)object).mainType != this.mCurAudioRoutesInfo.mainType) {
                this.mCurAudioRoutesInfo.mainType = ((AudioRoutesInfo)object).mainType;
                n = (((AudioRoutesInfo)object).mainType & 2) == 0 && (((AudioRoutesInfo)object).mainType & 1) == 0 ? ((((AudioRoutesInfo)object).mainType & 4) != 0 ? 17039841 : ((((AudioRoutesInfo)object).mainType & 8) != 0 ? 17039842 : ((((AudioRoutesInfo)object).mainType & 16) != 0 ? 17039844 : 17039840))) : 17039843;
                object2 = this.mDefaultAudioVideo;
                ((RouteInfo)object2).mNameResId = n;
                MediaRouter.dispatchRouteChanged((RouteInfo)object2);
                n = n2;
                if ((((AudioRoutesInfo)object).mainType & 19) != 0) {
                    n = 1;
                }
                bl = true;
            }
            if (!TextUtils.equals(((AudioRoutesInfo)object).bluetoothName, this.mCurAudioRoutesInfo.bluetoothName)) {
                n = 0;
                this.mCurAudioRoutesInfo.bluetoothName = ((AudioRoutesInfo)object).bluetoothName;
                if (this.mCurAudioRoutesInfo.bluetoothName != null) {
                    object2 = this.mBluetoothA2dpRoute;
                    if (object2 == null) {
                        object2 = new RouteInfo(this.mSystemCategory);
                        ((RouteInfo)object2).mName = this.mCurAudioRoutesInfo.bluetoothName;
                        ((RouteInfo)object2).mDescription = this.mResources.getText(17039615);
                        ((RouteInfo)object2).mSupportedTypes = 1;
                        ((RouteInfo)object2).mDeviceType = 3;
                        this.mBluetoothA2dpRoute = object2;
                        MediaRouter.addRouteStatic(this.mBluetoothA2dpRoute);
                    } else {
                        ((RouteInfo)object2).mName = this.mCurAudioRoutesInfo.bluetoothName;
                        MediaRouter.dispatchRouteChanged(this.mBluetoothA2dpRoute);
                    }
                } else {
                    object2 = this.mBluetoothA2dpRoute;
                    if (object2 != null) {
                        MediaRouter.removeRouteStatic((RouteInfo)object2);
                        this.mBluetoothA2dpRoute = null;
                    }
                }
                bl = true;
            }
            if (bl) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Audio routes updated: ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(", a2dp=");
                ((StringBuilder)object2).append(this.isBluetoothA2dpOn());
                Log.v(MediaRouter.TAG, ((StringBuilder)object2).toString());
                object = this.mSelectedRoute;
                if (object == null || object == this.mDefaultAudioVideo || object == this.mBluetoothA2dpRoute) {
                    if (n == 0 && (object = this.mBluetoothA2dpRoute) != null) {
                        MediaRouter.selectRouteStatic(1, (RouteInfo)object, false);
                    } else {
                        MediaRouter.selectRouteStatic(1, this.mDefaultAudioVideo, false);
                    }
                }
            }
        }

        void updateClientState() {
            RouteInfo routeInfo;
            int n;
            ArrayList<MediaRouterClientState.RouteInfo> arrayList = null;
            this.mClientState = null;
            Object object = this.mClient;
            if (object != null) {
                try {
                    this.mClientState = this.mMediaRouterService.getState((IMediaRouterClient)object);
                }
                catch (RemoteException remoteException) {
                    Log.e(MediaRouter.TAG, "Unable to retrieve media router client state.", remoteException);
                }
            }
            if ((object = this.mClientState) != null) {
                arrayList = ((MediaRouterClientState)object).routes;
            }
            int n2 = arrayList != null ? arrayList.size() : 0;
            for (n = 0; n < n2; ++n) {
                object = arrayList.get(n);
                routeInfo = this.findGlobalRoute(((MediaRouterClientState.RouteInfo)object).id);
                if (routeInfo == null) {
                    MediaRouter.addRouteStatic(this.makeGlobalRoute((MediaRouterClientState.RouteInfo)object));
                    continue;
                }
                this.updateGlobalRoute(routeInfo, (MediaRouterClientState.RouteInfo)object);
            }
            n = this.mRoutes.size();
            do {
                int n3;
                block10 : {
                    n3 = n - 1;
                    if (n <= 0) break;
                    routeInfo = this.mRoutes.get(n3);
                    object = routeInfo.mGlobalRouteId;
                    if (object != null) {
                        for (n = 0; n < n2; ++n) {
                            if (!((String)object).equals(arrayList.get((int)n).id)) {
                                continue;
                            }
                            break block10;
                        }
                        MediaRouter.removeRouteStatic(routeInfo);
                    }
                }
                n = n3;
            } while (true);
        }

        void updateDiscoveryRequest() {
            Object object;
            int n;
            boolean bl;
            int n2;
            int n3;
            block20 : {
                int n4;
                block19 : {
                    n3 = 0;
                    n4 = 0;
                    bl = false;
                    n2 = 0;
                    int n5 = this.mCallbacks.size();
                    for (n = 0; n < n5; ++n) {
                        object = this.mCallbacks.get(n);
                        if ((((CallbackInfo)object).flags & 5) != 0) {
                            n3 |= ((CallbackInfo)object).type;
                        } else if ((((CallbackInfo)object).flags & 8) != 0) {
                            n4 |= ((CallbackInfo)object).type;
                        } else {
                            n3 |= ((CallbackInfo)object).type;
                        }
                        int n6 = n2;
                        if ((1 & ((CallbackInfo)object).flags) != 0) {
                            boolean bl2;
                            bl = bl2 = true;
                            n6 = n2;
                            if ((4 & ((CallbackInfo)object).type) != 0) {
                                n6 = 1;
                                bl = bl2;
                            }
                        }
                        n2 = n6;
                    }
                    if (n3 != 0) break block19;
                    n = n3;
                    if (!bl) break block20;
                }
                n = n3 | n4;
            }
            if (this.mCanConfigureWifiDisplays) {
                object = this.mSelectedRoute;
                n3 = n2;
                if (object != null) {
                    n3 = n2;
                    if (((RouteInfo)object).matchesTypes(4)) {
                        n3 = 0;
                    }
                }
                if (n3 != 0) {
                    if (!this.mActivelyScanningWifiDisplays) {
                        this.mActivelyScanningWifiDisplays = true;
                        this.mDisplayService.startWifiDisplayScan();
                    }
                } else if (this.mActivelyScanningWifiDisplays) {
                    this.mActivelyScanningWifiDisplays = false;
                    this.mDisplayService.stopWifiDisplayScan();
                }
            }
            if (n != this.mDiscoveryRequestRouteTypes || bl != this.mDiscoverRequestActiveScan) {
                this.mDiscoveryRequestRouteTypes = n;
                this.mDiscoverRequestActiveScan = bl;
                this.publishClientDiscoveryRequest();
            }
        }

        void updateGlobalRoute(RouteInfo routeInfo, MediaRouterClientState.RouteInfo routeInfo2) {
            int n;
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!Objects.equals(routeInfo.mName, routeInfo2.name)) {
                routeInfo.mName = routeInfo2.name;
                bl = true;
            }
            if (!Objects.equals(routeInfo.mDescription, routeInfo2.description)) {
                routeInfo.mDescription = routeInfo2.description;
                bl = true;
            }
            if ((n = routeInfo.mSupportedTypes) != routeInfo2.supportedTypes) {
                routeInfo.mSupportedTypes = routeInfo2.supportedTypes;
                bl = true;
            }
            if (routeInfo.mEnabled != routeInfo2.enabled) {
                routeInfo.mEnabled = routeInfo2.enabled;
                bl = true;
            }
            if (routeInfo.mRealStatusCode != routeInfo2.statusCode) {
                routeInfo.setRealStatusCode(routeInfo2.statusCode);
                bl = true;
            }
            if (routeInfo.mPlaybackType != routeInfo2.playbackType) {
                routeInfo.mPlaybackType = routeInfo2.playbackType;
                bl = true;
            }
            if (routeInfo.mPlaybackStream != routeInfo2.playbackStream) {
                routeInfo.mPlaybackStream = routeInfo2.playbackStream;
                bl = true;
            }
            boolean bl4 = bl;
            bl = bl2;
            if (routeInfo.mVolume != routeInfo2.volume) {
                routeInfo.mVolume = routeInfo2.volume;
                bl4 = true;
                bl = true;
            }
            if (routeInfo.mVolumeMax != routeInfo2.volumeMax) {
                routeInfo.mVolumeMax = routeInfo2.volumeMax;
                bl4 = true;
                bl = true;
            }
            if (routeInfo.mVolumeHandling != routeInfo2.volumeHandling) {
                routeInfo.mVolumeHandling = routeInfo2.volumeHandling;
                bl4 = true;
                bl = true;
            }
            bl2 = bl4;
            bl4 = bl3;
            if (routeInfo.mPresentationDisplayId != routeInfo2.presentationDisplayId) {
                routeInfo.mPresentationDisplayId = routeInfo2.presentationDisplayId;
                routeInfo.updatePresentationDisplay();
                bl2 = true;
                bl4 = true;
            }
            if (bl2) {
                MediaRouter.dispatchRouteChanged(routeInfo, n);
            }
            if (bl) {
                MediaRouter.dispatchRouteVolumeChanged(routeInfo);
            }
            if (bl4) {
                MediaRouter.dispatchRoutePresentationDisplayChanged(routeInfo);
            }
        }

        void updateSelectedRouteForId(String string2) {
            RouteInfo routeInfo = this.isBluetoothA2dpOn() ? this.mBluetoothA2dpRoute : this.mDefaultAudioVideo;
            int n = this.mRoutes.size();
            for (int i = 0; i < n; ++i) {
                RouteInfo routeInfo2 = this.mRoutes.get(i);
                if (!TextUtils.equals(routeInfo2.mGlobalRouteId, string2)) continue;
                routeInfo = routeInfo2;
            }
            if (routeInfo != this.mSelectedRoute) {
                MediaRouter.selectRouteStatic(routeInfo.mSupportedTypes, routeInfo, false);
            }
        }

        final class Client
        extends IMediaRouterClient.Stub {
            Client() {
            }

            public /* synthetic */ void lambda$onSelectedRouteChanged$0$MediaRouter$Static$Client(String string2) {
                if (this == Static.this.mClient) {
                    Static.this.updateSelectedRouteForId(string2);
                }
            }

            @Override
            public void onRestoreRoute() {
                Static.this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        Object object = Client.this;
                        if (object == object.Static.this.mClient && Static.this.mSelectedRoute != null && (Static.this.mSelectedRoute == Static.this.mDefaultAudioVideo || Static.this.mSelectedRoute == Static.this.mBluetoothA2dpRoute)) {
                            if (DEBUG) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("onRestoreRoute() : route=");
                                ((StringBuilder)object).append(Static.this.mSelectedRoute);
                                Log.d(MediaRouter.TAG, ((StringBuilder)object).toString());
                            }
                            Static.this.mSelectedRoute.select();
                            return;
                        }
                    }
                });
            }

            @Override
            public void onSelectedRouteChanged(String string2) {
                Static.this.mHandler.post(new _$$Lambda$MediaRouter$Static$Client$xd63sG7fVJYYu49J1xqN8JYQvo4(this, string2));
            }

            @Override
            public void onStateChanged() {
                Static.this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        Client client = Client.this;
                        if (client == client.Static.this.mClient) {
                            Static.this.updateClientState();
                        }
                    }
                });
            }

        }

    }

    public static class UserRouteInfo
    extends RouteInfo {
        RemoteControlClient mRcc;
        SessionVolumeProvider mSvp;

        UserRouteInfo(RouteCategory routeCategory) {
            super(routeCategory);
            this.mSupportedTypes = 8388608;
            this.mPlaybackType = 1;
            this.mVolumeHandling = 0;
        }

        private void configureSessionVolume() {
            Object object = this.mRcc;
            if (object == null) {
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("No Rcc to configure volume for route ");
                    ((StringBuilder)object).append((Object)this.getName());
                    Log.d(MediaRouter.TAG, ((StringBuilder)object).toString());
                }
                return;
            }
            if ((object = ((RemoteControlClient)object).getMediaSession()) == null) {
                if (DEBUG) {
                    Log.d(MediaRouter.TAG, "Rcc has no session to configure volume");
                }
                return;
            }
            if (this.mPlaybackType == 1) {
                int n = 0;
                if (this.mVolumeHandling == 1) {
                    n = 2;
                }
                SessionVolumeProvider sessionVolumeProvider = this.mSvp;
                if (sessionVolumeProvider == null || sessionVolumeProvider.getVolumeControl() != n || this.mSvp.getMaxVolume() != this.mVolumeMax) {
                    this.mSvp = new SessionVolumeProvider(n, this.mVolumeMax, this.mVolume);
                    ((MediaSession)object).setPlaybackToRemote(this.mSvp);
                }
            } else {
                AudioAttributes.Builder builder = new AudioAttributes.Builder();
                builder.setLegacyStreamType(this.mPlaybackStream);
                ((MediaSession)object).setPlaybackToLocal(builder.build());
                this.mSvp = null;
            }
        }

        private void updatePlaybackInfoOnRcc() {
            this.configureSessionVolume();
        }

        public RemoteControlClient getRemoteControlClient() {
            return this.mRcc;
        }

        @Override
        public void requestSetVolume(int n) {
            if (this.mVolumeHandling == 1) {
                if (this.mVcb == null) {
                    Log.e(MediaRouter.TAG, "Cannot requestSetVolume on user route - no volume callback set");
                    return;
                }
                this.mVcb.vcb.onVolumeSetRequest(this, n);
            }
        }

        @Override
        public void requestUpdateVolume(int n) {
            if (this.mVolumeHandling == 1) {
                if (this.mVcb == null) {
                    Log.e(MediaRouter.TAG, "Cannot requestChangeVolume on user route - no volumec callback set");
                    return;
                }
                this.mVcb.vcb.onVolumeUpdateRequest(this, n);
            }
        }

        public void setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            this.routeUpdated();
        }

        public void setIconDrawable(Drawable drawable2) {
            this.mIcon = drawable2;
        }

        public void setIconResource(int n) {
            this.setIconDrawable(MediaRouter.sStatic.mResources.getDrawable(n));
        }

        public void setName(int n) {
            this.mNameResId = n;
            this.mName = null;
            this.routeUpdated();
        }

        public void setName(CharSequence charSequence) {
            this.mNameResId = 0;
            this.mName = charSequence;
            this.routeUpdated();
        }

        public void setPlaybackStream(int n) {
            if (this.mPlaybackStream != n) {
                this.mPlaybackStream = n;
                this.configureSessionVolume();
            }
        }

        public void setPlaybackType(int n) {
            if (this.mPlaybackType != n) {
                this.mPlaybackType = n;
                this.configureSessionVolume();
            }
        }

        public void setRemoteControlClient(RemoteControlClient remoteControlClient) {
            this.mRcc = remoteControlClient;
            this.updatePlaybackInfoOnRcc();
        }

        public void setStatus(CharSequence charSequence) {
            this.setStatusInt(charSequence);
        }

        public void setVolume(int n) {
            if (this.mVolume != (n = Math.max(0, Math.min(n, this.getVolumeMax())))) {
                this.mVolume = n;
                SessionVolumeProvider sessionVolumeProvider = this.mSvp;
                if (sessionVolumeProvider != null) {
                    sessionVolumeProvider.setCurrentVolume(this.mVolume);
                }
                MediaRouter.dispatchRouteVolumeChanged(this);
                if (this.mGroup != null) {
                    this.mGroup.memberVolumeChanged(this);
                }
            }
        }

        public void setVolumeCallback(VolumeCallback volumeCallback) {
            this.mVcb = new VolumeCallbackInfo(volumeCallback, this);
        }

        public void setVolumeHandling(int n) {
            if (this.mVolumeHandling != n) {
                this.mVolumeHandling = n;
                this.configureSessionVolume();
            }
        }

        public void setVolumeMax(int n) {
            if (this.mVolumeMax != n) {
                this.mVolumeMax = n;
                this.configureSessionVolume();
            }
        }

        class SessionVolumeProvider
        extends VolumeProvider {
            SessionVolumeProvider(int n, int n2, int n3) {
                super(n, n2, n3);
            }

            @Override
            public void onAdjustVolume(final int n) {
                MediaRouter.sStatic.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        if (UserRouteInfo.this.mVcb != null) {
                            UserRouteInfo.this.mVcb.vcb.onVolumeUpdateRequest(UserRouteInfo.this.mVcb.route, n);
                        }
                    }
                });
            }

            @Override
            public void onSetVolumeTo(final int n) {
                MediaRouter.sStatic.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        if (UserRouteInfo.this.mVcb != null) {
                            UserRouteInfo.this.mVcb.vcb.onVolumeSetRequest(UserRouteInfo.this.mVcb.route, n);
                        }
                    }
                });
            }

        }

    }

    public static abstract class VolumeCallback {
        public abstract void onVolumeSetRequest(RouteInfo var1, int var2);

        public abstract void onVolumeUpdateRequest(RouteInfo var1, int var2);
    }

    static class VolumeCallbackInfo {
        public final RouteInfo route;
        public final VolumeCallback vcb;

        public VolumeCallbackInfo(VolumeCallback volumeCallback, RouteInfo routeInfo) {
            this.vcb = volumeCallback;
            this.route = routeInfo;
        }
    }

    static class VolumeChangeReceiver
    extends BroadcastReceiver {
        VolumeChangeReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3) {
                    return;
                }
                int n = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
                if (n != intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0)) {
                    MediaRouter.systemVolumeChanged(n);
                }
            }
        }
    }

    static class WifiDisplayStatusChangedReceiver
    extends BroadcastReceiver {
        WifiDisplayStatusChangedReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED")) {
                MediaRouter.updateWifiDisplayStatus((WifiDisplayStatus)intent.getParcelableExtra("android.hardware.display.extra.WIFI_DISPLAY_STATUS"));
            }
        }
    }

}

