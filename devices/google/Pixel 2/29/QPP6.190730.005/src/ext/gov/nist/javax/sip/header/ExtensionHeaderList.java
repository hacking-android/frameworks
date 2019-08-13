/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.ExtensionHeaderImpl;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;
import java.util.ListIterator;

public class ExtensionHeaderList
extends SIPHeaderList<ExtensionHeaderImpl> {
    private static final long serialVersionUID = 4681326807149890197L;

    public ExtensionHeaderList() {
        super(ExtensionHeaderImpl.class, null);
    }

    public ExtensionHeaderList(String string) {
        super(ExtensionHeaderImpl.class, string);
    }

    @Override
    public Object clone() {
        ExtensionHeaderList extensionHeaderList = new ExtensionHeaderList(this.headerName);
        extensionHeaderList.clonehlist(this.hlist);
        return extensionHeaderList;
    }

    @Override
    public String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        ListIterator listIterator = this.listIterator();
        while (listIterator.hasNext()) {
            stringBuffer.append(((ExtensionHeaderImpl)listIterator.next()).encode());
        }
        return stringBuffer.toString();
    }
}

