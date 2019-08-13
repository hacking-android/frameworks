/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.DatabaseUtils
 *  android.drm.DrmManagerClient
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.provider.Telephony
 *  android.provider.Telephony$Mms
 *  android.provider.Telephony$Mms$Draft
 *  android.provider.Telephony$Mms$Inbox
 *  android.provider.Telephony$Mms$Outbox
 *  android.provider.Telephony$Mms$Sent
 *  android.provider.Telephony$MmsSms
 *  android.provider.Telephony$MmsSms$PendingMessages
 *  android.provider.Telephony$Threads
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.mms.pdu;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.drm.DrmManagerClient;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.mms.InvalidHeaderValueException;
import com.google.android.mms.MmsException;
import com.google.android.mms.pdu.EncodedStringValue;
import com.google.android.mms.pdu.GenericPdu;
import com.google.android.mms.pdu.MultimediaMessagePdu;
import com.google.android.mms.pdu.PduBody;
import com.google.android.mms.pdu.PduHeaders;
import com.google.android.mms.pdu.PduPart;
import com.google.android.mms.pdu.SendReq;
import com.google.android.mms.util.PduCache;
import com.google.android.mms.util.PduCacheEntry;
import com.google.android.mms.util.SqliteWrapper;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PduPersister {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int[] ADDRESS_FIELDS = new int[]{129, 130, 137, 151};
    private static final HashMap<Integer, Integer> CHARSET_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> CHARSET_COLUMN_NAME_MAP;
    private static final boolean DEBUG = false;
    private static final long DUMMY_THREAD_ID = Long.MAX_VALUE;
    private static final HashMap<Integer, Integer> ENCODED_STRING_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> ENCODED_STRING_COLUMN_NAME_MAP;
    private static final boolean LOCAL_LOGV = false;
    private static final HashMap<Integer, Integer> LONG_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> LONG_COLUMN_NAME_MAP;
    private static final HashMap<Uri, Integer> MESSAGE_BOX_MAP;
    private static final HashMap<Integer, Integer> OCTET_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> OCTET_COLUMN_NAME_MAP;
    private static final int PART_COLUMN_CHARSET = 1;
    private static final int PART_COLUMN_CONTENT_DISPOSITION = 2;
    private static final int PART_COLUMN_CONTENT_ID = 3;
    private static final int PART_COLUMN_CONTENT_LOCATION = 4;
    private static final int PART_COLUMN_CONTENT_TYPE = 5;
    private static final int PART_COLUMN_FILENAME = 6;
    private static final int PART_COLUMN_ID = 0;
    private static final int PART_COLUMN_NAME = 7;
    private static final int PART_COLUMN_TEXT = 8;
    private static final String[] PART_PROJECTION;
    private static final PduCache PDU_CACHE_INSTANCE;
    private static final int PDU_COLUMN_CONTENT_CLASS = 11;
    private static final int PDU_COLUMN_CONTENT_LOCATION = 5;
    private static final int PDU_COLUMN_CONTENT_TYPE = 6;
    private static final int PDU_COLUMN_DATE = 21;
    private static final int PDU_COLUMN_DELIVERY_REPORT = 12;
    private static final int PDU_COLUMN_DELIVERY_TIME = 22;
    private static final int PDU_COLUMN_EXPIRY = 23;
    private static final int PDU_COLUMN_ID = 0;
    private static final int PDU_COLUMN_MESSAGE_BOX = 1;
    private static final int PDU_COLUMN_MESSAGE_CLASS = 7;
    private static final int PDU_COLUMN_MESSAGE_ID = 8;
    private static final int PDU_COLUMN_MESSAGE_SIZE = 24;
    private static final int PDU_COLUMN_MESSAGE_TYPE = 13;
    private static final int PDU_COLUMN_MMS_VERSION = 14;
    private static final int PDU_COLUMN_PRIORITY = 15;
    private static final int PDU_COLUMN_READ_REPORT = 16;
    private static final int PDU_COLUMN_READ_STATUS = 17;
    private static final int PDU_COLUMN_REPORT_ALLOWED = 18;
    private static final int PDU_COLUMN_RESPONSE_TEXT = 9;
    private static final int PDU_COLUMN_RETRIEVE_STATUS = 19;
    private static final int PDU_COLUMN_RETRIEVE_TEXT = 3;
    private static final int PDU_COLUMN_RETRIEVE_TEXT_CHARSET = 26;
    private static final int PDU_COLUMN_STATUS = 20;
    private static final int PDU_COLUMN_SUBJECT = 4;
    private static final int PDU_COLUMN_SUBJECT_CHARSET = 25;
    private static final int PDU_COLUMN_THREAD_ID = 2;
    private static final int PDU_COLUMN_TRANSACTION_ID = 10;
    private static final String[] PDU_PROJECTION;
    public static final int PROC_STATUS_COMPLETED = 3;
    public static final int PROC_STATUS_PERMANENTLY_FAILURE = 2;
    public static final int PROC_STATUS_TRANSIENT_FAILURE = 1;
    private static final String TAG = "PduPersister";
    public static final String TEMPORARY_DRM_OBJECT_URI = "content://mms/9223372036854775807/part";
    private static final HashMap<Integer, Integer> TEXT_STRING_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> TEXT_STRING_COLUMN_NAME_MAP;
    private static PduPersister sPersister;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final DrmManagerClient mDrmManagerClient;
    private final TelephonyManager mTelephonyManager;

    static {
        PDU_PROJECTION = new String[]{"_id", "msg_box", "thread_id", "retr_txt", "sub", "ct_l", "ct_t", "m_cls", "m_id", "resp_txt", "tr_id", "ct_cls", "d_rpt", "m_type", "v", "pri", "rr", "read_status", "rpt_a", "retr_st", "st", "date", "d_tm", "exp", "m_size", "sub_cs", "retr_txt_cs"};
        PART_PROJECTION = new String[]{"_id", "chset", "cd", "cid", "cl", "ct", "fn", "name", "text"};
        MESSAGE_BOX_MAP = new HashMap();
        MESSAGE_BOX_MAP.put(Telephony.Mms.Inbox.CONTENT_URI, 1);
        MESSAGE_BOX_MAP.put(Telephony.Mms.Sent.CONTENT_URI, 2);
        MESSAGE_BOX_MAP.put(Telephony.Mms.Draft.CONTENT_URI, 3);
        MESSAGE_BOX_MAP.put(Telephony.Mms.Outbox.CONTENT_URI, 4);
        CHARSET_COLUMN_INDEX_MAP = new HashMap();
        CHARSET_COLUMN_INDEX_MAP.put(150, 25);
        CHARSET_COLUMN_INDEX_MAP.put(154, 26);
        CHARSET_COLUMN_NAME_MAP = new HashMap();
        CHARSET_COLUMN_NAME_MAP.put(150, "sub_cs");
        CHARSET_COLUMN_NAME_MAP.put(154, "retr_txt_cs");
        ENCODED_STRING_COLUMN_INDEX_MAP = new HashMap();
        ENCODED_STRING_COLUMN_INDEX_MAP.put(154, 3);
        ENCODED_STRING_COLUMN_INDEX_MAP.put(150, 4);
        ENCODED_STRING_COLUMN_NAME_MAP = new HashMap();
        ENCODED_STRING_COLUMN_NAME_MAP.put(154, "retr_txt");
        ENCODED_STRING_COLUMN_NAME_MAP.put(150, "sub");
        TEXT_STRING_COLUMN_INDEX_MAP = new HashMap();
        TEXT_STRING_COLUMN_INDEX_MAP.put(131, 5);
        TEXT_STRING_COLUMN_INDEX_MAP.put(132, 6);
        TEXT_STRING_COLUMN_INDEX_MAP.put(138, 7);
        TEXT_STRING_COLUMN_INDEX_MAP.put(139, 8);
        TEXT_STRING_COLUMN_INDEX_MAP.put(147, 9);
        TEXT_STRING_COLUMN_INDEX_MAP.put(152, 10);
        TEXT_STRING_COLUMN_NAME_MAP = new HashMap();
        TEXT_STRING_COLUMN_NAME_MAP.put(131, "ct_l");
        TEXT_STRING_COLUMN_NAME_MAP.put(132, "ct_t");
        TEXT_STRING_COLUMN_NAME_MAP.put(138, "m_cls");
        TEXT_STRING_COLUMN_NAME_MAP.put(139, "m_id");
        TEXT_STRING_COLUMN_NAME_MAP.put(147, "resp_txt");
        TEXT_STRING_COLUMN_NAME_MAP.put(152, "tr_id");
        OCTET_COLUMN_INDEX_MAP = new HashMap();
        OCTET_COLUMN_INDEX_MAP.put(186, 11);
        OCTET_COLUMN_INDEX_MAP.put(134, 12);
        OCTET_COLUMN_INDEX_MAP.put(140, 13);
        OCTET_COLUMN_INDEX_MAP.put(141, 14);
        OCTET_COLUMN_INDEX_MAP.put(143, 15);
        OCTET_COLUMN_INDEX_MAP.put(144, 16);
        OCTET_COLUMN_INDEX_MAP.put(155, 17);
        OCTET_COLUMN_INDEX_MAP.put(145, 18);
        OCTET_COLUMN_INDEX_MAP.put(153, 19);
        OCTET_COLUMN_INDEX_MAP.put(149, 20);
        OCTET_COLUMN_NAME_MAP = new HashMap();
        OCTET_COLUMN_NAME_MAP.put(186, "ct_cls");
        OCTET_COLUMN_NAME_MAP.put(134, "d_rpt");
        OCTET_COLUMN_NAME_MAP.put(140, "m_type");
        OCTET_COLUMN_NAME_MAP.put(141, "v");
        OCTET_COLUMN_NAME_MAP.put(143, "pri");
        OCTET_COLUMN_NAME_MAP.put(144, "rr");
        OCTET_COLUMN_NAME_MAP.put(155, "read_status");
        OCTET_COLUMN_NAME_MAP.put(145, "rpt_a");
        OCTET_COLUMN_NAME_MAP.put(153, "retr_st");
        OCTET_COLUMN_NAME_MAP.put(149, "st");
        LONG_COLUMN_INDEX_MAP = new HashMap();
        LONG_COLUMN_INDEX_MAP.put(133, 21);
        LONG_COLUMN_INDEX_MAP.put(135, 22);
        LONG_COLUMN_INDEX_MAP.put(136, 23);
        LONG_COLUMN_INDEX_MAP.put(142, 24);
        LONG_COLUMN_NAME_MAP = new HashMap();
        LONG_COLUMN_NAME_MAP.put(133, "date");
        LONG_COLUMN_NAME_MAP.put(135, "d_tm");
        LONG_COLUMN_NAME_MAP.put(136, "exp");
        LONG_COLUMN_NAME_MAP.put(142, "m_size");
        PDU_CACHE_INSTANCE = PduCache.getInstance();
    }

    private PduPersister(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mDrmManagerClient = new DrmManagerClient(context);
        this.mTelephonyManager = (TelephonyManager)context.getSystemService("phone");
    }

    private byte[] getByteArrayFromPartColumn(Cursor cursor, int n) {
        if (!cursor.isNull(n)) {
            return PduPersister.getBytes(cursor.getString(n));
        }
        return null;
    }

    public static byte[] getBytes(String arrby) {
        try {
            arrby = arrby.getBytes("iso-8859-1");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.e((String)TAG, (String)"ISO_8859_1 must be supported!", (Throwable)unsupportedEncodingException);
            return new byte[0];
        }
    }

    private Integer getIntegerFromPartColumn(Cursor cursor, int n) {
        if (!cursor.isNull(n)) {
            return cursor.getInt(n);
        }
        return null;
    }

    private static String getPartContentType(PduPart object) {
        object = ((PduPart)object).getContentType() == null ? null : PduPersister.toIsoString(((PduPart)object).getContentType());
        return object;
    }

    public static PduPersister getPduPersister(Context context) {
        PduPersister pduPersister = sPersister;
        if (pduPersister == null) {
            sPersister = new PduPersister(context);
        } else if (!context.equals((Object)pduPersister.mContext)) {
            sPersister.release();
            sPersister = new PduPersister(context);
        }
        return sPersister;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadAddress(long l, PduHeaders pduHeaders) {
        Context context = this.mContext;
        Object object = this.mContentResolver;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("content://mms/");
        ((StringBuilder)object2).append(l);
        ((StringBuilder)object2).append("/addr");
        context = SqliteWrapper.query(context, (ContentResolver)object, Uri.parse((String)((StringBuilder)object2).toString()), new String[]{"address", "charset", "type"}, null, null, null);
        if (context == null) return;
        {
            try {
                while (context.moveToNext()) {
                    object = context.getString(0);
                    if (TextUtils.isEmpty((CharSequence)object)) continue;
                    int n = context.getInt(2);
                    if (n != 129 && n != 130) {
                        if (n != 137) {
                            if (n != 151) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Unknown address type: ");
                                ((StringBuilder)object).append(n);
                                Log.e((String)TAG, (String)((StringBuilder)object).toString());
                                continue;
                            }
                        } else {
                            object2 = new EncodedStringValue(context.getInt(1), PduPersister.getBytes((String)object));
                            pduHeaders.setEncodedStringValue((EncodedStringValue)object2, n);
                            continue;
                        }
                    }
                    object2 = new EncodedStringValue(context.getInt(1), PduPersister.getBytes((String)object));
                    pduHeaders.appendEncodedStringValue((EncodedStringValue)object2, n);
                }
                return;
            }
            finally {
                context.close();
            }
        }
    }

    /*
     * Exception decompiling
     */
    private PduPart[] loadParts(long var1_1) throws MmsException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 20[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void loadRecipients(int n, HashSet<String> hashSet, HashMap<Integer, EncodedStringValue[]> cloneable, boolean bl) {
        String string;
        EncodedStringValue[] arrencodedStringValue = ((HashMap)cloneable).get(n);
        if (arrencodedStringValue == null) {
            return;
        }
        if (bl && arrencodedStringValue.length == 1) {
            return;
        }
        Object object = SubscriptionManager.from((Context)this.mContext);
        cloneable = new HashSet();
        int n2 = 0;
        if (bl) {
            for (int n3 : object.getActiveSubscriptionIdList()) {
                string = this.mTelephonyManager.getLine1Number(n3);
                if (string == null) continue;
                cloneable.add(string);
            }
        }
        int n4 = arrencodedStringValue.length;
        block1 : for (n = n2; n < n4; ++n) {
            object = arrencodedStringValue[n];
            if (object == null) continue;
            string = ((EncodedStringValue)object).getString();
            if (bl) {
                object = cloneable.iterator();
                while (object.hasNext()) {
                    if (PhoneNumberUtils.compare((String)string, (String)((String)object.next())) || hashSet.contains(string)) continue;
                    hashSet.add(string);
                    continue block1;
                }
                continue;
            }
            if (hashSet.contains(string)) continue;
            hashSet.add(string);
        }
    }

    private void persistAddress(long l, int n, EncodedStringValue[] arrencodedStringValue) {
        ContentValues contentValues = new ContentValues(3);
        for (EncodedStringValue encodedStringValue : arrencodedStringValue) {
            contentValues.clear();
            contentValues.put("address", PduPersister.toIsoString(encodedStringValue.getTextString()));
            contentValues.put("charset", Integer.valueOf(encodedStringValue.getCharacterSet()));
            contentValues.put("type", Integer.valueOf(n));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("content://mms/");
            stringBuilder.append(l);
            stringBuilder.append("/addr");
            Uri object = Uri.parse((String)stringBuilder.toString());
            SqliteWrapper.insert(this.mContext, this.mContentResolver, object, contentValues);
        }
    }

    /*
     * Exception decompiling
     */
    private void persistData(PduPart var1_1, Uri var2_21, String var3_32, HashMap<Uri, InputStream> var4_33) throws MmsException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 9[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void setEncodedStringValueToHeaders(Cursor cursor, int n, PduHeaders pduHeaders, int n2) {
        String string = cursor.getString(n);
        if (string != null && string.length() > 0) {
            pduHeaders.setEncodedStringValue(new EncodedStringValue(cursor.getInt(CHARSET_COLUMN_INDEX_MAP.get(n2).intValue()), PduPersister.getBytes(string)), n2);
        }
    }

    private void setLongToHeaders(Cursor cursor, int n, PduHeaders pduHeaders, int n2) {
        if (!cursor.isNull(n)) {
            pduHeaders.setLongInteger(cursor.getLong(n), n2);
        }
    }

    private void setOctetToHeaders(Cursor cursor, int n, PduHeaders pduHeaders, int n2) throws InvalidHeaderValueException {
        if (!cursor.isNull(n)) {
            pduHeaders.setOctet(cursor.getInt(n), n2);
        }
    }

    private void setTextStringToHeaders(Cursor object, int n, PduHeaders pduHeaders, int n2) {
        if ((object = object.getString(n)) != null) {
            pduHeaders.setTextString(PduPersister.getBytes((String)object), n2);
        }
    }

    public static String toIsoString(byte[] object) {
        try {
            object = new String((byte[])object, "iso-8859-1");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.e((String)TAG, (String)"ISO_8859_1 must be supported!", (Throwable)unsupportedEncodingException);
            return "";
        }
    }

    private void updateAddress(long l, int n, EncodedStringValue[] arrencodedStringValue) {
        Context context = this.mContext;
        ContentResolver contentResolver = this.mContentResolver;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("content://mms/");
        stringBuilder.append(l);
        stringBuilder.append("/addr");
        Uri uri = Uri.parse((String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("type=");
        stringBuilder.append(n);
        SqliteWrapper.delete(context, contentResolver, uri, stringBuilder.toString(), null);
        this.persistAddress(l, n, arrencodedStringValue);
    }

    private void updatePart(Uri uri, PduPart pduPart, HashMap<Uri, InputStream> hashMap) throws MmsException {
        ContentValues contentValues = new ContentValues(7);
        int n = pduPart.getCharset();
        if (n != 0) {
            contentValues.put("chset", Integer.valueOf(n));
        }
        if (pduPart.getContentType() != null) {
            String string = PduPersister.toIsoString(pduPart.getContentType());
            contentValues.put("ct", string);
            if (pduPart.getFilename() != null) {
                contentValues.put("fn", new String(pduPart.getFilename()));
            }
            if (pduPart.getName() != null) {
                contentValues.put("name", new String(pduPart.getName()));
            }
            String string2 = null;
            if (pduPart.getContentDisposition() != null) {
                string2 = PduPersister.toIsoString(pduPart.getContentDisposition());
                contentValues.put("cd", string2);
            }
            if (pduPart.getContentId() != null) {
                string2 = PduPersister.toIsoString(pduPart.getContentId());
                contentValues.put("cid", string2);
            }
            if (pduPart.getContentLocation() != null) {
                contentValues.put("cl", PduPersister.toIsoString(pduPart.getContentLocation()));
            }
            SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
            if (pduPart.getData() != null || !uri.equals((Object)pduPart.getDataUri())) {
                this.persistData(pduPart, uri, string, hashMap);
            }
            return;
        }
        throw new MmsException("MIME type of the part must be set.");
    }

    public Cursor getPendingMessages(long l) {
        Uri.Builder builder = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        builder.appendQueryParameter("protocol", "mms");
        return SqliteWrapper.query(this.mContext, this.mContentResolver, builder.build(), null, "err_type < ? AND due_time <= ?", new String[]{String.valueOf(10), String.valueOf(l)}, "due_time");
    }

    /*
     * Exception decompiling
     */
    public GenericPdu load(Uri var1_1) throws MmsException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[TRYBLOCK]], but top level block is 33[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public Uri move(Uri uri, Uri uri2) throws MmsException {
        long l = ContentUris.parseId((Uri)uri);
        if (l != -1L) {
            Integer n = MESSAGE_BOX_MAP.get((Object)uri2);
            if (n != null) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put("msg_box", n);
                SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
                return ContentUris.withAppendedId((Uri)uri2, (long)l);
            }
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        throw new MmsException("Error! ID of the message: -1.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Uri persist(GenericPdu object, Uri arrn, boolean bl, boolean bl2, HashMap<Uri, InputStream> arrencodedStringValue) throws MmsException {
        long l;
        Object object22;
        int n;
        int n22;
        long l2;
        Object object32;
        if (arrn == null) {
            throw new MmsException("Uri may not be null.");
        }
        try {
            l = ContentUris.parseId((Uri)arrn);
        }
        catch (NumberFormatException numberFormatException) {
            l = -1L;
        }
        int n3 = l != -1L ? 1 : 0;
        if (n3 == 0 && MESSAGE_BOX_MAP.get(arrn) == null) {
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        Object object42 = PDU_CACHE_INSTANCE;
        synchronized (object42) {
            boolean bl3 = PDU_CACHE_INSTANCE.isUpdating((Uri)arrn);
            if (bl3) {
                try {
                    PDU_CACHE_INSTANCE.wait();
                }
                catch (InterruptedException interruptedException) {
                    Log.e((String)TAG, (String)"persist1: ", (Throwable)interruptedException);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge((Uri)arrn);
        PduHeaders pduHeaders = ((GenericPdu)object).getPduHeaders();
        ContentValues contentValues = new ContentValues();
        for (Object object5 : ENCODED_STRING_COLUMN_NAME_MAP.entrySet()) {
            n = object5.getKey();
            object22 = pduHeaders.getEncodedStringValue(n);
            if (object22 == null) continue;
            object42 = CHARSET_COLUMN_NAME_MAP.get(n);
            contentValues.put(object5.getValue(), PduPersister.toIsoString(((EncodedStringValue)object22).getTextString()));
            contentValues.put((String)object42, Integer.valueOf(((EncodedStringValue)object22).getCharacterSet()));
        }
        for (Object object32 : TEXT_STRING_COLUMN_NAME_MAP.entrySet()) {
            object42 = pduHeaders.getTextString((Integer)object32.getKey());
            if (object42 == null) continue;
            contentValues.put((String)object32.getValue(), PduPersister.toIsoString((byte[])object42));
        }
        for (Object object42 : OCTET_COLUMN_NAME_MAP.entrySet()) {
            n = pduHeaders.getOctet((Integer)object42.getKey());
            if (n == 0) continue;
            contentValues.put((String)object42.getValue(), Integer.valueOf(n));
        }
        for (Object object22 : LONG_COLUMN_NAME_MAP.entrySet()) {
            l2 = pduHeaders.getLongInteger((Integer)object22.getKey());
            if (l2 == -1L) continue;
            contentValues.put((String)object22.getValue(), Long.valueOf(l2));
        }
        object32 = new HashMap<Integer, EncodedStringValue[]>(ADDRESS_FIELDS.length);
        for (int n22 : ADDRESS_FIELDS) {
            object42 = null;
            if (n22 == 137) {
                Object object5;
                object5 = pduHeaders.getEncodedStringValue(n22);
                if (object5 != null) {
                    object42 = new EncodedStringValue[]{object5};
                }
            } else {
                object42 = pduHeaders.getEncodedStringValues(n22);
            }
            ((HashMap)object32).put(n22, object42);
        }
        object42 = new HashSet();
        n = ((GenericPdu)object).getMessageType();
        if (n == 130 || n == 132 || n == 128) {
            long l3;
            if (n != 128) {
                if (n == 130 || n == 132) {
                    this.loadRecipients(137, (HashSet<String>)object42, (HashMap<Integer, EncodedStringValue[]>)object32, false);
                    if (bl2) {
                        this.loadRecipients(151, (HashSet<String>)object42, (HashMap<Integer, EncodedStringValue[]>)object32, true);
                        this.loadRecipients(130, (HashSet<String>)object42, (HashMap<Integer, EncodedStringValue[]>)object32, true);
                    }
                }
            } else {
                this.loadRecipients(151, (HashSet<String>)object42, (HashMap<Integer, EncodedStringValue[]>)object32, false);
            }
            l2 = l3 = 0L;
            if (bl) {
                l2 = l3;
                if (!((HashSet)object42).isEmpty()) {
                    l2 = Telephony.Threads.getOrCreateThreadId((Context)this.mContext, (Set)object42);
                }
            }
            contentValues.put("thread_id", Long.valueOf(l2));
        }
        l2 = System.currentTimeMillis();
        int n4 = 1;
        if (object instanceof MultimediaMessagePdu) {
            if ((object = ((MultimediaMessagePdu)object).getBody()) != null) {
                int n5 = ((PduBody)object).getPartsNum();
                n = n5 > 2 ? 0 : n4;
                n4 = 0;
                for (n22 = 0; n22 < n5; ++n22) {
                    object22 = ((PduBody)object).getPart(n22);
                    int n6 = n4 + ((PduPart)object22).getDataLength();
                    this.persistPart((PduPart)object22, l2, (HashMap<Uri, InputStream>)arrencodedStringValue);
                    object22 = PduPersister.getPartContentType((PduPart)object22);
                    n4 = n;
                    if (object22 != null) {
                        n4 = n;
                        if (!"application/smil".equals(object22)) {
                            n4 = n;
                            if (!"text/plain".equals(object22)) {
                                n4 = 0;
                            }
                        }
                    }
                    n = n4;
                    n4 = n6;
                }
                n22 = n;
                n = n4;
                n4 = n22;
            } else {
                n = 0;
            }
        } else {
            object = null;
            n = 0;
        }
        n4 = n4 != 0 ? 1 : 0;
        contentValues.put("text_only", Integer.valueOf(n4));
        if (contentValues.getAsInteger("m_size") == null) {
            contentValues.put("m_size", Integer.valueOf(n));
        }
        if (n3 != 0) {
            SqliteWrapper.update(this.mContext, this.mContentResolver, (Uri)arrn, contentValues, null, null);
            object = arrn;
        } else {
            object = SqliteWrapper.insert(this.mContext, this.mContentResolver, (Uri)arrn, contentValues);
            if (object == null) {
                throw new MmsException("persist() failed: return null.");
            }
            l = ContentUris.parseId((Uri)object);
        }
        object22 = new ContentValues(1);
        object22.put("mid", Long.valueOf(l));
        arrencodedStringValue = this.mContext;
        contentValues = this.mContentResolver;
        object42 = new StringBuilder();
        ((StringBuilder)object42).append("content://mms/");
        ((StringBuilder)object42).append(l2);
        ((StringBuilder)object42).append("/part");
        SqliteWrapper.update((Context)arrencodedStringValue, (ContentResolver)contentValues, Uri.parse((String)((StringBuilder)object42).toString()), (ContentValues)object22, null, null);
        if (n3 == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(arrn);
            ((StringBuilder)object).append("/");
            ((StringBuilder)object).append(l);
            object = Uri.parse((String)((StringBuilder)object).toString());
        }
        arrn = ADDRESS_FIELDS;
        n3 = arrn.length;
        n4 = 0;
        while (n4 < n3) {
            n22 = arrn[n4];
            arrencodedStringValue = ((HashMap)object32).get(n22);
            if (arrencodedStringValue != null) {
                this.persistAddress(l, n22, arrencodedStringValue);
            }
            ++n4;
        }
        return object;
    }

    public Uri persistPart(PduPart pduPart, long l, HashMap<Uri, InputStream> hashMap) throws MmsException {
        String string;
        CharSequence charSequence = new StringBuilder();
        charSequence.append("content://mms/");
        charSequence.append(l);
        charSequence.append("/part");
        Uri uri = Uri.parse((String)charSequence.toString());
        ContentValues contentValues = new ContentValues(8);
        int n = pduPart.getCharset();
        if (n != 0) {
            contentValues.put("chset", Integer.valueOf(n));
        }
        if ((string = PduPersister.getPartContentType(pduPart)) != null) {
            charSequence = string;
            if ("image/jpg".equals(string)) {
                charSequence = "image/jpeg";
            }
            contentValues.put("ct", (String)charSequence);
            if ("application/smil".equals(charSequence)) {
                contentValues.put("seq", Integer.valueOf(-1));
            }
            if (pduPart.getFilename() != null) {
                contentValues.put("fn", new String(pduPart.getFilename()));
            }
            if (pduPart.getName() != null) {
                contentValues.put("name", new String(pduPart.getName()));
            }
            if (pduPart.getContentDisposition() != null) {
                contentValues.put("cd", PduPersister.toIsoString(pduPart.getContentDisposition()));
            }
            if (pduPart.getContentId() != null) {
                contentValues.put("cid", PduPersister.toIsoString(pduPart.getContentId()));
            }
            if (pduPart.getContentLocation() != null) {
                contentValues.put("cl", PduPersister.toIsoString(pduPart.getContentLocation()));
            }
            if ((string = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, contentValues)) != null) {
                this.persistData(pduPart, (Uri)string, (String)charSequence, hashMap);
                pduPart.setDataUri((Uri)string);
                return string;
            }
            throw new MmsException("Failed to persist part, return null.");
        }
        throw new MmsException("MIME type of the part must be set.");
    }

    public void release() {
        Uri uri = Uri.parse((String)TEMPORARY_DRM_OBJECT_URI);
        SqliteWrapper.delete(this.mContext, this.mContentResolver, uri, null, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateHeaders(Uri uri, SendReq object) {
        int n;
        long l;
        Object object2 = PDU_CACHE_INSTANCE;
        synchronized (object2) {
            boolean bl = PDU_CACHE_INSTANCE.isUpdating(uri);
            if (bl) {
                try {
                    PDU_CACHE_INSTANCE.wait();
                }
                catch (InterruptedException interruptedException) {
                    Log.e((String)TAG, (String)"updateHeaders: ", (Throwable)interruptedException);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge(uri);
        ContentValues contentValues = new ContentValues(10);
        object2 = object.getContentType();
        if (object2 != null) {
            contentValues.put("ct_t", PduPersister.toIsoString((byte[])object2));
        }
        if ((l = object.getDate()) != -1L) {
            contentValues.put("date", Long.valueOf(l));
        }
        if ((n = object.getDeliveryReport()) != 0) {
            contentValues.put("d_rpt", Integer.valueOf(n));
        }
        if ((l = object.getExpiry()) != -1L) {
            contentValues.put("exp", Long.valueOf(l));
        }
        if ((object2 = object.getMessageClass()) != null) {
            contentValues.put("m_cls", PduPersister.toIsoString((byte[])object2));
        }
        if ((n = object.getPriority()) != 0) {
            contentValues.put("pri", Integer.valueOf(n));
        }
        if ((n = object.getReadReport()) != 0) {
            contentValues.put("rr", Integer.valueOf(n));
        }
        if ((object2 = object.getTransactionId()) != null) {
            contentValues.put("tr_id", PduPersister.toIsoString((byte[])object2));
        }
        if ((object2 = object.getSubject()) != null) {
            contentValues.put("sub", PduPersister.toIsoString(((EncodedStringValue)object2).getTextString()));
            contentValues.put("sub_cs", Integer.valueOf(((EncodedStringValue)object2).getCharacterSet()));
        } else {
            contentValues.put("sub", "");
        }
        l = object.getMessageSize();
        if (l > 0L) {
            contentValues.put("m_size", Long.valueOf(l));
        }
        PduHeaders pduHeaders = object.getPduHeaders();
        HashSet<String> hashSet = new HashSet<String>();
        for (Object object3 : ADDRESS_FIELDS) {
            EncodedStringValue encodedStringValue;
            object = object3 == 137 ? ((encodedStringValue = pduHeaders.getEncodedStringValue((int)object3)) != null ? new EncodedStringValue[]{encodedStringValue} : null) : pduHeaders.getEncodedStringValues((int)object3);
            if (object == null) continue;
            this.updateAddress(ContentUris.parseId((Uri)uri), (int)object3, (EncodedStringValue[])object);
            if (object3 != 151) continue;
            int n2 = ((EncodedStringValue[])object).length;
            for (object3 = (Object)false; object3 < n2; ++object3) {
                encodedStringValue = object[object3];
                if (encodedStringValue == null) continue;
                hashSet.add(encodedStringValue.getString());
            }
        }
        if (!hashSet.isEmpty()) {
            contentValues.put("thread_id", Long.valueOf(Telephony.Threads.getOrCreateThreadId((Context)this.mContext, hashSet)));
        }
        SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void updateParts(Uri var1_1, PduBody var2_2, HashMap<Uri, InputStream> var3_3) throws MmsException {
        block22 : {
            var4_5 = PduPersister.PDU_CACHE_INSTANCE;
            // MONITORENTER : var4_5
            var5_6 = PduPersister.PDU_CACHE_INSTANCE.isUpdating(var1_1);
            if (!var5_6) break block22;
            try {
                PduPersister.PDU_CACHE_INSTANCE.wait();
            }
            catch (InterruptedException var6_7) {
                Log.e((String)"PduPersister", (String)"updateParts: ", (Throwable)var6_7);
            }
            var6_8 = (PduCacheEntry)PduPersister.PDU_CACHE_INSTANCE.get(var1_1);
            if (var6_8 != null) {
                ((MultimediaMessagePdu)var6_8.getPdu()).setBody((PduBody)var2_2);
            }
        }
        PduPersister.PDU_CACHE_INSTANCE.setUpdating(var1_1, true);
        // MONITOREXIT : var4_5
        var6_8 = new ArrayList<PduPart>();
        var4_5 = new HashMap();
        var7_9 = var2_2.getPartsNum();
        var8_10 = new StringBuilder();
        var8_10.append('(');
        for (var9_11 = 0; var9_11 < var7_9; ++var9_11) {
            var10_12 = var2_2.getPart(var9_11);
            var11_13 = var10_12.getDataUri();
            if (var11_13 != null && !TextUtils.isEmpty((CharSequence)var11_13.getAuthority()) && var11_13.getAuthority().startsWith("mms")) {
                var4_5.put(var11_13, var10_12);
                if (var8_10.length() > 1) {
                    var8_10.append(" AND ");
                }
                var8_10.append("_id");
                var8_10.append("!=");
                DatabaseUtils.appendEscapedSQLString((StringBuilder)var8_10, (String)var11_13.getLastPathSegment());
                continue;
            }
            var6_8.add(var10_12);
        }
        ** try [egrp 4[TRYBLOCK] [14 : 258->458)] { 
lbl47: // 1 sources:
        var8_10.append(')');
        var12_14 = ContentUris.parseId((Uri)var1_1);
        var11_13 = this.mContext;
        var10_12 = this.mContentResolver;
        var2_2 = new StringBuilder();
        var2_2.append((Object)Telephony.Mms.CONTENT_URI);
        var2_2.append("/");
        var2_2.append(var12_14);
        var2_2.append("/part");
        var14_15 = Uri.parse((String)var2_2.toString());
        var2_2 = var8_10.length() > 2 ? var8_10.toString() : null;
        SqliteWrapper.delete((Context)var11_13, (ContentResolver)var10_12, var14_15, (String)var2_2, null);
        var2_2 = var6_8.iterator();
        while (var2_2.hasNext()) {
            this.persistPart((PduPart)var2_2.next(), var12_14, var3_3);
        }
        var2_2 = var4_5.entrySet().iterator();
        while (var2_2.hasNext() != false) {
            var4_5 = (Map.Entry)var2_2.next();
            this.updatePart((Uri)var4_5.getKey(), (PduPart)var4_5.getValue(), var3_3);
        }
        return;
lbl76: // 1 sources:
        finally {
            var2_2 = PduPersister.PDU_CACHE_INSTANCE;
            // MONITORENTER : var2_2
            PduPersister.PDU_CACHE_INSTANCE.setUpdating(var1_1, false);
            PduPersister.PDU_CACHE_INSTANCE.notifyAll();
            // MONITOREXIT : var2_2
        }
    }
}

