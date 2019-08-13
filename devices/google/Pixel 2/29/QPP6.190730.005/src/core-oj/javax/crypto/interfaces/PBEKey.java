/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.interfaces;

import javax.crypto.SecretKey;

public interface PBEKey
extends SecretKey {
    public static final long serialVersionUID = -1430015993304333921L;

    public int getIterationCount();

    public char[] getPassword();

    public byte[] getSalt();
}

