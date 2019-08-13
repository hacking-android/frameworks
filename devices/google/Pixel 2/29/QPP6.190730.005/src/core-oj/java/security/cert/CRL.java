/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.Certificate;

public abstract class CRL {
    private String type;

    protected CRL(String string) {
        this.type = string;
    }

    public final String getType() {
        return this.type;
    }

    public abstract boolean isRevoked(Certificate var1);

    public abstract String toString();
}

