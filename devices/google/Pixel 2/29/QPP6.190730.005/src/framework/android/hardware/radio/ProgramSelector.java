/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.-$
 *  android.hardware.radio.-$$Lambda
 *  android.hardware.radio.-$$Lambda$ProgramSelector
 *  android.hardware.radio.-$$Lambda$ProgramSelector$kEsOH_p_eN5KvKLjoDTGZXYtuP4
 *  android.hardware.radio.-$$Lambda$ProgramSelector$nFx6NE-itx7YUkyrPxAq5zDeJdQ
 *  android.hardware.radio.-$$Lambda$ProgramSelector$pP-Cu6h7-REdNveY60TFDS4pIKk
 */
package android.hardware.radio;

import android.annotation.SystemApi;
import android.hardware.radio.-$;
import android.hardware.radio._$$Lambda$ProgramSelector$TWK8H6GGx8Rt5rbA87tKag_pCqw;
import android.hardware.radio._$$Lambda$ProgramSelector$kEsOH_p_eN5KvKLjoDTGZXYtuP4;
import android.hardware.radio._$$Lambda$ProgramSelector$nFx6NE_itx7YUkyrPxAq5zDeJdQ;
import android.hardware.radio._$$Lambda$ProgramSelector$pP_Cu6h7_REdNveY60TFDS4pIKk;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SystemApi
public final class ProgramSelector
implements Parcelable {
    public static final Parcelable.Creator<ProgramSelector> CREATOR = new Parcelable.Creator<ProgramSelector>(){

        @Override
        public ProgramSelector createFromParcel(Parcel parcel) {
            return new ProgramSelector(parcel);
        }

        public ProgramSelector[] newArray(int n) {
            return new ProgramSelector[n];
        }
    };
    public static final int IDENTIFIER_TYPE_AMFM_FREQUENCY = 1;
    public static final int IDENTIFIER_TYPE_DAB_ENSEMBLE = 6;
    public static final int IDENTIFIER_TYPE_DAB_FREQUENCY = 8;
    public static final int IDENTIFIER_TYPE_DAB_SCID = 7;
    public static final int IDENTIFIER_TYPE_DAB_SIDECC = 5;
    public static final int IDENTIFIER_TYPE_DAB_SID_EXT = 5;
    public static final int IDENTIFIER_TYPE_DRMO_FREQUENCY = 10;
    @Deprecated
    public static final int IDENTIFIER_TYPE_DRMO_MODULATION = 11;
    public static final int IDENTIFIER_TYPE_DRMO_SERVICE_ID = 9;
    public static final int IDENTIFIER_TYPE_HD_STATION_ID_EXT = 3;
    public static final int IDENTIFIER_TYPE_HD_STATION_NAME = 10004;
    @Deprecated
    public static final int IDENTIFIER_TYPE_HD_SUBCHANNEL = 4;
    public static final int IDENTIFIER_TYPE_INVALID = 0;
    public static final int IDENTIFIER_TYPE_RDS_PI = 2;
    public static final int IDENTIFIER_TYPE_SXM_CHANNEL = 13;
    public static final int IDENTIFIER_TYPE_SXM_SERVICE_ID = 12;
    public static final int IDENTIFIER_TYPE_VENDOR_END = 1999;
    @Deprecated
    public static final int IDENTIFIER_TYPE_VENDOR_PRIMARY_END = 1999;
    @Deprecated
    public static final int IDENTIFIER_TYPE_VENDOR_PRIMARY_START = 1000;
    public static final int IDENTIFIER_TYPE_VENDOR_START = 1000;
    @Deprecated
    public static final int PROGRAM_TYPE_AM = 1;
    @Deprecated
    public static final int PROGRAM_TYPE_AM_HD = 3;
    @Deprecated
    public static final int PROGRAM_TYPE_DAB = 5;
    @Deprecated
    public static final int PROGRAM_TYPE_DRMO = 6;
    @Deprecated
    public static final int PROGRAM_TYPE_FM = 2;
    @Deprecated
    public static final int PROGRAM_TYPE_FM_HD = 4;
    @Deprecated
    public static final int PROGRAM_TYPE_INVALID = 0;
    @Deprecated
    public static final int PROGRAM_TYPE_SXM = 7;
    @Deprecated
    public static final int PROGRAM_TYPE_VENDOR_END = 1999;
    @Deprecated
    public static final int PROGRAM_TYPE_VENDOR_START = 1000;
    private final Identifier mPrimaryId;
    private final int mProgramType;
    private final Identifier[] mSecondaryIds;
    private final long[] mVendorIds;

    public ProgramSelector(int n, Identifier identifier, Identifier[] arrobject, long[] arrl) {
        Identifier[] arridentifier = arrobject;
        if (arrobject == null) {
            arridentifier = new Identifier[]{};
        }
        arrobject = arrl;
        if (arrl == null) {
            arrobject = new long[]{};
        }
        if (!Stream.of(arridentifier).anyMatch((Predicate<Identifier>)_$$Lambda$ProgramSelector$pP_Cu6h7_REdNveY60TFDS4pIKk.INSTANCE)) {
            this.mProgramType = n;
            this.mPrimaryId = Objects.requireNonNull(identifier);
            this.mSecondaryIds = arridentifier;
            this.mVendorIds = arrobject;
            return;
        }
        throw new IllegalArgumentException("secondaryIds list must not contain nulls");
    }

    private ProgramSelector(Parcel parcel) {
        this.mProgramType = parcel.readInt();
        this.mPrimaryId = parcel.readTypedObject(Identifier.CREATOR);
        this.mSecondaryIds = parcel.createTypedArray(Identifier.CREATOR);
        if (!Stream.of(this.mSecondaryIds).anyMatch((Predicate<Identifier>)_$$Lambda$ProgramSelector$nFx6NE_itx7YUkyrPxAq5zDeJdQ.INSTANCE)) {
            this.mVendorIds = parcel.createLongArray();
            return;
        }
        throw new IllegalArgumentException("secondaryIds list must not contain nulls");
    }

    public static ProgramSelector createAmFmSelector(int n, int n2) {
        return ProgramSelector.createAmFmSelector(n, n2, 0);
    }

    public static ProgramSelector createAmFmSelector(int n, int n2, int n3) {
        int n4 = 2;
        int n5 = n;
        if (n == -1) {
            if (n2 < 50000) {
                n = n3 <= 0 ? 0 : 3;
                n5 = n;
            } else {
                n = n3 <= 0 ? 1 : 2;
                n5 = n;
            }
        }
        boolean bl = n5 == 0 || n5 == 3;
        n = n5 != 3 && n5 != 2 ? 0 : 1;
        if (!bl && n == 0 && n5 != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown band: ");
            stringBuilder.append(n5);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n3 >= 0 && n3 <= 8) {
            if (n3 > 0 && n == 0) {
                throw new IllegalArgumentException("Subchannels are not supported for non-HD radio");
            }
            if (ProgramSelector.isValidAmFmFrequency(bl, n2)) {
                n = n4;
                if (bl) {
                    n = 1;
                }
                Identifier identifier = new Identifier(1, n2);
                Identifier[] arridentifier = null;
                if (n3 > 0) {
                    arridentifier = new Identifier[]{new Identifier(4, n3 - 1)};
                }
                return new ProgramSelector(n, identifier, arridentifier, null);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided value is not a valid AM/FM frequency: ");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid subchannel: ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static boolean isValidAmFmFrequency(boolean bl, int n) {
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl = n > 150 && n <= 30000 ? bl3 : false;
            return bl;
        }
        bl = n > 60000 && n < 110000 ? bl2 : false;
        return bl;
    }

    static /* synthetic */ boolean lambda$new$0(Identifier identifier) {
        boolean bl = identifier == null;
        return bl;
    }

    static /* synthetic */ boolean lambda$new$3(Identifier identifier) {
        boolean bl = identifier == null;
        return bl;
    }

    static /* synthetic */ boolean lambda$withSecondaryPreferred$1(int n, Identifier identifier) {
        boolean bl = identifier.getType() != n;
        return bl;
    }

    static /* synthetic */ Identifier[] lambda$withSecondaryPreferred$2(int n) {
        return new Identifier[n];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProgramSelector)) {
            return false;
        }
        object = (ProgramSelector)object;
        return this.mPrimaryId.equals(((ProgramSelector)object).getPrimaryId());
    }

    public Identifier[] getAllIds(int n) {
        ArrayList<Identifier> arrayList = new ArrayList<Identifier>();
        if (this.mPrimaryId.getType() == n) {
            arrayList.add(this.mPrimaryId);
        }
        for (Identifier identifier : this.mSecondaryIds) {
            if (identifier.getType() != n) continue;
            arrayList.add(identifier);
        }
        return arrayList.toArray(new Identifier[arrayList.size()]);
    }

    public long getFirstId(int n) {
        if (this.mPrimaryId.getType() == n) {
            return this.mPrimaryId.getValue();
        }
        for (Identifier identifier : this.mSecondaryIds) {
            if (identifier.getType() != n) continue;
            return identifier.getValue();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Identifier ");
        stringBuilder.append(n);
        stringBuilder.append(" not found");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Identifier getPrimaryId() {
        return this.mPrimaryId;
    }

    @Deprecated
    public int getProgramType() {
        return this.mProgramType;
    }

    public Identifier[] getSecondaryIds() {
        return this.mSecondaryIds;
    }

    @Deprecated
    public long[] getVendorIds() {
        return this.mVendorIds;
    }

    public int hashCode() {
        return this.mPrimaryId.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ProgramSelector(type=");
        stringBuilder.append(this.mProgramType);
        stringBuilder.append(", primary=");
        stringBuilder = stringBuilder.append(this.mPrimaryId);
        if (this.mSecondaryIds.length > 0) {
            stringBuilder.append(", secondary=");
            stringBuilder.append(this.mSecondaryIds);
        }
        if (this.mVendorIds.length > 0) {
            stringBuilder.append(", vendor=");
            stringBuilder.append(this.mVendorIds);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public ProgramSelector withSecondaryPreferred(Identifier arridentifier) {
        int n = arridentifier.getType();
        arridentifier = (Identifier[])Stream.concat(Arrays.stream(this.mSecondaryIds).filter(new _$$Lambda$ProgramSelector$TWK8H6GGx8Rt5rbA87tKag_pCqw(n)), Stream.of(arridentifier)).toArray((IntFunction<A[]>)_$$Lambda$ProgramSelector$kEsOH_p_eN5KvKLjoDTGZXYtuP4.INSTANCE);
        return new ProgramSelector(this.mProgramType, this.mPrimaryId, arridentifier, this.mVendorIds);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mProgramType);
        parcel.writeTypedObject(this.mPrimaryId, 0);
        parcel.writeTypedArray((Parcelable[])this.mSecondaryIds, 0);
        parcel.writeLongArray(this.mVendorIds);
    }

    public static final class Identifier
    implements Parcelable {
        public static final Parcelable.Creator<Identifier> CREATOR = new Parcelable.Creator<Identifier>(){

            @Override
            public Identifier createFromParcel(Parcel parcel) {
                return new Identifier(parcel);
            }

            public Identifier[] newArray(int n) {
                return new Identifier[n];
            }
        };
        private final int mType;
        private final long mValue;

        public Identifier(int n, long l) {
            int n2 = n;
            if (n == 10004) {
                n2 = 4;
            }
            this.mType = n2;
            this.mValue = l;
        }

        private Identifier(Parcel parcel) {
            this.mType = parcel.readInt();
            this.mValue = parcel.readLong();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Identifier)) {
                return false;
            }
            if (((Identifier)(object = (Identifier)object)).getType() != this.mType || ((Identifier)object).getValue() != this.mValue) {
                bl = false;
            }
            return bl;
        }

        public int getType() {
            if (this.mType == 4 && this.mValue > 10L) {
                return 10004;
            }
            return this.mType;
        }

        public long getValue() {
            return this.mValue;
        }

        public int hashCode() {
            return Objects.hash(this.mType, this.mValue);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Identifier(");
            stringBuilder.append(this.mType);
            stringBuilder.append(", ");
            stringBuilder.append(this.mValue);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mType);
            parcel.writeLong(this.mValue);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IdentifierType {
    }

    @Deprecated
    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProgramType {
    }

}

