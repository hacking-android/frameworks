/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ITestNetworkManager;
import android.net.LinkAddress;
import android.net.Network;
import android.net.TestNetworkInterface;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;

public class TestNetworkManager {
    private static final String TAG = TestNetworkManager.class.getSimpleName();
    private final ITestNetworkManager mService;

    public TestNetworkManager(ITestNetworkManager iTestNetworkManager) {
        this.mService = Preconditions.checkNotNull(iTestNetworkManager, "missing ITestNetworkManager");
    }

    public TestNetworkInterface createTapInterface() {
        try {
            TestNetworkInterface testNetworkInterface = this.mService.createTapInterface();
            return testNetworkInterface;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public TestNetworkInterface createTunInterface(LinkAddress[] object) {
        try {
            object = this.mService.createTunInterface((LinkAddress[])object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setupTestNetwork(String string2, IBinder iBinder) {
        try {
            this.mService.setupTestNetwork(string2, iBinder);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void teardownTestNetwork(Network network) {
        try {
            this.mService.teardownTestNetwork(network.netId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

