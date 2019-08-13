/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.sip.header.UserAgentHeader;

public class UserAgent
extends SIPHeader
implements UserAgentHeader {
    private static final long serialVersionUID = 4561239179796364295L;
    protected List productTokens = new LinkedList();

    public UserAgent() {
        super("User-Agent");
    }

    private String encodeProduct() {
        StringBuffer stringBuffer = new StringBuffer();
        ListIterator listIterator = this.productTokens.listIterator();
        while (listIterator.hasNext()) {
            stringBuffer.append((String)listIterator.next());
        }
        return stringBuffer.toString();
    }

    @Override
    public void addProductToken(String string) {
        this.productTokens.add(string);
    }

    @Override
    public Object clone() {
        UserAgent userAgent = (UserAgent)super.clone();
        List list = this.productTokens;
        if (list != null) {
            userAgent.productTokens = new LinkedList(list);
        }
        return userAgent;
    }

    @Override
    public String encodeBody() {
        return this.encodeProduct();
    }

    @Override
    public ListIterator getProduct() {
        List list = this.productTokens;
        if (list != null && !list.isEmpty()) {
            return this.productTokens.listIterator();
        }
        return null;
    }

    @Override
    public void setProduct(List list) throws ParseException {
        if (list != null) {
            this.productTokens = list;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, UserAgent, setProduct(), the  product parameter is null");
    }
}

