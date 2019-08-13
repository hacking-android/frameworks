/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Principal;
import java.security.ProtectionDomain;
import java.util.HashMap;
import sun.security.util.Debug;

public class SecureClassLoader
extends ClassLoader {
    private static final Debug debug = Debug.getInstance("scl");
    private final boolean initialized;
    private final HashMap<CodeSource, ProtectionDomain> pdcache = new HashMap(11);

    static {
        ClassLoader.registerAsParallelCapable();
    }

    protected SecureClassLoader() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.initialized = true;
    }

    protected SecureClassLoader(ClassLoader object) {
        super((ClassLoader)object);
        object = System.getSecurityManager();
        if (object != null) {
            ((SecurityManager)object).checkCreateClassLoader();
        }
        this.initialized = true;
    }

    private void check() {
        if (this.initialized) {
            return;
        }
        throw new SecurityException("ClassLoader object not initialized");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ProtectionDomain getProtectionDomain(CodeSource serializable) {
        if (serializable == null) {
            return null;
        }
        HashMap<CodeSource, ProtectionDomain> hashMap = this.pdcache;
        synchronized (hashMap) {
            ProtectionDomain protectionDomain = this.pdcache.get(serializable);
            Object object = protectionDomain;
            if (protectionDomain != null) return object;
            object = this.getPermissions((CodeSource)serializable);
            protectionDomain = new ProtectionDomain((CodeSource)serializable, (PermissionCollection)object, this, null);
            this.pdcache.put((CodeSource)serializable, protectionDomain);
            object = protectionDomain;
            if (debug == null) return object;
            object = debug;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(" getPermissions ");
            ((StringBuilder)serializable).append(protectionDomain);
            ((Debug)object).println(((StringBuilder)serializable).toString());
            debug.println("");
            return protectionDomain;
        }
    }

    protected final Class<?> defineClass(String string, ByteBuffer byteBuffer, CodeSource codeSource) {
        return this.defineClass(string, byteBuffer, this.getProtectionDomain(codeSource));
    }

    protected final Class<?> defineClass(String string, byte[] arrby, int n, int n2, CodeSource codeSource) {
        return this.defineClass(string, arrby, n, n2, this.getProtectionDomain(codeSource));
    }

    protected PermissionCollection getPermissions(CodeSource codeSource) {
        this.check();
        return new Permissions();
    }
}

