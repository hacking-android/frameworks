/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.ObjectPool;

public class StringBufferPool {
    private static ObjectPool m_stringBufPool = new ObjectPool(FastStringBuffer.class);

    public static void free(FastStringBuffer fastStringBuffer) {
        synchronized (StringBufferPool.class) {
            fastStringBuffer.setLength(0);
            m_stringBufPool.freeInstance(fastStringBuffer);
            return;
        }
    }

    public static FastStringBuffer get() {
        synchronized (StringBufferPool.class) {
            FastStringBuffer fastStringBuffer = (FastStringBuffer)m_stringBufPool.getInstance();
            return fastStringBuffer;
        }
    }
}

