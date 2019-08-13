/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CRLSelector;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.AdaptableX509CertSelector;
import sun.security.provider.certpath.AlgorithmChecker;
import sun.security.provider.certpath.PKIX;
import sun.security.provider.certpath.URICertStore;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.RDN;
import sun.security.x509.ReasonFlags;
import sun.security.x509.SerialNumber;
import sun.security.x509.URIName;
import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;
import sun.security.x509.X509CertImpl;

public class DistributionPointFetcher {
    private static final boolean[] ALL_REASONS;
    private static final Debug debug;

    static {
        debug = Debug.getInstance("certpath");
        ALL_REASONS = new boolean[]{true, true, true, true, true, true, true, true, true};
    }

    private DistributionPointFetcher() {
    }

    private static X509CRL getCRL(URIName collection) throws CertStoreException {
        block4 : {
            StringBuilder stringBuilder;
            collection = ((URIName)((Object)collection)).getURI();
            Object object = debug;
            if (object != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to fetch CRL from DP ");
                stringBuilder.append(collection);
                ((Debug)object).println(stringBuilder.toString());
            }
            try {
                object = new URICertStore.URICertStoreParameters((URI)((Object)collection));
                collection = URICertStore.getInstance((URICertStore.URICertStoreParameters)object);
                collection = ((CertStore)((Object)collection)).getCRLs(null);
                if (!collection.isEmpty()) break block4;
                return null;
            }
            catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException generalSecurityException) {
                object = debug;
                if (object != null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Can't create URICertStore: ");
                    stringBuilder.append(generalSecurityException.getMessage());
                    ((Debug)object).println(stringBuilder.toString());
                }
                return null;
            }
        }
        return (X509CRL)collection.iterator().next();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Collection<X509CRL> getCRLs(X509CRLSelector var0, X509CertImpl var1_2, DistributionPoint var2_3, boolean[] var3_4, boolean var4_5, PublicKey var5_6, X509Certificate var6_7, String var7_8, List<CertStore> var8_9, Set<TrustAnchor> var9_10, Date var10_11) throws CertStoreException {
        var12_13 = var11_12 = var2_3.getFullName();
        if (var11_12 == null) {
            var11_12 = var2_3.getRelativeName();
            if (var11_12 == null) {
                return Collections.emptySet();
            }
            try {
                var12_13 = var2_3.getCRLIssuer();
                if (var12_13 == null) {
                    var12_13 = DistributionPointFetcher.getFullNames((X500Name)var1_2.getIssuerDN(), (RDN)var11_12);
                } else {
                    if (var12_13.size() != 1) {
                        return Collections.emptySet();
                    }
                    var12_13 = DistributionPointFetcher.getFullNames((X500Name)var12_13.get(0).getName(), (RDN)var11_12);
                }
            }
            catch (IOException var0_1) {
                return Collections.emptySet();
            }
        }
        var13_16 = new ArrayList<X509CRL>();
        var14_17 = var12_13.iterator();
        var12_13 = null;
        while (var14_17.hasNext()) {
            block18 : {
                block19 : {
                    var15_19 = var14_17.next();
                    if (var15_19.getType() != 4) ** GOTO lbl31
                    var11_12 = (X500Name)var15_19.getName();
                    var15_19 = var1_2.getIssuerX500Principal();
                    try {
                        var13_16.addAll(DistributionPointFetcher.getCRLs((X500Name)var11_12, (X500Principal)var15_19, var8_9));
                        var11_12 = var12_13;
                        break block18;
lbl31: // 1 sources:
                        var11_12 = var12_13;
                        if (var15_19.getType() == 6) {
                            var15_19 = DistributionPointFetcher.getCRL((URIName)var15_19.getName());
                            var11_12 = var12_13;
                            if (var15_19 != null) {
                                var13_16.add(var15_19);
                                var11_12 = var12_13;
                            }
                        }
                        break block18;
                    }
                    catch (CertStoreException var12_14) {
                        break block19;
                    }
                    catch (CertStoreException var12_15) {
                        // empty catch block
                    }
                }
                var11_12 = var12_13;
            }
            var12_13 = var11_12;
        }
        if (var13_16.isEmpty()) {
            if (var12_13 != null) throw var12_13;
        }
        var12_13 = new ArrayList<E>(2);
        var11_12 = var13_16.iterator();
        while (var11_12.hasNext() != false) {
            var13_16 = (X509CRL)var11_12.next();
            try {
                var0.setIssuerNames(null);
                if (!var0.match((CRL)var13_16) || !DistributionPointFetcher.verifyCRL(var1_2, var2_3, (X509CRL)var13_16, var3_4, var4_5, var5_6, var6_7, var7_8, var9_10, var8_9, var10_11)) continue;
                var12_13.add(var13_16);
            }
            catch (IOException | CRLException var14_18) {
                var15_19 = DistributionPointFetcher.debug;
                if (var15_19 == null) continue;
                var13_16 = new StringBuilder();
                var13_16.append("Exception verifying CRL: ");
                var13_16.append(var14_18.getMessage());
                var15_19.println(var13_16.toString());
                var14_18.printStackTrace();
            }
        }
        return var12_13;
    }

