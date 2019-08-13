/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.extensions.ReferredByHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public final class ReferredBy
extends AddressParametersHeader
implements ExtensionHeader,
ReferredByHeader {
    public static final String NAME = "Referred-By";
    private static final long serialVersionUID = 3134344915465784267L;

    public ReferredBy() {
        super(NAME);
    }

    @Override
    protected String encodeBody() {
        if (this.address == null) {
            return null;
        }
        CharSequence charSequence = "";
        if (this.address.getAddressType() == 2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append("<");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(this.address.encode());
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = charSequence2;
        if (this.address.getAddressType() == 2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(">");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (!this.parameters.isEmpty()) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(";");
            ((StringBuilder)charSequence2).append(this.parameters.encode());
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

