/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;

public interface Key
extends Serializable {
    public static final long serialVersionUID = 6603384152749567654L;

    public String getAlgorithm();

    public byte[] getEncoded();

    public String getFormat();
}

