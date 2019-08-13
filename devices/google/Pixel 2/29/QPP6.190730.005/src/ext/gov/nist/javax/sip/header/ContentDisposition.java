/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.header.ContentDispositionHeader;

public final class ContentDisposition
extends ParametersHeader
implements ContentDispositionHeader {
    private static final long serialVersionUID = 835596496276127003L;
    protected String dispositionType;

    public ContentDisposition() {
        super("Content-Disposition");
    }

    @Override
    public String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer(this.dispositionType);
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public String getContentDisposition() {
        return this.encodeBody();
    }

    @Override
    public String getDispositionType() {
        return this.dispositionType;
    }

    @Override
    public String getHandling() {
        return this.getParameter("handling");
    }

    @Override
    public void setDispositionType(String string) throws ParseException {
        if (string != null) {
            this.dispositionType = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, ContentDisposition, setDispositionType(), the dispositionType parameter is null");
    }

    @Override
    public void setHandling(String string) throws ParseException {
        if (string != null) {
            this.setParameter("handling", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, ContentDisposition, setHandling(), the handling parameter is null");
    }
}

