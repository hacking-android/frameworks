/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;

public abstract class CertPathValidatorSpi {
    public CertPathChecker engineGetRevocationChecker() {
        throw new UnsupportedOperationException();
    }

    public abstract CertPathValidatorResult engineValidate(CertPath var1, CertPathParameters var2) throws CertPathValidatorException, InvalidAlgorithmParameterException;
}

