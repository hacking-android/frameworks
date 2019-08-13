/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.mtp;

import com.android.internal.util.Preconditions;
import dalvik.system.VMRuntime;

public final class MtpObjectInfo {
    private int mAssociationDesc;
    private int mAssociationType;
    private int mCompressedSize;
    private long mDateCreated;
    private long mDateModified;
    private int mFormat;
    private int mHandle;
    private int mImagePixDepth;
    private int mImagePixHeight;
    private int mImagePixWidth;
    private String mKeywords = "";
    private String mName = "";
    private int mParent;
    private int mProtectionStatus;
    private int mSequenceNumber;
    private int mStorageId;
    private int mThumbCompressedSize;
    private int mThumbFormat;
    private int mThumbPixHeight;
    private int mThumbPixWidth;

    private MtpObjectInfo() {
    }

    private static int longToUint32(long l, String string2) {
        Preconditions.checkArgumentInRange(l, 0L, 0xFFFFFFFFL, string2);
        return (int)l;
    }

    private static long uint32ToLong(int n) {
        long l = n < 0 ? (long)n + 0x100000000L : (long)n;
        return l;
    }

    public final int getAssociationDesc() {
        return this.mAssociationDesc;
    }

    public final int getAssociationType() {
        return this.mAssociationType;
    }

    public final int getCompressedSize() {
        boolean bl = this.mCompressedSize >= 0;
        Preconditions.checkState(bl);
        return this.mCompressedSize;
    }

    public final long getCompressedSizeLong() {
        return MtpObjectInfo.uint32ToLong(this.mCompressedSize);
    }

    public final long getDateCreated() {
        return this.mDateCreated;
    }

    public final long getDateModified() {
        return this.mDateModified;
    }

    public final int getFormat() {
        return this.mFormat;
    }

    public final int getImagePixDepth() {
        boolean bl = this.mImagePixDepth >= 0;
        Preconditions.checkState(bl);
        return this.mImagePixDepth;
    }

    public final long getImagePixDepthLong() {
        return MtpObjectInfo.uint32ToLong(this.mImagePixDepth);
    }

    public final int getImagePixHeight() {
        boolean bl = this.mImagePixHeight >= 0;
        Preconditions.checkState(bl);
        return this.mImagePixHeight;
    }

    public final long getImagePixHeightLong() {
        return MtpObjectInfo.uint32ToLong(this.mImagePixHeight);
    }

    public final int getImagePixWidth() {
        boolean bl = this.mImagePixWidth >= 0;
        Preconditions.checkState(bl);
        return this.mImagePixWidth;
    }

    public final long getImagePixWidthLong() {
        return MtpObjectInfo.uint32ToLong(this.mImagePixWidth);
    }

    public final String getKeywords() {
        return this.mKeywords;
    }

    public final String getName() {
        return this.mName;
    }

    public final int getObjectHandle() {
        return this.mHandle;
    }

    public final int getParent() {
        return this.mParent;
    }

    public final int getProtectionStatus() {
        return this.mProtectionStatus;
    }

    public final int getSequenceNumber() {
        boolean bl = this.mSequenceNumber >= 0;
        Preconditions.checkState(bl);
        return this.mSequenceNumber;
    }

    public final long getSequenceNumberLong() {
        return MtpObjectInfo.uint32ToLong(this.mSequenceNumber);
    }

    public final int getStorageId() {
        return this.mStorageId;
    }

    public final int getThumbCompressedSize() {
        boolean bl = this.mThumbCompressedSize >= 0;
        Preconditions.checkState(bl);
        return this.mThumbCompressedSize;
    }

    public final long getThumbCompressedSizeLong() {
        return MtpObjectInfo.uint32ToLong(this.mThumbCompressedSize);
    }

    public final int getThumbFormat() {
        return this.mThumbFormat;
    }

    public final int getThumbPixHeight() {
        boolean bl = this.mThumbPixHeight >= 0;
        Preconditions.checkState(bl);
        return this.mThumbPixHeight;
    }

    public final long getThumbPixHeightLong() {
        return MtpObjectInfo.uint32ToLong(this.mThumbPixHeight);
    }

    public final int getThumbPixWidth() {
        boolean bl = this.mThumbPixWidth >= 0;
        Preconditions.checkState(bl);
        return this.mThumbPixWidth;
    }

    public final long getThumbPixWidthLong() {
        return MtpObjectInfo.uint32ToLong(this.mThumbPixWidth);
    }

    public static class Builder {
        private MtpObjectInfo mObjectInfo = new MtpObjectInfo();

        public Builder() {
            this.mObjectInfo.mHandle = -1;
        }

