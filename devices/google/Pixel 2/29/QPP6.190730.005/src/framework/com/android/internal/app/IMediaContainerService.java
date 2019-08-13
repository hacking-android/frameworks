/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.content.pm.PackageInfoLite;
import android.content.res.ObbInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.os.IParcelFileDescriptorFactory;

public interface IMediaContainerService
extends IInterface {
    public long calculateInstalledSize(String var1, String var2) throws RemoteException;

    public int copyPackage(String var1, IParcelFileDescriptorFactory var2) throws RemoteException;

    public PackageInfoLite getMinimalPackageInfo(String var1, int var2, String var3) throws RemoteException;

    public ObbInfo getObbInfo(String var1) throws RemoteException;

    public static class Default
    implements IMediaContainerService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public long calculateInstalledSize(String string2, String string3) throws RemoteException {
            return 0L;
        }

        @Override
        public int copyPackage(String string2, IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws RemoteException {
            return 0;
        }

        @Override
        public PackageInfoLite getMinimalPackageInfo(String string2, int n, String string3) throws RemoteException {
            return null;
        }

        @Override
        public ObbInfo getObbInfo(String string2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaContainerService {
        private static final String DESCRIPTOR = "com.android.internal.app.IMediaContainerService";
        static final int TRANSACTION_calculateInstalledSize = 4;
        static final int TRANSACTION_copyPackage = 1;
        static final int TRANSACTION_getMinimalPackageInfo = 2;
        static final int TRANSACTION_getObbInfo = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaContainerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaContainerService) {
                return (IMediaContainerService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaContainerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "calculateInstalledSize";
                    }
                    return "getObbInfo";
                }
                return "getMinimalPackageInfo";
            }
            return "copyPackage";
        }

        public static boolean setDefaultImpl(IMediaContainerService iMediaContainerService) {
            if (Proxy.sDefaultImpl == null && iMediaContainerService != null) {
                Proxy.sDefaultImpl = iMediaContainerService;
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
                        long l = this.calculateInstalledSize(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = this.getObbInfo(((Parcel)object).readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ObbInfo)object).writeToParcel(parcel, 1);
                    } else {
                        parcel.writeInt(0);
                    }
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getMinimalPackageInfo(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((PackageInfoLite)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = this.copyPackage(((Parcel)object).readString(), IParcelFileDescriptorFactory.Stub.asInterface(((Parcel)object).readStrongBinder()));
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IMediaContainerService {
            public static IMediaContainerService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public long calculateInstalledSize(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().calculateInstalledSize(string2, string3);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int copyPackage(String string2, IParcelFileDescriptorFactory iParcelFileDescriptorFactory) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iParcelFileDescriptorFactory != null ? iParcelFileDescriptorFactory.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().copyPackage(string2, iParcelFileDescriptorFactory);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public PackageInfoLite getMinimalPackageInfo(String object, int n, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeInt(n);
                        parcel.writeString(string2);
                        if (this.mRemote.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getMinimalPackageInfo((String)object, n, string2);
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
                object = parcel2.readInt() != 0 ? PackageInfoLite.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public ObbInfo getObbInfo(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getObbInfo((String)object);
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
                object = parcel2.readInt() != 0 ? ObbInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }
        }

    }

}

