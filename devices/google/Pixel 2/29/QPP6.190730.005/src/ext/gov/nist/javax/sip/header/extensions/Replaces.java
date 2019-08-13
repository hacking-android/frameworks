/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.CallIdentifier;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.extensions.ReplacesHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class Replaces
extends ParametersHeader
implements ExtensionHeader,
ReplacesHeader {
    public static final String NAME = "Replaces";
    private static final long serialVersionUID = 8765762413224043300L;
    public String callId;
    public CallIdentifier callIdentifier;

    public Replaces() {
        super(NAME);
    }

    public Replaces(String string) throws IllegalArgumentException {
        super(NAME);
        this.callIdentifier = new CallIdentifier(string);
    }

    @Override
    public String encodeBody() {
        if (this.callId == null) {
            return null;
        }
        String string = this.callId;
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
    public String getCallId() {
        return this.callId;
    }

    public CallIdentifier getCallIdentifer() {
        return this.callIdentifier;
    }

    @Override
    public String getFromTag() {
        if (this.parameters == null) {
            return null;
        }
        return this.getParameter("from-tag");
    }

    @Override
    public String getToTag() {
        if (this.parameters == null) {
            return null;
        }
        return this.getParameter("to-tag");
    }

    public boolean hasFromTag() {
        return this.hasParameter("from-tag");
    }

    public boolean hasToTag() {
        return this.hasParameter("to-tag");
    }

    public void removeFromTag() {
        this.parameters.delete("from-tag");
    }

    public void removeToTag() {
        this.parameters.delete("to-tag");
    }

    @Override
    public void setCallId(String string) {
        this.callId = string;
    }

    public void setCallIdentifier(CallIdentifier callIdentifier) {
        this.callIdentifier = callIdentifier;
    }

    @Override
    public void setFromTag(String string) throws ParseException {
        if (string != null) {
            if (!string.trim().equals("")) {
                this.setParameter("from-tag", string);
                return;
            }
            throw new ParseException("bad tag", 0);
        }
        throw new NullPointerException("null tag ");
    }

    @Override
    public void setToTag(String string) throws ParseException {
        if (string != null) {
            if (!string.trim().equals("")) {
                this.setParameter("to-tag", string);
                return;
            }
            throw new ParseException("bad tag", 0);
        }
        throw new NullPointerException("null tag ");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

