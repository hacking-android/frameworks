/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.gsm;

import android.telephony.PhoneNumberUtils;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsAddress;
import java.text.ParseException;

public class GsmSmsAddress
extends SmsAddress {
    static final int OFFSET_ADDRESS_LENGTH = 0;
    static final int OFFSET_ADDRESS_VALUE = 2;
    static final int OFFSET_TOA = 1;

    public GsmSmsAddress(byte[] object, int n, int n2) throws ParseException {
        this.origBytes = new byte[n2];
        System.arraycopy(object, n, this.origBytes, 0, n2);
        int n3 = this.origBytes[0] & 255;
        int n4 = this.origBytes[1] & 255;
        this.ton = n4 >> 4 & 7;
        if ((n4 & 128) == 128) {
            if (this.isAlphanumeric()) {
                n = n3 * 4 / 7;
                this.address = GsmAlphabet.gsm7BitPackedToString(this.origBytes, 2, n);
            } else {
                n = this.origBytes[n2 - 1];
                if ((n3 & 1) == 1) {
                    object = this.origBytes;
                    n3 = n2 - 1;
                    object[n3] = (byte)(object[n3] | 240);
                }
                this.address = PhoneNumberUtils.calledPartyBCDToString(this.origBytes, 1, n2 - 1, 2);
                this.origBytes[n2 - 1] = (byte)n;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid TOA - high bit must be set. toa = ");
        ((StringBuilder)object).append(n4);
        throw new ParseException(((StringBuilder)object).toString(), n + 1);
    }

    @Override
    public String getAddressString() {
        return this.address;
    }

    @Override
    public boolean isAlphanumeric() {
        boolean bl = this.ton == 5;
        return bl;
    }

    public boolean isCphsVoiceMessageClear() {
        boolean bl = this.isCphsVoiceMessageIndicatorAddress() && (this.origBytes[2] & 255) == 16;
        return bl;
    }

    public boolean isCphsVoiceMessageIndicatorAddress() {
        boolean bl;
        block0 : {
            byte[] arrby = this.origBytes;
            bl = false;
            if ((arrby[0] & 255) != 4 || !this.isAlphanumeric() || (this.origBytes[1] & 15) != 0) break block0;
            bl = true;
        }
        return bl;
    }

    public boolean isCphsVoiceMessageSet() {
        boolean bl = this.isCphsVoiceMessageIndicatorAddress() && (this.origBytes[2] & 255) == 17;
        return bl;
    }

    @Override
    public boolean isNetworkSpecific() {
        boolean bl = this.ton == 3;
        return bl;
    }
}

