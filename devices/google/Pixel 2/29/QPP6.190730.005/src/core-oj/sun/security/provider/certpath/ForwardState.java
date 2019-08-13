/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.PKIX;
import sun.security.provider.certpath.State;
import sun.security.util.Debug;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;

class ForwardState
implements State {
    private static final Debug debug = Debug.getInstance("certpath");
    X509CertImpl cert;
    ArrayList<PKIXCertPathChecker> forwardCheckers;
    private boolean init = true;
    X500Principal issuerDN;
    boolean keyParamsNeededFlag = false;
    HashSet<GeneralNameInterface> subjectNamesTraversed;
    int traversedCACerts;

    ForwardState() {
    }

    @Override
    public Object clone() {
        try {
            ForwardState forwardState = (ForwardState)super.clone();
            forwardState.forwardCheckers = (ArrayList)this.forwardCheckers.clone();
            ListIterator<PKIXCertPathChecker> listIterator = forwardState.forwardCheckers.listIterator();
            while (listIterator.hasNext()) {
                PKIXCertPathChecker pKIXCertPathChecker = listIterator.next();
                if (!(pKIXCertPathChecker instanceof Cloneable)) continue;
                listIterator.set((PKIXCertPathChecker)pKIXCertPathChecker.clone());
            }
            forwardState.subjectNamesTraversed = (HashSet)this.subjectNamesTraversed.clone();
            return forwardState;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public void initState(List<PKIXCertPathChecker> object) throws CertPathValidatorException {
        this.subjectNamesTraversed = new HashSet();
        this.traversedCACerts = 0;
        this.forwardCheckers = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            PKIXCertPathChecker pKIXCertPathChecker = (PKIXCertPathChecker)object.next();
            if (!pKIXCertPathChecker.isForwardCheckingSupported()) continue;
            pKIXCertPathChecker.init(true);
            this.forwardCheckers.add(pKIXCertPathChecker);
        }
        this.init = true;
    }

    @Override
    public boolean isInitial() {
        return this.init;
    }

    @Override
    public boolean keyParamsNeeded() {
        return this.keyParamsNeededFlag;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("State [");
        stringBuilder.append("\n  issuerDN of last cert: ");
        stringBuilder.append(this.issuerDN);
        stringBuilder.append("\n  traversedCACerts: ");
        stringBuilder.append(this.traversedCACerts);
        stringBuilder.append("\n  init: ");
        stringBuilder.append(String.valueOf(this.init));
        stringBuilder.append("\n  keyParamsNeeded: ");
        stringBuilder.append(String.valueOf(this.keyParamsNeededFlag));
        stringBuilder.append("\n  subjectNamesTraversed: \n");
        stringBuilder.append(this.subjectNamesTraversed);
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void updateState(X509Certificate object3) throws CertificateException, IOException, CertPathValidatorException {
        Object object;
        block7 : {
            if (object3 == null) {
                return;
            }
            object = X509CertImpl.toImpl((X509Certificate)object3);
            if (PKIX.isDSAPublicKeyWithoutParams(((X509CertImpl)object).getPublicKey())) {
                this.keyParamsNeededFlag = true;
            }
            this.cert = object;
            this.issuerDN = ((X509Certificate)object3).getIssuerX500Principal();
            if (!X509CertImpl.isSelfIssued((X509Certificate)object3) && !this.init && ((X509Certificate)object3).getBasicConstraints() != -1) {
                ++this.traversedCACerts;
            }
            if (this.init || !X509CertImpl.isSelfIssued((X509Certificate)object3)) {
                X500Principal iOException = ((X509Certificate)object3).getSubjectX500Principal();
                this.subjectNamesTraversed.add(X500Name.asX500Name(iOException));
                SubjectAlternativeNameExtension subjectAlternativeNameExtension = ((X509CertImpl)object).getSubjectAlternativeNameExtension();
                if (subjectAlternativeNameExtension == null) break block7;
                for (GeneralName generalName : ((GeneralNames)subjectAlternativeNameExtension.get("subject_name")).names()) {
                    this.subjectNamesTraversed.add(generalName.getName());
                }
            }
        }
        this.init = false;
        return;
        catch (IOException iOException) {
            object = debug;
            if (object == null) throw new CertPathValidatorException(iOException);
            ((Debug)object).println("ForwardState.updateState() unexpected exception");
            iOException.printStackTrace();
            throw new CertPathValidatorException(iOException);
        }
    }
}

