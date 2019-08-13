/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;

public class DERPrintableString
extends ASN1Primitive
implements ASN1String {
    private final byte[] string;

    public DERPrintableString(String string) {
        this(string, false);
    }

    public DERPrintableString(String string, boolean bl) {
        if (bl && !DERPrintableString.isPrintableString(string)) {
            throw new IllegalArgumentException("string contains illegal characters");
        }
        this.string = Strings.toByteArray(string);
    }

    DERPrintableString(byte[] arrby) {
        this.string = arrby;
    }

    public static DERPrintableString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERPrintableString)) {
            return new DERPrintableString(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
        }
        return DERPrintableString.getInstance(aSN1Primitive);
    }

    public static DERPrintableString getInstance(Object object) {
        if (object != null && !(object instanceof DERPrintableString)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERPrintableString)DERPrintableString.fromByteArray((byte[])object);
                    return object;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("encoding error in getInstance: ");
                    stringBuilder.append(exception.toString());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DERPrintableString)object;
    }

    public static boolean isPrintableString(String string) {
        for (int i = string.length() - 1; i >= 0; --i) {
            char c = string.charAt(i);
            if (c > '') {
                return false;
            }
            if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9' || c == ' ' || c == ':' || c == '=' || c == '?') continue;
            switch (c) {
                default: {
                    switch (c) {
                        default: {
                            return false;
                        }
                        case '+': 
                        case ',': 
                        case '-': 
                        case '.': 
                        case '/': 
                    }
                }
                case '\'': 
                case '(': 
                case ')': 
            }
        }
        return true;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERPrintableString)) {
            return false;
        }
        aSN1Primitive = (DERPrintableString)aSN1Primitive;
        return Arrays.areEqual(this.string, ((DERPrintableString)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(19, this.string);
    }

    @Override
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.string.length) + 1 + this.string.length;
    }

    public byte[] getOctets() {
        return Arrays.clone(this.string);
    }

    @Override
    public String getString() {
        return Strings.fromByteArray(this.string);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.string);
    }

    @Override
    boolean isConstructed() {
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

