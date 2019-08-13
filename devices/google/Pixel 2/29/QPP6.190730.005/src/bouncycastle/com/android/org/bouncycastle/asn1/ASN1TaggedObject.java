/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Exception;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OctetStringParser;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1SequenceParser;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1SetParser;
import com.android.org.bouncycastle.asn1.ASN1TaggedObjectParser;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.DLTaggedObject;
import java.io.IOException;

public abstract class ASN1TaggedObject
extends ASN1Primitive
implements ASN1TaggedObjectParser {
    boolean empty = false;
    boolean explicit = true;
    ASN1Encodable obj = null;
    int tagNo;

    public ASN1TaggedObject(boolean bl, int n, ASN1Encodable aSN1Encodable) {
        this.explicit = aSN1Encodable instanceof ASN1Choice ? true : bl;
        this.tagNo = n;
        if (this.explicit) {
            this.obj = aSN1Encodable;
        } else {
            if (aSN1Encodable.toASN1Primitive() instanceof ASN1Set) {
                // empty if block
            }
            this.obj = aSN1Encodable;
        }
    }

    public static ASN1TaggedObject getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        if (bl) {
            return (ASN1TaggedObject)aSN1TaggedObject.getObject();
        }
        throw new IllegalArgumentException("implicitly tagged tagged object");
    }

    public static ASN1TaggedObject getInstance(Object object) {
        if (object != null && !(object instanceof ASN1TaggedObject)) {
            if (object instanceof byte[]) {
                try {
                    object = ASN1TaggedObject.getInstance(ASN1TaggedObject.fromByteArray((byte[])object));
                    return object;
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("failed to construct tagged object from byte[]: ");
                    stringBuilder.append(iOException.getMessage());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (ASN1TaggedObject)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1TaggedObject)) {
            return false;
        }
        aSN1Primitive = (ASN1TaggedObject)aSN1Primitive;
        if (this.tagNo == ((ASN1TaggedObject)aSN1Primitive).tagNo && this.empty == ((ASN1TaggedObject)aSN1Primitive).empty && this.explicit == ((ASN1TaggedObject)aSN1Primitive).explicit) {
            ASN1Encodable aSN1Encodable = this.obj;
            return !(aSN1Encodable == null ? ((ASN1TaggedObject)aSN1Primitive).obj != null : !aSN1Encodable.toASN1Primitive().equals(((ASN1TaggedObject)aSN1Primitive).obj.toASN1Primitive()));
        }
        return false;
    }

    @Override
    abstract void encode(ASN1OutputStream var1) throws IOException;

    @Override
    public ASN1Primitive getLoadedObject() {
        return this.toASN1Primitive();
    }

    public ASN1Primitive getObject() {
        ASN1Encodable aSN1Encodable = this.obj;
        if (aSN1Encodable != null) {
            return aSN1Encodable.toASN1Primitive();
        }
        return null;
    }

    @Override
    public ASN1Encodable getObjectParser(int n, boolean bl) throws IOException {
        if (n != 4) {
            if (n != 16) {
                if (n != 17) {
                    if (bl) {
                        return this.getObject();
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("implicit tagging not implemented for tag: ");
                    stringBuilder.append(n);
                    throw new ASN1Exception(stringBuilder.toString());
                }
                return ASN1Set.getInstance(this, bl).parser();
            }
            return ASN1Sequence.getInstance(this, bl).parser();
        }
        return ASN1OctetString.getInstance(this, bl).parser();
    }

    @Override
    public int getTagNo() {
        return this.tagNo;
    }

    @Override
    public int hashCode() {
        int n = this.tagNo;
        ASN1Encodable aSN1Encodable = this.obj;
        int n2 = n;
        if (aSN1Encodable != null) {
            n2 = n ^ aSN1Encodable.hashCode();
        }
        return n2;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isExplicit() {
        return this.explicit;
    }

    @Override
    ASN1Primitive toDERObject() {
        return new DERTaggedObject(this.explicit, this.tagNo, this.obj);
    }

    @Override
    ASN1Primitive toDLObject() {
        return new DLTaggedObject(this.explicit, this.tagNo, this.obj);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.tagNo);
        stringBuilder.append("]");
        stringBuilder.append(this.obj);
        return stringBuilder.toString();
    }
}

