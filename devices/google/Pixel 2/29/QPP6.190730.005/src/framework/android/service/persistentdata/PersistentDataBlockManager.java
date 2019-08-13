/*
 * Decompiled with CFR 0.145.
 */
package android.service.persistentdata;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.os.RemoteException;
import android.service.persistentdata.IPersistentDataBlockService;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public class PersistentDataBlockManager {
    public static final int FLASH_LOCK_LOCKED = 1;
    public static final int FLASH_LOCK_UNKNOWN = -1;
    public static final int FLASH_LOCK_UNLOCKED = 0;
    private static final String TAG = PersistentDataBlockManager.class.getSimpleName();
    private IPersistentDataBlockService sService;

    public PersistentDataBlockManager(IPersistentDataBlockService iPersistentDataBlockService) {
        this.sService = iPersistentDataBlockService;
    }

    public int getDataBlockSize() {
        try {
            int n = this.sService.getDataBlockSize();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getFlashLockState() {
        try {
            int n = this.sService.getFlashLockState();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SuppressLint(value={"Doclava125"})
    public long getMaximumDataBlockSize() {
        try {
            long l = this.sService.getMaximumDataBlockSize();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean getOemUnlockEnabled() {
        try {
            boolean bl = this.sService.getOemUnlockEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SuppressLint(value={"Doclava125"})
    public byte[] read() {
        try {
            byte[] arrby = this.sService.read();
            return arrby;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOemUnlockEnabled(boolean bl) {
        try {
            this.sService.setOemUnlockEnabled(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void wipe() {
        try {
            this.sService.wipe();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SuppressLint(value={"Doclava125"})
    public int write(byte[] arrby) {
        try {
            int n = this.sService.write(arrby);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FlashLockState {
    }

}

