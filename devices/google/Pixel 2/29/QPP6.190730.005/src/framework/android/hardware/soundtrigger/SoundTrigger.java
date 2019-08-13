/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.hardware.soundtrigger;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.hardware.soundtrigger.SoundTriggerModule;
import android.media.AudioFormat;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.OsConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@SystemApi
public class SoundTrigger {
    public static final int RECOGNITION_MODE_USER_AUTHENTICATION = 4;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    public static final int RECOGNITION_STATUS_ABORT = 1;
    public static final int RECOGNITION_STATUS_FAILURE = 2;
    public static final int RECOGNITION_STATUS_GET_STATE_RESPONSE = 3;
    public static final int RECOGNITION_STATUS_SUCCESS = 0;
    public static final int SERVICE_STATE_DISABLED = 1;
    public static final int SERVICE_STATE_ENABLED = 0;
    public static final int SOUNDMODEL_STATUS_UPDATED = 0;
    public static final int STATUS_BAD_VALUE;
    public static final int STATUS_DEAD_OBJECT;
    public static final int STATUS_ERROR = Integer.MIN_VALUE;
    public static final int STATUS_INVALID_OPERATION;
    public static final int STATUS_NO_INIT;
    public static final int STATUS_OK = 0;
    public static final int STATUS_PERMISSION_DENIED;

    static {
        STATUS_PERMISSION_DENIED = -OsConstants.EPERM;
        STATUS_NO_INIT = -OsConstants.ENODEV;
        STATUS_BAD_VALUE = -OsConstants.EINVAL;
        STATUS_DEAD_OBJECT = -OsConstants.EPIPE;
        STATUS_INVALID_OPERATION = -OsConstants.ENOSYS;
    }

    private SoundTrigger() {
    }

    @UnsupportedAppUsage
    public static SoundTriggerModule attachModule(int n, StatusListener statusListener, Handler handler) {
        if (statusListener == null) {
            return null;
        }
        return new SoundTriggerModule(n, statusListener, handler);
    }

    static String getCurrentOpPackageName() {
        String string2 = ActivityThread.currentOpPackageName();
        if (string2 == null) {
            return "";
        }
        return string2;
    }

    private static native int listModules(String var0, ArrayList<ModuleProperties> var1);

    @UnsupportedAppUsage
    public static int listModules(ArrayList<ModuleProperties> arrayList) {
        return SoundTrigger.listModules(SoundTrigger.getCurrentOpPackageName(), arrayList);
    }

    public static class ConfidenceLevel
    implements Parcelable {
        public static final Parcelable.Creator<ConfidenceLevel> CREATOR = new Parcelable.Creator<ConfidenceLevel>(){

            @Override
            public ConfidenceLevel createFromParcel(Parcel parcel) {
                return ConfidenceLevel.fromParcel(parcel);
            }

            public ConfidenceLevel[] newArray(int n) {
                return new ConfidenceLevel[n];
            }
        };
        @UnsupportedAppUsage
        public final int confidenceLevel;
        @UnsupportedAppUsage
        public final int userId;

        @UnsupportedAppUsage
        public ConfidenceLevel(int n, int n2) {
            this.userId = n;
            this.confidenceLevel = n2;
        }

        private static ConfidenceLevel fromParcel(Parcel parcel) {
            return new ConfidenceLevel(parcel.readInt(), parcel.readInt());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (ConfidenceLevel)object;
            if (this.confidenceLevel != ((ConfidenceLevel)object).confidenceLevel) {
                return false;
            }
            return this.userId == ((ConfidenceLevel)object).userId;
        }

        public int hashCode() {
            return (1 * 31 + this.confidenceLevel) * 31 + this.userId;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ConfidenceLevel [userId=");
            stringBuilder.append(this.userId);
            stringBuilder.append(", confidenceLevel=");
            stringBuilder.append(this.confidenceLevel);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.userId);
            parcel.writeInt(this.confidenceLevel);
        }

    }

