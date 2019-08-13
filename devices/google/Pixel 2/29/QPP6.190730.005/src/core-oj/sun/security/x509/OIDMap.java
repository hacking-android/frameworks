/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.CRLReasonCodeExtension;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.DeltaCRLIndicatorExtension;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.x509.FreshestCRLExtension;
import sun.security.x509.InhibitAnyPolicyExtension;
import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.KeyUsageExtension;
import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.NetscapeCertTypeExtension;
import sun.security.x509.OCSPNoCheckExtension;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.PolicyConstraintsExtension;
import sun.security.x509.PolicyMappingsExtension;
import sun.security.x509.PrivateKeyUsageExtension;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class OIDMap {
    private static final String AUTH_INFO_ACCESS = "x509.info.extensions.AuthorityInfoAccess";
    private static final String AUTH_KEY_IDENTIFIER = "x509.info.extensions.AuthorityKeyIdentifier";
    private static final String BASIC_CONSTRAINTS = "x509.info.extensions.BasicConstraints";
    private static final String CERT_ISSUER = "x509.info.extensions.CertificateIssuer";
    private static final String CERT_POLICIES = "x509.info.extensions.CertificatePolicies";
    private static final String CRL_DIST_POINTS = "x509.info.extensions.CRLDistributionPoints";
    private static final String CRL_NUMBER = "x509.info.extensions.CRLNumber";
    private static final String CRL_REASON = "x509.info.extensions.CRLReasonCode";
    private static final String DELTA_CRL_INDICATOR = "x509.info.extensions.DeltaCRLIndicator";
    private static final String EXT_KEY_USAGE = "x509.info.extensions.ExtendedKeyUsage";
    private static final String FRESHEST_CRL = "x509.info.extensions.FreshestCRL";
    private static final String INHIBIT_ANY_POLICY = "x509.info.extensions.InhibitAnyPolicy";
    private static final String ISSUER_ALT_NAME = "x509.info.extensions.IssuerAlternativeName";
    private static final String ISSUING_DIST_POINT = "x509.info.extensions.IssuingDistributionPoint";
    private static final String KEY_USAGE = "x509.info.extensions.KeyUsage";
    private static final String NAME_CONSTRAINTS = "x509.info.extensions.NameConstraints";
    private static final String NETSCAPE_CERT = "x509.info.extensions.NetscapeCertType";
    private static final int[] NetscapeCertType_data = new int[]{2, 16, 840, 1, 113730, 1, 1};
    private static final String OCSPNOCHECK = "x509.info.extensions.OCSPNoCheck";
    private static final String POLICY_CONSTRAINTS = "x509.info.extensions.PolicyConstraints";
    private static final String POLICY_MAPPINGS = "x509.info.extensions.PolicyMappings";
    private static final String PRIVATE_KEY_USAGE = "x509.info.extensions.PrivateKeyUsage";
    private static final String ROOT = "x509.info.extensions";
    private static final String SUBJECT_INFO_ACCESS = "x509.info.extensions.SubjectInfoAccess";
    private static final String SUB_ALT_NAME = "x509.info.extensions.SubjectAlternativeName";
    private static final String SUB_KEY_IDENTIFIER = "x509.info.extensions.SubjectKeyIdentifier";
    private static final Map<String, OIDInfo> nameMap;
    private static final Map<ObjectIdentifier, OIDInfo> oidMap;

    static {
        oidMap = new HashMap<ObjectIdentifier, OIDInfo>();
        nameMap = new HashMap<String, OIDInfo>();
        OIDMap.addInternal(SUB_KEY_IDENTIFIER, PKIXExtensions.SubjectKey_Id, SubjectKeyIdentifierExtension.class);
        OIDMap.addInternal(KEY_USAGE, PKIXExtensions.KeyUsage_Id, KeyUsageExtension.class);
        OIDMap.addInternal(PRIVATE_KEY_USAGE, PKIXExtensions.PrivateKeyUsage_Id, PrivateKeyUsageExtension.class);
        OIDMap.addInternal(SUB_ALT_NAME, PKIXExtensions.SubjectAlternativeName_Id, SubjectAlternativeNameExtension.class);
        OIDMap.addInternal(ISSUER_ALT_NAME, PKIXExtensions.IssuerAlternativeName_Id, IssuerAlternativeNameExtension.class);
        OIDMap.addInternal(BASIC_CONSTRAINTS, PKIXExtensions.BasicConstraints_Id, BasicConstraintsExtension.class);
        OIDMap.addInternal(CRL_NUMBER, PKIXExtensions.CRLNumber_Id, CRLNumberExtension.class);
        OIDMap.addInternal(CRL_REASON, PKIXExtensions.ReasonCode_Id, CRLReasonCodeExtension.class);
        OIDMap.addInternal(NAME_CONSTRAINTS, PKIXExtensions.NameConstraints_Id, NameConstraintsExtension.class);
        OIDMap.addInternal(POLICY_MAPPINGS, PKIXExtensions.PolicyMappings_Id, PolicyMappingsExtension.class);
        OIDMap.addInternal(AUTH_KEY_IDENTIFIER, PKIXExtensions.AuthorityKey_Id, AuthorityKeyIdentifierExtension.class);
        OIDMap.addInternal(POLICY_CONSTRAINTS, PKIXExtensions.PolicyConstraints_Id, PolicyConstraintsExtension.class);
        OIDMap.addInternal(NETSCAPE_CERT, ObjectIdentifier.newInternal(new int[]{2, 16, 840, 1, 113730, 1, 1}), NetscapeCertTypeExtension.class);
        OIDMap.addInternal(CERT_POLICIES, PKIXExtensions.CertificatePolicies_Id, CertificatePoliciesExtension.class);
        OIDMap.addInternal(EXT_KEY_USAGE, PKIXExtensions.ExtendedKeyUsage_Id, ExtendedKeyUsageExtension.class);
        OIDMap.addInternal(INHIBIT_ANY_POLICY, PKIXExtensions.InhibitAnyPolicy_Id, InhibitAnyPolicyExtension.class);
        OIDMap.addInternal(CRL_DIST_POINTS, PKIXExtensions.CRLDistributionPoints_Id, CRLDistributionPointsExtension.class);
        OIDMap.addInternal(CERT_ISSUER, PKIXExtensions.CertificateIssuer_Id, CertificateIssuerExtension.class);
        OIDMap.addInternal(SUBJECT_INFO_ACCESS, PKIXExtensions.SubjectInfoAccess_Id, SubjectInfoAccessExtension.class);
        OIDMap.addInternal(AUTH_INFO_ACCESS, PKIXExtensions.AuthInfoAccess_Id, AuthorityInfoAccessExtension.class);
        OIDMap.addInternal(ISSUING_DIST_POINT, PKIXExtensions.IssuingDistributionPoint_Id, IssuingDistributionPointExtension.class);
        OIDMap.addInternal(DELTA_CRL_INDICATOR, PKIXExtensions.DeltaCRLIndicator_Id, DeltaCRLIndicatorExtension.class);
        OIDMap.addInternal(FRESHEST_CRL, PKIXExtensions.FreshestCRL_Id, FreshestCRLExtension.class);
        OIDMap.addInternal(OCSPNOCHECK, PKIXExtensions.OCSPNoCheck_Id, OCSPNoCheckExtension.class);
    }

    private OIDMap() {
    }

    public static void addAttribute(String charSequence, String charSequence2, Class<?> object) throws CertificateException {
        block3 : {
            try {
                ObjectIdentifier objectIdentifier = new ObjectIdentifier((String)charSequence2);
                object = new OIDInfo((String)charSequence, objectIdentifier, (Class<?>)object);
                if (oidMap.put(objectIdentifier, (OIDInfo)object) != null) break block3;
                if (nameMap.put((String)charSequence, (OIDInfo)object) == null) {
                    return;
                }
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("Name already exists: ");
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid Object identifier: ");
                stringBuilder.append((String)charSequence2);
                throw new CertificateException(stringBuilder.toString());
            }
            ((StringBuilder)charSequence2).append((String)charSequence);
            throw new CertificateException(((StringBuilder)charSequence2).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Object identifier already exists: ");
        ((StringBuilder)charSequence).append((String)charSequence2);
        throw new CertificateException(((StringBuilder)charSequence).toString());
    }

    private static void addInternal(String string, ObjectIdentifier objectIdentifier, Class object) {
        object = new OIDInfo(string, objectIdentifier, (Class<?>)object);
        oidMap.put(objectIdentifier, (OIDInfo)object);
        nameMap.put(string, (OIDInfo)object);
    }

    public static Class<?> getClass(String class_) throws CertificateException {
        class_ = (class_ = nameMap.get(class_)) == null ? null : ((OIDInfo)((Object)class_)).getClazz();
        return class_;
    }

    public static Class<?> getClass(ObjectIdentifier class_) throws CertificateException {
        class_ = (class_ = oidMap.get(class_)) == null ? null : ((OIDInfo)((Object)class_)).getClazz();
        return class_;
    }

    public static String getName(ObjectIdentifier object) {
        object = (object = oidMap.get(object)) == null ? null : ((OIDInfo)object).name;
        return object;
    }

    public static ObjectIdentifier getOID(String object) {
        object = (object = nameMap.get(object)) == null ? null : ((OIDInfo)object).oid;
        return object;
    }

    private static class OIDInfo {
        private volatile Class<?> clazz;
        final String name;
        final ObjectIdentifier oid;

        OIDInfo(String string, ObjectIdentifier objectIdentifier, Class<?> class_) {
            this.name = string;
            this.oid = objectIdentifier;
            this.clazz = class_;
        }

        Class<?> getClazz() throws CertificateException {
            return this.clazz;
        }
    }

}

