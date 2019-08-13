/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  com.android.okhttp.internalandroidapi.Dns
 *  com.android.okhttp.internalandroidapi.HttpURLConnectionFactory
 *  libcore.io.IoUtils
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.ConnectivityManager;
import android.net.NetworkUtils;
import android.net.ProxyInfo;
import android.net._$$Lambda$Network$KD6DxaMRJIcajhj36TU1K7lJnHQ;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.proto.ProtoOutputStream;
import com.android.okhttp.internalandroidapi.Dns;
import com.android.okhttp.internalandroidapi.HttpURLConnectionFactory;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import libcore.io.IoUtils;

public class Network
implements Parcelable {
    public static final Parcelable.Creator<Network> CREATOR;
    private static final long HANDLE_MAGIC = 3405697037L;
    private static final int HANDLE_MAGIC_SIZE = 32;
    private static final boolean httpKeepAlive;
    private static final long httpKeepAliveDurationMs;
    private static final int httpMaxConnections;
    private final Object mLock = new Object();
    private volatile NetworkBoundSocketFactory mNetworkBoundSocketFactory = null;
    private final transient boolean mPrivateDnsBypass;
    private volatile HttpURLConnectionFactory mUrlConnectionFactory;
    @UnsupportedAppUsage
    public final int netId;

    static {
        httpKeepAlive = Boolean.parseBoolean(System.getProperty("http.keepAlive", "true"));
        int n = httpKeepAlive ? Integer.parseInt(System.getProperty("http.maxConnections", "5")) : 0;
        httpMaxConnections = n;
        httpKeepAliveDurationMs = Long.parseLong(System.getProperty("http.keepAliveDuration", "300000"));
        CREATOR = new Parcelable.Creator<Network>(){

            @Override
            public Network createFromParcel(Parcel parcel) {
                return new Network(parcel.readInt());
            }

            public Network[] newArray(int n) {
                return new Network[n];
            }
        };
    }

    @UnsupportedAppUsage
    public Network(int n) {
        this(n, false);
    }

    public Network(int n, boolean bl) {
        this.netId = n;
        this.mPrivateDnsBypass = bl;
    }

    @SystemApi
    public Network(Network network) {
        this(network.netId, network.mPrivateDnsBypass);
    }

    public static Network fromNetworkHandle(long l) {
        if (l != 0L) {
            if ((0xFFFFFFFFL & l) == 3405697037L && l >= 0L) {
                return new Network((int)(l >> 32));
            }
            throw new IllegalArgumentException("Value passed to fromNetworkHandle() is not a network handle.");
        }
        throw new IllegalArgumentException("Network.fromNetworkHandle refusing to instantiate NETID_UNSET Network.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void maybeInitUrlConnectionFactory() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUrlConnectionFactory == null) {
                _$$Lambda$Network$KD6DxaMRJIcajhj36TU1K7lJnHQ _$$Lambda$Network$KD6DxaMRJIcajhj36TU1K7lJnHQ = new _$$Lambda$Network$KD6DxaMRJIcajhj36TU1K7lJnHQ(this);
                HttpURLConnectionFactory httpURLConnectionFactory = new HttpURLConnectionFactory();
                httpURLConnectionFactory.setDns((Dns)_$$Lambda$Network$KD6DxaMRJIcajhj36TU1K7lJnHQ);
                httpURLConnectionFactory.setNewConnectionPool(httpMaxConnections, httpKeepAliveDurationMs, TimeUnit.MILLISECONDS);
                this.mUrlConnectionFactory = httpURLConnectionFactory;
            }
            return;
        }
    }

    public void bindSocket(FileDescriptor object) throws IOException {
        ErrnoException errnoException2;
        block5 : {
            int n;
            try {
                if (!((InetSocketAddress)Os.getpeername((FileDescriptor)object)).getAddress().isAnyLocalAddress()) {
                    SocketException socketException = new SocketException("Socket is connected");
                    throw socketException;
                }
            }
            catch (ClassCastException classCastException) {
                throw new SocketException("Only AF_INET/AF_INET6 sockets supported");
            }
            catch (ErrnoException errnoException2) {
                if (errnoException2.errno != OsConstants.ENOTCONN) break block5;
            }
            if ((n = NetworkUtils.bindSocketToNetwork(object.getInt$(), this.netId)) == 0) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Binding socket to network ");
            ((StringBuilder)object).append(this.netId);
            throw new ErrnoException(((StringBuilder)object).toString(), -n).rethrowAsSocketException();
        }
        throw errnoException2.rethrowAsSocketException();
    }

    public void bindSocket(DatagramSocket datagramSocket) throws IOException {
        datagramSocket.getReuseAddress();
        this.bindSocket(datagramSocket.getFileDescriptor$());
    }

    public void bindSocket(Socket socket) throws IOException {
        socket.getReuseAddress();
        this.bindSocket(socket.getFileDescriptor$());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Network;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (Network)object;
        if (this.netId == ((Network)object).netId) {
            bl2 = true;
        }
        return bl2;
    }

    public InetAddress[] getAllByName(String string2) throws UnknownHostException {
        return InetAddress.getAllByNameOnNet((String)string2, (int)this.getNetIdForResolv());
    }

    public InetAddress getByName(String string2) throws UnknownHostException {
        return InetAddress.getByNameOnNet((String)string2, (int)this.getNetIdForResolv());
    }

    public int getNetIdForResolv() {
        int n = this.mPrivateDnsBypass ? (int)(0x80000000L | (long)this.netId) : this.netId;
        return n;
    }

    public long getNetworkHandle() {
        int n = this.netId;
        if (n == 0) {
            return 0L;
        }
        return (long)n << 32 | 3405697037L;
    }

    @SystemApi
    public Network getPrivateDnsBypassingCopy() {
        return new Network(this.netId, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SocketFactory getSocketFactory() {
        if (this.mNetworkBoundSocketFactory != null) return this.mNetworkBoundSocketFactory;
        Object object = this.mLock;
        synchronized (object) {
            NetworkBoundSocketFactory networkBoundSocketFactory;
            if (this.mNetworkBoundSocketFactory != null) return this.mNetworkBoundSocketFactory;
            this.mNetworkBoundSocketFactory = networkBoundSocketFactory = new NetworkBoundSocketFactory();
            return this.mNetworkBoundSocketFactory;
        }
    }

    public int hashCode() {
        return this.netId * 11;
    }

    public /* synthetic */ List lambda$maybeInitUrlConnectionFactory$0$Network(String string2) throws UnknownHostException {
        return Arrays.asList(this.getAllByName(string2));
    }

    public URLConnection openConnection(URL uRL) throws IOException {
        Object object = ConnectivityManager.getInstanceOrNull();
        if (object != null) {
            object = (object = ((ConnectivityManager)object).getProxyForNetwork(this)) != null ? ((ProxyInfo)object).makeProxy() : Proxy.NO_PROXY;
            return this.openConnection(uRL, (Proxy)object);
        }
        throw new IOException("No ConnectivityManager yet constructed, please construct one");
    }

    public URLConnection openConnection(URL uRL, Proxy proxy) throws IOException {
        if (proxy != null) {
            this.maybeInitUrlConnectionFactory();
            SocketFactory socketFactory = this.getSocketFactory();
            return this.mUrlConnectionFactory.openConnection(uRL, socketFactory, proxy);
        }
        throw new IllegalArgumentException("proxy is null");
    }

    public String toString() {
        return Integer.toString(this.netId);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.netId);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1120986464257L, this.netId);
        protoOutputStream.end(l);
    }

    private class NetworkBoundSocketFactory
    extends SocketFactory {
        private NetworkBoundSocketFactory() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private Socket connectToHost(String var1_1, int var2_2, SocketAddress var3_3) throws IOException {
            var4_4 = Network.this.getAllByName(var1_1);
            var5_5 = 0;
            while (var5_5 < var4_4.length) {
                try {
                    var6_7 = this.createSocket();
                    if (var3_3 == null) ** GOTO lbl13
                }
                catch (IOException var6_6) {
                    if (var5_5 == var4_4.length - 1) throw var6_6;
                    ++var5_5;
                    continue;
                }
                var6_7.bind(var3_3);
lbl13: // 2 sources:
                var7_8 = new InetSocketAddress(var4_4[var5_5], var2_2);
                var6_7.connect(var7_8);
                if (false == false) return var6_7;
                {
                    catch (Throwable var7_9) {
                        if (true == false) throw var7_9;
                        IoUtils.closeQuietly((Socket)var6_7);
                        throw var7_9;
                    }
                }
                IoUtils.closeQuietly((Socket)var6_7);
                return var6_7;
            }
            throw new UnknownHostException(var1_1);
        }

        @Override
        public Socket createSocket() throws IOException {
            Socket socket = new Socket();
            try {
                Network.this.bindSocket(socket);
                return socket;
            }
            finally {
                if (false) {
                    IoUtils.closeQuietly((Socket)socket);
                }
            }
        }

        @Override
        public Socket createSocket(String string2, int n) throws IOException {
            return this.connectToHost(string2, n, null);
        }

        @Override
        public Socket createSocket(String string2, int n, InetAddress inetAddress, int n2) throws IOException {
            return this.connectToHost(string2, n, new InetSocketAddress(inetAddress, n2));
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
            Socket socket = this.createSocket();
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, n);
                socket.connect(inetSocketAddress);
                return socket;
            }
            finally {
                if (false) {
                    IoUtils.closeQuietly((Socket)socket);
                }
            }
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int n, InetAddress serializable, int n2) throws IOException {
            Socket socket = this.createSocket();
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress((InetAddress)serializable, n2);
                socket.bind(inetSocketAddress);
                serializable = new InetSocketAddress(inetAddress, n);
                socket.connect((SocketAddress)serializable);
                return socket;
            }
            finally {
                if (false) {
                    IoUtils.closeQuietly((Socket)socket);
                }
            }
        }
    }

}

