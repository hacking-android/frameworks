/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www.protocol.jar;

import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import java.util.HashMap;
import java.util.jar.JarFile;
import sun.net.util.URLUtil;
import sun.net.www.protocol.jar.URLJarFile;

class JarFileFactory
implements URLJarFile.URLJarFileCloseController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final HashMap<String, JarFile> fileCache = new HashMap();
    private static final JarFileFactory instance;
    private static final HashMap<JarFile, URL> urlCache;

    static {
        urlCache = new HashMap();
        instance = new JarFileFactory();
    }

    private JarFileFactory() {
    }

    private JarFile getCachedJarFile(URL uRL) {
        SecurityManager securityManager;
        Permission permission;
        JarFile jarFile = fileCache.get(URLUtil.urlNoFragString(uRL));
        if (jarFile != null && (permission = this.getPermission(jarFile)) != null && (securityManager = System.getSecurityManager()) != null) {
            try {
                securityManager.checkPermission(permission);
            }
            catch (SecurityException securityException) {
                if (permission instanceof FilePermission && permission.getActions().indexOf("read") != -1) {
                    securityManager.checkRead(permission.getName());
                }
                if (permission instanceof SocketPermission && permission.getActions().indexOf("connect") != -1) {
                    securityManager.checkConnect(uRL.getHost(), uRL.getPort());
                }
                throw securityException;
            }
        }
        return jarFile;
    }

    public static JarFileFactory getInstance() {
        return instance;
    }

    private Permission getPermission(JarFile object) {
        block3 : {
            object = this.getConnection((JarFile)object);
            if (object == null) break block3;
            try {
                object = ((URLConnection)object).getPermission();
                return object;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close(JarFile object) {
        JarFileFactory jarFileFactory = instance;
        synchronized (jarFileFactory) {
            object = urlCache.remove(object);
            if (object != null) {
                fileCache.remove(URLUtil.urlNoFragString((URL)object));
            }
            return;
        }
    }

    public JarFile get(URL uRL) throws IOException {
        return this.get(uRL, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    JarFile get(URL uRL, boolean bl) throws IOException {
        Object object;
        if (bl) {
            JarFile jarFile;
            object = instance;
            synchronized (object) {
                jarFile = this.getCachedJarFile(uRL);
            }
            object = jarFile;
            if (jarFile == null) {
                JarFile jarFile2 = URLJarFile.getJarFile(uRL, this);
                JarFileFactory jarFileFactory = instance;
                synchronized (jarFileFactory) {
                    jarFile = this.getCachedJarFile(uRL);
                    if (jarFile == null) {
                        fileCache.put(URLUtil.urlNoFragString(uRL), jarFile2);
                        urlCache.put(jarFile2, uRL);
                        object = jarFile2;
                    } else {
                        object = jarFile;
                        if (jarFile2 != null) {
                            jarFile2.close();
                            object = jarFile;
                        }
                    }
                }
            }
        } else {
            object = URLJarFile.getJarFile(uRL, this);
        }
        if (object != null) {
            return object;
        }
        throw new FileNotFoundException(uRL.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    URLConnection getConnection(JarFile object) throws IOException {
        JarFileFactory jarFileFactory = instance;
        // MONITORENTER : jarFileFactory
        object = urlCache.get(object);
        // MONITOREXIT : jarFileFactory
        if (object == null) return null;
        return ((URL)object).openConnection();
    }
}

