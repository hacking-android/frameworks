/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Parcel;
import android.os.Parcelable;

public final class PageRange
implements Parcelable {
    public static final PageRange ALL_PAGES = new PageRange(0, Integer.MAX_VALUE);
    public static final PageRange[] ALL_PAGES_ARRAY = new PageRange[]{ALL_PAGES};
    public static final Parcelable.Creator<PageRange> CREATOR = new Parcelable.Creator<PageRange>(){

        @Override
        public PageRange createFromParcel(Parcel parcel) {
            return new PageRange(parcel);
        }

        public PageRange[] newArray(int n) {
            return new PageRange[n];
        }
    };
    private final int mEnd;
    private final int mStart;

    public PageRange(int n, int n2) {
        if (n >= 0) {
            if (n2 >= 0) {
                if (n <= n2) {
                    this.mStart = n;
                    this.mEnd = n2;
                    return;
                }
                throw new IllegalArgumentException("start must be lesser than end.");
            }
            throw new IllegalArgumentException("end cannot be less than zero.");
        }
        throw new IllegalArgumentException("start cannot be less than zero.");
    }

    private PageRange(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt());
    }

    public boolean contains(int n) {
        boolean bl = n >= this.mStart && n <= this.mEnd;
        return bl;
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
        object = (PageRange)object;
        if (this.mEnd != ((PageRange)object).mEnd) {
            return false;
        }
        return this.mStart == ((PageRange)object).mStart;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public int getSize() {
        return this.mEnd - this.mStart + 1;
    }

    public int getStart() {
        return this.mStart;
    }

    public int hashCode() {
        return (1 * 31 + this.mEnd) * 31 + this.mStart;
    }

    public String toString() {
        if (this.mStart == 0 && this.mEnd == Integer.MAX_VALUE) {
            return "PageRange[<all pages>]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PageRange[");
        stringBuilder.append(this.mStart);
        stringBuilder.append(" - ");
        stringBuilder.append(this.mEnd);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mStart);
        parcel.writeInt(this.mEnd);
    }

}

