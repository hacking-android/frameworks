/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IConsumerIrService
extends IInterface {
    public int[] getCarrierFrequencies() throws RemoteException;

    public boolean hasIrEmitter() throws RemoteException;

    public void transmit(String var1, int var2, int[] var3) throws RemoteException;

    public static class Default
    implements IConsumerIrService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int[] getCarrierFrequencies() throws RemoteException {
            return null;
        }

        @Override
        public boolean hasIrEmitter() throws RemoteException {
            return false;
        }

        @Override
        public void transmit(String string2, int n, int[] arrn) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IConsumerIrService {
        private static final String DESCRIPTOR = "android.hardware.IConsumerIrService";
        static final int TRANSACTION_getCarrierFrequencies = 3;
        static final int TRANSACTION_hasIrEmitter = 1;
        static final int TRANSACTION_transmit = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IConsumerIrService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IConsumerIrService) {
                return (IConsumerIrService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IConsumerIrService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "getCarrierFrequencies";
                }
                return "transmit";
            }
            return "hasIrEmitter";
        }

        public static boolean setDefaultImpl(IConsumerIrService iConsumerIrService) {
            if (Proxy.sDefaultImpl == null && iConsumerIrService != null) {
                Proxy.sDefaultImpl = iConsumerIrService;
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
        public boolean onTransact(int n, Parcel arrn, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)arrn, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    arrn.enforceInterface(DESCRIPTOR);
                    arrn = this.getCarrierFrequencies();
                    parcel.writeNoException();
                    parcel.writeIntArray(arrn);
                    return true;
                }
                arrn.enforceInterface(DESCRIPTOR);
                this.transmit(arrn.readString(), arrn.readInt(), arrn.createIntArray());
                parcel.writeNoException();
                return true;
            }
            arrn.enforceInterface(DESCRIPTOR);
            n = this.hasIrEmitter() ? 1 : 0;
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IConsumerIrService {
            public static IConsumerIrService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int[] getCarrierFrequencies() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getCarrierFrequencies();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
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
            public boolean hasIrEmitter() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasIrEmitter();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void transmit(String string2, int n, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().transmit(string2, n, arrn);
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

