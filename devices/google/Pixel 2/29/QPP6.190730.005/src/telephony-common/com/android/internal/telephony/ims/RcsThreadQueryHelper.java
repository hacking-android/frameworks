/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.provider.Telephony
 *  android.provider.Telephony$RcsColumns
 *  android.provider.Telephony$RcsColumns$Rcs1To1ThreadColumns
 *  android.provider.Telephony$RcsColumns$RcsGroupThreadColumns
 *  android.provider.Telephony$RcsColumns$RcsThreadColumns
 *  android.telephony.ims.RcsQueryContinuationToken
 *  android.telephony.ims.RcsThreadQueryResultParcelable
 *  com.android.ims.RcsTypeIdPair
 */
package com.android.internal.telephony.ims;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.os.RemoteException;
import android.provider.Telephony;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.RcsThreadQueryResultParcelable;
import com.android.ims.RcsTypeIdPair;
import com.android.internal.telephony.ims.RcsParticipantQueryHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class RcsThreadQueryHelper {
    private static final int THREAD_ID_INDEX_IN_INSERTION_URI = 1;
    private final ContentResolver mContentResolver;
    private final RcsParticipantQueryHelper mParticipantQueryHelper;

    RcsThreadQueryHelper(ContentResolver contentResolver, RcsParticipantQueryHelper rcsParticipantQueryHelper) {
        this.mContentResolver = contentResolver;
        this.mParticipantQueryHelper = rcsParticipantQueryHelper;
    }

    static Uri get1To1ThreadUri(int n) {
        return Uri.withAppendedPath((Uri)Telephony.RcsColumns.Rcs1To1ThreadColumns.RCS_1_TO_1_THREAD_URI, (String)Integer.toString(n));
    }

    static Uri getAllParticipantsInThreadUri(int n) {
        return Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI.buildUpon().appendPath(Integer.toString(n)).appendPath("participant").build();
    }

    static Uri getGroupThreadUri(int n) {
        return Uri.withAppendedPath((Uri)Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI, (String)Integer.toString(n));
    }

    static Uri getParticipantInThreadUri(int n, int n2) {
        return Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI.buildUpon().appendPath(Integer.toString(n)).appendPath("participant").appendPath(Integer.toString(n2)).build();
    }

    int create1To1Thread(int n) throws RemoteException {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("rcs_participant_id", Integer.valueOf(n));
        contentValues = this.mContentResolver.insert(Telephony.RcsColumns.Rcs1To1ThreadColumns.RCS_1_TO_1_THREAD_URI, contentValues);
        if (contentValues != null) {
            n = Integer.parseInt(contentValues.getLastPathSegment());
            if (n > 0) {
                return n;
            }
            throw new RemoteException("Rcs1To1Thread creation failed");
        }
        throw new RemoteException("Rcs1To1Thread creation failed");
    }

    int createGroupThread(String string, Uri uri) throws RemoteException {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("group_name", string);
        if (uri != null) {
            contentValues.put("group_icon", uri.toString());
        }
        if ((string = this.mContentResolver.insert(Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI, contentValues)) != null) {
            return Integer.parseInt((String)string.getPathSegments().get(1));
        }
        throw new RemoteException("RcsGroupThread creation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    RcsThreadQueryResultParcelable performThreadQuery(Bundle var1_1) throws RemoteException {
        block9 : {
            block10 : {
                var2_3 = null;
                var3_5 = new ArrayList<E>();
                var4_6 = this.mContentResolver.query(Telephony.RcsColumns.RcsThreadColumns.RCS_THREAD_URI, null, var1_1 /* !! */ , null);
                if (var4_6 == null) break block10;
                try {}
                catch (Throwable var1_2) {}
                ** GOTO lbl-1000
            }
            var3_5 = new StringBuilder();
            var3_5.append("Could not perform thread query, bundle: ");
            var3_5.append((Object)var1_1 /* !! */ );
            var2_3 = new RemoteException(var3_5.toString());
            throw var2_3;
lbl-1000: // 3 sources:
            {
                while (var4_6.moveToNext()) {
                    var5_8 = var4_6.getInt(var4_6.getColumnIndex("thread_type"));
                    var5_8 = var5_8 == 1 ? 1 : 0;
                    if (var5_8 != 0) {
                        var5_8 = var4_6.getInt(var4_6.getColumnIndex("rcs_thread_id"));
                        var1_1 /* !! */  = new RcsTypeIdPair(1, var5_8);
                        var3_5.add(var1_1 /* !! */ );
                        continue;
                    }
                    var5_8 = var4_6.getInt(var4_6.getColumnIndex("rcs_thread_id"));
                    var1_1 /* !! */  = new RcsTypeIdPair(0, var5_8);
                    var3_5.add(var1_1 /* !! */ );
                }
                var6_9 = var4_6.getExtras();
                var1_1 /* !! */  = var2_3;
                if (var6_9 == null) break block9;
                var1_1 /* !! */  = (RcsQueryContinuationToken)var6_9.getParcelable("query_continuation_token");
            }
        }
        var4_6.close();
        return new RcsThreadQueryResultParcelable((RcsQueryContinuationToken)var1_1 /* !! */ , (List)var3_5);
        try {
            throw var1_2;
        }
        catch (Throwable var2_4) {
            if (var4_6 == null) throw var2_4;
            try {
                var4_6.close();
                throw var2_4;
            }
            catch (Throwable var4_7) {
                var1_2.addSuppressed(var4_7);
            }
            throw var2_4;
        }
    }
}

