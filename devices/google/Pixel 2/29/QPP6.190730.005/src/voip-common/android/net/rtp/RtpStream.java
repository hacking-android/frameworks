/*
 * Decompiled with CFR 0.145.
 */
package android.net.rtp;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;

public class RtpStream {
    private static final int MODE_LAST = 2;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RECEIVE_ONLY = 2;
    public static final int MODE_SEND_ONLY = 1;
    private final InetAddress mLocalAddress;
    private final int mLocalPort;
    private int mMode = 0;
    private InetAddress mRemoteAddress;
    private int mRemotePort = -1;
    private int mSocket = -1;

    static {
        System.loadLibrary("rtp_jni");
    }

    RtpStream(InetAddress inetAddress) throws SocketException {
        this.mLocalPort = this.create(inetAddress.getHostAddress());
        this.mLocalAddress = inetAddress;
    }

    private native void close();

    private native int create(String var1) throws SocketException;

    public void associate(InetAddress inetAddress, int n) {
        if (!this.isBusy()) {
            if (inetAddress instanceof Inet4Address && this.mLocalAddress instanceof Inet4Address || inetAddress instanceof Inet6Address && this.mLocalAddress instanceof Inet6Address) {
                if (n >= 0 && n <= 65535) {
                    this.mRemoteAddress = inetAddress;
                    this.mRemotePort = n;
                    return;
                }
                throw new IllegalArgumentException("Invalid port");
            }
            throw new IllegalArgumentException("Unsupported address");
        }
        throw new IllegalStateException("Busy");
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public InetAddress getLocalAddress() {
        return this.mLocalAddress;
    }

    public int getLocalPort() {
        return this.mLocalPort;
    }

    public int getMode() {
        return this.mMode;
    }

    public InetAddress getRemoteAddress() {
        return this.mRemoteAddress;
    }

    public int getRemotePort() {
        return this.mRemotePort;
    }

    int getSocket() {
        return this.mSocket;
    }

    public boolean isBusy() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        synchronized (this) {
            if (!this.isBusy()) {
                this.close();
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Busy");
            throw illegalStateException;
        }
    }

    public void setMode(int n) {
        if (!this.isBusy()) {
            if (n >= 0 && n <= 2) {
                this.mMode = n;
                return;
            }
            throw new IllegalArgumentException("Invalid mode");
        }
        throw new IllegalStateException("Busy");
    }
}

