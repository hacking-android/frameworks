/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.provider.Telephony
 *  android.provider.Telephony$RcsColumns
 *  android.provider.Telephony$RcsColumns$RcsParticipantColumns
 *  android.telephony.ims.RcsParticipantQueryResultParcelable
 *  android.telephony.ims.RcsQueryContinuationToken
 */
package com.android.internal.telephony.ims;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.os.RemoteException;
import android.provider.Telephony;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import java.util.ArrayList;
import java.util.List;

class RcsParticipantQueryHelper {
    private final ContentResolver mContentResolver;

    RcsParticipantQueryHelper(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    static Uri getUriForParticipant(int n) {
        return Uri.withAppendedPath((Uri)Telephony.RcsColumns.RcsParticipantColumns.RCS_PARTICIPANT_URI, (String)Integer.toString(n));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    RcsParticipantQueryResultParcelable performParticipantQuery(Bundle object) throws RemoteException {
        StringBuilder stringBuilder = null;
        Object object2 = new ArrayList();
        Cursor cursor = this.mContentResolver.query(Telephony.RcsColumns.RcsParticipantColumns.RCS_PARTICIPANT_URI, null, object, null);
        if (cursor != null) {
            block9 : {
                while (cursor.moveToNext()) {
                    object2.add(cursor.getInt(cursor.getColumnIndex("rcs_participant_id")));
                }
                Bundle bundle = cursor.getExtras();
                object = stringBuilder;
                if (bundle == null) break block9;
                object = (RcsQueryContinuationToken)bundle.getParcelable("query_continuation_token");
            }
            cursor.close();
            return new RcsParticipantQueryResultParcelable((RcsQueryContinuationToken)object, (List)object2);
        }
        try {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not perform participant query, bundle: ");
            stringBuilder.append(object);
            object2 = new RemoteException(stringBuilder.toString());
            throw object2;
        }
        catch (Throwable throwable) {}
        try {
            throw throwable;
        }
        catch (Throwable throwable) {
            if (cursor == null) throw throwable;
            try {
                cursor.close();
                throw throwable;
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
    }
}

