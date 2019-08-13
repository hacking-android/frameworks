/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

import java.util.List;
import javax.xml.xpath.XPathFunctionException;

public interface XPathFunction {
    public Object evaluate(List var1) throws XPathFunctionException;
}