    public static Collection<X509CRL> getCRLs(X509CRLSelector x509CRLSelector, boolean bl, PublicKey publicKey, String string, List<CertStore> list, boolean[] arrbl, Set<TrustAnchor> set, Date date) throws CertStoreException {
        return DistributionPointFetcher.getCRLs(x509CRLSelector, bl, publicKey, null, string, list, arrbl, set, date);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Collection<X509CRL> getCRLs(X509CRLSelector object, boolean bl, PublicKey serializable, X509Certificate x509Certificate, String string, List<CertStore> list, boolean[] arrbl, Set<TrustAnchor> set, Date date) throws CertStoreException {
        X509Certificate x509Certificate2 = ((X509CRLSelector)object).getCertificateChecking();
        if (x509Certificate2 == null) {
            return Collections.emptySet();
        }
        try {
            Iterator iterator;
            HashSet<X509CRL> hashSet;
            x509Certificate2 = X509CertImpl.toImpl(x509Certificate2);
            if (debug != null) {
                hashSet = debug;
                iterator = new Iterator();
                ((StringBuilder)((Object)iterator)).append("DistributionPointFetcher.getCRLs: Checking CRLDPs for ");
                ((StringBuilder)((Object)iterator)).append(((X509CertImpl)x509Certificate2).getSubjectX500Principal());
                ((Debug)((Object)hashSet)).println(((StringBuilder)((Object)iterator)).toString());
            }
            if ((hashSet = ((X509CertImpl)x509Certificate2).getCRLDistributionPointsExtension()) == null) {
                if (debug != null) {
                    debug.println("No CRLDP ext");
                }
                return Collections.emptySet();
            }
            iterator = ((CRLDistributionPointsExtension)((Object)hashSet)).get("points");
            hashSet = new HashSet<X509CRL>();
            iterator = iterator.iterator();
            while (iterator.hasNext() && !Arrays.equals(arrbl, ALL_REASONS)) {
                hashSet.addAll(DistributionPointFetcher.getCRLs((X509CRLSelector)object, (X509CertImpl)x509Certificate2, (DistributionPoint)iterator.next(), arrbl, bl, (PublicKey)serializable, x509Certificate, string, list, set, date));
            }
            if (debug != null) {
                object = debug;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Returning ");
                ((StringBuilder)serializable).append(hashSet.size());
                ((StringBuilder)serializable).append(" CRLs");
                ((Debug)object).println(((StringBuilder)serializable).toString());
            }
            return hashSet;
        }
        catch (IOException | CertificateException exception) {
            return Collections.emptySet();
        }
    }

    private static Collection<X509CRL> getCRLs(X500Name object, X500Principal serializable, List<CertStore> object2) throws CertStoreException {
        Object object3;
        Object object4 = debug;
        if (object4 != null) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Trying to fetch CRL from DP ");
            ((StringBuilder)object3).append(object);
            ((Debug)object4).println(((StringBuilder)object3).toString());
        }
        object3 = new X509CRLSelector();
        ((X509CRLSelector)object3).addIssuer(((X500Name)object).asX500Principal());
        ((X509CRLSelector)object3).addIssuer((X500Principal)serializable);
        serializable = new ArrayList();
        object = null;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object4 = (CertStore)object2.next();
            try {
                Iterator<? extends CRL> iterator = ((CertStore)object4).getCRLs((CRLSelector)object3).iterator();
                while (iterator.hasNext()) {
                    serializable.add((X509CRL)iterator.next());
                }
            }
            catch (CertStoreException certStoreException) {
                object = debug;
                if (object != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Exception while retrieving CRLs: ");
                    stringBuilder.append(certStoreException);
                    ((Debug)object).println(stringBuilder.toString());
                    certStoreException.printStackTrace();
                }
                object = new PKIX.CertStoreTypeException(((CertStore)object4).getType(), certStoreException);
            }
        }
        if (serializable.isEmpty() && object != null) {
            throw object;
        }
        return serializable;
    }

    private static GeneralNames getFullNames(X500Name object, RDN object2) throws IOException {
        object = new ArrayList<RDN>(((X500Name)object).rdns());
        object.add(object2);
        object2 = new X500Name(object.toArray(new RDN[0]));
        object = new GeneralNames();
        ((GeneralNames)object).add(new GeneralName((GeneralNameInterface)object2));
        return object;
    }

    private static boolean issues(X509CertImpl x509CertImpl, X509CRLImpl x509CRLImpl, String string) throws IOException {
        boolean bl;
        block6 : {
            block7 : {
                boolean bl2;
                AdaptableX509CertSelector adaptableX509CertSelector = new AdaptableX509CertSelector();
                Object object = x509CertImpl.getKeyUsage();
                if (object != null) {
                    object[6] = true;
                    adaptableX509CertSelector.setKeyUsage((boolean[])object);
                }
                adaptableX509CertSelector.setSubject(x509CRLImpl.getIssuerX500Principal());
                object = x509CRLImpl.getAuthKeyIdExtension();
                adaptableX509CertSelector.setSkiAndSerialNumber((AuthorityKeyIdentifierExtension)object);
                bl = bl2 = adaptableX509CertSelector.match(x509CertImpl);
                if (!bl2) break block6;
                if (object == null) break block7;
                bl = bl2;
                if (x509CertImpl.getAuthorityKeyIdentifierExtension() != null) break block6;
            }
            try {
                x509CRLImpl.verify(x509CertImpl.getPublicKey(), string);
                bl = true;
            }
            catch (GeneralSecurityException generalSecurityException) {
                bl = false;
            }
        }
        return bl;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static boolean verifyCRL(X509CertImpl object, DistributionPoint object2, X509CRL object3, boolean[] arrbl, boolean bl, PublicKey object4, X509Certificate x509Certificate, String string, Set<TrustAnchor> set, List<CertStore> list, Date date) throws CRLException, IOException {
        int n;
        block75 : {
            Object object5;
            Object object6;
            X500Name x500Name;
            int n2;
            X509CRLImpl x509CRLImpl;
            block70 : {
                block71 : {
                    block69 : {
                        block72 : {
                            IssuingDistributionPointExtension issuingDistributionPointExtension;
                            block67 : {
                                block68 : {
                                    object6 = debug;
                                    if (object6 != null) {
                                        object5 = new StringBuilder();
                                        ((StringBuilder)object5).append("DistributionPointFetcher.verifyCRL: checking revocation status for\n  SN: ");
                                        ((StringBuilder)object5).append(Debug.toHexString(((X509CertImpl)object).getSerialNumber()));
                                        ((StringBuilder)object5).append("\n  Subject: ");
                                        ((StringBuilder)object5).append(((X509CertImpl)object).getSubjectX500Principal());
                                        ((StringBuilder)object5).append("\n  Issuer: ");
                                        ((StringBuilder)object5).append(((X509CertImpl)object).getIssuerX500Principal());
                                        ((Debug)object6).println(((StringBuilder)object5).toString());
                                    }
                                    n = 0;
                                    x509CRLImpl = X509CRLImpl.toImpl((X509CRL)object3);
                                    issuingDistributionPointExtension = x509CRLImpl.getIssuingDistributionPointExtension();
                                    Object object7 = (X500Name)((X509CertImpl)object).getIssuerDN();
                                    x500Name = (X500Name)x509CRLImpl.getIssuerDN();
                                    Object object8 = ((DistributionPoint)object2).getCRLIssuer();
                                    object6 = null;
                                    object5 = null;
                                    if (object8 != null) {
                                        if (issuingDistributionPointExtension == null) return false;
                                        if (((Boolean)issuingDistributionPointExtension.get("indirect_crl")).equals(Boolean.FALSE)) {
                                            return false;
                                        }
                                        n2 = 0;
                                        Iterator<GeneralName> iterator = ((GeneralNames)object8).iterator();
                                        object6 = object5;
                                        while (n2 == 0 && iterator.hasNext()) {
                                            object5 = iterator.next().getName();
                                            if (!x500Name.equals(object5)) continue;
                                            object6 = (X500Name)object5;
                                            n2 = 1;
                                        }
                                        if (n2 == 0) {
                                            return false;
                                        }
                                        if (DistributionPointFetcher.issues((X509CertImpl)object, x509CRLImpl, string)) {
                                            object4 = ((X509CertImpl)object).getPublicKey();
                                        } else {
                                            n = 1;
                                        }
                                    } else {
                                        n = 0;
                                        if (!x500Name.equals(object7)) {
                                            object2 = debug;
                                            if (object2 == null) return false;
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("crl issuer does not equal cert issuer.\ncrl issuer: ");
                                            ((StringBuilder)object).append(x500Name);
                                            ((StringBuilder)object).append("\ncert issuer: ");
                                            ((StringBuilder)object).append(object7);
                                            ((Debug)object2).println(((StringBuilder)object).toString());
                                            return false;
                                        }
                                        object5 = ((X509CertImpl)object).getAuthKeyId();
                                        KeyIdentifier keyIdentifier = x509CRLImpl.getAuthKeyId();
                                        if (object5 != null && keyIdentifier != null) {
                                            if (!((KeyIdentifier)object5).equals(keyIdentifier)) {
                                                if (DistributionPointFetcher.issues((X509CertImpl)object, x509CRLImpl, string)) {
                                                    object4 = ((X509CertImpl)object).getPublicKey();
                                                } else {
                                                    n = 1;
                                                }
                                            }
                                        } else if (DistributionPointFetcher.issues((X509CertImpl)object, x509CRLImpl, string)) {
                                            object4 = ((X509CertImpl)object).getPublicKey();
                                        }
                                    }
                                    if (n == 0 && !bl) {
                                        return false;
                                    }
                                    if (issuingDistributionPointExtension != null) {
                                        object5 = (DistributionPointName)issuingDistributionPointExtension.get("point");
                                        if (object5 != null) {
                                            void var20_32;
                                            Object object9;
                                            GeneralNames generalNames = ((DistributionPointName)object5).getFullName();
                                            if (generalNames == null) {
                                                object9 = ((DistributionPointName)object5).getRelativeName();
                                                if (object9 == null) {
                                                    object = debug;
                                                    if (object == null) return false;
                                                    ((Debug)object).println("IDP must be relative or full DN");
                                                    return false;
                                                }
                                                object5 = debug;
                                                if (object5 != null) {
                                                    StringBuilder stringBuilder = new StringBuilder();
                                                    stringBuilder.append("IDP relativeName:");
                                                    stringBuilder.append(object9);
                                                    ((Debug)object5).println(stringBuilder.toString());
                                                }
                                                GeneralNames generalNames2 = DistributionPointFetcher.getFullNames(x500Name, (RDN)object9);
                                            }
                                            if (((DistributionPoint)object2).getFullName() == null && ((DistributionPoint)object2).getRelativeName() == null) {
                                                bl = false;
                                                object7 = ((GeneralNames)object8).iterator();
                                                while (!bl && object7.hasNext()) {
                                                    object5 = ((GeneralName)object7.next()).getName();
                                                    object6 = var20_32.iterator();
                                                    while (!bl && object6.hasNext()) {
                                                        bl = object5.equals(((GeneralName)object6.next()).getName());
                                                    }
                                                }
                                                if (!bl) {
                                                    return false;
                                                }
                                            } else {
                                                Object object10;
                                                object5 = ((DistributionPoint)object2).getFullName();
                                                if (object5 == null) {
                                                    object9 = ((DistributionPoint)object2).getRelativeName();
                                                    if (object9 == null) {
                                                        object = debug;
                                                        if (object == null) return false;
                                                        ((Debug)object).println("DP must be relative or full DN");
                                                        return false;
                                                    }
                                                    object5 = debug;
                                                    if (object5 != null) {
                                                        object10 = new StringBuilder();
                                                        ((StringBuilder)object10).append("DP relativeName:");
                                                        ((StringBuilder)object10).append(object9);
                                                        ((Debug)object5).println(((StringBuilder)object10).toString());
                                                    }
                                                    if (n != 0) {
                                                        if (((GeneralNames)object8).size() != 1) {
                                                            object = debug;
                                                            if (object == null) return false;
                                                            ((Debug)object).println("must only be one CRL issuer when relative name present");
                                                            return false;
                                                        }
                                                        object5 = DistributionPointFetcher.getFullNames((X500Name)object6, (RDN)object9);
                                                    } else {
                                                        object5 = DistributionPointFetcher.getFullNames((X500Name)object7, (RDN)object9);
                                                    }
                                                }
                                                bl = false;
                                                object9 = var20_32.iterator();
                                                GeneralNames generalNames3 = object8;
                                                while (!bl && object9.hasNext()) {
                                                    object8 = ((GeneralName)object9.next()).getName();
                                                    Object object11 = debug;
                                                    if (object11 != null) {
                                                        object10 = new StringBuilder();
                                                        ((StringBuilder)object10).append("idpName: ");
                                                        ((StringBuilder)object10).append(object8);
                                                        ((Debug)object11).println(((StringBuilder)object10).toString());
                                                    }
                                                    object10 = ((GeneralNames)object5).iterator();
                                                    while (!bl && object10.hasNext()) {
                                                        object11 = ((GeneralName)object10.next()).getName();
                                                        Debug debug = DistributionPointFetcher.debug;
                                                        if (debug != null) {
                                                            StringBuilder stringBuilder = new StringBuilder();
                                                            stringBuilder.append("pointName: ");
                                                            stringBuilder.append(object11);
                                                            debug.println(stringBuilder.toString());
                                                        }
                                                        bl = object8.equals(object11);
                                                    }
                                                }
                                                if (!bl) {
                                                    object = debug;
                                                    if (object == null) return false;
                                                    ((Debug)object).println("IDP name does not match DP name");
                                                    return false;
                                                }
                                            }
                                        }
                                        if (((Boolean)issuingDistributionPointExtension.get("only_user_certs")).equals(Boolean.TRUE) && ((X509CertImpl)object).getBasicConstraints() != -1) {
                                            object = debug;
                                            if (object == null) return false;
                                            ((Debug)object).println("cert must be a EE cert");
                                            return false;
                                        }
                                        if (((Boolean)issuingDistributionPointExtension.get("only_ca_certs")).equals(Boolean.TRUE) && ((X509CertImpl)object).getBasicConstraints() == -1) {
                                            object = debug;
                                            if (object == null) return false;
                                            ((Debug)object).println("cert must be a CA cert");
                                            return false;
                                        }
                                        if (((Boolean)issuingDistributionPointExtension.get("only_attribute_certs")).equals(Boolean.TRUE)) {
                                            object = debug;
                                            if (object == null) return false;
                                            ((Debug)object).println("cert must not be an AA cert");
                                            return false;
                                        }
                                    }
                                    object6 = new boolean[9];
                                    object5 = null;
                                    if (issuingDistributionPointExtension != null) {
                                        object5 = (ReasonFlags)issuingDistributionPointExtension.get("reasons");
                                    }
                                    object2 = ((DistributionPoint)object2).getReasonFlags();
                                    if (object5 == null) break block67;
                                    if (object2 == null) break block68;
                                    object5 = ((ReasonFlags)object5).getFlags();
                                    break block69;
                                }
                                object2 = (boolean[])((ReasonFlags)object5).getFlags().clone();
                                break block70;
                            }
                            if (issuingDistributionPointExtension != null && object5 != null) break block71;
                            if (object2 == null) break block72;
                            object2 = (boolean[])object2.clone();
                            break block70;
                        }
                        Arrays.fill((boolean[])object6, true);
                        break block71;
                    }
                    for (n2 = 0; n2 < ((boolean[])object6).length; ++n2) {
                        bl = n2 < ((boolean[])object5).length && object5[n2] && n2 < ((Object)object2).length && object2[n2] != false;
                        object6[n2] = bl;
                    }
                }
                object2 = object6;
            }
            n2 = 0;
            for (int i = 0; i < ((Object)object2).length && n2 == 0; ++i) {
                int n3;
                block73 : {
                    block74 : {
                        n3 = n2;
                        if (object2[i] == false) break block73;
                        if (i >= arrbl.length) break block74;
                        n3 = n2;
                        if (arrbl[i]) break block73;
                    }
                    n3 = 1;
                }
                n2 = n3;
            }
            if (n2 == 0) {
                return false;
            }
            if (n != 0) {
                object5 = new X509CertSelector();
                ((X509CertSelector)object5).setSubject(x500Name.asX500Principal());
                ((X509CertSelector)object5).setKeyUsage(new boolean[]{false, false, false, false, false, false, true});
                object6 = x509CRLImpl.getAuthKeyIdExtension();
                if (object6 != null) {
                    byte[] arrby = ((AuthorityKeyIdentifierExtension)object6).getEncodedKeyIdentifier();
                    if (arrby != null) {
                        ((X509CertSelector)object5).setSubjectKeyIdentifier(arrby);
                    }
                    if ((object6 = (SerialNumber)((AuthorityKeyIdentifierExtension)object6).get("serial_number")) != null) {
                        ((X509CertSelector)object5).setSerialNumber(((SerialNumber)object6).getNumber());
                    }
                }
                set = new HashSet<TrustAnchor>(set);
                if (object4 != null) {
                    object = x509Certificate != null ? new TrustAnchor(x509Certificate, null) : new TrustAnchor(((X509CertImpl)object).getIssuerX500Principal(), (PublicKey)object4, null);
                    set.add((TrustAnchor)object);
                }
                try {
                    object = new PKIXBuilderParameters(set, (CertSelector)object5);
                }
                catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                    throw new CRLException(invalidAlgorithmParameterException);
                }
                ((PKIXParameters)object).setCertStores(list);
                ((PKIXParameters)object).setSigProvider(string);
                ((PKIXParameters)object).setDate(date);
                try {
                    object4 = CertPathBuilder.getInstance("PKIX");
                    object = ((PKIXCertPathBuilderResult)((CertPathBuilder)object4).build((CertPathParameters)object)).getPublicKey();
                }
                catch (GeneralSecurityException generalSecurityException) {
                    throw new CRLException(generalSecurityException);
                }
            }
            object = object4;
            AlgorithmChecker.check((PublicKey)object, (X509CRL)object3);
            try {
                ((X509CRL)object3).verify((PublicKey)object, string);
            }
            catch (GeneralSecurityException generalSecurityException) {
                Debug debug = DistributionPointFetcher.debug;
                if (debug == null) return false;
                debug.println("CRL signature failed to verify");
                return false;
            }
            object = object3.getCriticalExtensionOIDs();
            if (object != null) {
                object.remove(PKIXExtensions.IssuingDistributionPoint_Id.toString());
                if (!object.isEmpty()) {
                    object2 = debug;
                    if (object2 == null) return false;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Unrecognized critical extension(s) in CRL: ");
                    ((StringBuilder)object3).append(object);
                    ((Debug)object2).println(((StringBuilder)object3).toString());
                    object2 = object.iterator();
                    while (object2.hasNext()) {
                        object = (String)object2.next();
                        debug.println((String)object);
                    }
                    return false;
                }
            }
            break block75;
            catch (CertPathValidatorException certPathValidatorException) {
                object = debug;
                if (object == null) return false;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("CRL signature algorithm check failed: ");
                ((StringBuilder)object2).append(certPathValidatorException);
                ((Debug)object).println(((StringBuilder)object2).toString());
                return false;
            }
        }
        n = 0;
        while (n < arrbl.length) {
            bl = arrbl[n] || n < ((Object)object2).length && object2[n] != false;
            arrbl[n] = bl;
            ++n;
        }
        return true;
    }
}

