/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.internal.util.MessageUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.BitSet;

@SystemApi
public final class ApfProgramEvent
implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<ApfProgramEvent> CREATOR = new Parcelable.Creator<ApfProgramEvent>(){

        @Override
        public ApfProgramEvent createFromParcel(Parcel parcel) {
            return new ApfProgramEvent(parcel);
        }

        public ApfProgramEvent[] newArray(int n) {
            return new ApfProgramEvent[n];
        }
    };
    public static final int FLAG_HAS_IPV4_ADDRESS = 1;
    public static final int FLAG_MULTICAST_FILTER_ON = 0;
    @UnsupportedAppUsage
    public final long actualLifetime;
    @UnsupportedAppUsage
    public final int currentRas;
    @UnsupportedAppUsage
    public final int filteredRas;
    @UnsupportedAppUsage
    public final int flags;
    @UnsupportedAppUsage
    public final long lifetime;
    @UnsupportedAppUsage
    public final int programLength;

    private ApfProgramEvent(long l, long l2, int n, int n2, int n3, int n4) {
        this.lifetime = l;
        this.actualLifetime = l2;
        this.filteredRas = n;
        this.currentRas = n2;
        this.programLength = n3;
        this.flags = n4;
    }

    private ApfProgramEvent(Parcel parcel) {
        this.lifetime = parcel.readLong();
        this.actualLifetime = parcel.readLong();
        this.filteredRas = parcel.readInt();
        this.currentRas = parcel.readInt();
        this.programLength = parcel.readInt();
        this.flags = parcel.readInt();
    }

    @UnsupportedAppUsage
    public static int flagsFor(boolean bl, boolean bl2) {
        int n = 0;
        if (bl) {
            n = 0 | 2;
        }
        int n2 = n;
        if (bl2) {
            n2 = n | 1;
        }
        return n2;
    }

    private static String namesOf(int n) {
        ArrayList<String> arrayList = new ArrayList<String>(Integer.bitCount(n));
        BitSet bitSet = BitSet.valueOf(new long[]{Integer.MAX_VALUE & n});
        n = bitSet.nextSetBit(0);
        while (n >= 0) {
            arrayList.add(Decoder.constants.get(n));
            n = bitSet.nextSetBit(n + 1);
        }
        return TextUtils.join((CharSequence)"|", arrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(ApfProgramEvent.class)) {
            object = (ApfProgramEvent)object;
            boolean bl2 = bl;
            if (this.lifetime == ((ApfProgramEvent)object).lifetime) {
                bl2 = bl;
                if (this.actualLifetime == ((ApfProgramEvent)object).actualLifetime) {
                    bl2 = bl;
                    if (this.filteredRas == ((ApfProgramEvent)object).filteredRas) {
                        bl2 = bl;
                        if (this.currentRas == ((ApfProgramEvent)object).currentRas) {
                            bl2 = bl;
                            if (this.programLength == ((ApfProgramEvent)object).programLength) {
                                bl2 = bl;
                                if (this.flags == ((ApfProgramEvent)object).flags) {
                                    bl2 = true;
                                }
                            }
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        CharSequence charSequence;
        if (this.lifetime < Long.MAX_VALUE) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.lifetime);
            ((StringBuilder)charSequence).append("s");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "forever";
        }
        return String.format("ApfProgramEvent(%d/%d RAs %dB %ds/%s %s)", this.filteredRas, this.currentRas, this.programLength, this.actualLifetime, charSequence, ApfProgramEvent.namesOf(this.flags));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.lifetime);
        parcel.writeLong(this.actualLifetime);
        parcel.writeInt(this.filteredRas);
        parcel.writeInt(this.currentRas);
        parcel.writeInt(this.programLength);
        parcel.writeInt(this.flags);
    }

    public static final class Builder {
        private long mActualLifetime;
        private int mCurrentRas;
        private int mFilteredRas;
        private int mFlags;
        private long mLifetime;
        private int mProgramLength;

        public ApfProgramEvent build() {
            return new ApfProgramEvent(this.mLifetime, this.mActualLifetime, this.mFilteredRas, this.mCurrentRas, this.mProgramLength, this.mFlags);
        }

        public Builder setActualLifetime(long l) {
            this.mActualLifetime = l;
            return this;
        }

        public Builder setCurrentRas(int n) {
            this.mCurrentRas = n;
            return this;
        }

        public Builder setFilteredRas(int n) {
            this.mFilteredRas = n;
            return this;
        }

        public Builder setFlags(boolean bl, boolean bl2) {
            this.mFlags = ApfProgramEvent.flagsFor(bl, bl2);
            return this;
        }

        public Builder setLifetime(long l) {
            this.mLifetime = l;
            return this;
        }

        public Builder setProgramLength(int n) {
            this.mProgramLength = n;
            return this;
        }
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{ApfProgramEvent.class}, new String[]{"FLAG_"});

        Decoder() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Flags {
    }

}

