/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.content.pm.ApplicationInfo;
import android.security.net.config.CertificateSource;
import android.security.net.config.CertificatesEntryRef;
import android.security.net.config.NetworkSecurityTrustManager;
import android.security.net.config.PinSet;
import android.security.net.config.SystemCertificateSource;
import android.security.net.config.TrustAnchor;
import android.security.net.config.UserCertificateSource;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class NetworkSecurityConfig {
    public static final boolean DEFAULT_CLEARTEXT_TRAFFIC_PERMITTED = true;
    public static final boolean DEFAULT_HSTS_ENFORCED = false;
    private Set<TrustAnchor> mAnchors;
    private final Object mAnchorsLock = new Object();
    private final List<CertificatesEntryRef> mCertificatesEntryRefs;
    private final boolean mCleartextTrafficPermitted;
    private final boolean mHstsEnforced;
    private final PinSet mPins;
    private NetworkSecurityTrustManager mTrustManager;
    private final Object mTrustManagerLock = new Object();

    private NetworkSecurityConfig(boolean bl, boolean bl2, PinSet pinSet, List<CertificatesEntryRef> list) {
        this.mCleartextTrafficPermitted = bl;
        this.mHstsEnforced = bl2;
        this.mPins = pinSet;
        this.mCertificatesEntryRefs = list;
        Collections.sort(this.mCertificatesEntryRefs, new Comparator<CertificatesEntryRef>(){

            @Override
            public int compare(CertificatesEntryRef certificatesEntryRef, CertificatesEntryRef certificatesEntryRef2) {
                if (certificatesEntryRef.overridesPins()) {
                    int n = certificatesEntryRef2.overridesPins() ? 0 : -1;
                    return n;
                }
                return (int)certificatesEntryRef2.overridesPins();
            }
        });
    }

    public static Builder getDefaultBuilder(ApplicationInfo applicationInfo) {
        Builder builder = new Builder().setHstsEnforced(false).addCertificatesEntryRef(new CertificatesEntryRef(SystemCertificateSource.getInstance(), false));
        boolean bl = applicationInfo.targetSdkVersion < 28 && !applicationInfo.isInstantApp();
        builder.setCleartextTrafficPermitted(bl);
        if (applicationInfo.targetSdkVersion <= 23 && !applicationInfo.isPrivilegedApp()) {
            builder.addCertificatesEntryRef(new CertificatesEntryRef(UserCertificateSource.getInstance(), false));
        }
        return builder;
    }

    public Set<X509Certificate> findAllCertificatesByIssuerAndSignature(X509Certificate x509Certificate) {
        ArraySet<X509Certificate> arraySet = new ArraySet<X509Certificate>();
        Iterator<CertificatesEntryRef> iterator = this.mCertificatesEntryRefs.iterator();
        while (iterator.hasNext()) {
            arraySet.addAll(iterator.next().findAllCertificatesByIssuerAndSignature(x509Certificate));
        }
        return arraySet;
    }

    public TrustAnchor findTrustAnchorByIssuerAndSignature(X509Certificate x509Certificate) {
        Iterator<CertificatesEntryRef> iterator = this.mCertificatesEntryRefs.iterator();
        while (iterator.hasNext()) {
            TrustAnchor trustAnchor = iterator.next().findByIssuerAndSignature(x509Certificate);
            if (trustAnchor == null) continue;
            return trustAnchor;
        }
        return null;
    }

    public TrustAnchor findTrustAnchorBySubjectAndPublicKey(X509Certificate x509Certificate) {
        Iterator<CertificatesEntryRef> iterator = this.mCertificatesEntryRefs.iterator();
        while (iterator.hasNext()) {
            TrustAnchor trustAnchor = iterator.next().findBySubjectAndPublicKey(x509Certificate);
            if (trustAnchor == null) continue;
            return trustAnchor;
        }
        return null;
    }

    public PinSet getPins() {
        return this.mPins;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Set<TrustAnchor> getTrustAnchors() {
        Object object = this.mAnchorsLock;
        synchronized (object) {
            if (this.mAnchors != null) {
                return this.mAnchors;
            }
            Object object2 = new ArrayMap();
            ArraySet<TrustAnchor> arraySet = this.mCertificatesEntryRefs.iterator();
            block2 : do {
                if (!arraySet.hasNext()) {
                    arraySet = new ArraySet<TrustAnchor>(object2.size());
                    arraySet.addAll(object2.values());
                    this.mAnchors = arraySet;
                    return this.mAnchors;
                }
                Iterator<TrustAnchor> iterator = arraySet.next().getTrustAnchors().iterator();
                do {
                    if (!iterator.hasNext()) continue block2;
                    TrustAnchor trustAnchor = iterator.next();
                    X509Certificate x509Certificate = trustAnchor.certificate;
                    if (object2.containsKey(x509Certificate)) continue;
                    object2.put(x509Certificate, trustAnchor);
                } while (true);
                break;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public NetworkSecurityTrustManager getTrustManager() {
        Object object = this.mTrustManagerLock;
        synchronized (object) {
            NetworkSecurityTrustManager networkSecurityTrustManager;
            if (this.mTrustManager != null) return this.mTrustManager;
            this.mTrustManager = networkSecurityTrustManager = new NetworkSecurityTrustManager(this);
            return this.mTrustManager;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleTrustStorageUpdate() {
        Object object = this.mAnchorsLock;
        synchronized (object) {
            this.mAnchors = null;
            Iterator<CertificatesEntryRef> iterator = this.mCertificatesEntryRefs.iterator();
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT [2, 3, 4] lbl7 : MonitorExitStatement: MONITOREXIT : var1_1
                    this.getTrustManager().handleTrustStorageUpdate();
                    return;
                }
                iterator.next().handleTrustStorageUpdate();
            } while (true);
        }
    }

    public boolean isCleartextTrafficPermitted() {
        return this.mCleartextTrafficPermitted;
    }

    public boolean isHstsEnforced() {
        return this.mHstsEnforced;
    }

    public static final class Builder {
        private List<CertificatesEntryRef> mCertificatesEntryRefs;
        private boolean mCleartextTrafficPermitted = true;
        private boolean mCleartextTrafficPermittedSet = false;
        private boolean mHstsEnforced = false;
        private boolean mHstsEnforcedSet = false;
        private Builder mParentBuilder;
        private PinSet mPinSet;

        private List<CertificatesEntryRef> getEffectiveCertificatesEntryRefs() {
            Object object = this.mCertificatesEntryRefs;
            if (object != null) {
                return object;
            }
            object = this.mParentBuilder;
            if (object != null) {
                return Builder.super.getEffectiveCertificatesEntryRefs();
            }
            return Collections.emptyList();
        }

        private boolean getEffectiveCleartextTrafficPermitted() {
            if (this.mCleartextTrafficPermittedSet) {
                return this.mCleartextTrafficPermitted;
            }
            Builder builder = this.mParentBuilder;
            if (builder != null) {
                return builder.getEffectiveCleartextTrafficPermitted();
            }
            return true;
        }

        private boolean getEffectiveHstsEnforced() {
            if (this.mHstsEnforcedSet) {
                return this.mHstsEnforced;
            }
            Builder builder = this.mParentBuilder;
            if (builder != null) {
                return builder.getEffectiveHstsEnforced();
            }
            return false;
        }

        private PinSet getEffectivePinSet() {
            Object object = this.mPinSet;
            if (object != null) {
                return object;
            }
            object = this.mParentBuilder;
            if (object != null) {
                return Builder.super.getEffectivePinSet();
            }
            return PinSet.EMPTY_PINSET;
        }

        public Builder addCertificatesEntryRef(CertificatesEntryRef certificatesEntryRef) {
            if (this.mCertificatesEntryRefs == null) {
                this.mCertificatesEntryRefs = new ArrayList<CertificatesEntryRef>();
            }
            this.mCertificatesEntryRefs.add(certificatesEntryRef);
            return this;
        }

        public Builder addCertificatesEntryRefs(Collection<? extends CertificatesEntryRef> collection) {
            if (this.mCertificatesEntryRefs == null) {
                this.mCertificatesEntryRefs = new ArrayList<CertificatesEntryRef>();
            }
            this.mCertificatesEntryRefs.addAll(collection);
            return this;
        }

        public NetworkSecurityConfig build() {
            return new NetworkSecurityConfig(this.getEffectiveCleartextTrafficPermitted(), this.getEffectiveHstsEnforced(), this.getEffectivePinSet(), this.getEffectiveCertificatesEntryRefs());
        }

        List<CertificatesEntryRef> getCertificatesEntryRefs() {
            return this.mCertificatesEntryRefs;
        }

        public Builder getParent() {
            return this.mParentBuilder;
        }

        public boolean hasCertificatesEntryRefs() {
            boolean bl = this.mCertificatesEntryRefs != null;
            return bl;
        }

        public Builder setCleartextTrafficPermitted(boolean bl) {
            this.mCleartextTrafficPermitted = bl;
            this.mCleartextTrafficPermittedSet = true;
            return this;
        }

        public Builder setHstsEnforced(boolean bl) {
            this.mHstsEnforced = bl;
            this.mHstsEnforcedSet = true;
            return this;
        }

        public Builder setParent(Builder builder) {
            for (Builder builder2 = builder; builder2 != null; builder2 = builder2.getParent()) {
                if (builder2 != this) {
                    continue;
                }
                throw new IllegalArgumentException("Loops are not allowed in Builder parents");
            }
            this.mParentBuilder = builder;
            return this;
        }

        public Builder setPinSet(PinSet pinSet) {
            this.mPinSet = pinSet;
            return this;
        }
    }

}

