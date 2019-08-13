/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.content.Context;
import android.os.CpuUsageInfo;
import android.os.IHardwarePropertiesManager;
import android.os.RemoteException;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class HardwarePropertiesManager {
    public static final int DEVICE_TEMPERATURE_BATTERY = 2;
    public static final int DEVICE_TEMPERATURE_CPU = 0;
    public static final int DEVICE_TEMPERATURE_GPU = 1;
    public static final int DEVICE_TEMPERATURE_SKIN = 3;
    private static final String TAG = HardwarePropertiesManager.class.getSimpleName();
    public static final int TEMPERATURE_CURRENT = 0;
    public static final int TEMPERATURE_SHUTDOWN = 2;
    public static final int TEMPERATURE_THROTTLING = 1;
    public static final int TEMPERATURE_THROTTLING_BELOW_VR_MIN = 3;
    public static final float UNDEFINED_TEMPERATURE = -3.4028235E38f;
    private final Context mContext;
    private final IHardwarePropertiesManager mService;

    public HardwarePropertiesManager(Context context, IHardwarePropertiesManager iHardwarePropertiesManager) {
        this.mContext = context;
        this.mService = iHardwarePropertiesManager;
    }

    public CpuUsageInfo[] getCpuUsages() {
        try {
            CpuUsageInfo[] arrcpuUsageInfo = this.mService.getCpuUsages(this.mContext.getOpPackageName());
            return arrcpuUsageInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public float[] getDeviceTemperatures(int n, int n2) {
        if (n != 0 && n != 1 && n != 2 && n != 3) {
            Log.w(TAG, "Unknown device temperature type.");
            return new float[0];
        }
        if (n2 != 0 && n2 != 1 && n2 != 2 && n2 != 3) {
            Log.w(TAG, "Unknown device temperature source.");
            return new float[0];
        }
        try {
            float[] arrf = this.mService.getDeviceTemperatures(this.mContext.getOpPackageName(), n, n2);
            return arrf;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public float[] getFanSpeeds() {
        try {
            float[] arrf = this.mService.getFanSpeeds(this.mContext.getOpPackageName());
            return arrf;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DeviceTemperatureType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TemperatureSource {
    }

}

