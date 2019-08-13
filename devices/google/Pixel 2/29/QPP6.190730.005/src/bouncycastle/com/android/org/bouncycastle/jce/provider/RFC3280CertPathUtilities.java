/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import com.android.org.bouncycastle.asn1.x500.RDN;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x500.style.BCStyle;
import com.android.org.bouncycastle.asn1.x509.BasicConstraints;
import com.android.org.bouncycastle.asn1.x509.CRLDistPoint;
import com.android.org.bouncycastle.asn1.x509.DistributionPoint;
import com.android.org.bouncycastle.asn1.x509.DistributionPointName;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.GeneralSubtree;
import com.android.org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import com.android.org.bouncycastle.asn1.x509.NameConstraints;
import com.android.org.bouncycastle.asn1.x509.PolicyInformation;
import com.android.org.bouncycastle.asn1.x509.ReasonFlags;
import com.android.org.bouncycastle.jcajce.PKIXCRLStore;
import com.android.org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import com.android.org.bouncycastle.jcajce.PKIXCertStore;
import com.android.org.bouncycastle.jcajce.PKIXCertStoreSelector;
import com.android.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import com.android.org.bouncycastle.jcajce.PKIXExtendedParameters;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import com.android.org.bouncycastle.jce.provider.AnnotatedException;
import com.android.org.bouncycastle.jce.provider.CertPathValidatorUtilities;
import com.android.org.bouncycastle.jce.provider.CertStatus;
import com.android.org.bouncycastle.jce.provider.PKIXCRLUtil;
import com.android.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi;
import com.android.org.bouncycastle.jce.provider.PKIXNameConstraintValidator;
import com.android.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException;
import com.android.org.bouncycastle.jce.provider.PKIXPolicyNode;
import com.android.org.bouncycastle.jce.provider.PrincipalUtils;
import com.android.org.bouncycastle.jce.provider.ReasonsMask;
import com.android.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PolicyNode;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

class RFC3280CertPathUtilities {
    public static final String ANY_POLICY = "2.5.29.32.0";
    public static final String AUTHORITY_KEY_IDENTIFIER;
    public static final String BASIC_CONSTRAINTS;
    public static final String CERTIFICATE_POLICIES;
    public static final String CRL_DISTRIBUTION_POINTS;
    public static final String CRL_NUMBER;
    protected static final int CRL_SIGN = 6;
    private static final PKIXCRLUtil CRL_UTIL;
    public static final String DELTA_CRL_INDICATOR;
    public static final String FRESHEST_CRL;
    public static final String INHIBIT_ANY_POLICY;
    public static final String ISSUING_DISTRIBUTION_POINT;
    protected static final int KEY_CERT_SIGN = 5;
    public static final String KEY_USAGE;
    public static final String NAME_CONSTRAINTS;
    public static final String POLICY_CONSTRAINTS;
    public static final String POLICY_MAPPINGS;
    public static final String SUBJECT_ALTERNATIVE_NAME;
    protected static final String[] crlReasons;

    static {
        CRL_UTIL = new PKIXCRLUtil();
        CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
        POLICY_MAPPINGS = Extension.policyMappings.getId();
        INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
        ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
        FRESHEST_CRL = Extension.freshestCRL.getId();
        DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
        POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
        BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
        CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
        SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
        NAME_CONSTRAINTS = Extension.nameConstraints.getId();
        AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
        KEY_USAGE = Extension.keyUsage.getId();
        CRL_NUMBER = Extension.cRLNumber.getId();
        crlReasons = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
    }

