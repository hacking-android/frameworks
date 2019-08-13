/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.AccountsException;

public class OperationCanceledException
extends AccountsException {
    public OperationCanceledException() {
    }

    public OperationCanceledException(String string2) {
        super(string2);
    }

    public OperationCanceledException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public OperationCanceledException(Throwable throwable) {
        super(throwable);
    }
}

