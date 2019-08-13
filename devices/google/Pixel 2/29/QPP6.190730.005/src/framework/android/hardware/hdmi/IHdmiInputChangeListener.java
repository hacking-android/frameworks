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

public interface IHdmiInputChangeListener
extends IInterface {
    public void onChanged(HdmiDeviceInfo var1) throws RemoteException;

    public static class Default
    implements IHdmiInputChangeListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onChanged(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiInputChangeListener {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiInputChangeListener";
        static final int TRANSACTION_onChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiInputChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiInputChangeListener) {
                return (IHdmiInputChangeListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiInputChangeListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onChanged";
        }

        public static boolean setDefaultImpl(IHdmiInputChangeListener iHdmiInputChangeListener) {
            if (Proxy.sDefaultImpl == null && iHdmiInputChangeListener != null) {
                Proxy.sDefaultImpl = iHdmiInputChangeListener;
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
            object = ((Parcel)object).readInt() != 0 ? HdmiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onChanged((HdmiDeviceInfo)object);
            return true;
        }

        private static class Proxy
        implements IHdmiInputChangeListener {
            public static IHdmiInputChangeListener sDefaultImpl;
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
            public void onChanged(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hdmiDeviceInfo != null) {
                        parcel.writeInt(1);
                        hdmiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onChanged(hdmiDeviceInfo);
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

