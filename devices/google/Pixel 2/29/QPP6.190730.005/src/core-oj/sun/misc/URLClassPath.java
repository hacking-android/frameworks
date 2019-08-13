/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.security.AccessControlContext;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.Permission;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import sun.misc.FileURLMapper;
import sun.misc.InvalidJarIndexException;
import sun.misc.JarIndex;
import sun.misc.MetaIndex;
import sun.misc.Resource;
import sun.net.util.URLUtil;
import sun.net.www.ParseUtil;
import sun.security.action.GetPropertyAction;

public class URLClassPath {
    private static final boolean DEBUG;
    private static final boolean DEBUG_LOOKUP_CACHE;
    private static final boolean DISABLE_ACC_CHECKING;
    private static final boolean DISABLE_JAR_CHECKING;
    static final String JAVA_VERSION;
    static final String USER_AGENT_JAVA_VERSION = "UA-Java-Version";
    private static volatile boolean lookupCacheEnabled;
    private final AccessControlContext acc;
    private boolean closed = false;
    private URLStreamHandler jarHandler;
    HashMap<String, Loader> lmap = new HashMap();
    ArrayList<Loader> loaders = new ArrayList();
    private ClassLoader lookupCacheLoader;
    private URL[] lookupCacheURLs;
    private ArrayList<URL> path = new ArrayList();
    Stack<URL> urls = new Stack();

    static {
        JAVA_VERSION = AccessController.doPrivileged(new GetPropertyAction("java.version"));
        String string = AccessController.doPrivileged(new GetPropertyAction("sun.misc.URLClassPath.debug"));
        boolean bl = true;
        boolean bl2 = string != null;
        DEBUG = bl2;
        bl2 = AccessController.doPrivileged(new GetPropertyAction("sun.misc.URLClassPath.debugLookupCache")) != null;
        DEBUG_LOOKUP_CACHE = bl2;
        string = AccessController.doPrivileged(new GetPropertyAction("sun.misc.URLClassPath.disableJarChecking"));
        bl2 = string != null && (string.equals("true") || string.equals(""));
        DISABLE_JAR_CHECKING = bl2;
        string = AccessController.doPrivileged(new GetPropertyAction("jdk.net.URLClassPath.disableRestrictedPermissions"));
        bl2 = string != null && (string.equals("true") || string.equals("")) ? bl : false;
        DISABLE_ACC_CHECKING = bl2;
        lookupCacheEnabled = false;
    }

    public URLClassPath(URL[] arruRL) {
        this(arruRL, null, null);
    }

    public URLClassPath(URL[] arruRL, URLStreamHandlerFactory uRLStreamHandlerFactory, AccessControlContext accessControlContext) {
        for (int i = 0; i < arruRL.length; ++i) {
            this.path.add(arruRL[i]);
        }
        this.push(arruRL);
        if (uRLStreamHandlerFactory != null) {
            this.jarHandler = uRLStreamHandlerFactory.createURLStreamHandler("jar");
        }
        this.acc = DISABLE_ACC_CHECKING ? null : accessControlContext;
    }

    public URLClassPath(URL[] arruRL, AccessControlContext accessControlContext) {
        this(arruRL, null, accessControlContext);
    }

    static /* synthetic */ int[] access$000(URLClassPath uRLClassPath, String string) {
        return uRLClassPath.getLookupCache(string);
    }

