/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHdmiSystemAudioModeChangeListener
extends IInterface {
    public void onStatusChanged(boolean var1) throws RemoteException;

    public static class Default
    implements IHdmiSystemAudioModeChangeListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStatusChanged(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiSystemAudioModeChangeListener {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiSystemAudioModeChangeListener";
        static final int TRANSACTION_onStatusChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiSystemAudioModeChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiSystemAudioModeChangeListener) {
                return (IHdmiSystemAudioModeChangeListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiSystemAudioModeChangeListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStatusChanged";
        }

        public static boolean setDefaultImpl(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            if (Proxy.sDefaultImpl == null && iHdmiSystemAudioModeChangeListener != null) {
                Proxy.sDefaultImpl = iHdmiSystemAudioModeChangeListener;
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
            boolean bl = parcel.readInt() != 0;
            this.onStatusChanged(bl);
            return true;
        }

        private static class Proxy
        implements IHdmiSystemAudioModeChangeListener {
            public static IHdmiSystemAudioModeChangeListener sDefaultImpl;
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
            public void onStatusChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusChanged(bl);
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

