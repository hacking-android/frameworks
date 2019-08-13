/*
 * Decompiled with CFR 0.145.
 */
package android.content.om;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import java.util.List;

@SystemApi
public class OverlayManager {
    private final Context mContext;
    private final IOverlayManager mService;

    public OverlayManager(Context context) {
        this(context, IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")));
    }

    public OverlayManager(Context context, IOverlayManager iOverlayManager) {
        this.mContext = context;
        this.mService = iOverlayManager;
    }

    @SystemApi
    public OverlayInfo getOverlayInfo(String object, UserHandle userHandle) {
        try {
            object = this.mService.getOverlayInfo((String)object, userHandle.getIdentifier());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<OverlayInfo> getOverlayInfosForTarget(String object, UserHandle userHandle) {
        try {
            object = this.mService.getOverlayInfosForTarget((String)object, userHandle.getIdentifier());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setEnabled(String object, boolean bl, UserHandle userHandle) {
        try {
            if (this.mService.setEnabled((String)object, bl, userHandle.getIdentifier())) {
                return;
            }
            object = new IllegalStateException("setEnabled failed");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setEnabledExclusiveInCategory(String object, UserHandle userHandle) {
        try {
            if (this.mService.setEnabledExclusiveInCategory((String)object, userHandle.getIdentifier())) {
                return;
            }
            object = new IllegalStateException("setEnabledExclusiveInCategory failed");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

