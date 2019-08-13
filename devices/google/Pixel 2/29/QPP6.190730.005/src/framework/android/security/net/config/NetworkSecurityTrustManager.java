/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.CertPinManager
 *  com.android.org.conscrypt.ConscryptCertStore
 *  com.android.org.conscrypt.TrustManagerImpl
 */
package android.security.net.config;

import android.security.net.config.NetworkSecurityConfig;
import android.security.net.config.Pin;
import android.security.net.config.PinSet;
import android.security.net.config.TrustAnchor;
import android.security.net.config.TrustedCertificateStoreAdapter;
import android.util.ArrayMap;
import com.android.org.conscrypt.CertPinManager;
import com.android.org.conscrypt.ConscryptCertStore;
import com.android.org.conscrypt.TrustManagerImpl;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;

public class NetworkSecurityTrustManager
extends X509ExtendedTrustManager {
    private final TrustManagerImpl mDelegate;
    private X509Certificate[] mIssuers;
    private final Object mIssuersLock = new Object();
    private final NetworkSecurityConfig mNetworkSecurityConfig;

    public NetworkSecurityTrustManager(NetworkSecurityConfig networkSecurityConfig) {
        if (networkSecurityConfig != null) {
            this.mNetworkSecurityConfig = networkSecurityConfig;
            try {
                TrustedCertificateStoreAdapter trustedCertificateStoreAdapter = new TrustedCertificateStoreAdapter(networkSecurityConfig);
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null);
                super(keyStore, null, (ConscryptCertStore)trustedCertificateStoreAdapter);
                this.mDelegate = networkSecurityConfig;
                return;
            }
            catch (IOException | GeneralSecurityException exception) {
                throw new RuntimeException(exception);
            }
        }
        throw new NullPointerException("config must not be null");
    }

    private void checkPins(List<X509Certificate> list) throws CertificateException {
        PinSet pinSet = this.mNetworkSecurityConfig.getPins();
        if (!pinSet.pins.isEmpty() && System.currentTimeMillis() <= pinSet.expirationTime && this.isPinningEnforced(list)) {
            Set<String> set = pinSet.getPinAlgorithms();
            ArrayMap<String, MessageDigest> arrayMap = new ArrayMap<String, MessageDigest>(set.size());
            for (int i = list.size() - 1; i >= 0; --i) {
                byte[] arrby = list.get(i).getPublicKey().getEncoded();
                for (String string2 : set) {
                    MessageDigest messageDigest;
                    MessageDigest messageDigest2 = messageDigest = (MessageDigest)arrayMap.get(string2);
                    if (messageDigest == null) {
                        try {
                            messageDigest2 = MessageDigest.getInstance(string2);
                            arrayMap.put(string2, messageDigest2);
                        }
                        catch (GeneralSecurityException generalSecurityException) {
                            throw new RuntimeException(generalSecurityException);
                        }
                    }
                    if (!pinSet.pins.contains(new Pin(string2, messageDigest2.digest(arrby)))) continue;
                    return;
                }
            }
            throw new CertificateException("Pin verification failed");
        }
    }

    private boolean isPinningEnforced(List<X509Certificate> object) throws CertificateException {
        if (object.isEmpty()) {
            return false;
        }
        object = object.get(object.size() - 1);
        if ((object = this.mNetworkSecurityConfig.findTrustAnchorBySubjectAndPublicKey((X509Certificate)object)) != null) {
            return ((TrustAnchor)object).overridesPins ^ true;
        }
        throw new CertificateException("Trusted chain does not end in a TrustAnchor");
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        this.mDelegate.checkClientTrusted(arrx509Certificate, string2);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2, Socket socket) throws CertificateException {
        this.mDelegate.checkClientTrusted(arrx509Certificate, string2, socket);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2, SSLEngine sSLEngine) throws CertificateException {
        this.mDelegate.checkClientTrusted(arrx509Certificate, string2, sSLEngine);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] object, String string2, String string3) throws CertificateException {
        object = this.mDelegate.checkServerTrusted(object, string2, string3);
        this.checkPins((List<X509Certificate>)object);
        return object;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        this.checkServerTrusted(arrx509Certificate, string2, (String)null);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2, Socket socket) throws CertificateException {
        this.checkPins(this.mDelegate.getTrustedChainForServer(arrx509Certificate, string2, socket));
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2, SSLEngine sSLEngine) throws CertificateException {
        this.checkPins(this.mDelegate.getTrustedChainForServer(arrx509Certificate, string2, sSLEngine));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        Object object = this.mIssuersLock;
        synchronized (object) {
            if (this.mIssuers != null) return (X509Certificate[])this.mIssuers.clone();
            Object object2 = this.mNetworkSecurityConfig.getTrustAnchors();
            X509Certificate[] arrx509Certificate = new X509Certificate[object2.size()];
            int n = 0;
            object2 = object2.iterator();
            while (object2.hasNext()) {
                arrx509Certificate[n] = ((TrustAnchor)object2.next()).certificate;
                ++n;
            }
            this.mIssuers = arrx509Certificate;
            return (X509Certificate[])this.mIssuers.clone();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleTrustStorageUpdate() {
        Object object = this.mIssuersLock;
        synchronized (object) {
            this.mIssuers = null;
            this.mDelegate.handleTrustStorageUpdate();
            return;
        }
    }
}

