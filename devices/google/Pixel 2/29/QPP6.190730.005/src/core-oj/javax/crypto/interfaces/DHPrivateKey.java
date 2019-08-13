/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import javax.crypto.interfaces.DHKey;

public interface DHPrivateKey
extends DHKey,
PrivateKey {
    public static final long serialVersionUID = 2211791113380396553L;

    public BigInteger getX();
}

