/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.nio.DirectByteBuffer$MemoryRef
 */
package java.nio;

import android.system.OsConstants;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.nio.DirectByteBuffer;
import java.nio.channels.FileChannel;
import sun.misc.Cleaner;
import sun.nio.ch.FileChannelImpl;

public final class NioUtils {
    private NioUtils() {
    }

    @UnsupportedAppUsage
    public static void freeDirectBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return;
        }
        byteBuffer = (DirectByteBuffer)byteBuffer;
        if (((DirectByteBuffer)byteBuffer).cleaner != null) {
            ((DirectByteBuffer)byteBuffer).cleaner.clean();
        }
        ((DirectByteBuffer)byteBuffer).memoryRef.free();
    }

    public static FileDescriptor getFD(FileChannel fileChannel) {
        return ((FileChannelImpl)fileChannel).fd;
    }

    public static FileChannel newFileChannel(Closeable closeable, FileDescriptor fileDescriptor, int n) {
        boolean bl = ((OsConstants.O_RDONLY | OsConstants.O_RDWR | OsConstants.O_SYNC) & n) != 0;
        boolean bl2 = ((OsConstants.O_WRONLY | OsConstants.O_RDWR | OsConstants.O_SYNC) & n) != 0;
        boolean bl3 = (OsConstants.O_APPEND & n) != 0;
        return FileChannelImpl.open(fileDescriptor, null, bl, bl2, bl3, closeable);
    }

    @UnsupportedAppUsage
    public static byte[] unsafeArray(ByteBuffer byteBuffer) {
        return byteBuffer.array();
    }

    @UnsupportedAppUsage
    public static int unsafeArrayOffset(ByteBuffer byteBuffer) {
        return byteBuffer.arrayOffset();
    }
}

