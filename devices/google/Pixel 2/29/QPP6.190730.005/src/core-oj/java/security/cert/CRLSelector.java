/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CRL;

public interface CRLSelector
extends Cloneable {
    public Object clone();

    public boolean match(CRL var1);
}

