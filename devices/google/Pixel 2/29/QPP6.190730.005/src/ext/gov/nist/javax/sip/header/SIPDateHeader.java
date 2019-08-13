/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPDate;
import gov.nist.javax.sip.header.SIPHeader;
import java.util.Calendar;
import java.util.Date;
import javax.sip.header.DateHeader;

public class SIPDateHeader
extends SIPHeader
implements DateHeader {
    private static final long serialVersionUID = 1734186339037274664L;
    protected SIPDate date;

    public SIPDateHeader() {
        super("Date");
    }

    @Override
    public Object clone() {
        SIPDateHeader sIPDateHeader = (SIPDateHeader)super.clone();
        SIPDate sIPDate = this.date;
        if (sIPDate != null) {
            sIPDateHeader.date = (SIPDate)sIPDate.clone();
        }
        return sIPDateHeader;
    }

    @Override
    public String encodeBody() {
        return this.date.encode();
    }

    @Override
    public Calendar getDate() {
        SIPDate sIPDate = this.date;
        if (sIPDate == null) {
            return null;
        }
        return sIPDate.getJavaCal();
    }

    public void setDate(SIPDate sIPDate) {
        this.date = sIPDate;
    }

    @Override
    public void setDate(Calendar calendar) {
        if (calendar != null) {
            this.date = new SIPDate(calendar.getTime().getTime());
        }
    }
}

