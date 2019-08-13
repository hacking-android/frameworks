/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.net.DefaultFileNameMap
 */
package java.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.DefaultFileNameMap;
import java.net.FileNameMap;
import java.net.URL;
import java.net.UnknownContentHandler;
import java.net.UnknownServiceException;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.Permission;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import sun.net.www.MessageHeader;
import sun.security.action.GetPropertyAction;
import sun.security.util.SecurityConstants;

public abstract class URLConnection {
    private static final String contentClassPrefix = "sun.net.www.content";
    private static final String contentPathProp = "java.content.handler.pkgs";
    private static boolean defaultAllowUserInteraction = false;
    private static boolean defaultUseCaches = true;
    static ContentHandlerFactory factory;
    private static FileNameMap fileNameMap;
    private static Hashtable<String, ContentHandler> handlers;
    protected boolean allowUserInteraction = defaultAllowUserInteraction;
    private int connectTimeout;
    protected boolean connected = false;
    protected boolean doInput = true;
    protected boolean doOutput = false;
    protected long ifModifiedSince = 0L;
    private int readTimeout;
    private MessageHeader requests;
    protected URL url;
    protected boolean useCaches = defaultUseCaches;

    static {
        handlers = new Hashtable();
    }

    protected URLConnection(URL uRL) {
        this.url = uRL;
    }

