/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;
import javax.sip.header.Header;

public interface ServerHeader
extends Header {
    public static final String NAME = "Server";

    public void addProductToken(String var1);

    public ListIterator getProduct();

    public void setProduct(List var1) throws ParseException;
}

