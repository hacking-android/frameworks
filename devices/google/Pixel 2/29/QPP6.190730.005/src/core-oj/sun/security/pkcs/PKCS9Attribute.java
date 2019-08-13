/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import sun.misc.HexDumpEncoder;
import sun.security.pkcs.SignerInfo;
import sun.security.pkcs.SigningCertificateInfo;
import sun.security.util.Debug;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertificateExtensions;

public class PKCS9Attribute
implements DerEncoder {
    private static final Class<?> BYTE_ARRAY_CLASS;
    public static final ObjectIdentifier CHALLENGE_PASSWORD_OID;
    public static final String CHALLENGE_PASSWORD_STR = "ChallengePassword";
    public static final ObjectIdentifier CONTENT_TYPE_OID;
    public static final String CONTENT_TYPE_STR = "ContentType";
    public static final ObjectIdentifier COUNTERSIGNATURE_OID;
    public static final String COUNTERSIGNATURE_STR = "Countersignature";
    public static final ObjectIdentifier EMAIL_ADDRESS_OID;
    public static final String EMAIL_ADDRESS_STR = "EmailAddress";
    public static final ObjectIdentifier EXTENDED_CERTIFICATE_ATTRIBUTES_OID;
    public static final String EXTENDED_CERTIFICATE_ATTRIBUTES_STR = "ExtendedCertificateAttributes";
    public static final ObjectIdentifier EXTENSION_REQUEST_OID;
    public static final String EXTENSION_REQUEST_STR = "ExtensionRequest";
    public static final ObjectIdentifier ISSUER_SERIALNUMBER_OID;
    public static final String ISSUER_SERIALNUMBER_STR = "IssuerAndSerialNumber";
    public static final ObjectIdentifier MESSAGE_DIGEST_OID;
    public static final String MESSAGE_DIGEST_STR = "MessageDigest";
    private static final Hashtable<String, ObjectIdentifier> NAME_OID_TABLE;
    private static final Hashtable<ObjectIdentifier, String> OID_NAME_TABLE;
    static final ObjectIdentifier[] PKCS9_OIDS;
    private static final Byte[][] PKCS9_VALUE_TAGS;
    private static final String RSA_PROPRIETARY_STR = "RSAProprietary";
    public static final ObjectIdentifier SIGNATURE_TIMESTAMP_TOKEN_OID;
    public static final String SIGNATURE_TIMESTAMP_TOKEN_STR = "SignatureTimestampToken";
    public static final ObjectIdentifier SIGNING_CERTIFICATE_OID;
    public static final String SIGNING_CERTIFICATE_STR = "SigningCertificate";
    public static final ObjectIdentifier SIGNING_TIME_OID;
    public static final String SIGNING_TIME_STR = "SigningTime";
    private static final boolean[] SINGLE_VALUED;
    public static final ObjectIdentifier SMIME_CAPABILITY_OID;
    public static final String SMIME_CAPABILITY_STR = "SMIMECapability";
    private static final String SMIME_SIGNING_DESC_STR = "SMIMESigningDesc";
    public static final ObjectIdentifier UNSTRUCTURED_ADDRESS_OID;
    public static final String UNSTRUCTURED_ADDRESS_STR = "UnstructuredAddress";
    public static final ObjectIdentifier UNSTRUCTURED_NAME_OID;
    public static final String UNSTRUCTURED_NAME_STR = "UnstructuredName";
    private static final Class<?>[] VALUE_CLASSES;
    private static final Debug debug;
    private int index;
    private ObjectIdentifier oid;
    private Object value;

    static {
        Object object;
        debug = Debug.getInstance("jar");
        PKCS9_OIDS = new ObjectIdentifier[18];
        int n = 1;
        while (n < ((ObjectIdentifier[])(object = PKCS9_OIDS)).length - 2) {
            int[] arrn = new int[]{1, 2, 840, 113549, 1, 9, n++};
            object[n] = ObjectIdentifier.newInternal(arrn);
        }
        object[((ObjectIdentifier[])object).length - 2] = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 9, 16, 2, 12});
        object = PKCS9_OIDS;
        object[((ObjectIdentifier[])object).length - 1] = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 9, 16, 2, 14});
        try {
            BYTE_ARRAY_CLASS = Class.forName("[B");
            object = PKCS9_OIDS;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new ExceptionInInitializerError(classNotFoundException.toString());
        }
        EMAIL_ADDRESS_OID = object[1];
        UNSTRUCTURED_NAME_OID = object[2];
        CONTENT_TYPE_OID = object[3];
        MESSAGE_DIGEST_OID = object[4];
        SIGNING_TIME_OID = object[5];
        COUNTERSIGNATURE_OID = object[6];
        CHALLENGE_PASSWORD_OID = object[7];
        UNSTRUCTURED_ADDRESS_OID = object[8];
        EXTENDED_CERTIFICATE_ATTRIBUTES_OID = object[9];
        ISSUER_SERIALNUMBER_OID = object[10];
        EXTENSION_REQUEST_OID = object[14];
        SMIME_CAPABILITY_OID = object[15];
        SIGNING_CERTIFICATE_OID = object[16];
        SIGNATURE_TIMESTAMP_TOKEN_OID = object[17];
        NAME_OID_TABLE = new Hashtable(18);
        NAME_OID_TABLE.put("emailaddress", PKCS9_OIDS[1]);
        NAME_OID_TABLE.put("unstructuredname", PKCS9_OIDS[2]);
        NAME_OID_TABLE.put("contenttype", PKCS9_OIDS[3]);
        NAME_OID_TABLE.put("messagedigest", PKCS9_OIDS[4]);
        NAME_OID_TABLE.put("signingtime", PKCS9_OIDS[5]);
        NAME_OID_TABLE.put("countersignature", PKCS9_OIDS[6]);
        NAME_OID_TABLE.put("challengepassword", PKCS9_OIDS[7]);
        NAME_OID_TABLE.put("unstructuredaddress", PKCS9_OIDS[8]);
        NAME_OID_TABLE.put("extendedcertificateattributes", PKCS9_OIDS[9]);
        NAME_OID_TABLE.put("issuerandserialnumber", PKCS9_OIDS[10]);
        NAME_OID_TABLE.put("rsaproprietary", PKCS9_OIDS[11]);
        NAME_OID_TABLE.put("rsaproprietary", PKCS9_OIDS[12]);
        NAME_OID_TABLE.put("signingdescription", PKCS9_OIDS[13]);
        NAME_OID_TABLE.put("extensionrequest", PKCS9_OIDS[14]);
        NAME_OID_TABLE.put("smimecapability", PKCS9_OIDS[15]);
        NAME_OID_TABLE.put("signingcertificate", PKCS9_OIDS[16]);
        NAME_OID_TABLE.put("signaturetimestamptoken", PKCS9_OIDS[17]);
        OID_NAME_TABLE = new Hashtable(16);
        OID_NAME_TABLE.put(PKCS9_OIDS[1], EMAIL_ADDRESS_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[2], UNSTRUCTURED_NAME_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[3], CONTENT_TYPE_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[4], MESSAGE_DIGEST_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[5], SIGNING_TIME_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[6], COUNTERSIGNATURE_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[7], CHALLENGE_PASSWORD_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[8], UNSTRUCTURED_ADDRESS_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[9], EXTENDED_CERTIFICATE_ATTRIBUTES_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[10], ISSUER_SERIALNUMBER_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[11], RSA_PROPRIETARY_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[12], RSA_PROPRIETARY_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[13], SMIME_SIGNING_DESC_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[14], EXTENSION_REQUEST_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[15], SMIME_CAPABILITY_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[16], SIGNING_CERTIFICATE_STR);
        OID_NAME_TABLE.put(PKCS9_OIDS[17], SIGNATURE_TIMESTAMP_TOKEN_STR);
        Byte by = new Byte(22);
        Byte by2 = new Byte(22);
        Byte by3 = new Byte(19);
        Byte by4 = new Byte(6);
        Byte by5 = new Byte(4);
        Byte by6 = new Byte(23);
        Byte by7 = new Byte(48);
        object = new Byte(19);
        Byte by8 = new Byte(20);
        Byte by9 = new Byte(19);
        Byte by10 = new Byte(20);
        Byte by11 = new Byte(49);
        Byte by12 = new Byte(48);
        Byte[] arrbyte = new Byte[]{new Byte(48)};
        Byte by13 = new Byte(48);
        Byte by14 = new Byte(48);
        Byte by15 = new Byte(48);
        PKCS9_VALUE_TAGS = new Byte[][]{null, {by}, {by2, by3}, {by4}, {by5}, {by6}, {by7}, {object, by8}, {by9, by10}, {by11}, {by12}, null, null, null, arrbyte, {by13}, {by14}, {by15}};
        VALUE_CLASSES = new Class[18];
        try {
            object = Class.forName("[Ljava.lang.String;");
            PKCS9Attribute.VALUE_CLASSES[0] = null;
            PKCS9Attribute.VALUE_CLASSES[1] = object;
            PKCS9Attribute.VALUE_CLASSES[2] = object;
            PKCS9Attribute.VALUE_CLASSES[3] = Class.forName("sun.security.util.ObjectIdentifier");
            PKCS9Attribute.VALUE_CLASSES[4] = BYTE_ARRAY_CLASS;
            PKCS9Attribute.VALUE_CLASSES[5] = Class.forName("java.util.Date");
            PKCS9Attribute.VALUE_CLASSES[6] = Class.forName("[Lsun.security.pkcs.SignerInfo;");
            PKCS9Attribute.VALUE_CLASSES[7] = Class.forName("java.lang.String");
            PKCS9Attribute.VALUE_CLASSES[8] = object;
            PKCS9Attribute.VALUE_CLASSES[9] = null;
            PKCS9Attribute.VALUE_CLASSES[10] = null;
            PKCS9Attribute.VALUE_CLASSES[11] = null;
            PKCS9Attribute.VALUE_CLASSES[12] = null;
            PKCS9Attribute.VALUE_CLASSES[13] = null;
            PKCS9Attribute.VALUE_CLASSES[14] = Class.forName("sun.security.x509.CertificateExtensions");
            PKCS9Attribute.VALUE_CLASSES[15] = null;
            PKCS9Attribute.VALUE_CLASSES[16] = null;
            PKCS9Attribute.VALUE_CLASSES[17] = BYTE_ARRAY_CLASS;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new ExceptionInInitializerError(classNotFoundException.toString());
        }
        SINGLE_VALUED = new boolean[]{false, false, false, true, true, true, false, true, false, false, true, false, false, false, true, true, true, true};
    }

    public PKCS9Attribute(String string, Object object) throws IllegalArgumentException {
        ObjectIdentifier objectIdentifier = PKCS9Attribute.getOID(string);
        if (objectIdentifier != null) {
            this.init(objectIdentifier, object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unrecognized attribute name ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" constructing PKCS9Attribute.");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public PKCS9Attribute(DerValue object) throws IOException {
        object = new DerInputStream(((DerValue)object).toByteArray());
        Object object2 = ((DerInputStream)object).getSequence(2);
        if (((DerInputStream)object).available() == 0) {
            if (((DerValue[])object2).length == 2) {
                this.oid = object2[0].getOID();
                object2 = object2[1].toByteArray();
                object = new DerInputStream((byte[])object2).getSet(1);
                this.index = PKCS9Attribute.indexOf(this.oid, PKCS9_OIDS, 1);
                int n = this.index;
                if (n == -1) {
                    Debug debug = PKCS9Attribute.debug;
                    if (debug != null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unsupported signer attribute: ");
                        ((StringBuilder)object).append(this.oid);
                        debug.println(((StringBuilder)object).toString());
                    }
                    this.value = object2;
                    return;
                }
                if (SINGLE_VALUED[n] && ((DerValue[])object).length > 1) {
                    this.throwSingleValuedException();
                }
                for (n = 0; n < ((DerValue[])object).length; ++n) {
                    object2 = new Byte(((DerValue)object[n]).tag);
                    if (PKCS9Attribute.indexOf(object2, PKCS9_VALUE_TAGS[this.index], 0) != -1) continue;
                    this.throwTagException((Byte)object2);
                }
                switch (this.index) {
                    default: {
                        break;
                    }
                    case 17: {
                        this.value = ((DerValue)object[0]).toByteArray();
                        break;
                    }
                    case 16: {
                        this.value = new SigningCertificateInfo(((DerValue)object[0]).toByteArray());
                        break;
                    }
                    case 15: {
                        throw new IOException("PKCS9 SMIMECapability attribute not supported.");
                    }
                    case 14: {
                        this.value = new CertificateExtensions(new DerInputStream(((DerValue)object[0]).toByteArray()));
                        break;
                    }
                    case 13: {
                        throw new IOException("PKCS9 attribute #13 not supported.");
                    }
                    case 11: 
                    case 12: {
                        throw new IOException("PKCS9 RSA DSI attributes11 and 12, not supported.");
                    }
                    case 10: {
                        throw new IOException("PKCS9 IssuerAndSerialNumberattribute not supported.");
                    }
                    case 9: {
                        throw new IOException("PKCS9 extended-certificate attribute not supported.");
                    }
                    case 7: {
                        this.value = ((DerValue)object[0]).getAsString();
                        break;
                    }
                    case 6: {
                        object2 = new SignerInfo[((Object)object).length];
                        for (n = 0; n < ((Object)object).length; ++n) {
                            object2[n] = new SignerInfo(((DerValue)object[n]).toDerInputStream());
                        }
                        this.value = object2;
                        break;
                    }
                    case 5: {
                        this.value = new DerInputStream(((DerValue)object[0]).toByteArray()).getUTCTime();
                        break;
                    }
                    case 4: {
                        this.value = ((DerValue)object[0]).getOctetString();
                        break;
                    }
                    case 3: {
                        this.value = ((DerValue)object[0]).getOID();
                        break;
                    }
                    case 1: 
                    case 2: 
                    case 8: {
                        object2 = new String[((Object)object).length];
                        for (n = 0; n < ((Object)object).length; ++n) {
                            object2[n] = ((DerValue)object[n]).getAsString();
                        }
                        this.value = object2;
                    }
                }
                return;
            }
            throw new IOException("PKCS9Attribute doesn't have two components");
        }
        throw new IOException("Excess data parsing PKCS9Attribute");
    }

    public PKCS9Attribute(ObjectIdentifier objectIdentifier, Object object) throws IllegalArgumentException {
        this.init(objectIdentifier, object);
    }

    public static String getName(ObjectIdentifier objectIdentifier) {
        return OID_NAME_TABLE.get(objectIdentifier);
    }

    public static ObjectIdentifier getOID(String string) {
        return NAME_OID_TABLE.get(string.toLowerCase(Locale.ENGLISH));
    }

    static int indexOf(Object object, Object[] arrobject, int n) {
        while (n < arrobject.length) {
            if (object.equals(arrobject[n])) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private void init(ObjectIdentifier objectIdentifier, Object object) throws IllegalArgumentException {
        this.oid = objectIdentifier;
        this.index = PKCS9Attribute.indexOf(objectIdentifier, PKCS9_OIDS, 1);
        int n = this.index;
        Class<?> class_ = n == -1 ? BYTE_ARRAY_CLASS : VALUE_CLASSES[n];
        if (class_.isInstance(object)) {
            this.value = object;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wrong value class  for attribute ");
        stringBuilder.append(objectIdentifier);
        stringBuilder.append(" constructing PKCS9Attribute; was ");
        stringBuilder.append(object.getClass().toString());
        stringBuilder.append(", should be ");
        stringBuilder.append(class_.toString());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void throwSingleValuedException() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Single-value attribute ");
        stringBuilder.append(this.oid);
        stringBuilder.append(" (");
        stringBuilder.append(this.getName());
        stringBuilder.append(") has multiple values.");
        throw new IOException(stringBuilder.toString());
    }

    private void throwTagException(Byte by) throws IOException {
        Byte[] arrbyte = PKCS9_VALUE_TAGS[this.index];
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("Value of attribute ");
        stringBuffer.append(this.oid.toString());
        stringBuffer.append(" (");
        stringBuffer.append(this.getName());
        stringBuffer.append(") has wrong tag: ");
        stringBuffer.append(by.toString());
        stringBuffer.append(".  Expected tags: ");
        stringBuffer.append(arrbyte[0].toString());
        for (int i = 1; i < arrbyte.length; ++i) {
            stringBuffer.append(", ");
            stringBuffer.append(arrbyte[i].toString());
        }
        stringBuffer.append(".");
        throw new IOException(stringBuffer.toString());
    }

    @Override
    public void derEncode(OutputStream outputStream) throws IOException {
        Object object;
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putOID(this.oid);
        switch (this.index) {
            default: {
                break;
            }
            case 17: {
                derOutputStream.write((byte)49, (byte[])this.value);
                break;
            }
            case 16: {
                throw new IOException("PKCS9 SigningCertificate attribute not supported.");
            }
            case 15: {
                throw new IOException("PKCS9 attribute #15 not supported.");
            }
            case 14: {
                object = new DerOutputStream();
                CertificateExtensions certificateExtensions = (CertificateExtensions)this.value;
                try {
                    certificateExtensions.encode((OutputStream)object, true);
                    derOutputStream.write((byte)49, ((ByteArrayOutputStream)object).toByteArray());
                    break;
                }
                catch (CertificateException certificateException) {
                    throw new IOException(certificateException.toString());
                }
            }
            case 13: {
                throw new IOException("PKCS9 attribute #13 not supported.");
            }
            case 11: 
            case 12: {
                throw new IOException("PKCS9 RSA DSI attributes11 and 12, not supported.");
            }
            case 10: {
                throw new IOException("PKCS9 IssuerAndSerialNumberattribute not supported.");
            }
            case 9: {
                throw new IOException("PKCS9 extended-certificate attribute not supported.");
            }
            case 8: {
                object = (String[])this.value;
                DerEncoder[] arrderEncoder = new DerOutputStream[((String[])object).length];
                for (int i = 0; i < ((Object)object).length; ++i) {
                    arrderEncoder[i] = new DerOutputStream();
                    ((DerOutputStream)arrderEncoder[i]).putPrintableString((String)object[i]);
                }
                derOutputStream.putOrderedSetOf((byte)49, arrderEncoder);
                break;
            }
            case 7: {
                object = new DerOutputStream();
                ((DerOutputStream)object).putPrintableString((String)this.value);
                derOutputStream.write((byte)49, ((ByteArrayOutputStream)object).toByteArray());
                break;
            }
            case 6: {
                derOutputStream.putOrderedSetOf((byte)49, (DerEncoder[])this.value);
                break;
            }
            case 5: {
                object = new DerOutputStream();
                ((DerOutputStream)object).putUTCTime((Date)this.value);
                derOutputStream.write((byte)49, ((ByteArrayOutputStream)object).toByteArray());
                break;
            }
            case 4: {
                object = new DerOutputStream();
                ((DerOutputStream)object).putOctetString((byte[])this.value);
                derOutputStream.write((byte)49, ((ByteArrayOutputStream)object).toByteArray());
                break;
            }
            case 3: {
                object = new DerOutputStream();
                ((DerOutputStream)object).putOID((ObjectIdentifier)this.value);
                derOutputStream.write((byte)49, ((ByteArrayOutputStream)object).toByteArray());
                break;
            }
            case 1: 
            case 2: {
                object = (String[])this.value;
                DerEncoder[] arrderEncoder = new DerOutputStream[((String[])object).length];
                for (int i = 0; i < ((Object)object).length; ++i) {
                    arrderEncoder[i] = new DerOutputStream();
                    ((DerOutputStream)arrderEncoder[i]).putIA5String((String)object[i]);
                }
                derOutputStream.putOrderedSetOf((byte)49, arrderEncoder);
                break;
            }
            case -1: {
                derOutputStream.write((byte[])this.value);
            }
        }
        object = new DerOutputStream();
        ((DerOutputStream)object).write((byte)48, derOutputStream.toByteArray());
        outputStream.write(((ByteArrayOutputStream)object).toByteArray());
    }

    public String getName() {
        int n = this.index;
        String string = n == -1 ? this.oid.toString() : OID_NAME_TABLE.get(PKCS9_OIDS[n]);
        return string;
    }

    public ObjectIdentifier getOID() {
        return this.oid;
    }

    public Object getValue() {
        return this.value;
    }

    public boolean isKnown() {
        boolean bl = this.index != -1;
        return bl;
    }

    public boolean isSingleValued() {
        int n = this.index;
        boolean bl = n == -1 || SINGLE_VALUED[n];
        return bl;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("[");
        int n = this.index;
        if (n == -1) {
            stringBuffer.append(this.oid.toString());
        } else {
            stringBuffer.append(OID_NAME_TABLE.get(PKCS9_OIDS[n]));
        }
        stringBuffer.append(": ");
        n = this.index;
        if (n != -1 && !SINGLE_VALUED[n]) {
            boolean bl = true;
            Object[] arrobject = (Object[])this.value;
            for (n = 0; n < arrobject.length; ++n) {
                if (bl) {
                    bl = false;
                } else {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(arrobject[n].toString());
            }
            return stringBuffer.toString();
        }
        Object object = this.value;
        if (object instanceof byte[]) {
            stringBuffer.append(new HexDumpEncoder().encodeBuffer((byte[])this.value));
        } else {
            stringBuffer.append(object.toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

