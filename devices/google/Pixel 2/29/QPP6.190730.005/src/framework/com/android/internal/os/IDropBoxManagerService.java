/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.DropBoxManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IDropBoxManagerService
extends IInterface {
    public void add(DropBoxManager.Entry var1) throws RemoteException;

    @UnsupportedAppUsage
    public DropBoxManager.Entry getNextEntry(String var1, long var2, String var4) throws RemoteException;

    public boolean isTagEnabled(String var1) throws RemoteException;

    public static class Default
    implements IDropBoxManagerService {
        @Override
        public void add(DropBoxManager.Entry entry) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public DropBoxManager.Entry getNextEntry(String string2, long l, String string3) throws RemoteException {
            return null;
        }

        @Override
        public boolean isTagEnabled(String string2) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDropBoxManagerService {
        private static final String DESCRIPTOR = "com.android.internal.os.IDropBoxManagerService";
        static final int TRANSACTION_add = 1;
        static final int TRANSACTION_getNextEntry = 3;
        static final int TRANSACTION_isTagEnabled = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDropBoxManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDropBoxManagerService) {
                return (IDropBoxManagerService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDropBoxManagerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "getNextEntry";
                }
                return "isTagEnabled";
            }
            return "add";
        }

        public static boolean setDefaultImpl(IDropBoxManagerService iDropBoxManagerService) {
            if (Proxy.sDefaultImpl == null && iDropBoxManagerService != null) {
                Proxy.sDefaultImpl = iDropBoxManagerService;
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
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = this.getNextEntry(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((DropBoxManager.Entry)object).writeToParcel(parcel, 1);
                    } else {
                        parcel.writeInt(0);
                    }
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.isTagEnabled(((Parcel)object).readString()) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? DropBoxManager.Entry.CREATOR.createFromParcel((Parcel)object) : null;
            this.add((DropBoxManager.Entry)object);
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IDropBoxManagerService {
            public static IDropBoxManagerService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void add(DropBoxManager.Entry entry) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (entry != null) {
                        parcel.writeInt(1);
                        entry.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().add(entry);
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
            public DropBoxManager.Entry getNextEntry(String object, long l, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeLong(l);
                        parcel.writeString(string2);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getNextEntry((String)object, l, string2);
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
                object = parcel2.readInt() != 0 ? DropBoxManager.Entry.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public boolean isTagEnabled(String string2) throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isTagEnabled(string2);
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
        }

    }

}