    RFC3280CertPathUtilities() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void checkCRL(DistributionPoint distributionPoint, PKIXExtendedParameters pKIXExtendedParameters, X509Certificate x509Certificate, Date date, X509Certificate x509Certificate2, PublicKey publicKey, CertStatus certStatus, ReasonsMask reasonsMask, List list, JcaJceHelper jcaJceHelper) throws AnnotatedException {
        Object object = reasonsMask;
        Date date2 = new Date(System.currentTimeMillis());
        if (date.getTime() > date2.getTime()) {
            throw new AnnotatedException("Validation time is in future.");
        }
        Set set = CertPathValidatorUtilities.getCompleteCRLs(distributionPoint, x509Certificate, date2, pKIXExtendedParameters);
        Iterator iterator = set.iterator();
        boolean bl = false;
        AnnotatedException annotatedException = null;
        while (iterator.hasNext() && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
            Object object2;
            Object object3;
            HashSet hashSet;
            try {
                object3 = (X509CRL)iterator.next();
                hashSet = RFC3280CertPathUtilities.processCRLD((X509CRL)object3, distributionPoint);
                boolean bl2 = ((ReasonsMask)((Object)hashSet)).hasNewReasons((ReasonsMask)object);
                if (!bl2) continue;
            }
            catch (AnnotatedException annotatedException4) {}
            try {
                PublicKey publicKey2 = RFC3280CertPathUtilities.processCRLG((X509CRL)object3, RFC3280CertPathUtilities.processCRLF((X509CRL)object3, x509Certificate, x509Certificate2, publicKey, pKIXExtendedParameters, list, jcaJceHelper));
                object2 = null;
                object = date2;
                if (pKIXExtendedParameters.getDate() != null) {
                    object = pKIXExtendedParameters.getDate();
                }
                if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                    object2 = RFC3280CertPathUtilities.processCRLH(CertPathValidatorUtilities.getDeltaCRLs((Date)object, (X509CRL)object3, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores()), publicKey2);
                }
                if (pKIXExtendedParameters.getValidityModel() != 1 && x509Certificate.getNotAfter().getTime() < ((X509CRL)object3).getThisUpdate().getTime()) {
                    object = new AnnotatedException("No valid CRL for current time found.");
                    throw object;
                }
                RFC3280CertPathUtilities.processCRLB1(distributionPoint, x509Certificate, (X509CRL)object3);
                RFC3280CertPathUtilities.processCRLB2(distributionPoint, x509Certificate, (X509CRL)object3);
                RFC3280CertPathUtilities.processCRLC((X509CRL)object2, (X509CRL)object3, pKIXExtendedParameters);
                RFC3280CertPathUtilities.processCRLI(date, (X509CRL)object2, x509Certificate, certStatus, pKIXExtendedParameters);
                RFC3280CertPathUtilities.processCRLJ(date, (X509CRL)object3, x509Certificate, certStatus);
                if (certStatus.getCertStatus() == 8) {
                    certStatus.setCertStatus(11);
                }
                object = reasonsMask;
            }
            catch (AnnotatedException annotatedException3) {
                object = reasonsMask;
                continue;
            }
            try {
                ((ReasonsMask)object).addReasons((ReasonsMask)((Object)hashSet));
                object3 = object3.getCriticalExtensionOIDs();
                if (object3 != null) {
                    hashSet = new HashSet(object3);
                    hashSet.remove(Extension.issuingDistributionPoint.getId());
                    hashSet.remove(Extension.deltaCRLIndicator.getId());
                    if (!hashSet.isEmpty()) {
                        annotatedException = new AnnotatedException("CRL contains unsupported critical extensions.");
                        throw annotatedException;
                    }
                }
                if (object2 != null && (object2 = object2.getCriticalExtensionOIDs()) != null) {
                    hashSet = new HashSet(object2);
                    hashSet.remove(Extension.issuingDistributionPoint.getId());
                    hashSet.remove(Extension.deltaCRLIndicator.getId());
                    if (!hashSet.isEmpty()) {
                        annotatedException = new AnnotatedException("Delta CRL contains unsupported critical extension.");
                        throw annotatedException;
                    }
                }
                bl = true;
            }
            catch (AnnotatedException annotatedException2) {}
        }
        if (bl) {
            return;
        }
        throw annotatedException;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static void checkCRLs(PKIXExtendedParameters object, X509Certificate serializable, Date date, X509Certificate x509Certificate, PublicKey publicKey, List list, JcaJceHelper jcaJceHelper) throws AnnotatedException {
        boolean bl;
        ReasonsMask reasonsMask;
        Object object2;
        Object object3;
        int n;
        block26 : {
            Object object4;
            Object object5;
            block28 : {
                DistributionPoint[] arrdistributionPoint;
                block25 : {
                    boolean bl2;
                    PKIXExtendedParameters.Builder builder;
                    void var0_7;
                    block24 : {
                        object3 = null;
                        try {
                            object5 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)((Object)serializable), CRL_DISTRIBUTION_POINTS));
                            builder = new PKIXExtendedParameters.Builder((PKIXExtendedParameters)object);
                        }
                        catch (Exception exception) {
                            throw new AnnotatedException("CRL distribution point extension could not be read.", exception);
                        }
                        try {
                            object2 = CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint((CRLDistPoint)object5, ((PKIXExtendedParameters)object).getNamedCRLStoreMap()).iterator();
                            break block24;
                        }
                        catch (AnnotatedException annotatedException) {
                            // empty catch block
                        }
                        throw new AnnotatedException("No additional CRL locations could be decoded from CRL distribution point extension.", (Throwable)var0_7);
                    }
                    while (bl2 = object2.hasNext()) {
                        try {
                            builder.addCRLStore(object2.next());
                        }
                        catch (AnnotatedException annotatedException) {
                            throw new AnnotatedException("No additional CRL locations could be decoded from CRL distribution point extension.", (Throwable)var0_7);
                        }
                    }
                    object2 = new CertStatus();
                    reasonsMask = new ReasonsMask();
                    object4 = builder.build();
                    bl = false;
                    n = 11;
                    if (object5 != null) {
                        try {
                            arrdistributionPoint = ((CRLDistPoint)object5).getDistributionPoints();
                            if (arrdistributionPoint != null) {
                                object3 = null;
                                bl = false;
                                break block25;
                            }
                            n = 11;
                        }
                        catch (Exception exception) {
                            throw new AnnotatedException("Distribution points could not be read.", exception);
                        }
                    }
                    n = 11;
                    break block28;
                }
                for (int i = 0; i < arrdistributionPoint.length && ((CertStatus)object2).getCertStatus() == n && !reasonsMask.isAllReasons(); ++i) {
                    DistributionPoint distributionPoint = arrdistributionPoint[i];
                    try {
                        RFC3280CertPathUtilities.checkCRL(distributionPoint, (PKIXExtendedParameters)object4, (X509Certificate)serializable, date, x509Certificate, publicKey, (CertStatus)object2, reasonsMask, list, jcaJceHelper);
                        bl = true;
                        continue;
                    }
                    catch (AnnotatedException annotatedException) {
                        // empty catch block
                    }
                }
            }
            if (((CertStatus)object2).getCertStatus() == n && !reasonsMask.isAllReasons()) {
                block27 : {
                    object5 = new ASN1InputStream(PrincipalUtils.getEncodedIssuerPrincipal(serializable).getEncoded());
                    object4 = ((ASN1InputStream)object5).readObject();
                    GeneralName generalName = new GeneralName(4, (ASN1Encodable)object4);
                    GeneralNames generalNames = new GeneralNames(generalName);
                    DistributionPointName distributionPointName = new DistributionPointName(0, generalNames);
                    object5 = new DistributionPoint(distributionPointName, null, null);
                    object = (PKIXExtendedParameters)((PKIXExtendedParameters)object).clone();
                    {
                        catch (AnnotatedException annotatedException) {
                            break block27;
                        }
                    }
                    try {
                        RFC3280CertPathUtilities.checkCRL((DistributionPoint)object5, (PKIXExtendedParameters)object, (X509Certificate)serializable, date, x509Certificate, publicKey, (CertStatus)object2, reasonsMask, list, jcaJceHelper);
                        bl = true;
                        break block26;
                        catch (Exception exception) {
                            serializable = new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", exception);
                            throw serializable;
                        }
                    }
                    catch (AnnotatedException annotatedException) {
                        // empty catch block
                    }
                }
                object3 = object;
            }
        }
        if (!bl) {
            if (!(object3 instanceof AnnotatedException)) throw new AnnotatedException("No valid CRL found.", (Throwable)object3);
            throw object3;
        }
        if (((CertStatus)object2).getCertStatus() != n) {
            object = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
            ((DateFormat)object).setTimeZone(TimeZone.getTimeZone("UTC"));
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Certificate revocation after ");
            ((StringBuilder)serializable).append(((DateFormat)object).format(((CertStatus)object2).getRevocationDate()));
            object = ((StringBuilder)serializable).toString();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append(", reason: ");
            ((StringBuilder)serializable).append(crlReasons[((CertStatus)object2).getCertStatus()]);
            throw new AnnotatedException(((StringBuilder)serializable).toString());
        }
        if (!reasonsMask.isAllReasons() && ((CertStatus)object2).getCertStatus() == n) {
            ((CertStatus)object2).setCertStatus(12);
        }
        if (((CertStatus)object2).getCertStatus() == 12) throw new AnnotatedException("Certificate status could not be determined.");
    }

    /*
     * WARNING - void declaration
     */
    protected static PKIXPolicyNode prepareCertB(CertPath certPath, int n, List[] arrlist, PKIXPolicyNode object, int n2) throws CertPathValidatorException {
        Object object2;
        block20 : {
            HashSet hashSet;
            int n3;
            Object object3;
            Object object42;
            object2 = certPath.getCertificates();
            X509Certificate x509Certificate = (X509Certificate)object2.get(n);
            int n4 = object2.size() - n;
            try {
                object3 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, POLICY_MAPPINGS));
                object2 = object;
                if (object3 == null) break block20;
                object2 = object3;
                object3 = new HashMap();
                hashSet = new HashSet();
            }
            catch (AnnotatedException annotatedException) {
                throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", annotatedException, certPath, n);
            }
            for (n3 = 0; n3 < ((ASN1Sequence)object2).size(); ++n3) {
                ASN1Sequence aSN1Sequence = (ASN1Sequence)((ASN1Sequence)object2).getObjectAt(n3);
                object42 = ((ASN1ObjectIdentifier)aSN1Sequence.getObjectAt(0)).getId();
                String string = ((ASN1ObjectIdentifier)aSN1Sequence.getObjectAt(1)).getId();
                if (!object3.containsKey(object42)) {
                    HashSet<Object> hashSet2 = new HashSet<Object>();
                    hashSet2.add(string);
                    object3.put(object42, hashSet2);
                    hashSet.add(object42);
                    continue;
                }
                ((Set)object3.get(object42)).add(string);
            }
            block9 : for (String string : hashSet) {
                Object object5;
                Object object6;
                if (n2 > 0) {
                    block21 : {
                        for (Object object42 : arrlist[n4]) {
                            if (!((PKIXPolicyNode)object42).getValidPolicy().equals(string)) continue;
                            ((PKIXPolicyNode)object42).expectedPolicies = (Set)object3.get(string);
                            n3 = 1;
                            break block21;
                        }
                        n3 = 0;
                    }
                    if (n3 != 0) continue;
                    object42 = arrlist[n4].iterator();
                    while (object42.hasNext()) {
                        block22 : {
                            object5 = (PKIXPolicyNode)object42.next();
                            if (!ANY_POLICY.equals(((PKIXPolicyNode)object5).getValidPolicy())) continue;
                            try {
                                object42 = (ASN1Sequence)CertPathValidatorUtilities.getExtensionValue(x509Certificate, CERTIFICATE_POLICIES);
                                object6 = ((ASN1Sequence)object42).getObjects();
                            }
                            catch (AnnotatedException annotatedException) {
                                throw new ExtCertPathValidatorException("Certificate policies extension could not be decoded.", annotatedException, certPath, n);
                            }
                            while (object6.hasMoreElements()) {
                                try {
                                    object42 = PolicyInformation.getInstance(object6.nextElement());
                                    if (!ANY_POLICY.equals(((PolicyInformation)object42).getPolicyIdentifier().getId())) continue;
                                }
                                catch (Exception exception) {
                                    throw new CertPathValidatorException("Policy information could not be decoded.", exception, certPath, n);
                                }
                                try {
                                    object42 = CertPathValidatorUtilities.getQualifierSet(((PolicyInformation)object42).getPolicyQualifiers());
                                    break block22;
                                }
                                catch (CertPathValidatorException certPathValidatorException) {
                                    throw new ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", certPathValidatorException, certPath, n);
                                }
                            }
                            object42 = null;
                        }
                        boolean bl = x509Certificate.getCriticalExtensionOIDs() != null ? x509Certificate.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES) : false;
                        if (!ANY_POLICY.equals(((PKIXPolicyNode)(object5 = (PKIXPolicyNode)((PKIXPolicyNode)object5).getParent())).getValidPolicy())) continue block9;
                        object42 = new PKIXPolicyNode(new ArrayList(), n4, (Set)object3.get(string), (PolicyNode)object5, (Set)object42, string, bl);
                        ((PKIXPolicyNode)object5).addChild((PKIXPolicyNode)object42);
                        arrlist[n4].add(object42);
                        continue block9;
                    }
                    continue;
                }
                object42 = string;
                if (n2 > 0) continue;
                object5 = arrlist[n4].iterator();
                while (object5.hasNext()) {
                    void var11_28;
                    object6 = (PKIXPolicyNode)object5.next();
                    Object object7 = object;
                    if (((PKIXPolicyNode)object6).getValidPolicy().equals(object42)) {
                        ((PKIXPolicyNode)((PKIXPolicyNode)object6).getParent()).removeChild((PKIXPolicyNode)object6);
                        object5.remove();
                        n3 = n4 - 1;
                        do {
                            Object object8 = object;
                            if (n3 < 0) break;
                            object6 = arrlist[n3];
                            for (int i = 0; i < object6.size(); ++i) {
                                void var11_27;
                                PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode)object6.get(i);
                                Object object9 = object;
                                if (!pKIXPolicyNode.hasChildren()) {
                                    Object object10 = object = CertPathValidatorUtilities.removePolicyNode((PKIXPolicyNode)object, arrlist, pKIXPolicyNode);
                                    if (object == null) break;
                                }
                                object = var11_27;
                            }
                            --n3;
                        } while (true);
                    }
                    object = var11_28;
                }
            }
            object2 = object;
        }
        return object2;
    }

    protected static void prepareNextCertA(CertPath certPath, int n) throws CertPathValidatorException {
        block5 : {
            ASN1Sequence aSN1Sequence;
            Object object = (X509Certificate)certPath.getCertificates().get(n);
            try {
                aSN1Sequence = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, POLICY_MAPPINGS));
                if (aSN1Sequence == null) break block5;
            }
            catch (AnnotatedException annotatedException) {
                throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", annotatedException, certPath, n);
            }
            for (int i = 0; i < aSN1Sequence.size(); ++i) {
                block6 : {
                    block7 : {
                        try {
                            ASN1Primitive aSN1Primitive = DERSequence.getInstance(aSN1Sequence.getObjectAt(i));
                            object = ASN1ObjectIdentifier.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(0));
                            aSN1Primitive = ASN1ObjectIdentifier.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(1));
                            if (ANY_POLICY.equals(((ASN1ObjectIdentifier)object).getId())) break block6;
                            if (ANY_POLICY.equals(((ASN1ObjectIdentifier)aSN1Primitive).getId())) break block7;
                        }
                        catch (Exception exception) {
                            throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", exception, certPath, n);
                        }
                        continue;
                    }
                    throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy,", null, certPath, n);
                }
                throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", null, certPath, n);
            }
        }
        return;
    }

    protected static void prepareNextCertG(CertPath certPath, int n, PKIXNameConstraintValidator pKIXNameConstraintValidator) throws CertPathValidatorException {
        GeneralSubtree[] arrgeneralSubtree;
        GeneralSubtree[] arrgeneralSubtree2;
        block11 : {
            arrgeneralSubtree2 = (X509Certificate)certPath.getCertificates().get(n);
            arrgeneralSubtree = null;
            arrgeneralSubtree2 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)arrgeneralSubtree2, NAME_CONSTRAINTS));
            if (arrgeneralSubtree2 == null) break block11;
            try {
                arrgeneralSubtree = NameConstraints.getInstance(arrgeneralSubtree2);
            }
            catch (Exception exception) {
                throw new ExtCertPathValidatorException("Name constraints extension could not be decoded.", exception, certPath, n);
            }
        }
        if (arrgeneralSubtree != null) {
            arrgeneralSubtree2 = arrgeneralSubtree.getPermittedSubtrees();
            if (arrgeneralSubtree2 != null) {
                try {
                    pKIXNameConstraintValidator.intersectPermittedSubtree(arrgeneralSubtree2);
                }
                catch (Exception exception) {
                    throw new ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", exception, certPath, n);
                }
            }
            if ((arrgeneralSubtree = arrgeneralSubtree.getExcludedSubtrees()) != null) {
                for (int i = 0; i != arrgeneralSubtree.length; ++i) {
                    try {
                        pKIXNameConstraintValidator.addExcludedSubtree(arrgeneralSubtree[i]);
                    }
                    catch (Exception exception) {
                        throw new ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", exception, certPath, n);
                    }
                }
            }
        }
        return;
    }

    protected static int prepareNextCertH1(CertPath certPath, int n, int n2) {
        if (!CertPathValidatorUtilities.isSelfIssued((X509Certificate)certPath.getCertificates().get(n)) && n2 != 0) {
            return n2 - 1;
        }
        return n2;
    }

    protected static int prepareNextCertH2(CertPath certPath, int n, int n2) {
        if (!CertPathValidatorUtilities.isSelfIssued((X509Certificate)certPath.getCertificates().get(n)) && n2 != 0) {
            return n2 - 1;
        }
        return n2;
    }

    protected static int prepareNextCertH3(CertPath certPath, int n, int n2) {
        if (!CertPathValidatorUtilities.isSelfIssued((X509Certificate)certPath.getCertificates().get(n)) && n2 != 0) {
            return n2 - 1;
        }
        return n2;
    }

    protected static int prepareNextCertI1(CertPath certPath, int n, int n2) throws CertPathValidatorException {
        block5 : {
            Object object = (X509Certificate)certPath.getCertificates().get(n);
            try {
                object = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, POLICY_CONSTRAINTS));
                if (object == null) break block5;
                object = ((ASN1Sequence)object).getObjects();
            }
            catch (Exception exception) {
                throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", exception, certPath, n);
            }
            while (object.hasMoreElements()) {
                try {
                    ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(object.nextElement());
                    if (aSN1TaggedObject.getTagNo() != 0) continue;
                    int n3 = ASN1Integer.getInstance(aSN1TaggedObject, false).getValue().intValue();
                    if (n3 >= n2) break;
                    return n3;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", illegalArgumentException, certPath, n);
                }
            }
        }
        return n2;
    }

    protected static int prepareNextCertI2(CertPath certPath, int n, int n2) throws CertPathValidatorException {
        block5 : {
            Object object = (X509Certificate)certPath.getCertificates().get(n);
            try {
                object = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, POLICY_CONSTRAINTS));
                if (object == null) break block5;
                object = ((ASN1Sequence)object).getObjects();
            }
            catch (Exception exception) {
                throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", exception, certPath, n);
            }
            while (object.hasMoreElements()) {
                try {
                    ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(object.nextElement());
                    if (aSN1TaggedObject.getTagNo() != 1) continue;
                    int n3 = ASN1Integer.getInstance(aSN1TaggedObject, false).getValue().intValue();
                    if (n3 >= n2) break;
                    return n3;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", illegalArgumentException, certPath, n);
                }
            }
        }
        return n2;
    }

    protected static int prepareNextCertJ(CertPath certPath, int n, int n2) throws CertPathValidatorException {
        Object object = (X509Certificate)certPath.getCertificates().get(n);
        try {
            object = ASN1Integer.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, INHIBIT_ANY_POLICY));
            if (object != null && (n = ((ASN1Integer)object).getValue().intValue()) < n2) {
                return n;
            }
            return n2;
        }
        catch (Exception exception) {
            throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", exception, certPath, n);
        }
    }

    protected static void prepareNextCertK(CertPath certPath, int n) throws CertPathValidatorException {
        block2 : {
            block3 : {
                Object object = (X509Certificate)certPath.getCertificates().get(n);
                try {
                    object = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, BASIC_CONSTRAINTS));
                    if (object == null) break block2;
                    if (!((BasicConstraints)object).isCA()) break block3;
                    return;
                }
                catch (Exception exception) {
                    throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", exception, certPath, n);
                }
            }
            throw new CertPathValidatorException("Not a CA certificate");
        }
        throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints");
    }

    protected static int prepareNextCertL(CertPath certPath, int n, int n2) throws CertPathValidatorException {
        if (!CertPathValidatorUtilities.isSelfIssued((X509Certificate)certPath.getCertificates().get(n))) {
            if (n2 > 0) {
                return n2 - 1;
            }
            throw new ExtCertPathValidatorException("Max path length not greater than zero", null, certPath, n);
        }
        return n2;
    }

    protected static int prepareNextCertM(CertPath serializable, int n, int n2) throws CertPathValidatorException {
        Object object = (X509Certificate)((CertPath)serializable).getCertificates().get(n);
        try {
            object = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, BASIC_CONSTRAINTS));
            if (object != null && (serializable = ((BasicConstraints)object).getPathLenConstraint()) != null && (n = ((BigInteger)serializable).intValue()) < n2) {
                return n;
            }
            return n2;
        }
        catch (Exception exception) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", exception, (CertPath)serializable, n);
        }
    }

    protected static void prepareNextCertN(CertPath certPath, int n) throws CertPathValidatorException {
        boolean[] arrbl = ((X509Certificate)certPath.getCertificates().get(n)).getKeyUsage();
        if (arrbl != null && !arrbl[5]) {
            throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", null, certPath, n);
        }
    }

    protected static void prepareNextCertO(CertPath certPath, int n, Set set, List object) throws CertPathValidatorException {
        X509Certificate x509Certificate = (X509Certificate)certPath.getCertificates().get(n);
        object = object.iterator();
        while (object.hasNext()) {
            try {
                ((PKIXCertPathChecker)object.next()).check(x509Certificate, set);
            }
            catch (CertPathValidatorException certPathValidatorException) {
                throw new CertPathValidatorException(certPathValidatorException.getMessage(), certPathValidatorException.getCause(), certPath, n);
            }
        }
        if (set.isEmpty()) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Certificate has unsupported critical extension: ");
        ((StringBuilder)object).append(set);
        throw new ExtCertPathValidatorException(((StringBuilder)object).toString(), null, certPath, n);
    }

    protected static Set processCRLA1i(Date date, PKIXExtendedParameters pKIXExtendedParameters, X509Certificate object, X509CRL x509CRL) throws AnnotatedException {
        HashSet hashSet = new HashSet();
        if (pKIXExtendedParameters.isUseDeltasEnabled()) {
            Object object2;
            block10 : {
                try {
                    object = object2 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, FRESHEST_CRL));
                    if (object2 != null) break block10;
                }
                catch (AnnotatedException annotatedException) {
                    throw new AnnotatedException("Freshest CRL extension could not be decoded from certificate.", annotatedException);
                }
                try {
                    object = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509CRL, FRESHEST_CRL));
                }
                catch (AnnotatedException annotatedException) {
                    throw new AnnotatedException("Freshest CRL extension could not be decoded from CRL.", annotatedException);
                }
            }
            if (object != null) {
                object2 = new ArrayList();
                object2.addAll(pKIXExtendedParameters.getCRLStores());
                try {
                    object2.addAll(CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint((CRLDistPoint)object, pKIXExtendedParameters.getNamedCRLStoreMap()));
                }
                catch (AnnotatedException annotatedException) {
                    throw new AnnotatedException("No new delta CRL locations could be added from Freshest CRL extension.", annotatedException);
                }
                try {
                    hashSet.addAll(CertPathValidatorUtilities.getDeltaCRLs(date, x509CRL, pKIXExtendedParameters.getCertStores(), (List<PKIXCRLStore>)object2));
                }
                catch (AnnotatedException annotatedException) {
                    throw new AnnotatedException("Exception obtaining delta CRLs.", annotatedException);
                }
            }
        }
        return hashSet;
    }

    protected static Set[] processCRLA1ii(Date serializable, PKIXExtendedParameters pKIXExtendedParameters, X509Certificate object, X509CRL x509CRL) throws AnnotatedException {
        HashSet hashSet;
        block5 : {
            hashSet = new HashSet();
            X509CRLSelector x509CRLSelector = new X509CRLSelector();
            x509CRLSelector.setCertificateChecking((X509Certificate)object);
            try {
                x509CRLSelector.addIssuerName(PrincipalUtils.getIssuerPrincipal(x509CRL).getEncoded());
                object = new PKIXCRLStoreSelector.Builder(x509CRLSelector).setCompleteCRLEnabled(true).build();
                if (pKIXExtendedParameters.getDate() != null) {
                    serializable = pKIXExtendedParameters.getDate();
                }
                object = CRL_UTIL.findCRLs((PKIXCRLStoreSelector)object, (Date)serializable, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores());
                if (!pKIXExtendedParameters.isUseDeltasEnabled()) break block5;
            }
            catch (IOException iOException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Cannot extract issuer from CRL.");
                ((StringBuilder)serializable).append(iOException);
                throw new AnnotatedException(((StringBuilder)serializable).toString(), iOException);
            }
            try {
                hashSet.addAll(CertPathValidatorUtilities.getDeltaCRLs((Date)serializable, x509CRL, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores()));
            }
            catch (AnnotatedException annotatedException) {
                throw new AnnotatedException("Exception obtaining delta CRLs.", annotatedException);
            }
        }
        return new Set[]{object, hashSet};
    }

    protected static void processCRLB1(DistributionPoint arrgeneralName, Object object, X509CRL x509CRL) throws AnnotatedException {
        int n;
        block14 : {
            int n2;
            block13 : {
                byte[] arrby = CertPathValidatorUtilities.getExtensionValue(x509CRL, ISSUING_DISTRIBUTION_POINT);
                int n3 = n = 0;
                if (arrby != null) {
                    n3 = n;
                    if (IssuingDistributionPoint.getInstance(arrby).isIndirectCRL()) {
                        n3 = 1;
                    }
                }
                try {
                    arrby = PrincipalUtils.getIssuerPrincipal(x509CRL).getEncoded();
                    n2 = 0;
                    n = 0;
                    if (arrgeneralName.getCRLIssuer() == null) break block13;
                    arrgeneralName = arrgeneralName.getCRLIssuer().getNames();
                }
                catch (IOException iOException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Exception encoding CRL issuer: ");
                    ((StringBuilder)object).append(iOException.getMessage());
                    throw new AnnotatedException(((StringBuilder)object).toString(), iOException);
                }
                for (n2 = 0; n2 < arrgeneralName.length; ++n2) {
                    int n4 = n;
                    if (arrgeneralName[n2].getTagNo() == 4) {
                        try {
                            boolean bl = Arrays.areEqual(arrgeneralName[n2].getName().toASN1Primitive().getEncoded(), arrby);
                            if (bl) {
                                n = 1;
                            }
                            n4 = n;
                        }
                        catch (IOException iOException) {
                            throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", iOException);
                        }
                    }
                    n = n4;
                }
                if (n != 0 && n3 == 0) {
                    throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
                }
                if (n == 0) {
                    throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
                }
                break block14;
            }
            n = n2;
            if (PrincipalUtils.getIssuerPrincipal(x509CRL).equals(PrincipalUtils.getEncodedIssuerPrincipal(object))) {
                n = 1;
            }
        }
        if (n != 0) {
            return;
        }
        throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static void processCRLB2(DistributionPoint aSN1Object, Object object, X509CRL object2) throws AnnotatedException {
        IssuingDistributionPoint issuingDistributionPoint;
        block24 : {
            boolean bl4;
            int n;
            boolean bl;
            ArrayList<GeneralName> arrayList;
            block27 : {
                boolean bl2;
                block28 : {
                    Object object3;
                    DistributionPointName distributionPointName;
                    block29 : {
                        block25 : {
                            try {
                                issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object2, ISSUING_DISTRIBUTION_POINT));
                                if (issuingDistributionPoint == null) return;
                                if (issuingDistributionPoint.getDistributionPoint() == null) break block24;
                                distributionPointName = IssuingDistributionPoint.getInstance(issuingDistributionPoint).getDistributionPoint();
                                arrayList = new ArrayList<GeneralName>();
                                if (distributionPointName.getType() != 0) break block25;
                                object3 = GeneralNames.getInstance(distributionPointName.getName()).getNames();
                            }
                            catch (Exception exception) {
                                throw new AnnotatedException("Issuing distribution point extension could not be decoded.", exception);
                            }
                            for (n = 0; n < ((GeneralName[])object3).length; ++n) {
                                arrayList.add(object3[n]);
                            }
                        }
                        if (distributionPointName.getType() == 1) {
                            object3 = new ASN1EncodableVector();
                            try {
                                object2 = ASN1Sequence.getInstance(PrincipalUtils.getIssuerPrincipal((X509CRL)object2)).getObjects();
                                while (object2.hasMoreElements()) {
                                    ((ASN1EncodableVector)object3).add((ASN1Encodable)object2.nextElement());
                                }
                                ((ASN1EncodableVector)object3).add(distributionPointName.getName());
                                arrayList.add(new GeneralName(X500Name.getInstance(new DERSequence((ASN1EncodableVector)object3))));
                            }
                            catch (Exception exception) {
                                throw new AnnotatedException("Could not read CRL issuer.", exception);
                            }
                        }
                        bl = false;
                        bl2 = false;
                        if (((DistributionPoint)aSN1Object).getDistributionPoint() == null) break block27;
                        distributionPointName = ((DistributionPoint)aSN1Object).getDistributionPoint();
                        object2 = null;
                        if (distributionPointName.getType() == 0) {
                            object2 = GeneralNames.getInstance(distributionPointName.getName()).getNames();
                        }
                        if (distributionPointName.getType() != 1) break block28;
                        if (((DistributionPoint)aSN1Object).getCRLIssuer() != null) {
                            aSN1Object = ((DistributionPoint)aSN1Object).getCRLIssuer().getNames();
                        } else {
                            aSN1Object = new GeneralName(X500Name.getInstance(PrincipalUtils.getEncodedIssuerPrincipal(object).getEncoded()));
                            aSN1Object = new GeneralName[]{aSN1Object};
                        }
                        break block29;
                        catch (Exception exception) {
                            throw new AnnotatedException("Could not read certificate issuer.", exception);
                        }
                    }
                    n = 0;
                    do {
                        object2 = aSN1Object;
                        if (n >= ((ASN1Object)aSN1Object).length) break;
                        object2 = ASN1Sequence.getInstance(((GeneralName)aSN1Object[n]).getName().toASN1Primitive()).getObjects();
                        object3 = new ASN1EncodableVector();
                        while (object2.hasMoreElements()) {
                            ((ASN1EncodableVector)object3).add((ASN1Encodable)object2.nextElement());
                        }
                        ((ASN1EncodableVector)object3).add(distributionPointName.getName());
                        aSN1Object[n] = new GeneralName(X500Name.getInstance(new DERSequence((ASN1EncodableVector)object3)));
                        ++n;
                    } while (true);
                }
                boolean bl3 = bl2;
                if (object2 != null) {
                    n = 0;
                    do {
                        bl3 = bl2;
                        if (n >= ((Object)object2).length) break;
                        if (arrayList.contains(object2[n])) {
                            bl3 = true;
                            break;
                        }
                        ++n;
                    } while (true);
                }
                if (!bl3) throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                break block24;
            }
            if (((DistributionPoint)aSN1Object).getCRLIssuer() == null) throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
            aSN1Object = ((DistributionPoint)aSN1Object).getCRLIssuer().getNames();
            n = 0;
            do {
                bl4 = bl;
                if (n >= ((ASN1Object)aSN1Object).length) break;
                if (arrayList.contains(aSN1Object[n])) {
                    bl4 = true;
                    break;
                }
                ++n;
            } while (true);
            if (!bl4) throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
        }
        try {
            aSN1Object = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, BASIC_CONSTRAINTS));
        }
        catch (Exception exception) {
            throw new AnnotatedException("Basic constraints extension could not be decoded.", exception);
        }
        if (object instanceof X509Certificate) {
            if (issuingDistributionPoint.onlyContainsUserCerts() && aSN1Object != null) {
                if (((BasicConstraints)aSN1Object).isCA()) throw new AnnotatedException("CA Cert CRL only contains user certificates.");
            }
            if (issuingDistributionPoint.onlyContainsCACerts()) {
                if (aSN1Object == null) throw new AnnotatedException("End CRL only contains CA certificates.");
                if (!((BasicConstraints)aSN1Object).isCA()) throw new AnnotatedException("End CRL only contains CA certificates.");
            }
        }
        if (issuingDistributionPoint.onlyContainsAttributeCerts()) throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static void processCRLC(X509CRL var0, X509CRL var1_5, PKIXExtendedParameters var2_6) throws AnnotatedException {
        block10 : {
            if (var0 == null) {
                return;
            }
            try {
                var3_7 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)var1_5, RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT));
                if (var2_6.isUseDeltasEnabled() == false) return;
                if (PrincipalUtils.getIssuerPrincipal((X509CRL)var0).equals(PrincipalUtils.getIssuerPrincipal((X509CRL)var1_5)) == false) throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
            }
            catch (Exception var0_4) {
                throw new AnnotatedException("Issuing distribution point extension could not be decoded.", var0_4);
            }
            try {
                var2_6 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)var0, RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT));
                var4_8 = false;
                if (var3_7 != null) break block10;
                if (var2_6 != null) ** GOTO lbl21
                var4_8 = true;
            }
            catch (Exception var0_3) {
                throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", var0_3);
            }
        }
        if (var3_7.equals(var2_6)) {
            var4_8 = true;
        }
