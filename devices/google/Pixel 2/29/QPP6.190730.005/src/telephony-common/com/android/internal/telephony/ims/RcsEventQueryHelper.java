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
 *  android.provider.Telephony$RcsColumns$RcsGroupThreadColumns
 *  android.provider.Telephony$RcsColumns$RcsParticipantColumns
 *  android.provider.Telephony$RcsColumns$RcsUnifiedEventHelper
 *  android.telephony.Rlog
 *  android.telephony.ims.RcsEventQueryResultDescriptor
 *  android.telephony.ims.RcsGroupThreadIconChangedEventDescriptor
 *  android.telephony.ims.RcsGroupThreadNameChangedEventDescriptor
 *  android.telephony.ims.RcsGroupThreadParticipantJoinedEventDescriptor
 *  android.telephony.ims.RcsGroupThreadParticipantLeftEventDescriptor
 *  android.telephony.ims.RcsParticipantAliasChangedEventDescriptor
 *  android.telephony.ims.RcsQueryContinuationToken
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
import android.telephony.Rlog;
import android.telephony.ims.RcsEventQueryResultDescriptor;
import android.telephony.ims.RcsGroupThreadIconChangedEventDescriptor;
import android.telephony.ims.RcsGroupThreadNameChangedEventDescriptor;
import android.telephony.ims.RcsGroupThreadParticipantJoinedEventDescriptor;
import android.telephony.ims.RcsGroupThreadParticipantLeftEventDescriptor;
import android.telephony.ims.RcsParticipantAliasChangedEventDescriptor;
import android.telephony.ims.RcsQueryContinuationToken;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class RcsEventQueryHelper {
    private final ContentResolver mContentResolver;

    RcsEventQueryHelper(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    private RcsGroupThreadIconChangedEventDescriptor createNewGroupIconChangedEvent(Cursor object) {
        String string = object.getString(object.getColumnIndex("new_icon_uri"));
        long l = object.getLong(object.getColumnIndex("origination_timestamp"));
        int n = object.getInt(object.getColumnIndex("rcs_thread_id"));
        int n2 = object.getInt(object.getColumnIndex("source_participant"));
        object = string == null ? null : Uri.parse((String)string);
        return new RcsGroupThreadIconChangedEventDescriptor(l, n, n2, (Uri)object);
    }

    private RcsGroupThreadNameChangedEventDescriptor createNewGroupNameChangedEvent(Cursor cursor) {
        return new RcsGroupThreadNameChangedEventDescriptor(cursor.getLong(cursor.getColumnIndex("origination_timestamp")), cursor.getInt(cursor.getColumnIndex("rcs_thread_id")), cursor.getInt(cursor.getColumnIndex("source_participant")), cursor.getString(cursor.getColumnIndex("new_name")));
    }

    private RcsParticipantAliasChangedEventDescriptor createNewParticipantAliasChangedEvent(Cursor cursor) {
        return new RcsParticipantAliasChangedEventDescriptor(cursor.getLong(cursor.getColumnIndex("origination_timestamp")), cursor.getInt(cursor.getColumnIndex("source_participant")), cursor.getString(cursor.getColumnIndex("new_alias")));
    }

    private RcsGroupThreadParticipantJoinedEventDescriptor createNewParticipantJoinedEvent(Cursor cursor) {
        return new RcsGroupThreadParticipantJoinedEventDescriptor(cursor.getLong(cursor.getColumnIndex("origination_timestamp")), cursor.getInt(cursor.getColumnIndex("rcs_thread_id")), cursor.getInt(cursor.getColumnIndex("source_participant")), cursor.getInt(cursor.getColumnIndex("destination_participant")));
    }

    private RcsGroupThreadParticipantLeftEventDescriptor createNewParticipantLeftEvent(Cursor cursor) {
        return new RcsGroupThreadParticipantLeftEventDescriptor(cursor.getLong(cursor.getColumnIndex("origination_timestamp")), cursor.getInt(cursor.getColumnIndex("rcs_thread_id")), cursor.getInt(cursor.getColumnIndex("source_participant")), cursor.getInt(cursor.getColumnIndex("destination_participant")));
    }

    private String getPathForEventType(int n) throws RemoteException {
        if (n != 2) {
            if (n != 4) {
                if (n != 8) {
                    if (n == 16) {
                        return "name_changed_event";
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Event type unrecognized: ");
                    stringBuilder.append(n);
                    throw new RemoteException(stringBuilder.toString());
                }
                return "icon_changed_event";
            }
            return "participant_left_event";
        }
        return "participant_joined_event";
    }

    int createGroupThreadEvent(int n, long l, int n2, int n3, ContentValues object) throws RemoteException {
        ContentValues contentValues = new ContentValues((ContentValues)object);
        contentValues.put("event_type", Integer.valueOf(n));
        contentValues.put("origination_timestamp", Long.valueOf(l));
        contentValues.put("source_participant", Integer.valueOf(n3));
        object = Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI.buildUpon().appendPath(Integer.toString(n2)).appendPath(this.getPathForEventType(n)).build();
        object = this.mContentResolver.insert((Uri)object, contentValues);
        n3 = 0;
        if (object != null) {
            n3 = Integer.parseInt(object.getLastPathSegment());
        }
        if (n3 > 0) {
            return n3;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not create event with type: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" on thread: ");
        ((StringBuilder)object).append(n2);
        throw new RemoteException(((StringBuilder)object).toString());
    }

    Uri getParticipantEventInsertionUri(int n) {
        return Telephony.RcsColumns.RcsParticipantColumns.RCS_PARTICIPANT_URI.buildUpon().appendPath(Integer.toString(n)).appendPath("alias_change_event").build();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    RcsEventQueryResultDescriptor performEventQuery(Bundle var1_1) throws RemoteException {
        block13 : {
            block14 : {
                var2_3 = null;
                var3_5 = new ArrayList<E>();
                var4_6 = this.mContentResolver.query(Telephony.RcsColumns.RcsUnifiedEventHelper.RCS_EVENT_QUERY_URI, null, (Bundle)var1_1, null);
                if (var4_6 == null) break block14;
                try {}
                catch (Throwable var1_2) {}
                ** GOTO lbl-1000
            }
            var3_5 = new StringBuilder();
            var3_5.append("Event query failed, bundle: ");
            var3_5.append(var1_1);
            var2_3 = new RemoteException(var3_5.toString());
            throw var2_3;
lbl-1000: // 7 sources:
            {
                while (var4_6.moveToNext()) {
                    var5_8 = var4_6.getInt(var4_6.getColumnIndex("event_type"));
                    if (var5_8 != 1) {
                        if (var5_8 != 2) {
                            if (var5_8 != 4) {
                                if (var5_8 != 8) {
                                    if (var5_8 != 16) {
                                        var1_1 = new StringBuilder();
                                        var1_1.append("RcsEventQueryHelper: invalid event type: ");
                                        var1_1.append(var5_8);
                                        Rlog.e((String)"RcsMsgStoreController", (String)var1_1.toString());
                                        continue;
                                    }
                                    var3_5.add(this.createNewGroupNameChangedEvent(var4_6));
                                    continue;
                                }
                                var3_5.add(this.createNewGroupIconChangedEvent(var4_6));
                                continue;
                            }
                            var3_5.add(this.createNewParticipantLeftEvent(var4_6));
                            continue;
                        }
                        var3_5.add(this.createNewParticipantJoinedEvent(var4_6));
                        continue;
                    }
                    var3_5.add(this.createNewParticipantAliasChangedEvent(var4_6));
                }
                var6_9 = var4_6.getExtras();
                var1_1 = var2_3;
                if (var6_9 == null) break block13;
                var1_1 = (RcsQueryContinuationToken)var6_9.getParcelable("query_continuation_token");
            }
        }
        var4_6.close();
        return new RcsEventQueryResultDescriptor((RcsQueryContinuationToken)var1_1, (List)var3_5);
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

