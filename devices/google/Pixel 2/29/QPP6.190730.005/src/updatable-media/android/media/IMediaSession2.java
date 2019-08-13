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

import android.media.Controller2Link;
import android.media.Session2Command;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;

public interface IMediaSession2
extends IInterface {
    public void cancelSessionCommand(Controller2Link var1, int var2) throws RemoteException;

    public void connect(Controller2Link var1, int var2, Bundle var3) throws RemoteException;

    public void disconnect(Controller2Link var1, int var2) throws RemoteException;

    public void sendSessionCommand(Controller2Link var1, int var2, Session2Command var3, Bundle var4, ResultReceiver var5) throws RemoteException;

    public static class Default
    implements IMediaSession2 {
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelSessionCommand(Controller2Link controller2Link, int n) throws RemoteException {
        }

        @Override
        public void connect(Controller2Link controller2Link, int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void disconnect(Controller2Link controller2Link, int n) throws RemoteException {
        }

        @Override
        public void sendSessionCommand(Controller2Link controller2Link, int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaSession2 {
        private static final String DESCRIPTOR = "android.media.IMediaSession2";
        static final int TRANSACTION_cancelSessionCommand = 4;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_sendSessionCommand = 3;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static IMediaSession2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaSession2) {
                return (IMediaSession2)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaSession2 getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IMediaSession2 iMediaSession2) {
            if (Proxy.sDefaultImpl == null && iMediaSession2 != null) {
                Proxy.sDefaultImpl = iMediaSession2;
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
                            if (n != 1598968902) {
                                return super.onTransact(n, object, object2, n2);
                            }
                            object2.writeString(DESCRIPTOR);
                            return true;
                        }
                        object.enforceInterface(DESCRIPTOR);
                        object2 = object.readInt() != 0 ? (Controller2Link)Controller2Link.CREATOR.createFromParcel(object) : null;
                        this.cancelSessionCommand((Controller2Link)object2, object.readInt());
                        return true;
                    }
                    object.enforceInterface(DESCRIPTOR);
                    object2 = object.readInt() != 0 ? (Controller2Link)Controller2Link.CREATOR.createFromParcel(object) : null;
                    n = object.readInt();
                    Session2Command session2Command = object.readInt() != 0 ? (Session2Command)Session2Command.CREATOR.createFromParcel(object) : null;
                    Bundle bundle = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    object = object.readInt() != 0 ? (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(object) : null;
                    this.sendSessionCommand((Controller2Link)object2, n, session2Command, bundle, (ResultReceiver)object);
                    return true;
                }
                object.enforceInterface(DESCRIPTOR);
                object2 = object.readInt() != 0 ? (Controller2Link)Controller2Link.CREATOR.createFromParcel(object) : null;
                this.disconnect((Controller2Link)object2, object.readInt());
                return true;
            }
            object.enforceInterface(DESCRIPTOR);
            object2 = object.readInt() != 0 ? (Controller2Link)Controller2Link.CREATOR.createFromParcel(object) : null;
            n = object.readInt();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            this.connect((Controller2Link)object2, n, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IMediaSession2 {
            public static IMediaSession2 sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelSessionCommand(Controller2Link controller2Link, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (controller2Link != null) {
                        parcel.writeInt(1);
                        controller2Link.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelSessionCommand(controller2Link, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void connect(Controller2Link controller2Link, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (controller2Link != null) {
                        parcel.writeInt(1);
                        controller2Link.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connect(controller2Link, n, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disconnect(Controller2Link controller2Link, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (controller2Link != null) {
                        parcel.writeInt(1);
                        controller2Link.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect(controller2Link, n);
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
            public void sendSessionCommand(Controller2Link controller2Link, int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (controller2Link != null) {
                        parcel.writeInt(1);
                        controller2Link.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendSessionCommand(controller2Link, n, session2Command, bundle, resultReceiver);
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

