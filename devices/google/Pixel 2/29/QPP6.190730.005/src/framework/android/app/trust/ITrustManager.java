/*
 * Decompiled with CFR 0.145.
 */
package android.app.trust;

import android.app.trust.ITrustListener;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITrustManager
extends IInterface {
    public void clearAllBiometricRecognized(BiometricSourceType var1) throws RemoteException;

    public boolean isDeviceLocked(int var1) throws RemoteException;

    public boolean isDeviceSecure(int var1) throws RemoteException;

    public boolean isTrustUsuallyManaged(int var1) throws RemoteException;

    public void registerTrustListener(ITrustListener var1) throws RemoteException;

    public void reportEnabledTrustAgentsChanged(int var1) throws RemoteException;

    public void reportKeyguardShowingChanged() throws RemoteException;

    public void reportUnlockAttempt(boolean var1, int var2) throws RemoteException;

    public void reportUnlockLockout(int var1, int var2) throws RemoteException;

    public void setDeviceLockedForUser(int var1, boolean var2) throws RemoteException;

    public void unlockedByBiometricForUser(int var1, BiometricSourceType var2) throws RemoteException;

    public void unregisterTrustListener(ITrustListener var1) throws RemoteException;

    public static class Default
    implements ITrustManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType) throws RemoteException {
        }

        @Override
        public boolean isDeviceLocked(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDeviceSecure(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isTrustUsuallyManaged(int n) throws RemoteException {
            return false;
        }

        @Override
        public void registerTrustListener(ITrustListener iTrustListener) throws RemoteException {
        }

        @Override
        public void reportEnabledTrustAgentsChanged(int n) throws RemoteException {
        }

        @Override
        public void reportKeyguardShowingChanged() throws RemoteException {
        }

        @Override
        public void reportUnlockAttempt(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void reportUnlockLockout(int n, int n2) throws RemoteException {
        }

        @Override
        public void setDeviceLockedForUser(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void unlockedByBiometricForUser(int n, BiometricSourceType biometricSourceType) throws RemoteException {
        }

        @Override
        public void unregisterTrustListener(ITrustListener iTrustListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITrustManager {
        private static final String DESCRIPTOR = "android.app.trust.ITrustManager";
        static final int TRANSACTION_clearAllBiometricRecognized = 12;
        static final int TRANSACTION_isDeviceLocked = 8;
        static final int TRANSACTION_isDeviceSecure = 9;
        static final int TRANSACTION_isTrustUsuallyManaged = 10;
        static final int TRANSACTION_registerTrustListener = 4;
        static final int TRANSACTION_reportEnabledTrustAgentsChanged = 3;
        static final int TRANSACTION_reportKeyguardShowingChanged = 6;
        static final int TRANSACTION_reportUnlockAttempt = 1;
        static final int TRANSACTION_reportUnlockLockout = 2;
        static final int TRANSACTION_setDeviceLockedForUser = 7;
        static final int TRANSACTION_unlockedByBiometricForUser = 11;
        static final int TRANSACTION_unregisterTrustListener = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITrustManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITrustManager) {
                return (ITrustManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITrustManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 12: {
                    return "clearAllBiometricRecognized";
                }
                case 11: {
                    return "unlockedByBiometricForUser";
                }
                case 10: {
                    return "isTrustUsuallyManaged";
                }
                case 9: {
                    return "isDeviceSecure";
                }
                case 8: {
                    return "isDeviceLocked";
                }
                case 7: {
                    return "setDeviceLockedForUser";
                }
                case 6: {
                    return "reportKeyguardShowingChanged";
                }
                case 5: {
                    return "unregisterTrustListener";
                }
                case 4: {
                    return "registerTrustListener";
                }
                case 3: {
                    return "reportEnabledTrustAgentsChanged";
                }
                case 2: {
                    return "reportUnlockLockout";
                }
                case 1: 
            }
            return "reportUnlockAttempt";
        }

        public static boolean setDefaultImpl(ITrustManager iTrustManager) {
            if (Proxy.sDefaultImpl == null && iTrustManager != null) {
                Proxy.sDefaultImpl = iTrustManager;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BiometricSourceType.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clearAllBiometricRecognized((BiometricSourceType)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? BiometricSourceType.CREATOR.createFromParcel((Parcel)object) : null;
                        this.unlockedByBiometricForUser(n, (BiometricSourceType)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTrustUsuallyManaged(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDeviceSecure(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDeviceLocked(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setDeviceLockedForUser(n, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportKeyguardShowingChanged();
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterTrustListener(ITrustListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerTrustListener(ITrustListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportEnabledTrustAgentsChanged(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportUnlockLockout(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                bl2 = bl;
                if (((Parcel)object).readInt() != 0) {
                    bl2 = true;
                }
                this.reportUnlockAttempt(bl2, ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITrustManager {
            public static ITrustManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (biometricSourceType != null) {
                        parcel.writeInt(1);
                        biometricSourceType.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearAllBiometricRecognized(biometricSourceType);
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
            public boolean isDeviceLocked(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDeviceLocked(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isDeviceSecure(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDeviceSecure(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isTrustUsuallyManaged(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(10, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTrustUsuallyManaged(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerTrustListener(ITrustListener iTrustListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTrustListener != null ? iTrustListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTrustListener(iTrustListener);
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
            public void reportEnabledTrustAgentsChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportEnabledTrustAgentsChanged(n);
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
            public void reportKeyguardShowingChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportKeyguardShowingChanged();
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
            public void reportUnlockAttempt(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportUnlockAttempt(bl, n);
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
            public void reportUnlockLockout(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportUnlockLockout(n, n2);
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
            public void setDeviceLockedForUser(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceLockedForUser(n, bl);
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
            public void unlockedByBiometricForUser(int n, BiometricSourceType biometricSourceType) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (biometricSourceType != null) {
                        parcel.writeInt(1);
                        biometricSourceType.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unlockedByBiometricForUser(n, biometricSourceType);
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
            public void unregisterTrustListener(ITrustListener iTrustListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTrustListener != null ? iTrustListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTrustListener(iTrustListener);
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

