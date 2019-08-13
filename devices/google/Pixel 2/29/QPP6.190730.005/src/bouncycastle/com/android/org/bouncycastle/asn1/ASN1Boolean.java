/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.util.Arrays;
import java.io.IOException;

public class ASN1Boolean
extends ASN1Primitive {
    public static final ASN1Boolean FALSE;
    private static final byte[] FALSE_VALUE;
    public static final ASN1Boolean TRUE;
    private static final byte[] TRUE_VALUE;
    private final byte[] value;

    static {
        TRUE_VALUE = new byte[]{-1};
        FALSE_VALUE = new byte[]{0};
        FALSE = new ASN1Boolean(false);
        TRUE = new ASN1Boolean(true);
    }

    protected ASN1Boolean(boolean bl) {
        byte[] arrby = bl ? TRUE_VALUE : FALSE_VALUE;
        this.value = arrby;
    }

    ASN1Boolean(byte[] arrby) {
        if (arrby.length == 1) {
            this.value = arrby[0] == 0 ? FALSE_VALUE : ((arrby[0] & 255) == 255 ? TRUE_VALUE : Arrays.clone(arrby));
            return;
        }
        throw new IllegalArgumentException("byte value should have 1 byte in it");
    }

    static ASN1Boolean fromOctetString(byte[] arrby) {
        if (arrby.length == 1) {
            if (arrby[0] == 0) {
                return FALSE;
            }
            if ((arrby[0] & 255) == 255) {
                return TRUE;
            }
            return new ASN1Boolean(arrby);
        }
        throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
    }

    public static ASN1Boolean getInstance(int n) {
        ASN1Boolean aSN1Boolean = n != 0 ? TRUE : FALSE;
        return aSN1Boolean;
    }

    public static ASN1Boolean getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof ASN1Boolean)) {
            return ASN1Boolean.fromOctetString(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return ASN1Boolean.getInstance(aSN1Primitive);
    }

    public static ASN1Boolean getInstance(Object object) {
        if (object != null && !(object instanceof ASN1Boolean)) {
            if (object instanceof byte[]) {
                object = (byte[])object;
                try {
                    object = (ASN1Boolean)ASN1Boolean.fromByteArray((byte[])object);
                    return object;
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("failed to construct boolean from byte[]: ");
                    stringBuilder.append(iOException.getMessage());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (ASN1Boolean)object;
    }

    public static ASN1Boolean getInstance(boolean bl) {
        ASN1Boolean aSN1Boolean = bl ? TRUE : FALSE;
        return aSN1Boolean;
    }

    public static ASN1Boolean getInstance(byte[] object) {
        object = object[0] != 0 ? TRUE : FALSE;
        return object;
    }

    @Override
    protected boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        boolean bl = aSN1Primitive instanceof ASN1Boolean;
        boolean bl2 = false;
        if (bl) {
            if (this.value[0] == ((ASN1Boolean)aSN1Primitive).value[0]) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(1, this.value);
    }

    @Override
    int encodedLength() {
        return 3;
    }

    @Override
    public int hashCode() {
        return this.value[0];
    }

    @Override
    boolean isConstructed() {
        return false;
    }

    public boolean isTrue() {
        byte[] arrby = this.value;
        boolean bl = false;
        if (arrby[0] != 0) {
            bl = true;
        }
        return bl;
    }

    public String toString() {
        String string = this.value[0] != 0 ? "TRUE" : "FALSE";
        return string;
    }
}

