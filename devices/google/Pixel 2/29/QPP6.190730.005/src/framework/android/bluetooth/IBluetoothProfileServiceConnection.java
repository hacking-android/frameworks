/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBluetoothProfileServiceConnection
extends IInterface {
    public void onServiceConnected(ComponentName var1, IBinder var2) throws RemoteException;

    public void onServiceDisconnected(ComponentName var1) throws RemoteException;

    public static class Default
    implements IBluetoothProfileServiceConnection {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothProfileServiceConnection {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothProfileServiceConnection";
        static final int TRANSACTION_onServiceConnected = 1;
        static final int TRANSACTION_onServiceDisconnected = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothProfileServiceConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothProfileServiceConnection) {
                return (IBluetoothProfileServiceConnection)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothProfileServiceConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onServiceDisconnected";
            }
            return "onServiceConnected";
        }

        public static boolean setDefaultImpl(IBluetoothProfileServiceConnection iBluetoothProfileServiceConnection) {
            if (Proxy.sDefaultImpl == null && iBluetoothProfileServiceConnection != null) {
                Proxy.sDefaultImpl = iBluetoothProfileServiceConnection;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    ((Parcel)object2).writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                this.onServiceDisconnected((ComponentName)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
            this.onServiceConnected((ComponentName)object2, ((Parcel)object).readStrongBinder());
            return true;
        }

        private static class Proxy
        implements IBluetoothProfileServiceConnection {
            public static IBluetoothProfileServiceConnection sDefaultImpl;
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
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onServiceConnected(componentName, iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onServiceDisconnected(componentName);
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

