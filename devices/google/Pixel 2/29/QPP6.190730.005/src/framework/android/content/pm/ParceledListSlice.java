/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.BaseParceledListSlice;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collections;
import java.util.List;

public class ParceledListSlice<T extends Parcelable>
extends BaseParceledListSlice<T> {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final Parcelable.ClassLoaderCreator<ParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<ParceledListSlice>(){

        @Override
        public ParceledListSlice createFromParcel(Parcel parcel) {
            return new ParceledListSlice(parcel, null);
        }

        @Override
        public ParceledListSlice createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new ParceledListSlice(parcel, classLoader);
        }

        public ParceledListSlice[] newArray(int n) {
            return new ParceledListSlice[n];
        }
    };

    private ParceledListSlice(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    @UnsupportedAppUsage
    public ParceledListSlice(List<T> list) {
        super(list);
    }

    public static <T extends Parcelable> ParceledListSlice<T> emptyList() {
        return new ParceledListSlice(Collections.emptyList());
    }

    @Override
    public int describeContents() {
        int n = 0;
        List list = this.getList();
        for (int i = 0; i < list.size(); ++i) {
            n |= ((Parcelable)list.get(i)).describeContents();
        }
        return n;
    }

    @Override
    protected Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader) {
        return parcel.readParcelableCreator(classLoader);
    }

    @Override
    protected void writeElement(T t, Parcel parcel, int n) {
        t.writeToParcel(parcel, n);
    }

    @UnsupportedAppUsage
    @Override
    protected void writeParcelableCreator(T t, Parcel parcel) {
        parcel.writeParcelableCreator((Parcelable)t);
    }

}

