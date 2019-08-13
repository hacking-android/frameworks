/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IWapPushManager
extends IInterface {
    @UnsupportedAppUsage
    public boolean addPackage(String var1, String var2, String var3, String var4, int var5, boolean var6, boolean var7) throws RemoteException;

    @UnsupportedAppUsage
    public boolean deletePackage(String var1, String var2, String var3, String var4) throws RemoteException;

    public int processMessage(String var1, String var2, Intent var3) throws RemoteException;

    @UnsupportedAppUsage
    public boolean updatePackage(String var1, String var2, String var3, String var4, int var5, boolean var6, boolean var7) throws RemoteException;

    public static class Default
    implements IWapPushManager {
        @Override
        public boolean addPackage(String string2, String string3, String string4, String string5, int n, boolean bl, boolean bl2) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean deletePackage(String string2, String string3, String string4, String string5) throws RemoteException {
            return false;
        }

        @Override
        public int processMessage(String string2, String string3, Intent intent) throws RemoteException {
            return 0;
        }

        @Override
        public boolean updatePackage(String string2, String string3, String string4, String string5, int n, boolean bl, boolean bl2) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWapPushManager {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IWapPushManager";
        static final int TRANSACTION_addPackage = 2;
        static final int TRANSACTION_deletePackage = 4;
        static final int TRANSACTION_processMessage = 1;
        static final int TRANSACTION_updatePackage = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWapPushManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWapPushManager) {
                return (IWapPushManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWapPushManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "deletePackage";
                    }
                    return "updatePackage";
                }
                return "addPackage";
            }
            return "processMessage";
        }

        public static boolean setDefaultImpl(IWapPushManager iWapPushManager) {
            if (Proxy.sDefaultImpl == null && iWapPushManager != null) {
                Proxy.sDefaultImpl = iWapPushManager;
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
                        n = this.deletePackage(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    String string2 = ((Parcel)object).readString();
                    String string3 = ((Parcel)object).readString();
                    String string4 = ((Parcel)object).readString();
                    String string5 = ((Parcel)object).readString();
                    n = ((Parcel)object).readInt();
                    boolean bl = ((Parcel)object).readInt() != 0;
                    boolean bl2 = ((Parcel)object).readInt() != 0;
                    n = this.updatePackage(string2, string3, string4, string5, n, bl, bl2) ? 1 : 0;
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string6 = ((Parcel)object).readString();
                String string7 = ((Parcel)object).readString();
                String string8 = ((Parcel)object).readString();
                String string9 = ((Parcel)object).readString();
                n = ((Parcel)object).readInt();
                boolean bl = ((Parcel)object).readInt() != 0;
                boolean bl3 = ((Parcel)object).readInt() != 0;
                n = this.addPackage(string6, string7, string8, string9, n, bl, bl3) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            String string10 = ((Parcel)object).readString();
            String string11 = ((Parcel)object).readString();
            object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
            n = this.processMessage(string10, string11, (Intent)object);
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IWapPushManager {
            public static IWapPushManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
            public boolean addPackage(String string2, String string3, String string4, String string5, int n, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string5);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                        boolean bl3 = true;
                        int n2 = bl ? 1 : 0;
                        parcel.writeInt(n2);
                        n2 = bl2 ? 1 : 0;
                        parcel.writeInt(n2);
                        if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().addPackage(string2, string3, string4, string5, n, bl, bl2);
                            parcel2.recycle();
                            parcel.recycle();
                            return bl;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        bl = n != 0 ? bl3 : false;
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_8;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean deletePackage(String string2, String string3, String string4, String string5) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeString(string4);
                        parcel2.writeString(string5);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().deletePackage(string2, string3, string4, string5);
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
            public int processMessage(String string2, String string3, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().processMessage(string2, string3, intent);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean updatePackage(String string2, String string3, String string4, String string5, int n, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string5);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                        boolean bl3 = true;
                        int n2 = bl ? 1 : 0;
                        parcel.writeInt(n2);
                        n2 = bl2 ? 1 : 0;
                        parcel.writeInt(n2);
                        if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().updatePackage(string2, string3, string4, string5, n, bl, bl2);
                            parcel2.recycle();
                            parcel.recycle();
                            return bl;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        bl = n != 0 ? bl3 : false;
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_8;
            }
        }

    }

}

