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

public interface ITvInputServiceCallback
extends IInterface {
    public void addHardwareInput(int var1, TvInputInfo var2) throws RemoteException;

    public void addHdmiInput(int var1, TvInputInfo var2) throws RemoteException;

    public void removeHardwareInput(String var1) throws RemoteException;

    public static class Default
    implements ITvInputServiceCallback {
        @Override
        public void addHardwareInput(int n, TvInputInfo tvInputInfo) throws RemoteException {
        }

        @Override
        public void addHdmiInput(int n, TvInputInfo tvInputInfo) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void removeHardwareInput(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputServiceCallback {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputServiceCallback";
        static final int TRANSACTION_addHardwareInput = 1;
        static final int TRANSACTION_addHdmiInput = 2;
        static final int TRANSACTION_removeHardwareInput = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputServiceCallback) {
                return (ITvInputServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "removeHardwareInput";
                }
                return "addHdmiInput";
            }
            return "addHardwareInput";
        }

        public static boolean setDefaultImpl(ITvInputServiceCallback iTvInputServiceCallback) {
            if (Proxy.sDefaultImpl == null && iTvInputServiceCallback != null) {
                Proxy.sDefaultImpl = iTvInputServiceCallback;
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
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.removeHardwareInput(((Parcel)object).readString());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.addHdmiInput(n, (TvInputInfo)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.addHardwareInput(n, (TvInputInfo)object);
            return true;
        }

        private static class Proxy
        implements ITvInputServiceCallback {
            public static ITvInputServiceCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addHardwareInput(int n, TvInputInfo tvInputInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (tvInputInfo != null) {
                        parcel.writeInt(1);
                        tvInputInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addHardwareInput(n, tvInputInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void addHdmiInput(int n, TvInputInfo tvInputInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (tvInputInfo != null) {
                        parcel.writeInt(1);
                        tvInputInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addHdmiInput(n, tvInputInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void removeHardwareInput(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeHardwareInput(string2);
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

