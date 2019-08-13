/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.Principal;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509KeyManager;

public abstract class X509ExtendedKeyManager
implements X509KeyManager {
    protected X509ExtendedKeyManager() {
    }

    public String chooseEngineClientAlias(String[] arrstring, Principal[] arrprincipal, SSLEngine sSLEngine) {
        return null;
    }

    public String chooseEngineServerAlias(String string, Principal[] arrprincipal, SSLEngine sSLEngine) {
        return null;
    }
}

