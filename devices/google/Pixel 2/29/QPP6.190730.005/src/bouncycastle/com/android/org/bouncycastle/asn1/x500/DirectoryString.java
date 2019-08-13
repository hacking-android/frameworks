/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBMPString;
import com.android.org.bouncycastle.asn1.DERPrintableString;
import com.android.org.bouncycastle.asn1.DERT61String;
import com.android.org.bouncycastle.asn1.DERUTF8String;
import com.android.org.bouncycastle.asn1.DERUniversalString;

public class DirectoryString
extends ASN1Object
implements ASN1Choice,
ASN1String {
    private ASN1String string;

    private DirectoryString(DERBMPString dERBMPString) {
        this.string = dERBMPString;
    }

    private DirectoryString(DERPrintableString dERPrintableString) {
        this.string = dERPrintableString;
    }

    private DirectoryString(DERT61String dERT61String) {
        this.string = dERT61String;
    }

    private DirectoryString(DERUTF8String dERUTF8String) {
        this.string = dERUTF8String;
    }

    private DirectoryString(DERUniversalString dERUniversalString) {
        this.string = dERUniversalString;
    }

    public DirectoryString(String string) {
        this.string = new DERUTF8String(string);
    }

    public static DirectoryString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        if (bl) {
            return DirectoryString.getInstance(aSN1TaggedObject.getObject());
        }
        throw new IllegalArgumentException("choice item must be explicitly tagged");
    }

    public static DirectoryString getInstance(Object object) {
        if (object != null && !(object instanceof DirectoryString)) {
            if (object instanceof DERT61String) {
                return new DirectoryString((DERT61String)object);
            }
            if (object instanceof DERPrintableString) {
                return new DirectoryString((DERPrintableString)object);
            }
            if (object instanceof DERUniversalString) {
                return new DirectoryString((DERUniversalString)object);
            }
            if (object instanceof DERUTF8String) {
                return new DirectoryString((DERUTF8String)object);
            }
            if (object instanceof DERBMPString) {
                return new DirectoryString((DERBMPString)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DirectoryString)object;
    }

    @Override
    public String getString() {
        return this.string.getString();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return ((ASN1Encodable)((Object)this.string)).toASN1Primitive();
    }

    public String toString() {
        return this.string.getString();
    }
}

