/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ProfilerInfo
implements Parcelable {
    public static final Parcelable.Creator<ProfilerInfo> CREATOR = new Parcelable.Creator<ProfilerInfo>(){

        @Override
        public ProfilerInfo createFromParcel(Parcel parcel) {
            return new ProfilerInfo(parcel);
        }

        public ProfilerInfo[] newArray(int n) {
            return new ProfilerInfo[n];
        }
    };
    private static final String TAG = "ProfilerInfo";
    public final String agent;
    public final boolean attachAgentDuringBind;
    public final boolean autoStopProfiler;
    public ParcelFileDescriptor profileFd;
    public final String profileFile;
    public final int samplingInterval;
    public final boolean streamingOutput;

    public ProfilerInfo(ProfilerInfo profilerInfo) {
        this.profileFile = profilerInfo.profileFile;
        this.profileFd = profilerInfo.profileFd;
        this.samplingInterval = profilerInfo.samplingInterval;
        this.autoStopProfiler = profilerInfo.autoStopProfiler;
        this.streamingOutput = profilerInfo.streamingOutput;
        this.agent = profilerInfo.agent;
        this.attachAgentDuringBind = profilerInfo.attachAgentDuringBind;
    }

    private ProfilerInfo(Parcel parcel) {
        this.profileFile = parcel.readString();
        ParcelFileDescriptor parcelFileDescriptor = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
        this.profileFd = parcelFileDescriptor;
        this.samplingInterval = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.autoStopProfiler = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.streamingOutput = bl2;
        this.agent = parcel.readString();
        this.attachAgentDuringBind = parcel.readBoolean();
    }

    public ProfilerInfo(String string2, ParcelFileDescriptor parcelFileDescriptor, int n, boolean bl, boolean bl2, String string3, boolean bl3) {
        this.profileFile = string2;
        this.profileFd = parcelFileDescriptor;
        this.samplingInterval = n;
        this.autoStopProfiler = bl;
        this.streamingOutput = bl2;
        this.agent = string3;
        this.attachAgentDuringBind = bl3;
    }

    public void closeFd() {
        ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            }
            catch (IOException iOException) {
                Slog.w(TAG, "Failure closing profile fd", iOException);
            }
            this.profileFd = null;
        }
    }

    @Override
    public int describeContents() {
        ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.describeContents();
        }
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ProfilerInfo)object;
            if (!Objects.equals(this.profileFile, ((ProfilerInfo)object).profileFile) || this.autoStopProfiler != ((ProfilerInfo)object).autoStopProfiler || this.samplingInterval != ((ProfilerInfo)object).samplingInterval || this.streamingOutput != ((ProfilerInfo)object).streamingOutput || !Objects.equals(this.agent, ((ProfilerInfo)object).agent)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return ((((17 * 31 + Objects.hashCode(this.profileFile)) * 31 + this.samplingInterval) * 31 + this.autoStopProfiler) * 31 + this.streamingOutput) * 31 + Objects.hashCode(this.agent);
    }

    public ProfilerInfo setAgent(String string2, boolean bl) {
        return new ProfilerInfo(this.profileFile, this.profileFd, this.samplingInterval, this.autoStopProfiler, this.streamingOutput, string2, bl);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.profileFile);
        if (this.profileFd != null) {
            parcel.writeInt(1);
            this.profileFd.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.samplingInterval);
        parcel.writeInt((int)this.autoStopProfiler);
        parcel.writeInt((int)this.streamingOutput);
        parcel.writeString(this.agent);
        parcel.writeBoolean(this.attachAgentDuringBind);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.profileFile);
        ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
        if (parcelFileDescriptor != null) {
            protoOutputStream.write(1120986464258L, parcelFileDescriptor.getFd());
        }
        protoOutputStream.write(1120986464259L, this.samplingInterval);
        protoOutputStream.write(1133871366148L, this.autoStopProfiler);
        protoOutputStream.write(1133871366149L, this.streamingOutput);
        protoOutputStream.write(1138166333446L, this.agent);
        protoOutputStream.end(l);
    }

}

