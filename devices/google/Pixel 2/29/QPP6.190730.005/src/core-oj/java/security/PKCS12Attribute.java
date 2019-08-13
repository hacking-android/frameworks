/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public final class PKCS12Attribute
implements KeyStore.Entry.Attribute {
    private static final Pattern COLON_SEPARATED_HEX_PAIRS = Pattern.compile("^[0-9a-fA-F]{2}(:[0-9a-fA-F]{2})+$");
    private byte[] encoded;
    private int hashValue = -1;
    private String name;
    private String value;

    public PKCS12Attribute(String arrstring, String string) {
        if (arrstring != null && string != null) {
            ObjectIdentifier objectIdentifier;
            block5 : {
                try {
                    objectIdentifier = new ObjectIdentifier((String)arrstring);
                    this.name = arrstring;
                    int n = string.length();
                    if (string.charAt(0) != '[' || string.charAt(n - 1) != ']') break block5;
                    arrstring = string.substring(1, n - 1).split(", ");
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("Incorrect format: name", iOException);
                }
            }
            arrstring = new String[]{string};
            this.value = string;
            try {
                this.encoded = this.encode(objectIdentifier, arrstring);
                return;
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Incorrect format: value", iOException);
            }
        }
        throw new NullPointerException();
    }

    public PKCS12Attribute(byte[] arrby) {
        if (arrby != null) {
            this.encoded = (byte[])arrby.clone();
            try {
                this.parse(arrby);
                return;
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Incorrect format: encoded", iOException);
            }
        }
        throw new NullPointerException();
    }

    /*
     * WARNING - void declaration
     */
    private byte[] encode(ObjectIdentifier object2, String[] arrstring) throws IOException {
        void var2_9;
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putOID((ObjectIdentifier)object2);
        DerOutputStream derOutputStream2 = new DerOutputStream();
        for (void var1_3 : var2_9) {
            if (COLON_SEPARATED_HEX_PAIRS.matcher((CharSequence)var1_3).matches()) {
                byte[] arrby;
                void var1_6;
                byte[] arrby2 = arrby = new BigInteger(var1_3.replace(":", ""), 16).toByteArray();
                if (arrby[0] == 0) {
                    byte[] arrby3 = Arrays.copyOfRange(arrby, 1, arrby.length);
                }
                derOutputStream2.putOctetString((byte[])var1_6);
                continue;
            }
            derOutputStream2.putUTF8String((String)var1_3);
        }
        derOutputStream.write((byte)49, derOutputStream2);
        DerOutputStream derOutputStream3 = new DerOutputStream();
        derOutputStream3.write((byte)48, derOutputStream);
        return derOutputStream3.toByteArray();
    }

    private void parse(byte[] object) throws IOException {
        DerValue[] arrderValue = new DerInputStream((byte[])object).getSequence(2);
        object = arrderValue[0].getOID();
        arrderValue = new DerInputStream(arrderValue[1].toByteArray()).getSet(1);
        Object[] arrobject = new String[arrderValue.length];
        for (int i = 0; i < arrderValue.length; ++i) {
            String string;
            arrobject[i] = arrderValue[i].tag == 4 ? Debug.toString(arrderValue[i].getOctetString()) : ((string = arrderValue[i].getAsString()) != null ? string : (arrderValue[i].tag == 6 ? arrderValue[i].getOID().toString() : (arrderValue[i].tag == 24 ? arrderValue[i].getGeneralizedTime().toString() : (arrderValue[i].tag == 23 ? arrderValue[i].getUTCTime().toString() : (arrderValue[i].tag == 2 ? arrderValue[i].getBigInteger().toString() : (arrderValue[i].tag == 1 ? String.valueOf(arrderValue[i].getBoolean()) : Debug.toString(arrderValue[i].getDataBytes())))))));
        }
        this.name = ((ObjectIdentifier)object).toString();
        object = arrobject.length == 1 ? arrobject[0] : Arrays.toString(arrobject);
        this.value = object;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PKCS12Attribute)) {
            return false;
        }
        return Arrays.equals(this.encoded, ((PKCS12Attribute)object).getEncoded());
    }

    public byte[] getEncoded() {
        return (byte[])this.encoded.clone();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        if (this.hashValue == -1) {
            Arrays.hashCode(this.encoded);
        }
        return this.hashValue;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append("=");
        stringBuilder.append(this.value);
        return stringBuilder.toString();
    }
}

