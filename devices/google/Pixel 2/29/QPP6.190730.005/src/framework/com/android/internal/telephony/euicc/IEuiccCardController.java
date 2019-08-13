/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.euicc.IAuthenticateServerCallback;
import com.android.internal.telephony.euicc.ICancelSessionCallback;
import com.android.internal.telephony.euicc.IDeleteProfileCallback;
import com.android.internal.telephony.euicc.IDisableProfileCallback;
import com.android.internal.telephony.euicc.IGetAllProfilesCallback;
import com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.IGetEuiccChallengeCallback;
import com.android.internal.telephony.euicc.IGetEuiccInfo1Callback;
import com.android.internal.telephony.euicc.IGetEuiccInfo2Callback;
import com.android.internal.telephony.euicc.IGetProfileCallback;
import com.android.internal.telephony.euicc.IGetRulesAuthTableCallback;
import com.android.internal.telephony.euicc.IGetSmdsAddressCallback;
import com.android.internal.telephony.euicc.IListNotificationsCallback;
import com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback;
import com.android.internal.telephony.euicc.IPrepareDownloadCallback;
import com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback;
import com.android.internal.telephony.euicc.IResetMemoryCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationListCallback;
import com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.ISetNicknameCallback;
import com.android.internal.telephony.euicc.ISwitchToProfileCallback;

