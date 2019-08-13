/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.content.Context;
import android.os.IDeviceIdleController;
import android.os.RemoteException;

public class DeviceIdleManager {
    private final Context mContext;
    private final IDeviceIdleController mService;

    public DeviceIdleManager(Context context, IDeviceIdleController iDeviceIdleController) {
        this.mContext = context;
        this.mService = iDeviceIdleController;
    }

    public String[] getSystemPowerWhitelist() {
        try {
            String[] arrstring = this.mService.getSystemPowerWhitelist();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return new String[0];
        }
    }

    public String[] getSystemPowerWhitelistExceptIdle() {
        try {
            String[] arrstring = this.mService.getSystemPowerWhitelistExceptIdle();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return new String[0];
        }
    }
}

