/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROutputStream;
import com.android.org.bouncycastle.asn1.DLOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ASN1OutputStream {
    private OutputStream os;

    public ASN1OutputStream(OutputStream outputStream) {
        this.os = outputStream;
    }

    public void close() throws IOException {
        this.os.close();
    }

    public void flush() throws IOException {
        this.os.flush();
    }

    ASN1OutputStream getDERSubStream() {
        return new DEROutputStream(this.os);
    }

    ASN1OutputStream getDLSubStream() {
        return new DLOutputStream(this.os);
    }

    void write(int n) throws IOException {
        this.os.write(n);
    }

    void write(byte[] arrby) throws IOException {
        this.os.write(arrby);
    }

    void write(byte[] arrby, int n, int n2) throws IOException {
        this.os.write(arrby, n, n2);
    }

    void writeEncoded(int n, int n2, byte[] arrby) throws IOException {
        this.writeTag(n, n2);
        this.writeLength(arrby.length);
        this.write(arrby);
    }

    void writeEncoded(int n, byte[] arrby) throws IOException {
        this.write(n);
        this.writeLength(arrby.length);
        this.write(arrby);
    }

    void writeImplicitObject(ASN1Primitive aSN1Primitive) throws IOException {
        if (aSN1Primitive != null) {
            aSN1Primitive.encode(new ImplicitOutputStream(this.os));
            return;
        }
        throw new IOException("null object detected");
    }

    void writeLength(int n) throws IOException {
        if (n > 127) {
            int n2 = 1;
            int n3 = n;
            do {
                int n4;
                n3 = n4 = n3 >>> 8;
                if (n4 == 0) break;
                ++n2;
            } while (true);
            this.write((byte)(n2 | 128));
            for (n3 = (n2 - 1) * 8; n3 >= 0; n3 -= 8) {
                this.write((byte)(n >> n3));
            }
        } else {
            this.write((byte)n);
        }
    }

    protected void writeNull() throws IOException {
        this.os.write(5);
        this.os.write(0);
    }

    public void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }

    void writeTag(int n, int n2) throws IOException {
        if (n2 < 31) {
            this.write(n | n2);
        } else {
            this.write(n | 31);
            if (n2 < 128) {
                this.write(n2);
            } else {
                byte[] arrby = new byte[5];
                n = arrby.length - 1;
                arrby[n] = (byte)(n2 & 127);
                do {
                    arrby[--n] = (byte)((n2 >>= 7) & 127 | 128);
                } while (n2 > 127);
                this.write(arrby, n, arrby.length - n);
            }
        }
    }

    private class ImplicitOutputStream
    extends ASN1OutputStream {
        private boolean first;

        public ImplicitOutputStream(OutputStream outputStream) {
            super(outputStream);
            this.first = true;
        }

        @Override
        public void write(int n) throws IOException {
            if (this.first) {
                this.first = false;
            } else {
                super.write(n);
            }
        }
    }

}

