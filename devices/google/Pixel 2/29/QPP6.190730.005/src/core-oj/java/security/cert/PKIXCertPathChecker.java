/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertPathChecker;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public abstract class PKIXCertPathChecker
implements CertPathChecker,
Cloneable {
    protected PKIXCertPathChecker() {
    }

    @Override
    public void check(Certificate certificate) throws CertPathValidatorException {
        this.check(certificate, Collections.emptySet());
    }

    public abstract void check(Certificate var1, Collection<String> var2) throws CertPathValidatorException;

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public abstract Set<String> getSupportedExtensions();

    @Override
    public abstract void init(boolean var1) throws CertPathValidatorException;

    @Override
    public abstract boolean isForwardCheckingSupported();
}

