/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.util.ASN1Dump;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.CRLDistPoint;
import com.android.org.bouncycastle.asn1.x509.CRLNumber;
import com.android.org.bouncycastle.asn1.x509.CertificateList;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import com.android.org.bouncycastle.asn1.x509.TBSCertList;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.jce.provider.ExtCRLException;
import com.android.org.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import com.android.org.bouncycastle.jce.provider.X509CRLEntryObject;
import com.android.org.bouncycastle.jce.provider.X509SignatureUtil;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public class X509CRLObject
extends X509CRL {
    private CertificateList c;
    private int hashCodeValue;
    private boolean isHashCodeSet = false;
    private boolean isIndirect;
    private String sigAlgName;
    private byte[] sigAlgParams;

    public X509CRLObject(CertificateList object) throws CRLException {
        this.c = object;
        try {
            this.sigAlgName = X509SignatureUtil.getSignatureName(((CertificateList)object).getSignatureAlgorithm());
            this.sigAlgParams = ((CertificateList)object).getSignatureAlgorithm().getParameters() != null ? ((CertificateList)object).getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded("DER") : null;
            this.isIndirect = X509CRLObject.isIndirectCRL(this);
            return;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("CRL contents invalid: ");
            ((StringBuilder)object).append(exception);
            throw new CRLException(((StringBuilder)object).toString());
        }
    }

    private void doVerify(PublicKey publicKey, Signature signature) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (this.c.getSignatureAlgorithm().equals(this.c.getTBSCertList().getSignature())) {
            signature.initVerify(publicKey);
            signature.update(this.getTBSCertList());
            if (signature.verify(this.getSignature())) {
                return;
            }
            throw new SignatureException("CRL does not verify with supplied public key.");
        }
        throw new CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
    }

    private Set getExtensionOIDs(boolean bl) {
        Extensions extensions;
        if (this.getVersion() == 2 && (extensions = this.c.getTBSCertList().getExtensions()) != null) {
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

    public static boolean isIndirectCRL(X509CRL arrby) throws CRLException {
        boolean bl;
        block4 : {
            block3 : {
                try {
                    arrby = arrby.getExtensionValue(Extension.issuingDistributionPoint.getId());
                    if (arrby == null) break block3;
                }
                catch (Exception exception) {
                    throw new ExtCRLException("Exception reading IssuingDistributionPoint", exception);
                }
                bl = IssuingDistributionPoint.getInstance(ASN1OctetString.getInstance(arrby).getOctets()).isIndirectCRL();
                if (!bl) break block3;
                bl = true;
                break block4;
            }
            bl = false;
        }
        return bl;
    }

    private Set loadCRLEntries() {
        HashSet<X509CRLEntryObject> hashSet = new HashSet<X509CRLEntryObject>();
        Enumeration enumeration = this.c.getRevokedCertificateEnumeration();
        X500Name x500Name = null;
        while (enumeration.hasMoreElements()) {
            ASN1Object aSN1Object = (TBSCertList.CRLEntry)enumeration.nextElement();
            hashSet.add(new X509CRLEntryObject((TBSCertList.CRLEntry)aSN1Object, this.isIndirect, x500Name));
            X500Name x500Name2 = x500Name;
            if (this.isIndirect) {
                x500Name2 = x500Name;
                if (((TBSCertList.CRLEntry)aSN1Object).hasExtensions()) {
                    aSN1Object = ((TBSCertList.CRLEntry)aSN1Object).getExtensions().getExtension(Extension.certificateIssuer);
                    x500Name2 = x500Name;
                    if (aSN1Object != null) {
                        x500Name2 = X500Name.getInstance(GeneralNames.getInstance(((Extension)aSN1Object).getParsedValue()).getNames()[0].getName());
                    }
                }
            }
            x500Name = x500Name2;
        }
        return hashSet;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof X509CRL)) {
            return false;
        }
        if (object instanceof X509CRLObject) {
            object = (X509CRLObject)object;
            if (this.isHashCodeSet && ((X509CRLObject)object).isHashCodeSet && ((X509CRLObject)object).hashCodeValue != this.hashCodeValue) {
                return false;
            }
            return this.c.equals(((X509CRLObject)object).c);
        }
        return super.equals(object);
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
        Object object = this.c.getTBSCertList().getExtensions();
        if (object != null && (arrby = ((Extensions)object).getExtension(new ASN1ObjectIdentifier((String)arrby))) != null) {
            try {
                arrby = arrby.getExtnValue().getEncoded();
                return arrby;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("error parsing ");
                ((StringBuilder)object).append(exception.toString());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
        return null;
    }

    @Override
    public Principal getIssuerDN() {
        return new X509Principal(X500Name.getInstance(this.c.getIssuer().toASN1Primitive()));
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        try {
            X500Principal x500Principal = new X500Principal(this.c.getIssuer().getEncoded());
            return x500Principal;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    @Override
    public Date getNextUpdate() {
        if (this.c.getNextUpdate() != null) {
            return this.c.getNextUpdate().getDate();
        }
        return null;
    }

    public Set getNonCriticalExtensionOIDs() {
        return this.getExtensionOIDs(false);
    }

    @Override
    public X509CRLEntry getRevokedCertificate(BigInteger bigInteger) {
        Enumeration enumeration = this.c.getRevokedCertificateEnumeration();
        X500Name x500Name = null;
        while (enumeration.hasMoreElements()) {
            ASN1Object aSN1Object = (TBSCertList.CRLEntry)enumeration.nextElement();
            if (bigInteger.equals(((TBSCertList.CRLEntry)aSN1Object).getUserCertificate().getValue())) {
                return new X509CRLEntryObject((TBSCertList.CRLEntry)aSN1Object, this.isIndirect, x500Name);
            }
            X500Name x500Name2 = x500Name;
            if (this.isIndirect) {
                x500Name2 = x500Name;
                if (((TBSCertList.CRLEntry)aSN1Object).hasExtensions()) {
                    aSN1Object = ((TBSCertList.CRLEntry)aSN1Object).getExtensions().getExtension(Extension.certificateIssuer);
                    x500Name2 = x500Name;
                    if (aSN1Object != null) {
                        x500Name2 = X500Name.getInstance(GeneralNames.getInstance(((Extension)aSN1Object).getParsedValue()).getNames()[0].getName());
                    }
                }
            }
            x500Name = x500Name2;
        }
        return null;
    }

    public Set getRevokedCertificates() {
        Set set = this.loadCRLEntries();
        if (!set.isEmpty()) {
            return Collections.unmodifiableSet(set);
        }
        return null;
    }

    @Override
    public String getSigAlgName() {
        return this.sigAlgName;
    }

    @Override
    public String getSigAlgOID() {
        return this.c.getSignatureAlgorithm().getAlgorithm().getId();
    }

    @Override
    public byte[] getSigAlgParams() {
        byte[] arrby = this.sigAlgParams;
        if (arrby != null) {
            byte[] arrby2 = new byte[arrby.length];
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby2.length);
            return arrby2;
        }
        return null;
    }

    @Override
    public byte[] getSignature() {
        return this.c.getSignature().getOctets();
    }

    @Override
    public byte[] getTBSCertList() throws CRLException {
        try {
            byte[] arrby = this.c.getTBSCertList().getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new CRLException(iOException.toString());
        }
    }

    @Override
    public Date getThisUpdate() {
        return this.c.getThisUpdate().getDate();
    }

    @Override
    public int getVersion() {
        return this.c.getVersionNumber();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        Set set = this.getCriticalExtensionOIDs();
        if (set == null) {
            return false;
        }
        set.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
        set.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
        return set.isEmpty() ^ true;
    }

    @Override
    public int hashCode() {
        if (!this.isHashCodeSet) {
            this.isHashCodeSet = true;
            this.hashCodeValue = super.hashCode();
        }
        return this.hashCodeValue;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isRevoked(Certificate object) {
        if (!((Certificate)object).getType().equals("X.509")) throw new RuntimeException("X.509 CRL used with non X.509 Cert");
        Enumeration enumeration = this.c.getRevokedCertificateEnumeration();
        X500Name x500Name = this.c.getIssuer();
        if (enumeration == null) return false;
        BigInteger bigInteger = ((X509Certificate)object).getSerialNumber();
        while (enumeration.hasMoreElements()) {
            TBSCertList.CRLEntry cRLEntry = TBSCertList.CRLEntry.getInstance(enumeration.nextElement());
            X500Name x500Name2 = x500Name;
            if (this.isIndirect) {
                x500Name2 = x500Name;
                if (cRLEntry.hasExtensions()) {
                    Extension extension = cRLEntry.getExtensions().getExtension(Extension.certificateIssuer);
                    x500Name2 = x500Name;
                    if (extension != null) {
                        x500Name2 = X500Name.getInstance(GeneralNames.getInstance(extension.getParsedValue()).getNames()[0].getName());
                    }
                }
            }
            if (cRLEntry.getUserCertificate().getValue().equals(bigInteger)) {
                if (object instanceof X509Certificate) {
                    object = X500Name.getInstance(((X509Certificate)object).getIssuerX500Principal().getEncoded());
                } else {
                    object = com.android.org.bouncycastle.asn1.x509.Certificate.getInstance(((Certificate)object).getEncoded()).getIssuer();
                }
                if (x500Name2.equals(object)) return true;
                return false;
                catch (CertificateEncodingException certificateEncodingException) {
                    throw new RuntimeException("Cannot process certificate");
                }
            }
            x500Name = x500Name2;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("              Version: ");
        stringBuffer.append(this.getVersion());
        stringBuffer.append(string);
        stringBuffer.append("             IssuerDN: ");
        stringBuffer.append(this.getIssuerDN());
        stringBuffer.append(string);
        stringBuffer.append("          This update: ");
        stringBuffer.append(this.getThisUpdate());
        stringBuffer.append(string);
        stringBuffer.append("          Next update: ");
        stringBuffer.append(this.getNextUpdate());
        stringBuffer.append(string);
        stringBuffer.append("  Signature Algorithm: ");
        stringBuffer.append(this.getSigAlgName());
        stringBuffer.append(string);
        Object object = this.getSignature();
        stringBuffer.append("            Signature: ");
        stringBuffer.append(new String(Hex.encode((byte[])object, 0, 20)));
        stringBuffer.append(string);
        for (int i = 20; i < ((byte[])object).length; i += 20) {
            if (i < ((byte[])object).length - 20) {
                stringBuffer.append("                       ");
                stringBuffer.append(new String(Hex.encode((byte[])object, i, 20)));
                stringBuffer.append(string);
                continue;
            }
            stringBuffer.append("                       ");
            stringBuffer.append(new String(Hex.encode((byte[])object, i, ((Object)object).length - i)));
            stringBuffer.append(string);
        }
        Extensions extensions = this.c.getTBSCertList().getExtensions();
        if (extensions != null) {
            object = extensions.oids();
            if (object.hasMoreElements()) {
                stringBuffer.append("           Extensions: ");
                stringBuffer.append(string);
            }
            while (object.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)object.nextElement();
                Object object2 = extensions.getExtension(aSN1ObjectIdentifier);
                if (((Extension)object2).getExtnValue() != null) {
                    ASN1InputStream aSN1InputStream = new ASN1InputStream(((Extension)object2).getExtnValue().getOctets());
                    stringBuffer.append("                       critical(");
                    stringBuffer.append(((Extension)object2).isCritical());
                    stringBuffer.append(") ");
                    try {
                        if (aSN1ObjectIdentifier.equals(Extension.cRLNumber)) {
                            object2 = new CRLNumber(ASN1Integer.getInstance(aSN1InputStream.readObject()).getPositiveValue());
                            stringBuffer.append(object2);
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(Extension.deltaCRLIndicator)) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Base CRL: ");
                            CRLNumber cRLNumber = new CRLNumber(ASN1Integer.getInstance(aSN1InputStream.readObject()).getPositiveValue());
                            ((StringBuilder)object2).append(cRLNumber);
                            stringBuffer.append(((StringBuilder)object2).toString());
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(Extension.issuingDistributionPoint)) {
                            stringBuffer.append(IssuingDistributionPoint.getInstance(aSN1InputStream.readObject()));
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(Extension.cRLDistributionPoints)) {
                            stringBuffer.append(CRLDistPoint.getInstance(aSN1InputStream.readObject()));
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(Extension.freshestCRL)) {
                            stringBuffer.append(CRLDistPoint.getInstance(aSN1InputStream.readObject()));
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
        if ((object = this.getRevokedCertificates()) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                stringBuffer.append(object.next());
                stringBuffer.append(string);
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public void verify(PublicKey publicKey) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        Signature signature;
        try {
            signature = Signature.getInstance(this.getSigAlgName(), "BC");
        }
        catch (Exception exception) {
            signature = Signature.getInstance(this.getSigAlgName());
        }
        this.doVerify(publicKey, signature);
    }

    @Override
    public void verify(PublicKey publicKey, String object) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        object = object != null ? Signature.getInstance(this.getSigAlgName(), (String)object) : Signature.getInstance(this.getSigAlgName());
        this.doVerify(publicKey, (Signature)object);
    }

    @Override
    public void verify(PublicKey publicKey, Provider object) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        object = object != null ? Signature.getInstance(this.getSigAlgName(), (Provider)object) : Signature.getInstance(this.getSigAlgName());
        this.doVerify(publicKey, (Signature)object);
    }
}

