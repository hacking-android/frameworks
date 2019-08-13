/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.ISensorPrivacyListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISensorPrivacyManager
extends IInterface {
    public void addSensorPrivacyListener(ISensorPrivacyListener var1) throws RemoteException;

    public boolean isSensorPrivacyEnabled() throws RemoteException;

    public void removeSensorPrivacyListener(ISensorPrivacyListener var1) throws RemoteException;

    public void setSensorPrivacy(boolean var1) throws RemoteException;

    public static class Default
    implements ISensorPrivacyManager {
        @Override
        public void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean isSensorPrivacyEnabled() throws RemoteException {
            return false;
        }

        @Override
        public void removeSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
        }

        @Override
        public void setSensorPrivacy(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISensorPrivacyManager {
        private static final String DESCRIPTOR = "android.hardware.ISensorPrivacyManager";
        static final int TRANSACTION_addSensorPrivacyListener = 1;
        static final int TRANSACTION_isSensorPrivacyEnabled = 3;
        static final int TRANSACTION_removeSensorPrivacyListener = 2;
        static final int TRANSACTION_setSensorPrivacy = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISensorPrivacyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISensorPrivacyManager) {
                return (ISensorPrivacyManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISensorPrivacyManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "setSensorPrivacy";
                    }
                    return "isSensorPrivacyEnabled";
                }
                return "removeSensorPrivacyListener";
            }
            return "addSensorPrivacyListener";
        }

        public static boolean setDefaultImpl(ISensorPrivacyManager iSensorPrivacyManager) {
            if (Proxy.sDefaultImpl == null && iSensorPrivacyManager != null) {
                Proxy.sDefaultImpl = iSensorPrivacyManager;
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
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, parcel2, n2);
                            }
                            parcel2.writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean bl = parcel.readInt() != 0;
                        this.setSensorPrivacy(bl);
                        parcel2.writeNoException();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = this.isSensorPrivacyEnabled() ? 1 : 0;
                    parcel2.writeNoException();
                    parcel2.writeInt(n);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.removeSensorPrivacyListener(ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.addSensorPrivacyListener(ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements ISensorPrivacyManager {
            public static ISensorPrivacyManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSensorPrivacyListener != null ? iSensorPrivacyListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSensorPrivacyListener(iSensorPrivacyListener);
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

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean isSensorPrivacyEnabled() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSensorPrivacyEnabled();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSensorPrivacyListener != null ? iSensorPrivacyListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSensorPrivacyListener(iSensorPrivacyListener);
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

            @Override
            public void setSensorPrivacy(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSensorPrivacy(bl);
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

