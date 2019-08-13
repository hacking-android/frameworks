/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import android.annotation.UnsupportedAppUsage;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.gatekeeper.IGateKeeperService;

public abstract class GateKeeper {
    public static final long INVALID_SECURE_USER_ID = 0L;

    private GateKeeper() {
    }

    @UnsupportedAppUsage
    public static long getSecureUserId() throws IllegalStateException {
        try {
            long l = GateKeeper.getService().getSecureUserId(UserHandle.myUserId());
            return l;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException("Failed to obtain secure user ID from gatekeeper", remoteException);
        }
    }

    public static IGateKeeperService getService() {
        IGateKeeperService iGateKeeperService = IGateKeeperService.Stub.asInterface(ServiceManager.getService("android.service.gatekeeper.IGateKeeperService"));
        if (iGateKeeperService != null) {
            return iGateKeeperService;
        }
        throw new IllegalStateException("Gatekeeper service not available");
    }
}

