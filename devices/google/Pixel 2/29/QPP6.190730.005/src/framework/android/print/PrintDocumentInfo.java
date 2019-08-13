/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PrintDocumentInfo
implements Parcelable {
    public static final int CONTENT_TYPE_DOCUMENT = 0;
    public static final int CONTENT_TYPE_PHOTO = 1;
    public static final int CONTENT_TYPE_UNKNOWN = -1;
    public static final Parcelable.Creator<PrintDocumentInfo> CREATOR = new Parcelable.Creator<PrintDocumentInfo>(){

        @Override
        public PrintDocumentInfo createFromParcel(Parcel parcel) {
            return new PrintDocumentInfo(parcel);
        }

        public PrintDocumentInfo[] newArray(int n) {
            return new PrintDocumentInfo[n];
        }
    };
    public static final int PAGE_COUNT_UNKNOWN = -1;
    private int mContentType;
    private long mDataSize;
    private String mName;
    private int mPageCount;

    private PrintDocumentInfo() {
    }

    private PrintDocumentInfo(Parcel parcel) {
        this.mName = Preconditions.checkStringNotEmpty(parcel.readString());
        int n = this.mPageCount = parcel.readInt();
        boolean bl = n == -1 || n > 0;
        Preconditions.checkArgument(bl);
        this.mContentType = parcel.readInt();
        this.mDataSize = Preconditions.checkArgumentNonnegative(parcel.readLong());
    }

    private PrintDocumentInfo(PrintDocumentInfo printDocumentInfo) {
        this.mName = printDocumentInfo.mName;
        this.mPageCount = printDocumentInfo.mPageCount;
        this.mContentType = printDocumentInfo.mContentType;
        this.mDataSize = printDocumentInfo.mDataSize;
    }

    private String contentTypeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                return "CONTENT_TYPE_UNKNOWN";
            }
            return "CONTENT_TYPE_PHOTO";
        }
        return "CONTENT_TYPE_DOCUMENT";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (PrintDocumentInfo)object;
        if (!TextUtils.equals(this.mName, ((PrintDocumentInfo)object).mName)) {
            return false;
        }
        if (this.mContentType != ((PrintDocumentInfo)object).mContentType) {
            return false;
        }
        if (this.mPageCount != ((PrintDocumentInfo)object).mPageCount) {
            return false;
        }
        return this.mDataSize == ((PrintDocumentInfo)object).mDataSize;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public long getDataSize() {
        return this.mDataSize;
    }

    public String getName() {
        return this.mName;
    }

    public int getPageCount() {
        return this.mPageCount;
    }

    public int hashCode() {
        String string2 = this.mName;
        int n = string2 != null ? string2.hashCode() : 0;
        int n2 = this.mContentType;
        int n3 = this.mPageCount;
        long l = this.mDataSize;
        return ((((1 * 31 + n) * 31 + n2) * 31 + n3) * 31 + (int)l) * 31 + (int)(l >> 32);
    }

    public void setDataSize(long l) {
        this.mDataSize = l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PrintDocumentInfo{");
        stringBuilder.append("name=");
        stringBuilder.append(this.mName);
        stringBuilder.append(", pageCount=");
        stringBuilder.append(this.mPageCount);
        stringBuilder.append(", contentType=");
        stringBuilder.append(this.contentTypeToString(this.mContentType));
        stringBuilder.append(", dataSize=");
        stringBuilder.append(this.mDataSize);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mPageCount);
        parcel.writeInt(this.mContentType);
        parcel.writeLong(this.mDataSize);
    }

    public static final class Builder {
        private final PrintDocumentInfo mPrototype;

        public Builder(String string2) {
            if (!TextUtils.isEmpty(string2)) {
                this.mPrototype = new PrintDocumentInfo();
                this.mPrototype.mName = string2;
                return;
            }
            throw new IllegalArgumentException("name cannot be empty");
        }

        public PrintDocumentInfo build() {
            if (this.mPrototype.mPageCount == 0) {
                this.mPrototype.mPageCount = -1;
            }
            return new PrintDocumentInfo(this.mPrototype);
        }

        public Builder setContentType(int n) {
            this.mPrototype.mContentType = n;
            return this;
        }

        public Builder setPageCount(int n) {
            if (n < 0 && n != -1) {
                throw new IllegalArgumentException("pageCount must be greater than or equal to zero or DocumentInfo#PAGE_COUNT_UNKNOWN");
            }
            this.mPrototype.mPageCount = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ContentType {
    }

}

