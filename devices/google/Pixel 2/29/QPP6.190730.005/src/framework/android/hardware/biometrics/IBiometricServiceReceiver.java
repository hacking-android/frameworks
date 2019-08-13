/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBiometricServiceReceiver
extends IInterface {
    public void onAcquired(int var1, String var2) throws RemoteException;

    public void onAuthenticationFailed() throws RemoteException;

    public void onAuthenticationSucceeded() throws RemoteException;

    public void onDialogDismissed(int var1) throws RemoteException;

    public void onError(int var1, String var2) throws RemoteException;

    public static class Default
    implements IBiometricServiceReceiver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAcquired(int n, String string2) throws RemoteException {
        }

        @Override
        public void onAuthenticationFailed() throws RemoteException {
        }

        @Override
        public void onAuthenticationSucceeded() throws RemoteException {
        }

        @Override
        public void onDialogDismissed(int n) throws RemoteException {
        }

        @Override
        public void onError(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBiometricServiceReceiver {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricServiceReceiver";
        static final int TRANSACTION_onAcquired = 4;
        static final int TRANSACTION_onAuthenticationFailed = 2;
        static final int TRANSACTION_onAuthenticationSucceeded = 1;
        static final int TRANSACTION_onDialogDismissed = 5;
        static final int TRANSACTION_onError = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricServiceReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBiometricServiceReceiver) {
                return (IBiometricServiceReceiver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBiometricServiceReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "onDialogDismissed";
                        }
                        return "onAcquired";
                    }
                    return "onError";
                }
                return "onAuthenticationFailed";
            }
            return "onAuthenticationSucceeded";
        }

        public static boolean setDefaultImpl(IBiometricServiceReceiver iBiometricServiceReceiver) {
            if (Proxy.sDefaultImpl == null && iBiometricServiceReceiver != null) {
                Proxy.sDefaultImpl = iBiometricServiceReceiver;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.onDialogDismissed(parcel.readInt());
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAcquired(parcel.readInt(), parcel.readString());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onError(parcel.readInt(), parcel.readString());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onAuthenticationFailed();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onAuthenticationSucceeded();
            return true;
        }

        private static class Proxy
        implements IBiometricServiceReceiver {
            public static IBiometricServiceReceiver sDefaultImpl;
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
            public void onAcquired(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAcquired(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAuthenticationFailed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAuthenticationFailed();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAuthenticationSucceeded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAuthenticationSucceeded();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDialogDismissed(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDialogDismissed(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n, string2);
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

