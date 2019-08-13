/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.zip.Checksum;
import sun.nio.ch.DirectBuffer;

public class Adler32
implements Checksum {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int adler = 1;

    private static native int update(int var0, int var1);

    private static native int updateByteBuffer(int var0, long var1, int var3, int var4);

    private static native int updateBytes(int var0, byte[] var1, int var2, int var3);

    @Override
    public long getValue() {
        return (long)this.adler & 0xFFFFFFFFL;
    }

    @Override
    public void reset() {
        this.adler = 1;
    }

    @Override
    public void update(int n) {
        this.adler = Adler32.update(this.adler, n);
    }

    public void update(ByteBuffer byteBuffer) {
        int n = byteBuffer.position();
        int n2 = byteBuffer.limit();
        int n3 = n2 - n;
        if (n3 <= 0) {
            return;
        }
        if (byteBuffer instanceof DirectBuffer) {
            this.adler = Adler32.updateByteBuffer(this.adler, ((DirectBuffer)((Object)byteBuffer)).address(), n, n3);
        } else if (byteBuffer.hasArray()) {
            this.adler = Adler32.updateBytes(this.adler, byteBuffer.array(), byteBuffer.arrayOffset() + n, n3);
        } else {
            byte[] arrby = new byte[n3];
            byteBuffer.get(arrby);
            this.adler = Adler32.updateBytes(this.adler, arrby, 0, arrby.length);
        }
        byteBuffer.position(n2);
    }

    public void update(byte[] arrby) {
        this.adler = Adler32.updateBytes(this.adler, arrby, 0, arrby.length);
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        if (arrby != null) {
            if (n >= 0 && n2 >= 0 && n <= arrby.length - n2) {
                this.adler = Adler32.updateBytes(this.adler, arrby, n, n2);
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }
}

