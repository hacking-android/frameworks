/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CryptoException;

public class InvalidCipherTextException
extends CryptoException {
    public InvalidCipherTextException() {
    }

    public InvalidCipherTextException(String string) {
        super(string);
    }

    public InvalidCipherTextException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

