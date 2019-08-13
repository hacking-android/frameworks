/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISensorPrivacyListener
extends IInterface {
    public void onSensorPrivacyChanged(boolean var1) throws RemoteException;

    public static class Default
    implements ISensorPrivacyListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSensorPrivacyChanged(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISensorPrivacyListener {
        private static final String DESCRIPTOR = "android.hardware.ISensorPrivacyListener";
        static final int TRANSACTION_onSensorPrivacyChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISensorPrivacyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISensorPrivacyListener) {
                return (ISensorPrivacyListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISensorPrivacyListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSensorPrivacyChanged";
        }

        public static boolean setDefaultImpl(ISensorPrivacyListener iSensorPrivacyListener) {
            if (Proxy.sDefaultImpl == null && iSensorPrivacyListener != null) {
                Proxy.sDefaultImpl = iSensorPrivacyListener;
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
            this.onSensorPrivacyChanged(bl);
            return true;
        }

        private static class Proxy
        implements ISensorPrivacyListener {
            public static ISensorPrivacyListener sDefaultImpl;
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
            public void onSensorPrivacyChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSensorPrivacyChanged(bl);
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

