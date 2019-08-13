/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;

public interface X509KeyManager
extends KeyManager {
    public String chooseClientAlias(String[] var1, Principal[] var2, Socket var3);

    public String chooseServerAlias(String var1, Principal[] var2, Socket var3);

    public X509Certificate[] getCertificateChain(String var1);

    public String[] getClientAliases(String var1, Principal[] var2);

    public PrivateKey getPrivateKey(String var1);

    public String[] getServerAliases(String var1, Principal[] var2);
}

