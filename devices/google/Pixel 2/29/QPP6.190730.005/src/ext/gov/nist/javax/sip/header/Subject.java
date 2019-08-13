/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.SubjectHeader;

public class Subject
extends SIPHeader
implements SubjectHeader {
    private static final long serialVersionUID = -6479220126758862528L;
    protected String subject;

    public Subject() {
        super("Subject");
    }

    @Override
    public String encodeBody() {
        String string = this.subject;
        if (string != null) {
            return string;
        }
        return "";
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public void setSubject(String string) throws ParseException {
        if (string != null) {
            this.subject = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  Subject, setSubject(), the subject parameter is null");
    }
}

