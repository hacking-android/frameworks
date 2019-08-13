/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.annotation.SystemApi;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.Utils;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@SystemApi
public final class Announcement
implements Parcelable {
    public static final Parcelable.Creator<Announcement> CREATOR = new Parcelable.Creator<Announcement>(){

        @Override
        public Announcement createFromParcel(Parcel parcel) {
            return new Announcement(parcel);
        }

        public Announcement[] newArray(int n) {
            return new Announcement[n];
        }
    };
    public static final int TYPE_EMERGENCY = 1;
    public static final int TYPE_EVENT = 6;
    public static final int TYPE_MISC = 8;
    public static final int TYPE_NEWS = 5;
    public static final int TYPE_SPORT = 7;
    public static final int TYPE_TRAFFIC = 3;
    public static final int TYPE_WARNING = 2;
    public static final int TYPE_WEATHER = 4;
    private final ProgramSelector mSelector;
    private final int mType;
    private final Map<String, String> mVendorInfo;

    public Announcement(ProgramSelector programSelector, int n, Map<String, String> map) {
        this.mSelector = Objects.requireNonNull(programSelector);
        this.mType = Objects.requireNonNull(n);
        this.mVendorInfo = Objects.requireNonNull(map);
    }

    private Announcement(Parcel parcel) {
        this.mSelector = parcel.readTypedObject(ProgramSelector.CREATOR);
        this.mType = parcel.readInt();
        this.mVendorInfo = Utils.readStringMap(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ProgramSelector getSelector() {
        return this.mSelector;
    }

    public int getType() {
        return this.mType;
    }

    public Map<String, String> getVendorInfo() {
        return this.mVendorInfo;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.mSelector, 0);
        parcel.writeInt(this.mType);
        Utils.writeStringMap(parcel, this.mVendorInfo);
    }

    public static interface OnListUpdatedListener {
        public void onListUpdated(Collection<Announcement> var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

