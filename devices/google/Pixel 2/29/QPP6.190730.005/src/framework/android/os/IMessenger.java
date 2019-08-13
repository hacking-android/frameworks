/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMessenger
extends IInterface {
    public void send(Message var1) throws RemoteException;

    public static class Default
    implements IMessenger {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void send(Message message) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMessenger {
        private static final String DESCRIPTOR = "android.os.IMessenger";
        static final int TRANSACTION_send = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMessenger asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMessenger) {
                return (IMessenger)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMessenger getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "send";
        }

        public static boolean setDefaultImpl(IMessenger iMessenger) {
            if (Proxy.sDefaultImpl == null && iMessenger != null) {
                Proxy.sDefaultImpl = iMessenger;
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
            object = ((Parcel)object).readInt() != 0 ? Message.CREATOR.createFromParcel((Parcel)object) : null;
            this.send((Message)object);
            return true;
        }

        private static class Proxy
        implements IMessenger {
            public static IMessenger sDefaultImpl;
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
            public void send(Message message) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (message != null) {
                        parcel.writeInt(1);
                        message.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().send(message);
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

