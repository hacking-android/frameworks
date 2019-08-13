/*
 * Decompiled with CFR 0.145.
 */
package sun.net.ftp.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpDirParser;
import sun.net.ftp.FtpProtocolException;
import sun.net.ftp.FtpReplyCode;
import sun.util.logging.PlatformLogger;

public class FtpClient
extends sun.net.ftp.FtpClient {
    private static String[] MDTMformats;
    private static SimpleDateFormat[] dateFormats;
    private static int defaultConnectTimeout;
    private static int defaultSoTimeout;
    private static String encoding;
    private static Pattern epsvPat;
    private static Pattern linkp;
    private static final PlatformLogger logger;
    private static Pattern pasvPat;
    private static String[] patStrings;
    private static int[][] patternGroups;
    private static Pattern[] patterns;
    private static Pattern transPat;
    private int connectTimeout = -1;
    private DateFormat df = DateFormat.getDateInstance(2, Locale.US);
    private InputStream in;
    private String lastFileName;
    private FtpReplyCode lastReplyCode = null;
    private long lastTransSize = -1L;
    private boolean loggedIn = false;
    private FtpDirParser mlsxParser = new MLSxParser();
    private Socket oldSocket;
    private PrintStream out;
    private FtpDirParser parser = new DefaultParser();
    private final boolean passiveMode;
    private Proxy proxy;
    private int readTimeout = -1;
    private boolean replyPending = false;
    private long restartOffset = 0L;
    private Socket server;
    private InetSocketAddress serverAddr;
    private Vector<String> serverResponse = new Vector(1);
    private SSLSocketFactory sslFact;
    private FtpClient.TransferType type = FtpClient.TransferType.BINARY;
    private boolean useCrypto = false;
    private String welcomeMsg;

    static {
        int n;
        Object[] arrobject;
        logger = PlatformLogger.getLogger("sun.net.ftp.FtpClient");
        encoding = "ISO8859_1";
        patStrings = new String[]{"([\\-ld](?:[r\\-][w\\-][x\\-]){3})\\s*\\d+ (\\w+)\\s*(\\w+)\\s*(\\d+)\\s*([A-Z][a-z][a-z]\\s*\\d+)\\s*(\\d\\d:\\d\\d)\\s*(\\p{Print}*)", "([\\-ld](?:[r\\-][w\\-][x\\-]){3})\\s*\\d+ (\\w+)\\s*(\\w+)\\s*(\\d+)\\s*([A-Z][a-z][a-z]\\s*\\d+)\\s*(\\d{4})\\s*(\\p{Print}*)", "(\\d{2}/\\d{2}/\\d{4})\\s*(\\d{2}:\\d{2}[ap])\\s*((?:[0-9,]+)|(?:<DIR>))\\s*(\\p{Graph}*)", "(\\d{2}-\\d{2}-\\d{2})\\s*(\\d{2}:\\d{2}[AP]M)\\s*((?:[0-9,]+)|(?:<DIR>))\\s*(\\p{Graph}*)"};
        patternGroups = new int[][]{{7, 4, 5, 6, 0, 1, 2, 3}, {7, 4, 5, 0, 6, 1, 2, 3}, {4, 3, 1, 2, 0, 0, 0, 0}, {4, 3, 1, 2, 0, 0, 0, 0}};
        linkp = Pattern.compile("(\\p{Print}+) \\-\\> (\\p{Print}+)$");
        int[] arrn = arrobject = new int[2];
        arrn[0] = 0;
        arrn[1] = 0;
        final String[] arrstring = new String[]{null};
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                val$vals[0] = Integer.getInteger("sun.net.client.defaultReadTimeout", 0);
                val$vals[1] = Integer.getInteger("sun.net.client.defaultConnectTimeout", 0);
                arrstring[0] = System.getProperty("file.encoding", "ISO8859_1");
                return null;
            }
        });
        defaultSoTimeout = arrobject[0] == 0 ? -1 : arrobject[0];
        defaultConnectTimeout = arrobject[1] == 0 ? -1 : arrobject[1];
        encoding = arrstring[0];
        try {
            if (!FtpClient.isASCIISuperset(encoding)) {
                encoding = "ISO8859_1";
            }
        }
        catch (Exception exception) {
            encoding = "ISO8859_1";
        }
        patterns = new Pattern[patStrings.length];
        for (n = 0; n < (arrobject = patStrings).length; ++n) {
            FtpClient.patterns[n] = Pattern.compile((String)arrobject[n]);
        }
        transPat = null;
        epsvPat = null;
        pasvPat = null;
        MDTMformats = new String[]{"yyyyMMddHHmmss.SSS", "yyyyMMddHHmmss"};
        dateFormats = new SimpleDateFormat[MDTMformats.length];
        for (n = 0; n < (arrobject = MDTMformats).length; ++n) {
            FtpClient.dateFormats[n] = new SimpleDateFormat((String)arrobject[n]);
            dateFormats[n].setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }

    protected FtpClient() {
        this.passiveMode = true;
    }

    public static sun.net.ftp.FtpClient create() {
        return new FtpClient();
    }

    private InputStream createInputStream(InputStream inputStream) {
        if (this.type == FtpClient.TransferType.ASCII) {
            return new TelnetInputStream(inputStream, false);
        }
        return inputStream;
    }

    private OutputStream createOutputStream(OutputStream outputStream) {
        if (this.type == FtpClient.TransferType.ASCII) {
            return new TelnetOutputStream(outputStream, false);
        }
        return outputStream;
    }

    private void disconnect() throws IOException {
        if (this.isConnected()) {
            this.server.close();
        }
        this.server = null;
        this.in = null;
        this.out = null;
        this.lastTransSize = -1L;
        this.lastFileName = null;
        this.restartOffset = 0L;
        this.welcomeMsg = null;
        this.lastReplyCode = null;
        this.serverResponse.setSize(0);
    }

    private Socket doConnect(InetSocketAddress inetSocketAddress, int n) throws IOException {
        Object object = this.proxy;
        object = object != null ? (((Proxy)object).type() == Proxy.Type.SOCKS ? AccessController.doPrivileged(new PrivilegedAction<Socket>(){

            @Override
            public Socket run() {
                return new Socket(FtpClient.this.proxy);
            }
        }) : new Socket(Proxy.NO_PROXY)) : new Socket();
        if (n >= 0) {
            ((Socket)object).connect(inetSocketAddress, n);
        } else {
            n = this.connectTimeout;
            if (n >= 0) {
                ((Socket)object).connect(inetSocketAddress, n);
            } else {
                n = defaultConnectTimeout;
                if (n > 0) {
                    ((Socket)object).connect(inetSocketAddress, n);
                } else {
                    ((Socket)object).connect(inetSocketAddress);
                }
            }
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

    private String getResponseString() {
        return this.serverResponse.elementAt(0);
    }

    private Vector<String> getResponseStrings() {
        return this.serverResponse;
    }

    private byte[] getSecurityData() {
        byte[] arrby = this.getLastResponseString();
        if (arrby.substring(4, 9).equalsIgnoreCase("ADAT=")) {
            BASE64Decoder bASE64Decoder = new BASE64Decoder();
            try {
                arrby = bASE64Decoder.decodeBuffer(arrby.substring(9, arrby.length() - 1));
                return arrby;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    private void getTransferName() {
        this.lastFileName = null;
        String string = this.getLastResponseString();
        int n = string.indexOf("unique file name:");
        int n2 = string.lastIndexOf(41);
        if (n >= 0) {
            this.lastFileName = string.substring(n + 17, n2);
        }
    }

    private void getTransferSize() {
        this.lastTransSize = -1L;
        Object object = this.getLastResponseString();
        if (transPat == null) {
            transPat = Pattern.compile("150 Opening .*\\((\\d+) bytes\\).");
        }
        if (((Matcher)(object = transPat.matcher((CharSequence)object))).find()) {
            this.lastTransSize = Long.parseLong(((Matcher)object).group(1));
        }
    }

    private static boolean isASCIISuperset(String string) throws Exception {
        return Arrays.equals("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.!~*'();/?:@&=+$,".getBytes(string), new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 45, 95, 46, 33, 126, 42, 39, 40, 41, 59, 47, 63, 58, 64, 38, 61, 43, 36, 44});
    }

    private boolean issueCommand(String object) throws IOException, FtpProtocolException {
        if (this.isConnected()) {
            if (this.replyPending) {
                try {
                    this.completePending();
                }
                catch (FtpProtocolException ftpProtocolException) {
                    // empty catch block
                }
            }
            if (((String)object).indexOf(10) == -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append("\r\n");
                this.sendServer(stringBuilder.toString());
                return this.readReply();
            }
            object = new FtpProtocolException("Illegal FTP command");
            ((Throwable)object).initCause(new IllegalArgumentException("Illegal carriage return"));
            throw object;
        }
        throw new IllegalStateException("Not connected");
    }

    private void issueCommandCheck(String string) throws FtpProtocolException, IOException {
        if (this.issueCommand(string)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(":");
        stringBuilder.append(this.getResponseString());
        throw new FtpProtocolException(stringBuilder.toString(), this.getLastReplyCode());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Socket openDataConnection(String object) throws FtpProtocolException, IOException {
        try {
            return this.openPassiveDataConnection((String)object);
        }
        catch (FtpProtocolException ftpProtocolException) {
            Object object2;
            Object object3 = ftpProtocolException.getMessage();
            if (!((String)object3).startsWith("PASV")) {
                if (!((String)object3).startsWith("EPSV")) throw ftpProtocolException;
            }
            if ((object3 = this.proxy) != null) {
                if (((Proxy)object3).type() == Proxy.Type.SOCKS) throw new FtpProtocolException("Passive mode failed");
            }
            object3 = this.server.getLocalAddress();
            int n = 0;
            ServerSocket serverSocket = new ServerSocket(0, 1, (InetAddress)object3);
            object3 = object2 = serverSocket.getInetAddress();
            if (((InetAddress)object2).isAnyLocalAddress()) {
                object3 = this.server.getLocalAddress();
            }
            byte[] arrby = new StringBuilder();
            arrby.append("EPRT |");
            object2 = object3 instanceof Inet6Address ? "2" : "1";
            arrby.append((String)object2);
            arrby.append("|");
            arrby.append(((InetAddress)object3).getHostAddress());
            arrby.append("|");
            arrby.append(serverSocket.getLocalPort());
            arrby.append("|");
            if (!this.issueCommand(arrby.toString()) || !this.issueCommand((String)object)) {
                int n2;
                object2 = "PORT ";
                arrby = ((InetAddress)object3).getAddress();
                object3 = object2;
                while (n < (n2 = arrby.length)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object3);
                    ((StringBuilder)object2).append(arrby[n] & 255);
                    ((StringBuilder)object2).append(",");
                    object3 = ((StringBuilder)object2).toString();
                    ++n;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object3);
                ((StringBuilder)object2).append(serverSocket.getLocalPort() >>> 8 & 255);
                ((StringBuilder)object2).append(",");
                ((StringBuilder)object2).append(serverSocket.getLocalPort() & 255);
                this.issueCommandCheck(((StringBuilder)object2).toString());
                this.issueCommandCheck((String)object);
            }
            if (this.connectTimeout >= 0) {
                serverSocket.setSoTimeout(this.connectTimeout);
            } else if (defaultConnectTimeout > 0) {
                serverSocket.setSoTimeout(defaultConnectTimeout);
            }
            object3 = serverSocket.accept();
            if (this.readTimeout >= 0) {
                ((Socket)object3).setSoTimeout(this.readTimeout);
            } else if (defaultSoTimeout > 0) {
                ((Socket)object3).setSoTimeout(defaultSoTimeout);
            }
            object = object3;
            if (!this.useCrypto) return object;
            try {
                return this.sslFact.createSocket((Socket)object3, this.serverAddr.getHostName(), this.serverAddr.getPort(), true);
            }
            catch (Exception exception) {
                throw new IOException(exception.getLocalizedMessage());
            }
        }
    }

    private Socket openPassiveDataConnection(String charSequence) throws FtpProtocolException, IOException {
        Object object;
        block18 : {
            Object object2;
            int n;
            block17 : {
                block15 : {
                    block16 : {
                        if (!this.issueCommand("EPSV ALL")) break block15;
                        this.issueCommandCheck("EPSV");
                        object = this.getResponseString();
                        if (epsvPat == null) {
                            epsvPat = Pattern.compile("^229 .* \\(\\|\\|\\|(\\d+)\\|\\)");
                        }
                        if (!((Matcher)(object2 = epsvPat.matcher((CharSequence)object))).find()) break block16;
                        n = Integer.parseInt(((Matcher)object2).group(1));
                        object = this.server.getInetAddress();
                        object = object != null ? new InetSocketAddress((InetAddress)object, n) : InetSocketAddress.createUnresolved(this.serverAddr.getHostName(), n);
                        object2 = object;
                        break block17;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("EPSV failed : ");
                    ((StringBuilder)charSequence).append((String)object);
                    throw new FtpProtocolException(((StringBuilder)charSequence).toString());
                }
                this.issueCommandCheck("PASV");
                object = this.getResponseString();
                if (pasvPat == null) {
                    pasvPat = Pattern.compile("227 .* \\(?(\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)?");
                }
                if (!((Matcher)(object2 = pasvPat.matcher((CharSequence)object))).find()) break block18;
                n = Integer.parseInt(((Matcher)object2).group(3));
                int n2 = Integer.parseInt(((Matcher)object2).group(2));
                object2 = new InetSocketAddress(((Matcher)object2).group(1).replace(',', '.'), (n2 << 8) + n);
            }
            object = this.proxy;
            object = object != null ? (((Proxy)object).type() == Proxy.Type.SOCKS ? AccessController.doPrivileged(new PrivilegedAction<Socket>(){

                @Override
                public Socket run() {
                    return new Socket(FtpClient.this.proxy);
                }
            }) : new Socket(Proxy.NO_PROXY)) : new Socket();
            ((Socket)object).bind(new InetSocketAddress(AccessController.doPrivileged(new PrivilegedAction<InetAddress>(){

                @Override
                public InetAddress run() {
                    return FtpClient.this.server.getLocalAddress();
                }
            }), 0));
            n = this.connectTimeout;
            if (n >= 0) {
                ((Socket)object).connect((SocketAddress)object2, n);
            } else {
                n = defaultConnectTimeout;
                if (n > 0) {
                    ((Socket)object).connect((SocketAddress)object2, n);
                } else {
                    ((Socket)object).connect((SocketAddress)object2);
                }
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
            Object object3 = object;
            if (this.useCrypto) {
                try {
                    object3 = this.sslFact.createSocket((Socket)object, ((InetSocketAddress)object2).getHostName(), ((InetSocketAddress)object2).getPort(), true);
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Can't open secure data channel: ");
                    ((StringBuilder)object).append(exception);
                    throw new FtpProtocolException(((StringBuilder)object).toString());
                }
            }
            if (!this.issueCommand((String)charSequence)) {
                ((Socket)object3).close();
                if (this.getLastReplyCode() == FtpReplyCode.FILE_UNAVAILABLE) {
                    throw new FileNotFoundException((String)charSequence);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(this.getResponseString());
                throw new FtpProtocolException(((StringBuilder)object).toString(), this.getLastReplyCode());
            }
            return object3;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("PASV failed : ");
        ((StringBuilder)charSequence).append((String)object);
        throw new FtpProtocolException(((StringBuilder)charSequence).toString());
    }

    private boolean readReply() throws IOException {
        this.lastReplyCode = FtpReplyCode.find(this.readServerResponse());
        if (this.lastReplyCode.isPositivePreliminary()) {
            this.replyPending = true;
            return true;
        }
        if (!this.lastReplyCode.isPositiveCompletion() && !this.lastReplyCode.isPositiveIntermediate()) {
            return false;
        }
        if (this.lastReplyCode == FtpReplyCode.CLOSING_DATA_CONNECTION) {
            this.getTransferName();
        }
        return true;
    }

    private int readServerResponse() throws IOException {
        int n;
        StringBuffer stringBuffer = new StringBuffer(32);
        int n2 = -1;
        this.serverResponse.setSize(0);
        do {
            int n3 = n = this.in.read();
            if (n != -1) {
                n = n3;
                if (n3 == 13) {
                    int n4;
                    n = n3 = (n4 = this.in.read());
                    if (n4 != 10) {
                        stringBuffer.append('\r');
                        n = n3;
                    }
                }
                stringBuffer.append((char)n);
                if (n != 10) continue;
            }
            String string = stringBuffer.toString();
            stringBuffer.setLength(0);
            if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
                PlatformLogger platformLogger = logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Server [");
                stringBuilder.append(this.serverAddr);
                stringBuilder.append("] --> ");
                stringBuilder.append(string);
                platformLogger.finest(stringBuilder.toString());
            }
            if (string.length() == 0) {
                n = -1;
            } else {
                try {
                    n = Integer.parseInt(string.substring(0, 3));
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    n = -1;
                }
            }
            this.serverResponse.addElement(string);
            if (n2 != -1) {
                if (n == n2 && (string.length() < 4 || string.charAt(3) != '-')) break;
                continue;
            }
            if (string.length() < 4 || string.charAt(3) != '-') break;
            n2 = n;
        } while (true);
        return n;
    }

    private boolean sendSecurityData(byte[] object) throws IOException, FtpProtocolException {
        object = new BASE64Encoder().encode((byte[])object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ADAT ");
        stringBuilder.append((String)object);
        return this.issueCommand(stringBuilder.toString());
    }

    private void sendServer(String string) {
        this.out.print(string);
        if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
            PlatformLogger platformLogger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Server [");
            stringBuilder.append(this.serverAddr);
            stringBuilder.append("] <-- ");
            stringBuilder.append(string);
            platformLogger.finest(stringBuilder.toString());
        }
    }

    private void tryConnect(InetSocketAddress object, int n) throws IOException {
        if (this.isConnected()) {
            this.disconnect();
        }
        this.server = this.doConnect((InetSocketAddress)object, n);
        try {
            PrintStream printStream;
            object = new BufferedOutputStream(this.server.getOutputStream());
            this.out = printStream = new PrintStream((OutputStream)object, true, encoding);
            this.in = new BufferedInputStream(this.server.getInputStream());
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(encoding);
            ((StringBuilder)object).append("encoding not found");
            throw new InternalError(((StringBuilder)object).toString(), unsupportedEncodingException);
        }
    }

    private void tryLogin(String charSequence, char[] arrc) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USER ");
        stringBuilder.append((String)charSequence);
        this.issueCommandCheck(stringBuilder.toString());
        if (this.lastReplyCode == FtpReplyCode.NEED_PASSWORD && arrc != null && arrc.length > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("PASS ");
            ((StringBuilder)charSequence).append(String.valueOf(arrc));
            this.issueCommandCheck(((StringBuilder)charSequence).toString());
        }
    }

    @Override
    public sun.net.ftp.FtpClient abort() throws FtpProtocolException, IOException {
        this.issueCommandCheck("ABOR");
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient allocate(long l) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ALLO ");
        stringBuilder.append(l);
        this.issueCommandCheck(stringBuilder.toString());
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient appendFile(String arrby, InputStream inputStream) throws FtpProtocolException, IOException {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("APPE ");
        ((StringBuilder)object).append((String)arrby);
        object = this.createOutputStream(this.openDataConnection(((StringBuilder)object).toString()).getOutputStream());
        arrby = new byte[1500 * 10];
        while ((n = inputStream.read(arrby)) >= 0) {
            if (n <= 0) continue;
            ((OutputStream)object).write(arrby, 0, n);
        }
        ((OutputStream)object).close();
        return this.completePending();
    }

    @Override
    public sun.net.ftp.FtpClient changeDirectory(String string) throws FtpProtocolException, IOException {
        if (string != null && !"".equals(string)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CWD ");
            stringBuilder.append(string);
            this.issueCommandCheck(stringBuilder.toString());
            return this;
        }
        throw new IllegalArgumentException("directory can't be null or empty");
    }

    @Override
    public sun.net.ftp.FtpClient changeToParentDirectory() throws FtpProtocolException, IOException {
        this.issueCommandCheck("CDUP");
        return this;
    }

    @Override
    public void close() throws IOException {
        if (this.isConnected()) {
            try {
                this.issueCommand("QUIT");
            }
            catch (FtpProtocolException ftpProtocolException) {
                // empty catch block
            }
            this.loggedIn = false;
        }
        this.disconnect();
    }

    @Override
    public sun.net.ftp.FtpClient completePending() throws FtpProtocolException, IOException {
        while (this.replyPending) {
            this.replyPending = false;
            if (this.readReply()) continue;
            throw new FtpProtocolException(this.getLastResponseString(), this.lastReplyCode);
        }
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient connect(SocketAddress socketAddress) throws FtpProtocolException, IOException {
        return this.connect(socketAddress, -1);
    }

    @Override
    public sun.net.ftp.FtpClient connect(SocketAddress serializable, int n) throws FtpProtocolException, IOException {
        if (serializable instanceof InetSocketAddress) {
            this.serverAddr = (InetSocketAddress)serializable;
            this.tryConnect(this.serverAddr, n);
            if (this.readReply()) {
                this.welcomeMsg = this.getResponseString().substring(4);
                return this;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Welcome message: ");
            ((StringBuilder)serializable).append(this.getResponseString());
            throw new FtpProtocolException(((StringBuilder)serializable).toString(), this.lastReplyCode);
        }
        throw new IllegalArgumentException("Wrong address type");
    }

    @Override
    public sun.net.ftp.FtpClient deleteFile(String string) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELE ");
        stringBuilder.append(string);
        this.issueCommandCheck(stringBuilder.toString());
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient enablePassiveMode(boolean bl) {
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient endSecureSession() throws FtpProtocolException, IOException {
        if (!this.useCrypto) {
            return this;
        }
        this.issueCommandCheck("CCC");
        this.issueCommandCheck("PROT C");
        this.useCrypto = false;
        this.server = this.oldSocket;
        this.oldSocket = null;
        try {
            PrintStream printStream;
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.server.getOutputStream());
            this.out = printStream = new PrintStream(bufferedOutputStream, true, encoding);
            this.in = new BufferedInputStream(this.server.getInputStream());
            return this;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(encoding);
            stringBuilder.append("encoding not found");
            throw new InternalError(stringBuilder.toString(), unsupportedEncodingException);
        }
    }

    @Override
    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    @Override
    public List<String> getFeatures() throws FtpProtocolException, IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        this.issueCommandCheck("FEAT");
        Vector<String> vector = this.getResponseStrings();
        for (int i = 1; i < vector.size() - 1; ++i) {
            String string = vector.get(i);
            arrayList.add(string.substring(1, string.length() - 1));
        }
        return arrayList;
    }

    @Override
    public sun.net.ftp.FtpClient getFile(String arrby, OutputStream outputStream) throws FtpProtocolException, IOException {
        block6 : {
            int n;
            block5 : {
                int n2;
                if (this.restartOffset <= 0L) break block5;
                Object object = new StringBuilder();
                ((StringBuilder)object).append("REST ");
                ((StringBuilder)object).append(this.restartOffset);
                Socket socket = this.openDataConnection(((StringBuilder)object).toString());
                object = new StringBuilder();
                ((StringBuilder)object).append("RETR ");
                ((StringBuilder)object).append((String)arrby);
                this.issueCommandCheck(((StringBuilder)object).toString());
                this.getTransferSize();
                object = this.createInputStream(socket.getInputStream());
                arrby = new byte[1500 * 10];
                while ((n2 = ((InputStream)object).read(arrby)) >= 0) {
                    if (n2 <= 0) continue;
                    outputStream.write(arrby, 0, n2);
                }
                ((InputStream)object).close();
                break block6;
                finally {
                    this.restartOffset = 0L;
                }
            }
            Object object = new StringBuilder();
            ((StringBuilder)object).append("RETR ");
            ((StringBuilder)object).append((String)arrby);
            arrby = this.openDataConnection(((StringBuilder)object).toString());
            this.getTransferSize();
            object = this.createInputStream(arrby.getInputStream());
            arrby = new byte[1500 * 10];
            while ((n = ((InputStream)object).read(arrby)) >= 0) {
                if (n <= 0) continue;
                outputStream.write(arrby, 0, n);
            }
            ((InputStream)object).close();
        }
        return this.completePending();
    }

    @Override
    public InputStream getFileStream(String object) throws FtpProtocolException, IOException {
        if (this.restartOffset > 0L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("REST ");
            stringBuilder.append(this.restartOffset);
            Socket socket = this.openDataConnection(stringBuilder.toString());
            if (socket == null) {
                return null;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("RETR ");
            stringBuilder.append((String)object);
            this.issueCommandCheck(stringBuilder.toString());
            this.getTransferSize();
            return this.createInputStream(socket.getInputStream());
            finally {
                this.restartOffset = 0L;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RETR ");
        stringBuilder.append((String)object);
        object = this.openDataConnection(stringBuilder.toString());
        if (object == null) {
            return null;
        }
        this.getTransferSize();
        return this.createInputStream(((Socket)object).getInputStream());
    }

    @Override
    public String getHelp(String object) throws FtpProtocolException, IOException {
        AbstractStringBuilder abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("HELP ");
        ((StringBuilder)abstractStringBuilder).append((String)object);
        this.issueCommandCheck(((StringBuilder)abstractStringBuilder).toString());
        object = this.getResponseStrings();
        if (((Vector)object).size() == 1) {
            return ((String)((Vector)object).get(0)).substring(4);
        }
        abstractStringBuilder = new StringBuffer();
        for (int i = 1; i < ((Vector)object).size() - 1; ++i) {
            ((StringBuffer)abstractStringBuilder).append(((String)((Vector)object).get(i)).substring(3));
        }
        return ((StringBuffer)abstractStringBuilder).toString();
    }

    @Override
    public String getLastFileName() {
        return this.lastFileName;
    }

    @Override
    public Date getLastModified(String object) throws FtpProtocolException, IOException {
        StringBuilder serializable2 = new StringBuilder();
        serializable2.append("MDTM ");
        serializable2.append((String)object);
        this.issueCommandCheck(serializable2.toString());
        if (this.lastReplyCode == FtpReplyCode.FILE_STATUS) {
            String string = this.getResponseString().substring(4);
            object = null;
            for (SimpleDateFormat simpleDateFormat : dateFormats) {
                try {
                    Date date = simpleDateFormat.parse(string);
                    object = date;
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
                if (object == null) continue;
                return object;
            }
        }
        return null;
    }

    @Override
    public FtpReplyCode getLastReplyCode() {
        return this.lastReplyCode;
    }

    @Override
    public String getLastResponseString() {
        StringBuffer stringBuffer = new StringBuffer();
        Vector<String> vector = this.serverResponse;
        if (vector != null) {
            for (String string : vector) {
                if (string == null) continue;
                stringBuffer.append(string);
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public long getLastTransferSize() {
        return this.lastTransSize;
    }

    @Override
    public Proxy getProxy() {
        return this.proxy;
    }

    @Override
    public int getReadTimeout() {
        return this.readTimeout;
    }

    @Override
    public SocketAddress getServerAddress() {
        Object object = this.server;
        object = object == null ? null : ((Socket)object).getRemoteSocketAddress();
        return object;
    }

    @Override
    public long getSize(String string) throws FtpProtocolException, IOException {
        if (string != null && string.length() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SIZE ");
            stringBuilder.append(string);
            this.issueCommandCheck(stringBuilder.toString());
            if (this.lastReplyCode == FtpReplyCode.FILE_STATUS) {
                string = this.getResponseString();
                return Long.parseLong(string.substring(4, string.length() - 1));
            }
            return -1L;
        }
        throw new IllegalArgumentException("path can't be null or empty");
    }

    @Override
    public String getStatus(String object) throws FtpProtocolException, IOException {
        AbstractStringBuilder abstractStringBuilder;
        if (object == null) {
            object = "STAT";
        } else {
            abstractStringBuilder = new StringBuilder();
            ((StringBuilder)abstractStringBuilder).append("STAT ");
            ((StringBuilder)abstractStringBuilder).append((String)object);
            object = ((StringBuilder)abstractStringBuilder).toString();
        }
        this.issueCommandCheck((String)object);
        object = this.getResponseStrings();
        abstractStringBuilder = new StringBuffer();
        for (int i = 1; i < ((Vector)object).size() - 1; ++i) {
            ((StringBuffer)abstractStringBuilder).append((String)((Vector)object).get(i));
        }
        return ((StringBuffer)abstractStringBuilder).toString();
    }

    @Override
    public String getSystem() throws FtpProtocolException, IOException {
        this.issueCommandCheck("SYST");
        return this.getResponseString().substring(4);
    }

    @Override
    public String getWelcomeMsg() {
        return this.welcomeMsg;
    }

    @Override
    public String getWorkingDirectory() throws FtpProtocolException, IOException {
        this.issueCommandCheck("PWD");
        String string = this.getResponseString();
        if (!string.startsWith("257")) {
            return null;
        }
        return string.substring(5, string.lastIndexOf(34));
    }

    @Override
    public boolean isConnected() {
        boolean bl = this.server != null;
        return bl;
    }

    @Override
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    @Override
    public boolean isPassiveModeEnabled() {
        return true;
    }

    @Override
    public InputStream list(String object) throws FtpProtocolException, IOException {
        if (object == null) {
            object = "LIST";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("LIST ");
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
        }
        object = this.openDataConnection((String)object);
        if (object != null) {
            return this.createInputStream(((Socket)object).getInputStream());
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Iterator<FtpDirEntry> listFiles(String var1_1) throws FtpProtocolException, IOException {
        block5 : {
            var2_2 = null;
            if (var1_1 != null) break block5;
            var3_3 = "MLSD";
            ** GOTO lbl13
        }
        try {
            var3_3 = new StringBuilder();
            var3_3.append("MLSD ");
            var3_3.append((String)var1_1);
            var3_3 = var3_3.toString();
lbl13: // 2 sources:
            var3_3 = this.openDataConnection((String)var3_3);
        }
        catch (FtpProtocolException var3_4) {
            var3_3 = var2_2;
        }
        if (var3_3 != null) {
            var1_1 = new BufferedReader(new InputStreamReader(var3_3.getInputStream()));
            return new FtpFileIterator(this.mlsxParser, (BufferedReader)var1_1);
        }
        if (var1_1 == null) {
            var1_1 = "LIST";
        } else {
            var3_3 = new StringBuilder();
            var3_3.append("LIST ");
            var3_3.append((String)var1_1);
            var1_1 = var3_3.toString();
        }
        var1_1 = this.openDataConnection((String)var1_1);
        if (var1_1 == null) return null;
        var1_1 = new BufferedReader(new InputStreamReader(var1_1.getInputStream()));
        return new FtpFileIterator(this.parser, (BufferedReader)var1_1);
    }

    @Override
    public sun.net.ftp.FtpClient login(String object, char[] object2) throws FtpProtocolException, IOException {
        if (this.isConnected()) {
            if (object != null && ((String)object).length() != 0) {
                this.tryLogin((String)object, (char[])object2);
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < this.serverResponse.size(); ++i) {
                    object2 = this.serverResponse.elementAt(i);
                    if (object2 == null) continue;
                    object = object2;
                    if (((String)object2).length() >= 4) {
                        object = object2;
                        if (((String)object2).startsWith("230")) {
                            object = ((String)object2).substring(4);
                        }
                    }
                    stringBuffer.append((String)object);
                }
                this.welcomeMsg = stringBuffer.toString();
                this.loggedIn = true;
                return this;
            }
            throw new IllegalArgumentException("User name can't be null or empty");
        }
        throw new FtpProtocolException("Not connected yet", FtpReplyCode.BAD_SEQUENCE);
    }

    @Override
    public sun.net.ftp.FtpClient login(String object, char[] object2, String charSequence) throws FtpProtocolException, IOException {
        if (this.isConnected()) {
            if (object != null && ((String)object).length() != 0) {
                this.tryLogin((String)object, (char[])object2);
                if (this.lastReplyCode == FtpReplyCode.NEED_ACCOUNT) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ACCT ");
                    ((StringBuilder)object).append((String)charSequence);
                    this.issueCommandCheck(((StringBuilder)object).toString());
                }
                charSequence = new StringBuffer();
                object = this.serverResponse;
                if (object != null) {
                    Iterator iterator = ((Vector)object).iterator();
                    while (iterator.hasNext()) {
                        object2 = (String)iterator.next();
                        if (object2 == null) continue;
                        object = object2;
                        if (((String)object2).length() >= 4) {
                            object = object2;
                            if (((String)object2).startsWith("230")) {
                                object = ((String)object2).substring(4);
                            }
                        }
                        ((StringBuffer)charSequence).append((String)object);
                    }
                }
                this.welcomeMsg = ((StringBuffer)charSequence).toString();
                this.loggedIn = true;
                return this;
            }
            throw new IllegalArgumentException("User name can't be null or empty");
        }
        throw new FtpProtocolException("Not connected yet", FtpReplyCode.BAD_SEQUENCE);
    }

    @Override
    public sun.net.ftp.FtpClient makeDirectory(String string) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MKD ");
        stringBuilder.append(string);
        this.issueCommandCheck(stringBuilder.toString());
        return this;
    }

    @Override
    public InputStream nameList(String object) throws FtpProtocolException, IOException {
        if (object == null) {
            object = "NLST";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NLST ");
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
        }
        object = this.openDataConnection((String)object);
        if (object != null) {
            return this.createInputStream(((Socket)object).getInputStream());
        }
        return null;
    }

    @Override
    public sun.net.ftp.FtpClient noop() throws FtpProtocolException, IOException {
        this.issueCommandCheck("NOOP");
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient putFile(String object, InputStream inputStream, boolean bl) throws FtpProtocolException, IOException {
        Object object2 = bl ? "STOU " : "STOR ";
        if (this.type == FtpClient.TransferType.BINARY) {
            int n;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object2);
            stringBuilder.append((String)object);
            object = this.createOutputStream(this.openDataConnection(stringBuilder.toString()).getOutputStream());
            object2 = new byte[1500 * 10];
            while ((n = inputStream.read((byte[])object2)) >= 0) {
                if (n <= 0) continue;
                ((OutputStream)object).write((byte[])object2, 0, n);
            }
            ((OutputStream)object).close();
        }
        return this.completePending();
    }

    @Override
    public OutputStream putFileStream(String object, boolean bl) throws FtpProtocolException, IOException {
        String string = bl ? "STOU " : "STOR ";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append((String)object);
        object = this.openDataConnection(stringBuilder.toString());
        if (object == null) {
            return null;
        }
        bl = this.type == FtpClient.TransferType.BINARY;
        return new TelnetOutputStream(((Socket)object).getOutputStream(), bl);
    }

    @Override
    public sun.net.ftp.FtpClient reInit() throws FtpProtocolException, IOException {
        Closeable closeable;
        this.issueCommandCheck("REIN");
        this.loggedIn = false;
        if (this.useCrypto && (closeable = this.server) instanceof SSLSocket) {
            ((SSLSocket)closeable).getSession().invalidate();
            this.server = this.oldSocket;
            this.oldSocket = null;
            try {
                PrintStream printStream;
                closeable = new BufferedOutputStream(this.server.getOutputStream());
                this.out = printStream = new PrintStream((OutputStream)closeable, true, encoding);
                this.in = new BufferedInputStream(this.server.getInputStream());
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(encoding);
                stringBuilder.append("encoding not found");
                throw new InternalError(stringBuilder.toString(), unsupportedEncodingException);
            }
        }
        this.useCrypto = false;
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient removeDirectory(String string) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RMD ");
        stringBuilder.append(string);
        this.issueCommandCheck(stringBuilder.toString());
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient rename(String charSequence, String string) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RNFR ");
        stringBuilder.append((String)charSequence);
        this.issueCommandCheck(stringBuilder.toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("RNTO ");
        ((StringBuilder)charSequence).append(string);
        this.issueCommandCheck(((StringBuilder)charSequence).toString());
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient setConnectTimeout(int n) {
        this.connectTimeout = n;
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient setDirParser(FtpDirParser ftpDirParser) {
        this.parser = ftpDirParser;
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient setReadTimeout(int n) {
        this.readTimeout = n;
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient setRestartOffset(long l) {
        if (l >= 0L) {
            this.restartOffset = l;
            return this;
        }
        throw new IllegalArgumentException("offset can't be negative");
    }

    @Override
    public sun.net.ftp.FtpClient setType(FtpClient.TransferType transferType) throws FtpProtocolException, IOException {
        String string = "NOOP";
        this.type = transferType;
        if (transferType == FtpClient.TransferType.ASCII) {
            string = "TYPE A";
        }
        if (transferType == FtpClient.TransferType.BINARY) {
            string = "TYPE I";
        }
        if (transferType == FtpClient.TransferType.EBCDIC) {
            string = "TYPE E";
        }
        this.issueCommandCheck(string);
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient siteCmd(String string) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SITE ");
        stringBuilder.append(string);
        this.issueCommandCheck(stringBuilder.toString());
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient startSecureSession() throws FtpProtocolException, IOException {
        if (this.isConnected()) {
            Object object;
            if (this.sslFact == null) {
                try {
                    this.sslFact = (SSLSocketFactory)SSLSocketFactory.getDefault();
                }
                catch (Exception exception) {
                    throw new IOException(exception.getLocalizedMessage());
                }
            }
            this.issueCommandCheck("AUTH TLS");
            try {
                object = this.sslFact.createSocket(this.server, this.serverAddr.getHostName(), this.serverAddr.getPort(), true);
                this.oldSocket = this.server;
                this.server = object;
            }
            catch (SSLException sSLException) {
                try {
                    this.disconnect();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                throw sSLException;
            }
            try {
                PrintStream printStream;
                object = new BufferedOutputStream(this.server.getOutputStream());
                this.out = printStream = new PrintStream((OutputStream)object, true, encoding);
                this.in = new BufferedInputStream(this.server.getInputStream());
                this.issueCommandCheck("PBSZ 0");
                this.issueCommandCheck("PROT P");
                this.useCrypto = true;
                return this;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                object = new StringBuilder();
                ((StringBuilder)object).append(encoding);
                ((StringBuilder)object).append("encoding not found");
                throw new InternalError(((StringBuilder)object).toString(), unsupportedEncodingException);
            }
        }
        throw new FtpProtocolException("Not connected yet", FtpReplyCode.BAD_SEQUENCE);
    }

    @Override
    public sun.net.ftp.FtpClient structureMount(String string) throws FtpProtocolException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SMNT ");
        stringBuilder.append(string);
        this.issueCommandCheck(stringBuilder.toString());
        return this;
    }

    @Override
    public sun.net.ftp.FtpClient useKerberos() throws FtpProtocolException, IOException {
        return this;
    }

    private class DefaultParser
    implements FtpDirParser {
        private DefaultParser() {
        }

        @Override
        public FtpDirEntry parseLine(String object) {
            int n;
            boolean[][] arrbl;
            boolean[] arrbl2;
            Object object2;
            Object object3;
            boolean bl;
            Object object4 = null;
            Calendar calendar = Calendar.getInstance();
            int n2 = calendar.get(1);
            boolean bl2 = false;
            Object object5 = null;
            Object object6 = null;
            Object object7 = null;
            Object object8 = null;
            String string = null;
            Object object9 = null;
            for (n = 0; n < patterns.length; ++n) {
                Matcher matcher = patterns[n].matcher((CharSequence)object);
                object2 = object4;
                arrbl2 = object8;
                Object object10 = object7;
                arrbl = object6;
                object3 = object5;
                bl = bl2;
                if (matcher.find()) {
                    object2 = matcher.group(patternGroups[n][0]);
                    string = matcher.group(patternGroups[n][1]);
                    object3 = matcher.group(patternGroups[n][2]);
                    if (patternGroups[n][4] > 0) {
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append((String)object3);
                        ((StringBuilder)object4).append(", ");
                        ((StringBuilder)object4).append(matcher.group(patternGroups[n][4]));
                        object4 = ((StringBuilder)object4).toString();
                    } else {
                        object4 = object3;
                        if (patternGroups[n][3] > 0) {
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append((String)object3);
                            ((StringBuilder)object4).append(", ");
                            ((StringBuilder)object4).append(String.valueOf(n2));
                            object4 = ((StringBuilder)object4).toString();
                        }
                    }
                    if (patternGroups[n][3] > 0) {
                        object8 = matcher.group(patternGroups[n][3]);
                    }
                    if (patternGroups[n][5] > 0) {
                        object7 = matcher.group(patternGroups[n][5]);
                        bl2 = ((String)object7).startsWith("d");
                    }
                    if (patternGroups[n][6] > 0) {
                        object6 = matcher.group(patternGroups[n][6]);
                    }
                    if (patternGroups[n][7] > 0) {
                        object5 = matcher.group(patternGroups[n][7]);
                    }
                    if ("<DIR>".equals(string)) {
                        string = null;
                        bl = true;
                        object9 = object4;
                        arrbl2 = object8;
                        object10 = object7;
                        arrbl = object6;
                        object3 = object5;
                    } else {
                        bl = bl2;
                        object3 = object5;
                        arrbl = object6;
                        object10 = object7;
                        arrbl2 = object8;
                        object9 = object4;
                    }
                }
                object4 = object2;
                object8 = arrbl2;
                object7 = object10;
                object6 = arrbl;
                object5 = object3;
                bl2 = bl;
            }
            if (object4 != null) {
                try {
                    object2 = FtpClient.this.df.parse((String)object9);
                }
                catch (Exception exception) {
                    object2 = null;
                }
                if (object2 != null && object8 != null) {
                    n = object8.indexOf(":");
                    calendar.setTime((Date)object2);
                    calendar.set(10, Integer.parseInt(object8.substring(0, n)));
                    calendar.set(12, Integer.parseInt(object8.substring(n + 1)));
                    object2 = calendar.getTime();
                }
                if (((Matcher)(object3 = linkp.matcher((CharSequence)object4))).find()) {
                    object4 = ((Matcher)object3).group(1);
                }
                arrbl = new boolean[3][3];
                object3 = calendar;
                for (n = 0; n < 3; ++n) {
                    for (n2 = 0; n2 < 3; ++n2) {
                        arrbl2 = arrbl[n];
                        bl = ((String)object7).charAt(n * 3 + n2) != '-';
                        arrbl2[n2] = bl;
                    }
                }
                object7 = new FtpDirEntry((String)object4);
                ((FtpDirEntry)object7).setUser((String)object6).setGroup((String)object5);
                ((FtpDirEntry)object7).setSize(Long.parseLong(string)).setLastModified((Date)object2);
                ((FtpDirEntry)object7).setPermissions(arrbl);
                object = bl2 ? FtpDirEntry.Type.DIR : (object.charAt(0) == 'l' ? FtpDirEntry.Type.LINK : FtpDirEntry.Type.FILE);
                ((FtpDirEntry)object7).setType((FtpDirEntry.Type)((Object)object));
                return object7;
            }
            return null;
        }
    }

    private class FtpFileIterator
    implements Iterator<FtpDirEntry>,
    Closeable {
        private boolean eof = false;
        private FtpDirParser fparser = null;
        private BufferedReader in = null;
        private FtpDirEntry nextFile = null;

        public FtpFileIterator(FtpDirParser ftpDirParser, BufferedReader bufferedReader) {
            this.in = bufferedReader;
            this.fparser = ftpDirParser;
            this.readNext();
        }

        private void readNext() {
            String string;
            this.nextFile = null;
            if (this.eof) {
                return;
            }
            do {
                string = this.in.readLine();
                if (string == null) continue;
                this.nextFile = this.fparser.parseLine(string);
                if (this.nextFile == null) continue;
                return;
            } while (string != null);
            try {
                this.in.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.eof = true;
        }

        @Override
        public void close() throws IOException {
            BufferedReader bufferedReader = this.in;
            if (bufferedReader != null && !this.eof) {
                bufferedReader.close();
            }
            this.eof = true;
            this.nextFile = null;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.nextFile != null;
            return bl;
        }

        @Override
        public FtpDirEntry next() {
            FtpDirEntry ftpDirEntry = this.nextFile;
            this.readNext();
            return ftpDirEntry;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class MLSxParser
    implements FtpDirParser {
        private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");

        private MLSxParser() {
        }

        @Override
        public FtpDirEntry parseLine(String object) {
            Object object2;
            int n = ((String)object).lastIndexOf(";");
            if (n > 0) {
                object2 = ((String)object).substring(n + 1).trim();
                object = ((String)object).substring(0, n);
            } else {
                object2 = ((String)object).trim();
                object = "";
            }
            FtpDirEntry ftpDirEntry = new FtpDirEntry((String)object2);
            while (!((String)object).isEmpty()) {
                n = ((String)object).indexOf(";");
                if (n > 0) {
                    object2 = ((String)object).substring(0, n);
                    object = ((String)object).substring(n + 1);
                } else {
                    object2 = object;
                    object = "";
                }
                if ((n = ((String)object2).indexOf("=")) <= 0) continue;
                ftpDirEntry.addFact(((String)object2).substring(0, n), ((String)object2).substring(n + 1));
            }
            object = ftpDirEntry.getFact("Size");
            if (object != null) {
                ftpDirEntry.setSize(Long.parseLong((String)object));
            }
            if ((object2 = ftpDirEntry.getFact("Modify")) != null) {
                object = null;
                try {
                    object = object2 = this.df.parse((String)object2);
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
                if (object != null) {
                    ftpDirEntry.setLastModified((Date)object);
                }
            }
            if ((object2 = ftpDirEntry.getFact("Create")) != null) {
                object = null;
                try {
                    object = object2 = this.df.parse((String)object2);
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
                if (object != null) {
                    ftpDirEntry.setCreated((Date)object);
                }
            }
            if ((object = ftpDirEntry.getFact("Type")) != null) {
                if (((String)object).equalsIgnoreCase("file")) {
                    ftpDirEntry.setType(FtpDirEntry.Type.FILE);
                }
                if (((String)object).equalsIgnoreCase("dir")) {
                    ftpDirEntry.setType(FtpDirEntry.Type.DIR);
                }
                if (((String)object).equalsIgnoreCase("cdir")) {
                    ftpDirEntry.setType(FtpDirEntry.Type.CDIR);
                }
                if (((String)object).equalsIgnoreCase("pdir")) {
                    ftpDirEntry.setType(FtpDirEntry.Type.PDIR);
                }
            }
            return ftpDirEntry;
        }
    }

}

