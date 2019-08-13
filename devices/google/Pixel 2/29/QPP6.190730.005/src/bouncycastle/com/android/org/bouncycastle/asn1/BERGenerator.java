/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Generator;
import java.io.IOException;
import java.io.OutputStream;

public class BERGenerator
extends ASN1Generator {
    private boolean _isExplicit;
    private int _tagNo;
    private boolean _tagged = false;

    protected BERGenerator(OutputStream outputStream) {
        super(outputStream);
    }

    protected BERGenerator(OutputStream outputStream, int n, boolean bl) {
        super(outputStream);
        this._tagged = true;
        this._isExplicit = bl;
        this._tagNo = n;
    }

    private void writeHdr(int n) throws IOException {
        this._out.write(n);
        this._out.write(128);
    }

    @Override
    public OutputStream getRawOutputStream() {
        return this._out;
    }

    protected void writeBEREnd() throws IOException {
        this._out.write(0);
        this._out.write(0);
        if (this._tagged && this._isExplicit) {
            this._out.write(0);
            this._out.write(0);
        }
    }

    protected void writeBERHeader(int n) throws IOException {
        if (this._tagged) {
            int n2 = this._tagNo | 128;
            if (this._isExplicit) {
                this.writeHdr(n2 | 32);
                this.writeHdr(n);
            } else if ((n & 32) != 0) {
                this.writeHdr(n2 | 32);
            } else {
                this.writeHdr(n2);
            }
        } else {
            this.writeHdr(n);
        }
    }
}

