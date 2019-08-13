/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.text.Collator;
import java.util.Locale;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPath;

class NodeSortKey {
    boolean m_caseOrderUpper;
    Collator m_col;
    boolean m_descending;
    Locale m_locale;
    PrefixResolver m_namespaceContext;
    TransformerImpl m_processor;
    XPath m_selectPat;
    boolean m_treatAsNumbers;

    NodeSortKey(TransformerImpl transformerImpl, XPath xPath, boolean bl, boolean bl2, String string, boolean bl3, PrefixResolver prefixResolver) throws TransformerException {
        this.m_processor = transformerImpl;
        this.m_namespaceContext = prefixResolver;
        this.m_selectPat = xPath;
        this.m_treatAsNumbers = bl;
        this.m_descending = bl2;
        this.m_caseOrderUpper = bl3;
        if (string != null && !this.m_treatAsNumbers) {
            this.m_locale = new Locale(string.toLowerCase(), Locale.getDefault().getCountry());
            if (this.m_locale == null) {
                this.m_locale = Locale.getDefault();
            }
        } else {
            this.m_locale = Locale.getDefault();
        }
        this.m_col = Collator.getInstance(this.m_locale);
        if (this.m_col == null) {
            this.m_processor.getMsgMgr().warn(null, "WG_CANNOT_FIND_COLLATOR", new Object[]{string});
            this.m_col = Collator.getInstance();
        }
    }
}

