/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.IFinancialSmsCallback;
import com.android.internal.telephony.ISms;
import com.android.internal.telephony.SmsRawData;
import java.util.List;

public class ISmsImplBase
extends ISms.Stub {
    @Override
    public int checkSmsShortCodeDestination(int n, String string2, String string3, String string4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean copyMessageToIccEfForSubscriber(int n, String string2, int n2, byte[] arrby, byte[] arrby2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String createAppSpecificSmsToken(int n, String string2, PendingIntent pendingIntent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String createAppSpecificSmsTokenWithPackageInfo(int n, String string2, String string3, PendingIntent pendingIntent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean disableCellBroadcastForSubscriber(int n, int n2, int n3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean disableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean enableCellBroadcastForSubscriber(int n, int n2, int n3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean enableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int n, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getImsSmsFormatForSubscriber(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPreferredSmsSubscription() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPremiumSmsPermission(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPremiumSmsPermissionForSubscriber(int n, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getSmsMessagesForFinancialApp(int n, String string2, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void injectSmsPduForSubscriber(int n, byte[] arrby, String string2, PendingIntent pendingIntent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isImsSmsSupportedForSubscriber(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSMSPromptEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSmsSimPickActivityNeeded(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendDataForSubscriber(int n, String string2, String string3, String string4, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendDataForSubscriberWithSelfPermissions(int n, String string2, String string3, String string4, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendMultipartTextForSubscriber(int n, String string2, String string3, String string4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendMultipartTextForSubscriberWithOptions(int n, String string2, String string3, String string4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl, int n2, boolean bl2, int n3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendStoredMultipartText(int n, String string2, Uri uri, String string3, List<PendingIntent> list, List<PendingIntent> list2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendStoredText(int n, String string2, Uri uri, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendTextForSubscriber(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendTextForSubscriberWithOptions(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, int n2, boolean bl2, int n3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendTextForSubscriberWithSelfPermissions(int n, String string2, String string3, String string4, String string5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPremiumSmsPermission(String string2, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPremiumSmsPermissionForSubscriber(int n, String string2, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateMessageOnIccEfForSubscriber(int n, String string2, int n2, int n3, byte[] arrby) {
        throw new UnsupportedOperationException();
    }
}

