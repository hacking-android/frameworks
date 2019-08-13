/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.IApplicationThread;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IAppTask
extends IInterface {
    public void finishAndRemoveTask() throws RemoteException;

    @UnsupportedAppUsage
    public ActivityManager.RecentTaskInfo getTaskInfo() throws RemoteException;

    public void moveToFront(IApplicationThread var1, String var2) throws RemoteException;

    public void setExcludeFromRecents(boolean var1) throws RemoteException;

    public int startActivity(IBinder var1, String var2, Intent var3, String var4, Bundle var5) throws RemoteException;

    public static class Default
    implements IAppTask {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void finishAndRemoveTask() throws RemoteException {
        }

        @Override
        public ActivityManager.RecentTaskInfo getTaskInfo() throws RemoteException {
            return null;
        }

        @Override
        public void moveToFront(IApplicationThread iApplicationThread, String string2) throws RemoteException {
        }

        @Override
        public void setExcludeFromRecents(boolean bl) throws RemoteException {
        }

        @Override
        public int startActivity(IBinder iBinder, String string2, Intent intent, String string3, Bundle bundle) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAppTask {
        private static final String DESCRIPTOR = "android.app.IAppTask";
        static final int TRANSACTION_finishAndRemoveTask = 1;
        static final int TRANSACTION_getTaskInfo = 2;
        static final int TRANSACTION_moveToFront = 3;
        static final int TRANSACTION_setExcludeFromRecents = 5;
        static final int TRANSACTION_startActivity = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAppTask asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAppTask) {
                return (IAppTask)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAppTask getDefaultImpl() {
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
                            return "setExcludeFromRecents";
                        }
                        return "startActivity";
                    }
                    return "moveToFront";
                }
                return "getTaskInfo";
            }
            return "finishAndRemoveTask";
        }

        public static boolean setDefaultImpl(IAppTask iAppTask) {
            if (Proxy.sDefaultImpl == null && iAppTask != null) {
                Proxy.sDefaultImpl = iAppTask;
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
                boolean bl = false;
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
                            if (((Parcel)object).readInt() != 0) {
                                bl = true;
                            }
                            this.setExcludeFromRecents(bl);
                            parcel.writeNoException();
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string2 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivity(iBinder, string2, intent, string3, (Bundle)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.moveToFront(IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getTaskInfo();
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((ActivityManager.RecentTaskInfo)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.finishAndRemoveTask();
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IAppTask {
            public static IAppTask sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void finishAndRemoveTask() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishAndRemoveTask();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public ActivityManager.RecentTaskInfo getTaskInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ActivityManager.RecentTaskInfo recentTaskInfo = Stub.getDefaultImpl().getTaskInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return recentTaskInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ActivityManager.RecentTaskInfo recentTaskInfo = parcel.readInt() != 0 ? ActivityManager.RecentTaskInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return recentTaskInfo;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void moveToFront(IApplicationThread iApplicationThread, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveToFront(iApplicationThread, string2);
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
            public void setExcludeFromRecents(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setExcludeFromRecents(bl);
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
            public int startActivity(IBinder iBinder, String string2, Intent intent, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().startActivity(iBinder, string2, intent, string3, bundle);
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
        }

    }

}

