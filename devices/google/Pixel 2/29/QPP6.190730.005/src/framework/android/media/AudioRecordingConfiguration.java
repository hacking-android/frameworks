/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioPatch;
import android.media.AudioPort;
import android.media.AudioPortConfig;
import android.media.MediaRecorder;
import android.media.audiofx.AudioEffect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class AudioRecordingConfiguration
implements Parcelable {
    public static final Parcelable.Creator<AudioRecordingConfiguration> CREATOR;
    private static final String TAG;
    private final AudioEffect.Descriptor[] mClientEffects;
    private final AudioFormat mClientFormat;
    private final String mClientPackageName;
    private final int mClientPortId;
    private final int mClientSessionId;
    private boolean mClientSilenced;
    private final int mClientSource;
    private final int mClientUid;
    private final AudioEffect.Descriptor[] mDeviceEffects;
    private final AudioFormat mDeviceFormat;
    private final int mDeviceSource;
    private final int mPatchHandle;

    static {
        TAG = new String("AudioRecordingConfiguration");
        CREATOR = new Parcelable.Creator<AudioRecordingConfiguration>(){

            @Override
            public AudioRecordingConfiguration createFromParcel(Parcel parcel) {
                return new AudioRecordingConfiguration(parcel);
            }

            public AudioRecordingConfiguration[] newArray(int n) {
                return new AudioRecordingConfiguration[n];
            }
        };
    }

    public AudioRecordingConfiguration(int n, int n2, int n3, AudioFormat audioFormat, AudioFormat audioFormat2, int n4, String string2) {
        this(n, n2, n3, audioFormat, audioFormat2, n4, string2, 0, false, 0, new AudioEffect.Descriptor[0], new AudioEffect.Descriptor[0]);
    }

    public AudioRecordingConfiguration(int n, int n2, int n3, AudioFormat audioFormat, AudioFormat audioFormat2, int n4, String string2, int n5, boolean bl, int n6, AudioEffect.Descriptor[] arrdescriptor, AudioEffect.Descriptor[] arrdescriptor2) {
        this.mClientUid = n;
        this.mClientSessionId = n2;
        this.mClientSource = n3;
        this.mClientFormat = audioFormat;
        this.mDeviceFormat = audioFormat2;
        this.mPatchHandle = n4;
        this.mClientPackageName = string2;
        this.mClientPortId = n5;
        this.mClientSilenced = bl;
        this.mDeviceSource = n6;
        this.mClientEffects = arrdescriptor;
        this.mDeviceEffects = arrdescriptor2;
    }

    private AudioRecordingConfiguration(Parcel parcel) {
        AudioEffect.Descriptor[] arrdescriptor;
        int n;
        this.mClientSessionId = parcel.readInt();
        this.mClientSource = parcel.readInt();
        this.mClientFormat = AudioFormat.CREATOR.createFromParcel(parcel);
        this.mDeviceFormat = AudioFormat.CREATOR.createFromParcel(parcel);
        this.mPatchHandle = parcel.readInt();
        this.mClientPackageName = parcel.readString();
        this.mClientUid = parcel.readInt();
        this.mClientPortId = parcel.readInt();
        this.mClientSilenced = parcel.readBoolean();
        this.mDeviceSource = parcel.readInt();
        this.mClientEffects = new AudioEffect.Descriptor[parcel.readInt()];
        for (n = 0; n < (arrdescriptor = this.mClientEffects).length; ++n) {
            arrdescriptor[n] = new AudioEffect.Descriptor(parcel);
        }
        this.mDeviceEffects = new AudioEffect.Descriptor[parcel.readInt()];
        for (n = 0; n < (arrdescriptor = this.mDeviceEffects).length; ++n) {
            arrdescriptor[n] = new AudioEffect.Descriptor(parcel);
        }
    }

    public static AudioRecordingConfiguration anonymizedCopy(AudioRecordingConfiguration audioRecordingConfiguration) {
        return new AudioRecordingConfiguration(-1, audioRecordingConfiguration.mClientSessionId, audioRecordingConfiguration.mClientSource, audioRecordingConfiguration.mClientFormat, audioRecordingConfiguration.mDeviceFormat, audioRecordingConfiguration.mPatchHandle, "", audioRecordingConfiguration.mClientPortId, audioRecordingConfiguration.mClientSilenced, audioRecordingConfiguration.mDeviceSource, audioRecordingConfiguration.mClientEffects, audioRecordingConfiguration.mDeviceEffects);
    }

    public static String toLogFriendlyString(AudioRecordingConfiguration audioRecordingConfiguration) {
        Object object;
        int n;
        Object object2;
        String string2 = new String();
        Object object3 = audioRecordingConfiguration.mClientEffects;
        int n2 = ((AudioEffect.Descriptor[])object3).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            object2 = object3[n];
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("'");
            ((StringBuilder)object).append(((AudioEffect.Descriptor)object2).name);
            ((StringBuilder)object).append("' ");
            string2 = ((StringBuilder)object).toString();
        }
        object3 = new String();
        object = audioRecordingConfiguration.mDeviceEffects;
        n2 = ((AudioEffect.Descriptor[])object).length;
        for (n = n3; n < n2; ++n) {
            object2 = object[n];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object3);
            stringBuilder.append("'");
            stringBuilder.append(((AudioEffect.Descriptor)object2).name);
            stringBuilder.append("' ");
            object3 = stringBuilder.toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("session:");
        ((StringBuilder)object).append(audioRecordingConfiguration.mClientSessionId);
        ((StringBuilder)object).append(" -- source client=");
        ((StringBuilder)object).append(MediaRecorder.toLogFriendlyAudioSource(audioRecordingConfiguration.mClientSource));
        ((StringBuilder)object).append(", dev=");
        ((StringBuilder)object).append(audioRecordingConfiguration.mDeviceFormat.toLogFriendlyString());
        ((StringBuilder)object).append(" -- uid:");
        ((StringBuilder)object).append(audioRecordingConfiguration.mClientUid);
        ((StringBuilder)object).append(" -- patch:");
        ((StringBuilder)object).append(audioRecordingConfiguration.mPatchHandle);
        ((StringBuilder)object).append(" -- pack:");
        ((StringBuilder)object).append(audioRecordingConfiguration.mClientPackageName);
        ((StringBuilder)object).append(" -- format client=");
        ((StringBuilder)object).append(audioRecordingConfiguration.mClientFormat.toLogFriendlyString());
        ((StringBuilder)object).append(", dev=");
        ((StringBuilder)object).append(audioRecordingConfiguration.mDeviceFormat.toLogFriendlyString());
        ((StringBuilder)object).append(" -- silenced:");
        ((StringBuilder)object).append(audioRecordingConfiguration.mClientSilenced);
        ((StringBuilder)object).append(" -- effects client=");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(", dev=");
        ((StringBuilder)object).append((String)object3);
        return new String(((StringBuilder)object).toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(AudioRecordingConfiguration.toLogFriendlyString(this));
        printWriter.println(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof AudioRecordingConfiguration) {
            object = (AudioRecordingConfiguration)object;
            if (!(this.mClientUid == ((AudioRecordingConfiguration)object).mClientUid && this.mClientSessionId == ((AudioRecordingConfiguration)object).mClientSessionId && this.mClientSource == ((AudioRecordingConfiguration)object).mClientSource && this.mPatchHandle == ((AudioRecordingConfiguration)object).mPatchHandle && this.mClientFormat.equals(((AudioRecordingConfiguration)object).mClientFormat) && this.mDeviceFormat.equals(((AudioRecordingConfiguration)object).mDeviceFormat) && this.mClientPackageName.equals(((AudioRecordingConfiguration)object).mClientPackageName) && this.mClientPortId == ((AudioRecordingConfiguration)object).mClientPortId && this.mClientSilenced == ((AudioRecordingConfiguration)object).mClientSilenced && this.mDeviceSource == ((AudioRecordingConfiguration)object).mDeviceSource && Arrays.equals(this.mClientEffects, ((AudioRecordingConfiguration)object).mClientEffects) && Arrays.equals(this.mDeviceEffects, ((AudioRecordingConfiguration)object).mDeviceEffects))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public AudioDeviceInfo getAudioDevice() {
        ArrayList<AudioPatch> arrayList = new ArrayList<AudioPatch>();
        if (AudioManager.listAudioPatches(arrayList) != 0) {
            Log.e(TAG, "Error retrieving list of audio patches");
            return null;
        }
        for (int i = 0; i < arrayList.size(); ++i) {
            Object[] arrobject = arrayList.get(i);
            if (arrobject.id() != this.mPatchHandle) continue;
            if ((arrobject = arrobject.sources()) == null || arrobject.length <= 0) break;
            int n = arrobject[0].port().id();
            arrobject = AudioManager.getDevicesStatic(1);
            for (i = 0; i < arrobject.length; ++i) {
                if (((AudioDeviceInfo)arrobject[i]).getId() != n) continue;
                return arrobject[i];
            }
            break;
        }
        Log.e(TAG, "Couldn't find device for recording, did recording end already?");
        return null;
    }

    public int getAudioSource() {
        return this.mDeviceSource;
    }

    public int getClientAudioSessionId() {
        return this.mClientSessionId;
    }

    public int getClientAudioSource() {
        return this.mClientSource;
    }

    public List<AudioEffect.Descriptor> getClientEffects() {
        return new ArrayList<AudioEffect.Descriptor>(Arrays.asList(this.mClientEffects));
    }

    public AudioFormat getClientFormat() {
        return this.mClientFormat;
    }

    @UnsupportedAppUsage
    public String getClientPackageName() {
        return this.mClientPackageName;
    }

    public int getClientPortId() {
        return this.mClientPortId;
    }

    @UnsupportedAppUsage
    public int getClientUid() {
        return this.mClientUid;
    }

    public List<AudioEffect.Descriptor> getEffects() {
        return new ArrayList<AudioEffect.Descriptor>(Arrays.asList(this.mDeviceEffects));
    }

    public AudioFormat getFormat() {
        return this.mDeviceFormat;
    }

    public int hashCode() {
        return Objects.hash(this.mClientSessionId, this.mClientSource);
    }

    public boolean isClientSilenced() {
        return this.mClientSilenced;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        AudioEffect.Descriptor[] arrdescriptor;
        parcel.writeInt(this.mClientSessionId);
        parcel.writeInt(this.mClientSource);
        this.mClientFormat.writeToParcel(parcel, 0);
        this.mDeviceFormat.writeToParcel(parcel, 0);
        parcel.writeInt(this.mPatchHandle);
        parcel.writeString(this.mClientPackageName);
        parcel.writeInt(this.mClientUid);
        parcel.writeInt(this.mClientPortId);
        parcel.writeBoolean(this.mClientSilenced);
        parcel.writeInt(this.mDeviceSource);
        parcel.writeInt(this.mClientEffects.length);
        for (n = 0; n < (arrdescriptor = this.mClientEffects).length; ++n) {
            arrdescriptor[n].writeToParcel(parcel);
        }
        parcel.writeInt(this.mDeviceEffects.length);
        for (n = 0; n < (arrdescriptor = this.mDeviceEffects).length; ++n) {
            arrdescriptor[n].writeToParcel(parcel);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AudioSource {
    }

}

