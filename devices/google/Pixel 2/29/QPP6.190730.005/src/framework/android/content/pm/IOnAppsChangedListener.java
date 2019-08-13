/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;

public interface IOnAppsChangedListener
extends IInterface {
    public void onPackageAdded(UserHandle var1, String var2) throws RemoteException;

    public void onPackageChanged(UserHandle var1, String var2) throws RemoteException;

    public void onPackageRemoved(UserHandle var1, String var2) throws RemoteException;

    public void onPackagesAvailable(UserHandle var1, String[] var2, boolean var3) throws RemoteException;

    public void onPackagesSuspended(UserHandle var1, String[] var2, Bundle var3) throws RemoteException;

    public void onPackagesUnavailable(UserHandle var1, String[] var2, boolean var3) throws RemoteException;

    public void onPackagesUnsuspended(UserHandle var1, String[] var2) throws RemoteException;

    public void onShortcutChanged(UserHandle var1, String var2, ParceledListSlice var3) throws RemoteException;

    public static class Default
    implements IOnAppsChangedListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPackageAdded(UserHandle userHandle, String string2) throws RemoteException {
        }

        @Override
        public void onPackageChanged(UserHandle userHandle, String string2) throws RemoteException {
        }

        @Override
        public void onPackageRemoved(UserHandle userHandle, String string2) throws RemoteException {
        }

        @Override
        public void onPackagesAvailable(UserHandle userHandle, String[] arrstring, boolean bl) throws RemoteException {
        }

        @Override
        public void onPackagesSuspended(UserHandle userHandle, String[] arrstring, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onPackagesUnavailable(UserHandle userHandle, String[] arrstring, boolean bl) throws RemoteException {
        }

        @Override
        public void onPackagesUnsuspended(UserHandle userHandle, String[] arrstring) throws RemoteException {
        }

        @Override
        public void onShortcutChanged(UserHandle userHandle, String string2, ParceledListSlice parceledListSlice) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOnAppsChangedListener {
        private static final String DESCRIPTOR = "android.content.pm.IOnAppsChangedListener";
        static final int TRANSACTION_onPackageAdded = 2;
        static final int TRANSACTION_onPackageChanged = 3;
        static final int TRANSACTION_onPackageRemoved = 1;
        static final int TRANSACTION_onPackagesAvailable = 4;
        static final int TRANSACTION_onPackagesSuspended = 6;
        static final int TRANSACTION_onPackagesUnavailable = 5;
        static final int TRANSACTION_onPackagesUnsuspended = 7;
        static final int TRANSACTION_onShortcutChanged = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOnAppsChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOnAppsChangedListener) {
                return (IOnAppsChangedListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOnAppsChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "onShortcutChanged";
                }
                case 7: {
                    return "onPackagesUnsuspended";
                }
                case 6: {
                    return "onPackagesSuspended";
                }
                case 5: {
                    return "onPackagesUnavailable";
                }
                case 4: {
                    return "onPackagesAvailable";
                }
                case 3: {
                    return "onPackageChanged";
                }
                case 2: {
                    return "onPackageAdded";
                }
                case 1: 
            }
            return "onPackageRemoved";
        }

        public static boolean setDefaultImpl(IOnAppsChangedListener iOnAppsChangedListener) {
            if (Proxy.sDefaultImpl == null && iOnAppsChangedListener != null) {
                Proxy.sDefaultImpl = iOnAppsChangedListener;
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
        public boolean onTransact(int n, Parcel parcelable, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)((Object)parcelable), (Parcel)object, n2);
                    }
                    case 8: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        String string2 = ((Parcel)((Object)parcelable)).readString();
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onShortcutChanged((UserHandle)object, string2, (ParceledListSlice)parcelable);
                        return true;
                    }
                    case 7: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onPackagesUnsuspended((UserHandle)object, ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 6: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        String[] arrstring = ((Parcel)((Object)parcelable)).createStringArray();
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onPackagesSuspended((UserHandle)object, arrstring, (Bundle)parcelable);
                        return true;
                    }
                    case 5: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        String[] arrstring = ((Parcel)((Object)parcelable)).createStringArray();
                        if (((Parcel)((Object)parcelable)).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onPackagesUnavailable((UserHandle)object, arrstring, bl2);
                        return true;
                    }
                    case 4: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        String[] arrstring = ((Parcel)((Object)parcelable)).createStringArray();
                        bl2 = bl;
                        if (((Parcel)((Object)parcelable)).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onPackagesAvailable((UserHandle)object, arrstring, bl2);
                        return true;
                    }
                    case 3: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onPackageChanged((UserHandle)object, ((Parcel)((Object)parcelable)).readString());
                        return true;
                    }
                    case 2: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onPackageAdded((UserHandle)object, ((Parcel)((Object)parcelable)).readString());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                object = ((Parcel)((Object)parcelable)).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                this.onPackageRemoved((UserHandle)object, ((Parcel)((Object)parcelable)).readString());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IOnAppsChangedListener {
            public static IOnAppsChangedListener sDefaultImpl;
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
            public void onPackageAdded(UserHandle userHandle, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageAdded(userHandle, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPackageChanged(UserHandle userHandle, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageChanged(userHandle, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPackageRemoved(UserHandle userHandle, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageRemoved(userHandle, string2);
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
            public void onPackagesAvailable(UserHandle userHandle, String[] arrstring, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onPackagesAvailable(userHandle, arrstring, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPackagesSuspended(UserHandle userHandle, String[] arrstring, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackagesSuspended(userHandle, arrstring, bundle);
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
            public void onPackagesUnavailable(UserHandle userHandle, String[] arrstring, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onPackagesUnavailable(userHandle, arrstring, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPackagesUnsuspended(UserHandle userHandle, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackagesUnsuspended(userHandle, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onShortcutChanged(UserHandle userHandle, String string2, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShortcutChanged(userHandle, string2, parceledListSlice);
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

