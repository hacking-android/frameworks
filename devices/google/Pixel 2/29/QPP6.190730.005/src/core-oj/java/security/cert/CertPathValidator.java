/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import sun.security.jca.GetInstance;

public class CertPathValidator {
    private static final String CPV_TYPE = "certpathvalidator.type";
    private final String algorithm;
    private final Provider provider;
    private final CertPathValidatorSpi validatorSpi;

    protected CertPathValidator(CertPathValidatorSpi certPathValidatorSpi, Provider provider, String string) {
        this.validatorSpi = certPathValidatorSpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static final String getDefaultType() {
        String string;
        block0 : {
            string = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return Security.getProperty(CertPathValidator.CPV_TYPE);
                }
            });
            if (string != null) break block0;
            string = "PKIX";
        }
        return string;
    }

    public static CertPathValidator getInstance(String string) throws NoSuchAlgorithmException {
        GetInstance.Instance instance = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, string);
        return new CertPathValidator((CertPathValidatorSpi)instance.impl, instance.provider, string);
    }

    public static CertPathValidator getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        object = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, string, (String)object);
        return new CertPathValidator((CertPathValidatorSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static CertPathValidator getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        object = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, string, (Provider)object);
        return new CertPathValidator((CertPathValidatorSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final CertPathChecker getRevocationChecker() {
        return this.validatorSpi.engineGetRevocationChecker();
    }

    public final CertPathValidatorResult validate(CertPath certPath, CertPathParameters certPathParameters) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        return this.validatorSpi.engineValidate(certPath, certPathParameters);
    }

}

