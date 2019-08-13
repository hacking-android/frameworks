/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.Phonemetadata;

interface MetadataSource {
    public Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int var1);

    public Phonemetadata.PhoneMetadata getMetadataForRegion(String var1);
}

