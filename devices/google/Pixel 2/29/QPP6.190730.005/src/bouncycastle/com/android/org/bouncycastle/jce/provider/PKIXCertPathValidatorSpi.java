/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.org.bouncycastle.jcajce.PKIXCertStoreSelector;
import com.android.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import com.android.org.bouncycastle.jcajce.PKIXExtendedParameters;
import com.android.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import com.android.org.bouncycastle.jce.provider.AnnotatedException;
import com.android.org.bouncycastle.jce.provider.CertBlacklist;
import com.android.org.bouncycastle.jce.provider.CertPathValidatorUtilities;
import com.android.org.bouncycastle.jce.provider.PKIXNameConstraintValidator;
import com.android.org.bouncycastle.jce.provider.PKIXPolicyNode;
import com.android.org.bouncycastle.jce.provider.PrincipalUtils;
import com.android.org.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import com.android.org.bouncycastle.x509.ExtendedPKIXParameters;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PKIXCertPathValidatorSpi
extends CertPathValidatorSpi {
    private final JcaJceHelper helper = new BCJcaJceHelper();

    static void checkCertificate(X509Certificate x509Certificate) throws AnnotatedException {
        try {
            TBSCertificate.getInstance(x509Certificate.getTBSCertificate());
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new AnnotatedException(illegalArgumentException.getMessage());
        }
        catch (CertificateEncodingException certificateEncodingException) {
            throw new AnnotatedException("unable to process TBSCertificate", certificateEncodingException);
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
    @Override
    public CertPathValidatorResult engineValidate(CertPath object, CertPathParameters set) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        List[] arrlist;
        Serializable serializable;
        List list;
        PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi;
        int n;
        Set set2;
        Object certPath;
        PKIXExtendedParameters pKIXExtendedParameters;
        Object object3;
        int n4;
        Object object2;
        TrustAnchor trustAnchor;
        Object object6;
        int n2;
        Object object7;
        int n7;
        Object object5;
        Object object4;
        int n6;
        int n5;
        PKIXNameConstraintValidator pKIXNameConstraintValidator;
        int n3;
        block36 : {
            void var2_13;
            block35 : {
                void var2_10;
                object4 = this;
                certPath = object;
                if (set instanceof PKIXParameters) {
                    object2 = new PKIXExtendedParameters.Builder((PKIXParameters)((Object)set));
                    if (set instanceof ExtendedPKIXParameters) {
                        set = (ExtendedPKIXParameters)((Object)set);
                        ((PKIXExtendedParameters.Builder)object2).setUseDeltasEnabled(((ExtendedPKIXParameters)((Object)set)).isUseDeltasEnabled());
                        ((PKIXExtendedParameters.Builder)object2).setValidityModel(((ExtendedPKIXParameters)((Object)set)).getValidityModel());
                    }
                    set = ((PKIXExtendedParameters.Builder)object2).build();
                } else if (set instanceof PKIXExtendedBuilderParameters) {
                    set = ((PKIXExtendedBuilderParameters)((Object)set)).getBaseParameters();
                } else {
                    if (!(set instanceof PKIXExtendedParameters)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Parameters must be a ");
                        ((StringBuilder)object).append(PKIXParameters.class.getName());
                        ((StringBuilder)object).append(" instance.");
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                    }
                    set = (PKIXExtendedParameters)((Object)set);
                }
                if (((PKIXExtendedParameters)((Object)set)).getTrustAnchors() == null) throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
                object5 = object2 = ((CertPath)object).getCertificates();
                n4 = object5.size();
                if (object5.isEmpty()) throw new CertPathValidatorException("Certification path is empty.", null, (CertPath)certPath, -1);
                serializable = (X509Certificate)object5.get(0);
                if (serializable != null) {
                    serializable = ((X509Certificate)serializable).getSerialNumber();
                    if (NoPreloadHolder.blacklist.isSerialNumberBlackListed((BigInteger)serializable)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Certificate revocation of serial 0x");
                        ((StringBuilder)object).append(((BigInteger)serializable).toString(16));
                        object = ((StringBuilder)object).toString();
                        System.out.println((String)object);
                        object = new AnnotatedException((String)object);
                        throw new CertPathValidatorException(((Throwable)object).getMessage(), (Throwable)object, (CertPath)certPath, 0);
                    }
                }
                set2 = ((PKIXExtendedParameters)((Object)set)).getInitialPolicies();
                trustAnchor = CertPathValidatorUtilities.findTrustAnchor((X509Certificate)object5.get(object5.size() - 1), ((PKIXExtendedParameters)((Object)set)).getTrustAnchors(), ((PKIXExtendedParameters)((Object)set)).getSigProvider());
                if (trustAnchor == null) break block35;
                PKIXCertPathValidatorSpi.checkCertificate(trustAnchor.getTrustedCert());
                pKIXExtendedParameters = new PKIXExtendedParameters.Builder((PKIXExtendedParameters)((Object)set)).setTrustAnchor(trustAnchor).build();
                object7 = new ArrayList[n4 + 1];
                for (n = 0; n < ((ArrayList[])object7).length; ++n) {
                    object7[n] = new ArrayList();
                }
                HashSet<String> hashSet = new HashSet<String>();
                hashSet.add("2.5.29.32.0");
                object6 = new PKIXPolicyNode(new ArrayList(), 0, hashSet, null, new HashSet(), "2.5.29.32.0", false);
                object7[0].add(object6);
                pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
                serializable = new HashSet();
                n = pKIXExtendedParameters.isExplicitPolicyRequired() ? 0 : n4 + 1;
                n3 = pKIXExtendedParameters.isAnyPolicyInhibited() ? 0 : n4 + 1;
                n2 = pKIXExtendedParameters.isPolicyMappingInhibited() ? 0 : n4 + 1;
                object3 = trustAnchor.getTrustedCert();
                if (object3 != null) {
                    try {
                        set = PrincipalUtils.getSubjectPrincipal((X509Certificate)object3);
                        object2 = ((Certificate)object3).getPublicKey();
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", (Throwable)var2_10, (CertPath)object, -1);
                    }
                } else {
                    set = PrincipalUtils.getCA(trustAnchor);
                    object2 = trustAnchor.getCAPublicKey();
                }
                try {
                    arrlist = CertPathValidatorUtilities.getAlgorithmIdentifier((PublicKey)object2);
                    arrlist.getAlgorithm();
                }
                catch (CertPathValidatorException certPathValidatorException) {
                    throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", certPathValidatorException, (CertPath)certPath, -1);
                }
                arrlist.getParameters();
                if (pKIXExtendedParameters.getTargetConstraints() != null) {
                    if (!pKIXExtendedParameters.getTargetConstraints().match((X509Certificate)object5.get(0))) throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, (CertPath)certPath, 0);
                }
                list = pKIXExtendedParameters.getCertPathCheckers();
                arrlist = list.iterator();
                while (arrlist.hasNext()) {
                    ((PKIXCertPathChecker)arrlist.next()).init(false);
                }
                Object var22_33 = null;
                n5 = object5.size();
                n6 = n2;
                n7 = n4;
                arrlist = object2;
                n2 = n;
                object2 = object6;
                n = n7;
                object6 = set;
                set = arrlist;
                arrlist = object7;
                pKIXCertPathValidatorSpi = object4;
                object4 = var22_33;
                break block36;
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
                throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", (Throwable)var2_10, (CertPath)object, -1);
            }
            try {
                set = new CertPathValidatorException("Trust anchor for certification path not found.", null, (CertPath)certPath, -1);
                throw set;
            }
            catch (AnnotatedException annotatedException) {
                throw new CertPathValidatorException(var2_13.getMessage(), var2_13.getUnderlyingException(), (CertPath)object, object2.size() - 1);
            }
            catch (AnnotatedException annotatedException) {
                // empty catch block
            }
            throw new CertPathValidatorException(var2_13.getMessage(), var2_13.getUnderlyingException(), (CertPath)object, object2.size() - 1);
        }
        for (n7 = --n5; n7 >= 0; --n7) {
            block39 : {
                block37 : {
                    void var1_4;
                    block38 : {
                        if (NoPreloadHolder.blacklist.isPublicKeyBlackListed((PublicKey)((Object)set))) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Certificate revocation of public key ");
                            ((StringBuilder)object).append(set);
                            object = ((StringBuilder)object).toString();
                            System.out.println((String)object);
                            object = new AnnotatedException((String)object);
                            throw new CertPathValidatorException(((Throwable)object).getMessage(), (Throwable)object, (CertPath)certPath, n7);
                        }
                        n5 = n4 - n7;
                        object4 = (X509Certificate)object5.get(n7);
                        boolean bl = n7 == object5.size() - 1;
                        try {
                            PKIXCertPathValidatorSpi.checkCertificate((X509Certificate)object4);
                        }
                        catch (AnnotatedException annotatedException) {
                            throw new CertPathValidatorException(annotatedException.getMessage(), annotatedException.getUnderlyingException(), (CertPath)certPath, n7);
                        }
                        RFC3280CertPathUtilities.processCertA((CertPath)object, pKIXExtendedParameters, n7, set, bl, (X500Name)object6, (X509Certificate)object3, pKIXCertPathValidatorSpi.helper);
                        RFC3280CertPathUtilities.processCertBC((CertPath)certPath, n7, pKIXNameConstraintValidator);
                        object2 = RFC3280CertPathUtilities.processCertE((CertPath)certPath, n7, RFC3280CertPathUtilities.processCertD((CertPath)object, n7, (Set)((Object)serializable), (PKIXPolicyNode)object2, arrlist, n3));
                        RFC3280CertPathUtilities.processCertF((CertPath)certPath, n7, (PKIXPolicyNode)object2, n2);
                        if (n5 == n4) break block37;
                        if (object4 == null || ((X509Certificate)object4).getVersion() != 1) break block38;
                        if (n5 != 1) throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, (CertPath)certPath, n7);
                        if (!((Certificate)object4).equals(trustAnchor.getTrustedCert())) throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, (CertPath)certPath, n7);
                        break block37;
                    }
                    RFC3280CertPathUtilities.prepareNextCertA((CertPath)certPath, n7);
                    object2 = RFC3280CertPathUtilities.prepareCertB((CertPath)certPath, n7, arrlist, (PKIXPolicyNode)object2, n6);
                    RFC3280CertPathUtilities.prepareNextCertG((CertPath)certPath, n7, pKIXNameConstraintValidator);
                    n2 = RFC3280CertPathUtilities.prepareNextCertH1((CertPath)certPath, n7, n2);
                    n6 = RFC3280CertPathUtilities.prepareNextCertH2((CertPath)certPath, n7, n6);
                    n3 = RFC3280CertPathUtilities.prepareNextCertH3((CertPath)certPath, n7, n3);
                    n2 = RFC3280CertPathUtilities.prepareNextCertI1((CertPath)certPath, n7, n2);
                    n6 = RFC3280CertPathUtilities.prepareNextCertI2((CertPath)certPath, n7, n6);
                    n3 = RFC3280CertPathUtilities.prepareNextCertJ((CertPath)certPath, n7, n3);
                    RFC3280CertPathUtilities.prepareNextCertK((CertPath)certPath, n7);
                    n = RFC3280CertPathUtilities.prepareNextCertM((CertPath)certPath, n7, RFC3280CertPathUtilities.prepareNextCertL((CertPath)certPath, n7, n));
                    RFC3280CertPathUtilities.prepareNextCertN((CertPath)certPath, n7);
                    set = object4.getCriticalExtensionOIDs();
                    if (set != null) {
                        set = new HashSet<String>(set);
                        set.remove(RFC3280CertPathUtilities.KEY_USAGE);
                        set.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                        set.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                        set.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                        set.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                        set.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                        set.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                        set.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                        set.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                        set.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                    } else {
                        set = new HashSet<String>();
                    }
                    RFC3280CertPathUtilities.prepareNextCertO((CertPath)certPath, n7, set, list);
                    object6 = object4;
                    object7 = PrincipalUtils.getSubjectPrincipal((X509Certificate)object6);
                    set = ((CertPath)object).getCertificates();
                    try {
                        set = CertPathValidatorUtilities.getNextWorkingKey(set, n7, this.helper);
                    }
                    catch (CertPathValidatorException certPathValidatorException) {
                        throw new CertPathValidatorException("Next working key could not be retrieved.", (Throwable)var1_4, (CertPath)certPath, n7);
                    }
                    object3 = CertPathValidatorUtilities.getAlgorithmIdentifier((PublicKey)((Object)set));
                    ((AlgorithmIdentifier)object3).getAlgorithm();
                    ((AlgorithmIdentifier)object3).getParameters();
                    break block39;
                    catch (CertPathValidatorException certPathValidatorException) {
                        // empty catch block
                    }
                    throw new CertPathValidatorException("Next working key could not be retrieved.", (Throwable)var1_4, (CertPath)certPath, n7);
                }
                object7 = object6;
                object6 = object3;
            }
            pKIXCertPathValidatorSpi = this;
            object3 = object6;
            object6 = object7;
        }
        n = RFC3280CertPathUtilities.wrapupCertB((CertPath)certPath, n7 + 1, RFC3280CertPathUtilities.wrapupCertA(n2, (X509Certificate)object4));
        set = object4.getCriticalExtensionOIDs();
        if (set != null) {
            set = new HashSet<String>(set);
            set.remove(RFC3280CertPathUtilities.KEY_USAGE);
            set.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
            set.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
            set.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
            set.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
            set.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
            set.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
            set.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
            set.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
            set.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
            set.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
            set.remove(Extension.extendedKeyUsage.getId());
        } else {
            set = new HashSet<String>();
        }
        RFC3280CertPathUtilities.wrapupCertF((CertPath)certPath, n7 + 1, list, set);
        object = RFC3280CertPathUtilities.wrapupCertG((CertPath)object, pKIXExtendedParameters, set2, n7 + 1, arrlist, (PKIXPolicyNode)object2, (Set)((Object)serializable));
        if (n > 0) return new PKIXCertPathValidatorResult(trustAnchor, (PolicyNode)object, ((Certificate)object4).getPublicKey());
        if (object == null) throw new CertPathValidatorException("Path processing failed on policy.", null, (CertPath)certPath, n7);
        return new PKIXCertPathValidatorResult(trustAnchor, (PolicyNode)object, ((Certificate)object4).getPublicKey());
    }

    private static class NoPreloadHolder {
        private static final CertBlacklist blacklist = new CertBlacklist();

        private NoPreloadHolder() {
        }
    }

}

