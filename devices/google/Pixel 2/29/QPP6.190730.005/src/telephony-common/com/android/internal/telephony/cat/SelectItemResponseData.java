/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.ResponseData;
import java.io.ByteArrayOutputStream;

class SelectItemResponseData
extends ResponseData {
    private int mId;

    public SelectItemResponseData(int n) {
        this.mId = n;
    }

    @Override
    public void format(ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(ComprehensionTlvTag.ITEM_ID.value() | 128);
        byteArrayOutputStream.write(1);
        byteArrayOutputStream.write(this.mId);
    }
}

