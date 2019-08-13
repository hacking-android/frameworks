/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;
import com.google.android.mms.pdu.EncodedStringValue;
import java.util.ArrayList;
import java.util.HashMap;

public class PduHeaders {
    public static final int ADAPTATION_ALLOWED = 188;
    public static final int ADDITIONAL_HEADERS = 176;
    public static final int APPLIC_ID = 183;
    public static final int ATTRIBUTES = 168;
    public static final int AUX_APPLIC_ID = 185;
    public static final int BCC = 129;
    public static final int CANCEL_ID = 190;
    public static final int CANCEL_STATUS = 191;
    public static final int CANCEL_STATUS_REQUEST_CORRUPTED = 129;
    public static final int CANCEL_STATUS_REQUEST_SUCCESSFULLY_RECEIVED = 128;
    public static final int CC = 130;
    public static final int CONTENT = 174;
    public static final int CONTENT_CLASS = 186;
    public static final int CONTENT_CLASS_CONTENT_BASIC = 134;
    public static final int CONTENT_CLASS_CONTENT_RICH = 135;
    public static final int CONTENT_CLASS_IMAGE_BASIC = 129;
    public static final int CONTENT_CLASS_IMAGE_RICH = 130;
    public static final int CONTENT_CLASS_MEGAPIXEL = 133;
    public static final int CONTENT_CLASS_TEXT = 128;
    public static final int CONTENT_CLASS_VIDEO_BASIC = 131;
    public static final int CONTENT_CLASS_VIDEO_RICH = 132;
    public static final int CONTENT_LOCATION = 131;
    public static final int CONTENT_TYPE = 132;
    public static final int CURRENT_MMS_VERSION = 18;
    public static final int DATE = 133;
    public static final int DELIVERY_REPORT = 134;
    public static final int DELIVERY_TIME = 135;
    public static final int DISTRIBUTION_INDICATOR = 177;
    public static final int DRM_CONTENT = 187;
    public static final int ELEMENT_DESCRIPTOR = 178;
    public static final int EXPIRY = 136;
    public static final int FROM = 137;
    public static final int FROM_ADDRESS_PRESENT_TOKEN = 128;
    public static final String FROM_ADDRESS_PRESENT_TOKEN_STR = "address-present-token";
    public static final int FROM_INSERT_ADDRESS_TOKEN = 129;
    public static final String FROM_INSERT_ADDRESS_TOKEN_STR = "insert-address-token";
    public static final int LIMIT = 179;
    public static final int MBOX_QUOTAS = 172;
    public static final int MBOX_TOTALS = 170;
    public static final int MESSAGE_CLASS = 138;
    public static final int MESSAGE_CLASS_ADVERTISEMENT = 129;
    public static final String MESSAGE_CLASS_ADVERTISEMENT_STR = "advertisement";
    public static final int MESSAGE_CLASS_AUTO = 131;
    public static final String MESSAGE_CLASS_AUTO_STR = "auto";
    public static final int MESSAGE_CLASS_INFORMATIONAL = 130;
    public static final String MESSAGE_CLASS_INFORMATIONAL_STR = "informational";
    public static final int MESSAGE_CLASS_PERSONAL = 128;
    public static final String MESSAGE_CLASS_PERSONAL_STR = "personal";
    public static final int MESSAGE_COUNT = 173;
    public static final int MESSAGE_ID = 139;
    public static final int MESSAGE_SIZE = 142;
    public static final int MESSAGE_TYPE = 140;
    public static final int MESSAGE_TYPE_ACKNOWLEDGE_IND = 133;
    public static final int MESSAGE_TYPE_CANCEL_CONF = 151;
    public static final int MESSAGE_TYPE_CANCEL_REQ = 150;
    public static final int MESSAGE_TYPE_DELETE_CONF = 149;
    public static final int MESSAGE_TYPE_DELETE_REQ = 148;
    public static final int MESSAGE_TYPE_DELIVERY_IND = 134;
    public static final int MESSAGE_TYPE_FORWARD_CONF = 138;
    public static final int MESSAGE_TYPE_FORWARD_REQ = 137;
    public static final int MESSAGE_TYPE_MBOX_DELETE_CONF = 146;
    public static final int MESSAGE_TYPE_MBOX_DELETE_REQ = 145;
    public static final int MESSAGE_TYPE_MBOX_DESCR = 147;
    public static final int MESSAGE_TYPE_MBOX_STORE_CONF = 140;
    public static final int MESSAGE_TYPE_MBOX_STORE_REQ = 139;
    public static final int MESSAGE_TYPE_MBOX_UPLOAD_CONF = 144;
    public static final int MESSAGE_TYPE_MBOX_UPLOAD_REQ = 143;
    public static final int MESSAGE_TYPE_MBOX_VIEW_CONF = 142;
    public static final int MESSAGE_TYPE_MBOX_VIEW_REQ = 141;
    public static final int MESSAGE_TYPE_NOTIFICATION_IND = 130;
    public static final int MESSAGE_TYPE_NOTIFYRESP_IND = 131;
    public static final int MESSAGE_TYPE_READ_ORIG_IND = 136;
    public static final int MESSAGE_TYPE_READ_REC_IND = 135;
    public static final int MESSAGE_TYPE_RETRIEVE_CONF = 132;
    public static final int MESSAGE_TYPE_SEND_CONF = 129;
    public static final int MESSAGE_TYPE_SEND_REQ = 128;
    public static final int MMS_VERSION = 141;
    public static final int MMS_VERSION_1_0 = 16;
    public static final int MMS_VERSION_1_1 = 17;
    public static final int MMS_VERSION_1_2 = 18;
    public static final int MMS_VERSION_1_3 = 19;
    public static final int MM_FLAGS = 164;
    public static final int MM_FLAGS_ADD_TOKEN = 128;
    public static final int MM_FLAGS_FILTER_TOKEN = 130;
    public static final int MM_FLAGS_REMOVE_TOKEN = 129;
    public static final int MM_STATE = 163;
    public static final int MM_STATE_DRAFT = 128;
    public static final int MM_STATE_FORWARDED = 132;
    public static final int MM_STATE_NEW = 130;
    public static final int MM_STATE_RETRIEVED = 131;
    public static final int MM_STATE_SENT = 129;
    public static final int PREVIOUSLY_SENT_BY = 160;
    public static final int PREVIOUSLY_SENT_DATE = 161;
    public static final int PRIORITY = 143;
    public static final int PRIORITY_HIGH = 130;
    public static final int PRIORITY_LOW = 128;
    public static final int PRIORITY_NORMAL = 129;
    public static final int QUOTAS = 171;
    public static final int READ_REPLY = 144;
    public static final int READ_REPORT = 144;
    public static final int READ_STATUS = 155;
    public static final int READ_STATUS_READ = 128;
    public static final int READ_STATUS__DELETED_WITHOUT_BEING_READ = 129;
    public static final int RECOMMENDED_RETRIEVAL_MODE = 180;
    public static final int RECOMMENDED_RETRIEVAL_MODE_MANUAL = 128;
    public static final int RECOMMENDED_RETRIEVAL_MODE_TEXT = 181;
    public static final int REPLACE_ID = 189;
    public static final int REPLY_APPLIC_ID = 184;
    public static final int REPLY_CHARGING = 156;
    public static final int REPLY_CHARGING_ACCEPTED = 130;
    public static final int REPLY_CHARGING_ACCEPTED_TEXT_ONLY = 131;
    public static final int REPLY_CHARGING_DEADLINE = 157;
    public static final int REPLY_CHARGING_ID = 158;
    public static final int REPLY_CHARGING_REQUESTED = 128;
    public static final int REPLY_CHARGING_REQUESTED_TEXT_ONLY = 129;
    public static final int REPLY_CHARGING_SIZE = 159;
    public static final int REPORT_ALLOWED = 145;
    public static final int RESPONSE_STATUS = 146;
    public static final int RESPONSE_STATUS_ERROR_CONTENT_NOT_ACCEPTED = 135;
    public static final int RESPONSE_STATUS_ERROR_MESSAGE_FORMAT_CORRUPT = 131;
    public static final int RESPONSE_STATUS_ERROR_MESSAGE_NOT_FOUND = 133;
    public static final int RESPONSE_STATUS_ERROR_NETWORK_PROBLEM = 134;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_ADDRESS_HIDING_NOT_SUPPORTED = 234;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_CONTENT_NOT_ACCEPTED = 229;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_END = 255;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_FAILURE = 224;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_LACK_OF_PREPAID = 235;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_MESSAGE_FORMAT_CORRUPT = 226;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND = 228;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_FORWARDING_DENIED = 232;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_LIMITATIONS_NOT_MET = 230;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_NOT_SUPPORTED = 233;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_REQUEST_NOT_ACCEPTED = 230;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_SENDING_ADDRESS_UNRESOLVED = 227;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_SERVICE_DENIED = 225;
    public static final int RESPONSE_STATUS_ERROR_SENDING_ADDRESS_UNRESOLVED = 132;
    public static final int RESPONSE_STATUS_ERROR_SERVICE_DENIED = 130;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_FAILURE = 192;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_MESSAGE_NOT_FOUND = 194;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM = 195;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_PARTIAL_SUCCESS = 196;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_SENDNG_ADDRESS_UNRESOLVED = 193;
    public static final int RESPONSE_STATUS_ERROR_UNSPECIFIED = 129;
    public static final int RESPONSE_STATUS_ERROR_UNSUPPORTED_MESSAGE = 136;
    public static final int RESPONSE_STATUS_OK = 128;
    public static final int RESPONSE_TEXT = 147;
    public static final int RETRIEVE_STATUS = 153;
    public static final int RETRIEVE_STATUS_ERROR_END = 255;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_CONTENT_UNSUPPORTED = 227;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_FAILURE = 224;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND = 226;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_SERVICE_DENIED = 225;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_FAILURE = 192;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_MESSAGE_NOT_FOUND = 193;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM = 194;
    public static final int RETRIEVE_STATUS_OK = 128;
    public static final int RETRIEVE_TEXT = 154;
    public static final int SENDER_VISIBILITY = 148;
    public static final int SENDER_VISIBILITY_HIDE = 128;
    public static final int SENDER_VISIBILITY_SHOW = 129;
    public static final int START = 175;
    public static final int STATUS = 149;
    public static final int STATUS_DEFERRED = 131;
    public static final int STATUS_EXPIRED = 128;
    public static final int STATUS_FORWARDED = 134;
    public static final int STATUS_INDETERMINATE = 133;
    public static final int STATUS_REJECTED = 130;
    public static final int STATUS_RETRIEVED = 129;
    public static final int STATUS_TEXT = 182;
    public static final int STATUS_UNREACHABLE = 135;
    public static final int STATUS_UNRECOGNIZED = 132;
    public static final int STORE = 162;
    public static final int STORED = 167;
    public static final int STORE_STATUS = 165;
    public static final int STORE_STATUS_ERROR_END = 255;
    public static final int STORE_STATUS_ERROR_PERMANENT_FAILURE = 224;
    public static final int STORE_STATUS_ERROR_PERMANENT_MESSAGE_FORMAT_CORRUPT = 226;
    public static final int STORE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND = 227;
    public static final int STORE_STATUS_ERROR_PERMANENT_MMBOX_FULL = 228;
    public static final int STORE_STATUS_ERROR_PERMANENT_SERVICE_DENIED = 225;
    public static final int STORE_STATUS_ERROR_TRANSIENT_FAILURE = 192;
    public static final int STORE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM = 193;
    public static final int STORE_STATUS_SUCCESS = 128;
    public static final int STORE_STATUS_TEXT = 166;
    public static final int SUBJECT = 150;
    public static final int TO = 151;
    public static final int TOTALS = 169;
    public static final int TRANSACTION_ID = 152;
    public static final int VALUE_ABSOLUTE_TOKEN = 128;
    public static final int VALUE_NO = 129;
    public static final int VALUE_RELATIVE_TOKEN = 129;
    public static final int VALUE_YES = 128;
    private HashMap<Integer, Object> mHeaderMap = new HashMap();

