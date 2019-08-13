/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public final class AttributesImplSerializer
extends AttributesImpl {
    private static final int MAX = 12;
    private static final int MAXMinus1 = 11;
    private final StringBuffer m_buff = new StringBuffer();
    private final Hashtable m_indexFromQName = new Hashtable();

    private void switchOverToHash(int n) {
        for (int i = 0; i < n; ++i) {
            String string = super.getQName(i);
            Integer n2 = new Integer(i);
            this.m_indexFromQName.put(string, n2);
            String string2 = super.getURI(i);
            string = super.getLocalName(i);
            this.m_buff.setLength(0);
            StringBuffer stringBuffer = this.m_buff;
            stringBuffer.append('{');
            stringBuffer.append(string2);
            stringBuffer.append('}');
            stringBuffer.append(string);
            string = this.m_buff.toString();
            this.m_indexFromQName.put(string, n2);
        }
    }

    @Override
    public final void addAttribute(String string, String string2, String charSequence, String object, String string3) {
        int n = super.getLength();
        super.addAttribute(string, string2, (String)charSequence, (String)object, string3);
        if (n < 11) {
            return;
        }
        if (n == 11) {
            this.switchOverToHash(12);
        } else {
            object = new Integer(n);
            this.m_indexFromQName.put(charSequence, object);
            this.m_buff.setLength(0);
            charSequence = this.m_buff;
            ((StringBuffer)charSequence).append('{');
            ((StringBuffer)charSequence).append(string);
            ((StringBuffer)charSequence).append('}');
            ((StringBuffer)charSequence).append(string2);
            string = this.m_buff.toString();
            this.m_indexFromQName.put(string, object);
        }
    }

    @Override
    public final void clear() {
        int n = super.getLength();
        super.clear();
        if (12 <= n) {
            this.m_indexFromQName.clear();
        }
    }

    @Override
    public final int getIndex(String object) {
        if (super.getLength() < 12) {
            return super.getIndex((String)object);
        }
        int n = (object = (Integer)this.m_indexFromQName.get(object)) == null ? -1 : (Integer)object;
        return n;
    }

    @Override
    public final int getIndex(String object, String string) {
        if (super.getLength() < 12) {
            return super.getIndex((String)object, string);
        }
        this.m_buff.setLength(0);
        StringBuffer stringBuffer = this.m_buff;
        stringBuffer.append('{');
        stringBuffer.append((String)object);
        stringBuffer.append('}');
        stringBuffer.append(string);
        object = this.m_buff.toString();
        object = (Integer)this.m_indexFromQName.get(object);
        int n = object == null ? -1 : (Integer)object;
        return n;
    }

    @Override
    public final void setAttributes(Attributes attributes) {
        super.setAttributes(attributes);
        int n = attributes.getLength();
        if (12 <= n) {
            this.switchOverToHash(n);
        }
    }
}

