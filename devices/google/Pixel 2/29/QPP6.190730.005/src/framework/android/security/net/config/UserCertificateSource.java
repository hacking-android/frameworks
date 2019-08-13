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

public final class UserCertificateSource
extends DirectoryCertificateSource {
    private UserCertificateSource() {
        super(new File(Environment.getUserConfigDirectory(UserHandle.myUserId()), "cacerts-added"));
    }

    public static UserCertificateSource getInstance() {
        return NoPreloadHolder.INSTANCE;
    }

    @Override
    protected boolean isCertMarkedAsRemoved(String string2) {
        return false;
    }

    private static class NoPreloadHolder {
        private static final UserCertificateSource INSTANCE = new UserCertificateSource();

        private NoPreloadHolder() {
        }
    }

}