    protected void appendEncodedStringValue(EncodedStringValue encodedStringValue, int n) {
        if (encodedStringValue != null) {
            ArrayList<EncodedStringValue> arrayList;
            if (n != 129 && n != 130 && n != 151) {
                throw new RuntimeException("Invalid header field!");
            }
            ArrayList<EncodedStringValue> arrayList2 = arrayList = (ArrayList<EncodedStringValue>)this.mHeaderMap.get(n);
            if (arrayList == null) {
                arrayList2 = new ArrayList<EncodedStringValue>();
            }
            arrayList2.add(encodedStringValue);
            this.mHeaderMap.put(n, arrayList2);
            return;
        }
        throw new NullPointerException();
    }

    protected EncodedStringValue getEncodedStringValue(int n) {
        return (EncodedStringValue)this.mHeaderMap.get(n);
    }

    protected EncodedStringValue[] getEncodedStringValues(int n) {
        ArrayList arrayList = (ArrayList)this.mHeaderMap.get(n);
        if (arrayList == null) {
            return null;
        }
        return arrayList.toArray(new EncodedStringValue[arrayList.size()]);
    }

    protected long getLongInteger(int n) {
        Long l = (Long)this.mHeaderMap.get(n);
        if (l == null) {
            return -1L;
        }
        return l;
    }

