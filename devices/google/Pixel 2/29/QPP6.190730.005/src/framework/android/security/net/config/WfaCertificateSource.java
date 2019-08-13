/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.security.net.config.DirectoryCertificateSource;
import java.io.File;
import java.security.cert.X509Certificate;
import java.util.Set;

public final class WfaCertificateSource
extends DirectoryCertificateSource {
    private WfaCertificateSource() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getenv("ANDROID_ROOT"));
        stringBuilder.append("/etc/security/cacerts_wfa");
        super(new File(stringBuilder.toString()));
    }

    public static WfaCertificateSource getInstance() {
        return NoPreloadHolder.INSTANCE;
    }

    @Override
    protected boolean isCertMarkedAsRemoved(String string2) {
        return false;
    }

    private static class NoPreloadHolder {
        private static final WfaCertificateSource INSTANCE = new WfaCertificateSource();

        private NoPreloadHolder() {
        }
    }

}

