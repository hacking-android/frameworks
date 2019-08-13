/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.PlainSocketImpl;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketInputStream;
import java.net.SocketTimeoutException;
import java.net.SocksConsts;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.security.action.GetPropertyAction;

class SocksSocketImpl
extends PlainSocketImpl
implements SocksConsts {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean applicationSetProxy;
    private InputStream cmdIn;
    private OutputStream cmdOut;
    private Socket cmdsock;
    private InetSocketAddress external_address;
    private String server = null;
    private int serverPort;
    private boolean useV4;

    SocksSocketImpl() {
        this.serverPort = 1080;
        this.useV4 = false;
        this.cmdsock = null;
        this.cmdIn = null;
        this.cmdOut = null;
    }

    SocksSocketImpl(String string, int n) {
        int n2 = 1080;
        this.serverPort = 1080;
        this.useV4 = false;
        this.cmdsock = null;
        this.cmdIn = null;
        this.cmdOut = null;
        this.server = string;
        if (n == -1) {
            n = n2;
        }
        this.serverPort = n;
    }

    SocksSocketImpl(Proxy object) {
        this.serverPort = 1080;
        this.useV4 = false;
        this.cmdsock = null;
        this.cmdIn = null;
        this.cmdOut = null;
        object = ((Proxy)object).address();
        if (object instanceof InetSocketAddress) {
            object = (InetSocketAddress)object;
            this.server = ((InetSocketAddress)object).getHostString();
            this.serverPort = ((InetSocketAddress)object).getPort();
        }
    }

    private boolean authenticate(byte by, InputStream inputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
        return this.authenticate(by, inputStream, bufferedOutputStream, 0L);
    }

    private boolean authenticate(byte by, InputStream inputStream, BufferedOutputStream bufferedOutputStream, long l) throws IOException {
        if (by == 0) {
            return true;
        }
        if (by == 2) {
            String string;
            byte[] arrby = null;
            PasswordAuthentication passwordAuthentication = AccessController.doPrivileged(new PrivilegedAction<PasswordAuthentication>(InetAddress.getByName(this.server)){
                final /* synthetic */ InetAddress val$addr;
                {
                    this.val$addr = inetAddress;
                }

                @Override
                public PasswordAuthentication run() {
                    return Authenticator.requestPasswordAuthentication(SocksSocketImpl.this.server, this.val$addr, SocksSocketImpl.this.serverPort, "SOCKS5", "SOCKS authentication", null);
                }
            });
            if (passwordAuthentication != null) {
                string = passwordAuthentication.getUserName();
                arrby = new String(passwordAuthentication.getPassword());
            } else {
                string = AccessController.doPrivileged(new GetPropertyAction("user.name"));
            }
            if (string == null) {
                return false;
            }
            bufferedOutputStream.write(1);
            bufferedOutputStream.write(string.length());
            try {
                bufferedOutputStream.write(string.getBytes("ISO-8859-1"));
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
            if (arrby != null) {
                bufferedOutputStream.write(arrby.length());
                try {
                    bufferedOutputStream.write(arrby.getBytes("ISO-8859-1"));
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {}
            } else {
                bufferedOutputStream.write(0);
            }
            bufferedOutputStream.flush();
            arrby = new byte[2];
            if (this.readSocksReply(inputStream, arrby, l) == 2 && arrby[1] == 0) {
                return true;
            }
            bufferedOutputStream.close();
            inputStream.close();
            return false;
        }
        return false;
    }

    private void connectV4(InputStream object, OutputStream outputStream, InetSocketAddress object2, long l) throws IOException {
        if (((InetSocketAddress)object2).getAddress() instanceof Inet4Address) {
            outputStream.write(4);
            outputStream.write(1);
            outputStream.write(((InetSocketAddress)object2).getPort() >> 8 & 255);
            outputStream.write(((InetSocketAddress)object2).getPort() >> 0 & 255);
            outputStream.write(((InetSocketAddress)object2).getAddress().getAddress());
            String string = this.getUserName();
            try {
                outputStream.write(string.getBytes("ISO-8859-1"));
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
            outputStream.write(0);
            outputStream.flush();
            byte[] arrby = new byte[8];
            int n = this.readSocksReply((InputStream)object, arrby, l);
            if (n == 8) {
                if (arrby[0] != 0 && arrby[0] != 4) {
                    throw new SocketException("Reply from SOCKS server has bad version");
                }
                string = null;
                switch (arrby[1]) {
                    default: {
                        object2 = new SocketException("Reply from SOCKS server contains bad status");
                        break;
                    }
                    case 93: {
                        object2 = new SocketException("SOCKS authentication failed");
                        break;
                    }
                    case 92: {
                        object2 = new SocketException("SOCKS server couldn't reach destination");
                        break;
                    }
                    case 91: {
                        object2 = new SocketException("SOCKS request rejected");
                        break;
                    }
                    case 90: {
                        this.external_address = object2;
                        object2 = string;
                    }
                }
                if (object2 == null) {
                    return;
                }
                ((InputStream)object).close();
                outputStream.close();
                throw object2;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Reply from SOCKS server has bad length: ");
            ((StringBuilder)object).append(n);
            throw new SocketException(((StringBuilder)object).toString());
        }
        throw new SocketException("SOCKS V4 requires IPv4 only addresses");
    }

    private String getUserName() {
        String string = "";
        if (this.applicationSetProxy) {
            try {
                String string2;
                string = string2 = System.getProperty("user.name");
            }
            catch (SecurityException securityException) {}
        } else {
            string = AccessController.doPrivileged(new GetPropertyAction("user.name"));
        }
        return string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void privilegedConnect(final String string, final int n, final int n2) throws IOException {
        synchronized (this) {
            try {
                try {
                    PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                        @Override
                        public Void run() throws IOException {
                            SocksSocketImpl.this.superConnectServer(string, n, n2);
                            SocksSocketImpl socksSocketImpl = SocksSocketImpl.this;
                            socksSocketImpl.cmdIn = socksSocketImpl.getInputStream();
                            socksSocketImpl = SocksSocketImpl.this;
                            socksSocketImpl.cmdOut = socksSocketImpl.getOutputStream();
                            return null;
                        }
                    };
                    AccessController.doPrivileged(privilegedExceptionAction);
                    return;
                }
                catch (PrivilegedActionException privilegedActionException) {
                    throw (IOException)privilegedActionException.getException();
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    private int readSocksReply(InputStream inputStream, byte[] arrby) throws IOException {
        return this.readSocksReply(inputStream, arrby, 0L);
    }

    private int readSocksReply(InputStream inputStream, byte[] arrby, long l) throws IOException {
        int n;
        int n2 = arrby.length;
        int n3 = 0;
        for (int i = 0; n3 < n2 && i < 3; n3 += n, ++i) {
            try {
                n = ((SocketInputStream)inputStream).read(arrby, n3, n2 - n3, SocksSocketImpl.remainingMillis(l));
                if (n >= 0) {
                    continue;
                }
                throw new SocketException("Malformed reply from SOCKS server");
            }
            catch (SocketTimeoutException socketTimeoutException) {
                throw new SocketTimeoutException("Connect timed out");
            }
        }
        return n3;
    }

    private static int remainingMillis(long l) throws IOException {
        if (l == 0L) {
            return 0;
        }
        if ((l -= System.currentTimeMillis()) > 0L) {
            return (int)l;
        }
        throw new SocketTimeoutException();
    }

    private void superConnectServer(String string, int n, int n2) throws IOException {
        super.connect(new InetSocketAddress(string, n), n2);
    }

    @Override
    protected void close() throws IOException {
        Socket socket = this.cmdsock;
        if (socket != null) {
            socket.close();
        }
        this.cmdsock = null;
        super.close();
    }

    @Override
    protected void connect(SocketAddress object, int n) throws IOException {
        long l;
        if (n == 0) {
            l = 0L;
        } else {
            l = System.currentTimeMillis() + (long)n;
            if (l < 0L) {
                l = Long.MAX_VALUE;
            }
        }
        Object object2 = System.getSecurityManager();
        if (object != null && object instanceof InetSocketAddress) {
            BufferedOutputStream bufferedOutputStream;
            InetSocketAddress inetSocketAddress;
            block39 : {
                block40 : {
                    inetSocketAddress = (InetSocketAddress)object;
                    if (object2 != null) {
                        if (inetSocketAddress.isUnresolved()) {
                            ((SecurityManager)object2).checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
                        } else {
                            ((SecurityManager)object2).checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
                        }
                    }
                    if ((object = this.server) == null) {
                        super.connect(inetSocketAddress, SocksSocketImpl.remainingMillis(l));
                        return;
                    }
                    try {
                        this.privilegedConnect((String)object, this.serverPort, SocksSocketImpl.remainingMillis(l));
                        bufferedOutputStream = new BufferedOutputStream(this.cmdOut, 512);
                        object2 = this.cmdIn;
                        if (!this.useV4) break block39;
                        if (inetSocketAddress.isUnresolved()) break block40;
                    }
                    catch (IOException iOException) {
                        throw new SocketException(iOException.getMessage());
                    }
                    this.connectV4((InputStream)object2, bufferedOutputStream, inetSocketAddress, l);
                    return;
                }
                throw new UnknownHostException(inetSocketAddress.toString());
            }
            bufferedOutputStream.write(5);
            bufferedOutputStream.write(2);
            bufferedOutputStream.write(0);
            bufferedOutputStream.write(2);
            bufferedOutputStream.flush();
            object = new byte[2];
            if (this.readSocksReply((InputStream)object2, (byte[])object, l) == 2 && object[0] == 5) {
                if (object[1] != -1) {
                    if (this.authenticate(object[1], (InputStream)object2, bufferedOutputStream, l)) {
                        bufferedOutputStream.write(5);
                        bufferedOutputStream.write(1);
                        bufferedOutputStream.write(0);
                        if (inetSocketAddress.isUnresolved()) {
                            bufferedOutputStream.write(3);
                            bufferedOutputStream.write(inetSocketAddress.getHostName().length());
                            try {
                                bufferedOutputStream.write(inetSocketAddress.getHostName().getBytes("ISO-8859-1"));
                            }
                            catch (UnsupportedEncodingException unsupportedEncodingException) {
                                // empty catch block
                            }
                            bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 255);
                            bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 255);
                        } else if (inetSocketAddress.getAddress() instanceof Inet6Address) {
                            bufferedOutputStream.write(4);
                            bufferedOutputStream.write(inetSocketAddress.getAddress().getAddress());
                            bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 255);
                            bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 255);
                        } else {
                            bufferedOutputStream.write(1);
                            bufferedOutputStream.write(inetSocketAddress.getAddress().getAddress());
                            bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 255);
                            bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 255);
                        }
                        bufferedOutputStream.flush();
                        byte[] arrby = new byte[4];
                        if (this.readSocksReply((InputStream)object2, arrby, l) == 4) {
                            object = null;
                            switch (arrby[1]) {
                                default: {
                                    break;
                                }
                                case 8: {
                                    object = new SocketException("SOCKS: address type not supported");
                                    break;
                                }
                                case 7: {
                                    object = new SocketException("SOCKS: Command not supported");
                                    break;
                                }
                                case 6: {
                                    object = new SocketException("SOCKS: TTL expired");
                                    break;
                                }
                                case 5: {
                                    object = new SocketException("SOCKS: Connection refused");
                                    break;
                                }
                                case 4: {
                                    object = new SocketException("SOCKS: Host unreachable");
                                    break;
                                }
                                case 3: {
                                    object = new SocketException("SOCKS: Network unreachable");
                                    break;
                                }
                                case 2: {
                                    object = new SocketException("SOCKS: Connection not allowed by ruleset");
                                    break;
                                }
                                case 1: {
                                    object = new SocketException("SOCKS server general failure");
                                    break;
                                }
                                case 0: {
                                    n = arrby[3];
                                    if (n != 1) {
                                        if (n != 3) {
                                            if (n != 4) {
                                                object = new SocketException("Reply from SOCKS server contains wrong code");
                                                break;
                                            }
                                            n = arrby[1];
                                            if (this.readSocksReply((InputStream)object2, new byte[n], l) == n) {
                                                if (this.readSocksReply((InputStream)object2, new byte[2], l) == 2) break;
                                                throw new SocketException("Reply from SOCKS server badly formatted");
                                            }
                                            throw new SocketException("Reply from SOCKS server badly formatted");
                                        }
                                        n = arrby[1];
                                        if (this.readSocksReply((InputStream)object2, new byte[n], l) == n) {
                                            if (this.readSocksReply((InputStream)object2, new byte[2], l) == 2) break;
                                            throw new SocketException("Reply from SOCKS server badly formatted");
                                        }
                                        throw new SocketException("Reply from SOCKS server badly formatted");
                                    }
                                    if (this.readSocksReply((InputStream)object2, new byte[4], l) == 4) {
                                        if (this.readSocksReply((InputStream)object2, new byte[2], l) == 2) break;
                                        throw new SocketException("Reply from SOCKS server badly formatted");
                                    }
                                    throw new SocketException("Reply from SOCKS server badly formatted");
                                }
                            }
                            if (object == null) {
                                this.external_address = inetSocketAddress;
                                return;
                            }
                            ((InputStream)object2).close();
                            bufferedOutputStream.close();
                            throw object;
                        }
                        throw new SocketException("Reply from SOCKS server has bad length");
                    }
                    throw new SocketException("SOCKS : authentication failed");
                }
                throw new SocketException("SOCKS : No acceptable methods");
            }
            if (!inetSocketAddress.isUnresolved()) {
                this.connectV4((InputStream)object2, bufferedOutputStream, inetSocketAddress, l);
                return;
            }
            throw new UnknownHostException(inetSocketAddress.toString());
        }
        throw new IllegalArgumentException("Unsupported address type");
    }

    @Override
    protected InetAddress getInetAddress() {
        InetSocketAddress inetSocketAddress = this.external_address;
        if (inetSocketAddress != null) {
            return inetSocketAddress.getAddress();
        }
        return super.getInetAddress();
    }

    @Override
    protected int getLocalPort() {
        if (this.socket != null) {
            return super.getLocalPort();
        }
        InetSocketAddress inetSocketAddress = this.external_address;
        if (inetSocketAddress != null) {
            return inetSocketAddress.getPort();
        }
        return super.getLocalPort();
    }

    @Override
    protected int getPort() {
        InetSocketAddress inetSocketAddress = this.external_address;
        if (inetSocketAddress != null) {
            return inetSocketAddress.getPort();
        }
        return super.getPort();
    }

    void setV4() {
        this.useV4 = true;
    }

}

