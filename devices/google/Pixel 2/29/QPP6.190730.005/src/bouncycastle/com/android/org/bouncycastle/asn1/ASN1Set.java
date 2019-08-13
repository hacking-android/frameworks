/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1SequenceParser;
import com.android.org.bouncycastle.asn1.ASN1SetParser;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.BERSet;
import com.android.org.bouncycastle.asn1.BERTaggedObject;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.DLSet;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Iterable;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public abstract class ASN1Set
extends ASN1Primitive
implements Iterable<ASN1Encodable> {
    private boolean isSorted = false;
    private Vector set = new Vector();

    protected ASN1Set() {
    }

    protected ASN1Set(ASN1Encodable aSN1Encodable) {
        this.set.addElement(aSN1Encodable);
    }

    protected ASN1Set(ASN1EncodableVector aSN1EncodableVector, boolean bl) {
        for (int i = 0; i != aSN1EncodableVector.size(); ++i) {
            this.set.addElement(aSN1EncodableVector.get(i));
        }
        if (bl) {
            this.sort();
        }
    }

    protected ASN1Set(ASN1Encodable[] arraSN1Encodable, boolean bl) {
        for (int i = 0; i != arraSN1Encodable.length; ++i) {
            this.set.addElement(arraSN1Encodable[i]);
        }
        if (bl) {
            this.sort();
        }
    }

    private byte[] getDEREncoded(ASN1Encodable arrby) {
        try {
            arrby = arrby.toASN1Primitive().getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    public static ASN1Set getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        if (bl) {
            if (aSN1TaggedObject.isExplicit()) {
                return (ASN1Set)aSN1TaggedObject.getObject();
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        }
        Object object = aSN1TaggedObject.getObject();
        if (aSN1TaggedObject.isExplicit()) {
            if (aSN1TaggedObject instanceof BERTaggedObject) {
                return new BERSet((ASN1Encodable)object);
            }
            return new DLSet((ASN1Encodable)object);
        }
        if (object instanceof ASN1Set) {
            return (ASN1Set)object;
        }
        if (object instanceof ASN1Sequence) {
            object = (ASN1Sequence)object;
            if (aSN1TaggedObject instanceof BERTaggedObject) {
                return new BERSet(((ASN1Sequence)object).toArray());
            }
            return new DLSet(((ASN1Sequence)object).toArray());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unknown object in getInstance: ");
        ((StringBuilder)object).append(aSN1TaggedObject.getClass().getName());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static ASN1Set getInstance(Object object) {
        if (object != null && !(object instanceof ASN1Set)) {
            Object object2;
            if (object instanceof ASN1SetParser) {
                return ASN1Set.getInstance(((ASN1SetParser)object).toASN1Primitive());
            }
            if (object instanceof byte[]) {
                try {
                    object = ASN1Set.getInstance(ASN1Primitive.fromByteArray((byte[])object));
                    return object;
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("failed to construct set from byte[]: ");
                    stringBuilder.append(iOException.getMessage());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            if (object instanceof ASN1Encodable && (object2 = ((ASN1Encodable)object).toASN1Primitive()) instanceof ASN1Set) {
                return (ASN1Set)object2;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("unknown object in getInstance: ");
            ((StringBuilder)object2).append(object.getClass().getName());
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        return (ASN1Set)object;
    }

    private ASN1Encodable getNext(Enumeration object) {
        if ((object = (ASN1Encodable)object.nextElement()) == null) {
            return DERNull.INSTANCE;
        }
        return object;
    }

    private boolean lessThanOrEqual(byte[] arrby, byte[] arrby2) {
        boolean bl;
        boolean bl2;
        int n = Math.min(arrby.length, arrby2.length);
        int n2 = 0;
        do {
            bl2 = false;
            bl = false;
            if (n2 == n) break;
            if (arrby[n2] != arrby2[n2]) {
                if ((arrby[n2] & 255) < (arrby2[n2] & 255)) {
                    bl = true;
                }
                return bl;
            }
            ++n2;
        } while (true);
        bl = bl2;
        if (n == arrby.length) {
            bl = true;
        }
        return bl;
    }

    @Override
    boolean asn1Equals(ASN1Primitive object) {
        if (!(object instanceof ASN1Set)) {
            return false;
        }
        Object object2 = (ASN1Set)object;
        if (this.size() != ((ASN1Set)object2).size()) {
            return false;
        }
        object = this.getObjects();
        object2 = ((ASN1Set)object2).getObjects();
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
        return (ASN1Encodable)this.set.elementAt(n);
    }

    public Enumeration getObjects() {
        return this.set.elements();
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

    public ASN1SetParser parser() {
        return new ASN1SetParser(){
            private int index;
            private final int max;
            {
                this.max = ASN1Set.this.size();
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
                ASN1Encodable aSN1Encodable = ASN1Set.this;
                this.index = n + 1;
                if ((aSN1Encodable = ((ASN1Set)aSN1Encodable).getObjectAt(n)) instanceof ASN1Sequence) {
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
        return this.set.size();
    }

    protected void sort() {
        if (!this.isSorted) {
            this.isSorted = true;
            if (this.set.size() > 1) {
                boolean bl = true;
                int n = this.set.size() - 1;
                while (bl) {
                    int n2 = 0;
                    byte[] arrby = this.getDEREncoded((ASN1Encodable)this.set.elementAt(0));
                    bl = false;
                    for (int i = 0; i != n; ++i) {
                        Object object = this.getDEREncoded((ASN1Encodable)this.set.elementAt(i + 1));
                        if (this.lessThanOrEqual(arrby, (byte[])object)) {
                            arrby = object;
                            continue;
                        }
                        E e = this.set.elementAt(i);
                        object = this.set;
                        ((Vector)object).setElementAt(((Vector)object).elementAt(i + 1), i);
                        this.set.setElementAt(e, i + 1);
                        bl = true;
                        n2 = i;
                    }
                    n = n2;
                }
            }
        }
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
        if (this.isSorted) {
            DERSet dERSet = new DERSet();
            dERSet.set = this.set;
            return dERSet;
        }
        Vector<E> vector = new Vector<E>();
        for (int i = 0; i != this.set.size(); ++i) {
            vector.addElement(this.set.elementAt(i));
        }
        DERSet dERSet = new DERSet();
        dERSet.set = vector;
        dERSet.sort();
        return dERSet;
    }

    @Override
    ASN1Primitive toDLObject() {
        DLSet dLSet = new DLSet();
        dLSet.set = this.set;
        return dLSet;
    }

    public String toString() {
        return this.set.toString();
    }

}

