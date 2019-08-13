/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.PKIXExtensions;

class KeyChecker
extends PKIXCertPathChecker {
    private static final int KEY_CERT_SIGN = 5;
    private static final Debug debug = Debug.getInstance("certpath");
    private final int certPathLen;
    private int remainingCerts;
    private Set<String> supportedExts;
    private final CertSelector targetConstraints;

    KeyChecker(int n, CertSelector certSelector) {
        this.certPathLen = n;
        this.targetConstraints = certSelector;
    }

    static void verifyCAKeyUsage(X509Certificate object) throws CertPathValidatorException {
        Debug debug = KeyChecker.debug;
        if (debug != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("KeyChecker.verifyCAKeyUsage() ---checking ");
            stringBuilder.append("CA key usage");
            stringBuilder.append("...");
            debug.println(stringBuilder.toString());
        }
        if ((object = ((X509Certificate)object).getKeyUsage()) == null) {
            return;
        }
        if (object[5]) {
            debug = KeyChecker.debug;
            if (debug != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("KeyChecker.verifyCAKeyUsage() ");
                ((StringBuilder)object).append("CA key usage");
                ((StringBuilder)object).append(" verified.");
                debug.println(((StringBuilder)object).toString());
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("CA key usage");
        ((StringBuilder)object).append(" check failed: keyCertSign bit is not set");
        throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, PKIXReason.INVALID_KEY_USAGE);
    }

    @Override
    public void check(Certificate object, Collection<String> collection) throws CertPathValidatorException {
        X509Certificate x509Certificate = (X509Certificate)object;
        --this.remainingCerts;
        if (this.remainingCerts == 0) {
            object = this.targetConstraints;
            if (object != null && !object.match(x509Certificate)) {
                throw new CertPathValidatorException("target certificate constraints check failed");
            }
        } else {
            KeyChecker.verifyCAKeyUsage(x509Certificate);
        }
        if (collection != null && !collection.isEmpty()) {
            collection.remove(PKIXExtensions.KeyUsage_Id.toString());
            collection.remove(PKIXExtensions.ExtendedKeyUsage_Id.toString());
            collection.remove(PKIXExtensions.SubjectAlternativeName_Id.toString());
        }
    }

    @Override
    public Set<String> getSupportedExtensions() {
        if (this.supportedExts == null) {
            this.supportedExts = new HashSet<String>(3);
            this.supportedExts.add(PKIXExtensions.KeyUsage_Id.toString());
            this.supportedExts.add(PKIXExtensions.ExtendedKeyUsage_Id.toString());
            this.supportedExts.add(PKIXExtensions.SubjectAlternativeName_Id.toString());
            this.supportedExts = Collections.unmodifiableSet(this.supportedExts);
        }
        return this.supportedExts;
    }

    @Override
    public void init(boolean bl) throws CertPathValidatorException {
        if (!bl) {
            this.remainingCerts = this.certPathLen;
            return;
        }
        throw new CertPathValidatorException("forward checking not supported");
    }

    @Override
    public boolean isForwardCheckingSupported() {
        return false;
    }
}

