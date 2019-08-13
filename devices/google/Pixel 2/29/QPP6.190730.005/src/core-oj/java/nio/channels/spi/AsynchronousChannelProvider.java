/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels.spi;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import sun.nio.ch.DefaultAsynchronousChannelProvider;

public abstract class AsynchronousChannelProvider {
    protected AsynchronousChannelProvider() {
        this(AsynchronousChannelProvider.checkPermission());
    }

    private AsynchronousChannelProvider(Void void_) {
    }

    private static Void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("asynchronousChannelProvider"));
        }
        return null;
    }

    public static AsynchronousChannelProvider provider() {
        return ProviderHolder.provider;
    }

    public abstract AsynchronousChannelGroup openAsynchronousChannelGroup(int var1, ThreadFactory var2) throws IOException;

    public abstract AsynchronousChannelGroup openAsynchronousChannelGroup(ExecutorService var1, int var2) throws IOException;

    public abstract AsynchronousServerSocketChannel openAsynchronousServerSocketChannel(AsynchronousChannelGroup var1) throws IOException;

    public abstract AsynchronousSocketChannel openAsynchronousSocketChannel(AsynchronousChannelGroup var1) throws IOException;

    private static class ProviderHolder {
        static final AsynchronousChannelProvider provider = ProviderHolder.load();

        private ProviderHolder() {
        }

        private static AsynchronousChannelProvider load() {
            return AccessController.doPrivileged(new PrivilegedAction<AsynchronousChannelProvider>(){

                @Override
                public AsynchronousChannelProvider run() {
                    AsynchronousChannelProvider asynchronousChannelProvider = ProviderHolder.loadProviderFromProperty();
                    if (asynchronousChannelProvider != null) {
                        return asynchronousChannelProvider;
                    }
                    asynchronousChannelProvider = ProviderHolder.loadProviderAsService();
                    if (asynchronousChannelProvider != null) {
                        return asynchronousChannelProvider;
                    }
                    return DefaultAsynchronousChannelProvider.create();
                }
            });
        }

        private static AsynchronousChannelProvider loadProviderAsService() {
            Iterator<AsynchronousChannelProvider> iterator = ServiceLoader.load(AsynchronousChannelProvider.class, ClassLoader.getSystemClassLoader()).iterator();
            do {
                try {
                    AsynchronousChannelProvider asynchronousChannelProvider = iterator.hasNext() ? iterator.next() : null;
                    return asynchronousChannelProvider;
                }
                catch (ServiceConfigurationError serviceConfigurationError) {
                    if (serviceConfigurationError.getCause() instanceof SecurityException) continue;
                    throw serviceConfigurationError;
                }
                break;
            } while (true);
        }

        private static AsynchronousChannelProvider loadProviderFromProperty() {
            Object object = System.getProperty("java.nio.channels.spi.AsynchronousChannelProvider");
            if (object == null) {
                return null;
            }
            try {
                object = (AsynchronousChannelProvider)Class.forName((String)object, true, ClassLoader.getSystemClassLoader()).newInstance();
                return object;
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

    }

}

