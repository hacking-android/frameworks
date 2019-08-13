/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.IMediaRouterClient;
import android.media.MediaRouterClientState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMediaRouterService
extends IInterface {
    public MediaRouterClientState getState(IMediaRouterClient var1) throws RemoteException;

    public boolean isPlaybackActive(IMediaRouterClient var1) throws RemoteException;

    public void registerClientAsUser(IMediaRouterClient var1, String var2, int var3) throws RemoteException;

    public void registerClientGroupId(IMediaRouterClient var1, String var2) throws RemoteException;

    public void requestSetVolume(IMediaRouterClient var1, String var2, int var3) throws RemoteException;

    public void requestUpdateVolume(IMediaRouterClient var1, String var2, int var3) throws RemoteException;

    public void setDiscoveryRequest(IMediaRouterClient var1, int var2, boolean var3) throws RemoteException;

    public void setSelectedRoute(IMediaRouterClient var1, String var2, boolean var3) throws RemoteException;

    public void unregisterClient(IMediaRouterClient var1) throws RemoteException;

    public static class Default
    implements IMediaRouterService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) throws RemoteException {
            return null;
        }

        @Override
        public boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) throws RemoteException {
            return false;
        }

        @Override
        public void registerClientAsUser(IMediaRouterClient iMediaRouterClient, String string2, int n) throws RemoteException {
        }

        @Override
        public void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String string2) throws RemoteException {
        }

        @Override
        public void requestSetVolume(IMediaRouterClient iMediaRouterClient, String string2, int n) throws RemoteException {
        }

        @Override
        public void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String string2, int n) throws RemoteException {
        }

        @Override
        public void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void unregisterClient(IMediaRouterClient iMediaRouterClient) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaRouterService {
        private static final String DESCRIPTOR = "android.media.IMediaRouterService";
        static final int TRANSACTION_getState = 4;
        static final int TRANSACTION_isPlaybackActive = 5;
        static final int TRANSACTION_registerClientAsUser = 1;
        static final int TRANSACTION_registerClientGroupId = 3;
        static final int TRANSACTION_requestSetVolume = 8;
        static final int TRANSACTION_requestUpdateVolume = 9;
        static final int TRANSACTION_setDiscoveryRequest = 6;
        static final int TRANSACTION_setSelectedRoute = 7;
        static final int TRANSACTION_unregisterClient = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaRouterService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaRouterService) {
                return (IMediaRouterService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaRouterService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "requestUpdateVolume";
                }
                case 8: {
                    return "requestSetVolume";
                }
                case 7: {
                    return "setSelectedRoute";
                }
                case 6: {
                    return "setDiscoveryRequest";
                }
                case 5: {
                    return "isPlaybackActive";
                }
                case 4: {
                    return "getState";
                }
                case 3: {
                    return "registerClientGroupId";
                }
                case 2: {
                    return "unregisterClient";
                }
                case 1: 
            }
            return "registerClientAsUser";
        }

        public static boolean setDefaultImpl(IMediaRouterService iMediaRouterService) {
            if (Proxy.sDefaultImpl == null && iMediaRouterService != null) {
                Proxy.sDefaultImpl = iMediaRouterService;
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
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestUpdateVolume(IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestSetVolume(IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IMediaRouterClient iMediaRouterClient = IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setSelectedRoute(iMediaRouterClient, string2, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IMediaRouterClient iMediaRouterClient = IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setDiscoveryRequest(iMediaRouterClient, n, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPlaybackActive(IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getState(IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MediaRouterClientState)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerClientGroupId(IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterClient(IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.registerClientAsUser(IMediaRouterClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMediaRouterService {
            public static IMediaRouterService sDefaultImpl;
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
            public MediaRouterClientState getState(IMediaRouterClient object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block7 : {
                    IBinder iBinder;
                    block6 : {
                        block5 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (object == null) break block5;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = object.asBinder();
                            break block6;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block7;
                    object = Stub.getDefaultImpl().getState((IMediaRouterClient)object);
                    parcel2.recycle();
                    parcel.recycle();
                    return object;
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? MediaRouterClientState.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iMediaRouterClient == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iMediaRouterClient.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().isPlaybackActive(iMediaRouterClient);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerClientAsUser(IMediaRouterClient iMediaRouterClient, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaRouterClient != null ? iMediaRouterClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerClientAsUser(iMediaRouterClient, string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaRouterClient != null ? iMediaRouterClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerClientGroupId(iMediaRouterClient, string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestSetVolume(IMediaRouterClient iMediaRouterClient, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaRouterClient != null ? iMediaRouterClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestSetVolume(iMediaRouterClient, string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaRouterClient != null ? iMediaRouterClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestUpdateVolume(iMediaRouterClient, string2, n);
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
            public void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int n, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iMediaRouterClient == null) break block8;
                    iBinder = iMediaRouterClient.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDiscoveryRequest(iMediaRouterClient, n, bl);
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
            public void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iMediaRouterClient == null) break block8;
                    iBinder = iMediaRouterClient.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSelectedRoute(iMediaRouterClient, string2, bl);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterClient(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaRouterClient != null ? iMediaRouterClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterClient(iMediaRouterClient);
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

