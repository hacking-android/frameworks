/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AccessController;
import java.security.Identity;
import java.security.IdentityScope;
import java.security.InvalidParameterException;
import java.security.KeyException;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.PublicKey;

@Deprecated
public abstract class Signer
extends Identity {
    private static final long serialVersionUID = -1763464102261361480L;
    private PrivateKey privateKey;

    protected Signer() {
    }

    public Signer(String string) {
        super(string);
    }

    public Signer(String string, IdentityScope identityScope) throws KeyManagementException {
        super(string, identityScope);
    }

    private static void check(String string) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(string);
        }
    }

    public PrivateKey getPrivateKey() {
        Signer.check("getSignerPrivateKey");
        return this.privateKey;
    }

    @Override
    String printKeys() {
        String string = this.getPublicKey() != null && this.privateKey != null ? "\tpublic and private keys initialized" : "\tno keys";
        return string;
    }

    public final void setKeyPair(KeyPair privilegedExceptionAction) throws InvalidParameterException, KeyException {
        Signer.check("setSignerKeyPair");
        final PublicKey publicKey = ((KeyPair)((Object)privilegedExceptionAction)).getPublic();
        PrivateKey privateKey = ((KeyPair)((Object)privilegedExceptionAction)).getPrivate();
        if (publicKey != null && privateKey != null) {
            try {
                privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                    @Override
                    public Void run() throws KeyManagementException {
                        Signer.this.setPublicKey(publicKey);
                        return null;
                    }
                };
                AccessController.doPrivileged(privilegedExceptionAction);
                this.privateKey = privateKey;
                return;
            }
            catch (PrivilegedActionException privilegedActionException) {
                throw (KeyManagementException)privilegedActionException.getException();
            }
        }
        throw new InvalidParameterException();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Signer]");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }

}

