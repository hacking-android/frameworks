/*
 * Decompiled with CFR 0.145.
 */
package android.net.nsd;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INsdManager
extends IInterface {
    @UnsupportedAppUsage
    public Messenger getMessenger() throws RemoteException;

    public void setEnabled(boolean var1) throws RemoteException;

    public static class Default
    implements INsdManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public Messenger getMessenger() throws RemoteException {
            return null;
        }

        @Override
        public void setEnabled(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INsdManager {
        private static final String DESCRIPTOR = "android.net.nsd.INsdManager";
        static final int TRANSACTION_getMessenger = 1;
        static final int TRANSACTION_setEnabled = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INsdManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INsdManager) {
                return (INsdManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INsdManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "setEnabled";
            }
            return "getMessenger";
        }

        public static boolean setDefaultImpl(INsdManager iNsdManager) {
            if (Proxy.sDefaultImpl == null && iNsdManager != null) {
                Proxy.sDefaultImpl = iNsdManager;
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
            boolean bl = false;
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                if (((Parcel)object).readInt() != 0) {
                    bl = true;
                }
                this.setEnabled(bl);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getMessenger();
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((Messenger)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements INsdManager {
            public static INsdManager sDefaultImpl;
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
            public Messenger getMessenger() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Messenger messenger = Stub.getDefaultImpl().getMessenger();
                        parcel.recycle();
                        parcel2.recycle();
                        return messenger;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Messenger messenger = parcel.readInt() != 0 ? Messenger.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return messenger;
            }

            @Override
            public void setEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setEnabled(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

