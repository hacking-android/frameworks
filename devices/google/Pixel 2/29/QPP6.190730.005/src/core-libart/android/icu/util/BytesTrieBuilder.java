/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.BytesTrie;
import android.icu.util.StringTrieBuilder;
import java.nio.ByteBuffer;

public final class BytesTrieBuilder
extends StringTrieBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private byte[] bytes;
    private int bytesLength;
    private final byte[] intBytes = new byte[5];

    private void buildBytes(StringTrieBuilder.Option option) {
        if (this.bytes == null) {
            this.bytes = new byte[1024];
        }
        this.buildImpl(option);
    }

    private void ensureCapacity(int n) {
        byte[] arrby = this.bytes;
        if (n > arrby.length) {
            int n2;
            int n3 = arrby.length;
            do {
                n3 = n2 = n3 * 2;
            } while (n2 <= n);
            arrby = new byte[n2];
            byte[] arrby2 = this.bytes;
            n3 = arrby2.length;
            n = this.bytesLength;
            System.arraycopy((byte[])arrby2, (int)(n3 - n), (byte[])arrby, (int)(arrby.length - n), (int)n);
            this.bytes = arrby;
        }
    }

    private int write(byte[] arrby, int n) {
        int n2 = this.bytesLength + n;
        this.ensureCapacity(n2);
        this.bytesLength = n2;
        byte[] arrby2 = this.bytes;
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)(arrby2.length - this.bytesLength), (int)n);
        return this.bytesLength;
    }

    public BytesTrieBuilder add(byte[] arrby, int n, int n2) {
        this.addImpl(new BytesAsCharSequence(arrby, n), n2);
        return this;
    }

    public BytesTrie build(StringTrieBuilder.Option arrby) {
        this.buildBytes((StringTrieBuilder.Option)arrby);
        arrby = this.bytes;
        return new BytesTrie(arrby, arrby.length - this.bytesLength);
    }

    public ByteBuffer buildByteBuffer(StringTrieBuilder.Option arrby) {
        this.buildBytes((StringTrieBuilder.Option)arrby);
        arrby = this.bytes;
        int n = arrby.length;
        int n2 = this.bytesLength;
        return ByteBuffer.wrap(arrby, n - n2, n2);
    }

    public BytesTrieBuilder clear() {
        this.clearImpl();
        this.bytes = null;
        this.bytesLength = 0;
        return this;
    }

    @Deprecated
    @Override
    protected int getMaxBranchLinearSubNodeLength() {
        return 5;
    }

    @Deprecated
    @Override
    protected int getMaxLinearMatchLength() {
        return 16;
    }

    @Deprecated
    @Override
    protected int getMinLinearMatch() {
        return 16;
    }

    @Deprecated
    @Override
    protected boolean matchNodesCanHaveValues() {
        return false;
    }

    @Deprecated
    @Override
    protected int write(int n) {
        int n2 = this.bytesLength + 1;
        this.ensureCapacity(n2);
        this.bytesLength = n2;
        byte[] arrby = this.bytes;
        int n3 = arrby.length;
        n2 = this.bytesLength;
        arrby[n3 - n2] = (byte)n;
        return n2;
    }

    @Deprecated
    @Override
    protected int write(int n, int n2) {
        int n3 = this.bytesLength + n2;
        this.ensureCapacity(n3);
        this.bytesLength = n3;
        n3 = this.bytes.length - this.bytesLength;
        while (n2 > 0) {
            this.bytes[n3] = (byte)this.strings.charAt(n);
            --n2;
            ++n3;
            ++n;
        }
        return this.bytesLength;
    }

    @Deprecated
    @Override
    protected int writeDeltaTo(int n) {
        byte[] arrby;
        int n2 = this.bytesLength - n;
        if (n2 <= 191) {
            return this.write(n2);
        }
        if (n2 <= 12287) {
            this.intBytes[0] = (byte)((n2 >> 8) + 192);
            n = 1;
        } else {
            if (n2 <= 917503) {
                this.intBytes[0] = (byte)((n2 >> 16) + 240);
                n = 2;
            } else {
                if (n2 <= 16777215) {
                    this.intBytes[0] = (byte)-2;
                    n = 3;
                } else {
                    arrby = this.intBytes;
                    arrby[0] = (byte)-1;
                    arrby[1] = (byte)(n2 >> 24);
                    n = 4;
                }
                this.intBytes[1] = (byte)(n2 >> 16);
            }
            this.intBytes[1] = (byte)(n2 >> 8);
        }
        arrby = this.intBytes;
        arrby[n] = (byte)n2;
        return this.write(arrby, n + 1);
    }

    @Deprecated
    @Override
    protected int writeValueAndFinal(int n, boolean bl) {
        byte[] arrby;
        if (n >= 0 && n <= 64) {
            return this.write(n + 16 << 1 | bl);
        }
        int n2 = 1;
        int n3 = 1;
        if (n >= 0 && n <= 16777215) {
            if (n <= 6911) {
                this.intBytes[0] = (byte)((n >> 8) + 81);
                n3 = n2;
            } else {
                if (n <= 1179647) {
                    this.intBytes[0] = (byte)((n >> 16) + 108);
                } else {
                    arrby = this.intBytes;
                    arrby[0] = (byte)126;
                    arrby[1] = (byte)(n >> 16);
                    n3 = 2;
                }
                this.intBytes[n3] = (byte)(n >> 8);
                ++n3;
            }
            arrby = this.intBytes;
            n2 = n3 + 1;
            arrby[n3] = (byte)n;
            n = n2;
        } else {
            arrby = this.intBytes;
            arrby[0] = (byte)127;
            arrby[1] = (byte)(n >> 24);
            arrby[2] = (byte)(n >> 16);
            arrby[3] = (byte)(n >> 8);
            arrby[4] = (byte)n;
            n = 5;
        }
        arrby = this.intBytes;
        arrby[0] = (byte)(arrby[0] << 1 | bl);
        return this.write(arrby, n);
    }

    @Deprecated
    @Override
    protected int writeValueAndType(boolean bl, int n, int n2) {
        n2 = this.write(n2);
        if (bl) {
            n2 = this.writeValueAndFinal(n, false);
        }
        return n2;
    }

    private static final class BytesAsCharSequence
    implements CharSequence {
        private int len;
        private byte[] s;

        public BytesAsCharSequence(byte[] arrby, int n) {
            this.s = arrby;
            this.len = n;
        }

        @Override
        public char charAt(int n) {
            return (char)(this.s[n] & 255);
        }

        @Override
        public int length() {
            return this.len;
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            return null;
        }
    }

}

