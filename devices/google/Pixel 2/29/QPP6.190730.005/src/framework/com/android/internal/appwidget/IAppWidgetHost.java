/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.appwidget;

import android.appwidget.AppWidgetProviderInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.widget.RemoteViews;

public interface IAppWidgetHost
extends IInterface {
    public void providerChanged(int var1, AppWidgetProviderInfo var2) throws RemoteException;

    public void providersChanged() throws RemoteException;

    public void updateAppWidget(int var1, RemoteViews var2) throws RemoteException;

    public void viewDataChanged(int var1, int var2) throws RemoteException;

    public static class Default
    implements IAppWidgetHost {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void providerChanged(int n, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException {
        }

        @Override
        public void providersChanged() throws RemoteException {
        }

        @Override
        public void updateAppWidget(int n, RemoteViews remoteViews) throws RemoteException {
        }

        @Override
        public void viewDataChanged(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAppWidgetHost {
        private static final String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetHost";
        static final int TRANSACTION_providerChanged = 2;
        static final int TRANSACTION_providersChanged = 3;
        static final int TRANSACTION_updateAppWidget = 1;
        static final int TRANSACTION_viewDataChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAppWidgetHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAppWidgetHost) {
                return (IAppWidgetHost)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAppWidgetHost getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "viewDataChanged";
                    }
                    return "providersChanged";
                }
                return "providerChanged";
            }
            return "updateAppWidget";
        }

        public static boolean setDefaultImpl(IAppWidgetHost iAppWidgetHost) {
            if (Proxy.sDefaultImpl == null && iAppWidgetHost != null) {
                Proxy.sDefaultImpl = iAppWidgetHost;
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
                        this.viewDataChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.providersChanged();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? AppWidgetProviderInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.providerChanged(n, (AppWidgetProviderInfo)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? RemoteViews.CREATOR.createFromParcel((Parcel)object) : null;
            this.updateAppWidget(n, (RemoteViews)object);
            return true;
        }

        private static class Proxy
        implements IAppWidgetHost {
            public static IAppWidgetHost sDefaultImpl;
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
            public void providerChanged(int n, AppWidgetProviderInfo appWidgetProviderInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (appWidgetProviderInfo != null) {
                        parcel.writeInt(1);
                        appWidgetProviderInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().providerChanged(n, appWidgetProviderInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void providersChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().providersChanged();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateAppWidget(int n, RemoteViews remoteViews) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (remoteViews != null) {
                        parcel.writeInt(1);
                        remoteViews.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateAppWidget(n, remoteViews);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void viewDataChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().viewDataChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

