/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.KeyManagerImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactorySpi;
import javax.net.ssl.ManagerFactoryParameters;

public class KeyManagerFactoryImpl
extends KeyManagerFactorySpi {
    private KeyStore keyStore;
    private char[] pwd;

    @Override
    protected KeyManager[] engineGetKeyManagers() {
        KeyStore keyStore = this.keyStore;
        if (keyStore != null) {
            return new KeyManager[]{new KeyManagerImpl(keyStore, this.pwd)};
        }
        throw new IllegalStateException("KeyManagerFactory is not initialized");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void engineInit(KeyStore object, char[] object2) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (object != null) {
            this.keyStore = object;
            this.pwd = object2 != null ? (char[])object2.clone() : EmptyArray.CHAR;
            return;
        }
        this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        object = System.getProperty("javax.net.ssl.keyStore");
        if (object != null && !((String)object).equalsIgnoreCase("NONE") && !((String)object).isEmpty()) {
            object2 = System.getProperty("javax.net.ssl.keyStorePassword");
            this.pwd = object2 == null ? EmptyArray.CHAR : ((String)object2).toCharArray();
            try {
                KeyStore keyStore = this.keyStore;
                File file = new File((String)object);
                object2 = new FileInputStream(file);
                keyStore.load((InputStream)object2, this.pwd);
                return;
            }
            catch (CertificateException certificateException) {
                throw new KeyStoreException(certificateException);
            }
            catch (IOException iOException) {
                throw new KeyStoreException(iOException);
            }
            catch (FileNotFoundException fileNotFoundException) {
                throw new KeyStoreException(fileNotFoundException);
            }
        }
        try {
            this.keyStore.load(null, null);
            return;
        }
        catch (CertificateException certificateException) {
            throw new KeyStoreException(certificateException);
        }
        catch (IOException iOException) {
            throw new KeyStoreException(iOException);
        }
    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("ManagerFactoryParameters not supported");
    }
}

