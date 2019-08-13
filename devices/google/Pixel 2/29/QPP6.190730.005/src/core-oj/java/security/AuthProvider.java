/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.Provider;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

public abstract class AuthProvider
extends Provider {
    protected AuthProvider(String string, double d, String string2) {
        super("", 0.0, "");
    }

    public abstract void login(Subject var1, CallbackHandler var2) throws LoginException;

    public abstract void logout() throws LoginException;

    public abstract void setCallbackHandler(CallbackHandler var1);
}

