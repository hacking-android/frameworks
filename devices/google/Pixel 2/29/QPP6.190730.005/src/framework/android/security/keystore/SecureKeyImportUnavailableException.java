/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.KeyStoreException;
import java.security.ProviderException;

public class SecureKeyImportUnavailableException
extends ProviderException {
    public SecureKeyImportUnavailableException() {
    }

    public SecureKeyImportUnavailableException(String string2) {
        super(string2, new KeyStoreException(-68, "Secure Key Import not available"));
    }

    public SecureKeyImportUnavailableException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public SecureKeyImportUnavailableException(Throwable throwable) {
        super(throwable);
    }
}

