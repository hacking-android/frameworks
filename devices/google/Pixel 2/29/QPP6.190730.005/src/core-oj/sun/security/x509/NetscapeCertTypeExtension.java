/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Vector;
import sun.security.util.BitArray;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.KeyUsageExtension;

public class NetscapeCertTypeExtension
extends Extension
implements CertAttrSet<String> {
    private static final int[] CertType_data = new int[]{2, 16, 840, 1, 113730, 1, 1};
    public static final String IDENT = "x509.info.extensions.NetscapeCertType";
    public static final String NAME = "NetscapeCertType";
    public static ObjectIdentifier NetscapeCertType_Id;
    public static final String OBJECT_SIGNING = "object_signing";
    public static final String OBJECT_SIGNING_CA = "object_signing_ca";
    public static final String SSL_CA = "ssl_ca";
    public static final String SSL_CLIENT = "ssl_client";
    public static final String SSL_SERVER = "ssl_server";
    public static final String S_MIME = "s_mime";
    public static final String S_MIME_CA = "s_mime_ca";
    private static final Vector<String> mAttributeNames;
    private static MapEntry[] mMapData;
    private boolean[] bitString;

    static {
        try {
            ObjectIdentifier object;
            NetscapeCertType_Id = object = new ObjectIdentifier(CertType_data);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        mMapData = new MapEntry[]{new MapEntry(SSL_CLIENT, 0), new MapEntry(SSL_SERVER, 1), new MapEntry(S_MIME, 2), new MapEntry(OBJECT_SIGNING, 3), new MapEntry(SSL_CA, 5), new MapEntry(S_MIME_CA, 6), new MapEntry(OBJECT_SIGNING_CA, 7)};
        mAttributeNames = new Vector();
        for (MapEntry mapEntry : mMapData) {
            mAttributeNames.add(mapEntry.mName);
        }
    }

    public NetscapeCertTypeExtension() {
        this.extensionId = NetscapeCertType_Id;
        this.critical = true;
        this.bitString = new boolean[0];
    }

    public NetscapeCertTypeExtension(Boolean bl, Object object) throws IOException {
        this.extensionId = NetscapeCertType_Id;
        this.critical = bl;
        this.extensionValue = (byte[])object;
        this.bitString = new DerValue(this.extensionValue).getUnalignedBitString().toBooleanArray();
    }

    public NetscapeCertTypeExtension(byte[] arrby) throws IOException {
        this.bitString = new BitArray(arrby.length * 8, arrby).toBooleanArray();
        this.extensionId = NetscapeCertType_Id;
        this.critical = true;
        this.encodeThis();
    }

    public NetscapeCertTypeExtension(boolean[] arrbl) throws IOException {
        this.bitString = arrbl;
        this.extensionId = NetscapeCertType_Id;
        this.critical = true;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
        this.extensionValue = derOutputStream.toByteArray();
    }

    private static int getPosition(String string) throws IOException {
        Object object;
        for (int i = 0; i < ((MapEntry[])(object = mMapData)).length; ++i) {
            if (!string.equalsIgnoreCase(object[i].mName)) continue;
            return NetscapeCertTypeExtension.mMapData[i].mPosition;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name [");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("] not recognized by CertAttrSet:NetscapeCertType.");
        throw new IOException(((StringBuilder)object).toString());
    }

    private boolean isSet(int n) {
        boolean[] arrbl = this.bitString;
        boolean bl = n < arrbl.length && arrbl[n];
        return bl;
    }

    private void set(int n, boolean bl) {
        boolean[] arrbl = this.bitString;
        if (n >= arrbl.length) {
            boolean[] arrbl2 = new boolean[n + 1];
            System.arraycopy((Object)arrbl, 0, (Object)arrbl2, 0, arrbl.length);
            this.bitString = arrbl2;
        }
        this.bitString[n] = bl;
    }

    @Override
    public void delete(String string) throws IOException {
        this.set(NetscapeCertTypeExtension.getPosition(string), false);
        this.encodeThis();
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = NetscapeCertType_Id;
            this.critical = true;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Boolean get(String string) throws IOException {
        return this.isSet(NetscapeCertTypeExtension.getPosition(string));
    }

    @Override
    public Enumeration<String> getElements() {
        return mAttributeNames.elements();
    }

    public boolean[] getKeyUsageMappedBits() {
        KeyUsageExtension keyUsageExtension = new KeyUsageExtension();
        Boolean bl = Boolean.TRUE;
        try {
            if (this.isSet(NetscapeCertTypeExtension.getPosition(SSL_CLIENT)) || this.isSet(NetscapeCertTypeExtension.getPosition(S_MIME)) || this.isSet(NetscapeCertTypeExtension.getPosition(OBJECT_SIGNING))) {
                keyUsageExtension.set("digital_signature", bl);
            }
            if (this.isSet(NetscapeCertTypeExtension.getPosition(SSL_SERVER))) {
                keyUsageExtension.set("key_encipherment", bl);
            }
            if (this.isSet(NetscapeCertTypeExtension.getPosition(SSL_CA)) || this.isSet(NetscapeCertTypeExtension.getPosition(S_MIME_CA)) || this.isSet(NetscapeCertTypeExtension.getPosition(OBJECT_SIGNING_CA))) {
                keyUsageExtension.set("key_certsign", bl);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return keyUsageExtension.getBits();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof Boolean) {
            boolean bl = (Boolean)object;
            this.set(NetscapeCertTypeExtension.getPosition(string), bl);
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute must be of type Boolean.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("NetscapeCertType [\n");
        if (this.isSet(0)) {
            stringBuilder.append("   SSL client\n");
        }
        if (this.isSet(1)) {
            stringBuilder.append("   SSL server\n");
        }
        if (this.isSet(2)) {
            stringBuilder.append("   S/MIME\n");
        }
        if (this.isSet(3)) {
            stringBuilder.append("   Object Signing\n");
        }
        if (this.isSet(5)) {
            stringBuilder.append("   SSL CA\n");
        }
        if (this.isSet(6)) {
            stringBuilder.append("   S/MIME CA\n");
        }
        if (this.isSet(7)) {
            stringBuilder.append("   Object Signing CA");
        }
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }

    private static class MapEntry {
        String mName;
        int mPosition;

        MapEntry(String string, int n) {
            this.mName = string;
            this.mPosition = n;
        }
    }

}

