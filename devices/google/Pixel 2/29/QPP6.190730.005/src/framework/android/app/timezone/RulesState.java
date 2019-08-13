/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.app.timezone.DistroFormatVersion;
import android.app.timezone.DistroRulesVersion;
import android.app.timezone.Utils;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RulesState
implements Parcelable {
    private static final byte BYTE_FALSE = 0;
    private static final byte BYTE_TRUE = 1;
    public static final Parcelable.Creator<RulesState> CREATOR = new Parcelable.Creator<RulesState>(){

        @Override
        public RulesState createFromParcel(Parcel parcel) {
            return RulesState.createFromParcel(parcel);
        }

        public RulesState[] newArray(int n) {
            return new RulesState[n];
        }
    };
    public static final int DISTRO_STATUS_INSTALLED = 2;
    public static final int DISTRO_STATUS_NONE = 1;
    public static final int DISTRO_STATUS_UNKNOWN = 0;
    public static final int STAGED_OPERATION_INSTALL = 3;
    public static final int STAGED_OPERATION_NONE = 1;
    public static final int STAGED_OPERATION_UNINSTALL = 2;
    public static final int STAGED_OPERATION_UNKNOWN = 0;
    private final String mBaseRulesVersion;
    private final DistroFormatVersion mDistroFormatVersionSupported;
    private final int mDistroStatus;
    private final DistroRulesVersion mInstalledDistroRulesVersion;
    private final boolean mOperationInProgress;
    private final DistroRulesVersion mStagedDistroRulesVersion;
    private final int mStagedOperationType;

    public RulesState(String string2, DistroFormatVersion distroFormatVersion, boolean bl, int n, DistroRulesVersion distroRulesVersion, int n2, DistroRulesVersion distroRulesVersion2) {
        this.mBaseRulesVersion = Utils.validateRulesVersion("baseRulesVersion", string2);
        this.mDistroFormatVersionSupported = Utils.validateNotNull("distroFormatVersionSupported", distroFormatVersion);
        this.mOperationInProgress = bl;
        if (bl && n != 0) {
            throw new IllegalArgumentException("stagedOperationType != STAGED_OPERATION_UNKNOWN");
        }
        n = this.mStagedOperationType = RulesState.validateStagedOperation(n);
        boolean bl2 = true;
        bl = n == 3;
        this.mStagedDistroRulesVersion = Utils.validateConditionalNull(bl, "stagedDistroRulesVersion", distroRulesVersion);
        this.mDistroStatus = RulesState.validateDistroStatus(n2);
        bl = this.mDistroStatus == 2 ? bl2 : false;
        this.mInstalledDistroRulesVersion = Utils.validateConditionalNull(bl, "installedDistroRulesVersion", distroRulesVersion2);
    }

    private static RulesState createFromParcel(Parcel parcel) {
        String string2 = parcel.readString();
        DistroFormatVersion distroFormatVersion = (DistroFormatVersion)parcel.readParcelable(null);
        boolean bl = parcel.readByte() == 1;
        return new RulesState(string2, distroFormatVersion, bl, parcel.readByte(), (DistroRulesVersion)parcel.readParcelable(null), parcel.readByte(), (DistroRulesVersion)parcel.readParcelable(null));
    }

    private static int validateDistroStatus(int n) {
        if (n >= 0 && n <= 2) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown distro status=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int validateStagedOperation(int n) {
        if (n >= 0 && n <= 3) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown operation type=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
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
        if (object != null && this.getClass() == object.getClass()) {
            object = (RulesState)object;
            if (this.mOperationInProgress != ((RulesState)object).mOperationInProgress) {
                return false;
            }
            if (this.mStagedOperationType != ((RulesState)object).mStagedOperationType) {
                return false;
            }
            if (this.mDistroStatus != ((RulesState)object).mDistroStatus) {
                return false;
            }
            if (!this.mBaseRulesVersion.equals(((RulesState)object).mBaseRulesVersion)) {
                return false;
            }
            if (!this.mDistroFormatVersionSupported.equals(((RulesState)object).mDistroFormatVersionSupported)) {
                return false;
            }
            DistroRulesVersion distroRulesVersion = this.mStagedDistroRulesVersion;
            if (distroRulesVersion != null ? !distroRulesVersion.equals(((RulesState)object).mStagedDistroRulesVersion) : ((RulesState)object).mStagedDistroRulesVersion != null) {
                return false;
            }
            distroRulesVersion = this.mInstalledDistroRulesVersion;
            if (distroRulesVersion != null) {
                bl = distroRulesVersion.equals(((RulesState)object).mInstalledDistroRulesVersion);
            } else if (((RulesState)object).mInstalledDistroRulesVersion != null) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getBaseRulesVersion() {
        return this.mBaseRulesVersion;
    }

    public int getDistroStatus() {
        return this.mDistroStatus;
    }

    public DistroRulesVersion getInstalledDistroRulesVersion() {
        return this.mInstalledDistroRulesVersion;
    }

    public DistroRulesVersion getStagedDistroRulesVersion() {
        return this.mStagedDistroRulesVersion;
    }

    public int getStagedOperationType() {
        return this.mStagedOperationType;
    }

    public int hashCode() {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        block0 : {
            n7 = this.mBaseRulesVersion.hashCode();
            n = this.mDistroFormatVersionSupported.hashCode();
            n4 = this.mOperationInProgress;
            n6 = this.mStagedOperationType;
            DistroRulesVersion distroRulesVersion = this.mStagedDistroRulesVersion;
            n5 = 0;
            n3 = distroRulesVersion != null ? distroRulesVersion.hashCode() : 0;
            n2 = this.mDistroStatus;
            distroRulesVersion = this.mInstalledDistroRulesVersion;
            if (distroRulesVersion == null) break block0;
            n5 = distroRulesVersion.hashCode();
        }
        return (((((n7 * 31 + n) * 31 + n4) * 31 + n6) * 31 + n3) * 31 + n2) * 31 + n5;
    }

    public boolean isBaseVersionNewerThan(DistroRulesVersion distroRulesVersion) {
        boolean bl = this.mBaseRulesVersion.compareTo(distroRulesVersion.getRulesVersion()) > 0;
        return bl;
    }

    public boolean isDistroFormatVersionSupported(DistroFormatVersion distroFormatVersion) {
        return this.mDistroFormatVersionSupported.supports(distroFormatVersion);
    }

    public boolean isOperationInProgress() {
        return this.mOperationInProgress;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RulesState{mBaseRulesVersion='");
        stringBuilder.append(this.mBaseRulesVersion);
        stringBuilder.append('\'');
        stringBuilder.append(", mDistroFormatVersionSupported=");
        stringBuilder.append(this.mDistroFormatVersionSupported);
        stringBuilder.append(", mOperationInProgress=");
        stringBuilder.append(this.mOperationInProgress);
        stringBuilder.append(", mStagedOperationType=");
        stringBuilder.append(this.mStagedOperationType);
        stringBuilder.append(", mStagedDistroRulesVersion=");
        stringBuilder.append(this.mStagedDistroRulesVersion);
        stringBuilder.append(", mDistroStatus=");
        stringBuilder.append(this.mDistroStatus);
        stringBuilder.append(", mInstalledDistroRulesVersion=");
        stringBuilder.append(this.mInstalledDistroRulesVersion);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mBaseRulesVersion);
        parcel.writeParcelable(this.mDistroFormatVersionSupported, 0);
        parcel.writeByte((byte)this.mOperationInProgress);
        parcel.writeByte((byte)this.mStagedOperationType);
        parcel.writeParcelable(this.mStagedDistroRulesVersion, 0);
        parcel.writeByte((byte)this.mDistroStatus);
        parcel.writeParcelable(this.mInstalledDistroRulesVersion, 0);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface DistroStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface StagedOperationType {
    }

}

