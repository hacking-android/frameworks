/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.NullCipherSpi;

public class NullCipher
extends Cipher {
    public NullCipher() {
        super(new NullCipherSpi(), null, null);
    }
}

