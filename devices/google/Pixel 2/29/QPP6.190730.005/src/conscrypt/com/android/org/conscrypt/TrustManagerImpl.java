/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.CertBlacklist;
import com.android.org.conscrypt.CertPinManager;
import com.android.org.conscrypt.CertificatePriorityComparator;
import com.android.org.conscrypt.ChainStrengthAnalyzer;
import com.android.org.conscrypt.ConscryptCertStore;
import com.android.org.conscrypt.ConscryptHostnameVerifier;
import com.android.org.conscrypt.ConscryptSession;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.TrustedCertificateIndex;
import com.android.org.conscrypt.ct.CTLogStore;
import com.android.org.conscrypt.ct.CTPolicy;
import com.android.org.conscrypt.ct.CTVerificationResult;
import com.android.org.conscrypt.ct.CTVerifier;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.security.auth.x500.X500Principal;

public final class TrustManagerImpl
extends X509ExtendedTrustManager {
    private static final TrustAnchorComparator TRUST_ANCHOR_COMPARATOR;
    private static ConscryptHostnameVerifier defaultHostnameVerifier;
    private static final Logger logger;
    private final X509Certificate[] acceptedIssuers;
    private final CertBlacklist blacklist;
    private boolean ctEnabledOverride;
    private CTPolicy ctPolicy;
    private CTVerifier ctVerifier;
    private final Exception err;
    private final CertificateFactory factory;
    private ConscryptHostnameVerifier hostnameVerifier;
    private final TrustedCertificateIndex intermediateIndex;
    private CertPinManager pinManager;
    private final KeyStore rootKeyStore;
    private final TrustedCertificateIndex trustedCertificateIndex;
    private final ConscryptCertStore trustedCertificateStore;
    private final CertPathValidator validator;

    static {
        logger = Logger.getLogger(TrustManagerImpl.class.getName());
        TRUST_ANCHOR_COMPARATOR = new TrustAnchorComparator();
    }

    @UnsupportedAppUsage
    public TrustManagerImpl(KeyStore keyStore) {
        this(keyStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager) {
        this(keyStore, certPinManager, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager, ConscryptCertStore conscryptCertStore) {
        this(keyStore, certPinManager, conscryptCertStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager, ConscryptCertStore conscryptCertStore, CertBlacklist certBlacklist) {
        this(keyStore, certPinManager, conscryptCertStore, certBlacklist, null, null, null);
    }

    public TrustManagerImpl(KeyStore arrx509Certificate, CertPinManager certPinManager, ConscryptCertStore arrx509Certificate2, CertBlacklist certBlacklist, CTLogStore cTLogStore, CTVerifier arrx509Certificate3, CTPolicy cTPolicy) {
        X509Certificate[] arrx509Certificate4;
        X509Certificate[] arrx509Certificate5;
        CertificateFactory certificateFactory;
        Object var14_15;
        X509Certificate[] arrx509Certificate6;
        CertificateFactory certificateFactory2;
        X509Certificate[] arrx509Certificate7;
        block15 : {
            Object object;
            X509Certificate[] arrx509Certificate8;
            Object var12_13;
            Object var10_11;
            block13 : {
                block14 : {
                    arrx509Certificate7 = null;
                    certificateFactory2 = null;
                    var10_11 = null;
                    arrx509Certificate8 = null;
                    var12_13 = null;
                    object = null;
                    var14_15 = null;
                    certificateFactory = certificateFactory2;
                    arrx509Certificate3 = var10_11;
                    arrx509Certificate5 = arrx509Certificate8;
                    arrx509Certificate6 = object;
                    arrx509Certificate7 = arrx509Certificate4 = CertPathValidator.getInstance("PKIX");
                    certificateFactory = certificateFactory2;
                    arrx509Certificate3 = var10_11;
                    arrx509Certificate5 = arrx509Certificate8;
                    arrx509Certificate6 = object;
                    certificateFactory2 = CertificateFactory.getInstance("X509");
                    arrx509Certificate7 = arrx509Certificate4;
                    certificateFactory = certificateFactory2;
                    arrx509Certificate3 = var10_11;
                    arrx509Certificate5 = arrx509Certificate8;
                    arrx509Certificate6 = object;
                    if (!"AndroidCAStore".equals(arrx509Certificate.getType())) break block13;
                    arrx509Certificate7 = arrx509Certificate4;
                    certificateFactory = certificateFactory2;
                    arrx509Certificate3 = var10_11;
                    arrx509Certificate5 = arrx509Certificate8;
                    arrx509Certificate6 = object;
                    if (!Platform.supportsConscryptCertStore()) break block13;
                    if (arrx509Certificate2 != null) break block14;
                    arrx509Certificate7 = arrx509Certificate4;
                    certificateFactory = certificateFactory2;
                    arrx509Certificate3 = arrx509Certificate;
                    arrx509Certificate5 = arrx509Certificate8;
                    arrx509Certificate6 = object;
                    arrx509Certificate2 = Platform.newDefaultCertStore();
                }
                var10_11 = null;
                arrx509Certificate8 = null;
                arrx509Certificate7 = arrx509Certificate4;
                certificateFactory = certificateFactory2;
                arrx509Certificate3 = arrx509Certificate;
                arrx509Certificate5 = arrx509Certificate2;
                arrx509Certificate6 = var10_11;
                arrx509Certificate7 = arrx509Certificate4;
                certificateFactory = certificateFactory2;
                arrx509Certificate3 = arrx509Certificate;
                arrx509Certificate5 = arrx509Certificate2;
                arrx509Certificate6 = var10_11;
                arrx509Certificate3 = object;
                arrx509Certificate5 = arrx509Certificate;
                arrx509Certificate = arrx509Certificate8;
                break block15;
            }
            var10_11 = null;
            arrx509Certificate8 = null;
            arrx509Certificate7 = arrx509Certificate4;
            certificateFactory = certificateFactory2;
            arrx509Certificate3 = var10_11;
            arrx509Certificate5 = arrx509Certificate2;
            arrx509Certificate6 = object;
            arrx509Certificate = TrustManagerImpl.acceptedIssuers((KeyStore)arrx509Certificate);
            arrx509Certificate7 = arrx509Certificate4;
            certificateFactory = certificateFactory2;
            arrx509Certificate3 = var10_11;
            arrx509Certificate5 = arrx509Certificate2;
            arrx509Certificate6 = arrx509Certificate;
            try {
                arrx509Certificate3 = object = new TrustedCertificateIndex(TrustManagerImpl.trustAnchors(arrx509Certificate));
                arrx509Certificate5 = arrx509Certificate8;
            }
            catch (Exception exception) {
                arrx509Certificate4 = arrx509Certificate6;
                arrx509Certificate2 = var12_13;
                arrx509Certificate6 = arrx509Certificate3;
            }
        }
        arrx509Certificate7 = arrx509Certificate4;
        certificateFactory = certificateFactory2;
        arrx509Certificate6 = arrx509Certificate5;
        arrx509Certificate5 = arrx509Certificate2;
        arrx509Certificate2 = arrx509Certificate3;
        arrx509Certificate4 = arrx509Certificate;
        arrx509Certificate = var14_15;
        if (certBlacklist == null) {
            certBlacklist = Platform.newDefaultBlacklist();
        }
        if (cTLogStore == null) {
            cTLogStore = Platform.newDefaultLogStore();
        }
        if (cTPolicy == null) {
            cTPolicy = Platform.newDefaultPolicy(cTLogStore);
        }
        this.pinManager = certPinManager;
        this.rootKeyStore = arrx509Certificate6;
        this.trustedCertificateStore = arrx509Certificate5;
        this.validator = arrx509Certificate7;
        this.factory = certificateFactory;
        this.trustedCertificateIndex = arrx509Certificate2;
        this.intermediateIndex = new TrustedCertificateIndex();
        this.acceptedIssuers = arrx509Certificate4;
        this.err = arrx509Certificate;
        this.blacklist = certBlacklist;
        this.ctVerifier = new CTVerifier(cTLogStore);
        this.ctPolicy = cTPolicy;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static X509Certificate[] acceptedIssuers(KeyStore arrx509Certificate) {
        try {
            ArrayList<X509Certificate> arrayList = new ArrayList<X509Certificate>();
            Enumeration<String> enumeration = arrx509Certificate.aliases();
            do {
                if (!enumeration.hasMoreElements()) {
                    return arrayList.toArray(new X509Certificate[arrayList.size()]);
                }
                X509Certificate x509Certificate = (X509Certificate)arrx509Certificate.getCertificate(enumeration.nextElement());
                if (x509Certificate == null) continue;
                arrayList.add(x509Certificate);
            } while (true);
        }
        catch (KeyStoreException keyStoreException) {
            return new X509Certificate[0];
        }
    }

    private void checkBlacklist(X509Certificate x509Certificate) throws CertificateException {
        Object object = this.blacklist;
        if (object != null && object.isPublicKeyBlackListed(x509Certificate.getPublicKey())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Certificate blacklisted by public key: ");
            ((StringBuilder)object).append(x509Certificate);
            throw new CertificateException(((StringBuilder)object).toString());
        }
    }

    private void checkCT(String string, List<X509Certificate> list, byte[] object, byte[] arrby) throws CertificateException {
        if (this.ctPolicy.doesResultConformToPolicy((CTVerificationResult)(object = this.ctVerifier.verifySignedCertificateTimestamps(list, arrby, (byte[])object)), string, list.toArray(new X509Certificate[list.size()]))) {
            return;
        }
        throw new CertificateException("Certificate chain does not conform to required transparency policy.");
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] arrx509Certificate, String string, SSLSession sSLSession, SSLParameters sSLParameters, boolean bl) throws CertificateException {
        byte[] arrby = null;
        byte[] arrby2 = null;
        String string2 = null;
        if (sSLSession != null) {
            string2 = sSLSession.getPeerHost();
            arrby = this.getOcspDataFromSession(sSLSession);
            arrby2 = this.getTlsSctDataFromSession(sSLSession);
        }
        if (sSLSession != null && sSLParameters != null && "HTTPS".equalsIgnoreCase(sSLParameters.getEndpointIdentificationAlgorithm()) && !this.getHttpsVerifier().verify(string2, sSLSession)) {
            throw new CertificateException("No subjectAltNames on the certificate match");
        }
        return this.checkTrusted(arrx509Certificate, arrby, arrby2, string, string2, bl);
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] arrx509Certificate, byte[] arrby, byte[] arrby2, String serializable, String string, boolean bl) throws CertificateException {
        if (arrx509Certificate != null && arrx509Certificate.length != 0 && serializable != null && ((String)((Object)serializable)).length() != 0) {
            serializable = this.err;
            if (serializable == null) {
                HashSet<X509Certificate> hashSet = new HashSet<X509Certificate>();
                ArrayList<X509Certificate> arrayList = new ArrayList<X509Certificate>();
                serializable = new ArrayList<TrustAnchor>();
                X509Certificate x509Certificate = arrx509Certificate[0];
                TrustAnchor trustAnchor = this.findTrustAnchorBySubjectAndPublicKey(x509Certificate);
                if (trustAnchor != null) {
                    serializable.add(trustAnchor);
                    hashSet.add(trustAnchor.getTrustedCert());
                } else {
                    arrayList.add(x509Certificate);
                }
                hashSet.add(x509Certificate);
                return this.checkTrustedRecursive(arrx509Certificate, arrby, arrby2, string, bl, arrayList, (ArrayList<TrustAnchor>)serializable, (Set<X509Certificate>)hashSet);
            }
            throw new CertificateException((Throwable)serializable);
        }
        throw new IllegalArgumentException("null or zero-length parameter");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private List<X509Certificate> checkTrustedRecursive(X509Certificate[] arrx509Certificate, byte[] arrby, byte[] arrby2, String string, boolean bl, ArrayList<X509Certificate> arrayList, ArrayList<TrustAnchor> arrayList2, Set<X509Certificate> set) throws CertificateException {
        Serializable serializable;
        Object object = arrayList2.isEmpty() ? arrayList.get(arrayList.size() - 1) : arrayList2.get(arrayList2.size() - 1).getTrustedCert();
        this.checkBlacklist((X509Certificate)object);
        if (((X509Certificate)object).getIssuerDN().equals(((X509Certificate)object).getSubjectDN())) {
            return this.verifyChain(arrayList, arrayList2, string, bl, arrby, arrby2);
        }
        Object object2 = TrustManagerImpl.sortPotentialAnchors(this.findAllTrustAnchorsByIssuerAndSignature((X509Certificate)object)).iterator();
        List<X509Certificate> list = null;
        int n = 0;
        while (object2.hasNext()) {
            TrustAnchor trustAnchor = object2.next();
            serializable = trustAnchor.getTrustedCert();
            if (set.contains(serializable)) continue;
            n = 1;
            set.add((X509Certificate)serializable);
            arrayList2.add(trustAnchor);
            try {
                return this.checkTrustedRecursive(arrx509Certificate, arrby, arrby2, string, bl, arrayList, arrayList2, set);
            }
            catch (CertificateException certificateException) {
                arrayList2.remove(arrayList2.size() - 1);
                set.remove(serializable);
            }
        }
        if (!arrayList2.isEmpty()) {
            if (n != 0) throw list;
            return this.verifyChain(arrayList, arrayList2, string, bl, arrby, arrby2);
        }
        for (n = 1; n < arrx509Certificate.length; ++n) {
            object2 = arrx509Certificate[n];
            if (set.contains(object2) || !((X509Certificate)object).getIssuerDN().equals(((X509Certificate)object2).getSubjectDN())) continue;
            ((X509Certificate)object2).checkValidity();
            ChainStrengthAnalyzer.checkCert((X509Certificate)object2);
            set.add((X509Certificate)object2);
            arrayList.add((X509Certificate)object2);
            try {
                return this.checkTrustedRecursive(arrx509Certificate, arrby, arrby2, string, bl, arrayList, arrayList2, set);
            }
            catch (CertificateException certificateException) {
                set.remove(object2);
                arrayList.remove(arrayList.size() - 1);
                continue;
            }
            catch (CertificateException certificateException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Unacceptable certificate: ");
                ((StringBuilder)serializable).append(((X509Certificate)object2).getSubjectX500Principal());
                list = new CertificateException(((StringBuilder)serializable).toString(), certificateException);
            }
        }
        object = TrustManagerImpl.sortPotentialAnchors(this.intermediateIndex.findAllByIssuerAndSignature((X509Certificate)object)).iterator();
        do {
            if (!object.hasNext()) {
                if (list == null) throw new CertificateException(new CertPathValidatorException("Trust anchor for certification path not found.", null, this.factory.generateCertPath(arrayList), -1));
                throw list;
            }
            object2 = ((TrustAnchor)object.next()).getTrustedCert();
            if (set.contains(object2)) continue;
            set.add((X509Certificate)object2);
            arrayList.add((X509Certificate)object2);
            try {
                return this.checkTrustedRecursive(arrx509Certificate, arrby, arrby2, string, bl, arrayList, arrayList2, set);
            }
            catch (CertificateException certificateException) {
                arrayList.remove(arrayList.size() - 1);
                set.remove(object2);
                continue;
            }
            break;
        } while (true);
    }

    private Set<TrustAnchor> findAllTrustAnchorsByIssuerAndSignature(X509Certificate serializable) {
        Object object;
        Set<TrustAnchor> set = this.trustedCertificateIndex.findAllByIssuerAndSignature((X509Certificate)serializable);
        if (set.isEmpty() && (object = this.trustedCertificateStore) != null) {
            if ((object = object.findAllIssuers((X509Certificate)serializable)).isEmpty()) {
                return set;
            }
            serializable = new HashSet(object.size());
            set = object.iterator();
            while (set.hasNext()) {
                object = (X509Certificate)set.next();
                serializable.add(this.trustedCertificateIndex.index((X509Certificate)object));
            }
            return serializable;
        }
        return set;
    }

    private TrustAnchor findTrustAnchorBySubjectAndPublicKey(X509Certificate x509Certificate) {
        Object object = this.trustedCertificateIndex.findBySubjectAndPublicKey(x509Certificate);
        if (object != null) {
            return object;
        }
        object = this.trustedCertificateStore;
        if (object == null) {
            return null;
        }
        if ((x509Certificate = object.getTrustAnchor(x509Certificate)) != null) {
            return new TrustAnchor(x509Certificate, null);
        }
        return null;
    }

    static ConscryptHostnameVerifier getDefaultHostnameVerifier() {
        synchronized (TrustManagerImpl.class) {
            ConscryptHostnameVerifier conscryptHostnameVerifier = defaultHostnameVerifier;
            return conscryptHostnameVerifier;
        }
    }

    private static SSLSession getHandshakeSessionOrThrow(SSLSocket object) throws CertificateException {
        if ((object = ((SSLSocket)object).getHandshakeSession()) != null) {
            return object;
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    private ConscryptHostnameVerifier getHttpsVerifier() {
        ConscryptHostnameVerifier conscryptHostnameVerifier = this.hostnameVerifier;
        if (conscryptHostnameVerifier != null) {
            return conscryptHostnameVerifier;
        }
        conscryptHostnameVerifier = TrustManagerImpl.getDefaultHostnameVerifier();
        if (conscryptHostnameVerifier != null) {
            return conscryptHostnameVerifier;
        }
        return GlobalHostnameVerifierAdapter.INSTANCE;
    }

    private byte[] getOcspDataFromSession(SSLSession object) {
        Object var2_7 = null;
        Object var3_8 = null;
        if (object instanceof ConscryptSession) {
            object = ((ConscryptSession)object).getStatusResponses();
        } else {
            Object object2 = object.getClass().getDeclaredMethod("getStatusResponses", new Class[0]);
            ((AccessibleObject)object2).setAccessible(true);
            object2 = ((Method)object2).invoke(object, new Object[0]);
            object = var3_8;
            try {
                if (object2 instanceof List) {
                    object = (List)object2;
                }
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException(invocationTargetException.getCause());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                object = var2_7;
            }
            catch (IllegalAccessException illegalAccessException) {
                object = var2_7;
            }
            catch (SecurityException securityException) {
                object = var2_7;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                object = var2_7;
            }
        }
        if (object != null && !object.isEmpty()) {
            return (byte[])object.get(0);
        }
        return null;
    }

    private byte[] getTlsSctDataFromSession(SSLSession arrby) {
        block8 : {
            if (arrby instanceof ConscryptSession) {
                return ((ConscryptSession)arrby).getPeerSignedCertificateTimestamp();
            }
            Object var2_7 = null;
            Object object = arrby.getClass().getDeclaredMethod("getPeerSignedCertificateTimestamp", new Class[0]);
            ((AccessibleObject)object).setAccessible(true);
            object = ((Method)object).invoke(arrby, new Object[0]);
            arrby = var2_7;
            try {
                if (!(object instanceof byte[])) break block8;
                arrby = (byte[])object;
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException(invocationTargetException.getCause());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                arrby = var2_7;
            }
            catch (IllegalAccessException illegalAccessException) {
                arrby = var2_7;
            }
            catch (SecurityException securityException) {
                arrby = var2_7;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                arrby = var2_7;
            }
        }
        return arrby;
    }

    static void setDefaultHostnameVerifier(ConscryptHostnameVerifier conscryptHostnameVerifier) {
        synchronized (TrustManagerImpl.class) {
            defaultHostnameVerifier = conscryptHostnameVerifier;
            return;
        }
    }

    private void setOcspResponses(PKIXParameters pKIXParameters, X509Certificate x509Certificate, byte[] arrby) {
        PKIXCertPathChecker pKIXCertPathChecker;
        PKIXCertPathChecker pKIXCertPathChecker2;
        ArrayList<PKIXCertPathChecker> arrayList;
        block5 : {
            if (arrby == null) {
                return;
            }
            pKIXCertPathChecker2 = null;
            arrayList = new ArrayList<PKIXCertPathChecker>(pKIXParameters.getCertPathCheckers());
            Iterator iterator = arrayList.iterator();
            do {
                pKIXCertPathChecker = pKIXCertPathChecker2;
                if (!iterator.hasNext()) break block5;
            } while (!((pKIXCertPathChecker = (PKIXCertPathChecker)iterator.next()) instanceof PKIXRevocationChecker));
            pKIXCertPathChecker = (PKIXRevocationChecker)pKIXCertPathChecker;
        }
        pKIXCertPathChecker2 = pKIXCertPathChecker;
        if (pKIXCertPathChecker == null) {
            try {
                pKIXCertPathChecker2 = (PKIXRevocationChecker)this.validator.getRevocationChecker();
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                return;
            }
            arrayList.add(pKIXCertPathChecker2);
            ((PKIXRevocationChecker)pKIXCertPathChecker2).setOptions(Collections.singleton(PKIXRevocationChecker.Option.ONLY_END_ENTITY));
        }
        ((PKIXRevocationChecker)pKIXCertPathChecker2).setOcspResponses(Collections.singletonMap(x509Certificate, arrby));
        pKIXParameters.setCertPathCheckers(arrayList);
    }

    private static Collection<TrustAnchor> sortPotentialAnchors(Set<TrustAnchor> collection) {
        if (collection.size() <= 1) {
            return collection;
        }
        collection = new ArrayList<TrustAnchor>(collection);
        Collections.sort(collection, TRUST_ANCHOR_COMPARATOR);
        return collection;
    }

    private static Set<TrustAnchor> trustAnchors(X509Certificate[] arrx509Certificate) {
        HashSet<TrustAnchor> hashSet = new HashSet<TrustAnchor>(arrx509Certificate.length);
        int n = arrx509Certificate.length;
        for (int i = 0; i < n; ++i) {
            hashSet.add(new TrustAnchor(arrx509Certificate[i], null));
        }
        return hashSet;
    }

    /*
     * Exception decompiling
     */
    private List<X509Certificate> verifyChain(List<X509Certificate> var1_1, List<TrustAnchor> var2_4, String var3_6, boolean var4_7, byte[] var5_8, byte[] var6_9) throws CertificateException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[CATCHBLOCK]
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

    public List<X509Certificate> checkClientTrusted(X509Certificate[] arrx509Certificate, String string, String string2) throws CertificateException {
        return this.checkTrusted(arrx509Certificate, null, null, string, string2, true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string) throws CertificateException {
        this.checkTrusted(arrx509Certificate, string, null, null, true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string, Socket socket) throws CertificateException {
        SSLSession sSLSession = null;
        SSLParameters sSLParameters = null;
        if (socket instanceof SSLSocket) {
            socket = (SSLSocket)socket;
            sSLSession = TrustManagerImpl.getHandshakeSessionOrThrow((SSLSocket)socket);
            sSLParameters = ((SSLSocket)socket).getSSLParameters();
        }
        this.checkTrusted(arrx509Certificate, string, sSLSession, sSLParameters, true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string, SSLEngine sSLEngine) throws CertificateException {
        SSLSession sSLSession = sSLEngine.getHandshakeSession();
        if (sSLSession != null) {
            this.checkTrusted(arrx509Certificate, string, sSLSession, sSLEngine.getSSLParameters(), true);
            return;
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    @UnsupportedAppUsage
    public List<X509Certificate> checkServerTrusted(X509Certificate[] arrx509Certificate, String string, String string2) throws CertificateException {
        return this.checkTrusted(arrx509Certificate, null, null, string, string2, false);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] arrx509Certificate, String string, SSLSession sSLSession) throws CertificateException {
        return this.checkTrusted(arrx509Certificate, string, sSLSession, null, false);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string) throws CertificateException {
        this.checkTrusted(arrx509Certificate, string, null, null, false);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string, Socket socket) throws CertificateException {
        this.getTrustedChainForServer(arrx509Certificate, string, socket);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string, SSLEngine sSLEngine) throws CertificateException {
        this.getTrustedChainForServer(arrx509Certificate, string, sSLEngine);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        X509Certificate[] arrx509Certificate = this.acceptedIssuers;
        arrx509Certificate = arrx509Certificate != null ? (X509Certificate[])arrx509Certificate.clone() : TrustManagerImpl.acceptedIssuers(this.rootKeyStore);
        return arrx509Certificate;
    }

    ConscryptHostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] arrx509Certificate, String string, Socket socket) throws CertificateException {
        SSLSession sSLSession = null;
        SSLParameters sSLParameters = null;
        if (socket instanceof SSLSocket) {
            socket = (SSLSocket)socket;
            sSLSession = TrustManagerImpl.getHandshakeSessionOrThrow((SSLSocket)socket);
            sSLParameters = ((SSLSocket)socket).getSSLParameters();
        }
        return this.checkTrusted(arrx509Certificate, string, sSLSession, sSLParameters, false);
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] arrx509Certificate, String string, SSLEngine sSLEngine) throws CertificateException {
        SSLSession sSLSession = sSLEngine.getHandshakeSession();
        if (sSLSession != null) {
            return this.checkTrusted(arrx509Certificate, string, sSLSession, sSLEngine.getSSLParameters(), false);
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    public void handleTrustStorageUpdate() {
        X509Certificate[] arrx509Certificate = this.acceptedIssuers;
        if (arrx509Certificate == null) {
            this.trustedCertificateIndex.reset();
        } else {
            this.trustedCertificateIndex.reset(TrustManagerImpl.trustAnchors(arrx509Certificate));
        }
    }

    public void setCTEnabledOverride(boolean bl) {
        this.ctEnabledOverride = bl;
    }

    public void setCTPolicy(CTPolicy cTPolicy) {
        this.ctPolicy = cTPolicy;
    }

    public void setCTVerifier(CTVerifier cTVerifier) {
        this.ctVerifier = cTVerifier;
    }

    void setHostnameVerifier(ConscryptHostnameVerifier conscryptHostnameVerifier) {
        this.hostnameVerifier = conscryptHostnameVerifier;
    }

    private static class ExtendedKeyUsagePKIXCertPathChecker
    extends PKIXCertPathChecker {
        private static final String EKU_OID = "2.5.29.37";
        private static final String EKU_anyExtendedKeyUsage = "2.5.29.37.0";
        private static final String EKU_clientAuth = "1.3.6.1.5.5.7.3.2";
        private static final String EKU_msSGC = "1.3.6.1.4.1.311.10.3.3";
        private static final String EKU_nsSGC = "2.16.840.1.113730.4.1";
        private static final String EKU_serverAuth = "1.3.6.1.5.5.7.3.1";
        private static final Set<String> SUPPORTED_EXTENSIONS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("2.5.29.37")));
        private final boolean clientAuth;
        private final X509Certificate leaf;

        private ExtendedKeyUsagePKIXCertPathChecker(boolean bl, X509Certificate x509Certificate) {
            this.clientAuth = bl;
            this.leaf = x509Certificate;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void check(Certificate var1_1, Collection<String> var2_3) throws CertPathValidatorException {
            block8 : {
                var3_4 = this.leaf;
                if (var1_1 != var3_4) {
                    return;
                }
                try {
                    var1_1 = var3_4.getExtendedKeyUsage();
                    if (var1_1 == null) {
                        return;
                    }
                    var4_5 = false;
                    var3_4 = var1_1.iterator();
                }
                catch (CertificateParsingException var1_2) {
                    throw new CertPathValidatorException(var1_2);
                }
                do lbl-1000: // 3 sources:
                {
                    block9 : {
                        var5_6 = var4_5;
                        if (!var3_4.hasNext()) break block8;
                        var1_1 = (String)var3_4.next();
                        if (var1_1.equals("2.5.29.37.0")) {
                            var5_6 = true;
                            break block8;
                        }
                        if (!this.clientAuth) break block9;
                        if (!var1_1.equals("1.3.6.1.5.5.7.3.2")) ** GOTO lbl-1000
                        var5_6 = true;
                        break block8;
                    }
                    if (var1_1.equals("1.3.6.1.5.5.7.3.1")) {
                        var5_6 = true;
                    } else {
                        if (!var1_1.equals("2.16.840.1.113730.4.1")) continue;
                        var5_6 = true;
                    }
                    break block8;
                } while (!var1_1.equals("1.3.6.1.4.1.311.10.3.3"));
                var5_6 = true;
            }
            if (var5_6 == false) throw new CertPathValidatorException("End-entity certificate does not have a valid extendedKeyUsage.");
            var2_3.remove("2.5.29.37");
        }

        @Override
        public Set<String> getSupportedExtensions() {
            return SUPPORTED_EXTENSIONS;
        }

        @Override
        public void init(boolean bl) throws CertPathValidatorException {
        }

        @Override
        public boolean isForwardCheckingSupported() {
            return true;
        }
    }

    private static enum GlobalHostnameVerifierAdapter implements ConscryptHostnameVerifier
    {
        INSTANCE;
        

        @Override
        public boolean verify(String string, SSLSession sSLSession) {
            return HttpsURLConnection.getDefaultHostnameVerifier().verify(string, sSLSession);
        }
    }

    private static class TrustAnchorComparator
    implements Comparator<TrustAnchor> {
        private static final CertificatePriorityComparator CERT_COMPARATOR = new CertificatePriorityComparator();

        private TrustAnchorComparator() {
        }

        @Override
        public int compare(TrustAnchor object, TrustAnchor object2) {
            object = ((TrustAnchor)object).getTrustedCert();
            object2 = ((TrustAnchor)object2).getTrustedCert();
            return CERT_COMPARATOR.compare((X509Certificate)object, (X509Certificate)object2);
        }
    }

}

