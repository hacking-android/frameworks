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

public class DERIA5String
extends ASN1Primitive
implements ASN1String {
    private final byte[] string;

    public DERIA5String(String string) {
        this(string, false);
    }

    public DERIA5String(String string, boolean bl) {
        if (string != null) {
            if (bl && !DERIA5String.isIA5String(string)) {
                throw new IllegalArgumentException("string contains illegal characters");
            }
            this.string = Strings.toByteArray(string);
            return;
        }
        throw new NullPointerException("string cannot be null");
    }

    DERIA5String(byte[] arrby) {
        this.string = arrby;
    }

    public static DERIA5String getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERIA5String)) {
            return new DERIA5String(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return DERIA5String.getInstance(aSN1Primitive);
    }

    public static DERIA5String getInstance(Object object) {
        if (object != null && !(object instanceof DERIA5String)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERIA5String)DERIA5String.fromByteArray((byte[])object);
                    return object;
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("encoding error in getInstance: ");
                    ((StringBuilder)object).append(exception.toString());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DERIA5String)object;
    }

    public static boolean isIA5String(String string) {
        for (int i = string.length() - 1; i >= 0; --i) {
            if (string.charAt(i) <= '') continue;
            return false;
        }
        return true;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERIA5String)) {
            return false;
        }
        aSN1Primitive = (DERIA5String)aSN1Primitive;
        return Arrays.areEqual(this.string, ((DERIA5String)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(22, this.string);
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

