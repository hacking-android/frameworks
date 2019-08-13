/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import javax.crypto.CryptoPermission;

final class CryptoAllPermission
extends CryptoPermission {
    static final String ALG_NAME = null;
    static final CryptoAllPermission INSTANCE = null;

    private CryptoAllPermission() {
        super("");
    }
}

