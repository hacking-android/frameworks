/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISerialManager
extends IInterface {
    public String[] getSerialPorts() throws RemoteException;

    public ParcelFileDescriptor openSerialPort(String var1) throws RemoteException;

    public static class Default
    implements ISerialManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String[] getSerialPorts() throws RemoteException {
            return null;
        }

        @Override
        public ParcelFileDescriptor openSerialPort(String string2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISerialManager {
        private static final String DESCRIPTOR = "android.hardware.ISerialManager";
        static final int TRANSACTION_getSerialPorts = 1;
        static final int TRANSACTION_openSerialPort = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISerialManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISerialManager) {
                return (ISerialManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISerialManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "openSerialPort";
            }
            return "getSerialPorts";
        }

        public static boolean setDefaultImpl(ISerialManager iSerialManager) {
            if (Proxy.sDefaultImpl == null && iSerialManager != null) {
                Proxy.sDefaultImpl = iSerialManager;
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
                object = this.openSerialPort(((Parcel)object).readString());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getSerialPorts();
            parcel.writeNoException();
            parcel.writeStringArray((String[])object);
            return true;
        }

        private static class Proxy
        implements ISerialManager {
            public static ISerialManager sDefaultImpl;
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
            public String[] getSerialPorts() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getSerialPorts();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParcelFileDescriptor openSerialPort(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().openSerialPort((String)object);
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
                object = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }
        }

    }

}

