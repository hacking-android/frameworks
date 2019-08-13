/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.display.AmbientBrightnessDayStats;
import android.hardware.display.BrightnessChangeEvent;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.WifiDisplayStatus;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Display;
import android.view.Surface;
import java.util.ArrayList;
import java.util.List;

public final class DisplayManager {
    @UnsupportedAppUsage
    public static final String ACTION_WIFI_DISPLAY_STATUS_CHANGED = "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED";
    private static final boolean DEBUG = false;
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    @UnsupportedAppUsage
    public static final String EXTRA_WIFI_DISPLAY_STATUS = "android.hardware.display.extra.WIFI_DISPLAY_STATUS";
    private static final String TAG = "DisplayManager";
    public static final int VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR = 16;
    public static final int VIRTUAL_DISPLAY_FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 32;
    public static final int VIRTUAL_DISPLAY_FLAG_DESTROY_CONTENT_ON_REMOVAL = 256;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY = 8;
    public static final int VIRTUAL_DISPLAY_FLAG_PRESENTATION = 2;
    public static final int VIRTUAL_DISPLAY_FLAG_PUBLIC = 1;
    public static final int VIRTUAL_DISPLAY_FLAG_ROTATES_WITH_CONTENT = 128;
    public static final int VIRTUAL_DISPLAY_FLAG_SECURE = 4;
    public static final int VIRTUAL_DISPLAY_FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 512;
    public static final int VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH = 64;
    private final Context mContext;
    private final SparseArray<Display> mDisplays = new SparseArray();
    private final DisplayManagerGlobal mGlobal;
    private final Object mLock = new Object();
    private final ArrayList<Display> mTempDisplays = new ArrayList();

    public DisplayManager(Context context) {
        this.mContext = context;
        this.mGlobal = DisplayManagerGlobal.getInstance();
    }

    private void addAllDisplaysLocked(ArrayList<Display> arrayList, int[] arrn) {
        for (int i = 0; i < arrn.length; ++i) {
            Display display = this.getOrCreateDisplayLocked(arrn[i], true);
            if (display == null) continue;
            arrayList.add(display);
        }
    }

    private void addPresentationDisplaysLocked(ArrayList<Display> arrayList, int[] arrn, int n) {
        for (int i = 0; i < arrn.length; ++i) {
            Display display = this.getOrCreateDisplayLocked(arrn[i], true);
            if (display == null || (display.getFlags() & 8) == 0 || display.getType() != n) continue;
            arrayList.add(display);
        }
    }

    private Display getOrCreateDisplayLocked(int n, boolean bl) {
        Object object;
        Display display = this.mDisplays.get(n);
        if (display == null) {
            object = this.mContext.getDisplayId() == n ? this.mContext : this.mContext.getApplicationContext();
            display = this.mGlobal.getCompatibleDisplay(n, ((Context)object).getResources());
            object = display;
            if (display != null) {
                this.mDisplays.put(n, display);
                object = display;
            }
        } else {
            object = display;
            if (!bl) {
                object = display;
                if (!display.isValid()) {
                    object = null;
                }
            }
        }
        return object;
    }

    @UnsupportedAppUsage
    public void connectWifiDisplay(String string2) {
        this.mGlobal.connectWifiDisplay(string2);
    }

    public VirtualDisplay createVirtualDisplay(MediaProjection mediaProjection, String string2, int n, int n2, int n3, Surface surface, int n4, VirtualDisplay.Callback callback, Handler handler, String string3) {
        return this.mGlobal.createVirtualDisplay(this.mContext, mediaProjection, string2, n, n2, n3, surface, n4, callback, handler, string3);
    }

    public VirtualDisplay createVirtualDisplay(String string2, int n, int n2, int n3, Surface surface, int n4) {
        return this.createVirtualDisplay(string2, n, n2, n3, surface, n4, null, null);
    }

    public VirtualDisplay createVirtualDisplay(String string2, int n, int n2, int n3, Surface surface, int n4, VirtualDisplay.Callback callback, Handler handler) {
        return this.createVirtualDisplay(null, string2, n, n2, n3, surface, n4, callback, handler, null);
    }

    @UnsupportedAppUsage
    public void disconnectWifiDisplay() {
        this.mGlobal.disconnectWifiDisplay();
    }

    @UnsupportedAppUsage
    public void forgetWifiDisplay(String string2) {
        this.mGlobal.forgetWifiDisplay(string2);
    }

    @SystemApi
    public List<AmbientBrightnessDayStats> getAmbientBrightnessStats() {
        return this.mGlobal.getAmbientBrightnessStats();
    }

