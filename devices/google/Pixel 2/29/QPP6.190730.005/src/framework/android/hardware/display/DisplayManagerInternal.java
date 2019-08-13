/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.hardware.SensorManager;
import android.hardware.display.DisplayedContentSample;
import android.hardware.display.DisplayedContentSamplingAttributes;
import android.os.Handler;
import android.util.IntArray;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;

public abstract class DisplayManagerInternal {
    public abstract DisplayInfo getDisplayInfo(int var1);

    public abstract DisplayedContentSample getDisplayedContentSample(int var1, long var2, long var4);

    public abstract DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int var1);

    public abstract void getNonOverrideDisplayInfo(int var1, DisplayInfo var2);

    public abstract void initPowerManagement(DisplayPowerCallbacks var1, Handler var2, SensorManager var3);

    public abstract boolean isProximitySensorAvailable();

    public abstract void onOverlayChanged();

    public abstract void performTraversal(SurfaceControl.Transaction var1);

    public abstract void persistBrightnessTrackerState();

    public abstract void registerDisplayTransactionListener(DisplayTransactionListener var1);

    public abstract boolean requestPowerState(DisplayPowerRequest var1, boolean var2);

    public abstract SurfaceControl.ScreenshotGraphicBuffer screenshot(int var1);

    public abstract void setDisplayAccessUIDs(SparseArray<IntArray> var1);

    public abstract void setDisplayInfoOverrideFromWindowManager(int var1, DisplayInfo var2);

    public abstract void setDisplayOffsets(int var1, int var2, int var3);

    public abstract void setDisplayProperties(int var1, boolean var2, float var3, int var4, boolean var5);

    public abstract void setDisplayScalingDisabled(int var1, boolean var2);

    public abstract boolean setDisplayedContentSamplingEnabled(int var1, boolean var2, int var3, int var4);

    public abstract void unregisterDisplayTransactionListener(DisplayTransactionListener var1);

    public static interface DisplayPowerCallbacks {
        public void acquireSuspendBlocker();

        public void onDisplayStateChange(int var1);

        public void onProximityNegative();

        public void onProximityPositive();

        public void onStateChanged();

        public void releaseSuspendBlocker();
    }

    public static final class DisplayPowerRequest {
        public static final int POLICY_BRIGHT = 3;
        public static final int POLICY_DIM = 2;
        public static final int POLICY_DOZE = 1;
        public static final int POLICY_OFF = 0;
        public static final int POLICY_VR = 4;
        public boolean blockScreenOn;
        public boolean boostScreenBrightness;
        public int dozeScreenBrightness;
        public int dozeScreenState;
        public boolean lowPowerMode;
        public int policy;
        public float screenAutoBrightnessAdjustmentOverride;
        public int screenBrightnessOverride;
        public float screenLowPowerBrightnessFactor;
        public boolean useAutoBrightness;
        public boolean useProximitySensor;

        public DisplayPowerRequest() {
            this.policy = 3;
            this.useProximitySensor = false;
            this.screenBrightnessOverride = -1;
            this.useAutoBrightness = false;
            this.screenAutoBrightnessAdjustmentOverride = Float.NaN;
            this.screenLowPowerBrightnessFactor = 0.5f;
            this.blockScreenOn = false;
            this.dozeScreenBrightness = -1;
            this.dozeScreenState = 0;
        }

        public DisplayPowerRequest(DisplayPowerRequest displayPowerRequest) {
            this.copyFrom(displayPowerRequest);
        }

        private boolean floatEquals(float f, float f2) {
            boolean bl = f == f2 || Float.isNaN(f) && Float.isNaN(f2);
            return bl;
        }

        public static String policyToString(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                return Integer.toString(n);
                            }
                            return "VR";
                        }
                        return "BRIGHT";
                    }
                    return "DIM";
                }
                return "DOZE";
            }
            return "OFF";
        }

        public void copyFrom(DisplayPowerRequest displayPowerRequest) {
            this.policy = displayPowerRequest.policy;
            this.useProximitySensor = displayPowerRequest.useProximitySensor;
            this.screenBrightnessOverride = displayPowerRequest.screenBrightnessOverride;
            this.useAutoBrightness = displayPowerRequest.useAutoBrightness;
            this.screenAutoBrightnessAdjustmentOverride = displayPowerRequest.screenAutoBrightnessAdjustmentOverride;
            this.screenLowPowerBrightnessFactor = displayPowerRequest.screenLowPowerBrightnessFactor;
            this.blockScreenOn = displayPowerRequest.blockScreenOn;
            this.lowPowerMode = displayPowerRequest.lowPowerMode;
            this.boostScreenBrightness = displayPowerRequest.boostScreenBrightness;
            this.dozeScreenBrightness = displayPowerRequest.dozeScreenBrightness;
            this.dozeScreenState = displayPowerRequest.dozeScreenState;
        }

        public boolean equals(DisplayPowerRequest displayPowerRequest) {
            boolean bl = displayPowerRequest != null && this.policy == displayPowerRequest.policy && this.useProximitySensor == displayPowerRequest.useProximitySensor && this.screenBrightnessOverride == displayPowerRequest.screenBrightnessOverride && this.useAutoBrightness == displayPowerRequest.useAutoBrightness && this.floatEquals(this.screenAutoBrightnessAdjustmentOverride, displayPowerRequest.screenAutoBrightnessAdjustmentOverride) && this.screenLowPowerBrightnessFactor == displayPowerRequest.screenLowPowerBrightnessFactor && this.blockScreenOn == displayPowerRequest.blockScreenOn && this.lowPowerMode == displayPowerRequest.lowPowerMode && this.boostScreenBrightness == displayPowerRequest.boostScreenBrightness && this.dozeScreenBrightness == displayPowerRequest.dozeScreenBrightness && this.dozeScreenState == displayPowerRequest.dozeScreenState;
            return bl;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof DisplayPowerRequest && this.equals((DisplayPowerRequest)object);
            return bl;
        }

        public int hashCode() {
            return 0;
        }

        public boolean isBrightOrDim() {
            int n = this.policy;
            boolean bl = n == 3 || n == 2;
            return bl;
        }

        public boolean isVr() {
            boolean bl = this.policy == 4;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("policy=");
            stringBuilder.append(DisplayPowerRequest.policyToString(this.policy));
            stringBuilder.append(", useProximitySensor=");
            stringBuilder.append(this.useProximitySensor);
            stringBuilder.append(", screenBrightnessOverride=");
            stringBuilder.append(this.screenBrightnessOverride);
            stringBuilder.append(", useAutoBrightness=");
            stringBuilder.append(this.useAutoBrightness);
            stringBuilder.append(", screenAutoBrightnessAdjustmentOverride=");
            stringBuilder.append(this.screenAutoBrightnessAdjustmentOverride);
            stringBuilder.append(", screenLowPowerBrightnessFactor=");
            stringBuilder.append(this.screenLowPowerBrightnessFactor);
            stringBuilder.append(", blockScreenOn=");
            stringBuilder.append(this.blockScreenOn);
            stringBuilder.append(", lowPowerMode=");
            stringBuilder.append(this.lowPowerMode);
            stringBuilder.append(", boostScreenBrightness=");
            stringBuilder.append(this.boostScreenBrightness);
            stringBuilder.append(", dozeScreenBrightness=");
            stringBuilder.append(this.dozeScreenBrightness);
            stringBuilder.append(", dozeScreenState=");
            stringBuilder.append(Display.stateToString(this.dozeScreenState));
            return stringBuilder.toString();
        }
    }

    public static interface DisplayTransactionListener {
        public void onDisplayTransaction(SurfaceControl.Transaction var1);
    }

}

