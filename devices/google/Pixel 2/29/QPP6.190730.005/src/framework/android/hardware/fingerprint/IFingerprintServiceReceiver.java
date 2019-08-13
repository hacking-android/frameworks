/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.fingerprint;

import android.hardware.fingerprint.Fingerprint;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IFingerprintServiceReceiver
extends IInterface {
    public void onAcquired(long var1, int var3, int var4) throws RemoteException;

    public void onAuthenticationFailed(long var1) throws RemoteException;

    public void onAuthenticationSucceeded(long var1, Fingerprint var3, int var4) throws RemoteException;

    public void onEnrollResult(long var1, int var3, int var4, int var5) throws RemoteException;

    public void onEnumerated(long var1, int var3, int var4, int var5) throws RemoteException;

    public void onError(long var1, int var3, int var4) throws RemoteException;

    public void onRemoved(long var1, int var3, int var4, int var5) throws RemoteException;

    public static class Default
    implements IFingerprintServiceReceiver {
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
        public void onAuthenticationSucceeded(long l, Fingerprint fingerprint, int n) throws RemoteException {
        }

        @Override
        public void onEnrollResult(long l, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onEnumerated(long l, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onError(long l, int n, int n2) throws RemoteException {
        }

        @Override
        public void onRemoved(long l, int n, int n2, int n3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFingerprintServiceReceiver {
        private static final String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintServiceReceiver";
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 4;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onEnumerated = 7;
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onRemoved = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprintServiceReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFingerprintServiceReceiver) {
                return (IFingerprintServiceReceiver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFingerprintServiceReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
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

        public static boolean setDefaultImpl(IFingerprintServiceReceiver iFingerprintServiceReceiver) {
            if (Proxy.sDefaultImpl == null && iFingerprintServiceReceiver != null) {
                Proxy.sDefaultImpl = iFingerprintServiceReceiver;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onEnumerated(parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRemoved(parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readInt());
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
                        object = parcel.readInt() != 0 ? Fingerprint.CREATOR.createFromParcel(parcel) : null;
                        this.onAuthenticationSucceeded(l, (Fingerprint)object, parcel.readInt());
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
                this.onEnrollResult(parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IFingerprintServiceReceiver {
            public static IFingerprintServiceReceiver sDefaultImpl;
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
            public void onAuthenticationSucceeded(long l, Fingerprint fingerprint, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (fingerprint != null) {
                        parcel.writeInt(1);
                        fingerprint.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAuthenticationSucceeded(l, fingerprint, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEnrollResult(long l, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnrollResult(l, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEnumerated(long l, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnumerated(l, n, n2, n3);
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
            public void onRemoved(long l, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRemoved(l, n, n2, n3);
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

