/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.NetPermission;
import java.net.Parts;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.net.UrlDeserializedState;
import java.security.Permission;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;
import sun.net.ApplicationProxy;
import sun.net.www.protocol.file.Handler;
import sun.security.util.SecurityConstants;

public final class URL
implements Serializable {
    private static final Set<String> BUILTIN_HANDLER_CLASS_NAMES = URL.createBuiltinHandlerClassNames();
    static URLStreamHandlerFactory factory;
    static Hashtable<String, URLStreamHandler> handlers;
    private static final String protocolPathProp = "java.protocol.handler.pkgs";
    private static final ObjectStreamField[] serialPersistentFields;
    static final long serialVersionUID = -7627629688361524110L;
    private static Object streamHandlerLock;
    private String authority;
    private String file;
    transient URLStreamHandler handler;
    private int hashCode;
    private String host;
    transient InetAddress hostAddress;
    private transient String path;
    private int port;
    private String protocol;
    private transient String query;
    private String ref;
    private transient UrlDeserializedState tempState;
    private transient String userInfo;

    static {
        handlers = new Hashtable();
        streamHandlerLock = new Object();
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("protocol", String.class), new ObjectStreamField("host", String.class), new ObjectStreamField("port", Integer.TYPE), new ObjectStreamField("authority", String.class), new ObjectStreamField("file", String.class), new ObjectStreamField("ref", String.class)};
    }

    public URL(String string) throws MalformedURLException {
        this(null, string);
    }

    public URL(String string, String string2, int n, String string3) throws MalformedURLException {
        this(string, string2, n, string3, null);
    }

    public URL(String object, String object2, int n, String string, URLStreamHandler uRLStreamHandler) throws MalformedURLException {
        Object object3;
        this.port = -1;
        this.hashCode = -1;
        if (uRLStreamHandler != null && (object3 = System.getSecurityManager()) != null) {
            this.checkSpecifyHandler((SecurityManager)object3);
        }
        this.protocol = object3 = ((String)object).toLowerCase();
        object = object2;
        if (object2 != null) {
            object = object2;
            if (((String)object2).indexOf(58) >= 0) {
                object = object2;
                if (!((String)object2).startsWith("[")) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("[");
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append("]");
                    object = ((StringBuilder)object).toString();
                }
            }
            this.host = object;
            if (n >= -1) {
                this.port = n;
                if (n == -1) {
                    object2 = object;
                } else {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(":");
                    ((StringBuilder)object2).append(n);
                    object2 = ((StringBuilder)object2).toString();
                }
                this.authority = object2;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid port number :");
                ((StringBuilder)object).append(n);
                throw new MalformedURLException(((StringBuilder)object).toString());
            }
        }
        object = new Parts(string, (String)object);
        this.path = ((Parts)object).getPath();
        this.query = ((Parts)object).getQuery();
        if (this.query != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(this.path);
            ((StringBuilder)object2).append("?");
            ((StringBuilder)object2).append(this.query);
            this.file = ((StringBuilder)object2).toString();
        } else {
            this.file = this.path;
        }
        this.ref = ((Parts)object).getRef();
        object = uRLStreamHandler;
        if (uRLStreamHandler == null) {
            object = object2 = URL.getURLStreamHandler((String)object3);
            if (object2 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("unknown protocol: ");
                ((StringBuilder)object).append((String)object3);
                throw new MalformedURLException(((StringBuilder)object).toString());
            }
        }
        this.handler = object;
    }

    public URL(String string, String string2, String string3) throws MalformedURLException {
        this(string, string2, -1, string3);
    }

    public URL(URL uRL, String string) throws MalformedURLException {
        this(uRL, string, null);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public URL(URL var1_1, String var2_4, URLStreamHandler var3_5) throws MalformedURLException {
        block33 : {
            block30 : {
                block32 : {
                    block31 : {
                        block28 : {
                            block27 : {
                                super();
                                this.port = -1;
                                this.hashCode = -1;
                                var4_8 = 0;
                                var5_9 = null;
                                var6_10 = 0;
                                var7_11 = 0;
                                if (var3_7 != null && (var8_12 = System.getSecurityManager()) != null) {
                                    this.checkSpecifyHandler((SecurityManager)var8_12);
                                }
                                var9_13 = var2_6.length();
                                do {
                                    var10_14 = var4_8;
                                    if (var9_13 <= 0) break;
                                    var10_14 = var4_8;
                                    if (var2_6.charAt(var9_13 - 1) > ' ') break;
                                    --var9_13;
                                } while (true);
                                while (var10_14 < var9_13) {
                                    if (var2_6.charAt(var10_14) > ' ') break;
                                    ++var10_14;
                                }
                                var4_8 = var10_14;
                                if (!var2_6.regionMatches(true, var10_14, "url:", 0, 4)) break block27;
                                var4_8 = var10_14 + 4;
                            }
                            var11_15 = var6_10;
                            if (var4_8 >= var2_6.length()) break block28;
                            var11_15 = var6_10;
                            if (var2_6.charAt(var4_8) != '#') break block28;
                            var11_15 = 1;
                        }
                        var6_10 = var4_8;
                        do {
                            block29 : {
                                var10_14 = var4_8;
                                var8_12 = var5_9;
                                if (var11_15 != 0) break;
                                var10_14 = var4_8;
                                var8_12 = var5_9;
                                if (var6_10 >= var9_13) break;
                                var12_16 = var2_6.charAt(var6_10);
                                var10_14 = var4_8;
                                var8_12 = var5_9;
                                if (var12_16 == '/') break;
                                if (var12_16 != ':') break block29;
                                var13_17 = var2_6.substring(var4_8, var6_10).toLowerCase();
                                var10_14 = var4_8;
                                var8_12 = var5_9;
                                if (!this.isValidProtocol((String)var13_17)) break;
                                var8_12 = var13_17;
                                var10_14 = var6_10 + 1;
                                break;
                            }
                            ++var6_10;
                        } while (true);
                        this.protocol = var8_12;
                        var4_8 = var7_11;
                        var13_17 = var3_7;
                        if (var1_1 /* !! */  == null) break block30;
                        if (var8_12 == null) break block31;
                        var4_8 = var7_11;
                        var13_17 = var3_7;
                        if (!var8_12.equalsIgnoreCase(var1_1 /* !! */ .protocol)) break block30;
                    }
                    var5_9 = var3_7;
                    if (var3_7 != null) ** GOTO lbl79
                    var5_9 = var1_1 /* !! */ .handler;
lbl79: // 2 sources:
                    var13_17 = var1_1 /* !! */ .path;
                    var3_7 = var8_12;
                    if (var13_17 == null) break block32;
                    var3_7 = var8_12;
                    if (!var13_17.startsWith("/")) break block32;
                    var3_7 = null;
                }
                var4_8 = var7_11;
                var13_17 = var5_9;
                if (var3_7 == null) {
                    this.protocol = var1_1 /* !! */ .protocol;
                    this.authority = var1_1 /* !! */ .authority;
                    this.userInfo = var1_1 /* !! */ .userInfo;
                    this.host = var1_1 /* !! */ .host;
                    this.port = var1_1 /* !! */ .port;
                    this.file = var1_1 /* !! */ .file;
                    this.path = var1_1 /* !! */ .path;
                    var4_8 = 1;
                    var13_17 = var5_9;
                }
            }
            if (this.protocol == null) ** GOTO lbl131
            var3_7 = var13_17;
            if (var13_17 != null) ** GOTO lbl116
            var3_7 = var8_12 = URL.getURLStreamHandler(this.protocol);
            if (var8_12 != null) ** GOTO lbl116
            super();
            var2_6.append("unknown protocol: ");
            var2_6.append(this.protocol);
            var1_2 = new MalformedURLException(var2_6.toString());
            throw var1_2;
lbl116: // 2 sources:
            this.handler = var3_7;
            var6_10 = var2_6.indexOf(35, var10_14);
            var11_15 = var9_13;
            if (var6_10 < 0) break block33;
            this.ref = var2_6.substring(var6_10 + 1, var9_13);
            var11_15 = var6_10;
        }
        if (var4_8 == 0 || var10_14 != var11_15) ** GOTO lbl129
        try {
            this.query = var1_1 /* !! */ .query;
            if (this.ref == null) {
                this.ref = var1_1 /* !! */ .ref;
            }
lbl129: // 4 sources:
            var3_7.parseURL(this, (String)var2_6, var10_14, var11_15);
            return;
lbl131: // 1 sources:
            var3_7 = new StringBuilder();
            var3_7.append("no protocol: ");
            var3_7.append((String)var2_6);
            var1_3 = new MalformedURLException(var3_7.toString());
            throw var1_3;
        }
        catch (Exception var1_4) {
            var2_6 = new MalformedURLException(var1_4.getMessage());
            var2_6.initCause(var1_4);
            throw var2_6;
        }
        catch (MalformedURLException var1_5) {
            throw var1_5;
        }
    }

    private void checkSpecifyHandler(SecurityManager securityManager) {
        securityManager.checkPermission(SecurityConstants.SPECIFY_HANDLER_PERMISSION);
    }

    private static URLStreamHandler createBuiltinHandler(String string) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        URLStreamHandler uRLStreamHandler = null;
        if (string.equals("file")) {
            uRLStreamHandler = new Handler();
        } else if (string.equals("ftp")) {
            uRLStreamHandler = new sun.net.www.protocol.ftp.Handler();
        } else if (string.equals("jar")) {
            uRLStreamHandler = new sun.net.www.protocol.jar.Handler();
        } else if (string.equals("http")) {
            uRLStreamHandler = (URLStreamHandler)Class.forName("com.android.okhttp.HttpHandler").newInstance();
        } else if (string.equals("https")) {
            uRLStreamHandler = (URLStreamHandler)Class.forName("com.android.okhttp.HttpsHandler").newInstance();
        }
        return uRLStreamHandler;
    }

    private static Set<String> createBuiltinHandlerClassNames() {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("sun.net.www.protocol.file.Handler");
        hashSet.add("sun.net.www.protocol.ftp.Handler");
        hashSet.add("sun.net.www.protocol.jar.Handler");
        hashSet.add("com.android.okhttp.HttpHandler");
        hashSet.add("com.android.okhttp.HttpsHandler");
        return Collections.unmodifiableSet(hashSet);
    }

    private URL fabricateNewURL() throws InvalidObjectException {
        Object object = this.tempState.reconstituteUrlString();
        try {
            URL uRL = new URL((String)object);
            uRL.setSerializedHashCode(this.tempState.getHashCode());
            this.resetState();
            return uRL;
        }
        catch (MalformedURLException malformedURLException) {
            this.resetState();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed URL: ");
            stringBuilder.append((String)object);
            object = new InvalidObjectException(stringBuilder.toString());
            ((Throwable)object).initCause(malformedURLException);
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static URLStreamHandler getURLStreamHandler(String string) {
        Object object;
        Object object2;
        Object object3 = handlers.get(string);
        Object object4 = object3;
        if (object3 != null) return object4;
        boolean bl = false;
        object4 = factory;
        if (object4 != null) {
            object3 = object4.createURLStreamHandler(string);
            bl = true;
        }
        object4 = object3;
        if (object3 == null) {
            object2 = new StringTokenizer(System.getProperty(protocolPathProp, ""), "|");
            do {
                object4 = object3;
                if (object3 != null) break;
                object4 = object3;
                if (!((StringTokenizer)object2).hasMoreTokens()) break;
                object = ((StringTokenizer)object2).nextToken().trim();
                try {
                    block17 : {
                        object4 = new Object();
                        ((StringBuilder)object4).append((String)object);
                        ((StringBuilder)object4).append(".");
                        ((StringBuilder)object4).append(string);
                        ((StringBuilder)object4).append(".Handler");
                        String string2 = ((StringBuilder)object4).toString();
                        object4 = null;
                        try {
                            object = Class.forName(string2, true, ClassLoader.getSystemClassLoader());
                            object4 = object;
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            object = Thread.currentThread().getContextClassLoader();
                            if (object == null) break block17;
                            object4 = Class.forName(string2, true, (ClassLoader)object);
                        }
                    }
                    object = object3;
                    if (object4 != null) {
                        object = (URLStreamHandler)((Class)object4).newInstance();
                    }
                    object3 = object;
                }
                catch (ReflectiveOperationException reflectiveOperationException) {}
            } while (true);
        }
        object3 = object4;
        if (object4 == null) {
            try {
                object3 = URL.createBuiltinHandler(string);
            }
            catch (Exception exception) {
                throw new AssertionError(exception);
            }
        }
        object2 = streamHandlerLock;
        synchronized (object2) {
            object = handlers.get(string);
            if (object != null) {
                return object;
            }
            object4 = object;
            if (!bl) {
                object4 = object;
                if (factory != null) {
                    object4 = factory.createURLStreamHandler(string);
                }
            }
            if (object4 != null) {
                object3 = object4;
            }
            if (object3 == null) return object3;
            handlers.put(string, (URLStreamHandler)object3);
            return object3;
        }
    }

    private boolean isBuiltinStreamHandler(String string) {
        return BUILTIN_HANDLER_CLASS_NAMES.contains(string);
    }

    private boolean isValidProtocol(String string) {
        int n = string.length();
        if (n < 1) {
            return false;
        }
        if (!Character.isLetter(string.charAt(0))) {
            return false;
        }
        for (int i = 1; i < n; ++i) {
            char c = string.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '.' || c == '+' || c == '-') continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        synchronized (this) {
            Object object2 = ((ObjectInputStream)object).readFields();
            String string = (String)((ObjectInputStream.GetField)object2).get("protocol", null);
            if (URL.getURLStreamHandler(string) == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("unknown protocol: ");
                ((StringBuilder)object).append(string);
                IOException iOException = new IOException(((StringBuilder)object).toString());
                throw iOException;
            }
            Object object3 = (String)((ObjectInputStream.GetField)object2).get("host", null);
            int n = ((ObjectInputStream.GetField)object2).get("port", -1);
            object = (String)((ObjectInputStream.GetField)object2).get("authority", null);
            String string2 = (String)((ObjectInputStream.GetField)object2).get("file", null);
            String string3 = (String)((ObjectInputStream.GetField)object2).get("ref", null);
            if (object == null && (object3 != null && ((String)object3).length() > 0 || n != -1)) {
                object = object3;
                if (object3 == null) {
                    object = "";
                }
                if (n == -1) {
                    object3 = object;
                } else {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append((String)object);
                    ((StringBuilder)object3).append(":");
                    ((StringBuilder)object3).append(n);
                    object3 = ((StringBuilder)object3).toString();
                }
                object2 = object3;
                object3 = object;
                object = object2;
            }
            this.tempState = object2 = new UrlDeserializedState(string, (String)object3, n, (String)object, string2, string3, -1);
            return;
        }
    }

    private Object readResolve() throws ObjectStreamException {
        Object object = URL.getURLStreamHandler(this.tempState.getProtocol());
        object = this.isBuiltinStreamHandler(object.getClass().getName()) ? this.fabricateNewURL() : this.setDeserializedFields((URLStreamHandler)object);
        return object;
    }

    private void resetState() {
        this.protocol = null;
        this.host = null;
        this.port = -1;
        this.file = null;
        this.authority = null;
        this.ref = null;
        this.hashCode = -1;
        this.handler = null;
        this.query = null;
        this.path = null;
        this.userInfo = null;
        this.tempState = null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private URL setDeserializedFields(URLStreamHandler var1_1) {
        block10 : {
            var2_2 = null;
            var3_3 = null;
            var4_4 = this.tempState.getProtocol();
            var5_5 = this.tempState.getHost();
            var6_6 = this.tempState.getPort();
            var7_7 = this.tempState.getAuthority();
            var8_8 = this.tempState.getFile();
            var9_9 = this.tempState.getRef();
            var10_10 = this.tempState.getHashCode();
            if (var7_7 != null || (var5_5 == null || var5_5.length() <= 0) && var6_6 == -1) break block10;
            var2_2 = var5_5;
            if (var5_5 == null) {
                var2_2 = "";
            }
            if (var6_6 == -1) {
                var11_11 = var2_2;
            } else {
                var11_11 = new StringBuilder();
                var11_11.append(var2_2);
                var11_11.append(":");
                var11_11.append(var6_6);
                var11_11 = var11_11.toString();
            }
            var5_5 = var11_11;
            var12_12 = var2_2.lastIndexOf(64);
            var11_11 = var2_2;
            var13_13 = var5_5;
            if (var12_12 != -1) {
                var3_3 = var2_2.substring(0, var12_12);
                var11_11 = var2_2.substring(var12_12 + 1);
                var13_13 = var5_5;
            }
            ** GOTO lbl-1000
        }
        var11_11 = var5_5;
        var13_13 = var7_7;
        if (var7_7 != null) {
            var12_12 = var7_7.indexOf(64);
            var3_3 = var5_5;
            var13_13 = var7_7;
            if (var12_12 != -1) {
                var2_2 = var7_7.substring(0, var12_12);
                var13_13 = var7_7;
                var3_3 = var5_5;
            }
        } else lbl-1000: // 2 sources:
        {
            var2_2 = var3_3;
            var3_3 = var11_11;
        }
        var11_11 = null;
        var5_5 = var7_7 = null;
        if (var8_8 != null) {
            var12_12 = var8_8.lastIndexOf(63);
            if (var12_12 != -1) {
                var5_5 = var8_8.substring(var12_12 + 1);
                var11_11 = var8_8.substring(0, var12_12);
            } else {
                var11_11 = var8_8;
                var5_5 = var7_7;
            }
        }
        this.protocol = var4_4;
        this.host = var3_3;
        this.port = var6_6;
        this.file = var8_8;
        this.authority = var13_13;
        this.ref = var9_9;
        this.hashCode = var10_10;
        this.handler = var1_1;
        this.query = var5_5;
        this.path = var11_11;
        this.userInfo = var2_2;
        return this;
    }

    private void setSerializedHashCode(int n) {
        this.hashCode = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setURLStreamHandlerFactory(URLStreamHandlerFactory object) {
        Object object2 = streamHandlerLock;
        synchronized (object2) {
            if (factory != null) {
                object = new Error("factory already defined");
                throw object;
            }
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkSetFactory();
            }
            handlers.clear();
            factory = object;
            return;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            objectOutputStream.defaultWriteObject();
            return;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof URL)) {
            return false;
        }
        object = (URL)object;
        return this.handler.equals(this, (URL)object);
    }

    public String getAuthority() {
        return this.authority;
    }

    public final Object getContent() throws IOException {
        return this.openConnection().getContent();
    }

    public final Object getContent(Class[] arrclass) throws IOException {
        return this.openConnection().getContent(arrclass);
    }

    public int getDefaultPort() {
        return this.handler.getDefaultPort();
    }

    public String getFile() {
        return this.file;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        return this.path;
    }

    public int getPort() {
        return this.port;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getQuery() {
        return this.query;
    }

    public String getRef() {
        return this.ref;
    }

    public String getUserInfo() {
        return this.userInfo;
    }

    public int hashCode() {
        synchronized (this) {
            block4 : {
                if (this.hashCode == -1) break block4;
                int n = this.hashCode;
                return n;
            }
            int n = this.hashCode = this.handler.hashCode(this);
            return n;
        }
    }

    public URLConnection openConnection() throws IOException {
        return this.handler.openConnection(this);
    }

    public URLConnection openConnection(Proxy proxy) throws IOException {
        if (proxy != null) {
            proxy = proxy == Proxy.NO_PROXY ? Proxy.NO_PROXY : ApplicationProxy.create(proxy);
            SecurityManager securityManager = System.getSecurityManager();
            if (proxy.type() != Proxy.Type.DIRECT && securityManager != null) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
                if (inetSocketAddress.isUnresolved()) {
                    securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
                } else {
                    securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
                }
            }
            return this.handler.openConnection(this, proxy);
        }
        throw new IllegalArgumentException("proxy can not be null");
    }

    public final InputStream openStream() throws IOException {
        return this.openConnection().getInputStream();
    }

    public boolean sameFile(URL uRL) {
        return this.handler.sameFile(this, uRL);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void set(String charSequence, String string, int n, String string2, String string3) {
        synchronized (this) {
            this.protocol = charSequence;
            this.host = string;
            if (n == -1) {
                charSequence = string;
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(":");
                ((StringBuilder)charSequence).append(n);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            this.authority = charSequence;
            this.port = n;
            this.file = string2;
            this.ref = string3;
            this.hashCode = -1;
            this.hostAddress = null;
            n = string2.lastIndexOf(63);
            if (n != -1) {
                this.query = string2.substring(n + 1);
                this.path = string2.substring(0, n);
            } else {
                this.path = string2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void set(String charSequence, String string, int n, String string2, String string3, String string4, String string5, String string6) {
        synchronized (this) {
            this.protocol = charSequence;
            this.host = string;
            this.port = n;
            if (string5 != null && !string5.isEmpty()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string4);
                ((StringBuilder)charSequence).append("?");
                ((StringBuilder)charSequence).append(string5);
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = string4;
            }
            this.file = charSequence;
            this.userInfo = string3;
            this.path = string4;
            this.ref = string6;
            this.hashCode = -1;
            this.hostAddress = null;
            this.query = string5;
            this.authority = string2;
            return;
        }
    }

    public String toExternalForm() {
        return this.handler.toExternalForm(this);
    }

    public String toString() {
        return this.toExternalForm();
    }

    public URI toURI() throws URISyntaxException {
        return new URI(this.toString());
    }
}

