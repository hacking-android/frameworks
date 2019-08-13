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

public class StringParceledListSlice
extends BaseParceledListSlice<String> {
    public static final Parcelable.ClassLoaderCreator<StringParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<StringParceledListSlice>(){

        @Override
        public StringParceledListSlice createFromParcel(Parcel parcel) {
            return new StringParceledListSlice(parcel, null);
        }

        @Override
        public StringParceledListSlice createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new StringParceledListSlice(parcel, classLoader);
        }

        public StringParceledListSlice[] newArray(int n) {
            return new StringParceledListSlice[n];
        }
    };

    private StringParceledListSlice(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    public StringParceledListSlice(List<String> list) {
        super(list);
    }

    public static StringParceledListSlice emptyList() {
        return new StringParceledListSlice(Collections.<String>emptyList());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    protected Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader) {
        return Parcel.STRING_CREATOR;
    }

    @Override
    protected void writeElement(String string2, Parcel parcel, int n) {
        parcel.writeString(string2);
    }

    @Override
    protected void writeParcelableCreator(String string2, Parcel parcel) {
    }

}

