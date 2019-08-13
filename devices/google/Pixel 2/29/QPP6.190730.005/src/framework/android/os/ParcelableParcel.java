/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;

public class ParcelableParcel
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.ClassLoaderCreator<ParcelableParcel> CREATOR = new Parcelable.ClassLoaderCreator<ParcelableParcel>(){

        @Override
        public ParcelableParcel createFromParcel(Parcel parcel) {
            return new ParcelableParcel(parcel, null);
        }

        @Override
        public ParcelableParcel createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new ParcelableParcel(parcel, classLoader);
        }

        public ParcelableParcel[] newArray(int n) {
            return new ParcelableParcel[n];
        }
    };
    final ClassLoader mClassLoader;
    final Parcel mParcel = Parcel.obtain();

    public ParcelableParcel(Parcel parcel, ClassLoader classLoader) {
        this.mClassLoader = classLoader;
        int n = parcel.readInt();
        if (n >= 0) {
            int n2 = parcel.dataPosition();
            parcel.setDataPosition(MathUtils.addOrThrow(n2, n));
            this.mParcel.appendFrom(parcel, n2, n);
            return;
        }
        throw new IllegalArgumentException("Negative size read from parcel");
    }

    @UnsupportedAppUsage
    public ParcelableParcel(ClassLoader classLoader) {
        this.mClassLoader = classLoader;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    @UnsupportedAppUsage
    public Parcel getParcel() {
        this.mParcel.setDataPosition(0);
        return this.mParcel;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mParcel.dataSize());
        Parcel parcel2 = this.mParcel;
        parcel.appendFrom(parcel2, 0, parcel2.dataSize());
    }

}

