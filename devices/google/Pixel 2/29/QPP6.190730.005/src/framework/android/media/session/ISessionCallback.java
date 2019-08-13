/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.content.Intent;
import android.media.Rating;
import android.media.session.ISessionControllerCallback;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;

public interface ISessionCallback
extends IInterface {
    public void onAdjustVolume(String var1, int var2, int var3, ISessionControllerCallback var4, int var5) throws RemoteException;

    public void onCommand(String var1, int var2, int var3, ISessionControllerCallback var4, String var5, Bundle var6, ResultReceiver var7) throws RemoteException;

    public void onCustomAction(String var1, int var2, int var3, ISessionControllerCallback var4, String var5, Bundle var6) throws RemoteException;

    public void onFastForward(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public void onMediaButton(String var1, int var2, int var3, Intent var4, int var5, ResultReceiver var6) throws RemoteException;

    public void onMediaButtonFromController(String var1, int var2, int var3, ISessionControllerCallback var4, Intent var5) throws RemoteException;

    public void onNext(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public void onPause(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public void onPlay(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public void onPlayFromMediaId(String var1, int var2, int var3, ISessionControllerCallback var4, String var5, Bundle var6) throws RemoteException;

    public void onPlayFromSearch(String var1, int var2, int var3, ISessionControllerCallback var4, String var5, Bundle var6) throws RemoteException;

    public void onPlayFromUri(String var1, int var2, int var3, ISessionControllerCallback var4, Uri var5, Bundle var6) throws RemoteException;

    public void onPrepare(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public void onPrepareFromMediaId(String var1, int var2, int var3, ISessionControllerCallback var4, String var5, Bundle var6) throws RemoteException;

    public void onPrepareFromSearch(String var1, int var2, int var3, ISessionControllerCallback var4, String var5, Bundle var6) throws RemoteException;

    public void onPrepareFromUri(String var1, int var2, int var3, ISessionControllerCallback var4, Uri var5, Bundle var6) throws RemoteException;

    public void onPrevious(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public void onRate(String var1, int var2, int var3, ISessionControllerCallback var4, Rating var5) throws RemoteException;

    public void onRewind(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public void onSeekTo(String var1, int var2, int var3, ISessionControllerCallback var4, long var5) throws RemoteException;

    public void onSetPlaybackSpeed(String var1, int var2, int var3, ISessionControllerCallback var4, float var5) throws RemoteException;

    public void onSetVolumeTo(String var1, int var2, int var3, ISessionControllerCallback var4, int var5) throws RemoteException;

    public void onSkipToTrack(String var1, int var2, int var3, ISessionControllerCallback var4, long var5) throws RemoteException;

    public void onStop(String var1, int var2, int var3, ISessionControllerCallback var4) throws RemoteException;

    public static class Default
    implements ISessionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAdjustVolume(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, int n3) throws RemoteException {
        }

        @Override
        public void onCommand(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void onCustomAction(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onFastForward(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void onMediaButton(String string2, int n, int n2, Intent intent, int n3, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void onMediaButtonFromController(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Intent intent) throws RemoteException {
        }

        @Override
        public void onNext(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void onPause(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void onPlay(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void onPlayFromMediaId(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onPlayFromSearch(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onPlayFromUri(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onPrepare(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void onPrepareFromMediaId(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onPrepareFromSearch(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onPrepareFromUri(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onPrevious(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void onRate(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Rating rating) throws RemoteException {
        }

        @Override
        public void onRewind(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override
        public void onSeekTo(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
        }

        @Override
        public void onSetPlaybackSpeed(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, float f) throws RemoteException {
        }

        @Override
        public void onSetVolumeTo(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, int n3) throws RemoteException {
        }

        @Override
        public void onSkipToTrack(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
        }

        @Override
        public void onStop(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISessionCallback {
        private static final String DESCRIPTOR = "android.media.session.ISessionCallback";
        static final int TRANSACTION_onAdjustVolume = 23;
        static final int TRANSACTION_onCommand = 1;
        static final int TRANSACTION_onCustomAction = 22;
        static final int TRANSACTION_onFastForward = 17;
        static final int TRANSACTION_onMediaButton = 2;
        static final int TRANSACTION_onMediaButtonFromController = 3;
        static final int TRANSACTION_onNext = 15;
        static final int TRANSACTION_onPause = 13;
        static final int TRANSACTION_onPlay = 8;
        static final int TRANSACTION_onPlayFromMediaId = 9;
        static final int TRANSACTION_onPlayFromSearch = 10;
        static final int TRANSACTION_onPlayFromUri = 11;
        static final int TRANSACTION_onPrepare = 4;
        static final int TRANSACTION_onPrepareFromMediaId = 5;
        static final int TRANSACTION_onPrepareFromSearch = 6;
        static final int TRANSACTION_onPrepareFromUri = 7;
        static final int TRANSACTION_onPrevious = 16;
        static final int TRANSACTION_onRate = 20;
        static final int TRANSACTION_onRewind = 18;
        static final int TRANSACTION_onSeekTo = 19;
        static final int TRANSACTION_onSetPlaybackSpeed = 21;
        static final int TRANSACTION_onSetVolumeTo = 24;
        static final int TRANSACTION_onSkipToTrack = 12;
        static final int TRANSACTION_onStop = 14;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISessionCallback) {
                return (ISessionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 24: {
                    return "onSetVolumeTo";
                }
                case 23: {
                    return "onAdjustVolume";
                }
                case 22: {
                    return "onCustomAction";
                }
                case 21: {
                    return "onSetPlaybackSpeed";
                }
                case 20: {
                    return "onRate";
                }
                case 19: {
                    return "onSeekTo";
                }
                case 18: {
                    return "onRewind";
                }
                case 17: {
                    return "onFastForward";
                }
                case 16: {
                    return "onPrevious";
                }
                case 15: {
                    return "onNext";
                }
                case 14: {
                    return "onStop";
                }
                case 13: {
                    return "onPause";
                }
                case 12: {
                    return "onSkipToTrack";
                }
                case 11: {
                    return "onPlayFromUri";
                }
                case 10: {
                    return "onPlayFromSearch";
                }
                case 9: {
                    return "onPlayFromMediaId";
                }
                case 8: {
                    return "onPlay";
                }
                case 7: {
                    return "onPrepareFromUri";
                }
                case 6: {
                    return "onPrepareFromSearch";
                }
                case 5: {
                    return "onPrepareFromMediaId";
                }
                case 4: {
                    return "onPrepare";
                }
                case 3: {
                    return "onMediaButtonFromController";
                }
                case 2: {
                    return "onMediaButton";
                }
                case 1: 
            }
            return "onCommand";
        }

        public static boolean setDefaultImpl(ISessionCallback iSessionCallback) {
            if (Proxy.sDefaultImpl == null && iSessionCallback != null) {
                Proxy.sDefaultImpl = iSessionCallback;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSetVolumeTo(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onAdjustVolume(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCustomAction(string2, n2, n, iSessionControllerCallback, (String)object2, (Bundle)object);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSetPlaybackSpeed(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readFloat());
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Rating.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRate((String)object2, n2, n, iSessionControllerCallback, (Rating)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSeekTo(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRewind(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onFastForward(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPrevious(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNext(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onStop(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPause(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSkipToTrack(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPlayFromUri(string3, n2, n, iSessionControllerCallback, (Uri)object2, (Bundle)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPlayFromSearch(string4, n, n2, iSessionControllerCallback, (String)object2, (Bundle)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPlayFromMediaId(string5, n, n2, iSessionControllerCallback, (String)object2, (Bundle)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPlay(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPrepareFromUri(string6, n2, n, iSessionControllerCallback, (Uri)object2, (Bundle)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string7 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPrepareFromSearch((String)object2, n, n2, iSessionControllerCallback, string7, (Bundle)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string8 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPrepareFromMediaId(string8, n2, n, iSessionControllerCallback, (String)object2, (Bundle)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPrepare(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onMediaButtonFromController((String)object2, n, n2, iSessionControllerCallback, (Intent)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string9 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onMediaButton(string9, n2, n3, (Intent)object2, n, (ResultReceiver)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string10 = ((Parcel)object).readString();
                n2 = ((Parcel)object).readInt();
                n = ((Parcel)object).readInt();
                ISessionControllerCallback iSessionControllerCallback = ISessionControllerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                String string11 = ((Parcel)object).readString();
                object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                this.onCommand(string10, n2, n, iSessionControllerCallback, string11, (Bundle)object2, (ResultReceiver)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISessionCallback {
            public static ISessionCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onAdjustVolume(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n3);
                    if (this.mRemote.transact(23, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onAdjustVolume(string2, n, n2, iSessionControllerCallback, n3);
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
            public void onCommand(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel;
                void var1_7;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                        IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
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
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onCommand(string2, n, n2, iSessionControllerCallback, string3, bundle, resultReceiver);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_7;
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
            public void onCustomAction(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
                            IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeString(string3);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onCustomAction(string2, n, n2, iSessionControllerCallback, string3, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onFastForward(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(17, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onFastForward(string2, n, n2, iSessionControllerCallback);
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
            public void onMediaButton(String string2, int n, int n2, Intent intent, int n3, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block17 : {
                    block16 : {
                        block15 : {
                            parcel = Parcel.obtain();
                            parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                            try {
                                parcel.writeString(string2);
                            }
                            catch (Throwable throwable) {
                                break block17;
                            }
                            try {
                                parcel.writeInt(n);
                            }
                            catch (Throwable throwable) {
                                break block17;
                            }
                            try {
                                parcel.writeInt(n2);
                                if (intent != null) {
                                    parcel.writeInt(1);
                                    intent.writeToParcel(parcel, 0);
                                    break block15;
                                }
                                parcel.writeInt(0);
                            }
                            catch (Throwable throwable) {}
                        }
                        try {
                            parcel.writeInt(n3);
                            if (resultReceiver != null) {
                                parcel.writeInt(1);
                                resultReceiver.writeToParcel(parcel, 0);
                                break block16;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onMediaButton(string2, n, n2, intent, n3, resultReceiver);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onMediaButtonFromController(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onMediaButtonFromController(string2, n, n2, iSessionControllerCallback, intent);
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
            public void onNext(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(15, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onNext(string2, n, n2, iSessionControllerCallback);
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
            public void onPause(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(13, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onPause(string2, n, n2, iSessionControllerCallback);
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
            public void onPlay(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onPlay(string2, n, n2, iSessionControllerCallback);
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
            public void onPlayFromMediaId(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
                            IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeString(string3);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onPlayFromMediaId(string2, n, n2, iSessionControllerCallback, string3, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
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
            public void onPlayFromSearch(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
                            IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeString(string3);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onPlayFromSearch(string2, n, n2, iSessionControllerCallback, string3, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
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
            public void onPlayFromUri(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_7;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
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
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onPlayFromUri(string2, n, n2, iSessionControllerCallback, uri, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onPrepare(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onPrepare(string2, n, n2, iSessionControllerCallback);
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
            public void onPrepareFromMediaId(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
                            IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeString(string3);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onPrepareFromMediaId(string2, n, n2, iSessionControllerCallback, string3, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
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
            public void onPrepareFromSearch(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
                            IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeString(string3);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onPrepareFromSearch(string2, n, n2, iSessionControllerCallback, string3, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
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
            public void onPrepareFromUri(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_7;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
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
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onPrepareFromUri(string2, n, n2, iSessionControllerCallback, uri, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onPrevious(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(16, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onPrevious(string2, n, n2, iSessionControllerCallback);
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
            public void onRate(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, Rating rating) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (rating != null) {
                        parcel.writeInt(1);
                        rating.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(20, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onRate(string2, n, n2, iSessionControllerCallback, rating);
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
            public void onRewind(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(18, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onRewind(string2, n, n2, iSessionControllerCallback);
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
            public void onSeekTo(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n2);
                        IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onSeekTo(string2, n, n2, iSessionControllerCallback, l);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onSetPlaybackSpeed(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeFloat(f);
                    if (this.mRemote.transact(21, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSetPlaybackSpeed(string2, n, n2, iSessionControllerCallback, f);
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
            public void onSetVolumeTo(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n3);
                    if (this.mRemote.transact(24, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSetVolumeTo(string2, n, n2, iSessionControllerCallback, n3);
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
            public void onSkipToTrack(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback, long l) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n2);
                        IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onSkipToTrack(string2, n, n2, iSessionControllerCallback, l);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onStop(String string2, int n, int n2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iSessionControllerCallback != null ? iSessionControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(14, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onStop(string2, n, n2, iSessionControllerCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

