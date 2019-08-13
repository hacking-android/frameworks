/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.extensions.SessionExpiresHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ExtensionHeader;

public final class SessionExpires
extends ParametersHeader
implements ExtensionHeader,
SessionExpiresHeader {
    public static final String NAME = "Session-Expires";
    public static final String REFRESHER = "refresher";
    private static final long serialVersionUID = 8765762413224043300L;
    public int expires;

    public SessionExpires() {
        super(NAME);
    }

    @Override
    protected String encodeBody() {
        String string = Integer.toString(this.expires);
        CharSequence charSequence = string;
        if (!this.parameters.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(";");
            ((StringBuilder)charSequence).append(this.parameters.encode());
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    @Override
    public int getExpires() {
        return this.expires;
    }

    @Override
    public String getRefresher() {
        return this.parameters.getParameter(REFRESHER);
    }

    @Override
    public void setExpires(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.expires = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad argument ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public void setRefresher(String string) {
        this.parameters.set(REFRESHER, string);
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

