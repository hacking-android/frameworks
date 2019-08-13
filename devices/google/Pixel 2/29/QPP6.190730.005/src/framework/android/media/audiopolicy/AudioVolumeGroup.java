/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SystemApi
public final class AudioVolumeGroup
implements Parcelable {
    public static final Parcelable.Creator<AudioVolumeGroup> CREATOR;
    public static final int DEFAULT_VOLUME_GROUP = -1;
    private static final String TAG = "AudioVolumeGroup";
    @GuardedBy(value={"sLock"})
    private static List<AudioVolumeGroup> sAudioVolumeGroups;
    private static final Object sLock;
    private final AudioAttributes[] mAudioAttributes;
    private int mId;
    private int[] mLegacyStreamTypes;
    private final String mName;

    static {
        sLock = new Object();
        CREATOR = new Parcelable.Creator<AudioVolumeGroup>(){

            @Override
            public AudioVolumeGroup createFromParcel(Parcel parcel) {
                int n;
                Preconditions.checkNotNull(parcel, "in Parcel must not be null");
                String string2 = parcel.readString();
                int n2 = parcel.readInt();
                int n3 = parcel.readInt();
                AudioAttributes[] arraudioAttributes = new AudioAttributes[n3];
                for (n = 0; n < n3; ++n) {
                    arraudioAttributes[n] = AudioAttributes.CREATOR.createFromParcel(parcel);
                }
                n3 = parcel.readInt();
                int[] arrn = new int[n3];
                for (n = 0; n < n3; ++n) {
                    arrn[n] = parcel.readInt();
                }
                return new AudioVolumeGroup(string2, n2, arraudioAttributes, arrn);
            }

            public AudioVolumeGroup[] newArray(int n) {
                return new AudioVolumeGroup[n];
            }
        };
    }

    AudioVolumeGroup(String string2, int n, AudioAttributes[] arraudioAttributes, int[] arrn) {
        Preconditions.checkNotNull(string2, "name must not be null");
        Preconditions.checkNotNull(arraudioAttributes, "audioAttributes must not be null");
        Preconditions.checkNotNull(arrn, "legacyStreamTypes must not be null");
        this.mName = string2;
        this.mId = n;
        this.mAudioAttributes = arraudioAttributes;
        this.mLegacyStreamTypes = arrn;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<AudioVolumeGroup> getAudioVolumeGroups() {
        if (sAudioVolumeGroups != null) return sAudioVolumeGroups;
        Object object = sLock;
        synchronized (object) {
            if (sAudioVolumeGroups != null) return sAudioVolumeGroups;
            sAudioVolumeGroups = AudioVolumeGroup.initializeAudioVolumeGroups();
            return sAudioVolumeGroups;
        }
    }

    private static List<AudioVolumeGroup> initializeAudioVolumeGroups() {
        ArrayList<AudioVolumeGroup> arrayList = new ArrayList<AudioVolumeGroup>();
        if (AudioVolumeGroup.native_list_audio_volume_groups(arrayList) != 0) {
            Log.w(TAG, ": listAudioVolumeGroups failed");
        }
        return arrayList;
    }

    private static native int native_list_audio_volume_groups(ArrayList<AudioVolumeGroup> var0);

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
            object = (AudioVolumeGroup)object;
            if (this.mName != ((AudioVolumeGroup)object).mName || this.mId != ((AudioVolumeGroup)object).mId || !this.mAudioAttributes.equals(((AudioVolumeGroup)object).mAudioAttributes)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public List<AudioAttributes> getAudioAttributes() {
        return Arrays.asList(this.mAudioAttributes);
    }

    public int getId() {
        return this.mId;
    }

    public int[] getLegacyStreamTypes() {
        return this.mLegacyStreamTypes;
    }

    public String name() {
        return this.mName;
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n Name: ");
        stringBuilder.append(this.mName);
        stringBuilder.append(" Id: ");
        stringBuilder.append(Integer.toString(this.mId));
        stringBuilder.append("\n     Supported Audio Attributes:");
        Object[] arrobject = this.mAudioAttributes;
        int n2 = arrobject.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            AudioAttributes audioAttributes = arrobject[n];
            stringBuilder.append("\n       -");
            stringBuilder.append(audioAttributes.toString());
        }
        stringBuilder.append("\n     Supported Legacy Stream Types: { ");
        arrobject = this.mLegacyStreamTypes;
        n2 = arrobject.length;
        for (n = n3; n < n2; ++n) {
            stringBuilder.append(Integer.toString((int)arrobject[n]));
            stringBuilder.append(" ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        int n2;
        parcel.writeString(this.mName);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAudioAttributes.length);
        Object[] arrobject = this.mAudioAttributes;
        int n3 = arrobject.length;
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            arrobject[n2].writeToParcel(parcel, n | 1);
        }
        parcel.writeInt(this.mLegacyStreamTypes.length);
        arrobject = this.mLegacyStreamTypes;
        n2 = arrobject.length;
        for (n = n4; n < n2; ++n) {
            parcel.writeInt((int)arrobject[n]);
        }
    }

}

