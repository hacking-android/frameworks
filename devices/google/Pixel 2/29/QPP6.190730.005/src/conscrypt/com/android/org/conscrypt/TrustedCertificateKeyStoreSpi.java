/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.TrustedCertificateStore;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStoreSpi;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Set;

public final class TrustedCertificateKeyStoreSpi
extends KeyStoreSpi {
    private final TrustedCertificateStore store = new TrustedCertificateStore();

    @Override
    public Enumeration<String> engineAliases() {
        return Collections.enumeration(this.store.aliases());
    }

    @Override
    public boolean engineContainsAlias(String string) {
        return this.store.containsAlias(string);
    }

    @Override
    public void engineDeleteEntry(String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Certificate engineGetCertificate(String string) {
        return this.store.getCertificate(string);
    }

    @Override
    public String engineGetCertificateAlias(Certificate certificate) {
        return this.store.getCertificateAlias(certificate);
    }

    @Override
    public Certificate[] engineGetCertificateChain(String string) {
        if (string != null) {
            return null;
        }
        throw new NullPointerException("alias == null");
    }

    @Override
    public Date engineGetCreationDate(String string) {
        return this.store.getCreationDate(string);
    }

    @Override
    public Key engineGetKey(String string, char[] arrc) {
        if (string != null) {
            return null;
        }
        throw new NullPointerException("alias == null");
    }

    @Override
    public boolean engineIsCertificateEntry(String string) {
        return this.engineContainsAlias(string);
    }

    @Override
    public boolean engineIsKeyEntry(String string) {
        if (string != null) {
            return false;
        }
        throw new NullPointerException("alias == null");
    }

    @Override
    public void engineLoad(InputStream inputStream, char[] arrc) {
        if (inputStream == null) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void engineSetCertificateEntry(String string, Certificate certificate) {
        if (string == null) {
            throw new NullPointerException("alias == null");
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void engineSetKeyEntry(String string, Key key, char[] arrc, Certificate[] arrcertificate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void engineSetKeyEntry(String string, byte[] arrby, Certificate[] arrcertificate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int engineSize() {
        return this.store.aliases().size();
    }

    @Override
    public void engineStore(OutputStream outputStream, char[] arrc) {
        throw new UnsupportedOperationException();
    }
}

