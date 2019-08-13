/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.security.Provider;
import java.util.Enumeration;

public abstract class Policy {
    public static final PermissionCollection UNSUPPORTED_EMPTY_COLLECTION = new UnsupportedEmptyCollection();

    public static Policy getInstance(String string, Parameters parameters) throws NoSuchAlgorithmException {
        return null;
    }

    public static Policy getInstance(String string, Parameters parameters, String string2) throws NoSuchProviderException, NoSuchAlgorithmException {
        return null;
    }

    public static Policy getInstance(String string, Parameters parameters, Provider provider) throws NoSuchAlgorithmException {
        return null;
    }

    public static Policy getPolicy() {
        return null;
    }

    public static void setPolicy(Policy policy) {
    }

    public Parameters getParameters() {
        return null;
    }

    public PermissionCollection getPermissions(CodeSource codeSource) {
        return null;
    }

    public PermissionCollection getPermissions(ProtectionDomain protectionDomain) {
        return null;
    }

    public Provider getProvider() {
        return null;
    }

    public String getType() {
        return null;
    }

    public boolean implies(ProtectionDomain protectionDomain, Permission permission) {
        return true;
    }

    public void refresh() {
    }

    public static interface Parameters {
    }

    private static class UnsupportedEmptyCollection
    extends PermissionCollection {
        @Override
        public void add(Permission permission) {
        }

        @Override
        public Enumeration<Permission> elements() {
            return null;
        }

        @Override
        public boolean implies(Permission permission) {
            return true;
        }
    }

}

