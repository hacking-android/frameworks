/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXReason;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.PolicyNode;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.AdaptableX509CertSelector;
import sun.security.provider.certpath.AlgorithmChecker;
import sun.security.provider.certpath.BasicChecker;
import sun.security.provider.certpath.ConstraintsChecker;
import sun.security.provider.certpath.KeyChecker;
import sun.security.provider.certpath.PKIX;
import sun.security.provider.certpath.PKIXMasterCertPathValidator;
import sun.security.provider.certpath.PolicyChecker;
import sun.security.provider.certpath.PolicyNodeImpl;
import sun.security.provider.certpath.RevocationChecker;
import sun.security.util.Debug;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.X509CertImpl;

public final class PKIXCertPathValidator
extends CertPathValidatorSpi {
    private static final Debug debug = Debug.getInstance("certpath");

    private static PKIXCertPathValidatorResult validate(TrustAnchor trustAnchor, PKIX.ValidatorParams validatorParams) throws CertPathValidatorException {
        int n = validatorParams.certificates().size();
        ArrayList<PKIXCertPathChecker> arrayList = new ArrayList<PKIXCertPathChecker>();
        arrayList.add(new AlgorithmChecker(trustAnchor));
        arrayList.add(new KeyChecker(n, validatorParams.targetCertConstraints()));
        arrayList.add(new ConstraintsChecker(n));
        Object object = new PolicyNodeImpl(null, "2.5.29.32.0", null, false, Collections.singleton("2.5.29.32.0"), false);
        PolicyChecker policyChecker = new PolicyChecker(validatorParams.initialPolicies(), n, validatorParams.explicitPolicyRequired(), validatorParams.policyMappingInhibited(), validatorParams.anyPolicyInhibited(), validatorParams.policyQualifiersRejected(), (PolicyNodeImpl)object);
        arrayList.add(policyChecker);
        BasicChecker basicChecker = new BasicChecker(trustAnchor, validatorParams.date(), validatorParams.sigProvider(), false);
        arrayList.add(basicChecker);
        int n2 = 0;
        object = validatorParams.certPathCheckers();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            PKIXCertPathChecker pKIXCertPathChecker = (PKIXCertPathChecker)iterator.next();
            n = n2;
            if (pKIXCertPathChecker instanceof PKIXRevocationChecker) {
                if (n2 == 0) {
                    n = n2 = 1;
                    if (pKIXCertPathChecker instanceof RevocationChecker) {
                        ((RevocationChecker)pKIXCertPathChecker).init(trustAnchor, validatorParams);
                        n = n2;
                    }
                } else {
                    throw new CertPathValidatorException("Only one PKIXRevocationChecker can be specified");
                }
            }
            n2 = n;
        }
        if (validatorParams.revocationEnabled() && n2 == 0) {
            arrayList.add(new RevocationChecker(trustAnchor, validatorParams));
        }
        arrayList.addAll((Collection<PKIXCertPathChecker>)object);
        PKIXMasterCertPathValidator.validate(validatorParams.certPath(), validatorParams.certificates(), arrayList);
        return new PKIXCertPathValidatorResult(trustAnchor, policyChecker.getPolicyTree(), basicChecker.getPublicKey());
    }

    private static PKIXCertPathValidatorResult validate(PKIX.ValidatorParams validatorParams) throws CertPathValidatorException {
        Object object = debug;
        if (object != null) {
            ((Debug)object).println("PKIXCertPathValidator.engineValidate()...");
        }
        object = null;
        Object object2 = validatorParams.certificates();
        if (!object2.isEmpty()) {
            object = new AdaptableX509CertSelector();
            object2 = object2.get(0);
            ((X509CertSelector)object).setSubject(((X509Certificate)object2).getIssuerX500Principal());
            try {
                object2 = X509CertImpl.toImpl((X509Certificate)object2);
                ((AdaptableX509CertSelector)object).setSkiAndSerialNumber(((X509CertImpl)object2).getAuthorityKeyIdentifierExtension());
            }
            catch (IOException | CertificateException exception) {
                // empty catch block
            }
        }
        object2 = null;
        for (TrustAnchor trustAnchor : validatorParams.trustAnchors()) {
            X509Certificate x509Certificate = trustAnchor.getTrustedCert();
            if (x509Certificate != null) {
                if (object != null && !((AdaptableX509CertSelector)object).match(x509Certificate)) {
                    Debug debug = PKIXCertPathValidator.debug;
                    if (debug == null) continue;
                    debug.println("NO - don't try this trustedCert");
                    continue;
                }
                object2 = debug;
                if (object2 != null) {
                    ((Debug)object2).println("YES - try this trustedCert");
                    object2 = debug;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("anchor.getTrustedCert().getSubjectX500Principal() = ");
                    stringBuilder.append(x509Certificate.getSubjectX500Principal());
                    ((Debug)object2).println(stringBuilder.toString());
                }
            } else {
                object2 = debug;
                if (object2 != null) {
                    ((Debug)object2).println("PKIXCertPathValidator.engineValidate(): anchor.getTrustedCert() == null");
                }
            }
            try {
                object2 = PKIXCertPathValidator.validate(trustAnchor, validatorParams);
                return object2;
            }
            catch (CertPathValidatorException certPathValidatorException) {
            }
        }
        if (object2 != null) {
            throw object2;
        }
        throw new CertPathValidatorException("Path does not chain with any of the trust anchors", null, null, -1, PKIXReason.NO_TRUST_ANCHOR);
    }

    @Override
    public CertPathChecker engineGetRevocationChecker() {
        return new RevocationChecker();
    }

    @Override
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        return PKIXCertPathValidator.validate(PKIX.checkParams(certPath, certPathParameters));
    }
}

