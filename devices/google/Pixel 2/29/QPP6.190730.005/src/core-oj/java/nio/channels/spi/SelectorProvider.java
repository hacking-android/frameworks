/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels.spi;

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import sun.nio.ch.DefaultSelectorProvider;

public abstract class SelectorProvider {
    private static final Object lock = new Object();
    private static SelectorProvider provider = null;

    protected SelectorProvider() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("selectorProvider"));
        }
    }

    private static boolean loadProviderAsService() {
        Iterator<SelectorProvider> iterator = ServiceLoader.load(SelectorProvider.class, ClassLoader.getSystemClassLoader()).iterator();
        do {
            block4 : {
                try {
                    if (iterator.hasNext()) break block4;
                    return false;
                }
                catch (ServiceConfigurationError serviceConfigurationError) {
                    if (serviceConfigurationError.getCause() instanceof SecurityException) continue;
                    throw serviceConfigurationError;
                }
            }
            provider = iterator.next();
            return true;
            break;
        } while (true);
    }

    private static boolean loadProviderFromProperty() {
        String string = System.getProperty("java.nio.channels.spi.SelectorProvider");
        if (string == null) {
            return false;
        }
        try {
            provider = (SelectorProvider)Class.forName(string, true, ClassLoader.getSystemClassLoader()).newInstance();
            return true;
        }
        catch (SecurityException securityException) {
            throw new ServiceConfigurationError(null, securityException);
        }
        catch (InstantiationException instantiationException) {
            throw new ServiceConfigurationError(null, instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new ServiceConfigurationError(null, illegalAccessException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new ServiceConfigurationError(null, classNotFoundException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SelectorProvider provider() {
        Object object = lock;
        synchronized (object) {
            if (provider != null) {
                return provider;
            }
            PrivilegedAction<SelectorProvider> privilegedAction = new PrivilegedAction<SelectorProvider>(){

                @Override
                public SelectorProvider run() {
                    if (SelectorProvider.loadProviderFromProperty()) {
                        return provider;
                    }
                    if (SelectorProvider.loadProviderAsService()) {
                        return provider;
                    }
                    provider = DefaultSelectorProvider.create();
                    return provider;
                }
            };
            return AccessController.doPrivileged(privilegedAction);
        }
    }

    public Channel inheritedChannel() throws IOException {
        return null;
    }

    public abstract DatagramChannel openDatagramChannel() throws IOException;

    public abstract DatagramChannel openDatagramChannel(ProtocolFamily var1) throws IOException;

    public abstract Pipe openPipe() throws IOException;

    public abstract AbstractSelector openSelector() throws IOException;

    public abstract ServerSocketChannel openServerSocketChannel() throws IOException;

    public abstract SocketChannel openSocketChannel() throws IOException;

}

