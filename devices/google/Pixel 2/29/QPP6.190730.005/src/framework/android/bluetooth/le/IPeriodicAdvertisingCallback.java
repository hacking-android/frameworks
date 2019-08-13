/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.PeriodicAdvertisingReport;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IPeriodicAdvertisingCallback
extends IInterface {
    public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport var1) throws RemoteException;

    public void onSyncEstablished(int var1, BluetoothDevice var2, int var3, int var4, int var5, int var6) throws RemoteException;

    public void onSyncLost(int var1) throws RemoteException;

    public static class Default
    implements IPeriodicAdvertisingCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport periodicAdvertisingReport) throws RemoteException {
        }

        @Override
        public void onSyncEstablished(int n, BluetoothDevice bluetoothDevice, int n2, int n3, int n4, int n5) throws RemoteException {
        }

        @Override
        public void onSyncLost(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPeriodicAdvertisingCallback {
        private static final String DESCRIPTOR = "android.bluetooth.le.IPeriodicAdvertisingCallback";
        static final int TRANSACTION_onPeriodicAdvertisingReport = 2;
        static final int TRANSACTION_onSyncEstablished = 1;
        static final int TRANSACTION_onSyncLost = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPeriodicAdvertisingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPeriodicAdvertisingCallback) {
                return (IPeriodicAdvertisingCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPeriodicAdvertisingCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onSyncLost";
                }
                return "onPeriodicAdvertisingReport";
            }
            return "onSyncEstablished";
        }

        public static boolean setDefaultImpl(IPeriodicAdvertisingCallback iPeriodicAdvertisingCallback) {
            if (Proxy.sDefaultImpl == null && iPeriodicAdvertisingCallback != null) {
                Proxy.sDefaultImpl = iPeriodicAdvertisingCallback;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                        }
                        ((Parcel)object2).writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.onSyncLost(((Parcel)object).readInt());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? PeriodicAdvertisingReport.CREATOR.createFromParcel((Parcel)object) : null;
                this.onPeriodicAdvertisingReport((PeriodicAdvertisingReport)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object2 = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
            this.onSyncEstablished(n, (BluetoothDevice)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
            return true;
        }

        private static class Proxy
        implements IPeriodicAdvertisingCallback {
            public static IPeriodicAdvertisingCallback sDefaultImpl;
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
            public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport periodicAdvertisingReport) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (periodicAdvertisingReport != null) {
                        parcel.writeInt(1);
                        periodicAdvertisingReport.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPeriodicAdvertisingReport(periodicAdvertisingReport);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
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
            public void onSyncEstablished(int n, BluetoothDevice bluetoothDevice, int n2, int n3, int n4, int n5) throws RemoteException {
                void var2_10;
                Parcel parcel;
                block17 : {
                    block16 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                            if (bluetoothDevice != null) {
                                parcel.writeInt(1);
                                bluetoothDevice.writeToParcel(parcel, 0);
                                break block16;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n5);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onSyncEstablished(n, bluetoothDevice, n2, n3, n4, n5);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_10;
            }

            @Override
            public void onSyncLost(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSyncLost(n);
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

