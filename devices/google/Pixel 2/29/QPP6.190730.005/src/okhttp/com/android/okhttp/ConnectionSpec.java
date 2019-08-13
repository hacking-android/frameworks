/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.CipherSuite;
import com.android.okhttp.TlsVersion;
import com.android.okhttp.internal.Util;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;

public final class ConnectionSpec {
    private static final CipherSuite[] APPROVED_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
    public static final ConnectionSpec CLEARTEXT;
    public static final ConnectionSpec COMPATIBLE_TLS;
    public static final ConnectionSpec MODERN_TLS;
    private final String[] cipherSuites;
    private final boolean supportsTlsExtensions;
    private final boolean tls;
    private final String[] tlsVersions;

    static {
        MODERN_TLS = new Builder(true).cipherSuites(APPROVED_CIPHER_SUITES).tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
        COMPATIBLE_TLS = new Builder(MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
        CLEARTEXT = new Builder(false).build();
    }

    private ConnectionSpec(Builder builder) {
        this.tls = builder.tls;
        this.cipherSuites = builder.cipherSuites;
        this.tlsVersions = builder.tlsVersions;
        this.supportsTlsExtensions = builder.supportsTlsExtensions;
    }

    private static boolean nonEmptyIntersection(String[] arrstring, String[] arrstring2) {
        if (arrstring != null && arrstring2 != null && arrstring.length != 0 && arrstring2.length != 0) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (!Util.contains(arrstring2, arrstring[i])) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private ConnectionSpec supportedSpec(SSLSocket sSLSocket, boolean bl) {
        String[] arrstring = this.cipherSuites;
        arrstring = arrstring != null ? Util.intersect(String.class, arrstring, sSLSocket.getEnabledCipherSuites()) : sSLSocket.getEnabledCipherSuites();
        String[] arrstring2 = this.tlsVersions;
        arrstring2 = arrstring2 != null ? Util.intersect(String.class, arrstring2, sSLSocket.getEnabledProtocols()) : sSLSocket.getEnabledProtocols();
        String[] arrstring3 = arrstring;
        if (bl) {
            arrstring3 = arrstring;
            if (Util.contains(sSLSocket.getSupportedCipherSuites(), "TLS_FALLBACK_SCSV")) {
                arrstring3 = Util.concat(arrstring, "TLS_FALLBACK_SCSV");
            }
        }
        return new Builder(this).cipherSuites(arrstring3).tlsVersions(arrstring2).build();
    }

    void apply(SSLSocket sSLSocket, boolean bl) {
        ConnectionSpec connectionSpec = this.supportedSpec(sSLSocket, bl);
        String[] arrstring = connectionSpec.tlsVersions;
        if (arrstring != null) {
            sSLSocket.setEnabledProtocols(arrstring);
        }
        if ((arrstring = connectionSpec.cipherSuites) != null) {
            sSLSocket.setEnabledCipherSuites(arrstring);
        }
    }

    public List<CipherSuite> cipherSuites() {
        String[] arrstring = this.cipherSuites;
        if (arrstring == null) {
            return null;
        }
        CipherSuite[] arrcipherSuite = new CipherSuite[arrstring.length];
        for (int i = 0; i < (arrstring = this.cipherSuites).length; ++i) {
            arrcipherSuite[i] = CipherSuite.forJavaName(arrstring[i]);
        }
        return Util.immutableList(arrcipherSuite);
    }

    public boolean equals(Object object) {
        if (!(object instanceof ConnectionSpec)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (ConnectionSpec)object;
        boolean bl = this.tls;
        if (bl != ((ConnectionSpec)object).tls) {
            return false;
        }
        if (bl) {
            if (!Arrays.equals(this.cipherSuites, ((ConnectionSpec)object).cipherSuites)) {
                return false;
            }
            if (!Arrays.equals(this.tlsVersions, ((ConnectionSpec)object).tlsVersions)) {
                return false;
            }
            if (this.supportsTlsExtensions != ((ConnectionSpec)object).supportsTlsExtensions) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int n = 17;
        if (this.tls) {
            n = ((17 * 31 + Arrays.hashCode(this.cipherSuites)) * 31 + Arrays.hashCode(this.tlsVersions)) * 31 + (this.supportsTlsExtensions ^ true);
        }
        return n;
    }

    public boolean isCompatible(SSLSocket sSLSocket) {
        if (!this.tls) {
            return false;
        }
        String[] arrstring = this.tlsVersions;
        if (arrstring != null && !ConnectionSpec.nonEmptyIntersection(arrstring, sSLSocket.getEnabledProtocols())) {
            return false;
        }
        arrstring = this.cipherSuites;
        return arrstring == null || ConnectionSpec.nonEmptyIntersection(arrstring, sSLSocket.getEnabledCipherSuites());
    }

    public boolean isTls() {
        return this.tls;
    }

    public boolean supportsTlsExtensions() {
        return this.supportsTlsExtensions;
    }

    public List<TlsVersion> tlsVersions() {
        String[] arrstring;
        Object[] arrobject = this.tlsVersions;
        if (arrobject == null) {
            return null;
        }
        arrobject = new TlsVersion[arrobject.length];
        for (int i = 0; i < (arrstring = this.tlsVersions).length; ++i) {
            arrobject[i] = TlsVersion.forJavaName(arrstring[i]);
        }
        return Util.immutableList(arrobject);
    }

    public String toString() {
        if (!this.tls) {
            return "ConnectionSpec()";
        }
        Object object = this.cipherSuites;
        String string = "[all enabled]";
        object = object != null ? this.cipherSuites().toString() : "[all enabled]";
        if (this.tlsVersions != null) {
            string = this.tlsVersions().toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ConnectionSpec(cipherSuites=");
        stringBuilder.append((String)object);
        stringBuilder.append(", tlsVersions=");
        stringBuilder.append(string);
        stringBuilder.append(", supportsTlsExtensions=");
        stringBuilder.append(this.supportsTlsExtensions);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static final class Builder {
        private String[] cipherSuites;
        private boolean supportsTlsExtensions;
        private boolean tls;
        private String[] tlsVersions;

        public Builder(ConnectionSpec connectionSpec) {
            this.tls = connectionSpec.tls;
            this.cipherSuites = connectionSpec.cipherSuites;
            this.tlsVersions = connectionSpec.tlsVersions;
            this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions;
        }

        Builder(boolean bl) {
            this.tls = bl;
        }

        public Builder allEnabledCipherSuites() {
            if (this.tls) {
                this.cipherSuites = null;
                return this;
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder allEnabledTlsVersions() {
            if (this.tls) {
                this.tlsVersions = null;
                return this;
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public ConnectionSpec build() {
            return new ConnectionSpec(this);
        }

        public Builder cipherSuites(CipherSuite ... arrcipherSuite) {
            if (this.tls) {
                String[] arrstring = new String[arrcipherSuite.length];
                for (int i = 0; i < arrcipherSuite.length; ++i) {
                    arrstring[i] = arrcipherSuite[i].javaName;
                }
                return this.cipherSuites(arrstring);
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder cipherSuites(String ... arrstring) {
            if (this.tls) {
                if (arrstring.length != 0) {
                    this.cipherSuites = (String[])arrstring.clone();
                    return this;
                }
                throw new IllegalArgumentException("At least one cipher suite is required");
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder supportsTlsExtensions(boolean bl) {
            if (this.tls) {
                this.supportsTlsExtensions = bl;
                return this;
            }
            throw new IllegalStateException("no TLS extensions for cleartext connections");
        }

        public Builder tlsVersions(TlsVersion ... arrtlsVersion) {
            if (this.tls) {
                String[] arrstring = new String[arrtlsVersion.length];
                for (int i = 0; i < arrtlsVersion.length; ++i) {
                    arrstring[i] = arrtlsVersion[i].javaName;
                }
                return this.tlsVersions(arrstring);
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public Builder tlsVersions(String ... arrstring) {
            if (this.tls) {
                if (arrstring.length != 0) {
                    this.tlsVersions = (String[])arrstring.clone();
                    return this;
                }
                throw new IllegalArgumentException("At least one TLS version is required");
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }
    }

}