    public static class GenericRecognitionEvent
    extends RecognitionEvent
    implements Parcelable {
        public static final Parcelable.Creator<GenericRecognitionEvent> CREATOR = new Parcelable.Creator<GenericRecognitionEvent>(){

            @Override
            public GenericRecognitionEvent createFromParcel(Parcel parcel) {
                return GenericRecognitionEvent.fromParcelForGeneric(parcel);
            }

            public GenericRecognitionEvent[] newArray(int n) {
                return new GenericRecognitionEvent[n];
            }
        };

        @UnsupportedAppUsage
        public GenericRecognitionEvent(int n, int n2, boolean bl, int n3, int n4, int n5, boolean bl2, AudioFormat audioFormat, byte[] arrby) {
            super(n, n2, bl, n3, n4, n5, bl2, audioFormat, arrby);
        }

        private static GenericRecognitionEvent fromParcelForGeneric(Parcel object) {
            object = RecognitionEvent.fromParcel((Parcel)object);
            return new GenericRecognitionEvent(((RecognitionEvent)object).status, ((RecognitionEvent)object).soundModelHandle, ((RecognitionEvent)object).captureAvailable, ((RecognitionEvent)object).captureSession, ((RecognitionEvent)object).captureDelayMs, ((RecognitionEvent)object).capturePreambleMs, ((RecognitionEvent)object).triggerInData, ((RecognitionEvent)object).captureFormat, ((RecognitionEvent)object).data);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            RecognitionEvent recognitionEvent = (RecognitionEvent)object;
            return super.equals(object);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("GenericRecognitionEvent ::");
            stringBuilder.append(super.toString());
            return stringBuilder.toString();
        }

    }

