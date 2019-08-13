/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms.vendor;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.mbms.IMbmsStreamingSessionCallback;
import android.telephony.mbms.IStreamingServiceCallback;
import java.util.ArrayList;
import java.util.List;

public interface IMbmsStreamingService
extends IInterface {
    public void dispose(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public Uri getPlaybackUri(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public int initialize(IMbmsStreamingSessionCallback var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public int requestUpdateStreamingServices(int var1, List<String> var2) throws RemoteException;

    @UnsupportedAppUsage
    public int startStreaming(int var1, String var2, IStreamingServiceCallback var3) throws RemoteException;

    public void stopStreaming(int var1, String var2) throws RemoteException;

    public static class Default
    implements IMbmsStreamingService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispose(int n) throws RemoteException {
        }

        @Override
        public Uri getPlaybackUri(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int initialize(IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int requestUpdateStreamingServices(int n, List<String> list) throws RemoteException {
            return 0;
        }

        @Override
        public int startStreaming(int n, String string2, IStreamingServiceCallback iStreamingServiceCallback) throws RemoteException {
            return 0;
        }

        @Override
        public void stopStreaming(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMbmsStreamingService {
        private static final String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsStreamingService";
        static final int TRANSACTION_dispose = 6;
        static final int TRANSACTION_getPlaybackUri = 4;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_requestUpdateStreamingServices = 2;
        static final int TRANSACTION_startStreaming = 3;
        static final int TRANSACTION_stopStreaming = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMbmsStreamingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMbmsStreamingService) {
                return (IMbmsStreamingService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMbmsStreamingService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "dispose";
                }
                case 5: {
                    return "stopStreaming";
                }
                case 4: {
                    return "getPlaybackUri";
                }
                case 3: {
                    return "startStreaming";
                }
                case 2: {
                    return "requestUpdateStreamingServices";
                }
                case 1: 
            }
            return "initialize";
        }

        public static boolean setDefaultImpl(IMbmsStreamingService iMbmsStreamingService) {
            if (Proxy.sDefaultImpl == null && iMbmsStreamingService != null) {
                Proxy.sDefaultImpl = iMbmsStreamingService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dispose(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopStreaming(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPlaybackUri(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startStreaming(((Parcel)object).readInt(), ((Parcel)object).readString(), IStreamingServiceCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.requestUpdateStreamingServices(((Parcel)object).readInt(), ((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.initialize(IMbmsStreamingSessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMbmsStreamingService {
            public static IMbmsStreamingService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispose(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispose(n);
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
            public Uri getPlaybackUri(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getPlaybackUri(n, (String)object);
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
                object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int initialize(IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMbmsStreamingSessionCallback != null ? iMbmsStreamingSessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().initialize(iMbmsStreamingSessionCallback, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int requestUpdateStreamingServices(int n, List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().requestUpdateStreamingServices(n, list);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
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
            public int startStreaming(int n, String string2, IStreamingServiceCallback iStreamingServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    IBinder iBinder = iStreamingServiceCallback != null ? iStreamingServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().startStreaming(n, string2, iStreamingServiceCallback);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void stopStreaming(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopStreaming(n, string2);
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

