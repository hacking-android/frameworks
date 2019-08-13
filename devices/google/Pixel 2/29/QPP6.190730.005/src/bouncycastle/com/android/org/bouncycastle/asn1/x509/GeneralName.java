/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.util.IPAddress;
import java.io.IOException;
import java.util.StringTokenizer;

public class GeneralName
extends ASN1Object
implements ASN1Choice {
    public static final int dNSName = 2;
    public static final int directoryName = 4;
    public static final int ediPartyName = 5;
    public static final int iPAddress = 7;
    public static final int otherName = 0;
    public static final int registeredID = 8;
    public static final int rfc822Name = 1;
    public static final int uniformResourceIdentifier = 6;
    public static final int x400Address = 3;
    private ASN1Encodable obj;
    private int tag;

    public GeneralName(int n, ASN1Encodable aSN1Encodable) {
        this.obj = aSN1Encodable;
        this.tag = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public GeneralName(int n, String object) {
        this.tag = n;
        if (n != 1 && n != 2 && n != 6) {
            if (n == 8) {
                this.obj = new ASN1ObjectIdentifier((String)object);
                return;
            }
            if (n == 4) {
                this.obj = new X500Name((String)object);
                return;
            }
            if (n == 7) {
                if ((object = this.toGeneralNameEncoding((String)object)) == null) throw new IllegalArgumentException("IP Address is invalid");
                this.obj = new DEROctetString((byte[])object);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("can't process String for tag: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        this.obj = new DERIA5String((String)object);
    }

    public GeneralName(X500Name x500Name) {
        this.obj = x500Name;
        this.tag = 4;
    }

    public GeneralName(X509Name x509Name) {
        this.obj = X500Name.getInstance(x509Name);
        this.tag = 4;
    }

    private void copyInts(int[] arrn, byte[] arrby, int n) {
        for (int i = 0; i != arrn.length; ++i) {
            arrby[i * 2 + n] = (byte)(arrn[i] >> 8);
            arrby[i * 2 + 1 + n] = (byte)arrn[i];
        }
    }

    public static GeneralName getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return GeneralName.getInstance(ASN1TaggedObject.getInstance(aSN1TaggedObject, true));
    }

    public static GeneralName getInstance(Object object) {
        if (object != null && !(object instanceof GeneralName)) {
            Object object2;
            if (object instanceof ASN1TaggedObject) {
                object2 = (ASN1TaggedObject)object;
                int n = ((ASN1TaggedObject)object2).getTagNo();
                switch (n) {
                    default: {
                        break;
                    }
                    case 8: {
                        return new GeneralName(n, ASN1ObjectIdentifier.getInstance((ASN1TaggedObject)object2, false));
                    }
                    case 7: {
                        return new GeneralName(n, ASN1OctetString.getInstance((ASN1TaggedObject)object2, false));
                    }
                    case 6: {
                        return new GeneralName(n, DERIA5String.getInstance((ASN1TaggedObject)object2, false));
                    }
                    case 5: {
                        return new GeneralName(n, ASN1Sequence.getInstance((ASN1TaggedObject)object2, false));
                    }
                    case 4: {
                        return new GeneralName(n, X500Name.getInstance((ASN1TaggedObject)object2, true));
                    }
                    case 3: {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("unknown tag: ");
                        ((StringBuilder)object).append(n);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    case 2: {
                        return new GeneralName(n, DERIA5String.getInstance((ASN1TaggedObject)object2, false));
                    }
                    case 1: {
                        return new GeneralName(n, DERIA5String.getInstance((ASN1TaggedObject)object2, false));
                    }
                    case 0: {
                        return new GeneralName(n, ASN1Sequence.getInstance((ASN1TaggedObject)object2, false));
                    }
                }
            }
            if (object instanceof byte[]) {
                try {
                    object = GeneralName.getInstance(ASN1Primitive.fromByteArray((byte[])object));
                    return object;
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("unable to parse encoded general name");
                }
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("unknown object in getInstance: ");
            ((StringBuilder)object2).append(object.getClass().getName());
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        return (GeneralName)object;
    }

    private void parseIPv4(String object, byte[] arrby, int n) {
        object = new StringTokenizer((String)object, "./");
        int n2 = 0;
        while (((StringTokenizer)object).hasMoreTokens()) {
            arrby[n2 + n] = (byte)Integer.parseInt(((StringTokenizer)object).nextToken());
            ++n2;
        }
    }

    private void parseIPv4Mask(String string, byte[] arrby, int n) {
        int n2 = Integer.parseInt(string);
        for (int i = 0; i != n2; ++i) {
            int n3 = i / 8 + n;
            arrby[n3] = (byte)(arrby[n3] | 1 << 7 - i % 8);
        }
    }

    private int[] parseIPv6(String object) {
        int n;
        StringTokenizer stringTokenizer = new StringTokenizer((String)object, ":", true);
        int n2 = 0;
        int[] arrn = new int[8];
        if (((String)object).charAt(0) == ':' && ((String)object).charAt(1) == ':') {
            stringTokenizer.nextToken();
        }
        int n3 = -1;
        while (stringTokenizer.hasMoreTokens()) {
            object = stringTokenizer.nextToken();
            if (((String)object).equals(":")) {
                n3 = n2++;
                arrn[n2] = 0;
                continue;
            }
            if (((String)object).indexOf(46) < 0) {
                arrn[n2] = Integer.parseInt((String)object, 16);
                if (stringTokenizer.hasMoreTokens()) {
                    stringTokenizer.nextToken();
                }
                ++n2;
                continue;
            }
            object = new StringTokenizer((String)object, ".");
            n = n2 + 1;
            arrn[n2] = Integer.parseInt(((StringTokenizer)object).nextToken()) << 8 | Integer.parseInt(((StringTokenizer)object).nextToken());
            n2 = n + 1;
            arrn[n] = Integer.parseInt(((StringTokenizer)object).nextToken()) << 8 | Integer.parseInt(((StringTokenizer)object).nextToken());
        }
        if (n2 != arrn.length) {
            System.arraycopy(arrn, n3, arrn, arrn.length - (n2 - n3), n2 - n3);
            for (n = n3; n != arrn.length - (n2 - n3); ++n) {
                arrn[n] = 0;
            }
        }
        return arrn;
    }

    private int[] parseMask(String string) {
        int[] arrn = new int[8];
        int n = Integer.parseInt(string);
        for (int i = 0; i != n; ++i) {
            int n2 = i / 16;
            arrn[n2] = arrn[n2] | 1 << 15 - i % 16;
        }
        return arrn;
    }

    private byte[] toGeneralNameEncoding(String arrn) {
        if (!IPAddress.isValidIPv6WithNetmask((String)arrn) && !IPAddress.isValidIPv6((String)arrn)) {
            if (!IPAddress.isValidIPv4WithNetmask((String)arrn) && !IPAddress.isValidIPv4((String)arrn)) {
                return null;
            }
            int n = arrn.indexOf(47);
            if (n < 0) {
                byte[] arrby = new byte[4];
                this.parseIPv4((String)arrn, arrby, 0);
                return arrby;
            }
            byte[] arrby = new byte[8];
            this.parseIPv4(arrn.substring(0, n), arrby, 0);
            arrn = arrn.substring(n + 1);
            if (arrn.indexOf(46) > 0) {
                this.parseIPv4((String)arrn, arrby, 4);
            } else {
                this.parseIPv4Mask((String)arrn, arrby, 4);
            }
            return arrby;
        }
        int n = arrn.indexOf(47);
        if (n < 0) {
            byte[] arrby = new byte[16];
            this.copyInts(this.parseIPv6((String)arrn), arrby, 0);
            return arrby;
        }
        byte[] arrby = new byte[32];
        this.copyInts(this.parseIPv6(arrn.substring(0, n)), arrby, 0);
        arrn = arrn.substring(n + 1);
        arrn = arrn.indexOf(58) > 0 ? this.parseIPv6((String)arrn) : this.parseMask((String)arrn);
        this.copyInts(arrn, arrby, 16);
        return arrby;
    }

    public ASN1Encodable getName() {
        return this.obj;
    }

    public int getTagNo() {
        return this.tag;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        int n = this.tag;
        if (n == 4) {
            return new DERTaggedObject(true, n, this.obj);
        }
        return new DERTaggedObject(false, n, this.obj);
    }

    public String toString() {
        StringBuffer stringBuffer;
        block2 : {
            block0 : {
                block1 : {
                    stringBuffer = new StringBuffer();
                    stringBuffer.append(this.tag);
                    stringBuffer.append(": ");
                    int n = this.tag;
                    if (n == 1 || n == 2) break block0;
                    if (n == 4) break block1;
                    if (n == 6) break block0;
                    stringBuffer.append(this.obj.toString());
                    break block2;
                }
                stringBuffer.append(X500Name.getInstance(this.obj).toString());
                break block2;
            }
            stringBuffer.append(DERIA5String.getInstance(this.obj).getString());
        }
        return stringBuffer.toString();
    }
}

