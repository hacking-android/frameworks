/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.IConnectivityManager;
import android.net.ISocketKeepaliveCallback;
import android.net.Network;
import android.net.SocketKeepalive;
import android.net._$$Lambda$TcpSocketKeepalive$E1MP45uBTM6jPfrxAAqXFllEmAA;
import android.net._$$Lambda$TcpSocketKeepalive$XcFd1FxqMQjF6WWgzFIVR4hV2xk;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import java.io.FileDescriptor;
import java.util.concurrent.Executor;

final class TcpSocketKeepalive
extends SocketKeepalive {
    TcpSocketKeepalive(IConnectivityManager iConnectivityManager, Network network, ParcelFileDescriptor parcelFileDescriptor, Executor executor, SocketKeepalive.Callback callback) {
        super(iConnectivityManager, network, parcelFileDescriptor, executor, callback);
    }

    public /* synthetic */ void lambda$startImpl$0$TcpSocketKeepalive(int n) {
        try {
            FileDescriptor fileDescriptor = this.mPfd.getFileDescriptor();
            this.mService.startTcpKeepalive(this.mNetwork, fileDescriptor, n, this.mCallback);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e("SocketKeepalive", "Error starting packet keepalive: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public /* synthetic */ void lambda$stopImpl$1$TcpSocketKeepalive() {
        try {
            if (this.mSlot != null) {
                this.mService.stopKeepalive(this.mNetwork, this.mSlot);
            }
            return;
        }
        catch (RemoteException remoteException) {
            Log.e("SocketKeepalive", "Error stopping packet keepalive: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    void startImpl(int n) {
        this.mExecutor.execute(new _$$Lambda$TcpSocketKeepalive$E1MP45uBTM6jPfrxAAqXFllEmAA(this, n));
    }

    @Override
    void stopImpl() {
        this.mExecutor.execute(new _$$Lambda$TcpSocketKeepalive$XcFd1FxqMQjF6WWgzFIVR4hV2xk(this));
    }
}

