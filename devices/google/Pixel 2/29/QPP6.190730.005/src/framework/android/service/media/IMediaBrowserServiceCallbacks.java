/*
 * Decompiled with CFR 0.145.
 */
package android.service.media;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ParceledListSlice;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMediaBrowserServiceCallbacks
extends IInterface {
    @UnsupportedAppUsage
    public void onConnect(String var1, MediaSession.Token var2, Bundle var3) throws RemoteException;

    @UnsupportedAppUsage
    public void onConnectFailed() throws RemoteException;

    public void onLoadChildren(String var1, ParceledListSlice var2) throws RemoteException;

    public void onLoadChildrenWithOptions(String var1, ParceledListSlice var2, Bundle var3) throws RemoteException;

    public static class Default
    implements IMediaBrowserServiceCallbacks {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConnect(String string2, MediaSession.Token token, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onConnectFailed() throws RemoteException {
        }

        @Override
        public void onLoadChildren(String string2, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void onLoadChildrenWithOptions(String string2, ParceledListSlice parceledListSlice, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaBrowserServiceCallbacks {
        private static final String DESCRIPTOR = "android.service.media.IMediaBrowserServiceCallbacks";
        static final int TRANSACTION_onConnect = 1;
        static final int TRANSACTION_onConnectFailed = 2;
        static final int TRANSACTION_onLoadChildren = 3;
        static final int TRANSACTION_onLoadChildrenWithOptions = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaBrowserServiceCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaBrowserServiceCallbacks) {
                return (IMediaBrowserServiceCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaBrowserServiceCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onLoadChildrenWithOptions";
                    }
                    return "onLoadChildren";
                }
                return "onConnectFailed";
            }
            return "onConnect";
        }

        public static boolean setDefaultImpl(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            if (Proxy.sDefaultImpl == null && iMediaBrowserServiceCallbacks != null) {
                Proxy.sDefaultImpl = iMediaBrowserServiceCallbacks;
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
        public boolean onTransact(int n, Parcel parcelable, Parcel parcelable2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)((Object)parcelable), (Parcel)((Object)parcelable2), n2);
                            }
                            ((Parcel)((Object)parcelable2)).writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)((Object)parcelable)).readString();
                        parcelable2 = ((Parcel)((Object)parcelable)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onLoadChildrenWithOptions(string2, (ParceledListSlice)parcelable2, (Bundle)parcelable);
                        return true;
                    }
                    ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                    parcelable2 = ((Parcel)((Object)parcelable)).readString();
                    parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                    this.onLoadChildren((String)((Object)parcelable2), (ParceledListSlice)parcelable);
                    return true;
                }
                ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                this.onConnectFailed();
                return true;
            }
            ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
            String string3 = ((Parcel)((Object)parcelable)).readString();
            parcelable2 = ((Parcel)((Object)parcelable)).readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
            parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
            this.onConnect(string3, (MediaSession.Token)parcelable2, (Bundle)parcelable);
            return true;
        }

        private static class Proxy
        implements IMediaBrowserServiceCallbacks {
            public static IMediaBrowserServiceCallbacks sDefaultImpl;
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
            public void onConnect(String string2, MediaSession.Token token, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (token != null) {
                        parcel.writeInt(1);
                        token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnect(string2, token, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConnectFailed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectFailed();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLoadChildren(String string2, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLoadChildren(string2, parceledListSlice);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLoadChildrenWithOptions(String string2, ParceledListSlice parceledListSlice, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLoadChildrenWithOptions(string2, parceledListSlice, bundle);
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

