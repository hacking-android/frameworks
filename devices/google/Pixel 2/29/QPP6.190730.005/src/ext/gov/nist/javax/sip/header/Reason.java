/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ReasonHeader;

public class Reason
extends ParametersHeader
implements ReasonHeader {
    private static final long serialVersionUID = -8903376965568297388L;
    public final String CAUSE;
    public final String TEXT;
    protected String protocol;

    public Reason() {
        super("Reason");
        this.TEXT = "text";
        this.CAUSE = "cause";
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.protocol);
        if (this.parameters != null && !this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    @Override
    public int getCause() {
        return this.getParameterAsInt("cause");
    }

    @Override
    public String getName() {
        return "Reason";
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getText() {
        return this.parameters.getParameter("text");
    }

    @Override
    public void setCause(int n) throws InvalidArgumentException {
        this.parameters.set("cause", n);
    }

    @Override
    public void setProtocol(String string) throws ParseException {
        this.protocol = string;
    }

    @Override
    public void setText(String string) throws ParseException {
        String string2 = string;
        if (string.charAt(0) != '\"') {
            string2 = Utils.getQuotedString(string);
        }
        this.parameters.set("text", string2);
    }
}

