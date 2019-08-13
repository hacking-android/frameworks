/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.os.Environment;
import android.os.UserHandle;
import android.security.net.config.DirectoryCertificateSource;
import java.io.File;
import java.security.cert.X509Certificate;
import java.util.Set;

public final class SystemCertificateSource
extends DirectoryCertificateSource {
    private final File mUserRemovedCaDir;

    private SystemCertificateSource() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getenv("ANDROID_ROOT"));
        stringBuilder.append("/etc/security/cacerts");
        super(new File(stringBuilder.toString()));
        this.mUserRemovedCaDir = new File(Environment.getUserConfigDirectory(UserHandle.myUserId()), "cacerts-removed");
    }

    public static SystemCertificateSource getInstance() {
        return NoPreloadHolder.INSTANCE;
    }

    @Override
    protected boolean isCertMarkedAsRemoved(String string2) {
        return new File(this.mUserRemovedCaDir, string2).exists();
    }

    private static class NoPreloadHolder {
        private static final SystemCertificateSource INSTANCE = new SystemCertificateSource();

        private NoPreloadHolder() {
        }
    }

}

