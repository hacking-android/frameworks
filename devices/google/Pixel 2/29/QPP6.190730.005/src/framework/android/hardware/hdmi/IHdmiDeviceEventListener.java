/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IHdmiDeviceEventListener
extends IInterface {
    public void onStatusChanged(HdmiDeviceInfo var1, int var2) throws RemoteException;

    public static class Default
    implements IHdmiDeviceEventListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStatusChanged(HdmiDeviceInfo hdmiDeviceInfo, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiDeviceEventListener {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiDeviceEventListener";
        static final int TRANSACTION_onStatusChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiDeviceEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiDeviceEventListener) {
                return (IHdmiDeviceEventListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiDeviceEventListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStatusChanged";
        }

        public static boolean setDefaultImpl(IHdmiDeviceEventListener iHdmiDeviceEventListener) {
            if (Proxy.sDefaultImpl == null && iHdmiDeviceEventListener != null) {
                Proxy.sDefaultImpl = iHdmiDeviceEventListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? HdmiDeviceInfo.CREATOR.createFromParcel(parcel) : null;
            this.onStatusChanged((HdmiDeviceInfo)object, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IHdmiDeviceEventListener {
            public static IHdmiDeviceEventListener sDefaultImpl;
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
            public void onStatusChanged(HdmiDeviceInfo hdmiDeviceInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hdmiDeviceInfo != null) {
                        parcel.writeInt(1);
                        hdmiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusChanged(hdmiDeviceInfo, n);
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

