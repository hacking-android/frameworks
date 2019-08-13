/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUsbSerialReader
extends IInterface {
    public String getSerial(String var1) throws RemoteException;

    public static class Default
    implements IUsbSerialReader {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String getSerial(String string2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUsbSerialReader {
        private static final String DESCRIPTOR = "android.hardware.usb.IUsbSerialReader";
        static final int TRANSACTION_getSerial = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUsbSerialReader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUsbSerialReader) {
                return (IUsbSerialReader)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUsbSerialReader getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getSerial";
        }

        public static boolean setDefaultImpl(IUsbSerialReader iUsbSerialReader) {
            if (Proxy.sDefaultImpl == null && iUsbSerialReader != null) {
                Proxy.sDefaultImpl = iUsbSerialReader;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getSerial(((Parcel)object).readString());
            parcel.writeNoException();
            parcel.writeString((String)object);
            return true;
        }

        private static class Proxy
        implements IUsbSerialReader {
            public static IUsbSerialReader sDefaultImpl;
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
            public String getSerial(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getSerial(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

