/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;

public abstract class CertPathBuilderSpi {
    public abstract CertPathBuilderResult engineBuild(CertPathParameters var1) throws CertPathBuilderException, InvalidAlgorithmParameterException;

    public CertPathChecker engineGetRevocationChecker() {
        throw new UnsupportedOperationException();
    }
}

