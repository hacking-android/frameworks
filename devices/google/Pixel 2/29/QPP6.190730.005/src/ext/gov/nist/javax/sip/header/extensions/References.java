/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.extensions.ReferencesHeader;
import java.text.ParseException;
import java.util.Iterator;
import javax.sip.header.ExtensionHeader;

public class References
extends ParametersHeader
implements ReferencesHeader,
ExtensionHeader {
    private static final long serialVersionUID = 8536961681006637622L;
    private String callId;

    public References() {
        super("References");
    }

    @Override
    protected String encodeBody() {
        if (this.parameters.isEmpty()) {
            return this.callId;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.callId);
        stringBuilder.append(";");
        stringBuilder.append(this.parameters.encode());
        return stringBuilder.toString();
    }

    @Override
    public String getCallId() {
        return this.callId;
    }

    @Override
    public String getName() {
        return "References";
    }

    @Override
    public String getParameter(String string) {
        return super.getParameter(string);
    }

    @Override
    public Iterator getParameterNames() {
        return super.getParameterNames();
    }

    @Override
    public String getRel() {
        return this.getParameter("rel");
    }

    @Override
    public void removeParameter(String string) {
        super.removeParameter(string);
    }

    @Override
    public void setCallId(String string) {
        this.callId = string;
    }

    @Override
    public void setParameter(String string, String string2) throws ParseException {
        super.setParameter(string, string2);
    }

    @Override
    public void setRel(String string) throws ParseException {
        if (string != null) {
            this.setParameter("rel", string);
        }
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new UnsupportedOperationException("operation not supported");
    }
}

