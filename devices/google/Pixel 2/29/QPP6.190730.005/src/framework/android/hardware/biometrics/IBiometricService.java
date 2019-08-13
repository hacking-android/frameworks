/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.hardware.biometrics.IBiometricConfirmDeviceCredentialCallback;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBiometricService
extends IInterface {
    public void authenticate(IBinder var1, long var2, int var4, IBiometricServiceReceiver var5, String var6, Bundle var7, IBiometricConfirmDeviceCredentialCallback var8) throws RemoteException;

    public int canAuthenticate(String var1) throws RemoteException;

    public void cancelAuthentication(IBinder var1, String var2) throws RemoteException;

    public void onConfirmDeviceCredentialError(int var1, String var2) throws RemoteException;

    public void onConfirmDeviceCredentialSuccess() throws RemoteException;

    public void onReadyForAuthentication(int var1, boolean var2, int var3) throws RemoteException;

    public void registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback var1) throws RemoteException;

    public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback var1) throws RemoteException;

    public void resetLockout(byte[] var1) throws RemoteException;

    public void setActiveUser(int var1) throws RemoteException;

    public static class Default
    implements IBiometricService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void authenticate(IBinder iBinder, long l, int n, IBiometricServiceReceiver iBiometricServiceReceiver, String string2, Bundle bundle, IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) throws RemoteException {
        }

        @Override
        public int canAuthenticate(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void cancelAuthentication(IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public void onConfirmDeviceCredentialError(int n, String string2) throws RemoteException {
        }

        @Override
        public void onConfirmDeviceCredentialSuccess() throws RemoteException {
        }

        @Override
        public void onReadyForAuthentication(int n, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) throws RemoteException {
        }

        @Override
        public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
        }

        @Override
        public void resetLockout(byte[] arrby) throws RemoteException {
        }

        @Override
        public void setActiveUser(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBiometricService {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricService";
        static final int TRANSACTION_authenticate = 1;
        static final int TRANSACTION_canAuthenticate = 3;
        static final int TRANSACTION_cancelAuthentication = 2;
        static final int TRANSACTION_onConfirmDeviceCredentialError = 9;
        static final int TRANSACTION_onConfirmDeviceCredentialSuccess = 8;
        static final int TRANSACTION_onReadyForAuthentication = 6;
        static final int TRANSACTION_registerCancellationCallback = 10;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 4;
        static final int TRANSACTION_resetLockout = 7;
        static final int TRANSACTION_setActiveUser = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBiometricService) {
                return (IBiometricService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBiometricService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "registerCancellationCallback";
                }
                case 9: {
                    return "onConfirmDeviceCredentialError";
                }
                case 8: {
                    return "onConfirmDeviceCredentialSuccess";
                }
                case 7: {
                    return "resetLockout";
                }
                case 6: {
                    return "onReadyForAuthentication";
                }
                case 5: {
                    return "setActiveUser";
                }
                case 4: {
                    return "registerEnabledOnKeyguardCallback";
                }
                case 3: {
                    return "canAuthenticate";
                }
                case 2: {
                    return "cancelAuthentication";
                }
                case 1: 
            }
            return "authenticate";
        }

        public static boolean setDefaultImpl(IBiometricService iBiometricService) {
            if (Proxy.sDefaultImpl == null && iBiometricService != null) {
                Proxy.sDefaultImpl = iBiometricService;
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
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onConfirmDeviceCredentialError(parcel.readInt(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onConfirmDeviceCredentialSuccess();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.resetLockout(parcel.createByteArray());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        boolean bl = parcel.readInt() != 0;
                        this.onReadyForAuthentication(n, bl, parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setActiveUser(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.canAuthenticate(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.cancelAuthentication(parcel.readStrongBinder(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                IBinder iBinder = parcel.readStrongBinder();
                long l = parcel.readLong();
                n = parcel.readInt();
                IBiometricServiceReceiver iBiometricServiceReceiver = IBiometricServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                String string2 = parcel.readString();
                Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                this.authenticate(iBinder, l, n, iBiometricServiceReceiver, string2, bundle, IBiometricConfirmDeviceCredentialCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBiometricService {
            public static IBiometricService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void authenticate(IBinder iBinder, long l, int n, IBiometricServiceReceiver iBiometricServiceReceiver, String string2, Bundle bundle, IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) throws RemoteException {
                Parcel parcel2;
                Parcel parcel;
                void var1_5;
                block11 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block11;
                    }
                    try {
                        parcel.writeLong(l);
                        parcel.writeInt(n);
                        Object var11_14 = null;
                        IBinder iBinder2 = iBiometricServiceReceiver != null ? iBiometricServiceReceiver.asBinder() : null;
                        parcel.writeStrongBinder(iBinder2);
                        parcel.writeString(string2);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        iBinder2 = var11_14;
                        if (iBiometricConfirmDeviceCredentialCallback != null) {
                            iBinder2 = iBiometricConfirmDeviceCredentialCallback.asBinder();
                        }
                        parcel.writeStrongBinder(iBinder2);
                        if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().authenticate(iBinder, l, n, iBiometricServiceReceiver, string2, bundle, iBiometricConfirmDeviceCredentialCallback);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public int canAuthenticate(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().canAuthenticate(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void cancelAuthentication(IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAuthentication(iBinder, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onConfirmDeviceCredentialError(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfirmDeviceCredentialError(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onConfirmDeviceCredentialSuccess() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfirmDeviceCredentialSuccess();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onReadyForAuthentication(int n, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReadyForAuthentication(n, bl, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iBiometricConfirmDeviceCredentialCallback != null ? iBiometricConfirmDeviceCredentialCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCancellationCallback(iBiometricConfirmDeviceCredentialCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iBiometricEnabledOnKeyguardCallback != null ? iBiometricEnabledOnKeyguardCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void resetLockout(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetLockout(arrby);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setActiveUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActiveUser(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

