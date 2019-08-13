/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.x509.X509StoreParameters;
import java.util.Collection;

public abstract class X509StoreSpi {
    public abstract Collection engineGetMatches(Selector var1);

    public abstract void engineInit(X509StoreParameters var1);
}

