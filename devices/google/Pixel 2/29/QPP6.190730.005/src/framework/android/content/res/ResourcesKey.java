/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.Objects;

public final class ResourcesKey {
    public final CompatibilityInfo mCompatInfo;
    public final int mDisplayId;
    private final int mHash;
    public final String[] mLibDirs;
    public final String[] mOverlayDirs;
    public final Configuration mOverrideConfiguration;
    @UnsupportedAppUsage
    public final String mResDir;
    @UnsupportedAppUsage
    public final String[] mSplitResDirs;

    @UnsupportedAppUsage
    public ResourcesKey(String string2, String[] arrstring, String[] arrstring2, String[] arrstring3, int n, Configuration configuration, CompatibilityInfo compatibilityInfo) {
        this.mResDir = string2;
        this.mSplitResDirs = arrstring;
        this.mOverlayDirs = arrstring2;
        this.mLibDirs = arrstring3;
        this.mDisplayId = n;
        if (configuration == null) {
            configuration = Configuration.EMPTY;
        }
        this.mOverrideConfiguration = new Configuration(configuration);
        if (compatibilityInfo == null) {
            compatibilityInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
        this.mCompatInfo = compatibilityInfo;
        this.mHash = ((((((17 * 31 + Objects.hashCode(this.mResDir)) * 31 + Arrays.hashCode(this.mSplitResDirs)) * 31 + Arrays.hashCode(this.mOverlayDirs)) * 31 + Arrays.hashCode(this.mLibDirs)) * 31 + this.mDisplayId) * 31 + Objects.hashCode(this.mOverrideConfiguration)) * 31 + Objects.hashCode(this.mCompatInfo);
    }

    private static boolean anyStartsWith(String[] arrstring, String string2) {
        if (arrstring != null) {
            for (String string3 : arrstring) {
                if (string3 == null || !string3.startsWith(string2)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ResourcesKey)) {
            return false;
        }
        object = (ResourcesKey)object;
        if (this.mHash != ((ResourcesKey)object).mHash) {
            return false;
        }
        if (!Objects.equals(this.mResDir, ((ResourcesKey)object).mResDir)) {
            return false;
        }
        if (!Arrays.equals(this.mSplitResDirs, ((ResourcesKey)object).mSplitResDirs)) {
            return false;
        }
        if (!Arrays.equals(this.mOverlayDirs, ((ResourcesKey)object).mOverlayDirs)) {
            return false;
        }
        if (!Arrays.equals(this.mLibDirs, ((ResourcesKey)object).mLibDirs)) {
            return false;
        }
        if (this.mDisplayId != ((ResourcesKey)object).mDisplayId) {
            return false;
        }
        if (!Objects.equals(this.mOverrideConfiguration, ((ResourcesKey)object).mOverrideConfiguration)) {
            return false;
        }
        return Objects.equals(this.mCompatInfo, ((ResourcesKey)object).mCompatInfo);
    }

    public boolean hasOverrideConfiguration() {
        return Configuration.EMPTY.equals(this.mOverrideConfiguration) ^ true;
    }

    public int hashCode() {
        return this.mHash;
    }

    public boolean isPathReferenced(String string2) {
        boolean bl;
        block1 : {
            String string3 = this.mResDir;
            bl = true;
            if (string3 != null && string3.startsWith(string2)) {
                return true;
            }
            if (ResourcesKey.anyStartsWith(this.mSplitResDirs, string2) || ResourcesKey.anyStartsWith(this.mOverlayDirs, string2) || ResourcesKey.anyStartsWith(this.mLibDirs, string2)) break block1;
            bl = false;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("ResourcesKey{");
        stringBuilder.append(" mHash=");
        stringBuilder.append(Integer.toHexString(this.mHash));
        stringBuilder.append(" mResDir=");
        stringBuilder.append(this.mResDir);
        stringBuilder.append(" mSplitDirs=[");
        Object[] arrobject = this.mSplitResDirs;
        if (arrobject != null) {
            stringBuilder.append(TextUtils.join((CharSequence)",", arrobject));
        }
        stringBuilder.append("]");
        stringBuilder.append(" mOverlayDirs=[");
        arrobject = this.mOverlayDirs;
        if (arrobject != null) {
            stringBuilder.append(TextUtils.join((CharSequence)",", arrobject));
        }
        stringBuilder.append("]");
        stringBuilder.append(" mLibDirs=[");
        arrobject = this.mLibDirs;
        if (arrobject != null) {
            stringBuilder.append(TextUtils.join((CharSequence)",", arrobject));
        }
        stringBuilder.append("]");
        stringBuilder.append(" mDisplayId=");
        stringBuilder.append(this.mDisplayId);
        stringBuilder.append(" mOverrideConfig=");
        stringBuilder.append(Configuration.resourceQualifierString(this.mOverrideConfiguration));
        stringBuilder.append(" mCompatInfo=");
        stringBuilder.append(this.mCompatInfo);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

