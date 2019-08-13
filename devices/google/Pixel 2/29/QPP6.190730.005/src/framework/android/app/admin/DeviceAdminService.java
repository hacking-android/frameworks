/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.Service;
import android.app.admin.IDeviceAdminService;
import android.content.Intent;
import android.os.IBinder;

public class DeviceAdminService
extends Service {
    private final IDeviceAdminServiceImpl mImpl = new IDeviceAdminServiceImpl();

    @Override
    public final IBinder onBind(Intent intent) {
        return this.mImpl.asBinder();
    }

    private class IDeviceAdminServiceImpl
    extends IDeviceAdminService.Stub {
        private IDeviceAdminServiceImpl() {
        }
    }

}

