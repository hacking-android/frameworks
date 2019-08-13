/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import java.util.Iterator;

public interface Parameters {
    public String getParameter(String var1);

    public Iterator getParameterNames();

    public void removeParameter(String var1);

    public void setParameter(String var1, String var2) throws ParseException;
}

