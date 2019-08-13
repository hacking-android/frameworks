/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DateUtil;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class ASN1UTCTime
extends ASN1Primitive {
    private byte[] time;

    public ASN1UTCTime(String string) {
        this.time = Strings.toByteArray(string);
        try {
            this.getDate();
            return;
        }
        catch (ParseException parseException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid date string: ");
            stringBuilder.append(parseException.getMessage());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public ASN1UTCTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1UTCTime(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss'Z'", Locale.US);
        simpleDateFormat.setCalendar(Calendar.getInstance(locale));
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(simpleDateFormat.format(date));
    }

    ASN1UTCTime(byte[] arrby) {
        this.time = arrby;
    }

    public static ASN1UTCTime getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof ASN1UTCTime)) {
            return new ASN1UTCTime(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return ASN1UTCTime.getInstance(aSN1Primitive);
    }

    public static ASN1UTCTime getInstance(Object object) {
        if (object != null && !(object instanceof ASN1UTCTime)) {
            if (object instanceof byte[]) {
                try {
                    object = (ASN1UTCTime)ASN1UTCTime.fromByteArray((byte[])object);
                    return object;
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("encoding error in getInstance: ");
                    ((StringBuilder)object).append(exception.toString());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (ASN1UTCTime)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1UTCTime)) {
            return false;
        }
        return Arrays.areEqual(this.time, ((ASN1UTCTime)aSN1Primitive).time);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.write(23);
        int n = this.time.length;
        aSN1OutputStream.writeLength(n);
        for (int i = 0; i != n; ++i) {
            aSN1OutputStream.write(this.time[i]);
        }
    }

    @Override
    int encodedLength() {
        int n = this.time.length;
        return StreamUtil.calculateBodyLength(n) + 1 + n;
    }

    public Date getAdjustedDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssz", Locale.US);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        return DateUtil.epochAdjust(simpleDateFormat.parse(this.getAdjustedTime()));
    }

    public String getAdjustedTime() {
        String string = this.getTime();
        if (string.charAt(0) < '5') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("20");
            stringBuilder.append(string);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("19");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public Date getDate() throws ParseException {
        return DateUtil.epochAdjust(new SimpleDateFormat("yyMMddHHmmssz", Locale.US).parse(this.getTime()));
    }

    public String getTime() {
        int n;
        CharSequence charSequence;
        String string = Strings.fromByteArray(this.time);
        if (string.indexOf(45) < 0 && string.indexOf(43) < 0) {
            if (string.length() == 11) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string.substring(0, 10));
                stringBuilder.append("00GMT+00:00");
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.substring(0, 12));
            stringBuilder.append("GMT+00:00");
            return stringBuilder.toString();
        }
        int n2 = n = string.indexOf(45);
        if (n < 0) {
            n2 = string.indexOf(43);
        }
        CharSequence charSequence2 = charSequence = string;
        if (n2 == string.length() - 3) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("00");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        if (n2 == 10) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(((String)charSequence2).substring(0, 10));
            ((StringBuilder)charSequence).append("00GMT");
            ((StringBuilder)charSequence).append(((String)charSequence2).substring(10, 13));
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(((String)charSequence2).substring(13, 15));
            return ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(((String)charSequence2).substring(0, 12));
        ((StringBuilder)charSequence).append("GMT");
        ((StringBuilder)charSequence).append(((String)charSequence2).substring(12, 15));
        ((StringBuilder)charSequence).append(":");
        ((StringBuilder)charSequence).append(((String)charSequence2).substring(15, 17));
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.time);
    }

    @Override
    boolean isConstructed() {
        return false;
    }

    public String toString() {
        return Strings.fromByteArray(this.time);
    }
}

