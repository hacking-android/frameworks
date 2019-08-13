/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io.pem;

import com.android.org.bouncycastle.util.io.pem.PemGenerationException;
import com.android.org.bouncycastle.util.io.pem.PemObject;

public interface PemObjectGenerator {
    public PemObject generate() throws PemGenerationException;
}

