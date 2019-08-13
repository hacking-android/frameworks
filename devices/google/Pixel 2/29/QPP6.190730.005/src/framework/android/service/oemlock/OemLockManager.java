/*
 * Decompiled with CFR 0.145.
 */
package android.service.oemlock;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.service.oemlock.IOemLockService;

@SystemApi
public class OemLockManager {
    private IOemLockService mService;

    public OemLockManager(IOemLockService iOemLockService) {
        this.mService = iOemLockService;
    }

    public String getLockName() {
        try {
            String string2 = this.mService.getLockName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceOemUnlocked() {
        try {
            boolean bl = this.mService.isDeviceOemUnlocked();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowed() {
        try {
            boolean bl = this.mService.isOemUnlockAllowed();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowedByCarrier() {
        try {
            boolean bl = this.mService.isOemUnlockAllowedByCarrier();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowedByUser() {
        try {
            boolean bl = this.mService.isOemUnlockAllowedByUser();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOemUnlockAllowedByCarrier(boolean bl, byte[] arrby) {
        try {
            this.mService.setOemUnlockAllowedByCarrier(bl, arrby);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOemUnlockAllowedByUser(boolean bl) {
        try {
            this.mService.setOemUnlockAllowedByUser(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

