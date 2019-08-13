/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.util;

import com.android.org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Provider;
import java.security.Security;

public class BCJcaJceHelper
extends ProviderJcaJceHelper {
    private static volatile Provider bcProvider;

    public BCJcaJceHelper() {
        super(BCJcaJceHelper.getBouncyCastleProvider());
    }

    private static Provider getBouncyCastleProvider() {
        synchronized (BCJcaJceHelper.class) {
            if (Security.getProvider("BC") != null) {
                Provider provider = Security.getProvider("BC");
                return provider;
            }
            if (bcProvider != null) {
                Provider provider = bcProvider;
                return provider;
            }
            Provider provider = new BouncyCastleProvider();
            bcProvider = provider;
            provider = bcProvider;
            return provider;
        }
    }
}

