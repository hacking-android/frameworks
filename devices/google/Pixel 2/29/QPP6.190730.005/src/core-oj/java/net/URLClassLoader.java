/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.Closeable;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.net.FactoryURLClassLoader;
import java.net.JarURLConnection;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;
import java.nio.ByteBuffer;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import sun.misc.Resource;
import sun.misc.URLClassPath;
import sun.net.www.ParseUtil;
import sun.net.www.protocol.file.FileURLConnection;

public class URLClassLoader
extends SecureClassLoader
implements Closeable {
    private final AccessControlContext acc;
    private WeakHashMap<Closeable, Void> closeables = new WeakHashMap();
    private final URLClassPath ucp;

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public URLClassLoader(URL[] arruRL) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.acc = AccessController.getContext();
        this.ucp = new URLClassPath(arruRL, this.acc);
    }

    public URLClassLoader(URL[] arruRL, ClassLoader object) {
        super((ClassLoader)object);
        object = System.getSecurityManager();
        if (object != null) {
            ((SecurityManager)object).checkCreateClassLoader();
        }
        this.acc = AccessController.getContext();
        this.ucp = new URLClassPath(arruRL, this.acc);
    }

    public URLClassLoader(URL[] arruRL, ClassLoader object, URLStreamHandlerFactory uRLStreamHandlerFactory) {
        super((ClassLoader)object);
        object = System.getSecurityManager();
        if (object != null) {
            ((SecurityManager)object).checkCreateClassLoader();
        }
        this.acc = AccessController.getContext();
        this.ucp = new URLClassPath(arruRL, uRLStreamHandlerFactory, this.acc);
    }

    URLClassLoader(URL[] arruRL, ClassLoader object, AccessControlContext accessControlContext) {
        super((ClassLoader)object);
        object = System.getSecurityManager();
        if (object != null) {
            ((SecurityManager)object).checkCreateClassLoader();
        }
        this.acc = accessControlContext;
        this.ucp = new URLClassPath(arruRL, accessControlContext);
    }

    URLClassLoader(URL[] arruRL, AccessControlContext accessControlContext) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.acc = accessControlContext;
        this.ucp = new URLClassPath(arruRL, accessControlContext);
    }

    private Class<?> defineClass(String string, Resource object) throws IOException {
        byte[] arrby;
        System.nanoTime();
        int n = string.lastIndexOf(46);
        URL uRL = ((Resource)object).getCodeSourceURL();
        if (n != -1) {
            this.definePackageInternal(string.substring(0, n), ((Resource)object).getManifest(), uRL);
        }
        if ((arrby = ((Resource)object).getByteBuffer()) != null) {
            return this.defineClass(string, (ByteBuffer)arrby, new CodeSource(uRL, ((Resource)object).getCodeSigners()));
        }
        arrby = ((Resource)object).getBytes();
        object = new CodeSource(uRL, ((Resource)object).getCodeSigners());
        return this.defineClass(string, arrby, 0, arrby.length, (CodeSource)object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void definePackageInternal(String var1_1, Manifest var2_2, URL var3_3) {
        if (this.getAndVerifyPackage(var1_1, (Manifest)var2_2, var3_3) != null) return;
        if (var2_2 == null) ** GOTO lbl7
        try {
            this.definePackage(var1_1, (Manifest)var2_2, var3_3);
            return;
lbl7: // 1 sources:
            this.definePackage(var1_1, null, null, null, null, null, null, null);
            return;
        }
        catch (IllegalArgumentException var4_4) {
            if (this.getAndVerifyPackage(var1_1, (Manifest)var2_2, var3_3) != null) {
                return;
            }
            var2_2 = new StringBuilder();
            var2_2.append("Cannot find package ");
            var2_2.append(var1_1);
            throw new AssertionError((Object)var2_2.toString());
        }
    }

    private Package getAndVerifyPackage(String string, Manifest object, URL uRL) {
        Package package_ = this.getPackage(string);
        if (package_ != null) {
            if (package_.isSealed()) {
                if (!package_.isSealed(uRL)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("sealing violation: package ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" is sealed");
                    throw new SecurityException(((StringBuilder)object).toString());
                }
            } else if (object != null && this.isSealed(string, (Manifest)object)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sealing violation: can't seal package ");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(": already loaded");
                throw new SecurityException(((StringBuilder)object).toString());
            }
        }
        return package_;
    }

    private boolean isSealed(String string, Manifest cloneable) {
        Object object = ((Manifest)cloneable).getAttributes(string.replace('.', '/').concat("/"));
        string = null;
        if (object != null) {
            string = ((Attributes)object).getValue(Attributes.Name.SEALED);
        }
        object = string;
        if (string == null) {
            cloneable = ((Manifest)cloneable).getMainAttributes();
            object = string;
            if (cloneable != null) {
                object = ((Attributes)cloneable).getValue(Attributes.Name.SEALED);
            }
        }
        return "true".equalsIgnoreCase((String)object);
    }

    public static URLClassLoader newInstance(final URL[] arruRL) {
        return AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>(AccessController.getContext()){
            final /* synthetic */ AccessControlContext val$acc;
            {
                this.val$acc = accessControlContext;
            }

            @Override
            public URLClassLoader run() {
                return new FactoryURLClassLoader(arruRL, this.val$acc);
            }
        });
    }

    public static URLClassLoader newInstance(final URL[] arruRL, final ClassLoader classLoader) {
        return AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>(AccessController.getContext()){
            final /* synthetic */ AccessControlContext val$acc;
            {
                this.val$acc = accessControlContext;
            }

            @Override
            public URLClassLoader run() {
                return new FactoryURLClassLoader(arruRL, classLoader, this.val$acc);
            }
        });
    }

    protected void addURL(URL uRL) {
        this.ucp.addURL(uRL);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void close() throws IOException {
        Object object = System.getSecurityManager();
        if (object != null) {
            ((SecurityManager)object).checkPermission(new RuntimePermission("closeClassLoader"));
        }
        Object object2 = this.ucp.closeLoaders();
        object = this.closeables;
        // MONITORENTER : object
        for (Closeable closeable : this.closeables.keySet()) {
            try {
                closeable.close();
            }
            catch (IOException iOException) {
                object2.add((IOException)iOException);
            }
        }
        this.closeables.clear();
        // MONITOREXIT : object
        if (object2.isEmpty()) {
            return;
        }
        object = object2.remove(0);
        object2 = object2.iterator();
        while (object2.hasNext()) {
            ((Throwable)object).addSuppressed((IOException)object2.next());
        }
        throw object;
    }

    protected Package definePackage(String string, Manifest object, URL uRL) throws IllegalArgumentException {
        Object object2;
        Object object3 = string.replace('.', '/').concat("/");
        String string2 = null;
        Object object4 = null;
        Object object5 = null;
        String string3 = null;
        Object object6 = null;
        Object object7 = null;
        Object object8 = null;
        if ((object3 = ((Manifest)object).getAttributes((String)object3)) != null) {
            string2 = ((Attributes)object3).getValue(Attributes.Name.SPECIFICATION_TITLE);
            object4 = ((Attributes)object3).getValue(Attributes.Name.SPECIFICATION_VERSION);
            object5 = ((Attributes)object3).getValue(Attributes.Name.SPECIFICATION_VENDOR);
            string3 = ((Attributes)object3).getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            object6 = ((Attributes)object3).getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            object7 = ((Attributes)object3).getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            object8 = ((Attributes)object3).getValue(Attributes.Name.SEALED);
        }
        if ((object2 = ((Manifest)object).getMainAttributes()) != null) {
            object3 = string2;
            if (string2 == null) {
                object3 = ((Attributes)object2).getValue(Attributes.Name.SPECIFICATION_TITLE);
            }
            string2 = object4;
            if (object4 == null) {
                string2 = ((Attributes)object2).getValue(Attributes.Name.SPECIFICATION_VERSION);
            }
            object4 = object5;
            if (object5 == null) {
                object4 = ((Attributes)object2).getValue(Attributes.Name.SPECIFICATION_VENDOR);
            }
            object5 = string3;
            if (string3 == null) {
                object5 = ((Attributes)object2).getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            }
            object = object6;
            if (object6 == null) {
                object = ((Attributes)object2).getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            }
            object6 = object7;
            if (object7 == null) {
                object6 = ((Attributes)object2).getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            }
            if (object8 == null) {
                object7 = ((Attributes)object2).getValue(Attributes.Name.SEALED);
                object8 = object3;
                string3 = object5;
                object5 = object;
                object3 = object6;
                object = object8;
                object8 = string2;
                object6 = object4;
                object4 = object3;
            } else {
                object2 = object3;
                object3 = object4;
                string3 = object5;
                object5 = object;
                object4 = object6;
                object7 = object8;
                object = object2;
                object8 = string2;
                object6 = object3;
            }
        } else {
            object = string2;
            object3 = object4;
            string2 = object5;
            object4 = object7;
            object7 = object8;
            object5 = object6;
            object6 = string2;
            object8 = object3;
        }
        if (!"true".equalsIgnoreCase((String)object7)) {
            uRL = null;
        }
        return this.definePackage(string, (String)object, (String)object8, (String)object6, string3, (String)object5, (String)object4, uRL);
    }

    @Override
    protected Class<?> findClass(final String string) throws ClassNotFoundException {
        try {
            PrivilegedExceptionAction privilegedExceptionAction = new PrivilegedExceptionAction<Class<?>>(){

                @Override
                public Class<?> run() throws ClassNotFoundException {
                    Object object = string.replace('.', '/').concat(".class");
                    object = URLClassLoader.this.ucp.getResource((String)object, false);
                    if (object != null) {
                        try {
                            object = URLClassLoader.this.defineClass(string, (Resource)object);
                            return object;
                        }
                        catch (IOException iOException) {
                            throw new ClassNotFoundException(string, iOException);
                        }
                    }
                    return null;
                }
            };
            privilegedExceptionAction = (Class)AccessController.doPrivileged(privilegedExceptionAction, this.acc);
            if (privilegedExceptionAction != null) {
                return privilegedExceptionAction;
            }
            throw new ClassNotFoundException(string);
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (ClassNotFoundException)privilegedActionException.getException();
        }
    }

    @Override
    public URL findResource(String object) {
        object = (object = AccessController.doPrivileged(new PrivilegedAction<URL>((String)object){
            final /* synthetic */ String val$name;
            {
                this.val$name = string;
            }

            @Override
            public URL run() {
                return URLClassLoader.this.ucp.findResource(this.val$name, true);
            }
        }, this.acc)) != null ? this.ucp.checkURL((URL)object) : null;
        return object;
    }

    @Override
    public Enumeration<URL> findResources(String string) throws IOException {
        return new Enumeration<URL>(this.ucp.findResources(string, true)){
            private URL url = null;
            final /* synthetic */ Enumeration val$e;
            {
                this.val$e = enumeration;
            }

            private boolean next() {
                URL uRL = this.url;
                boolean bl = true;
                if (uRL != null) {
                    return true;
                }
                while ((uRL = AccessController.doPrivileged(new PrivilegedAction<URL>(){

                    @Override
                    public URL run() {
                        if (!val$e.hasMoreElements()) {
                            return null;
                        }
                        return (URL)val$e.nextElement();
                    }
                }, URLClassLoader.this.acc)) != null) {
                    this.url = URLClassLoader.this.ucp.checkURL(uRL);
                    if (this.url == null) continue;
                }
                if (this.url == null) {
                    bl = false;
                }
                return bl;
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

    @Override
    protected PermissionCollection getPermissions(CodeSource object) {
        Object object2;
        PermissionCollection permissionCollection = super.getPermissions((CodeSource)object);
        Object object3 = ((CodeSource)object).getLocation();
        try {
            object2 = ((URL)object3).openConnection();
            object = ((URLConnection)object2).getPermission();
        }
        catch (IOException iOException) {
            object = null;
            object2 = null;
        }
        if (object instanceof FilePermission) {
            object2 = ((Permission)object).getName();
            if (((String)object2).endsWith(File.separator)) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append("-");
                object = new FilePermission(((StringBuilder)object).toString(), "read");
            }
            object2 = object;
        } else if (object == null && ((URL)object3).getProtocol().equals("file")) {
            object = object2 = ParseUtil.decode(((URL)object3).getFile().replace('/', File.separatorChar));
            if (((String)object2).endsWith(File.separator)) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append("-");
                object = ((StringBuilder)object).toString();
            }
            object2 = new FilePermission((String)object, "read");
        } else {
            if (object2 instanceof JarURLConnection) {
                object3 = ((JarURLConnection)object2).getJarFileURL();
            }
            object3 = ((URL)object3).getHost();
            object2 = object;
            if (object3 != null) {
                object2 = object;
                if (((String)object3).length() > 0) {
                    object2 = new SocketPermission((String)object3, "connect,accept");
                }
            }
        }
        if (object2 != null) {
            object = System.getSecurityManager();
            if (object != null) {
                AccessController.doPrivileged(new PrivilegedAction<Void>((SecurityManager)object, (Permission)object2){
                    final /* synthetic */ Permission val$fp;
                    final /* synthetic */ SecurityManager val$sm;
                    {
                        this.val$sm = securityManager;
                        this.val$fp = permission;
                    }

                    @Override
                    public Void run() throws SecurityException {
                        this.val$sm.checkPermission(this.val$fp);
                        return null;
                    }
                }, this.acc);
            }
            permissionCollection.add((Permission)object2);
        }
        return permissionCollection;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public InputStream getResourceAsStream(String var1_1) {
        block9 : {
            if ((var1_1 = this.getResource((String)var1_1)) == null) {
                return null;
            }
            try {
                var2_3 = var1_1.openConnection();
                var1_1 = var2_3.getInputStream();
                if (!(var2_3 instanceof JarURLConnection)) ** GOTO lbl-1000
                var3_4 = ((JarURLConnection)var2_3).getJarFile();
                var2_3 = this.closeables;
                // MONITORENTER : var2_3
                if (this.closeables.containsKey(var3_4)) break block9;
                this.closeables.put(var3_4, null);
            }
            catch (IOException var1_2) {
                return null;
            }
        }
        // MONITOREXIT : var2_3
        return var1_1;
lbl-1000: // 1 sources:
        {
            if (var2_3 instanceof FileURLConnection == false) return var1_1;
            var2_3 = this.closeables;
            // MONITORENTER : var2_3
        }
        this.closeables.put((Closeable)var1_1, null);
        // MONITOREXIT : var2_3
        return var1_1;
    }

    public URL[] getURLs() {
        return this.ucp.getURLs();
    }

}

