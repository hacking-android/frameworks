/*
 * Decompiled with CFR 0.145.
 */
package android.media.soundtrigger;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger.SoundTriggerDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.app.ISoundTriggerService;
import com.android.internal.util.Preconditions;
import java.util.HashMap;
import java.util.UUID;

@SystemApi
public final class SoundTriggerManager {
    private static final boolean DBG = false;
    public static final String EXTRA_MESSAGE_TYPE = "android.media.soundtrigger.MESSAGE_TYPE";
    public static final String EXTRA_RECOGNITION_EVENT = "android.media.soundtrigger.RECOGNITION_EVENT";
    public static final String EXTRA_STATUS = "android.media.soundtrigger.STATUS";
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_ERROR = 1;
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_EVENT = 0;
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_PAUSED = 2;
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_RESUMED = 3;
    public static final int FLAG_MESSAGE_TYPE_UNKNOWN = -1;
    private static final String TAG = "SoundTriggerManager";
    private final Context mContext;
    private final HashMap<UUID, SoundTriggerDetector> mReceiverInstanceMap;
    private final ISoundTriggerService mSoundTriggerService;

    public SoundTriggerManager(Context context, ISoundTriggerService iSoundTriggerService) {
        this.mSoundTriggerService = iSoundTriggerService;
        this.mContext = context;
        this.mReceiverInstanceMap = new HashMap();
    }

    public SoundTriggerDetector createSoundTriggerDetector(UUID uUID, SoundTriggerDetector.Callback object, Handler handler) {
        if (uUID == null) {
            return null;
        }
        SoundTriggerDetector soundTriggerDetector = this.mReceiverInstanceMap.get(uUID);
        object = new SoundTriggerDetector(this.mSoundTriggerService, uUID, (SoundTriggerDetector.Callback)object, handler);
        this.mReceiverInstanceMap.put(uUID, (SoundTriggerDetector)object);
        return object;
    }

    public void deleteModel(UUID uUID) {
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(uUID);
            iSoundTriggerService.deleteSoundModel(parcelUuid);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getDetectionServiceOperationsTimeout() {
        try {
            int n = Settings.Global.getInt(this.mContext.getContentResolver(), "sound_trigger_detection_service_op_timeout");
            return n;
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            return Integer.MAX_VALUE;
        }
    }

    public Model getModel(UUID object) {
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid((UUID)object);
            object = new Model(iSoundTriggerService.getSoundModel(parcelUuid));
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getModelState(UUID uUID) {
        if (uUID == null) {
            return Integer.MIN_VALUE;
        }
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(uUID);
            int n = iSoundTriggerService.getModelState(parcelUuid);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isRecognitionActive(UUID uUID) {
        if (uUID == null) {
            return false;
        }
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(uUID);
            boolean bl = iSoundTriggerService.isRecognitionActive(parcelUuid);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public int loadSoundModel(SoundTrigger.SoundModel soundModel) {
        block5 : {
            if (soundModel == null) {
                return Integer.MIN_VALUE;
            }
            try {
                int n = soundModel.type;
                if (n == 0) return this.mSoundTriggerService.loadKeyphraseSoundModel((SoundTrigger.KeyphraseSoundModel)soundModel);
                if (n == 1) break block5;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            Slog.e(TAG, "Unkown model type");
            return Integer.MIN_VALUE;
        }
        return this.mSoundTriggerService.loadGenericSoundModel((SoundTrigger.GenericSoundModel)soundModel);
    }

    @UnsupportedAppUsage
    public int startRecognition(UUID uUID, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) {
        Preconditions.checkNotNull(uUID);
        Preconditions.checkNotNull(componentName);
        Preconditions.checkNotNull(recognitionConfig);
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(uUID);
            int n = iSoundTriggerService.startRecognitionForService(parcelUuid, bundle, componentName, recognitionConfig);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int stopRecognition(UUID uUID) {
        if (uUID == null) {
            return Integer.MIN_VALUE;
        }
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(uUID);
            int n = iSoundTriggerService.stopRecognitionForService(parcelUuid);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int unloadSoundModel(UUID uUID) {
        if (uUID == null) {
            return Integer.MIN_VALUE;
        }
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(uUID);
            int n = iSoundTriggerService.unloadSoundModel(parcelUuid);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updateModel(Model model) {
        try {
            this.mSoundTriggerService.updateSoundModel(model.getGenericSoundModel());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static class Model {
        private SoundTrigger.GenericSoundModel mGenericSoundModel;

        Model(SoundTrigger.GenericSoundModel genericSoundModel) {
            this.mGenericSoundModel = genericSoundModel;
        }

        public static Model create(UUID uUID, UUID uUID2, byte[] arrby) {
            return new Model(new SoundTrigger.GenericSoundModel(uUID, uUID2, arrby));
        }

        SoundTrigger.GenericSoundModel getGenericSoundModel() {
            return this.mGenericSoundModel;
        }

        public byte[] getModelData() {
            return this.mGenericSoundModel.data;
        }

        public UUID getModelUuid() {
            return this.mGenericSoundModel.uuid;
        }

        public UUID getVendorUuid() {
            return this.mGenericSoundModel.vendorUuid;
        }
    }

}

