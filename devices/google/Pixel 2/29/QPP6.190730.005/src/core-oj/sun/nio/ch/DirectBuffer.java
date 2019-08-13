/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import sun.misc.Cleaner;

public interface DirectBuffer {
    public long address();

    public Object attachment();

    public Cleaner cleaner();
}

