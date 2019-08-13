/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.Rlog;
import com.android.internal.telephony.HbpcdLookup;

public final class HbpcdUtils {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "HbpcdUtils";
    private ContentResolver resolver = null;

    public HbpcdUtils(Context context) {
        this.resolver = context.getContentResolver();
    }

    public String getIddByMcc(int n) {
        String string = "";
        ContentResolver contentResolver = this.resolver;
        Object object = HbpcdLookup.MccIdd.CONTENT_URI;
        CharSequence charSequence = new StringBuilder();
        charSequence.append("MCC=");
        charSequence.append(n);
        charSequence = charSequence.toString();
        contentResolver = contentResolver.query(object, new String[]{"IDD"}, (String)charSequence, null, null);
        object = string;
        if (contentResolver != null) {
            if (contentResolver.getCount() > 0) {
                contentResolver.moveToFirst();
                string = contentResolver.getString(0);
            }
            contentResolver.close();
            object = string;
        }
        if (false) {
            throw new NullPointerException();
        }
        return object;
    }

    public int getMcc(int n, int n2, int n3, boolean bl) {
        ContentResolver contentResolver = this.resolver;
        Object object = HbpcdLookup.ArbitraryMccSidMatch.CONTENT_URI;
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("SID=");
        ((StringBuilder)charSequence).append(n);
        charSequence = ((StringBuilder)charSequence).toString();
        object = contentResolver.query((Uri)object, new String[]{"MCC"}, (String)charSequence, null, null);
        if (object != null) {
            if (object.getCount() == 1) {
                object.moveToFirst();
                n = object.getInt(0);
                object.close();
                return n;
            }
            object.close();
        }
        contentResolver = this.resolver;
        object = HbpcdLookup.MccSidConflicts.CONTENT_URI;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("SID_Conflict=");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" and (((");
        ((StringBuilder)charSequence).append("GMT_Offset_Low");
        ((StringBuilder)charSequence).append("<=");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append(") and (");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append("<=");
        ((StringBuilder)charSequence).append("GMT_Offset_High");
        ((StringBuilder)charSequence).append(") and (0=");
        ((StringBuilder)charSequence).append(n3);
        ((StringBuilder)charSequence).append(")) or ((");
        ((StringBuilder)charSequence).append("GMT_DST_Low");
        ((StringBuilder)charSequence).append("<=");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append(") and (");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append("<=");
        ((StringBuilder)charSequence).append("GMT_DST_High");
        ((StringBuilder)charSequence).append(") and (1=");
        ((StringBuilder)charSequence).append(n3);
        ((StringBuilder)charSequence).append(")))");
        charSequence = ((StringBuilder)charSequence).toString();
        contentResolver = contentResolver.query((Uri)object, new String[]{"MCC"}, (String)charSequence, null, null);
        if (contentResolver != null) {
            n2 = contentResolver.getCount();
            if (n2 > 0) {
                if (n2 > 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("something wrong, get more results for 1 conflict SID: ");
                    ((StringBuilder)object).append((Object)contentResolver);
                    Rlog.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
                }
                contentResolver.moveToFirst();
                n = contentResolver.getInt(0);
                if (!bl) {
                    n = 0;
                }
                contentResolver.close();
                return n;
            }
            contentResolver.close();
        }
        object = this.resolver;
        contentResolver = HbpcdLookup.MccSidRange.CONTENT_URI;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("SID_Range_Low<=");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" and ");
        ((StringBuilder)charSequence).append("SID_Range_High");
        ((StringBuilder)charSequence).append(">=");
        ((StringBuilder)charSequence).append(n);
        charSequence = ((StringBuilder)charSequence).toString();
        object = object.query((Uri)contentResolver, new String[]{"MCC"}, (String)charSequence, null, null);
        if (object != null) {
            if (object.getCount() > 0) {
                object.moveToFirst();
                n = object.getInt(0);
                object.close();
                return n;
            }
            object.close();
        }
        return 0;
    }
}

