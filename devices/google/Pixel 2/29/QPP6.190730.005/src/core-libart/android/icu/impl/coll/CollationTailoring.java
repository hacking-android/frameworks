/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Trie2_32;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.SharedObject;
import android.icu.text.UnicodeSet;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.VersionInfo;
import java.util.Map;

public final class CollationTailoring {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public ULocale actualLocale = ULocale.ROOT;
    public CollationData data;
    public Map<Integer, Integer> maxExpansions;
    CollationData ownedData;
    private String rules;
    private UResourceBundle rulesResource;
    public SharedObject.Reference<CollationSettings> settings;
    Trie2_32 trie;
    UnicodeSet unsafeBackwardSet;
    public int version = 0;

    CollationTailoring(SharedObject.Reference<CollationSettings> reference) {
        this.settings = reference != null ? reference.clone() : new SharedObject.Reference<CollationSettings>(new CollationSettings());
    }

    static VersionInfo makeBaseVersion(VersionInfo versionInfo) {
        return VersionInfo.getInstance(VersionInfo.UCOL_BUILDER_VERSION.getMajor(), (versionInfo.getMajor() << 3) + versionInfo.getMinor(), versionInfo.getMilli() << 6, 0);
    }

    void ensureOwnedData() {
        if (this.ownedData == null) {
            this.ownedData = new CollationData(Norm2AllModes.getNFCInstance().impl);
        }
        this.data = this.ownedData;
    }

    public String getRules() {
        Object object = this.rules;
        if (object != null) {
            return object;
        }
        object = this.rulesResource;
        if (object != null) {
            return ((UResourceBundle)object).getString();
        }
        return "";
    }

    int getUCAVersion() {
        int n = this.version;
        return n >> 14 & 3 | n >> 12 & 4080;
    }

    void setRules(String string) {
        this.rules = string;
    }

    void setRulesResource(UResourceBundle uResourceBundle) {
        this.rulesResource = uResourceBundle;
    }

    void setVersion(int n, int n2) {
        int n3 = n2 >> 16 & 65280;
        int n4 = n2 >> 16 & 255;
        int n5 = n2 & 255;
        this.version = VersionInfo.UCOL_BUILDER_VERSION.getMajor() << 24 | 16760832 & n | (n3 >> 6) + n3 & 16128 | (n4 << 3) + (n4 >> 5) + (n2 >> 8 & 255) + (n5 << 4) + (n5 >> 4) & 255;
    }
}

