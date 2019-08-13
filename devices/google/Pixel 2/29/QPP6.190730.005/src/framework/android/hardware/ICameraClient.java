/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICameraClient
extends IInterface {

    public static class Default
    implements ICameraClient {
        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICameraClient {
        private static final String DESCRIPTOR = "android.hardware.ICameraClient";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICameraClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICameraClient) {
                return (ICameraClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICameraClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            return null;
        }

        public static boolean setDefaultImpl(ICameraClient iCameraClient) {
            if (Proxy.sDefaultImpl == null && iCameraClient != null) {
                Proxy.sDefaultImpl = iCameraClient;
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
            if (n != 1598968902) {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ICameraClient {
            public static ICameraClient sDefaultImpl;
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
        }

    }

}

