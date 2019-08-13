/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.Date;
import java.util.Set;
import sun.security.provider.certpath.CertPathHelper;
import sun.security.x509.GeneralNameInterface;

class CertPathHelperImpl
extends CertPathHelper {
    private CertPathHelperImpl() {
    }

    static void initialize() {
        synchronized (CertPathHelperImpl.class) {
            if (CertPathHelper.instance == null) {
                CertPathHelperImpl certPathHelperImpl = new CertPathHelperImpl();
                CertPathHelper.instance = certPathHelperImpl;
            }
            return;
        }
    }

    @Override
    protected void implSetDateAndTime(X509CRLSelector x509CRLSelector, Date date, long l) {
        x509CRLSelector.setDateAndTime(date, l);
    }

    @Override
    protected void implSetPathToNames(X509CertSelector x509CertSelector, Set<GeneralNameInterface> set) {
        x509CertSelector.setPathToNamesInternal(set);
    }
}

