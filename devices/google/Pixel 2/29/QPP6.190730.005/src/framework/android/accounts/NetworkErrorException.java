/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.AccountsException;

public class NetworkErrorException
extends AccountsException {
    public NetworkErrorException() {
    }

    public NetworkErrorException(String string2) {
        super(string2);
    }

    public NetworkErrorException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public NetworkErrorException(Throwable throwable) {
        super(throwable);
    }
}

