/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.BEROctetString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;

public class BERConstructedOctetString
extends BEROctetString {
    private static final int MAX_LENGTH = 1000;
    private Vector octs;

    public BERConstructedOctetString(ASN1Encodable aSN1Encodable) {
        this(aSN1Encodable.toASN1Primitive());
    }

    public BERConstructedOctetString(ASN1Primitive aSN1Primitive) {
        super(BERConstructedOctetString.toByteArray(aSN1Primitive));
    }

    public BERConstructedOctetString(Vector vector) {
        super(BERConstructedOctetString.toBytes(vector));
        this.octs = vector;
    }

    public BERConstructedOctetString(byte[] arrby) {
        super(arrby);
    }

    public static BEROctetString fromSequence(ASN1Sequence object) {
        Vector vector = new Vector();
        object = ((ASN1Sequence)object).getObjects();
        while (object.hasMoreElements()) {
            vector.addElement(object.nextElement());
        }
        return new BERConstructedOctetString(vector);
    }

    private Vector generateOcts() {
        Vector<DEROctetString> vector = new Vector<DEROctetString>();
        for (int i = 0; i < this.string.length; i += 1000) {
            int n = i + 1000 > this.string.length ? this.string.length : i + 1000;
            byte[] arrby = new byte[n - i];
            System.arraycopy((byte[])this.string, (int)i, (byte[])arrby, (int)0, (int)arrby.length);
            vector.addElement(new DEROctetString(arrby));
        }
        return vector;
    }

    private static byte[] toByteArray(ASN1Primitive arrby) {
        try {
            arrby = arrby.getEncoded();
            return arrby;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("Unable to encode object");
        }
    }

    private static byte[] toBytes(Vector vector) {
        Object object = new ByteArrayOutputStream();
        for (int i = 0; i != vector.size(); ++i) {
            try {
                ((OutputStream)object).write(((DEROctetString)vector.elementAt(i)).getOctets());
                continue;
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("exception converting octets ");
                ((StringBuilder)object).append(iOException.toString());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            catch (ClassCastException classCastException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(vector.elementAt(i).getClass().getName());
                stringBuilder.append(" found in input should only contain DEROctetString");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        return ((ByteArrayOutputStream)object).toByteArray();
    }

    @Override
    public Enumeration getObjects() {
        Vector vector = this.octs;
        if (vector == null) {
            return this.generateOcts().elements();
        }
        return vector.elements();
    }

    @Override
    public byte[] getOctets() {
        return this.string;
    }
}

