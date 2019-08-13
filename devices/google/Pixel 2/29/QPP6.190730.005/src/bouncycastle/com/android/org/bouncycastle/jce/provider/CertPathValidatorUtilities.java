/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.org.bouncycastle.asn1.x500.style.RFC4519Style;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import com.android.org.bouncycastle.asn1.x509.CRLDistPoint;
import com.android.org.bouncycastle.asn1.x509.DistributionPoint;
import com.android.org.bouncycastle.asn1.x509.DistributionPointName;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.PolicyInformation;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.jcajce.PKIXCRLStore;
import com.android.org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import com.android.org.bouncycastle.jcajce.PKIXCertStore;
import com.android.org.bouncycastle.jcajce.PKIXCertStoreSelector;
import com.android.org.bouncycastle.jcajce.PKIXExtendedParameters;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import com.android.org.bouncycastle.jce.provider.AnnotatedException;
import com.android.org.bouncycastle.jce.provider.CertStatus;
import com.android.org.bouncycastle.jce.provider.PKIXCRLUtil;
import com.android.org.bouncycastle.jce.provider.PKIXPolicyNode;
import com.android.org.bouncycastle.jce.provider.PrincipalUtils;
import com.android.org.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import com.android.org.bouncycastle.jce.provider.X509CRLObject;
import com.android.org.bouncycastle.x509.AttributeCertificateIssuer;
import com.android.org.bouncycastle.x509.X509AttributeCertificate;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CRLSelector;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.PolicyNode;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

class CertPathValidatorUtilities {
    protected static final String ANY_POLICY = "2.5.29.32.0";
    protected static final String AUTHORITY_KEY_IDENTIFIER;
    protected static final String BASIC_CONSTRAINTS;
    protected static final String CERTIFICATE_POLICIES;
    protected static final String CRL_DISTRIBUTION_POINTS;
    protected static final String CRL_NUMBER;
    protected static final int CRL_SIGN = 6;
    protected static final PKIXCRLUtil CRL_UTIL;
    protected static final String DELTA_CRL_INDICATOR;
    protected static final String FRESHEST_CRL;
    protected static final String INHIBIT_ANY_POLICY;
    protected static final String ISSUING_DISTRIBUTION_POINT;
    protected static final int KEY_CERT_SIGN = 5;
    protected static final String KEY_USAGE;
    protected static final String NAME_CONSTRAINTS;
    protected static final String POLICY_CONSTRAINTS;
    protected static final String POLICY_MAPPINGS;
    protected static final String SUBJECT_ALTERNATIVE_NAME;
    protected static final String[] crlReasons;

    static {
        CRL_UTIL = new PKIXCRLUtil();
        CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
        BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
        POLICY_MAPPINGS = Extension.policyMappings.getId();
        SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
        NAME_CONSTRAINTS = Extension.nameConstraints.getId();
        KEY_USAGE = Extension.keyUsage.getId();
        INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
        ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
        DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
        POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
        FRESHEST_CRL = Extension.freshestCRL.getId();
        CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
        AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
        CRL_NUMBER = Extension.cRLNumber.getId();
        crlReasons = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
    }

    CertPathValidatorUtilities() {
    }

    static void checkCRLsNotEmpty(Set object, Object object2) throws AnnotatedException {
        if (object.isEmpty()) {
            if (object2 instanceof X509AttributeCertificate) {
                object = (X509AttributeCertificate)object2;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("No CRLs found for issuer \"");
                ((StringBuilder)object2).append(object.getIssuer().getPrincipals()[0]);
                ((StringBuilder)object2).append("\"");
                throw new AnnotatedException(((StringBuilder)object2).toString());
            }
            object2 = (X509Certificate)object2;
            object = new StringBuilder();
            ((StringBuilder)object).append("No CRLs found for issuer \"");
            ((StringBuilder)object).append(RFC4519Style.INSTANCE.toString(PrincipalUtils.getIssuerPrincipal((X509Certificate)object2)));
            ((StringBuilder)object).append("\"");
            throw new AnnotatedException(((StringBuilder)object).toString());
        }
    }

