/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AccessController;
import java.security.Identity;
import java.security.KeyManagementException;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PublicKey;
import java.security.Security;
import java.util.Enumeration;

@Deprecated
public abstract class IdentityScope
extends Identity {
    private static IdentityScope scope;
    private static final long serialVersionUID = -2337346281189773310L;

    protected IdentityScope() {
        this("restoring...");
    }

    public IdentityScope(String string) {
        super(string);
    }

    public IdentityScope(String string, IdentityScope identityScope) throws KeyManagementException {
        super(string, identityScope);
    }

    private static void check(String string) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(string);
        }
    }

    public static IdentityScope getSystemScope() {
        if (scope == null) {
            IdentityScope.initializeSystemScope();
        }
        return scope;
    }

    private static void initializeSystemScope() {
        String string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return Security.getProperty("system.scope");
            }
        });
        if (string == null) {
            return;
        }
        try {
            scope = (IdentityScope)Class.forName(string).newInstance();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected static void setSystemScope(IdentityScope identityScope) {
        IdentityScope.check("setSystemScope");
        scope = identityScope;
    }

    public abstract void addIdentity(Identity var1) throws KeyManagementException;

    public abstract Identity getIdentity(String var1);

    public Identity getIdentity(Principal principal) {
        return this.getIdentity(principal.getName());
    }

    public abstract Identity getIdentity(PublicKey var1);

    public abstract Enumeration<Identity> identities();

    public abstract void removeIdentity(Identity var1) throws KeyManagementException;

    public abstract int size();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[");
        stringBuilder.append(this.size());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}

