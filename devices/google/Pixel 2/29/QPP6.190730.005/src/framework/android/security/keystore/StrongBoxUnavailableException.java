/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.KeyStoreException;
import java.security.ProviderException;

public class StrongBoxUnavailableException
extends ProviderException {
    public StrongBoxUnavailableException() {
    }

    public StrongBoxUnavailableException(String string2) {
        super(string2, new KeyStoreException(-68, "No StrongBox available"));
    }

    public StrongBoxUnavailableException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public StrongBoxUnavailableException(Throwable throwable) {
        super(throwable);
    }
}

