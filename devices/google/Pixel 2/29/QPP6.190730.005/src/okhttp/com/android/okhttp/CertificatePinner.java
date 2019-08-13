/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.internal.Util;
import com.android.okhttp.okio.ByteString;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final Map<String, Set<ByteString>> hostnameToPins;

    private CertificatePinner(Builder builder) {
        this.hostnameToPins = Util.immutableMap(builder.hostnameToPins);
    }

    public static String pin(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sha1/");
            stringBuilder.append(CertificatePinner.sha1((X509Certificate)certificate).base64());
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    private static ByteString sha1(X509Certificate x509Certificate) {
        return Util.sha1(ByteString.of(x509Certificate.getPublicKey().getEncoded()));
    }

    public void check(String object3, List<Certificate> object2) throws SSLPeerUnverifiedException {
        Iterator<ByteString> iterator;
        int n;
        Set<ByteString> set = this.findMatchingPins((String)object3);
        if (set == null) {
            return;
        }
        int n2 = iterator.size();
        for (n = 0; n < n2; ++n) {
            if (!set.contains(CertificatePinner.sha1((X509Certificate)iterator.get(n)))) continue;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Certificate pinning failure!");
        stringBuilder = stringBuilder.append("\n  Peer certificate chain:");
        n2 = iterator.size();
        for (n = 0; n < n2; ++n) {
            X509Certificate x509Certificate = (X509Certificate)iterator.get(n);
            stringBuilder.append("\n    ");
            stringBuilder.append(CertificatePinner.pin(x509Certificate));
            stringBuilder.append(": ");
            stringBuilder.append(x509Certificate.getSubjectDN().getName());
        }
        stringBuilder.append("\n  Pinned certificates for ");
        stringBuilder.append((String)object3);
        stringBuilder.append(":");
        for (ByteString byteString : set) {
            stringBuilder.append("\n    sha1/");
            stringBuilder.append(byteString.base64());
        }
        throw new SSLPeerUnverifiedException(stringBuilder.toString());
    }

    public void check(String string, Certificate ... arrcertificate) throws SSLPeerUnverifiedException {
        this.check(string, Arrays.asList(arrcertificate));
    }

    Set<ByteString> findMatchingPins(String object) {
        Set<ByteString> set = this.hostnameToPins.get(object);
        Object object2 = null;
        int n = ((String)object).indexOf(46);
        if (n != ((String)object).lastIndexOf(46)) {
            Map<String, Set<ByteString>> map = this.hostnameToPins;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("*.");
            ((StringBuilder)object2).append(((String)object).substring(n + 1));
            object2 = map.get(((StringBuilder)object2).toString());
        }
        if (set == null && object2 == null) {
            return null;
        }
        if (set != null && object2 != null) {
            object = new LinkedHashSet();
            object.addAll(set);
            object.addAll(object2);
            return object;
        }
        if (set != null) {
            return set;
        }
        return object2;
    }

    public static final class Builder {
        private final Map<String, Set<ByteString>> hostnameToPins = new LinkedHashMap<String, Set<ByteString>>();

        public Builder add(String object, String ... object2) {
            if (object != null) {
                LinkedHashSet<ByteString> linkedHashSet = new LinkedHashSet<ByteString>();
                if ((object = this.hostnameToPins.put((String)object, Collections.unmodifiableSet(linkedHashSet))) != null) {
                    linkedHashSet.addAll((Collection<ByteString>)object);
                }
                int n = ((String[])object2).length;
                for (int i = 0; i < n; ++i) {
                    object = object2[i];
                    if (((String)object).startsWith("sha1/")) {
                        ByteString byteString = ByteString.decodeBase64(((String)object).substring("sha1/".length()));
                        if (byteString != null) {
                            linkedHashSet.add(byteString);
                            continue;
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("pins must be base64: ");
                        ((StringBuilder)object2).append((String)object);
                        throw new IllegalArgumentException(((StringBuilder)object2).toString());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("pins must start with 'sha1/': ");
                    ((StringBuilder)object2).append((String)object);
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
                return this;
            }
            throw new IllegalArgumentException("hostname == null");
        }

        public CertificatePinner build() {
            return new CertificatePinner(this);
        }
    }

}

