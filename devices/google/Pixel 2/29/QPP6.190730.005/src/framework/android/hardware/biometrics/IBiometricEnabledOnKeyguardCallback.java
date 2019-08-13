/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.hardware.biometrics.BiometricSourceType;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBiometricEnabledOnKeyguardCallback
extends IInterface {
    public void onChanged(BiometricSourceType var1, boolean var2) throws RemoteException;

    public static class Default
    implements IBiometricEnabledOnKeyguardCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onChanged(BiometricSourceType biometricSourceType, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBiometricEnabledOnKeyguardCallback {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback";
        static final int TRANSACTION_onChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricEnabledOnKeyguardCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBiometricEnabledOnKeyguardCallback) {
                return (IBiometricEnabledOnKeyguardCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBiometricEnabledOnKeyguardCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onChanged";
        }

        public static boolean setDefaultImpl(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
            if (Proxy.sDefaultImpl == null && iBiometricEnabledOnKeyguardCallback != null) {
                Proxy.sDefaultImpl = iBiometricEnabledOnKeyguardCallback;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? BiometricSourceType.CREATOR.createFromParcel(parcel) : null;
            boolean bl = parcel.readInt() != 0;
            this.onChanged((BiometricSourceType)object, bl);
            return true;
        }

        private static class Proxy
        implements IBiometricEnabledOnKeyguardCallback {
            public static IBiometricEnabledOnKeyguardCallback sDefaultImpl;
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
            public void onChanged(BiometricSourceType biometricSourceType, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (biometricSourceType != null) {
                        parcel.writeInt(1);
                        biometricSourceType.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onChanged(biometricSourceType, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

