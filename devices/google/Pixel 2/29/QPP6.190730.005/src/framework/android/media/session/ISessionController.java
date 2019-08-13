/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.app.PendingIntent;
import android.content.pm.ParceledListSlice;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.ISessionControllerCallback;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.KeyEvent;

public interface ISessionController
extends IInterface {
    public void adjustVolume(String var1, String var2, ISessionControllerCallback var3, int var4, int var5) throws RemoteException;

    public void fastForward(String var1, ISessionControllerCallback var2) throws RemoteException;

    public Bundle getExtras() throws RemoteException;

    public long getFlags() throws RemoteException;

    public PendingIntent getLaunchPendingIntent() throws RemoteException;

    public MediaMetadata getMetadata() throws RemoteException;

    public String getPackageName() throws RemoteException;

    public PlaybackState getPlaybackState() throws RemoteException;

    public ParceledListSlice getQueue() throws RemoteException;

    public CharSequence getQueueTitle() throws RemoteException;

    public int getRatingType() throws RemoteException;

    public Bundle getSessionInfo() throws RemoteException;

    public String getTag() throws RemoteException;

    public MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException;

    public void next(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void pause(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void play(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void playFromMediaId(String var1, ISessionControllerCallback var2, String var3, Bundle var4) throws RemoteException;

    public void playFromSearch(String var1, ISessionControllerCallback var2, String var3, Bundle var4) throws RemoteException;

    public void playFromUri(String var1, ISessionControllerCallback var2, Uri var3, Bundle var4) throws RemoteException;

    public void prepare(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void prepareFromMediaId(String var1, ISessionControllerCallback var2, String var3, Bundle var4) throws RemoteException;

    public void prepareFromSearch(String var1, ISessionControllerCallback var2, String var3, Bundle var4) throws RemoteException;

    public void prepareFromUri(String var1, ISessionControllerCallback var2, Uri var3, Bundle var4) throws RemoteException;

    public void previous(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void rate(String var1, ISessionControllerCallback var2, Rating var3) throws RemoteException;

    public void registerCallback(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void rewind(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void seekTo(String var1, ISessionControllerCallback var2, long var3) throws RemoteException;

    public void sendCommand(String var1, ISessionControllerCallback var2, String var3, Bundle var4, ResultReceiver var5) throws RemoteException;

    public void sendCustomAction(String var1, ISessionControllerCallback var2, String var3, Bundle var4) throws RemoteException;

    public boolean sendMediaButton(String var1, ISessionControllerCallback var2, KeyEvent var3) throws RemoteException;

    public void setPlaybackSpeed(String var1, ISessionControllerCallback var2, float var3) throws RemoteException;

    public void setVolumeTo(String var1, String var2, ISessionControllerCallback var3, int var4, int var5) throws RemoteException;

    public void skipToQueueItem(String var1, ISessionControllerCallback var2, long var3) throws RemoteException;

    public void stop(String var1, ISessionControllerCallback var2) throws RemoteException;

    public void unregisterCallback(ISessionControllerCallback var1) throws RemoteException;

    public static class Default
    implements ISessionController {
        @Override
        public void adjustVolume(String string2, String string3, ISessionControllerCallback iSessionControllerCallback, int n, int n2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void fastForward(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public Bundle getExtras() throws RemoteException {
            return null;
        }

        @Override
        public long getFlags() throws RemoteException {
            return 0L;
        }

        @Override
        public PendingIntent getLaunchPendingIntent() throws RemoteException {
            return null;
        }

        @Override
        public MediaMetadata getMetadata() throws RemoteException {
            return null;
        }

        @Override
        public String getPackageName() throws RemoteException {
            return null;
        }

        @Override
        public PlaybackState getPlaybackState() throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getQueue() throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getQueueTitle() throws RemoteException {
            return null;
        }

        @Override
        public int getRatingType() throws RemoteException {
            return 0;
        }

        @Override
        public Bundle getSessionInfo() throws RemoteException {
            return null;
        }

        @Override
        public String getTag() throws RemoteException {
            return null;
        }

        @Override
        public MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException {
            return null;
        }

        @Override
        public void next(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void pause(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void play(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void playFromMediaId(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void playFromSearch(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void playFromUri(String string2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override
        public void prepare(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void prepareFromMediaId(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void prepareFromSearch(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void prepareFromUri(String string2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override
        public void previous(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void rate(String string2, ISessionControllerCallback iSessionControllerCallback, Rating rating) throws RemoteException {
        }

        @Override
        public void registerCallback(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void rewind(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void seekTo(String string2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
        }

        @Override
        public void sendCommand(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void sendCustomAction(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public boolean sendMediaButton(String string2, ISessionControllerCallback iSessionControllerCallback, KeyEvent keyEvent) throws RemoteException {
            return false;
        }

        @Override
        public void setPlaybackSpeed(String string2, ISessionControllerCallback iSessionControllerCallback, float f) throws RemoteException {
        }

        @Override
        public void setVolumeTo(String string2, String string3, ISessionControllerCallback iSessionControllerCallback, int n, int n2) throws RemoteException {
        }

        @Override
        public void skipToQueueItem(String string2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
        }

        @Override
        public void stop(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void unregisterCallback(ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISessionController {
        private static final String DESCRIPTOR = "android.media.session.ISessionController";
        static final int TRANSACTION_adjustVolume = 11;
        static final int TRANSACTION_fastForward = 26;
        static final int TRANSACTION_getExtras = 36;
        static final int TRANSACTION_getFlags = 9;
        static final int TRANSACTION_getLaunchPendingIntent = 8;
        static final int TRANSACTION_getMetadata = 32;
        static final int TRANSACTION_getPackageName = 5;
        static final int TRANSACTION_getPlaybackState = 33;
        static final int TRANSACTION_getQueue = 34;
        static final int TRANSACTION_getQueueTitle = 35;
        static final int TRANSACTION_getRatingType = 37;
        static final int TRANSACTION_getSessionInfo = 7;
        static final int TRANSACTION_getTag = 6;
        static final int TRANSACTION_getVolumeAttributes = 10;
        static final int TRANSACTION_next = 24;
        static final int TRANSACTION_pause = 22;
        static final int TRANSACTION_play = 17;
        static final int TRANSACTION_playFromMediaId = 18;
        static final int TRANSACTION_playFromSearch = 19;
        static final int TRANSACTION_playFromUri = 20;
        static final int TRANSACTION_prepare = 13;
        static final int TRANSACTION_prepareFromMediaId = 14;
        static final int TRANSACTION_prepareFromSearch = 15;
        static final int TRANSACTION_prepareFromUri = 16;
        static final int TRANSACTION_previous = 25;
        static final int TRANSACTION_rate = 29;
        static final int TRANSACTION_registerCallback = 3;
        static final int TRANSACTION_rewind = 27;
        static final int TRANSACTION_seekTo = 28;
        static final int TRANSACTION_sendCommand = 1;
        static final int TRANSACTION_sendCustomAction = 31;
        static final int TRANSACTION_sendMediaButton = 2;
        static final int TRANSACTION_setPlaybackSpeed = 30;
        static final int TRANSACTION_setVolumeTo = 12;
        static final int TRANSACTION_skipToQueueItem = 21;
        static final int TRANSACTION_stop = 23;
        static final int TRANSACTION_unregisterCallback = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISessionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISessionController) {
                return (ISessionController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISessionController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 37: {
                    return "getRatingType";
                }
                case 36: {
                    return "getExtras";
                }
                case 35: {
                    return "getQueueTitle";
                }
                case 34: {
                    return "getQueue";
                }
                case 33: {
                    return "getPlaybackState";
                }
                case 32: {
                    return "getMetadata";
                }
                case 31: {
                    return "sendCustomAction";
                }
                case 30: {
                    return "setPlaybackSpeed";
                }
                case 29: {
                    return "rate";
                }
                case 28: {
                    return "seekTo";
                }
                case 27: {
                    return "rewind";
                }
                case 26: {
                    return "fastForward";
                }
                case 25: {
                    return "previous";
                }
                case 24: {
                    return "next";
                }
                case 23: {
                    return "stop";
                }
                case 22: {
                    return "pause";
                }
                case 21: {
                    return "skipToQueueItem";
                }
                case 20: {
                    return "playFromUri";
                }
                case 19: {
                    return "playFromSearch";
                }
                case 18: {
                    return "playFromMediaId";
                }
                case 17: {
                    return "play";
                }
                case 16: {
                    return "prepareFromUri";
                }
                case 15: {
                    return "prepareFromSearch";
                }
                case 14: {
                    return "prepareFromMediaId";
                }
                case 13: {
                    return "prepare";
                }
                case 12: {
                    return "setVolumeTo";
                }
                case 11: {
                    return "adjustVolume";
                }
                case 10: {
                    return "getVolumeAttributes";
                }
                case 9: {
                    return "getFlags";
                }
                case 8: {
                    return "getLaunchPendingIntent";
                }
                case 7: {
                    return "getSessionInfo";
                }
                case 6: {
                    return "getTag";
                }
                case 5: {
                    return "getPackageName";
                }
                case 4: {
                    return "unregisterCallback";
                }
                case 3: {
                    return "registerCallback";
                }
                case 2: {
                    return "sendMediaButton";
                }
                case 1: 
            }
            return "sendCommand";
        }

        public static boolean setDefaultImpl(ISessionController iSessionController) {
            if (Proxy.sDefaultImpl == null && iSessionController != null) {
                Proxy.sDefaultImpl = iSessionController;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRatingType();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getExtras();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getQueueTitle();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getQueue();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPlaybackState();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PlaybackState)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMetadata();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MediaMetadata)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendCustomAction(string2, iSessionControllerCallback, string3, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPlaybackSpeed(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readFloat());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Rating.CREATOR.createFromParcel((Parcel)object) : null;
                        this.rate(string4, iSessionControllerCallback, (Rating)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.seekTo(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.rewind(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.fastForward(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.previous(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.next(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stop(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pause(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.skipToQueueItem(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.playFromUri(string5, iSessionControllerCallback, uri, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string7 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.playFromSearch(string6, iSessionControllerCallback, string7, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string8 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string9 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.playFromMediaId(string8, iSessionControllerCallback, string9, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.play(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string10 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.prepareFromUri(string10, iSessionControllerCallback, uri, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string11 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string12 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.prepareFromSearch(string11, iSessionControllerCallback, string12, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string13 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string14 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.prepareFromMediaId(string13, iSessionControllerCallback, string14, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.prepare(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVolumeTo(((Parcel)object).readString(), ((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.adjustVolume(((Parcel)object).readString(), ((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getVolumeAttributes();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MediaController.PlaybackInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getFlags();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLaunchPendingIntent();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PendingIntent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSessionInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTag();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackageName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterCallback(ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerCallback(((Parcel)object).readString(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string15 = ((Parcel)object).readString();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.sendMediaButton(string15, iSessionControllerCallback, (KeyEvent)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string16 = ((Parcel)object).readString();
                ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                String string17 = ((Parcel)object).readString();
                Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                this.sendCommand(string16, iSessionControllerCallback, string17, bundle, (ResultReceiver)object);
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISessionController {
            public static ISessionController sDefaultImpl;
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
            public void adjustVolume(String string2, String string3, ISessionControllerCallback iSessionControllerCallback, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adjustVolume(string2, string3, iSessionControllerCallback, n, n2);
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
            public void fastForward(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().fastForward(string2, iSessionControllerCallback);
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
            public Bundle getExtras() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(36, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bundle bundle = Stub.getDefaultImpl().getExtras();
                        parcel.recycle();
                        parcel2.recycle();
                        return bundle;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return bundle;
            }

            @Override
            public long getFlags() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getFlags();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
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
            public PendingIntent getLaunchPendingIntent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(8, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        PendingIntent pendingIntent = Stub.getDefaultImpl().getLaunchPendingIntent();
                        parcel.recycle();
                        parcel2.recycle();
                        return pendingIntent;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                PendingIntent pendingIntent = parcel.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return pendingIntent;
            }

            @Override
            public MediaMetadata getMetadata() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(32, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        MediaMetadata mediaMetadata = Stub.getDefaultImpl().getMetadata();
                        parcel.recycle();
                        parcel2.recycle();
                        return mediaMetadata;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                MediaMetadata mediaMetadata = parcel.readInt() != 0 ? MediaMetadata.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return mediaMetadata;
            }

            @Override
            public String getPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getPackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PlaybackState getPlaybackState() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(33, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        PlaybackState playbackState = Stub.getDefaultImpl().getPlaybackState();
                        parcel.recycle();
                        parcel2.recycle();
                        return playbackState;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                PlaybackState playbackState = parcel.readInt() != 0 ? PlaybackState.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return playbackState;
            }

            @Override
            public ParceledListSlice getQueue() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(34, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getQueue();
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParceledListSlice parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public CharSequence getQueueTitle() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(35, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        CharSequence charSequence = Stub.getDefaultImpl().getQueueTitle();
                        parcel.recycle();
                        parcel2.recycle();
                        return charSequence;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                CharSequence charSequence = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return charSequence;
            }

            @Override
            public int getRatingType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRatingType();
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
            public Bundle getSessionInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bundle bundle = Stub.getDefaultImpl().getSessionInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return bundle;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return bundle;
            }

            @Override
            public String getTag() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getTag();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        MediaController.PlaybackInfo playbackInfo = Stub.getDefaultImpl().getVolumeAttributes();
                        parcel.recycle();
                        parcel2.recycle();
                        return playbackInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                MediaController.PlaybackInfo playbackInfo = parcel.readInt() != 0 ? MediaController.PlaybackInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return playbackInfo;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void next(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().next(string2, iSessionControllerCallback);
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
            public void pause(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pause(string2, iSessionControllerCallback);
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
            public void play(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().play(string2, iSessionControllerCallback);
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
            public void playFromMediaId(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playFromMediaId(string2, iSessionControllerCallback, string3, bundle);
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
            public void playFromSearch(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playFromSearch(string2, iSessionControllerCallback, string3, bundle);
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
            public void playFromUri(String string2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
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
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playFromUri(string2, iSessionControllerCallback, uri, bundle);
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
            public void prepare(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepare(string2, iSessionControllerCallback);
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
            public void prepareFromMediaId(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepareFromMediaId(string2, iSessionControllerCallback, string3, bundle);
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
            public void prepareFromSearch(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepareFromSearch(string2, iSessionControllerCallback, string3, bundle);
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
            public void prepareFromUri(String string2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
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
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepareFromUri(string2, iSessionControllerCallback, uri, bundle);
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
            public void previous(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().previous(string2, iSessionControllerCallback);
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
            public void rate(String string2, ISessionControllerCallback iSessionControllerCallback, Rating rating) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (rating != null) {
                        parcel.writeInt(1);
                        rating.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rate(string2, iSessionControllerCallback, rating);
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
            public void registerCallback(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallback(string2, iSessionControllerCallback);
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
            public void rewind(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rewind(string2, iSessionControllerCallback);
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
            public void seekTo(String string2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().seekTo(string2, iSessionControllerCallback, l);
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
            public void sendCommand(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendCommand(string2, iSessionControllerCallback, string3, bundle, resultReceiver);
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
            public void sendCustomAction(String string2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendCustomAction(string2, iSessionControllerCallback, string3, bundle);
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
            public boolean sendMediaButton(String string2, ISessionControllerCallback iSessionControllerCallback, KeyEvent keyEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    boolean bl = true;
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().sendMediaButton(string2, iSessionControllerCallback, keyEvent);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setPlaybackSpeed(String string2, ISessionControllerCallback iSessionControllerCallback, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPlaybackSpeed(string2, iSessionControllerCallback, f);
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
            public void setVolumeTo(String string2, String string3, ISessionControllerCallback iSessionControllerCallback, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumeTo(string2, string3, iSessionControllerCallback, n, n2);
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
            public void skipToQueueItem(String string2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().skipToQueueItem(string2, iSessionControllerCallback, l);
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
            public void stop(String string2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stop(string2, iSessionControllerCallback);
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
            public void unregisterCallback(ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterCallback(iSessionControllerCallback);
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

