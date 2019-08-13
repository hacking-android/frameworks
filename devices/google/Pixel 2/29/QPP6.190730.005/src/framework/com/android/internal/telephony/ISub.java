/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.SubscriptionInfo;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import java.util.ArrayList;
import java.util.List;

public interface ISub
extends IInterface {
    public int addSubInfo(String var1, String var2, int var3, int var4) throws RemoteException;

    public int addSubInfoRecord(String var1, int var2) throws RemoteException;

    public void addSubscriptionsIntoGroup(int[] var1, ParcelUuid var2, String var3) throws RemoteException;

    public int clearSubInfo() throws RemoteException;

    public ParcelUuid createSubscriptionGroup(int[] var1, String var2) throws RemoteException;

    public List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String var1) throws RemoteException;

    public int[] getActiveSubIdList(boolean var1) throws RemoteException;

    public int getActiveSubInfoCount(String var1) throws RemoteException;

    public int getActiveSubInfoCountMax() throws RemoteException;

    public SubscriptionInfo getActiveSubscriptionInfo(int var1, String var2) throws RemoteException;

    public SubscriptionInfo getActiveSubscriptionInfoForIccId(String var1, String var2) throws RemoteException;

    public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int var1, String var2) throws RemoteException;

    public List<SubscriptionInfo> getActiveSubscriptionInfoList(String var1) throws RemoteException;

    public int getAllSubInfoCount(String var1) throws RemoteException;

    public List<SubscriptionInfo> getAllSubInfoList(String var1) throws RemoteException;

    public List<SubscriptionInfo> getAvailableSubscriptionInfoList(String var1) throws RemoteException;

    public int getDefaultDataSubId() throws RemoteException;

    public int getDefaultSmsSubId() throws RemoteException;

    public int getDefaultSubId() throws RemoteException;

    public int getDefaultVoiceSubId() throws RemoteException;

    public int getEnabledSubscriptionId(int var1) throws RemoteException;

    public List<SubscriptionInfo> getOpportunisticSubscriptions(String var1) throws RemoteException;

    public int getPhoneId(int var1) throws RemoteException;

    public int getPreferredDataSubscriptionId() throws RemoteException;

    public int getSimStateForSlotIndex(int var1) throws RemoteException;

    public int getSlotIndex(int var1) throws RemoteException;

    public int[] getSubId(int var1) throws RemoteException;

    public String getSubscriptionProperty(int var1, String var2, String var3) throws RemoteException;

    public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid var1, String var2) throws RemoteException;

    public boolean isActiveSubId(int var1, String var2) throws RemoteException;

    public boolean isSubscriptionEnabled(int var1) throws RemoteException;

    public int removeSubInfo(String var1, int var2) throws RemoteException;

    public void removeSubscriptionsFromGroup(int[] var1, ParcelUuid var2, String var3) throws RemoteException;

    public void requestEmbeddedSubscriptionInfoListRefresh(int var1) throws RemoteException;

    public boolean setAlwaysAllowMmsData(int var1, boolean var2) throws RemoteException;

    public int setDataRoaming(int var1, int var2) throws RemoteException;

    public void setDefaultDataSubId(int var1) throws RemoteException;

    public void setDefaultSmsSubId(int var1) throws RemoteException;

    public void setDefaultVoiceSubId(int var1) throws RemoteException;

    public int setDisplayNameUsingSrc(String var1, int var2, int var3) throws RemoteException;

    public int setDisplayNumber(String var1, int var2) throws RemoteException;

    public int setIconTint(int var1, int var2) throws RemoteException;

    public int setOpportunistic(boolean var1, int var2, String var3) throws RemoteException;

    public void setPreferredDataSubscriptionId(int var1, boolean var2, ISetOpportunisticDataCallback var3) throws RemoteException;

    public boolean setSubscriptionEnabled(boolean var1, int var2) throws RemoteException;

    public int setSubscriptionProperty(int var1, String var2, String var3) throws RemoteException;

    public static class Default
    implements ISub {
        @Override
        public int addSubInfo(String string2, String string3, int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int addSubInfoRecord(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public void addSubscriptionsIntoGroup(int[] arrn, ParcelUuid parcelUuid, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int clearSubInfo() throws RemoteException {
            return 0;
        }

        @Override
        public ParcelUuid createSubscriptionGroup(int[] arrn, String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int[] getActiveSubIdList(boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public int getActiveSubInfoCount(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getActiveSubInfoCountMax() throws RemoteException {
            return 0;
        }

        @Override
        public SubscriptionInfo getActiveSubscriptionInfo(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public SubscriptionInfo getActiveSubscriptionInfoForIccId(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<SubscriptionInfo> getActiveSubscriptionInfoList(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getAllSubInfoCount(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public List<SubscriptionInfo> getAllSubInfoList(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<SubscriptionInfo> getAvailableSubscriptionInfoList(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getDefaultDataSubId() throws RemoteException {
            return 0;
        }

        @Override
        public int getDefaultSmsSubId() throws RemoteException {
            return 0;
        }

        @Override
        public int getDefaultSubId() throws RemoteException {
            return 0;
        }

        @Override
        public int getDefaultVoiceSubId() throws RemoteException {
            return 0;
        }

        @Override
        public int getEnabledSubscriptionId(int n) throws RemoteException {
            return 0;
        }

        @Override
        public List<SubscriptionInfo> getOpportunisticSubscriptions(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getPhoneId(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getPreferredDataSubscriptionId() throws RemoteException {
            return 0;
        }

        @Override
        public int getSimStateForSlotIndex(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getSlotIndex(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int[] getSubId(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getSubscriptionProperty(int n, String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid parcelUuid, String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean isActiveSubId(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSubscriptionEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public int removeSubInfo(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public void removeSubscriptionsFromGroup(int[] arrn, ParcelUuid parcelUuid, String string2) throws RemoteException {
        }

        @Override
        public void requestEmbeddedSubscriptionInfoListRefresh(int n) throws RemoteException {
        }

        @Override
        public boolean setAlwaysAllowMmsData(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public int setDataRoaming(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public void setDefaultDataSubId(int n) throws RemoteException {
        }

        @Override
        public void setDefaultSmsSubId(int n) throws RemoteException {
        }

        @Override
        public void setDefaultVoiceSubId(int n) throws RemoteException {
        }

        @Override
        public int setDisplayNameUsingSrc(String string2, int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int setDisplayNumber(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int setIconTint(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int setOpportunistic(boolean bl, int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void setPreferredDataSubscriptionId(int n, boolean bl, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws RemoteException {
        }

        @Override
        public boolean setSubscriptionEnabled(boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public int setSubscriptionProperty(int n, String string2, String string3) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISub {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ISub";
        static final int TRANSACTION_addSubInfo = 13;
        static final int TRANSACTION_addSubInfoRecord = 12;
        static final int TRANSACTION_addSubscriptionsIntoGroup = 25;
        static final int TRANSACTION_clearSubInfo = 30;
        static final int TRANSACTION_createSubscriptionGroup = 20;
        static final int TRANSACTION_getAccessibleSubscriptionInfoList = 10;
        static final int TRANSACTION_getActiveSubIdList = 38;
        static final int TRANSACTION_getActiveSubInfoCount = 7;
        static final int TRANSACTION_getActiveSubInfoCountMax = 8;
        static final int TRANSACTION_getActiveSubscriptionInfo = 3;
        static final int TRANSACTION_getActiveSubscriptionInfoForIccId = 4;
        static final int TRANSACTION_getActiveSubscriptionInfoForSimSlotIndex = 5;
        static final int TRANSACTION_getActiveSubscriptionInfoList = 6;
        static final int TRANSACTION_getAllSubInfoCount = 2;
        static final int TRANSACTION_getAllSubInfoList = 1;
        static final int TRANSACTION_getAvailableSubscriptionInfoList = 9;
        static final int TRANSACTION_getDefaultDataSubId = 32;
        static final int TRANSACTION_getDefaultSmsSubId = 36;
        static final int TRANSACTION_getDefaultSubId = 29;
        static final int TRANSACTION_getDefaultVoiceSubId = 34;
        static final int TRANSACTION_getEnabledSubscriptionId = 43;
        static final int TRANSACTION_getOpportunisticSubscriptions = 23;
        static final int TRANSACTION_getPhoneId = 31;
        static final int TRANSACTION_getPreferredDataSubscriptionId = 22;
        static final int TRANSACTION_getSimStateForSlotIndex = 44;
        static final int TRANSACTION_getSlotIndex = 27;
        static final int TRANSACTION_getSubId = 28;
        static final int TRANSACTION_getSubscriptionProperty = 40;
        static final int TRANSACTION_getSubscriptionsInGroup = 26;
        static final int TRANSACTION_isActiveSubId = 45;
        static final int TRANSACTION_isSubscriptionEnabled = 42;
        static final int TRANSACTION_removeSubInfo = 14;
        static final int TRANSACTION_removeSubscriptionsFromGroup = 24;
        static final int TRANSACTION_requestEmbeddedSubscriptionInfoListRefresh = 11;
        static final int TRANSACTION_setAlwaysAllowMmsData = 46;
        static final int TRANSACTION_setDataRoaming = 18;
        static final int TRANSACTION_setDefaultDataSubId = 33;
        static final int TRANSACTION_setDefaultSmsSubId = 37;
        static final int TRANSACTION_setDefaultVoiceSubId = 35;
        static final int TRANSACTION_setDisplayNameUsingSrc = 16;
        static final int TRANSACTION_setDisplayNumber = 17;
        static final int TRANSACTION_setIconTint = 15;
        static final int TRANSACTION_setOpportunistic = 19;
        static final int TRANSACTION_setPreferredDataSubscriptionId = 21;
        static final int TRANSACTION_setSubscriptionEnabled = 41;
        static final int TRANSACTION_setSubscriptionProperty = 39;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISub asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISub) {
                return (ISub)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISub getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 46: {
                    return "setAlwaysAllowMmsData";
                }
                case 45: {
                    return "isActiveSubId";
                }
                case 44: {
                    return "getSimStateForSlotIndex";
                }
                case 43: {
                    return "getEnabledSubscriptionId";
                }
                case 42: {
                    return "isSubscriptionEnabled";
                }
                case 41: {
                    return "setSubscriptionEnabled";
                }
                case 40: {
                    return "getSubscriptionProperty";
                }
                case 39: {
                    return "setSubscriptionProperty";
                }
                case 38: {
                    return "getActiveSubIdList";
                }
                case 37: {
                    return "setDefaultSmsSubId";
                }
                case 36: {
                    return "getDefaultSmsSubId";
                }
                case 35: {
                    return "setDefaultVoiceSubId";
                }
                case 34: {
                    return "getDefaultVoiceSubId";
                }
                case 33: {
                    return "setDefaultDataSubId";
                }
                case 32: {
                    return "getDefaultDataSubId";
                }
                case 31: {
                    return "getPhoneId";
                }
                case 30: {
                    return "clearSubInfo";
                }
                case 29: {
                    return "getDefaultSubId";
                }
                case 28: {
                    return "getSubId";
                }
                case 27: {
                    return "getSlotIndex";
                }
                case 26: {
                    return "getSubscriptionsInGroup";
                }
                case 25: {
                    return "addSubscriptionsIntoGroup";
                }
                case 24: {
                    return "removeSubscriptionsFromGroup";
                }
                case 23: {
                    return "getOpportunisticSubscriptions";
                }
                case 22: {
                    return "getPreferredDataSubscriptionId";
                }
                case 21: {
                    return "setPreferredDataSubscriptionId";
                }
                case 20: {
                    return "createSubscriptionGroup";
                }
                case 19: {
                    return "setOpportunistic";
                }
                case 18: {
                    return "setDataRoaming";
                }
                case 17: {
                    return "setDisplayNumber";
                }
                case 16: {
                    return "setDisplayNameUsingSrc";
                }
                case 15: {
                    return "setIconTint";
                }
                case 14: {
                    return "removeSubInfo";
                }
                case 13: {
                    return "addSubInfo";
                }
                case 12: {
                    return "addSubInfoRecord";
                }
                case 11: {
                    return "requestEmbeddedSubscriptionInfoListRefresh";
                }
                case 10: {
                    return "getAccessibleSubscriptionInfoList";
                }
                case 9: {
                    return "getAvailableSubscriptionInfoList";
                }
                case 8: {
                    return "getActiveSubInfoCountMax";
                }
                case 7: {
                    return "getActiveSubInfoCount";
                }
                case 6: {
                    return "getActiveSubscriptionInfoList";
                }
                case 5: {
                    return "getActiveSubscriptionInfoForSimSlotIndex";
                }
                case 4: {
                    return "getActiveSubscriptionInfoForIccId";
                }
                case 3: {
                    return "getActiveSubscriptionInfo";
                }
                case 2: {
                    return "getAllSubInfoCount";
                }
                case 1: 
            }
            return "getAllSubInfoList";
        }

        public static boolean setDefaultImpl(ISub iSub) {
            if (Proxy.sDefaultImpl == null && iSub != null) {
                Proxy.sDefaultImpl = iSub;
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
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        n = this.setAlwaysAllowMmsData(n, bl5) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isActiveSubId(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getSimStateForSlotIndex(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getEnabledSubscriptionId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSubscriptionEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        n = this.setSubscriptionEnabled(bl5, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSubscriptionProperty(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setSubscriptionProperty(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        object = this.getActiveSubIdList(bl5);
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDefaultSmsSubId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDefaultSmsSubId();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDefaultVoiceSubId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDefaultVoiceSubId();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDefaultDataSubId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDefaultDataSubId();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPhoneId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.clearSubInfo();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDefaultSubId();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSubId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getSlotIndex(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getSubscriptionsInGroup(parcelUuid, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int[] arrn = ((Parcel)object).createIntArray();
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addSubscriptionsIntoGroup(arrn, parcelUuid, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int[] arrn = ((Parcel)object).createIntArray();
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeSubscriptionsFromGroup(arrn, parcelUuid, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getOpportunisticSubscriptions(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPreferredDataSubscriptionId();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl5 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setPreferredDataSubscriptionId(n, bl5, ISetOpportunisticDataCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createSubscriptionGroup(((Parcel)object).createIntArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelUuid)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        n = this.setOpportunistic(bl5, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setDataRoaming(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setDisplayNumber(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setDisplayNameUsingSrc(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setIconTint(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeSubInfo(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addSubInfo(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addSubInfoRecord(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestEmbeddedSubscriptionInfoListRefresh(((Parcel)object).readInt());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccessibleSubscriptionInfoList(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAvailableSubscriptionInfoList(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getActiveSubInfoCountMax();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getActiveSubInfoCount(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveSubscriptionInfoList(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveSubscriptionInfoForSimSlotIndex(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SubscriptionInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveSubscriptionInfoForIccId(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SubscriptionInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveSubscriptionInfo(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SubscriptionInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getAllSubInfoCount(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getAllSubInfoList(((Parcel)object).readString());
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISub {
            public static ISub sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public int addSubInfo(String string2, String string3, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().addSubInfo(string2, string3, n, n2);
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
            public int addSubInfoRecord(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().addSubInfoRecord(string2, n);
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
            public void addSubscriptionsIntoGroup(int[] arrn, ParcelUuid parcelUuid, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSubscriptionsIntoGroup(arrn, parcelUuid, string2);
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
            public int clearSubInfo() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().clearSubInfo();
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
            public ParcelUuid createSubscriptionGroup(int[] object, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeIntArray((int[])object);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(20, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().createSubscriptionGroup((int[])object, string2);
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
                object = parcel.readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAccessibleSubscriptionInfoList((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(SubscriptionInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getActiveSubIdList(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getActiveSubIdList(bl);
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getActiveSubInfoCount(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getActiveSubInfoCount(string2);
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
            public int getActiveSubInfoCountMax() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getActiveSubInfoCountMax();
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
            public SubscriptionInfo getActiveSubscriptionInfo(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getActiveSubscriptionInfo(n, (String)object);
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
                object = parcel.readInt() != 0 ? SubscriptionInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public SubscriptionInfo getActiveSubscriptionInfoForIccId(String object, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getActiveSubscriptionInfoForIccId((String)object, string2);
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
                object = parcel.readInt() != 0 ? SubscriptionInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getActiveSubscriptionInfoForSimSlotIndex(n, (String)object);
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
                object = parcel.readInt() != 0 ? SubscriptionInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public List<SubscriptionInfo> getActiveSubscriptionInfoList(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getActiveSubscriptionInfoList((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(SubscriptionInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getAllSubInfoCount(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getAllSubInfoCount(string2);
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
            public List<SubscriptionInfo> getAllSubInfoList(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAllSubInfoList((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(SubscriptionInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<SubscriptionInfo> getAvailableSubscriptionInfoList(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAvailableSubscriptionInfoList((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(SubscriptionInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getDefaultDataSubId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDefaultDataSubId();
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
            public int getDefaultSmsSubId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDefaultSmsSubId();
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
            public int getDefaultSubId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDefaultSubId();
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
            public int getDefaultVoiceSubId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDefaultVoiceSubId();
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
            public int getEnabledSubscriptionId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getEnabledSubscriptionId(n);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public List<SubscriptionInfo> getOpportunisticSubscriptions(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getOpportunisticSubscriptions((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(SubscriptionInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getPhoneId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPhoneId(n);
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
            public int getPreferredDataSubscriptionId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPreferredDataSubscriptionId();
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
            public int getSimStateForSlotIndex(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getSimStateForSlotIndex(n);
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
            public int getSlotIndex(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getSlotIndex(n);
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
            public int[] getSubId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getSubId(n);
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSubscriptionProperty(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getSubscriptionProperty(n, string2, string3);
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
            public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid arrayList, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ParcelUuid)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getSubscriptionsInGroup((ParcelUuid)((Object)arrayList), string2);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(SubscriptionInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isActiveSubId(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(45, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isActiveSubId(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isSubscriptionEnabled(int n) throws RemoteException {
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
                    if (iBinder.transact(42, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSubscriptionEnabled(n);
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
            public int removeSubInfo(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().removeSubInfo(string2, n);
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
            public void removeSubscriptionsFromGroup(int[] arrn, ParcelUuid parcelUuid, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSubscriptionsFromGroup(arrn, parcelUuid, string2);
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
            public void requestEmbeddedSubscriptionInfoListRefresh(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestEmbeddedSubscriptionInfoListRefresh(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public boolean setAlwaysAllowMmsData(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(46, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setAlwaysAllowMmsData(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public int setDataRoaming(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setDataRoaming(n, n2);
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
            public void setDefaultDataSubId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultDataSubId(n);
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
            public void setDefaultSmsSubId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultSmsSubId(n);
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
            public void setDefaultVoiceSubId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultVoiceSubId(n);
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
            public int setDisplayNameUsingSrc(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setDisplayNameUsingSrc(string2, n, n2);
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
            public int setDisplayNumber(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setDisplayNumber(string2, n);
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
            public int setIconTint(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setIconTint(n, n2);
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
            public int setOpportunistic(boolean bl, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setOpportunistic(bl, n, string2);
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
            public void setPreferredDataSubscriptionId(int n, boolean bl, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    IBinder iBinder = iSetOpportunisticDataCallback != null ? iSetOpportunisticDataCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPreferredDataSubscriptionId(n, bl, iSetOpportunisticDataCallback);
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
            public boolean setSubscriptionEnabled(boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(41, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setSubscriptionEnabled(bl, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public int setSubscriptionProperty(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setSubscriptionProperty(n, string2, string3);
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
        }

    }

}

