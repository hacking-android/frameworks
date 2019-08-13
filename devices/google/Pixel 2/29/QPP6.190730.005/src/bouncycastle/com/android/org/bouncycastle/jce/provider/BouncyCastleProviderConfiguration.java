/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission;
import com.android.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import java.math.BigInteger;
import java.security.Permission;
import java.security.spec.DSAParameterSpec;
import java.security.spec.ECParameterSpec;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;

class BouncyCastleProviderConfiguration
implements ProviderConfiguration {
    private static Permission BC_ADDITIONAL_EC_CURVE_PERMISSION;
    private static Permission BC_DH_LOCAL_PERMISSION;
    private static Permission BC_DH_PERMISSION;
    private static Permission BC_EC_CURVE_PERMISSION;
    private static Permission BC_EC_LOCAL_PERMISSION;
    private static Permission BC_EC_PERMISSION;
    private volatile Set acceptableNamedCurves = new HashSet();
    private volatile Map additionalECParameters = new HashMap();
    private volatile Object dhDefaultParams;
    private ThreadLocal dhThreadSpec = new ThreadLocal();
    private volatile com.android.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitCaParams;
    private ThreadLocal ecThreadSpec = new ThreadLocal();

    static {
        BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission("BC", "threadLocalEcImplicitlyCa");
        BC_EC_PERMISSION = new ProviderConfigurationPermission("BC", "ecImplicitlyCa");
        BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission("BC", "threadLocalDhDefaultParams");
        BC_DH_PERMISSION = new ProviderConfigurationPermission("BC", "DhDefaultParams");
        BC_EC_CURVE_PERMISSION = new ProviderConfigurationPermission("BC", "acceptableEcCurves");
        BC_ADDITIONAL_EC_CURVE_PERMISSION = new ProviderConfigurationPermission("BC", "additionalEcParameters");
    }

    BouncyCastleProviderConfiguration() {
    }

    @Override
    public Set getAcceptableNamedCurves() {
        return Collections.unmodifiableSet(this.acceptableNamedCurves);
    }

    @Override
    public Map getAdditionalECParameters() {
        return Collections.unmodifiableMap(this.additionalECParameters);
    }

    @Override
    public DHParameterSpec getDHDefaultParameters(int n) {
        Object t = this.dhThreadSpec.get();
        Object object = t;
        if (t == null) {
            object = this.dhDefaultParams;
        }
        if (object instanceof DHParameterSpec) {
            if (((DHParameterSpec)(object = (DHParameterSpec)object)).getP().bitLength() == n) {
                return object;
            }
        } else if (object instanceof DHParameterSpec[]) {
            object = (DHParameterSpec[])object;
            for (int i = 0; i != ((Object)object).length; ++i) {
                if (((DHParameterSpec)object[i]).getP().bitLength() != n) continue;
                return object[i];
            }
        }
        if ((object = (DHParameters)CryptoServicesRegistrar.getSizedProperty(CryptoServicesRegistrar.Property.DH_DEFAULT_PARAMS, n)) != null) {
            return new DHDomainParameterSpec((DHParameters)object);
        }
        return null;
    }

    @Override
    public DSAParameterSpec getDSADefaultParameters(int n) {
        DSAParameters dSAParameters = (DSAParameters)CryptoServicesRegistrar.getSizedProperty(CryptoServicesRegistrar.Property.DSA_DEFAULT_PARAMS, n);
        if (dSAParameters != null) {
            return new DSAParameterSpec(dSAParameters.getP(), dSAParameters.getQ(), dSAParameters.getG());
        }
        return null;
    }

    @Override
    public com.android.org.bouncycastle.jce.spec.ECParameterSpec getEcImplicitlyCa() {
        com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec = (com.android.org.bouncycastle.jce.spec.ECParameterSpec)this.ecThreadSpec.get();
        if (eCParameterSpec != null) {
            return eCParameterSpec;
        }
        return this.ecImplicitCaParams;
    }

    void setParameter(String object, Object object2) {
        SecurityManager securityManager = System.getSecurityManager();
        if (((String)object).equals("threadLocalEcImplicitlyCa")) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_LOCAL_PERMISSION);
            }
            object = !(object2 instanceof com.android.org.bouncycastle.jce.spec.ECParameterSpec) && object2 != null ? EC5Util.convertSpec((ECParameterSpec)object2, false) : (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object2;
            if (object == null) {
                this.ecThreadSpec.remove();
            } else {
                this.ecThreadSpec.set(object);
            }
        } else if (((String)object).equals("ecImplicitlyCa")) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_PERMISSION);
            }
            this.ecImplicitCaParams = !(object2 instanceof com.android.org.bouncycastle.jce.spec.ECParameterSpec) && object2 != null ? EC5Util.convertSpec((ECParameterSpec)object2, false) : (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object2;
        } else if (((String)object).equals("threadLocalDhDefaultParams")) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_LOCAL_PERMISSION);
            }
            if (!(object2 instanceof DHParameterSpec) && !(object2 instanceof DHParameterSpec[]) && object2 != null) {
                throw new IllegalArgumentException("not a valid DHParameterSpec");
            }
            if (object2 == null) {
                this.dhThreadSpec.remove();
            } else {
                this.dhThreadSpec.set(object2);
            }
        } else if (((String)object).equals("DhDefaultParams")) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_PERMISSION);
            }
            if (!(object2 instanceof DHParameterSpec) && !(object2 instanceof DHParameterSpec[]) && object2 != null) {
                throw new IllegalArgumentException("not a valid DHParameterSpec or DHParameterSpec[]");
            }
            this.dhDefaultParams = object2;
        } else if (((String)object).equals("acceptableEcCurves")) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_CURVE_PERMISSION);
            }
            this.acceptableNamedCurves = (Set)object2;
        } else if (((String)object).equals("additionalEcParameters")) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_ADDITIONAL_EC_CURVE_PERMISSION);
            }
            this.additionalECParameters = (Map)object2;
        }
    }
}

