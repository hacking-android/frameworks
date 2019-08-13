/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.euicc.IDeleteSubscriptionCallback;
import android.service.euicc.IDownloadSubscriptionCallback;
import android.service.euicc.IEraseSubscriptionsCallback;
import android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback;
import android.service.euicc.IGetDownloadableSubscriptionMetadataCallback;
import android.service.euicc.IGetEidCallback;
import android.service.euicc.IGetEuiccInfoCallback;
import android.service.euicc.IGetEuiccProfileInfoListCallback;
import android.service.euicc.IGetOtaStatusCallback;
import android.service.euicc.IOtaStatusChangedCallback;
import android.service.euicc.IRetainSubscriptionsForFactoryResetCallback;
import android.service.euicc.ISwitchToSubscriptionCallback;
import android.service.euicc.IUpdateSubscriptionNicknameCallback;
import android.telephony.euicc.DownloadableSubscription;

public interface IEuiccService
extends IInterface {
    public void deleteSubscription(int var1, String var2, IDeleteSubscriptionCallback var3) throws RemoteException;

    public void downloadSubscription(int var1, DownloadableSubscription var2, boolean var3, boolean var4, Bundle var5, IDownloadSubscriptionCallback var6) throws RemoteException;

    public void eraseSubscriptions(int var1, IEraseSubscriptionsCallback var2) throws RemoteException;

    public void getDefaultDownloadableSubscriptionList(int var1, boolean var2, IGetDefaultDownloadableSubscriptionListCallback var3) throws RemoteException;

    public void getDownloadableSubscriptionMetadata(int var1, DownloadableSubscription var2, boolean var3, IGetDownloadableSubscriptionMetadataCallback var4) throws RemoteException;

    public void getEid(int var1, IGetEidCallback var2) throws RemoteException;

    public void getEuiccInfo(int var1, IGetEuiccInfoCallback var2) throws RemoteException;

    public void getEuiccProfileInfoList(int var1, IGetEuiccProfileInfoListCallback var2) throws RemoteException;

    public void getOtaStatus(int var1, IGetOtaStatusCallback var2) throws RemoteException;

    public void retainSubscriptionsForFactoryReset(int var1, IRetainSubscriptionsForFactoryResetCallback var2) throws RemoteException;

    public void startOtaIfNecessary(int var1, IOtaStatusChangedCallback var2) throws RemoteException;

    public void switchToSubscription(int var1, String var2, boolean var3, ISwitchToSubscriptionCallback var4) throws RemoteException;

    public void updateSubscriptionNickname(int var1, String var2, String var3, IUpdateSubscriptionNicknameCallback var4) throws RemoteException;

    public static class Default
    implements IEuiccService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void deleteSubscription(int n, String string2, IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws RemoteException {
        }

        @Override
        public void downloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, boolean bl2, Bundle bundle, IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws RemoteException {
        }

        @Override
        public void eraseSubscriptions(int n, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
        }

        @Override
        public void getDefaultDownloadableSubscriptionList(int n, boolean bl, IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws RemoteException {
        }

        @Override
        public void getDownloadableSubscriptionMetadata(int n, DownloadableSubscription downloadableSubscription, boolean bl, IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws RemoteException {
        }

        @Override
        public void getEid(int n, IGetEidCallback iGetEidCallback) throws RemoteException {
        }

        @Override
        public void getEuiccInfo(int n, IGetEuiccInfoCallback iGetEuiccInfoCallback) throws RemoteException {
        }

        @Override
        public void getEuiccProfileInfoList(int n, IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws RemoteException {
        }

        @Override
        public void getOtaStatus(int n, IGetOtaStatusCallback iGetOtaStatusCallback) throws RemoteException {
        }

        @Override
        public void retainSubscriptionsForFactoryReset(int n, IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws RemoteException {
        }

        @Override
        public void startOtaIfNecessary(int n, IOtaStatusChangedCallback iOtaStatusChangedCallback) throws RemoteException {
        }

        @Override
        public void switchToSubscription(int n, String string2, boolean bl, ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback) throws RemoteException {
        }

        @Override
        public void updateSubscriptionNickname(int n, String string2, String string3, IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IEuiccService {
        private static final String DESCRIPTOR = "android.service.euicc.IEuiccService";
        static final int TRANSACTION_deleteSubscription = 9;
        static final int TRANSACTION_downloadSubscription = 1;
        static final int TRANSACTION_eraseSubscriptions = 12;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 7;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 3;
        static final int TRANSACTION_getEuiccInfo = 8;
        static final int TRANSACTION_getEuiccProfileInfoList = 6;
        static final int TRANSACTION_getOtaStatus = 4;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 13;
        static final int TRANSACTION_startOtaIfNecessary = 5;
        static final int TRANSACTION_switchToSubscription = 10;
        static final int TRANSACTION_updateSubscriptionNickname = 11;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IEuiccService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IEuiccService) {
                return (IEuiccService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IEuiccService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 13: {
                    return "retainSubscriptionsForFactoryReset";
                }
                case 12: {
                    return "eraseSubscriptions";
                }
                case 11: {
                    return "updateSubscriptionNickname";
                }
                case 10: {
                    return "switchToSubscription";
                }
                case 9: {
                    return "deleteSubscription";
                }
                case 8: {
                    return "getEuiccInfo";
                }
                case 7: {
                    return "getDefaultDownloadableSubscriptionList";
                }
                case 6: {
                    return "getEuiccProfileInfoList";
                }
                case 5: {
                    return "startOtaIfNecessary";
                }
                case 4: {
                    return "getOtaStatus";
                }
                case 3: {
                    return "getEid";
                }
                case 2: {
                    return "getDownloadableSubscriptionMetadata";
                }
                case 1: 
            }
            return "downloadSubscription";
        }

        public static boolean setDefaultImpl(IEuiccService iEuiccService) {
            if (Proxy.sDefaultImpl == null && iEuiccService != null) {
                Proxy.sDefaultImpl = iEuiccService;
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
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 13: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.retainSubscriptionsForFactoryReset(parcel.readInt(), IRetainSubscriptionsForFactoryResetCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 12: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.eraseSubscriptions(parcel.readInt(), IEraseSubscriptionsCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 11: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.updateSubscriptionNickname(parcel.readInt(), parcel.readString(), parcel.readString(), IUpdateSubscriptionNicknameCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        object = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bl3 = true;
                        }
                        this.switchToSubscription(n, (String)object, bl3, ISwitchToSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.deleteSubscription(parcel.readInt(), parcel.readString(), IDeleteSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getEuiccInfo(parcel.readInt(), IGetEuiccInfoCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        bl3 = bl;
                        if (parcel.readInt() != 0) {
                            bl3 = true;
                        }
                        this.getDefaultDownloadableSubscriptionList(n, bl3, IGetDefaultDownloadableSubscriptionListCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getEuiccProfileInfoList(parcel.readInt(), IGetEuiccProfileInfoListCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.startOtaIfNecessary(parcel.readInt(), IOtaStatusChangedCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getOtaStatus(parcel.readInt(), IGetOtaStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getEid(parcel.readInt(), IGetEidCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        object = parcel.readInt() != 0 ? DownloadableSubscription.CREATOR.createFromParcel(parcel) : null;
                        bl3 = bl2;
                        if (parcel.readInt() != 0) {
                            bl3 = true;
                        }
                        this.getDownloadableSubscriptionMetadata(n, (DownloadableSubscription)object, bl3, IGetDownloadableSubscriptionMetadataCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = parcel.readInt();
                object = parcel.readInt() != 0 ? DownloadableSubscription.CREATOR.createFromParcel(parcel) : null;
                bl3 = parcel.readInt() != 0;
                bl = parcel.readInt() != 0;
                Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                this.downloadSubscription(n, (DownloadableSubscription)object, bl3, bl, bundle, IDownloadSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IEuiccService {
            public static IEuiccService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
            public void deleteSubscription(int n, String string2, IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    IBinder iBinder = iDeleteSubscriptionCallback != null ? iDeleteSubscriptionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().deleteSubscription(n, string2, iDeleteSubscriptionCallback);
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
            public void downloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, boolean bl2, Bundle bundle, IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws RemoteException {
                Parcel parcel;
                void var2_6;
                block11 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        if (downloadableSubscription != null) {
                            parcel.writeInt(1);
                            downloadableSubscription.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        int n2 = bl ? 1 : 0;
                        parcel.writeInt(n2);
                        n2 = bl2 ? 1 : 0;
                        parcel.writeInt(n2);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        IBinder iBinder = iDownloadSubscriptionCallback != null ? iDownloadSubscriptionCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().downloadSubscription(n, downloadableSubscription, bl, bl2, bundle, iDownloadSubscriptionCallback);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_6;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void eraseSubscriptions(int n, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iEraseSubscriptionsCallback != null ? iEraseSubscriptionsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().eraseSubscriptions(n, iEraseSubscriptionsCallback);
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
            public void getDefaultDownloadableSubscriptionList(int n, boolean bl, IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    IBinder iBinder = iGetDefaultDownloadableSubscriptionListCallback != null ? iGetDefaultDownloadableSubscriptionListCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getDefaultDownloadableSubscriptionList(n, bl, iGetDefaultDownloadableSubscriptionListCallback);
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
            public void getDownloadableSubscriptionMetadata(int n, DownloadableSubscription downloadableSubscription, boolean bl, IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = 0;
                    if (downloadableSubscription != null) {
                        parcel.writeInt(1);
                        downloadableSubscription.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    IBinder iBinder = iGetDownloadableSubscriptionMetadataCallback != null ? iGetDownloadableSubscriptionMetadataCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getDownloadableSubscriptionMetadata(n, downloadableSubscription, bl, iGetDownloadableSubscriptionMetadataCallback);
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
            public void getEid(int n, IGetEidCallback iGetEidCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iGetEidCallback != null ? iGetEidCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEid(n, iGetEidCallback);
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
            public void getEuiccInfo(int n, IGetEuiccInfoCallback iGetEuiccInfoCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iGetEuiccInfoCallback != null ? iGetEuiccInfoCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEuiccInfo(n, iGetEuiccInfoCallback);
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
            public void getEuiccProfileInfoList(int n, IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iGetEuiccProfileInfoListCallback != null ? iGetEuiccProfileInfoListCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEuiccProfileInfoList(n, iGetEuiccProfileInfoListCallback);
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
            public void getOtaStatus(int n, IGetOtaStatusCallback iGetOtaStatusCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iGetOtaStatusCallback != null ? iGetOtaStatusCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getOtaStatus(n, iGetOtaStatusCallback);
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
            public void retainSubscriptionsForFactoryReset(int n, IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iRetainSubscriptionsForFactoryResetCallback != null ? iRetainSubscriptionsForFactoryResetCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(13, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().retainSubscriptionsForFactoryReset(n, iRetainSubscriptionsForFactoryResetCallback);
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
            public void startOtaIfNecessary(int n, IOtaStatusChangedCallback iOtaStatusChangedCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iOtaStatusChangedCallback != null ? iOtaStatusChangedCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().startOtaIfNecessary(n, iOtaStatusChangedCallback);
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
            public void switchToSubscription(int n, String string2, boolean bl, ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    IBinder iBinder = iSwitchToSubscriptionCallback != null ? iSwitchToSubscriptionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(10, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().switchToSubscription(n, string2, bl, iSwitchToSubscriptionCallback);
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
            public void updateSubscriptionNickname(int n, String string2, String string3, IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iUpdateSubscriptionNicknameCallback != null ? iUpdateSubscriptionNicknameCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().updateSubscriptionNickname(n, string2, string3, iUpdateSubscriptionNicknameCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

