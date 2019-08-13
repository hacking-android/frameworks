/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertificatePolicyId;

public class PolicyInformation {
    public static final String ID = "id";
    public static final String NAME = "PolicyInformation";
    public static final String QUALIFIERS = "qualifiers";
    private CertificatePolicyId policyIdentifier;
    private Set<PolicyQualifierInfo> policyQualifiers;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public PolicyInformation(DerValue derValue) throws IOException {
        if (derValue.tag != 48) throw new IOException("Invalid encoding of PolicyInformation");
        this.policyIdentifier = new CertificatePolicyId(derValue.data.getDerValue());
        if (derValue.data.available() != 0) {
            this.policyQualifiers = new LinkedHashSet<PolicyQualifierInfo>();
            derValue = derValue.data.getDerValue();
            if (derValue.tag != 48) throw new IOException("Invalid encoding of PolicyInformation");
            if (derValue.data.available() == 0) throw new IOException("No data available in policyQualifiers");
            while (derValue.data.available() != 0) {
                this.policyQualifiers.add(new PolicyQualifierInfo(derValue.data.getDerValue().toByteArray()));
            }
            return;
        } else {
            this.policyQualifiers = Collections.emptySet();
        }
    }

    public PolicyInformation(CertificatePolicyId certificatePolicyId, Set<PolicyQualifierInfo> set) throws IOException {
        if (set != null) {
            this.policyQualifiers = new LinkedHashSet<PolicyQualifierInfo>(set);
            this.policyIdentifier = certificatePolicyId;
            return;
        }
        throw new NullPointerException("policyQualifiers is null");
    }

    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(QUALIFIERS)) {
            this.policyQualifiers = Collections.emptySet();
            return;
        }
        if (string.equalsIgnoreCase(ID)) {
            throw new IOException("Attribute ID may not be deleted from PolicyInformation.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by PolicyInformation.");
        throw new IOException(stringBuilder.toString());
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.policyIdentifier.encode(derOutputStream2);
        if (!this.policyQualifiers.isEmpty()) {
            DerOutputStream derOutputStream3 = new DerOutputStream();
            Iterator<PolicyQualifierInfo> iterator = this.policyQualifiers.iterator();
            while (iterator.hasNext()) {
                derOutputStream3.write(iterator.next().getEncoded());
            }
            derOutputStream2.write((byte)48, derOutputStream3);
        }
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof PolicyInformation)) {
            return false;
        }
        if (!this.policyIdentifier.equals(((PolicyInformation)(object = (PolicyInformation)object)).getPolicyIdentifier())) {
            return false;
        }
        return this.policyQualifiers.equals(((PolicyInformation)object).getPolicyQualifiers());
    }

    public Object get(String string) throws IOException {
        if (string.equalsIgnoreCase(ID)) {
            return this.policyIdentifier;
        }
        if (string.equalsIgnoreCase(QUALIFIERS)) {
            return this.policyQualifiers;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by PolicyInformation.");
        throw new IOException(stringBuilder.toString());
    }

    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(ID);
        attributeNameEnumeration.addElement(QUALIFIERS);
        return attributeNameEnumeration.elements();
    }

    public String getName() {
        return NAME;
    }

    public CertificatePolicyId getPolicyIdentifier() {
        return this.policyIdentifier;
    }

    public Set<PolicyQualifierInfo> getPolicyQualifiers() {
        return this.policyQualifiers;
    }

    public int hashCode() {
        return (this.policyIdentifier.hashCode() + 37) * 37 + this.policyQualifiers.hashCode();
    }

    public void set(String object, Object object2) throws IOException {
        block6 : {
            block7 : {
                block8 : {
                    block5 : {
                        block3 : {
                            block4 : {
                                if (!((String)object).equalsIgnoreCase(ID)) break block3;
                                if (!(object2 instanceof CertificatePolicyId)) break block4;
                                this.policyIdentifier = (CertificatePolicyId)object2;
                                break block5;
                            }
                            throw new IOException("Attribute value must be instance of CertificatePolicyId.");
                        }
                        if (!((String)object).equalsIgnoreCase(QUALIFIERS)) break block6;
                        if (this.policyIdentifier == null) break block7;
                        if (!(object2 instanceof Set)) break block8;
                        object = ((Set)object2).iterator();
                        while (object.hasNext()) {
                            if (object.next() instanceof PolicyQualifierInfo) continue;
                            throw new IOException("Attribute value must be aSet of PolicyQualifierInfo objects.");
                        }
                        this.policyQualifiers = (Set)object2;
                    }
                    return;
                }
                throw new IOException("Attribute value must be of type Set.");
            }
            throw new IOException("Attribute must have a CertificatePolicyIdentifier value before PolicyQualifierInfo can be set.");
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Attribute name [");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("] not recognized by PolicyInformation");
        throw new IOException(((StringBuilder)object2).toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  [");
        stringBuilder.append(this.policyIdentifier.toString());
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.policyQualifiers);
        stringBuilder.append("  ]\n");
        stringBuilder2.append(stringBuilder.toString());
        return stringBuilder2.toString();
    }
}

