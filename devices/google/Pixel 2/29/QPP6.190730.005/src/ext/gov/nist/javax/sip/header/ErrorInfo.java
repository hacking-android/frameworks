/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.header.ErrorInfoHeader;

public final class ErrorInfo
extends ParametersHeader
implements ErrorInfoHeader {
    private static final long serialVersionUID = -6347702901964436362L;
    protected GenericURI errorInfo;

    public ErrorInfo() {
        super("Error-Info");
    }

    public ErrorInfo(GenericURI genericURI) {
        this();
        this.errorInfo = genericURI;
    }

    @Override
    public Object clone() {
        ErrorInfo errorInfo = (ErrorInfo)super.clone();
        GenericURI genericURI = this.errorInfo;
        if (genericURI != null) {
            errorInfo.errorInfo = (GenericURI)genericURI.clone();
        }
        return errorInfo;
    }

    @Override
    public String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer("<");
        stringBuffer.append(this.errorInfo.toString());
        stringBuffer = stringBuffer.append(">");
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    @Override
    public URI getErrorInfo() {
        return this.errorInfo;
    }

    @Override
    public String getErrorMessage() {
        return this.getParameter("message");
    }

    @Override
    public void setErrorInfo(URI uRI) {
        this.errorInfo = (GenericURI)uRI;
    }

    @Override
    public void setErrorMessage(String string) throws ParseException {
        if (string != null) {
            this.setParameter("message", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception , ErrorInfoHeader, setErrorMessage(), the message parameter is null");
    }
}

