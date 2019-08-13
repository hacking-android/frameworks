/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public final class RcsFileTransferCreationParams
implements Parcelable {
    public static final Parcelable.Creator<RcsFileTransferCreationParams> CREATOR = new Parcelable.Creator<RcsFileTransferCreationParams>(){

        @Override
        public RcsFileTransferCreationParams createFromParcel(Parcel parcel) {
            return new RcsFileTransferCreationParams(parcel);
        }

        public RcsFileTransferCreationParams[] newArray(int n) {
            return new RcsFileTransferCreationParams[n];
        }
    };
    private String mContentMimeType;
    private Uri mContentUri;
    private long mFileSize;
    private int mFileTransferStatus;
    private int mHeight;
    private long mMediaDuration;
    private String mPreviewMimeType;
    private Uri mPreviewUri;
    private String mRcsFileTransferSessionId;
    private long mTransferOffset;
    private int mWidth;

    private RcsFileTransferCreationParams(Parcel parcel) {
        this.mRcsFileTransferSessionId = parcel.readString();
        this.mContentUri = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.mContentMimeType = parcel.readString();
        this.mFileSize = parcel.readLong();
        this.mTransferOffset = parcel.readLong();
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mMediaDuration = parcel.readLong();
        this.mPreviewUri = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.mPreviewMimeType = parcel.readString();
        this.mFileTransferStatus = parcel.readInt();
    }

    RcsFileTransferCreationParams(Builder builder) {
        this.mRcsFileTransferSessionId = builder.mRcsFileTransferSessionId;
        this.mContentUri = builder.mContentUri;
        this.mContentMimeType = builder.mContentMimeType;
        this.mFileSize = builder.mFileSize;
        this.mTransferOffset = builder.mTransferOffset;
        this.mWidth = builder.mWidth;
        this.mHeight = builder.mHeight;
        this.mMediaDuration = builder.mLength;
        this.mPreviewUri = builder.mPreviewUri;
        this.mPreviewMimeType = builder.mPreviewMimeType;
        this.mFileTransferStatus = builder.mFileTransferStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getContentMimeType() {
        return this.mContentMimeType;
    }

    public Uri getContentUri() {
        return this.mContentUri;
    }

    public long getFileSize() {
        return this.mFileSize;
    }

    public int getFileTransferStatus() {
        return this.mFileTransferStatus;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public long getMediaDuration() {
        return this.mMediaDuration;
    }

    public String getPreviewMimeType() {
        return this.mPreviewMimeType;
    }

    public Uri getPreviewUri() {
        return this.mPreviewUri;
    }

    public String getRcsFileTransferSessionId() {
        return this.mRcsFileTransferSessionId;
    }

    public long getTransferOffset() {
        return this.mTransferOffset;
    }

    public int getWidth() {
        return this.mWidth;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mRcsFileTransferSessionId);
        parcel.writeParcelable(this.mContentUri, n);
        parcel.writeString(this.mContentMimeType);
        parcel.writeLong(this.mFileSize);
        parcel.writeLong(this.mTransferOffset);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeLong(this.mMediaDuration);
        parcel.writeParcelable(this.mPreviewUri, n);
        parcel.writeString(this.mPreviewMimeType);
        parcel.writeInt(this.mFileTransferStatus);
    }

    public class Builder {
        private String mContentMimeType;
        private Uri mContentUri;
        private long mFileSize;
        private int mFileTransferStatus;
        private int mHeight;
        private long mLength;
        private String mPreviewMimeType;
        private Uri mPreviewUri;
        private String mRcsFileTransferSessionId;
        private long mTransferOffset;
        private int mWidth;

        public RcsFileTransferCreationParams build() {
            return new RcsFileTransferCreationParams(this);
        }

        public Builder setContentMimeType(String string2) {
            this.mContentMimeType = string2;
            return this;
        }

        public Builder setContentUri(Uri uri) {
            this.mContentUri = uri;
            return this;
        }

        public Builder setFileSize(long l) {
            this.mFileSize = l;
            return this;
        }

        public Builder setFileTransferSessionId(String string2) {
            this.mRcsFileTransferSessionId = string2;
            return this;
        }

        public Builder setFileTransferStatus(int n) {
            this.mFileTransferStatus = n;
            return this;
        }

        public Builder setHeight(int n) {
            this.mHeight = n;
            return this;
        }

        public Builder setMediaDuration(long l) {
            this.mLength = l;
            return this;
        }

        public Builder setPreviewMimeType(String string2) {
            this.mPreviewMimeType = string2;
            return this;
        }

        public Builder setPreviewUri(Uri uri) {
            this.mPreviewUri = uri;
            return this;
        }

        public Builder setTransferOffset(long l) {
            this.mTransferOffset = l;
            return this;
        }

        public Builder setWidth(int n) {
            this.mWidth = n;
            return this;
        }
    }

}

