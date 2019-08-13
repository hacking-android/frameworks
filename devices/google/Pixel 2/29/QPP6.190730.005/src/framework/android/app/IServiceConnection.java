/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IServiceConnection
extends IInterface {
    @UnsupportedAppUsage
    public void connected(ComponentName var1, IBinder var2, boolean var3) throws RemoteException;

    public static class Default
    implements IServiceConnection {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void connected(ComponentName componentName, IBinder iBinder, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IServiceConnection {
        private static final String DESCRIPTOR = "android.app.IServiceConnection";
        static final int TRANSACTION_connected = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IServiceConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IServiceConnection) {
                return (IServiceConnection)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IServiceConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "connected";
        }

        public static boolean setDefaultImpl(IServiceConnection iServiceConnection) {
            if (Proxy.sDefaultImpl == null && iServiceConnection != null) {
                Proxy.sDefaultImpl = iServiceConnection;
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
            object = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
            IBinder iBinder = parcel.readStrongBinder();
            boolean bl = parcel.readInt() != 0;
            this.connected((ComponentName)object, iBinder, bl);
            return true;
        }

        private static class Proxy
        implements IServiceConnection {
            public static IServiceConnection sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void connected(ComponentName componentName, IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().connected(componentName, iBinder, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

