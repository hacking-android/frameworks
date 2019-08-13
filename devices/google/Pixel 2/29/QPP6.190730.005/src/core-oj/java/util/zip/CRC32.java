/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.zip.Checksum;
import sun.nio.ch.DirectBuffer;

public class CRC32
implements Checksum {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int crc;

    private static native int update(int var0, int var1);

    private static native int updateByteBuffer(int var0, long var1, int var3, int var4);

    private static native int updateBytes(int var0, byte[] var1, int var2, int var3);

    @Override
    public long getValue() {
        return (long)this.crc & 0xFFFFFFFFL;
    }

    @Override
    public void reset() {
        this.crc = 0;
    }

    @Override
    public void update(int n) {
        this.crc = CRC32.update(this.crc, n);
    }

    public void update(ByteBuffer byteBuffer) {
        int n = byteBuffer.position();
        int n2 = byteBuffer.limit();
        int n3 = n2 - n;
        if (n3 <= 0) {
            return;
        }
        if (byteBuffer instanceof DirectBuffer) {
            this.crc = CRC32.updateByteBuffer(this.crc, ((DirectBuffer)((Object)byteBuffer)).address(), n, n3);
        } else if (byteBuffer.hasArray()) {
            this.crc = CRC32.updateBytes(this.crc, byteBuffer.array(), byteBuffer.arrayOffset() + n, n3);
        } else {
            byte[] arrby = new byte[n3];
            byteBuffer.get(arrby);
            this.crc = CRC32.updateBytes(this.crc, arrby, 0, arrby.length);
        }
        byteBuffer.position(n2);
    }

    public void update(byte[] arrby) {
        this.crc = CRC32.updateBytes(this.crc, arrby, 0, arrby.length);
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        if (arrby != null) {
            if (n >= 0 && n2 >= 0 && n <= arrby.length - n2) {
                this.crc = CRC32.updateBytes(this.crc, arrby, n, n2);
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }
}

