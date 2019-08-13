/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AttestedKeyPair {
    private final Certificate[] mAttestationRecord;
    private final KeyPair mKeyPair;

    public AttestedKeyPair(KeyPair keyPair, Certificate[] arrcertificate) {
        this.mKeyPair = keyPair;
        this.mAttestationRecord = arrcertificate;
    }

    public List<Certificate> getAttestationRecord() {
        Certificate[] arrcertificate = this.mAttestationRecord;
        if (arrcertificate == null) {
            return new ArrayList<Certificate>();
        }
        return Arrays.asList(arrcertificate);
    }

    public KeyPair getKeyPair() {
        return this.mKeyPair;
    }
}

