/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.Serializable;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.X509CertImpl;

class ConstraintsChecker
extends PKIXCertPathChecker {
    private static final Debug debug = Debug.getInstance("certpath");
    private final int certPathLength;
    private int i;
    private int maxPathLength;
    private NameConstraintsExtension prevNC;
    private Set<String> supportedExts;

    ConstraintsChecker(int n) {
        this.certPathLength = n;
    }

    private void checkBasicConstraints(X509Certificate object) throws CertPathValidatorException {
        Object object2;
        Debug debug = ConstraintsChecker.debug;
        if (debug != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("---checking ");
            ((StringBuilder)object2).append("basic constraints");
            ((StringBuilder)object2).append("...");
            debug.println(((StringBuilder)object2).toString());
            debug = ConstraintsChecker.debug;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("i = ");
            ((StringBuilder)object2).append(this.i);
            ((StringBuilder)object2).append(", maxPathLength = ");
            ((StringBuilder)object2).append(this.maxPathLength);
            debug.println(((StringBuilder)object2).toString());
        }
        if (this.i < this.certPathLength) {
            int n;
            int n2 = -1;
            if (((X509Certificate)object).getVersion() < 3) {
                n = n2;
                if (this.i == 1) {
                    n = n2;
                    if (X509CertImpl.isSelfIssued((X509Certificate)object)) {
                        n = Integer.MAX_VALUE;
                    }
                }
            } else {
                n = ((X509Certificate)object).getBasicConstraints();
            }
            if (n != -1) {
                if (!X509CertImpl.isSelfIssued((X509Certificate)object)) {
                    n2 = this.maxPathLength;
                    if (n2 > 0) {
                        this.maxPathLength = n2 - 1;
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("basic constraints");
                        ((StringBuilder)object).append(" check failed: pathLenConstraint violated - this cert must be the last cert in the certification path");
                        throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, PKIXReason.PATH_TOO_LONG);
                    }
                }
                if (n < this.maxPathLength) {
                    this.maxPathLength = n;
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("basic constraints");
                ((StringBuilder)object).append(" check failed: this is not a CA certificate");
                throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, PKIXReason.NOT_CA_CERT);
            }
        }
        if ((object = ConstraintsChecker.debug) != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("after processing, maxPathLength = ");
            ((StringBuilder)object2).append(this.maxPathLength);
            ((Debug)object).println(((StringBuilder)object2).toString());
            object2 = ConstraintsChecker.debug;
            object = new StringBuilder();
            ((StringBuilder)object).append("basic constraints");
            ((StringBuilder)object).append(" verified.");
            ((Debug)object2).println(((StringBuilder)object).toString());
        }
    }

    static int mergeBasicConstraints(X509Certificate x509Certificate, int n) {
        int n2 = x509Certificate.getBasicConstraints();
        int n3 = n;
        if (!X509CertImpl.isSelfIssued(x509Certificate)) {
            n3 = n - 1;
        }
        n = n3;
        if (n2 < n3) {
            n = n2;
        }
        return n;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static NameConstraintsExtension mergeNameConstraints(X509Certificate object, NameConstraintsExtension object2) throws CertPathValidatorException {
        Object object3;
        block6 : {
            StringBuilder stringBuilder;
            try {
                object = X509CertImpl.toImpl((X509Certificate)object);
                object = ((X509CertImpl)object).getNameConstraintsExtension();
                object3 = debug;
                if (object3 == null) break block6;
                stringBuilder = new StringBuilder();
                stringBuilder.append("prevNC = ");
            }
            catch (CertificateException certificateException) {
                throw new CertPathValidatorException(certificateException);
            }
            stringBuilder.append(object2);
            stringBuilder.append(", newNC = ");
            stringBuilder.append(String.valueOf(object));
            ((Debug)object3).println(stringBuilder.toString());
        }
        if (object2 == null) {
            object2 = debug;
            if (object2 != null) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("mergedNC = ");
                ((StringBuilder)object3).append(String.valueOf(object));
                ((Debug)object2).println(((StringBuilder)object3).toString());
            }
            if (object != null) return (NameConstraintsExtension)((NameConstraintsExtension)object).clone();
            return object;
        }
        ((NameConstraintsExtension)object2).merge((NameConstraintsExtension)object);
        object3 = debug;
        if (object3 == null) return object2;
        object = new StringBuilder();
        ((StringBuilder)object).append("mergedNC = ");
        catch (IOException iOException) {
            throw new CertPathValidatorException(iOException);
        }
        ((StringBuilder)object).append(object2);
        ((Debug)object3).println(((StringBuilder)object).toString());
        return object2;
    }

    private void verifyNameConstraints(X509Certificate serializable) throws CertPathValidatorException {
        Object object;
        Object object2 = debug;
        if (object2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("---checking ");
            ((StringBuilder)object).append("name constraints");
            ((StringBuilder)object).append("...");
            ((Debug)object2).println(((StringBuilder)object).toString());
        }
        if (!(this.prevNC == null || this.i != this.certPathLength && X509CertImpl.isSelfIssued((X509Certificate)serializable))) {
            object = debug;
            if (object != null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("prevNC = ");
                ((StringBuilder)object2).append(this.prevNC);
                ((StringBuilder)object2).append(", currDN = ");
                ((StringBuilder)object2).append(((X509Certificate)serializable).getSubjectX500Principal());
                ((Debug)object).println(((StringBuilder)object2).toString());
            }
            try {
                if (!this.prevNC.verify((X509Certificate)serializable)) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("name constraints");
                    ((StringBuilder)serializable).append(" check failed");
                    object2 = new CertPathValidatorException(((StringBuilder)serializable).toString(), null, null, -1, PKIXReason.INVALID_NAME);
                    throw object2;
                }
            }
            catch (IOException iOException) {
                throw new CertPathValidatorException(iOException);
            }
        }
        this.prevNC = ConstraintsChecker.mergeNameConstraints((X509Certificate)serializable, this.prevNC);
        object2 = debug;
        if (object2 != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("name constraints");
            ((StringBuilder)serializable).append(" verified.");
            ((Debug)object2).println(((StringBuilder)serializable).toString());
        }
    }

    @Override
    public void check(Certificate certificate, Collection<String> collection) throws CertPathValidatorException {
        certificate = (X509Certificate)certificate;
        ++this.i;
        this.checkBasicConstraints((X509Certificate)certificate);
        this.verifyNameConstraints((X509Certificate)certificate);
        if (collection != null && !collection.isEmpty()) {
            collection.remove(PKIXExtensions.BasicConstraints_Id.toString());
            collection.remove(PKIXExtensions.NameConstraints_Id.toString());
        }
    }

    @Override
    public Set<String> getSupportedExtensions() {
        if (this.supportedExts == null) {
            this.supportedExts = new HashSet<String>(2);
            this.supportedExts.add(PKIXExtensions.BasicConstraints_Id.toString());
            this.supportedExts.add(PKIXExtensions.NameConstraints_Id.toString());
            this.supportedExts = Collections.unmodifiableSet(this.supportedExts);
        }
        return this.supportedExts;
    }

    @Override
    public void init(boolean bl) throws CertPathValidatorException {
        if (!bl) {
            this.i = 0;
            this.maxPathLength = this.certPathLength;
            this.prevNC = null;
            return;
        }
        throw new CertPathValidatorException("forward checking not supported");
    }

    @Override
    public boolean isForwardCheckingSupported() {
        return false;
    }
}

