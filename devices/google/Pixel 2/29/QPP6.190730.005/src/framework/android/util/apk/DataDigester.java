/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import java.nio.ByteBuffer;
import java.security.DigestException;

interface DataDigester {
    public void consume(ByteBuffer var1) throws DigestException;
}

