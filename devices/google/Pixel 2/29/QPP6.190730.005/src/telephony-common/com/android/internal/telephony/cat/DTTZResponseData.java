/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.SystemProperties
 *  android.text.TextUtils
 */
package com.android.internal.telephony.cat;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.CatLog;
import com.android.internal.telephony.cat.ResponseData;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.TimeZone;

class DTTZResponseData
extends ResponseData {
    private Calendar mCalendar;

    public DTTZResponseData(Calendar calendar) {
        this.mCalendar = calendar;
    }

    private byte byteToBCD(int n) {
        if (n < 0 && n > 99) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Err: byteToBCD conversion Value is ");
            stringBuilder.append(n);
            stringBuilder.append(" Value has to be between 0 and 99");
            CatLog.d(this, stringBuilder.toString());
            return 0;
        }
        return (byte)(n / 10 | n % 10 << 4);
    }

    private byte getTZOffSetByte(long l) {
        byte by = 1;
        byte by2 = l < 0L ? (byte)1 : 0;
        l /= 900000L;
        if (by2 != 0) {
            by = -1;
        }
        by = this.byteToBCD((int)((long)by * l));
        byte by3 = by2 != 0 ? (by2 = (byte)(by | 8)) : by;
        return by3;
    }

    @Override
    public void format(ByteArrayOutputStream byteArrayOutputStream) {
        if (byteArrayOutputStream == null) {
            return;
        }
        byteArrayOutputStream.write(AppInterface.CommandType.PROVIDE_LOCAL_INFORMATION.value() | 128);
        byte[] arrby = new byte[8];
        int n = 0;
        arrby[0] = (byte)7;
        if (this.mCalendar == null) {
            this.mCalendar = Calendar.getInstance();
        }
        arrby[1] = this.byteToBCD(this.mCalendar.get(1) % 100);
        arrby[2] = this.byteToBCD(this.mCalendar.get(2) + 1);
        arrby[3] = this.byteToBCD(this.mCalendar.get(5));
        arrby[4] = this.byteToBCD(this.mCalendar.get(11));
        arrby[5] = this.byteToBCD(this.mCalendar.get(12));
        arrby[6] = this.byteToBCD(this.mCalendar.get(13));
        Object object = SystemProperties.get((String)"persist.sys.timezone", (String)"");
        if (TextUtils.isEmpty((CharSequence)object)) {
            arrby[7] = (byte)-1;
        } else {
            object = TimeZone.getTimeZone((String)object);
            arrby[7] = this.getTZOffSetByte(((TimeZone)object).getRawOffset() + ((TimeZone)object).getDSTSavings());
        }
        int n2 = arrby.length;
        while (n < n2) {
            byteArrayOutputStream.write(arrby[n]);
            ++n;
        }
    }
}

