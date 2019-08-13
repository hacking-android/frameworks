/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DebugUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.CharArrayWriter;
import java.io.Writer;
import java.util.Objects;

public class DiskInfo
implements Parcelable {
    public static final String ACTION_DISK_SCANNED = "android.os.storage.action.DISK_SCANNED";
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final Parcelable.Creator<DiskInfo> CREATOR = new Parcelable.Creator<DiskInfo>(){

        @Override
        public DiskInfo createFromParcel(Parcel parcel) {
            return new DiskInfo(parcel);
        }

        public DiskInfo[] newArray(int n) {
            return new DiskInfo[n];
        }
    };
    public static final String EXTRA_DISK_ID = "android.os.storage.extra.DISK_ID";
    public static final String EXTRA_VOLUME_COUNT = "android.os.storage.extra.VOLUME_COUNT";
    public static final int FLAG_ADOPTABLE = 1;
    public static final int FLAG_DEFAULT_PRIMARY = 2;
    public static final int FLAG_SD = 4;
    public static final int FLAG_USB = 8;
    @UnsupportedAppUsage
    public final int flags;
    public final String id;
    @UnsupportedAppUsage
    public String label;
    @UnsupportedAppUsage
    public long size;
    public String sysPath;
    public int volumeCount;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public DiskInfo(Parcel parcel) {
        this.id = parcel.readString();
        this.flags = parcel.readInt();
        this.size = parcel.readLong();
        this.label = parcel.readString();
        this.volumeCount = parcel.readInt();
        this.sysPath = parcel.readString();
    }

    public DiskInfo(String string2, int n) {
        this.id = Preconditions.checkNotNull(string2);
        this.flags = n;
    }

    private boolean isInteresting(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return false;
        }
        if (string2.equalsIgnoreCase("ata")) {
            return false;
        }
        if (string2.toLowerCase().contains("generic")) {
            return false;
        }
        if (string2.toLowerCase().startsWith("usb")) {
            return false;
        }
        return !string2.toLowerCase().startsWith("multiple");
    }

    public DiskInfo clone() {
        Parcel parcel = Parcel.obtain();
        try {
            this.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            DiskInfo diskInfo = CREATOR.createFromParcel(parcel);
            return diskInfo;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DiskInfo{");
        stringBuilder.append(this.id);
        stringBuilder.append("}:");
        indentingPrintWriter.println(stringBuilder.toString());
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("flags", DebugUtils.flagsToString(this.getClass(), "FLAG_", this.flags));
        indentingPrintWriter.printPair("size", this.size);
        indentingPrintWriter.printPair("label", this.label);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("sysPath", this.sysPath);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public boolean equals(Object object) {
        if (object instanceof DiskInfo) {
            return Objects.equals(this.id, ((DiskInfo)object).id);
        }
        return false;
    }

    @UnsupportedAppUsage
    public String getDescription() {
        Resources resources = Resources.getSystem();
        int n = this.flags;
        if ((n & 4) != 0) {
            if (this.isInteresting(this.label)) {
                return resources.getString(17041105, this.label);
            }
            return resources.getString(17041104);
        }
        if ((n & 8) != 0) {
            if (this.isInteresting(this.label)) {
                return resources.getString(17041108, this.label);
            }
            return resources.getString(17041107);
        }
        return null;
    }

    @UnsupportedAppUsage
    public String getId() {
        return this.id;
    }

    public String getShortDescription() {
        Resources resources = Resources.getSystem();
        if (this.isSd()) {
            return resources.getString(17041104);
        }
        if (this.isUsb()) {
            return resources.getString(17041107);
        }
        return null;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    @UnsupportedAppUsage
    public boolean isAdoptable() {
        int n = this.flags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isDefaultPrimary() {
        boolean bl = (this.flags & 2) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isSd() {
        boolean bl = (this.flags & 4) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isUsb() {
        boolean bl = (this.flags & 8) != 0;
        return bl;
    }

    public String toString() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        this.dump(new IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.id);
        parcel.writeInt(this.flags);
        parcel.writeLong(this.size);
        parcel.writeString(this.label);
        parcel.writeInt(this.volumeCount);
        parcel.writeString(this.sysPath);
    }

}

