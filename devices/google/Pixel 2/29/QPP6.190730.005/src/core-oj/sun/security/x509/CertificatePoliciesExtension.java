/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.PolicyInformation;

public class CertificatePoliciesExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.CertificatePolicies";
    public static final String NAME = "CertificatePolicies";
    public static final String POLICIES = "policies";
    private List<PolicyInformation> certPolicies;

    public CertificatePoliciesExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.CertificatePolicies_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object2 = new DerValue(this.extensionValue);
        if (((DerValue)object2).tag == 48) {
            this.certPolicies = new ArrayList<PolicyInformation>();
            while (((DerValue)object2).data.available() != 0) {
                object = new PolicyInformation(((DerValue)object2).data.getDerValue());
                this.certPolicies.add((PolicyInformation)object);
            }
            return;
        }
        throw new IOException("Invalid encoding for CertificatePoliciesExtension.");
    }

    public CertificatePoliciesExtension(Boolean bl, List<PolicyInformation> list) throws IOException {
        this.certPolicies = list;
        this.extensionId = PKIXExtensions.CertificatePolicies_Id;
        this.critical = bl;
        this.encodeThis();
    }

    public CertificatePoliciesExtension(List<PolicyInformation> list) throws IOException {
        this(Boolean.FALSE, list);
    }

    private void encodeThis() throws IOException {
        List<PolicyInformation> list = this.certPolicies;
        if (list != null && !list.isEmpty()) {
            DerOutputStream derOutputStream = new DerOutputStream();
            list = new DerOutputStream();
            Iterator<PolicyInformation> iterator = this.certPolicies.iterator();
            while (iterator.hasNext()) {
                iterator.next().encode((DerOutputStream)((Object)list));
            }
            derOutputStream.write((byte)48, (DerOutputStream)((Object)list));
            this.extensionValue = derOutputStream.toByteArray();
        } else {
            this.extensionValue = null;
        }
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(POLICIES)) {
            this.certPolicies = null;
            this.encodeThis();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:CertificatePoliciesExtension.");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.CertificatePolicies_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public List<PolicyInformation> get(String string) throws IOException {
        if (string.equalsIgnoreCase(POLICIES)) {
            return this.certPolicies;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:CertificatePoliciesExtension.");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(POLICIES);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(POLICIES)) {
            if (object instanceof List) {
                this.certPolicies = (List)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type List.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name [");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("] not recognized by CertAttrSet:CertificatePoliciesExtension.");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public String toString() {
        if (this.certPolicies == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("CertificatePolicies [\n");
        Iterator<PolicyInformation> iterator = this.certPolicies.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
        }
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

