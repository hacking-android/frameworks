/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGnssStatusListener
extends IInterface {
    public void onFirstFix(int var1) throws RemoteException;

    public void onGnssStarted() throws RemoteException;

    public void onGnssStopped() throws RemoteException;

    public void onNmeaReceived(long var1, String var3) throws RemoteException;

    public void onSvStatusChanged(int var1, int[] var2, float[] var3, float[] var4, float[] var5, float[] var6) throws RemoteException;

    public static class Default
    implements IGnssStatusListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFirstFix(int n) throws RemoteException {
        }

        @Override
        public void onGnssStarted() throws RemoteException {
        }

        @Override
        public void onGnssStopped() throws RemoteException {
        }

        @Override
        public void onNmeaReceived(long l, String string2) throws RemoteException {
        }

        @Override
        public void onSvStatusChanged(int n, int[] arrn, float[] arrf, float[] arrf2, float[] arrf3, float[] arrf4) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGnssStatusListener {
        private static final String DESCRIPTOR = "android.location.IGnssStatusListener";
        static final int TRANSACTION_onFirstFix = 3;
        static final int TRANSACTION_onGnssStarted = 1;
        static final int TRANSACTION_onGnssStopped = 2;
        static final int TRANSACTION_onNmeaReceived = 5;
        static final int TRANSACTION_onSvStatusChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGnssStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGnssStatusListener) {
                return (IGnssStatusListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGnssStatusListener getDefaultImpl() {
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
                            return "onNmeaReceived";
                        }
                        return "onSvStatusChanged";
                    }
                    return "onFirstFix";
                }
                return "onGnssStopped";
            }
            return "onGnssStarted";
        }

        public static boolean setDefaultImpl(IGnssStatusListener iGnssStatusListener) {
            if (Proxy.sDefaultImpl == null && iGnssStatusListener != null) {
                Proxy.sDefaultImpl = iGnssStatusListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.onNmeaReceived(parcel.readLong(), parcel.readString());
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSvStatusChanged(parcel.readInt(), parcel.createIntArray(), parcel.createFloatArray(), parcel.createFloatArray(), parcel.createFloatArray(), parcel.createFloatArray());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onFirstFix(parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onGnssStopped();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onGnssStarted();
            return true;
        }

        private static class Proxy
        implements IGnssStatusListener {
            public static IGnssStatusListener sDefaultImpl;
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
            public void onFirstFix(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFirstFix(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGnssStarted() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGnssStarted();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGnssStopped() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGnssStopped();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNmeaReceived(long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNmeaReceived(l, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onSvStatusChanged(int n, int[] arrn, float[] arrf, float[] arrf2, float[] arrf3, float[] arrf4) throws RemoteException {
                Parcel parcel;
                void var2_11;
                block17 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeIntArray(arrn);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeFloatArray(arrf);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeFloatArray(arrf2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeFloatArray(arrf3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeFloatArray(arrf4);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onSvStatusChanged(n, arrn, arrf, arrf2, arrf3, arrf4);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_11;
            }
        }

    }

}

