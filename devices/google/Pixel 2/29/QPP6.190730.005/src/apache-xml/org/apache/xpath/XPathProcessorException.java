/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import org.apache.xpath.XPathException;

public class XPathProcessorException
extends XPathException {
    static final long serialVersionUID = 1215509418326642603L;

    public XPathProcessorException(String string) {
        super(string);
    }

    public XPathProcessorException(String string, Exception exception) {
        super(string, exception);
    }
}

