/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.Security;
import java.util.Locale;
import javax.net.SocketFactory;
import javax.net.ssl.DefaultSSLSocketFactory;
import javax.net.ssl.SSLContext;
import sun.security.action.GetPropertyAction;

public abstract class SSLSocketFactory
extends SocketFactory {
    static final boolean DEBUG;
    private static SSLSocketFactory defaultSocketFactory;
    private static int lastVersion;

    static {
        lastVersion = -1;
        String string = AccessController.doPrivileged(new GetPropertyAction("javax.net.debug", "")).toLowerCase(Locale.ENGLISH);
        boolean bl = string.contains("all") || string.contains("ssl");
        DEBUG = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SocketFactory getDefault() {
        synchronized (SSLSocketFactory.class) {
            Serializable serializable;
            if (defaultSocketFactory != null && lastVersion == Security.getVersion()) {
                return defaultSocketFactory;
            }
            lastVersion = Security.getVersion();
            Object object = defaultSocketFactory;
            defaultSocketFactory = null;
            String string = SSLSocketFactory.getSecurityProperty("ssl.SocketFactory.provider");
            if (string != null) {
                Exception exception22;
                block19 : {
                    if (object != null && string.equals(object.getClass().getName())) {
                        defaultSocketFactory = object;
                        return defaultSocketFactory;
                    }
                    SSLSocketFactory.log("setting up default SSLSocketFactory");
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
                        SSLSocketFactory.log(((StringBuilder)object2).toString());
                        object = (SSLSocketFactory)((Class)object).newInstance();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("instantiated an instance of class ");
                        ((StringBuilder)object2).append(string);
                        SSLSocketFactory.log(((StringBuilder)object2).toString());
                        defaultSocketFactory = object;
                    }
                    catch (Exception exception22) {
                        break block19;
                    }
                    return object;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("SSLSocketFactory instantiation failed: ");
                ((StringBuilder)serializable).append(exception22.toString());
                SSLSocketFactory.log(((StringBuilder)serializable).toString());
            }
            try {
                object = SSLContext.getDefault();
                if (object != null) {
                    defaultSocketFactory = ((SSLContext)object).getSocketFactory();
                    return defaultSocketFactory;
                } else {
                    serializable = new IllegalStateException("No factory found.");
                    defaultSocketFactory = object = new DefaultSSLSocketFactory((Exception)serializable);
                }
                return defaultSocketFactory;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                return new DefaultSSLSocketFactory(noSuchAlgorithmException);
            }
        }
    }

    static String getSecurityProperty(final String string) {
        return AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                String string3;
                String string2 = string3 = Security.getProperty(string);
                if (string3 != null) {
                    string2 = string3 = string3.trim();
                    if (string3.length() == 0) {
                        string2 = null;
                    }
                }
                return string2;
            }
        });
    }

    private static void log(String string) {
        if (DEBUG) {
            System.out.println(string);
        }
    }

    public Socket createSocket(Socket socket, InputStream inputStream, boolean bl) throws IOException {
        throw new UnsupportedOperationException();
    }

    public abstract Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException;

    public abstract String[] getDefaultCipherSuites();

    public abstract String[] getSupportedCipherSuites();

}

