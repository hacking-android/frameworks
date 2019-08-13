/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionDef1Arg;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class FuncNormalizeSpace
extends FunctionDef1Arg {
    static final long serialVersionUID = -3377956872032190880L;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return (XString)this.getArg0AsString(xPathContext).fixWhiteSpace(true, true, false);
    }

    @Override
    public void executeCharsToContentHandler(XPathContext xPathContext, ContentHandler contentHandler) throws TransformerException, SAXException {
        if (this.Arg0IsNodesetExpr()) {
            int n = this.getArg0AsNode(xPathContext);
            if (-1 != n) {
                xPathContext.getDTM(n).dispatchCharactersEvents(n, contentHandler, true);
            }
        } else {
            this.execute(xPathContext).dispatchCharactersEvents(contentHandler);
        }
    }
}