        public Builder(MtpObjectInfo mtpObjectInfo) {
            this.mObjectInfo.mHandle = -1;
            this.mObjectInfo.mAssociationDesc = mtpObjectInfo.mAssociationDesc;
            this.mObjectInfo.mAssociationType = mtpObjectInfo.mAssociationType;
            this.mObjectInfo.mCompressedSize = mtpObjectInfo.mCompressedSize;
            this.mObjectInfo.mDateCreated = mtpObjectInfo.mDateCreated;
            this.mObjectInfo.mDateModified = mtpObjectInfo.mDateModified;
            this.mObjectInfo.mFormat = mtpObjectInfo.mFormat;
            this.mObjectInfo.mImagePixDepth = mtpObjectInfo.mImagePixDepth;
            this.mObjectInfo.mImagePixHeight = mtpObjectInfo.mImagePixHeight;
            this.mObjectInfo.mImagePixWidth = mtpObjectInfo.mImagePixWidth;
            this.mObjectInfo.mKeywords = mtpObjectInfo.mKeywords;
            this.mObjectInfo.mName = mtpObjectInfo.mName;
            this.mObjectInfo.mParent = mtpObjectInfo.mParent;
            this.mObjectInfo.mProtectionStatus = mtpObjectInfo.mProtectionStatus;
            this.mObjectInfo.mSequenceNumber = mtpObjectInfo.mSequenceNumber;
            this.mObjectInfo.mStorageId = mtpObjectInfo.mStorageId;
            this.mObjectInfo.mThumbCompressedSize = mtpObjectInfo.mThumbCompressedSize;
            this.mObjectInfo.mThumbFormat = mtpObjectInfo.mThumbFormat;
            this.mObjectInfo.mThumbPixHeight = mtpObjectInfo.mThumbPixHeight;
            this.mObjectInfo.mThumbPixWidth = mtpObjectInfo.mThumbPixWidth;
        }

        public MtpObjectInfo build() {
            MtpObjectInfo mtpObjectInfo = this.mObjectInfo;
            this.mObjectInfo = null;
            return mtpObjectInfo;
        }

        public Builder setAssociationDesc(int n) {
            this.mObjectInfo.mAssociationDesc = n;
            return this;
        }

        public Builder setAssociationType(int n) {
            this.mObjectInfo.mAssociationType = n;
            return this;
        }

        public Builder setCompressedSize(long l) {
            this.mObjectInfo.mCompressedSize = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }

        public Builder setDateCreated(long l) {
            this.mObjectInfo.mDateCreated = l;
            return this;
        }

        public Builder setDateModified(long l) {
            this.mObjectInfo.mDateModified = l;
            return this;
        }

        public Builder setFormat(int n) {
            this.mObjectInfo.mFormat = n;
            return this;
        }

        public Builder setImagePixDepth(long l) {
            this.mObjectInfo.mImagePixDepth = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }

        public Builder setImagePixHeight(long l) {
            this.mObjectInfo.mImagePixHeight = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }

        public Builder setImagePixWidth(long l) {
            this.mObjectInfo.mImagePixWidth = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }

        public Builder setKeywords(String string2) {
            String string3;
            if (VMRuntime.getRuntime().getTargetSdkVersion() > 25) {
                Preconditions.checkNotNull(string2);
                string3 = string2;
            } else {
                string3 = string2;
                if (string2 == null) {
                    string3 = "";
                }
            }
            this.mObjectInfo.mKeywords = string3;
            return this;
        }

        public Builder setName(String string2) {
            Preconditions.checkNotNull(string2);
            this.mObjectInfo.mName = string2;
            return this;
        }

        public Builder setObjectHandle(int n) {
            this.mObjectInfo.mHandle = n;
            return this;
        }

        public Builder setParent(int n) {
            this.mObjectInfo.mParent = n;
            return this;
        }

        public Builder setProtectionStatus(int n) {
            this.mObjectInfo.mProtectionStatus = n;
            return this;
        }

        public Builder setSequenceNumber(long l) {
            this.mObjectInfo.mSequenceNumber = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }

        public Builder setStorageId(int n) {
            this.mObjectInfo.mStorageId = n;
            return this;
        }

        public Builder setThumbCompressedSize(long l) {
            this.mObjectInfo.mThumbCompressedSize = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }

        public Builder setThumbFormat(int n) {
            this.mObjectInfo.mThumbFormat = n;
            return this;
        }

        public Builder setThumbPixHeight(long l) {
            this.mObjectInfo.mThumbPixHeight = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }

        public Builder setThumbPixWidth(long l) {
            this.mObjectInfo.mThumbPixWidth = MtpObjectInfo.longToUint32(l, "value");
            return this;
        }
    }

}

