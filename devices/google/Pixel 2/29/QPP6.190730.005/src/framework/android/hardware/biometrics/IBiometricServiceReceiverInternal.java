/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBiometricServiceReceiverInternal
extends IInterface {
    public void onAcquired(int var1, String var2) throws RemoteException;

    public void onAuthenticationFailed(int var1, boolean var2) throws RemoteException;

    public void onAuthenticationSucceeded(boolean var1, byte[] var2) throws RemoteException;

    public void onDialogDismissed(int var1) throws RemoteException;

    public void onError(int var1, int var2, String var3) throws RemoteException;

    public void onTryAgainPressed() throws RemoteException;

    public static class Default
    implements IBiometricServiceReceiverInternal {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAcquired(int n, String string2) throws RemoteException {
        }

        @Override
        public void onAuthenticationFailed(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onAuthenticationSucceeded(boolean bl, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onDialogDismissed(int n) throws RemoteException {
        }

        @Override
        public void onError(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void onTryAgainPressed() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBiometricServiceReceiverInternal {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricServiceReceiverInternal";
        static final int TRANSACTION_onAcquired = 4;
        static final int TRANSACTION_onAuthenticationFailed = 2;
        static final int TRANSACTION_onAuthenticationSucceeded = 1;
        static final int TRANSACTION_onDialogDismissed = 5;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onTryAgainPressed = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricServiceReceiverInternal asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBiometricServiceReceiverInternal) {
                return (IBiometricServiceReceiverInternal)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBiometricServiceReceiverInternal getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "onTryAgainPressed";
                }
                case 5: {
                    return "onDialogDismissed";
                }
                case 4: {
                    return "onAcquired";
                }
                case 3: {
                    return "onError";
                }
                case 2: {
                    return "onAuthenticationFailed";
                }
                case 1: 
            }
            return "onAuthenticationSucceeded";
        }

        public static boolean setDefaultImpl(IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal) {
            if (Proxy.sDefaultImpl == null && iBiometricServiceReceiverInternal != null) {
                Proxy.sDefaultImpl = iBiometricServiceReceiverInternal;
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
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onTryAgainPressed();
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDialogDismissed(parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAcquired(parcel.readInt(), parcel.readString());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onError(parcel.readInt(), parcel.readInt(), parcel.readString());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.onAuthenticationFailed(n, bl2);
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                bl2 = bl;
                if (parcel.readInt() != 0) {
                    bl2 = true;
                }
                this.onAuthenticationSucceeded(bl2, parcel.createByteArray());
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBiometricServiceReceiverInternal {
            public static IBiometricServiceReceiverInternal sDefaultImpl;
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
            public void onAuthenticationFailed(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAuthenticationFailed(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAuthenticationSucceeded(boolean bl, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAuthenticationSucceeded(bl, arrby);
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
            public void onError(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n, n2, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTryAgainPressed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTryAgainPressed();
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

