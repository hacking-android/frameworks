/*
 * Decompiled with CFR 0.145.
 */
package android.net.http;

import android.annotation.UnsupportedAppUsage;
import android.net.http.SslCertificate;
import java.security.cert.X509Certificate;

public class SslError {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int SSL_DATE_INVALID = 4;
    public static final int SSL_EXPIRED = 1;
    public static final int SSL_IDMISMATCH = 2;
    public static final int SSL_INVALID = 5;
    @Deprecated
    public static final int SSL_MAX_ERROR = 6;
    public static final int SSL_NOTYETVALID = 0;
    public static final int SSL_UNTRUSTED = 3;
    @UnsupportedAppUsage
    final SslCertificate mCertificate;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mErrors;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final String mUrl;

    @Deprecated
    public SslError(int n, SslCertificate sslCertificate) {
        this(n, sslCertificate, "");
    }

    public SslError(int n, SslCertificate sslCertificate, String string2) {
        this.addError(n);
        this.mCertificate = sslCertificate;
        this.mUrl = string2;
    }

    @Deprecated
    public SslError(int n, X509Certificate x509Certificate) {
        this(n, x509Certificate, "");
    }

    public SslError(int n, X509Certificate x509Certificate, String string2) {
        this(n, new SslCertificate(x509Certificate), string2);
    }

    public static SslError SslErrorFromChromiumErrorCode(int n, SslCertificate sslCertificate, String string2) {
        if (n == -200) {
            return new SslError(2, sslCertificate, string2);
        }
        if (n == -201) {
            return new SslError(4, sslCertificate, string2);
        }
        if (n == -202) {
            return new SslError(3, sslCertificate, string2);
        }
        return new SslError(5, sslCertificate, string2);
    }

    public boolean addError(int n) {
        boolean bl = n >= 0 && n < 6;
        if (bl) {
            this.mErrors = 1 << n | this.mErrors;
        }
        return bl;
    }

    public SslCertificate getCertificate() {
        return this.mCertificate;
    }

    public int getPrimaryError() {
        if (this.mErrors != 0) {
            for (int i = 5; i >= 0; --i) {
                if ((this.mErrors & 1 << i) == 0) continue;
                return i;
            }
        }
        return -1;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean hasError(int n) {
        boolean bl = false;
        boolean bl2 = n >= 0 && n < 6;
        boolean bl3 = bl2;
        if (bl2) {
            bl2 = bl;
            if ((this.mErrors & 1 << n) != 0) {
                bl2 = true;
            }
            bl3 = bl2;
        }
        return bl3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("primary error: ");
        stringBuilder.append(this.getPrimaryError());
        stringBuilder.append(" certificate: ");
        stringBuilder.append(this.getCertificate());
        stringBuilder.append(" on URL: ");
        stringBuilder.append(this.getUrl());
        return stringBuilder.toString();
    }
}