    private static boolean checkfpx(InputStream inputStream) throws IOException {
        inputStream.mark(256);
        long l = URLConnection.skipForward(inputStream, 28L);
        if (l < 28L) {
            inputStream.reset();
            return false;
        }
        int[] arrn = new int[16];
        if (URLConnection.readBytes(arrn, 2, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        int n = arrn[0];
        if (URLConnection.readBytes(arrn, 2, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        int n2 = n == 254 ? arrn[0] + (arrn[1] << 8) : (arrn[0] << 8) + arrn[1];
        l = l + 2L + 2L;
        long l2 = URLConnection.skipForward(inputStream, l = 48L - l);
        if (l2 < l) {
            inputStream.reset();
            return false;
        }
        if (URLConnection.readBytes(arrn, 4, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        int n3 = n == 254 ? arrn[0] + (arrn[1] << 8) + (arrn[2] << 16) + (arrn[3] << 24) : (arrn[0] << 24) + (arrn[1] << 16) + (arrn[2] << 8) + arrn[3];
        inputStream.reset();
        l = (long)(1 << n2) * (long)n3 + 512L + 80L;
        if (l < 0L) {
            return false;
        }
        inputStream.mark((int)l + 48);
        if (URLConnection.skipForward(inputStream, l) < l) {
            inputStream.reset();
            return false;
        }
        if (URLConnection.readBytes(arrn, 16, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        if (n == 254 && arrn[0] == 0 && arrn[2] == 97 && arrn[3] == 86 && arrn[4] == 84 && arrn[5] == 193 && arrn[6] == 206 && arrn[7] == 17 && arrn[8] == 133 && arrn[9] == 83 && arrn[10] == 0 && arrn[11] == 170 && arrn[12] == 0 && arrn[13] == 161 && arrn[14] == 249 && arrn[15] == 91) {
            inputStream.reset();
            return true;
        }
        if (arrn[3] == 0 && arrn[1] == 97 && arrn[0] == 86 && arrn[5] == 84 && arrn[4] == 193 && arrn[7] == 206 && arrn[6] == 17 && arrn[8] == 133 && arrn[9] == 83 && arrn[10] == 0 && arrn[11] == 170 && arrn[12] == 0 && arrn[13] == 161 && arrn[14] == 249 && arrn[15] == 91) {
            inputStream.reset();
            return true;
        }
        inputStream.reset();
        return false;
    }

    private String getContentHandlerPkgPrefixes() {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = AccessController.doPrivileged(new GetPropertyAction(contentPathProp, ""));
        if (charSequence != "") {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(contentClassPrefix);
        return ((StringBuilder)charSequence).toString();
    }

    public static boolean getDefaultAllowUserInteraction() {
        return defaultAllowUserInteraction;
    }

    @Deprecated
    public static String getDefaultRequestProperty(String string) {
        return null;
    }

    public static FileNameMap getFileNameMap() {
        synchronized (URLConnection.class) {
            Object object;
            if (fileNameMap == null) {
                object = new DefaultFileNameMap();
                fileNameMap = object;
            }
            object = fileNameMap;
            return object;
        }
    }

    public static String guessContentTypeFromName(String string) {
        return URLConnection.getFileNameMap().getContentTypeFor(string);
    }

    public static String guessContentTypeFromStream(InputStream inputStream) throws IOException {
        if (!inputStream.markSupported()) {
            return null;
        }
        inputStream.mark(16);
        int n = inputStream.read();
        int n2 = inputStream.read();
        int n3 = inputStream.read();
        int n4 = inputStream.read();
        int n5 = inputStream.read();
        int n6 = inputStream.read();
        int n7 = inputStream.read();
        int n8 = inputStream.read();
        int n9 = inputStream.read();
        int n10 = inputStream.read();
        int n11 = inputStream.read();
        int n12 = inputStream.read();
        int n13 = inputStream.read();
        int n14 = inputStream.read();
        int n15 = inputStream.read();
        int n16 = inputStream.read();
        inputStream.reset();
        if (n == 202 && n2 == 254 && n3 == 186 && n4 == 190) {
            return "application/java-vm";
        }
        if (n == 172 && n2 == 237) {
            return "application/x-java-serialized-object";
        }
        if (n == 60) {
            if (!(n2 == 33 || n2 == 104 && (n3 == 116 && n4 == 109 && n5 == 108 || n3 == 101 && n4 == 97 && n5 == 100) || n2 == 98 && n3 == 111 && n4 == 100 && n5 == 121 || n2 == 72 && (n3 == 84 && n4 == 77 && n5 == 76 || n3 == 69 && n4 == 65 && n5 == 68) || n2 == 66 && n3 == 79 && n4 == 68 && n5 == 89)) {
                if (n2 == 63 && n3 == 120 && n4 == 109 && n5 == 108 && n6 == 32) {
                    return "application/xml";
                }
            } else {
                return "text/html";
            }
        }
        if (n == 239 && n2 == 187 && n3 == 191 && n4 == 60 && n5 == 63 && n6 == 120) {
            return "application/xml";
        }
        if (n == 254 && n2 == 255 && n3 == 0 && n4 == 60 && n5 == 0 && n6 == 63 && n7 == 0 && n8 == 120) {
            return "application/xml";
        }
        if (n == 255 && n2 == 254 && n3 == 60 && n4 == 0 && n5 == 63 && n6 == 0 && n7 == 120 && n8 == 0) {
            return "application/xml";
        }
        if (n == 0 && n2 == 0 && n3 == 254 && n4 == 255 && n5 == 0 && n6 == 0 && n7 == 0 && n8 == 60 && n9 == 0 && n10 == 0 && n11 == 0 && n12 == 63 && n13 == 0 && n14 == 0 && n15 == 0 && n16 == 120) {
            return "application/xml";
        }
        if (n == 255 && n2 == 254 && n3 == 0 && n4 == 0 && n5 == 60 && n6 == 0 && n7 == 0 && n8 == 0 && n9 == 63 && n10 == 0 && n11 == 0 && n12 == 0 && n13 == 120 && n14 == 0 && n15 == 0 && n16 == 0) {
            return "application/xml";
        }
        if (n == 71 && n2 == 73 && n3 == 70 && n4 == 56) {
            return "image/gif";
        }
        if (n == 35 && n2 == 100 && n3 == 101 && n4 == 102) {
            return "image/x-bitmap";
        }
        if (n == 33 && n2 == 32 && n3 == 88 && n4 == 80 && n5 == 77 && n6 == 50) {
            return "image/x-pixmap";
        }
        if (n == 137 && n2 == 80 && n3 == 78 && n4 == 71 && n5 == 13 && n6 == 10 && n7 == 26 && n8 == 10) {
            return "image/png";
        }
        if (n == 255 && n2 == 216 && n3 == 255) {
            if (n4 != 224 && n4 != 238) {
                if (n4 == 225 && n7 == 69 && n8 == 120 && n9 == 105 && n10 == 102 && n11 == 0) {
                    return "image/jpeg";
                }
            } else {
                return "image/jpeg";
            }
        }
        if (n == 208 && n2 == 207 && n3 == 17 && n4 == 224 && n5 == 161 && n6 == 177 && n7 == 26 && n8 == 225 && URLConnection.checkfpx(inputStream)) {
            return "image/vnd.fpx";
        }
        if (n == 46 && n2 == 115 && n3 == 110 && n4 == 100) {
            return "audio/basic";
        }
        if (n == 100 && n2 == 110 && n3 == 115 && n4 == 46) {
            return "audio/basic";
        }
        if (n == 82 && n2 == 73 && n3 == 70 && n4 == 70) {
            return "audio/x-wav";
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ContentHandler lookupContentHandlerClassFor(String object) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String string = this.typeToPackageName((String)object);
        StringTokenizer stringTokenizer = new StringTokenizer(this.getContentHandlerPkgPrefixes(), "|");
        while (stringTokenizer.hasMoreTokens()) {
            Object object2 = stringTokenizer.nextToken().trim();
            try {
                block6 : {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append(".");
                    ((StringBuilder)object).append(string);
                    String string2 = ((StringBuilder)object).toString();
                    object = null;
                    try {
                        object = object2 = Class.forName(string2);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        object2 = ClassLoader.getSystemClassLoader();
                        if (object2 == null) break block6;
                        object = ((ClassLoader)object2).loadClass(string2);
                    }
                }
                if (object == null) continue;
                return (ContentHandler)((Class)object).newInstance();
            }
            catch (Exception exception) {
                continue;
            }
            break;
        }
        return UnknownContentHandler.INSTANCE;
    }

    private static int readBytes(int[] arrn, int n, InputStream inputStream) throws IOException {
        byte[] arrby = new byte[n];
        if (inputStream.read(arrby, 0, n) < n) {
            return -1;
        }
        for (int i = 0; i < n; ++i) {
            arrn[i] = arrby[i] & 255;
        }
        return 0;
    }

    public static void setContentHandlerFactory(ContentHandlerFactory object) {
        synchronized (URLConnection.class) {
            if (factory == null) {
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    securityManager.checkSetFactory();
                }
                factory = object;
                return;
            }
            object = new Error("factory already defined");
            throw object;
        }
    }

    public static void setDefaultAllowUserInteraction(boolean bl) {
        defaultAllowUserInteraction = bl;
    }

    @Deprecated
    public static void setDefaultRequestProperty(String string, String string2) {
    }

    public static void setFileNameMap(FileNameMap fileNameMap) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        URLConnection.fileNameMap = fileNameMap;
    }

    private static long skipForward(InputStream inputStream, long l) throws IOException {
        long l2 = 0L;
        while (l2 != l) {
            long l3 = inputStream.skip(l - l2);
            long l4 = l2;
            if (l3 <= 0L) {
                if (inputStream.read() == -1) {
                    return l2;
                }
                l4 = l2 + 1L;
            }
            l2 = l4 + l3;
        }
        return l2;
    }

    private String stripOffParameters(String string) {
        if (string == null) {
            return null;
        }
        int n = string.indexOf(59);
        if (n > 0) {
            return string.substring(0, n);
        }
        return string;
    }

    private String typeToPackageName(String string) {
        string = string.toLowerCase();
        int n = string.length();
        char[] arrc = new char[n];
        string.getChars(0, n, arrc, 0);
        for (int i = 0; i < n; ++i) {
            char c = arrc[i];
            if (c == '/') {
                arrc[i] = (char)46;
                continue;
            }
            if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || '0' <= c && c <= '9') continue;
            arrc[i] = (char)95;
        }
        return new String(arrc);
    }

    public void addRequestProperty(String string, String string2) {
        if (!this.connected) {
            if (string != null) {
                if (this.requests == null) {
                    this.requests = new MessageHeader();
                }
                this.requests.add(string, string2);
                return;
            }
            throw new NullPointerException("key is null");
        }
        throw new IllegalStateException("Already connected");
    }

    public abstract void connect() throws IOException;

    public boolean getAllowUserInteraction() {
        return this.allowUserInteraction;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public Object getContent() throws IOException {
        this.getInputStream();
        return this.getContentHandler().getContent(this);
    }

    public Object getContent(Class[] arrclass) throws IOException {
        this.getInputStream();
        return this.getContentHandler().getContent(this, arrclass);
    }

    public String getContentEncoding() {
        return this.getHeaderField("content-encoding");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    ContentHandler getContentHandler() throws IOException {
        // MONITORENTER : this
        var1_1 = this.stripOffParameters(this.getContentType());
        var2_3 = null;
        var3_5 = var1_1;
        if (var1_1 == null) {
            var3_5 = var1_1 = URLConnection.guessContentTypeFromName(this.url.getFile());
            if (var1_1 == null) {
                var3_5 = URLConnection.guessContentTypeFromStream(this.getInputStream());
            }
        }
        if (var3_5 == null) {
            var3_5 = UnknownContentHandler.INSTANCE;
            // MONITOREXIT : this
            return var3_5;
        }
        try {
            var2_3 = var1_1 = URLConnection.handlers.get(var3_5);
            ** if (var2_3 == null) goto lbl-1000
        }
        catch (Exception var1_2) {
            // empty catch block
        }
lbl-1000: // 1 sources:
        {
            // MONITOREXIT : this
            return var2_3;
        }
lbl-1000: // 1 sources:
        {
        }
        if (URLConnection.factory != null) {
            var2_3 = URLConnection.factory.createContentHandler((String)var3_5);
        }
        var1_1 = var2_3;
        if (var2_3 == null) {
            try {
                var2_3 = this.lookupContentHandlerClassFor((String)var3_5);
            }
            catch (Exception var2_4) {
                var2_4.printStackTrace();
                var2_3 = UnknownContentHandler.INSTANCE;
            }
            URLConnection.handlers.put((String)var3_5, (ContentHandler)var2_3);
            var1_1 = var2_3;
        }
        // MONITOREXIT : this
        return var1_1;
    }

    public int getContentLength() {
        long l = this.getContentLengthLong();
        if (l > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)l;
    }

    public long getContentLengthLong() {
        return this.getHeaderFieldLong("content-length", -1L);
    }

    public String getContentType() {
        return this.getHeaderField("content-type");
    }

    public long getDate() {
        return this.getHeaderFieldDate("date", 0L);
    }

    public boolean getDefaultUseCaches() {
        return defaultUseCaches;
    }

    public boolean getDoInput() {
        return this.doInput;
    }

    public boolean getDoOutput() {
        return this.doOutput;
    }

    public long getExpiration() {
        return this.getHeaderFieldDate("expires", 0L);
    }

    public String getHeaderField(int n) {
        return null;
    }

    public String getHeaderField(String string) {
        return null;
    }

    public long getHeaderFieldDate(String string, long l) {
        string = this.getHeaderField(string);
        try {
            long l2 = Date.parse(string);
            return l2;
        }
        catch (Exception exception) {
            return l;
        }
    }

    public int getHeaderFieldInt(String string, int n) {
        string = this.getHeaderField(string);
        try {
            int n2 = Integer.parseInt(string);
            return n2;
        }
        catch (Exception exception) {
            return n;
        }
    }

    public String getHeaderFieldKey(int n) {
        return null;
    }

    public long getHeaderFieldLong(String string, long l) {
        string = this.getHeaderField(string);
        try {
            long l2 = Long.parseLong(string);
            return l2;
        }
        catch (Exception exception) {
            return l;
        }
    }

    public Map<String, List<String>> getHeaderFields() {
        return Collections.emptyMap();
    }

    public long getIfModifiedSince() {
        return this.ifModifiedSince;
    }

    public InputStream getInputStream() throws IOException {
        throw new UnknownServiceException("protocol doesn't support input");
    }

    public long getLastModified() {
        return this.getHeaderFieldDate("last-modified", 0L);
    }

    public OutputStream getOutputStream() throws IOException {
        throw new UnknownServiceException("protocol doesn't support output");
    }

    public Permission getPermission() throws IOException {
        return SecurityConstants.ALL_PERMISSION;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public Map<String, List<String>> getRequestProperties() {
        if (!this.connected) {
            MessageHeader messageHeader = this.requests;
            if (messageHeader == null) {
                return Collections.emptyMap();
            }
            return messageHeader.getHeaders(null);
        }
        throw new IllegalStateException("Already connected");
    }

    public String getRequestProperty(String string) {
        if (!this.connected) {
            MessageHeader messageHeader = this.requests;
            if (messageHeader == null) {
                return null;
            }
            return messageHeader.findValue(string);
        }
        throw new IllegalStateException("Already connected");
    }

    public URL getURL() {
        return this.url;
    }

    public boolean getUseCaches() {
        return this.useCaches;
    }

    public void setAllowUserInteraction(boolean bl) {
        if (!this.connected) {
            this.allowUserInteraction = bl;
            return;
        }
        throw new IllegalStateException("Already connected");
    }

    public void setConnectTimeout(int n) {
        if (n >= 0) {
            this.connectTimeout = n;
            return;
        }
        throw new IllegalArgumentException("timeout can not be negative");
    }

    public void setDefaultUseCaches(boolean bl) {
        defaultUseCaches = bl;
    }

    public void setDoInput(boolean bl) {
        if (!this.connected) {
            this.doInput = bl;
            return;
        }
        throw new IllegalStateException("Already connected");
    }

    public void setDoOutput(boolean bl) {
        if (!this.connected) {
            this.doOutput = bl;
            return;
        }
        throw new IllegalStateException("Already connected");
    }

    public void setIfModifiedSince(long l) {
        if (!this.connected) {
            this.ifModifiedSince = l;
            return;
        }
        throw new IllegalStateException("Already connected");
    }

    public void setReadTimeout(int n) {
        if (n >= 0) {
            this.readTimeout = n;
            return;
        }
        throw new IllegalArgumentException("timeout can not be negative");
    }

    public void setRequestProperty(String string, String string2) {
        if (!this.connected) {
            if (string != null) {
                if (this.requests == null) {
                    this.requests = new MessageHeader();
                }
                this.requests.set(string, string2);
                return;
            }
            throw new NullPointerException("key is null");
        }
        throw new IllegalStateException("Already connected");
    }

    public void setUseCaches(boolean bl) {
        if (!this.connected) {
            this.useCaches = bl;
            return;
        }
        throw new IllegalStateException("Already connected");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(":");
        stringBuilder.append(this.url);
        return stringBuilder.toString();
    }
}

