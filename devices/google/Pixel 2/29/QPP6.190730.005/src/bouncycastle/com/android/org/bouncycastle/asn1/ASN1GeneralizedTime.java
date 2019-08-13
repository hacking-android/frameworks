/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERGeneralizedTime;
import com.android.org.bouncycastle.asn1.DateUtil;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class ASN1GeneralizedTime
extends ASN1Primitive {
    protected byte[] time;

    public ASN1GeneralizedTime(String charSequence) {
        this.time = Strings.toByteArray((String)charSequence);
        try {
            this.getDate();
            return;
        }
        catch (ParseException parseException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("invalid date string: ");
            ((StringBuilder)charSequence).append(parseException.getMessage());
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
    }

    public ASN1GeneralizedTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1GeneralizedTime(Date date, Locale cloneable) {
        cloneable = new SimpleDateFormat("yyyyMMddHHmmss'Z'", Locale.US);
        ((DateFormat)cloneable).setCalendar(Calendar.getInstance(Locale.US));
        ((DateFormat)cloneable).setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(((DateFormat)cloneable).format(date));
    }

    ASN1GeneralizedTime(byte[] arrby) {
        this.time = arrby;
    }

    private String calculateGMTOffset() {
        int n;
        int n2;
        String string;
        Serializable serializable;
        block5 : {
            int n3;
            string = "+";
            serializable = TimeZone.getDefault();
            n2 = n3 = ((TimeZone)serializable).getRawOffset();
            if (n3 < 0) {
                string = "-";
                n2 = -n3;
            }
            n3 = n2 / 3600000;
            n = (n2 - n3 * 60 * 60 * 1000) / 60000;
            n2 = n3;
            if (!((TimeZone)serializable).useDaylightTime()) break block5;
            n2 = n3;
            try {
                if (((TimeZone)serializable).inDaylightTime(this.getDate())) {
                    boolean bl = string.equals("+");
                    n2 = bl ? 1 : -1;
                    n2 = n3 + n2;
                }
            }
            catch (ParseException parseException) {
                n2 = n3;
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("GMT");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(this.convert(n2));
        ((StringBuilder)serializable).append(":");
        ((StringBuilder)serializable).append(this.convert(n));
        return ((StringBuilder)serializable).toString();
    }

    private String convert(int n) {
        if (n < 10) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0");
            stringBuilder.append(n);
            return stringBuilder.toString();
        }
        return Integer.toString(n);
    }

    public static ASN1GeneralizedTime getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof ASN1GeneralizedTime)) {
            return new ASN1GeneralizedTime(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return ASN1GeneralizedTime.getInstance(aSN1Primitive);
    }

    public static ASN1GeneralizedTime getInstance(Object object) {
        if (object != null && !(object instanceof ASN1GeneralizedTime)) {
            if (object instanceof byte[]) {
                try {
                    object = (ASN1GeneralizedTime)ASN1GeneralizedTime.fromByteArray((byte[])object);
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
        return (ASN1GeneralizedTime)object;
    }

    private boolean isDigit(int n) {
        byte[] arrby = this.time;
        boolean bl = arrby.length > n && arrby[n] >= 48 && arrby[n] <= 57;
        return bl;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1GeneralizedTime)) {
            return false;
        }
        return Arrays.areEqual(this.time, ((ASN1GeneralizedTime)aSN1Primitive).time);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(24, this.time);
    }

    @Override
    int encodedLength() {
        int n = this.time.length;
        return StreamUtil.calculateBodyLength(n) + 1 + n;
    }

    public Date getDate() throws ParseException {
        Object object;
        Object object2 = Strings.fromByteArray(this.time);
        String string = object2;
        if (((String)object2).endsWith("Z")) {
            object2 = this.hasFractionalSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'", Locale.US) : (this.hasSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss'Z'", Locale.US) : (this.hasMinutes() ? new SimpleDateFormat("yyyyMMddHHmm'Z'", Locale.US) : new SimpleDateFormat("yyyyMMddHH'Z'", Locale.US)));
            ((DateFormat)object2).setTimeZone(new SimpleTimeZone(0, "Z"));
            object = object2;
        } else if (((String)object2).indexOf(45) <= 0 && ((String)object2).indexOf(43) <= 0) {
            object2 = this.hasFractionalSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS", Locale.US) : (this.hasSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss", Locale.US) : (this.hasMinutes() ? new SimpleDateFormat("yyyyMMddHHmm", Locale.US) : new SimpleDateFormat("yyyyMMddHH", Locale.US)));
            ((DateFormat)object2).setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
            object = object2;
        } else {
            string = this.getTime();
            object2 = this.hasFractionalSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss.SSSz", Locale.US) : (this.hasSeconds() ? new SimpleDateFormat("yyyyMMddHHmmssz", Locale.US) : (this.hasMinutes() ? new SimpleDateFormat("yyyyMMddHHmmz", Locale.US) : new SimpleDateFormat("yyyyMMddHHz", Locale.US)));
            ((DateFormat)object2).setTimeZone(new SimpleTimeZone(0, "Z"));
            object = object2;
        }
        object2 = string;
        if (this.hasFractionalSeconds()) {
            char c;
            int n;
            CharSequence charSequence = string.substring(14);
            for (n = 1; n < ((String)charSequence).length() && '0' <= (c = ((String)charSequence).charAt(n)) && c <= '9'; ++n) {
            }
            if (n - 1 > 3) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(((String)charSequence).substring(0, 4));
                ((StringBuilder)object2).append(((String)charSequence).substring(n));
                object2 = ((StringBuilder)object2).toString();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string.substring(0, 14));
                ((StringBuilder)charSequence).append((String)object2);
                object2 = ((StringBuilder)charSequence).toString();
            } else if (n - 1 == 1) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(((String)charSequence).substring(0, n));
                ((StringBuilder)object2).append("00");
                ((StringBuilder)object2).append(((String)charSequence).substring(n));
                object2 = ((StringBuilder)object2).toString();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string.substring(0, 14));
                ((StringBuilder)charSequence).append((String)object2);
                object2 = ((StringBuilder)charSequence).toString();
            } else {
                object2 = string;
                if (n - 1 == 2) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(((String)charSequence).substring(0, n));
                    ((StringBuilder)object2).append("0");
                    ((StringBuilder)object2).append(((String)charSequence).substring(n));
                    charSequence = ((StringBuilder)object2).toString();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(string.substring(0, 14));
                    ((StringBuilder)object2).append((String)charSequence);
                    object2 = ((StringBuilder)object2).toString();
                }
            }
        }
        return DateUtil.epochAdjust(((DateFormat)object).parse((String)object2));
    }

    public String getTime() {
        String string = Strings.fromByteArray(this.time);
        if (string.charAt(string.length() - 1) == 'Z') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.substring(0, string.length() - 1));
            stringBuilder.append("GMT+00:00");
            return stringBuilder.toString();
        }
        int n = string.length() - 5;
        char c = string.charAt(n);
        if (c != '-' && c != '+') {
            n = string.length() - 3;
            c = string.charAt(n);
            if (c != '-' && c != '+') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(this.calculateGMTOffset());
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.substring(0, n));
            stringBuilder.append("GMT");
            stringBuilder.append(string.substring(n));
            stringBuilder.append(":00");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string.substring(0, n));
        stringBuilder.append("GMT");
        stringBuilder.append(string.substring(n, n + 3));
        stringBuilder.append(":");
        stringBuilder.append(string.substring(n + 3));
        return stringBuilder.toString();
    }

    public String getTimeString() {
        return Strings.fromByteArray(this.time);
    }

    protected boolean hasFractionalSeconds() {
        byte[] arrby;
        for (int i = 0; i != (arrby = this.time).length; ++i) {
            if (arrby[i] != 46 || i != 14) continue;
            return true;
        }
        return false;
    }

    protected boolean hasMinutes() {
        boolean bl = this.isDigit(10) && this.isDigit(11);
        return bl;
    }

    protected boolean hasSeconds() {
        boolean bl = this.isDigit(12) && this.isDigit(13);
        return bl;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.time);
    }

    @Override
    boolean isConstructed() {
        return false;
    }

    @Override
    ASN1Primitive toDERObject() {
        return new DERGeneralizedTime(this.time);
    }
}

