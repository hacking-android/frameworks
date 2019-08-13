/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertPath;

public interface CertPathBuilderResult
extends Cloneable {
    public Object clone();

    public CertPath getCertPath();
}

