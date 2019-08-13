/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.OpenSSLContextImpl;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public final class DefaultSSLContextImpl
extends OpenSSLContextImpl {
    private static KeyManager[] KEY_MANAGERS;
    private static TrustManager[] TRUST_MANAGERS;

    @Override
    public void engineInit(KeyManager[] arrkeyManager, TrustManager[] arrtrustManager, SecureRandom secureRandom) throws KeyManagementException {
        throw new KeyManagementException("Do not init() the default SSLContext ");
    }

    KeyManager[] getKeyManagers() throws GeneralSecurityException, IOException {
        BufferedInputStream bufferedInputStream;
        FileInputStream fileInputStream;
        Object object = KEY_MANAGERS;
        if (object != null) {
            return object;
        }
        String string = System.getProperty("javax.net.ssl.keyStore");
        char[] arrc = null;
        if (string == null) {
            return null;
        }
        object = System.getProperty("javax.net.ssl.keyStorePassword");
        if (object != null) {
            arrc = ((String)object).toCharArray();
        }
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        BufferedInputStream bufferedInputStream2 = null;
        object = bufferedInputStream2;
        object = bufferedInputStream2;
        object = bufferedInputStream2;
        try {
            fileInputStream = new FileInputStream(string);
            object = bufferedInputStream2;
        }
        catch (Throwable throwable) {
            if (object != null) {
                ((InputStream)object).close();
            }
            throw throwable;
        }
        bufferedInputStream2 = bufferedInputStream = new BufferedInputStream(fileInputStream);
        object = bufferedInputStream2;
        keyStore.load(bufferedInputStream2, arrc);
        ((InputStream)bufferedInputStream2).close();
        object = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        ((KeyManagerFactory)object).init(keyStore, arrc);
        KEY_MANAGERS = ((KeyManagerFactory)object).getKeyManagers();
        return KEY_MANAGERS;
    }

    TrustManager[] getTrustManagers() throws GeneralSecurityException, IOException {
        BufferedInputStream bufferedInputStream;
        FileInputStream fileInputStream;
        Object object = TRUST_MANAGERS;
        if (object != null) {
            return object;
        }
        String string = System.getProperty("javax.net.ssl.trustStore");
        char[] arrc = null;
        if (string == null) {
            return null;
        }
        object = System.getProperty("javax.net.ssl.trustStorePassword");
        if (object != null) {
            arrc = ((String)object).toCharArray();
        }
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        BufferedInputStream bufferedInputStream2 = null;
        object = bufferedInputStream2;
        object = bufferedInputStream2;
        object = bufferedInputStream2;
        try {
            fileInputStream = new FileInputStream(string);
            object = bufferedInputStream2;
        }
        catch (Throwable throwable) {
            if (object != null) {
                ((InputStream)object).close();
            }
            throw throwable;
        }
        bufferedInputStream2 = bufferedInputStream = new BufferedInputStream(fileInputStream);
        object = bufferedInputStream2;
        keyStore.load(bufferedInputStream2, arrc);
        ((InputStream)bufferedInputStream2).close();
        object = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        ((TrustManagerFactory)object).init(keyStore);
        TRUST_MANAGERS = ((TrustManagerFactory)object).getTrustManagers();
        return TRUST_MANAGERS;
    }
}

