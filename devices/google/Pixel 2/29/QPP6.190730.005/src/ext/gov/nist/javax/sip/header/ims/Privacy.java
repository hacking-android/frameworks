/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PrivacyHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class Privacy
extends SIPHeader
implements PrivacyHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    private String privacy;

    public Privacy() {
        super("Privacy");
    }

    public Privacy(String string) {
        this();
        this.privacy = string;
    }

    @Override
    public Object clone() {
        Privacy privacy = (Privacy)super.clone();
        String string = this.privacy;
        if (string != null) {
            privacy.privacy = string;
        }
        return privacy;
    }

    @Override
    public String encodeBody() {
        return this.privacy;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PrivacyHeader) {
            object = (PrivacyHeader)object;
            return this.getPrivacy().equals(object.getPrivacy());
        }
        return false;
    }

    @Override
    public String getPrivacy() {
        return this.privacy;
    }

    @Override
    public void setPrivacy(String string) throws ParseException {
        if (string != null && string != "") {
            this.privacy = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  Privacy, setPrivacy(), privacy value is null or empty");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

