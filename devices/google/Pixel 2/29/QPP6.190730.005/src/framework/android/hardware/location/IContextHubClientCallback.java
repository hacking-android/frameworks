/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.NanoAppMessage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IContextHubClientCallback
extends IInterface {
    public void onHubReset() throws RemoteException;

    public void onMessageFromNanoApp(NanoAppMessage var1) throws RemoteException;

    public void onNanoAppAborted(long var1, int var3) throws RemoteException;

    public void onNanoAppDisabled(long var1) throws RemoteException;

    public void onNanoAppEnabled(long var1) throws RemoteException;

    public void onNanoAppLoaded(long var1) throws RemoteException;

    public void onNanoAppUnloaded(long var1) throws RemoteException;

    public static class Default
    implements IContextHubClientCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onHubReset() throws RemoteException {
        }

        @Override
        public void onMessageFromNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
        }

        @Override
        public void onNanoAppAborted(long l, int n) throws RemoteException {
        }

        @Override
        public void onNanoAppDisabled(long l) throws RemoteException {
        }

        @Override
        public void onNanoAppEnabled(long l) throws RemoteException {
        }

        @Override
        public void onNanoAppLoaded(long l) throws RemoteException {
        }

        @Override
        public void onNanoAppUnloaded(long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContextHubClientCallback {
        private static final String DESCRIPTOR = "android.hardware.location.IContextHubClientCallback";
        static final int TRANSACTION_onHubReset = 2;
        static final int TRANSACTION_onMessageFromNanoApp = 1;
        static final int TRANSACTION_onNanoAppAborted = 3;
        static final int TRANSACTION_onNanoAppDisabled = 7;
        static final int TRANSACTION_onNanoAppEnabled = 6;
        static final int TRANSACTION_onNanoAppLoaded = 4;
        static final int TRANSACTION_onNanoAppUnloaded = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubClientCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContextHubClientCallback) {
                return (IContextHubClientCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContextHubClientCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "onNanoAppDisabled";
                }
                case 6: {
                    return "onNanoAppEnabled";
                }
                case 5: {
                    return "onNanoAppUnloaded";
                }
                case 4: {
                    return "onNanoAppLoaded";
                }
                case 3: {
                    return "onNanoAppAborted";
                }
                case 2: {
                    return "onHubReset";
                }
                case 1: 
            }
            return "onMessageFromNanoApp";
        }

        public static boolean setDefaultImpl(IContextHubClientCallback iContextHubClientCallback) {
            if (Proxy.sDefaultImpl == null && iContextHubClientCallback != null) {
                Proxy.sDefaultImpl = iContextHubClientCallback;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNanoAppDisabled(((Parcel)object).readLong());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNanoAppEnabled(((Parcel)object).readLong());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNanoAppUnloaded(((Parcel)object).readLong());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNanoAppLoaded(((Parcel)object).readLong());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNanoAppAborted(((Parcel)object).readLong(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onHubReset();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? NanoAppMessage.CREATOR.createFromParcel((Parcel)object) : null;
                this.onMessageFromNanoApp((NanoAppMessage)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IContextHubClientCallback {
            public static IContextHubClientCallback sDefaultImpl;
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
            public void onHubReset() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHubReset();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMessageFromNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (nanoAppMessage != null) {
                        parcel.writeInt(1);
                        nanoAppMessage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMessageFromNanoApp(nanoAppMessage);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNanoAppAborted(long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNanoAppAborted(l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNanoAppDisabled(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNanoAppDisabled(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNanoAppEnabled(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNanoAppEnabled(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNanoAppLoaded(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNanoAppLoaded(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNanoAppUnloaded(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNanoAppUnloaded(l);
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

