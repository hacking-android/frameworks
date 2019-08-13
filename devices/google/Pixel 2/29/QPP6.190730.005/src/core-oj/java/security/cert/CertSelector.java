/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.Certificate;

public interface CertSelector
extends Cloneable {
    public Object clone();

    public boolean match(Certificate var1);
}