    static void check(URL uRL) throws IOException {
        URLConnection uRLConnection;
        Permission permission;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null && (permission = (uRLConnection = uRL.openConnection()).getPermission()) != null) {
            try {
                securityManager.checkPermission(permission);
            }
            catch (SecurityException securityException) {
                if (permission instanceof FilePermission && permission.getActions().indexOf("read") != -1) {
                    securityManager.checkRead(permission.getName());
                }
                if (permission instanceof SocketPermission && permission.getActions().indexOf("connect") != -1) {
                    if (uRLConnection instanceof JarURLConnection) {
                        uRL = ((JarURLConnection)uRLConnection).getJarFileURL();
                    }
                    securityManager.checkConnect(uRL.getHost(), uRL.getPort());
                }
                throw securityException;
            }
        }
    }

    static void disableAllLookupCaches() {
        lookupCacheEnabled = false;
    }

    private boolean ensureLoaderOpened(int n) {
        if (this.loaders.size() <= n) {
            if (this.getLoader(n) == null) {
                return false;
            }
            if (!lookupCacheEnabled) {
                return false;
            }
            if (DEBUG_LOOKUP_CACHE) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expanded loaders ");
                stringBuilder.append(this.loaders.size());
                stringBuilder.append(" to index=");
                stringBuilder.append(n);
                printStream.println(stringBuilder.toString());
            }
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Loader getLoader(int n) {
        synchronized (this) {
            Object object;
            URL[] arruRL;
            boolean bl = this.closed;
            if (bl) {
                return null;
            }
            while (this.loaders.size() < n + 1) {
                arruRL = this.urls;
                synchronized (arruRL) {
                    Throwable throwable2;
                    bl = this.urls.empty();
                    if (bl) {
                        try {
                            return null;
                        }
                        catch (Throwable throwable2) {}
                    } else {
                        Loader loader;
                        CharSequence charSequence;
                        block18 : {
                            object = this.urls.pop();
                            // MONITOREXIT [16, 9, 10, 14] lbl19 : MonitorExitStatement: MONITOREXIT : var3_3
                            charSequence = URLUtil.urlNoFragString((URL)object);
                            bl = this.lmap.containsKey(charSequence);
                            if (bl) continue;
                            try {
                                loader = this.getLoader((URL)object);
                                arruRL = loader.getClassPath();
                                if (arruRL == null) break block18;
                                this.push(arruRL);
                            }
                            catch (SecurityException securityException) {
                                if (!DEBUG) continue;
                                arruRL = System.err;
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("Failed to access ");
                                ((StringBuilder)charSequence).append(object);
                                ((StringBuilder)charSequence).append(", ");
                                ((StringBuilder)charSequence).append(securityException);
                                arruRL.println(((StringBuilder)charSequence).toString());
                                continue;
                            }
                            catch (IOException iOException) {
                                continue;
                            }
                        }
                        this.validateLookupCache(this.loaders.size(), (String)charSequence);
                        this.loaders.add(loader);
                        this.lmap.put((String)charSequence, loader);
                        continue;
                    }
                    throw throwable2;
                }
            }
            if (!DEBUG_LOOKUP_CACHE) return this.loaders.get(n);
            object = System.out;
            arruRL = new StringBuilder();
            arruRL.append("NOCACHE: Loading from : ");
            arruRL.append(n);
            ((PrintStream)object).println(arruRL.toString());
            return this.loaders.get(n);
        }
    }

    private Loader getLoader(URL object) throws IOException {
        try {
            PrivilegedExceptionAction<Loader> privilegedExceptionAction = new PrivilegedExceptionAction<Loader>((URL)object){
                final /* synthetic */ URL val$url;
                {
                    this.val$url = uRL;
                }

                @Override
                public Loader run() throws IOException {
                    String string = this.val$url.getFile();
                    if (string != null && string.endsWith("/")) {
                        if ("file".equals(this.val$url.getProtocol())) {
                            return new FileLoader(this.val$url);
                        }
                        return new Loader(this.val$url);
                    }
                    return new JarLoader(this.val$url, URLClassPath.this.jarHandler, URLClassPath.this.lmap, URLClassPath.this.acc);
                }
            };
            object = AccessController.doPrivileged(privilegedExceptionAction, this.acc);
            return object;
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getException();
        }
    }

    private int[] getLookupCache(String object) {
        synchronized (this) {
            block6 : {
                block7 : {
                    int n;
                    if (this.lookupCacheURLs == null || !lookupCacheEnabled) break block6;
                    if ((object = URLClassPath.getLookupCacheForClassLoader(this.lookupCacheLoader, (String)object)) == null) break block7;
                    if (((int[])object).length <= 0 || this.ensureLoaderOpened(n = object[((int[])object).length - 1])) break block7;
                    if (DEBUG_LOOKUP_CACHE) {
                        object = System.out;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Expanded loaders FAILED ");
                        stringBuilder.append(this.loaders.size());
                        stringBuilder.append(" for maxindex=");
                        stringBuilder.append(n);
                        ((PrintStream)object).println(stringBuilder.toString());
                    }
                    return null;
                }
                return object;
            }
            return null;
            finally {
            }
        }
    }

    private static int[] getLookupCacheForClassLoader(ClassLoader classLoader, String string) {
        return null;
    }

    private URL[] getLookupCacheURLs(ClassLoader classLoader) {
        return null;
    }

    private Loader getNextLoader(int[] object, int n) {
        synchronized (this) {
            block9 : {
                boolean bl = this.closed;
                if (!bl) break block9;
                return null;
            }
            if (object != null) {
                if (n < ((int[])object).length) {
                    Loader loader = this.loaders.get(object[n]);
                    if (DEBUG_LOOKUP_CACHE) {
                        PrintStream printStream = System.out;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("HASCACHE: Loading from : ");
                        stringBuilder.append(object[n]);
                        stringBuilder.append(" = ");
                        stringBuilder.append(loader.getBaseURL());
                        printStream.println(stringBuilder.toString());
                    }
                    return loader;
                }
                return null;
            }
            object = this.getLoader(n);
            return object;
        }
    }

    private static boolean knownToNotExist0(ClassLoader classLoader, String string) {
        return false;
    }

    public static URL[] pathToURLs(String object) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)object, File.pathSeparator);
        URL[] arruRL = new URL[stringTokenizer.countTokens()];
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            object = new File(stringTokenizer.nextToken());
            try {
                File file = new File(object.getCanonicalPath());
                object = file;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            try {
                arruRL[n] = ParseUtil.fileToEncodedURL((File)object);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            ++n;
        }
        object = arruRL;
        if (arruRL.length != n) {
            object = new URL[n];
            System.arraycopy(arruRL, 0, object, 0, n);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void push(URL[] arruRL) {
        Stack<URL> stack = this.urls;
        synchronized (stack) {
            int n = arruRL.length - 1;
            while (n >= 0) {
                this.urls.push(arruRL[n]);
                --n;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void validateLookupCache(int n, String charSequence) {
        synchronized (this) {
            if (this.lookupCacheURLs != null && lookupCacheEnabled) {
                boolean bl;
                if (n < this.lookupCacheURLs.length && (bl = ((String)charSequence).equals(URLUtil.urlNoFragString(this.lookupCacheURLs[n])))) {
                    return;
                }
                if (DEBUG || DEBUG_LOOKUP_CACHE) {
                    PrintStream printStream = System.out;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("WARNING: resource lookup cache invalidated for lookupCacheLoader at ");
                    ((StringBuilder)charSequence).append(n);
                    printStream.println(((StringBuilder)charSequence).toString());
                }
                URLClassPath.disableAllLookupCaches();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addURL(URL uRL) {
        synchronized (this) {
            boolean bl = this.closed;
            if (bl) {
                return;
            }
            Stack<URL> stack = this.urls;
            synchronized (stack) {
                if (uRL != null && !this.path.contains(uRL)) {
                    this.urls.add(0, uRL);
                    this.path.add(uRL);
                    if (this.lookupCacheURLs != null) {
                        URLClassPath.disableAllLookupCaches();
                    }
                    return;
                }
                return;
            }
        }
    }

    public URL checkURL(URL uRL) {
        try {
            URLClassPath.check(uRL);
            return uRL;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<IOException> closeLoaders() {
        synchronized (this) {
            if (this.closed) {
                return Collections.emptyList();
            }
            LinkedList<IOException> linkedList = new LinkedList<IOException>();
            Iterator<Loader> iterator = this.loaders.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.closed = true;
                    return linkedList;
                }
                Loader loader = iterator.next();
                try {
                    loader.close();
                }
                catch (IOException iOException) {
                    linkedList.add(iOException);
                    continue;
                }
                break;
            } while (true);
        }
    }

    public URL findResource(String string, boolean bl) {
        Object object;
        int[] arrn = this.getLookupCache(string);
        int n = 0;
        while ((object = this.getNextLoader(arrn, n)) != null) {
            if ((object = ((Loader)object).findResource(string, bl)) != null) {
                return object;
            }
            ++n;
        }
        return null;
    }

    public Enumeration<URL> findResources(final String string, final boolean bl) {
        return new Enumeration<URL>(){
            private int[] cache = URLClassPath.access$000(URLClassPath.this, string);
            private int index = 0;
            private URL url = null;

            private boolean next() {
                block2 : {
                    if (this.url != null) {
                        return true;
                    }
                    do {
                        URLClassPath uRLClassPath = URLClassPath.this;
                        Object object = this.cache;
                        int n = this.index;
                        this.index = n + 1;
                        if ((object = uRLClassPath.getNextLoader((int[])object, n)) == null) break block2;
                        this.url = ((Loader)object).findResource(string, bl);
                    } while (this.url == null);
                    return true;
                }
                return false;
            }

            @Override
            public boolean hasMoreElements() {
                return this.next();
            }

            @Override
            public URL nextElement() {
                if (this.next()) {
                    URL uRL = this.url;
                    this.url = null;
                    return uRL;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public Resource getResource(String string) {
        return this.getResource(string, true);
    }

    public Resource getResource(String string, boolean bl) {
        Object object;
        Object object2;
        if (DEBUG) {
            object2 = System.err;
            object = new StringBuilder();
            ((StringBuilder)object).append("URLClassPath.getResource(\"");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("\")");
            ((PrintStream)object2).println(((StringBuilder)object).toString());
        }
        object = this.getLookupCache(string);
        int n = 0;
        while ((object2 = this.getNextLoader((int[])object, n)) != null) {
            if ((object2 = ((Loader)object2).getResource(string, bl)) != null) {
                return object2;
            }
            ++n;
        }
        return null;
    }

    public Enumeration<Resource> getResources(String string) {
        return this.getResources(string, true);
    }

    public Enumeration<Resource> getResources(final String string, final boolean bl) {
        return new Enumeration<Resource>(){
            private int[] cache = URLClassPath.access$000(URLClassPath.this, string);
            private int index = 0;
            private Resource res = null;

            private boolean next() {
                block2 : {
                    if (this.res != null) {
                        return true;
                    }
                    do {
                        Object object = URLClassPath.this;
                        int[] arrn = this.cache;
                        int n = this.index;
                        this.index = n + 1;
                        if ((object = ((URLClassPath)object).getNextLoader(arrn, n)) == null) break block2;
                        this.res = ((Loader)object).getResource(string, bl);
                    } while (this.res == null);
                    return true;
                }
                return false;
            }

            @Override
            public boolean hasMoreElements() {
                return this.next();
            }

            @Override
            public Resource nextElement() {
                if (this.next()) {
                    Resource resource = this.res;
                    this.res = null;
                    return resource;
                }
                throw new NoSuchElementException();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public URL[] getURLs() {
        Stack<URL> stack = this.urls;
        synchronized (stack) {
            return this.path.toArray(new URL[this.path.size()]);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void initLookupCache(ClassLoader classLoader) {
        synchronized (this) {
            URL[] arruRL = this.getLookupCacheURLs(classLoader);
            this.lookupCacheURLs = arruRL;
            if (arruRL != null) {
                this.lookupCacheLoader = classLoader;
            } else {
                URLClassPath.disableAllLookupCaches();
            }
            return;
        }
    }

    boolean knownToNotExist(String string) {
        synchronized (this) {
            if (this.lookupCacheURLs != null && lookupCacheEnabled) {
                boolean bl = URLClassPath.knownToNotExist0(this.lookupCacheLoader, string);
                return bl;
            }
            return false;
        }
    }

    private static class FileLoader
    extends Loader {
        private File dir;

        FileLoader(URL uRL) throws IOException {
            super(uRL);
            if ("file".equals(uRL.getProtocol())) {
                this.dir = new File(ParseUtil.decode(uRL.getFile().replace('/', File.separatorChar))).getCanonicalFile();
                return;
            }
            throw new IllegalArgumentException("url");
        }

        @Override
        URL findResource(String object, boolean bl) {
            if ((object = this.getResource((String)object, bl)) != null) {
                return ((Resource)object).getURL();
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        Resource getResource(String object, boolean bl) {
            try {
                Serializable serializable = new URL(this.getBaseURL(), ".");
                URL uRL = new URL(this.getBaseURL(), ParseUtil.encodePath((String)object, false));
                if (!uRL.getFile().startsWith(((URL)serializable).getFile())) {
                    return null;
                }
                if (bl) {
                    URLClassPath.check(uRL);
                }
                if (((String)object).indexOf("..") != -1) {
                    serializable = new File(this.dir, ((String)object).replace('/', File.separatorChar));
                    File file = ((File)serializable).getCanonicalFile();
                    serializable = file;
                    if (!file.getPath().startsWith(this.dir.getPath())) {
                        return null;
                    }
                } else {
                    serializable = new File(this.dir, ((String)object).replace('/', File.separatorChar));
                }
                if (!((File)serializable).exists()) return null;
                return new Resource((String)object, uRL, (File)serializable){
                    final /* synthetic */ File val$file;
                    final /* synthetic */ String val$name;
                    final /* synthetic */ URL val$url;
                    {
                        this.val$name = string;
                        this.val$url = uRL;
                        this.val$file = file;
                    }

                    @Override
                    public URL getCodeSourceURL() {
                        return this.getBaseURL();
                    }

                    @Override
                    public int getContentLength() throws IOException {
                        return (int)this.val$file.length();
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return new FileInputStream(this.val$file);
                    }

                    @Override
                    public String getName() {
                        return this.val$name;
                    }

                    @Override
                    public URL getURL() {
                        return this.val$url;
                    }
                };
            }
            catch (Exception exception) {
                return null;
            }
        }

    }

    static class JarLoader
    extends Loader {
        private final AccessControlContext acc;
        private boolean closed;
        private final URL csu;
        private URLStreamHandler handler;
        private JarIndex index;
        private JarFile jar;
        private final HashMap<String, Loader> lmap;
        private MetaIndex metaIndex;

        JarLoader(URL object, URLStreamHandler uRLStreamHandler, HashMap<String, Loader> hashMap, AccessControlContext accessControlContext) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object);
            stringBuilder.append("!/");
            super(new URL("jar", "", -1, stringBuilder.toString(), uRLStreamHandler));
            this.closed = false;
            this.csu = object;
            this.handler = uRLStreamHandler;
            this.lmap = hashMap;
            this.acc = accessControlContext;
            if (!this.isOptimizable((URL)object)) {
                this.ensureOpen();
            } else {
                if ((object = ((URL)object).getFile()) != null) {
                    object = new File(ParseUtil.decode((String)object));
                    this.metaIndex = MetaIndex.forJar((File)object);
                    if (this.metaIndex != null && !((File)object).exists()) {
                        this.metaIndex = null;
                    }
                }
                if (this.metaIndex == null) {
                    this.ensureOpen();
                }
            }
        }

        static JarFile checkJar(JarFile jarFile) throws IOException {
            if (System.getSecurityManager() != null && !DISABLE_JAR_CHECKING && !jarFile.startsWithLocHeader()) {
                IOException iOException = new IOException("Invalid Jar file");
                try {
                    jarFile.close();
                }
                catch (IOException iOException2) {
                    iOException.addSuppressed(iOException2);
                }
                throw iOException;
            }
            return jarFile;
        }

        private void ensureOpen() throws IOException {
            if (this.jar == null) {
                try {
                    PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                        @Override
                        public Void run() throws IOException {
                            Object object;
                            String[] arrstring;
                            if (DEBUG) {
                                object = System.err;
                                arrstring = new StringBuilder();
                                arrstring.append("Opening ");
                                arrstring.append(csu);
                                ((PrintStream)object).println(arrstring.toString());
                                Thread.dumpStack();
                            }
                            arrstring = this;
                            ((JarLoader)arrstring).jar = ((JarLoader)arrstring).getJarFile(((JarLoader)arrstring).csu);
                            arrstring = this;
                            ((JarLoader)arrstring).index = JarIndex.getJarIndex(((JarLoader)arrstring).jar, metaIndex);
                            if (index != null) {
                                arrstring = index.getJarFiles();
                                for (int i = 0; i < arrstring.length; ++i) {
                                    try {
                                        object = new URL(csu, arrstring[i]);
                                        object = URLUtil.urlNoFragString((URL)object);
                                        if (lmap.containsKey(object)) continue;
                                        lmap.put(object, null);
                                        continue;
                                    }
                                    catch (MalformedURLException malformedURLException) {
                                        // empty catch block
                                    }
                                }
                            }
                            return null;
                        }
                    };
                    AccessController.doPrivileged(privilegedExceptionAction, this.acc);
                }
                catch (PrivilegedActionException privilegedActionException) {
                    throw (IOException)privilegedActionException.getException();
                }
            }
        }

        private JarFile getJarFile(URL object) throws IOException {
            if (this.isOptimizable((URL)object)) {
                if (((FileURLMapper)(object = new FileURLMapper((URL)object))).exists()) {
                    return JarLoader.checkJar(new JarFile(((FileURLMapper)object).getPath()));
                }
                throw new FileNotFoundException(((FileURLMapper)object).getPath());
            }
            object = this.getBaseURL().openConnection();
            ((URLConnection)object).setRequestProperty(URLClassPath.USER_AGENT_JAVA_VERSION, JAVA_VERSION);
            return JarLoader.checkJar(((JarURLConnection)object).getJarFile());
        }

        private boolean isOptimizable(URL uRL) {
            return "file".equals(uRL.getProtocol());
        }

        private URL[] parseClassPath(URL uRL, String arruRL) throws MalformedURLException {
            StringTokenizer stringTokenizer = new StringTokenizer((String)arruRL);
            arruRL = new URL[stringTokenizer.countTokens()];
            int n = 0;
            while (stringTokenizer.hasMoreTokens()) {
                arruRL[n] = new URL(uRL, stringTokenizer.nextToken());
                ++n;
            }
            return arruRL;
        }

        private void parseExtensionsDependencies() throws IOException {
        }

        Resource checkResource(final String string, boolean bl, final JarEntry jarEntry) {
            URL uRL;
            block5 : {
                uRL = new URL(this.getBaseURL(), ParseUtil.encodePath(string, false));
                if (!bl) break block5;
                try {
                    URLClassPath.check(uRL);
                }
                catch (AccessControlException accessControlException) {
                    return null;
                }
                catch (IOException iOException) {
                    return null;
                }
                catch (MalformedURLException malformedURLException) {
                    return null;
                }
            }
            return new Resource(){

                @Override
                public Certificate[] getCertificates() {
                    return jarEntry.getCertificates();
                }

                @Override
                public CodeSigner[] getCodeSigners() {
                    return jarEntry.getCodeSigners();
                }

                @Override
                public URL getCodeSourceURL() {
                    return csu;
                }

                @Override
                public int getContentLength() {
                    return (int)jarEntry.getSize();
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return jar.getInputStream(jarEntry);
                }

                @Override
                public Manifest getManifest() throws IOException {
                    return jar.getManifest();
                }

                @Override
                public String getName() {
                    return string;
                }

                @Override
                public URL getURL() {
                    return uRL;
                }
            };
        }

        @Override
        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                this.ensureOpen();
                this.jar.close();
            }
        }

        @Override
        URL findResource(String object, boolean bl) {
            if ((object = this.getResource((String)object, bl)) != null) {
                return ((Resource)object).getURL();
            }
            return null;
        }

        @Override
        URL[] getClassPath() throws IOException {
            Object object;
            if (this.index != null) {
                return null;
            }
            if (this.metaIndex != null) {
                return null;
            }
            this.ensureOpen();
            this.parseExtensionsDependencies();
            if (this.jar.hasClassPathAttribute() && (object = this.jar.getManifest()) != null && (object = ((Manifest)object).getMainAttributes()) != null && (object = ((Attributes)object).getValue(Attributes.Name.CLASS_PATH)) != null) {
                return this.parseClassPath(this.csu, (String)object);
            }
            return null;
        }

        JarIndex getIndex() {
            try {
                this.ensureOpen();
                return this.index;
            }
            catch (IOException iOException) {
                throw new InternalError(iOException);
            }
        }

        JarFile getJarFile() {
            return this.jar;
        }

        @Override
        Resource getResource(String string, boolean bl) {
            Object object = this.metaIndex;
            if (object != null && !((MetaIndex)object).mayContain(string)) {
                return null;
            }
            try {
                this.ensureOpen();
                object = this.jar.getJarEntry(string);
                if (object != null) {
                    return this.checkResource(string, bl, (JarEntry)object);
                }
                if (this.index == null) {
                    return null;
                }
                return this.getResource(string, bl, new HashSet<String>());
            }
            catch (IOException iOException) {
                throw new InternalError(iOException);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        Resource getResource(String var1_1, boolean var2_3, Set<String> var3_4) {
            var4_5 = 0;
            var5_6 = this.index.get(var1_1);
            var6_11 = var5_6;
            if (var5_6 == null) {
                return null;
            }
            do {
                var7_12 = var6_11.size();
                var8_13 = var6_11.toArray(new String[var7_12]);
                while (var4_5 < var7_12) {
                    block16 : {
                        block14 : {
                            block15 : {
                                var9_14 = var8_13[var4_5];
                                var10_15 = new URL(this.csu, var9_14);
                                var11_16 = URLUtil.urlNoFragString(var10_15);
                                var12_17 = (JarLoader)this.lmap.get(var11_16);
                                var5_6 = var12_17;
                                if (var12_17 != null) break block14;
                                var5_6 = new PrivilegedExceptionAction<JarLoader>(){

                                    @Override
                                    public JarLoader run() throws IOException {
                                        return new JarLoader(var10_15, handler, lmap, acc);
                                    }
                                };
                                var12_17 = (JarLoader)AccessController.doPrivileged(var5_6, this.acc);
                                var13_18 = var12_17.getIndex();
                                if (var13_18 == null) ** GOTO lbl30
                                var14_19 = var9_14.lastIndexOf("/");
                                var15_20 = this.index;
                                if (var14_19 != -1) break block15;
                                var5_6 = null;
                                ** GOTO lbl29
                            }
                            try {
                                var5_6 = var9_14.substring(0, var14_19 + 1);
lbl29: // 2 sources:
                                var13_18.merge(var15_20, (String)var5_6);
lbl30: // 2 sources:
                                this.lmap.put(var11_16, (Loader)var12_17);
                                var5_6 = var12_17;
                            }
                            catch (MalformedURLException var5_7) {
                                break block16;
                            }
                            catch (PrivilegedActionException var5_8) {
                                break block16;
                            }
                        }
                        if ((var14_19 = var3_4.add(URLUtil.urlNoFragString(var10_15)) ^ true) == 0) {
                            try {
                                JarLoader.super.ensureOpen();
                                var12_17 = var5_6.jar.getJarEntry(var1_1);
                                if (var12_17 != null) {
                                    return var5_6.checkResource(var1_1, var2_3, (JarEntry)var12_17);
                                }
                                if (var5_6.validIndex(var1_1) == false) throw new InvalidJarIndexException("Invalid index");
                            }
                            catch (IOException var1_2) {
                                throw new InternalError(var1_2);
                            }
                        }
                        if (var14_19 == 0 && var5_6 != this && var5_6.getIndex() != null && (var5_6 = var5_6.getResource(var1_1, var2_3, var3_4)) != null) {
                            return var5_6;
                        }
                        break block16;
                        catch (MalformedURLException var5_9) {
                        }
                        catch (PrivilegedActionException var5_10) {
                            // empty catch block
                        }
                    }
                    ++var4_5;
                }
            } while (var4_5 < (var6_11 = this.index.get(var1_1)).size());
            return null;
        }

        boolean validIndex(String string) {
            String string2 = string;
            int n = string.lastIndexOf("/");
            if (n != -1) {
                string2 = string.substring(0, n);
            }
            Enumeration<JarEntry> enumeration = this.jar.entries();
            while (enumeration.hasMoreElements()) {
                String string3 = ((ZipEntry)enumeration.nextElement()).getName();
                n = string3.lastIndexOf("/");
                string = string3;
                if (n != -1) {
                    string = string3.substring(0, n);
                }
                if (!string.equals(string2)) continue;
                return true;
            }
            return false;
        }

    }

    private static class Loader
    implements Closeable {
        private final URL base;
        private JarFile jarfile;

        Loader(URL uRL) {
            this.base = uRL;
        }

        @Override
        public void close() throws IOException {
            JarFile jarFile = this.jarfile;
            if (jarFile != null) {
                jarFile.close();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        URL findResource(String var1_1, boolean var2_4) {
            try {
                var1_1 = new URL(this.base, ParseUtil.encodePath((String)var1_1, false));
                if (!var2_4) ** GOTO lbl8
            }
            catch (MalformedURLException var1_3) {
                throw new IllegalArgumentException("name");
            }
            try {
                URLClassPath.check((URL)var1_1);
lbl8: // 2 sources:
                if ((var3_5 = var1_1.openConnection()) instanceof HttpURLConnection) {
                    var3_5 = (HttpURLConnection)var3_5;
                    var3_5.setRequestMethod("HEAD");
                    if (var3_5.getResponseCode() < 400) return var1_1;
                    return null;
                }
                var3_5.setUseCaches(false);
                var3_5.getInputStream().close();
                return var1_1;
            }
            catch (Exception var1_2) {
                return null;
            }
        }

        URL getBaseURL() {
            return this.base;
        }

        URL[] getClassPath() throws IOException {
            return null;
        }

        Resource getResource(String string) {
            return this.getResource(string, true);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        Resource getResource(final String var1_1, boolean var2_4) {
            try {
                var3_5 = new URL(this.base, ParseUtil.encodePath(var1_1, false));
                if (!var2_4) ** GOTO lbl8
            }
            catch (MalformedURLException var1_3) {
                throw new IllegalArgumentException("name");
            }
            try {
                URLClassPath.check(var3_5);
lbl8: // 2 sources:
                var4_6 = var3_5.openConnection();
                var4_6.getInputStream();
                if (var4_6 instanceof JarURLConnection == false) return new Resource(){

                    @Override
                    public URL getCodeSourceURL() {
                        return base;
                    }

                    @Override
                    public int getContentLength() throws IOException {
                        return var4_6.getContentLength();
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return var4_6.getInputStream();
                    }

                    @Override
                    public String getName() {
                        return var1_1;
                    }

                    @Override
                    public URL getURL() {
                        return var3_5;
                    }
                };
                this.jarfile = JarLoader.checkJar(((JarURLConnection)var4_6).getJarFile());
            }
            catch (Exception var1_2) {
                return null;
            }
            return new /* invalid duplicate definition of identical inner class */;
        }

    }

}

