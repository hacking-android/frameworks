/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.media.AudioAttributes;
import android.os.Binder;
import android.os.IBinder;
import android.os.IExternalVibrationController;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.util.Preconditions;

public class ExternalVibration
implements Parcelable {
    public static final Parcelable.Creator<ExternalVibration> CREATOR = new Parcelable.Creator<ExternalVibration>(){

        @Override
        public ExternalVibration createFromParcel(Parcel parcel) {
            return new ExternalVibration(parcel);
        }

        public ExternalVibration[] newArray(int n) {
            return new ExternalVibration[n];
        }
    };
    private static final String TAG = "ExternalVibration";
    private AudioAttributes mAttrs;
    private IExternalVibrationController mController;
    private String mPkg;
    private IBinder mToken;
    private int mUid;

    public ExternalVibration(int n, String string2, AudioAttributes audioAttributes, IExternalVibrationController iExternalVibrationController) {
        this.mUid = n;
        this.mPkg = Preconditions.checkNotNull(string2);
        this.mAttrs = Preconditions.checkNotNull(audioAttributes);
        this.mController = Preconditions.checkNotNull(iExternalVibrationController);
        this.mToken = new Binder();
    }

    private ExternalVibration(Parcel parcel) {
        this.mUid = parcel.readInt();
        this.mPkg = parcel.readString();
        this.mAttrs = this.readAudioAttributes(parcel);
        this.mController = IExternalVibrationController.Stub.asInterface(parcel.readStrongBinder());
        this.mToken = parcel.readStrongBinder();
    }

    private AudioAttributes readAudioAttributes(Parcel parcel) {
        int n = parcel.readInt();
        int n2 = parcel.readInt();
        int n3 = parcel.readInt();
        int n4 = parcel.readInt();
        return new AudioAttributes.Builder().setUsage(n).setContentType(n2).setCapturePreset(n3).setFlags(n4).build();
    }

    private static void writeAudioAttributes(AudioAttributes audioAttributes, Parcel parcel, int n) {
        parcel.writeInt(audioAttributes.getUsage());
        parcel.writeInt(audioAttributes.getContentType());
        parcel.writeInt(audioAttributes.getCapturePreset());
        parcel.writeInt(audioAttributes.getAllFlags());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object != null && object instanceof ExternalVibration) {
            object = (ExternalVibration)object;
            return this.mToken.equals(((ExternalVibration)object).mToken);
        }
        return false;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAttrs;
    }

    public String getPackage() {
        return this.mPkg;
    }

    public int getUid() {
        return this.mUid;
    }

    public void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        try {
            this.mToken.linkToDeath(deathRecipient, 0);
            return;
        }
        catch (RemoteException remoteException) {
            return;
        }
    }

    public boolean mute() {
        try {
            this.mController.mute();
            return true;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to mute vibration stream: ");
            stringBuilder.append(this);
            Slog.wtf(TAG, stringBuilder.toString(), remoteException);
            return false;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExternalVibration{uid=");
        stringBuilder.append(this.mUid);
        stringBuilder.append(", pkg=");
        stringBuilder.append(this.mPkg);
        stringBuilder.append(", attrs=");
        stringBuilder.append(this.mAttrs);
        stringBuilder.append(", controller=");
        stringBuilder.append(this.mController);
        stringBuilder.append("token=");
        stringBuilder.append(this.mController);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void unlinkToDeath(IBinder.DeathRecipient deathRecipient) {
        this.mToken.unlinkToDeath(deathRecipient, 0);
    }

    public boolean unmute() {
        try {
            this.mController.unmute();
            return true;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to unmute vibration stream: ");
            stringBuilder.append(this);
            Slog.wtf(TAG, stringBuilder.toString(), remoteException);
            return false;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mPkg);
        ExternalVibration.writeAudioAttributes(this.mAttrs, parcel, n);
        parcel.writeParcelable(this.mAttrs, n);
        parcel.writeStrongBinder(this.mController.asBinder());
        parcel.writeStrongBinder(this.mToken);
    }

}

