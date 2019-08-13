/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.storage.VolumeInfo;
import android.util.DebugUtils;
import android.util.TimeUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.util.Locale;
import java.util.Objects;

public class VolumeRecord
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<VolumeRecord> CREATOR = new Parcelable.Creator<VolumeRecord>(){

        @Override
        public VolumeRecord createFromParcel(Parcel parcel) {
            return new VolumeRecord(parcel);
        }

        public VolumeRecord[] newArray(int n) {
            return new VolumeRecord[n];
        }
    };
    public static final String EXTRA_FS_UUID = "android.os.storage.extra.FS_UUID";
    public static final int USER_FLAG_INITED = 1;
    public static final int USER_FLAG_SNOOZED = 2;
    public long createdMillis;
    public final String fsUuid;
    public long lastBenchMillis;
    public long lastSeenMillis;
    public long lastTrimMillis;
    public String nickname;
    public String partGuid;
    public final int type;
    public int userFlags;

    public VolumeRecord(int n, String string2) {
        this.type = n;
        this.fsUuid = Preconditions.checkNotNull(string2);
    }

    @UnsupportedAppUsage
    public VolumeRecord(Parcel parcel) {
        this.type = parcel.readInt();
        this.fsUuid = parcel.readString();
        this.partGuid = parcel.readString();
        this.nickname = parcel.readString();
        this.userFlags = parcel.readInt();
        this.createdMillis = parcel.readLong();
        this.lastSeenMillis = parcel.readLong();
        this.lastTrimMillis = parcel.readLong();
        this.lastBenchMillis = parcel.readLong();
    }

    public VolumeRecord clone() {
        Parcel parcel = Parcel.obtain();
        try {
            this.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            VolumeRecord volumeRecord = CREATOR.createFromParcel(parcel);
            return volumeRecord;
        }
        finally {
            parcel.recycle();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VolumeRecord:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("type", DebugUtils.valueToString(VolumeInfo.class, "TYPE_", this.type));
        indentingPrintWriter.printPair("fsUuid", this.fsUuid);
        indentingPrintWriter.printPair("partGuid", this.partGuid);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("nickname", this.nickname);
        indentingPrintWriter.printPair("userFlags", DebugUtils.flagsToString(VolumeRecord.class, "USER_FLAG_", this.userFlags));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("createdMillis", TimeUtils.formatForLogging(this.createdMillis));
        indentingPrintWriter.printPair("lastSeenMillis", TimeUtils.formatForLogging(this.lastSeenMillis));
        indentingPrintWriter.printPair("lastTrimMillis", TimeUtils.formatForLogging(this.lastTrimMillis));
        indentingPrintWriter.printPair("lastBenchMillis", TimeUtils.formatForLogging(this.lastBenchMillis));
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public boolean equals(Object object) {
        if (object instanceof VolumeRecord) {
            return Objects.equals(this.fsUuid, ((VolumeRecord)object).fsUuid);
        }
        return false;
    }

    public String getFsUuid() {
        return this.fsUuid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getNormalizedFsUuid() {
        String string2 = this.fsUuid;
        string2 = string2 != null ? string2.toLowerCase(Locale.US) : null;
        return string2;
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return this.fsUuid.hashCode();
    }

    public boolean isInited() {
        int n = this.userFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isSnoozed() {
        boolean bl = (this.userFlags & 2) != 0;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.type);
        parcel.writeString(this.fsUuid);
        parcel.writeString(this.partGuid);
        parcel.writeString(this.nickname);
        parcel.writeInt(this.userFlags);
        parcel.writeLong(this.createdMillis);
        parcel.writeLong(this.lastSeenMillis);
        parcel.writeLong(this.lastTrimMillis);
        parcel.writeLong(this.lastBenchMillis);
    }

}

