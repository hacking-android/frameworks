/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import javax.crypto.CryptoPermissions;
import javax.crypto.JarVerifier;
import sun.security.jca.GetInstance;

final class JceSecurity {
    private static final URL NULL_URL;
    private static final Object PROVIDER_VERIFIED;
    static final SecureRandom RANDOM;
    private static final Map<Class<?>, URL> codeBaseCacheRef;
    private static CryptoPermissions defaultPolicy;
    private static CryptoPermissions exemptPolicy;
    private static final Map<Provider, Object> verificationResults;
    private static final Map<Provider, Object> verifyingProviders;

    static {
        RANDOM = new SecureRandom();
        defaultPolicy = null;
        exemptPolicy = null;
        verificationResults = new IdentityHashMap<Provider, Object>();
        verifyingProviders = new IdentityHashMap<Provider, Object>();
        PROVIDER_VERIFIED = Boolean.TRUE;
        try {
            URL uRL;
            NULL_URL = uRL = new URL("http://null.sun.com/");
            codeBaseCacheRef = new WeakHashMap();
            return;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private JceSecurity() {
    }

    static boolean canUseProvider(Provider provider) {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static URL getCodeBase(final Class<?> class_) {
        Map<Class<?>, URL> map = codeBaseCacheRef;
        synchronized (map) {
            URL uRL = codeBaseCacheRef.get(class_);
            PrivilegedAction<URL> privilegedAction = uRL;
            if (uRL == null) {
                privilegedAction = new PrivilegedAction<URL>(){

                    @Override
                    public URL run() {
                        Object object = class_.getProtectionDomain();
                        if (object != null && (object = ((ProtectionDomain)object).getCodeSource()) != null) {
                            return ((CodeSource)object).getLocation();
                        }
                        return NULL_URL;
                    }
                };
                privilegedAction = (URL)AccessController.doPrivileged(privilegedAction);
                codeBaseCacheRef.put(class_, (URL)((Object)privilegedAction));
            }
            if (privilegedAction != NULL_URL) return privilegedAction;
            return null;
        }
    }

    static CryptoPermissions getDefaultPolicy() {
        return defaultPolicy;
    }

    static CryptoPermissions getExemptPolicy() {
        return exemptPolicy;
    }

    static GetInstance.Instance getInstance(String object, Class<?> serializable, String string) throws NoSuchAlgorithmException {
        Object object2 = GetInstance.getServices((String)object, string);
        object = null;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Provider.Service service = (Provider.Service)object2.next();
            if (!JceSecurity.canUseProvider(service.getProvider())) continue;
            try {
                object = GetInstance.getInstance(service, serializable);
                return object;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Algorithm ");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(" not available");
        throw new NoSuchAlgorithmException(((StringBuilder)serializable).toString(), (Throwable)object);
    }

    static GetInstance.Instance getInstance(String object, Class<?> serializable, String object2, String string) throws NoSuchAlgorithmException, NoSuchProviderException {
        object2 = GetInstance.getService((String)object, (String)object2, string);
        object = JceSecurity.getVerificationResult(((Provider.Service)object2).getProvider());
        if (object == null) {
            return GetInstance.getInstance((Provider.Service)object2, serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("JCE cannot authenticate the provider ");
        ((StringBuilder)serializable).append(string);
        throw (NoSuchProviderException)new NoSuchProviderException(((StringBuilder)serializable).toString()).initCause((Throwable)object);
    }

    static GetInstance.Instance getInstance(String object, Class<?> serializable, String object2, Provider provider) throws NoSuchAlgorithmException {
        object2 = GetInstance.getService((String)object, (String)object2, provider);
        object = JceSecurity.getVerificationResult(provider);
        if (object == null) {
            return GetInstance.getInstance((Provider.Service)object2, serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("JCE cannot authenticate the provider ");
        ((StringBuilder)serializable).append(provider.getName());
        throw new SecurityException(((StringBuilder)serializable).toString(), (Throwable)object);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static Exception getVerificationResult(Provider serializable) {
        Throwable throwable2222;
        // MONITORENTER : javax.crypto.JceSecurity.class
        Object object = verificationResults.get(serializable);
        Object object2 = PROVIDER_VERIFIED;
        if (object == object2) {
            // MONITOREXIT : javax.crypto.JceSecurity.class
            return null;
        }
        if (object != null) {
            serializable = (Exception)object;
            // MONITOREXIT : javax.crypto.JceSecurity.class
            return serializable;
        }
        if (verifyingProviders.get(serializable) != null) {
            serializable = new NoSuchProviderException("Recursion during verification");
            // MONITOREXIT : javax.crypto.JceSecurity.class
            return serializable;
        }
        verifyingProviders.put((Provider)serializable, Boolean.FALSE);
        JceSecurity.verifyProviderJar(JceSecurity.getCodeBase(serializable.getClass()));
        verificationResults.put((Provider)serializable, PROVIDER_VERIFIED);
        verifyingProviders.remove(serializable);
        // MONITOREXIT : javax.crypto.JceSecurity.class
        return null;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                verificationResults.put((Provider)serializable, exception);
                verifyingProviders.remove(serializable);
            }
            // MONITOREXIT : javax.crypto.JceSecurity.class
            return exception;
        }
        verifyingProviders.remove(serializable);
        throw throwable2222;
    }

    private static void loadPolicies(File object, CryptoPermissions cryptoPermissions, CryptoPermissions cryptoPermissions2) throws Exception {
        JarFile jarFile = new JarFile((File)object);
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            block11 : {
                JarEntry jarEntry;
                InputStream inputStream;
                block10 : {
                    jarEntry = enumeration.nextElement();
                    inputStream = null;
                    object = inputStream;
                    try {
                        if (!jarEntry.getName().startsWith("default_")) break block10;
                        object = inputStream;
                    }
                    catch (Throwable throwable) {
                        if (object != null) {
                            ((InputStream)object).close();
                        }
                        throw throwable;
                    }
                    inputStream = jarFile.getInputStream(jarEntry);
                    object = inputStream;
                    cryptoPermissions.load(inputStream);
                }
                object = inputStream;
                if (!jarEntry.getName().startsWith("exempt_")) break block11;
                object = inputStream;
                inputStream = jarFile.getInputStream(jarEntry);
                object = inputStream;
                cryptoPermissions2.load(inputStream);
                if (inputStream != null) {
                    inputStream.close();
                }
                JarVerifier.verifyPolicySigned(jarEntry.getCertificates());
                continue;
            }
            if (!false) continue;
            throw new NullPointerException();
        }
        jarFile.close();
    }

    static CryptoPermissions verifyExemptJar(URL object) throws Exception {
        object = new JarVerifier((URL)object, true);
        ((JarVerifier)object).verify();
        return ((JarVerifier)object).getPermissions();
    }

    static void verifyProviderJar(URL uRL) throws Exception {
        new JarVerifier(uRL, false).verify();
    }

}

