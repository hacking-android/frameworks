/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import javax.xml.transform.SourceLocator;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPath;

public interface XPathFactory {
    public XPath create(String var1, SourceLocator var2, PrefixResolver var3, int var4);
}

