/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ims.PAccessNetworkInfoHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class PAccessNetworkInfo
extends ParametersHeader
implements PAccessNetworkInfoHeader,
ExtensionHeader {
    private String accessType;
    private Object extendAccessInfo;

    public PAccessNetworkInfo() {
        super("P-Access-Network-Info");
        this.parameters.setSeparator(";");
    }

    public PAccessNetworkInfo(String string) {
        this();
        this.setAccessType(string);
    }

    @Override
    public Object clone() {
        return (PAccessNetworkInfo)super.clone();
    }

    @Override
    protected String encodeBody() {
        StringBuilder stringBuilder;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.getAccessType() != null) {
            stringBuffer.append(this.getAccessType());
        }
        if (!this.parameters.isEmpty()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("; ");
            stringBuilder.append(this.parameters.encode());
            stringBuffer.append(stringBuilder.toString());
        }
        if (this.getExtensionAccessInfo() != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("; ");
            stringBuilder.append(this.getExtensionAccessInfo().toString());
            stringBuffer.append(stringBuilder.toString());
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof PAccessNetworkInfoHeader && super.equals(object);
        return bl;
    }

    @Override
    public String getAccessType() {
        return this.accessType;
    }

    @Override
    public String getCGI3GPP() {
        return this.getParameter("cgi-3gpp");
    }

    @Override
    public String getCI3GPP2() {
        return this.getParameter("ci-3gpp2");
    }

    @Override
    public String getDSLLocation() {
        return this.getParameter("dsl-location");
    }

    @Override
    public Object getExtensionAccessInfo() {
        return this.extendAccessInfo;
    }

    @Override
    public String getUtranCellID3GPP() {
        return this.getParameter("utran-cell-id-3gpp");
    }

    @Override
    public void setAccessType(String string) {
        if (string != null) {
            this.accessType = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Access-Network-Info, setAccessType(), the accessType parameter is null.");
    }

    @Override
    public void setCGI3GPP(String string) throws ParseException {
        if (string != null) {
            this.setParameter("cgi-3gpp", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Access-Network-Info, setCGI3GPP(), the cgi parameter is null.");
    }

    @Override
    public void setCI3GPP2(String string) throws ParseException {
        if (string != null) {
            this.setParameter("ci-3gpp2", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Access-Network-Info, setCI3GPP2(), the ci3Gpp2 parameter is null.");
    }

    @Override
    public void setDSLLocation(String string) throws ParseException {
        if (string != null) {
            this.setParameter("dsl-location", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Access-Network-Info, setDSLLocation(), the dslLocation parameter is null.");
    }

    @Override
    public void setExtensionAccessInfo(Object object) throws ParseException {
        if (object != null) {
            this.extendAccessInfo = object;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Access-Network-Info, setExtendAccessInfo(), the extendAccessInfo parameter is null.");
    }

    @Override
    public void setParameter(String string, Object object) {
        if (!(string.equalsIgnoreCase("cgi-3gpp") || string.equalsIgnoreCase("utran-cell-id-3gpp") || string.equalsIgnoreCase("dsl-location") || string.equalsIgnoreCase("ci-3gpp2"))) {
            super.setParameter(string, object);
        } else {
            try {
                super.setQuotedParameter(string, object.toString());
            }
            catch (ParseException parseException) {
                // empty catch block
            }
        }
    }

    @Override
    public void setUtranCellID3GPP(String string) throws ParseException {
        if (string != null) {
            this.setParameter("utran-cell-id-3gpp", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Access-Network-Info, setUtranCellID3GPP(), the utranCellID parameter is null.");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

