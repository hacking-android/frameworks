/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.IConnectivityManager;
import android.net.ISocketKeepaliveCallback;
import android.net.Network;
import android.net.SocketKeepalive;
import android.net._$$Lambda$NattSocketKeepalive$60CcdfQ34rdXme76td_j4bbtPHU;
import android.net._$$Lambda$NattSocketKeepalive$7nsE_7bVdhw33oN4gmk8WVi_r9U;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import java.io.FileDescriptor;
import java.net.InetAddress;
import java.util.concurrent.Executor;

public final class NattSocketKeepalive
extends SocketKeepalive {
    public static final int NATT_PORT = 4500;
    private final InetAddress mDestination;
    private final int mResourceId;
    private final InetAddress mSource;

    NattSocketKeepalive(IConnectivityManager iConnectivityManager, Network network, ParcelFileDescriptor parcelFileDescriptor, int n, InetAddress inetAddress, InetAddress inetAddress2, Executor executor, SocketKeepalive.Callback callback) {
        super(iConnectivityManager, network, parcelFileDescriptor, executor, callback);
        this.mSource = inetAddress;
        this.mDestination = inetAddress2;
        this.mResourceId = n;
    }

    public /* synthetic */ void lambda$startImpl$0$NattSocketKeepalive(int n) {
        try {
            this.mService.startNattKeepaliveWithFd(this.mNetwork, this.mPfd.getFileDescriptor(), this.mResourceId, n, this.mCallback, this.mSource.getHostAddress(), this.mDestination.getHostAddress());
            return;
        }
        catch (RemoteException remoteException) {
            Log.e("SocketKeepalive", "Error starting socket keepalive: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public /* synthetic */ void lambda$stopImpl$1$NattSocketKeepalive() {
        try {
            if (this.mSlot != null) {
                this.mService.stopKeepalive(this.mNetwork, this.mSlot);
            }
            return;
        }
        catch (RemoteException remoteException) {
            Log.e("SocketKeepalive", "Error stopping socket keepalive: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    void startImpl(int n) {
        this.mExecutor.execute(new _$$Lambda$NattSocketKeepalive$7nsE_7bVdhw33oN4gmk8WVi_r9U(this, n));
    }

    @Override
    void stopImpl() {
        this.mExecutor.execute(new _$$Lambda$NattSocketKeepalive$60CcdfQ34rdXme76td_j4bbtPHU(this));
    }
}

