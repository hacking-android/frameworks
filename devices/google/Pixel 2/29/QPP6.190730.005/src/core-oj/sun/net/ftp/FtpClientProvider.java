/*
 * Decompiled with CFR 0.145.
 */
package sun.net.ftp;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.ServiceConfigurationError;
import sun.net.ftp.FtpClient;
import sun.net.ftp.impl.DefaultFtpClientProvider;

public abstract class FtpClientProvider {
    private static final Object lock = new Object();
    private static FtpClientProvider provider = null;

    protected FtpClientProvider() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("ftpClientProvider"));
        }
    }

    private static boolean loadProviderAsService() {
        return false;
    }

    private static boolean loadProviderFromProperty() {
        String string = System.getProperty("sun.net.ftpClientProvider");
        if (string == null) {
            return false;
        }
        try {
            provider = (FtpClientProvider)Class.forName(string, true, null).newInstance();
            return true;
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SecurityException exception) {
            throw new ServiceConfigurationError(exception.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static FtpClientProvider provider() {
        Object object = lock;
        synchronized (object) {
            if (provider != null) {
                return provider;
            }
            PrivilegedAction<Object> privilegedAction = new PrivilegedAction<Object>(){

                @Override
                public Object run() {
                    if (FtpClientProvider.loadProviderFromProperty()) {
                        return provider;
                    }
                    if (FtpClientProvider.loadProviderAsService()) {
                        return provider;
                    }
                    provider = new DefaultFtpClientProvider();
                    return provider;
                }
            };
            return (FtpClientProvider)AccessController.doPrivileged(privilegedAction);
        }
    }

    public abstract FtpClient createFtpClient();

}

