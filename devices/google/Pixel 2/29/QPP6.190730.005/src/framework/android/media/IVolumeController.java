/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVolumeController
extends IInterface {
    public void dismiss() throws RemoteException;

    public void displaySafeVolumeWarning(int var1) throws RemoteException;

    public void masterMuteChanged(int var1) throws RemoteException;

    public void setA11yMode(int var1) throws RemoteException;

    public void setLayoutDirection(int var1) throws RemoteException;

    public void volumeChanged(int var1, int var2) throws RemoteException;

    public static class Default
    implements IVolumeController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dismiss() throws RemoteException {
        }

        @Override
        public void displaySafeVolumeWarning(int n) throws RemoteException {
        }

        @Override
        public void masterMuteChanged(int n) throws RemoteException {
        }

        @Override
        public void setA11yMode(int n) throws RemoteException {
        }

        @Override
        public void setLayoutDirection(int n) throws RemoteException {
        }

        @Override
        public void volumeChanged(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVolumeController {
        private static final String DESCRIPTOR = "android.media.IVolumeController";
        static final int TRANSACTION_dismiss = 5;
        static final int TRANSACTION_displaySafeVolumeWarning = 1;
        static final int TRANSACTION_masterMuteChanged = 3;
        static final int TRANSACTION_setA11yMode = 6;
        static final int TRANSACTION_setLayoutDirection = 4;
        static final int TRANSACTION_volumeChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVolumeController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVolumeController) {
                return (IVolumeController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVolumeController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "setA11yMode";
                }
                case 5: {
                    return "dismiss";
                }
                case 4: {
                    return "setLayoutDirection";
                }
                case 3: {
                    return "masterMuteChanged";
                }
                case 2: {
                    return "volumeChanged";
                }
                case 1: 
            }
            return "displaySafeVolumeWarning";
        }

        public static boolean setDefaultImpl(IVolumeController iVolumeController) {
            if (Proxy.sDefaultImpl == null && iVolumeController != null) {
                Proxy.sDefaultImpl = iVolumeController;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setA11yMode(parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.dismiss();
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setLayoutDirection(parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.masterMuteChanged(parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.volumeChanged(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.displaySafeVolumeWarning(parcel.readInt());
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IVolumeController {
            public static IVolumeController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dismiss() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismiss();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void displaySafeVolumeWarning(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().displaySafeVolumeWarning(n);
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
            public void masterMuteChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().masterMuteChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setA11yMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setA11yMode(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setLayoutDirection(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLayoutDirection(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void volumeChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().volumeChanged(n, n2);
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

