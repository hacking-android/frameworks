/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHdmiMhlVendorCommandListener
extends IInterface {
    public void onReceived(int var1, int var2, int var3, byte[] var4) throws RemoteException;

    public static class Default
    implements IHdmiMhlVendorCommandListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onReceived(int n, int n2, int n3, byte[] arrby) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiMhlVendorCommandListener {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiMhlVendorCommandListener";
        static final int TRANSACTION_onReceived = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiMhlVendorCommandListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiMhlVendorCommandListener) {
                return (IHdmiMhlVendorCommandListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiMhlVendorCommandListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onReceived";
        }

        public static boolean setDefaultImpl(IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) {
            if (Proxy.sDefaultImpl == null && iHdmiMhlVendorCommandListener != null) {
                Proxy.sDefaultImpl = iHdmiMhlVendorCommandListener;
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
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onReceived(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
            return true;
        }

        private static class Proxy
        implements IHdmiMhlVendorCommandListener {
            public static IHdmiMhlVendorCommandListener sDefaultImpl;
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
            public void onReceived(int n, int n2, int n3, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReceived(n, n2, n3, arrby);
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

