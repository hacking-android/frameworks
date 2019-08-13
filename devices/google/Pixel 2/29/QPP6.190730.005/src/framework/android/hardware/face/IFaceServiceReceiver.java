/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.face;

import android.hardware.face.Face;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IFaceServiceReceiver
extends IInterface {
    public void onAcquired(long var1, int var3, int var4) throws RemoteException;

    public void onAuthenticationFailed(long var1) throws RemoteException;

    public void onAuthenticationSucceeded(long var1, Face var3, int var4) throws RemoteException;

    public void onEnrollResult(long var1, int var3, int var4) throws RemoteException;

    public void onEnumerated(long var1, int var3, int var4) throws RemoteException;

    public void onError(long var1, int var3, int var4) throws RemoteException;

    public void onFeatureGet(boolean var1, int var2, boolean var3) throws RemoteException;

    public void onFeatureSet(boolean var1, int var2) throws RemoteException;

    public void onRemoved(long var1, int var3, int var4) throws RemoteException;

    public static class Default
    implements IFaceServiceReceiver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAcquired(long l, int n, int n2) throws RemoteException {
        }

        @Override
        public void onAuthenticationFailed(long l) throws RemoteException {
        }

        @Override
        public void onAuthenticationSucceeded(long l, Face face, int n) throws RemoteException {
        }

        @Override
        public void onEnrollResult(long l, int n, int n2) throws RemoteException {
        }

        @Override
        public void onEnumerated(long l, int n, int n2) throws RemoteException {
        }

        @Override
        public void onError(long l, int n, int n2) throws RemoteException {
        }

        @Override
        public void onFeatureGet(boolean bl, int n, boolean bl2) throws RemoteException {
        }

        @Override
        public void onFeatureSet(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void onRemoved(long l, int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFaceServiceReceiver {
        private static final String DESCRIPTOR = "android.hardware.face.IFaceServiceReceiver";
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 4;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onEnumerated = 7;
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onFeatureGet = 9;
        static final int TRANSACTION_onFeatureSet = 8;
        static final int TRANSACTION_onRemoved = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFaceServiceReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFaceServiceReceiver) {
                return (IFaceServiceReceiver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFaceServiceReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "onFeatureGet";
                }
                case 8: {
                    return "onFeatureSet";
                }
                case 7: {
                    return "onEnumerated";
                }
                case 6: {
                    return "onRemoved";
                }
                case 5: {
                    return "onError";
                }
                case 4: {
                    return "onAuthenticationFailed";
                }
                case 3: {
                    return "onAuthenticationSucceeded";
                }
                case 2: {
                    return "onAcquired";
                }
                case 1: 
            }
            return "onEnrollResult";
        }

        public static boolean setDefaultImpl(IFaceServiceReceiver iFaceServiceReceiver) {
            if (Proxy.sDefaultImpl == null && iFaceServiceReceiver != null) {
                Proxy.sDefaultImpl = iFaceServiceReceiver;
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
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        bl = parcel.readInt() != 0;
                        n = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.onFeatureGet(bl, n, bl2);
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bl = true;
                        }
                        this.onFeatureSet(bl, parcel.readInt());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onEnumerated(parcel.readLong(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRemoved(parcel.readLong(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onError(parcel.readLong(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAuthenticationFailed(parcel.readLong());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        long l = parcel.readLong();
                        object = parcel.readInt() != 0 ? Face.CREATOR.createFromParcel(parcel) : null;
                        this.onAuthenticationSucceeded(l, (Face)object, parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAcquired(parcel.readLong(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onEnrollResult(parcel.readLong(), parcel.readInt(), parcel.readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IFaceServiceReceiver {
            public static IFaceServiceReceiver sDefaultImpl;
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
            public void onAcquired(long l, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAcquired(l, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAuthenticationFailed(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAuthenticationFailed(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAuthenticationSucceeded(long l, Face face, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (face != null) {
                        parcel.writeInt(1);
                        face.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAuthenticationSucceeded(l, face, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEnrollResult(long l, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnrollResult(l, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEnumerated(long l, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnumerated(l, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(long l, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(l, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onFeatureGet(boolean bl, int n, boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n3 = 0;
                    n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    n2 = n3;
                    if (!bl2) break block6;
                    n2 = 1;
                }
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFeatureGet(bl, n, bl2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onFeatureSet(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFeatureSet(bl, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRemoved(long l, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRemoved(l, n, n2);
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

