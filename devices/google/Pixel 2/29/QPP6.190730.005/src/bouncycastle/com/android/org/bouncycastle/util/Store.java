/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.util.StoreException;
import java.util.Collection;

public interface Store<T> {
    public Collection<T> getMatches(Selector<T> var1) throws StoreException;
}

