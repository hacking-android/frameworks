/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import javax.security.auth.x500.X500Principal;
import sun.security.action.GetIntegerAction;
import sun.security.provider.certpath.CertStoreHelper;
import sun.security.provider.certpath.PKIX;
import sun.security.util.Cache;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AccessDescription;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.URIName;

class URICertStore
extends CertStoreSpi {
    private static final int CACHE_SIZE = 185;
    private static final int CHECK_INTERVAL = 30000;
    private static final int CRL_CONNECT_TIMEOUT;
    private static final int DEFAULT_CRL_CONNECT_TIMEOUT = 15000;
    private static final Cache<URICertStoreParameters, CertStore> certStoreCache;
    private static final Debug debug;
    private Collection<X509Certificate> certs = Collections.emptySet();
    private X509CRL crl;
    private final CertificateFactory factory;
    private long lastChecked;
    private long lastModified;
    private boolean ldap = false;
    private CertStore ldapCertStore;
    private CertStoreHelper ldapHelper;
    private String ldapPath;
    private URI uri;

    private static /* synthetic */ /* end resource */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        debug = Debug.getInstance("certpath");
        CRL_CONNECT_TIMEOUT = URICertStore.initializeTimeout();
        certStoreCache = Cache.newSoftMemoryCache(185);
    }

    URICertStore(CertStoreParameters certStoreParameters) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        super(certStoreParameters);
        if (certStoreParameters instanceof URICertStoreParameters) {
            this.uri = ((URICertStoreParameters)certStoreParameters).uri;
            if (this.uri.getScheme().toLowerCase(Locale.ENGLISH).equals("ldap")) {
                this.ldap = true;
                this.ldapHelper = CertStoreHelper.getInstance("LDAP");
                this.ldapCertStore = this.ldapHelper.getCertStore(this.uri);
                this.ldapPath = this.uri.getPath();
                if (this.ldapPath.charAt(0) == '/') {
                    this.ldapPath = this.ldapPath.substring(1);
                }
            }
            try {
                this.factory = CertificateFactory.getInstance("X.509");
                return;
            }
            catch (CertificateException certificateException) {
                throw new RuntimeException();
            }
        }
        throw new InvalidAlgorithmParameterException("params must be instanceof URICertStoreParameters");
    }

    static CertStore getInstance(URICertStoreParameters object) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        synchronized (URICertStore.class) {
            block7 : {
                Object object2;
                block6 : {
                    Object object3;
                    if (debug != null) {
                        object2 = debug;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("CertStore URI:");
                        ((StringBuilder)object3).append(((URICertStoreParameters)object).uri);
                        ((Debug)object2).println(((StringBuilder)object3).toString());
                    }
                    if ((object2 = certStoreCache.get(object)) != null) break block6;
                    object3 = new URICertStore((CertStoreParameters)object);
                    object2 = new UCS((CertStoreSpi)object3, null, "URI", (CertStoreParameters)object);
                    certStoreCache.put((URICertStoreParameters)object, (CertStore)object2);
                    object = object2;
                    break block7;
                }
                object = object2;
                if (debug == null) break block7;
                debug.println("URICertStore.getInstance: cache hit");
                object = object2;
            }
            return object;
        }
    }

    static CertStore getInstance(AccessDescription object) {
        if (!((AccessDescription)object).getAccessMethod().equals((Object)AccessDescription.Ad_CAISSUERS_Id)) {
            return null;
        }
        if (!((object = ((AccessDescription)object).getAccessLocation().getName()) instanceof URIName)) {
            return null;
        }
        URI uRI = ((URIName)object).getURI();
        try {
            object = new URICertStoreParameters(uRI);
            object = URICertStore.getInstance((URICertStoreParameters)object);
            return object;
        }
        catch (Exception exception) {
            Debug debug = URICertStore.debug;
            if (debug != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("exception creating CertStore: ");
                ((StringBuilder)object).append(exception);
                debug.println(((StringBuilder)object).toString());
                exception.printStackTrace();
            }
            return null;
        }
    }

    private static Collection<X509CRL> getMatchingCRLs(X509CRL x509CRL, CRLSelector cRLSelector) {
        if (!(cRLSelector == null || x509CRL != null && cRLSelector.match(x509CRL))) {
            return Collections.emptyList();
        }
        return Collections.singletonList(x509CRL);
    }

    private static Collection<X509Certificate> getMatchingCerts(Collection<X509Certificate> object, CertSelector certSelector) {
        if (certSelector == null) {
            return object;
        }
        ArrayList<X509Certificate> arrayList = new ArrayList<X509Certificate>(object.size());
        Iterator<X509Certificate> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (!certSelector.match((Certificate)object)) continue;
            arrayList.add((X509Certificate)object);
        }
        return arrayList;
    }

    private static int initializeTimeout() {
        Integer n = AccessController.doPrivileged(new GetIntegerAction("com.sun.security.crl.timeout"));
        if (n != null && n >= 0) {
            return n * 1000;
        }
        return 15000;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Collection<X509CRL> engineGetCRLs(CRLSelector collection) throws CertStoreException {
        block35 : {
            block34 : {
                block31 : {
                    block32 : {
                        block33 : {
                            // MONITORENTER : this
                            if (this.ldap) {
                                collection = (X509CRLSelector)collection;
                                try {
                                    collection = this.ldapHelper.wrap((X509CRLSelector)collection, null, this.ldapPath);
                                }
                                catch (IOException iOException) {
                                    certStoreException = new CertStoreException(iOException);
                                    throw certStoreException;
                                }
                                try {
                                    collection = this.ldapCertStore.getCRLs((CRLSelector)collection);
                                    // MONITOREXIT : this
                                    return collection;
                                }
                                catch (CertStoreException certStoreException) {
                                    collection = new Collection<X509CRL>("LDAP", certStoreException);
                                    throw collection;
                                }
                            }
                            l = System.currentTimeMillis();
                            if (l - this.lastChecked < 30000L) {
                                if (URICertStore.debug != null) {
                                    URICertStore.debug.println("Returning CRL from cache");
                                }
                                collection = URICertStore.getMatchingCRLs(this.crl, (CRLSelector)collection);
                                // MONITOREXIT : this
                                return collection;
                            }
                            this.lastChecked = l;
                            object = this.uri.toURL().openConnection();
                            if (this.lastModified != 0L) {
                                object.setIfModifiedSince(this.lastModified);
                            }
                            l = this.lastModified;
                            object.setConnectTimeout(URICertStore.CRL_CONNECT_TIMEOUT);
                            inputStream = object.getInputStream();
                            this.lastModified = object.getLastModified();
                            if (l == 0L) break block31;
                            try {
                                if (l != this.lastModified) break block32;
                                object = URICertStore.debug;
                                if (object == null) break block33;
                            }
                            catch (Throwable throwable) {}
                            URICertStore.debug.println("Not modified, using cached copy");
                        }
                        collection = URICertStore.getMatchingCRLs(this.crl, (CRLSelector)collection);
                        if (inputStream == null) return collection;
                        URICertStore.$closeResource(null, inputStream);
                        // MONITOREXIT : this
                        return collection;
                    }
                    if (!(object instanceof HttpURLConnection) || ((HttpURLConnection)object).getResponseCode() != 304) break block31;
                    if (URICertStore.debug != null) {
                        URICertStore.debug.println("Not modified, using cached copy");
                    }
                    collection = URICertStore.getMatchingCRLs(this.crl, collection);
                    if (inputStream == null) return collection;
                    URICertStore.$closeResource(null, inputStream);
                    // MONITOREXIT : this
                    return collection;
                }
                object = URICertStore.debug;
                if (object == null) break block34;
                URICertStore.debug.println("Downloading new CRL...");
                break block34;
                break block35;
            }
            this.crl = (X509CRL)this.factory.generateCRL(inputStream);
            if (inputStream == null) ** GOTO lbl74
            URICertStore.$closeResource(null, inputStream);
lbl74: // 2 sources:
            collection = URICertStore.getMatchingCRLs(this.crl, collection);
            // MONITOREXIT : this
            return collection;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        try {
            throw collection;
        }
        catch (Throwable throwable) {
            if (inputStream == null) throw throwable;
            try {
                URICertStore.$closeResource((Throwable)collection, inputStream);
                throw throwable;
            }
            catch (IOException | CRLException exception) {
                if (URICertStore.debug != null) {
                    URICertStore.debug.println("Exception fetching CRL:");
                    exception.printStackTrace();
                }
                this.lastModified = 0L;
                this.crl = null;
                collection = new Collection<X509CRL>((Throwable)exception);
                object = new PKIX.CertStoreTypeException("URI", (CertStoreException)collection);
                throw object;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Collection<X509Certificate> engineGetCertificates(CertSelector collection) throws CertStoreException {
        block33 : {
            block32 : {
                block29 : {
                    block30 : {
                        block31 : {
                            // MONITORENTER : this
                            if (this.ldap) {
                                collection /* !! */  = (X509CertSelector)collection /* !! */ ;
                                try {
                                    collection /* !! */  = this.ldapHelper.wrap((X509CertSelector)collection /* !! */ , collection /* !! */ .getSubject(), this.ldapPath);
                                    collection /* !! */  = this.ldapCertStore.getCertificates((CertSelector)collection /* !! */ );
                                }
                                catch (IOException iOException) {
                                    certStoreException = new CertStoreException(iOException);
                                    throw certStoreException;
                                }
                                return collection /* !! */ ;
                            }
                            l = System.currentTimeMillis();
                            if (l - this.lastChecked < 30000L) {
                                if (URICertStore.debug != null) {
                                    URICertStore.debug.println("Returning certificates from cache");
                                }
                                collection /* !! */  = URICertStore.getMatchingCerts(this.certs, (CertSelector)collection /* !! */ );
                                // MONITOREXIT : this
                                return collection /* !! */ ;
                            }
                            this.lastChecked = l;
                            object = this.uri.toURL().openConnection();
                            if (this.lastModified != 0L) {
                                object.setIfModifiedSince(this.lastModified);
                            }
                            l = this.lastModified;
                            inputStream = object.getInputStream();
                            this.lastModified = object.getLastModified();
                            if (l == 0L) break block29;
                            try {
                                if (l != this.lastModified) break block30;
                                object = URICertStore.debug;
                                if (object == null) break block31;
                            }
                            catch (Throwable throwable) {}
                            URICertStore.debug.println("Not modified, using cached copy");
                        }
                        collection /* !! */  = URICertStore.getMatchingCerts(this.certs, collection /* !! */ );
                        if (inputStream == null) return collection /* !! */ ;
                        URICertStore.$closeResource(null, inputStream);
                        // MONITOREXIT : this
                        return collection /* !! */ ;
                    }
                    if (!(object instanceof HttpURLConnection) || ((HttpURLConnection)object).getResponseCode() != 304) break block29;
                    if (URICertStore.debug != null) {
                        URICertStore.debug.println("Not modified, using cached copy");
                    }
                    collection /* !! */  = URICertStore.getMatchingCerts(this.certs, collection /* !! */ );
                    if (inputStream == null) return collection /* !! */ ;
                    URICertStore.$closeResource(null, inputStream);
                    // MONITOREXIT : this
                    return collection /* !! */ ;
                }
                object = URICertStore.debug;
                if (object == null) break block32;
                URICertStore.debug.println("Downloading new certificates...");
                break block32;
                break block33;
            }
            this.certs = this.factory.generateCertificates(inputStream);
            if (inputStream == null) ** GOTO lbl69
            URICertStore.$closeResource(null, inputStream);
lbl69: // 2 sources:
            collection /* !! */  = URICertStore.getMatchingCerts(this.certs, collection /* !! */ );
            // MONITOREXIT : this
            return collection /* !! */ ;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        try {
            throw collection /* !! */ ;
        }
        catch (Throwable throwable) {
            if (inputStream == null) throw throwable;
            try {
                URICertStore.$closeResource(collection /* !! */ , inputStream);
                throw throwable;
            }
            catch (IOException | CertificateException exception) {
                if (URICertStore.debug != null) {
                    URICertStore.debug.println("Exception fetching certificates:");
                    exception.printStackTrace();
                }
                this.lastModified = 0L;
                var1_6 = this.certs = Collections.emptySet();
                // MONITOREXIT : this
                return var1_6;
            }
        }
    }

    private static class UCS
    extends CertStore {
        protected UCS(CertStoreSpi certStoreSpi, Provider provider, String string, CertStoreParameters certStoreParameters) {
            super(certStoreSpi, provider, string, certStoreParameters);
        }
    }

    static class URICertStoreParameters
    implements CertStoreParameters {
        private volatile int hashCode = 0;
        private final URI uri;

        URICertStoreParameters(URI uRI) {
            this.uri = uRI;
        }

        @Override
        public Object clone() {
            try {
                Object object = super.clone();
                return object;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
            }
        }

        public boolean equals(Object object) {
            if (!(object instanceof URICertStoreParameters)) {
                return false;
            }
            object = (URICertStoreParameters)object;
            return this.uri.equals(((URICertStoreParameters)object).uri);
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                this.hashCode = 17 * 37 + this.uri.hashCode();
            }
            return this.hashCode;
        }
    }

}

