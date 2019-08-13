/*
 * Decompiled with CFR 0.145.
 */
package android.service.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.trust.ITrustAgentServiceCallback;
import java.util.ArrayList;
import java.util.List;

public interface ITrustAgentService
extends IInterface {
    public void onConfigure(List<PersistableBundle> var1, IBinder var2) throws RemoteException;

    public void onDeviceLocked() throws RemoteException;

    public void onDeviceUnlocked() throws RemoteException;

    public void onEscrowTokenAdded(byte[] var1, long var2, UserHandle var4) throws RemoteException;

    public void onEscrowTokenRemoved(long var1, boolean var3) throws RemoteException;

    public void onTokenStateReceived(long var1, int var3) throws RemoteException;

    public void onTrustTimeout() throws RemoteException;

    public void onUnlockAttempt(boolean var1) throws RemoteException;

    public void onUnlockLockout(int var1) throws RemoteException;

    public void setCallback(ITrustAgentServiceCallback var1) throws RemoteException;

    public static class Default
    implements ITrustAgentService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConfigure(List<PersistableBundle> list, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void onDeviceLocked() throws RemoteException {
        }

        @Override
        public void onDeviceUnlocked() throws RemoteException {
        }

        @Override
        public void onEscrowTokenAdded(byte[] arrby, long l, UserHandle userHandle) throws RemoteException {
        }

        @Override
        public void onEscrowTokenRemoved(long l, boolean bl) throws RemoteException {
        }

        @Override
        public void onTokenStateReceived(long l, int n) throws RemoteException {
        }

        @Override
        public void onTrustTimeout() throws RemoteException {
        }

        @Override
        public void onUnlockAttempt(boolean bl) throws RemoteException {
        }

        @Override
        public void onUnlockLockout(int n) throws RemoteException {
        }

        @Override
        public void setCallback(ITrustAgentServiceCallback iTrustAgentServiceCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITrustAgentService {
        private static final String DESCRIPTOR = "android.service.trust.ITrustAgentService";
        static final int TRANSACTION_onConfigure = 6;
        static final int TRANSACTION_onDeviceLocked = 4;
        static final int TRANSACTION_onDeviceUnlocked = 5;
        static final int TRANSACTION_onEscrowTokenAdded = 8;
        static final int TRANSACTION_onEscrowTokenRemoved = 10;
        static final int TRANSACTION_onTokenStateReceived = 9;
        static final int TRANSACTION_onTrustTimeout = 3;
        static final int TRANSACTION_onUnlockAttempt = 1;
        static final int TRANSACTION_onUnlockLockout = 2;
        static final int TRANSACTION_setCallback = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITrustAgentService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITrustAgentService) {
                return (ITrustAgentService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITrustAgentService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "onEscrowTokenRemoved";
                }
                case 9: {
                    return "onTokenStateReceived";
                }
                case 8: {
                    return "onEscrowTokenAdded";
                }
                case 7: {
                    return "setCallback";
                }
                case 6: {
                    return "onConfigure";
                }
                case 5: {
                    return "onDeviceUnlocked";
                }
                case 4: {
                    return "onDeviceLocked";
                }
                case 3: {
                    return "onTrustTimeout";
                }
                case 2: {
                    return "onUnlockLockout";
                }
                case 1: 
            }
            return "onUnlockAttempt";
        }

        public static boolean setDefaultImpl(ITrustAgentService iTrustAgentService) {
            if (Proxy.sDefaultImpl == null && iTrustAgentService != null) {
                Proxy.sDefaultImpl = iTrustAgentService;
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
        public boolean onTransact(int n, Parcel object, Parcel arrby, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)arrby, n2);
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onEscrowTokenRemoved(l, bl2);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTokenStateReceived(((Parcel)object).readLong(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        arrby = ((Parcel)object).createByteArray();
                        long l = ((Parcel)object).readLong();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onEscrowTokenAdded(arrby, l, (UserHandle)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCallback(ITrustAgentServiceCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onConfigure(((Parcel)object).createTypedArrayList(PersistableBundle.CREATOR), ((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDeviceUnlocked();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDeviceLocked();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTrustTimeout();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onUnlockLockout(((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                bl2 = bl;
                if (((Parcel)object).readInt() != 0) {
                    bl2 = true;
                }
                this.onUnlockAttempt(bl2);
                return true;
            }
            arrby.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITrustAgentService {
            public static ITrustAgentService sDefaultImpl;
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
            public void onConfigure(List<PersistableBundle> list, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfigure(list, iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeviceLocked() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceLocked();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeviceUnlocked() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceUnlocked();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEscrowTokenAdded(byte[] arrby, long l, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeLong(l);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEscrowTokenAdded(arrby, l, userHandle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEscrowTokenRemoved(long l, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeLong(l);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEscrowTokenRemoved(l, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTokenStateReceived(long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTokenStateReceived(l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTrustTimeout() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrustTimeout();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUnlockAttempt(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUnlockAttempt(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUnlockLockout(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUnlockLockout(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setCallback(ITrustAgentServiceCallback iTrustAgentServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTrustAgentServiceCallback != null ? iTrustAgentServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setCallback(iTrustAgentServiceCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

