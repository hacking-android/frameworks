/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AlgorithmParameters;
import java.security.CryptoPrimitive;
import java.security.Key;
import java.util.Set;

public interface AlgorithmConstraints {
    public boolean permits(Set<CryptoPrimitive> var1, String var2, AlgorithmParameters var3);

    public boolean permits(Set<CryptoPrimitive> var1, String var2, Key var3, AlgorithmParameters var4);

    public boolean permits(Set<CryptoPrimitive> var1, Key var2);
}

