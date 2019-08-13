/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROutputStream;
import com.android.org.bouncycastle.asn1.StreamUtil;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;

public class DEROctetString
extends ASN1OctetString {
    public DEROctetString(ASN1Encodable aSN1Encodable) throws IOException {
        super(aSN1Encodable.toASN1Primitive().getEncoded("DER"));
    }

    @UnsupportedAppUsage
    public DEROctetString(byte[] arrby) {
        super(arrby);
    }

    static void encode(DEROutputStream dEROutputStream, byte[] arrby) throws IOException {
        dEROutputStream.writeEncoded(4, arrby);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(4, this.string);
    }

    @Override
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.string.length) + 1 + this.string.length;
    }

    @Override
    boolean isConstructed() {
        return false;
    }
}

