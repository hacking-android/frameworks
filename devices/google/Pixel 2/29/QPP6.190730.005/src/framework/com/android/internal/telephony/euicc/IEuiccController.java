/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;

public interface IEuiccController
extends IInterface {
    public void continueOperation(int var1, Intent var2, Bundle var3) throws RemoteException;

    public void deleteSubscription(int var1, int var2, String var3, PendingIntent var4) throws RemoteException;

    public void downloadSubscription(int var1, DownloadableSubscription var2, boolean var3, String var4, Bundle var5, PendingIntent var6) throws RemoteException;

    public void eraseSubscriptions(int var1, PendingIntent var2) throws RemoteException;

    public void getDefaultDownloadableSubscriptionList(int var1, String var2, PendingIntent var3) throws RemoteException;

    public void getDownloadableSubscriptionMetadata(int var1, DownloadableSubscription var2, String var3, PendingIntent var4) throws RemoteException;

    public String getEid(int var1, String var2) throws RemoteException;

    public EuiccInfo getEuiccInfo(int var1) throws RemoteException;

    public int getOtaStatus(int var1) throws RemoteException;

    public void retainSubscriptionsForFactoryReset(int var1, PendingIntent var2) throws RemoteException;

    public void switchToSubscription(int var1, int var2, String var3, PendingIntent var4) throws RemoteException;

    public void updateSubscriptionNickname(int var1, int var2, String var3, String var4, PendingIntent var5) throws RemoteException;

    public static class Default
    implements IEuiccController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void continueOperation(int n, Intent intent, Bundle bundle) throws RemoteException {
        }

        @Override
        public void deleteSubscription(int n, int n2, String string2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void downloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, String string2, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void eraseSubscriptions(int n, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void getDefaultDownloadableSubscriptionList(int n, String string2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void getDownloadableSubscriptionMetadata(int n, DownloadableSubscription downloadableSubscription, String string2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public String getEid(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public EuiccInfo getEuiccInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getOtaStatus(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void retainSubscriptionsForFactoryReset(int n, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void switchToSubscription(int n, int n2, String string2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void updateSubscriptionNickname(int n, int n2, String string2, String string3, PendingIntent pendingIntent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IEuiccController {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IEuiccController";
        static final int TRANSACTION_continueOperation = 1;
        static final int TRANSACTION_deleteSubscription = 8;
        static final int TRANSACTION_downloadSubscription = 6;
        static final int TRANSACTION_eraseSubscriptions = 11;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 3;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 4;
        static final int TRANSACTION_getEuiccInfo = 7;
        static final int TRANSACTION_getOtaStatus = 5;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 12;
        static final int TRANSACTION_switchToSubscription = 9;
        static final int TRANSACTION_updateSubscriptionNickname = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IEuiccController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IEuiccController) {
                return (IEuiccController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IEuiccController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 12: {
                    return "retainSubscriptionsForFactoryReset";
                }
                case 11: {
                    return "eraseSubscriptions";
                }
                case 10: {
                    return "updateSubscriptionNickname";
                }
                case 9: {
                    return "switchToSubscription";
                }
                case 8: {
                    return "deleteSubscription";
                }
                case 7: {
                    return "getEuiccInfo";
                }
                case 6: {
                    return "downloadSubscription";
                }
                case 5: {
                    return "getOtaStatus";
                }
                case 4: {
                    return "getEid";
                }
                case 3: {
                    return "getDefaultDownloadableSubscriptionList";
                }
                case 2: {
                    return "getDownloadableSubscriptionMetadata";
                }
                case 1: 
            }
            return "continueOperation";
        }

        public static boolean setDefaultImpl(IEuiccController iEuiccController) {
            if (Proxy.sDefaultImpl == null && iEuiccController != null) {
                Proxy.sDefaultImpl = iEuiccController;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.retainSubscriptionsForFactoryReset(n, (PendingIntent)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.eraseSubscriptions(n, (PendingIntent)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateSubscriptionNickname(n2, n, string2, (String)object2, (PendingIntent)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.switchToSubscription(n, n2, (String)object2, (PendingIntent)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deleteSubscription(n2, n, (String)object2, (PendingIntent)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEuiccInfo(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((EuiccInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? DownloadableSubscription.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        String string3 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.downloadSubscription(n, (DownloadableSubscription)object2, bl, string3, bundle, (PendingIntent)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getOtaStatus(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEid(((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getDefaultDownloadableSubscriptionList(n, (String)object2, (PendingIntent)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? DownloadableSubscription.CREATOR.createFromParcel((Parcel)object) : null;
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getDownloadableSubscriptionMetadata(n, (DownloadableSubscription)object2, string4, (PendingIntent)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object2 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.continueOperation(n, (Intent)object2, (Bundle)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IEuiccController {
            public static IEuiccController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void continueOperation(int n, Intent intent, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
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
                        Stub.getDefaultImpl().continueOperation(n, intent, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deleteSubscription(int n, int n2, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteSubscription(n, n2, string2, pendingIntent);
                        return;
                    }
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
            public void downloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, String string2, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel;
                void var2_7;
                block15 : {
                    block14 : {
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
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeString(string2);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            if (pendingIntent != null) {
                                parcel.writeInt(1);
                                pendingIntent.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().downloadSubscription(n, downloadableSubscription, bl, string2, bundle, pendingIntent);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_7;
            }

            @Override
            public void eraseSubscriptions(int n, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().eraseSubscriptions(n, pendingIntent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void getDefaultDownloadableSubscriptionList(int n, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getDefaultDownloadableSubscriptionList(n, string2, pendingIntent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void getDownloadableSubscriptionMetadata(int n, DownloadableSubscription downloadableSubscription, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (downloadableSubscription != null) {
                        parcel.writeInt(1);
                        downloadableSubscription.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getDownloadableSubscriptionMetadata(n, downloadableSubscription, string2, pendingIntent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public String getEid(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getEid(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public EuiccInfo getEuiccInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        EuiccInfo euiccInfo = Stub.getDefaultImpl().getEuiccInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return euiccInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                EuiccInfo euiccInfo = parcel2.readInt() != 0 ? EuiccInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return euiccInfo;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getOtaStatus(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getOtaStatus(n);
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
            public void retainSubscriptionsForFactoryReset(int n, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().retainSubscriptionsForFactoryReset(n, pendingIntent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void switchToSubscription(int n, int n2, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().switchToSubscription(n, n2, string2, pendingIntent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateSubscriptionNickname(int n, int n2, String string2, String string3, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateSubscriptionNickname(n, n2, string2, string3, pendingIntent);
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