    protected int getOctet(int n) {
        Integer n2 = (Integer)this.mHeaderMap.get(n);
        if (n2 == null) {
            return 0;
        }
        return n2;
    }

    protected byte[] getTextString(int n) {
        return (byte[])this.mHeaderMap.get(n);
    }

    protected void setEncodedStringValue(EncodedStringValue encodedStringValue, int n) {
        if (encodedStringValue != null) {
            if (n != 137 && n != 147 && n != 150 && n != 154 && n != 160 && n != 164 && n != 166 && n != 181 && n != 182) {
                throw new RuntimeException("Invalid header field!");
            }
            this.mHeaderMap.put(n, encodedStringValue);
            return;
        }
        throw new NullPointerException();
    }

    protected void setEncodedStringValues(EncodedStringValue[] arrencodedStringValue, int n) {
        if (arrencodedStringValue != null) {
            if (n != 129 && n != 130 && n != 151) {
                throw new RuntimeException("Invalid header field!");
            }
            ArrayList<EncodedStringValue> arrayList = new ArrayList<EncodedStringValue>();
            for (int i = 0; i < arrencodedStringValue.length; ++i) {
                arrayList.add(arrencodedStringValue[i]);
            }
            this.mHeaderMap.put(n, arrayList);
            return;
        }
        throw new NullPointerException();
    }

    protected void setLongInteger(long l, int n) {
        if (n != 133 && n != 142 && n != 157 && n != 159 && n != 161 && n != 173 && n != 175 && n != 179 && n != 135 && n != 136) {
            throw new RuntimeException("Invalid header field!");
        }
        this.mHeaderMap.put(n, l);
    }

    /*
     * Exception decompiling
     */
    protected void setOctet(int var1_1, int var2_2) throws InvalidHeaderValueException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    protected void setTextString(byte[] arrby, int n) {
        if (arrby != null) {
            if (n != 131 && n != 132 && n != 138 && n != 139 && n != 152 && n != 158 && n != 189 && n != 190) {
                switch (n) {
                    default: {
                        throw new RuntimeException("Invalid header field!");
                    }
                    case 183: 
                    case 184: 
                    case 185: 
                }
            }
            this.mHeaderMap.put(n, arrby);
            return;
        }
        throw new NullPointerException();
    }
}

