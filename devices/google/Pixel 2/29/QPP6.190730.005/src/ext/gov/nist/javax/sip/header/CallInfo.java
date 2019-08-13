/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.header.CallInfoHeader;

public final class CallInfo
extends ParametersHeader
implements CallInfoHeader {
    private static final long serialVersionUID = -8179246487696752928L;
    protected GenericURI info;

    public CallInfo() {
        super("Call-Info");
    }

    @Override
    public Object clone() {
        CallInfo callInfo = (CallInfo)super.clone();
        GenericURI genericURI = this.info;
        if (genericURI != null) {
            callInfo.info = (GenericURI)genericURI.clone();
        }
        return callInfo;
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append("<");
        this.info.encode(stringBuffer);
        stringBuffer.append(">");
        if (this.parameters != null && !this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public URI getInfo() {
        return this.info;
    }

    @Override
    public String getPurpose() {
        return this.getParameter("purpose");
    }

    @Override
    public void setInfo(URI uRI) {
        this.info = (GenericURI)uRI;
    }

    @Override
    public void setPurpose(String string) {
        if (string != null) {
            try {
                this.setParameter("purpose", string);
            }
            catch (ParseException parseException) {
                // empty catch block
            }
            return;
        }
        throw new NullPointerException("null arg");
    }
}

