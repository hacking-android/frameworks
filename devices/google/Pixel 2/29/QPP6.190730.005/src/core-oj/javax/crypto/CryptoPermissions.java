/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import javax.crypto.CryptoPolicyParser;

final class CryptoPermissions
extends PermissionCollection
implements Serializable {
    CryptoPermissions() {
    }

    @Override
    public void add(Permission permission) {
    }

    public Enumeration elements() {
        return null;
    }

    CryptoPermissions getMinimum(CryptoPermissions cryptoPermissions) {
        return null;
    }

    PermissionCollection getPermissionCollection(String string) {
        return null;
    }

    @Override
    public boolean implies(Permission permission) {
        return true;
    }

    boolean isEmpty() {
        return true;
    }

    void load(InputStream inputStream) throws IOException, CryptoPolicyParser.ParsingException {
    }
}

