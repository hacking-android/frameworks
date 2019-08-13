/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.AppOpsManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import com.android.internal.app.IAppOpsActiveCallback;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsNotedCallback;
import java.util.ArrayList;
import java.util.List;

public interface IAppOpsService
extends IInterface {
    public void addHistoricalOps(AppOpsManager.HistoricalOps var1) throws RemoteException;

    public int checkAudioOperation(int var1, int var2, int var3, String var4) throws RemoteException;

    public int checkOperation(int var1, int var2, String var3) throws RemoteException;

    public int checkOperationRaw(int var1, int var2, String var3) throws RemoteException;

    public int checkPackage(int var1, String var2) throws RemoteException;

    public void clearHistory() throws RemoteException;

    @UnsupportedAppUsage
    public void finishOperation(IBinder var1, int var2, int var3, String var4) throws RemoteException;

    public void getHistoricalOps(int var1, String var2, List<String> var3, long var4, long var6, int var8, RemoteCallback var9) throws RemoteException;

    public void getHistoricalOpsFromDiskRaw(int var1, String var2, List<String> var3, long var4, long var6, int var8, RemoteCallback var9) throws RemoteException;

    @UnsupportedAppUsage
    public List<AppOpsManager.PackageOps> getOpsForPackage(int var1, String var2, int[] var3) throws RemoteException;

    @UnsupportedAppUsage
    public List<AppOpsManager.PackageOps> getPackagesForOps(int[] var1) throws RemoteException;

    public IBinder getToken(IBinder var1) throws RemoteException;

    public List<AppOpsManager.PackageOps> getUidOps(int var1, int[] var2) throws RemoteException;

    public boolean isOperationActive(int var1, int var2, String var3) throws RemoteException;

    public int noteOperation(int var1, int var2, String var3) throws RemoteException;

    public int noteProxyOperation(int var1, int var2, String var3, int var4, String var5) throws RemoteException;

    public void offsetHistory(long var1) throws RemoteException;

    public int permissionToOpCode(String var1) throws RemoteException;

    public void reloadNonHistoricalState() throws RemoteException;

    public void removeUser(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void resetAllModes(int var1, String var2) throws RemoteException;

    public void resetHistoryParameters() throws RemoteException;

    public void setAudioRestriction(int var1, int var2, int var3, int var4, String[] var5) throws RemoteException;

    public void setHistoryParameters(int var1, long var2, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void setMode(int var1, int var2, String var3, int var4) throws RemoteException;

    public void setUidMode(int var1, int var2, int var3) throws RemoteException;

    public void setUserRestriction(int var1, boolean var2, IBinder var3, int var4, String[] var5) throws RemoteException;

    public void setUserRestrictions(Bundle var1, IBinder var2, int var3) throws RemoteException;

    public int startOperation(IBinder var1, int var2, int var3, String var4, boolean var5) throws RemoteException;

    public void startWatchingActive(int[] var1, IAppOpsActiveCallback var2) throws RemoteException;

    public void startWatchingMode(int var1, String var2, IAppOpsCallback var3) throws RemoteException;

    public void startWatchingModeWithFlags(int var1, String var2, int var3, IAppOpsCallback var4) throws RemoteException;

    public void startWatchingNoted(int[] var1, IAppOpsNotedCallback var2) throws RemoteException;

    public void stopWatchingActive(IAppOpsActiveCallback var1) throws RemoteException;

    public void stopWatchingMode(IAppOpsCallback var1) throws RemoteException;

    public void stopWatchingNoted(IAppOpsNotedCallback var1) throws RemoteException;

    public static class Default
    implements IAppOpsService {
        @Override
        public void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int checkAudioOperation(int n, int n2, int n3, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int checkOperation(int n, int n2, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int checkOperationRaw(int n, int n2, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int checkPackage(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void clearHistory() throws RemoteException {
        }

        @Override
        public void finishOperation(IBinder iBinder, int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void getHistoricalOps(int n, String string2, List<String> list, long l, long l2, int n2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void getHistoricalOpsFromDiskRaw(int n, String string2, List<String> list, long l, long l2, int n2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public List<AppOpsManager.PackageOps> getOpsForPackage(int n, String string2, int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public List<AppOpsManager.PackageOps> getPackagesForOps(int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public IBinder getToken(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public List<AppOpsManager.PackageOps> getUidOps(int n, int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public boolean isOperationActive(int n, int n2, String string2) throws RemoteException {
            return false;
        }

        @Override
        public int noteOperation(int n, int n2, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int noteProxyOperation(int n, int n2, String string2, int n3, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public void offsetHistory(long l) throws RemoteException {
        }

        @Override
        public int permissionToOpCode(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void reloadNonHistoricalState() throws RemoteException {
        }

        @Override
        public void removeUser(int n) throws RemoteException {
        }

        @Override
        public void resetAllModes(int n, String string2) throws RemoteException {
        }

        @Override
        public void resetHistoryParameters() throws RemoteException {
        }

        @Override
        public void setAudioRestriction(int n, int n2, int n3, int n4, String[] arrstring) throws RemoteException {
        }

        @Override
        public void setHistoryParameters(int n, long l, int n2) throws RemoteException {
        }

        @Override
        public void setMode(int n, int n2, String string2, int n3) throws RemoteException {
        }

        @Override
        public void setUidMode(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void setUserRestriction(int n, boolean bl, IBinder iBinder, int n2, String[] arrstring) throws RemoteException {
        }

        @Override
        public void setUserRestrictions(Bundle bundle, IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public int startOperation(IBinder iBinder, int n, int n2, String string2, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public void startWatchingActive(int[] arrn, IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
        }

        @Override
        public void startWatchingMode(int n, String string2, IAppOpsCallback iAppOpsCallback) throws RemoteException {
        }

        @Override
        public void startWatchingModeWithFlags(int n, String string2, int n2, IAppOpsCallback iAppOpsCallback) throws RemoteException {
        }

        @Override
        public void startWatchingNoted(int[] arrn, IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
        }

        @Override
        public void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
        }

        @Override
        public void stopWatchingMode(IAppOpsCallback iAppOpsCallback) throws RemoteException {
        }

        @Override
        public void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAppOpsService {
        private static final String DESCRIPTOR = "com.android.internal.app.IAppOpsService";
        static final int TRANSACTION_addHistoricalOps = 18;
        static final int TRANSACTION_checkAudioOperation = 9;
        static final int TRANSACTION_checkOperation = 1;
        static final int TRANSACTION_checkOperationRaw = 35;
        static final int TRANSACTION_checkPackage = 11;
        static final int TRANSACTION_clearHistory = 20;
        static final int TRANSACTION_finishOperation = 4;
        static final int TRANSACTION_getHistoricalOps = 14;
        static final int TRANSACTION_getHistoricalOpsFromDiskRaw = 15;
        static final int TRANSACTION_getOpsForPackage = 13;
        static final int TRANSACTION_getPackagesForOps = 12;
        static final int TRANSACTION_getToken = 7;
        static final int TRANSACTION_getUidOps = 21;
        static final int TRANSACTION_isOperationActive = 31;
        static final int TRANSACTION_noteOperation = 2;
        static final int TRANSACTION_noteProxyOperation = 10;
        static final int TRANSACTION_offsetHistory = 16;
        static final int TRANSACTION_permissionToOpCode = 8;
        static final int TRANSACTION_reloadNonHistoricalState = 36;
        static final int TRANSACTION_removeUser = 28;
        static final int TRANSACTION_resetAllModes = 24;
        static final int TRANSACTION_resetHistoryParameters = 19;
        static final int TRANSACTION_setAudioRestriction = 25;
        static final int TRANSACTION_setHistoryParameters = 17;
        static final int TRANSACTION_setMode = 23;
        static final int TRANSACTION_setUidMode = 22;
        static final int TRANSACTION_setUserRestriction = 27;
        static final int TRANSACTION_setUserRestrictions = 26;
        static final int TRANSACTION_startOperation = 3;
        static final int TRANSACTION_startWatchingActive = 29;
        static final int TRANSACTION_startWatchingMode = 5;
        static final int TRANSACTION_startWatchingModeWithFlags = 32;
        static final int TRANSACTION_startWatchingNoted = 33;
        static final int TRANSACTION_stopWatchingActive = 30;
        static final int TRANSACTION_stopWatchingMode = 6;
        static final int TRANSACTION_stopWatchingNoted = 34;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAppOpsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAppOpsService) {
                return (IAppOpsService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAppOpsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 36: {
                    return "reloadNonHistoricalState";
                }
                case 35: {
                    return "checkOperationRaw";
                }
                case 34: {
                    return "stopWatchingNoted";
                }
                case 33: {
                    return "startWatchingNoted";
                }
                case 32: {
                    return "startWatchingModeWithFlags";
                }
                case 31: {
                    return "isOperationActive";
                }
                case 30: {
                    return "stopWatchingActive";
                }
                case 29: {
                    return "startWatchingActive";
                }
                case 28: {
                    return "removeUser";
                }
                case 27: {
                    return "setUserRestriction";
                }
                case 26: {
                    return "setUserRestrictions";
                }
                case 25: {
                    return "setAudioRestriction";
                }
                case 24: {
                    return "resetAllModes";
                }
                case 23: {
                    return "setMode";
                }
                case 22: {
                    return "setUidMode";
                }
                case 21: {
                    return "getUidOps";
                }
                case 20: {
                    return "clearHistory";
                }
                case 19: {
                    return "resetHistoryParameters";
                }
                case 18: {
                    return "addHistoricalOps";
                }
                case 17: {
                    return "setHistoryParameters";
                }
                case 16: {
                    return "offsetHistory";
                }
                case 15: {
                    return "getHistoricalOpsFromDiskRaw";
                }
                case 14: {
                    return "getHistoricalOps";
                }
                case 13: {
                    return "getOpsForPackage";
                }
                case 12: {
                    return "getPackagesForOps";
                }
                case 11: {
                    return "checkPackage";
                }
                case 10: {
                    return "noteProxyOperation";
                }
                case 9: {
                    return "checkAudioOperation";
                }
                case 8: {
                    return "permissionToOpCode";
                }
                case 7: {
                    return "getToken";
                }
                case 6: {
                    return "stopWatchingMode";
                }
                case 5: {
                    return "startWatchingMode";
                }
                case 4: {
                    return "finishOperation";
                }
                case 3: {
                    return "startOperation";
                }
                case 2: {
                    return "noteOperation";
                }
                case 1: 
            }
            return "checkOperation";
        }

        public static boolean setDefaultImpl(IAppOpsService iAppOpsService) {
            if (Proxy.sDefaultImpl == null && iAppOpsService != null) {
                Proxy.sDefaultImpl = iAppOpsService;
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
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reloadNonHistoricalState();
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkOperationRaw(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopWatchingNoted(IAppOpsNotedCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startWatchingNoted(((Parcel)object).createIntArray(), IAppOpsNotedCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startWatchingModeWithFlags(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), IAppOpsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isOperationActive(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopWatchingActive(IAppOpsActiveCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startWatchingActive(((Parcel)object).createIntArray(), IAppOpsActiveCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.setUserRestriction(n, bl, ((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUserRestrictions(bundle, ((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAudioRestriction(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetAllModes(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setMode(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setUidMode(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUidOps(((Parcel)object).readInt(), ((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearHistory();
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetHistoryParameters();
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? AppOpsManager.HistoricalOps.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addHistoricalOps((AppOpsManager.HistoricalOps)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setHistoryParameters(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.offsetHistory(((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        ArrayList<String> arrayList = ((Parcel)object).createStringArrayList();
                        long l = ((Parcel)object).readLong();
                        long l2 = ((Parcel)object).readLong();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getHistoricalOpsFromDiskRaw(n, string2, arrayList, l, l2, n2, (RemoteCallback)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string3 = ((Parcel)object).readString();
                        ArrayList<String> arrayList = ((Parcel)object).createStringArrayList();
                        long l = ((Parcel)object).readLong();
                        long l3 = ((Parcel)object).readLong();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getHistoricalOps(n, string3, arrayList, l, l3, n2, (RemoteCallback)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getOpsForPackage(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackagesForOps(((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkPackage(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.noteProxyOperation(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkAudioOperation(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.permissionToOpCode(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getToken(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopWatchingMode(IAppOpsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startWatchingMode(((Parcel)object).readInt(), ((Parcel)object).readString(), IAppOpsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishOperation(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        String string4 = ((Parcel)object).readString();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        n = this.startOperation(iBinder, n2, n, string4, bl);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.noteOperation(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.checkOperation(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAppOpsService {
            public static IAppOpsService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (historicalOps != null) {
                        parcel.writeInt(1);
                        historicalOps.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addHistoricalOps(historicalOps);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int checkAudioOperation(int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkAudioOperation(n, n2, n3, string2);
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
            public int checkOperation(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkOperation(n, n2, string2);
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
            public int checkOperationRaw(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkOperationRaw(n, n2, string2);
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
            public int checkPackage(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkPackage(n, string2);
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
            public void clearHistory() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearHistory();
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
            public void finishOperation(IBinder iBinder, int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishOperation(iBinder, n, n2, string2);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void getHistoricalOps(int n, String string2, List<String> list, long l, long l2, int n2, RemoteCallback remoteCallback) throws RemoteException {
                void var2_6;
                Parcel parcel2;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeStringList(list);
                        parcel.writeLong(l);
                        parcel.writeLong(l2);
                        parcel.writeInt(n2);
                        if (remoteCallback != null) {
                            parcel.writeInt(1);
                            remoteCallback.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().getHistoricalOps(n, string2, list, l, l2, n2, remoteCallback);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_6;
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
            public void getHistoricalOpsFromDiskRaw(int n, String string2, List<String> list, long l, long l2, int n2, RemoteCallback remoteCallback) throws RemoteException {
                void var2_6;
                Parcel parcel2;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeStringList(list);
                        parcel.writeLong(l);
                        parcel.writeLong(l2);
                        parcel.writeInt(n2);
                        if (remoteCallback != null) {
                            parcel.writeInt(1);
                            remoteCallback.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().getHistoricalOpsFromDiskRaw(n, string2, list, l, l2, n2, remoteCallback);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_6;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public List<AppOpsManager.PackageOps> getOpsForPackage(int n, String list, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)((Object)list));
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getOpsForPackage(n, (String)((Object)list), arrn);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                    return list;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AppOpsManager.PackageOps> getPackagesForOps(int[] object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])object);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPackagesForOps((int[])object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder getToken(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        iBinder = Stub.getDefaultImpl().getToken(iBinder);
                        return iBinder;
                    }
                    parcel2.readException();
                    iBinder = parcel2.readStrongBinder();
                    return iBinder;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AppOpsManager.PackageOps> getUidOps(int n, int[] object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeIntArray((int[])object);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getUidOps(n, (int[])object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isOperationActive(int n, int n2, String string2) throws RemoteException {
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
                        parcel.writeInt(n2);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(31, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isOperationActive(n, n2, string2);
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

            @Override
            public int noteOperation(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().noteOperation(n, n2, string2);
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
            public int noteProxyOperation(int n, int n2, String string2, int n3, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeInt(n3);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().noteProxyOperation(n, n2, string2, n3, string3);
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
            public void offsetHistory(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().offsetHistory(l);
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
            public int permissionToOpCode(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().permissionToOpCode(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void reloadNonHistoricalState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reloadNonHistoricalState();
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
            public void removeUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeUser(n);
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
            public void resetAllModes(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetAllModes(n, string2);
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
            public void resetHistoryParameters() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetHistoryParameters();
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
            public void setAudioRestriction(int n, int n2, int n3, int n4, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAudioRestriction(n, n2, n3, n4, arrstring);
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
            public void setHistoryParameters(int n, long l, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHistoryParameters(n, l, n2);
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
            public void setMode(int n, int n2, String string2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMode(n, n2, string2, n3);
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
            public void setUidMode(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidMode(n, n2, n3);
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
            public void setUserRestriction(int n, boolean bl, IBinder iBinder, int n2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserRestriction(n, bl, iBinder, n2, arrstring);
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
            public void setUserRestrictions(Bundle bundle, IBinder iBinder, int n) throws RemoteException {
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
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserRestrictions(bundle, iBinder, n);
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
            public int startOperation(IBinder iBinder, int n, int n2, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeString(string2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().startOperation(iBinder, n, n2, string2, bl);
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
            public void startWatchingActive(int[] arrn, IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    IBinder iBinder = iAppOpsActiveCallback != null ? iAppOpsActiveCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWatchingActive(arrn, iAppOpsActiveCallback);
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
            public void startWatchingMode(int n, String string2, IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    IBinder iBinder = iAppOpsCallback != null ? iAppOpsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWatchingMode(n, string2, iAppOpsCallback);
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
            public void startWatchingModeWithFlags(int n, String string2, int n2, IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    IBinder iBinder = iAppOpsCallback != null ? iAppOpsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWatchingModeWithFlags(n, string2, n2, iAppOpsCallback);
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
            public void startWatchingNoted(int[] arrn, IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    IBinder iBinder = iAppOpsNotedCallback != null ? iAppOpsNotedCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWatchingNoted(arrn, iAppOpsNotedCallback);
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
            public void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAppOpsActiveCallback != null ? iAppOpsActiveCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopWatchingActive(iAppOpsActiveCallback);
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
            public void stopWatchingMode(IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAppOpsCallback != null ? iAppOpsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopWatchingMode(iAppOpsCallback);
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
            public void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAppOpsNotedCallback != null ? iAppOpsNotedCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopWatchingNoted(iAppOpsNotedCallback);
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

