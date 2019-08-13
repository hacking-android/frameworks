/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.DistributionPointName;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.ReasonFlags;

public class IssuingDistributionPointExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.IssuingDistributionPoint";
    public static final String INDIRECT_CRL = "indirect_crl";
    public static final String NAME = "IssuingDistributionPoint";
    public static final String ONLY_ATTRIBUTE_CERTS = "only_attribute_certs";
    public static final String ONLY_CA_CERTS = "only_ca_certs";
    public static final String ONLY_USER_CERTS = "only_user_certs";
    public static final String POINT = "point";
    public static final String REASONS = "reasons";
    private static final byte TAG_DISTRIBUTION_POINT = 0;
    private static final byte TAG_INDIRECT_CRL = 4;
    private static final byte TAG_ONLY_ATTRIBUTE_CERTS = 5;
    private static final byte TAG_ONLY_CA_CERTS = 2;
    private static final byte TAG_ONLY_SOME_REASONS = 3;
    private static final byte TAG_ONLY_USER_CERTS = 1;
    private DistributionPointName distributionPoint = null;
    private boolean hasOnlyAttributeCerts = false;
    private boolean hasOnlyCACerts = false;
    private boolean hasOnlyUserCerts = false;
    private boolean isIndirectCRL = false;
    private ReasonFlags revocationReasons = null;

    public IssuingDistributionPointExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
        this.critical = (Boolean)object;
        if (object2 instanceof byte[]) {
            this.extensionValue = (byte[])object2;
            object = new DerValue(this.extensionValue);
            if (((DerValue)object).tag == 48) {
                if (((DerValue)object).data != null && ((DerValue)object).data.available() != 0) {
                    object = ((DerValue)object).data;
                    while (object != null && ((DerInputStream)object).available() != 0) {
                        object2 = ((DerInputStream)object).getDerValue();
                        if (((DerValue)object2).isContextSpecific((byte)0) && ((DerValue)object2).isConstructed()) {
                            this.distributionPoint = new DistributionPointName(((DerValue)object2).data.getDerValue());
                            continue;
                        }
                        if (((DerValue)object2).isContextSpecific((byte)1) && !((DerValue)object2).isConstructed()) {
                            ((DerValue)object2).resetTag((byte)1);
                            this.hasOnlyUserCerts = ((DerValue)object2).getBoolean();
                            continue;
                        }
                        if (((DerValue)object2).isContextSpecific((byte)2) && !((DerValue)object2).isConstructed()) {
                            ((DerValue)object2).resetTag((byte)1);
                            this.hasOnlyCACerts = ((DerValue)object2).getBoolean();
                            continue;
                        }
                        if (((DerValue)object2).isContextSpecific((byte)3) && !((DerValue)object2).isConstructed()) {
                            this.revocationReasons = new ReasonFlags((DerValue)object2);
                            continue;
                        }
                        if (((DerValue)object2).isContextSpecific((byte)4) && !((DerValue)object2).isConstructed()) {
                            ((DerValue)object2).resetTag((byte)1);
                            this.isIndirectCRL = ((DerValue)object2).getBoolean();
                            continue;
                        }
                        if (((DerValue)object2).isContextSpecific((byte)5) && !((DerValue)object2).isConstructed()) {
                            ((DerValue)object2).resetTag((byte)1);
                            this.hasOnlyAttributeCerts = ((DerValue)object2).getBoolean();
                            continue;
                        }
                        throw new IOException("Invalid encoding of IssuingDistributionPoint");
                    }
                    return;
                }
                return;
            }
            throw new IOException("Invalid encoding for IssuingDistributionPointExtension.");
        }
        throw new IOException("Illegal argument type");
    }

    public IssuingDistributionPointExtension(DistributionPointName distributionPointName, ReasonFlags reasonFlags, boolean bl, boolean bl2, boolean bl3, boolean bl4) throws IOException {
        if (bl && (bl2 || bl3) || bl2 && (bl || bl3) || bl3 && (bl || bl2)) {
            throw new IllegalArgumentException("Only one of hasOnlyUserCerts, hasOnlyCACerts, hasOnlyAttributeCerts may be set to true");
        }
        this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
        this.critical = true;
        this.distributionPoint = distributionPointName;
        this.revocationReasons = reasonFlags;
        this.hasOnlyUserCerts = bl;
        this.hasOnlyCACerts = bl2;
        this.hasOnlyAttributeCerts = bl3;
        this.isIndirectCRL = bl4;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream;
        if (!(this.distributionPoint != null || this.revocationReasons != null || this.hasOnlyUserCerts || this.hasOnlyCACerts || this.hasOnlyAttributeCerts || this.isIndirectCRL)) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        if (this.distributionPoint != null) {
            derOutputStream = new DerOutputStream();
            this.distributionPoint.encode(derOutputStream);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, true, (byte)0), derOutputStream);
        }
        if (this.hasOnlyUserCerts) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putBoolean(this.hasOnlyUserCerts);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, false, (byte)1), derOutputStream);
        }
        if (this.hasOnlyCACerts) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putBoolean(this.hasOnlyCACerts);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, false, (byte)2), derOutputStream);
        }
        if (this.revocationReasons != null) {
            derOutputStream = new DerOutputStream();
            this.revocationReasons.encode(derOutputStream);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, false, (byte)3), derOutputStream);
        }
        if (this.isIndirectCRL) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putBoolean(this.isIndirectCRL);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, false, (byte)4), derOutputStream);
        }
        if (this.hasOnlyAttributeCerts) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putBoolean(this.hasOnlyAttributeCerts);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, false, (byte)5), derOutputStream);
        }
        derOutputStream = new DerOutputStream();
        derOutputStream.write((byte)48, derOutputStream2);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override
    public void delete(String string) throws IOException {
        block8 : {
            block3 : {
                block7 : {
                    block6 : {
                        block5 : {
                            block4 : {
                                block2 : {
                                    if (!string.equalsIgnoreCase(POINT)) break block2;
                                    this.distributionPoint = null;
                                    break block3;
                                }
                                if (!string.equalsIgnoreCase(INDIRECT_CRL)) break block4;
                                this.isIndirectCRL = false;
                                break block3;
                            }
                            if (!string.equalsIgnoreCase(REASONS)) break block5;
                            this.revocationReasons = null;
                            break block3;
                        }
                        if (!string.equalsIgnoreCase(ONLY_USER_CERTS)) break block6;
                        this.hasOnlyUserCerts = false;
                        break block3;
                    }
                    if (!string.equalsIgnoreCase(ONLY_CA_CERTS)) break block7;
                    this.hasOnlyCACerts = false;
                    break block3;
                }
                if (!string.equalsIgnoreCase(ONLY_ATTRIBUTE_CERTS)) break block8;
                this.hasOnlyAttributeCerts = false;
            }
            this.encodeThis();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:IssuingDistributionPointExtension.");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Object get(String string) throws IOException {
        if (string.equalsIgnoreCase(POINT)) {
            return this.distributionPoint;
        }
        if (string.equalsIgnoreCase(INDIRECT_CRL)) {
            return this.isIndirectCRL;
        }
        if (string.equalsIgnoreCase(REASONS)) {
            return this.revocationReasons;
        }
        if (string.equalsIgnoreCase(ONLY_USER_CERTS)) {
            return this.hasOnlyUserCerts;
        }
        if (string.equalsIgnoreCase(ONLY_CA_CERTS)) {
            return this.hasOnlyCACerts;
        }
        if (string.equalsIgnoreCase(ONLY_ATTRIBUTE_CERTS)) {
            return this.hasOnlyAttributeCerts;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:IssuingDistributionPointExtension.");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(POINT);
        attributeNameEnumeration.addElement(REASONS);
        attributeNameEnumeration.addElement(ONLY_USER_CERTS);
        attributeNameEnumeration.addElement(ONLY_CA_CERTS);
        attributeNameEnumeration.addElement(ONLY_ATTRIBUTE_CERTS);
        attributeNameEnumeration.addElement(INDIRECT_CRL);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        block13 : {
            block14 : {
                block4 : {
                    block11 : {
                        block12 : {
                            block9 : {
                                block10 : {
                                    block7 : {
                                        block8 : {
                                            block5 : {
                                                block6 : {
                                                    block2 : {
                                                        block3 : {
                                                            if (!string.equalsIgnoreCase(POINT)) break block2;
                                                            if (!(object instanceof DistributionPointName)) break block3;
                                                            this.distributionPoint = (DistributionPointName)object;
                                                            break block4;
                                                        }
                                                        throw new IOException("Attribute value should be of type DistributionPointName.");
                                                    }
                                                    if (!string.equalsIgnoreCase(REASONS)) break block5;
                                                    if (!(object instanceof ReasonFlags)) break block6;
                                                    this.revocationReasons = (ReasonFlags)object;
                                                    break block4;
                                                }
                                                throw new IOException("Attribute value should be of type ReasonFlags.");
                                            }
                                            if (!string.equalsIgnoreCase(INDIRECT_CRL)) break block7;
                                            if (!(object instanceof Boolean)) break block8;
                                            this.isIndirectCRL = (Boolean)object;
                                            break block4;
                                        }
                                        throw new IOException("Attribute value should be of type Boolean.");
                                    }
                                    if (!string.equalsIgnoreCase(ONLY_USER_CERTS)) break block9;
                                    if (!(object instanceof Boolean)) break block10;
                                    this.hasOnlyUserCerts = (Boolean)object;
                                    break block4;
                                }
                                throw new IOException("Attribute value should be of type Boolean.");
                            }
                            if (!string.equalsIgnoreCase(ONLY_CA_CERTS)) break block11;
                            if (!(object instanceof Boolean)) break block12;
                            this.hasOnlyCACerts = (Boolean)object;
                            break block4;
                        }
                        throw new IOException("Attribute value should be of type Boolean.");
                    }
                    if (!string.equalsIgnoreCase(ONLY_ATTRIBUTE_CERTS)) break block13;
                    if (!(object instanceof Boolean)) break block14;
                    this.hasOnlyAttributeCerts = (Boolean)object;
                }
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type Boolean.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name [");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("] not recognized by CertAttrSet:IssuingDistributionPointExtension.");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("IssuingDistributionPoint [\n  ");
        Object object = this.distributionPoint;
        if (object != null) {
            stringBuilder.append(object);
        }
        if ((object = this.revocationReasons) != null) {
            stringBuilder.append(object);
        }
        object = this.hasOnlyUserCerts ? "  Only contains user certs: true" : "  Only contains user certs: false";
        stringBuilder.append((String)object);
        stringBuilder.append("\n");
        object = this.hasOnlyCACerts ? "  Only contains CA certs: true" : "  Only contains CA certs: false";
        stringBuilder.append((String)object);
        stringBuilder.append("\n");
        object = this.hasOnlyAttributeCerts ? "  Only contains attribute certs: true" : "  Only contains attribute certs: false";
        stringBuilder.append((String)object);
        stringBuilder.append("\n");
        object = this.isIndirectCRL ? "  Indirect CRL: true" : "  Indirect CRL: false";
        stringBuilder.append((String)object);
        stringBuilder.append("\n");
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

