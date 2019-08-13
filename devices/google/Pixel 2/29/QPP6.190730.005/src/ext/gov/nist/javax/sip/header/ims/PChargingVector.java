/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ims.PChargingVectorHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class PChargingVector
extends ParametersHeader
implements PChargingVectorHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    public PChargingVector() {
        super("P-Charging-Vector");
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        this.getNameValue("icid-value").encode(stringBuffer);
        if (this.parameters.containsKey("icid-generated-at")) {
            stringBuffer.append(";");
            stringBuffer.append("icid-generated-at");
            stringBuffer.append("=");
            stringBuffer.append(this.getICIDGeneratedAt());
        }
        if (this.parameters.containsKey("term-ioi")) {
            stringBuffer.append(";");
            stringBuffer.append("term-ioi");
            stringBuffer.append("=");
            stringBuffer.append(this.getTerminatingIOI());
        }
        if (this.parameters.containsKey("orig-ioi")) {
            stringBuffer.append(";");
            stringBuffer.append("orig-ioi");
            stringBuffer.append("=");
            stringBuffer.append(this.getOriginatingIOI());
        }
        return stringBuffer.toString();
    }

    @Override
    public String getICID() {
        return this.getParameter("icid-value");
    }

    @Override
    public String getICIDGeneratedAt() {
        return this.getParameter("icid-generated-at");
    }

    @Override
    public String getOriginatingIOI() {
        return this.getParameter("orig-ioi");
    }

    @Override
    public String getTerminatingIOI() {
        return this.getParameter("term-ioi");
    }

    @Override
    public void setICID(String string) throws ParseException {
        if (string != null) {
            this.setParameter("icid-value", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Vector, setICID(), the icid parameter is null.");
    }

    @Override
    public void setICIDGeneratedAt(String string) throws ParseException {
        if (string != null) {
            this.setParameter("icid-generated-at", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Vector, setICIDGeneratedAt(), the host parameter is null.");
    }

    @Override
    public void setOriginatingIOI(String string) throws ParseException {
        if (string != null && string.length() != 0) {
            this.setParameter("orig-ioi", string);
        } else {
            this.removeParameter("orig-ioi");
        }
    }

    @Override
    public void setTerminatingIOI(String string) throws ParseException {
        if (string != null && string.length() != 0) {
            this.setParameter("term-ioi", string);
        } else {
            this.removeParameter("term-ioi");
        }
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

