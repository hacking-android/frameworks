/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface JoinHeader
extends Parameters,
Header {
    public static final String NAME = "Join";

    public String getCallId();

    public String getFromTag();

    public String getToTag();

    public void setCallId(String var1) throws ParseException;

    public void setFromTag(String var1) throws ParseException;

    public void setToTag(String var1) throws ParseException;
}