    public static class GenericSoundModel
    extends SoundModel
    implements Parcelable {
        public static final Parcelable.Creator<GenericSoundModel> CREATOR = new Parcelable.Creator<GenericSoundModel>(){

            @Override
            public GenericSoundModel createFromParcel(Parcel parcel) {
                return GenericSoundModel.fromParcel(parcel);
            }

            public GenericSoundModel[] newArray(int n) {
                return new GenericSoundModel[n];
            }
        };

        @UnsupportedAppUsage
        public GenericSoundModel(UUID uUID, UUID uUID2, byte[] arrby) {
            super(uUID, uUID2, 1, arrby);
        }

        private static GenericSoundModel fromParcel(Parcel parcel) {
            UUID uUID = UUID.fromString(parcel.readString());
            UUID uUID2 = null;
            if (parcel.readInt() >= 0) {
                uUID2 = UUID.fromString(parcel.readString());
            }
            return new GenericSoundModel(uUID, uUID2, parcel.readBlob());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("GenericSoundModel [uuid=");
            stringBuilder.append(this.uuid);
            stringBuilder.append(", vendorUuid=");
            stringBuilder.append(this.vendorUuid);
            stringBuilder.append(", type=");
            stringBuilder.append(this.type);
            stringBuilder.append(", data=");
            int n = this.data == null ? 0 : this.data.length;
            stringBuilder.append(n);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.uuid.toString());
            if (this.vendorUuid == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(this.vendorUuid.toString().length());
                parcel.writeString(this.vendorUuid.toString());
            }
            parcel.writeBlob(this.data);
        }

    }

    public static class Keyphrase
    implements Parcelable {
        public static final Parcelable.Creator<Keyphrase> CREATOR = new Parcelable.Creator<Keyphrase>(){

            @Override
            public Keyphrase createFromParcel(Parcel parcel) {
                return Keyphrase.fromParcel(parcel);
            }

            public Keyphrase[] newArray(int n) {
                return new Keyphrase[n];
            }
        };
        @UnsupportedAppUsage
        public final int id;
        @UnsupportedAppUsage
        public final String locale;
        @UnsupportedAppUsage
        public final int recognitionModes;
        @UnsupportedAppUsage
        public final String text;
        @UnsupportedAppUsage
        public final int[] users;

        @UnsupportedAppUsage
        public Keyphrase(int n, int n2, String string2, String string3, int[] arrn) {
            this.id = n;
            this.recognitionModes = n2;
            this.locale = string2;
            this.text = string3;
            this.users = arrn;
        }

        private static Keyphrase fromParcel(Parcel arrn) {
            int n = arrn.readInt();
            int n2 = arrn.readInt();
            String string2 = arrn.readString();
            String string3 = arrn.readString();
            int n3 = arrn.readInt();
            if (n3 >= 0) {
                int[] arrn2 = new int[n3];
                arrn.readIntArray(arrn2);
                arrn = arrn2;
            } else {
                arrn = null;
            }
            return new Keyphrase(n, n2, string2, string3, arrn);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (Keyphrase)object;
            String string2 = this.text;
            if (string2 == null ? ((Keyphrase)object).text != null : !string2.equals(((Keyphrase)object).text)) {
                return false;
            }
            if (this.id != ((Keyphrase)object).id) {
                return false;
            }
            string2 = this.locale;
            if (string2 == null ? ((Keyphrase)object).locale != null : !string2.equals(((Keyphrase)object).locale)) {
                return false;
            }
            if (this.recognitionModes != ((Keyphrase)object).recognitionModes) {
                return false;
            }
            return Arrays.equals(this.users, ((Keyphrase)object).users);
        }

        public int hashCode() {
            String string2 = this.text;
            int n = 0;
            int n2 = string2 == null ? 0 : string2.hashCode();
            int n3 = this.id;
            string2 = this.locale;
            if (string2 != null) {
                n = string2.hashCode();
            }
            return ((((1 * 31 + n2) * 31 + n3) * 31 + n) * 31 + this.recognitionModes) * 31 + Arrays.hashCode(this.users);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Keyphrase [id=");
            stringBuilder.append(this.id);
            stringBuilder.append(", recognitionModes=");
            stringBuilder.append(this.recognitionModes);
            stringBuilder.append(", locale=");
            stringBuilder.append(this.locale);
            stringBuilder.append(", text=");
            stringBuilder.append(this.text);
            stringBuilder.append(", users=");
            stringBuilder.append(Arrays.toString(this.users));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.recognitionModes);
            parcel.writeString(this.locale);
            parcel.writeString(this.text);
            int[] arrn = this.users;
            if (arrn != null) {
                parcel.writeInt(arrn.length);
                parcel.writeIntArray(this.users);
            } else {
                parcel.writeInt(-1);
            }
        }

    }

    public static class KeyphraseRecognitionEvent
    extends RecognitionEvent
    implements Parcelable {
        public static final Parcelable.Creator<KeyphraseRecognitionEvent> CREATOR = new Parcelable.Creator<KeyphraseRecognitionEvent>(){

            @Override
            public KeyphraseRecognitionEvent createFromParcel(Parcel parcel) {
                return KeyphraseRecognitionEvent.fromParcelForKeyphrase(parcel);
            }

            public KeyphraseRecognitionEvent[] newArray(int n) {
                return new KeyphraseRecognitionEvent[n];
            }
        };
        @UnsupportedAppUsage
        public final KeyphraseRecognitionExtra[] keyphraseExtras;

        @UnsupportedAppUsage
        public KeyphraseRecognitionEvent(int n, int n2, boolean bl, int n3, int n4, int n5, boolean bl2, AudioFormat audioFormat, byte[] arrby, KeyphraseRecognitionExtra[] arrkeyphraseRecognitionExtra) {
            super(n, n2, bl, n3, n4, n5, bl2, audioFormat, arrby);
            this.keyphraseExtras = arrkeyphraseRecognitionExtra;
        }

        private static KeyphraseRecognitionEvent fromParcelForKeyphrase(Parcel parcel) {
            AudioFormat audioFormat;
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            boolean bl = parcel.readByte() == 1;
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            int n5 = parcel.readInt();
            boolean bl2 = parcel.readByte() == 1;
            if (parcel.readByte() == 1) {
                int n6 = parcel.readInt();
                int n7 = parcel.readInt();
                int n8 = parcel.readInt();
                audioFormat = new AudioFormat.Builder().setChannelMask(n8).setEncoding(n7).setSampleRate(n6).build();
            } else {
                audioFormat = null;
            }
            return new KeyphraseRecognitionEvent(n, n2, bl, n3, n4, n5, bl2, audioFormat, parcel.readBlob(), parcel.createTypedArray(KeyphraseRecognitionExtra.CREATOR));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (KeyphraseRecognitionEvent)object;
            return Arrays.equals(this.keyphraseExtras, ((KeyphraseRecognitionEvent)object).keyphraseExtras);
        }

        @Override
        public int hashCode() {
            return super.hashCode() * 31 + Arrays.hashCode(this.keyphraseExtras);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("KeyphraseRecognitionEvent [keyphraseExtras=");
            stringBuilder.append(Arrays.toString(this.keyphraseExtras));
            stringBuilder.append(", status=");
            stringBuilder.append(this.status);
            stringBuilder.append(", soundModelHandle=");
            stringBuilder.append(this.soundModelHandle);
            stringBuilder.append(", captureAvailable=");
            stringBuilder.append(this.captureAvailable);
            stringBuilder.append(", captureSession=");
            stringBuilder.append(this.captureSession);
            stringBuilder.append(", captureDelayMs=");
            stringBuilder.append(this.captureDelayMs);
            stringBuilder.append(", capturePreambleMs=");
            stringBuilder.append(this.capturePreambleMs);
            stringBuilder.append(", triggerInData=");
            stringBuilder.append(this.triggerInData);
            Object object = this.captureFormat;
            String string2 = "";
            if (object == null) {
                object = "";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(", sampleRate=");
                ((StringBuilder)object).append(this.captureFormat.getSampleRate());
                object = ((StringBuilder)object).toString();
            }
            stringBuilder.append((String)object);
            if (this.captureFormat == null) {
                object = "";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(", encoding=");
                ((StringBuilder)object).append(this.captureFormat.getEncoding());
                object = ((StringBuilder)object).toString();
            }
            stringBuilder.append((String)object);
            if (this.captureFormat == null) {
                object = string2;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(", channelMask=");
                ((StringBuilder)object).append(this.captureFormat.getChannelMask());
                object = ((StringBuilder)object).toString();
            }
            stringBuilder.append((String)object);
            stringBuilder.append(", data=");
            int n = this.data == null ? 0 : this.data.length;
            stringBuilder.append(n);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.status);
            parcel.writeInt(this.soundModelHandle);
            parcel.writeByte((byte)(this.captureAvailable ? 1 : 0));
            parcel.writeInt(this.captureSession);
            parcel.writeInt(this.captureDelayMs);
            parcel.writeInt(this.capturePreambleMs);
            parcel.writeByte((byte)(this.triggerInData ? 1 : 0));
            if (this.captureFormat != null) {
                parcel.writeByte((byte)1);
                parcel.writeInt(this.captureFormat.getSampleRate());
                parcel.writeInt(this.captureFormat.getEncoding());
                parcel.writeInt(this.captureFormat.getChannelMask());
            } else {
                parcel.writeByte((byte)0);
            }
            parcel.writeBlob(this.data);
            parcel.writeTypedArray((Parcelable[])this.keyphraseExtras, n);
        }

    }

    public static class KeyphraseRecognitionExtra
    implements Parcelable {
        public static final Parcelable.Creator<KeyphraseRecognitionExtra> CREATOR = new Parcelable.Creator<KeyphraseRecognitionExtra>(){

            @Override
            public KeyphraseRecognitionExtra createFromParcel(Parcel parcel) {
                return KeyphraseRecognitionExtra.fromParcel(parcel);
            }

            public KeyphraseRecognitionExtra[] newArray(int n) {
                return new KeyphraseRecognitionExtra[n];
            }
        };
        @UnsupportedAppUsage
        public final int coarseConfidenceLevel;
        @UnsupportedAppUsage
        public final ConfidenceLevel[] confidenceLevels;
        @UnsupportedAppUsage
        public final int id;
        @UnsupportedAppUsage
        public final int recognitionModes;

        @UnsupportedAppUsage
        public KeyphraseRecognitionExtra(int n, int n2, int n3, ConfidenceLevel[] arrconfidenceLevel) {
            this.id = n;
            this.recognitionModes = n2;
            this.coarseConfidenceLevel = n3;
            this.confidenceLevels = arrconfidenceLevel;
        }

        private static KeyphraseRecognitionExtra fromParcel(Parcel parcel) {
            return new KeyphraseRecognitionExtra(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createTypedArray(ConfidenceLevel.CREATOR));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (KeyphraseRecognitionExtra)object;
            if (!Arrays.equals(this.confidenceLevels, ((KeyphraseRecognitionExtra)object).confidenceLevels)) {
                return false;
            }
            if (this.id != ((KeyphraseRecognitionExtra)object).id) {
                return false;
            }
            if (this.recognitionModes != ((KeyphraseRecognitionExtra)object).recognitionModes) {
                return false;
            }
            return this.coarseConfidenceLevel == ((KeyphraseRecognitionExtra)object).coarseConfidenceLevel;
        }

        public int hashCode() {
            return (((1 * 31 + Arrays.hashCode(this.confidenceLevels)) * 31 + this.id) * 31 + this.recognitionModes) * 31 + this.coarseConfidenceLevel;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("KeyphraseRecognitionExtra [id=");
            stringBuilder.append(this.id);
            stringBuilder.append(", recognitionModes=");
            stringBuilder.append(this.recognitionModes);
            stringBuilder.append(", coarseConfidenceLevel=");
            stringBuilder.append(this.coarseConfidenceLevel);
            stringBuilder.append(", confidenceLevels=");
            stringBuilder.append(Arrays.toString(this.confidenceLevels));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.recognitionModes);
            parcel.writeInt(this.coarseConfidenceLevel);
            parcel.writeTypedArray((Parcelable[])this.confidenceLevels, n);
        }

    }

    public static class KeyphraseSoundModel
    extends SoundModel
    implements Parcelable {
        public static final Parcelable.Creator<KeyphraseSoundModel> CREATOR = new Parcelable.Creator<KeyphraseSoundModel>(){

            @Override
            public KeyphraseSoundModel createFromParcel(Parcel parcel) {
                return KeyphraseSoundModel.fromParcel(parcel);
            }

            public KeyphraseSoundModel[] newArray(int n) {
                return new KeyphraseSoundModel[n];
            }
        };
        @UnsupportedAppUsage
        public final Keyphrase[] keyphrases;

        @UnsupportedAppUsage
        public KeyphraseSoundModel(UUID uUID, UUID uUID2, byte[] arrby, Keyphrase[] arrkeyphrase) {
            super(uUID, uUID2, 0, arrby);
            this.keyphrases = arrkeyphrase;
        }

        private static KeyphraseSoundModel fromParcel(Parcel parcel) {
            UUID uUID = UUID.fromString(parcel.readString());
            UUID uUID2 = null;
            if (parcel.readInt() >= 0) {
                uUID2 = UUID.fromString(parcel.readString());
            }
            return new KeyphraseSoundModel(uUID, uUID2, parcel.readBlob(), parcel.createTypedArray(Keyphrase.CREATOR));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            if (!(object instanceof KeyphraseSoundModel)) {
                return false;
            }
            object = (KeyphraseSoundModel)object;
            return Arrays.equals(this.keyphrases, ((KeyphraseSoundModel)object).keyphrases);
        }

        @Override
        public int hashCode() {
            return super.hashCode() * 31 + Arrays.hashCode(this.keyphrases);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("KeyphraseSoundModel [keyphrases=");
            stringBuilder.append(Arrays.toString(this.keyphrases));
            stringBuilder.append(", uuid=");
            stringBuilder.append(this.uuid);
            stringBuilder.append(", vendorUuid=");
            stringBuilder.append(this.vendorUuid);
            stringBuilder.append(", type=");
            stringBuilder.append(this.type);
            stringBuilder.append(", data=");
            int n = this.data == null ? 0 : this.data.length;
            stringBuilder.append(n);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.uuid.toString());
            if (this.vendorUuid == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(this.vendorUuid.toString().length());
                parcel.writeString(this.vendorUuid.toString());
            }
            parcel.writeBlob(this.data);
            parcel.writeTypedArray((Parcelable[])this.keyphrases, n);
        }

    }

    public static class ModuleProperties
    implements Parcelable {
        public static final Parcelable.Creator<ModuleProperties> CREATOR = new Parcelable.Creator<ModuleProperties>(){

            @Override
            public ModuleProperties createFromParcel(Parcel parcel) {
                return ModuleProperties.fromParcel(parcel);
            }

            public ModuleProperties[] newArray(int n) {
                return new ModuleProperties[n];
            }
        };
        public final String description;
        @UnsupportedAppUsage
        public final int id;
        public final String implementor;
        public final int maxBufferMs;
        public final int maxKeyphrases;
        @UnsupportedAppUsage
        public final int maxSoundModels;
        public final int maxUsers;
        public final int powerConsumptionMw;
        public final int recognitionModes;
        public final boolean returnsTriggerInEvent;
        public final boolean supportsCaptureTransition;
        public final boolean supportsConcurrentCapture;
        @UnsupportedAppUsage
        public final UUID uuid;
        public final int version;

        @UnsupportedAppUsage
        ModuleProperties(int n, String string2, String string3, String string4, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7, boolean bl2, int n8, boolean bl3) {
            this.id = n;
            this.implementor = string2;
            this.description = string3;
            this.uuid = UUID.fromString(string4);
            this.version = n2;
            this.maxSoundModels = n3;
            this.maxKeyphrases = n4;
            this.maxUsers = n5;
            this.recognitionModes = n6;
            this.supportsCaptureTransition = bl;
            this.maxBufferMs = n7;
            this.supportsConcurrentCapture = bl2;
            this.powerConsumptionMw = n8;
            this.returnsTriggerInEvent = bl3;
        }

        private static ModuleProperties fromParcel(Parcel parcel) {
            int n = parcel.readInt();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            int n5 = parcel.readInt();
            int n6 = parcel.readInt();
            boolean bl = parcel.readByte() == 1;
            int n7 = parcel.readInt();
            boolean bl2 = parcel.readByte() == 1;
            int n8 = parcel.readInt();
            boolean bl3 = parcel.readByte() == 1;
            return new ModuleProperties(n, string2, string3, string4, n2, n3, n4, n5, n6, bl, n7, bl2, n8, bl3);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ModuleProperties [id=");
            stringBuilder.append(this.id);
            stringBuilder.append(", implementor=");
            stringBuilder.append(this.implementor);
            stringBuilder.append(", description=");
            stringBuilder.append(this.description);
            stringBuilder.append(", uuid=");
            stringBuilder.append(this.uuid);
            stringBuilder.append(", version=");
            stringBuilder.append(this.version);
            stringBuilder.append(", maxSoundModels=");
            stringBuilder.append(this.maxSoundModels);
            stringBuilder.append(", maxKeyphrases=");
            stringBuilder.append(this.maxKeyphrases);
            stringBuilder.append(", maxUsers=");
            stringBuilder.append(this.maxUsers);
            stringBuilder.append(", recognitionModes=");
            stringBuilder.append(this.recognitionModes);
            stringBuilder.append(", supportsCaptureTransition=");
            stringBuilder.append(this.supportsCaptureTransition);
            stringBuilder.append(", maxBufferMs=");
            stringBuilder.append(this.maxBufferMs);
            stringBuilder.append(", supportsConcurrentCapture=");
            stringBuilder.append(this.supportsConcurrentCapture);
            stringBuilder.append(", powerConsumptionMw=");
            stringBuilder.append(this.powerConsumptionMw);
            stringBuilder.append(", returnsTriggerInEvent=");
            stringBuilder.append(this.returnsTriggerInEvent);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.id);
            parcel.writeString(this.implementor);
            parcel.writeString(this.description);
            parcel.writeString(this.uuid.toString());
            parcel.writeInt(this.version);
            parcel.writeInt(this.maxSoundModels);
            parcel.writeInt(this.maxKeyphrases);
            parcel.writeInt(this.maxUsers);
            parcel.writeInt(this.recognitionModes);
            parcel.writeByte((byte)(this.supportsCaptureTransition ? 1 : 0));
            parcel.writeInt(this.maxBufferMs);
            parcel.writeByte((byte)(this.supportsConcurrentCapture ? 1 : 0));
            parcel.writeInt(this.powerConsumptionMw);
            parcel.writeByte((byte)(this.returnsTriggerInEvent ? 1 : 0));
        }

    }

    public static class RecognitionConfig
    implements Parcelable {
        public static final Parcelable.Creator<RecognitionConfig> CREATOR = new Parcelable.Creator<RecognitionConfig>(){

            @Override
            public RecognitionConfig createFromParcel(Parcel parcel) {
                return RecognitionConfig.fromParcel(parcel);
            }

            public RecognitionConfig[] newArray(int n) {
                return new RecognitionConfig[n];
            }
        };
        public final boolean allowMultipleTriggers;
        @UnsupportedAppUsage
        public final boolean captureRequested;
        @UnsupportedAppUsage
        public final byte[] data;
        @UnsupportedAppUsage
        public final KeyphraseRecognitionExtra[] keyphrases;

        @UnsupportedAppUsage
        public RecognitionConfig(boolean bl, boolean bl2, KeyphraseRecognitionExtra[] arrkeyphraseRecognitionExtra, byte[] arrby) {
            this.captureRequested = bl;
            this.allowMultipleTriggers = bl2;
            this.keyphrases = arrkeyphraseRecognitionExtra;
            this.data = arrby;
        }

        private static RecognitionConfig fromParcel(Parcel parcel) {
            byte by = parcel.readByte();
            boolean bl = false;
            boolean bl2 = by == 1;
            if (parcel.readByte() == 1) {
                bl = true;
            }
            return new RecognitionConfig(bl2, bl, parcel.createTypedArray(KeyphraseRecognitionExtra.CREATOR), parcel.readBlob());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RecognitionConfig [captureRequested=");
            stringBuilder.append(this.captureRequested);
            stringBuilder.append(", allowMultipleTriggers=");
            stringBuilder.append(this.allowMultipleTriggers);
            stringBuilder.append(", keyphrases=");
            stringBuilder.append(Arrays.toString(this.keyphrases));
            stringBuilder.append(", data=");
            stringBuilder.append(Arrays.toString(this.data));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeByte((byte)(this.captureRequested ? 1 : 0));
            parcel.writeByte((byte)(this.allowMultipleTriggers ? 1 : 0));
            parcel.writeTypedArray((Parcelable[])this.keyphrases, n);
            parcel.writeBlob(this.data);
        }

    }

    public static class RecognitionEvent {
        public static final Parcelable.Creator<RecognitionEvent> CREATOR = new Parcelable.Creator<RecognitionEvent>(){

            @Override
            public RecognitionEvent createFromParcel(Parcel parcel) {
                return RecognitionEvent.fromParcel(parcel);
            }

            public RecognitionEvent[] newArray(int n) {
                return new RecognitionEvent[n];
            }
        };
        @UnsupportedAppUsage
        public final boolean captureAvailable;
        public final int captureDelayMs;
        public final AudioFormat captureFormat;
        public final int capturePreambleMs;
        @UnsupportedAppUsage
        public final int captureSession;
        @UnsupportedAppUsage
        public final byte[] data;
        @UnsupportedAppUsage
        public final int soundModelHandle;
        @UnsupportedAppUsage
        public final int status;
        public final boolean triggerInData;

        @UnsupportedAppUsage
        public RecognitionEvent(int n, int n2, boolean bl, int n3, int n4, int n5, boolean bl2, AudioFormat audioFormat, byte[] arrby) {
            this.status = n;
            this.soundModelHandle = n2;
            this.captureAvailable = bl;
            this.captureSession = n3;
            this.captureDelayMs = n4;
            this.capturePreambleMs = n5;
            this.triggerInData = bl2;
            this.captureFormat = audioFormat;
            this.data = arrby;
        }

        protected static RecognitionEvent fromParcel(Parcel parcel) {
            AudioFormat audioFormat;
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            boolean bl = parcel.readByte() == 1;
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            int n5 = parcel.readInt();
            boolean bl2 = parcel.readByte() == 1;
            if (parcel.readByte() == 1) {
                int n6 = parcel.readInt();
                int n7 = parcel.readInt();
                int n8 = parcel.readInt();
                audioFormat = new AudioFormat.Builder().setChannelMask(n8).setEncoding(n7).setSampleRate(n6).build();
            } else {
                audioFormat = null;
            }
            return new RecognitionEvent(n, n2, bl, n3, n4, n5, bl2, audioFormat, parcel.readBlob());
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            RecognitionEvent recognitionEvent = (RecognitionEvent)object;
            if (this.captureAvailable != recognitionEvent.captureAvailable) {
                return false;
            }
            if (this.captureDelayMs != recognitionEvent.captureDelayMs) {
                return false;
            }
            if (this.capturePreambleMs != recognitionEvent.capturePreambleMs) {
                return false;
            }
            if (this.captureSession != recognitionEvent.captureSession) {
                return false;
            }
            if (!Arrays.equals(this.data, recognitionEvent.data)) {
                return false;
            }
            if (this.soundModelHandle != recognitionEvent.soundModelHandle) {
                return false;
            }
            if (this.status != recognitionEvent.status) {
                return false;
            }
            if (this.triggerInData != recognitionEvent.triggerInData) {
                return false;
            }
            object = this.captureFormat;
            if (object == null) {
                if (recognitionEvent.captureFormat != null) {
                    return false;
                }
            } else {
                if (recognitionEvent.captureFormat == null) {
                    return false;
                }
                if (((AudioFormat)object).getSampleRate() != recognitionEvent.captureFormat.getSampleRate()) {
                    return false;
                }
                if (this.captureFormat.getEncoding() != recognitionEvent.captureFormat.getEncoding()) {
                    return false;
                }
                if (this.captureFormat.getChannelMask() != recognitionEvent.captureFormat.getChannelMask()) {
                    return false;
                }
            }
            return true;
        }

        public AudioFormat getCaptureFormat() {
            return this.captureFormat;
        }

        public int getCaptureSession() {
            return this.captureSession;
        }

        public byte[] getData() {
            return this.data;
        }

        public int hashCode() {
            boolean bl = this.captureAvailable;
            int n = 1231;
            int n2 = bl ? 1231 : 1237;
            int n3 = this.captureDelayMs;
            int n4 = this.capturePreambleMs;
            int n5 = this.captureSession;
            if (!this.triggerInData) {
                n = 1237;
            }
            n = ((((1 * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n;
            AudioFormat audioFormat = this.captureFormat;
            n2 = n;
            if (audioFormat != null) {
                n2 = ((n * 31 + audioFormat.getSampleRate()) * 31 + this.captureFormat.getEncoding()) * 31 + this.captureFormat.getChannelMask();
            }
            return ((n2 * 31 + Arrays.hashCode(this.data)) * 31 + this.soundModelHandle) * 31 + this.status;
        }

        public boolean isCaptureAvailable() {
            return this.captureAvailable;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RecognitionEvent [status=");
            stringBuilder.append(this.status);
            stringBuilder.append(", soundModelHandle=");
            stringBuilder.append(this.soundModelHandle);
            stringBuilder.append(", captureAvailable=");
            stringBuilder.append(this.captureAvailable);
            stringBuilder.append(", captureSession=");
            stringBuilder.append(this.captureSession);
            stringBuilder.append(", captureDelayMs=");
            stringBuilder.append(this.captureDelayMs);
            stringBuilder.append(", capturePreambleMs=");
            stringBuilder.append(this.capturePreambleMs);
            stringBuilder.append(", triggerInData=");
            stringBuilder.append(this.triggerInData);
            Object object = this.captureFormat;
            String string2 = "";
            if (object == null) {
                object = "";
            } else {
                object = new StringBuilder();
                object.append(", sampleRate=");
                object.append(this.captureFormat.getSampleRate());
                object = object.toString();
            }
            stringBuilder.append((String)object);
            if (this.captureFormat == null) {
                object = "";
            } else {
                object = new StringBuilder();
                object.append(", encoding=");
                object.append(this.captureFormat.getEncoding());
                object = object.toString();
            }
            stringBuilder.append((String)object);
            if (this.captureFormat == null) {
                object = string2;
            } else {
                object = new StringBuilder();
                object.append(", channelMask=");
                object.append(this.captureFormat.getChannelMask());
                object = object.toString();
            }
            stringBuilder.append((String)object);
            stringBuilder.append(", data=");
            object = this.data;
            int n = object == null ? 0 : ((byte[])object).length;
            stringBuilder.append(n);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.status);
            parcel.writeInt(this.soundModelHandle);
            parcel.writeByte((byte)(this.captureAvailable ? 1 : 0));
            parcel.writeInt(this.captureSession);
            parcel.writeInt(this.captureDelayMs);
            parcel.writeInt(this.capturePreambleMs);
            parcel.writeByte((byte)(this.triggerInData ? 1 : 0));
            if (this.captureFormat != null) {
                parcel.writeByte((byte)1);
                parcel.writeInt(this.captureFormat.getSampleRate());
                parcel.writeInt(this.captureFormat.getEncoding());
                parcel.writeInt(this.captureFormat.getChannelMask());
            } else {
                parcel.writeByte((byte)0);
            }
            parcel.writeBlob(this.data);
        }

    }

    public static class SoundModel {
        public static final int TYPE_GENERIC_SOUND = 1;
        public static final int TYPE_KEYPHRASE = 0;
        public static final int TYPE_UNKNOWN = -1;
        @UnsupportedAppUsage
        public final byte[] data;
        public final int type;
        @UnsupportedAppUsage
        public final UUID uuid;
        @UnsupportedAppUsage
        public final UUID vendorUuid;

        public SoundModel(UUID uUID, UUID uUID2, int n, byte[] arrby) {
            this.uuid = uUID;
            this.vendorUuid = uUID2;
            this.type = n;
            this.data = arrby;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (!(object instanceof SoundModel)) {
                return false;
            }
            object = (SoundModel)object;
            if (!Arrays.equals(this.data, ((SoundModel)object).data)) {
                return false;
            }
            if (this.type != ((SoundModel)object).type) {
                return false;
            }
            UUID uUID = this.uuid;
            if (uUID == null ? ((SoundModel)object).uuid != null : !uUID.equals(((SoundModel)object).uuid)) {
                return false;
            }
            uUID = this.vendorUuid;
            return !(uUID == null ? ((SoundModel)object).vendorUuid != null : !uUID.equals(((SoundModel)object).vendorUuid));
        }

        public int hashCode() {
            int n = Arrays.hashCode(this.data);
            int n2 = this.type;
            UUID uUID = this.uuid;
            int n3 = 0;
            int n4 = uUID == null ? 0 : uUID.hashCode();
            uUID = this.vendorUuid;
            if (uUID != null) {
                n3 = uUID.hashCode();
            }
            return (((1 * 31 + n) * 31 + n2) * 31 + n4) * 31 + n3;
        }
    }

    public static class SoundModelEvent
    implements Parcelable {
        public static final Parcelable.Creator<SoundModelEvent> CREATOR = new Parcelable.Creator<SoundModelEvent>(){

            @Override
            public SoundModelEvent createFromParcel(Parcel parcel) {
                return SoundModelEvent.fromParcel(parcel);
            }

            public SoundModelEvent[] newArray(int n) {
                return new SoundModelEvent[n];
            }
        };
        public final byte[] data;
        public final int soundModelHandle;
        public final int status;

        @UnsupportedAppUsage
        SoundModelEvent(int n, int n2, byte[] arrby) {
            this.status = n;
            this.soundModelHandle = n2;
            this.data = arrby;
        }

        private static SoundModelEvent fromParcel(Parcel parcel) {
            return new SoundModelEvent(parcel.readInt(), parcel.readInt(), parcel.readBlob());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (SoundModelEvent)object;
            if (!Arrays.equals(this.data, ((SoundModelEvent)object).data)) {
                return false;
            }
            if (this.soundModelHandle != ((SoundModelEvent)object).soundModelHandle) {
                return false;
            }
            return this.status == ((SoundModelEvent)object).status;
        }

        public int hashCode() {
            return ((1 * 31 + Arrays.hashCode(this.data)) * 31 + this.soundModelHandle) * 31 + this.status;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SoundModelEvent [status=");
            stringBuilder.append(this.status);
            stringBuilder.append(", soundModelHandle=");
            stringBuilder.append(this.soundModelHandle);
            stringBuilder.append(", data=");
            byte[] arrby = this.data;
            int n = arrby == null ? 0 : arrby.length;
            stringBuilder.append(n);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.status);
            parcel.writeInt(this.soundModelHandle);
            parcel.writeBlob(this.data);
        }

    }

    public static interface StatusListener {
        public void onRecognition(RecognitionEvent var1);

        public void onServiceDied();

        public void onServiceStateChange(int var1);

        public void onSoundModelUpdate(SoundModelEvent var1);
    }

}

