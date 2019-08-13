/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.NetworkSecurityPolicy
 */
package sun.net.www.protocol.ftp;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.SocketPermission;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import libcore.net.NetworkSecurityPolicy;
import sun.net.ProgressMonitor;
import sun.net.ProgressSource;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;
import sun.net.ftp.FtpProtocolException;
import sun.net.www.MessageHeader;
import sun.net.www.MeteredStream;
import sun.net.www.ParseUtil;
import sun.net.www.URLConnection;
import sun.security.action.GetPropertyAction;

public class FtpURLConnection
extends URLConnection {
    static final int ASCII = 1;
    static final int BIN = 2;
    static final int DIR = 3;
    static final int NONE = 0;
    private int connectTimeout = -1;
    String filename;
    FtpClient ftp = null;
    String fullpath;
    String host;
    private Proxy instProxy;
    InputStream is = null;
    OutputStream os = null;
    String password;
    String pathname;
    Permission permission;
    int port;
    private int readTimeout = -1;
    int type = 0;
    String user;

    public FtpURLConnection(URL uRL) throws IOException {
        this(uRL, null);
    }

    FtpURLConnection(URL object, Proxy object2) throws IOException {
        super((URL)object);
        this.instProxy = object2;
        this.host = ((URL)object).getHost();
        this.port = ((URL)object).getPort();
        object2 = ((URL)object).getUserInfo();
        if (!NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted()) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Cleartext traffic not permitted: ");
            ((StringBuilder)object2).append(((URL)object).getProtocol());
            ((StringBuilder)object2).append("://");
            ((StringBuilder)object2).append(this.host);
            if (((URL)object).getPort() >= 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(":");
                stringBuilder.append(((URL)object).getPort());
                object = stringBuilder.toString();
            } else {
                object = "";
            }
            ((StringBuilder)object2).append((String)object);
            throw new IOException(((StringBuilder)object2).toString());
        }
        if (object2 != null) {
            int n = ((String)object2).indexOf(58);
            if (n == -1) {
                this.user = ParseUtil.decode((String)object2);
                this.password = null;
            } else {
                this.user = ParseUtil.decode(((String)object2).substring(0, n));
                this.password = ParseUtil.decode(((String)object2).substring(n + 1));
            }
        }
    }

    private void cd(String object) throws FtpProtocolException, IOException {
        if (object != null && !((String)object).isEmpty()) {
            if (((String)object).indexOf(47) == -1) {
                this.ftp.changeDirectory(ParseUtil.decode((String)object));
                return;
            }
            object = new StringTokenizer((String)object, "/");
            while (((StringTokenizer)object).hasMoreTokens()) {
                this.ftp.changeDirectory(ParseUtil.decode(((StringTokenizer)object).nextToken()));
            }
            return;
        }
    }

    private void decodePath(String string) {
        CharSequence charSequence;
        int n;
        block18 : {
            block17 : {
                n = string.indexOf(";type=");
                charSequence = string;
                if (n >= 0) {
                    charSequence = string.substring(n + 6, string.length());
                    if ("i".equalsIgnoreCase((String)charSequence)) {
                        this.type = 2;
                    }
                    if ("a".equalsIgnoreCase((String)charSequence)) {
                        this.type = 1;
                    }
                    if ("d".equalsIgnoreCase((String)charSequence)) {
                        this.type = 3;
                    }
                    charSequence = string.substring(0, n);
                }
                string = charSequence;
                if (charSequence != null) {
                    string = charSequence;
                    if (((String)charSequence).length() > 1) {
                        string = charSequence;
                        if (((String)charSequence).charAt(0) == '/') {
                            string = ((String)charSequence).substring(1);
                        }
                    }
                }
                if (string == null) break block17;
                charSequence = string;
                if (string.length() != 0) break block18;
            }
            charSequence = "./";
        }
        if (!((String)charSequence).endsWith("/")) {
            n = ((String)charSequence).lastIndexOf(47);
            if (n > 0) {
                this.filename = ((String)charSequence).substring(n + 1, ((String)charSequence).length());
                this.filename = ParseUtil.decode(this.filename);
                this.pathname = ((String)charSequence).substring(0, n);
            } else {
                this.filename = ParseUtil.decode((String)charSequence);
                this.pathname = null;
            }
        } else {
            this.pathname = ((String)charSequence).substring(0, ((String)charSequence).length() - 1);
            this.filename = null;
        }
        if (this.pathname != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.pathname);
            ((StringBuilder)charSequence).append("/");
            string = this.filename;
            if (string == null) {
                string = "";
            }
            ((StringBuilder)charSequence).append(string);
            this.fullpath = ((StringBuilder)charSequence).toString();
        } else {
            this.fullpath = this.filename;
        }
    }

    private void setTimeouts() {
        FtpClient ftpClient = this.ftp;
        if (ftpClient != null) {
            int n = this.connectTimeout;
            if (n >= 0) {
                ftpClient.setConnectTimeout(n);
            }
            if ((n = this.readTimeout) >= 0) {
                this.ftp.setReadTimeout(n);
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void connect() throws IOException {
        Object object2;
        Object object;
        PrivilegedAction<ProxySelector> privilegedAction;
        block15 : {
            Object object3;
            block17 : {
                URI uRI;
                block18 : {
                    block16 : {
                        // MONITORENTER : this
                        boolean bl = this.connected;
                        if (bl) {
                            // MONITOREXIT : this
                            return;
                        }
                        object2 = null;
                        object = null;
                        if (this.instProxy != null) break block16;
                        privilegedAction = new PrivilegedAction<ProxySelector>(){

                            @Override
                            public ProxySelector run() {
                                return ProxySelector.getDefault();
                            }
                        };
                        if ((privilegedAction = AccessController.doPrivileged(privilegedAction)) == null) break block17;
                        uRI = ParseUtil.toURI(this.url);
                        object3 = ((ProxySelector)((Object)privilegedAction)).select(uRI).iterator();
                        object2 = object;
                        break block18;
                    }
                    object2 = this.instProxy;
                    break block17;
                }
                while (object3.hasNext()) {
                    IOException iOException;
                    object = (Proxy)object3.next();
                    object2 = object;
                    if (object == null) break;
                    object2 = object;
                    if (object == Proxy.NO_PROXY) break;
                    if (((Proxy)object).type() == Proxy.Type.SOCKS) {
                        object2 = object;
                        break;
                    }
                    if (((Proxy)object).type() == Proxy.Type.HTTP && ((Proxy)object).address() instanceof InetSocketAddress) {
                        object2 = ((Proxy)object).address();
                        iOException = new IOException("FTP connections over HTTP proxy not supported");
                        ((ProxySelector)((Object)privilegedAction)).connectFailed(uRI, (SocketAddress)object2, iOException);
                        object2 = object;
                        continue;
                    }
                    object2 = ((Proxy)object).address();
                    iOException = new IOException("Wrong proxy type");
                    ((ProxySelector)((Object)privilegedAction)).connectFailed(uRI, (SocketAddress)object2, iOException);
                    object2 = object;
                }
            }
            if (this.user == null) {
                this.user = "anonymous";
                object = new GetPropertyAction("java.version");
                object = (String)AccessController.doPrivileged(object);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Java");
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append("@");
                privilegedAction = new PrivilegedAction<ProxySelector>("ftp.protocol.user", ((StringBuilder)object3).toString());
                this.password = (String)AccessController.doPrivileged(privilegedAction);
            }
            this.ftp = FtpClient.create();
            if (object2 != null) {
                this.ftp.setProxy((Proxy)object2);
            }
            this.setTimeouts();
            if (this.port != -1) {
                object = this.ftp;
                object2 = new InetSocketAddress(this.host, this.port);
                ((FtpClient)object).connect((SocketAddress)object2);
                break block15;
            }
            object2 = this.ftp;
            object = new InetSocketAddress(this.host, FtpClient.defaultPort());
            object2.connect((SocketAddress)object);
        }
        try {
            privilegedAction = this.ftp;
            object = this.user;
            object2 = this.password == null ? null : this.password.toCharArray();
            ((FtpClient)((Object)privilegedAction)).login((String)object, (char[])object2);
            this.connected = true;
        }
        catch (FtpProtocolException ftpProtocolException) {
            this.ftp.close();
            FtpLoginException ftpLoginException = new FtpLoginException("Invalid username/password");
            throw ftpLoginException;
        }
        return;
        catch (FtpProtocolException ftpProtocolException) {
            object = new IOException(ftpProtocolException);
            throw object;
        }
        catch (UnknownHostException unknownHostException) {
            throw unknownHostException;
        }
    }

    @Override
    public int getConnectTimeout() {
        int n;
        int n2 = n = this.connectTimeout;
        if (n < 0) {
            n2 = 0;
        }
        return n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if (!this.connected) {
            this.connect();
        }
        if (this.os != null) throw new IOException("Already opened for output");
        object = this.is;
        if (object != null) {
            return object;
        }
        messageHeader = new MessageHeader();
        try {
            this.decodePath(this.url.getPath());
            if (this.filename != null && this.type != 3) {
                if (this.type == 1) {
                    this.ftp.setAsciiType();
                } else {
                    this.ftp.setBinaryType();
                }
                this.cd(this.pathname);
                object = new FtpInputStream(this.ftp, this.ftp.getFileStream(this.filename));
                this.is = object;
            } else {
                this.ftp.setAsciiType();
                this.cd(this.pathname);
                if (this.filename == null) {
                    object = new FtpInputStream(this.ftp, this.ftp.list(null));
                    this.is = object;
                } else {
                    object = new FtpInputStream(this.ftp, this.ftp.nameList(this.filename));
                    this.is = object;
                }
            }
            try {
                l = this.ftp.getLastTransferSize();
                messageHeader.add("content-length", Long.toString(l));
                if (l > 0L) {
                    bl = ProgressMonitor.getDefault().shouldMeterInput(this.url, "GET");
                    object = null;
                    if (bl) {
                        object = new ProgressSource(this.url, "GET", l);
                        object.beginTracking();
                    }
                    this.is = object2 = new MeteredStream(this.is, (ProgressSource)object, l);
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            if (false) {
                messageHeader.add("content-type", "text/plain");
                messageHeader.add("access-type", "directory");
            } else {
                messageHeader.add("access-type", "file");
                object = object2 = FtpURLConnection.guessContentTypeFromName(this.fullpath);
                if (object2 == null) {
                    object = object2;
                    if (this.is.markSupported()) {
                        object = FtpURLConnection.guessContentTypeFromStream(this.is);
                    }
                }
                if (object != null) {
                    messageHeader.add("content-type", (String)object);
                }
            }
            ** GOTO lbl67
        }
        catch (FtpProtocolException ftpProtocolException) {
            throw new IOException(ftpProtocolException);
        }
        catch (FileNotFoundException fileNotFoundException) {
            try {
                this.cd(this.fullpath);
                this.ftp.setAsciiType();
                ftpInputStream = new FtpInputStream(this.ftp, this.ftp.list(null));
                this.is = ftpInputStream;
                messageHeader.add("content-type", "text/plain");
                messageHeader.add("access-type", "directory");
            }
            catch (FtpProtocolException ftpProtocolException) {
                throw new FileNotFoundException(this.fullpath);
            }
            catch (IOException iOException) {
                throw new FileNotFoundException(this.fullpath);
            }
lbl67: // 3 sources:
            this.setProperties(messageHeader);
            return this.is;
        }
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (!this.connected) {
            this.connect();
        }
        if (this.is == null) {
            Object object = this.os;
            if (object != null) {
                return object;
            }
            this.decodePath(this.url.getPath());
            object = this.filename;
            if (object != null && ((String)object).length() != 0) {
                try {
                    if (this.pathname != null) {
                        this.cd(this.pathname);
                    }
                    if (this.type == 1) {
                        this.ftp.setAsciiType();
                    } else {
                        this.ftp.setBinaryType();
                    }
                    this.os = object = new FtpOutputStream(this.ftp, this.ftp.putFileStream(this.filename, false));
                    return this.os;
                }
                catch (FtpProtocolException ftpProtocolException) {
                    throw new IOException(ftpProtocolException);
                }
            }
            throw new IOException("illegal filename for a PUT");
        }
        throw new IOException("Already opened for input");
    }

    @Override
    public Permission getPermission() {
        if (this.permission == null) {
            int n = this.url.getPort();
            if (n < 0) {
                n = FtpClient.defaultPort();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.host);
            stringBuilder.append(":");
            stringBuilder.append(n);
            this.permission = new SocketPermission(stringBuilder.toString(), "connect");
        }
        return this.permission;
    }

    @Override
    public int getReadTimeout() {
        int n;
        int n2 = n = this.readTimeout;
        if (n < 0) {
            n2 = 0;
        }
        return n2;
    }

    @Override
    public String getRequestProperty(String string) {
        String string2;
        String string3 = string2 = super.getRequestProperty(string);
        if (string2 == null) {
            string3 = string2;
            if ("type".equals(string)) {
                int n = this.type;
                string = n == 1 ? "a" : (n == 3 ? "d" : "i");
                string3 = string;
            }
        }
        return string3;
    }

    String guessContentTypeFromFilename(String string) {
        return FtpURLConnection.guessContentTypeFromName(string);
    }

    @Override
    public void setConnectTimeout(int n) {
        if (n >= 0) {
            this.connectTimeout = n;
            return;
        }
        throw new IllegalArgumentException("timeouts can't be negative");
    }

    @Override
    public void setReadTimeout(int n) {
        if (n >= 0) {
            this.readTimeout = n;
            return;
        }
        throw new IllegalArgumentException("timeouts can't be negative");
    }

    @Override
    public void setRequestProperty(String string, String string2) {
        super.setRequestProperty(string, string2);
        if ("type".equals(string)) {
            if ("i".equalsIgnoreCase(string2)) {
                this.type = 2;
            } else if ("a".equalsIgnoreCase(string2)) {
                this.type = 1;
            } else if ("d".equalsIgnoreCase(string2)) {
                this.type = 3;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value of '");
                stringBuilder.append(string);
                stringBuilder.append("' request property was '");
                stringBuilder.append(string2);
                stringBuilder.append("' when it must be either 'i', 'a' or 'd'");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
    }

    protected class FtpInputStream
    extends FilterInputStream {
        FtpClient ftp;

        FtpInputStream(FtpClient ftpClient, InputStream inputStream) {
            super(new BufferedInputStream(inputStream));
            this.ftp = ftpClient;
        }

        @Override
        public void close() throws IOException {
            super.close();
            FtpClient ftpClient = this.ftp;
            if (ftpClient != null) {
                ftpClient.close();
            }
        }
    }

    protected class FtpOutputStream
    extends FilterOutputStream {
        FtpClient ftp;

        FtpOutputStream(FtpClient ftpClient, OutputStream outputStream) {
            super(outputStream);
            this.ftp = ftpClient;
        }

        @Override
        public void close() throws IOException {
            super.close();
            FtpClient ftpClient = this.ftp;
            if (ftpClient != null) {
                ftpClient.close();
            }
        }
    }

}

