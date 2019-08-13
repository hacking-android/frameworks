/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.mbms.StreamingServiceInfo;
import java.util.ArrayList;
import java.util.List;

public interface IMbmsStreamingSessionCallback
extends IInterface {
    public void onError(int var1, String var2) throws RemoteException;

    public void onMiddlewareReady() throws RemoteException;

    public void onStreamingServicesUpdated(List<StreamingServiceInfo> var1) throws RemoteException;

    public static class Default
    implements IMbmsStreamingSessionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onError(int n, String string2) throws RemoteException {
        }

        @Override
        public void onMiddlewareReady() throws RemoteException {
        }

        @Override
        public void onStreamingServicesUpdated(List<StreamingServiceInfo> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMbmsStreamingSessionCallback {
        private static final String DESCRIPTOR = "android.telephony.mbms.IMbmsStreamingSessionCallback";
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onMiddlewareReady = 3;
        static final int TRANSACTION_onStreamingServicesUpdated = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMbmsStreamingSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMbmsStreamingSessionCallback) {
                return (IMbmsStreamingSessionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMbmsStreamingSessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onMiddlewareReady";
                }
                return "onStreamingServicesUpdated";
            }
            return "onError";
        }

        public static boolean setDefaultImpl(IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback) {
            if (Proxy.sDefaultImpl == null && iMbmsStreamingSessionCallback != null) {
                Proxy.sDefaultImpl = iMbmsStreamingSessionCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onMiddlewareReady();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onStreamingServicesUpdated(parcel.createTypedArrayList(StreamingServiceInfo.CREATOR));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onError(parcel.readInt(), parcel.readString());
            return true;
        }

        private static class Proxy
        implements IMbmsStreamingSessionCallback {
            public static IMbmsStreamingSessionCallback sDefaultImpl;
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
            public void onError(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMiddlewareReady() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMiddlewareReady();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStreamingServicesUpdated(List<StreamingServiceInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStreamingServicesUpdated(list);
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

