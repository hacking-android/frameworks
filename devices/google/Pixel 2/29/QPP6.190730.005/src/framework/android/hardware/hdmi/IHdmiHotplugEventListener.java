/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.hardware.hdmi.HdmiHotplugEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IHdmiHotplugEventListener
extends IInterface {
    public void onReceived(HdmiHotplugEvent var1) throws RemoteException;

    public static class Default
    implements IHdmiHotplugEventListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onReceived(HdmiHotplugEvent hdmiHotplugEvent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiHotplugEventListener {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiHotplugEventListener";
        static final int TRANSACTION_onReceived = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiHotplugEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiHotplugEventListener) {
                return (IHdmiHotplugEventListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiHotplugEventListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onReceived";
        }

        public static boolean setDefaultImpl(IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            if (Proxy.sDefaultImpl == null && iHdmiHotplugEventListener != null) {
                Proxy.sDefaultImpl = iHdmiHotplugEventListener;
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
            object = ((Parcel)object).readInt() != 0 ? HdmiHotplugEvent.CREATOR.createFromParcel((Parcel)object) : null;
            this.onReceived((HdmiHotplugEvent)object);
            return true;
        }

        private static class Proxy
        implements IHdmiHotplugEventListener {
            public static IHdmiHotplugEventListener sDefaultImpl;
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
            public void onReceived(HdmiHotplugEvent hdmiHotplugEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hdmiHotplugEvent != null) {
                        parcel.writeInt(1);
                        hdmiHotplugEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReceived(hdmiHotplugEvent);
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

