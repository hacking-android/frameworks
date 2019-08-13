/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.content.pm.ParceledListSlice;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;

public interface ISessionControllerCallback
extends IInterface {
    public void onEvent(String var1, Bundle var2) throws RemoteException;

    public void onExtrasChanged(Bundle var1) throws RemoteException;

    public void onMetadataChanged(MediaMetadata var1) throws RemoteException;

    public void onPlaybackStateChanged(PlaybackState var1) throws RemoteException;

    public void onQueueChanged(ParceledListSlice var1) throws RemoteException;

    public void onQueueTitleChanged(CharSequence var1) throws RemoteException;

    public void onSessionDestroyed() throws RemoteException;

    public void onVolumeInfoChanged(MediaController.PlaybackInfo var1) throws RemoteException;

    public static class Default
    implements ISessionControllerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onEvent(String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onExtrasChanged(Bundle bundle) throws RemoteException {
        }

        @Override
        public void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException {
        }

        @Override
        public void onPlaybackStateChanged(PlaybackState playbackState) throws RemoteException {
        }

        @Override
        public void onQueueChanged(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void onSessionDestroyed() throws RemoteException {
        }

        @Override
        public void onVolumeInfoChanged(MediaController.PlaybackInfo playbackInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISessionControllerCallback {
        private static final String DESCRIPTOR = "android.media.session.ISessionControllerCallback";
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISessionControllerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISessionControllerCallback) {
                return (ISessionControllerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISessionControllerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "onVolumeInfoChanged";
                }
                case 7: {
                    return "onExtrasChanged";
                }
                case 6: {
                    return "onQueueTitleChanged";
                }
                case 5: {
                    return "onQueueChanged";
                }
                case 4: {
                    return "onMetadataChanged";
                }
                case 3: {
                    return "onPlaybackStateChanged";
                }
                case 2: {
                    return "onSessionDestroyed";
                }
                case 1: 
            }
            return "onEvent";
        }

        public static boolean setDefaultImpl(ISessionControllerCallback iSessionControllerCallback) {
            if (Proxy.sDefaultImpl == null && iSessionControllerCallback != null) {
                Proxy.sDefaultImpl = iSessionControllerCallback;
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
        public boolean onTransact(int n, Parcel parcelable, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)((Object)parcelable), (Parcel)object, n2);
                    }
                    case 8: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? MediaController.PlaybackInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onVolumeInfoChanged((MediaController.PlaybackInfo)parcelable);
                        return true;
                    }
                    case 7: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onExtrasChanged((Bundle)parcelable);
                        return true;
                    }
                    case 6: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onQueueTitleChanged((CharSequence)((Object)parcelable));
                        return true;
                    }
                    case 5: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onQueueChanged((ParceledListSlice)parcelable);
                        return true;
                    }
                    case 4: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? MediaMetadata.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onMetadataChanged((MediaMetadata)parcelable);
                        return true;
                    }
                    case 3: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? PlaybackState.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onPlaybackStateChanged((PlaybackState)parcelable);
                        return true;
                    }
                    case 2: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.onSessionDestroyed();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                object = ((Parcel)((Object)parcelable)).readString();
                parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                this.onEvent((String)object, (Bundle)parcelable);
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISessionControllerCallback {
            public static ISessionControllerCallback sDefaultImpl;
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

            @Override
            public void onEvent(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEvent(string2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onExtrasChanged(bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mediaMetadata != null) {
                        parcel.writeInt(1);
                        mediaMetadata.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMetadataChanged(mediaMetadata);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPlaybackStateChanged(PlaybackState playbackState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (playbackState != null) {
                        parcel.writeInt(1);
                        playbackState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlaybackStateChanged(playbackState);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onQueueChanged(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onQueueChanged(parceledListSlice);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onQueueTitleChanged(charSequence);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionDestroyed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionDestroyed();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeInfoChanged(MediaController.PlaybackInfo playbackInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (playbackInfo != null) {
                        parcel.writeInt(1);
                        playbackInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeInfoChanged(playbackInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

