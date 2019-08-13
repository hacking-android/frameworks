/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.EmptyStackException;
import java.util.Enumeration;
import org.apache.xml.utils.Context2;
import org.apache.xml.utils.PrefixForUriEnumerator;
import org.xml.sax.helpers.NamespaceSupport;

public class NamespaceSupport2
extends NamespaceSupport {
    public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
    private Context2 currentContext;

    public NamespaceSupport2() {
        this.reset();
    }

    @Override
    public boolean declarePrefix(String string, String string2) {
        if (!string.equals("xml") && !string.equals("xmlns")) {
            this.currentContext.declarePrefix(string, string2);
            return true;
        }
        return false;
    }

    @Override
    public Enumeration getDeclaredPrefixes() {
        return this.currentContext.getDeclaredPrefixes();
    }

    @Override
    public String getPrefix(String string) {
        return this.currentContext.getPrefix(string);
    }

    @Override
    public Enumeration getPrefixes() {
        return this.currentContext.getPrefixes();
    }

    @Override
    public Enumeration getPrefixes(String string) {
        return new PrefixForUriEnumerator(this, string, this.getPrefixes());
    }

    @Override
    public String getURI(String string) {
        return this.currentContext.getURI(string);
    }

    @Override
    public void popContext() {
        Context2 context2 = this.currentContext.getParent();
        if (context2 != null) {
            this.currentContext = context2;
            return;
        }
        throw new EmptyStackException();
    }

    @Override
    public String[] processName(String arrstring, String[] arrstring2, boolean bl) {
        if ((arrstring = this.currentContext.processName((String)arrstring, bl)) == null) {
            return null;
        }
        System.arraycopy(arrstring, 0, arrstring2, 0, 3);
        return arrstring2;
    }

    @Override
    public void pushContext() {
        Context2 context2 = this.currentContext;
        this.currentContext = context2.getChild();
        Context2 context22 = this.currentContext;
        if (context22 == null) {
            this.currentContext = new Context2(context2);
        } else {
            context22.setParent(context2);
        }
    }

    @Override
    public void reset() {
        this.currentContext = new Context2(null);
        this.currentContext.declarePrefix("xml", XMLNS);
    }
}

