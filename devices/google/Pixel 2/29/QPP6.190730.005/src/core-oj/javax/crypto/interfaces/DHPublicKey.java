/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;
import javax.crypto.interfaces.DHKey;

public interface DHPublicKey
extends DHKey,
PublicKey {
    public static final long serialVersionUID = -6628103563352519193L;

    public BigInteger getY();
}

