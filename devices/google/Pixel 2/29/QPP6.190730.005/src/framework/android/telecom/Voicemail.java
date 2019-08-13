/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.PhoneAccountHandle;

public class Voicemail
implements Parcelable {
    public static final Parcelable.Creator<Voicemail> CREATOR = new Parcelable.Creator<Voicemail>(){

        @Override
        public Voicemail createFromParcel(Parcel parcel) {
            return new Voicemail(parcel);
        }

        public Voicemail[] newArray(int n) {
            return new Voicemail[n];
        }
    };
    private final Long mDuration;
    private final Boolean mHasContent;
    private final Long mId;
    private final Boolean mIsRead;
    private final String mNumber;
    private final PhoneAccountHandle mPhoneAccount;
    private final String mProviderData;
    private final String mSource;
    private final Long mTimestamp;
    private final String mTranscription;
    private final Uri mUri;

    private Voicemail(Parcel parcel) {
        this.mTimestamp = parcel.readLong();
        this.mNumber = (String)parcel.readCharSequence();
        this.mPhoneAccount = parcel.readInt() > 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel) : null;
        this.mId = parcel.readLong();
        this.mDuration = parcel.readLong();
        this.mSource = (String)parcel.readCharSequence();
        this.mProviderData = (String)parcel.readCharSequence();
        this.mUri = parcel.readInt() > 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n > 0;
        this.mIsRead = bl2;
        bl2 = parcel.readInt() > 0 ? bl : false;
        this.mHasContent = bl2;
        this.mTranscription = (String)parcel.readCharSequence();
    }

    private Voicemail(Long l, String string2, PhoneAccountHandle phoneAccountHandle, Long l2, Long l3, String string3, String string4, Uri uri, Boolean bl, Boolean bl2, String string5) {
        this.mTimestamp = l;
        this.mNumber = string2;
        this.mPhoneAccount = phoneAccountHandle;
        this.mId = l2;
        this.mDuration = l3;
        this.mSource = string3;
        this.mProviderData = string4;
        this.mUri = uri;
        this.mIsRead = bl;
        this.mHasContent = bl2;
        this.mTranscription = string5;
    }

    public static Builder createForInsertion(long l, String string2) {
        return new Builder().setNumber(string2).setTimestamp(l);
    }

    public static Builder createForUpdate(long l, String string2) {
        return new Builder().setId(l).setSourceData(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public long getId() {
        return this.mId;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public PhoneAccountHandle getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public String getSourceData() {
        return this.mProviderData;
    }

    public String getSourcePackage() {
        return this.mSource;
    }

    public long getTimestampMillis() {
        return this.mTimestamp;
    }

    public String getTranscription() {
        return this.mTranscription;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean hasContent() {
        return this.mHasContent;
    }

    public boolean isRead() {
        return this.mIsRead;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTimestamp);
        parcel.writeCharSequence(this.mNumber);
        if (this.mPhoneAccount == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mPhoneAccount.writeToParcel(parcel, n);
        }
        parcel.writeLong(this.mId);
        parcel.writeLong(this.mDuration);
        parcel.writeCharSequence(this.mSource);
        parcel.writeCharSequence(this.mProviderData);
        if (this.mUri == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mUri.writeToParcel(parcel, n);
        }
        if (this.mIsRead.booleanValue()) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        if (this.mHasContent.booleanValue()) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeCharSequence(this.mTranscription);
    }

    public static class Builder {
        private Long mBuilderDuration;
        private boolean mBuilderHasContent;
        private Long mBuilderId;
        private Boolean mBuilderIsRead;
        private String mBuilderNumber;
        private PhoneAccountHandle mBuilderPhoneAccount;
        private String mBuilderSourceData;
        private String mBuilderSourcePackage;
        private Long mBuilderTimestamp;
        private String mBuilderTranscription;
        private Uri mBuilderUri;

        private Builder() {
        }

        public Voicemail build() {
            Comparable<Long> comparable = this.mBuilderId;
            long l = comparable == null ? -1L : (Long)comparable;
            this.mBuilderId = l;
            comparable = this.mBuilderTimestamp;
            long l2 = 0L;
            l = comparable == null ? 0L : (Long)comparable;
            this.mBuilderTimestamp = l;
            comparable = this.mBuilderDuration;
            l = comparable == null ? l2 : (Long)comparable;
            this.mBuilderDuration = l;
            comparable = this.mBuilderIsRead;
            boolean bl = comparable == null ? false : (Boolean)comparable;
            this.mBuilderIsRead = bl;
            return new Voicemail(this.mBuilderTimestamp, this.mBuilderNumber, this.mBuilderPhoneAccount, this.mBuilderId, this.mBuilderDuration, this.mBuilderSourcePackage, this.mBuilderSourceData, this.mBuilderUri, this.mBuilderIsRead, this.mBuilderHasContent, this.mBuilderTranscription);
        }

        public Builder setDuration(long l) {
            this.mBuilderDuration = l;
            return this;
        }

        public Builder setHasContent(boolean bl) {
            this.mBuilderHasContent = bl;
            return this;
        }

        public Builder setId(long l) {
            this.mBuilderId = l;
            return this;
        }

        public Builder setIsRead(boolean bl) {
            this.mBuilderIsRead = bl;
            return this;
        }

        public Builder setNumber(String string2) {
            this.mBuilderNumber = string2;
            return this;
        }

        public Builder setPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
            this.mBuilderPhoneAccount = phoneAccountHandle;
            return this;
        }

        public Builder setSourceData(String string2) {
            this.mBuilderSourceData = string2;
            return this;
        }

        public Builder setSourcePackage(String string2) {
            this.mBuilderSourcePackage = string2;
            return this;
        }

        public Builder setTimestamp(long l) {
            this.mBuilderTimestamp = l;
            return this;
        }

        public Builder setTranscription(String string2) {
            this.mBuilderTranscription = string2;
            return this;
        }

        public Builder setUri(Uri uri) {
            this.mBuilderUri = uri;
            return this;
        }
    }

}

