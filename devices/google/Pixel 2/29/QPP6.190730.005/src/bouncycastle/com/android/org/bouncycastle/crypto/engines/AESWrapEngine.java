/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.engines.AESEngine;
import com.android.org.bouncycastle.crypto.engines.RFC3394WrapEngine;

public class AESWrapEngine
extends RFC3394WrapEngine {
    public AESWrapEngine() {
        super(new AESEngine());
    }

    public AESWrapEngine(boolean bl) {
        super(new AESEngine(), bl);
    }
}

