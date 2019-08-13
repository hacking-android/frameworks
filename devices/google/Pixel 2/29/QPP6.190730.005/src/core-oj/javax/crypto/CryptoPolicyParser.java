/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.IOException;
import java.io.Reader;
import java.security.GeneralSecurityException;
import javax.crypto.CryptoPermission;

final class CryptoPolicyParser {
    CryptoPolicyParser() {
    }

    CryptoPermission[] getPermissions() {
        return null;
    }

    void read(Reader reader) throws ParsingException, IOException {
    }

    static final class ParsingException
    extends GeneralSecurityException {
        ParsingException(int n, String string) {
            super("");
        }

        ParsingException(int n, String string, String string2) {
            super("");
        }

        ParsingException(String string) {
            super("");
        }
    }

}