lbl21: // 5 sources:
        if (var4_8 == false) throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
        try {
            var1_5 = CertPathValidatorUtilities.getExtensionValue((X509Extension)var1_5, RFC3280CertPathUtilities.AUTHORITY_KEY_IDENTIFIER);
        }
        catch (AnnotatedException var0_2) {
            throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", var0_2);
        }
        try {
            var0 = CertPathValidatorUtilities.getExtensionValue((X509Extension)var0, RFC3280CertPathUtilities.AUTHORITY_KEY_IDENTIFIER);
            if (var1_5 == null) throw new AnnotatedException("CRL authority key identifier is null.");
            if (var0 == null) throw new AnnotatedException("Delta CRL authority key identifier is null.");
            if (var1_5.equals(var0) == false) throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
            return;
        }
        catch (AnnotatedException var0_1) {
            throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", var0_1);
        }
    }

    protected static ReasonsMask processCRLD(X509CRL object, DistributionPoint object2) throws AnnotatedException {
        try {
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, ISSUING_DISTRIBUTION_POINT));
            if (issuingDistributionPoint != null && issuingDistributionPoint.getOnlySomeReasons() != null && ((DistributionPoint)object2).getReasons() != null) {
                return new ReasonsMask(((DistributionPoint)object2).getReasons()).intersect(new ReasonsMask(issuingDistributionPoint.getOnlySomeReasons()));
            }
            if ((issuingDistributionPoint == null || issuingDistributionPoint.getOnlySomeReasons() == null) && ((DistributionPoint)object2).getReasons() == null) {
                return ReasonsMask.allReasons;
            }
            object = ((DistributionPoint)object2).getReasons() == null ? ReasonsMask.allReasons : new ReasonsMask(((DistributionPoint)object2).getReasons());
            object2 = issuingDistributionPoint == null ? ReasonsMask.allReasons : new ReasonsMask(issuingDistributionPoint.getOnlySomeReasons());
            return ((ReasonsMask)object).intersect((ReasonsMask)object2);
        }
        catch (Exception exception) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", exception);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static Set processCRLF(X509CRL object, Object cloneable, X509Certificate serializable, PublicKey arrbl, PKIXExtendedParameters pKIXExtendedParameters, List list, JcaJceHelper jcaJceHelper) throws AnnotatedException {
        cloneable = new X509CertSelector();
        try {
            ((X509CertSelector)cloneable).setSubject(PrincipalUtils.getIssuerPrincipal((X509CRL)object).getEncoded());
            cloneable = new PKIXCertStoreSelector.Builder((CertSelector)cloneable).build();
        }
        catch (IOException iOException) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", iOException);
        }
        try {
            object = CertPathValidatorUtilities.findCertificates((PKIXCertStoreSelector)cloneable, pKIXExtendedParameters.getCertificateStores());
            object.addAll(CertPathValidatorUtilities.findCertificates((PKIXCertStoreSelector)cloneable, pKIXExtendedParameters.getCertStores()));
            object.add(serializable);
        }
        catch (AnnotatedException annotatedException) {
            throw new AnnotatedException("Issuer certificate for CRL cannot be searched.", annotatedException);
        }
        object = object.iterator();
        cloneable = new ArrayList();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        while (object.hasNext()) {
            void var0_11;
            void var0_13;
            void var0_15;
            X509Certificate x509Certificate = (X509Certificate)object.next();
            if (x509Certificate.equals(serializable)) {
                cloneable.add(x509Certificate);
                arrayList.add(arrbl);
                continue;
            }
            Object object2 = new PKIXCertPathBuilderSpi();
            Object object3 = new X509CertSelector();
            ((X509CertSelector)object3).setCertificate(x509Certificate);
            PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXExtendedParameters);
            PKIXCertStoreSelector.Builder builder2 = new PKIXCertStoreSelector.Builder((CertSelector)object3);
            builder = builder.setTargetConstraints(builder2.build());
            if (list.contains(x509Certificate)) {
                builder.setRevocationEnabled(false);
            } else {
                builder.setRevocationEnabled(true);
            }
            object3 = new PKIXExtendedBuilderParameters.Builder(builder.build());
            object2 = ((PKIXCertPathBuilderSpi)object2).engineBuild(((PKIXExtendedBuilderParameters.Builder)object3).build()).getCertPath().getCertificates();
            cloneable.add(x509Certificate);
            try {
                arrayList.add(CertPathValidatorUtilities.getNextWorkingKey((List)object2, 0, jcaJceHelper));
                continue;
            }
            catch (Exception exception) {
                throw new AnnotatedException(var0_11.getMessage());
            }
            catch (CertPathValidatorException certPathValidatorException) {
                throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", (Throwable)var0_13);
            }
            catch (CertPathBuilderException certPathBuilderException) {
                throw new AnnotatedException("CertPath for CRL signer failed to validate.", (Throwable)var0_15);
            }
            catch (Exception exception) {
                throw new AnnotatedException(var0_11.getMessage());
            }
            catch (CertPathValidatorException certPathValidatorException) {
                throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", (Throwable)var0_13);
            }
            catch (CertPathBuilderException certPathBuilderException) {
                throw new AnnotatedException("CertPath for CRL signer failed to validate.", (Throwable)var0_15);
            }
            catch (Exception exception) {
                throw new AnnotatedException(var0_11.getMessage());
            }
            catch (CertPathValidatorException certPathValidatorException) {
                throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", (Throwable)var0_13);
            }
            catch (CertPathBuilderException certPathBuilderException) {
                throw new AnnotatedException("CertPath for CRL signer failed to validate.", (Throwable)var0_15);
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw new AnnotatedException(var0_11.getMessage());
            catch (CertPathValidatorException certPathValidatorException) {
                // empty catch block
            }
            throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", (Throwable)var0_13);
            catch (CertPathBuilderException certPathBuilderException) {
                // empty catch block
            }
            throw new AnnotatedException("CertPath for CRL signer failed to validate.", (Throwable)var0_15);
        }
        serializable = new HashSet();
        object = null;
        for (int i = 0; i < cloneable.size(); ++i) {
            arrbl = ((X509Certificate)cloneable.get(i)).getKeyUsage();
            if (!(arrbl == null || arrbl.length >= 7 && arrbl[6])) {
                object = new AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
                continue;
            }
            serializable.add(arrayList.get(i));
        }
        if (serializable.isEmpty()) {
            if (object == null) throw new AnnotatedException("Cannot find a valid issuer certificate.");
        }
        if (!serializable.isEmpty()) return serializable;
        if (object != null) throw object;
        return serializable;
    }

    protected static PublicKey processCRLG(X509CRL x509CRL, Set object) throws AnnotatedException {
        Object var2_3 = null;
        Iterator iterator = object.iterator();
        object = var2_3;
        while (iterator.hasNext()) {
            object = (PublicKey)iterator.next();
            try {
                x509CRL.verify((PublicKey)object);
                return object;
            }
            catch (Exception exception) {
            }
        }
        throw new AnnotatedException("Cannot verify CRL.", (Throwable)object);
    }

    protected static X509CRL processCRLH(Set object, PublicKey publicKey) throws AnnotatedException {
        Object var2_3 = null;
        Iterator iterator = object.iterator();
        object = var2_3;
        while (iterator.hasNext()) {
            object = (X509CRL)iterator.next();
            try {
                ((X509CRL)object).verify(publicKey);
                return object;
            }
            catch (Exception exception) {
            }
        }
        if (object == null) {
            return null;
        }
        throw new AnnotatedException("Cannot verify delta CRL.", (Throwable)object);
    }

    protected static void processCRLI(Date date, X509CRL x509CRL, Object object, CertStatus certStatus, PKIXExtendedParameters pKIXExtendedParameters) throws AnnotatedException {
        if (pKIXExtendedParameters.isUseDeltasEnabled() && x509CRL != null) {
            CertPathValidatorUtilities.getCertStatus(date, x509CRL, object, certStatus);
        }
    }

    protected static void processCRLJ(Date date, X509CRL x509CRL, Object object, CertStatus certStatus) throws AnnotatedException {
        if (certStatus.getCertStatus() == 11) {
            CertPathValidatorUtilities.getCertStatus(date, x509CRL, object, certStatus);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static void processCertA(CertPath certPath, PKIXExtendedParameters object, int n, PublicKey serializable, boolean bl, X500Name x500Name, X509Certificate x509Certificate, JcaJceHelper jcaJceHelper) throws ExtCertPathValidatorException {
        X509Certificate x509Certificate2;
        block12 : {
            List<? extends Certificate> list = certPath.getCertificates();
            x509Certificate2 = (X509Certificate)list.get(n);
            if (!bl) {
                void var1_4;
                String string = ((PKIXExtendedParameters)object).getSigProvider();
                try {
                    CertPathValidatorUtilities.verifyX509Certificate(x509Certificate2, (PublicKey)serializable, string);
                }
                catch (GeneralSecurityException generalSecurityException) {
                    throw new ExtCertPathValidatorException("Could not validate certificate signature.", (Throwable)var1_4, certPath, n);
                }
                catch (GeneralSecurityException generalSecurityException) {
                    // empty catch block
                }
                throw new ExtCertPathValidatorException("Could not validate certificate signature.", (Throwable)var1_4, certPath, n);
            }
            x509Certificate2.checkValidity(CertPathValidatorUtilities.getValidCertDateFromValidityModel((PKIXExtendedParameters)object, certPath, n));
            if (!((PKIXExtendedParameters)object).isRevocationEnabled()) break block12;
            try {
                RFC3280CertPathUtilities.checkCRLs((PKIXExtendedParameters)object, x509Certificate2, CertPathValidatorUtilities.getValidCertDateFromValidityModel((PKIXExtendedParameters)object, certPath, n), x509Certificate, (PublicKey)serializable, list, jcaJceHelper);
            }
            catch (AnnotatedException annotatedException) {
                object = annotatedException;
                if (annotatedException.getCause() == null) throw new ExtCertPathValidatorException(annotatedException.getMessage(), (Throwable)object, certPath, n);
                object = annotatedException.getCause();
                throw new ExtCertPathValidatorException(annotatedException.getMessage(), (Throwable)object, certPath, n);
            }
        }
        if (PrincipalUtils.getEncodedIssuerPrincipal(x509Certificate2).equals(x500Name)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("IssuerName(");
        ((StringBuilder)object).append(PrincipalUtils.getEncodedIssuerPrincipal(x509Certificate2));
        ((StringBuilder)object).append(") does not match SubjectName(");
        ((StringBuilder)object).append(x500Name);
        ((StringBuilder)object).append(") of signing certificate.");
        throw new ExtCertPathValidatorException(((StringBuilder)object).toString(), null, certPath, n);
        catch (AnnotatedException annotatedException) {
            throw new ExtCertPathValidatorException("Could not validate time of certificate.", annotatedException, certPath, n);
        }
        catch (CertificateNotYetValidException certificateNotYetValidException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not validate certificate: ");
            ((StringBuilder)object).append(certificateNotYetValidException.getMessage());
            throw new ExtCertPathValidatorException(((StringBuilder)object).toString(), certificateNotYetValidException, certPath, n);
        }
        catch (CertificateExpiredException certificateExpiredException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Could not validate certificate: ");
            ((StringBuilder)serializable).append(certificateExpiredException.getMessage());
            throw new ExtCertPathValidatorException(((StringBuilder)serializable).toString(), certificateExpiredException, certPath, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static void processCertBC(CertPath certPath, int n, PKIXNameConstraintValidator pKIXNameConstraintValidator) throws CertPathValidatorException {
        Object object = certPath.getCertificates();
        GeneralName[] arrgeneralName = (X509Certificate)object.get(n);
        int n2 = object.size();
        if (!CertPathValidatorUtilities.isSelfIssued((X509Certificate)arrgeneralName) || n2 - n >= n2) {
            RDN[] arrrDN;
            object = PrincipalUtils.getSubjectPrincipal((X509Certificate)arrgeneralName);
            try {
                object = DERSequence.getInstance(((ASN1Object)object).getEncoded());
            }
            catch (Exception exception) {
                throw new CertPathValidatorException("Exception extracting subject name when checking subtrees.", exception, certPath, n);
            }
            try {
                pKIXNameConstraintValidator.checkPermittedDN((ASN1Sequence)object);
                pKIXNameConstraintValidator.checkExcludedDN((ASN1Sequence)object);
            }
            catch (PKIXNameConstraintValidatorException pKIXNameConstraintValidatorException) {
                throw new CertPathValidatorException("Subtree check for certificate subject failed.", pKIXNameConstraintValidatorException, certPath, n);
            }
            try {
                arrgeneralName = GeneralNames.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)arrgeneralName, SUBJECT_ALTERNATIVE_NAME));
                arrrDN = X500Name.getInstance(object).getRDNs(BCStyle.EmailAddress);
            }
            catch (Exception exception) {
                throw new CertPathValidatorException("Subject alternative name extension could not be decoded.", exception, certPath, n);
            }
            for (n2 = 0; n2 != arrrDN.length; ++n2) {
                object = new GeneralName(1, ((ASN1String)((Object)arrrDN[n2].getFirst().getValue())).getString());
                try {
                    pKIXNameConstraintValidator.checkPermitted((GeneralName)object);
                    pKIXNameConstraintValidator.checkExcluded((GeneralName)object);
                    continue;
                }
                catch (PKIXNameConstraintValidatorException pKIXNameConstraintValidatorException) {
                    throw new CertPathValidatorException("Subtree check for certificate subject alternative email failed.", pKIXNameConstraintValidatorException, certPath, n);
                }
            }
            if (arrgeneralName != null) {
                try {
                    arrgeneralName = arrgeneralName.getNames();
                }
                catch (Exception exception) {
                    throw new CertPathValidatorException("Subject alternative name contents could not be decoded.", exception, certPath, n);
                }
                for (n2 = 0; n2 < arrgeneralName.length; ++n2) {
                    try {
                        pKIXNameConstraintValidator.checkPermitted(arrgeneralName[n2]);
                        pKIXNameConstraintValidator.checkExcluded(arrgeneralName[n2]);
                        continue;
                    }
                    catch (PKIXNameConstraintValidatorException pKIXNameConstraintValidatorException) {
                        throw new CertPathValidatorException("Subtree check for certificate subject alternative name failed.", pKIXNameConstraintValidatorException, certPath, n);
                    }
                }
            }
        }
    }

    protected static PKIXPolicyNode processCertD(CertPath object, int n, Set set, PKIXPolicyNode object2, List[] arrlist, int n2) throws CertPathValidatorException {
        block22 : {
            X509Certificate x509Certificate;
            int n3;
            Object object3;
            block23 : {
                Object object42;
                Object object5;
                Object object6;
                object3 = ((CertPath)object).getCertificates();
                x509Certificate = (X509Certificate)object3.get(n);
                int n4 = object3.size();
                n3 = n4 - n;
                try {
                    object5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, CERTIFICATE_POLICIES));
                    if (object5 == null || object2 == null) break block22;
                    object6 = ((ASN1Sequence)object5).getObjects();
                    object3 = new HashSet();
                }
                catch (AnnotatedException annotatedException) {
                    throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", annotatedException, (CertPath)object, n);
                }
                while (object6.hasMoreElements()) {
                    Iterator iterator = PolicyInformation.getInstance(object6.nextElement());
                    object42 = ((PolicyInformation)((Object)iterator)).getPolicyIdentifier();
                    object3.add((String)((ASN1ObjectIdentifier)object42).getId());
                    if (ANY_POLICY.equals(((ASN1ObjectIdentifier)object42).getId())) continue;
                    try {}
                    catch (CertPathValidatorException certPathValidatorException) {
                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be build.", certPathValidatorException, (CertPath)object, n);
                    }
                    if (CertPathValidatorUtilities.processCertD1i(n3, arrlist, (ASN1ObjectIdentifier)object42, (Set)((Object)(iterator = CertPathValidatorUtilities.getQualifierSet(((PolicyInformation)((Object)iterator)).getPolicyQualifiers()))))) continue;
                    CertPathValidatorUtilities.processCertD1ii(n3, arrlist, (ASN1ObjectIdentifier)object42, (Set)((Object)iterator));
                }
                if (!set.isEmpty() && !set.contains(ANY_POLICY)) {
                    object = set.iterator();
                    object6 = new HashSet();
                    while (object.hasNext()) {
                        object42 = object.next();
                        if (!object3.contains(object42)) continue;
                        object6.add(object42);
                    }
                    set.clear();
                    set.addAll((Collection<String>)object6);
                } else {
                    set.clear();
                    set.addAll((Collection<String>)object3);
                }
                if (!(n2 > 0 || n3 < n4 && CertPathValidatorUtilities.isSelfIssued(x509Certificate))) {
                    n = n3;
                } else {
                    object = ((ASN1Sequence)object5).getObjects();
                    while (object.hasMoreElements()) {
                        set = PolicyInformation.getInstance(object.nextElement());
                        if (!ANY_POLICY.equals(((PolicyInformation)((Object)set)).getPolicyIdentifier().getId())) continue;
                        object6 = CertPathValidatorUtilities.getQualifierSet(((PolicyInformation)((Object)set)).getPolicyQualifiers());
                        object5 = arrlist[n3 - 1];
                        set = object3;
                        object3 = object5;
                        for (n = 0; n < object3.size(); ++n) {
                            object5 = (PKIXPolicyNode)object3.get(n);
                            for (Object object42 : ((PKIXPolicyNode)object5).getExpectedPolicies()) {
                                if (object42 instanceof String) {
                                    object42 = (String)object42;
                                } else {
                                    if (!(object42 instanceof ASN1ObjectIdentifier)) continue;
                                    object42 = ((ASN1ObjectIdentifier)object42).getId();
                                }
                                Object object7 = ((PKIXPolicyNode)object5).getChildren();
                                n2 = 0;
                                while (object7.hasNext()) {
                                    if (!((String)object42).equals(((PKIXPolicyNode)object7.next()).getValidPolicy())) continue;
                                    n2 = 1;
                                }
                                if (n2 != 0) continue;
                                object7 = new HashSet();
                                object7.add(object42);
                                object42 = new PKIXPolicyNode(new ArrayList(), n3, (Set)object7, (PolicyNode)object5, (Set)object6, (String)object42, false);
                                ((PKIXPolicyNode)object5).addChild((PKIXPolicyNode)object42);
                                arrlist[n3].add(object42);
                            }
                        }
                        n = n3;
                        break block23;
                    }
                    n = n3;
                }
            }
            object = object2;
            for (n2 = n - 1; n2 >= 0; --n2) {
                object2 = arrlist[n2];
                n3 = 0;
                do {
                    set = object;
                    if (n3 >= object2.size()) break;
                    object3 = (PKIXPolicyNode)object2.get(n3);
                    set = object;
                    if (!((PKIXPolicyNode)object3).hasChildren()) {
                        set = object = CertPathValidatorUtilities.removePolicyNode((PKIXPolicyNode)object, arrlist, (PKIXPolicyNode)object3);
                        if (object == null) {
                            set = object;
                            break;
                        }
                    }
                    ++n3;
                    object = set;
                } while (true);
                object = set;
            }
            set = x509Certificate.getCriticalExtensionOIDs();
            if (set != null) {
                boolean bl = set.contains(CERTIFICATE_POLICIES);
                set = arrlist[n];
                for (n = 0; n < set.size(); ++n) {
                    ((PKIXPolicyNode)set.get(n)).setCritical(bl);
                }
            }
            return object;
        }
        return null;
    }

    protected static PKIXPolicyNode processCertE(CertPath certPath, int n, PKIXPolicyNode pKIXPolicyNode) throws CertPathValidatorException {
        Object object = (X509Certificate)certPath.getCertificates().get(n);
        try {
            object = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, CERTIFICATE_POLICIES));
            if (object == null) {
                pKIXPolicyNode = null;
            }
            return pKIXPolicyNode;
        }
        catch (AnnotatedException annotatedException) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", annotatedException, certPath, n);
        }
    }

    protected static void processCertF(CertPath certPath, int n, PKIXPolicyNode pKIXPolicyNode, int n2) throws CertPathValidatorException {
        if (n2 <= 0 && pKIXPolicyNode == null) {
            throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", null, certPath, n);
        }
    }

    protected static int wrapupCertA(int n, X509Certificate x509Certificate) {
        int n2 = n;
        if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate)) {
            n2 = n;
            if (n != 0) {
                n2 = n - 1;
            }
        }
        return n2;
    }

    protected static int wrapupCertB(CertPath certPath, int n, int n2) throws CertPathValidatorException {
        block5 : {
            Enumeration enumeration;
            Object object = (X509Certificate)certPath.getCertificates().get(n);
            try {
                object = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)object, POLICY_CONSTRAINTS));
                if (object == null) break block5;
                enumeration = ((ASN1Sequence)object).getObjects();
            }
            catch (AnnotatedException annotatedException) {
                throw new ExtCertPathValidatorException("Policy constraints could not be decoded.", annotatedException, certPath, n);
            }
            while (enumeration.hasMoreElements()) {
                object = (ASN1TaggedObject)enumeration.nextElement();
                if (((ASN1TaggedObject)object).getTagNo() != 0) continue;
                try {
                    int n3 = ASN1Integer.getInstance((ASN1TaggedObject)object, false).getValue().intValue();
                    if (n3 != 0) continue;
                    return 0;
                }
                catch (Exception exception) {
                    throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", exception, certPath, n);
                }
            }
        }
        return n2;
    }

    protected static void wrapupCertF(CertPath certPath, int n, List object, Set set) throws CertPathValidatorException {
        X509Certificate x509Certificate = (X509Certificate)certPath.getCertificates().get(n);
        object = object.iterator();
        while (object.hasNext()) {
            try {
                ((PKIXCertPathChecker)object.next()).check(x509Certificate, set);
            }
            catch (Exception exception) {
                throw new CertPathValidatorException("Additional certificate path checker failed.", exception, certPath, n);
            }
            catch (CertPathValidatorException certPathValidatorException) {
                throw new ExtCertPathValidatorException(certPathValidatorException.getMessage(), certPathValidatorException, certPath, n);
            }
        }
        if (set.isEmpty()) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Certificate has unsupported critical extension: ");
        ((StringBuilder)object).append(set);
        throw new ExtCertPathValidatorException(((StringBuilder)object).toString(), null, certPath, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static PKIXPolicyNode wrapupCertG(CertPath object, PKIXExtendedParameters object2, Set object3, int n, List[] arrlist, PKIXPolicyNode pKIXPolicyNode, Set object4) throws CertPathValidatorException {
        int n2;
        int n3 = ((CertPath)object).getCertificates().size();
        if (pKIXPolicyNode == null) {
            if (((PKIXExtendedParameters)object2).isExplicitPolicyRequired()) throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, (CertPath)object, n);
            return null;
        }
        if (CertPathValidatorUtilities.isAnyPolicy((Set)object3)) {
            if (((PKIXExtendedParameters)object2).isExplicitPolicyRequired()) {
                int n4;
                if (object4.isEmpty()) throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, (CertPath)object, n);
                object2 = new HashSet();
                for (n = 0; n < arrlist.length; ++n) {
                    object = arrlist[n];
                    for (n4 = 0; n4 < object.size(); ++n4) {
                        object3 = (PKIXPolicyNode)object.get(n4);
                        if (!ANY_POLICY.equals(((PKIXPolicyNode)object3).getValidPolicy())) continue;
                        object3 = ((PKIXPolicyNode)object3).getChildren();
                        while (object3.hasNext()) {
                            object2.add(object3.next());
                        }
                    }
                }
                object = object2.iterator();
                while (object.hasNext()) {
                    object4.contains(((PKIXPolicyNode)object.next()).getValidPolicy());
                }
                n = n3 - 1;
                object = pKIXPolicyNode;
                do {
                    object2 = object;
                    if (n < 0) return object2;
                    object3 = arrlist[n];
                    for (n4 = 0; n4 < object3.size(); ++n4) {
                        pKIXPolicyNode = (PKIXPolicyNode)object3.get(n4);
                        object2 = object;
                        if (!pKIXPolicyNode.hasChildren()) {
                            object2 = CertPathValidatorUtilities.removePolicyNode((PKIXPolicyNode)object, arrlist, pKIXPolicyNode);
                        }
                        object = object2;
                    }
                    --n;
                } while (true);
            }
            object2 = pKIXPolicyNode;
            return object2;
        }
        object2 = new HashSet();
        for (n = 0; n < arrlist.length; ++n) {
            object = arrlist[n];
            for (n2 = 0; n2 < object.size(); ++n2) {
                object4 = (PKIXPolicyNode)object.get(n2);
                if (!ANY_POLICY.equals(((PKIXPolicyNode)object4).getValidPolicy())) continue;
                Iterator iterator = ((PKIXPolicyNode)object4).getChildren();
                while (iterator.hasNext()) {
                    object4 = (PKIXPolicyNode)iterator.next();
                    if (ANY_POLICY.equals(((PKIXPolicyNode)object4).getValidPolicy())) continue;
                    object2.add(object4);
                }
            }
        }
        object4 = object2.iterator();
        object = pKIXPolicyNode;
        while (object4.hasNext()) {
            pKIXPolicyNode = (PKIXPolicyNode)object4.next();
            object2 = object;
            if (!object3.contains(pKIXPolicyNode.getValidPolicy())) {
                object2 = CertPathValidatorUtilities.removePolicyNode((PKIXPolicyNode)object, arrlist, pKIXPolicyNode);
            }
            object = object2;
        }
        object2 = object;
        if (object == null) return object2;
        n = n3 - 1;
        do {
            object2 = object;
            if (n < 0) return object2;
            object3 = arrlist[n];
            object2 = object;
            for (n2 = 0; n2 < object3.size(); ++n2) {
                pKIXPolicyNode = (PKIXPolicyNode)object3.get(n2);
                object = object2;
                if (!pKIXPolicyNode.hasChildren()) {
                    object = CertPathValidatorUtilities.removePolicyNode((PKIXPolicyNode)object2, arrlist, pKIXPolicyNode);
                }
                object2 = object;
            }
            --n;
            object = object2;
        } while (true);
    }
}

