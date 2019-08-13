/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AllocatedBuffer;
import java.nio.ByteBuffer;

public abstract class BufferAllocator {
    private static final BufferAllocator UNPOOLED = new BufferAllocator(){

        @Override
        public AllocatedBuffer allocateDirectBuffer(int n) {
            return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(n));
        }
    };

    public static BufferAllocator unpooled() {
        return UNPOOLED;
    }

    public abstract AllocatedBuffer allocateDirectBuffer(int var1);

}

