/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioMetadata;
import java.util.List;
import java.util.Map;

@SystemApi
public abstract class RadioTuner {
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_UP = 0;
    @Deprecated
    public static final int ERROR_BACKGROUND_SCAN_FAILED = 6;
    @Deprecated
    public static final int ERROR_BACKGROUND_SCAN_UNAVAILABLE = 5;
    @Deprecated
    public static final int ERROR_CANCELLED = 2;
    @Deprecated
    public static final int ERROR_CONFIG = 4;
    @Deprecated
    public static final int ERROR_HARDWARE_FAILURE = 0;
    @Deprecated
    public static final int ERROR_SCAN_TIMEOUT = 3;
    @Deprecated
    public static final int ERROR_SERVER_DIED = 1;

    public abstract int cancel();

    public abstract void cancelAnnouncement();

    public abstract void close();

    @Deprecated
    public abstract int getConfiguration(RadioManager.BandConfig[] var1);

    public ProgramList getDynamicProgramList(ProgramList.Filter filter) {
        return null;
    }

    public abstract Bitmap getMetadataImage(int var1);

    public abstract boolean getMute();

    public Map<String, String> getParameters(List<String> list) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public abstract int getProgramInformation(RadioManager.ProgramInfo[] var1);

    @Deprecated
    public abstract List<RadioManager.ProgramInfo> getProgramList(Map<String, String> var1);

    public abstract boolean hasControl();

    @Deprecated
    public abstract boolean isAnalogForced();

    @Deprecated
    public abstract boolean isAntennaConnected();

    public boolean isConfigFlagSet(int n) {
        throw new UnsupportedOperationException();
    }

    public boolean isConfigFlagSupported(int n) {
        return false;
    }

    public abstract int scan(int var1, boolean var2);

    @Deprecated
    public abstract void setAnalogForced(boolean var1);

    public void setConfigFlag(int n, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public abstract int setConfiguration(RadioManager.BandConfig var1);

    public abstract int setMute(boolean var1);

    public Map<String, String> setParameters(Map<String, String> map) {
        throw new UnsupportedOperationException();
    }

    public abstract boolean startBackgroundScan();

    public abstract int step(int var1, boolean var2);

    @Deprecated
    public abstract int tune(int var1, int var2);

    public abstract void tune(ProgramSelector var1);

    public static abstract class Callback {
        public void onAntennaState(boolean bl) {
        }

        public void onBackgroundScanAvailabilityChange(boolean bl) {
        }

        public void onBackgroundScanComplete() {
        }

        @Deprecated
        public void onConfigurationChanged(RadioManager.BandConfig bandConfig) {
        }

        public void onControlChanged(boolean bl) {
        }

        public void onEmergencyAnnouncement(boolean bl) {
        }

        public void onError(int n) {
        }

        @Deprecated
        public void onMetadataChanged(RadioMetadata radioMetadata) {
        }

        public void onParametersUpdated(Map<String, String> map) {
        }

        public void onProgramInfoChanged(RadioManager.ProgramInfo programInfo) {
        }

        public void onProgramListChanged() {
        }

        public void onTrafficAnnouncement(boolean bl) {
        }

        public void onTuneFailed(int n, ProgramSelector programSelector) {
        }
    }

}

