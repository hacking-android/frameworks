/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructTimeval
 *  dalvik.system.BlockGuard
 *  dalvik.system.CloseGuard
 *  libcore.net.NetworkSecurityPolicy
 */
package com.android.org.conscrypt;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructTimeval;
import com.android.org.conscrypt.AbstractConscryptSocket;
import com.android.org.conscrypt.AddressUtils;
import com.android.org.conscrypt.CertBlacklist;
import com.android.org.conscrypt.CertBlacklistImpl;
import com.android.org.conscrypt.ConscryptCertStore;
import com.android.org.conscrypt.ConscryptEngine;
import com.android.org.conscrypt.ConscryptEngineSocket;
import com.android.org.conscrypt.ConscryptFileDescriptorSocket;
import com.android.org.conscrypt.ExternalSession;
import com.android.org.conscrypt.GCMParameters;
import com.android.org.conscrypt.Java8EngineSocket;
import com.android.org.conscrypt.Java8EngineWrapper;
import com.android.org.conscrypt.Java8ExtendedSSLSession;
import com.android.org.conscrypt.Java8FileDescriptorSocket;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLSocketFactoryImpl;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.android.org.conscrypt.ct.CTLogStore;
import com.android.org.conscrypt.ct.CTLogStoreImpl;
import com.android.org.conscrypt.ct.CTPolicy;
import com.android.org.conscrypt.ct.CTPolicyImpl;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.security.AlgorithmParameters;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.crypto.spec.GCMParameterSpec;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import libcore.net.NetworkSecurityPolicy;
import sun.security.x509.AlgorithmId;

final class Platform {
    private Platform() {
    }

    static void blockGuardOnNetwork() {
        BlockGuard.getThreadPolicy().onNetwork();
    }

