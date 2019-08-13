/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import org.apache.xml.utils.NamespaceSupport2;

class PrefixForUriEnumerator
implements Enumeration {
    private Enumeration allPrefixes;
    private String lookahead = null;
    private NamespaceSupport2 nsup;
    private String uri;

    PrefixForUriEnumerator(NamespaceSupport2 namespaceSupport2, String string, Enumeration enumeration) {
        this.nsup = namespaceSupport2;
        this.uri = string;
        this.allPrefixes = enumeration;
    }

    @Override
    public boolean hasMoreElements() {
        if (this.lookahead != null) {
            return true;
        }
        while (this.allPrefixes.hasMoreElements()) {
            String string = (String)this.allPrefixes.nextElement();
            if (!this.uri.equals(this.nsup.getURI(string))) continue;
            this.lookahead = string;
            return true;
        }
        return false;
    }

    public Object nextElement() {
        if (this.hasMoreElements()) {
            String string = this.lookahead;
            this.lookahead = null;
            return string;
        }
        throw new NoSuchElementException();
    }
}

