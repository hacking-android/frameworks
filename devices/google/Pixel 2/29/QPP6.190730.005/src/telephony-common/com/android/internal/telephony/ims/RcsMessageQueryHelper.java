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
 *  android.provider.Telephony$RcsColumns$RcsFileTransferColumns
 *  android.provider.Telephony$RcsColumns$RcsIncomingMessageColumns
 *  android.provider.Telephony$RcsColumns$RcsOutgoingMessageColumns
 *  android.provider.Telephony$RcsColumns$RcsUnifiedMessageColumns
 *  android.telephony.ims.RcsFileTransferCreationParams
 *  android.telephony.ims.RcsMessageCreationParams
 *  android.telephony.ims.RcsMessageQueryResultParcelable
 *  android.telephony.ims.RcsQueryContinuationToken
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
import android.telephony.ims.RcsFileTransferCreationParams;
import android.telephony.ims.RcsMessageCreationParams;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import com.android.ims.RcsTypeIdPair;
import java.util.ArrayList;
import java.util.List;

class RcsMessageQueryHelper {
    private final ContentResolver mContentResolver;

    private static /* synthetic */ /* end resource */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    RcsMessageQueryHelper(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    private Uri getMessageDeliveryQueryUri(int n) {
        return this.getMessageInsertionUri(false).buildUpon().appendPath(Integer.toString(n)).appendPath("delivery").build();
    }

    void createContentValuesForGenericMessage(ContentValues contentValues, int n, RcsMessageCreationParams rcsMessageCreationParams) {
        contentValues.put("rcs_message_global_id", rcsMessageCreationParams.getRcsMessageGlobalId());
        contentValues.put("sub_id", Integer.valueOf(rcsMessageCreationParams.getSubId()));
        contentValues.put("status", Integer.valueOf(rcsMessageCreationParams.getMessageStatus()));
        contentValues.put("origination_timestamp", Long.valueOf(rcsMessageCreationParams.getOriginationTimestamp()));
        contentValues.put("rcs_thread_id", Integer.valueOf(n));
    }

    ContentValues getContentValuesForFileTransfer(RcsFileTransferCreationParams rcsFileTransferCreationParams) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("session_id", rcsFileTransferCreationParams.getRcsFileTransferSessionId());
        contentValues.put("content_uri", rcsFileTransferCreationParams.getContentUri().toString());
        contentValues.put("content_type", rcsFileTransferCreationParams.getContentMimeType());
        contentValues.put("file_size", Long.valueOf(rcsFileTransferCreationParams.getFileSize()));
        contentValues.put("transfer_offset", Long.valueOf(rcsFileTransferCreationParams.getTransferOffset()));
        contentValues.put("transfer_status", Integer.valueOf(rcsFileTransferCreationParams.getFileTransferStatus()));
        contentValues.put("width", Integer.valueOf(rcsFileTransferCreationParams.getWidth()));
        contentValues.put("height", Integer.valueOf(rcsFileTransferCreationParams.getHeight()));
        contentValues.put("duration", Long.valueOf(rcsFileTransferCreationParams.getMediaDuration()));
        contentValues.put("preview_uri", rcsFileTransferCreationParams.getPreviewUri().toString());
        contentValues.put("preview_type", rcsFileTransferCreationParams.getPreviewMimeType());
        return contentValues;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    int[] getDeliveryParticipantsForMessage(int var1_1) throws RemoteException {
        block6 : {
            var2_2 = this.mContentResolver.query(this.getMessageDeliveryQueryUri(var1_1), null, null, null);
            if (var2_2 == null) ** GOTO lbl5
            try {
                block7 : {
                    break block7;
lbl5: // 1 sources:
                    var4_7 = new StringBuilder();
                    var4_7.append("Could not query deliveries for message, messageId: ");
                    var4_7.append(var1_1);
                    var3_4 = new RemoteException(var4_7.toString());
                    throw var3_4;
                }
                var3_3 = new int[var2_2.getCount()];
                var1_1 = 0;
                break block6;
            }
            catch (Throwable var4_6) {}
            try {
                throw var4_6;
            }
            catch (Throwable var3_5) {
                if (var2_2 == null) throw var3_5;
                RcsMessageQueryHelper.$closeResource(var4_6, (AutoCloseable)var2_2);
                throw var3_5;
            }
        }
        while (var2_2.moveToNext()) {
            var3_3[var1_1] = var2_2.getInt(var2_2.getColumnIndex("rcs_participant_id"));
            ++var1_1;
        }
        RcsMessageQueryHelper.$closeResource(null, (AutoCloseable)var2_2);
        return var3_3;
    }

    Uri getFileTransferInsertionUri(int n) {
        return Telephony.RcsColumns.RcsUnifiedMessageColumns.UNIFIED_MESSAGE_URI.buildUpon().appendPath(Integer.toString(n)).appendPath("file_transfer").build();
    }

    Uri getFileTransferUpdateUri(int n) {
        return Uri.withAppendedPath((Uri)Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, (String)Integer.toString(n));
    }

    long getLongValueFromDelivery(int n, int n2, String object) throws RemoteException {
        Cursor cursor;
        block7 : {
            cursor = this.mContentResolver.query(this.getMessageDeliveryUri(n, n2), null, null, null);
            if (cursor != null) {
                if (!cursor.moveToFirst()) break block7;
                long l = cursor.getLong(cursor.getColumnIndex(object));
                RcsMessageQueryHelper.$closeResource(null, (AutoCloseable)cursor);
                return l;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not read delivery for message: ");
            stringBuilder.append(n);
            stringBuilder.append(", participant: ");
            stringBuilder.append(n2);
            object = new RemoteException(stringBuilder.toString());
            throw object;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (cursor != null) {
                    RcsMessageQueryHelper.$closeResource(throwable, (AutoCloseable)cursor);
                }
                throw throwable2;
            }
        }
    }

    Uri getMessageDeletionUri(int n, boolean bl, int n2, boolean bl2) {
        Uri.Builder builder = Telephony.RcsColumns.CONTENT_AND_AUTHORITY.buildUpon();
        String string = bl2 ? "group_thread" : "p2p_thread";
        builder = builder.appendPath(string).appendPath(Integer.toString(n2));
        string = bl ? "incoming_message" : "outgoing_message";
        return builder.appendPath(string).appendPath(Integer.toString(n)).build();
    }

    Uri getMessageDeliveryUri(int n, int n2) {
        return Uri.withAppendedPath((Uri)this.getMessageDeliveryQueryUri(n), (String)Integer.toString(n2));
    }

    Uri getMessageInsertionUri(boolean bl) {
        Uri uri = bl ? Telephony.RcsColumns.RcsIncomingMessageColumns.INCOMING_MESSAGE_URI : Telephony.RcsColumns.RcsOutgoingMessageColumns.OUTGOING_MESSAGE_URI;
        return uri;
    }

    Uri getMessageUpdateUri(int n, boolean bl) {
        return this.getMessageInsertionUri(bl).buildUpon().appendPath(Integer.toString(n)).build();
    }

    /*
     * Enabled aggressive exception aggregation
     */
    RcsMessageQueryResultParcelable performMessageQuery(Bundle object) throws RemoteException {
        StringBuilder stringBuilder = null;
        Object object2 = new ArrayList();
        Cursor cursor = this.mContentResolver.query(Telephony.RcsColumns.RcsUnifiedMessageColumns.UNIFIED_MESSAGE_URI, null, object, null);
        if (cursor != null) {
            block9 : {
                while (cursor.moveToNext()) {
                    int n = cursor.getInt(cursor.getColumnIndex("message_type"));
                    int n2 = 0;
                    n = n == 1 ? 1 : 0;
                    int n3 = cursor.getInt(cursor.getColumnIndex("rcs_message_row_id"));
                    if (n != 0) {
                        n2 = 1;
                    }
                    object = new RcsTypeIdPair(n2, n3);
                    object2.add(object);
                }
                Bundle bundle = cursor.getExtras();
                object = stringBuilder;
                if (bundle == null) break block9;
                object = (RcsQueryContinuationToken)bundle.getParcelable("query_continuation_token");
            }
            RcsMessageQueryHelper.$closeResource(null, (AutoCloseable)cursor);
            return new RcsMessageQueryResultParcelable((RcsQueryContinuationToken)object, object2);
        }
        try {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not perform message query, bundle: ");
            stringBuilder.append(object);
            object2 = new RemoteException(stringBuilder.toString());
            throw object2;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (cursor != null) {
                    RcsMessageQueryHelper.$closeResource(throwable, (AutoCloseable)cursor);
                }
                throw throwable2;
            }
        }
    }
}

