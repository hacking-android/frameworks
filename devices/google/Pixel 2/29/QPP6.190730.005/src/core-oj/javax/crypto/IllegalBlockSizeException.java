/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.GeneralSecurityException;

public class IllegalBlockSizeException
extends GeneralSecurityException {
    private static final long serialVersionUID = -1965144811953540392L;

    public IllegalBlockSizeException() {
    }

    public IllegalBlockSizeException(String string) {
        super(string);
    }
}

