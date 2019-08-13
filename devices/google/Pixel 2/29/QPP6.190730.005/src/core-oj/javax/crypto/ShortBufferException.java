/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.GeneralSecurityException;

public class ShortBufferException
extends GeneralSecurityException {
    private static final long serialVersionUID = 8427718640832943747L;

    public ShortBufferException() {
    }

    public ShortBufferException(String string) {
        super(string);
    }
}

