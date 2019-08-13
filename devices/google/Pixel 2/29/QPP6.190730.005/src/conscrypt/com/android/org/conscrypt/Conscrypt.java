/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractConscryptEngine;
import com.android.org.conscrypt.AbstractConscryptSocket;
import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.BufferAllocator;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.ConscryptEngine;
import com.android.org.conscrypt.ConscryptEngineSocket;
import com.android.org.conscrypt.ConscryptHostnameVerifier;
import com.android.org.conscrypt.HandshakeListener;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLContextImpl;
import com.android.org.conscrypt.OpenSSLProvider;
import com.android.org.conscrypt.OpenSSLServerSocketFactoryImpl;
import com.android.org.conscrypt.OpenSSLSocketFactoryImpl;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.SSLClientSessionCache;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.SSLServerSessionCache;
import com.android.org.conscrypt.ServerSessionContext;
import com.android.org.conscrypt.TrustManagerImpl;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.PrivateKey;
import java.security.Provider;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class Conscrypt {
    private static final Version VERSION;

    static {
        int n;
        int n2;
        int n3;
        int n4;
        block8 : {
            int n5 = -1;
            int n6 = -1;
            n3 = -1;
            n = n5;
            n4 = n6;
            InputStream inputStream = Conscrypt.class.getResourceAsStream("conscrypt.properties");
            n2 = n5;
            n4 = n6;
            n = n3;
            if (inputStream == null) break block8;
            n = n5;
            n4 = n6;
            n = n5;
            n4 = n6;
            Properties properties = new Properties();
            n = n5;
            n4 = n6;
            properties.load(inputStream);
            n = n5;
            n4 = n6;
            n = n2 = Integer.parseInt(properties.getProperty("com.android.org.conscrypt.version.major", "-1"));
            n4 = n6;
            n6 = Integer.parseInt(properties.getProperty("com.android.org.conscrypt.version.minor", "-1"));
            n = n2;
            n4 = n6;
            try {
                n = n5 = Integer.parseInt(properties.getProperty("com.android.org.conscrypt.version.patch", "-1"));
                n4 = n6;
            }
            catch (IOException iOException) {
                n2 = n;
            }
        }
        n3 = n;
        VERSION = n2 >= 0 && n4 >= 0 && n3 >= 0 ? new Version(n2, n4, n3) : null;
    }

    private Conscrypt() {
    }

    public static void checkAvailability() {
        NativeCrypto.checkAvailability();
    }

    public static byte[] exportKeyingMaterial(SSLEngine sSLEngine, String string, byte[] arrby, int n) throws SSLException {
        return Conscrypt.toConscrypt(sSLEngine).exportKeyingMaterial(string, arrby, n);
    }

    public static byte[] exportKeyingMaterial(SSLSocket sSLSocket, String string, byte[] arrby, int n) throws SSLException {
        return Conscrypt.toConscrypt(sSLSocket).exportKeyingMaterial(string, arrby, n);
    }

    public static String getApplicationProtocol(SSLEngine sSLEngine) {
        return Conscrypt.toConscrypt(sSLEngine).getApplicationProtocol();
    }

    public static String getApplicationProtocol(SSLSocket sSLSocket) {
        return Conscrypt.toConscrypt(sSLSocket).getApplicationProtocol();
    }

    public static String[] getApplicationProtocols(SSLEngine sSLEngine) {
        return Conscrypt.toConscrypt(sSLEngine).getApplicationProtocols();
    }

    public static String[] getApplicationProtocols(SSLSocket sSLSocket) {
        return Conscrypt.toConscrypt(sSLSocket).getApplicationProtocols();
    }

    public static byte[] getChannelId(SSLEngine sSLEngine) throws SSLException {
        return Conscrypt.toConscrypt(sSLEngine).getChannelId();
    }

    public static byte[] getChannelId(SSLSocket sSLSocket) throws SSLException {
        return Conscrypt.toConscrypt(sSLSocket).getChannelId();
    }

    public static ConscryptHostnameVerifier getDefaultHostnameVerifier(TrustManager object) {
        synchronized (Conscrypt.class) {
            object = TrustManagerImpl.getDefaultHostnameVerifier();
            return object;
        }
    }

    public static X509TrustManager getDefaultX509TrustManager() throws KeyManagementException {
        Conscrypt.checkAvailability();
        return SSLParametersImpl.getDefaultX509TrustManager();
    }

    public static String getHostname(SSLEngine sSLEngine) {
        return Conscrypt.toConscrypt(sSLEngine).getHostname();
    }

    public static String getHostname(SSLSocket sSLSocket) {
        return Conscrypt.toConscrypt(sSLSocket).getHostname();
    }

    public static String getHostnameOrIP(SSLSocket sSLSocket) {
        return Conscrypt.toConscrypt(sSLSocket).getHostnameOrIP();
    }

    public static ConscryptHostnameVerifier getHostnameVerifier(TrustManager trustManager) {
        return Conscrypt.toConscrypt(trustManager).getHostnameVerifier();
    }

    public static byte[] getTlsUnique(SSLEngine sSLEngine) {
        return Conscrypt.toConscrypt(sSLEngine).getTlsUnique();
    }

    public static byte[] getTlsUnique(SSLSocket sSLSocket) {
        return Conscrypt.toConscrypt(sSLSocket).getTlsUnique();
    }

    public static boolean isAvailable() {
        try {
            Conscrypt.checkAvailability();
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static boolean isConscrypt(Provider provider) {
        return provider instanceof OpenSSLProvider;
    }

    public static boolean isConscrypt(SSLContext sSLContext) {
        return sSLContext.getProvider() instanceof OpenSSLProvider;
    }

    public static boolean isConscrypt(SSLEngine sSLEngine) {
        return sSLEngine instanceof AbstractConscryptEngine;
    }

    public static boolean isConscrypt(SSLServerSocketFactory sSLServerSocketFactory) {
        return sSLServerSocketFactory instanceof OpenSSLServerSocketFactoryImpl;
    }

    public static boolean isConscrypt(SSLSocket sSLSocket) {
        return sSLSocket instanceof AbstractConscryptSocket;
    }

    public static boolean isConscrypt(SSLSocketFactory sSLSocketFactory) {
        return sSLSocketFactory instanceof OpenSSLSocketFactoryImpl;
    }

    public static boolean isConscrypt(TrustManager trustManager) {
        return trustManager instanceof TrustManagerImpl;
    }

    public static int maxEncryptedPacketLength() {
        return 16709;
    }

    public static int maxSealOverhead(SSLEngine sSLEngine) {
        return Conscrypt.toConscrypt(sSLEngine).maxSealOverhead();
    }

    public static SSLContextSpi newPreferredSSLContextSpi() {
        Conscrypt.checkAvailability();
        return OpenSSLContextImpl.getPreferred();
    }

    public static Provider newProvider() {
        Conscrypt.checkAvailability();
        return new OpenSSLProvider();
    }

    @Deprecated
    public static Provider newProvider(String string) {
        Conscrypt.checkAvailability();
        return new OpenSSLProvider(string, Platform.provideTrustManagerByDefault());
    }

    public static ProviderBuilder newProviderBuilder() {
        return new ProviderBuilder();
    }

    public static void setApplicationProtocolSelector(SSLEngine sSLEngine, ApplicationProtocolSelector applicationProtocolSelector) {
        Conscrypt.toConscrypt(sSLEngine).setApplicationProtocolSelector(applicationProtocolSelector);
    }

    public static void setApplicationProtocolSelector(SSLSocket sSLSocket, ApplicationProtocolSelector applicationProtocolSelector) {
        Conscrypt.toConscrypt(sSLSocket).setApplicationProtocolSelector(applicationProtocolSelector);
    }

    public static void setApplicationProtocols(SSLEngine sSLEngine, String[] arrstring) {
        Conscrypt.toConscrypt(sSLEngine).setApplicationProtocols(arrstring);
    }

    public static void setApplicationProtocols(SSLSocket sSLSocket, String[] arrstring) {
        Conscrypt.toConscrypt(sSLSocket).setApplicationProtocols(arrstring);
    }

    public static void setBufferAllocator(SSLEngine sSLEngine, BufferAllocator bufferAllocator) {
        Conscrypt.toConscrypt(sSLEngine).setBufferAllocator(bufferAllocator);
    }

    public static void setBufferAllocator(SSLSocket sSLSocket, BufferAllocator bufferAllocator) {
        if ((sSLSocket = Conscrypt.toConscrypt(sSLSocket)) instanceof ConscryptEngineSocket) {
            ((ConscryptEngineSocket)sSLSocket).setBufferAllocator(bufferAllocator);
        }
    }

    public static void setChannelIdEnabled(SSLEngine sSLEngine, boolean bl) {
        Conscrypt.toConscrypt(sSLEngine).setChannelIdEnabled(bl);
    }

    public static void setChannelIdEnabled(SSLSocket sSLSocket, boolean bl) {
        Conscrypt.toConscrypt(sSLSocket).setChannelIdEnabled(bl);
    }

    public static void setChannelIdPrivateKey(SSLEngine sSLEngine, PrivateKey privateKey) {
        Conscrypt.toConscrypt(sSLEngine).setChannelIdPrivateKey(privateKey);
    }

    public static void setChannelIdPrivateKey(SSLSocket sSLSocket, PrivateKey privateKey) {
        Conscrypt.toConscrypt(sSLSocket).setChannelIdPrivateKey(privateKey);
    }

    public static void setClientSessionCache(SSLContext object, SSLClientSessionCache object2) {
        if ((object = ((SSLContext)object).getClientSessionContext()) instanceof ClientSessionContext) {
            ((ClientSessionContext)object).setPersistentCache((SSLClientSessionCache)object2);
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Not a conscrypt client context: ");
        ((StringBuilder)object2).append(object.getClass().getName());
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    public static void setDefaultBufferAllocator(BufferAllocator bufferAllocator) {
        ConscryptEngine.setDefaultBufferAllocator(bufferAllocator);
    }

    public static void setDefaultHostnameVerifier(ConscryptHostnameVerifier conscryptHostnameVerifier) {
        synchronized (Conscrypt.class) {
            TrustManagerImpl.setDefaultHostnameVerifier(conscryptHostnameVerifier);
            return;
        }
    }

    public static void setHandshakeListener(SSLEngine sSLEngine, HandshakeListener handshakeListener) {
        Conscrypt.toConscrypt(sSLEngine).setHandshakeListener(handshakeListener);
    }

    public static void setHostname(SSLEngine sSLEngine, String string) {
        Conscrypt.toConscrypt(sSLEngine).setHostname(string);
    }

    public static void setHostname(SSLSocket sSLSocket, String string) {
        Conscrypt.toConscrypt(sSLSocket).setHostname(string);
    }

    public static void setHostnameVerifier(TrustManager trustManager, ConscryptHostnameVerifier conscryptHostnameVerifier) {
        Conscrypt.toConscrypt(trustManager).setHostnameVerifier(conscryptHostnameVerifier);
    }

    public static void setServerSessionCache(SSLContext object, SSLServerSessionCache object2) {
        if ((object = ((SSLContext)object).getServerSessionContext()) instanceof ServerSessionContext) {
            ((ServerSessionContext)object).setPersistentCache((SSLServerSessionCache)object2);
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Not a conscrypt client context: ");
        ((StringBuilder)object2).append(object.getClass().getName());
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    public static void setUseEngineSocket(SSLServerSocketFactory sSLServerSocketFactory, boolean bl) {
        Conscrypt.toConscrypt(sSLServerSocketFactory).setUseEngineSocket(bl);
    }

    public static void setUseEngineSocket(SSLSocketFactory sSLSocketFactory, boolean bl) {
        Conscrypt.toConscrypt(sSLSocketFactory).setUseEngineSocket(bl);
    }

    public static void setUseEngineSocketByDefault(boolean bl) {
        OpenSSLSocketFactoryImpl.setUseEngineSocketByDefault(bl);
        OpenSSLServerSocketFactoryImpl.setUseEngineSocketByDefault(bl);
    }

    public static void setUseSessionTickets(SSLEngine sSLEngine, boolean bl) {
        Conscrypt.toConscrypt(sSLEngine).setUseSessionTickets(bl);
    }

    public static void setUseSessionTickets(SSLSocket sSLSocket, boolean bl) {
        Conscrypt.toConscrypt(sSLSocket).setUseSessionTickets(bl);
    }

    private static AbstractConscryptEngine toConscrypt(SSLEngine sSLEngine) {
        if (Conscrypt.isConscrypt(sSLEngine)) {
            return (AbstractConscryptEngine)sSLEngine;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a conscrypt engine: ");
        stringBuilder.append(sSLEngine.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static AbstractConscryptSocket toConscrypt(SSLSocket sSLSocket) {
        if (Conscrypt.isConscrypt(sSLSocket)) {
            return (AbstractConscryptSocket)sSLSocket;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a conscrypt socket: ");
        stringBuilder.append(sSLSocket.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static OpenSSLServerSocketFactoryImpl toConscrypt(SSLServerSocketFactory sSLServerSocketFactory) {
        if (Conscrypt.isConscrypt(sSLServerSocketFactory)) {
            return (OpenSSLServerSocketFactoryImpl)sSLServerSocketFactory;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a conscrypt server socket factory: ");
        stringBuilder.append(sSLServerSocketFactory.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static OpenSSLSocketFactoryImpl toConscrypt(SSLSocketFactory sSLSocketFactory) {
        if (Conscrypt.isConscrypt(sSLSocketFactory)) {
            return (OpenSSLSocketFactoryImpl)sSLSocketFactory;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a conscrypt socket factory: ");
        stringBuilder.append(sSLSocketFactory.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static TrustManagerImpl toConscrypt(TrustManager trustManager) {
        if (Conscrypt.isConscrypt(trustManager)) {
            return (TrustManagerImpl)trustManager;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a Conscrypt trust manager: ");
        stringBuilder.append(trustManager.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static SSLEngineResult unwrap(SSLEngine sSLEngine, ByteBuffer[] arrbyteBuffer, int n, int n2, ByteBuffer[] arrbyteBuffer2, int n3, int n4) throws SSLException {
        return Conscrypt.toConscrypt(sSLEngine).unwrap(arrbyteBuffer, n, n2, arrbyteBuffer2, n3, n4);
    }

    public static SSLEngineResult unwrap(SSLEngine sSLEngine, ByteBuffer[] arrbyteBuffer, ByteBuffer[] arrbyteBuffer2) throws SSLException {
        return Conscrypt.toConscrypt(sSLEngine).unwrap(arrbyteBuffer, arrbyteBuffer2);
    }

    public static Version version() {
        return VERSION;
    }

    public static class ProviderBuilder {
        private String name = Platform.getDefaultProviderName();
        private boolean provideTrustManager = Platform.provideTrustManagerByDefault();

        private ProviderBuilder() {
        }

        public Provider build() {
            return new OpenSSLProvider(this.name, this.provideTrustManager);
        }

        @Deprecated
        public ProviderBuilder provideTrustManager() {
            return this.provideTrustManager(true);
        }

        public ProviderBuilder provideTrustManager(boolean bl) {
            this.provideTrustManager = bl;
            return this;
        }

        public ProviderBuilder setName(String string) {
            this.name = string;
            return this;
        }
    }

    public static class Version {
        private final int major;
        private final int minor;
        private final int patch;

        private Version(int n, int n2, int n3) {
            this.major = n;
            this.minor = n2;
            this.patch = n3;
        }

        public int major() {
            return this.major;
        }

        public int minor() {
            return this.minor;
        }

        public int patch() {
            return this.patch;
        }
    }

}

