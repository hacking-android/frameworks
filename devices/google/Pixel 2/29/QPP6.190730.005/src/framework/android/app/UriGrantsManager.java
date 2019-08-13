/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.GrantedUriPermission;
import android.app.IUriGrantsManager;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Singleton;

public class UriGrantsManager {
    private static final Singleton<IUriGrantsManager> IUriGrantsManagerSingleton = new Singleton<IUriGrantsManager>(){

        @Override
        protected IUriGrantsManager create() {
            return IUriGrantsManager.Stub.asInterface(ServiceManager.getService("uri_grants"));
        }
    };
    private final Context mContext;

    UriGrantsManager(Context context, Handler handler) {
        this.mContext = context;
    }

    public static IUriGrantsManager getService() {
        return IUriGrantsManagerSingleton.get();
    }

    public void clearGrantedUriPermissions(String string2) {
        try {
            UriGrantsManager.getService().clearGrantedUriPermissions(string2, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ParceledListSlice<GrantedUriPermission> getGrantedUriPermissions(String object) {
        try {
            object = UriGrantsManager.getService().getGrantedUriPermissions((String)object, this.mContext.getUserId());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

}

