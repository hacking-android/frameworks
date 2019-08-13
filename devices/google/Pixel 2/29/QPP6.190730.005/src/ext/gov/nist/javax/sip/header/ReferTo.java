/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.ReferToHeader;

public final class ReferTo
extends AddressParametersHeader
implements ReferToHeader {
    private static final long serialVersionUID = -1666700428440034851L;

    public ReferTo() {
        super("Refer-To");
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
}

