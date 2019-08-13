/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io.pem;

import com.android.org.bouncycastle.util.io.pem.PemObject;
import java.io.IOException;

public interface PemObjectParser {
    public Object parseObject(PemObject var1) throws IOException;
}

