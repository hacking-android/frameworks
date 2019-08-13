/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.ApplicationProtocolSelectorAdapter;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.DuckTypedPSKKeyManager;
import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.PSKKeyManager;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.SSLUtils;
import com.android.org.conscrypt.ServerSessionContext;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

final class SSLParametersImpl
implements Cloneable {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static volatile SSLParametersImpl defaultParameters;
    private static volatile X509KeyManager defaultX509KeyManager;
    private static volatile X509TrustManager defaultX509TrustManager;
    ApplicationProtocolSelectorAdapter applicationProtocolSelector;
    byte[] applicationProtocols;
    boolean channelIdEnabled;
    private final ClientSessionContext clientSessionContext;
    private boolean client_mode;
    private boolean ctVerificationEnabled;
    private boolean enable_session_creation;
    String[] enabledCipherSuites;
    String[] enabledProtocols;
    private String endpointIdentificationAlgorithm;
    boolean isEnabledProtocolsFiltered;
    private boolean need_client_auth;
    byte[] ocspResponse;
    private final PSKKeyManager pskKeyManager;
    byte[] sctExtension;
    private final ServerSessionContext serverSessionContext;
    private boolean useCipherSuitesOrder;
    boolean useSessionTickets;
    private Boolean useSni;
    private boolean want_client_auth;
    private final X509KeyManager x509KeyManager;
    @UnsupportedAppUsage
    private final X509TrustManager x509TrustManager;

    private SSLParametersImpl(ClientSessionContext object, ServerSessionContext serverSessionContext, X509KeyManager x509KeyManager, PSKKeyManager pSKKeyManager, X509TrustManager x509TrustManager, SSLParametersImpl sSLParametersImpl) {
        this.client_mode = true;
        this.need_client_auth = false;
        this.want_client_auth = false;
        this.enable_session_creation = true;
        this.applicationProtocols = EmptyArray.BYTE;
        this.clientSessionContext = object;
        this.serverSessionContext = serverSessionContext;
        this.x509KeyManager = x509KeyManager;
        this.pskKeyManager = pSKKeyManager;
        this.x509TrustManager = x509TrustManager;
        object = sSLParametersImpl.enabledProtocols;
        serverSessionContext = null;
        object = object == null ? null : (String[])object.clone();
        this.enabledProtocols = object;
        this.isEnabledProtocolsFiltered = sSLParametersImpl.isEnabledProtocolsFiltered;
        object = sSLParametersImpl.enabledCipherSuites;
        object = object == null ? null : (String[])object.clone();
        this.enabledCipherSuites = object;
        this.client_mode = sSLParametersImpl.client_mode;
        this.need_client_auth = sSLParametersImpl.need_client_auth;
        this.want_client_auth = sSLParametersImpl.want_client_auth;
        this.enable_session_creation = sSLParametersImpl.enable_session_creation;
        this.endpointIdentificationAlgorithm = sSLParametersImpl.endpointIdentificationAlgorithm;
        this.useCipherSuitesOrder = sSLParametersImpl.useCipherSuitesOrder;
        this.ctVerificationEnabled = sSLParametersImpl.ctVerificationEnabled;
        object = sSLParametersImpl.sctExtension;
        object = object == null ? null : (byte[])object.clone();
        this.sctExtension = object;
        object = sSLParametersImpl.ocspResponse;
        object = object == null ? null : (byte[])object.clone();
        this.ocspResponse = object;
        object = sSLParametersImpl.applicationProtocols;
        object = object == null ? serverSessionContext : (byte[])object.clone();
        this.applicationProtocols = object;
        this.applicationProtocolSelector = sSLParametersImpl.applicationProtocolSelector;
        this.useSessionTickets = sSLParametersImpl.useSessionTickets;
        this.useSni = sSLParametersImpl.useSni;
        this.channelIdEnabled = sSLParametersImpl.channelIdEnabled;
    }

    SSLParametersImpl(KeyManager[] arrkeyManager, TrustManager[] arrtrustManager, SecureRandom secureRandom, ClientSessionContext clientSessionContext, ServerSessionContext serverSessionContext, String[] arrstring) throws KeyManagementException {
        boolean bl = true;
        this.client_mode = true;
        this.need_client_auth = false;
        this.want_client_auth = false;
        this.enable_session_creation = true;
        this.applicationProtocols = EmptyArray.BYTE;
        this.serverSessionContext = serverSessionContext;
        this.clientSessionContext = clientSessionContext;
        if (arrkeyManager == null) {
            this.x509KeyManager = SSLParametersImpl.getDefaultX509KeyManager();
            this.pskKeyManager = null;
        } else {
            this.x509KeyManager = SSLParametersImpl.findFirstX509KeyManager(arrkeyManager);
            this.pskKeyManager = SSLParametersImpl.findFirstPSKKeyManager(arrkeyManager);
        }
        this.x509TrustManager = arrtrustManager == null ? SSLParametersImpl.getDefaultX509TrustManager() : SSLParametersImpl.findFirstX509TrustManager(arrtrustManager);
        if (arrstring == null) {
            arrstring = NativeCrypto.DEFAULT_PROTOCOLS;
        }
        this.enabledProtocols = (String[])NativeCrypto.checkEnabledProtocols(arrstring).clone();
        boolean bl2 = this.x509KeyManager != null || this.x509TrustManager != null;
        if (this.pskKeyManager == null) {
            bl = false;
        }
        this.enabledCipherSuites = SSLParametersImpl.getDefaultCipherSuites(bl2, bl);
    }

    private static X509KeyManager createDefaultX509KeyManager() throws KeyManagementException {
        Object object;
        Object[] arrobject;
        block5 : {
            arrobject = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            arrobject.init(null, null);
            arrobject = arrobject.getKeyManagers();
            object = SSLParametersImpl.findFirstX509KeyManager((KeyManager[])arrobject);
            if (object == null) break block5;
            return object;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No X509KeyManager among default KeyManagers: ");
            stringBuilder.append(Arrays.toString(arrobject));
            object = new KeyManagementException(stringBuilder.toString());
            throw object;
        }
        catch (UnrecoverableKeyException unrecoverableKeyException) {
            throw new KeyManagementException(unrecoverableKeyException);
        }
        catch (KeyStoreException keyStoreException) {
            throw new KeyManagementException(keyStoreException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new KeyManagementException(noSuchAlgorithmException);
        }
    }

    private static X509TrustManager createDefaultX509TrustManager() throws KeyManagementException {
        Object object;
        Object[] arrobject;
        block4 : {
            arrobject = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            arrobject.init((KeyStore)null);
            arrobject = arrobject.getTrustManagers();
            object = SSLParametersImpl.findFirstX509TrustManager((TrustManager[])arrobject);
            if (object == null) break block4;
            return object;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No X509TrustManager in among default TrustManagers: ");
            stringBuilder.append(Arrays.toString(arrobject));
            object = new KeyManagementException(stringBuilder.toString());
            throw object;
        }
        catch (KeyStoreException keyStoreException) {
            throw new KeyManagementException(keyStoreException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new KeyManagementException(noSuchAlgorithmException);
        }
    }

    private static String[] filterFromCipherSuites(String[] arrstring, Set<String> set) {
        if (arrstring != null && arrstring.length != 0) {
            ArrayList<String> arrayList = new ArrayList<String>(arrstring.length);
            for (String string : arrstring) {
                if (set.contains(string)) continue;
                arrayList.add(string);
            }
            return arrayList.toArray(EMPTY_STRING_ARRAY);
        }
        return arrstring;
    }

    private static String[] filterFromProtocols(String[] arrstring, String string) {
        int n = arrstring.length;
        if (n == 1 && string.equals(arrstring[0])) {
            return EMPTY_STRING_ARRAY;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string2 : arrstring) {
            if (string.equals(string2)) continue;
            arrayList.add(string2);
        }
        return arrayList.toArray(EMPTY_STRING_ARRAY);
    }

    private static PSKKeyManager findFirstPSKKeyManager(KeyManager[] arrkeyManager) {
        for (KeyManager keyManager : arrkeyManager) {
            if (keyManager instanceof PSKKeyManager) {
                return (PSKKeyManager)keyManager;
            }
            if (keyManager == null) continue;
            try {
                keyManager = DuckTypedPSKKeyManager.getInstance(keyManager);
                return keyManager;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
        }
        return null;
    }

    private static X509KeyManager findFirstX509KeyManager(KeyManager[] arrkeyManager) {
        for (KeyManager keyManager : arrkeyManager) {
            if (!(keyManager instanceof X509KeyManager)) continue;
            return (X509KeyManager)keyManager;
        }
        return null;
    }

    private static X509TrustManager findFirstX509TrustManager(TrustManager[] arrtrustManager) {
        for (TrustManager trustManager : arrtrustManager) {
            if (!(trustManager instanceof X509TrustManager)) continue;
            return (X509TrustManager)trustManager;
        }
        return null;
    }

    @UnsupportedAppUsage
    static SSLParametersImpl getDefault() throws KeyManagementException {
        SSLParametersImpl sSLParametersImpl;
        SSLParametersImpl sSLParametersImpl2 = sSLParametersImpl = defaultParameters;
        if (sSLParametersImpl == null) {
            sSLParametersImpl2 = sSLParametersImpl = new SSLParametersImpl(null, null, null, new ClientSessionContext(), new ServerSessionContext(), null);
            defaultParameters = sSLParametersImpl;
        }
        return (SSLParametersImpl)sSLParametersImpl2.clone();
    }

    private static String[] getDefaultCipherSuites(boolean bl, boolean bl2) {
        if (bl) {
            if (bl2) {
                return SSLUtils.concat(NativeCrypto.DEFAULT_PSK_CIPHER_SUITES, NativeCrypto.DEFAULT_X509_CIPHER_SUITES, {"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"});
            }
            return SSLUtils.concat(NativeCrypto.DEFAULT_X509_CIPHER_SUITES, {"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"});
        }
        if (bl2) {
            return SSLUtils.concat(NativeCrypto.DEFAULT_PSK_CIPHER_SUITES, {"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"});
        }
        return new String[]{"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"};
    }

    private static X509KeyManager getDefaultX509KeyManager() throws KeyManagementException {
        X509KeyManager x509KeyManager;
        X509KeyManager x509KeyManager2 = x509KeyManager = defaultX509KeyManager;
        if (x509KeyManager == null) {
            x509KeyManager2 = x509KeyManager = SSLParametersImpl.createDefaultX509KeyManager();
            defaultX509KeyManager = x509KeyManager;
        }
        return x509KeyManager2;
    }

    @UnsupportedAppUsage
    static X509TrustManager getDefaultX509TrustManager() throws KeyManagementException {
        X509TrustManager x509TrustManager;
        X509TrustManager x509TrustManager2 = x509TrustManager = defaultX509TrustManager;
        if (x509TrustManager == null) {
            x509TrustManager2 = x509TrustManager = SSLParametersImpl.createDefaultX509TrustManager();
            defaultX509TrustManager = x509TrustManager;
        }
        return x509TrustManager2;
    }

    private boolean isSniEnabledByDefault() {
        Object object;
        block5 : {
            block4 : {
                try {
                    object = System.getProperty("jsse.enableSNIExtension", "true");
                    if (!"true".equalsIgnoreCase((String)object)) break block4;
                    return true;
                }
                catch (SecurityException securityException) {
                    return true;
                }
            }
            if (!"false".equalsIgnoreCase((String)object)) break block5;
            return false;
        }
        object = new RuntimeException("Can only set \"jsse.enableSNIExtension\" to \"true\" or \"false\"");
        throw object;
    }

    protected Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    SSLParametersImpl cloneWithTrustManager(X509TrustManager x509TrustManager) {
        return new SSLParametersImpl(this.clientSessionContext, this.serverSessionContext, this.x509KeyManager, this.pskKeyManager, x509TrustManager, this);
    }

    String[] getApplicationProtocols() {
        return SSLUtils.decodeProtocols(this.applicationProtocols);
    }

    ClientSessionContext getClientSessionContext() {
        return this.clientSessionContext;
    }

    boolean getEnableSessionCreation() {
        return this.enable_session_creation;
    }

    String[] getEnabledCipherSuites() {
        if (Arrays.asList(this.enabledProtocols).contains("TLSv1.3")) {
            return SSLUtils.concat(NativeCrypto.SUPPORTED_TLS_1_3_CIPHER_SUITES, this.enabledCipherSuites);
        }
        return (String[])this.enabledCipherSuites.clone();
    }

    String[] getEnabledProtocols() {
        return (String[])this.enabledProtocols.clone();
    }

    String getEndpointIdentificationAlgorithm() {
        return this.endpointIdentificationAlgorithm;
    }

    boolean getNeedClientAuth() {
        return this.need_client_auth;
    }

    byte[] getOCSPResponse() {
        return this.ocspResponse;
    }

    PSKKeyManager getPSKKeyManager() {
        return this.pskKeyManager;
    }

    AbstractSessionContext getSessionContext() {
        AbstractSessionContext abstractSessionContext = this.client_mode ? this.clientSessionContext : this.serverSessionContext;
        return abstractSessionContext;
    }

    boolean getUseCipherSuitesOrder() {
        return this.useCipherSuitesOrder;
    }

    boolean getUseClientMode() {
        return this.client_mode;
    }

    boolean getUseSni() {
        Boolean bl = this.useSni;
        boolean bl2 = bl != null ? bl.booleanValue() : this.isSniEnabledByDefault();
        return bl2;
    }

    boolean getWantClientAuth() {
        return this.want_client_auth;
    }

    X509KeyManager getX509KeyManager() {
        return this.x509KeyManager;
    }

    @UnsupportedAppUsage
    X509TrustManager getX509TrustManager() {
        return this.x509TrustManager;
    }

    boolean isCTVerificationEnabled(String string) {
        if (string == null) {
            return false;
        }
        if (this.ctVerificationEnabled) {
            return true;
        }
        return Platform.isCTVerificationRequired(string);
    }

    void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.applicationProtocolSelector = applicationProtocolSelectorAdapter;
    }

    void setApplicationProtocols(String[] arrstring) {
        this.applicationProtocols = SSLUtils.encodeProtocols(arrstring);
    }

    void setCTVerificationEnabled(boolean bl) {
        this.ctVerificationEnabled = bl;
    }

    void setEnableSessionCreation(boolean bl) {
        this.enable_session_creation = bl;
    }

    void setEnabledCipherSuites(String[] arrstring) {
        this.enabledCipherSuites = NativeCrypto.checkEnabledCipherSuites(SSLParametersImpl.filterFromCipherSuites(arrstring, NativeCrypto.SUPPORTED_TLS_1_3_CIPHER_SUITES_SET));
    }

    @UnsupportedAppUsage
    void setEnabledProtocols(String[] arrstring) {
        if (arrstring != null) {
            String[] arrstring2 = SSLParametersImpl.filterFromProtocols(arrstring, "SSLv3");
            boolean bl = arrstring.length != arrstring2.length;
            this.isEnabledProtocolsFiltered = bl;
            this.enabledProtocols = (String[])NativeCrypto.checkEnabledProtocols(arrstring2).clone();
            return;
        }
        throw new IllegalArgumentException("protocols == null");
    }

    void setEndpointIdentificationAlgorithm(String string) {
        this.endpointIdentificationAlgorithm = string;
    }

    void setNeedClientAuth(boolean bl) {
        this.need_client_auth = bl;
        this.want_client_auth = false;
    }

    void setOCSPResponse(byte[] arrby) {
        this.ocspResponse = arrby;
    }

    void setSCTExtension(byte[] arrby) {
        this.sctExtension = arrby;
    }

    void setUseCipherSuitesOrder(boolean bl) {
        this.useCipherSuitesOrder = bl;
    }

    void setUseClientMode(boolean bl) {
        this.client_mode = bl;
    }

    void setUseSessionTickets(boolean bl) {
        this.useSessionTickets = bl;
    }

    void setUseSni(boolean bl) {
        this.useSni = bl;
    }

    void setWantClientAuth(boolean bl) {
        this.want_client_auth = bl;
        this.need_client_auth = false;
    }

    static interface AliasChooser {
        public String chooseClientAlias(X509KeyManager var1, X500Principal[] var2, String[] var3);

        public String chooseServerAlias(X509KeyManager var1, String var2);
    }

    static interface PSKCallbacks {
        public String chooseClientPSKIdentity(PSKKeyManager var1, String var2);

        public String chooseServerPSKIdentityHint(PSKKeyManager var1);

        public SecretKey getPSKKey(PSKKeyManager var1, String var2, String var3);
    }

}

