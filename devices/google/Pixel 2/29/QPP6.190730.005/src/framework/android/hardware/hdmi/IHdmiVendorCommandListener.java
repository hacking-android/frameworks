/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHdmiVendorCommandListener
extends IInterface {
    public void onControlStateChanged(boolean var1, int var2) throws RemoteException;

    public void onReceived(int var1, int var2, byte[] var3, boolean var4) throws RemoteException;

    public static class Default
    implements IHdmiVendorCommandListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onControlStateChanged(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void onReceived(int n, int n2, byte[] arrby, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiVendorCommandListener {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiVendorCommandListener";
        static final int TRANSACTION_onControlStateChanged = 2;
        static final int TRANSACTION_onReceived = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiVendorCommandListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiVendorCommandListener) {
                return (IHdmiVendorCommandListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiVendorCommandListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onControlStateChanged";
            }
            return "onReceived";
        }

        public static boolean setDefaultImpl(IHdmiVendorCommandListener iHdmiVendorCommandListener) {
            if (Proxy.sDefaultImpl == null && iHdmiVendorCommandListener != null) {
                Proxy.sDefaultImpl = iHdmiVendorCommandListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            boolean bl = false;
            boolean bl2 = false;
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                if (parcel.readInt() != 0) {
                    bl2 = true;
                }
                this.onControlStateChanged(bl2, parcel.readInt());
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = parcel.readInt();
            n2 = parcel.readInt();
            byte[] arrby = parcel.createByteArray();
            bl2 = bl;
            if (parcel.readInt() != 0) {
                bl2 = true;
            }
            this.onReceived(n, n2, arrby, bl2);
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IHdmiVendorCommandListener {
            public static IHdmiVendorCommandListener sDefaultImpl;
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
            public void onControlStateChanged(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onControlStateChanged(bl, n);
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
            public void onReceived(int n, int n2, byte[] arrby, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeByteArray(arrby);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReceived(n, n2, arrby, bl);
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

