/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.WarningHeader;

public class Warning
extends SIPHeader
implements WarningHeader {
    private static final long serialVersionUID = -3433328864230783899L;
    protected String agent;
    protected int code;
    protected String text;

    public Warning() {
        super("Warning");
    }

    @Override
    public String encodeBody() {
        CharSequence charSequence;
        if (this.text != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Integer.toString(this.code));
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(this.agent);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append("\"");
            ((StringBuilder)charSequence).append(this.text);
            ((StringBuilder)charSequence).append("\"");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Integer.toString(this.code));
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(this.agent);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    @Override
    public String getAgent() {
        return this.agent;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setAgent(String string) throws ParseException {
        if (string != null) {
            this.agent = string;
            return;
        }
        throw new NullPointerException("the host parameter in the Warning header is null");
    }

    @Override
    public void setCode(int n) throws InvalidArgumentException {
        if (n > 99 && n < 1000) {
            this.code = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Code parameter in the Warning header is invalid: code=");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public void setText(String string) throws ParseException {
        if (string != null) {
            this.text = string;
            return;
        }
        throw new ParseException("The text parameter in the Warning header is null", 0);
    }
}