    protected static Collection findCertificates(PKIXCertStoreSelector pKIXCertStoreSelector, List object) throws AnnotatedException {
        LinkedHashSet<Certificate> linkedHashSet = new LinkedHashSet<Certificate>();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (CertStore)iterator.next();
            try {
                linkedHashSet.addAll(PKIXCertStoreSelector.getCertificates(pKIXCertStoreSelector, (CertStore)object));
            }
            catch (CertStoreException certStoreException) {
                throw new AnnotatedException("Problem while picking certificates from certificate store.", certStoreException);
            }
        }
        return linkedHashSet;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Collection findIssuerCerts(X509Certificate object, List<CertStore> object2, List<PKIXCertStore> list) throws AnnotatedException {
        byte[] arrby;
        Cloneable cloneable = new X509CertSelector();
        ((X509CertSelector)cloneable).setSubject(PrincipalUtils.getIssuerPrincipal((X509Certificate)object).getEncoded());
        try {
            object = object.getExtensionValue(AUTHORITY_KEY_IDENTIFIER);
            if (object != null && (arrby = AuthorityKeyIdentifier.getInstance(ASN1OctetString.getInstance(object).getOctets()).getKeyIdentifier()) != null) {
                object = new DEROctetString(arrby);
                ((X509CertSelector)cloneable).setSubjectKeyIdentifier(((ASN1Object)object).getEncoded());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        arrby = new PKIXCertStoreSelector.Builder((CertSelector)cloneable).build();
        object = new LinkedHashSet();
        try {
            cloneable = new ArrayList();
            cloneable.addAll(CertPathValidatorUtilities.findCertificates((PKIXCertStoreSelector)arrby, object2));
            cloneable.addAll(CertPathValidatorUtilities.findCertificates((PKIXCertStoreSelector)arrby, list));
            object2 = cloneable.iterator();
        }
        catch (AnnotatedException annotatedException) {
            throw new AnnotatedException("Issuer certificate cannot be searched.", annotatedException);
        }
        while (object2.hasNext()) {
            object.add((X509Certificate)object2.next());
        }
        return object;
        catch (IOException iOException) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", iOException);
        }
    }

    protected static TrustAnchor findTrustAnchor(X509Certificate x509Certificate, Set set) throws AnnotatedException {
        return CertPathValidatorUtilities.findTrustAnchor(x509Certificate, set, null);
    }

    protected static TrustAnchor findTrustAnchor(X509Certificate x509Certificate, Set object, String string) throws AnnotatedException {
        Iterator iterator;
        Object object2 = null;
        PublicKey publicKey = null;
        Throwable throwable = null;
        X509CertSelector x509CertSelector = new X509CertSelector();
        X500Name x500Name = PrincipalUtils.getEncodedIssuerPrincipal(x509Certificate);
        try {
            x509CertSelector.setSubject(x500Name.getEncoded());
            iterator = object.iterator();
        }
        catch (IOException iOException) {
            throw new AnnotatedException("Cannot set subject search criteria for trust anchor.", iOException);
        }
        while (iterator.hasNext() && object2 == null) {
            PublicKey publicKey2;
            object = (TrustAnchor)iterator.next();
            if (((TrustAnchor)object).getTrustedCert() != null) {
                if (x509CertSelector.match(((TrustAnchor)object).getTrustedCert())) {
                    publicKey2 = ((TrustAnchor)object).getTrustedCert().getPublicKey();
                } else {
                    object = null;
                    publicKey2 = publicKey;
                }
            } else if (((TrustAnchor)object).getCAName() != null && ((TrustAnchor)object).getCAPublicKey() != null) {
                try {
                    if (x500Name.equals(PrincipalUtils.getCA((TrustAnchor)object))) {
                        publicKey = publicKey2 = ((TrustAnchor)object).getCAPublicKey();
                    } else {
                        object = null;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    object = null;
                }
                publicKey2 = publicKey;
            } else {
                object = null;
                publicKey2 = publicKey;
            }
            object2 = object;
            publicKey = publicKey2;
            if (publicKey2 == null) continue;
            try {
                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate, publicKey2, string);
                publicKey = publicKey2;
            }
            catch (Exception exception) {
                object = null;
                publicKey = null;
            }
            object2 = object;
        }
        if (object2 == null && throwable != null) {
            throw new AnnotatedException("TrustAnchor found but certificate validation failed.", throwable);
        }
        return object2;
    }

    static List<PKIXCertStore> getAdditionalStoresFromAltNames(byte[] object, Map<GeneralName, PKIXCertStore> map) throws CertificateParsingException {
        if (object != null) {
            GeneralName[] arrgeneralName = GeneralNames.getInstance(ASN1OctetString.getInstance(object).getOctets()).getNames();
            ArrayList<PKIXCertStore> arrayList = new ArrayList<PKIXCertStore>();
            for (int i = 0; i != arrgeneralName.length; ++i) {
                object = map.get(arrgeneralName[i]);
                if (object == null) continue;
                arrayList.add((PKIXCertStore)object);
            }
            return arrayList;
        }
        return Collections.EMPTY_LIST;
    }

    static List<PKIXCRLStore> getAdditionalStoresFromCRLDistributionPoint(CRLDistPoint arrdistributionPoint, Map<GeneralName, PKIXCRLStore> map) throws AnnotatedException {
        if (arrdistributionPoint != null) {
            ArrayList<PKIXCRLStore> arrayList;
            try {
                arrdistributionPoint = arrdistributionPoint.getDistributionPoints();
                arrayList = new ArrayList<PKIXCRLStore>();
            }
            catch (Exception exception) {
                throw new AnnotatedException("Distribution points could not be read.", exception);
            }
            for (int i = 0; i < arrdistributionPoint.length; ++i) {
                GeneralName[] arrgeneralName = arrdistributionPoint[i].getDistributionPoint();
                if (arrgeneralName == null || arrgeneralName.getType() != 0) continue;
                arrgeneralName = GeneralNames.getInstance(arrgeneralName.getName()).getNames();
                for (int j = 0; j < arrgeneralName.length; ++j) {
                    PKIXCRLStore pKIXCRLStore = map.get(arrgeneralName[j]);
                    if (pKIXCRLStore == null) continue;
                    arrayList.add(pKIXCRLStore);
                }
            }
            return arrayList;
        }
        return Collections.EMPTY_LIST;
    }

    protected static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey object) throws CertPathValidatorException {
        try {
            ASN1InputStream aSN1InputStream = new ASN1InputStream(object.getEncoded());
            object = SubjectPublicKeyInfo.getInstance(aSN1InputStream.readObject()).getAlgorithm();
            return object;
        }
        catch (Exception exception) {
            throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", exception);
        }
    }

