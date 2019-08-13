/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SystemApi
public final class AudioProductStrategy
implements Parcelable {
    public static final Parcelable.Creator<AudioProductStrategy> CREATOR;
    public static final int DEFAULT_GROUP = -1;
    private static final String TAG = "AudioProductStrategy";
    @GuardedBy(value={"sLock"})
    private static List<AudioProductStrategy> sAudioProductStrategies;
    public static final AudioAttributes sDefaultAttributes;
    private static final Object sLock;
    private final AudioAttributesGroup[] mAudioAttributesGroups;
    private int mId;
    private final String mName;

    static {
        sLock = new Object();
        CREATOR = new Parcelable.Creator<AudioProductStrategy>(){

            @Override
            public AudioProductStrategy createFromParcel(Parcel parcel) {
                String string2 = parcel.readString();
                int n = parcel.readInt();
                int n2 = parcel.readInt();
                AudioAttributesGroup[] arraudioAttributesGroup = new AudioAttributesGroup[n2];
                for (int i = 0; i < n2; ++i) {
                    arraudioAttributesGroup[i] = AudioAttributesGroup.CREATOR.createFromParcel(parcel);
                }
                return new AudioProductStrategy(string2, n, arraudioAttributesGroup);
            }

            public AudioProductStrategy[] newArray(int n) {
                return new AudioProductStrategy[n];
            }
        };
        sDefaultAttributes = new AudioAttributes.Builder().setCapturePreset(0).build();
    }

    private AudioProductStrategy(String string2, int n, AudioAttributesGroup[] arraudioAttributesGroup) {
        Preconditions.checkNotNull(string2, "name must not be null");
        Preconditions.checkNotNull(arraudioAttributesGroup, "AudioAttributesGroups must not be null");
        this.mName = string2;
        this.mId = n;
        this.mAudioAttributesGroups = arraudioAttributesGroup;
    }

    private static boolean attributesMatches(AudioAttributes audioAttributes, AudioAttributes audioAttributes2) {
        Preconditions.checkNotNull(audioAttributes, "refAttr must not be null");
        Preconditions.checkNotNull(audioAttributes2, "attr must not be null");
        String string2 = TextUtils.join((CharSequence)";", audioAttributes.getTags());
        String string3 = TextUtils.join((CharSequence)";", audioAttributes2.getTags());
        boolean bl = audioAttributes.equals(sDefaultAttributes);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (!(audioAttributes.getUsage() != 0 && audioAttributes2.getUsage() != audioAttributes.getUsage() || audioAttributes.getContentType() != 0 && audioAttributes2.getContentType() != audioAttributes.getContentType() || audioAttributes.getAllFlags() != 0 && (audioAttributes2.getAllFlags() == 0 || (audioAttributes2.getAllFlags() & audioAttributes.getAllFlags()) != audioAttributes.getAllFlags()) || string2.length() != 0 && !string2.equals(string3))) {
            bl2 = true;
        }
        return bl2;
    }

    public static AudioAttributes getAudioAttributesForStrategyWithLegacyStreamType(int n) {
        Iterator<AudioProductStrategy> iterator = AudioProductStrategy.getAudioProductStrategies().iterator();
        while (iterator.hasNext()) {
            AudioAttributes audioAttributes = iterator.next().getAudioAttributesForLegacyStreamType(n);
            if (audioAttributes == null) continue;
            return audioAttributes;
        }
        return new AudioAttributes.Builder().setContentType(0).setUsage(0).build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<AudioProductStrategy> getAudioProductStrategies() {
        if (sAudioProductStrategies != null) return sAudioProductStrategies;
        Object object = sLock;
        synchronized (object) {
            if (sAudioProductStrategies != null) return sAudioProductStrategies;
            sAudioProductStrategies = AudioProductStrategy.initializeAudioProductStrategies();
            return sAudioProductStrategies;
        }
    }

    public static int getLegacyStreamTypeForStrategyWithAudioAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "AudioAttributes must not be null");
        Object object = AudioProductStrategy.getAudioProductStrategies().iterator();
        while (object.hasNext()) {
            AudioProductStrategy audioProductStrategy = object.next();
            if (!audioProductStrategy.supportsAudioAttributes(audioAttributes)) continue;
            int n = audioProductStrategy.getLegacyStreamTypeForAudioAttributes(audioAttributes);
            if (n == -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Attributes ");
                ((StringBuilder)object).append(audioAttributes.toString());
                ((StringBuilder)object).append(" ported by strategy ");
                ((StringBuilder)object).append(audioProductStrategy.getId());
                ((StringBuilder)object).append(" has no stream type associated, DO NOT USE STREAM TO CONTROL THE VOLUME");
                Log.w(TAG, ((StringBuilder)object).toString());
                return 3;
            }
            return n;
        }
        return 3;
    }

    private static List<AudioProductStrategy> initializeAudioProductStrategies() {
        ArrayList<AudioProductStrategy> arrayList = new ArrayList<AudioProductStrategy>();
        if (AudioProductStrategy.native_list_audio_product_strategies(arrayList) != 0) {
            Log.w(TAG, ": initializeAudioProductStrategies failed");
        }
        return arrayList;
    }

    private static native int native_list_audio_product_strategies(ArrayList<AudioProductStrategy> var0);

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
            object = (AudioProductStrategy)object;
            if (this.mName != ((AudioProductStrategy)object).mName || this.mId != ((AudioProductStrategy)object).mId || !this.mAudioAttributesGroups.equals(((AudioProductStrategy)object).mAudioAttributesGroups)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @SystemApi
    public AudioAttributes getAudioAttributes() {
        Object object = this.mAudioAttributesGroups;
        object = ((AudioAttributesGroup[])object).length == 0 ? new AudioAttributes.Builder().build() : object[0].getAudioAttributes();
        return object;
    }

    public AudioAttributes getAudioAttributesForLegacyStreamType(int n) {
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (!audioAttributesGroup.supportsStreamType(n)) continue;
            return audioAttributesGroup.getAudioAttributes();
        }
        return null;
    }

    @SystemApi
    public int getId() {
        return this.mId;
    }

    public int getLegacyStreamTypeForAudioAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "AudioAttributes must not be null");
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (!audioAttributesGroup.supportsAttributes(audioAttributes)) continue;
            return audioAttributesGroup.getStreamType();
        }
        return -1;
    }

    public int getVolumeGroupIdForAudioAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "AudioAttributes must not be null");
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (!audioAttributesGroup.supportsAttributes(audioAttributes)) continue;
            return audioAttributesGroup.getVolumeGroupId();
        }
        return -1;
    }

    public int getVolumeGroupIdForLegacyStreamType(int n) {
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (!audioAttributesGroup.supportsStreamType(n)) continue;
            return audioAttributesGroup.getVolumeGroupId();
        }
        return -1;
    }

    public boolean supportsAudioAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "AudioAttributes must not be null");
        AudioAttributesGroup[] arraudioAttributesGroup = this.mAudioAttributesGroups;
        int n = arraudioAttributesGroup.length;
        for (int i = 0; i < n; ++i) {
            if (!arraudioAttributesGroup[i].supportsAttributes(audioAttributes)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n Name: ");
        stringBuilder.append(this.mName);
        stringBuilder.append(" Id: ");
        stringBuilder.append(Integer.toString(this.mId));
        AudioAttributesGroup[] arraudioAttributesGroup = this.mAudioAttributesGroups;
        int n = arraudioAttributesGroup.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arraudioAttributesGroup[i].toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAudioAttributesGroups.length);
        AudioAttributesGroup[] arraudioAttributesGroup = this.mAudioAttributesGroups;
        int n2 = arraudioAttributesGroup.length;
        for (int i = 0; i < n2; ++i) {
            arraudioAttributesGroup[i].writeToParcel(parcel, n);
        }
    }

    private static final class AudioAttributesGroup
    implements Parcelable {
        public static final Parcelable.Creator<AudioAttributesGroup> CREATOR = new Parcelable.Creator<AudioAttributesGroup>(){

            @Override
            public AudioAttributesGroup createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                int n2 = parcel.readInt();
                int n3 = parcel.readInt();
                AudioAttributes[] arraudioAttributes = new AudioAttributes[n3];
                for (int i = 0; i < n3; ++i) {
                    arraudioAttributes[i] = AudioAttributes.CREATOR.createFromParcel(parcel);
                }
                return new AudioAttributesGroup(n, n2, arraudioAttributes);
            }

            public AudioAttributesGroup[] newArray(int n) {
                return new AudioAttributesGroup[n];
            }
        };
        private final AudioAttributes[] mAudioAttributes;
        private int mLegacyStreamType;
        private int mVolumeGroupId;

        AudioAttributesGroup(int n, int n2, AudioAttributes[] arraudioAttributes) {
            this.mVolumeGroupId = n;
            this.mLegacyStreamType = n2;
            this.mAudioAttributes = arraudioAttributes;
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
                object = (AudioAttributesGroup)object;
                if (this.mVolumeGroupId != ((AudioAttributesGroup)object).mVolumeGroupId || this.mLegacyStreamType != ((AudioAttributesGroup)object).mLegacyStreamType || !this.mAudioAttributes.equals(((AudioAttributesGroup)object).mAudioAttributes)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public AudioAttributes getAudioAttributes() {
            Object object = this.mAudioAttributes;
            object = ((AudioAttributes[])object).length == 0 ? new AudioAttributes.Builder().build() : object[0];
            return object;
        }

        public int getStreamType() {
            return this.mLegacyStreamType;
        }

        public int getVolumeGroupId() {
            return this.mVolumeGroupId;
        }

        public boolean supportsAttributes(AudioAttributes audioAttributes) {
            for (AudioAttributes audioAttributes2 : this.mAudioAttributes) {
                if (!audioAttributes2.equals(audioAttributes) && !AudioProductStrategy.attributesMatches(audioAttributes2, audioAttributes)) {
                    continue;
                }
                return true;
            }
            return false;
        }

        public boolean supportsStreamType(int n) {
            boolean bl = this.mLegacyStreamType == n;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n    Legacy Stream Type: ");
            stringBuilder.append(Integer.toString(this.mLegacyStreamType));
            stringBuilder.append(" Volume Group Id: ");
            stringBuilder.append(Integer.toString(this.mVolumeGroupId));
            for (AudioAttributes audioAttributes : this.mAudioAttributes) {
                stringBuilder.append("\n    -");
                stringBuilder.append(audioAttributes.toString());
            }
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mVolumeGroupId);
            parcel.writeInt(this.mLegacyStreamType);
            parcel.writeInt(this.mAudioAttributes.length);
            AudioAttributes[] arraudioAttributes = this.mAudioAttributes;
            int n2 = arraudioAttributes.length;
            for (int i = 0; i < n2; ++i) {
                arraudioAttributes[i].writeToParcel(parcel, n | 1);
            }
        }

    }

}

