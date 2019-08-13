/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.mms.pdu;

import android.util.Log;
import com.google.android.mms.pdu.CharacterSets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EncodedStringValue
implements Cloneable {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "EncodedStringValue";
    private int mCharacterSet;
    private byte[] mData;

    public EncodedStringValue(int n, byte[] arrby) {
        if (arrby != null) {
            this.mCharacterSet = n;
            this.mData = new byte[arrby.length];
            System.arraycopy(arrby, 0, this.mData, 0, arrby.length);
            return;
        }
        throw new NullPointerException("EncodedStringValue: Text-string is null.");
    }

    public EncodedStringValue(String string) {
        try {
            this.mData = string.getBytes("utf-8");
            this.mCharacterSet = 106;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.e((String)TAG, (String)"Default encoding must be supported.", (Throwable)unsupportedEncodingException);
        }
    }

    public EncodedStringValue(byte[] arrby) {
        this(106, arrby);
    }

    public static String concat(EncodedStringValue[] arrencodedStringValue) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrencodedStringValue.length - 1;
        for (int i = 0; i <= n; ++i) {
            stringBuilder.append(arrencodedStringValue[i].getString());
            if (i >= n) continue;
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }

    public static EncodedStringValue copy(EncodedStringValue encodedStringValue) {
        if (encodedStringValue == null) {
            return null;
        }
        return new EncodedStringValue(encodedStringValue.mCharacterSet, encodedStringValue.mData);
    }

    public static EncodedStringValue[] encodeStrings(String[] arrstring) {
        int n = arrstring.length;
        if (n > 0) {
            EncodedStringValue[] arrencodedStringValue = new EncodedStringValue[n];
            for (int i = 0; i < n; ++i) {
                arrencodedStringValue[i] = new EncodedStringValue(arrstring[i]);
            }
            return arrencodedStringValue;
        }
        return null;
    }

    public static EncodedStringValue[] extract(String object) {
        int n;
        String[] arrstring = ((String)object).split(";");
        object = new ArrayList();
        for (n = 0; n < arrstring.length; ++n) {
            if (arrstring[n].length() <= 0) continue;
            ((ArrayList)object).add(new EncodedStringValue(arrstring[n]));
        }
        n = ((ArrayList)object).size();
        if (n > 0) {
            return ((ArrayList)object).toArray(new EncodedStringValue[n]);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void appendTextString(byte[] arrby) {
        if (arrby == null) throw new NullPointerException("Text-string is null.");
        if (this.mData == null) {
            this.mData = new byte[arrby.length];
            System.arraycopy(arrby, 0, this.mData, 0, arrby.length);
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.mData);
            byteArrayOutputStream.write(arrby);
            this.mData = byteArrayOutputStream.toByteArray();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            throw new NullPointerException("appendTextString: failed when write a new Text-string");
        }
    }

    public Object clone() throws CloneNotSupportedException {
        super.clone();
        Object object = this.mData;
        int n = ((byte[])object).length;
        byte[] arrby = new byte[n];
        System.arraycopy(object, 0, arrby, 0, n);
        try {
            object = new EncodedStringValue(this.mCharacterSet, arrby);
            return object;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("failed to clone an EncodedStringValue: ");
            ((StringBuilder)object).append(this);
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
            exception.printStackTrace();
            throw new CloneNotSupportedException(exception.getMessage());
        }
    }

    public int getCharacterSet() {
        return this.mCharacterSet;
    }

    public String getString() {
        int n = this.mCharacterSet;
        if (n == 0) {
            return new String(this.mData);
        }
        try {
            String string = CharacterSets.getMimeName(n);
            string = new String(this.mData, string);
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            try {
                String string = new String(this.mData, "iso-8859-1");
                return string;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException2) {
                return new String(this.mData);
            }
        }
    }

    public byte[] getTextString() {
        byte[] arrby = this.mData;
        byte[] arrby2 = new byte[arrby.length];
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        return arrby2;
    }

    public void setCharacterSet(int n) {
        this.mCharacterSet = n;
    }

    public void setTextString(byte[] arrby) {
        if (arrby != null) {
            this.mData = new byte[arrby.length];
            System.arraycopy(arrby, 0, this.mData, 0, arrby.length);
            return;
        }
        throw new NullPointerException("EncodedStringValue: Text-string is null.");
    }

    public EncodedStringValue[] split(String arrstring) {
        arrstring = this.getString().split((String)arrstring);
        EncodedStringValue[] arrencodedStringValue = new EncodedStringValue[arrstring.length];
        for (int i = 0; i < arrencodedStringValue.length; ++i) {
            try {
                arrencodedStringValue[i] = new EncodedStringValue(this.mCharacterSet, arrstring[i].getBytes());
                continue;
            }
            catch (NullPointerException nullPointerException) {
                return null;
            }
        }
        return arrencodedStringValue;
    }
}

