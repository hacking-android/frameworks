/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.MetadataLoader;
import com.android.i18n.phonenumbers.MetadataManager;
import com.android.i18n.phonenumbers.MetadataSource;
import com.android.i18n.phonenumbers.Phonemetadata;
import java.util.concurrent.atomic.AtomicReference;

final class SingleFileMetadataSourceImpl
implements MetadataSource {
    private final MetadataLoader metadataLoader;
    private final String phoneNumberMetadataFileName;
    private final AtomicReference<MetadataManager.SingleFileMetadataMaps> phoneNumberMetadataRef = new AtomicReference();

    SingleFileMetadataSourceImpl(MetadataLoader metadataLoader) {
        this("/com/android/i18n/phonenumbers/data/SingleFilePhoneNumberMetadataProto", metadataLoader);
    }

    SingleFileMetadataSourceImpl(String string, MetadataLoader metadataLoader) {
        this.phoneNumberMetadataFileName = string;
        this.metadataLoader = metadataLoader;
    }

    @Override
    public Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int n) {
        return MetadataManager.getSingleFileMetadataMaps(this.phoneNumberMetadataRef, this.phoneNumberMetadataFileName, this.metadataLoader).get(n);
    }

    @Override
    public Phonemetadata.PhoneMetadata getMetadataForRegion(String string) {
        return MetadataManager.getSingleFileMetadataMaps(this.phoneNumberMetadataRef, this.phoneNumberMetadataFileName, this.metadataLoader).get(string);
    }
}

