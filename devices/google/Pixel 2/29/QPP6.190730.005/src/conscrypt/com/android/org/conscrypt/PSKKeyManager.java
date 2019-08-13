/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.net.Socket;
import javax.crypto.SecretKey;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;

@Deprecated
public interface PSKKeyManager
extends KeyManager {
    public static final int MAX_IDENTITY_HINT_LENGTH_BYTES = 128;
    public static final int MAX_IDENTITY_LENGTH_BYTES = 128;
    public static final int MAX_KEY_LENGTH_BYTES = 256;

    public String chooseClientKeyIdentity(String var1, Socket var2);

    public String chooseClientKeyIdentity(String var1, SSLEngine var2);

    public String chooseServerKeyIdentityHint(Socket var1);

    public String chooseServerKeyIdentityHint(SSLEngine var1);

    public SecretKey getKey(String var1, String var2, Socket var3);

    public SecretKey getKey(String var1, String var2, SSLEngine var3);
}

