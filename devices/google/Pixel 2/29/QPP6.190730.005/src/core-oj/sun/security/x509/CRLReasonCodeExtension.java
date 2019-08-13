/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CRLReason;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class CRLReasonCodeExtension
extends Extension
implements CertAttrSet<String> {
    public static final String NAME = "CRLReasonCode";
    public static final String REASON = "reason";
    private static CRLReason[] values = CRLReason.values();
    private int reasonCode = 0;

    public CRLReasonCodeExtension(int n) throws IOException {
        this(false, n);
    }

    public CRLReasonCodeExtension(Boolean bl, Object object) throws IOException {
        this.extensionId = PKIXExtensions.ReasonCode_Id;
        this.critical = bl;
        this.extensionValue = (byte[])object;
        this.reasonCode = new DerValue(this.extensionValue).getEnumerated();
    }

    public CRLReasonCodeExtension(boolean bl, int n) throws IOException {
        this.extensionId = PKIXExtensions.ReasonCode_Id;
        this.critical = bl;
        this.reasonCode = n;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        if (this.reasonCode == 0) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putEnumerated(this.reasonCode);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(REASON)) {
            this.reasonCode = 0;
            this.encodeThis();
            return;
        }
        throw new IOException("Name not supported by CRLReasonCodeExtension");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.ReasonCode_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Integer get(String string) throws IOException {
        if (string.equalsIgnoreCase(REASON)) {
            return new Integer(this.reasonCode);
        }
        throw new IOException("Name not supported by CRLReasonCodeExtension");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(REASON);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    public CRLReason getReasonCode() {
        CRLReason[] arrcRLReason;
        int n = this.reasonCode;
        if (n > 0 && n < (arrcRLReason = values).length) {
            return arrcRLReason[n];
        }
        return CRLReason.UNSPECIFIED;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof Integer) {
            if (string.equalsIgnoreCase(REASON)) {
                this.reasonCode = (Integer)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Name not supported by CRLReasonCodeExtension");
        }
        throw new IOException("Attribute must be of type Integer.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("    Reason Code: ");
        stringBuilder.append((Object)this.getReasonCode());
        return stringBuilder.toString();
    }
}

