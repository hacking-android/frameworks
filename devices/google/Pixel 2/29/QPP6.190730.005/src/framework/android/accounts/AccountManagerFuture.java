/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public interface AccountManagerFuture<V> {
    public boolean cancel(boolean var1);

    public V getResult() throws OperationCanceledException, IOException, AuthenticatorException;

    public V getResult(long var1, TimeUnit var3) throws OperationCanceledException, IOException, AuthenticatorException;

    public boolean isCancelled();

    public boolean isDone();
}

