/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.mms.pdu;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class PduPart {
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    static final byte[] DISPOSITION_ATTACHMENT;
    static final byte[] DISPOSITION_FROM_DATA;
    static final byte[] DISPOSITION_INLINE;
    public static final String P_7BIT = "7bit";
    public static final String P_8BIT = "8bit";
    public static final String P_BASE64 = "base64";
    public static final String P_BINARY = "binary";
    public static final int P_CHARSET = 129;
    public static final int P_COMMENT = 155;
    public static final int P_CONTENT_DISPOSITION = 197;
    public static final int P_CONTENT_ID = 192;
    public static final int P_CONTENT_LOCATION = 142;
    public static final int P_CONTENT_TRANSFER_ENCODING = 200;
    public static final int P_CONTENT_TYPE = 145;
    public static final int P_CREATION_DATE = 147;
    public static final int P_CT_MR_TYPE = 137;
    public static final int P_DEP_COMMENT = 140;
    public static final int P_DEP_CONTENT_DISPOSITION = 174;
    public static final int P_DEP_DOMAIN = 141;
    public static final int P_DEP_FILENAME = 134;
    public static final int P_DEP_NAME = 133;
    public static final int P_DEP_PATH = 143;
    public static final int P_DEP_START = 138;
    public static final int P_DEP_START_INFO = 139;
    public static final int P_DIFFERENCES = 135;
    public static final int P_DISPOSITION_ATTACHMENT = 129;
    public static final int P_DISPOSITION_FROM_DATA = 128;
    public static final int P_DISPOSITION_INLINE = 130;
    public static final int P_DOMAIN = 156;
    public static final int P_FILENAME = 152;
    public static final int P_LEVEL = 130;
    public static final int P_MAC = 146;
    public static final int P_MAX_AGE = 142;
    public static final int P_MODIFICATION_DATE = 148;
    public static final int P_NAME = 151;
    public static final int P_PADDING = 136;
    public static final int P_PATH = 157;
    public static final int P_Q = 128;
    public static final String P_QUOTED_PRINTABLE = "quoted-printable";
    public static final int P_READ_DATE = 149;
    public static final int P_SEC = 145;
    public static final int P_SECURE = 144;
    public static final int P_SIZE = 150;
    public static final int P_START = 153;
    public static final int P_START_INFO = 154;
    public static final int P_TYPE = 131;
    private static final String TAG = "PduPart";
    private byte[] mPartData = null;
    private Map<Integer, Object> mPartHeader = new HashMap<Integer, Object>();
    private Uri mUri = null;

    static {
        DISPOSITION_FROM_DATA = "from-data".getBytes();
        DISPOSITION_ATTACHMENT = "attachment".getBytes();
        DISPOSITION_INLINE = "inline".getBytes();
    }

    public String generateLocation() {
        byte[] arrby = (byte[])this.mPartHeader.get(151);
        Object object = arrby;
        if (arrby == null) {
            arrby = (byte[])this.mPartHeader.get(152);
            object = arrby;
            if (arrby == null) {
                object = (byte[])this.mPartHeader.get(142);
            }
        }
        if (object == null) {
            arrby = (byte[])this.mPartHeader.get(192);
            object = new StringBuilder();
            ((StringBuilder)object).append("cid:");
            ((StringBuilder)object).append(new String(arrby));
            return ((StringBuilder)object).toString();
        }
        return new String((byte[])object);
    }

    public int getCharset() {
        Integer n = (Integer)this.mPartHeader.get(129);
        if (n == null) {
            return 0;
        }
        return n;
    }

    public byte[] getContentDisposition() {
        return (byte[])this.mPartHeader.get(197);
    }

    public byte[] getContentId() {
        return (byte[])this.mPartHeader.get(192);
    }

    public byte[] getContentLocation() {
        return (byte[])this.mPartHeader.get(142);
    }

    public byte[] getContentTransferEncoding() {
        return (byte[])this.mPartHeader.get(200);
    }

    public byte[] getContentType() {
        return (byte[])this.mPartHeader.get(145);
    }

    public byte[] getData() {
        byte[] arrby = this.mPartData;
        if (arrby == null) {
            return null;
        }
        byte[] arrby2 = new byte[arrby.length];
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        return arrby2;
    }

    public int getDataLength() {
        byte[] arrby = this.mPartData;
        if (arrby != null) {
            return arrby.length;
        }
        return 0;
    }

    public Uri getDataUri() {
        return this.mUri;
    }

    public byte[] getFilename() {
        return (byte[])this.mPartHeader.get(152);
    }

    public byte[] getName() {
        return (byte[])this.mPartHeader.get(151);
    }

    public void setCharset(int n) {
        this.mPartHeader.put(129, n);
    }

    public void setContentDisposition(byte[] arrby) {
        if (arrby != null) {
            this.mPartHeader.put(197, arrby);
            return;
        }
        throw new NullPointerException("null content-disposition");
    }

    public void setContentId(byte[] arrby) {
        if (arrby != null && arrby.length != 0) {
            if (arrby.length > 1 && (char)arrby[0] == '<' && (char)arrby[arrby.length - 1] == '>') {
                this.mPartHeader.put(192, arrby);
                return;
            }
            byte[] arrby2 = new byte[arrby.length + 2];
            arrby2[0] = (byte)60;
            arrby2[arrby2.length - 1] = (byte)62;
            System.arraycopy(arrby, 0, arrby2, 1, arrby.length);
            this.mPartHeader.put(192, arrby2);
            return;
        }
        throw new IllegalArgumentException("Content-Id may not be null or empty.");
    }

    public void setContentLocation(byte[] arrby) {
        if (arrby != null) {
            this.mPartHeader.put(142, arrby);
            return;
        }
        throw new NullPointerException("null content-location");
    }

    public void setContentTransferEncoding(byte[] arrby) {
        if (arrby != null) {
            this.mPartHeader.put(200, arrby);
            return;
        }
        throw new NullPointerException("null content-transfer-encoding");
    }

    public void setContentType(byte[] arrby) {
        if (arrby != null) {
            this.mPartHeader.put(145, arrby);
            return;
        }
        throw new NullPointerException("null content-type");
    }

    public void setData(byte[] arrby) {
        if (arrby == null) {
            return;
        }
        this.mPartData = new byte[arrby.length];
        System.arraycopy(arrby, 0, this.mPartData, 0, arrby.length);
    }

    public void setDataUri(Uri uri) {
        this.mUri = uri;
    }

    public void setFilename(byte[] arrby) {
        if (arrby != null) {
            this.mPartHeader.put(152, arrby);
            return;
        }
        throw new NullPointerException("null content-id");
    }

    public void setName(byte[] arrby) {
        if (arrby != null) {
            this.mPartHeader.put(151, arrby);
            return;
        }
        throw new NullPointerException("null content-id");
    }
}

