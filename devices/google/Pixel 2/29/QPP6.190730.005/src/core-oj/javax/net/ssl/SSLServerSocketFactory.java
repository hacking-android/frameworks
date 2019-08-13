/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.net.ServerSocketFactory;
import javax.net.ssl.DefaultSSLServerSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public abstract class SSLServerSocketFactory
extends ServerSocketFactory {
    private static SSLServerSocketFactory defaultServerSocketFactory;
    private static int lastVersion;

    static {
        lastVersion = -1;
    }

    protected SSLServerSocketFactory() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ServerSocketFactory getDefault() {
        synchronized (SSLServerSocketFactory.class) {
            if (defaultServerSocketFactory != null && lastVersion == Security.getVersion()) {
                return defaultServerSocketFactory;
            }
            lastVersion = Security.getVersion();
            Object object = defaultServerSocketFactory;
            defaultServerSocketFactory = null;
            String string = SSLSocketFactory.getSecurityProperty("ssl.ServerSocketFactory.provider");
            if (string != null) {
                Exception exception22;
                block19 : {
                    if (object != null && string.equals(object.getClass().getName())) {
                        defaultServerSocketFactory = object;
                        return defaultServerSocketFactory;
                    }
                    SSLServerSocketFactory.log("setting up default SSLServerSocketFactory");
                    object = null;
                    try {
                        Object object2;
                        block18 : {
                            try {
                                object = object2 = Class.forName(string);
                            }
                            catch (ClassNotFoundException classNotFoundException) {
                                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                                object2 = classLoader;
                                if (classLoader == null) {
                                    object2 = ClassLoader.getSystemClassLoader();
                                }
                                if (object2 == null) break block18;
                                object = Class.forName(string, true, (ClassLoader)object2);
                            }
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("class ");
                        ((StringBuilder)object2).append(string);
                        ((StringBuilder)object2).append(" is loaded");
                        SSLServerSocketFactory.log(((StringBuilder)object2).toString());
                        object = (SSLServerSocketFactory)((Class)object).newInstance();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("instantiated an instance of class ");
                        ((StringBuilder)object2).append(string);
                        SSLServerSocketFactory.log(((StringBuilder)object2).toString());
                        defaultServerSocketFactory = object;
                    }
                    catch (Exception exception22) {
                        break block19;
                    }
                    return object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("SSLServerSocketFactory instantiation failed: ");
                ((StringBuilder)object).append(exception22);
                SSLServerSocketFactory.log(((StringBuilder)object).toString());
            }
            try {
                object = SSLContext.getDefault();
                if (object != null) {
                    defaultServerSocketFactory = ((SSLContext)object).getServerSocketFactory();
                    return defaultServerSocketFactory;
                } else {
                    object = new IllegalStateException("No factory found.");
                    DefaultSSLServerSocketFactory defaultSSLServerSocketFactory = new DefaultSSLServerSocketFactory((Exception)object);
                    defaultServerSocketFactory = defaultSSLServerSocketFactory;
                }
                return defaultServerSocketFactory;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                return new DefaultSSLServerSocketFactory(noSuchAlgorithmException);
            }
        }
    }

    private static void log(String string) {
        if (SSLSocketFactory.DEBUG) {
            System.out.println(string);
        }
    }

    public abstract String[] getDefaultCipherSuites();

    public abstract String[] getSupportedCipherSuites();
}

