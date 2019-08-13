/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ISearchManager
extends IInterface {
    public List<ResolveInfo> getGlobalSearchActivities() throws RemoteException;

    @UnsupportedAppUsage
    public ComponentName getGlobalSearchActivity() throws RemoteException;

    public SearchableInfo getSearchableInfo(ComponentName var1) throws RemoteException;

    public List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException;

    public ComponentName getWebSearchActivity() throws RemoteException;

    public void launchAssist(Bundle var1) throws RemoteException;

    public boolean launchLegacyAssist(String var1, int var2, Bundle var3) throws RemoteException;

    public static class Default
    implements ISearchManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public List<ResolveInfo> getGlobalSearchActivities() throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getGlobalSearchActivity() throws RemoteException {
            return null;
        }

        @Override
        public SearchableInfo getSearchableInfo(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getWebSearchActivity() throws RemoteException {
            return null;
        }

        @Override
        public void launchAssist(Bundle bundle) throws RemoteException {
        }

        @Override
        public boolean launchLegacyAssist(String string2, int n, Bundle bundle) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISearchManager {
        private static final String DESCRIPTOR = "android.app.ISearchManager";
        static final int TRANSACTION_getGlobalSearchActivities = 3;
        static final int TRANSACTION_getGlobalSearchActivity = 4;
        static final int TRANSACTION_getSearchableInfo = 1;
        static final int TRANSACTION_getSearchablesInGlobalSearch = 2;
        static final int TRANSACTION_getWebSearchActivity = 5;
        static final int TRANSACTION_launchAssist = 6;
        static final int TRANSACTION_launchLegacyAssist = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISearchManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISearchManager) {
                return (ISearchManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISearchManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "launchLegacyAssist";
                }
                case 6: {
                    return "launchAssist";
                }
                case 5: {
                    return "getWebSearchActivity";
                }
                case 4: {
                    return "getGlobalSearchActivity";
                }
                case 3: {
                    return "getGlobalSearchActivities";
                }
                case 2: {
                    return "getSearchablesInGlobalSearch";
                }
                case 1: 
            }
            return "getSearchableInfo";
        }

        public static boolean setDefaultImpl(ISearchManager iSearchManager) {
            if (Proxy.sDefaultImpl == null && iSearchManager != null) {
                Proxy.sDefaultImpl = iSearchManager;
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
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.launchLegacyAssist(string2, n, (Bundle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.launchAssist((Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWebSearchActivity();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGlobalSearchActivity();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGlobalSearchActivities();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSearchablesInGlobalSearch();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.getSearchableInfo((ComponentName)object);
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((SearchableInfo)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISearchManager {
            public static ISearchManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public List<ResolveInfo> getGlobalSearchActivities() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ResolveInfo> list = Stub.getDefaultImpl().getGlobalSearchActivities();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ResolveInfo> arrayList = parcel2.createTypedArrayList(ResolveInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getGlobalSearchActivity() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getGlobalSearchActivity();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public SearchableInfo getSearchableInfo(ComponentName parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SearchableInfo searchableInfo = Stub.getDefaultImpl().getSearchableInfo((ComponentName)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return searchableInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        SearchableInfo searchableInfo = SearchableInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<SearchableInfo> list = Stub.getDefaultImpl().getSearchablesInGlobalSearch();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<SearchableInfo> arrayList = parcel2.createTypedArrayList(SearchableInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getWebSearchActivity() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getWebSearchActivity();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            @Override
            public void launchAssist(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().launchAssist(bundle);
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
            public boolean launchLegacyAssist(String string2, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().launchLegacyAssist(string2, n, bundle);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }
        }

    }

}

