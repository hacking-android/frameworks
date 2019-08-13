/*
 * Decompiled with CFR 0.145.
 */
package android.os.image;

import android.gsi.GsiProgress;
import android.os.RemoteException;
import android.os.image.IDynamicSystemService;

public class DynamicSystemManager {
    private static final String TAG = "DynamicSystemManager";
    private final IDynamicSystemService mService;

    public DynamicSystemManager(IDynamicSystemService iDynamicSystemService) {
        this.mService = iDynamicSystemService;
    }

    public boolean abort() {
        try {
            boolean bl = this.mService.abort();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public GsiProgress getInstallationProgress() {
        try {
            GsiProgress gsiProgress = this.mService.getInstallationProgress();
            return gsiProgress;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public boolean isEnabled() {
        try {
            boolean bl = this.mService.isEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public boolean isInUse() {
        try {
            boolean bl = this.mService.isInUse();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public boolean isInstalled() {
        try {
            boolean bl = this.mService.isInstalled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public boolean remove() {
        try {
            boolean bl = this.mService.remove();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public boolean setEnable(boolean bl) {
        try {
            bl = this.mService.setEnable(bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public Session startInstallation(long l, long l2) {
        try {
            if (this.mService.startInstallation(l, l2)) {
                Session session = new Session();
                return session;
            }
            return null;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.toString());
        }
    }

    public class Session {
        private Session() {
        }

        public boolean commit() {
            try {
                boolean bl = DynamicSystemManager.this.mService.commit();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException.toString());
            }
        }

        public boolean write(byte[] arrby) {
            try {
                boolean bl = DynamicSystemManager.this.mService.write(arrby);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException.toString());
            }
        }
    }

}

