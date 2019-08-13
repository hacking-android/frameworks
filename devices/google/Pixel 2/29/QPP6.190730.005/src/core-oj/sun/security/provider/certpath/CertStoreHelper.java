/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.net.URI;
import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
import sun.security.util.Cache;

public abstract class CertStoreHelper {
    private static final int NUM_TYPES = 2;
    private static Cache<String, CertStoreHelper> cache;
    private static final Map<String, String> classMap;

    static {
        classMap = new HashMap<String, String>(2);
        classMap.put("LDAP", "sun.security.provider.certpath.ldap.LDAPCertStoreHelper");
        classMap.put("SSLServer", "sun.security.provider.certpath.ssl.SSLServerCertStoreHelper");
        cache = Cache.newSoftMemoryCache(2);
    }

    public static CertStoreHelper getInstance(String string) throws NoSuchAlgorithmException {
        Object object = cache.get(string);
        if (object != null) {
            return object;
        }
        CharSequence charSequence = classMap.get(string);
        if (charSequence != null) {
            try {
                object = new PrivilegedExceptionAction<CertStoreHelper>((String)charSequence, string){
                    final /* synthetic */ String val$cl;
                    final /* synthetic */ String val$type;
                    {
                        this.val$cl = string;
                        this.val$type = string2;
                    }

                    @Override
                    public CertStoreHelper run() throws ClassNotFoundException {
                        try {
                            Object object = Class.forName(this.val$cl, true, null);
                            object = (CertStoreHelper)((Class)object).newInstance();
                            cache.put(this.val$type, object);
                            return object;
                        }
                        catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
                            throw new AssertionError(reflectiveOperationException);
                        }
                    }
                };
                object = (CertStoreHelper)AccessController.doPrivileged(object);
                return object;
            }
            catch (PrivilegedActionException privilegedActionException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" not available");
                throw new NoSuchAlgorithmException(((StringBuilder)charSequence).toString(), privilegedActionException.getException());
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" not available");
        throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean isCausedByNetworkIssue(String object, CertStoreException certStoreException) {
        int n = ((String)object).hashCode();
        boolean bl = true;
        if (n != 84300) {
            if (n != 2331559) {
                if (n != 133315663) return false;
                if (!((String)object).equals("SSLServer")) return false;
                n = 1;
            } else {
                if (!((String)object).equals("LDAP")) return false;
                n = 0;
            }
        } else {
            if (!((String)object).equals("URI")) return false;
            n = 2;
        }
        if (n != 0 && n != 1) {
            if (n != 2) {
                return false;
            }
            object = certStoreException.getCause();
            if (object == null) return false;
            if (!(object instanceof IOException)) return false;
            return bl;
        }
        try {
            return CertStoreHelper.getInstance((String)object).isCausedByNetworkIssue(certStoreException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return false;
        }
    }

    public abstract CertStore getCertStore(URI var1) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;

    public abstract boolean isCausedByNetworkIssue(CertStoreException var1);

    public abstract X509CRLSelector wrap(X509CRLSelector var1, Collection<X500Principal> var2, String var3) throws IOException;

    public abstract X509CertSelector wrap(X509CertSelector var1, X500Principal var2, String var3) throws IOException;

}

