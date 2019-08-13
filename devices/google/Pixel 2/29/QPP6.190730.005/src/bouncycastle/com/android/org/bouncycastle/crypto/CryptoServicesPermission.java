/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import java.security.Permission;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CryptoServicesPermission
extends Permission {
    public static final String DEFAULT_RANDOM = "defaultRandomConfig";
    public static final String GLOBAL_CONFIG = "globalConfig";
    public static final String THREAD_LOCAL_CONFIG = "threadLocalConfig";
    private final Set<String> actions = new HashSet<String>();

    public CryptoServicesPermission(String string) {
        super(string);
        this.actions.add(string);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof CryptoServicesPermission) {
            object = (CryptoServicesPermission)object;
            if (this.actions.equals(((CryptoServicesPermission)object).actions)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getActions() {
        return this.actions.toString();
    }

    @Override
    public int hashCode() {
        return this.actions.hashCode();
    }

    @Override
    public boolean implies(Permission permission) {
        if (permission instanceof CryptoServicesPermission) {
            permission = (CryptoServicesPermission)permission;
            if (this.getName().equals(permission.getName())) {
                return true;
            }
            if (this.actions.containsAll(((CryptoServicesPermission)permission).actions)) {
                return true;
            }
        }
        return false;
    }
}