    static void checkClientTrusted(X509TrustManager x509TrustManager, X509Certificate[] arrx509Certificate, String string, AbstractConscryptSocket abstractConscryptSocket) throws CertificateException {
        if (x509TrustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)x509TrustManager).checkClientTrusted(arrx509Certificate, string, abstractConscryptSocket);
        } else if (!Platform.checkTrusted("checkClientTrusted", x509TrustManager, arrx509Certificate, string, Socket.class, abstractConscryptSocket) && !Platform.checkTrusted("checkClientTrusted", x509TrustManager, arrx509Certificate, string, String.class, abstractConscryptSocket.getHandshakeSession().getPeerHost())) {
            x509TrustManager.checkClientTrusted(arrx509Certificate, string);
        }
    }

    static void checkClientTrusted(X509TrustManager x509TrustManager, X509Certificate[] arrx509Certificate, String string, ConscryptEngine conscryptEngine) throws CertificateException {
        if (x509TrustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)x509TrustManager).checkClientTrusted(arrx509Certificate, string, conscryptEngine);
        } else if (!Platform.checkTrusted("checkClientTrusted", x509TrustManager, arrx509Certificate, string, SSLEngine.class, conscryptEngine) && !Platform.checkTrusted("checkClientTrusted", x509TrustManager, arrx509Certificate, string, String.class, conscryptEngine.getHandshakeSession().getPeerHost())) {
            x509TrustManager.checkClientTrusted(arrx509Certificate, string);
        }
    }

    static void checkServerTrusted(X509TrustManager x509TrustManager, X509Certificate[] arrx509Certificate, String string, AbstractConscryptSocket abstractConscryptSocket) throws CertificateException {
        if (x509TrustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)x509TrustManager).checkServerTrusted(arrx509Certificate, string, abstractConscryptSocket);
        } else if (!Platform.checkTrusted("checkServerTrusted", x509TrustManager, arrx509Certificate, string, Socket.class, abstractConscryptSocket) && !Platform.checkTrusted("checkServerTrusted", x509TrustManager, arrx509Certificate, string, String.class, abstractConscryptSocket.getHandshakeSession().getPeerHost())) {
            x509TrustManager.checkServerTrusted(arrx509Certificate, string);
        }
    }

    static void checkServerTrusted(X509TrustManager x509TrustManager, X509Certificate[] arrx509Certificate, String string, ConscryptEngine conscryptEngine) throws CertificateException {
        if (x509TrustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)x509TrustManager).checkServerTrusted(arrx509Certificate, string, conscryptEngine);
        } else if (!Platform.checkTrusted("checkServerTrusted", x509TrustManager, arrx509Certificate, string, SSLEngine.class, conscryptEngine) && !Platform.checkTrusted("checkServerTrusted", x509TrustManager, arrx509Certificate, string, String.class, conscryptEngine.getHandshakeSession().getPeerHost())) {
            x509TrustManager.checkServerTrusted(arrx509Certificate, string);
        }
    }

    private static boolean checkTrusted(String string, X509TrustManager x509TrustManager, X509Certificate[] arrx509Certificate, String string2, Class<?> class_, Object object) throws CertificateException {
        try {
            x509TrustManager.getClass().getMethod(string, X509Certificate[].class, String.class, class_).invoke(x509TrustManager, arrx509Certificate, string2, object);
            return true;
        }
        catch (InvocationTargetException invocationTargetException) {
            if (invocationTargetException.getCause() instanceof CertificateException) {
                throw (CertificateException)invocationTargetException.getCause();
            }
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
            return false;
        }
    }

    static void closeGuardClose(Object object) {
        ((CloseGuard)object).close();
    }

    static CloseGuard closeGuardGet() {
        return CloseGuard.get();
    }

    static void closeGuardOpen(Object object, String string) {
        ((CloseGuard)object).open(string);
    }

    static void closeGuardWarnIfOpen(Object object) {
        ((CloseGuard)object).warnIfOpen();
    }

    static ConscryptEngineSocket createEngineSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8EngineSocket(sSLParametersImpl);
    }

    static ConscryptEngineSocket createEngineSocket(String string, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8EngineSocket(string, n, sSLParametersImpl);
    }

    static ConscryptEngineSocket createEngineSocket(String string, int n, InetAddress inetAddress, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8EngineSocket(string, n, inetAddress, n2, sSLParametersImpl);
    }

    static ConscryptEngineSocket createEngineSocket(InetAddress inetAddress, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8EngineSocket(inetAddress, n, sSLParametersImpl);
    }

    static ConscryptEngineSocket createEngineSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8EngineSocket(inetAddress, n, inetAddress2, n2, sSLParametersImpl);
    }

    static ConscryptEngineSocket createEngineSocket(Socket socket, String string, int n, boolean bl, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8EngineSocket(socket, string, n, bl, sSLParametersImpl);
    }

    static ConscryptFileDescriptorSocket createFileDescriptorSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8FileDescriptorSocket(sSLParametersImpl);
    }

    static ConscryptFileDescriptorSocket createFileDescriptorSocket(String string, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8FileDescriptorSocket(string, n, sSLParametersImpl);
    }

    static ConscryptFileDescriptorSocket createFileDescriptorSocket(String string, int n, InetAddress inetAddress, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8FileDescriptorSocket(string, n, inetAddress, n2, sSLParametersImpl);
    }

    static ConscryptFileDescriptorSocket createFileDescriptorSocket(InetAddress inetAddress, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8FileDescriptorSocket(inetAddress, n, sSLParametersImpl);
    }

    static ConscryptFileDescriptorSocket createFileDescriptorSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8FileDescriptorSocket(inetAddress, n, inetAddress2, n2, sSLParametersImpl);
    }

    static ConscryptFileDescriptorSocket createFileDescriptorSocket(Socket socket, String string, int n, boolean bl, SSLParametersImpl sSLParametersImpl) throws IOException {
        return new Java8FileDescriptorSocket(socket, string, n, bl, sSLParametersImpl);
    }

    static GCMParameters fromGCMParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
        if (algorithmParameterSpec instanceof GCMParameterSpec) {
            algorithmParameterSpec = (GCMParameterSpec)algorithmParameterSpec;
            return new GCMParameters(((GCMParameterSpec)algorithmParameterSpec).getTLen(), ((GCMParameterSpec)algorithmParameterSpec).getIV());
        }
        return null;
    }

    static AlgorithmParameterSpec fromGCMParameters(AlgorithmParameters object) {
        try {
            object = ((AlgorithmParameters)object).getParameterSpec(GCMParameterSpec.class);
            return object;
        }
        catch (InvalidParameterSpecException invalidParameterSpecException) {
            return null;
        }
    }

    static String getCurveName(ECParameterSpec eCParameterSpec) {
        return eCParameterSpec.getCurveName();
    }

    static KeyStore getDefaultCertKeyStore() throws KeyStoreException {
        KeyStore keyStore = KeyStore.getInstance("AndroidCAStore");
        try {
            keyStore.load(null, null);
            return keyStore;
        }
        catch (IOException | NoSuchAlgorithmException | CertificateException exception) {
            throw new KeyStoreException(exception);
        }
    }

    public static String getDefaultProviderName() {
        return "AndroidOpenSSL";
    }

    static FileDescriptor getFileDescriptor(Socket socket) {
        return socket.getFileDescriptor$();
    }

    static FileDescriptor getFileDescriptorFromSSLSocket(AbstractConscryptSocket object) {
        try {
            Field field = Socket.class.getDeclaredField("impl");
            field.setAccessible(true);
            object = field.get(object);
            field = SocketImpl.class.getDeclaredField("fd");
            field.setAccessible(true);
            object = (FileDescriptor)field.get(object);
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException("Can't get FileDescriptor from socket", exception);
        }
    }

    static String getHostStringFromInetSocketAddress(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.getHostString();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String getOriginalHostNameFromInetAddress(InetAddress inetAddress) {
        try {
            Method method = InetAddress.class.getDeclaredMethod("holder", new Class[0]);
            method.setAccessible(true);
            Object object = Class.forName("java.net.InetAddress$InetAddressHolder").getDeclaredMethod("getOriginalHostName", new Class[0]);
            ((AccessibleObject)object).setAccessible(true);
            object = (String)((Method)object).invoke(method.invoke(inetAddress, new Object[0]), new Object[0]);
            if (object != null) return object;
            return inetAddress.getHostAddress();
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return inetAddress.getHostAddress();
        }
        catch (IllegalAccessException illegalAccessException) {
            return inetAddress.getHostAddress();
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return inetAddress.getHostAddress();
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException("Failed to get originalHostName", invocationTargetException);
        }
    }

    static void getSSLParameters(SSLParameters sSLParameters, SSLParametersImpl sSLParametersImpl, AbstractConscryptSocket abstractConscryptSocket) {
        sSLParameters.setEndpointIdentificationAlgorithm(sSLParametersImpl.getEndpointIdentificationAlgorithm());
        sSLParameters.setUseCipherSuitesOrder(sSLParametersImpl.getUseCipherSuitesOrder());
        if (sSLParametersImpl.getUseSni() && AddressUtils.isValidSniHostname(abstractConscryptSocket.getHostname())) {
            sSLParameters.setServerNames(Collections.singletonList(new SNIHostName(abstractConscryptSocket.getHostname())));
        }
        sSLParameters.setApplicationProtocols(sSLParametersImpl.getApplicationProtocols());
    }

    static void getSSLParameters(SSLParameters sSLParameters, SSLParametersImpl sSLParametersImpl, ConscryptEngine conscryptEngine) {
        sSLParameters.setEndpointIdentificationAlgorithm(sSLParametersImpl.getEndpointIdentificationAlgorithm());
        sSLParameters.setUseCipherSuitesOrder(sSLParametersImpl.getUseCipherSuitesOrder());
        if (sSLParametersImpl.getUseSni() && AddressUtils.isValidSniHostname(conscryptEngine.getHostname())) {
            sSLParameters.setServerNames(Collections.singletonList(new SNIHostName(conscryptEngine.getHostname())));
        }
        sSLParameters.setApplicationProtocols(sSLParametersImpl.getApplicationProtocols());
    }

    static boolean isCTVerificationRequired(String string) {
        return NetworkSecurityPolicy.getInstance().isCertificateTransparencyVerificationRequired(string);
    }

    static void logEvent(String string) {
        try {
            Class<Object> class_ = Class.forName("android.os.Process");
            Object object = class_.newInstance();
            int n = (Integer)class_.getMethod("myUid", null).invoke(object, new Object[0]);
            object = Class.forName("android.util.EventLog");
            class_ = ((Class)object).newInstance();
            ((Class)object).getMethod("writeEvent", Integer.TYPE, Object[].class).invoke(class_, 1397638484, new Object[]{"conscrypt", n, string});
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    static CertBlacklist newDefaultBlacklist() {
        return CertBlacklistImpl.getDefault();
    }

    static ConscryptCertStore newDefaultCertStore() {
        return new TrustedCertificateStore();
    }

    static CTLogStore newDefaultLogStore() {
        return new CTLogStoreImpl();
    }

    static CTPolicy newDefaultPolicy(CTLogStore cTLogStore) {
        return new CTPolicyImpl(cTLogStore, 2);
    }

    static String oidToAlgorithmName(String string) {
        try {
            String string2 = AlgorithmId.get(string).getName();
            return string2;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return string;
        }
    }

    private void ping() {
    }

    static boolean provideTrustManagerByDefault() {
        return false;
    }

    static void setCurveName(ECParameterSpec eCParameterSpec, String string) {
        eCParameterSpec.setCurveName(string);
    }

    static void setSSLParameters(SSLParameters sSLParameters, SSLParametersImpl sSLParametersImpl, AbstractConscryptSocket abstractConscryptSocket) {
        sSLParametersImpl.setEndpointIdentificationAlgorithm(sSLParameters.getEndpointIdentificationAlgorithm());
        sSLParametersImpl.setUseCipherSuitesOrder(sSLParameters.getUseCipherSuitesOrder());
        Object object = sSLParameters.getServerNames();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                SNIServerName sNIServerName = (SNIServerName)object.next();
                if (sNIServerName.getType() != 0) continue;
                abstractConscryptSocket.setHostname(((SNIHostName)sNIServerName).getAsciiName());
                break;
            }
        }
        sSLParametersImpl.setApplicationProtocols(sSLParameters.getApplicationProtocols());
    }

    static void setSSLParameters(SSLParameters sSLParameters, SSLParametersImpl sSLParametersImpl, ConscryptEngine conscryptEngine) {
        sSLParametersImpl.setEndpointIdentificationAlgorithm(sSLParameters.getEndpointIdentificationAlgorithm());
        sSLParametersImpl.setUseCipherSuitesOrder(sSLParameters.getUseCipherSuitesOrder());
        Object object = sSLParameters.getServerNames();
        if (object != null) {
            Iterator<SNIServerName> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (((SNIServerName)object).getType() != 0) continue;
                conscryptEngine.setHostname(((SNIHostName)object).getAsciiName());
                break;
            }
        }
        sSLParametersImpl.setApplicationProtocols(sSLParameters.getApplicationProtocols());
    }

    static void setSocketWriteTimeout(Socket socket, long l) throws SocketException {
        StructTimeval structTimeval = StructTimeval.fromMillis((long)l);
        try {
            Os.setsockoptTimeval((FileDescriptor)socket.getFileDescriptor$(), (int)OsConstants.SOL_SOCKET, (int)OsConstants.SO_SNDTIMEO, (StructTimeval)structTimeval);
            return;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsSocketException();
        }
    }

    public static void setup() {
        NoPreloadHolder.MAPPER.ping();
    }

    static boolean supportsConscryptCertStore() {
        return true;
    }

    static boolean supportsX509ExtendedTrustManager() {
        return true;
    }

    static AlgorithmParameterSpec toGCMParameterSpec(int n, byte[] arrby) {
        return new GCMParameterSpec(n, arrby);
    }

    static SSLEngine unwrapEngine(SSLEngine sSLEngine) {
        return Java8EngineWrapper.getDelegate(sSLEngine);
    }

    static SSLEngine wrapEngine(ConscryptEngine conscryptEngine) {
        return new Java8EngineWrapper(conscryptEngine);
    }

    static OpenSSLKey wrapRsaKey(PrivateKey privateKey) {
        return null;
    }

    static SSLSession wrapSSLSession(ExternalSession externalSession) {
        return new Java8ExtendedSSLSession(externalSession);
    }

    static SSLSocketFactory wrapSocketFactoryIfNeeded(OpenSSLSocketFactoryImpl openSSLSocketFactoryImpl) {
        return openSSLSocketFactoryImpl;
    }

    private static class NoPreloadHolder {
        public static final Platform MAPPER = new Platform();

        private NoPreloadHolder() {
        }
    }

}

