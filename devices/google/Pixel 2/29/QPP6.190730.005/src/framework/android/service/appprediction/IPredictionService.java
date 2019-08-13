/*
 * Decompiled with CFR 0.145.
 */
package android.service.appprediction;

import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.IPredictionCallback;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IPredictionService
extends IInterface {
    public void notifyAppTargetEvent(AppPredictionSessionId var1, AppTargetEvent var2) throws RemoteException;

    public void notifyLaunchLocationShown(AppPredictionSessionId var1, String var2, ParceledListSlice var3) throws RemoteException;

    public void onCreatePredictionSession(AppPredictionContext var1, AppPredictionSessionId var2) throws RemoteException;

    public void onDestroyPredictionSession(AppPredictionSessionId var1) throws RemoteException;

    public void registerPredictionUpdates(AppPredictionSessionId var1, IPredictionCallback var2) throws RemoteException;

    public void requestPredictionUpdate(AppPredictionSessionId var1) throws RemoteException;

    public void sortAppTargets(AppPredictionSessionId var1, ParceledListSlice var2, IPredictionCallback var3) throws RemoteException;

    public void unregisterPredictionUpdates(AppPredictionSessionId var1, IPredictionCallback var2) throws RemoteException;

    public static class Default
    implements IPredictionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException {
        }

        @Override
        public void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String string2, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override
        public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override
        public void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
        }

        @Override
        public void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override
        public void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException {
        }

        @Override
        public void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPredictionService {
        private static final String DESCRIPTOR = "android.service.appprediction.IPredictionService";
        static final int TRANSACTION_notifyAppTargetEvent = 2;
        static final int TRANSACTION_notifyLaunchLocationShown = 3;
        static final int TRANSACTION_onCreatePredictionSession = 1;
        static final int TRANSACTION_onDestroyPredictionSession = 8;
        static final int TRANSACTION_registerPredictionUpdates = 5;
        static final int TRANSACTION_requestPredictionUpdate = 7;
        static final int TRANSACTION_sortAppTargets = 4;
        static final int TRANSACTION_unregisterPredictionUpdates = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPredictionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPredictionService) {
                return (IPredictionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPredictionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "onDestroyPredictionSession";
                }
                case 7: {
                    return "requestPredictionUpdate";
                }
                case 6: {
                    return "unregisterPredictionUpdates";
                }
                case 5: {
                    return "registerPredictionUpdates";
                }
                case 4: {
                    return "sortAppTargets";
                }
                case 3: {
                    return "notifyLaunchLocationShown";
                }
                case 2: {
                    return "notifyAppTargetEvent";
                }
                case 1: 
            }
            return "onCreatePredictionSession";
        }

        public static boolean setDefaultImpl(IPredictionService iPredictionService) {
            if (Proxy.sDefaultImpl == null && iPredictionService != null) {
                Proxy.sDefaultImpl = iPredictionService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)((Object)parcelable), (Parcel)object, n2);
                    }
                    case 8: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.onDestroyPredictionSession((AppPredictionSessionId)parcelable);
                        return true;
                    }
                    case 7: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.requestPredictionUpdate((AppPredictionSessionId)parcelable);
                        return true;
                    }
                    case 6: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.unregisterPredictionUpdates((AppPredictionSessionId)object, IPredictionCallback.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.registerPredictionUpdates((AppPredictionSessionId)object, IPredictionCallback.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        ParceledListSlice parceledListSlice = ((Parcel)((Object)parcelable)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.sortAppTargets((AppPredictionSessionId)object, parceledListSlice, IPredictionCallback.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        String string2 = ((Parcel)((Object)parcelable)).readString();
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.notifyLaunchLocationShown((AppPredictionSessionId)object, string2, (ParceledListSlice)parcelable);
                        return true;
                    }
                    case 2: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppTargetEvent.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.notifyAppTargetEvent((AppPredictionSessionId)object, (AppTargetEvent)parcelable);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                object = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionContext.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? AppPredictionSessionId.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                this.onCreatePredictionSession((AppPredictionContext)object, (AppPredictionSessionId)parcelable);
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPredictionService {
            public static IPredictionService sDefaultImpl;
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
            public void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (appTargetEvent != null) {
                        parcel.writeInt(1);
                        appTargetEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAppTargetEvent(appPredictionSessionId, appTargetEvent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String string2, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
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
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyLaunchLocationShown(appPredictionSessionId, string2, parceledListSlice);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionContext != null) {
                        parcel.writeInt(1);
                        appPredictionContext.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCreatePredictionSession(appPredictionContext, appPredictionSessionId);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDestroyPredictionSession(appPredictionSessionId);
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
            public void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iPredictionCallback != null ? iPredictionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().registerPredictionUpdates(appPredictionSessionId, iPredictionCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestPredictionUpdate(appPredictionSessionId);
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
            public void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iPredictionCallback != null ? iPredictionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().sortAppTargets(appPredictionSessionId, parceledListSlice, iPredictionCallback);
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
            public void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appPredictionSessionId != null) {
                        parcel.writeInt(1);
                        appPredictionSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iPredictionCallback != null ? iPredictionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterPredictionUpdates(appPredictionSessionId, iPredictionCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