    @SystemApi
    public BrightnessConfiguration getBrightnessConfiguration() {
        return this.getBrightnessConfigurationForUser(this.mContext.getUserId());
    }

    public BrightnessConfiguration getBrightnessConfigurationForUser(int n) {
        return this.mGlobal.getBrightnessConfigurationForUser(n);
    }

    @SystemApi
    public List<BrightnessChangeEvent> getBrightnessEvents() {
        return this.mGlobal.getBrightnessEvents(this.mContext.getOpPackageName());
    }

    @SystemApi
    public BrightnessConfiguration getDefaultBrightnessConfiguration() {
        return this.mGlobal.getDefaultBrightnessConfiguration();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Display getDisplay(int n) {
        Object object = this.mLock;
        synchronized (object) {
            return this.getOrCreateDisplayLocked(n, false);
        }
    }

    public Display[] getDisplays() {
        return this.getDisplays(null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Display[] getDisplays(String var1_1) {
        var2_3 = this.mGlobal.getDisplayIds();
        var3_4 = this.mLock;
        // MONITORENTER : var3_4
        if (var1_1 != null) ** GOTO lbl8
        try {
            block6 : {
                this.addAllDisplaysLocked(this.mTempDisplays, var2_3);
                break block6;
lbl8: // 1 sources:
                if (var1_1.equals("android.hardware.display.category.PRESENTATION")) {
                    this.addPresentationDisplaysLocked(this.mTempDisplays, var2_3, 3);
                    this.addPresentationDisplaysLocked(this.mTempDisplays, var2_3, 2);
                    this.addPresentationDisplaysLocked(this.mTempDisplays, var2_3, 4);
                    this.addPresentationDisplaysLocked(this.mTempDisplays, var2_3, 5);
                }
            }
            var1_1 = this.mTempDisplays.toArray(new Display[this.mTempDisplays.size()]);
            return var1_1;
        }
        finally {
            this.mTempDisplays.clear();
        }
    }

    @SystemApi
    public Pair<float[], float[]> getMinimumBrightnessCurve() {
        return this.mGlobal.getMinimumBrightnessCurve();
    }

    @SystemApi
    public Point getStableDisplaySize() {
        return this.mGlobal.getStableDisplaySize();
    }

    @UnsupportedAppUsage
    public WifiDisplayStatus getWifiDisplayStatus() {
        return this.mGlobal.getWifiDisplayStatus();
    }

    @UnsupportedAppUsage
    public void pauseWifiDisplay() {
        this.mGlobal.pauseWifiDisplay();
    }

    public void registerDisplayListener(DisplayListener displayListener, Handler handler) {
        this.mGlobal.registerDisplayListener(displayListener, handler);
    }

    @UnsupportedAppUsage
    public void renameWifiDisplay(String string2, String string3) {
        this.mGlobal.renameWifiDisplay(string2, string3);
    }

    @UnsupportedAppUsage
    public void resumeWifiDisplay() {
        this.mGlobal.resumeWifiDisplay();
    }

    @SystemApi
    public void setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration) {
        this.setBrightnessConfigurationForUser(brightnessConfiguration, this.mContext.getUserId(), this.mContext.getPackageName());
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int n, String string2) {
        this.mGlobal.setBrightnessConfigurationForUser(brightnessConfiguration, n, string2);
    }

    @SystemApi
    public void setSaturationLevel(float f) {
        if (!(f < 0.0f) && !(f > 1.0f)) {
            this.mContext.getSystemService(ColorDisplayManager.class).setSaturationLevel(Math.round(100.0f * f));
            return;
        }
        throw new IllegalArgumentException("Saturation level must be between 0 and 1");
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mGlobal.setTemporaryAutoBrightnessAdjustment(f);
    }

    public void setTemporaryBrightness(int n) {
        this.mGlobal.setTemporaryBrightness(n);
    }

    @UnsupportedAppUsage
    public void startWifiDisplayScan() {
        this.mGlobal.startWifiDisplayScan();
    }

    @UnsupportedAppUsage
    public void stopWifiDisplayScan() {
        this.mGlobal.stopWifiDisplayScan();
    }

    public void unregisterDisplayListener(DisplayListener displayListener) {
        this.mGlobal.unregisterDisplayListener(displayListener);
    }

    public static interface DisplayListener {
        public void onDisplayAdded(int var1);

        public void onDisplayChanged(int var1);

        public void onDisplayRemoved(int var1);
    }

}

