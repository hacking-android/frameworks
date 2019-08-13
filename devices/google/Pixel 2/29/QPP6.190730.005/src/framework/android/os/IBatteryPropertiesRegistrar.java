/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.BatteryProperty;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBatteryPropertiesRegistrar
extends IInterface {
    public int getProperty(int var1, BatteryProperty var2) throws RemoteException;

    public void scheduleUpdate() throws RemoteException;

    public static class Default
    implements IBatteryPropertiesRegistrar {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int getProperty(int n, BatteryProperty batteryProperty) throws RemoteException {
            return 0;
        }

        @Override
        public void scheduleUpdate() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBatteryPropertiesRegistrar {
        private static final String DESCRIPTOR = "android.os.IBatteryPropertiesRegistrar";
        static final int TRANSACTION_getProperty = 1;
        static final int TRANSACTION_scheduleUpdate = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBatteryPropertiesRegistrar asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBatteryPropertiesRegistrar) {
                return (IBatteryPropertiesRegistrar)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBatteryPropertiesRegistrar getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "scheduleUpdate";
            }
            return "getProperty";
        }

        public static boolean setDefaultImpl(IBatteryPropertiesRegistrar iBatteryPropertiesRegistrar) {
            if (Proxy.sDefaultImpl == null && iBatteryPropertiesRegistrar != null) {
                Proxy.sDefaultImpl = iBatteryPropertiesRegistrar;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.scheduleUpdate();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = new BatteryProperty();
            n = this.getProperty(n, (BatteryProperty)object);
            parcel.writeNoException();
            parcel.writeInt(n);
            parcel.writeInt(1);
            ((BatteryProperty)object).writeToParcel(parcel, 1);
            return true;
        }

        private static class Proxy
        implements IBatteryPropertiesRegistrar {
            public static IBatteryPropertiesRegistrar sDefaultImpl;
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
            public int getProperty(int n, BatteryProperty batteryProperty) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getProperty(n, batteryProperty);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (parcel2.readInt() != 0) {
                        batteryProperty.readFromParcel(parcel2);
                    }
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleUpdate() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleUpdate();
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

