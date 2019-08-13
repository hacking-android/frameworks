/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;

public class NetworkClient {
    public static final int DEFAULT_CONNECT_TIMEOUT = -1;
    public static final int DEFAULT_READ_TIMEOUT = -1;
    protected static int defaultConnectTimeout;
    protected static int defaultSoTimeout;
    protected static String encoding;
    protected int connectTimeout = -1;
    protected Proxy proxy = Proxy.NO_PROXY;
    protected int readTimeout = -1;
    public InputStream serverInput;
    public PrintStream serverOutput;
    protected Socket serverSocket = null;

    static {
        int[] arrn;
        int[] arrn2 = arrn = new int[2];
        arrn2[0] = 0;
        arrn2[1] = 0;
        final String[] arrstring = new String[]{null};
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                val$vals[0] = Integer.getInteger("sun.net.client.defaultReadTimeout", 0);
                val$vals[1] = Integer.getInteger("sun.net.client.defaultConnectTimeout", 0);
                arrstring[0] = System.getProperty("file.encoding", "ISO8859_1");
                return null;
            }
        });
        if (arrn[0] != 0) {
            defaultSoTimeout = arrn[0];
        }
        if (arrn[1] != 0) {
            defaultConnectTimeout = arrn[1];
        }
        encoding = arrstring[0];
        try {
            if (!NetworkClient.isASCIISuperset(encoding)) {
                encoding = "ISO8859_1";
            }
        }
        catch (Exception exception) {
            encoding = "ISO8859_1";
        }
    }

    public NetworkClient() {
    }

    public NetworkClient(String string, int n) throws IOException {
        this.openServer(string, n);
    }

    private static boolean isASCIISuperset(String string) throws Exception {
        return Arrays.equals("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.!~*'();/?:@&=+$,".getBytes(string), new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 45, 95, 46, 33, 126, 42, 39, 40, 41, 59, 47, 63, 58, 64, 38, 61, 43, 36, 44});
    }

    public void closeServer() throws IOException {
        if (!this.serverIsOpen()) {
            return;
        }
        this.serverSocket.close();
        this.serverSocket = null;
        this.serverInput = null;
        this.serverOutput = null;
    }

    protected Socket createSocket() throws IOException {
        return new Socket();
    }

    protected Socket doConnect(String string, int n) throws IOException, UnknownHostException {
        Object object = this.proxy;
        object = object != null ? (((Proxy)object).type() == Proxy.Type.SOCKS ? AccessController.doPrivileged(new PrivilegedAction<Socket>(){

            @Override
            public Socket run() {
                return new Socket(NetworkClient.this.proxy);
            }
        }) : (this.proxy.type() == Proxy.Type.DIRECT ? this.createSocket() : new Socket(Proxy.NO_PROXY))) : this.createSocket();
        if (this.connectTimeout >= 0) {
            ((Socket)object).connect(new InetSocketAddress(string, n), this.connectTimeout);
        } else if (defaultConnectTimeout > 0) {
            ((Socket)object).connect(new InetSocketAddress(string, n), defaultConnectTimeout);
        } else {
            ((Socket)object).connect(new InetSocketAddress(string, n));
        }
        n = this.readTimeout;
        if (n >= 0) {
            ((Socket)object).setSoTimeout(n);
        } else {
            n = defaultSoTimeout;
            if (n > 0) {
                ((Socket)object).setSoTimeout(n);
            }
        }
        return object;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    protected InetAddress getLocalAddress() throws IOException {
        if (this.serverSocket != null) {
            return AccessController.doPrivileged(new PrivilegedAction<InetAddress>(){

                @Override
                public InetAddress run() {
                    return NetworkClient.this.serverSocket.getLocalAddress();
                }
            });
        }
        throw new IOException("not connected");
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void openServer(String object, int n) throws IOException, UnknownHostException {
        if (this.serverSocket != null) {
            this.closeServer();
        }
        this.serverSocket = this.doConnect((String)object, n);
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.serverSocket.getOutputStream());
            this.serverOutput = object = new PrintStream(bufferedOutputStream, true, encoding);
            this.serverInput = new BufferedInputStream(this.serverSocket.getInputStream());
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(encoding);
            stringBuilder.append("encoding not found");
            throw new InternalError(stringBuilder.toString(), unsupportedEncodingException);
        }
    }

    public boolean serverIsOpen() {
        boolean bl = this.serverSocket != null;
        return bl;
    }

    public void setConnectTimeout(int n) {
        this.connectTimeout = n;
    }

    public void setReadTimeout(int n) {
        Socket socket;
        int n2 = n;
        if (n == -1) {
            n2 = defaultSoTimeout;
        }
        if ((socket = this.serverSocket) != null && n2 >= 0) {
            try {
                socket.setSoTimeout(n2);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.readTimeout = n2;
    }

}

