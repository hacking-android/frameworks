/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.BERGenerator;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DEROutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BEROctetStringGenerator
extends BERGenerator {
    public BEROctetStringGenerator(OutputStream outputStream) throws IOException {
        super(outputStream);
        this.writeBERHeader(36);
    }

    public BEROctetStringGenerator(OutputStream outputStream, int n, boolean bl) throws IOException {
        super(outputStream, n, bl);
        this.writeBERHeader(36);
    }

    public OutputStream getOctetOutputStream() {
        return this.getOctetOutputStream(new byte[1000]);
    }

    public OutputStream getOctetOutputStream(byte[] arrby) {
        return new BufferedBEROctetStream(arrby);
    }

    private class BufferedBEROctetStream
    extends OutputStream {
        private byte[] _buf;
        private DEROutputStream _derOut;
        private int _off;

        BufferedBEROctetStream(byte[] arrby) {
            this._buf = arrby;
            this._off = 0;
            this._derOut = new DEROutputStream(BEROctetStringGenerator.this._out);
        }

        @Override
        public void close() throws IOException {
            int n = this._off;
            if (n != 0) {
                byte[] arrby = new byte[n];
                System.arraycopy((byte[])this._buf, (int)0, (byte[])arrby, (int)0, (int)n);
                DEROctetString.encode(this._derOut, arrby);
            }
            BEROctetStringGenerator.this.writeBEREnd();
        }

        @Override
        public void write(int n) throws IOException {
            byte[] arrby = this._buf;
            int n2 = this._off;
            this._off = n2 + 1;
            arrby[n2] = (byte)n;
            if (this._off == arrby.length) {
                DEROctetString.encode(this._derOut, arrby);
                this._off = 0;
            }
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            while (n2 > 0) {
                int n3 = Math.min(n2, this._buf.length - this._off);
                System.arraycopy((byte[])arrby, (int)n, (byte[])this._buf, (int)this._off, (int)n3);
                this._off += n3;
                int n4 = this._off;
                byte[] arrby2 = this._buf;
                if (n4 < arrby2.length) break;
                DEROctetString.encode(this._derOut, arrby2);
                this._off = 0;
                n += n3;
                n2 -= n3;
            }
        }
    }

}

