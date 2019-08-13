/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.content.Intent;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.DvbDeviceInfo;
import android.media.tv.ITvInputClient;
import android.media.tv.ITvInputHardware;
import android.media.tv.ITvInputHardwareCallback;
import android.media.tv.ITvInputManagerCallback;
import android.media.tv.TvContentRatingSystemInfo;
import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvInputInfo;
import android.media.tv.TvStreamConfig;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.Surface;
import java.util.ArrayList;
import java.util.List;

public interface ITvInputManager
extends IInterface {
    public ITvInputHardware acquireTvInputHardware(int var1, ITvInputHardwareCallback var2, TvInputInfo var3, int var4) throws RemoteException;

    public void addBlockedRating(String var1, int var2) throws RemoteException;

    public boolean captureFrame(String var1, Surface var2, TvStreamConfig var3, int var4) throws RemoteException;

    public void createOverlayView(IBinder var1, IBinder var2, Rect var3, int var4) throws RemoteException;

    public void createSession(ITvInputClient var1, String var2, boolean var3, int var4, int var5) throws RemoteException;

    public void dispatchSurfaceChanged(IBinder var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public List<TvStreamConfig> getAvailableTvStreamConfigList(String var1, int var2) throws RemoteException;

    public List<String> getBlockedRatings(int var1) throws RemoteException;

    public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException;

    public List<TvInputHardwareInfo> getHardwareList() throws RemoteException;

    public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int var1) throws RemoteException;

    public TvInputInfo getTvInputInfo(String var1, int var2) throws RemoteException;

    public List<TvInputInfo> getTvInputList(int var1) throws RemoteException;

    public int getTvInputState(String var1, int var2) throws RemoteException;

    public boolean isParentalControlsEnabled(int var1) throws RemoteException;

    public boolean isRatingBlocked(String var1, int var2) throws RemoteException;

    public boolean isSingleSessionActive(int var1) throws RemoteException;

    public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo var1, int var2) throws RemoteException;

    public void registerCallback(ITvInputManagerCallback var1, int var2) throws RemoteException;

    public void relayoutOverlayView(IBinder var1, Rect var2, int var3) throws RemoteException;

    public void releaseSession(IBinder var1, int var2) throws RemoteException;

    public void releaseTvInputHardware(int var1, ITvInputHardware var2, int var3) throws RemoteException;

    public void removeBlockedRating(String var1, int var2) throws RemoteException;

    public void removeOverlayView(IBinder var1, int var2) throws RemoteException;

    public void requestChannelBrowsable(Uri var1, int var2) throws RemoteException;

    public void selectTrack(IBinder var1, int var2, String var3, int var4) throws RemoteException;

    public void sendAppPrivateCommand(IBinder var1, String var2, Bundle var3, int var4) throws RemoteException;

    public void sendTvInputNotifyIntent(Intent var1, int var2) throws RemoteException;

    public void setCaptionEnabled(IBinder var1, boolean var2, int var3) throws RemoteException;

    public void setMainSession(IBinder var1, int var2) throws RemoteException;

    public void setParentalControlsEnabled(boolean var1, int var2) throws RemoteException;

    public void setSurface(IBinder var1, Surface var2, int var3) throws RemoteException;

    public void setVolume(IBinder var1, float var2, int var3) throws RemoteException;

    public void startRecording(IBinder var1, Uri var2, int var3) throws RemoteException;

    public void stopRecording(IBinder var1, int var2) throws RemoteException;

    public void timeShiftEnablePositionTracking(IBinder var1, boolean var2, int var3) throws RemoteException;

    public void timeShiftPause(IBinder var1, int var2) throws RemoteException;

    public void timeShiftPlay(IBinder var1, Uri var2, int var3) throws RemoteException;

    public void timeShiftResume(IBinder var1, int var2) throws RemoteException;

    public void timeShiftSeekTo(IBinder var1, long var2, int var4) throws RemoteException;

    public void timeShiftSetPlaybackParams(IBinder var1, PlaybackParams var2, int var3) throws RemoteException;

    public void tune(IBinder var1, Uri var2, Bundle var3, int var4) throws RemoteException;

    public void unblockContent(IBinder var1, String var2, int var3) throws RemoteException;

    public void unregisterCallback(ITvInputManagerCallback var1, int var2) throws RemoteException;

    public void updateTvInputInfo(TvInputInfo var1, int var2) throws RemoteException;

    public static class Default
    implements ITvInputManager {
        @Override
        public ITvInputHardware acquireTvInputHardware(int n, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void addBlockedRating(String string2, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean captureFrame(String string2, Surface surface, TvStreamConfig tvStreamConfig, int n) throws RemoteException {
            return false;
        }

        @Override
        public void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int n) throws RemoteException {
        }

        @Override
        public void createSession(ITvInputClient iTvInputClient, String string2, boolean bl, int n, int n2) throws RemoteException {
        }

        @Override
        public void dispatchSurfaceChanged(IBinder iBinder, int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public List<TvStreamConfig> getAvailableTvStreamConfigList(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getBlockedRatings(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException {
            return null;
        }

        @Override
        public List<TvInputHardwareInfo> getHardwareList() throws RemoteException {
            return null;
        }

        @Override
        public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int n) throws RemoteException {
            return null;
        }

        @Override
        public TvInputInfo getTvInputInfo(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public List<TvInputInfo> getTvInputList(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getTvInputState(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean isParentalControlsEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRatingBlocked(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSingleSessionActive(int n) throws RemoteException {
            return false;
        }

        @Override
        public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int n) throws RemoteException {
            return null;
        }

        @Override
        public void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int n) throws RemoteException {
        }

        @Override
        public void relayoutOverlayView(IBinder iBinder, Rect rect, int n) throws RemoteException {
        }

        @Override
        public void releaseSession(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void releaseTvInputHardware(int n, ITvInputHardware iTvInputHardware, int n2) throws RemoteException {
        }

        @Override
        public void removeBlockedRating(String string2, int n) throws RemoteException {
        }

        @Override
        public void removeOverlayView(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void requestChannelBrowsable(Uri uri, int n) throws RemoteException {
        }

        @Override
        public void selectTrack(IBinder iBinder, int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void sendAppPrivateCommand(IBinder iBinder, String string2, Bundle bundle, int n) throws RemoteException {
        }

        @Override
        public void sendTvInputNotifyIntent(Intent intent, int n) throws RemoteException {
        }

        @Override
        public void setCaptionEnabled(IBinder iBinder, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setMainSession(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void setParentalControlsEnabled(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setSurface(IBinder iBinder, Surface surface, int n) throws RemoteException {
        }

        @Override
        public void setVolume(IBinder iBinder, float f, int n) throws RemoteException {
        }

        @Override
        public void startRecording(IBinder iBinder, Uri uri, int n) throws RemoteException {
        }

        @Override
        public void stopRecording(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void timeShiftEnablePositionTracking(IBinder iBinder, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void timeShiftPause(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void timeShiftPlay(IBinder iBinder, Uri uri, int n) throws RemoteException {
        }

        @Override
        public void timeShiftResume(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void timeShiftSeekTo(IBinder iBinder, long l, int n) throws RemoteException {
        }

        @Override
        public void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int n) throws RemoteException {
        }

        @Override
        public void tune(IBinder iBinder, Uri uri, Bundle bundle, int n) throws RemoteException {
        }

        @Override
        public void unblockContent(IBinder iBinder, String string2, int n) throws RemoteException {
        }

        @Override
        public void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int n) throws RemoteException {
        }

        @Override
        public void updateTvInputInfo(TvInputInfo tvInputInfo, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputManager {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputManager";
        static final int TRANSACTION_acquireTvInputHardware = 37;
        static final int TRANSACTION_addBlockedRating = 12;
        static final int TRANSACTION_captureFrame = 40;
        static final int TRANSACTION_createOverlayView = 24;
        static final int TRANSACTION_createSession = 14;
        static final int TRANSACTION_dispatchSurfaceChanged = 18;
        static final int TRANSACTION_getAvailableTvStreamConfigList = 39;
        static final int TRANSACTION_getBlockedRatings = 11;
        static final int TRANSACTION_getDvbDeviceList = 42;
        static final int TRANSACTION_getHardwareList = 36;
        static final int TRANSACTION_getTvContentRatingSystemList = 5;
        static final int TRANSACTION_getTvInputInfo = 2;
        static final int TRANSACTION_getTvInputList = 1;
        static final int TRANSACTION_getTvInputState = 4;
        static final int TRANSACTION_isParentalControlsEnabled = 8;
        static final int TRANSACTION_isRatingBlocked = 10;
        static final int TRANSACTION_isSingleSessionActive = 41;
        static final int TRANSACTION_openDvbDevice = 43;
        static final int TRANSACTION_registerCallback = 6;
        static final int TRANSACTION_relayoutOverlayView = 25;
        static final int TRANSACTION_releaseSession = 15;
        static final int TRANSACTION_releaseTvInputHardware = 38;
        static final int TRANSACTION_removeBlockedRating = 13;
        static final int TRANSACTION_removeOverlayView = 26;
        static final int TRANSACTION_requestChannelBrowsable = 45;
        static final int TRANSACTION_selectTrack = 22;
        static final int TRANSACTION_sendAppPrivateCommand = 23;
        static final int TRANSACTION_sendTvInputNotifyIntent = 44;
        static final int TRANSACTION_setCaptionEnabled = 21;
        static final int TRANSACTION_setMainSession = 16;
        static final int TRANSACTION_setParentalControlsEnabled = 9;
        static final int TRANSACTION_setSurface = 17;
        static final int TRANSACTION_setVolume = 19;
        static final int TRANSACTION_startRecording = 34;
        static final int TRANSACTION_stopRecording = 35;
        static final int TRANSACTION_timeShiftEnablePositionTracking = 33;
        static final int TRANSACTION_timeShiftPause = 29;
        static final int TRANSACTION_timeShiftPlay = 28;
        static final int TRANSACTION_timeShiftResume = 30;
        static final int TRANSACTION_timeShiftSeekTo = 31;
        static final int TRANSACTION_timeShiftSetPlaybackParams = 32;
        static final int TRANSACTION_tune = 20;
        static final int TRANSACTION_unblockContent = 27;
        static final int TRANSACTION_unregisterCallback = 7;
        static final int TRANSACTION_updateTvInputInfo = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputManager) {
                return (ITvInputManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 45: {
                    return "requestChannelBrowsable";
                }
                case 44: {
                    return "sendTvInputNotifyIntent";
                }
                case 43: {
                    return "openDvbDevice";
                }
                case 42: {
                    return "getDvbDeviceList";
                }
                case 41: {
                    return "isSingleSessionActive";
                }
                case 40: {
                    return "captureFrame";
                }
                case 39: {
                    return "getAvailableTvStreamConfigList";
                }
                case 38: {
                    return "releaseTvInputHardware";
                }
                case 37: {
                    return "acquireTvInputHardware";
                }
                case 36: {
                    return "getHardwareList";
                }
                case 35: {
                    return "stopRecording";
                }
                case 34: {
                    return "startRecording";
                }
                case 33: {
                    return "timeShiftEnablePositionTracking";
                }
                case 32: {
                    return "timeShiftSetPlaybackParams";
                }
                case 31: {
                    return "timeShiftSeekTo";
                }
                case 30: {
                    return "timeShiftResume";
                }
                case 29: {
                    return "timeShiftPause";
                }
                case 28: {
                    return "timeShiftPlay";
                }
                case 27: {
                    return "unblockContent";
                }
                case 26: {
                    return "removeOverlayView";
                }
                case 25: {
                    return "relayoutOverlayView";
                }
                case 24: {
                    return "createOverlayView";
                }
                case 23: {
                    return "sendAppPrivateCommand";
                }
                case 22: {
                    return "selectTrack";
                }
                case 21: {
                    return "setCaptionEnabled";
                }
                case 20: {
                    return "tune";
                }
                case 19: {
                    return "setVolume";
                }
                case 18: {
                    return "dispatchSurfaceChanged";
                }
                case 17: {
                    return "setSurface";
                }
                case 16: {
                    return "setMainSession";
                }
                case 15: {
                    return "releaseSession";
                }
                case 14: {
                    return "createSession";
                }
                case 13: {
                    return "removeBlockedRating";
                }
                case 12: {
                    return "addBlockedRating";
                }
                case 11: {
                    return "getBlockedRatings";
                }
                case 10: {
                    return "isRatingBlocked";
                }
                case 9: {
                    return "setParentalControlsEnabled";
                }
                case 8: {
                    return "isParentalControlsEnabled";
                }
                case 7: {
                    return "unregisterCallback";
                }
                case 6: {
                    return "registerCallback";
                }
                case 5: {
                    return "getTvContentRatingSystemList";
                }
                case 4: {
                    return "getTvInputState";
                }
                case 3: {
                    return "updateTvInputInfo";
                }
                case 2: {
                    return "getTvInputInfo";
                }
                case 1: 
            }
            return "getTvInputList";
        }

        public static boolean setDefaultImpl(ITvInputManager iTvInputManager) {
            if (Proxy.sDefaultImpl == null && iTvInputManager != null) {
                Proxy.sDefaultImpl = iTvInputManager;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestChannelBrowsable(uri, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendTvInputNotifyIntent(intent, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        DvbDeviceInfo dvbDeviceInfo = ((Parcel)object).readInt() != 0 ? DvbDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.openDvbDevice(dvbDeviceInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDvbDeviceList();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSingleSessionActive(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        Surface surface = ((Parcel)object).readInt() != 0 ? Surface.CREATOR.createFromParcel((Parcel)object) : null;
                        TvStreamConfig tvStreamConfig = ((Parcel)object).readInt() != 0 ? TvStreamConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.captureFrame(string2, surface, tvStreamConfig, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAvailableTvStreamConfigList(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseTvInputHardware(((Parcel)object).readInt(), ITvInputHardware.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ITvInputHardwareCallback iTvInputHardwareCallback = ITvInputHardwareCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        TvInputInfo tvInputInfo = ((Parcel)object).readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.acquireTvInputHardware(n, iTvInputHardwareCallback, tvInputInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = object != null ? object.asBinder() : null;
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getHardwareList();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopRecording(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startRecording(iBinder, uri, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.timeShiftEnablePositionTracking(iBinder, bl3, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        PlaybackParams playbackParams = ((Parcel)object).readInt() != 0 ? PlaybackParams.CREATOR.createFromParcel((Parcel)object) : null;
                        this.timeShiftSetPlaybackParams(iBinder, playbackParams, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.timeShiftSeekTo(((Parcel)object).readStrongBinder(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.timeShiftResume(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.timeShiftPause(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.timeShiftPlay(iBinder, uri, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unblockContent(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeOverlayView(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.relayoutOverlayView(iBinder, rect, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        IBinder iBinder2 = ((Parcel)object).readStrongBinder();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createOverlayView(iBinder, iBinder2, rect, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string3 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendAppPrivateCommand(iBinder, string3, bundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.selectTrack(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl3 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.setCaptionEnabled(iBinder, bl3, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.tune(iBinder, uri, bundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVolume(((Parcel)object).readStrongBinder(), ((Parcel)object).readFloat(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dispatchSurfaceChanged(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Surface surface = ((Parcel)object).readInt() != 0 ? Surface.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setSurface(iBinder, surface, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setMainSession(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseSession(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ITvInputClient iTvInputClient = ITvInputClient.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string4 = ((Parcel)object).readString();
                        bl3 = ((Parcel)object).readInt() != 0;
                        this.createSession(iTvInputClient, string4, bl3, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeBlockedRating(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addBlockedRating(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getBlockedRatings(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRatingBlocked(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl3 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.setParentalControlsEnabled(bl3, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isParentalControlsEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterCallback(ITvInputManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerCallback(ITvInputManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTvContentRatingSystemList(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getTvInputState(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        TvInputInfo tvInputInfo = ((Parcel)object).readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateTvInputInfo(tvInputInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTvInputInfo(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((TvInputInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getTvInputList(((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITvInputManager {
            public static ITvInputManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ITvInputHardware acquireTvInputHardware(int n, ITvInputHardwareCallback iInterface, TvInputInfo tvInputInfo, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_6;
                    void var4_7;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (var3_6 != null) {
                        parcel.writeInt(1);
                        var3_6.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var4_7);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ITvInputHardware iTvInputHardware = Stub.getDefaultImpl().acquireTvInputHardware(n, (ITvInputHardwareCallback)iInterface, (TvInputInfo)var3_6, (int)var4_7);
                        return iTvInputHardware;
                    }
                    parcel2.readException();
                    ITvInputHardware iTvInputHardware = ITvInputHardware.Stub.asInterface(parcel2.readStrongBinder());
                    return iTvInputHardware;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void addBlockedRating(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addBlockedRating(string2, n);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean captureFrame(String string2, Surface surface, TvStreamConfig tvStreamConfig, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (surface != null) {
                        parcel.writeInt(1);
                        surface.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (tvStreamConfig != null) {
                        parcel.writeInt(1);
                        tvStreamConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().captureFrame(string2, surface, tvStreamConfig, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
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
            public void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStrongBinder(iBinder2);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createOverlayView(iBinder, iBinder2, rect, n);
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
            public void createSession(ITvInputClient iTvInputClient, String string2, boolean bl, int n, int n2) throws RemoteException {
                IBinder iBinder;
                Parcel parcel;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iTvInputClient == null) break block8;
                    iBinder = iTvInputClient.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                parcel2.writeString(string2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n3);
                    parcel2.writeInt(n);
                    parcel2.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createSession(iTvInputClient, string2, bl, n, n2);
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
            public void dispatchSurfaceChanged(IBinder iBinder, int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchSurfaceChanged(iBinder, n, n2, n3, n4);
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
            public List<TvStreamConfig> getAvailableTvStreamConfigList(String arrayList, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAvailableTvStreamConfigList((String)((Object)arrayList), n);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(TvStreamConfig.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getBlockedRatings(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getBlockedRatings(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<DvbDeviceInfo> list = Stub.getDefaultImpl().getDvbDeviceList();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<DvbDeviceInfo> arrayList = parcel2.createTypedArrayList(DvbDeviceInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<TvInputHardwareInfo> getHardwareList() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<TvInputHardwareInfo> list = Stub.getDefaultImpl().getHardwareList();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<TvInputHardwareInfo> arrayList = parcel2.createTypedArrayList(TvInputHardwareInfo.CREATOR);
                    return arrayList;
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
            public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<TvContentRatingSystemInfo> list = Stub.getDefaultImpl().getTvContentRatingSystemList(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<TvContentRatingSystemInfo> arrayList = parcel2.createTypedArrayList(TvContentRatingSystemInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public TvInputInfo getTvInputInfo(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getTvInputInfo((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public List<TvInputInfo> getTvInputList(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<TvInputInfo> list = Stub.getDefaultImpl().getTvInputList(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<TvInputInfo> arrayList = parcel2.createTypedArrayList(TvInputInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getTvInputState(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getTvInputState(string2, n);
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
            public boolean isParentalControlsEnabled(int n) throws RemoteException {
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
                    if (iBinder.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isParentalControlsEnabled(n);
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
            public boolean isRatingBlocked(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRatingBlocked(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isSingleSessionActive(int n) throws RemoteException {
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
                    if (iBinder.transact(41, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSingleSessionActive(n);
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo parcelable, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((DvbDeviceInfo)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().openDvbDevice((DvbDeviceInfo)parcelable, (int)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTvInputManagerCallback != null ? iTvInputManagerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallback(iTvInputManagerCallback, n);
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
            public void relayoutOverlayView(IBinder iBinder, Rect rect, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().relayoutOverlayView(iBinder, rect, n);
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
            public void releaseSession(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseSession(iBinder, n);
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
            public void releaseTvInputHardware(int n, ITvInputHardware iTvInputHardware, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iTvInputHardware != null ? iTvInputHardware.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseTvInputHardware(n, iTvInputHardware, n2);
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
            public void removeBlockedRating(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeBlockedRating(string2, n);
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
            public void removeOverlayView(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOverlayView(iBinder, n);
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
            public void requestChannelBrowsable(Uri uri, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestChannelBrowsable(uri, n);
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
            public void selectTrack(IBinder iBinder, int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().selectTrack(iBinder, n, string2, n2);
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
            public void sendAppPrivateCommand(IBinder iBinder, String string2, Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendAppPrivateCommand(iBinder, string2, bundle, n);
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
            public void sendTvInputNotifyIntent(Intent intent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendTvInputNotifyIntent(intent, n);
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
            public void setCaptionEnabled(IBinder iBinder, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCaptionEnabled(iBinder, bl, n);
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
            public void setMainSession(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMainSession(iBinder, n);
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
            public void setParentalControlsEnabled(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setParentalControlsEnabled(bl, n);
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
            public void setSurface(IBinder iBinder, Surface surface, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (surface != null) {
                        parcel.writeInt(1);
                        surface.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSurface(iBinder, surface, n);
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
            public void setVolume(IBinder iBinder, float f, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeFloat(f);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolume(iBinder, f, n);
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
            public void startRecording(IBinder iBinder, Uri uri, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startRecording(iBinder, uri, n);
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
            public void stopRecording(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopRecording(iBinder, n);
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
            public void timeShiftEnablePositionTracking(IBinder iBinder, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().timeShiftEnablePositionTracking(iBinder, bl, n);
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
            public void timeShiftPause(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().timeShiftPause(iBinder, n);
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
            public void timeShiftPlay(IBinder iBinder, Uri uri, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().timeShiftPlay(iBinder, uri, n);
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
            public void timeShiftResume(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().timeShiftResume(iBinder, n);
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
            public void timeShiftSeekTo(IBinder iBinder, long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().timeShiftSeekTo(iBinder, l, n);
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
            public void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (playbackParams != null) {
                        parcel.writeInt(1);
                        playbackParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().timeShiftSetPlaybackParams(iBinder, playbackParams, n);
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
            public void tune(IBinder iBinder, Uri uri, Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tune(iBinder, uri, bundle, n);
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
            public void unblockContent(IBinder iBinder, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unblockContent(iBinder, string2, n);
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
            public void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTvInputManagerCallback != null ? iTvInputManagerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterCallback(iTvInputManagerCallback, n);
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
            public void updateTvInputInfo(TvInputInfo tvInputInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tvInputInfo != null) {
                        parcel.writeInt(1);
                        tvInputInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateTvInputInfo(tvInputInfo, n);
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
        }

    }

}

