/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.extensions.MinSEHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ExtensionHeader;

public class MinSE
extends ParametersHeader
implements ExtensionHeader,
MinSEHeader {
    public static final String NAME = "Min-SE";
    private static final long serialVersionUID = 3134344915465784267L;
    public int expires;

    public MinSE() {
        super(NAME);
    }

    @Override
    public String encodeBody() {
        String string = Integer.toString(this.expires);
        CharSequence charSequence = string;
        if (!this.parameters.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(";");
            ((StringBuilder)charSequence).append(this.parameters.encode());
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public int getExpires() {
        return this.expires;
    }

    public void setExpires(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.expires = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad argument ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

