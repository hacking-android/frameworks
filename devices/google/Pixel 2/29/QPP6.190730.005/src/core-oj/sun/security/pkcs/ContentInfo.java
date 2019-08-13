/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.IOException;
import sun.security.pkcs.ParsingException;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class ContentInfo {
    public static ObjectIdentifier DATA_OID;
    public static ObjectIdentifier DIGESTED_DATA_OID;
    public static ObjectIdentifier ENCRYPTED_DATA_OID;
    public static ObjectIdentifier ENVELOPED_DATA_OID;
    public static ObjectIdentifier NETSCAPE_CERT_SEQUENCE_OID;
    private static final int[] OLD_DATA;
    public static ObjectIdentifier OLD_DATA_OID;
    private static final int[] OLD_SDATA;
    public static ObjectIdentifier OLD_SIGNED_DATA_OID;
    public static ObjectIdentifier PKCS7_OID;
    public static ObjectIdentifier SIGNED_AND_ENVELOPED_DATA_OID;
    public static ObjectIdentifier SIGNED_DATA_OID;
    public static ObjectIdentifier TIMESTAMP_TOKEN_INFO_OID;
    private static int[] crdata;
    private static int[] data;
    private static int[] ddata;
    private static int[] edata;
    private static int[] nsdata;
    private static int[] pkcs7;
    private static int[] sdata;
    private static int[] sedata;
    private static int[] tstInfo;
    DerValue content;
    ObjectIdentifier contentType;

    static {
        pkcs7 = new int[]{1, 2, 840, 113549, 1, 7};
        data = new int[]{1, 2, 840, 113549, 1, 7, 1};
        sdata = new int[]{1, 2, 840, 113549, 1, 7, 2};
        edata = new int[]{1, 2, 840, 113549, 1, 7, 3};
        sedata = new int[]{1, 2, 840, 113549, 1, 7, 4};
        ddata = new int[]{1, 2, 840, 113549, 1, 7, 5};
        crdata = new int[]{1, 2, 840, 113549, 1, 7, 6};
        nsdata = new int[]{2, 16, 840, 1, 113730, 2, 5};
        tstInfo = new int[]{1, 2, 840, 113549, 1, 9, 16, 1, 4};
        OLD_SDATA = new int[]{1, 2, 840, 1113549, 1, 7, 2};
        OLD_DATA = new int[]{1, 2, 840, 1113549, 1, 7, 1};
        PKCS7_OID = ObjectIdentifier.newInternal(pkcs7);
        DATA_OID = ObjectIdentifier.newInternal(data);
        SIGNED_DATA_OID = ObjectIdentifier.newInternal(sdata);
        ENVELOPED_DATA_OID = ObjectIdentifier.newInternal(edata);
        SIGNED_AND_ENVELOPED_DATA_OID = ObjectIdentifier.newInternal(sedata);
        DIGESTED_DATA_OID = ObjectIdentifier.newInternal(ddata);
        ENCRYPTED_DATA_OID = ObjectIdentifier.newInternal(crdata);
        OLD_SIGNED_DATA_OID = ObjectIdentifier.newInternal(OLD_SDATA);
        OLD_DATA_OID = ObjectIdentifier.newInternal(OLD_DATA);
        NETSCAPE_CERT_SEQUENCE_OID = ObjectIdentifier.newInternal(nsdata);
        TIMESTAMP_TOKEN_INFO_OID = ObjectIdentifier.newInternal(tstInfo);
    }

    public ContentInfo(DerInputStream derInputStream) throws IOException, ParsingException {
        this(derInputStream, false);
    }

    public ContentInfo(DerInputStream arrderValue, boolean bl) throws IOException, ParsingException {
        arrderValue = arrderValue.getSequence(2);
        this.contentType = new DerInputStream(arrderValue[0].toByteArray()).getOID();
        if (bl) {
            this.content = arrderValue[1];
        } else if (arrderValue.length > 1) {
            this.content = new DerInputStream(arrderValue[1].toByteArray()).getSet(1, true)[0];
        }
    }

    public ContentInfo(ObjectIdentifier objectIdentifier, DerValue derValue) {
        this.contentType = objectIdentifier;
        this.content = derValue;
    }

    public ContentInfo(byte[] object) {
        object = new DerValue(4, (byte[])object);
        this.contentType = DATA_OID;
        this.content = object;
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putOID(this.contentType);
        if (this.content != null) {
            DerOutputStream derOutputStream3 = new DerOutputStream();
            this.content.encode(derOutputStream3);
            derOutputStream2.putDerValue(new DerValue(-96, derOutputStream3.toByteArray()));
        }
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public DerValue getContent() {
        return this.content;
    }

    public byte[] getContentBytes() throws IOException {
        DerValue derValue = this.content;
        if (derValue == null) {
            return null;
        }
        return new DerInputStream(derValue.toByteArray()).getOctetString();
    }

    public ObjectIdentifier getContentType() {
        return this.contentType;
    }

    public byte[] getData() throws IOException {
        if (!(this.contentType.equals((Object)DATA_OID) || this.contentType.equals((Object)OLD_DATA_OID) || this.contentType.equals((Object)TIMESTAMP_TOKEN_INFO_OID))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("content type is not DATA: ");
            stringBuilder.append(this.contentType);
            throw new IOException(stringBuilder.toString());
        }
        DerValue derValue = this.content;
        if (derValue == null) {
            return null;
        }
        return derValue.getOctetString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append("Content Info Sequence\n\tContent type: ");
        stringBuilder.append(this.contentType);
        stringBuilder.append("\n");
        String string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("\tContent: ");
        stringBuilder.append(this.content);
        return stringBuilder.toString();
    }
}

