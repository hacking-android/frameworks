/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.GenericObject;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.header.AlertInfoHeader;

public final class AlertInfo
extends ParametersHeader
implements AlertInfoHeader {
    private static final long serialVersionUID = 4159657362051508719L;
    protected String string;
    protected GenericURI uri;

    public AlertInfo() {
        super("Alert-Info");
    }

    @Override
    public Object clone() {
        AlertInfo alertInfo = (AlertInfo)super.clone();
        Object object = this.uri;
        if (object != null) {
            alertInfo.uri = (GenericURI)((GenericObject)object).clone();
        } else {
            object = this.string;
            if (object != null) {
                alertInfo.string = object;
            }
        }
        return alertInfo;
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.uri != null) {
            stringBuffer.append("<");
            stringBuffer.append(this.uri.encode());
            stringBuffer.append(">");
        } else {
            String string = this.string;
            if (string != null) {
                stringBuffer.append(string);
            }
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    @Override
    public URI getAlertInfo() {
        GenericURI genericURI = null;
        if (this.uri != null) {
            genericURI = this.uri;
        } else {
            try {
                GenericURI genericURI2;
                genericURI = genericURI2 = new GenericURI(this.string);
            }
            catch (ParseException parseException) {
                // empty catch block
            }
        }
        return genericURI;
    }

    @Override
    public void setAlertInfo(String string) {
        this.string = string;
    }

    @Override
    public void setAlertInfo(URI uRI) {
        this.uri = (GenericURI)uRI;
    }
}

