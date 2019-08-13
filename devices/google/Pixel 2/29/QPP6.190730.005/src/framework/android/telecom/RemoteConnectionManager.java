/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.content.ComponentName;
import android.os.RemoteException;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.Log;
import android.telecom.Logging.Session;
import android.telecom.PhoneAccountHandle;
import android.telecom.RemoteConnection;
import android.telecom.RemoteConnectionService;
import com.android.internal.telecom.IConnectionService;
import java.util.HashMap;
import java.util.Map;

public class RemoteConnectionManager {
    private final ConnectionService mOurConnectionServiceImpl;
    private final Map<ComponentName, RemoteConnectionService> mRemoteConnectionServices = new HashMap<ComponentName, RemoteConnectionService>();

    public RemoteConnectionManager(ConnectionService connectionService) {
        this.mOurConnectionServiceImpl = connectionService;
    }

    void addConnectionService(ComponentName componentName, IConnectionService iConnectionService) {
        if (!this.mRemoteConnectionServices.containsKey(componentName)) {
            try {
                RemoteConnectionService remoteConnectionService = new RemoteConnectionService(iConnectionService, this.mOurConnectionServiceImpl);
                this.mRemoteConnectionServices.put(componentName, remoteConnectionService);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void conferenceRemoteConnections(RemoteConnection remoteConnection, RemoteConnection remoteConnection2) {
        if (remoteConnection.getConnectionService() == remoteConnection2.getConnectionService()) {
            try {
                remoteConnection.getConnectionService().conference(remoteConnection.getId(), remoteConnection2.getId(), null);
            }
            catch (RemoteException remoteException) {}
        } else {
            Log.w(this, "Request to conference incompatible remote connections (%s,%s) (%s,%s)", remoteConnection.getConnectionService(), remoteConnection.getId(), remoteConnection2.getConnectionService(), remoteConnection2.getId());
        }
    }

    public RemoteConnection createRemoteConnection(PhoneAccountHandle object, ConnectionRequest connectionRequest, boolean bl) {
        if (connectionRequest.getAccountHandle() != null) {
            Object object2 = connectionRequest.getAccountHandle().getComponentName();
            if (this.mRemoteConnectionServices.containsKey(object2)) {
                if ((object2 = this.mRemoteConnectionServices.get(object2)) != null) {
                    return ((RemoteConnectionService)object2).createRemoteConnection((PhoneAccountHandle)object, connectionRequest, bl);
                }
                return null;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("accountHandle not supported: ");
            ((StringBuilder)object).append(object2);
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("accountHandle must be specified.");
    }
}

