/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1SequenceParser;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1SetParser;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.BERTaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DLSequence;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Iterable;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public abstract class ASN1Sequence
extends ASN1Primitive
implements Iterable<ASN1Encodable> {
    protected Vector seq = new Vector();

    protected ASN1Sequence() {
    }

    protected ASN1Sequence(ASN1Encodable aSN1Encodable) {
        this.seq.addElement(aSN1Encodable);
    }

    protected ASN1Sequence(ASN1EncodableVector aSN1EncodableVector) {
        for (int i = 0; i != aSN1EncodableVector.size(); ++i) {
            this.seq.addElement(aSN1EncodableVector.get(i));
        }
    }

    protected ASN1Sequence(ASN1Encodable[] arraSN1Encodable) {
        for (int i = 0; i != arraSN1Encodable.length; ++i) {
            this.seq.addElement(arraSN1Encodable[i]);
        }
    }

    public static ASN1Sequence getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        if (bl) {
            if (aSN1TaggedObject.isExplicit()) {
                return ASN1Sequence.getInstance(aSN1TaggedObject.getObject().toASN1Primitive());
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        }
        Object object = aSN1TaggedObject.getObject();
        if (aSN1TaggedObject.isExplicit()) {
            if (aSN1TaggedObject instanceof BERTaggedObject) {
                return new BERSequence((ASN1Encodable)object);
            }
            return new DLSequence((ASN1Encodable)object);
        }
        if (object instanceof ASN1Sequence) {
            return (ASN1Sequence)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unknown object in getInstance: ");
        ((StringBuilder)object).append(aSN1TaggedObject.getClass().getName());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static ASN1Sequence getInstance(Object object) {
        if (object != null && !(object instanceof ASN1Sequence)) {
            Object object2;
            if (object instanceof ASN1SequenceParser) {
                return ASN1Sequence.getInstance(((ASN1SequenceParser)object).toASN1Primitive());
            }
            if (object instanceof byte[]) {
                try {
                    object = ASN1Sequence.getInstance(ASN1Sequence.fromByteArray((byte[])object));
                    return object;
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("failed to construct sequence from byte[]: ");
                    stringBuilder.append(iOException.getMessage());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            if (object instanceof ASN1Encodable && (object2 = ((ASN1Encodable)object).toASN1Primitive()) instanceof ASN1Sequence) {
                return (ASN1Sequence)object2;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("unknown object in getInstance: ");
            ((StringBuilder)object2).append(object.getClass().getName());
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        return (ASN1Sequence)object;
    }

    private ASN1Encodable getNext(Enumeration enumeration) {
        return (ASN1Encodable)enumeration.nextElement();
    }

    @Override
    boolean asn1Equals(ASN1Primitive object) {
        if (!(object instanceof ASN1Sequence)) {
            return false;
        }
        Object object2 = (ASN1Sequence)object;
        if (this.size() != ((ASN1Sequence)object2).size()) {
            return false;
        }
        object = this.getObjects();
        object2 = ((ASN1Sequence)object2).getObjects();
        while (object.hasMoreElements()) {
            ASN1Encodable aSN1Encodable = this.getNext((Enumeration)object);
            ASN1Encodable aSN1Encodable2 = this.getNext((Enumeration)object2);
            if ((aSN1Encodable = aSN1Encodable.toASN1Primitive()) == (aSN1Encodable2 = aSN1Encodable2.toASN1Primitive()) || ((ASN1Primitive)aSN1Encodable).equals(aSN1Encodable2)) continue;
            return false;
        }
        return true;
    }

    @Override
    abstract void encode(ASN1OutputStream var1) throws IOException;

    public ASN1Encodable getObjectAt(int n) {
        return (ASN1Encodable)this.seq.elementAt(n);
    }

    public Enumeration getObjects() {
        return this.seq.elements();
    }

    @Override
    public int hashCode() {
        Enumeration enumeration = this.getObjects();
        int n = this.size();
        while (enumeration.hasMoreElements()) {
            n = n * 17 ^ this.getNext(enumeration).hashCode();
        }
        return n;
    }

    @Override
    boolean isConstructed() {
        return true;
    }

    @Override
    public Iterator<ASN1Encodable> iterator() {
        return new Arrays.Iterator<ASN1Encodable>(this.toArray());
    }

    public ASN1SequenceParser parser() {
        return new ASN1SequenceParser(){
            private int index;
            private final int max;
            {
                this.max = ASN1Sequence.this.size();
            }

            @Override
            public ASN1Primitive getLoadedObject() {
                return this;
            }

            @Override
            public ASN1Encodable readObject() throws IOException {
                int n = this.index;
                if (n == this.max) {
                    return null;
                }
                ASN1Encodable aSN1Encodable = ASN1Sequence.this;
                this.index = n + 1;
                if ((aSN1Encodable = ((ASN1Sequence)aSN1Encodable).getObjectAt(n)) instanceof ASN1Sequence) {
                    return ((ASN1Sequence)aSN1Encodable).parser();
                }
                if (aSN1Encodable instanceof ASN1Set) {
                    return ((ASN1Set)aSN1Encodable).parser();
                }
                return aSN1Encodable;
            }

            @Override
            public ASN1Primitive toASN1Primitive() {
                return this;
            }
        };
    }

    public int size() {
        return this.seq.size();
    }

    public ASN1Encodable[] toArray() {
        ASN1Encodable[] arraSN1Encodable = new ASN1Encodable[this.size()];
        for (int i = 0; i != this.size(); ++i) {
            arraSN1Encodable[i] = this.getObjectAt(i);
        }
        return arraSN1Encodable;
    }

    @Override
    ASN1Primitive toDERObject() {
        DERSequence dERSequence = new DERSequence();
        dERSequence.seq = this.seq;
        return dERSequence;
    }

    @Override
    ASN1Primitive toDLObject() {
        DLSequence dLSequence = new DLSequence();
        dLSequence.seq = this.seq;
        return dLSequence;
    }

    public String toString() {
        return this.seq.toString();
    }

}

