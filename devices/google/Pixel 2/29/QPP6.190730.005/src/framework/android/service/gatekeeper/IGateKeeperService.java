/*
 * Decompiled with CFR 0.145.
 */
package android.service.gatekeeper;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.gatekeeper.GateKeeperResponse;

public interface IGateKeeperService
extends IInterface {
    public void clearSecureUserId(int var1) throws RemoteException;

    public GateKeeperResponse enroll(int var1, byte[] var2, byte[] var3, byte[] var4) throws RemoteException;

    public long getSecureUserId(int var1) throws RemoteException;

    public void reportDeviceSetupComplete() throws RemoteException;

    public GateKeeperResponse verify(int var1, byte[] var2, byte[] var3) throws RemoteException;

    public GateKeeperResponse verifyChallenge(int var1, long var2, byte[] var4, byte[] var5) throws RemoteException;

    public static class Default
    implements IGateKeeperService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearSecureUserId(int n) throws RemoteException {
        }

        @Override
        public GateKeeperResponse enroll(int n, byte[] arrby, byte[] arrby2, byte[] arrby3) throws RemoteException {
            return null;
        }

        @Override
        public long getSecureUserId(int n) throws RemoteException {
            return 0L;
        }

        @Override
        public void reportDeviceSetupComplete() throws RemoteException {
        }

        @Override
        public GateKeeperResponse verify(int n, byte[] arrby, byte[] arrby2) throws RemoteException {
            return null;
        }

        @Override
        public GateKeeperResponse verifyChallenge(int n, long l, byte[] arrby, byte[] arrby2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGateKeeperService {
        private static final String DESCRIPTOR = "android.service.gatekeeper.IGateKeeperService";
        static final int TRANSACTION_clearSecureUserId = 5;
        static final int TRANSACTION_enroll = 1;
        static final int TRANSACTION_getSecureUserId = 4;
        static final int TRANSACTION_reportDeviceSetupComplete = 6;
        static final int TRANSACTION_verify = 2;
        static final int TRANSACTION_verifyChallenge = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGateKeeperService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGateKeeperService) {
                return (IGateKeeperService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGateKeeperService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "reportDeviceSetupComplete";
                }
                case 5: {
                    return "clearSecureUserId";
                }
                case 4: {
                    return "getSecureUserId";
                }
                case 3: {
                    return "verifyChallenge";
                }
                case 2: {
                    return "verify";
                }
                case 1: 
            }
            return "enroll";
        }

        public static boolean setDefaultImpl(IGateKeeperService iGateKeeperService) {
            if (Proxy.sDefaultImpl == null && iGateKeeperService != null) {
                Proxy.sDefaultImpl = iGateKeeperService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportDeviceSetupComplete();
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearSecureUserId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getSecureUserId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.verifyChallenge(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((GateKeeperResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.verify(((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((GateKeeperResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.enroll(((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((GateKeeperResponse)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IGateKeeperService {
            public static IGateKeeperService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearSecureUserId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearSecureUserId(n);
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
            public GateKeeperResponse enroll(int n, byte[] object, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeByteArray((byte[])object);
                        parcel2.writeByteArray(arrby);
                        parcel2.writeByteArray(arrby2);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().enroll(n, (byte[])object, arrby, arrby2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? GateKeeperResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public long getSecureUserId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getSecureUserId(n);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void reportDeviceSetupComplete() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportDeviceSetupComplete();
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
            public GateKeeperResponse verify(int n, byte[] object, byte[] arrby) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeByteArray((byte[])object);
                        parcel.writeByteArray(arrby);
                        if (this.mRemote.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().verify(n, (byte[])object, arrby);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? GateKeeperResponse.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public GateKeeperResponse verifyChallenge(int n, long l, byte[] object, byte[] arrby) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeLong(l);
                        parcel2.writeByteArray((byte[])object);
                        parcel2.writeByteArray(arrby);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().verifyChallenge(n, l, (byte[])object, arrby);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? GateKeeperResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }
        }

    }

}

