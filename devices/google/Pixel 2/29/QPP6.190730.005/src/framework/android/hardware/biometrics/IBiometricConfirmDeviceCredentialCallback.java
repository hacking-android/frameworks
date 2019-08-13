/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBiometricConfirmDeviceCredentialCallback
extends IInterface {
    public void cancel() throws RemoteException;

    public static class Default
    implements IBiometricConfirmDeviceCredentialCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancel() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBiometricConfirmDeviceCredentialCallback {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricConfirmDeviceCredentialCallback";
        static final int TRANSACTION_cancel = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricConfirmDeviceCredentialCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBiometricConfirmDeviceCredentialCallback) {
                return (IBiometricConfirmDeviceCredentialCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBiometricConfirmDeviceCredentialCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "cancel";
        }

        public static boolean setDefaultImpl(IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) {
            if (Proxy.sDefaultImpl == null && iBiometricConfirmDeviceCredentialCallback != null) {
                Proxy.sDefaultImpl = iBiometricConfirmDeviceCredentialCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.cancel();
            return true;
        }

        private static class Proxy
        implements IBiometricConfirmDeviceCredentialCallback {
            public static IBiometricConfirmDeviceCredentialCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancel();
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
        }

    }

}

