/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.OIDTokenizer;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ASN1ObjectIdentifier
extends ASN1Primitive {
    private static final long LONG_LIMIT = 72057594037927808L;
    private static final ConcurrentMap<OidHandle, ASN1ObjectIdentifier> pool = new ConcurrentHashMap<OidHandle, ASN1ObjectIdentifier>();
    private byte[] body;
    private final String identifier;

    ASN1ObjectIdentifier(ASN1ObjectIdentifier object, String string) {
        if (ASN1ObjectIdentifier.isValidBranchID(string, 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((ASN1ObjectIdentifier)object).getId());
            stringBuilder.append(".");
            stringBuilder.append(string);
            this.identifier = stringBuilder.toString();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("string ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" not a valid OID branch");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public ASN1ObjectIdentifier(String string) {
        if (string != null) {
            if (ASN1ObjectIdentifier.isValidIdentifier(string)) {
                this.identifier = string.intern();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("string ");
            stringBuilder.append(string);
            stringBuilder.append(" not an OID");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        throw new IllegalArgumentException("'identifier' cannot be null");
    }

    ASN1ObjectIdentifier(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer();
        long l = 0L;
        BigInteger bigInteger = null;
        int n = 1;
        for (int i = 0; i != arrby.length; ++i) {
            int n2 = arrby[i] & 255;
            if (l <= 72057594037927808L) {
                long l2 = l + (long)(n2 & 127);
                if ((n2 & 128) == 0) {
                    l = l2;
                    n2 = n;
                    if (n != 0) {
                        if (l2 < 40L) {
                            stringBuffer.append('0');
                            l = l2;
                        } else if (l2 < 80L) {
                            stringBuffer.append('1');
                            l = l2 - 40L;
                        } else {
                            stringBuffer.append('2');
                            l = l2 - 80L;
                        }
                        n2 = 0;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(l);
                    l = 0L;
                    n = n2;
                    continue;
                }
                l = l2 << 7;
                continue;
            }
            BigInteger bigInteger2 = bigInteger;
            if (bigInteger == null) {
                bigInteger2 = BigInteger.valueOf(l);
            }
            bigInteger2 = bigInteger2.or(BigInteger.valueOf(n2 & 127));
            if ((n2 & 128) == 0) {
                bigInteger = bigInteger2;
                n2 = n;
                if (n != 0) {
                    stringBuffer.append('2');
                    bigInteger = bigInteger2.subtract(BigInteger.valueOf(80L));
                    n2 = 0;
                }
                stringBuffer.append('.');
                stringBuffer.append(bigInteger);
                bigInteger = null;
                l = 0L;
                n = n2;
                continue;
            }
            bigInteger = bigInteger2.shiftLeft(7);
        }
        this.identifier = stringBuffer.toString().intern();
        this.body = Arrays.clone(arrby);
    }

    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) {
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.identifier);
        int n = Integer.parseInt(oIDTokenizer.nextToken()) * 40;
        String string = oIDTokenizer.nextToken();
        if (string.length() <= 18) {
            this.writeField(byteArrayOutputStream, (long)n + Long.parseLong(string));
        } else {
            this.writeField(byteArrayOutputStream, new BigInteger(string).add(BigInteger.valueOf(n)));
        }
        while (oIDTokenizer.hasMoreTokens()) {
            string = oIDTokenizer.nextToken();
            if (string.length() <= 18) {
                this.writeField(byteArrayOutputStream, Long.parseLong(string));
                continue;
            }
            this.writeField(byteArrayOutputStream, new BigInteger(string));
        }
    }

    static ASN1ObjectIdentifier fromOctetString(byte[] arrby) {
        Object object = new OidHandle(arrby);
        if ((object = (ASN1ObjectIdentifier)pool.get(object)) == null) {
            return new ASN1ObjectIdentifier(arrby);
        }
        return object;
    }

    private byte[] getBody() {
        synchronized (this) {
            byte[] arrby;
            if (this.body == null) {
                arrby = new ByteArrayOutputStream();
                this.doOutput((ByteArrayOutputStream)arrby);
                this.body = arrby.toByteArray();
            }
            arrby = this.body;
            return arrby;
        }
    }

    public static ASN1ObjectIdentifier getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof ASN1ObjectIdentifier)) {
            return ASN1ObjectIdentifier.fromOctetString(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
        }
        return ASN1ObjectIdentifier.getInstance(aSN1Primitive);
    }

    public static ASN1ObjectIdentifier getInstance(Object object) {
        if (object != null && !(object instanceof ASN1ObjectIdentifier)) {
            Object object2;
            if (object instanceof ASN1Encodable && (object2 = ((ASN1Encodable)object).toASN1Primitive()) instanceof ASN1ObjectIdentifier) {
                return (ASN1ObjectIdentifier)object2;
            }
            if (object instanceof byte[]) {
                object = (byte[])object;
                try {
                    object = (ASN1ObjectIdentifier)ASN1ObjectIdentifier.fromByteArray((byte[])object);
                    return object;
                }
                catch (IOException iOException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("failed to construct object identifier from byte[]: ");
                    ((StringBuilder)object).append(iOException.getMessage());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("illegal object in getInstance: ");
            ((StringBuilder)object2).append(object.getClass().getName());
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        return (ASN1ObjectIdentifier)object;
    }

    private static boolean isValidBranchID(String string, int n) {
        boolean bl = false;
        int n2 = string.length();
        while (--n2 >= n) {
            char c = string.charAt(n2);
            if ('0' <= c && c <= '9') {
                bl = true;
                continue;
            }
            if (c == '.') {
                if (!bl) {
                    return false;
                }
                bl = false;
                continue;
            }
            return false;
        }
        return bl;
    }

    private static boolean isValidIdentifier(String string) {
        if (string.length() >= 3 && string.charAt(1) == '.') {
            char c = string.charAt(0);
            if (c >= '0' && c <= '2') {
                return ASN1ObjectIdentifier.isValidBranchID(string, 2);
            }
            return false;
        }
        return false;
    }

    private void writeField(ByteArrayOutputStream byteArrayOutputStream, long l) {
        byte[] arrby = new byte[9];
        int n = 8;
        arrby[8] = (byte)((int)l & 127);
        while (l >= 128L) {
            arrby[--n] = (byte)((int)(l >>= 7) & 127 | 128);
        }
        byteArrayOutputStream.write(arrby, n, 9 - n);
    }

    private void writeField(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int n = (bigInteger.bitLength() + 6) / 7;
        if (n == 0) {
            byteArrayOutputStream.write(0);
        } else {
            int n2;
            byte[] arrby = new byte[n];
            for (n2 = n - 1; n2 >= 0; --n2) {
                arrby[n2] = (byte)(bigInteger.intValue() & 127 | 128);
                bigInteger = bigInteger.shiftRight(7);
            }
            n2 = n - 1;
            arrby[n2] = (byte)(arrby[n2] & 127);
            byteArrayOutputStream.write(arrby, 0, arrby.length);
        }
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive == this) {
            return true;
        }
        if (!(aSN1Primitive instanceof ASN1ObjectIdentifier)) {
            return false;
        }
        return this.identifier.equals(((ASN1ObjectIdentifier)aSN1Primitive).identifier);
    }

    public ASN1ObjectIdentifier branch(String string) {
        return new ASN1ObjectIdentifier(this, string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] arrby = this.getBody();
        aSN1OutputStream.write(6);
        aSN1OutputStream.writeLength(arrby.length);
        aSN1OutputStream.write(arrby);
    }

    @Override
    int encodedLength() throws IOException {
        int n = this.getBody().length;
        return StreamUtil.calculateBodyLength(n) + 1 + n;
    }

    public String getId() {
        return this.identifier;
    }

    @Override
    public int hashCode() {
        return this.identifier.hashCode();
    }

    public ASN1ObjectIdentifier intern() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        OidHandle oidHandle = new OidHandle(this.getBody());
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = aSN1ObjectIdentifier = (ASN1ObjectIdentifier)pool.get(oidHandle);
        if (aSN1ObjectIdentifier == null) {
            aSN1ObjectIdentifier2 = aSN1ObjectIdentifier = pool.putIfAbsent(oidHandle, this);
            if (aSN1ObjectIdentifier == null) {
                aSN1ObjectIdentifier2 = this;
            }
        }
        return aSN1ObjectIdentifier2;
    }

    @Override
    boolean isConstructed() {
        return false;
    }

    public boolean on(ASN1ObjectIdentifier object) {
        String string = this.getId();
        object = ((ASN1ObjectIdentifier)object).getId();
        boolean bl = string.length() > ((String)object).length() && string.charAt(((String)object).length()) == '.' && string.startsWith((String)object);
        return bl;
    }

    public String toString() {
        return this.getId();
    }

    private static class OidHandle {
        private final byte[] enc;
        private final int key;

        OidHandle(byte[] arrby) {
            this.key = Arrays.hashCode(arrby);
            this.enc = arrby;
        }

        public boolean equals(Object object) {
            if (object instanceof OidHandle) {
                return Arrays.areEqual(this.enc, ((OidHandle)object).enc);
            }
            return false;
        }

        public int hashCode() {
            return this.key;
        }
    }

}

