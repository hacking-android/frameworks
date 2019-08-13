/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValueList;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ims.PVisitedNetworkIDHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;
import javax.sip.header.Parameters;

public class PVisitedNetworkID
extends ParametersHeader
implements PVisitedNetworkIDHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    private boolean isQuoted;
    private String networkID;

    public PVisitedNetworkID() {
        super("P-Visited-Network-ID");
    }

    public PVisitedNetworkID(Token token) {
        super("P-Visited-Network-ID");
        this.setVisitedNetworkID(token.getTokenValue());
    }

    public PVisitedNetworkID(String string) {
        super("P-Visited-Network-ID");
        this.setVisitedNetworkID(string);
    }

    @Override
    public Object clone() {
        PVisitedNetworkID pVisitedNetworkID = (PVisitedNetworkID)super.clone();
        String string = this.networkID;
        if (string != null) {
            pVisitedNetworkID.networkID = string;
        }
        pVisitedNetworkID.isQuoted = this.isQuoted;
        return pVisitedNetworkID;
    }

    @Override
    protected String encodeBody() {
        StringBuilder stringBuilder;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.getVisitedNetworkID() != null) {
            if (this.isQuoted) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("\"");
                stringBuilder.append(this.getVisitedNetworkID());
                stringBuilder.append("\"");
                stringBuffer.append(stringBuilder.toString());
            } else {
                stringBuffer.append(this.getVisitedNetworkID());
            }
        }
        if (!this.parameters.isEmpty()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(";");
            stringBuilder.append(this.parameters.encode());
            stringBuffer.append(stringBuilder.toString());
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof PVisitedNetworkIDHeader;
        boolean bl2 = false;
        if (bl) {
            object = (PVisitedNetworkIDHeader)object;
            if (this.getVisitedNetworkID().equals(object.getVisitedNetworkID()) && this.equalParameters((Parameters)object)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    public String getVisitedNetworkID() {
        return this.networkID;
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }

    @Override
    public void setVisitedNetworkID(Token token) {
        if (token != null) {
            this.networkID = token.getTokenValue();
            this.isQuoted = false;
            return;
        }
        throw new NullPointerException(" the networkID parameter is null");
    }

    @Override
    public void setVisitedNetworkID(String string) {
        if (string != null) {
            this.networkID = string;
            this.isQuoted = true;
            return;
        }
        throw new NullPointerException(" the networkID parameter is null");
    }
}

