/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.AccountsException;

public class AuthenticatorException
extends AccountsException {
    public AuthenticatorException() {
    }

    public AuthenticatorException(String string2) {
        super(string2);
    }

    public AuthenticatorException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public AuthenticatorException(Throwable throwable) {
        super(throwable);
    }
}

