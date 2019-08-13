/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.org.bouncycastle.asn1.DERGeneralizedTime;
import com.android.org.bouncycastle.asn1.DERUTCTime;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Time
extends ASN1Object
implements ASN1Choice {
    ASN1Primitive time;

    public Time(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1UTCTime) && !(aSN1Primitive instanceof ASN1GeneralizedTime)) {
            throw new IllegalArgumentException("unknown object passed to Time");
        }
        this.time = aSN1Primitive;
    }

    public Time(Date object) {
        Serializable serializable = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        simpleDateFormat.setTimeZone((TimeZone)serializable);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(simpleDateFormat.format((Date)object));
        ((StringBuilder)serializable).append("Z");
        object = ((StringBuilder)serializable).toString();
        int n = Integer.parseInt(((String)object).substring(0, 4));
        this.time = n >= 1950 && n <= 2049 ? new DERUTCTime(((String)object).substring(2)) : new DERGeneralizedTime((String)object);
    }

    public Time(Date object, Locale serializable) {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        simpleDateFormat.setCalendar(Calendar.getInstance((Locale)serializable));
        simpleDateFormat.setTimeZone(simpleTimeZone);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(simpleDateFormat.format((Date)object));
        ((StringBuilder)serializable).append("Z");
        object = ((StringBuilder)serializable).toString();
        int n = Integer.parseInt(((String)object).substring(0, 4));
        this.time = n >= 1950 && n <= 2049 ? new DERUTCTime(((String)object).substring(2)) : new DERGeneralizedTime((String)object);
    }

    public static Time getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return Time.getInstance(aSN1TaggedObject.getObject());
    }

    public static Time getInstance(Object object) {
        if (object != null && !(object instanceof Time)) {
            if (object instanceof ASN1UTCTime) {
                return new Time((ASN1UTCTime)object);
            }
            if (object instanceof ASN1GeneralizedTime) {
                return new Time((ASN1GeneralizedTime)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown object in factory: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (Time)object;
    }

    public Date getDate() {
        try {
            if (this.time instanceof ASN1UTCTime) {
                return ((ASN1UTCTime)this.time).getAdjustedDate();
            }
            Date date = ((ASN1GeneralizedTime)this.time).getDate();
            return date;
        }
        catch (ParseException parseException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid date string: ");
            stringBuilder.append(parseException.getMessage());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public String getTime() {
        ASN1Primitive aSN1Primitive = this.time;
        if (aSN1Primitive instanceof ASN1UTCTime) {
            return ((ASN1UTCTime)aSN1Primitive).getAdjustedTime();
        }
        return ((ASN1GeneralizedTime)aSN1Primitive).getTime();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.time;
    }
}

