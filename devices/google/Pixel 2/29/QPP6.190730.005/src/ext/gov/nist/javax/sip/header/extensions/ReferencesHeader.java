/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface ReferencesHeader
extends Parameters,
Header {
    public static final String CHAIN = "chain";
    public static final String INQUIRY = "inquiry";
    public static final String NAME = "References";
    public static final String REFER = "refer";
    public static final String REL = "rel";
    public static final String SEQUEL = "sequel";
    public static final String SERVICE = "service";
    public static final String XFER = "xfer";

    public String getCallId();

    public String getRel();

    public void setCallId(String var1) throws ParseException;

    public void setRel(String var1) throws ParseException;
}

