/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralSubtree;
import sun.security.x509.GeneralSubtrees;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.RFC822Name;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;

public class NameConstraintsExtension
extends Extension
implements CertAttrSet<String>,
Cloneable {
    public static final String EXCLUDED_SUBTREES = "excluded_subtrees";
    public static final String IDENT = "x509.info.extensions.NameConstraints";
    public static final String NAME = "NameConstraints";
    public static final String PERMITTED_SUBTREES = "permitted_subtrees";
    private static final byte TAG_EXCLUDED = 1;
    private static final byte TAG_PERMITTED = 0;
    private GeneralSubtrees excluded = null;
    private boolean hasMax;
    private boolean hasMin;
    private boolean minMaxValid = false;
    private GeneralSubtrees permitted = null;

    public NameConstraintsExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.NameConstraints_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object = new DerValue(this.extensionValue);
        if (((DerValue)object).tag == 48) {
            if (((DerValue)object).data == null) {
                return;
            }
            while (((DerValue)object).data.available() != 0) {
                object2 = ((DerValue)object).data.getDerValue();
                if (((DerValue)object2).isContextSpecific((byte)0) && ((DerValue)object2).isConstructed()) {
                    if (this.permitted == null) {
                        ((DerValue)object2).resetTag((byte)48);
                        this.permitted = new GeneralSubtrees((DerValue)object2);
                        continue;
                    }
                    throw new IOException("Duplicate permitted GeneralSubtrees in NameConstraintsExtension.");
                }
                if (((DerValue)object2).isContextSpecific((byte)1) && ((DerValue)object2).isConstructed()) {
                    if (this.excluded == null) {
                        ((DerValue)object2).resetTag((byte)48);
                        this.excluded = new GeneralSubtrees((DerValue)object2);
                        continue;
                    }
                    throw new IOException("Duplicate excluded GeneralSubtrees in NameConstraintsExtension.");
                }
                throw new IOException("Invalid encoding of NameConstraintsExtension.");
            }
            this.minMaxValid = false;
            return;
        }
        throw new IOException("Invalid encoding for NameConstraintsExtension.");
    }

    public NameConstraintsExtension(GeneralSubtrees generalSubtrees, GeneralSubtrees generalSubtrees2) throws IOException {
        this.permitted = generalSubtrees;
        this.excluded = generalSubtrees2;
        this.extensionId = PKIXExtensions.NameConstraints_Id;
        this.critical = true;
        this.encodeThis();
    }

    private void calcMinMax() throws IOException {
        int n;
        GeneralSubtree generalSubtree;
        this.hasMin = false;
        this.hasMax = false;
        if (this.excluded != null) {
            for (n = 0; n < this.excluded.size(); ++n) {
                generalSubtree = this.excluded.get(n);
                if (generalSubtree.getMinimum() != 0) {
                    this.hasMin = true;
                }
                if (generalSubtree.getMaximum() == -1) continue;
                this.hasMax = true;
            }
        }
        if (this.permitted != null) {
            for (n = 0; n < this.permitted.size(); ++n) {
                generalSubtree = this.permitted.get(n);
                if (generalSubtree.getMinimum() != 0) {
                    this.hasMin = true;
                }
                if (generalSubtree.getMaximum() == -1) continue;
                this.hasMax = true;
            }
        }
        this.minMaxValid = true;
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream;
        this.minMaxValid = false;
        if (this.permitted == null && this.excluded == null) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.permitted != null) {
            derOutputStream = new DerOutputStream();
            this.permitted.encode(derOutputStream);
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, true, (byte)0), derOutputStream);
        }
        if (this.excluded != null) {
            derOutputStream = new DerOutputStream();
            this.excluded.encode(derOutputStream);
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, true, (byte)1), derOutputStream);
        }
        derOutputStream2.write((byte)48, derOutputStream3);
        this.extensionValue = derOutputStream2.toByteArray();
    }

    public Object clone() {
        try {
            NameConstraintsExtension nameConstraintsExtension = (NameConstraintsExtension)Object.super.clone();
            if (this.permitted != null) {
                nameConstraintsExtension.permitted = (GeneralSubtrees)this.permitted.clone();
            }
            if (this.excluded != null) {
                nameConstraintsExtension.excluded = (GeneralSubtrees)this.excluded.clone();
            }
            return nameConstraintsExtension;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException("CloneNotSupportedException while cloning NameConstraintsException. This should never happen.");
        }
    }

    @Override
    public void delete(String string) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!string.equalsIgnoreCase(PERMITTED_SUBTREES)) break block2;
                    this.permitted = null;
                    break block3;
                }
                if (!string.equalsIgnoreCase(EXCLUDED_SUBTREES)) break block4;
                this.excluded = null;
            }
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.NameConstraints_Id;
            this.critical = true;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public GeneralSubtrees get(String string) throws IOException {
        if (string.equalsIgnoreCase(PERMITTED_SUBTREES)) {
            return this.permitted;
        }
        if (string.equalsIgnoreCase(EXCLUDED_SUBTREES)) {
            return this.excluded;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(PERMITTED_SUBTREES);
        attributeNameEnumeration.addElement(EXCLUDED_SUBTREES);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    public void merge(NameConstraintsExtension cloneable) throws IOException {
        if (cloneable == null) {
            return;
        }
        Object object = ((NameConstraintsExtension)cloneable).get(EXCLUDED_SUBTREES);
        GeneralSubtrees generalSubtrees = this.excluded;
        Object var4_4 = null;
        if (generalSubtrees == null) {
            object = object != null ? (GeneralSubtrees)((GeneralSubtrees)object).clone() : null;
            this.excluded = object;
        } else if (object != null) {
            generalSubtrees.union((GeneralSubtrees)object);
        }
        object = ((NameConstraintsExtension)cloneable).get(PERMITTED_SUBTREES);
        cloneable = this.permitted;
        if (cloneable == null) {
            cloneable = object != null ? (GeneralSubtrees)((GeneralSubtrees)object).clone() : var4_4;
            this.permitted = cloneable;
        } else if (object != null && (cloneable = ((GeneralSubtrees)cloneable).intersect((GeneralSubtrees)object)) != null) {
            object = this.excluded;
            if (object != null) {
                ((GeneralSubtrees)object).union((GeneralSubtrees)cloneable);
            } else {
                this.excluded = (GeneralSubtrees)((GeneralSubtrees)cloneable).clone();
            }
        }
        cloneable = this.permitted;
        if (cloneable != null) {
            ((GeneralSubtrees)cloneable).reduce(this.excluded);
        }
        this.encodeThis();
    }

    @Override
    public void set(String string, Object object) throws IOException {
        block5 : {
            block6 : {
                block4 : {
                    block2 : {
                        block3 : {
                            if (!string.equalsIgnoreCase(PERMITTED_SUBTREES)) break block2;
                            if (!(object instanceof GeneralSubtrees)) break block3;
                            this.permitted = (GeneralSubtrees)object;
                            break block4;
                        }
                        throw new IOException("Attribute value should be of type GeneralSubtrees.");
                    }
                    if (!string.equalsIgnoreCase(EXCLUDED_SUBTREES)) break block5;
                    if (!(object instanceof GeneralSubtrees)) break block6;
                    this.excluded = (GeneralSubtrees)object;
                }
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type GeneralSubtrees.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("NameConstraints: [");
        Object object = this.permitted;
        String string = "";
        if (object == null) {
            object = "";
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("\n    Permitted:");
            ((StringBuilder)object).append(this.permitted.toString());
            object = ((StringBuilder)object).toString();
        }
        stringBuilder.append((String)object);
        if (this.excluded == null) {
            object = string;
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("\n    Excluded:");
            ((StringBuilder)object).append(this.excluded.toString());
            object = ((StringBuilder)object).toString();
        }
        stringBuilder.append((String)object);
        stringBuilder.append("   ]\n");
        return stringBuilder.toString();
    }

    public boolean verify(X509Certificate object) throws IOException {
        if (object != null) {
            if (!this.minMaxValid) {
                this.calcMinMax();
            }
            if (!this.hasMin) {
                if (!this.hasMax) {
                    X500Name x500Name;
                    block10 : {
                        x500Name = X500Name.asX500Name(((X509Certificate)object).getSubjectX500Principal());
                        if (!x500Name.isEmpty() && !this.verify(x500Name)) {
                            return false;
                        }
                        StringBuilder stringBuilder = null;
                        object = X509CertImpl.toImpl((X509Certificate)object);
                        SubjectAlternativeNameExtension subjectAlternativeNameExtension = ((X509CertImpl)object).getSubjectAlternativeNameExtension();
                        object = stringBuilder;
                        if (subjectAlternativeNameExtension == null) break block10;
                        try {
                            object = subjectAlternativeNameExtension.get("subject_name");
                        }
                        catch (CertificateException certificateException) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Unable to extract extensions from certificate: ");
                            stringBuilder.append(certificateException.getMessage());
                            throw new IOException(stringBuilder.toString());
                        }
                    }
                    if (object == null) {
                        return this.verifyRFC822SpecialCase(x500Name);
                    }
                    for (int i = 0; i < ((GeneralNames)object).size(); ++i) {
                        if (this.verify(((GeneralNames)object).get(i).getName())) continue;
                        return false;
                    }
                    return true;
                }
                throw new IOException("Maximum BaseDistance in name constraints not supported");
            }
            throw new IOException("Non-zero minimum BaseDistance in name constraints not supported");
        }
        throw new IOException("Certificate is null");
    }

    public boolean verify(GeneralNameInterface generalNameInterface) throws IOException {
        if (generalNameInterface != null) {
            int n;
            int n2;
            Object object = this.excluded;
            if (object != null && ((GeneralSubtrees)object).size() > 0) {
                for (n2 = 0; n2 < this.excluded.size(); ++n2) {
                    object = this.excluded.get(n2);
                    if (object == null || (object = ((GeneralSubtree)object).getName()) == null || (object = ((GeneralName)object).getName()) == null || (n = object.constrains(generalNameInterface)) == -1) continue;
                    if (n != 0 && n != 1) {
                        if (n == 2 || n == 3) continue;
                        continue;
                    }
                    return false;
                }
            }
            if ((object = this.permitted) != null && ((GeneralSubtrees)object).size() > 0) {
                n = 0;
                for (n2 = 0; n2 < this.permitted.size(); ++n2) {
                    int n3;
                    object = this.permitted.get(n2);
                    if (object == null || (object = ((GeneralSubtree)object).getName()) == null || (object = ((GeneralName)object).getName()) == null || (n3 = object.constrains(generalNameInterface)) == -1) continue;
                    if (n3 != 0 && n3 != 1) {
                        if (n3 != 2 && n3 != 3) continue;
                        n = 1;
                        continue;
                    }
                    return true;
                }
                if (n != 0) {
                    return false;
                }
            }
            return true;
        }
        throw new IOException("name is null");
    }

    public boolean verifyRFC822SpecialCase(X500Name object) throws IOException {
        for (AVA iOException : ((X500Name)((Object)object)).allAvas()) {
            String string;
            if (!iOException.getObjectIdentifier().equals((Object)PKCS9Attribute.EMAIL_ADDRESS_OID) || (string = iOException.getValueString()) == null) continue;
            try {}
            catch (IOException iOException2) {
                continue;
            }
            RFC822Name rFC822Name = new RFC822Name(string);
            if (this.verify(rFC822Name)) continue;
            return false;
        }
        return true;
    }
}

