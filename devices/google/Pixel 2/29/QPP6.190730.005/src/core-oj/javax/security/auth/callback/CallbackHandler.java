/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth.callback;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

public interface CallbackHandler {
    public void handle(Callback[] var1) throws IOException, UnsupportedCallbackException;
}

