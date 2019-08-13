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
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import sun.security.jca.GetInstance;

public class CertPathBuilder {
    private static final String CPB_TYPE = "certpathbuilder.type";
    private final String algorithm;
    private final CertPathBuilderSpi builderSpi;
    private final Provider provider;

    protected CertPathBuilder(CertPathBuilderSpi certPathBuilderSpi, Provider provider, String string) {
        this.builderSpi = certPathBuilderSpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static final String getDefaultType() {
        String string;
        block0 : {
            string = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return Security.getProperty(CertPathBuilder.CPB_TYPE);
                }
            });
            if (string != null) break block0;
            string = "PKIX";
        }
        return string;
    }

    public static CertPathBuilder getInstance(String string) throws NoSuchAlgorithmException {
        GetInstance.Instance instance = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, string);
        return new CertPathBuilder((CertPathBuilderSpi)instance.impl, instance.provider, string);
    }

    public static CertPathBuilder getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        object = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, string, (String)object);
        return new CertPathBuilder((CertPathBuilderSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static CertPathBuilder getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        object = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, string, (Provider)object);
        return new CertPathBuilder((CertPathBuilderSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public final CertPathBuilderResult build(CertPathParameters certPathParameters) throws CertPathBuilderException, InvalidAlgorithmParameterException {
        return this.builderSpi.engineBuild(certPathParameters);
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final CertPathChecker getRevocationChecker() {
        return this.builderSpi.engineGetRevocationChecker();
    }

}

