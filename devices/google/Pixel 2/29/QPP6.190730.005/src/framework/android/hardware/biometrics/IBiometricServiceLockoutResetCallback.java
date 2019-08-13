/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBiometricServiceLockoutResetCallback
extends IInterface {
    public void onLockoutReset(long var1, IRemoteCallback var3) throws RemoteException;

    public static class Default
    implements IBiometricServiceLockoutResetCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onLockoutReset(long l, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBiometricServiceLockoutResetCallback {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricServiceLockoutResetCallback";
        static final int TRANSACTION_onLockoutReset = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricServiceLockoutResetCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBiometricServiceLockoutResetCallback) {
                return (IBiometricServiceLockoutResetCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBiometricServiceLockoutResetCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onLockoutReset";
        }

        public static boolean setDefaultImpl(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback) {
            if (Proxy.sDefaultImpl == null && iBiometricServiceLockoutResetCallback != null) {
                Proxy.sDefaultImpl = iBiometricServiceLockoutResetCallback;
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
            this.onLockoutReset(parcel.readLong(), IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IBiometricServiceLockoutResetCallback {
            public static IBiometricServiceLockoutResetCallback sDefaultImpl;
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
            public void onLockoutReset(long l, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    IBinder iBinder = iRemoteCallback != null ? iRemoteCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onLockoutReset(l, iRemoteCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

