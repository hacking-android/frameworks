/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ContextHubMessage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IContextHubCallback
extends IInterface {
    public void onMessageReceipt(int var1, int var2, ContextHubMessage var3) throws RemoteException;

    public static class Default
    implements IContextHubCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMessageReceipt(int n, int n2, ContextHubMessage contextHubMessage) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContextHubCallback {
        private static final String DESCRIPTOR = "android.hardware.location.IContextHubCallback";
        static final int TRANSACTION_onMessageReceipt = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContextHubCallback) {
                return (IContextHubCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContextHubCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onMessageReceipt";
        }

        public static boolean setDefaultImpl(IContextHubCallback iContextHubCallback) {
            if (Proxy.sDefaultImpl == null && iContextHubCallback != null) {
                Proxy.sDefaultImpl = iContextHubCallback;
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
            n = ((Parcel)object).readInt();
            n2 = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? ContextHubMessage.CREATOR.createFromParcel((Parcel)object) : null;
            this.onMessageReceipt(n, n2, (ContextHubMessage)object);
            return true;
        }

        private static class Proxy
        implements IContextHubCallback {
            public static IContextHubCallback sDefaultImpl;
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
            public void onMessageReceipt(int n, int n2, ContextHubMessage contextHubMessage) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (contextHubMessage != null) {
                        parcel.writeInt(1);
                        contextHubMessage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMessageReceipt(n, n2, contextHubMessage);
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

