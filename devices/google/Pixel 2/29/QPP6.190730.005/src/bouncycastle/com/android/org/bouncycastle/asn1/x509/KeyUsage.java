/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;

public class KeyUsage
extends ASN1Object {
    public static final int cRLSign = 2;
    public static final int dataEncipherment = 16;
    public static final int decipherOnly = 32768;
    public static final int digitalSignature = 128;
    public static final int encipherOnly = 1;
    public static final int keyAgreement = 8;
    public static final int keyCertSign = 4;
    public static final int keyEncipherment = 32;
    public static final int nonRepudiation = 64;
    private DERBitString bitString;

    public KeyUsage(int n) {
        this.bitString = new DERBitString(n);
    }

    private KeyUsage(DERBitString dERBitString) {
        this.bitString = dERBitString;
    }

    public static KeyUsage fromExtensions(Extensions extensions) {
        return KeyUsage.getInstance(extensions.getExtensionParsedValue(Extension.keyUsage));
    }

    public static KeyUsage getInstance(Object object) {
        if (object instanceof KeyUsage) {
            return (KeyUsage)object;
        }
        if (object != null) {
            return new KeyUsage(DERBitString.getInstance(object));
        }
        return null;
    }

    public byte[] getBytes() {
        return this.bitString.getBytes();
    }

    public int getPadBits() {
        return this.bitString.getPadBits();
    }

    public boolean hasUsages(int n) {
        boolean bl = (this.bitString.intValue() & n) == n;
        return bl;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.bitString;
    }

    public String toString() {
        byte[] arrby = this.bitString.getBytes();
        if (arrby.length == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("KeyUsage: 0x");
            stringBuilder.append(Integer.toHexString(arrby[0] & 255));
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("KeyUsage: 0x");
        byte by = arrby[1];
        stringBuilder.append(Integer.toHexString(arrby[0] & 255 | (by & 255) << 8));
        return stringBuilder.toString();
    }
}

