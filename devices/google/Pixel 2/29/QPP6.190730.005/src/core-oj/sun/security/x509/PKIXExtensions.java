/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import sun.security.util.ObjectIdentifier;

public class PKIXExtensions {
    public static final ObjectIdentifier AuthInfoAccess_Id;
    private static final int[] AuthInfoAccess_data;
    public static final ObjectIdentifier AuthorityKey_Id;
    private static final int[] AuthorityKey_data;
    public static final ObjectIdentifier BasicConstraints_Id;
    private static final int[] BasicConstraints_data;
    public static final ObjectIdentifier CRLDistributionPoints_Id;
    private static final int[] CRLDistributionPoints_data;
    public static final ObjectIdentifier CRLNumber_Id;
    private static final int[] CRLNumber_data;
    public static final ObjectIdentifier CertificateIssuer_Id;
    private static final int[] CertificateIssuer_data;
    public static final ObjectIdentifier CertificatePolicies_Id;
    private static final int[] CertificatePolicies_data;
    public static final ObjectIdentifier DeltaCRLIndicator_Id;
    private static final int[] DeltaCRLIndicator_data;
    public static final ObjectIdentifier ExtendedKeyUsage_Id;
    private static final int[] ExtendedKeyUsage_data;
    public static final ObjectIdentifier FreshestCRL_Id;
    private static final int[] FreshestCRL_data;
    public static final ObjectIdentifier HoldInstructionCode_Id;
    private static final int[] HoldInstructionCode_data;
    public static final ObjectIdentifier InhibitAnyPolicy_Id;
    private static final int[] InhibitAnyPolicy_data;
    public static final ObjectIdentifier InvalidityDate_Id;
    private static final int[] InvalidityDate_data;
    public static final ObjectIdentifier IssuerAlternativeName_Id;
    private static final int[] IssuerAlternativeName_data;
    public static final ObjectIdentifier IssuingDistributionPoint_Id;
    private static final int[] IssuingDistributionPoint_data;
    public static final ObjectIdentifier KeyUsage_Id;
    private static final int[] KeyUsage_data;
    public static final ObjectIdentifier NameConstraints_Id;
    private static final int[] NameConstraints_data;
    public static final ObjectIdentifier OCSPNoCheck_Id;
    private static final int[] OCSPNoCheck_data;
    public static final ObjectIdentifier PolicyConstraints_Id;
    private static final int[] PolicyConstraints_data;
    public static final ObjectIdentifier PolicyMappings_Id;
    private static final int[] PolicyMappings_data;
    public static final ObjectIdentifier PrivateKeyUsage_Id;
    private static final int[] PrivateKeyUsage_data;
    public static final ObjectIdentifier ReasonCode_Id;
    private static final int[] ReasonCode_data;
    public static final ObjectIdentifier SubjectAlternativeName_Id;
    private static final int[] SubjectAlternativeName_data;
    public static final ObjectIdentifier SubjectDirectoryAttributes_Id;
    private static final int[] SubjectDirectoryAttributes_data;
    public static final ObjectIdentifier SubjectInfoAccess_Id;
    private static final int[] SubjectInfoAccess_data;
    public static final ObjectIdentifier SubjectKey_Id;
    private static final int[] SubjectKey_data;

