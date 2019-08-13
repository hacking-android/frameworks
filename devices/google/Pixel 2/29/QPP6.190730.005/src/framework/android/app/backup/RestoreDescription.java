/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class RestoreDescription
implements Parcelable {
    public static final Parcelable.Creator<RestoreDescription> CREATOR;
    public static final RestoreDescription NO_MORE_PACKAGES;
    private static final String NO_MORE_PACKAGES_SENTINEL = "NO_MORE_PACKAGES";
    public static final int TYPE_FULL_STREAM = 2;
    public static final int TYPE_KEY_VALUE = 1;
    private final int mDataType;
    private final String mPackageName;

    static {
        NO_MORE_PACKAGES = new RestoreDescription(NO_MORE_PACKAGES_SENTINEL, 0);
        CREATOR = new Parcelable.Creator<RestoreDescription>(){

            @Override
            public RestoreDescription createFromParcel(Parcel object) {
                block0 : {
                    if (!RestoreDescription.NO_MORE_PACKAGES_SENTINEL.equals(((RestoreDescription)(object = new RestoreDescription((Parcel)object))).mPackageName)) break block0;
                    object = NO_MORE_PACKAGES;
                }
                return object;
            }

            public RestoreDescription[] newArray(int n) {
                return new RestoreDescription[n];
            }
        };
    }

    private RestoreDescription(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mDataType = parcel.readInt();
    }

    public RestoreDescription(String string2, int n) {
        this.mPackageName = string2;
        this.mDataType = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getDataType() {
        return this.mDataType;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RestoreDescription{");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append(" : ");
        String string2 = this.mDataType == 1 ? "KEY_VALUE" : "STREAM";
        stringBuilder.append(string2);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mDataType);
    }

}

