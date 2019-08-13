/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.IConnectivityManager;
import android.net.ISocketKeepaliveCallback;
import android.net.Network;
import android.net._$$Lambda$SocketKeepalive$1$0jK7H49vYYFjBANIXTac00ocnSo;
import android.net._$$Lambda$SocketKeepalive$1$GQbcC2yhPzv5xknkQV01K3_QTNA;
import android.net._$$Lambda$SocketKeepalive$1$Ghy_awbQuJd8C_GZAjeZCXMiaUw;
import android.net._$$Lambda$SocketKeepalive$1$m_VPtyb2YaC8aWd5gXQYgFGhVbM;
import android.net._$$Lambda$SocketKeepalive$1$nDWCSiqzvu6z8lptsLq_qY42hTk;
import android.net._$$Lambda$SocketKeepalive$1$nPQMIWzmX3WEJCjp1qnz_O7qaxs;
import android.net._$$Lambda$SocketKeepalive$1$xxwNi85oVXVQ_ILhrZNWwo4ppA8;
import android.net._$$Lambda$SocketKeepalive$1$yVvEaumPDc_celEzvlSEH2FU0nc;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

public abstract class SocketKeepalive
implements AutoCloseable {
    public static final int BINDER_DIED = -10;
    public static final int DATA_RECEIVED = -2;
    public static final int ERROR_HARDWARE_ERROR = -31;
    public static final int ERROR_HARDWARE_UNSUPPORTED = -30;
    public static final int ERROR_INSUFFICIENT_RESOURCES = -32;
    public static final int ERROR_INVALID_INTERVAL = -24;
    public static final int ERROR_INVALID_IP_ADDRESS = -21;
    public static final int ERROR_INVALID_LENGTH = -23;
    public static final int ERROR_INVALID_NETWORK = -20;
    public static final int ERROR_INVALID_PORT = -22;
    public static final int ERROR_INVALID_SOCKET = -25;
    public static final int ERROR_SOCKET_NOT_IDLE = -26;
    public static final int ERROR_UNSUPPORTED = -30;
    public static final int MAX_INTERVAL_SEC = 3600;
    public static final int MIN_INTERVAL_SEC = 10;
    public static final int NO_KEEPALIVE = -1;
    public static final int SUCCESS = 0;
    static final String TAG = "SocketKeepalive";
    final ISocketKeepaliveCallback mCallback;
    final Executor mExecutor;
    final Network mNetwork;
    final ParcelFileDescriptor mPfd;
    final IConnectivityManager mService;
    Integer mSlot;

    SocketKeepalive(IConnectivityManager iConnectivityManager, Network network, ParcelFileDescriptor parcelFileDescriptor, final Executor executor, final Callback callback) {
        this.mService = iConnectivityManager;
        this.mNetwork = network;
        this.mPfd = parcelFileDescriptor;
        this.mExecutor = executor;
        this.mCallback = new ISocketKeepaliveCallback.Stub(){

            public /* synthetic */ void lambda$onDataReceived$6$SocketKeepalive$1(Callback callback2) {
                SocketKeepalive.this.mSlot = null;
                callback2.onDataReceived();
            }

            public /* synthetic */ void lambda$onDataReceived$7$SocketKeepalive$1(Executor executor2, Callback callback2) throws Exception {
                executor2.execute(new _$$Lambda$SocketKeepalive$1$yVvEaumPDc_celEzvlSEH2FU0nc(this, callback2));
            }

            public /* synthetic */ void lambda$onError$4$SocketKeepalive$1(Callback callback2, int n) {
                SocketKeepalive.this.mSlot = null;
                callback2.onError(n);
            }

            public /* synthetic */ void lambda$onError$5$SocketKeepalive$1(Executor executor2, Callback callback2, int n) throws Exception {
                executor2.execute(new _$$Lambda$SocketKeepalive$1$xxwNi85oVXVQ_ILhrZNWwo4ppA8(this, callback2, n));
            }

            public /* synthetic */ void lambda$onStarted$0$SocketKeepalive$1(int n, Callback callback2) {
                SocketKeepalive.this.mSlot = n;
                callback2.onStarted();
            }

            public /* synthetic */ void lambda$onStarted$1$SocketKeepalive$1(int n, Callback callback2) throws Exception {
                SocketKeepalive.this.mExecutor.execute(new _$$Lambda$SocketKeepalive$1$nDWCSiqzvu6z8lptsLq_qY42hTk(this, n, callback2));
            }

            public /* synthetic */ void lambda$onStopped$2$SocketKeepalive$1(Callback callback2) {
                SocketKeepalive.this.mSlot = null;
                callback2.onStopped();
            }

            public /* synthetic */ void lambda$onStopped$3$SocketKeepalive$1(Executor executor2, Callback callback2) throws Exception {
                executor2.execute(new _$$Lambda$SocketKeepalive$1$Ghy_awbQuJd8C_GZAjeZCXMiaUw(this, callback2));
            }

            @Override
            public void onDataReceived() {
                Binder.withCleanCallingIdentity(new _$$Lambda$SocketKeepalive$1$nPQMIWzmX3WEJCjp1qnz_O7qaxs(this, executor, callback));
            }

            @Override
            public void onError(int n) {
                Binder.withCleanCallingIdentity(new _$$Lambda$SocketKeepalive$1$0jK7H49vYYFjBANIXTac00ocnSo(this, executor, callback, n));
            }

            @Override
            public void onStarted(int n) {
                Binder.withCleanCallingIdentity(new _$$Lambda$SocketKeepalive$1$m_VPtyb2YaC8aWd5gXQYgFGhVbM(this, n, callback));
            }

            @Override
            public void onStopped() {
                Binder.withCleanCallingIdentity(new _$$Lambda$SocketKeepalive$1$GQbcC2yhPzv5xknkQV01K3_QTNA(this, executor, callback));
            }
        };
    }

    @Override
    public final void close() {
        this.stop();
        try {
            this.mPfd.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public final void start(int n) {
        this.startImpl(n);
    }

    abstract void startImpl(int var1);

    public final void stop() {
        this.stopImpl();
    }

    abstract void stopImpl();

    public static class Callback {
        public void onDataReceived() {
        }

        public void onError(int n) {
        }

        public void onStarted() {
        }

        public void onStopped() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ErrorCode {
    }

    public static class ErrorCodeException
    extends Exception {
        public final int error;

        public ErrorCodeException(int n) {
            this.error = n;
        }

        public ErrorCodeException(int n, Throwable throwable) {
            super(throwable);
            this.error = n;
        }
    }

    public static class InvalidPacketException
    extends ErrorCodeException {
        public InvalidPacketException(int n) {
            super(n);
        }
    }

    public static class InvalidSocketException
    extends ErrorCodeException {
        public InvalidSocketException(int n) {
            super(n);
        }

        public InvalidSocketException(int n, Throwable throwable) {
            super(n, throwable);
        }
    }

}

