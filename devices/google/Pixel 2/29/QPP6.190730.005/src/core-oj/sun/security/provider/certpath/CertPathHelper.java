/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.Date;
import java.util.Set;
import sun.security.x509.GeneralNameInterface;

public abstract class CertPathHelper {
    protected static CertPathHelper instance;

    protected CertPathHelper() {
    }

    public static void setDateAndTime(X509CRLSelector x509CRLSelector, Date date, long l) {
        instance.implSetDateAndTime(x509CRLSelector, date, l);
    }

    static void setPathToNames(X509CertSelector x509CertSelector, Set<GeneralNameInterface> set) {
        instance.implSetPathToNames(x509CertSelector, set);
    }

    protected abstract void implSetDateAndTime(X509CRLSelector var1, Date var2, long var3);

    protected abstract void implSetPathToNames(X509CertSelector var1, Set<GeneralNameInterface> var2);
}

