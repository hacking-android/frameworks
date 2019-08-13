/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.media.AudioAttributes;
import android.media.AudioFocusInfo;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.media.AudioRoutesInfo;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioRoutesObserver;
import android.media.IAudioServerStateDispatcher;
import android.media.IPlaybackConfigDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.IRingtonePlayer;
import android.media.IVolumeController;
import android.media.PlayerBase;
import android.media.VolumePolicy;
import android.media.audiopolicy.AudioPolicyConfig;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.audiopolicy.AudioVolumeGroup;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.projection.IMediaProjection;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IAudioService
extends IInterface {
    public int abandonAudioFocus(IAudioFocusDispatcher var1, String var2, AudioAttributes var3, String var4) throws RemoteException;

    public int addMixForPolicy(AudioPolicyConfig var1, IAudioPolicyCallback var2) throws RemoteException;

    public void adjustStreamVolume(int var1, int var2, int var3, String var4) throws RemoteException;

    public void adjustSuggestedStreamVolume(int var1, int var2, int var3, String var4, String var5) throws RemoteException;

    public void avrcpSupportsAbsoluteVolume(String var1, boolean var2) throws RemoteException;

    public void disableRingtoneSync(int var1) throws RemoteException;

    public void disableSafeMediaVolume(String var1) throws RemoteException;

    public int dispatchFocusChange(AudioFocusInfo var1, int var2, IAudioPolicyCallback var3) throws RemoteException;

    public void forceRemoteSubmixFullVolume(boolean var1, IBinder var2) throws RemoteException;

    public void forceVolumeControlStream(int var1, IBinder var2) throws RemoteException;

    public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException;

    public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException;

    public List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException;

    public List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException;

    public int getCurrentAudioFocus() throws RemoteException;

    public int getFocusRampTimeMs(int var1, AudioAttributes var2) throws RemoteException;

    public int getLastAudibleStreamVolume(int var1) throws RemoteException;

    public int getMaxVolumeIndexForAttributes(AudioAttributes var1) throws RemoteException;

    public int getMinVolumeIndexForAttributes(AudioAttributes var1) throws RemoteException;

    public int getMode() throws RemoteException;

    public int getRingerModeExternal() throws RemoteException;

    public int getRingerModeInternal() throws RemoteException;

    public IRingtonePlayer getRingtonePlayer() throws RemoteException;

    @UnsupportedAppUsage
    public int getStreamMaxVolume(int var1) throws RemoteException;

    public int getStreamMinVolume(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getStreamVolume(int var1) throws RemoteException;

    public int getUiSoundsStreamType() throws RemoteException;

    public int getVibrateSetting(int var1) throws RemoteException;

    public int getVolumeIndexForAttributes(AudioAttributes var1) throws RemoteException;

    public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice var1) throws RemoteException;

    public boolean hasHapticChannels(Uri var1) throws RemoteException;

    public boolean hasRegisteredDynamicPolicy() throws RemoteException;

    public boolean isAudioServerRunning() throws RemoteException;

    public boolean isBluetoothA2dpOn() throws RemoteException;

    public boolean isBluetoothScoOn() throws RemoteException;

    public boolean isCameraSoundForced() throws RemoteException;

    public boolean isHdmiSystemAudioSupported() throws RemoteException;

    public boolean isMasterMute() throws RemoteException;

    public boolean isSpeakerphoneOn() throws RemoteException;

    public boolean isStreamAffectedByMute(int var1) throws RemoteException;

    public boolean isStreamAffectedByRingerMode(int var1) throws RemoteException;

    public boolean isStreamMute(int var1) throws RemoteException;

    public boolean isValidRingerMode(int var1) throws RemoteException;

    public boolean loadSoundEffects() throws RemoteException;

    public void notifyVolumeControllerVisible(IVolumeController var1, boolean var2) throws RemoteException;

    public void playSoundEffect(int var1) throws RemoteException;

    public void playSoundEffectVolume(int var1, float var2) throws RemoteException;

    public void playerAttributes(int var1, AudioAttributes var2) throws RemoteException;

    public void playerEvent(int var1, int var2) throws RemoteException;

    public void playerHasOpPlayAudio(int var1, boolean var2) throws RemoteException;

    public void recorderEvent(int var1, int var2) throws RemoteException;

    public String registerAudioPolicy(AudioPolicyConfig var1, IAudioPolicyCallback var2, boolean var3, boolean var4, boolean var5, boolean var6, IMediaProjection var7) throws RemoteException;

    public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher var1) throws RemoteException;

    public void registerPlaybackCallback(IPlaybackConfigDispatcher var1) throws RemoteException;

    public void registerRecordingCallback(IRecordingConfigDispatcher var1) throws RemoteException;

    public void releasePlayer(int var1) throws RemoteException;

    public void releaseRecorder(int var1) throws RemoteException;

    public void reloadAudioSettings() throws RemoteException;

    public int removeMixForPolicy(AudioPolicyConfig var1, IAudioPolicyCallback var2) throws RemoteException;

    public int removeUidDeviceAffinity(IAudioPolicyCallback var1, int var2) throws RemoteException;

    public int requestAudioFocus(AudioAttributes var1, int var2, IBinder var3, IAudioFocusDispatcher var4, String var5, String var6, int var7, IAudioPolicyCallback var8, int var9) throws RemoteException;

    public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice var1, int var2, int var3, boolean var4, int var5) throws RemoteException;

    public void setBluetoothA2dpOn(boolean var1) throws RemoteException;

    public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice var1, int var2, boolean var3, int var4) throws RemoteException;

    public void setBluetoothScoOn(boolean var1) throws RemoteException;

    public int setFocusPropertiesForPolicy(int var1, IAudioPolicyCallback var2) throws RemoteException;

    public void setFocusRequestResultFromExtPolicy(AudioFocusInfo var1, int var2, IAudioPolicyCallback var3) throws RemoteException;

    public int setHdmiSystemAudioSupported(boolean var1) throws RemoteException;

    public void setMasterMute(boolean var1, int var2, String var3, int var4) throws RemoteException;

    public void setMicrophoneMute(boolean var1, String var2, int var3) throws RemoteException;

    public void setMode(int var1, IBinder var2, String var3) throws RemoteException;

    public void setRingerModeExternal(int var1, String var2) throws RemoteException;

    public void setRingerModeInternal(int var1, String var2) throws RemoteException;

    public void setRingtonePlayer(IRingtonePlayer var1) throws RemoteException;

    public void setSpeakerphoneOn(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setStreamVolume(int var1, int var2, int var3, String var4) throws RemoteException;

    public int setUidDeviceAffinity(IAudioPolicyCallback var1, int var2, int[] var3, String[] var4) throws RemoteException;

    public void setVibrateSetting(int var1, int var2) throws RemoteException;

    public void setVolumeController(IVolumeController var1) throws RemoteException;

    public void setVolumeIndexForAttributes(AudioAttributes var1, int var2, int var3, String var4) throws RemoteException;

    public void setVolumePolicy(VolumePolicy var1) throws RemoteException;

    public void setWiredDeviceConnectionState(int var1, int var2, String var3, String var4, String var5) throws RemoteException;

    public boolean shouldVibrate(int var1) throws RemoteException;

    public void startBluetoothSco(IBinder var1, int var2) throws RemoteException;

    public void startBluetoothScoVirtualCall(IBinder var1) throws RemoteException;

    @UnsupportedAppUsage
    public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver var1) throws RemoteException;

    public void stopBluetoothSco(IBinder var1) throws RemoteException;

    public int trackPlayer(PlayerBase.PlayerIdCard var1) throws RemoteException;

    public int trackRecorder(IBinder var1) throws RemoteException;

    public void unloadSoundEffects() throws RemoteException;

    public void unregisterAudioFocusClient(String var1) throws RemoteException;

    public void unregisterAudioPolicy(IAudioPolicyCallback var1) throws RemoteException;

    public void unregisterAudioPolicyAsync(IAudioPolicyCallback var1) throws RemoteException;

    public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher var1) throws RemoteException;

    public void unregisterPlaybackCallback(IPlaybackConfigDispatcher var1) throws RemoteException;

    public void unregisterRecordingCallback(IRecordingConfigDispatcher var1) throws RemoteException;

    public static class Default
    implements IAudioService {
        @Override
        public int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String string2, AudioAttributes audioAttributes, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public int addMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override
        public void adjustStreamVolume(int n, int n2, int n3, String string2) throws RemoteException {
        }

        @Override
        public void adjustSuggestedStreamVolume(int n, int n2, int n3, String string2, String string3) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void avrcpSupportsAbsoluteVolume(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void disableRingtoneSync(int n) throws RemoteException {
        }

        @Override
        public void disableSafeMediaVolume(String string2) throws RemoteException {
        }

        @Override
        public int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int n, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override
        public void forceRemoteSubmixFullVolume(boolean bl, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void forceVolumeControlStream(int n, IBinder iBinder) throws RemoteException {
        }

        @Override
        public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
            return null;
        }

        @Override
        public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
            return null;
        }

        @Override
        public List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
            return null;
        }

        @Override
        public List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
            return null;
        }

        @Override
        public int getCurrentAudioFocus() throws RemoteException {
            return 0;
        }

        @Override
        public int getFocusRampTimeMs(int n, AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override
        public int getLastAudibleStreamVolume(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getMaxVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override
        public int getMinVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override
        public int getMode() throws RemoteException {
            return 0;
        }

        @Override
        public int getRingerModeExternal() throws RemoteException {
            return 0;
        }

        @Override
        public int getRingerModeInternal() throws RemoteException {
            return 0;
        }

        @Override
        public IRingtonePlayer getRingtonePlayer() throws RemoteException {
            return null;
        }

        @Override
        public int getStreamMaxVolume(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getStreamMinVolume(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getStreamVolume(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getUiSoundsStreamType() throws RemoteException {
            return 0;
        }

        @Override
        public int getVibrateSetting(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override
        public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice bluetoothDevice) throws RemoteException {
        }

        @Override
        public boolean hasHapticChannels(Uri uri) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasRegisteredDynamicPolicy() throws RemoteException {
            return false;
        }

        @Override
        public boolean isAudioServerRunning() throws RemoteException {
            return false;
        }

        @Override
        public boolean isBluetoothA2dpOn() throws RemoteException {
            return false;
        }

        @Override
        public boolean isBluetoothScoOn() throws RemoteException {
            return false;
        }

        @Override
        public boolean isCameraSoundForced() throws RemoteException {
            return false;
        }

        @Override
        public boolean isHdmiSystemAudioSupported() throws RemoteException {
            return false;
        }

        @Override
        public boolean isMasterMute() throws RemoteException {
            return false;
        }

        @Override
        public boolean isSpeakerphoneOn() throws RemoteException {
            return false;
        }

        @Override
        public boolean isStreamAffectedByMute(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isStreamAffectedByRingerMode(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isStreamMute(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isValidRingerMode(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean loadSoundEffects() throws RemoteException {
            return false;
        }

        @Override
        public void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean bl) throws RemoteException {
        }

        @Override
        public void playSoundEffect(int n) throws RemoteException {
        }

        @Override
        public void playSoundEffectVolume(int n, float f) throws RemoteException {
        }

        @Override
        public void playerAttributes(int n, AudioAttributes audioAttributes) throws RemoteException {
        }

        @Override
        public void playerEvent(int n, int n2) throws RemoteException {
        }

        @Override
        public void playerHasOpPlayAudio(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void recorderEvent(int n, int n2) throws RemoteException {
        }

        @Override
        public String registerAudioPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean bl, boolean bl2, boolean bl3, boolean bl4, IMediaProjection iMediaProjection) throws RemoteException {
            return null;
        }

        @Override
        public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
        }

        @Override
        public void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
        }

        @Override
        public void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
        }

        @Override
        public void releasePlayer(int n) throws RemoteException {
        }

        @Override
        public void releaseRecorder(int n) throws RemoteException {
        }

        @Override
        public void reloadAudioSettings() throws RemoteException {
        }

        @Override
        public int removeMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override
        public int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int requestAudioFocus(AudioAttributes audioAttributes, int n, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String string2, String string3, int n2, IAudioPolicyCallback iAudioPolicyCallback, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice bluetoothDevice, int n, int n2, boolean bl, int n3) throws RemoteException {
        }

        @Override
        public void setBluetoothA2dpOn(boolean bl) throws RemoteException {
        }

        @Override
        public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice bluetoothDevice, int n, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void setBluetoothScoOn(boolean bl) throws RemoteException {
        }

        @Override
        public int setFocusPropertiesForPolicy(int n, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override
        public void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int n, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
        }

        @Override
        public int setHdmiSystemAudioSupported(boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public void setMasterMute(boolean bl, int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void setMicrophoneMute(boolean bl, String string2, int n) throws RemoteException {
        }

        @Override
        public void setMode(int n, IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public void setRingerModeExternal(int n, String string2) throws RemoteException {
        }

        @Override
        public void setRingerModeInternal(int n, String string2) throws RemoteException {
        }

        @Override
        public void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) throws RemoteException {
        }

        @Override
        public void setSpeakerphoneOn(boolean bl) throws RemoteException {
        }

        @Override
        public void setStreamVolume(int n, int n2, int n3, String string2) throws RemoteException {
        }

        @Override
        public int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int n, int[] arrn, String[] arrstring) throws RemoteException {
            return 0;
        }

        @Override
        public void setVibrateSetting(int n, int n2) throws RemoteException {
        }

        @Override
        public void setVolumeController(IVolumeController iVolumeController) throws RemoteException {
        }

        @Override
        public void setVolumeIndexForAttributes(AudioAttributes audioAttributes, int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException {
        }

        @Override
        public void setWiredDeviceConnectionState(int n, int n2, String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public boolean shouldVibrate(int n) throws RemoteException {
            return false;
        }

        @Override
        public void startBluetoothSco(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void startBluetoothScoVirtualCall(IBinder iBinder) throws RemoteException {
        }

        @Override
        public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) throws RemoteException {
            return null;
        }

        @Override
        public void stopBluetoothSco(IBinder iBinder) throws RemoteException {
        }

        @Override
        public int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException {
            return 0;
        }

        @Override
        public int trackRecorder(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public void unloadSoundEffects() throws RemoteException {
        }

        @Override
        public void unregisterAudioFocusClient(String string2) throws RemoteException {
        }

        @Override
        public void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
        }

        @Override
        public void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
        }

        @Override
        public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
        }

        @Override
        public void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
        }

        @Override
        public void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAudioService {
        private static final String DESCRIPTOR = "android.media.IAudioService";
        static final int TRANSACTION_abandonAudioFocus = 49;
        static final int TRANSACTION_addMixForPolicy = 73;
        static final int TRANSACTION_adjustStreamVolume = 9;
        static final int TRANSACTION_adjustSuggestedStreamVolume = 8;
        static final int TRANSACTION_avrcpSupportsAbsoluteVolume = 41;
        static final int TRANSACTION_disableRingtoneSync = 84;
        static final int TRANSACTION_disableSafeMediaVolume = 67;
        static final int TRANSACTION_dispatchFocusChange = 86;
        static final int TRANSACTION_forceRemoteSubmixFullVolume = 12;
        static final int TRANSACTION_forceVolumeControlStream = 55;
        static final int TRANSACTION_getActivePlaybackConfigurations = 83;
        static final int TRANSACTION_getActiveRecordingConfigurations = 80;
        static final int TRANSACTION_getAudioProductStrategies = 24;
        static final int TRANSACTION_getAudioVolumeGroups = 18;
        static final int TRANSACTION_getCurrentAudioFocus = 51;
        static final int TRANSACTION_getFocusRampTimeMs = 85;
        static final int TRANSACTION_getLastAudibleStreamVolume = 23;
        static final int TRANSACTION_getMaxVolumeIndexForAttributes = 21;
        static final int TRANSACTION_getMinVolumeIndexForAttributes = 22;
        static final int TRANSACTION_getMode = 35;
        static final int TRANSACTION_getRingerModeExternal = 28;
        static final int TRANSACTION_getRingerModeInternal = 29;
        static final int TRANSACTION_getRingtonePlayer = 57;
        static final int TRANSACTION_getStreamMaxVolume = 17;
        static final int TRANSACTION_getStreamMinVolume = 16;
        static final int TRANSACTION_getStreamVolume = 15;
        static final int TRANSACTION_getUiSoundsStreamType = 58;
        static final int TRANSACTION_getVibrateSetting = 32;
        static final int TRANSACTION_getVolumeIndexForAttributes = 20;
        static final int TRANSACTION_handleBluetoothA2dpDeviceConfigChange = 60;
        static final int TRANSACTION_hasHapticChannels = 96;
        static final int TRANSACTION_hasRegisteredDynamicPolicy = 77;
        static final int TRANSACTION_isAudioServerRunning = 93;
        static final int TRANSACTION_isBluetoothA2dpOn = 47;
        static final int TRANSACTION_isBluetoothScoOn = 45;
        static final int TRANSACTION_isCameraSoundForced = 62;
        static final int TRANSACTION_isHdmiSystemAudioSupported = 69;
        static final int TRANSACTION_isMasterMute = 13;
        static final int TRANSACTION_isSpeakerphoneOn = 43;
        static final int TRANSACTION_isStreamAffectedByMute = 66;
        static final int TRANSACTION_isStreamAffectedByRingerMode = 65;
        static final int TRANSACTION_isStreamMute = 11;
        static final int TRANSACTION_isValidRingerMode = 30;
        static final int TRANSACTION_loadSoundEffects = 38;
        static final int TRANSACTION_notifyVolumeControllerVisible = 64;
        static final int TRANSACTION_playSoundEffect = 36;
        static final int TRANSACTION_playSoundEffectVolume = 37;
        static final int TRANSACTION_playerAttributes = 2;
        static final int TRANSACTION_playerEvent = 3;
        static final int TRANSACTION_playerHasOpPlayAudio = 87;
        static final int TRANSACTION_recorderEvent = 6;
        static final int TRANSACTION_registerAudioPolicy = 70;
        static final int TRANSACTION_registerAudioServerStateDispatcher = 91;
        static final int TRANSACTION_registerPlaybackCallback = 81;
        static final int TRANSACTION_registerRecordingCallback = 78;
        static final int TRANSACTION_releasePlayer = 4;
        static final int TRANSACTION_releaseRecorder = 7;
        static final int TRANSACTION_reloadAudioSettings = 40;
        static final int TRANSACTION_removeMixForPolicy = 74;
        static final int TRANSACTION_removeUidDeviceAffinity = 95;
        static final int TRANSACTION_requestAudioFocus = 48;
        static final int TRANSACTION_setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent = 89;
        static final int TRANSACTION_setBluetoothA2dpOn = 46;
        static final int TRANSACTION_setBluetoothHearingAidDeviceConnectionState = 88;
        static final int TRANSACTION_setBluetoothScoOn = 44;
        static final int TRANSACTION_setFocusPropertiesForPolicy = 75;
        static final int TRANSACTION_setFocusRequestResultFromExtPolicy = 90;
        static final int TRANSACTION_setHdmiSystemAudioSupported = 68;
        static final int TRANSACTION_setMasterMute = 14;
        static final int TRANSACTION_setMicrophoneMute = 25;
        static final int TRANSACTION_setMode = 34;
        static final int TRANSACTION_setRingerModeExternal = 26;
        static final int TRANSACTION_setRingerModeInternal = 27;
        static final int TRANSACTION_setRingtonePlayer = 56;
        static final int TRANSACTION_setSpeakerphoneOn = 42;
        static final int TRANSACTION_setStreamVolume = 10;
        static final int TRANSACTION_setUidDeviceAffinity = 94;
        static final int TRANSACTION_setVibrateSetting = 31;
        static final int TRANSACTION_setVolumeController = 63;
        static final int TRANSACTION_setVolumeIndexForAttributes = 19;
        static final int TRANSACTION_setVolumePolicy = 76;
        static final int TRANSACTION_setWiredDeviceConnectionState = 59;
        static final int TRANSACTION_shouldVibrate = 33;
        static final int TRANSACTION_startBluetoothSco = 52;
        static final int TRANSACTION_startBluetoothScoVirtualCall = 53;
        static final int TRANSACTION_startWatchingRoutes = 61;
        static final int TRANSACTION_stopBluetoothSco = 54;
        static final int TRANSACTION_trackPlayer = 1;
        static final int TRANSACTION_trackRecorder = 5;
        static final int TRANSACTION_unloadSoundEffects = 39;
        static final int TRANSACTION_unregisterAudioFocusClient = 50;
        static final int TRANSACTION_unregisterAudioPolicy = 72;
        static final int TRANSACTION_unregisterAudioPolicyAsync = 71;
        static final int TRANSACTION_unregisterAudioServerStateDispatcher = 92;
        static final int TRANSACTION_unregisterPlaybackCallback = 82;
        static final int TRANSACTION_unregisterRecordingCallback = 79;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAudioService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAudioService) {
                return (IAudioService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAudioService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 96: {
                    return "hasHapticChannels";
                }
                case 95: {
                    return "removeUidDeviceAffinity";
                }
                case 94: {
                    return "setUidDeviceAffinity";
                }
                case 93: {
                    return "isAudioServerRunning";
                }
                case 92: {
                    return "unregisterAudioServerStateDispatcher";
                }
                case 91: {
                    return "registerAudioServerStateDispatcher";
                }
                case 90: {
                    return "setFocusRequestResultFromExtPolicy";
                }
                case 89: {
                    return "setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent";
                }
                case 88: {
                    return "setBluetoothHearingAidDeviceConnectionState";
                }
                case 87: {
                    return "playerHasOpPlayAudio";
                }
                case 86: {
                    return "dispatchFocusChange";
                }
                case 85: {
                    return "getFocusRampTimeMs";
                }
                case 84: {
                    return "disableRingtoneSync";
                }
                case 83: {
                    return "getActivePlaybackConfigurations";
                }
                case 82: {
                    return "unregisterPlaybackCallback";
                }
                case 81: {
                    return "registerPlaybackCallback";
                }
                case 80: {
                    return "getActiveRecordingConfigurations";
                }
                case 79: {
                    return "unregisterRecordingCallback";
                }
                case 78: {
                    return "registerRecordingCallback";
                }
                case 77: {
                    return "hasRegisteredDynamicPolicy";
                }
                case 76: {
                    return "setVolumePolicy";
                }
                case 75: {
                    return "setFocusPropertiesForPolicy";
                }
                case 74: {
                    return "removeMixForPolicy";
                }
                case 73: {
                    return "addMixForPolicy";
                }
                case 72: {
                    return "unregisterAudioPolicy";
                }
                case 71: {
                    return "unregisterAudioPolicyAsync";
                }
                case 70: {
                    return "registerAudioPolicy";
                }
                case 69: {
                    return "isHdmiSystemAudioSupported";
                }
                case 68: {
                    return "setHdmiSystemAudioSupported";
                }
                case 67: {
                    return "disableSafeMediaVolume";
                }
                case 66: {
                    return "isStreamAffectedByMute";
                }
                case 65: {
                    return "isStreamAffectedByRingerMode";
                }
                case 64: {
                    return "notifyVolumeControllerVisible";
                }
                case 63: {
                    return "setVolumeController";
                }
                case 62: {
                    return "isCameraSoundForced";
                }
                case 61: {
                    return "startWatchingRoutes";
                }
                case 60: {
                    return "handleBluetoothA2dpDeviceConfigChange";
                }
                case 59: {
                    return "setWiredDeviceConnectionState";
                }
                case 58: {
                    return "getUiSoundsStreamType";
                }
                case 57: {
                    return "getRingtonePlayer";
                }
                case 56: {
                    return "setRingtonePlayer";
                }
                case 55: {
                    return "forceVolumeControlStream";
                }
                case 54: {
                    return "stopBluetoothSco";
                }
                case 53: {
                    return "startBluetoothScoVirtualCall";
                }
                case 52: {
                    return "startBluetoothSco";
                }
                case 51: {
                    return "getCurrentAudioFocus";
                }
                case 50: {
                    return "unregisterAudioFocusClient";
                }
                case 49: {
                    return "abandonAudioFocus";
                }
                case 48: {
                    return "requestAudioFocus";
                }
                case 47: {
                    return "isBluetoothA2dpOn";
                }
                case 46: {
                    return "setBluetoothA2dpOn";
                }
                case 45: {
                    return "isBluetoothScoOn";
                }
                case 44: {
                    return "setBluetoothScoOn";
                }
                case 43: {
                    return "isSpeakerphoneOn";
                }
                case 42: {
                    return "setSpeakerphoneOn";
                }
                case 41: {
                    return "avrcpSupportsAbsoluteVolume";
                }
                case 40: {
                    return "reloadAudioSettings";
                }
                case 39: {
                    return "unloadSoundEffects";
                }
                case 38: {
                    return "loadSoundEffects";
                }
                case 37: {
                    return "playSoundEffectVolume";
                }
                case 36: {
                    return "playSoundEffect";
                }
                case 35: {
                    return "getMode";
                }
                case 34: {
                    return "setMode";
                }
                case 33: {
                    return "shouldVibrate";
                }
                case 32: {
                    return "getVibrateSetting";
                }
                case 31: {
                    return "setVibrateSetting";
                }
                case 30: {
                    return "isValidRingerMode";
                }
                case 29: {
                    return "getRingerModeInternal";
                }
                case 28: {
                    return "getRingerModeExternal";
                }
                case 27: {
                    return "setRingerModeInternal";
                }
                case 26: {
                    return "setRingerModeExternal";
                }
                case 25: {
                    return "setMicrophoneMute";
                }
                case 24: {
                    return "getAudioProductStrategies";
                }
                case 23: {
                    return "getLastAudibleStreamVolume";
                }
                case 22: {
                    return "getMinVolumeIndexForAttributes";
                }
                case 21: {
                    return "getMaxVolumeIndexForAttributes";
                }
                case 20: {
                    return "getVolumeIndexForAttributes";
                }
                case 19: {
                    return "setVolumeIndexForAttributes";
                }
                case 18: {
                    return "getAudioVolumeGroups";
                }
                case 17: {
                    return "getStreamMaxVolume";
                }
                case 16: {
                    return "getStreamMinVolume";
                }
                case 15: {
                    return "getStreamVolume";
                }
                case 14: {
                    return "setMasterMute";
                }
                case 13: {
                    return "isMasterMute";
                }
                case 12: {
                    return "forceRemoteSubmixFullVolume";
                }
                case 11: {
                    return "isStreamMute";
                }
                case 10: {
                    return "setStreamVolume";
                }
                case 9: {
                    return "adjustStreamVolume";
                }
                case 8: {
                    return "adjustSuggestedStreamVolume";
                }
                case 7: {
                    return "releaseRecorder";
                }
                case 6: {
                    return "recorderEvent";
                }
                case 5: {
                    return "trackRecorder";
                }
                case 4: {
                    return "releasePlayer";
                }
                case 3: {
                    return "playerEvent";
                }
                case 2: {
                    return "playerAttributes";
                }
                case 1: 
            }
            return "trackPlayer";
        }

        public static boolean setDefaultImpl(IAudioService iAudioService) {
            if (Proxy.sDefaultImpl == null && iAudioService != null) {
                Proxy.sDefaultImpl = iAudioService;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = false;
                boolean bl8 = false;
                boolean bl9 = false;
                boolean bl10 = false;
                boolean bl11 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.hasHapticChannels((Uri)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeUidDeviceAffinity(IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setUidDeviceAffinity(IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).createIntArray(), ((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 93: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAudioServerRunning() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerAudioServerStateDispatcher(IAudioServerStateDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setFocusRequestResultFromExtPolicy((AudioFocusInfo)object2, ((Parcel)object).readInt(), IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl11 = ((Parcel)object).readInt() != 0;
                        this.setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(bluetoothDevice, n, n2, bl11, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setBluetoothHearingAidDeviceConnectionState(bluetoothDevice, n, bl11, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl11 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.playerHasOpPlayAudio(n, bl11);
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AudioFocusInfo audioFocusInfo = ((Parcel)object).readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.dispatchFocusChange(audioFocusInfo, ((Parcel)object).readInt(), IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getFocusRampTimeMs(n, (AudioAttributes)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableRingtoneSync(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActivePlaybackConfigurations();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterPlaybackCallback(IPlaybackConfigDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerPlaybackCallback(IPlaybackConfigDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveRecordingConfigurations();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterRecordingCallback(IRecordingConfigDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerRecordingCallback(IRecordingConfigDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasRegisteredDynamicPolicy() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? VolumePolicy.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setVolumePolicy((VolumePolicy)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setFocusPropertiesForPolicy(((Parcel)object).readInt(), IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AudioPolicyConfig audioPolicyConfig = ((Parcel)object).readInt() != 0 ? AudioPolicyConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeMixForPolicy(audioPolicyConfig, IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AudioPolicyConfig audioPolicyConfig = ((Parcel)object).readInt() != 0 ? AudioPolicyConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addMixForPolicy(audioPolicyConfig, IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterAudioPolicy(IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterAudioPolicyAsync(IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AudioPolicyConfig audioPolicyConfig = ((Parcel)object).readInt() != 0 ? AudioPolicyConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        IAudioPolicyCallback iAudioPolicyCallback = IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl11 = ((Parcel)object).readInt() != 0;
                        bl = ((Parcel)object).readInt() != 0;
                        bl2 = ((Parcel)object).readInt() != 0;
                        bl3 = ((Parcel)object).readInt() != 0;
                        object = this.registerAudioPolicy(audioPolicyConfig, iAudioPolicyCallback, bl11, bl, bl2, bl3, IMediaProjection.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isHdmiSystemAudioSupported() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl11 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        n = this.setHdmiSystemAudioSupported(bl11);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableSafeMediaVolume(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isStreamAffectedByMute(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isStreamAffectedByRingerMode(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IVolumeController iVolumeController = IVolumeController.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl11 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.notifyVolumeControllerVisible(iVolumeController, bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVolumeController(IVolumeController.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCameraSoundForced() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.startWatchingRoutes(IAudioRoutesObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((AudioRoutesInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handleBluetoothA2dpDeviceConfigChange((BluetoothDevice)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setWiredDeviceConnectionState(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUiSoundsStreamType();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRingtonePlayer();
                        ((Parcel)object2).writeNoException();
                        object = object != null ? object.asBinder() : null;
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRingtonePlayer(IRingtonePlayer.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forceVolumeControlStream(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopBluetoothSco(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startBluetoothScoVirtualCall(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startBluetoothSco(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCurrentAudioFocus();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterAudioFocusClient(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAudioFocusDispatcher iAudioFocusDispatcher = IAudioFocusDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string2 = ((Parcel)object).readString();
                        AudioAttributes audioAttributes = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.abandonAudioFocus(iAudioFocusDispatcher, string2, audioAttributes, ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AudioAttributes audioAttributes = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.requestAudioFocus(audioAttributes, ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), IAudioFocusDispatcher.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), IAudioPolicyCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isBluetoothA2dpOn() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl11 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setBluetoothA2dpOn(bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isBluetoothScoOn() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl11 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setBluetoothScoOn(bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSpeakerphoneOn() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl11 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setSpeakerphoneOn(bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        bl11 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.avrcpSupportsAbsoluteVolume((String)object2, bl11);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reloadAudioSettings();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unloadSoundEffects();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.loadSoundEffects() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.playSoundEffectVolume(((Parcel)object).readInt(), ((Parcel)object).readFloat());
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.playSoundEffect(((Parcel)object).readInt());
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMode();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setMode(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldVibrate(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVibrateSetting(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVibrateSetting(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isValidRingerMode(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRingerModeInternal();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRingerModeExternal();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRingerModeInternal(((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRingerModeExternal(((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl11 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setMicrophoneMute(bl11, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAudioProductStrategies();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLastAudibleStreamVolume(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getMinVolumeIndexForAttributes((AudioAttributes)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getMaxVolumeIndexForAttributes((AudioAttributes)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getVolumeIndexForAttributes((AudioAttributes)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AudioAttributes audioAttributes = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setVolumeIndexForAttributes(audioAttributes, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAudioVolumeGroups();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getStreamMaxVolume(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getStreamMinVolume(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getStreamVolume(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl11 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setMasterMute(bl11, ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isMasterMute() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl11 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.forceRemoteSubmixFullVolume(bl11, ((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isStreamMute(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setStreamVolume(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.adjustStreamVolume(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.adjustSuggestedStreamVolume(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseRecorder(((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.recorderEvent(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.trackRecorder(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releasePlayer(((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.playerEvent(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        this.playerAttributes(n, (AudioAttributes)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? PlayerBase.PlayerIdCard.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.trackPlayer((PlayerBase.PlayerIdCard)object);
                ((Parcel)object2).writeNoException();
                ((Parcel)object2).writeInt(n);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAudioService {
            public static IAudioService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String string2, AudioAttributes audioAttributes, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAudioFocusDispatcher != null ? iAudioFocusDispatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().abandonAudioFocus(iAudioFocusDispatcher, string2, audioAttributes, string3);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int addMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioPolicyConfig != null) {
                        parcel.writeInt(1);
                        audioPolicyConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().addMixForPolicy(audioPolicyConfig, iAudioPolicyCallback);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void adjustStreamVolume(int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adjustStreamVolume(n, n2, n3, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void adjustSuggestedStreamVolume(int n, int n2, int n3, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adjustSuggestedStreamVolume(n, n2, n3, string2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void avrcpSupportsAbsoluteVolume(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(41, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().avrcpSupportsAbsoluteVolume(string2, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disableRingtoneSync(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableRingtoneSync(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void disableSafeMediaVolume(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableSafeMediaVolume(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int n, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioFocusInfo != null) {
                        parcel.writeInt(1);
                        audioFocusInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().dispatchFocusChange(audioFocusInfo, n, iAudioPolicyCallback);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void forceRemoteSubmixFullVolume(boolean bl, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceRemoteSubmixFullVolume(bl, iBinder);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void forceVolumeControlStream(int n, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceVolumeControlStream(n, iBinder);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(83, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AudioPlaybackConfiguration> list = Stub.getDefaultImpl().getActivePlaybackConfigurations();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<AudioPlaybackConfiguration> arrayList = parcel2.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AudioRecordingConfiguration> list = Stub.getDefaultImpl().getActiveRecordingConfigurations();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<AudioRecordingConfiguration> arrayList = parcel2.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AudioProductStrategy> list = Stub.getDefaultImpl().getAudioProductStrategies();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<AudioProductStrategy> arrayList = parcel2.createTypedArrayList(AudioProductStrategy.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AudioVolumeGroup> list = Stub.getDefaultImpl().getAudioVolumeGroups();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<AudioVolumeGroup> arrayList = parcel2.createTypedArrayList(AudioVolumeGroup.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCurrentAudioFocus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCurrentAudioFocus();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getFocusRampTimeMs(int n, AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getFocusRampTimeMs(n, audioAttributes);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getLastAudibleStreamVolume(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getLastAudibleStreamVolume(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getMaxVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getMaxVolumeIndexForAttributes(audioAttributes);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getMinVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getMinVolumeIndexForAttributes(audioAttributes);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getMode();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRingerModeExternal() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRingerModeExternal();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRingerModeInternal() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRingerModeInternal();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IRingtonePlayer getRingtonePlayer() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IRingtonePlayer iRingtonePlayer = Stub.getDefaultImpl().getRingtonePlayer();
                        return iRingtonePlayer;
                    }
                    parcel2.readException();
                    IRingtonePlayer iRingtonePlayer = IRingtonePlayer.Stub.asInterface(parcel2.readStrongBinder());
                    return iRingtonePlayer;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getStreamMaxVolume(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getStreamMaxVolume(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getStreamMinVolume(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getStreamMinVolume(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getStreamVolume(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getStreamVolume(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getUiSoundsStreamType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getUiSoundsStreamType();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getVibrateSetting(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getVibrateSetting(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getVolumeIndexForAttributes(audioAttributes);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice bluetoothDevice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleBluetoothA2dpDeviceConfigChange(bluetoothDevice);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean hasHapticChannels(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(96, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hasHapticChannels(uri);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean hasRegisteredDynamicPolicy() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(77, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasRegisteredDynamicPolicy();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isAudioServerRunning() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(93, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAudioServerRunning();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isBluetoothA2dpOn() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(47, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBluetoothA2dpOn();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isBluetoothScoOn() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(45, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBluetoothScoOn();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isCameraSoundForced() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(62, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCameraSoundForced();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isHdmiSystemAudioSupported() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(69, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isHdmiSystemAudioSupported();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isMasterMute() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isMasterMute();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isSpeakerphoneOn() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(43, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSpeakerphoneOn();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isStreamAffectedByMute(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(66, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isStreamAffectedByMute(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isStreamAffectedByRingerMode(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(65, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isStreamAffectedByRingerMode(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isStreamMute(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(11, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isStreamMute(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isValidRingerMode(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(30, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isValidRingerMode(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean loadSoundEffects() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(38, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().loadSoundEffects();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iVolumeController == null) break block8;
                    iBinder = iVolumeController.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n);
                    if (!this.mRemote.transact(64, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyVolumeControllerVisible(iVolumeController, bl);
                        return;
                    }
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void playSoundEffect(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(36, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playSoundEffect(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void playSoundEffectVolume(int n, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(37, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playSoundEffectVolume(n, f);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void playerAttributes(int n, AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playerAttributes(n, audioAttributes);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void playerEvent(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playerEvent(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void playerHasOpPlayAudio(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(87, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playerHasOpPlayAudio(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void recorderEvent(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().recorderEvent(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String registerAudioPolicy(AudioPolicyConfig object, IAudioPolicyCallback iAudioPolicyCallback, boolean bl, boolean bl2, boolean bl3, boolean bl4, IMediaProjection iMediaProjection) throws RemoteException {
                void var1_4;
                Parcel parcel2;
                Parcel parcel;
                block9 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (object != null) {
                        parcel2.writeInt(1);
                        ((AudioPolicyConfig)object).writeToParcel(parcel2, 0);
                    } else {
                        parcel2.writeInt(0);
                    }
                    Object var11_14 = null;
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel2.writeStrongBinder(iBinder);
                    int n2 = bl ? 1 : 0;
                    parcel2.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel2.writeInt(n2);
                    n2 = bl3 ? 1 : 0;
                    parcel2.writeInt(n2);
                    n2 = bl4 ? n : 0;
                    parcel2.writeInt(n2);
                    iBinder = var11_14;
                    if (iMediaProjection != null) {
                        iBinder = iMediaProjection.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    try {
                        if (!this.mRemote.transact(70, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().registerAudioPolicy((AudioPolicyConfig)object, iAudioPolicyCallback, bl, bl2, bl3, bl4, iMediaProjection);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readString();
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block9;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_4;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAudioServerStateDispatcher != null ? iAudioServerStateDispatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(91, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerAudioServerStateDispatcher(iAudioServerStateDispatcher);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPlaybackConfigDispatcher != null ? iPlaybackConfigDispatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerPlaybackCallback(iPlaybackConfigDispatcher);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecordingConfigDispatcher != null ? iRecordingConfigDispatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRecordingCallback(iRecordingConfigDispatcher);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void releasePlayer(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releasePlayer(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void releaseRecorder(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseRecorder(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void reloadAudioSettings() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(40, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reloadAudioSettings();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int removeMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioPolicyConfig != null) {
                        parcel.writeInt(1);
                        audioPolicyConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(74, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().removeMixForPolicy(audioPolicyConfig, iAudioPolicyCallback);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(95, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().removeUidDeviceAffinity(iAudioPolicyCallback, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int requestAudioFocus(AudioAttributes audioAttributes, int n, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String string2, String string3, int n2, IAudioPolicyCallback iAudioPolicyCallback, int n3) throws RemoteException {
                Parcel parcel2;
                void var1_5;
                Parcel parcel;
                block11 : {
                    block10 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (audioAttributes != null) {
                            parcel2.writeInt(1);
                            audioAttributes.writeToParcel(parcel2, 0);
                            break block10;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block11;
                    }
                    try {
                        parcel2.writeStrongBinder(iBinder);
                        Object var12_16 = null;
                        IBinder iBinder2 = iAudioFocusDispatcher != null ? iAudioFocusDispatcher.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder2);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeInt(n2);
                        iBinder2 = var12_16;
                        if (iAudioPolicyCallback != null) {
                            iBinder2 = iAudioPolicyCallback.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder2);
                        parcel2.writeInt(n3);
                        if (!this.mRemote.transact(48, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().requestAudioFocus(audioAttributes, n, iBinder, iAudioFocusDispatcher, string2, string3, n2, iAudioPolicyCallback, n3);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_5;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice bluetoothDevice, int n, int n2, boolean bl, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n4 = 1;
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!bl) {
                        n4 = 0;
                    }
                    parcel.writeInt(n4);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(89, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(bluetoothDevice, n, n2, bl, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setBluetoothA2dpOn(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothA2dpOn(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice bluetoothDevice, int n, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n3 = 1;
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n3 = 0;
                    }
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothHearingAidDeviceConnectionState(bluetoothDevice, n, bl, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setBluetoothScoOn(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothScoOn(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int setFocusPropertiesForPolicy(int n, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setFocusPropertiesForPolicy(n, iAudioPolicyCallback);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int n, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioFocusInfo != null) {
                        parcel.writeInt(1);
                        audioFocusInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(90, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setFocusRequestResultFromExtPolicy(audioFocusInfo, n, iAudioPolicyCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public int setHdmiSystemAudioSupported(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setHdmiSystemAudioSupported(bl);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMasterMute(boolean bl, int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMasterMute(bl, n, string2, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMicrophoneMute(boolean bl, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMicrophoneMute(bl, string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMode(int n, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMode(n, iBinder, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setRingerModeExternal(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRingerModeExternal(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setRingerModeInternal(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRingerModeInternal(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRingtonePlayer != null ? iRingtonePlayer.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRingtonePlayer(iRingtonePlayer);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setSpeakerphoneOn(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSpeakerphoneOn(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setStreamVolume(int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStreamVolume(n, n2, n3, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int n, int[] arrn, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeIntArray(arrn);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(94, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setUidDeviceAffinity(iAudioPolicyCallback, n, arrn, arrstring);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVibrateSetting(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVibrateSetting(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setVolumeController(IVolumeController iVolumeController) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVolumeController != null ? iVolumeController.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumeController(iVolumeController);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVolumeIndexForAttributes(AudioAttributes audioAttributes, int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumeIndexForAttributes(audioAttributes, n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (volumePolicy != null) {
                        parcel.writeInt(1);
                        volumePolicy.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumePolicy(volumePolicy);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setWiredDeviceConnectionState(int n, int n2, String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWiredDeviceConnectionState(n, n2, string2, string3, string4);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean shouldVibrate(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(33, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shouldVibrate(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void startBluetoothSco(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startBluetoothSco(iBinder, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void startBluetoothScoVirtualCall(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startBluetoothScoVirtualCall(iBinder);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block7 : {
                    IBinder iBinder;
                    block6 : {
                        block5 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (object == null) break block5;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = object.asBinder();
                            break block6;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(61, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block7;
                    object = Stub.getDefaultImpl().startWatchingRoutes((IAudioRoutesObserver)object);
                    parcel2.recycle();
                    parcel.recycle();
                    return object;
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? AudioRoutesInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public void stopBluetoothSco(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopBluetoothSco(iBinder);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (playerIdCard != null) {
                        parcel.writeInt(1);
                        playerIdCard.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().trackPlayer(playerIdCard);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int trackRecorder(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().trackRecorder(iBinder);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void unloadSoundEffects() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(39, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unloadSoundEffects();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void unregisterAudioFocusClient(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAudioFocusClient(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAudioPolicy(iAudioPolicyCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAudioPolicyCallback != null ? iAudioPolicyCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(71, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterAudioPolicyAsync(iAudioPolicyCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAudioServerStateDispatcher != null ? iAudioServerStateDispatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(92, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterAudioServerStateDispatcher(iAudioServerStateDispatcher);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPlaybackConfigDispatcher != null ? iPlaybackConfigDispatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(82, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterPlaybackCallback(iPlaybackConfigDispatcher);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecordingConfigDispatcher != null ? iRecordingConfigDispatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(79, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterRecordingCallback(iRecordingConfigDispatcher);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

