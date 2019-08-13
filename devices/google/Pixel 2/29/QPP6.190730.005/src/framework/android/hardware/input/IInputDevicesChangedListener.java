/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInputDevicesChangedListener
extends IInterface {
    public void onInputDevicesChanged(int[] var1) throws RemoteException;

    public static class Default
    implements IInputDevicesChangedListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onInputDevicesChanged(int[] arrn) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputDevicesChangedListener {
        private static final String DESCRIPTOR = "android.hardware.input.IInputDevicesChangedListener";
        static final int TRANSACTION_onInputDevicesChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputDevicesChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputDevicesChangedListener) {
                return (IInputDevicesChangedListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputDevicesChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onInputDevicesChanged";
        }

        public static boolean setDefaultImpl(IInputDevicesChangedListener iInputDevicesChangedListener) {
            if (Proxy.sDefaultImpl == null && iInputDevicesChangedListener != null) {
                Proxy.sDefaultImpl = iInputDevicesChangedListener;
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
            this.onInputDevicesChanged(parcel.createIntArray());
            return true;
        }

        private static class Proxy
        implements IInputDevicesChangedListener {
            public static IInputDevicesChangedListener sDefaultImpl;
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
            public void onInputDevicesChanged(int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputDevicesChanged(arrn);
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

