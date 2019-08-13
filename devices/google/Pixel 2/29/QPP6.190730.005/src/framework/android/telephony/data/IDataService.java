/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.net.LinkProperties;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.data.DataProfile;
import android.telephony.data.IDataServiceCallback;
import java.util.ArrayList;
import java.util.List;

public interface IDataService
extends IInterface {
    public void createDataServiceProvider(int var1) throws RemoteException;

    public void deactivateDataCall(int var1, int var2, int var3, IDataServiceCallback var4) throws RemoteException;

    public void registerForDataCallListChanged(int var1, IDataServiceCallback var2) throws RemoteException;

    public void removeDataServiceProvider(int var1) throws RemoteException;

    public void requestDataCallList(int var1, IDataServiceCallback var2) throws RemoteException;

    public void setDataProfile(int var1, List<DataProfile> var2, boolean var3, IDataServiceCallback var4) throws RemoteException;

    public void setInitialAttachApn(int var1, DataProfile var2, boolean var3, IDataServiceCallback var4) throws RemoteException;

    public void setupDataCall(int var1, int var2, DataProfile var3, boolean var4, boolean var5, int var6, LinkProperties var7, IDataServiceCallback var8) throws RemoteException;

    public void unregisterForDataCallListChanged(int var1, IDataServiceCallback var2) throws RemoteException;

    public static class Default
    implements IDataService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void createDataServiceProvider(int n) throws RemoteException {
        }

        @Override
        public void deactivateDataCall(int n, int n2, int n3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override
        public void registerForDataCallListChanged(int n, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override
        public void removeDataServiceProvider(int n) throws RemoteException {
        }

        @Override
        public void requestDataCallList(int n, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override
        public void setDataProfile(int n, List<DataProfile> list, boolean bl, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override
        public void setInitialAttachApn(int n, DataProfile dataProfile, boolean bl, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override
        public void setupDataCall(int n, int n2, DataProfile dataProfile, boolean bl, boolean bl2, int n3, LinkProperties linkProperties, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override
        public void unregisterForDataCallListChanged(int n, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDataService {
        private static final String DESCRIPTOR = "android.telephony.data.IDataService";
        static final int TRANSACTION_createDataServiceProvider = 1;
        static final int TRANSACTION_deactivateDataCall = 4;
        static final int TRANSACTION_registerForDataCallListChanged = 8;
        static final int TRANSACTION_removeDataServiceProvider = 2;
        static final int TRANSACTION_requestDataCallList = 7;
        static final int TRANSACTION_setDataProfile = 6;
        static final int TRANSACTION_setInitialAttachApn = 5;
        static final int TRANSACTION_setupDataCall = 3;
        static final int TRANSACTION_unregisterForDataCallListChanged = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDataService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDataService) {
                return (IDataService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDataService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "unregisterForDataCallListChanged";
                }
                case 8: {
                    return "registerForDataCallListChanged";
                }
                case 7: {
                    return "requestDataCallList";
                }
                case 6: {
                    return "setDataProfile";
                }
                case 5: {
                    return "setInitialAttachApn";
                }
                case 4: {
                    return "deactivateDataCall";
                }
                case 3: {
                    return "setupDataCall";
                }
                case 2: {
                    return "removeDataServiceProvider";
                }
                case 1: 
            }
            return "createDataServiceProvider";
        }

        public static boolean setDefaultImpl(IDataService iDataService) {
            if (Proxy.sDefaultImpl == null && iDataService != null) {
                Proxy.sDefaultImpl = iDataService;
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
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.unregisterForDataCallListChanged(parcel.readInt(), IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.registerForDataCallListChanged(parcel.readInt(), IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.requestDataCallList(parcel.readInt(), IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        object = parcel.createTypedArrayList(DataProfile.CREATOR);
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.setDataProfile(n, (List<DataProfile>)object, bl2, IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        object = parcel.readInt() != 0 ? DataProfile.CREATOR.createFromParcel(parcel) : null;
                        bl2 = bl;
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.setInitialAttachApn(n, (DataProfile)object, bl2, IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.deactivateDataCall(parcel.readInt(), parcel.readInt(), parcel.readInt(), IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        n2 = parcel.readInt();
                        object = parcel.readInt() != 0 ? DataProfile.CREATOR.createFromParcel(parcel) : null;
                        bl2 = parcel.readInt() != 0;
                        bl = parcel.readInt() != 0;
                        int n3 = parcel.readInt();
                        LinkProperties linkProperties = parcel.readInt() != 0 ? LinkProperties.CREATOR.createFromParcel(parcel) : null;
                        this.setupDataCall(n, n2, (DataProfile)object, bl2, bl, n3, linkProperties, IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.removeDataServiceProvider(parcel.readInt());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.createDataServiceProvider(parcel.readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IDataService {
            public static IDataService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void createDataServiceProvider(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createDataServiceProvider(n);
                        return;
                    }
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
            public void deactivateDataCall(int n, int n2, int n3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    IBinder iBinder = iDataServiceCallback != null ? iDataServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().deactivateDataCall(n, n2, n3, iDataServiceCallback);
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
            public void registerForDataCallListChanged(int n, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iDataServiceCallback != null ? iDataServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().registerForDataCallListChanged(n, iDataServiceCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeDataServiceProvider(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeDataServiceProvider(n);
                        return;
                    }
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
            public void requestDataCallList(int n, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iDataServiceCallback != null ? iDataServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().requestDataCallList(n, iDataServiceCallback);
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
            public void setDataProfile(int n, List<DataProfile> list, boolean bl, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    IBinder iBinder = iDataServiceCallback != null ? iDataServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setDataProfile(n, list, bl, iDataServiceCallback);
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
            public void setInitialAttachApn(int n, DataProfile dataProfile, boolean bl, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = 0;
                    if (dataProfile != null) {
                        parcel.writeInt(1);
                        dataProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    IBinder iBinder = iDataServiceCallback != null ? iDataServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setInitialAttachApn(n, dataProfile, bl, iDataServiceCallback);
                    return;
                }
                finally {
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
            public void setupDataCall(int n, int n2, DataProfile dataProfile, boolean bl, boolean bl2, int n3, LinkProperties linkProperties, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                void var3_8;
                Parcel parcel;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n2);
                        if (dataProfile != null) {
                            parcel.writeInt(1);
                            dataProfile.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        int n4 = bl ? 1 : 0;
                        parcel.writeInt(n4);
                        n4 = bl2 ? 1 : 0;
                        parcel.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n3);
                        if (linkProperties != null) {
                            parcel.writeInt(1);
                            linkProperties.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        IBinder iBinder = iDataServiceCallback != null ? iDataServiceCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setupDataCall(n, n2, dataProfile, bl, bl2, n3, linkProperties, iDataServiceCallback);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var3_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterForDataCallListChanged(int n, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iDataServiceCallback != null ? iDataServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterForDataCallListChanged(n, iDataServiceCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

