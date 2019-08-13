/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.LazyConstructionEnumeration;
import com.android.org.bouncycastle.asn1.StreamUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

class LazyEncodedSequence
extends ASN1Sequence {
    private byte[] encoded;

    LazyEncodedSequence(byte[] arrby) throws IOException {
        this.encoded = arrby;
    }

    private void parse() {
        LazyConstructionEnumeration lazyConstructionEnumeration = new LazyConstructionEnumeration(this.encoded);
        while (lazyConstructionEnumeration.hasMoreElements()) {
            this.seq.addElement(lazyConstructionEnumeration.nextElement());
        }
        this.encoded = null;
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] arrby = this.encoded;
        if (arrby != null) {
            aSN1OutputStream.writeEncoded(48, arrby);
        } else {
            super.toDLObject().encode(aSN1OutputStream);
        }
    }

    @Override
    int encodedLength() throws IOException {
        byte[] arrby = this.encoded;
        if (arrby != null) {
            return StreamUtil.calculateBodyLength(arrby.length) + 1 + this.encoded.length;
        }
        return super.toDLObject().encodedLength();
    }

    @Override
    public ASN1Encodable getObjectAt(int n) {
        synchronized (this) {
            if (this.encoded != null) {
                this.parse();
            }
            ASN1Encodable aSN1Encodable = super.getObjectAt(n);
            return aSN1Encodable;
        }
    }

    @Override
    public Enumeration getObjects() {
        synchronized (this) {
            block4 : {
                if (this.encoded != null) break block4;
                Enumeration enumeration = super.getObjects();
                return enumeration;
            }
            LazyConstructionEnumeration lazyConstructionEnumeration = new LazyConstructionEnumeration(this.encoded);
            return lazyConstructionEnumeration;
        }
    }

    @Override
    public int size() {
        synchronized (this) {
            if (this.encoded != null) {
                this.parse();
            }
            int n = super.size();
            return n;
        }
    }

    @Override
    ASN1Primitive toDERObject() {
        if (this.encoded != null) {
            this.parse();
        }
        return super.toDERObject();
    }

    @Override
    ASN1Primitive toDLObject() {
        if (this.encoded != null) {
            this.parse();
        }
        return super.toDLObject();
    }
}

