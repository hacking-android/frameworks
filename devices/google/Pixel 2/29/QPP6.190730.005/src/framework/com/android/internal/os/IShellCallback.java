/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IShellCallback
extends IInterface {
    public ParcelFileDescriptor openFile(String var1, String var2, String var3) throws RemoteException;

    public static class Default
    implements IShellCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ParcelFileDescriptor openFile(String string2, String string3, String string4) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IShellCallback {
        private static final String DESCRIPTOR = "com.android.internal.os.IShellCallback";
        static final int TRANSACTION_openFile = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IShellCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IShellCallback) {
                return (IShellCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IShellCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "openFile";
        }

        public static boolean setDefaultImpl(IShellCallback iShellCallback) {
            if (Proxy.sDefaultImpl == null && iShellCallback != null) {
                Proxy.sDefaultImpl = iShellCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.openFile(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IShellCallback {
            public static IShellCallback sDefaultImpl;
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
            public ParcelFileDescriptor openFile(String object, String string2, String string3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().openFile((String)object, string2, string3);
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
                object = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }
        }

    }

}

