/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.EncodeException
 *  com.android.internal.telephony.GsmAlphabet
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.ResponseData;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

class GetInkeyInputResponseData
extends ResponseData {
    protected static final byte GET_INKEY_NO = 0;
    protected static final byte GET_INKEY_YES = 1;
    public String mInData;
    private boolean mIsPacked;
    private boolean mIsUcs2;
    private boolean mIsYesNo;
    private boolean mYesNoResponse;

    public GetInkeyInputResponseData(String string, boolean bl, boolean bl2) {
        this.mIsUcs2 = bl;
        this.mIsPacked = bl2;
        this.mInData = string;
        this.mIsYesNo = false;
    }

    public GetInkeyInputResponseData(boolean bl) {
        this.mIsUcs2 = false;
        this.mIsPacked = false;
        this.mInData = "";
        this.mIsYesNo = true;
        this.mYesNoResponse = bl;
    }

    @Override
    public void format(ByteArrayOutputStream byteArrayOutputStream) {
        Object object;
        int n;
        block17 : {
            if (byteArrayOutputStream == null) {
                return;
            }
            byteArrayOutputStream.write(ComprehensionTlvTag.TEXT_STRING.value() | 128);
            boolean bl = this.mIsYesNo;
            n = 0;
            if (bl) {
                object = new byte[]{(byte)(this.mYesNoResponse ? 1 : 0)};
            } else {
                object = this.mInData;
                if (object != null && ((String)object).length() > 0) {
                    try {
                        if (this.mIsUcs2) {
                            object = this.mInData.getBytes("UTF-16BE");
                            break block17;
                        }
                        if (this.mIsPacked) {
                            byte[] arrby = GsmAlphabet.stringToGsm7BitPacked((String)this.mInData, (int)0, (int)0);
                            object = new byte[arrby.length - 1];
                            System.arraycopy(arrby, 1, object, 0, arrby.length - 1);
                            break block17;
                        }
                        object = GsmAlphabet.stringToGsm8BitPacked((String)this.mInData);
                    }
                    catch (EncodeException encodeException) {
                        object = new byte[]{};
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        object = new byte[]{};
                    }
                } else {
                    object = new byte[]{};
                }
            }
        }
        if (((Object)object).length + 1 <= 255) {
            GetInkeyInputResponseData.writeLength(byteArrayOutputStream, ((Object)object).length + 1);
        } else {
            object = new byte[]{};
        }
        if (this.mIsUcs2) {
            byteArrayOutputStream.write(8);
        } else if (this.mIsPacked) {
            byteArrayOutputStream.write(0);
        } else {
            byteArrayOutputStream.write(4);
        }
        int n2 = ((Object)object).length;
        while (n < n2) {
            byteArrayOutputStream.write((int)object[n]);
            ++n;
        }
    }
}

