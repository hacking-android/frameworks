/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.media.tv.TvInputInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITvInputManagerCallback
extends IInterface {
    public void onInputAdded(String var1) throws RemoteException;

    public void onInputRemoved(String var1) throws RemoteException;

    public void onInputStateChanged(String var1, int var2) throws RemoteException;

    public void onInputUpdated(String var1) throws RemoteException;

    public void onTvInputInfoUpdated(TvInputInfo var1) throws RemoteException;

    public static class Default
    implements ITvInputManagerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onInputAdded(String string2) throws RemoteException {
        }

        @Override
        public void onInputRemoved(String string2) throws RemoteException {
        }

        @Override
        public void onInputStateChanged(String string2, int n) throws RemoteException {
        }

        @Override
        public void onInputUpdated(String string2) throws RemoteException {
        }

        @Override
        public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputManagerCallback {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputManagerCallback";
        static final int TRANSACTION_onInputAdded = 1;
        static final int TRANSACTION_onInputRemoved = 2;
        static final int TRANSACTION_onInputStateChanged = 4;
        static final int TRANSACTION_onInputUpdated = 3;
        static final int TRANSACTION_onTvInputInfoUpdated = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputManagerCallback) {
                return (ITvInputManagerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputManagerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "onTvInputInfoUpdated";
                        }
                        return "onInputStateChanged";
                    }
                    return "onInputUpdated";
                }
                return "onInputRemoved";
            }
            return "onInputAdded";
        }

        public static boolean setDefaultImpl(ITvInputManagerCallback iTvInputManagerCallback) {
            if (Proxy.sDefaultImpl == null && iTvInputManagerCallback != null) {
                Proxy.sDefaultImpl = iTvInputManagerCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            object = ((Parcel)object).readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel((Parcel)object) : null;
                            this.onTvInputInfoUpdated((TvInputInfo)object);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onInputStateChanged(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.onInputUpdated(((Parcel)object).readString());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onInputRemoved(((Parcel)object).readString());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onInputAdded(((Parcel)object).readString());
            return true;
        }

        private static class Proxy
        implements ITvInputManagerCallback {
            public static ITvInputManagerCallback sDefaultImpl;
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
            public void onInputAdded(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputAdded(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onInputRemoved(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputRemoved(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onInputStateChanged(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputStateChanged(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onInputUpdated(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputUpdated(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tvInputInfo != null) {
                        parcel.writeInt(1);
                        tvInputInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTvInputInfoUpdated(tvInputInfo);
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

