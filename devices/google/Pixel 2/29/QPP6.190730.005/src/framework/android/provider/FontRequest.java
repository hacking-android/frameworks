/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.util.Base64;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;

public final class FontRequest {
    private final List<List<byte[]>> mCertificates;
    private final String mIdentifier;
    private final String mProviderAuthority;
    private final String mProviderPackage;
    private final String mQuery;

    public FontRequest(String charSequence, String string2, String string3) {
        this.mProviderAuthority = Preconditions.checkNotNull(charSequence);
        this.mQuery = Preconditions.checkNotNull(string3);
        this.mProviderPackage = Preconditions.checkNotNull(string2);
        this.mCertificates = Collections.emptyList();
        charSequence = new StringBuilder(this.mProviderAuthority);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mProviderPackage);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mQuery);
        this.mIdentifier = ((StringBuilder)charSequence).toString();
    }

    public FontRequest(String charSequence, String string2, String string3, List<List<byte[]>> list) {
        this.mProviderAuthority = Preconditions.checkNotNull(charSequence);
        this.mProviderPackage = Preconditions.checkNotNull(string2);
        this.mQuery = Preconditions.checkNotNull(string3);
        this.mCertificates = Preconditions.checkNotNull(list);
        charSequence = new StringBuilder(this.mProviderAuthority);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mProviderPackage);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mQuery);
        this.mIdentifier = ((StringBuilder)charSequence).toString();
    }

    public List<List<byte[]>> getCertificates() {
        return this.mCertificates;
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public String getProviderAuthority() {
        return this.mProviderAuthority;
    }

    public String getProviderPackage() {
        return this.mProviderPackage;
    }

    public String getQuery() {
        return this.mQuery;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("FontRequest {mProviderAuthority: ");
        ((StringBuilder)object).append(this.mProviderAuthority);
        ((StringBuilder)object).append(", mProviderPackage: ");
        ((StringBuilder)object).append(this.mProviderPackage);
        ((StringBuilder)object).append(", mQuery: ");
        ((StringBuilder)object).append(this.mQuery);
        ((StringBuilder)object).append(", mCertificates:");
        stringBuilder.append(((StringBuilder)object).toString());
        for (int i = 0; i < this.mCertificates.size(); ++i) {
            stringBuilder.append(" [");
            object = this.mCertificates.get(i);
            for (int j = 0; j < object.size(); ++j) {
                stringBuilder.append(" \"");
                stringBuilder.append(Base64.encodeToString((byte[])object.get(j), 0));
                stringBuilder.append("\"");
            }
            stringBuilder.append(" ]");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

