/*
 * Decompiled with CFR 0.145.
 */
package android.service.media;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.media.IMediaBrowserServiceCallbacks;

public interface IMediaBrowserService
extends IInterface {
    public void addSubscription(String var1, IBinder var2, Bundle var3, IMediaBrowserServiceCallbacks var4) throws RemoteException;

    public void addSubscriptionDeprecated(String var1, IMediaBrowserServiceCallbacks var2) throws RemoteException;

    public void connect(String var1, Bundle var2, IMediaBrowserServiceCallbacks var3) throws RemoteException;

    public void disconnect(IMediaBrowserServiceCallbacks var1) throws RemoteException;

    public void getMediaItem(String var1, ResultReceiver var2, IMediaBrowserServiceCallbacks var3) throws RemoteException;

    public void removeSubscription(String var1, IBinder var2, IMediaBrowserServiceCallbacks var3) throws RemoteException;

    public void removeSubscriptionDeprecated(String var1, IMediaBrowserServiceCallbacks var2) throws RemoteException;

    public static class Default
    implements IMediaBrowserService {
        @Override
        public void addSubscription(String string2, IBinder iBinder, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override
        public void addSubscriptionDeprecated(String string2, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void connect(String string2, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override
        public void disconnect(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override
        public void getMediaItem(String string2, ResultReceiver resultReceiver, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override
        public void removeSubscription(String string2, IBinder iBinder, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override
        public void removeSubscriptionDeprecated(String string2, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaBrowserService {
        private static final String DESCRIPTOR = "android.service.media.IMediaBrowserService";
        static final int TRANSACTION_addSubscription = 6;
        static final int TRANSACTION_addSubscriptionDeprecated = 3;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getMediaItem = 5;
        static final int TRANSACTION_removeSubscription = 7;
        static final int TRANSACTION_removeSubscriptionDeprecated = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaBrowserService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaBrowserService) {
                return (IMediaBrowserService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaBrowserService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "removeSubscription";
                }
                case 6: {
                    return "addSubscription";
                }
                case 5: {
                    return "getMediaItem";
                }
                case 4: {
                    return "removeSubscriptionDeprecated";
                }
                case 3: {
                    return "addSubscriptionDeprecated";
                }
                case 2: {
                    return "disconnect";
                }
                case 1: 
            }
            return "connect";
        }

        public static boolean setDefaultImpl(IMediaBrowserService iMediaBrowserService) {
            if (Proxy.sDefaultImpl == null && iMediaBrowserService != null) {
                Proxy.sDefaultImpl = iMediaBrowserService;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.removeSubscription(parcel.readString(), parcel.readStrongBinder(), IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        String string2 = parcel.readString();
                        IBinder iBinder = parcel.readStrongBinder();
                        object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                        this.addSubscription(string2, iBinder, (Bundle)object, IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        String string3 = parcel.readString();
                        object = parcel.readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel(parcel) : null;
                        this.getMediaItem(string3, (ResultReceiver)object, IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.removeSubscriptionDeprecated(parcel.readString(), IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.addSubscriptionDeprecated(parcel.readString(), IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.disconnect(IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                String string4 = parcel.readString();
                object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                this.connect(string4, (Bundle)object, IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMediaBrowserService {
            public static IMediaBrowserService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addSubscription(String string2, IBinder iBinder, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder2 = iMediaBrowserServiceCallbacks != null ? iMediaBrowserServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().addSubscription(string2, iBinder, bundle, iMediaBrowserServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addSubscriptionDeprecated(String string2, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iMediaBrowserServiceCallbacks != null ? iMediaBrowserServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().addSubscriptionDeprecated(string2, iMediaBrowserServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void connect(String string2, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iMediaBrowserServiceCallbacks != null ? iMediaBrowserServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().connect(string2, bundle, iMediaBrowserServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void disconnect(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaBrowserServiceCallbacks != null ? iMediaBrowserServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().disconnect(iMediaBrowserServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getMediaItem(String string2, ResultReceiver resultReceiver, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iMediaBrowserServiceCallbacks != null ? iMediaBrowserServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getMediaItem(string2, resultReceiver, iMediaBrowserServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeSubscription(String string2, IBinder iBinder, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iMediaBrowserServiceCallbacks != null ? iMediaBrowserServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().removeSubscription(string2, iBinder, iMediaBrowserServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeSubscriptionDeprecated(String string2, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iMediaBrowserServiceCallbacks != null ? iMediaBrowserServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().removeSubscriptionDeprecated(string2, iMediaBrowserServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

