/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.CountryCodeToRegionCodeMap;
import com.android.i18n.phonenumbers.MetadataLoader;
import com.android.i18n.phonenumbers.MetadataManager;
import com.android.i18n.phonenumbers.MetadataSource;
import com.android.i18n.phonenumbers.Phonemetadata;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

final class MultiFileMetadataSourceImpl
implements MetadataSource {
    private final ConcurrentHashMap<String, Phonemetadata.PhoneMetadata> geographicalRegions = new ConcurrentHashMap();
    private final MetadataLoader metadataLoader;
    private final ConcurrentHashMap<Integer, Phonemetadata.PhoneMetadata> nonGeographicalRegions = new ConcurrentHashMap();
    private final String phoneNumberMetadataFilePrefix;

    MultiFileMetadataSourceImpl(MetadataLoader metadataLoader) {
        this("/com/android/i18n/phonenumbers/data/PhoneNumberMetadataProto", metadataLoader);
    }

    MultiFileMetadataSourceImpl(String string, MetadataLoader metadataLoader) {
        this.phoneNumberMetadataFilePrefix = string;
        this.metadataLoader = metadataLoader;
    }

    private boolean isNonGeographical(int n) {
        boolean bl;
        block0 : {
            List<String> list = CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap().get(n);
            n = list.size();
            bl = false;
            if (n != 1 || !"001".equals(list.get(0))) break block0;
            bl = true;
        }
        return bl;
    }

    @Override
    public Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int n) {
        if (!this.isNonGeographical(n)) {
            return null;
        }
        return MetadataManager.getMetadataFromMultiFilePrefix(n, this.nonGeographicalRegions, this.phoneNumberMetadataFilePrefix, this.metadataLoader);
    }

    @Override
    public Phonemetadata.PhoneMetadata getMetadataForRegion(String string) {
        return MetadataManager.getMetadataFromMultiFilePrefix(string, this.geographicalRegions, this.phoneNumberMetadataFilePrefix, this.metadataLoader);
    }
}

