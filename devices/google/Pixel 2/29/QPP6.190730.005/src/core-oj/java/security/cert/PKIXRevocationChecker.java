/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.net.URI;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Extension;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class PKIXRevocationChecker
extends PKIXCertPathChecker {
    private List<Extension> ocspExtensions = Collections.emptyList();
    private URI ocspResponder;
    private X509Certificate ocspResponderCert;
    private Map<X509Certificate, byte[]> ocspResponses = Collections.emptyMap();
    private Set<Option> options = Collections.emptySet();

    protected PKIXRevocationChecker() {
    }

    @Override
    public PKIXRevocationChecker clone() {
        PKIXRevocationChecker pKIXRevocationChecker = (PKIXRevocationChecker)super.clone();
        pKIXRevocationChecker.ocspExtensions = new ArrayList<Extension>(this.ocspExtensions);
        pKIXRevocationChecker.ocspResponses = new HashMap<X509Certificate, byte[]>(this.ocspResponses);
        for (Map.Entry<X509Certificate, byte[]> entry : pKIXRevocationChecker.ocspResponses.entrySet()) {
            entry.setValue((byte[])entry.getValue().clone());
        }
        pKIXRevocationChecker.options = new HashSet<Option>(this.options);
        return pKIXRevocationChecker;
    }

    public List<Extension> getOcspExtensions() {
        return Collections.unmodifiableList(this.ocspExtensions);
    }

    public URI getOcspResponder() {
        return this.ocspResponder;
    }

    public X509Certificate getOcspResponderCert() {
        return this.ocspResponderCert;
    }

    public Map<X509Certificate, byte[]> getOcspResponses() {
        HashMap<X509Certificate, byte[]> hashMap = new HashMap<X509Certificate, byte[]>(this.ocspResponses.size());
        for (Map.Entry<X509Certificate, byte[]> entry : this.ocspResponses.entrySet()) {
            hashMap.put(entry.getKey(), (byte[])entry.getValue().clone());
        }
        return hashMap;
    }

    public Set<Option> getOptions() {
        return Collections.unmodifiableSet(this.options);
    }

    public abstract List<CertPathValidatorException> getSoftFailExceptions();

    public void setOcspExtensions(List<Extension> list) {
        list = list == null ? Collections.emptyList() : new ArrayList<Extension>(list);
        this.ocspExtensions = list;
    }

    public void setOcspResponder(URI uRI) {
        this.ocspResponder = uRI;
    }

    public void setOcspResponderCert(X509Certificate x509Certificate) {
        this.ocspResponderCert = x509Certificate;
    }

    public void setOcspResponses(Map<X509Certificate, byte[]> object2) {
        if (object2 == null) {
            this.ocspResponses = Collections.emptyMap();
        } else {
            HashMap<X509Certificate, byte[]> hashMap = new HashMap<X509Certificate, byte[]>(object2.size());
            for (Map.Entry<K, V> entry : object2.entrySet()) {
                hashMap.put((X509Certificate)entry.getKey(), (byte[])((byte[])entry.getValue()).clone());
            }
            this.ocspResponses = hashMap;
        }
    }

    public void setOptions(Set<Option> set) {
        set = set == null ? Collections.emptySet() : new HashSet<Option>(set);
        this.options = set;
    }

    public static enum Option {
        ONLY_END_ENTITY,
        PREFER_CRLS,
        NO_FALLBACK,
        SOFT_FAIL;
        
    }

}

