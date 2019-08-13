/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.provider.BlockedNumberContract
 *  android.provider.BlockedNumberContract$SystemContract
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.content.Context;
import android.os.Bundle;
import android.provider.BlockedNumberContract;
import android.telephony.Rlog;

public class BlockChecker {
    private static final String TAG = "BlockChecker";
    private static final boolean VDBG = false;

    public static int getBlockStatus(Context object, String charSequence, Bundle bundle) {
        long l;
        int n;
        int n2;
        block8 : {
            n = 0;
            l = System.nanoTime();
            n2 = BlockedNumberContract.SystemContract.shouldSystemBlockNumber((Context)object, (String)charSequence, (Bundle)bundle);
            if (n2 == 0) break block8;
            n = n2;
            n = n2;
            object = new StringBuilder();
            n = n2;
            ((StringBuilder)object).append((String)charSequence);
            n = n2;
            ((StringBuilder)object).append(" is blocked.");
            n = n2;
            try {
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Exception checking for blocked number: ");
                ((StringBuilder)charSequence).append(exception);
                Rlog.e((String)TAG, (String)((StringBuilder)charSequence).toString());
            }
        }
        n = n2;
        n2 = (int)((System.nanoTime() - l) / 1000000L);
        if (n2 > 500) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Blocked number lookup took: ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" ms.");
            Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        return n;
    }

    @Deprecated
    public static boolean isBlocked(Context context, String string) {
        return BlockChecker.isBlocked(context, string, null);
    }

    public static boolean isBlocked(Context context, String string, Bundle bundle) {
        boolean bl = BlockChecker.getBlockStatus(context, string, bundle) != 0;
        return bl;
    }
}

