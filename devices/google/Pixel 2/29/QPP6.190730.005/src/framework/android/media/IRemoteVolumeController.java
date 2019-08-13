/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IRemoteVolumeController
extends IInterface {
    public void remoteVolumeChanged(MediaSession.Token var1, int var2) throws RemoteException;

    public void updateRemoteController(MediaSession.Token var1) throws RemoteException;

    public static class Default
    implements IRemoteVolumeController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void remoteVolumeChanged(MediaSession.Token token, int n) throws RemoteException {
        }

        @Override
        public void updateRemoteController(MediaSession.Token token) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRemoteVolumeController {
        private static final String DESCRIPTOR = "android.media.IRemoteVolumeController";
        static final int TRANSACTION_remoteVolumeChanged = 1;
        static final int TRANSACTION_updateRemoteController = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteVolumeController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRemoteVolumeController) {
                return (IRemoteVolumeController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRemoteVolumeController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "updateRemoteController";
            }
            return "remoteVolumeChanged";
        }

        public static boolean setDefaultImpl(IRemoteVolumeController iRemoteVolumeController) {
            if (Proxy.sDefaultImpl == null && iRemoteVolumeController != null) {
                Proxy.sDefaultImpl = iRemoteVolumeController;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    ((Parcel)object2).writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel((Parcel)object) : null;
                this.updateRemoteController((MediaSession.Token)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel((Parcel)object) : null;
            this.remoteVolumeChanged((MediaSession.Token)object2, ((Parcel)object).readInt());
            return true;
        }

        private static class Proxy
        implements IRemoteVolumeController {
            public static IRemoteVolumeController sDefaultImpl;
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
            public void remoteVolumeChanged(MediaSession.Token token, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (token != null) {
                        parcel.writeInt(1);
                        token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().remoteVolumeChanged(token, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateRemoteController(MediaSession.Token token) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (token != null) {
                        parcel.writeInt(1);
                        token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateRemoteController(token);
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