    protected static void getCRLIssuersFromDistributionPoint(DistributionPoint object, Collection collection, X509CRLSelector x509CRLSelector) throws AnnotatedException {
        block10 : {
            ArrayList<X500Name> arrayList;
            block9 : {
                block8 : {
                    arrayList = new ArrayList<X500Name>();
                    if (((DistributionPoint)object).getCRLIssuer() == null) break block8;
                    object = ((DistributionPoint)object).getCRLIssuer().getNames();
                    for (int i = 0; i < ((Object)object).length; ++i) {
                        if (((GeneralName)object[i]).getTagNo() != 4) continue;
                        try {
                            arrayList.add(X500Name.getInstance(((GeneralName)object[i]).getName().toASN1Primitive().getEncoded()));
                            continue;
                        }
                        catch (IOException iOException) {
                            throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", iOException);
                        }
                    }
                    break block9;
                }
                if (((DistributionPoint)object).getDistributionPoint() != null) {
                    object = collection.iterator();
                    while (object.hasNext()) {
                        arrayList.add((X500Name)object.next());
                    }
                }
                break block10;
            }
            object = arrayList.iterator();
            while (object.hasNext()) {
                try {
                    x509CRLSelector.addIssuerName(((X500Name)object.next()).getEncoded());
                }
                catch (IOException iOException) {
                    throw new AnnotatedException("Cannot decode CRL issuer information.", iOException);
                }
            }
            return;
        }
        throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
    }

