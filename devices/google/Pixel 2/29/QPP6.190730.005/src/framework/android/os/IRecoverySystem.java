/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRecoverySystemProgressListener;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRecoverySystem
extends IInterface {
    public boolean clearBcb() throws RemoteException;

    public void rebootRecoveryWithCommand(String var1) throws RemoteException;

    public boolean setupBcb(String var1) throws RemoteException;

    public boolean uncrypt(String var1, IRecoverySystemProgressListener var2) throws RemoteException;

    public static class Default
    implements IRecoverySystem {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean clearBcb() throws RemoteException {
            return false;
        }

        @Override
        public void rebootRecoveryWithCommand(String string2) throws RemoteException {
        }

        @Override
        public boolean setupBcb(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean uncrypt(String string2, IRecoverySystemProgressListener iRecoverySystemProgressListener) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecoverySystem {
        private static final String DESCRIPTOR = "android.os.IRecoverySystem";
        static final int TRANSACTION_clearBcb = 3;
        static final int TRANSACTION_rebootRecoveryWithCommand = 4;
        static final int TRANSACTION_setupBcb = 2;
        static final int TRANSACTION_uncrypt = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecoverySystem asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecoverySystem) {
                return (IRecoverySystem)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecoverySystem getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "rebootRecoveryWithCommand";
                    }
                    return "clearBcb";
                }
                return "setupBcb";
            }
            return "uncrypt";
        }

        public static boolean setDefaultImpl(IRecoverySystem iRecoverySystem) {
            if (Proxy.sDefaultImpl == null && iRecoverySystem != null) {
                Proxy.sDefaultImpl = iRecoverySystem;
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
                        this.rebootRecoveryWithCommand(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = this.clearBcb() ? 1 : 0;
                    parcel2.writeNoException();
                    parcel2.writeInt(n);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = this.setupBcb(parcel.readString()) ? 1 : 0;
                parcel2.writeNoException();
                parcel2.writeInt(n);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = this.uncrypt(parcel.readString(), IRecoverySystemProgressListener.Stub.asInterface(parcel.readStrongBinder())) ? 1 : 0;
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IRecoverySystem {
            public static IRecoverySystem sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean clearBcb() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().clearBcb();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void rebootRecoveryWithCommand(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rebootRecoveryWithCommand(string2);
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
            public boolean setupBcb(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setupBcb(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean uncrypt(String string2, IRecoverySystemProgressListener iRecoverySystemProgressListener) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeString(string2);
                                if (iRecoverySystemProgressListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iRecoverySystemProgressListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().uncrypt(string2, iRecoverySystemProgressListener);
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
        }

    }

}

