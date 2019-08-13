/*
 * Decompiled with CFR 0.145.
 */
package java.security.interfaces;

import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.interfaces.DSAParams;

public interface DSAKeyPairGenerator {
    public void initialize(int var1, boolean var2, SecureRandom var3) throws InvalidParameterException;

    public void initialize(DSAParams var1, SecureRandom var2) throws InvalidParameterException;
}

