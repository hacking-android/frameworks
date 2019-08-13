/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class ExtendedKeyUsageExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.ExtendedKeyUsage";
    public static final String NAME = "ExtendedKeyUsage";
    private static final int[] OCSPSigningOidData;
    public static final String USAGES = "usages";
    private static final int[] anyExtendedKeyUsageOidData;
    private static final int[] clientAuthOidData;
    private static final int[] codeSigningOidData;
    private static final int[] emailProtectionOidData;
    private static final int[] ipsecEndSystemOidData;
    private static final int[] ipsecTunnelOidData;
    private static final int[] ipsecUserOidData;
    private static final Map<ObjectIdentifier, String> map;
    private static final int[] serverAuthOidData;
    private static final int[] timeStampingOidData;
    private Vector<ObjectIdentifier> keyUsages;

    static {
        map = new HashMap<ObjectIdentifier, String>();
        anyExtendedKeyUsageOidData = new int[]{2, 5, 29, 37, 0};
        serverAuthOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 1};
        clientAuthOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 2};
        codeSigningOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 3};
        emailProtectionOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 4};
        ipsecEndSystemOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 5};
        ipsecTunnelOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 6};
        ipsecUserOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 7};
        timeStampingOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 8};
        OCSPSigningOidData = new int[]{1, 3, 6, 1, 5, 5, 7, 3, 9};
        map.put(ObjectIdentifier.newInternal(anyExtendedKeyUsageOidData), "anyExtendedKeyUsage");
        map.put(ObjectIdentifier.newInternal(serverAuthOidData), "serverAuth");
        map.put(ObjectIdentifier.newInternal(clientAuthOidData), "clientAuth");
        map.put(ObjectIdentifier.newInternal(codeSigningOidData), "codeSigning");
        map.put(ObjectIdentifier.newInternal(emailProtectionOidData), "emailProtection");
        map.put(ObjectIdentifier.newInternal(ipsecEndSystemOidData), "ipsecEndSystem");
        map.put(ObjectIdentifier.newInternal(ipsecTunnelOidData), "ipsecTunnel");
        map.put(ObjectIdentifier.newInternal(ipsecUserOidData), "ipsecUser");
        map.put(ObjectIdentifier.newInternal(timeStampingOidData), "timeStamping");
        map.put(ObjectIdentifier.newInternal(OCSPSigningOidData), "OCSPSigning");
    }

    public ExtendedKeyUsageExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object = new DerValue(this.extensionValue);
        if (((DerValue)object).tag == 48) {
            this.keyUsages = new Vector();
            while (((DerValue)object).data.available() != 0) {
                object2 = ((DerValue)object).data.getDerValue().getOID();
                this.keyUsages.addElement((ObjectIdentifier)object2);
            }
            return;
        }
        throw new IOException("Invalid encoding for ExtendedKeyUsageExtension.");
    }

    public ExtendedKeyUsageExtension(Boolean bl, Vector<ObjectIdentifier> vector) throws IOException {
        this.keyUsages = vector;
        this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
        this.critical = bl;
        this.encodeThis();
    }

    public ExtendedKeyUsageExtension(Vector<ObjectIdentifier> vector) throws IOException {
        this(Boolean.FALSE, vector);
    }

    private void encodeThis() throws IOException {
        Vector<ObjectIdentifier> vector = this.keyUsages;
        if (vector != null && !vector.isEmpty()) {
            vector = new DerOutputStream();
            DerOutputStream derOutputStream = new DerOutputStream();
            for (int i = 0; i < this.keyUsages.size(); ++i) {
                derOutputStream.putOID(this.keyUsages.elementAt(i));
            }
            ((DerOutputStream)((Object)vector)).write((byte)48, derOutputStream);
            this.extensionValue = ((ByteArrayOutputStream)((Object)vector)).toByteArray();
            return;
        }
        this.extensionValue = null;
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(USAGES)) {
            this.keyUsages = null;
            this.encodeThis();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:ExtendedKeyUsageExtension.");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Vector<ObjectIdentifier> get(String string) throws IOException {
        if (string.equalsIgnoreCase(USAGES)) {
            return this.keyUsages;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:ExtendedKeyUsageExtension.");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(USAGES);
        return attributeNameEnumeration.elements();
    }

    public List<String> getExtendedKeyUsage() {
        ArrayList<String> arrayList = new ArrayList<String>(this.keyUsages.size());
        Iterator<ObjectIdentifier> iterator = this.keyUsages.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().toString());
        }
        return arrayList;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(USAGES)) {
            if (object instanceof Vector) {
                this.keyUsages = (Vector)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type Vector.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name [");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("] not recognized by CertAttrSet:ExtendedKeyUsageExtension.");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public String toString() {
        Vector<ObjectIdentifier> vector = this.keyUsages;
        if (vector == null) {
            return "";
        }
        CharSequence charSequence = "  ";
        boolean bl = true;
        for (ObjectIdentifier objectIdentifier : vector) {
            vector = charSequence;
            if (!bl) {
                vector = new StringBuilder();
                ((StringBuilder)((Object)vector)).append((String)charSequence);
                ((StringBuilder)((Object)vector)).append("\n  ");
                vector = ((StringBuilder)((Object)vector)).toString();
            }
            if ((charSequence = map.get(objectIdentifier)) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)((Object)vector));
                stringBuilder.append((String)charSequence);
                charSequence = stringBuilder.toString();
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)((Object)vector));
                ((StringBuilder)charSequence).append(objectIdentifier.toString());
                charSequence = ((StringBuilder)charSequence).toString();
            }
            bl = false;
        }
        vector = new StringBuilder();
        ((StringBuilder)((Object)vector)).append(super.toString());
        ((StringBuilder)((Object)vector)).append("ExtendedKeyUsages [\n");
        ((StringBuilder)((Object)vector)).append((String)charSequence);
        ((StringBuilder)((Object)vector)).append("\n]\n");
        return ((StringBuilder)((Object)vector)).toString();
    }
}