public interface IEuiccCardController
extends IInterface {
    public void authenticateServer(String var1, String var2, String var3, byte[] var4, byte[] var5, byte[] var6, byte[] var7, IAuthenticateServerCallback var8) throws RemoteException;

    public void cancelSession(String var1, String var2, byte[] var3, int var4, ICancelSessionCallback var5) throws RemoteException;

    public void deleteProfile(String var1, String var2, String var3, IDeleteProfileCallback var4) throws RemoteException;

    public void disableProfile(String var1, String var2, String var3, boolean var4, IDisableProfileCallback var5) throws RemoteException;

    public void getAllProfiles(String var1, String var2, IGetAllProfilesCallback var3) throws RemoteException;

    public void getDefaultSmdpAddress(String var1, String var2, IGetDefaultSmdpAddressCallback var3) throws RemoteException;

    public void getEuiccChallenge(String var1, String var2, IGetEuiccChallengeCallback var3) throws RemoteException;

    public void getEuiccInfo1(String var1, String var2, IGetEuiccInfo1Callback var3) throws RemoteException;

    public void getEuiccInfo2(String var1, String var2, IGetEuiccInfo2Callback var3) throws RemoteException;

    public void getProfile(String var1, String var2, String var3, IGetProfileCallback var4) throws RemoteException;

    public void getRulesAuthTable(String var1, String var2, IGetRulesAuthTableCallback var3) throws RemoteException;

    public void getSmdsAddress(String var1, String var2, IGetSmdsAddressCallback var3) throws RemoteException;

    public void listNotifications(String var1, String var2, int var3, IListNotificationsCallback var4) throws RemoteException;

    public void loadBoundProfilePackage(String var1, String var2, byte[] var3, ILoadBoundProfilePackageCallback var4) throws RemoteException;

    public void prepareDownload(String var1, String var2, byte[] var3, byte[] var4, byte[] var5, byte[] var6, IPrepareDownloadCallback var7) throws RemoteException;

    public void removeNotificationFromList(String var1, String var2, int var3, IRemoveNotificationFromListCallback var4) throws RemoteException;

    public void resetMemory(String var1, String var2, int var3, IResetMemoryCallback var4) throws RemoteException;

    public void retrieveNotification(String var1, String var2, int var3, IRetrieveNotificationCallback var4) throws RemoteException;

    public void retrieveNotificationList(String var1, String var2, int var3, IRetrieveNotificationListCallback var4) throws RemoteException;

    public void setDefaultSmdpAddress(String var1, String var2, String var3, ISetDefaultSmdpAddressCallback var4) throws RemoteException;

    public void setNickname(String var1, String var2, String var3, String var4, ISetNicknameCallback var5) throws RemoteException;

    public void switchToProfile(String var1, String var2, String var3, boolean var4, ISwitchToProfileCallback var5) throws RemoteException;

    public static class Default
    implements IEuiccCardController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void authenticateServer(String string2, String string3, String string4, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, IAuthenticateServerCallback iAuthenticateServerCallback) throws RemoteException {
        }

        @Override
        public void cancelSession(String string2, String string3, byte[] arrby, int n, ICancelSessionCallback iCancelSessionCallback) throws RemoteException {
        }

        @Override
        public void deleteProfile(String string2, String string3, String string4, IDeleteProfileCallback iDeleteProfileCallback) throws RemoteException {
        }

        @Override
        public void disableProfile(String string2, String string3, String string4, boolean bl, IDisableProfileCallback iDisableProfileCallback) throws RemoteException {
        }

        @Override
        public void getAllProfiles(String string2, String string3, IGetAllProfilesCallback iGetAllProfilesCallback) throws RemoteException {
        }

        @Override
        public void getDefaultSmdpAddress(String string2, String string3, IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws RemoteException {
        }

        @Override
        public void getEuiccChallenge(String string2, String string3, IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws RemoteException {
        }

        @Override
        public void getEuiccInfo1(String string2, String string3, IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws RemoteException {
        }

        @Override
        public void getEuiccInfo2(String string2, String string3, IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws RemoteException {
        }

        @Override
        public void getProfile(String string2, String string3, String string4, IGetProfileCallback iGetProfileCallback) throws RemoteException {
        }

        @Override
        public void getRulesAuthTable(String string2, String string3, IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws RemoteException {
        }

        @Override
        public void getSmdsAddress(String string2, String string3, IGetSmdsAddressCallback iGetSmdsAddressCallback) throws RemoteException {
        }

        @Override
        public void listNotifications(String string2, String string3, int n, IListNotificationsCallback iListNotificationsCallback) throws RemoteException {
        }

        @Override
        public void loadBoundProfilePackage(String string2, String string3, byte[] arrby, ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws RemoteException {
        }

        @Override
        public void prepareDownload(String string2, String string3, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, IPrepareDownloadCallback iPrepareDownloadCallback) throws RemoteException {
        }

        @Override
        public void removeNotificationFromList(String string2, String string3, int n, IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws RemoteException {
        }

        @Override
        public void resetMemory(String string2, String string3, int n, IResetMemoryCallback iResetMemoryCallback) throws RemoteException {
        }

        @Override
        public void retrieveNotification(String string2, String string3, int n, IRetrieveNotificationCallback iRetrieveNotificationCallback) throws RemoteException {
        }

        @Override
        public void retrieveNotificationList(String string2, String string3, int n, IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws RemoteException {
        }

        @Override
        public void setDefaultSmdpAddress(String string2, String string3, String string4, ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws RemoteException {
        }

        @Override
        public void setNickname(String string2, String string3, String string4, String string5, ISetNicknameCallback iSetNicknameCallback) throws RemoteException {
        }

        @Override
        public void switchToProfile(String string2, String string3, String string4, boolean bl, ISwitchToProfileCallback iSwitchToProfileCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IEuiccCardController {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IEuiccCardController";
        static final int TRANSACTION_authenticateServer = 15;
        static final int TRANSACTION_cancelSession = 18;
        static final int TRANSACTION_deleteProfile = 6;
        static final int TRANSACTION_disableProfile = 3;
        static final int TRANSACTION_getAllProfiles = 1;
        static final int TRANSACTION_getDefaultSmdpAddress = 8;
        static final int TRANSACTION_getEuiccChallenge = 12;
        static final int TRANSACTION_getEuiccInfo1 = 13;
        static final int TRANSACTION_getEuiccInfo2 = 14;
        static final int TRANSACTION_getProfile = 2;
        static final int TRANSACTION_getRulesAuthTable = 11;
        static final int TRANSACTION_getSmdsAddress = 9;
        static final int TRANSACTION_listNotifications = 19;
        static final int TRANSACTION_loadBoundProfilePackage = 17;
        static final int TRANSACTION_prepareDownload = 16;
        static final int TRANSACTION_removeNotificationFromList = 22;
        static final int TRANSACTION_resetMemory = 7;
        static final int TRANSACTION_retrieveNotification = 21;
        static final int TRANSACTION_retrieveNotificationList = 20;
        static final int TRANSACTION_setDefaultSmdpAddress = 10;
        static final int TRANSACTION_setNickname = 5;
        static final int TRANSACTION_switchToProfile = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IEuiccCardController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IEuiccCardController) {
                return (IEuiccCardController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IEuiccCardController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 22: {
                    return "removeNotificationFromList";
                }
                case 21: {
                    return "retrieveNotification";
                }
                case 20: {
                    return "retrieveNotificationList";
                }
                case 19: {
                    return "listNotifications";
                }
                case 18: {
                    return "cancelSession";
                }
                case 17: {
                    return "loadBoundProfilePackage";
                }
                case 16: {
                    return "prepareDownload";
                }
                case 15: {
                    return "authenticateServer";
                }
                case 14: {
                    return "getEuiccInfo2";
                }
                case 13: {
                    return "getEuiccInfo1";
                }
                case 12: {
                    return "getEuiccChallenge";
                }
                case 11: {
                    return "getRulesAuthTable";
                }
                case 10: {
                    return "setDefaultSmdpAddress";
                }
                case 9: {
                    return "getSmdsAddress";
                }
                case 8: {
                    return "getDefaultSmdpAddress";
                }
                case 7: {
                    return "resetMemory";
                }
                case 6: {
                    return "deleteProfile";
                }
                case 5: {
                    return "setNickname";
                }
                case 4: {
                    return "switchToProfile";
                }
                case 3: {
                    return "disableProfile";
                }
                case 2: {
                    return "getProfile";
                }
                case 1: 
            }
            return "getAllProfiles";
        }

        public static boolean setDefaultImpl(IEuiccCardController iEuiccCardController) {
            if (Proxy.sDefaultImpl == null && iEuiccCardController != null) {
                Proxy.sDefaultImpl = iEuiccCardController;
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
                    case 22: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.removeNotificationFromList(parcel.readString(), parcel.readString(), parcel.readInt(), IRemoveNotificationFromListCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 21: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.retrieveNotification(parcel.readString(), parcel.readString(), parcel.readInt(), IRetrieveNotificationCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 20: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.retrieveNotificationList(parcel.readString(), parcel.readString(), parcel.readInt(), IRetrieveNotificationListCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 19: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.listNotifications(parcel.readString(), parcel.readString(), parcel.readInt(), IListNotificationsCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 18: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.cancelSession(parcel.readString(), parcel.readString(), parcel.createByteArray(), parcel.readInt(), ICancelSessionCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 17: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.loadBoundProfilePackage(parcel.readString(), parcel.readString(), parcel.createByteArray(), ILoadBoundProfilePackageCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 16: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.prepareDownload(parcel.readString(), parcel.readString(), parcel.createByteArray(), parcel.createByteArray(), parcel.createByteArray(), parcel.createByteArray(), IPrepareDownloadCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 15: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.authenticateServer(parcel.readString(), parcel.readString(), parcel.readString(), parcel.createByteArray(), parcel.createByteArray(), parcel.createByteArray(), parcel.createByteArray(), IAuthenticateServerCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 14: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getEuiccInfo2(parcel.readString(), parcel.readString(), IGetEuiccInfo2Callback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 13: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getEuiccInfo1(parcel.readString(), parcel.readString(), IGetEuiccInfo1Callback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 12: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getEuiccChallenge(parcel.readString(), parcel.readString(), IGetEuiccChallengeCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 11: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getRulesAuthTable(parcel.readString(), parcel.readString(), IGetRulesAuthTableCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setDefaultSmdpAddress(parcel.readString(), parcel.readString(), parcel.readString(), ISetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getSmdsAddress(parcel.readString(), parcel.readString(), IGetSmdsAddressCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getDefaultSmdpAddress(parcel.readString(), parcel.readString(), IGetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.resetMemory(parcel.readString(), parcel.readString(), parcel.readInt(), IResetMemoryCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.deleteProfile(parcel.readString(), parcel.readString(), parcel.readString(), IDeleteProfileCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setNickname(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), ISetNicknameCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        String string2 = parcel.readString();
                        String string3 = parcel.readString();
                        object = parcel.readString();
                        boolean bl = parcel.readInt() != 0;
                        this.switchToProfile(string2, string3, (String)object, bl, ISwitchToProfileCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readString();
                        String string4 = parcel.readString();
                        String string5 = parcel.readString();
                        boolean bl = parcel.readInt() != 0;
                        this.disableProfile((String)object, string4, string5, bl, IDisableProfileCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.getProfile(parcel.readString(), parcel.readString(), parcel.readString(), IGetProfileCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.getAllProfiles(parcel.readString(), parcel.readString(), IGetAllProfilesCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IEuiccCardController {
            public static IEuiccCardController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
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
            public void authenticateServer(String string2, String string3, String string4, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, IAuthenticateServerCallback iAuthenticateServerCallback) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeByteArray(arrby2);
                        parcel.writeByteArray(arrby3);
                        parcel.writeByteArray(arrby4);
                        IBinder iBinder = iAuthenticateServerCallback != null ? iAuthenticateServerCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().authenticateServer(string2, string3, string4, arrby, arrby2, arrby3, arrby4, iAuthenticateServerCallback);
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
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void cancelSession(String string2, String string3, byte[] arrby, int n, ICancelSessionCallback iCancelSessionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    IBinder iBinder = iCancelSessionCallback != null ? iCancelSessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(18, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().cancelSession(string2, string3, arrby, n, iCancelSessionCallback);
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
            public void deleteProfile(String string2, String string3, String string4, IDeleteProfileCallback iDeleteProfileCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    IBinder iBinder = iDeleteProfileCallback != null ? iDeleteProfileCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().deleteProfile(string2, string3, string4, iDeleteProfileCallback);
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
            public void disableProfile(String string2, String string3, String string4, boolean bl, IDisableProfileCallback iDisableProfileCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    IBinder iBinder = iDisableProfileCallback != null ? iDisableProfileCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().disableProfile(string2, string3, string4, bl, iDisableProfileCallback);
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
            public void getAllProfiles(String string2, String string3, IGetAllProfilesCallback iGetAllProfilesCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iGetAllProfilesCallback != null ? iGetAllProfilesCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAllProfiles(string2, string3, iGetAllProfilesCallback);
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
            public void getDefaultSmdpAddress(String string2, String string3, IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iGetDefaultSmdpAddressCallback != null ? iGetDefaultSmdpAddressCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getDefaultSmdpAddress(string2, string3, iGetDefaultSmdpAddressCallback);
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
            public void getEuiccChallenge(String string2, String string3, IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iGetEuiccChallengeCallback != null ? iGetEuiccChallengeCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEuiccChallenge(string2, string3, iGetEuiccChallengeCallback);
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
            public void getEuiccInfo1(String string2, String string3, IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iGetEuiccInfo1Callback != null ? iGetEuiccInfo1Callback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(13, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEuiccInfo1(string2, string3, iGetEuiccInfo1Callback);
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
            public void getEuiccInfo2(String string2, String string3, IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iGetEuiccInfo2Callback != null ? iGetEuiccInfo2Callback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(14, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEuiccInfo2(string2, string3, iGetEuiccInfo2Callback);
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
            public void getProfile(String string2, String string3, String string4, IGetProfileCallback iGetProfileCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    IBinder iBinder = iGetProfileCallback != null ? iGetProfileCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getProfile(string2, string3, string4, iGetProfileCallback);
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
            public void getRulesAuthTable(String string2, String string3, IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iGetRulesAuthTableCallback != null ? iGetRulesAuthTableCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getRulesAuthTable(string2, string3, iGetRulesAuthTableCallback);
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
            public void getSmdsAddress(String string2, String string3, IGetSmdsAddressCallback iGetSmdsAddressCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iGetSmdsAddressCallback != null ? iGetSmdsAddressCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getSmdsAddress(string2, string3, iGetSmdsAddressCallback);
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
            public void listNotifications(String string2, String string3, int n, IListNotificationsCallback iListNotificationsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    IBinder iBinder = iListNotificationsCallback != null ? iListNotificationsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(19, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().listNotifications(string2, string3, n, iListNotificationsCallback);
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
            public void loadBoundProfilePackage(String string2, String string3, byte[] arrby, ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeByteArray(arrby);
                    IBinder iBinder = iLoadBoundProfilePackageCallback != null ? iLoadBoundProfilePackageCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(17, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().loadBoundProfilePackage(string2, string3, arrby, iLoadBoundProfilePackageCallback);
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
            public void prepareDownload(String string2, String string3, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, IPrepareDownloadCallback iPrepareDownloadCallback) throws RemoteException {
                Parcel parcel;
                void var1_9;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeByteArray(arrby2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeByteArray(arrby3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeByteArray(arrby4);
                        IBinder iBinder = iPrepareDownloadCallback != null ? iPrepareDownloadCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().prepareDownload(string2, string3, arrby, arrby2, arrby3, arrby4, iPrepareDownloadCallback);
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
                throw var1_9;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeNotificationFromList(String string2, String string3, int n, IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    IBinder iBinder = iRemoveNotificationFromListCallback != null ? iRemoveNotificationFromListCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(22, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().removeNotificationFromList(string2, string3, n, iRemoveNotificationFromListCallback);
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
            public void resetMemory(String string2, String string3, int n, IResetMemoryCallback iResetMemoryCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    IBinder iBinder = iResetMemoryCallback != null ? iResetMemoryCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().resetMemory(string2, string3, n, iResetMemoryCallback);
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
            public void retrieveNotification(String string2, String string3, int n, IRetrieveNotificationCallback iRetrieveNotificationCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    IBinder iBinder = iRetrieveNotificationCallback != null ? iRetrieveNotificationCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(21, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().retrieveNotification(string2, string3, n, iRetrieveNotificationCallback);
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
            public void retrieveNotificationList(String string2, String string3, int n, IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    IBinder iBinder = iRetrieveNotificationListCallback != null ? iRetrieveNotificationListCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(20, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().retrieveNotificationList(string2, string3, n, iRetrieveNotificationListCallback);
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
            public void setDefaultSmdpAddress(String string2, String string3, String string4, ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    IBinder iBinder = iSetDefaultSmdpAddressCallback != null ? iSetDefaultSmdpAddressCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(10, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setDefaultSmdpAddress(string2, string3, string4, iSetDefaultSmdpAddressCallback);
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
            public void setNickname(String string2, String string3, String string4, String string5, ISetNicknameCallback iSetNicknameCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    parcel.writeString(string5);
                    IBinder iBinder = iSetNicknameCallback != null ? iSetNicknameCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setNickname(string2, string3, string4, string5, iSetNicknameCallback);
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
            public void switchToProfile(String string2, String string3, String string4, boolean bl, ISwitchToProfileCallback iSwitchToProfileCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    IBinder iBinder = iSwitchToProfileCallback != null ? iSwitchToProfileCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().switchToProfile(string2, string3, string4, bl, iSwitchToProfileCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

