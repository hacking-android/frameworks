/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;

public final class UidRange
implements Parcelable {
    public static final Parcelable.Creator<UidRange> CREATOR = new Parcelable.Creator<UidRange>(){

        @Override
        public UidRange createFromParcel(Parcel parcel) {
            return new UidRange(parcel.readInt(), parcel.readInt());
        }

        public UidRange[] newArray(int n) {
            return new UidRange[n];
        }
    };
    public final int start;
    public final int stop;

    public UidRange(int n, int n2) {
        if (n >= 0) {
            if (n2 >= 0) {
                if (n <= n2) {
                    this.start = n;
                    this.stop = n2;
                    return;
                }
                throw new IllegalArgumentException("Invalid UID range.");
            }
            throw new IllegalArgumentException("Invalid stop UID.");
        }
        throw new IllegalArgumentException("Invalid start UID.");
    }

    public static boolean containsUid(Collection<UidRange> object, int n) {
        if (object == null) {
            return false;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!((UidRange)object.next()).contains(n)) continue;
            return true;
        }
        return false;
    }

    public static UidRange createForUser(int n) {
        return new UidRange(n * 100000, (n + 1) * 100000 - 1);
    }

    public boolean contains(int n) {
        boolean bl = this.start <= n && n <= this.stop;
        return bl;
    }

    public boolean containsRange(UidRange uidRange) {
        boolean bl = this.start <= uidRange.start && uidRange.stop <= this.stop;
        return bl;
    }

    public int count() {
        return this.stop + 1 - this.start;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof UidRange) {
            object = (UidRange)object;
            if (this.start != ((UidRange)object).start || this.stop != ((UidRange)object).stop) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getEndUser() {
        return this.stop / 100000;
    }

    public int getStartUser() {
        return this.start / 100000;
    }

    public int hashCode() {
        return (17 * 31 + this.start) * 31 + this.stop;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.start);
        stringBuilder.append("-");
        stringBuilder.append(this.stop);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.start);
        parcel.writeInt(this.stop);
    }

}

