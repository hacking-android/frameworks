/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.GsmAlphabet
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.ResponseData;
import java.io.ByteArrayOutputStream;

class LanguageResponseData
extends ResponseData {
    private String mLang;

    public LanguageResponseData(String string) {
        this.mLang = string;
    }

    @Override
    public void format(ByteArrayOutputStream byteArrayOutputStream) {
        if (byteArrayOutputStream == null) {
            return;
        }
        byteArrayOutputStream.write(ComprehensionTlvTag.LANGUAGE.value() | 128);
        byte[] arrby = this.mLang;
        arrby = arrby != null && arrby.length() > 0 ? GsmAlphabet.stringToGsm8BitPacked((String)this.mLang) : new byte[0];
        byteArrayOutputStream.write(arrby.length);
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            byteArrayOutputStream.write(arrby[i]);
        }
    }
}

