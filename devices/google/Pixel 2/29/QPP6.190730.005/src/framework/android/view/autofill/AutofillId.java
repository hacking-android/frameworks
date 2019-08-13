/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.os.Parcel;
import android.os.Parcelable;

public final class AutofillId
implements Parcelable {
    public static final Parcelable.Creator<AutofillId> CREATOR = new Parcelable.Creator<AutofillId>(){

        @Override
        public AutofillId createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            int n3 = (n2 & 4) != 0 ? parcel.readInt() : 0;
            if ((n2 & 1) != 0) {
                return new AutofillId(n2, n, parcel.readInt(), n3);
            }
            if ((n2 & 2) != 0) {
                return new AutofillId(n2, n, parcel.readLong(), n3);
            }
            return new AutofillId(n2, n, -1L, n3);
        }

        public AutofillId[] newArray(int n) {
            return new AutofillId[n];
        }
    };
    private static final int FLAG_HAS_SESSION = 4;
    private static final int FLAG_IS_VIRTUAL_INT = 1;
    private static final int FLAG_IS_VIRTUAL_LONG = 2;
    public static final int NO_SESSION = 0;
    private int mFlags;
    private int mSessionId;
    private final int mViewId;
    private final int mVirtualIntId;
    private final long mVirtualLongId;

    public AutofillId(int n) {
        this(0, n, -1L, 0);
    }

    public AutofillId(int n, int n2) {
        this(1, n, n2, 0);
    }

    private AutofillId(int n, int n2, long l, int n3) {
        this.mFlags = n;
        this.mViewId = n2;
        n2 = (n & 1) != 0 ? (int)l : -1;
        this.mVirtualIntId = n2;
        if ((n & 2) == 0) {
            l = -1L;
        }
        this.mVirtualLongId = l;
        this.mSessionId = n3;
    }

    public AutofillId(AutofillId autofillId, int n) {
        this(1, autofillId.mViewId, n, 0);
    }

    public AutofillId(AutofillId autofillId, long l, int n) {
        this(6, autofillId.mViewId, l, n);
    }

    public static AutofillId withoutSession(AutofillId autofillId) {
        return new AutofillId(autofillId.mFlags & -5, autofillId.mViewId, autofillId.mVirtualLongId, 0);
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
        object = (AutofillId)object;
        if (this.mViewId != ((AutofillId)object).mViewId) {
            return false;
        }
        if (this.mVirtualIntId != ((AutofillId)object).mVirtualIntId) {
            return false;
        }
        if (this.mVirtualLongId != ((AutofillId)object).mVirtualLongId) {
            return false;
        }
        return this.mSessionId == ((AutofillId)object).mSessionId;
    }

    public boolean equalsIgnoreSession(AutofillId autofillId) {
        if (this == autofillId) {
            return true;
        }
        if (autofillId == null) {
            return false;
        }
        if (this.mViewId != autofillId.mViewId) {
            return false;
        }
        if (this.mVirtualIntId != autofillId.mVirtualIntId) {
            return false;
        }
        return this.mVirtualLongId == autofillId.mVirtualLongId;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getViewId() {
        return this.mViewId;
    }

    public int getVirtualChildIntId() {
        return this.mVirtualIntId;
    }

    public long getVirtualChildLongId() {
        return this.mVirtualLongId;
    }

    public boolean hasSession() {
        boolean bl = (this.mFlags & 4) != 0;
        return bl;
    }

    public int hashCode() {
        int n = this.mViewId;
        int n2 = this.mVirtualIntId;
        long l = this.mVirtualLongId;
        return (((1 * 31 + n) * 31 + n2) * 31 + (int)(l ^ l >>> 32)) * 31 + this.mSessionId;
    }

    public boolean isNonVirtual() {
        boolean bl = !this.isVirtualInt() && !this.isVirtualLong();
        return bl;
    }

    public boolean isVirtualInt() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isVirtualLong() {
        boolean bl = (this.mFlags & 2) != 0;
        return bl;
    }

    public void resetSessionId() {
        this.mFlags &= -5;
        this.mSessionId = 0;
    }

    public void setSessionId(int n) {
        this.mFlags |= 4;
        this.mSessionId = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(this.mViewId);
        if (this.isVirtualInt()) {
            stringBuilder.append(':');
            stringBuilder.append(this.mVirtualIntId);
        } else if (this.isVirtualLong()) {
            stringBuilder.append(':');
            stringBuilder.append(this.mVirtualLongId);
        }
        if (this.hasSession()) {
            stringBuilder.append('@');
            stringBuilder.append(this.mSessionId);
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mViewId);
        parcel.writeInt(this.mFlags);
        if (this.hasSession()) {
            parcel.writeInt(this.mSessionId);
        }
        if (this.isVirtualInt()) {
            parcel.writeInt(this.mVirtualIntId);
        } else if (this.isVirtualLong()) {
            parcel.writeLong(this.mVirtualLongId);
        }
    }

}

