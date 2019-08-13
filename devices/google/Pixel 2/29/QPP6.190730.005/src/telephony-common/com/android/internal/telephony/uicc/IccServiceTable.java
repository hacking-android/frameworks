/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.telephony.Rlog;

public abstract class IccServiceTable {
    @UnsupportedAppUsage
    protected final byte[] mServiceTable;

    protected IccServiceTable(byte[] arrby) {
        this.mServiceTable = arrby;
    }

    @UnsupportedAppUsage
    protected abstract String getTag();

    protected abstract Object[] getValues();

    protected boolean isAvailable(int n) {
        int n2 = n / 8;
        Object object = this.mServiceTable;
        int n3 = ((byte[])object).length;
        boolean bl = false;
        if (n2 >= n3) {
            object = this.getTag();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isAvailable for service ");
            stringBuilder.append(n + 1);
            stringBuilder.append(" fails, max service is ");
            stringBuilder.append(this.mServiceTable.length * 8);
            Rlog.e((String)object, (String)stringBuilder.toString());
            return false;
        }
        if ((object[n2] & 1 << n % 8) != 0) {
            bl = true;
        }
        return bl;
    }

    public String toString() {
        Object[] arrobject = this.getValues();
        int n = this.mServiceTable.length;
        StringBuilder stringBuilder = new StringBuilder(this.getTag());
        stringBuilder.append('[');
        stringBuilder.append(n * 8);
        stringBuilder = stringBuilder.append("]={ ");
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            byte by = this.mServiceTable[i];
            for (int j = 0; j < 8; ++j) {
                int n3 = n2;
                if ((1 << j & by) != 0) {
                    if (n2 != 0) {
                        stringBuilder.append(", ");
                    } else {
                        n2 = 1;
                    }
                    n3 = i * 8 + j;
                    if (n3 < arrobject.length) {
                        stringBuilder.append(arrobject[n3]);
                        n3 = n2;
                    } else {
                        stringBuilder.append('#');
                        stringBuilder.append(n3 + 1);
                        n3 = n2;
                    }
                }
                n2 = n3;
            }
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}

