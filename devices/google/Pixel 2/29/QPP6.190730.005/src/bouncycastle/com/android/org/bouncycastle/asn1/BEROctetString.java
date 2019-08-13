/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DEROctetString;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;

public class BEROctetString
extends ASN1OctetString {
    private static final int DEFAULT_LENGTH = 1000;
    private final int chunkSize;
    private final ASN1OctetString[] octs;

    public BEROctetString(byte[] arrby) {
        this(arrby, 1000);
    }

    public BEROctetString(byte[] arrby, int n) {
        this(arrby, null, n);
    }

    private BEROctetString(byte[] arrby, ASN1OctetString[] arraSN1OctetString, int n) {
        super(arrby);
        this.octs = arraSN1OctetString;
        this.chunkSize = n;
    }

    public BEROctetString(ASN1OctetString[] arraSN1OctetString) {
        this(arraSN1OctetString, 1000);
    }

    public BEROctetString(ASN1OctetString[] arraSN1OctetString, int n) {
        this(BEROctetString.toBytes(arraSN1OctetString), arraSN1OctetString, n);
    }

    static BEROctetString fromSequence(ASN1Sequence object) {
        ASN1OctetString[] arraSN1OctetString = new ASN1OctetString[((ASN1Sequence)object).size()];
        object = ((ASN1Sequence)object).getObjects();
        int n = 0;
        while (object.hasMoreElements()) {
            arraSN1OctetString[n] = (ASN1OctetString)object.nextElement();
            ++n;
        }
        return new BEROctetString(arraSN1OctetString);
    }

    private Vector generateOcts() {
        Vector<DEROctetString> vector = new Vector<DEROctetString>();
        for (int i = 0; i < this.string.length; i += this.chunkSize) {
            int n = this.chunkSize + i > this.string.length ? this.string.length : this.chunkSize + i;
            byte[] arrby = new byte[n - i];
            System.arraycopy((byte[])this.string, (int)i, (byte[])arrby, (int)0, (int)arrby.length);
            vector.addElement(new DEROctetString(arrby));
        }
        return vector;
    }

    private static byte[] toBytes(ASN1OctetString[] arraSN1OctetString) {
        Object object = new ByteArrayOutputStream();
        for (int i = 0; i != arraSN1OctetString.length; ++i) {
            try {
                ((OutputStream)object).write(((DEROctetString)arraSN1OctetString[i]).getOctets());
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
                stringBuilder.append(arraSN1OctetString[i].getClass().getName());
                stringBuilder.append(" found in input should only contain DEROctetString");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        return ((ByteArrayOutputStream)object).toByteArray();
    }

    @Override
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.write(36);
        aSN1OutputStream.write(128);
        Enumeration enumeration = this.getObjects();
        while (enumeration.hasMoreElements()) {
            aSN1OutputStream.writeObject((ASN1Encodable)enumeration.nextElement());
        }
        aSN1OutputStream.write(0);
        aSN1OutputStream.write(0);
    }

    @Override
    int encodedLength() throws IOException {
        int n = 0;
        Enumeration enumeration = this.getObjects();
        while (enumeration.hasMoreElements()) {
            n += ((ASN1Encodable)enumeration.nextElement()).toASN1Primitive().encodedLength();
        }
        return n + 2 + 2;
    }

    public Enumeration getObjects() {
        if (this.octs == null) {
            return this.generateOcts().elements();
        }
        return new Enumeration(){
            int counter = 0;

            @Override
            public boolean hasMoreElements() {
                boolean bl = this.counter < BEROctetString.this.octs.length;
                return bl;
            }

            public Object nextElement() {
                ASN1OctetString[] arraSN1OctetString = BEROctetString.this.octs;
                int n = this.counter;
                this.counter = n + 1;
                return arraSN1OctetString[n];
            }
        };
    }

    @Override
    public byte[] getOctets() {
        return this.string;
    }

    @Override
    boolean isConstructed() {
        return true;
    }

}