    protected static void getCertStatus(Date date, X509CRL object, Object object2, CertStatus certStatus) throws AnnotatedException {
        block13 : {
            block12 : {
                X509CRLEntry x509CRLEntry;
                try {
                    boolean bl = X509CRLObject.isIndirectCRL((X509CRL)object);
                    if (!bl) break block12;
                    x509CRLEntry = ((X509CRL)object).getRevokedCertificate(CertPathValidatorUtilities.getSerialNumber(object2));
                    if (x509CRLEntry == null) {
                        return;
                    }
                    X500Principal x500Principal = x509CRLEntry.getCertificateIssuer();
                    object = x500Principal == null ? PrincipalUtils.getIssuerPrincipal((X509CRL)object) : X500Name.getInstance(x500Principal.getEncoded());
                }
                catch (CRLException cRLException) {
                    throw new AnnotatedException("Failed check for indirect CRL.", cRLException);
                }
                if (!PrincipalUtils.getEncodedIssuerPrincipal(object2).equals(object)) {
                    return;
                }
                object = x509CRLEntry;
                break block13;
            }
            if (!PrincipalUtils.getEncodedIssuerPrincipal(object2).equals(PrincipalUtils.getIssuerPrincipal((X509CRL)object))) {
                return;
            }
            object = object2 = ((X509CRL)object).getRevokedCertificate(CertPathValidatorUtilities.getSerialNumber(object2));
            if (object2 == null) {
                return;
            }
        }
        object2 = null;
        if (((X509CRLEntry)object).hasExtensions()) {
            try {
                object2 = ASN1Enumerated.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, Extension.reasonCode.getId()));
            }
            catch (Exception exception) {
                throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", exception);
            }
        }
        if (date.getTime() >= ((X509CRLEntry)object).getRevocationDate().getTime() || object2 == null || ((ASN1Enumerated)object2).getValue().intValue() == 0 || ((ASN1Enumerated)object2).getValue().intValue() == 1 || ((ASN1Enumerated)object2).getValue().intValue() == 2 || ((ASN1Enumerated)object2).getValue().intValue() == 8) {
            if (object2 != null) {
                certStatus.setCertStatus(((ASN1Enumerated)object2).getValue().intValue());
            } else {
                certStatus.setCertStatus(0);
            }
            certStatus.setRevocationDate(((X509CRLEntry)object).getRevocationDate());
        }
    }

    protected static Set getCompleteCRLs(DistributionPoint object, Object object2, Date date, PKIXExtendedParameters pKIXExtendedParameters) throws AnnotatedException {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        try {
            HashSet<X500Name> hashSet = new HashSet<X500Name>();
            hashSet.add(PrincipalUtils.getEncodedIssuerPrincipal(object2));
            CertPathValidatorUtilities.getCRLIssuersFromDistributionPoint((DistributionPoint)object, hashSet, x509CRLSelector);
        }
        catch (AnnotatedException annotatedException) {
            throw new AnnotatedException("Could not get issuer information from distribution point.", annotatedException);
        }
        if (object2 instanceof X509Certificate) {
            x509CRLSelector.setCertificateChecking((X509Certificate)object2);
        }
        object = new PKIXCRLStoreSelector.Builder(x509CRLSelector).setCompleteCRLEnabled(true).build();
        if (pKIXExtendedParameters.getDate() != null) {
            date = pKIXExtendedParameters.getDate();
        }
        object = CRL_UTIL.findCRLs((PKIXCRLStoreSelector)object, date, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores());
        CertPathValidatorUtilities.checkCRLsNotEmpty((Set)object, object2);
        return object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static Set getDeltaCRLs(Date cloneable, X509CRL object, List<CertStore> object2, List<PKIXCRLStore> list) throws AnnotatedException {
        byte[] arrby;
        X509CRLSelector x509CRLSelector;
        BigInteger bigInteger;
        block7 : {
            x509CRLSelector = new X509CRLSelector();
            x509CRLSelector.addIssuerName(PrincipalUtils.getIssuerPrincipal((X509CRL)object).getEncoded());
            bigInteger = null;
            arrby = CertPathValidatorUtilities.getExtensionValue((X509Extension)object, CRL_NUMBER);
            if (arrby == null) break block7;
            bigInteger = ASN1Integer.getInstance(arrby).getPositiveValue();
        }
        try {
            arrby = object.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
            object = bigInteger == null ? null : bigInteger.add(BigInteger.valueOf(1L));
            x509CRLSelector.setMinCRLNumber((BigInteger)object);
            object = new PKIXCRLStoreSelector.Builder(x509CRLSelector);
            ((PKIXCRLStoreSelector.Builder)object).setIssuingDistributionPoint(arrby);
            ((PKIXCRLStoreSelector.Builder)object).setIssuingDistributionPointEnabled(true);
            ((PKIXCRLStoreSelector.Builder)object).setMaxBaseCRLNumber(bigInteger);
            object = ((PKIXCRLStoreSelector.Builder)object).build();
        }
        catch (Exception exception) {
            throw new AnnotatedException("Issuing distribution point extension value could not be read.", exception);
        }
        object = CRL_UTIL.findCRLs((PKIXCRLStoreSelector)object, (Date)cloneable, (List)object2, list);
        cloneable = new HashSet();
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (X509CRL)object.next();
            if (!CertPathValidatorUtilities.isDeltaCRL((X509CRL)object2)) continue;
            cloneable.add(object2);
        }
        return cloneable;
        catch (Exception exception) {
            throw new AnnotatedException("CRL number extension could not be extracted from CRL.", exception);
        }
        catch (IOException iOException) {
            throw new AnnotatedException("Cannot extract issuer from CRL.", iOException);
        }
    }

    protected static ASN1Primitive getExtensionValue(X509Extension arrby, String string) throws AnnotatedException {
        if ((arrby = arrby.getExtensionValue(string)) == null) {
            return null;
        }
        return CertPathValidatorUtilities.getObject(string, arrby);
    }

    protected static PublicKey getNextWorkingKey(List object, int n, JcaJceHelper jcaJceHelper) throws CertPathValidatorException {
        PublicKey publicKey = ((Certificate)object.get(n)).getPublicKey();
        if (!(publicKey instanceof DSAPublicKey)) {
            return publicKey;
        }
        if ((publicKey = (DSAPublicKey)publicKey).getParams() != null) {
            return publicKey;
        }
        ++n;
        while (n < object.size()) {
            PublicKey publicKey2 = ((X509Certificate)object.get(n)).getPublicKey();
            if (publicKey2 instanceof DSAPublicKey) {
                if ((publicKey2 = (DSAPublicKey)publicKey2).getParams() == null) {
                    ++n;
                    continue;
                }
                object = publicKey2.getParams();
                object = new DSAPublicKeySpec(publicKey.getY(), object.getP(), object.getQ(), object.getG());
                try {
                    object = jcaJceHelper.createKeyFactory("DSA").generatePublic((KeySpec)object);
                    return object;
                }
                catch (Exception exception) {
                    throw new RuntimeException(exception.getMessage());
                }
            }
            throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
        }
        throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
    }

    private static ASN1Primitive getObject(String string, byte[] object) throws AnnotatedException {
        try {
            ASN1InputStream aSN1InputStream = new ASN1InputStream((byte[])object);
            object = (ASN1OctetString)aSN1InputStream.readObject();
            aSN1InputStream = new ASN1InputStream(((ASN1OctetString)object).getOctets());
            object = aSN1InputStream.readObject();
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("exception processing extension ");
            stringBuilder.append(string);
            throw new AnnotatedException(stringBuilder.toString(), exception);
        }
    }

    protected static final Set getQualifierSet(ASN1Sequence object) throws CertPathValidatorException {
        HashSet<PolicyQualifierInfo> hashSet = new HashSet<PolicyQualifierInfo>();
        if (object == null) {
            return hashSet;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1OutputStream aSN1OutputStream = new ASN1OutputStream(byteArrayOutputStream);
        object = ((ASN1Sequence)object).getObjects();
        while (object.hasMoreElements()) {
            try {
                aSN1OutputStream.writeObject((ASN1Encodable)object.nextElement());
                PolicyQualifierInfo policyQualifierInfo = new PolicyQualifierInfo(byteArrayOutputStream.toByteArray());
                hashSet.add(policyQualifierInfo);
                byteArrayOutputStream.reset();
            }
            catch (IOException iOException) {
                throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", iOException);
            }
        }
        return hashSet;
    }

    private static BigInteger getSerialNumber(Object object) {
        return ((X509Certificate)object).getSerialNumber();
    }

    protected static Date getValidCertDateFromValidityModel(PKIXExtendedParameters object, CertPath certPath, int n) throws AnnotatedException {
        if (((PKIXExtendedParameters)object).getValidityModel() == 1) {
            if (n <= 0) {
                return CertPathValidatorUtilities.getValidDate((PKIXExtendedParameters)object);
            }
            if (n - 1 == 0) {
                block10 : {
                    object = null;
                    byte[] arrby = ((X509Certificate)certPath.getCertificates().get(n - 1)).getExtensionValue(ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
                    if (arrby == null) break block10;
                    try {
                        object = ASN1GeneralizedTime.getInstance(ASN1Primitive.fromByteArray(arrby));
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw new AnnotatedException("Date of cert gen extension could not be read.");
                    }
                    catch (IOException iOException) {
                        throw new AnnotatedException("Date of cert gen extension could not be read.");
                    }
                }
                if (object != null) {
                    try {
                        object = ((ASN1GeneralizedTime)object).getDate();
                        return object;
                    }
                    catch (ParseException parseException) {
                        throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", parseException);
                    }
                }
                return ((X509Certificate)certPath.getCertificates().get(n - 1)).getNotBefore();
            }
            return ((X509Certificate)certPath.getCertificates().get(n - 1)).getNotBefore();
        }
        return CertPathValidatorUtilities.getValidDate((PKIXExtendedParameters)object);
    }

    protected static Date getValidDate(PKIXExtendedParameters cloneable) {
        Date date = cloneable.getDate();
        cloneable = date;
        if (date == null) {
            cloneable = new Date();
        }
        return cloneable;
    }

    protected static boolean isAnyPolicy(Set set) {
        boolean bl = set == null || set.contains(ANY_POLICY) || set.isEmpty();
        return bl;
    }

    private static boolean isDeltaCRL(X509CRL object) {
        if ((object = object.getCriticalExtensionOIDs()) == null) {
            return false;
        }
        return object.contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    }

    static boolean isIssuerTrustAnchor(X509Certificate object, Set set, String string) throws AnnotatedException {
        boolean bl = false;
        try {
            object = CertPathValidatorUtilities.findTrustAnchor((X509Certificate)object, set, string);
            if (object != null) {
                bl = true;
            }
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    protected static boolean isSelfIssued(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN());
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static void prepareNextCertB1(int n, List[] arrlist, String object, Map map, X509Certificate object2) throws AnnotatedException, CertPathValidatorException {
        PKIXPolicyNode pKIXPolicyNode22222;
        Object object3;
        block12 : {
            void var1_6;
            boolean bl;
            Enumeration enumeration;
            block11 : {
                for (PKIXPolicyNode pKIXPolicyNode22222 : arrlist[n]) {
                    if (!pKIXPolicyNode22222.getValidPolicy().equals(object)) continue;
                    pKIXPolicyNode22222.expectedPolicies = (Set)map.get(object);
                    bl = true;
                    break block11;
                }
                bl = false;
            }
            if (bl) return;
            object3 = arrlist[n].iterator();
            do {
                if (!object3.hasNext()) return;
            } while (!ANY_POLICY.equals((pKIXPolicyNode22222 = (PKIXPolicyNode)object3.next()).getValidPolicy()));
            object3 = CERTIFICATE_POLICIES;
            try {
                object3 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object2, (String)object3));
                enumeration = ((ASN1Sequence)object3).getObjects();
            }
            catch (Exception exception) {
                throw new AnnotatedException("Certificate policies cannot be decoded.", (Throwable)var1_6);
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw new AnnotatedException("Certificate policies cannot be decoded.", (Throwable)var1_6);
            while (enumeration.hasMoreElements()) {
                try {
                    object3 = PolicyInformation.getInstance(enumeration.nextElement());
                    if (!ANY_POLICY.equals(((PolicyInformation)object3).getPolicyIdentifier().getId())) continue;
                }
                catch (Exception exception) {
                    throw new AnnotatedException("Policy information cannot be decoded.", exception);
                }
                try {
                    object3 = CertPathValidatorUtilities.getQualifierSet(((PolicyInformation)object3).getPolicyQualifiers());
                    break block12;
                }
                catch (CertPathValidatorException certPathValidatorException) {
                    throw new ExtCertPathValidatorException("Policy qualifier info set could not be built.", certPathValidatorException);
                }
            }
            object3 = null;
        }
        boolean bl2 = object2.getCriticalExtensionOIDs() != null ? object2.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES) : false;
        object2 = (PKIXPolicyNode)pKIXPolicyNode22222.getParent();
        if (!ANY_POLICY.equals(((PKIXPolicyNode)object2).getValidPolicy())) return;
        object = new PKIXPolicyNode(new ArrayList(), n, (Set)map.get(object), (PolicyNode)object2, (Set)object3, (String)object, bl2);
        ((PKIXPolicyNode)object2).addChild((PKIXPolicyNode)object);
        arrlist[n].add(object);
    }

    protected static PKIXPolicyNode prepareNextCertB2(int n, List[] arrlist, String string, PKIXPolicyNode pKIXPolicyNode) {
        Iterator iterator = arrlist[n].iterator();
        while (iterator.hasNext()) {
            Object object = (PKIXPolicyNode)iterator.next();
            PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
            if (((PKIXPolicyNode)object).getValidPolicy().equals(string)) {
                ((PKIXPolicyNode)((PKIXPolicyNode)object).getParent()).removeChild((PKIXPolicyNode)object);
                iterator.remove();
                int n2 = n - 1;
                do {
                    pKIXPolicyNode2 = pKIXPolicyNode;
                    if (n2 < 0) break;
                    object = arrlist[n2];
                    int n3 = 0;
                    do {
                        pKIXPolicyNode2 = pKIXPolicyNode;
                        if (n3 >= object.size()) break;
                        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode)object.get(n3);
                        pKIXPolicyNode2 = pKIXPolicyNode;
                        if (!pKIXPolicyNode3.hasChildren()) {
                            pKIXPolicyNode2 = pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, arrlist, pKIXPolicyNode3);
                            if (pKIXPolicyNode == null) {
                                pKIXPolicyNode2 = pKIXPolicyNode;
                                break;
                            }
                        }
                        ++n3;
                        pKIXPolicyNode = pKIXPolicyNode2;
                    } while (true);
                    --n2;
                    pKIXPolicyNode = pKIXPolicyNode2;
                } while (true);
            }
            pKIXPolicyNode = pKIXPolicyNode2;
        }
        return pKIXPolicyNode;
    }

    protected static boolean processCertD1i(int n, List[] arrlist, ASN1ObjectIdentifier object, Set set) {
        Collection<String> collection = arrlist[n - 1];
        for (int i = 0; i < collection.size(); ++i) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode)collection.get(i);
            if (!pKIXPolicyNode.getExpectedPolicies().contains(((ASN1ObjectIdentifier)object).getId())) continue;
            collection = new HashSet();
            collection.add(((ASN1ObjectIdentifier)object).getId());
            object = new PKIXPolicyNode(new ArrayList(), n, (Set)collection, pKIXPolicyNode, set, ((ASN1ObjectIdentifier)object).getId(), false);
            pKIXPolicyNode.addChild((PKIXPolicyNode)object);
            arrlist[n].add(object);
            return true;
        }
        return false;
    }

    protected static void processCertD1ii(int n, List[] arrlist, ASN1ObjectIdentifier object, Set set) {
        Collection<String> collection = arrlist[n - 1];
        for (int i = 0; i < collection.size(); ++i) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode)collection.get(i);
            if (!ANY_POLICY.equals(pKIXPolicyNode.getValidPolicy())) continue;
            collection = new HashSet();
            collection.add(((ASN1ObjectIdentifier)object).getId());
            object = new PKIXPolicyNode(new ArrayList(), n, (Set)collection, pKIXPolicyNode, set, ((ASN1ObjectIdentifier)object).getId(), false);
            pKIXPolicyNode.addChild((PKIXPolicyNode)object);
            arrlist[n].add(object);
            return;
        }
    }

    protected static PKIXPolicyNode removePolicyNode(PKIXPolicyNode pKIXPolicyNode, List[] arrlist, PKIXPolicyNode pKIXPolicyNode2) {
        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode)pKIXPolicyNode2.getParent();
        if (pKIXPolicyNode == null) {
            return null;
        }
        if (pKIXPolicyNode3 == null) {
            for (int i = 0; i < arrlist.length; ++i) {
                arrlist[i] = new ArrayList();
            }
            return null;
        }
        pKIXPolicyNode3.removeChild(pKIXPolicyNode2);
        CertPathValidatorUtilities.removePolicyNodeRecurse(arrlist, pKIXPolicyNode2);
        return pKIXPolicyNode;
    }

    private static void removePolicyNodeRecurse(List[] arrlist, PKIXPolicyNode object) {
        arrlist[((PKIXPolicyNode)object).getDepth()].remove(object);
        if (((PKIXPolicyNode)object).hasChildren()) {
            object = ((PKIXPolicyNode)object).getChildren();
            while (object.hasNext()) {
                CertPathValidatorUtilities.removePolicyNodeRecurse(arrlist, (PKIXPolicyNode)object.next());
            }
        }
    }

    protected static void verifyX509Certificate(X509Certificate x509Certificate, PublicKey publicKey, String string) throws GeneralSecurityException {
        if (string == null) {
            x509Certificate.verify(publicKey);
        } else {
            x509Certificate.verify(publicKey, string);
        }
    }
}

