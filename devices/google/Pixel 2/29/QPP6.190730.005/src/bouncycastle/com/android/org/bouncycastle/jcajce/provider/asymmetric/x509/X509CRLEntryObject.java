/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.util.ASN1Dump;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.CRLReason;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.TBSCertList;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

class X509CRLEntryObject
extends X509CRLEntry {
    private TBSCertList.CRLEntry c;
    private X500Name certificateIssuer;
    private int hashValue;
    private boolean isHashValueSet;

    protected X509CRLEntryObject(TBSCertList.CRLEntry cRLEntry) {
        this.c = cRLEntry;
        this.certificateIssuer = null;
    }

    protected X509CRLEntryObject(TBSCertList.CRLEntry cRLEntry, boolean bl, X500Name x500Name) {
        this.c = cRLEntry;
        this.certificateIssuer = this.loadCertificateIssuer(bl, x500Name);
    }

    private Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extensions extensions = this.c.getExtensions();
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    private Set getExtensionOIDs(boolean bl) {
        Extensions extensions = this.c.getExtensions();
        if (extensions != null) {
            HashSet<String> hashSet = new HashSet<String>();
            Enumeration enumeration = extensions.oids();
            while (enumeration.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                if (bl != extensions.getExtension(aSN1ObjectIdentifier).isCritical()) continue;
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
            return hashSet;
        }
        return null;
    }

    private X500Name loadCertificateIssuer(boolean bl, X500Name object) {
        int n;
        if (!bl) {
            return null;
        }
        Extension extension = this.getExtension(Extension.certificateIssuer);
        if (extension == null) {
            return object;
        }
        try {
            object = GeneralNames.getInstance(extension.getParsedValue()).getNames();
            n = 0;
        }
        catch (Exception exception) {
            return null;
        }
        do {
            block6 : {
                if (n >= ((GeneralName[])object).length) break;
                if (object[n].getTagNo() != 4) break block6;
                object = X500Name.getInstance(object[n].getName());
                return object;
            }
            ++n;
        } while (true);
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof X509CRLEntryObject) {
            object = (X509CRLEntryObject)object;
            return this.c.equals(((X509CRLEntryObject)object).c);
        }
        return super.equals(this);
    }

    @Override
    public X500Principal getCertificateIssuer() {
        Object object = this.certificateIssuer;
        if (object == null) {
            return null;
        }
        try {
            object = new X500Principal(((ASN1Object)object).getEncoded());
            return object;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public Set getCriticalExtensionOIDs() {
        return this.getExtensionOIDs(true);
    }

    @Override
    public byte[] getEncoded() throws CRLException {
        try {
            byte[] arrby = this.c.getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new CRLException(iOException.toString());
        }
    }

    @Override
    public byte[] getExtensionValue(String arrby) {
        if ((arrby = this.getExtension(new ASN1ObjectIdentifier((String)arrby))) != null) {
            try {
                arrby = arrby.getExtnValue().getEncoded();
                return arrby;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception encoding: ");
                stringBuilder.append(exception.toString());
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return null;
    }

    public Set getNonCriticalExtensionOIDs() {
        return this.getExtensionOIDs(false);
    }

    @Override
    public Date getRevocationDate() {
        return this.c.getRevocationDate().getDate();
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.c.getUserCertificate().getValue();
    }

    @Override
    public boolean hasExtensions() {
        boolean bl = this.c.getExtensions() != null;
        return bl;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        Set set = this.getCriticalExtensionOIDs();
        boolean bl = set != null && !set.isEmpty();
        return bl;
    }

    @Override
    public int hashCode() {
        if (!this.isHashValueSet) {
            this.hashValue = super.hashCode();
            this.isHashValueSet = true;
        }
        return this.hashValue;
    }

    @Override
    public String toString() {
        Enumeration enumeration;
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("      userCertificate: ");
        stringBuffer.append(this.getSerialNumber());
        stringBuffer.append(string);
        stringBuffer.append("       revocationDate: ");
        stringBuffer.append(this.getRevocationDate());
        stringBuffer.append(string);
        stringBuffer.append("       certificateIssuer: ");
        stringBuffer.append(this.getCertificateIssuer());
        stringBuffer.append(string);
        Extensions extensions = this.c.getExtensions();
        if (extensions != null && (enumeration = extensions.oids()).hasMoreElements()) {
            stringBuffer.append("   crlEntryExtensions:");
            stringBuffer.append(string);
            while (enumeration.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                if (extension.getExtnValue() != null) {
                    ASN1InputStream aSN1InputStream = new ASN1InputStream(extension.getExtnValue().getOctets());
                    stringBuffer.append("                       critical(");
                    stringBuffer.append(extension.isCritical());
                    stringBuffer.append(") ");
                    try {
                        if (aSN1ObjectIdentifier.equals(Extension.reasonCode)) {
                            stringBuffer.append(CRLReason.getInstance(ASN1Enumerated.getInstance(aSN1InputStream.readObject())));
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(Extension.certificateIssuer)) {
                            stringBuffer.append("Certificate issuer: ");
                            stringBuffer.append(GeneralNames.getInstance(aSN1InputStream.readObject()));
                            stringBuffer.append(string);
                            continue;
                        }
                        stringBuffer.append(aSN1ObjectIdentifier.getId());
                        stringBuffer.append(" value = ");
                        stringBuffer.append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                        stringBuffer.append(string);
                    }
                    catch (Exception exception) {
                        stringBuffer.append(aSN1ObjectIdentifier.getId());
                        stringBuffer.append(" value = ");
                        stringBuffer.append("*****");
                        stringBuffer.append(string);
                    }
                    continue;
                }
                stringBuffer.append(string);
            }
        }
        return stringBuffer.toString();
    }
}

