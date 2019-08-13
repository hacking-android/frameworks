/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.nio.Buffer;

public final class NIOAccess {
    @UnsupportedAppUsage
    static Object getBaseArray(Buffer object) {
        object = ((Buffer)object).hasArray() ? ((Buffer)object).array() : null;
        return object;
    }

    @UnsupportedAppUsage
    static int getBaseArrayOffset(Buffer buffer) {
        int n = buffer.hasArray() ? buffer.arrayOffset() + buffer.position << buffer._elementSizeShift : 0;
        return n;
    }

    @UnsupportedAppUsage
    public static long getBasePointer(Buffer buffer) {
        long l = buffer.address;
        if (l == 0L) {
            return 0L;
        }
        return (long)(buffer.position << buffer._elementSizeShift) + l;
    }
}

