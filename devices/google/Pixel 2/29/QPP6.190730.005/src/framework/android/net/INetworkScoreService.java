/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.INetworkScoreCache;
import android.net.NetworkKey;
import android.net.NetworkScorerAppData;
import android.net.ScoredNetwork;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface INetworkScoreService
extends IInterface {
    public boolean clearScores() throws RemoteException;

    public void disableScoring() throws RemoteException;

    public NetworkScorerAppData getActiveScorer() throws RemoteException;

    public String getActiveScorerPackage() throws RemoteException;

    public List<NetworkScorerAppData> getAllValidScorers() throws RemoteException;

    public boolean isCallerActiveScorer(int var1) throws RemoteException;

    public void registerNetworkScoreCache(int var1, INetworkScoreCache var2, int var3) throws RemoteException;

    public boolean requestScores(NetworkKey[] var1) throws RemoteException;

    public boolean setActiveScorer(String var1) throws RemoteException;

    public void unregisterNetworkScoreCache(int var1, INetworkScoreCache var2) throws RemoteException;

    public boolean updateScores(ScoredNetwork[] var1) throws RemoteException;

    public static class Default
    implements INetworkScoreService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean clearScores() throws RemoteException {
            return false;
        }

        @Override
        public void disableScoring() throws RemoteException {
        }

        @Override
        public NetworkScorerAppData getActiveScorer() throws RemoteException {
            return null;
        }

        @Override
        public String getActiveScorerPackage() throws RemoteException {
            return null;
        }

        @Override
        public List<NetworkScorerAppData> getAllValidScorers() throws RemoteException {
            return null;
        }

        @Override
        public boolean isCallerActiveScorer(int n) throws RemoteException {
            return false;
        }

        @Override
        public void registerNetworkScoreCache(int n, INetworkScoreCache iNetworkScoreCache, int n2) throws RemoteException {
        }

        @Override
        public boolean requestScores(NetworkKey[] arrnetworkKey) throws RemoteException {
            return false;
        }

        @Override
        public boolean setActiveScorer(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void unregisterNetworkScoreCache(int n, INetworkScoreCache iNetworkScoreCache) throws RemoteException {
        }

        @Override
        public boolean updateScores(ScoredNetwork[] arrscoredNetwork) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkScoreService {
        private static final String DESCRIPTOR = "android.net.INetworkScoreService";
        static final int TRANSACTION_clearScores = 2;
        static final int TRANSACTION_disableScoring = 4;
        static final int TRANSACTION_getActiveScorer = 10;
        static final int TRANSACTION_getActiveScorerPackage = 9;
        static final int TRANSACTION_getAllValidScorers = 11;
        static final int TRANSACTION_isCallerActiveScorer = 8;
        static final int TRANSACTION_registerNetworkScoreCache = 5;
        static final int TRANSACTION_requestScores = 7;
        static final int TRANSACTION_setActiveScorer = 3;
        static final int TRANSACTION_unregisterNetworkScoreCache = 6;
        static final int TRANSACTION_updateScores = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkScoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkScoreService) {
                return (INetworkScoreService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkScoreService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "getAllValidScorers";
                }
                case 10: {
                    return "getActiveScorer";
                }
                case 9: {
                    return "getActiveScorerPackage";
                }
                case 8: {
                    return "isCallerActiveScorer";
                }
                case 7: {
                    return "requestScores";
                }
                case 6: {
                    return "unregisterNetworkScoreCache";
                }
                case 5: {
                    return "registerNetworkScoreCache";
                }
                case 4: {
                    return "disableScoring";
                }
                case 3: {
                    return "setActiveScorer";
                }
                case 2: {
                    return "clearScores";
                }
                case 1: 
            }
            return "updateScores";
        }

        public static boolean setDefaultImpl(INetworkScoreService iNetworkScoreService) {
            if (Proxy.sDefaultImpl == null && iNetworkScoreService != null) {
                Proxy.sDefaultImpl = iNetworkScoreService;
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
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllValidScorers();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveScorer();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkScorerAppData)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveScorerPackage();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCallerActiveScorer(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.requestScores(((Parcel)object).createTypedArray(NetworkKey.CREATOR)) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterNetworkScoreCache(((Parcel)object).readInt(), INetworkScoreCache.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerNetworkScoreCache(((Parcel)object).readInt(), INetworkScoreCache.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableScoring();
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setActiveScorer(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.clearScores() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.updateScores(((Parcel)object).createTypedArray(ScoredNetwork.CREATOR)) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INetworkScoreService {
            public static INetworkScoreService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean clearScores() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().clearScores();
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

            @Override
            public void disableScoring() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableScoring();
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
            public NetworkScorerAppData getActiveScorer() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkScorerAppData networkScorerAppData = Stub.getDefaultImpl().getActiveScorer();
                        parcel.recycle();
                        parcel2.recycle();
                        return networkScorerAppData;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NetworkScorerAppData networkScorerAppData = parcel.readInt() != 0 ? NetworkScorerAppData.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkScorerAppData;
            }

            @Override
            public String getActiveScorerPackage() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getActiveScorerPackage();
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

            @Override
            public List<NetworkScorerAppData> getAllValidScorers() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<NetworkScorerAppData> list = Stub.getDefaultImpl().getAllValidScorers();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<NetworkScorerAppData> arrayList = parcel2.createTypedArrayList(NetworkScorerAppData.CREATOR);
                    return arrayList;
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
            public boolean isCallerActiveScorer(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCallerActiveScorer(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
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
            public void registerNetworkScoreCache(int n, INetworkScoreCache iNetworkScoreCache, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iNetworkScoreCache != null ? iNetworkScoreCache.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerNetworkScoreCache(n, iNetworkScoreCache, n2);
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
            public boolean requestScores(NetworkKey[] arrnetworkKey) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeTypedArray((Parcelable[])arrnetworkKey, 0);
                    if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().requestScores(arrnetworkKey);
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

            @Override
            public boolean setActiveScorer(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setActiveScorer(string2);
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
            public void unregisterNetworkScoreCache(int n, INetworkScoreCache iNetworkScoreCache) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iNetworkScoreCache != null ? iNetworkScoreCache.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterNetworkScoreCache(n, iNetworkScoreCache);
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
            public boolean updateScores(ScoredNetwork[] arrscoredNetwork) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeTypedArray((Parcelable[])arrscoredNetwork, 0);
                    if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().updateScores(arrscoredNetwork);
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
        }

    }

}

