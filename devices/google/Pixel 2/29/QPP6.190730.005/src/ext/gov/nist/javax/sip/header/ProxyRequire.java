/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.ProxyRequireHeader;

public class ProxyRequire
extends SIPHeader
implements ProxyRequireHeader {
    private static final long serialVersionUID = -3269274234851067893L;
    protected String optionTag;

    public ProxyRequire() {
        super("Proxy-Require");
    }

    public ProxyRequire(String string) {
        super("Proxy-Require");
        this.optionTag = string;
    }

    @Override
    public String encodeBody() {
        return this.optionTag;
    }

    @Override
    public String getOptionTag() {
        return this.optionTag;
    }

    @Override
    public void setOptionTag(String string) throws ParseException {
        if (string != null) {
            this.optionTag = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, ProxyRequire, setOptionTag(), the optionTag parameter is null");
    }
}