    static {
        AuthorityKey_data = new int[]{2, 5, 29, 35};
        SubjectKey_data = new int[]{2, 5, 29, 14};
        KeyUsage_data = new int[]{2, 5, 29, 15};
        PrivateKeyUsage_data = new int[]{2, 5, 29, 16};
        CertificatePolicies_data = new int[]{2, 5, 29, 32};
        PolicyMappings_data = new int[]{2, 5, 29, 33};
        SubjectAlternativeName_data = new int[]{2, 5, 29, 17};
        IssuerAlternativeName_data = new int[]{2, 5, 29, 18};
        SubjectDirectoryAttributes_data = new int[]{2, 5, 29, 9};
        BasicConstraints_data = new int[]{2, 5, 29, 19};
        NameConstraints_data = new int[]{2, 5, 29, 30};
        PolicyConstraints_data = new int[]{2, 5, 29, 36};
        CRLDistributionPoints_data = new int[]{2, 5, 29, 31};
        CRLNumber_data = new int[]{2, 5, 29, 20};
        IssuingDistributionPoint_data = new int[]{2, 5, 29, 28};
        DeltaCRLIndicator_data = new int[]{2, 5, 29, 27};
        ReasonCode_data = new int[]{2, 5, 29, 21};
        HoldInstructionCode_data = new int[]{2, 5, 29, 23};
        InvalidityDate_data = new int[]{2, 5, 29, 24};
        ExtendedKeyUsage_data = new int[]{2, 5, 29, 37};
        InhibitAnyPolicy_data = new int[]{2, 5, 29, 54};
        CertificateIssuer_data = new int[]{2, 5, 29, 29};
        AuthInfoAccess_data = new int[]{1, 3, 6, 1, 5, 5, 7, 1, 1};
        SubjectInfoAccess_data = new int[]{1, 3, 6, 1, 5, 5, 7, 1, 11};
        FreshestCRL_data = new int[]{2, 5, 29, 46};
        OCSPNoCheck_data = new int[]{1, 3, 6, 1, 5, 5, 7, 48, 1, 5};
        AuthorityKey_Id = ObjectIdentifier.newInternal(AuthorityKey_data);
        SubjectKey_Id = ObjectIdentifier.newInternal(SubjectKey_data);
        KeyUsage_Id = ObjectIdentifier.newInternal(KeyUsage_data);
        PrivateKeyUsage_Id = ObjectIdentifier.newInternal(PrivateKeyUsage_data);
        CertificatePolicies_Id = ObjectIdentifier.newInternal(CertificatePolicies_data);
        PolicyMappings_Id = ObjectIdentifier.newInternal(PolicyMappings_data);
        SubjectAlternativeName_Id = ObjectIdentifier.newInternal(SubjectAlternativeName_data);
        IssuerAlternativeName_Id = ObjectIdentifier.newInternal(IssuerAlternativeName_data);
        ExtendedKeyUsage_Id = ObjectIdentifier.newInternal(ExtendedKeyUsage_data);
        InhibitAnyPolicy_Id = ObjectIdentifier.newInternal(InhibitAnyPolicy_data);
        SubjectDirectoryAttributes_Id = ObjectIdentifier.newInternal(SubjectDirectoryAttributes_data);
        BasicConstraints_Id = ObjectIdentifier.newInternal(BasicConstraints_data);
        ReasonCode_Id = ObjectIdentifier.newInternal(ReasonCode_data);
        HoldInstructionCode_Id = ObjectIdentifier.newInternal(HoldInstructionCode_data);
        InvalidityDate_Id = ObjectIdentifier.newInternal(InvalidityDate_data);
        NameConstraints_Id = ObjectIdentifier.newInternal(NameConstraints_data);
        PolicyConstraints_Id = ObjectIdentifier.newInternal(PolicyConstraints_data);
        CRLDistributionPoints_Id = ObjectIdentifier.newInternal(CRLDistributionPoints_data);
        CRLNumber_Id = ObjectIdentifier.newInternal(CRLNumber_data);
        IssuingDistributionPoint_Id = ObjectIdentifier.newInternal(IssuingDistributionPoint_data);
        DeltaCRLIndicator_Id = ObjectIdentifier.newInternal(DeltaCRLIndicator_data);
        CertificateIssuer_Id = ObjectIdentifier.newInternal(CertificateIssuer_data);
        AuthInfoAccess_Id = ObjectIdentifier.newInternal(AuthInfoAccess_data);
        SubjectInfoAccess_Id = ObjectIdentifier.newInternal(SubjectInfoAccess_data);
        FreshestCRL_Id = ObjectIdentifier.newInternal(FreshestCRL_data);
        OCSPNoCheck_Id = ObjectIdentifier.newInternal(OCSPNoCheck_data);
    }
}

