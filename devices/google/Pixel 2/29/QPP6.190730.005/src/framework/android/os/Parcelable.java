/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Parcelable {
    public static final int CONTENTS_FILE_DESCRIPTOR = 1;
    public static final int PARCELABLE_ELIDE_DUPLICATES = 2;
    public static final int PARCELABLE_WRITE_RETURN_VALUE = 1;

    public int describeContents();

    public void writeToParcel(Parcel var1, int var2);

    public static interface ClassLoaderCreator<T>
    extends Creator<T> {
        public T createFromParcel(Parcel var1, ClassLoader var2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ContentsFlags {
    }

    public static interface Creator<T> {
        public T createFromParcel(Parcel var1);

        public T[] newArray(int var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WriteFlags {
    }

}

