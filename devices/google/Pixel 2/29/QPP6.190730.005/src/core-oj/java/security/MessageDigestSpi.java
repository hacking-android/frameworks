/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.DigestException;
import sun.security.jca.JCAUtil;

public abstract class MessageDigestSpi {
    private byte[] tempArray;

    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }

    protected int engineDigest(byte[] arrby, int n, int n2) throws DigestException {
        byte[] arrby2 = this.engineDigest();
        if (n2 >= arrby2.length) {
            if (arrby.length - n >= arrby2.length) {
                System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
                return arrby2.length;
            }
            throw new DigestException("insufficient space in the output buffer to store the digest");
        }
        throw new DigestException("partial digests not returned");
    }

    protected abstract byte[] engineDigest();

    protected int engineGetDigestLength() {
        return 0;
    }

    protected abstract void engineReset();

    protected abstract void engineUpdate(byte var1);

    protected void engineUpdate(ByteBuffer byteBuffer) {
        block6 : {
            int n;
            int n2;
            block8 : {
                int n3;
                block7 : {
                    block5 : {
                        if (!byteBuffer.hasRemaining()) {
                            return;
                        }
                        if (!byteBuffer.hasArray()) break block5;
                        byte[] arrby = byteBuffer.array();
                        int n4 = byteBuffer.arrayOffset();
                        int n5 = byteBuffer.position();
                        int n6 = byteBuffer.limit();
                        this.engineUpdate(arrby, n4 + n5, n6 - n5);
                        byteBuffer.position(n6);
                        break block6;
                    }
                    n2 = byteBuffer.remaining();
                    n3 = JCAUtil.getTempArraySize(n2);
                    byte[] arrby = this.tempArray;
                    if (arrby == null) break block7;
                    n = n2;
                    if (n3 <= arrby.length) break block8;
                }
                this.tempArray = new byte[n3];
                n = n2;
            }
            while (n > 0) {
                n2 = Math.min(n, this.tempArray.length);
                byteBuffer.get(this.tempArray, 0, n2);
                this.engineUpdate(this.tempArray, 0, n2);
                n -= n2;
            }
        }
    }

    protected abstract void engineUpdate(byte[] var1, int var2, int var3);
}

