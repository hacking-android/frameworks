/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce;

import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.util.Store;
import com.android.org.bouncycastle.util.StoreException;
import java.security.cert.Certificate;
import java.util.Collection;

public interface PKIXCertStore<T extends Certificate>
extends Store<T> {
    @Override
    public Collection<T> getMatches(Selector<T> var1) throws StoreException;
}

