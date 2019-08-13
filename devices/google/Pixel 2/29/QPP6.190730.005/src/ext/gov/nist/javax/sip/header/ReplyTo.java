/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.HostPort;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.ReplyToHeader;

public final class ReplyTo
extends AddressParametersHeader
implements ReplyToHeader {
    private static final long serialVersionUID = -9103698729465531373L;

    public ReplyTo() {
        super("Reply-To");
    }

    public ReplyTo(AddressImpl addressImpl) {
        super("Reply-To");
        this.address = addressImpl;
    }

    @Override
    public String encode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.headerName);
        stringBuilder.append(":");
        stringBuilder.append(" ");
        stringBuilder.append(this.encodeBody());
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    @Override
    public String encodeBody() {
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
    public String getDisplayName() {
        return this.address.getDisplayName();
    }

    public HostPort getHostPort() {
        return this.address.getHostPort();
    }
}

