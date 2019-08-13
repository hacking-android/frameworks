/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.lang.reflect.Constructor;

public final class ParcelableException
extends RuntimeException
implements Parcelable {
    public static final Parcelable.Creator<ParcelableException> CREATOR = new Parcelable.Creator<ParcelableException>(){

        @Override
        public ParcelableException createFromParcel(Parcel parcel) {
            return new ParcelableException(ParcelableException.readFromParcel(parcel));
        }

        public ParcelableException[] newArray(int n) {
            return new ParcelableException[n];
        }
    };

    public ParcelableException(Throwable throwable) {
        super(throwable);
    }

    public static Throwable readFromParcel(Parcel object) {
        Serializable serializable;
        String string2 = ((Parcel)object).readString();
        object = ((Parcel)object).readString();
        try {
            serializable = Class.forName(string2, true, Parcelable.class.getClassLoader());
            if (Throwable.class.isAssignableFrom((Class<?>)serializable)) {
                serializable = (Throwable)((Class)serializable).getConstructor(String.class).newInstance(object);
                return serializable;
            }
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            // empty catch block
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append(": ");
        ((StringBuilder)serializable).append((String)object);
        return new RuntimeException(((StringBuilder)serializable).toString());
    }

    public static void writeToParcel(Parcel parcel, Throwable throwable) {
        parcel.writeString(throwable.getClass().getName());
        parcel.writeString(throwable.getMessage());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public <T extends Throwable> void maybeRethrow(Class<T> class_) throws Throwable {
        if (!class_.isAssignableFrom(this.getCause().getClass())) {
            return;
        }
        throw this.getCause();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        ParcelableException.writeToParcel(parcel, this.getCause());
    }

}

