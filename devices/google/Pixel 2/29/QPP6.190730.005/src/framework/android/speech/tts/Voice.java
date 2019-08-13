/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Voice
implements Parcelable {
    public static final Parcelable.Creator<Voice> CREATOR = new Parcelable.Creator<Voice>(){

        @Override
        public Voice createFromParcel(Parcel parcel) {
            return new Voice(parcel);
        }

        public Voice[] newArray(int n) {
            return new Voice[n];
        }
    };
    public static final int LATENCY_HIGH = 400;
    public static final int LATENCY_LOW = 200;
    public static final int LATENCY_NORMAL = 300;
    public static final int LATENCY_VERY_HIGH = 500;
    public static final int LATENCY_VERY_LOW = 100;
    public static final int QUALITY_HIGH = 400;
    public static final int QUALITY_LOW = 200;
    public static final int QUALITY_NORMAL = 300;
    public static final int QUALITY_VERY_HIGH = 500;
    public static final int QUALITY_VERY_LOW = 100;
    private final Set<String> mFeatures;
    private final int mLatency;
    private final Locale mLocale;
    private final String mName;
    private final int mQuality;
    private final boolean mRequiresNetworkConnection;

    private Voice(Parcel parcel) {
        this.mName = parcel.readString();
        this.mLocale = (Locale)parcel.readSerializable();
        this.mQuality = parcel.readInt();
        this.mLatency = parcel.readInt();
        byte by = parcel.readByte();
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        this.mRequiresNetworkConnection = bl;
        this.mFeatures = new HashSet<String>();
        Collections.addAll(this.mFeatures, parcel.readStringArray());
    }

    public Voice(String string2, Locale locale, int n, int n2, boolean bl, Set<String> set) {
        this.mName = string2;
        this.mLocale = locale;
        this.mQuality = n;
        this.mLatency = n2;
        this.mRequiresNetworkConnection = bl;
        this.mFeatures = set;
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
        object = (Voice)object;
        Object object2 = this.mFeatures;
        if (object2 == null ? ((Voice)object).mFeatures != null : !object2.equals(((Voice)object).mFeatures)) {
            return false;
        }
        if (this.mLatency != ((Voice)object).mLatency) {
            return false;
        }
        object2 = this.mLocale;
        if (object2 == null ? ((Voice)object).mLocale != null : !((Locale)object2).equals(((Voice)object).mLocale)) {
            return false;
        }
        object2 = this.mName;
        if (object2 == null ? ((Voice)object).mName != null : !((String)object2).equals(((Voice)object).mName)) {
            return false;
        }
        if (this.mQuality != ((Voice)object).mQuality) {
            return false;
        }
        return this.mRequiresNetworkConnection == ((Voice)object).mRequiresNetworkConnection;
    }

    public Set<String> getFeatures() {
        return this.mFeatures;
    }

    public int getLatency() {
        return this.mLatency;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public String getName() {
        return this.mName;
    }

    public int getQuality() {
        return this.mQuality;
    }

    public int hashCode() {
        Object object = this.mFeatures;
        int n = 0;
        int n2 = object == null ? 0 : object.hashCode();
        int n3 = this.mLatency;
        object = this.mLocale;
        int n4 = object == null ? 0 : ((Locale)object).hashCode();
        object = this.mName;
        if (object != null) {
            n = ((String)object).hashCode();
        }
        int n5 = this.mQuality;
        int n6 = this.mRequiresNetworkConnection ? 1231 : 1237;
        return (((((1 * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n) * 31 + n5) * 31 + n6;
    }

    public boolean isNetworkConnectionRequired() {
        return this.mRequiresNetworkConnection;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append("Voice[Name: ");
        stringBuilder.append(this.mName);
        stringBuilder.append(", locale: ");
        stringBuilder.append(this.mLocale);
        stringBuilder.append(", quality: ");
        stringBuilder.append(this.mQuality);
        stringBuilder.append(", latency: ");
        stringBuilder.append(this.mLatency);
        stringBuilder.append(", requiresNetwork: ");
        stringBuilder.append(this.mRequiresNetworkConnection);
        stringBuilder.append(", features: ");
        stringBuilder.append(this.mFeatures.toString());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeSerializable(this.mLocale);
        parcel.writeInt(this.mQuality);
        parcel.writeInt(this.mLatency);
        parcel.writeByte((byte)(this.mRequiresNetworkConnection ? 1 : 0));
        parcel.writeStringList(new ArrayList<String>(this.mFeatures));
    }

}

