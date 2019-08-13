/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.xml.sax.SAXException;

public class ElemTextLiteral
extends ElemTemplateElement {
    static final long serialVersionUID = -7872620006767660088L;
    private char[] m_ch;
    private boolean m_disableOutputEscaping = false;
    private boolean m_preserveSpace;
    private String m_str;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void execute(TransformerImpl object) throws TransformerException {
        try {
            object = ((TransformerImpl)object).getResultTreeHandler();
            boolean bl = this.m_disableOutputEscaping;
            if (bl) {
                object.processingInstruction("javax.xml.transform.disable-output-escaping", "");
            }
            object.characters(this.m_ch, 0, this.m_ch.length);
            if (this.m_disableOutputEscaping) {
                object.processingInstruction("javax.xml.transform.enable-output-escaping", "");
            }
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    public char[] getChars() {
        return this.m_ch;
    }

    public boolean getDisableOutputEscaping() {
        return this.m_disableOutputEscaping;
    }

    @Override
    public String getNodeName() {
        return "#Text";
    }

    @Override
    public String getNodeValue() {
        synchronized (this) {
            String string;
            if (this.m_str == null) {
                this.m_str = string = new String(this.m_ch);
            }
            string = this.m_str;
            return string;
        }
    }

    public boolean getPreserveSpace() {
        return this.m_preserveSpace;
    }

    @Override
    public int getXSLToken() {
        return 78;
    }

    public void setChars(char[] arrc) {
        this.m_ch = arrc;
    }

    public void setDisableOutputEscaping(boolean bl) {
        this.m_disableOutputEscaping = bl;
    }

    public void setPreserveSpace(boolean bl) {
        this.m_preserveSpace = bl;
    }
}

