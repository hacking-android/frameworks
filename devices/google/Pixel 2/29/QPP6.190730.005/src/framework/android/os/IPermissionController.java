/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPermissionController
extends IInterface {
    public boolean checkPermission(String var1, int var2, int var3) throws RemoteException;

    public int getPackageUid(String var1, int var2) throws RemoteException;

    public String[] getPackagesForUid(int var1) throws RemoteException;

    public boolean isRuntimePermission(String var1) throws RemoteException;

    public int noteOp(String var1, int var2, String var3) throws RemoteException;

    public static class Default
    implements IPermissionController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean checkPermission(String string2, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public int getPackageUid(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public String[] getPackagesForUid(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean isRuntimePermission(String string2) throws RemoteException {
            return false;
        }

        @Override
        public int noteOp(String string2, int n, String string3) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPermissionController {
        private static final String DESCRIPTOR = "android.os.IPermissionController";
        static final int TRANSACTION_checkPermission = 1;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 3;
        static final int TRANSACTION_isRuntimePermission = 4;
        static final int TRANSACTION_noteOp = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPermissionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPermissionController) {
                return (IPermissionController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPermissionController getDefaultImpl() {
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
                            return "getPackageUid";
                        }
                        return "isRuntimePermission";
                    }
                    return "getPackagesForUid";
                }
                return "noteOp";
            }
            return "checkPermission";
        }

        public static boolean setDefaultImpl(IPermissionController iPermissionController) {
            if (Proxy.sDefaultImpl == null && iPermissionController != null) {
                Proxy.sDefaultImpl = iPermissionController;
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
        public boolean onTransact(int n, Parcel arrstring, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)arrstring, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            arrstring.enforceInterface(DESCRIPTOR);
                            n = this.getPackageUid(arrstring.readString(), arrstring.readInt());
                            parcel.writeNoException();
                            parcel.writeInt(n);
                            return true;
                        }
                        arrstring.enforceInterface(DESCRIPTOR);
                        n = this.isRuntimePermission(arrstring.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    arrstring.enforceInterface(DESCRIPTOR);
                    arrstring = this.getPackagesForUid(arrstring.readInt());
                    parcel.writeNoException();
                    parcel.writeStringArray(arrstring);
                    return true;
                }
                arrstring.enforceInterface(DESCRIPTOR);
                n = this.noteOp(arrstring.readString(), arrstring.readInt(), arrstring.readString());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            arrstring.enforceInterface(DESCRIPTOR);
            n = this.checkPermission(arrstring.readString(), arrstring.readInt(), arrstring.readInt()) ? 1 : 0;
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IPermissionController {
            public static IPermissionController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean checkPermission(String string2, int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().checkPermission(string2, n, n2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getPackageUid(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPackageUid(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getPackagesForUid(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getPackagesForUid(n);
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isRuntimePermission(String string2) throws RemoteException {
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
                    if (iBinder.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRuntimePermission(string2);
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
            public int noteOp(String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().noteOp(string2, n, string3);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

