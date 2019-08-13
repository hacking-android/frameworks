/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CRLReason;
import java.security.cert.CRLSelector;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateRevokedException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.Extension;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.BasicChecker;
import sun.security.provider.certpath.CertId;
import sun.security.provider.certpath.CertPathHelper;
import sun.security.provider.certpath.CertStoreHelper;
import sun.security.provider.certpath.DistributionPointFetcher;
import sun.security.provider.certpath.OCSP;
import sun.security.provider.certpath.OCSPResponse;
import sun.security.provider.certpath.PKIX;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.SerialNumber;
import sun.security.x509.X509CRLEntryImpl;
import sun.security.x509.X509CertImpl;

class RevocationChecker
extends PKIXRevocationChecker {
    private static final boolean[] ALL_REASONS;
    private static final boolean[] CRL_SIGN_USAGE;
    private static final String HEX_DIGITS = "0123456789ABCDEFabcdef";
    private static final long MAX_CLOCK_SKEW = 900000L;
    private static final Debug debug;
    private TrustAnchor anchor;
    private int certIndex;
    private List<CertStore> certStores;
    private boolean crlDP;
    private boolean crlSignFlag;
    private X509Certificate issuerCert;
    private boolean legacy;
    private Mode mode = Mode.PREFER_OCSP;
    private List<Extension> ocspExtensions;
    private Map<X509Certificate, byte[]> ocspResponses;
    private boolean onlyEE;
    private PKIX.ValidatorParams params;
    private PublicKey prevPubKey;
    private X509Certificate responderCert;
    private URI responderURI;
    private boolean softFail;
    private LinkedList<CertPathValidatorException> softFailExceptions = new LinkedList();

    static {
        debug = Debug.getInstance("certpath");
        ALL_REASONS = new boolean[]{true, true, true, true, true, true, true, true, true};
        CRL_SIGN_USAGE = new boolean[]{false, false, false, false, false, false, true};
    }

    RevocationChecker() {
        this.legacy = false;
    }

    RevocationChecker(TrustAnchor trustAnchor, PKIX.ValidatorParams validatorParams) throws CertPathValidatorException {
        this.legacy = true;
        this.init(trustAnchor, validatorParams);
    }

    /*
     * Exception decompiling
     */
    private void buildToNewKey(X509Certificate var1_1, PublicKey var2_15, Set<X509Certificate> var3_16) throws CertPathValidatorException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[TRYBLOCK]], but top level block is 16[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    static boolean certCanSignCrl(X509Certificate arrbl) {
        if ((arrbl = arrbl.getKeyUsage()) != null) {
            return arrbl[6];
        }
        return false;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void check(X509Certificate var1_1, Collection<String> var2_2, PublicKey var3_4, boolean var4_5) throws CertPathValidatorException {
        block19 : {
            block18 : {
                block16 : {
                    block17 : {
                        var5_6 = RevocationChecker.debug;
                        if (var5_6 != null) {
                            var6_8 = new StringBuilder();
                            var6_8.append("RevocationChecker.check: checking cert\n  SN: ");
                            var6_8.append(Debug.toHexString(var1_1.getSerialNumber()));
                            var6_8.append("\n  Subject: ");
                            var6_8.append(var1_1.getSubjectX500Principal());
                            var6_8.append("\n  Issuer: ");
                            var6_8.append(var1_1.getIssuerX500Principal());
                            var5_6.println(var6_8.toString());
                        }
                        if (!this.onlyEE || var1_1.getBasicConstraints() == -1) break block16;
                        if (RevocationChecker.debug == null) break block17;
                        RevocationChecker.debug.println("Skipping revocation check; cert is not an end entity cert");
                    }
                    this.updateState(var1_1);
                    return;
                }
                var7_10 = 2.$SwitchMap$sun$security$provider$certpath$RevocationChecker$Mode[this.mode.ordinal()];
                if (var7_10 != 1 && var7_10 != 2) {
                    if (var7_10 == 3 || var7_10 == 4) {
                        this.checkCRLs(var1_1, (Collection<String>)var2_2, null, (PublicKey)var3_4, var4_5);
                    }
                    break block18;
                }
                this.checkOCSP(var1_1, (Collection<String>)var2_2);
            }
lbl33: // 4 sources:
            do {
                this.updateState(var1_1);
                return;
                break;
            } while (true);
            {
                catch (Throwable var2_3) {
                    break block19;
                }
                catch (CertPathValidatorException var6_9) {}
                {
                    if (var6_9.getReason() == CertPathValidatorException.BasicReason.REVOKED) throw var6_9;
                    var8_12 = this.isSoftFailException(var6_9);
                    if (!var8_12) ** GOTO lbl-1000
                    ** if (this.mode != Mode.ONLY_OCSP && (var5_6 = this.mode) != (var9_13 = Mode.ONLY_CRLS)) goto lbl45
                }
lbl-1000: // 1 sources:
                {
                    this.updateState(var1_1);
                    return;
                }
lbl45: // 1 sources:
                ** GOTO lbl48
lbl-1000: // 1 sources:
                {
                    block20 : {
                        if (this.mode == Mode.ONLY_OCSP) throw var6_9;
                        if (this.mode == Mode.ONLY_CRLS) throw var6_9;
lbl48: // 2 sources:
                        if ((var5_6 = RevocationChecker.debug) != null) {
                            var9_13 = RevocationChecker.debug;
                            var5_6 = new StringBuilder();
                            var5_6.append("RevocationChecker.check() ");
                            var5_6.append(var6_9.getMessage());
                            var9_13.println(var5_6.toString());
                            RevocationChecker.debug.println("RevocationChecker.check() preparing to failover");
                        }
                        try {
                            block21 : {
                                var7_11 = 2.$SwitchMap$sun$security$provider$certpath$RevocationChecker$Mode[this.mode.ordinal()];
                                if (var7_11 == 1) break block21;
                                if (var7_11 == 3) {
                                    this.checkOCSP(var1_1, (Collection<String>)var2_2);
                                }
                                ** GOTO lbl33
                            }
                            this.checkCRLs(var1_1, (Collection<String>)var2_2, null, (PublicKey)var3_4, var4_5);
                        }
                        catch (CertPathValidatorException var5_7) {
                            if (RevocationChecker.debug != null) {
                                RevocationChecker.debug.println("RevocationChecker.check() failover failed");
                                var3_4 = RevocationChecker.debug;
                                var2_2 = new StringBuilder();
                                var2_2.append("RevocationChecker.check() ");
                                var2_2.append(var5_7.getMessage());
                                var3_4.println(var2_2.toString());
                            }
                            if (var5_7.getReason() == CertPathValidatorException.BasicReason.REVOKED) throw var5_7;
                            if (!this.isSoftFailException(var5_7)) break block20;
                            if (var8_12 == false) throw var6_9;
                        }
                        ** continue;
                    }
                    var6_9.addSuppressed(var5_7);
                    throw var6_9;
                }
            }
        }
        this.updateState(var1_1);
        throw var2_3;
    }

    private void checkApprovedCRLs(X509Certificate serializable, Set<X509CRL> object) throws CertPathValidatorException {
        Object object2;
        Object object3;
        Object object4;
        if (debug != null) {
            object3 = ((X509Certificate)serializable).getSerialNumber();
            debug.println("RevocationChecker.checkApprovedCRLs() starting the final sweep...");
            object2 = debug;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("RevocationChecker.checkApprovedCRLs() cert SN: ");
            ((StringBuilder)object4).append(((BigInteger)object3).toString());
            ((Debug)object2).println(((StringBuilder)object4).toString());
        }
        object3 = CRLReason.UNSPECIFIED;
        object2 = object.iterator();
        while (object2.hasNext()) {
            X509CRLEntryImpl x509CRLEntryImpl;
            block7 : {
                object4 = (X509CRL)object2.next();
                object = ((X509CRL)object4).getRevokedCertificate((X509Certificate)serializable);
                if (object == null) continue;
                try {
                    x509CRLEntryImpl = X509CRLEntryImpl.toImpl((X509CRLEntry)object);
                    object = debug;
                    if (object == null) break block7;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("RevocationChecker.checkApprovedCRLs() CRL entry: ");
                }
                catch (CRLException cRLException) {
                    throw new CertPathValidatorException(cRLException);
                }
                ((StringBuilder)object3).append(x509CRLEntryImpl.toString());
                ((Debug)object).println(((StringBuilder)object3).toString());
            }
            if ((object = x509CRLEntryImpl.getCriticalExtensionOIDs()) != null && !object.isEmpty()) {
                object.remove(PKIXExtensions.ReasonCode_Id.toString());
                object.remove(PKIXExtensions.CertificateIssuer_Id.toString());
                if (!object.isEmpty()) {
                    throw new CertPathValidatorException("Unrecognized critical extension(s) in revoked CRL entry");
                }
            }
            object = object3 = x509CRLEntryImpl.getRevocationReason();
            if (object3 == null) {
                object = CRLReason.UNSPECIFIED;
            }
            if (!((Date)(object3 = x509CRLEntryImpl.getRevocationDate())).before(this.params.date())) continue;
            serializable = new CertificateRevokedException((Date)object3, (CRLReason)((Object)object), ((X509CRL)object4).getIssuerX500Principal(), x509CRLEntryImpl.getExtensions());
            throw new CertPathValidatorException(((Throwable)serializable).getMessage(), (Throwable)serializable, null, -1, CertPathValidatorException.BasicReason.REVOKED);
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
    private void checkCRLs(X509Certificate object, PublicKey publicKey, X509Certificate x509Certificate, boolean bl, boolean bl2, Set<X509Certificate> set, Set<TrustAnchor> set2) throws CertPathValidatorException {
        void var1_6;
        block23 : {
            HashSet<X509CRL> hashSet;
            Object object2;
            Object object3;
            block22 : {
                object2 = debug;
                if (object2 != null) {
                    ((Debug)object2).println("RevocationChecker.checkCRLs() ---checking revocation status ...");
                }
                if (set != null && set.contains(object)) {
                    object = debug;
                    if (object == null) throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                    ((Debug)object).println("RevocationChecker.checkCRLs() circular dependency");
                    throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                }
                Object object4 = new HashSet();
                hashSet = new HashSet<X509CRL>();
                X509CRLSelector x509CRLSelector = new X509CRLSelector();
                x509CRLSelector.setCertificateChecking((X509Certificate)object);
                CertPathHelper.setDateAndTime(x509CRLSelector, this.params.date(), 900000L);
                List<CertStore> list = this.certStores.iterator();
                object2 = null;
                while (list.hasNext()) {
                    block21 : {
                        CertStore certStore = list.next();
                        try {
                            object3 = certStore.getCRLs(x509CRLSelector).iterator();
                            while (object3.hasNext()) {
                                object4.add((X509CRL)((CRL)object3.next()));
                            }
                            object3 = object2;
                        }
                        catch (CertStoreException certStoreException) {
                            Debug debug = RevocationChecker.debug;
                            if (debug != null) {
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append("RevocationChecker.checkCRLs() CertStoreException: ");
                                ((StringBuilder)object3).append(certStoreException.getMessage());
                                debug.println(((StringBuilder)object3).toString());
                            }
                            object3 = object2;
                            if (object2 != null) break block21;
                            object3 = object2;
                            if (!CertStoreHelper.isCausedByNetworkIssue(certStore.getType(), certStoreException)) break block21;
                            object3 = new CertPathValidatorException("Unable to determine revocation status due to network error", certStoreException, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                        }
                    }
                    object2 = object3;
                }
                object3 = debug;
                if (object3 != null) {
                    list = new StringBuilder();
                    ((StringBuilder)((Object)list)).append("RevocationChecker.checkCRLs() possible crls.size() = ");
                    ((StringBuilder)((Object)list)).append(object4.size());
                    ((Debug)object3).println(((StringBuilder)((Object)list)).toString());
                }
                object3 = new boolean[9];
                if (!object4.isEmpty()) {
                    hashSet.addAll(this.verifyPossibleCRLs((Set<X509CRL>)object4, (X509Certificate)object, publicKey, bl, (boolean[])object3, set2));
                }
                if ((object4 = debug) != null) {
                    list = new StringBuilder();
                    ((StringBuilder)((Object)list)).append("RevocationChecker.checkCRLs() approved crls.size() = ");
                    ((StringBuilder)((Object)list)).append(hashSet.size());
                    ((Debug)object4).println(((StringBuilder)((Object)list)).toString());
                }
                if (!hashSet.isEmpty() && Arrays.equals((boolean[])object3, ALL_REASONS)) {
                    this.checkApprovedCRLs((X509Certificate)object, hashSet);
                    return;
                }
                boolean bl3 = this.crlDP;
                if (!bl3) break block22;
                try {
                    object4 = this.params.sigProvider();
                    list = this.certStores;
                }
                catch (CertStoreException certStoreException) {}
                try {
                    hashSet.addAll(DistributionPointFetcher.getCRLs(x509CRLSelector, bl, publicKey, x509Certificate, (String)object4, list, (boolean[])object3, set2, null));
                    break block22;
                }
                catch (CertStoreException certStoreException) {
                    break block23;
                }
                break block23;
            }
            if (!hashSet.isEmpty() && Arrays.equals((boolean[])object3, ALL_REASONS)) {
                this.checkApprovedCRLs((X509Certificate)object, hashSet);
                return;
            }
            if (!bl2) {
                if (object2 == null) throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                throw object2;
            }
            try {
                this.verifyWithSeparateSigningKey((X509Certificate)object, publicKey, bl, set);
                return;
            }
            catch (CertPathValidatorException certPathValidatorException) {
                if (object2 == null) throw certPathValidatorException;
                throw object2;
            }
            catch (CertStoreException certStoreException) {
                // empty catch block
            }
        }
        if (!(var1_6 instanceof PKIX.CertStoreTypeException)) throw new CertPathValidatorException((Throwable)var1_6);
        if (!CertStoreHelper.isCausedByNetworkIssue(((PKIX.CertStoreTypeException)var1_6).getType(), (CertStoreException)var1_6)) throw new CertPathValidatorException((Throwable)var1_6);
        throw new CertPathValidatorException("Unable to determine revocation status due to network error", (Throwable)var1_6, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
    }

    private void checkCRLs(X509Certificate x509Certificate, Collection<String> collection, Set<X509Certificate> set, PublicKey publicKey, boolean bl) throws CertPathValidatorException {
        this.checkCRLs(x509Certificate, publicKey, null, bl, true, set, this.params.trustAnchors());
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void checkOCSP(X509Certificate object, Collection<String> object2) throws CertPathValidatorException {
        void var1_4;
        block13 : {
            Object object3;
            block12 : {
                object3 = X509CertImpl.toImpl((X509Certificate)object);
                object2 = this.issuerCert != null ? new CertId(this.issuerCert, ((X509CertImpl)object3).getSerialNumberObject()) : new CertId(this.anchor.getCA(), this.anchor.getCAPublicKey(), ((X509CertImpl)object3).getSerialNumberObject());
                object = this.ocspResponses.get(object);
                if (object == null) break block12;
                if (debug != null) {
                    debug.println("Found cached OCSP response");
                }
                object3 = new OCSPResponse((byte[])object);
                object = null;
                try {
                    for (Extension extension : this.ocspExtensions) {
                        if (!extension.getId().equals("1.3.6.1.5.5.7.48.1.2")) continue;
                        object = extension.getValue();
                    }
                    ((OCSPResponse)object3).verify(Collections.singletonList(object2), this.issuerCert, this.responderCert, this.params.date(), (byte[])object);
                    object = object3;
                }
                catch (IOException iOException) {
                    throw new CertPathValidatorException("Unable to determine revocation status due to network error", (Throwable)var1_4, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                }
            }
            object = this.responderURI != null ? this.responderURI : OCSP.getResponderURI((X509CertImpl)object3);
            if (object == null) break block13;
            object = OCSP.check(Collections.singletonList(object2), (URI)object, this.issuerCert, this.responderCert, null, this.ocspExtensions);
            object2 = ((OCSPResponse)object).getSingleResponse((CertId)object2);
            object3 = object2.getCertStatus();
            if (object3 != OCSP.RevocationStatus.CertStatus.REVOKED) {
                if (object3 == OCSP.RevocationStatus.CertStatus.UNKNOWN) throw new CertPathValidatorException("Certificate's revocation status is unknown", null, this.params.certPath(), -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                return;
            }
            object3 = object2.getRevocationTime();
            if (!((Date)object3).before(this.params.date())) {
                return;
            }
            object = new CertificateRevokedException((Date)object3, object2.getRevocationReason(), ((OCSPResponse)object).getSignerCertificate().getSubjectX500Principal(), object2.getSingleExtensions());
            throw new CertPathValidatorException(((Throwable)object).getMessage(), (Throwable)object, null, -1, CertPathValidatorException.BasicReason.REVOKED);
        }
        try {
            object = new CertPathValidatorException("Certificate does not specify OCSP responder", null, null, -1);
            throw object;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw new CertPathValidatorException("Unable to determine revocation status due to network error", (Throwable)var1_4, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
        catch (CertificateException certificateException) {
            throw new CertPathValidatorException(certificateException);
        }
    }

    private static X509Certificate getResponderCert(String object, String string, Set<TrustAnchor> set, List<CertStore> list) throws CertPathValidatorException {
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            X500Principal x500Principal = new X500Principal((String)object);
            x509CertSelector.setIssuer(x500Principal);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CertPathValidatorException("cannot parse ocsp.responderCertIssuerName property", illegalArgumentException);
        }
        try {
            object = new BigInteger(RevocationChecker.stripOutSeparators(string), 16);
            x509CertSelector.setSerialNumber((BigInteger)object);
        }
        catch (NumberFormatException numberFormatException) {
            throw new CertPathValidatorException("cannot parse ocsp.responderCertSerialNumber property", numberFormatException);
        }
        return RevocationChecker.getResponderCert(x509CertSelector, set, list);
    }

    private static X509Certificate getResponderCert(String string, Set<TrustAnchor> set, List<CertStore> list) throws CertPathValidatorException {
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            X500Principal x500Principal = new X500Principal(string);
            x509CertSelector.setSubject(x500Principal);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CertPathValidatorException("cannot parse ocsp.responderCertSubjectName property", illegalArgumentException);
        }
        return RevocationChecker.getResponderCert(x509CertSelector, set, list);
    }

    private static X509Certificate getResponderCert(X509CertSelector x509CertSelector, Set<TrustAnchor> object, List<CertStore> object2) throws CertPathValidatorException {
        Object object3 = object.iterator();
        while (object3.hasNext()) {
            object = object3.next().getTrustedCert();
            if (object == null || !x509CertSelector.match((Certificate)object)) continue;
            return object;
        }
        object = object2.iterator();
        while (object.hasNext()) {
            object2 = (CertStore)object.next();
            try {
                if ((object2 = ((CertStore)object2).getCertificates(x509CertSelector)).isEmpty()) continue;
                object2 = (X509Certificate)object2.iterator().next();
                return object2;
            }
            catch (CertStoreException certStoreException) {
                Debug debug = RevocationChecker.debug;
                if (debug == null) continue;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("CertStore exception:");
                ((StringBuilder)object3).append(certStoreException);
                debug.println(((StringBuilder)object3).toString());
            }
        }
        throw new CertPathValidatorException("Cannot find the responder's certificate (set using the OCSP security properties).");
    }

    private static X509Certificate getResponderCert(RevocationProperties revocationProperties, Set<TrustAnchor> set, List<CertStore> list) throws CertPathValidatorException {
        if (revocationProperties.ocspSubject != null) {
            return RevocationChecker.getResponderCert(revocationProperties.ocspSubject, set, list);
        }
        if (revocationProperties.ocspIssuer != null && revocationProperties.ocspSerial != null) {
            return RevocationChecker.getResponderCert(revocationProperties.ocspIssuer, revocationProperties.ocspSerial, set, list);
        }
        if (revocationProperties.ocspIssuer == null && revocationProperties.ocspSerial == null) {
            return null;
        }
        throw new CertPathValidatorException("Must specify both ocsp.responderCertIssuerName and ocsp.responderCertSerialNumber properties");
    }

    private static RevocationProperties getRevocationProperties() {
        return AccessController.doPrivileged(new PrivilegedAction<RevocationProperties>(){

            @Override
            public RevocationProperties run() {
                RevocationProperties revocationProperties = new RevocationProperties();
                String string = Security.getProperty("com.sun.security.onlyCheckRevocationOfEECert");
                boolean bl = true;
                boolean bl2 = string != null && string.equalsIgnoreCase("true");
                revocationProperties.onlyEE = bl2;
                string = Security.getProperty("ocsp.enable");
                bl2 = string != null && string.equalsIgnoreCase("true") ? bl : false;
                revocationProperties.ocspEnabled = bl2;
                revocationProperties.ocspUrl = Security.getProperty("ocsp.responderURL");
                revocationProperties.ocspSubject = Security.getProperty("ocsp.responderCertSubjectName");
                revocationProperties.ocspIssuer = Security.getProperty("ocsp.responderCertIssuerName");
                revocationProperties.ocspSerial = Security.getProperty("ocsp.responderCertSerialNumber");
                revocationProperties.crlDPEnabled = Boolean.getBoolean("com.sun.security.enableCRLDP");
                return revocationProperties;
            }
        });
    }

    private boolean isSoftFailException(CertPathValidatorException certPathValidatorException) {
        if (this.softFail && certPathValidatorException.getReason() == CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS) {
            certPathValidatorException = new CertPathValidatorException(certPathValidatorException.getMessage(), certPathValidatorException.getCause(), this.params.certPath(), this.certIndex, certPathValidatorException.getReason());
            this.softFailExceptions.addFirst(certPathValidatorException);
            return true;
        }
        return false;
    }

    private static String stripOutSeparators(String arrc) {
        arrc = arrc.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrc.length; ++i) {
            if (HEX_DIGITS.indexOf(arrc[i]) == -1) continue;
            stringBuilder.append(arrc[i]);
        }
        return stringBuilder.toString();
    }

    private static URI toURI(String object) throws CertPathValidatorException {
        if (object != null) {
            try {
                object = new URI((String)object);
                return object;
            }
            catch (URISyntaxException uRISyntaxException) {
                throw new CertPathValidatorException("cannot parse ocsp.responderURL property", uRISyntaxException);
            }
        }
        return null;
    }

    private void updateState(X509Certificate x509Certificate) throws CertPathValidatorException {
        PublicKey publicKey;
        this.issuerCert = x509Certificate;
        PublicKey publicKey2 = publicKey = x509Certificate.getPublicKey();
        if (PKIX.isDSAPublicKeyWithoutParams(publicKey)) {
            publicKey2 = BasicChecker.makeInheritedParamsKey(publicKey, this.prevPubKey);
        }
        this.prevPubKey = publicKey2;
        this.crlSignFlag = RevocationChecker.certCanSignCrl(x509Certificate);
        int n = this.certIndex;
        if (n > 0) {
            this.certIndex = n - 1;
        }
    }

    /*
     * Exception decompiling
     */
    private Collection<X509CRL> verifyPossibleCRLs(Set<X509CRL> var1_1, X509Certificate var2_5, PublicKey var3_6, boolean var4_7, boolean[] var5_8, Set<TrustAnchor> var6_9) throws CertPathValidatorException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[DOLOOP]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void verifyWithSeparateSigningKey(X509Certificate object, PublicKey publicKey, boolean bl, Set<X509Certificate> set) throws CertPathValidatorException {
        Debug debug = RevocationChecker.debug;
        if (debug != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RevocationChecker.verifyWithSeparateSigningKey() ---checking ");
            stringBuilder.append("revocation status");
            stringBuilder.append("...");
            debug.println(stringBuilder.toString());
        }
        if (set != null && set.contains(object)) {
            object = RevocationChecker.debug;
            if (object != null) {
                ((Debug)object).println("RevocationChecker.verifyWithSeparateSigningKey() circular dependency");
            }
            throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
        }
        if (!bl) {
            this.buildToNewKey((X509Certificate)object, null, set);
        } else {
            this.buildToNewKey((X509Certificate)object, publicKey, set);
        }
    }

    @Override
    public void check(Certificate certificate, Collection<String> collection) throws CertPathValidatorException {
        this.check((X509Certificate)certificate, collection, this.prevPubKey, this.crlSignFlag);
    }

    @Override
    public RevocationChecker clone() {
        RevocationChecker revocationChecker = (RevocationChecker)super.clone();
        revocationChecker.softFailExceptions = new LinkedList<CertPathValidatorException>(this.softFailExceptions);
        return revocationChecker;
    }

    @Override
    public List<CertPathValidatorException> getSoftFailExceptions() {
        return Collections.unmodifiableList(this.softFailExceptions);
    }

    @Override
    public Set<String> getSupportedExtensions() {
        return null;
    }

    /*
     * WARNING - void declaration
     */
    void init(TrustAnchor list, PKIX.ValidatorParams object) throws CertPathValidatorException {
        block12 : {
            void var4_10;
            void var4_7;
            RevocationProperties revocationProperties = RevocationChecker.getRevocationProperties();
            URI object22 = this.getOcspResponder();
            if (object22 == null) {
                URI uRI = RevocationChecker.toURI(revocationProperties.ocspUrl);
            }
            this.responderURI = var4_7;
            X509Certificate x509Certificate = this.getOcspResponderCert();
            if (x509Certificate == null) {
                X509Certificate x509Certificate2 = RevocationChecker.getResponderCert(revocationProperties, ((PKIX.ValidatorParams)object).trustAnchors(), ((PKIX.ValidatorParams)object).certStores());
            }
            this.responderCert = var4_10;
            Set<PKIXRevocationChecker.Option> set = this.getOptions();
            for (PKIXRevocationChecker.Option option : set) {
                int n = 2.$SwitchMap$java$security$cert$PKIXRevocationChecker$Option[option.ordinal()];
                if (n == 1 || n == 2 || n == 3 || n == 4) continue;
                list = new StringBuilder();
                ((StringBuilder)((Object)list)).append("Unrecognized revocation parameter option: ");
                ((StringBuilder)((Object)list)).append((Object)option);
                throw new CertPathValidatorException(((StringBuilder)((Object)list)).toString());
            }
            this.softFail = set.contains((Object)PKIXRevocationChecker.Option.SOFT_FAIL);
            if (this.legacy) {
                void var4_15;
                if (revocationProperties.ocspEnabled) {
                    Mode mode = Mode.PREFER_OCSP;
                } else {
                    Mode mode = Mode.ONLY_CRLS;
                }
                this.mode = var4_15;
                this.onlyEE = revocationProperties.onlyEE;
            } else {
                if (set.contains((Object)PKIXRevocationChecker.Option.NO_FALLBACK)) {
                    this.mode = set.contains((Object)PKIXRevocationChecker.Option.PREFER_CRLS) ? Mode.ONLY_CRLS : Mode.ONLY_OCSP;
                } else if (set.contains((Object)PKIXRevocationChecker.Option.PREFER_CRLS)) {
                    this.mode = Mode.PREFER_CRLS;
                }
                this.onlyEE = set.contains((Object)PKIXRevocationChecker.Option.ONLY_END_ENTITY);
            }
            this.crlDP = this.legacy ? revocationProperties.crlDPEnabled : true;
            this.ocspResponses = this.getOcspResponses();
            this.ocspExtensions = this.getOcspExtensions();
            this.anchor = list;
            this.params = object;
            this.certStores = new ArrayList<CertStore>(((PKIX.ValidatorParams)object).certStores());
            try {
                list = this.certStores;
                CollectionCertStoreParameters collectionCertStoreParameters = new CollectionCertStoreParameters(((PKIX.ValidatorParams)object).certificates());
                list.add(CertStore.getInstance("Collection", collectionCertStoreParameters));
            }
            catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException generalSecurityException) {
                object = debug;
                if (object == null) break block12;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RevocationChecker: error creating Collection CertStore: ");
                stringBuilder.append(generalSecurityException);
                ((Debug)object).println(stringBuilder.toString());
            }
        }
    }

    @Override
    public void init(boolean bl) throws CertPathValidatorException {
        if (!bl) {
            Object object = this.anchor;
            if (object != null) {
                this.issuerCert = ((TrustAnchor)object).getTrustedCert();
                object = this.issuerCert;
                object = object != null ? ((Certificate)object).getPublicKey() : this.anchor.getCAPublicKey();
                this.prevPubKey = object;
            }
            this.crlSignFlag = true;
            object = this.params;
            this.certIndex = object != null && ((PKIX.ValidatorParams)object).certPath() != null ? this.params.certPath().getCertificates().size() - 1 : -1;
            this.softFailExceptions.clear();
            return;
        }
        throw new CertPathValidatorException("forward checking not supported");
    }

    @Override
    public boolean isForwardCheckingSupported() {
        return false;
    }

    private static enum Mode {
        PREFER_OCSP,
        PREFER_CRLS,
        ONLY_CRLS,
        ONLY_OCSP;
        
    }

    private static class RejectKeySelector
    extends X509CertSelector {
        private final Set<PublicKey> badKeySet;

        RejectKeySelector(Set<PublicKey> set) {
            this.badKeySet = set;
        }

        @Override
        public boolean match(Certificate certificate) {
            if (!super.match(certificate)) {
                return false;
            }
            if (this.badKeySet.contains(certificate.getPublicKey())) {
                if (debug != null) {
                    debug.println("RejectKeySelector.match: bad key");
                }
                return false;
            }
            if (debug != null) {
                debug.println("RejectKeySelector.match: returning true");
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RejectKeySelector: [\n");
            stringBuilder.append(super.toString());
            stringBuilder.append(this.badKeySet);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class RevocationProperties {
        boolean crlDPEnabled;
        boolean ocspEnabled;
        String ocspIssuer;
        String ocspSerial;
        String ocspSubject;
        String ocspUrl;
        boolean onlyEE;

        private RevocationProperties() {
        }
    }

}

