/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.IFinancialSmsCallback;
import com.android.internal.telephony.SmsRawData;
import java.util.ArrayList;
import java.util.List;

public interface ISms
extends IInterface {
    public int checkSmsShortCodeDestination(int var1, String var2, String var3, String var4) throws RemoteException;

    public boolean copyMessageToIccEfForSubscriber(int var1, String var2, int var3, byte[] var4, byte[] var5) throws RemoteException;

    public String createAppSpecificSmsToken(int var1, String var2, PendingIntent var3) throws RemoteException;

    public String createAppSpecificSmsTokenWithPackageInfo(int var1, String var2, String var3, PendingIntent var4) throws RemoteException;

    public boolean disableCellBroadcastForSubscriber(int var1, int var2, int var3) throws RemoteException;

    public boolean disableCellBroadcastRangeForSubscriber(int var1, int var2, int var3, int var4) throws RemoteException;

    public boolean enableCellBroadcastForSubscriber(int var1, int var2, int var3) throws RemoteException;

    public boolean enableCellBroadcastRangeForSubscriber(int var1, int var2, int var3, int var4) throws RemoteException;

    public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int var1, String var2) throws RemoteException;

    public String getImsSmsFormatForSubscriber(int var1) throws RemoteException;

    public int getPreferredSmsSubscription() throws RemoteException;

    public int getPremiumSmsPermission(String var1) throws RemoteException;

    public int getPremiumSmsPermissionForSubscriber(int var1, String var2) throws RemoteException;

    public void getSmsMessagesForFinancialApp(int var1, String var2, Bundle var3, IFinancialSmsCallback var4) throws RemoteException;

    public void injectSmsPduForSubscriber(int var1, byte[] var2, String var3, PendingIntent var4) throws RemoteException;

    public boolean isImsSmsSupportedForSubscriber(int var1) throws RemoteException;

    public boolean isSMSPromptEnabled() throws RemoteException;

    public boolean isSmsSimPickActivityNeeded(int var1) throws RemoteException;

    public void sendDataForSubscriber(int var1, String var2, String var3, String var4, int var5, byte[] var6, PendingIntent var7, PendingIntent var8) throws RemoteException;

    public void sendDataForSubscriberWithSelfPermissions(int var1, String var2, String var3, String var4, int var5, byte[] var6, PendingIntent var7, PendingIntent var8) throws RemoteException;

    public void sendMultipartTextForSubscriber(int var1, String var2, String var3, String var4, List<String> var5, List<PendingIntent> var6, List<PendingIntent> var7, boolean var8) throws RemoteException;

    public void sendMultipartTextForSubscriberWithOptions(int var1, String var2, String var3, String var4, List<String> var5, List<PendingIntent> var6, List<PendingIntent> var7, boolean var8, int var9, boolean var10, int var11) throws RemoteException;

    public void sendStoredMultipartText(int var1, String var2, Uri var3, String var4, List<PendingIntent> var5, List<PendingIntent> var6) throws RemoteException;

    public void sendStoredText(int var1, String var2, Uri var3, String var4, PendingIntent var5, PendingIntent var6) throws RemoteException;

    public void sendTextForSubscriber(int var1, String var2, String var3, String var4, String var5, PendingIntent var6, PendingIntent var7, boolean var8) throws RemoteException;

    public void sendTextForSubscriberWithOptions(int var1, String var2, String var3, String var4, String var5, PendingIntent var6, PendingIntent var7, boolean var8, int var9, boolean var10, int var11) throws RemoteException;

    public void sendTextForSubscriberWithSelfPermissions(int var1, String var2, String var3, String var4, String var5, PendingIntent var6, PendingIntent var7, boolean var8) throws RemoteException;

    public void setPremiumSmsPermission(String var1, int var2) throws RemoteException;

    public void setPremiumSmsPermissionForSubscriber(int var1, String var2, int var3) throws RemoteException;

    public boolean updateMessageOnIccEfForSubscriber(int var1, String var2, int var3, int var4, byte[] var5) throws RemoteException;

    public static class Default
    implements ISms {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int checkSmsShortCodeDestination(int n, String string2, String string3, String string4) throws RemoteException {
            return 0;
        }

        @Override
        public boolean copyMessageToIccEfForSubscriber(int n, String string2, int n2, byte[] arrby, byte[] arrby2) throws RemoteException {
            return false;
        }

        @Override
        public String createAppSpecificSmsToken(int n, String string2, PendingIntent pendingIntent) throws RemoteException {
            return null;
        }

        @Override
        public String createAppSpecificSmsTokenWithPackageInfo(int n, String string2, String string3, PendingIntent pendingIntent) throws RemoteException {
            return null;
        }

        @Override
        public boolean disableCellBroadcastForSubscriber(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public boolean disableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) throws RemoteException {
            return false;
        }

        @Override
        public boolean enableCellBroadcastForSubscriber(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public boolean enableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) throws RemoteException {
            return false;
        }

        @Override
        public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getImsSmsFormatForSubscriber(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getPreferredSmsSubscription() throws RemoteException {
            return 0;
        }

        @Override
        public int getPremiumSmsPermission(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getPremiumSmsPermissionForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void getSmsMessagesForFinancialApp(int n, String string2, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) throws RemoteException {
        }

        @Override
        public void injectSmsPduForSubscriber(int n, byte[] arrby, String string2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public boolean isImsSmsSupportedForSubscriber(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSMSPromptEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isSmsSimPickActivityNeeded(int n) throws RemoteException {
            return false;
        }

        @Override
        public void sendDataForSubscriber(int n, String string2, String string3, String string4, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
        }

        @Override
        public void sendDataForSubscriberWithSelfPermissions(int n, String string2, String string3, String string4, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
        }

        @Override
        public void sendMultipartTextForSubscriber(int n, String string2, String string3, String string4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl) throws RemoteException {
        }

        @Override
        public void sendMultipartTextForSubscriberWithOptions(int n, String string2, String string3, String string4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl, int n2, boolean bl2, int n3) throws RemoteException {
        }

        @Override
        public void sendStoredMultipartText(int n, String string2, Uri uri, String string3, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException {
        }

        @Override
        public void sendStoredText(int n, String string2, Uri uri, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
        }

        @Override
        public void sendTextForSubscriber(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) throws RemoteException {
        }

        @Override
        public void sendTextForSubscriberWithOptions(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, int n2, boolean bl2, int n3) throws RemoteException {
        }

        @Override
        public void sendTextForSubscriberWithSelfPermissions(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) throws RemoteException {
        }

        @Override
        public void setPremiumSmsPermission(String string2, int n) throws RemoteException {
        }

        @Override
        public void setPremiumSmsPermissionForSubscriber(int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public boolean updateMessageOnIccEfForSubscriber(int n, String string2, int n2, int n3, byte[] arrby) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISms {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ISms";
        static final int TRANSACTION_checkSmsShortCodeDestination = 30;
        static final int TRANSACTION_copyMessageToIccEfForSubscriber = 3;
        static final int TRANSACTION_createAppSpecificSmsToken = 27;
        static final int TRANSACTION_createAppSpecificSmsTokenWithPackageInfo = 28;
        static final int TRANSACTION_disableCellBroadcastForSubscriber = 13;
        static final int TRANSACTION_disableCellBroadcastRangeForSubscriber = 15;
        static final int TRANSACTION_enableCellBroadcastForSubscriber = 12;
        static final int TRANSACTION_enableCellBroadcastRangeForSubscriber = 14;
        static final int TRANSACTION_getAllMessagesFromIccEfForSubscriber = 1;
        static final int TRANSACTION_getImsSmsFormatForSubscriber = 23;
        static final int TRANSACTION_getPreferredSmsSubscription = 22;
        static final int TRANSACTION_getPremiumSmsPermission = 16;
        static final int TRANSACTION_getPremiumSmsPermissionForSubscriber = 17;
        static final int TRANSACTION_getSmsMessagesForFinancialApp = 29;
        static final int TRANSACTION_injectSmsPduForSubscriber = 9;
        static final int TRANSACTION_isImsSmsSupportedForSubscriber = 20;
        static final int TRANSACTION_isSMSPromptEnabled = 24;
        static final int TRANSACTION_isSmsSimPickActivityNeeded = 21;
        static final int TRANSACTION_sendDataForSubscriber = 4;
        static final int TRANSACTION_sendDataForSubscriberWithSelfPermissions = 5;
        static final int TRANSACTION_sendMultipartTextForSubscriber = 10;
        static final int TRANSACTION_sendMultipartTextForSubscriberWithOptions = 11;
        static final int TRANSACTION_sendStoredMultipartText = 26;
        static final int TRANSACTION_sendStoredText = 25;
        static final int TRANSACTION_sendTextForSubscriber = 6;
        static final int TRANSACTION_sendTextForSubscriberWithOptions = 8;
        static final int TRANSACTION_sendTextForSubscriberWithSelfPermissions = 7;
        static final int TRANSACTION_setPremiumSmsPermission = 18;
        static final int TRANSACTION_setPremiumSmsPermissionForSubscriber = 19;
        static final int TRANSACTION_updateMessageOnIccEfForSubscriber = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISms asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISms) {
                return (ISms)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISms getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 30: {
                    return "checkSmsShortCodeDestination";
                }
                case 29: {
                    return "getSmsMessagesForFinancialApp";
                }
                case 28: {
                    return "createAppSpecificSmsTokenWithPackageInfo";
                }
                case 27: {
                    return "createAppSpecificSmsToken";
                }
                case 26: {
                    return "sendStoredMultipartText";
                }
                case 25: {
                    return "sendStoredText";
                }
                case 24: {
                    return "isSMSPromptEnabled";
                }
                case 23: {
                    return "getImsSmsFormatForSubscriber";
                }
                case 22: {
                    return "getPreferredSmsSubscription";
                }
                case 21: {
                    return "isSmsSimPickActivityNeeded";
                }
                case 20: {
                    return "isImsSmsSupportedForSubscriber";
                }
                case 19: {
                    return "setPremiumSmsPermissionForSubscriber";
                }
                case 18: {
                    return "setPremiumSmsPermission";
                }
                case 17: {
                    return "getPremiumSmsPermissionForSubscriber";
                }
                case 16: {
                    return "getPremiumSmsPermission";
                }
                case 15: {
                    return "disableCellBroadcastRangeForSubscriber";
                }
                case 14: {
                    return "enableCellBroadcastRangeForSubscriber";
                }
                case 13: {
                    return "disableCellBroadcastForSubscriber";
                }
                case 12: {
                    return "enableCellBroadcastForSubscriber";
                }
                case 11: {
                    return "sendMultipartTextForSubscriberWithOptions";
                }
                case 10: {
                    return "sendMultipartTextForSubscriber";
                }
                case 9: {
                    return "injectSmsPduForSubscriber";
                }
                case 8: {
                    return "sendTextForSubscriberWithOptions";
                }
                case 7: {
                    return "sendTextForSubscriberWithSelfPermissions";
                }
                case 6: {
                    return "sendTextForSubscriber";
                }
                case 5: {
                    return "sendDataForSubscriberWithSelfPermissions";
                }
                case 4: {
                    return "sendDataForSubscriber";
                }
                case 3: {
                    return "copyMessageToIccEfForSubscriber";
                }
                case 2: {
                    return "updateMessageOnIccEfForSubscriber";
                }
                case 1: 
            }
            return "getAllMessagesFromIccEfForSubscriber";
        }

        public static boolean setDefaultImpl(ISms iSms) {
            if (Proxy.sDefaultImpl == null && iSms != null) {
                Proxy.sDefaultImpl = iSms;
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
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkSmsShortCodeDestination(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getSmsMessagesForFinancialApp(n, string2, bundle, IFinancialSmsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string3 = ((Parcel)object).readString();
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.createAppSpecificSmsTokenWithPackageInfo(n, string3, string4, (PendingIntent)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string5 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.createAppSpecificSmsToken(n, string5, (PendingIntent)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string6 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendStoredMultipartText(n, string6, uri, ((Parcel)object).readString(), ((Parcel)object).createTypedArrayList(PendingIntent.CREATOR), ((Parcel)object).createTypedArrayList(PendingIntent.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string7 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        String string8 = ((Parcel)object).readString();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendStoredText(n, string7, uri, string8, pendingIntent, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSMSPromptEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getImsSmsFormatForSubscriber(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPreferredSmsSubscription();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSmsSimPickActivityNeeded(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isImsSmsSupportedForSubscriber(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPremiumSmsPermissionForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPremiumSmsPermission(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPremiumSmsPermissionForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPremiumSmsPermission(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.disableCellBroadcastRangeForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.enableCellBroadcastRangeForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.disableCellBroadcastForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.enableCellBroadcastForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string9 = ((Parcel)object).readString();
                        String string10 = ((Parcel)object).readString();
                        String string11 = ((Parcel)object).readString();
                        ArrayList<String> arrayList = ((Parcel)object).createStringArrayList();
                        ArrayList<PendingIntent> arrayList2 = ((Parcel)object).createTypedArrayList(PendingIntent.CREATOR);
                        ArrayList<PendingIntent> arrayList3 = ((Parcel)object).createTypedArrayList(PendingIntent.CREATOR);
                        boolean bl = ((Parcel)object).readInt() != 0;
                        n2 = ((Parcel)object).readInt();
                        boolean bl2 = ((Parcel)object).readInt() != 0;
                        this.sendMultipartTextForSubscriberWithOptions(n, string9, string10, string11, arrayList, arrayList2, arrayList3, bl, n2, bl2, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string12 = ((Parcel)object).readString();
                        String string13 = ((Parcel)object).readString();
                        String string14 = ((Parcel)object).readString();
                        ArrayList<String> arrayList = ((Parcel)object).createStringArrayList();
                        ArrayList<PendingIntent> arrayList4 = ((Parcel)object).createTypedArrayList(PendingIntent.CREATOR);
                        ArrayList<PendingIntent> arrayList5 = ((Parcel)object).createTypedArrayList(PendingIntent.CREATOR);
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.sendMultipartTextForSubscriber(n, string12, string13, string14, arrayList, arrayList4, arrayList5, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        byte[] arrby = ((Parcel)object).createByteArray();
                        String string15 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.injectSmsPduForSubscriber(n, arrby, string15, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string16 = ((Parcel)object).readString();
                        String string17 = ((Parcel)object).readString();
                        String string18 = ((Parcel)object).readString();
                        String string19 = ((Parcel)object).readString();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        PendingIntent pendingIntent2 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        n2 = ((Parcel)object).readInt();
                        boolean bl3 = ((Parcel)object).readInt() != 0;
                        this.sendTextForSubscriberWithOptions(n, string16, string17, string18, string19, pendingIntent, pendingIntent2, bl, n2, bl3, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string20 = ((Parcel)object).readString();
                        String string21 = ((Parcel)object).readString();
                        String string22 = ((Parcel)object).readString();
                        String string23 = ((Parcel)object).readString();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        PendingIntent pendingIntent3 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.sendTextForSubscriberWithSelfPermissions(n, string20, string21, string22, string23, pendingIntent, pendingIntent3, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string24 = ((Parcel)object).readString();
                        String string25 = ((Parcel)object).readString();
                        String string26 = ((Parcel)object).readString();
                        String string27 = ((Parcel)object).readString();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        PendingIntent pendingIntent4 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.sendTextForSubscriber(n, string24, string25, string26, string27, pendingIntent, pendingIntent4, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        String string28 = ((Parcel)object).readString();
                        String string29 = ((Parcel)object).readString();
                        String string30 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        byte[] arrby = ((Parcel)object).createByteArray();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendDataForSubscriberWithSelfPermissions(n2, string28, string29, string30, n, arrby, pendingIntent, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        String string31 = ((Parcel)object).readString();
                        String string32 = ((Parcel)object).readString();
                        String string33 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        byte[] arrby = ((Parcel)object).createByteArray();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendDataForSubscriber(n2, string31, string32, string33, n, arrby, pendingIntent, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.copyMessageToIccEfForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.updateMessageOnIccEfForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getAllMessagesFromIccEfForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISms {
            public static ISms sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int checkSmsShortCodeDestination(int n, String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkSmsShortCodeDestination(n, string2, string3, string4);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean copyMessageToIccEfForSubscriber(int n, String string2, int n2, byte[] arrby, byte[] arrby2) throws RemoteException {
                void var2_10;
                Parcel parcel;
                Parcel parcel2;
                block17 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeByteArray(arrby2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        IBinder iBinder = this.mRemote;
                        boolean bl = false;
                        if (!iBinder.transact(3, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().copyMessageToIccEfForSubscriber(n, string2, n2, arrby, arrby2);
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
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_10;
            }

            @Override
            public String createAppSpecificSmsToken(int n, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
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
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().createAppSpecificSmsToken(n, string2, pendingIntent);
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
            public String createAppSpecificSmsTokenWithPackageInfo(int n, String string2, String string3, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().createAppSpecificSmsTokenWithPackageInfo(n, string2, string3, pendingIntent);
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
            public boolean disableCellBroadcastForSubscriber(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disableCellBroadcastForSubscriber(n, n2, n3);
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
            public boolean disableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(15, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disableCellBroadcastRangeForSubscriber(n, n2, n3, n4);
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
            public boolean enableCellBroadcastForSubscriber(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(12, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().enableCellBroadcastForSubscriber(n, n2, n3);
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
            public boolean enableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(14, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().enableCellBroadcastRangeForSubscriber(n, n2, n3, n4);
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
            public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int n, String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAllMessagesFromIccEfForSubscriber(n, (String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(SmsRawData.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getImsSmsFormatForSubscriber(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getImsSmsFormatForSubscriber(n);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getPreferredSmsSubscription() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPreferredSmsSubscription();
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
            public int getPremiumSmsPermission(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPremiumSmsPermission(string2);
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
            public int getPremiumSmsPermissionForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPremiumSmsPermissionForSubscriber(n, string2);
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
            public void getSmsMessagesForFinancialApp(int n, String string2, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iFinancialSmsCallback != null ? iFinancialSmsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getSmsMessagesForFinancialApp(n, string2, bundle, iFinancialSmsCallback);
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
            public void injectSmsPduForSubscriber(int n, byte[] arrby, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    parcel.writeString(string2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().injectSmsPduForSubscriber(n, arrby, string2, pendingIntent);
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
            public boolean isImsSmsSupportedForSubscriber(int n) throws RemoteException {
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
                    if (iBinder.transact(20, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isImsSmsSupportedForSubscriber(n);
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
            public boolean isSMSPromptEnabled() throws RemoteException {
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
                    if (iBinder.transact(24, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSMSPromptEnabled();
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
            public boolean isSmsSimPickActivityNeeded(int n) throws RemoteException {
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
                    if (iBinder.transact(21, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSmsSimPickActivityNeeded(n);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void sendDataForSubscriber(int n, String string2, String string3, String string4, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                void var2_6;
                Parcel parcel;
                Parcel parcel2;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeString(string4);
                        parcel.writeInt(n2);
                        parcel.writeByteArray(arrby);
                        if (pendingIntent != null) {
                            parcel.writeInt(1);
                            pendingIntent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            parcel.writeInt(1);
                            pendingIntent2.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendDataForSubscriber(n, string2, string3, string4, n2, arrby, pendingIntent, pendingIntent2);
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
                    break block12;
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
            public void sendDataForSubscriberWithSelfPermissions(int n, String string2, String string3, String string4, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                void var2_6;
                Parcel parcel;
                Parcel parcel2;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeString(string4);
                        parcel.writeInt(n2);
                        parcel.writeByteArray(arrby);
                        if (pendingIntent != null) {
                            parcel.writeInt(1);
                            pendingIntent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            parcel.writeInt(1);
                            pendingIntent2.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendDataForSubscriberWithSelfPermissions(n, string2, string3, string4, n2, arrby, pendingIntent, pendingIntent2);
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
                    break block12;
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
            public void sendMultipartTextForSubscriber(int n, String string2, String string3, String string4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl) throws RemoteException {
                Parcel parcel;
                void var2_8;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string4);
                        parcel2.writeStringList(list);
                        parcel2.writeTypedList(list2);
                        parcel2.writeTypedList(list3);
                        int n2 = bl ? 1 : 0;
                        parcel2.writeInt(n2);
                        if (!this.mRemote.transact(10, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendMultipartTextForSubscriber(n, string2, string3, string4, list, list2, list3, bl);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_8;
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
            public void sendMultipartTextForSubscriberWithOptions(int n, String string2, String string3, String string4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl, int n2, boolean bl2, int n3) throws RemoteException {
                Parcel parcel2;
                void var2_5;
                Parcel parcel;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeString(string4);
                        parcel.writeStringList(list);
                        parcel.writeTypedList(list2);
                        parcel.writeTypedList(list3);
                        int n5 = 1;
                        int n4 = bl ? 1 : 0;
                        parcel.writeInt(n4);
                        parcel.writeInt(n2);
                        n4 = bl2 ? n5 : 0;
                        parcel.writeInt(n4);
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendMultipartTextForSubscriberWithOptions(n, string2, string3, string4, list, list2, list3, bl, n2, bl2, n3);
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
                    break block6;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_5;
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
            public void sendStoredMultipartText(int n, String string2, Uri uri, String string3, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException {
                Parcel parcel;
                void var2_9;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeString(string2);
                            if (uri != null) {
                                parcel2.writeInt(1);
                                uri.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeTypedList(list);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeTypedList(list2);
                        if (!this.mRemote.transact(26, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendStoredMultipartText(n, string2, uri, string3, list, list2);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_9;
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
            public void sendStoredText(int n, String string2, Uri uri, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                Parcel parcel;
                void var2_7;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeString(string2);
                            if (uri != null) {
                                parcel2.writeInt(1);
                                uri.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string3);
                        if (pendingIntent != null) {
                            parcel2.writeInt(1);
                            pendingIntent.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            parcel2.writeInt(1);
                            pendingIntent2.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(25, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendStoredText(n, string2, uri, string3, pendingIntent, pendingIntent2);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_7;
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
            public void sendTextForSubscriber(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) throws RemoteException {
                void var2_6;
                Parcel parcel;
                Parcel parcel2;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeString(string4);
                        parcel.writeString(string5);
                        int n2 = 1;
                        if (pendingIntent != null) {
                            parcel.writeInt(1);
                            pendingIntent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            parcel.writeInt(1);
                            pendingIntent2.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!bl) {
                            n2 = 0;
                        }
                        parcel.writeInt(n2);
                        if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendTextForSubscriber(n, string2, string3, string4, string5, pendingIntent, pendingIntent2, bl);
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
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_6;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void sendTextForSubscriberWithOptions(int var1_1, String var2_2, String var3_7, String var4_8, String var5_9, PendingIntent var6_10, PendingIntent var7_11, boolean var8_12, int var9_13, boolean var10_14, int var11_15) throws RemoteException {
                block12 : {
                    block13 : {
                        block14 : {
                            block11 : {
                                block10 : {
                                    var12_16 = Parcel.obtain();
                                    var13_17 = Parcel.obtain();
                                    var12_16.writeInterfaceToken("com.android.internal.telephony.ISms");
                                    var12_16.writeInt(var1_1);
                                    var12_16.writeString((String)var2_2);
                                    var12_16.writeString(var3_7);
                                    var12_16.writeString(var4_8);
                                    var12_16.writeString(var5_9);
                                    var14_18 = 1;
                                    if (var6_10 == null) break block10;
                                    try {
                                        var12_16.writeInt(1);
                                        var6_10.writeToParcel(var12_16, 0);
                                        break block11;
                                    }
                                    catch (Throwable var2_3) {
                                        break block12;
                                    }
                                }
                                var12_16.writeInt(0);
                            }
                            if (var7_11 == null) break block14;
                            var12_16.writeInt(1);
                            var7_11.writeToParcel(var12_16, 0);
                            ** GOTO lbl30
                        }
                        var12_16.writeInt(0);
lbl30: // 2 sources:
                        var15_19 = var8_12 != false ? 1 : 0;
                        var12_16.writeInt(var15_19);
                        var12_16.writeInt(var9_13);
                        var15_19 = var10_14 != false ? var14_18 : 0;
                        var12_16.writeInt(var15_19);
                        var12_16.writeInt(var11_15);
                        if (this.mRemote.transact(8, var12_16, var13_17, 0) || Stub.getDefaultImpl() == null) break block13;
                        var16_20 = Stub.getDefaultImpl();
                        try {
                            var16_20.sendTextForSubscriberWithOptions(var1_1, (String)var2_2, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15);
                            var13_17.recycle();
                            var12_16.recycle();
                            return;
                        }
                        catch (Throwable var2_4) {}
                    }
                    var2_2 = var13_17;
                    var2_2.readException();
                    var2_2.recycle();
                    var12_16.recycle();
                    return;
                    break block12;
                    catch (Throwable var2_5) {
                        // empty catch block
                    }
                }
                var13_17.recycle();
                var12_16.recycle();
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
            public void sendTextForSubscriberWithSelfPermissions(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) throws RemoteException {
                void var2_6;
                Parcel parcel;
                Parcel parcel2;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeString(string4);
                        parcel.writeString(string5);
                        int n2 = 1;
                        if (pendingIntent != null) {
                            parcel.writeInt(1);
                            pendingIntent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            parcel.writeInt(1);
                            pendingIntent2.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!bl) {
                            n2 = 0;
                        }
                        parcel.writeInt(n2);
                        if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendTextForSubscriberWithSelfPermissions(n, string2, string3, string4, string5, pendingIntent, pendingIntent2, bl);
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
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_6;
            }

            @Override
            public void setPremiumSmsPermission(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPremiumSmsPermission(string2, n);
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
            public void setPremiumSmsPermissionForSubscriber(int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPremiumSmsPermissionForSubscriber(n, string2, n2);
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
            public boolean updateMessageOnIccEfForSubscriber(int n, String string2, int n2, int n3, byte[] arrby) throws RemoteException {
                void var2_10;
                Parcel parcel;
                Parcel parcel2;
                block17 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        IBinder iBinder = this.mRemote;
                        boolean bl = false;
                        if (!iBinder.transact(2, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().updateMessageOnIccEfForSubscriber(n, string2, n2, n3, arrby);
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
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_10;
            }
        }

    }

}

