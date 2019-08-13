/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IMbmsGroupCallSessionCallback
extends IInterface {
    public void onAvailableSaisUpdated(List var1, List var2) throws RemoteException;

    public void onError(int var1, String var2) throws RemoteException;

    public void onMiddlewareReady() throws RemoteException;

    public void onServiceInterfaceAvailable(String var1, int var2) throws RemoteException;

    public static class Default
    implements IMbmsGroupCallSessionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAvailableSaisUpdated(List list, List list2) throws RemoteException {
        }

        @Override
        public void onError(int n, String string2) throws RemoteException {
        }

        @Override
        public void onMiddlewareReady() throws RemoteException {
        }

        @Override
        public void onServiceInterfaceAvailable(String string2, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMbmsGroupCallSessionCallback {
        private static final String DESCRIPTOR = "android.telephony.mbms.IMbmsGroupCallSessionCallback";
        static final int TRANSACTION_onAvailableSaisUpdated = 2;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onMiddlewareReady = 4;
        static final int TRANSACTION_onServiceInterfaceAvailable = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMbmsGroupCallSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMbmsGroupCallSessionCallback) {
                return (IMbmsGroupCallSessionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMbmsGroupCallSessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onMiddlewareReady";
                    }
                    return "onServiceInterfaceAvailable";
                }
                return "onAvailableSaisUpdated";
            }
            return "onError";
        }

        public static boolean setDefaultImpl(IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback) {
            if (Proxy.sDefaultImpl == null && iMbmsGroupCallSessionCallback != null) {
                Proxy.sDefaultImpl = iMbmsGroupCallSessionCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, (Parcel)object, n2);
                            }
                            ((Parcel)object).writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onMiddlewareReady();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onServiceInterfaceAvailable(parcel.readString(), parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = this.getClass().getClassLoader();
                this.onAvailableSaisUpdated(parcel.readArrayList((ClassLoader)object), parcel.readArrayList((ClassLoader)object));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onError(parcel.readInt(), parcel.readString());
            return true;
        }

        private static class Proxy
        implements IMbmsGroupCallSessionCallback {
            public static IMbmsGroupCallSessionCallback sDefaultImpl;
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
            public void onAvailableSaisUpdated(List list, List list2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeList(list);
                    parcel.writeList(list2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAvailableSaisUpdated(list, list2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
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
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
            public void onServiceInterfaceAvailable(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onServiceInterfaceAvailable(string2, n);
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

