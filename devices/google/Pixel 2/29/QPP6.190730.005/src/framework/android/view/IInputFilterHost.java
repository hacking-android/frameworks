/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.InputEvent;

public interface IInputFilterHost
extends IInterface {
    public void sendInputEvent(InputEvent var1, int var2) throws RemoteException;

    public static class Default
    implements IInputFilterHost {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void sendInputEvent(InputEvent inputEvent, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputFilterHost {
        private static final String DESCRIPTOR = "android.view.IInputFilterHost";
        static final int TRANSACTION_sendInputEvent = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputFilterHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputFilterHost) {
                return (IInputFilterHost)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputFilterHost getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "sendInputEvent";
        }

        public static boolean setDefaultImpl(IInputFilterHost iInputFilterHost) {
            if (Proxy.sDefaultImpl == null && iInputFilterHost != null) {
                Proxy.sDefaultImpl = iInputFilterHost;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? InputEvent.CREATOR.createFromParcel(parcel) : null;
            this.sendInputEvent((InputEvent)object, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IInputFilterHost {
            public static IInputFilterHost sDefaultImpl;
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
            public void sendInputEvent(InputEvent inputEvent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputEvent != null) {
                        parcel.writeInt(1);
                        inputEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendInputEvent(inputEvent, n);
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

