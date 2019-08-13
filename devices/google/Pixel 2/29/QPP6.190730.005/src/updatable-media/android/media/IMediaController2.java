/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 */
package android.media;

import android.media.Session2Command;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;

public interface IMediaController2
extends IInterface {
    public void cancelSessionCommand(int var1) throws RemoteException;

    public void notifyConnected(int var1, Bundle var2) throws RemoteException;

    public void notifyDisconnected(int var1) throws RemoteException;

    public void notifyPlaybackActiveChanged(int var1, boolean var2) throws RemoteException;

    public void sendSessionCommand(int var1, Session2Command var2, Bundle var3, ResultReceiver var4) throws RemoteException;

    public static class Default
    implements IMediaController2 {
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelSessionCommand(int n) throws RemoteException {
        }

        @Override
        public void notifyConnected(int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void notifyDisconnected(int n) throws RemoteException {
        }

        @Override
        public void notifyPlaybackActiveChanged(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void sendSessionCommand(int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaController2 {
        private static final String DESCRIPTOR = "android.media.IMediaController2";
        static final int TRANSACTION_cancelSessionCommand = 5;
        static final int TRANSACTION_notifyConnected = 1;
        static final int TRANSACTION_notifyDisconnected = 2;
        static final int TRANSACTION_notifyPlaybackActiveChanged = 3;
        static final int TRANSACTION_sendSessionCommand = 4;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static IMediaController2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaController2) {
                return (IMediaController2)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaController2 getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IMediaController2 iMediaController2) {
            if (Proxy.sDefaultImpl == null && iMediaController2 != null) {
                Proxy.sDefaultImpl = iMediaController2;
                return true;
            }
            return false;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, object, object2, n2);
                                }
                                object2.writeString(DESCRIPTOR);
                                return true;
                            }
                            object.enforceInterface(DESCRIPTOR);
                            this.cancelSessionCommand(object.readInt());
                            return true;
                        }
                        object.enforceInterface(DESCRIPTOR);
                        n = object.readInt();
                        object2 = object.readInt() != 0 ? (Session2Command)Session2Command.CREATOR.createFromParcel(object) : null;
                        Bundle bundle = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                        object = object.readInt() != 0 ? (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(object) : null;
                        this.sendSessionCommand(n, (Session2Command)object2, bundle, (ResultReceiver)object);
                        return true;
                    }
                    object.enforceInterface(DESCRIPTOR);
                    n = object.readInt();
                    boolean bl = object.readInt() != 0;
                    this.notifyPlaybackActiveChanged(n, bl);
                    return true;
                }
                object.enforceInterface(DESCRIPTOR);
                this.notifyDisconnected(object.readInt());
                return true;
            }
            object.enforceInterface(DESCRIPTOR);
            n = object.readInt();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            this.notifyConnected(n, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IMediaController2 {
            public static IMediaController2 sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelSessionCommand(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelSessionCommand(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void notifyConnected(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyConnected(n, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyDisconnected(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDisconnected(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPlaybackActiveChanged(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPlaybackActiveChanged(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendSessionCommand(int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (session2Command != null) {
                        parcel.writeInt(1);
                        session2Command.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendSessionCommand(n, session2Command, bundle, resultReceiver);
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

