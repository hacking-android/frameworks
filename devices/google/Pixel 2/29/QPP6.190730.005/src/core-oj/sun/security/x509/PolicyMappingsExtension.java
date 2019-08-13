/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class PolicyMappingsExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.PolicyMappings";
    public static final String MAP = "map";
    public static final String NAME = "PolicyMappings";
    private List<CertificatePolicyMap> maps;

    public PolicyMappingsExtension() {
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = false;
        this.maps = Collections.emptyList();
    }

    public PolicyMappingsExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.PolicyMappings_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object2 = new DerValue(this.extensionValue);
        if (((DerValue)object2).tag == 48) {
            this.maps = new ArrayList<CertificatePolicyMap>();
            while (((DerValue)object2).data.available() != 0) {
                object = new CertificatePolicyMap(((DerValue)object2).data.getDerValue());
                this.maps.add((CertificatePolicyMap)object);
            }
            return;
        }
        throw new IOException("Invalid encoding for PolicyMappingsExtension.");
    }

    public PolicyMappingsExtension(List<CertificatePolicyMap> list) throws IOException {
        this.maps = list;
        this.extensionId = PKIXExtensions.PolicyMappings_Id;
        this.critical = false;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        List<CertificatePolicyMap> list = this.maps;
        if (list != null && !list.isEmpty()) {
            DerOutputStream derOutputStream = new DerOutputStream();
            list = new DerOutputStream();
            Iterator<CertificatePolicyMap> iterator = this.maps.iterator();
            while (iterator.hasNext()) {
                iterator.next().encode((DerOutputStream)((Object)list));
            }
            derOutputStream.write((byte)48, (DerOutputStream)((Object)list));
            this.extensionValue = derOutputStream.toByteArray();
            return;
        }
        this.extensionValue = null;
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(MAP)) {
            this.maps = null;
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.PolicyMappings_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public List<CertificatePolicyMap> get(String string) throws IOException {
        if (string.equalsIgnoreCase(MAP)) {
            return this.maps;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(MAP);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(MAP)) {
            if (object instanceof List) {
                this.maps = (List)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type List.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
    }

    @Override
    public String toString() {
        if (this.maps == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("PolicyMappings [\n");
        stringBuilder.append(this.maps.toString());
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

