/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.config;

import com.android.org.bouncycastle.util.Strings;
import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;

public class ProviderConfigurationPermission
extends BasicPermission {
    private static final int ACCEPTABLE_EC_CURVES = 16;
    private static final String ACCEPTABLE_EC_CURVES_STR = "acceptableeccurves";
    private static final int ADDITIONAL_EC_PARAMETERS = 32;
    private static final String ADDITIONAL_EC_PARAMETERS_STR = "additionalecparameters";
    private static final int ALL = 63;
    private static final String ALL_STR = "all";
    private static final int DH_DEFAULT_PARAMS = 8;
    private static final String DH_DEFAULT_PARAMS_STR = "dhdefaultparams";
    private static final int EC_IMPLICITLY_CA = 2;
    private static final String EC_IMPLICITLY_CA_STR = "ecimplicitlyca";
    private static final int THREAD_LOCAL_DH_DEFAULT_PARAMS = 4;
    private static final String THREAD_LOCAL_DH_DEFAULT_PARAMS_STR = "threadlocaldhdefaultparams";
    private static final int THREAD_LOCAL_EC_IMPLICITLY_CA = 1;
    private static final String THREAD_LOCAL_EC_IMPLICITLY_CA_STR = "threadlocalecimplicitlyca";
    private final String actions;
    private final int permissionMask;

    public ProviderConfigurationPermission(String string) {
        super(string);
        this.actions = ALL_STR;
        this.permissionMask = 63;
    }

    public ProviderConfigurationPermission(String string, String string2) {
        super(string, string2);
        this.actions = string2;
        this.permissionMask = this.calculateMask(string2);
    }

    private int calculateMask(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(Strings.toLowerCase(string), " ,");
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            int n2;
            string = stringTokenizer.nextToken();
            if (string.equals(THREAD_LOCAL_EC_IMPLICITLY_CA_STR)) {
                n2 = n | 1;
            } else if (string.equals(EC_IMPLICITLY_CA_STR)) {
                n2 = n | 2;
            } else if (string.equals(THREAD_LOCAL_DH_DEFAULT_PARAMS_STR)) {
                n2 = n | 4;
            } else if (string.equals(DH_DEFAULT_PARAMS_STR)) {
                n2 = n | 8;
            } else if (string.equals(ACCEPTABLE_EC_CURVES_STR)) {
                n2 = n | 16;
            } else if (string.equals(ADDITIONAL_EC_PARAMETERS_STR)) {
                n2 = n | 32;
            } else {
                n2 = n;
                if (string.equals(ALL_STR)) {
                    n2 = n | 63;
                }
            }
            n = n2;
        }
        if (n != 0) {
            return n;
        }
        throw new IllegalArgumentException("unknown permissions passed to mask");
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof ProviderConfigurationPermission) {
            object = (ProviderConfigurationPermission)object;
            if (this.permissionMask != ((ProviderConfigurationPermission)object).permissionMask || !this.getName().equals(((Permission)object).getName())) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public String getActions() {
        return this.actions;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.permissionMask;
    }

    @Override
    public boolean implies(Permission permission) {
        boolean bl = permission instanceof ProviderConfigurationPermission;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (!this.getName().equals(permission.getName())) {
            return false;
        }
        permission = (ProviderConfigurationPermission)permission;
        int n = this.permissionMask;
        int n2 = ((ProviderConfigurationPermission)permission).permissionMask;
        if ((n & n2) == n2) {
            bl2 = true;
        }
        return bl2;
    }
}

