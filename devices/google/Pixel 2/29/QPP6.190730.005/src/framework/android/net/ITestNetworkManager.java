/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.LinkAddress;
import android.net.TestNetworkInterface;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITestNetworkManager
extends IInterface {
    public TestNetworkInterface createTapInterface() throws RemoteException;

    public TestNetworkInterface createTunInterface(LinkAddress[] var1) throws RemoteException;

    public void setupTestNetwork(String var1, IBinder var2) throws RemoteException;

    public void teardownTestNetwork(int var1) throws RemoteException;

    public static class Default
    implements ITestNetworkManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public TestNetworkInterface createTapInterface() throws RemoteException {
            return null;
        }

        @Override
        public TestNetworkInterface createTunInterface(LinkAddress[] arrlinkAddress) throws RemoteException {
            return null;
        }

        @Override
        public void setupTestNetwork(String string2, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void teardownTestNetwork(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITestNetworkManager {
        private static final String DESCRIPTOR = "android.net.ITestNetworkManager";
        static final int TRANSACTION_createTapInterface = 2;
        static final int TRANSACTION_createTunInterface = 1;
        static final int TRANSACTION_setupTestNetwork = 3;
        static final int TRANSACTION_teardownTestNetwork = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITestNetworkManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITestNetworkManager) {
                return (ITestNetworkManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITestNetworkManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "teardownTestNetwork";
                    }
                    return "setupTestNetwork";
                }
                return "createTapInterface";
            }
            return "createTunInterface";
        }

        public static boolean setDefaultImpl(ITestNetworkManager iTestNetworkManager) {
            if (Proxy.sDefaultImpl == null && iTestNetworkManager != null) {
                Proxy.sDefaultImpl = iTestNetworkManager;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.teardownTestNetwork(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.setupTestNetwork(((Parcel)object).readString(), ((Parcel)object).readStrongBinder());
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.createTapInterface();
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((TestNetworkInterface)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.createTunInterface(((Parcel)object).createTypedArray(LinkAddress.CREATOR));
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((TestNetworkInterface)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements ITestNetworkManager {
            public static ITestNetworkManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public TestNetworkInterface createTapInterface() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        TestNetworkInterface testNetworkInterface = Stub.getDefaultImpl().createTapInterface();
                        parcel.recycle();
                        parcel2.recycle();
                        return testNetworkInterface;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                TestNetworkInterface testNetworkInterface = parcel.readInt() != 0 ? TestNetworkInterface.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return testNetworkInterface;
            }

            @Override
            public TestNetworkInterface createTunInterface(LinkAddress[] object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeTypedArray((Parcelable[])object, 0);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().createTunInterface((LinkAddress[])object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? TestNetworkInterface.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void setupTestNetwork(String string2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setupTestNetwork(string2, iBinder);
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
            public void teardownTestNetwork(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().teardownTestNetwork(n);
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

