/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.net.wifi.rtt.IRttCallback;
import android.net.wifi.rtt.RangingRequest;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;

public interface IWifiRttManager
extends IInterface {
    public void cancelRanging(WorkSource var1) throws RemoteException;

    public boolean isAvailable() throws RemoteException;

    public void startRanging(IBinder var1, String var2, WorkSource var3, RangingRequest var4, IRttCallback var5) throws RemoteException;

    public static class Default
    implements IWifiRttManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelRanging(WorkSource workSource) throws RemoteException {
        }

        @Override
        public boolean isAvailable() throws RemoteException {
            return false;
        }

        @Override
        public void startRanging(IBinder iBinder, String string2, WorkSource workSource, RangingRequest rangingRequest, IRttCallback iRttCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiRttManager {
        private static final String DESCRIPTOR = "android.net.wifi.rtt.IWifiRttManager";
        static final int TRANSACTION_cancelRanging = 3;
        static final int TRANSACTION_isAvailable = 1;
        static final int TRANSACTION_startRanging = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiRttManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiRttManager) {
                return (IWifiRttManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiRttManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "cancelRanging";
                }
                return "startRanging";
            }
            return "isAvailable";
        }

        public static boolean setDefaultImpl(IWifiRttManager iWifiRttManager) {
            if (Proxy.sDefaultImpl == null && iWifiRttManager != null) {
                Proxy.sDefaultImpl = iWifiRttManager;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                    this.cancelRanging((WorkSource)object);
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IBinder iBinder = ((Parcel)object).readStrongBinder();
                String string2 = ((Parcel)object).readString();
                WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                RangingRequest rangingRequest = ((Parcel)object).readInt() != 0 ? RangingRequest.CREATOR.createFromParcel((Parcel)object) : null;
                this.startRanging(iBinder, string2, workSource, rangingRequest, IRttCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = this.isAvailable() ? 1 : 0;
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IWifiRttManager {
            public static IWifiRttManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelRanging(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelRanging(workSource);
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
            public boolean isAvailable() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isAvailable();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startRanging(IBinder iBinder, String string2, WorkSource workSource, RangingRequest rangingRequest, IRttCallback iRttCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rangingRequest != null) {
                        parcel.writeInt(1);
                        rangingRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder2 = iRttCallback != null ? iRttCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startRanging(iBinder, string2, workSource, rangingRequest, iRttCallback);
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

