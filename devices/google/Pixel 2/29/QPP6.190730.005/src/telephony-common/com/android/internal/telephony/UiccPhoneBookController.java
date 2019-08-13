/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.Rlog;
import com.android.internal.telephony.IIccPhoneBook;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.uicc.AdnRecord;
import java.util.List;

public class UiccPhoneBookController
extends IIccPhoneBook.Stub {
    private static final String TAG = "UiccPhoneBookController";
    @UnsupportedAppUsage
    private Phone[] mPhone;

    @UnsupportedAppUsage
    public UiccPhoneBookController(Phone[] arrphone) {
        if (ServiceManager.getService((String)"simphonebook") == null) {
            ServiceManager.addService((String)"simphonebook", (IBinder)this);
        }
        this.mPhone = arrphone;
    }

    @UnsupportedAppUsage
    private int getDefaultSubscription() {
        return PhoneFactory.getDefaultSubscription();
    }

    @UnsupportedAppUsage
    private IccPhoneBookInterfaceManager getIccPhoneBookInterfaceManager(int n) {
        int n2 = SubscriptionController.getInstance().getPhoneId(n);
        try {
            IccPhoneBookInterfaceManager iccPhoneBookInterfaceManager = this.mPhone[n2].getIccPhoneBookInterfaceManager();
            return iccPhoneBookInterfaceManager;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception is :");
            stringBuilder.append(arrayIndexOutOfBoundsException.toString());
            stringBuilder.append(" For subscription :");
            stringBuilder.append(n);
            Rlog.e((String)TAG, (String)stringBuilder.toString());
            arrayIndexOutOfBoundsException.printStackTrace();
            return null;
        }
        catch (NullPointerException nullPointerException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception is :");
            stringBuilder.append(nullPointerException.toString());
            stringBuilder.append(" For subscription :");
            stringBuilder.append(n);
            Rlog.e((String)TAG, (String)stringBuilder.toString());
            nullPointerException.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AdnRecord> getAdnRecordsInEf(int n) throws RemoteException {
        return this.getAdnRecordsInEfForSubscriber(this.getDefaultSubscription(), n);
    }

    @Override
    public List<AdnRecord> getAdnRecordsInEfForSubscriber(int n, int n2) throws RemoteException {
        Object object = this.getIccPhoneBookInterfaceManager(n);
        if (object != null) {
            return ((IccPhoneBookInterfaceManager)object).getAdnRecordsInEf(n2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("getAdnRecordsInEf iccPbkIntMgr isnull for Subscription:");
        ((StringBuilder)object).append(n);
        Rlog.e((String)TAG, (String)((StringBuilder)object).toString());
        return null;
    }

    @Override
    public int[] getAdnRecordsSize(int n) throws RemoteException {
        return this.getAdnRecordsSizeForSubscriber(this.getDefaultSubscription(), n);
    }

    @Override
    public int[] getAdnRecordsSizeForSubscriber(int n, int n2) throws RemoteException {
        Object object = this.getIccPhoneBookInterfaceManager(n);
        if (object != null) {
            return ((IccPhoneBookInterfaceManager)object).getAdnRecordsSize(n2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("getAdnRecordsSize iccPbkIntMgr is null for Subscription:");
        ((StringBuilder)object).append(n);
        Rlog.e((String)TAG, (String)((StringBuilder)object).toString());
        return null;
    }

    @Override
    public boolean updateAdnRecordsInEfByIndex(int n, String string, String string2, int n2, String string3) throws RemoteException {
        return this.updateAdnRecordsInEfByIndexForSubscriber(this.getDefaultSubscription(), n, string, string2, n2, string3);
    }

    @Override
    public boolean updateAdnRecordsInEfByIndexForSubscriber(int n, int n2, String charSequence, String string, int n3, String string2) throws RemoteException {
        IccPhoneBookInterfaceManager iccPhoneBookInterfaceManager = this.getIccPhoneBookInterfaceManager(n);
        if (iccPhoneBookInterfaceManager != null) {
            return iccPhoneBookInterfaceManager.updateAdnRecordsInEfByIndex(n2, (String)charSequence, string, n3, string2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("updateAdnRecordsInEfByIndex iccPbkIntMgr is null for Subscription:");
        ((StringBuilder)charSequence).append(n);
        Rlog.e((String)TAG, (String)((StringBuilder)charSequence).toString());
        return false;
    }

    @Override
    public boolean updateAdnRecordsInEfBySearch(int n, String string, String string2, String string3, String string4, String string5) throws RemoteException {
        return this.updateAdnRecordsInEfBySearchForSubscriber(this.getDefaultSubscription(), n, string, string2, string3, string4, string5);
    }

    @Override
    public boolean updateAdnRecordsInEfBySearchForSubscriber(int n, int n2, String charSequence, String string, String string2, String string3, String string4) throws RemoteException {
        IccPhoneBookInterfaceManager iccPhoneBookInterfaceManager = this.getIccPhoneBookInterfaceManager(n);
        if (iccPhoneBookInterfaceManager != null) {
            return iccPhoneBookInterfaceManager.updateAdnRecordsInEfBySearch(n2, (String)charSequence, string, string2, string3, string4);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("updateAdnRecordsInEfBySearch iccPbkIntMgr is null for Subscription:");
        ((StringBuilder)charSequence).append(n);
        Rlog.e((String)TAG, (String)((StringBuilder)charSequence).toString());
        return false;
    }
}

