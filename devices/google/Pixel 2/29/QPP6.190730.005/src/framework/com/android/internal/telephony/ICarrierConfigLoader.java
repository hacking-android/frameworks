/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;

public interface ICarrierConfigLoader
extends IInterface {
    @UnsupportedAppUsage
    public PersistableBundle getConfigForSubId(int var1, String var2) throws RemoteException;

    public String getDefaultCarrierServicePackageName() throws RemoteException;

    public void notifyConfigChangedForSubId(int var1) throws RemoteException;

    public void overrideConfig(int var1, PersistableBundle var2) throws RemoteException;

    public void updateConfigForPhoneId(int var1, String var2) throws RemoteException;

    public static class Default
    implements ICarrierConfigLoader {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public PersistableBundle getConfigForSubId(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getDefaultCarrierServicePackageName() throws RemoteException {
            return null;
        }

        @Override
        public void notifyConfigChangedForSubId(int n) throws RemoteException {
        }

        @Override
        public void overrideConfig(int n, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override
        public void updateConfigForPhoneId(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICarrierConfigLoader {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ICarrierConfigLoader";
        static final int TRANSACTION_getConfigForSubId = 1;
        static final int TRANSACTION_getDefaultCarrierServicePackageName = 5;
        static final int TRANSACTION_notifyConfigChangedForSubId = 3;
        static final int TRANSACTION_overrideConfig = 2;
        static final int TRANSACTION_updateConfigForPhoneId = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierConfigLoader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICarrierConfigLoader) {
                return (ICarrierConfigLoader)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICarrierConfigLoader getDefaultImpl() {
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
                            return "getDefaultCarrierServicePackageName";
                        }
                        return "updateConfigForPhoneId";
                    }
                    return "notifyConfigChangedForSubId";
                }
                return "overrideConfig";
            }
            return "getConfigForSubId";
        }

        public static boolean setDefaultImpl(ICarrierConfigLoader iCarrierConfigLoader) {
            if (Proxy.sDefaultImpl == null && iCarrierConfigLoader != null) {
                Proxy.sDefaultImpl = iCarrierConfigLoader;
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
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            object = this.getDefaultCarrierServicePackageName();
                            parcel.writeNoException();
                            parcel.writeString((String)object);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateConfigForPhoneId(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.notifyConfigChangedForSubId(((Parcel)object).readInt());
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.overrideConfig(n, (PersistableBundle)object);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getConfigForSubId(((Parcel)object).readInt(), ((Parcel)object).readString());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((PersistableBundle)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements ICarrierConfigLoader {
            public static ICarrierConfigLoader sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public PersistableBundle getConfigForSubId(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getConfigForSubId(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public String getDefaultCarrierServicePackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getDefaultCarrierServicePackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
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
            public void notifyConfigChangedForSubId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyConfigChangedForSubId(n);
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
            public void overrideConfig(int n, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overrideConfig(n, persistableBundle);
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
            public void updateConfigForPhoneId(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateConfigForPhoneId(n, string2);
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

